package com.hondacrm.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.hondacrm.Activity.MainPage;
import com.hondacrm.Extra.DetectConnection;
import com.hondacrm.Module.CategoryResponse;
import com.hondacrm.Module.ModelColorResponse;
import com.hondacrm.Module.ModelNameResponse;
import com.hondacrm.Module.ModelVariantResponse;
import com.hondacrm.R;
import com.hondacrm.Retrofit.Api;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FilterVehicle extends Fragment {

    public static TextView selectFromDate, selectToDate;
    View view;
    @BindView(R.id.mobileNumber)
    TextInputEditText mobileNumber;
    @BindViews({R.id.categorySpinner, R.id.nameSpinner, R.id.variantSpinner, R.id.colorSpinner, R.id.statusSpinner})
    List<MaterialSpinner> materialSpinners;
    List<CategoryResponse> categoryResponseList = new ArrayList<>();
    List<ModelNameResponse> modelNameResponseList = new ArrayList<>();
    List<ModelVariantResponse> modelVariantResponseList = new ArrayList<>();
    List<ModelColorResponse> modelColorResponseList = new ArrayList<>();
    String[] categoryIdList, categoryNameList, modelNameIdList, modelNameList, variantIdList, variantNameList, colorIdList,
            colorNameList, statusIdList, statusNameList;
    String categoryId = "0", categoryName, modelNameId = "0", modelName, variantId = "0", variantName, colorId = "0", colorName, statusId="0", statusName;
    //Select Date
    Calendar calender;
    DatePickerDialog datePickerDialog;
    private int mYear, mMonth, mDay;
    private int hour, minute, second;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_filter_vehicle, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("");

        initViews();

        MainPage.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    ((MainPage) getActivity()).removeCurrentFragmentAndMoveBack();
                    ((MainPage) getActivity()).loadFragment(new Home(), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("tag", "onKey Back listener is working!!!");
                    try {
                        ((MainPage) getActivity()).removeCurrentFragmentAndMoveBack();
                        ((MainPage) getActivity()).loadFragment(new Home(), false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });

        try {
            calender = Calendar.getInstance();
            System.out.println("Current time => " + calender.getTime());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(calender.getTime());
            selectFromDate.setText("" + formattedDate);
            selectToDate.setText("" + formattedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }


        materialSpinners.get(0).setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                try {

                    categoryId = categoryIdList[position];
                    categoryName = categoryNameList[position];
                    getModelNameList(categoryId);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        materialSpinners.get(1).setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                try {

                    modelNameId = modelNameIdList[position];
                    modelName = modelNameList[position];
                    getModelVariantList(modelNameId);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        materialSpinners.get(2).setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                try {

                    variantId = variantIdList[position];
                    variantName = variantNameList[position];
                    getModelColorList(variantId);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        materialSpinners.get(3).setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                try {

                    colorId = colorIdList[position];
                    colorName = colorNameList[position];

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        statusNameList = getActivity().getResources().getStringArray(R.array.status);
        try {
            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, statusNameList);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            materialSpinners.get(4).setAdapter(adapter);
            if (statusName != null) {
                materialSpinners.get(4).setSelectedIndex(adapter.getPosition("" + statusName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        materialSpinners.get(4).setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                try {

                    statusName = statusNameList[position];
                    if (statusNameList[position].equalsIgnoreCase("Open")) {
                        statusId = "1";
                    } else if (statusNameList[position].equalsIgnoreCase("Close")) {
                        statusId = "2";
                    } else {
                        statusId = "0";
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });


        return view;
    }

    private void initViews() {
        selectFromDate = view.findViewById(R.id.selectFromDate);
        selectToDate = view.findViewById(R.id.selectToDate);
    }

    @OnClick({R.id.selectFromDate, R.id.selectToDate, R.id.clear, R.id.apply})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.selectFromDate:

                // Get Current Date
                mYear = calender.get(Calendar.YEAR);
                mMonth = calender.get(Calendar.MONTH);
                mDay = calender.get(Calendar.DAY_OF_MONTH);

                showFromDatePickerDialog(view);

                break;

            case R.id.selectToDate:

                // Get Current Date
                mYear = calender.get(Calendar.YEAR);
                mMonth = calender.get(Calendar.MONTH);
                mDay = calender.get(Calendar.DAY_OF_MONTH);

                if (selectFromDate.getText().toString().equalsIgnoreCase("")) {
                    Toasty.error(getActivity(), "Select From Date", Toasty.LENGTH_SHORT).show();
                } else {
                    showToDatePickerDialog(view);
                }

                break;

            case R.id.clear:
                categoryId = null;
                modelNameId = null;
                variantId = null;
                colorId = null;
                selectFromDate.setText("");
                selectToDate.setText("");
                mobileNumber.setText("");
                break;

            case R.id.apply:
                applyFilter(mobileNumber.getText().toString().trim(), categoryId, modelNameId, variantId, colorId, statusId, selectFromDate.getText().toString(), selectToDate.getText().toString());
                break;

        }
    }

    private void applyFilter(String mobileNumber, String categoryId, String modelNameId, String variantId, String colorId, String statusId, String fromDate, String toDate) {

        FilterEnquiryList filterEnquiryList = new FilterEnquiryList();
        Bundle bundle = new Bundle();
        bundle.putString("mobileNumber", "" + mobileNumber);
        bundle.putString("categoryId", "" + categoryId);
        bundle.putString("modelNameId", "" + modelNameId);
        bundle.putString("variantId", "" + variantId);
        bundle.putString("colorId", "" + colorId);
        bundle.putString("statusId", "" + statusId);
        bundle.putString("fromDate", "" + fromDate);
        bundle.putString("toDate", "" + toDate);
        filterEnquiryList.setArguments(bundle);
        ((MainPage) getActivity()).loadFragment(filterEnquiryList, true);

    }

    private void showFromDatePickerDialog(View view) {

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getSupportFragmentManager(), "From Date");

    }

    public void showToDatePickerDialog(View view) {
        DialogFragment newFragment = new ToDatePickerFragment();
        newFragment.show(getActivity().getSupportFragmentManager(), "To Date");
    }

    private void getModelColorList(String variantId) {

        Call<List<ModelColorResponse>> call = Api.getClient().getModelColorList(variantId);
        call.enqueue(new Callback<List<ModelColorResponse>>() {
            @Override
            public void onResponse(Call<List<ModelColorResponse>> call, Response<List<ModelColorResponse>> response) {

                if (response.isSuccessful()) {

                    modelColorResponseList = response.body();
                    if (modelColorResponseList.size() > 0) {

                        colorIdList = new String[modelColorResponseList.size()];
                        colorNameList = new String[modelColorResponseList.size()];

                        for (int i = 0; i < modelColorResponseList.size(); i++) {

                            colorIdList[i] = modelColorResponseList.get(i).getId();
                            colorNameList[i] = modelColorResponseList.get(i).getModelColor();

                        }

                        try {
                            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, colorNameList);
                            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            materialSpinners.get(3).setAdapter(adapter);
                            if (colorId != null) {
                                materialSpinners.get(3).setSelectedIndex(adapter.getPosition("" + colorId));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {

                    }

                } else {

                }

            }

            @Override
            public void onFailure(Call<List<ModelColorResponse>> call, Throwable t) {
                Log.e("colorError", "" + t.getMessage());
            }
        });


    }

    private void getModelVariantList(String modelNameId) {

        Call<List<ModelVariantResponse>> call = Api.getClient().getModelvariantList(modelNameId);
        call.enqueue(new Callback<List<ModelVariantResponse>>() {
            @Override
            public void onResponse(Call<List<ModelVariantResponse>> call, Response<List<ModelVariantResponse>> response) {

                if (response.isSuccessful()) {

                    modelVariantResponseList = response.body();
                    if (modelVariantResponseList.size() > 0) {

                        variantIdList = new String[modelVariantResponseList.size()];
                        variantNameList = new String[modelVariantResponseList.size()];

                        for (int i = 0; i < modelVariantResponseList.size(); i++) {

                            variantIdList[i] = modelVariantResponseList.get(i).getId();
                            variantNameList[i] = modelVariantResponseList.get(i).getModelVarient();

                        }

                        try {
                            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, variantNameList);
                            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            materialSpinners.get(2).setAdapter(adapter);
                            if (variantId != null) {
                                materialSpinners.get(2).setSelectedIndex(adapter.getPosition("" + variantId));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {

                    }

                } else {

                }

            }

            @Override
            public void onFailure(Call<List<ModelVariantResponse>> call, Throwable t) {
                Log.e("variantError", "" + t.getMessage());
            }
        });

    }

    private void getModelNameList(String categoryId) {

        Call<List<ModelNameResponse>> call = Api.getClient().getModelNameList(categoryId);
        call.enqueue(new Callback<List<ModelNameResponse>>() {
            @Override
            public void onResponse(Call<List<ModelNameResponse>> call, Response<List<ModelNameResponse>> response) {

                if (response.isSuccessful()) {
                    modelNameResponseList = response.body();
                    if (modelNameResponseList.size() > 0) {
                        modelNameIdList = new String[modelNameResponseList.size()];
                        modelNameList = new String[modelNameResponseList.size()];

                        for (int i = 0; i < modelNameResponseList.size(); i++) {
                            modelNameIdList[i] = modelNameResponseList.get(i).getId();
                            modelNameList[i] = modelNameResponseList.get(i).getModelName();
                        }

                        try {
                            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, modelNameList);
                            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            materialSpinners.get(1).setAdapter(adapter);
                            if (modelNameId != null) {
                                materialSpinners.get(1).setSelectedIndex(adapter.getPosition("" + modelNameId));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {

                    }

                } else {

                }

            }

            @Override
            public void onFailure(Call<List<ModelNameResponse>> call, Throwable t) {
                Log.e("NameError", "" + t.getMessage());
            }
        });

    }

    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        if (DetectConnection.checkInternetConnection(getActivity())) {
            getCategoryList();
        } else {
            DetectConnection.noInternetConnection(getActivity());
        }
    }

    private void getCategoryList() {

        Call<List<CategoryResponse>> call = Api.getClient().getCategoryList();
        call.enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {

                categoryResponseList = response.body();
                if (categoryResponseList.size() > 0) {

                    categoryIdList = new String[categoryResponseList.size()];
                    categoryNameList = new String[categoryResponseList.size()];

                    for (int i = 0; i < categoryResponseList.size(); i++) {
                        categoryIdList[i] = categoryResponseList.get(i).getId();
                        categoryNameList[i] = categoryResponseList.get(i).getModelCategory();
                    }

                    try {
                        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, categoryNameList);
                        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                        materialSpinners.get(0).setAdapter(adapter);
                        if (categoryId != null) {
                            materialSpinners.get(0).setSelectedIndex(adapter.getPosition("" + categoryId));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {
                Log.e("contactError", "" + t.getMessage());
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}