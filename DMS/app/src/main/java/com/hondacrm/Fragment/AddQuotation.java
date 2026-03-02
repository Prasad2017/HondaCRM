package com.hondacrm.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hondacrm.Activity.MainPage;
import com.hondacrm.Extra.DetectConnection;
import com.hondacrm.Module.BranchResponse;
import com.hondacrm.Module.CategoryResponse;
import com.hondacrm.Module.ContactResponse;
import com.hondacrm.Module.EnquiryMaxNumberResponse;
import com.hondacrm.Module.EnquiryResponse;
import com.hondacrm.Module.FinanceResponse;
import com.hondacrm.Module.ModelColorResponse;
import com.hondacrm.Module.ModelNameResponse;
import com.hondacrm.Module.ModelPriceResponse;
import com.hondacrm.Module.ModelVariantResponse;
import com.hondacrm.Module.SourceResponse;
import com.hondacrm.Module.StageResponse;
import com.hondacrm.R;
import com.hondacrm.Retrofit.Api;
import com.ikovac.timepickerwithseconds.MyTimePickerDialog;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddQuotation extends Fragment {

    View view;
    @BindViews({R.id.contactSpinner, R.id.categorySpinner, R.id.nameSpinner, R.id.variantSpinner, R.id.colorSpinner, R.id.statusSpinner,
                R.id.purchaseTypeSpinner, R.id.enquiryTypeSpinner, R.id.stageSpinner, R.id.sourceSpinner, R.id.customerTypeSpinner, R.id.branchSpinner,
                R.id.financeTypeSpinner, R.id.financierSpinner})
    List<MaterialSpinner> materialSpinners;
    @BindView(R.id.searchContactSpinner)
    SearchableSpinner searchContactSpinner;
    @BindViews({R.id.mobileNumber, R.id.description, R.id.campaign, R.id.enquiryNumber, R.id.unitPrice, R.id.exShowRoomPrice, R.id.roadTaxFee, R.id.roadSide, R.id.amc,
            R.id.extendedWarranty, R.id.other, R.id.taxType, R.id.taxType2, R.id.cessType, R.id.percentage, R.id.percentage2, R.id.cessPercentage, R.id.insurance, R.id.accessories})
    List<TextInputEditText> textInputEditTexts;
    @BindViews({R.id.campaignLayout})
    List<TextInputLayout> textInputLayouts;
    @BindView(R.id.selectDate)
    TextView textView;
    @BindView(R.id.selectTime)
    TextView selectTime;
    @BindView(R.id.financeLayout)
    LinearLayout financeLayout;
    //List of module class
    List<ContactResponse> contactResponseList = new ArrayList<>();
    List<CategoryResponse> categoryResponseList = new ArrayList<>();
    List<ModelNameResponse> modelNameResponseList = new ArrayList<>();
    List<ModelVariantResponse> modelVariantResponseList = new ArrayList<>();
    List<ModelColorResponse> modelColorResponseList = new ArrayList<>();
    List<EnquiryResponse> enquiryResponseList = new ArrayList<>();
    List<SourceResponse> sourceResponseList = new ArrayList<>();
    List<StageResponse> stageResponseList = new ArrayList<>();
    List<BranchResponse> branchResponseList = new ArrayList<>();
    List<FinanceResponse> financeResponseList = new ArrayList<>();
    //String of array List
    String[] contactIdList, contactNameList, contactNumberList, categoryIdList, categoryNameList, modelNameIdList, modelNameList, variantIdList, variantNameList, colorIdList,
            colorNameList, enquiryIdList, enquiryNameList, statusNameList, purchaseNameList, sourceIdList, sourceNameList, stageIdList, stageNameList, customerTypeList,
            branchIdList, branchNameList, financeTypeList, financierIdList, financierNameList;
    String contactId, contactNumber, categoryId, categoryName, modelNameId, modelName, variantId, variantName, colorId, colorName, enquiryId, enquiryName, statusId, statusName = "Open", purchaseNameId, purchaseName,
            sourceId, sourceName, stageId = "2", stageName = "Quotation", customerTypeId, customerType, branchId, branchName, financeTypeId="0", financeTypeName, financierId="0", financierName;
    String branchEnquiryNumber, branchEnquiryName;
    //Select Date
    Calendar calender;
    DatePickerDialog datePickerDialog;
    private int mYear, mMonth, mDay;
    private int hour, minute, second;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_quotation, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("Create Quotation");

        try {
            calender = Calendar.getInstance();
            System.out.println("Current time => " + calender.getTime());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String formattedDate = df.format(calender.getTime());
            String formattedTimeDate = timeFormat.format(calender.getTime());
            textView.setText("" + formattedDate);
            selectTime.setText("" + formattedTimeDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Bundle bundle = getArguments();
            if (bundle != null) {
                contactId = bundle.getString("contactId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .detectNetwork()
                    .detectCustomSlowCalls()
                    .permitNetwork()
                    .permitAll()
                    .build();
            StrictMode.setThreadPolicy(policy);
        }

        MainPage.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainPage) getActivity()).removeCurrentFragmentAndMoveBack();
                ((MainPage) getActivity()).loadFragment(new QuotationList(), true);
            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("tag", "onKey Back listener is working!!!");
                    ((MainPage) getActivity()).removeCurrentFragmentAndMoveBack();
                    ((MainPage) getActivity()).loadFragment(new QuotationList(), true);
                    return true;
                }
                return false;
            }
        });

        searchContactSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                try {

                    contactId = contactIdList[position];
                    contactNumber = contactNumberList[position];

                    textInputEditTexts.get(0).setText("" + contactNumber);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        materialSpinners.get(1).setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
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

        materialSpinners.get(2).setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
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

        materialSpinners.get(3).setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                try {

                    variantId = variantIdList[position];
                    variantName = variantNameList[position];

                    getModelColorList(variantId);
                    getModelPriceList(variantId);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        materialSpinners.get(4).setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
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
            materialSpinners.get(5).setAdapter(adapter);
            if (statusName != null) {
                materialSpinners.get(8).setSelectedIndex(adapter.getPosition("" + statusName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        materialSpinners.get(5).setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                try {

                    statusName = statusNameList[position];
                    if (statusNameList[position].equalsIgnoreCase("Open")) {
                        statusId = "1";
                    } else if (statusNameList[position].equalsIgnoreCase("Close")) {
                        statusId = "2";
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        purchaseNameList = getActivity().getResources().getStringArray(R.array.purchase_type);
        try {
            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, purchaseNameList);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            materialSpinners.get(6).setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        materialSpinners.get(6).setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                try {

                    purchaseName = purchaseNameList[position];
                    if (purchaseName.equalsIgnoreCase("Cash")) {
                        purchaseNameId = "1";
                        financeLayout.setVisibility(View.GONE);
                    } else if (purchaseName.equalsIgnoreCase("Finance")) {
                        purchaseNameId = "2";
                        financeLayout.setVisibility(View.VISIBLE);
                    } else if (purchaseName.equalsIgnoreCase("Best Deal")) {
                        purchaseNameId = "3";
                        financeLayout.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        materialSpinners.get(7).setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                try {

                    enquiryId = enquiryIdList[position];
                    enquiryName = enquiryNameList[position];

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        stageNameList = getActivity().getResources().getStringArray(R.array.stage_quotation);
        try {
            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, stageNameList);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            materialSpinners.get(8).setAdapter(adapter);
            if (stageName != null) {
                materialSpinners.get(8).setSelectedIndex(adapter.getPosition("" + stageName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        materialSpinners.get(8).setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                try {

                    stageName = stageNameList[position];
                    stageId = "2";

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        materialSpinners.get(9).setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                try {

                    sourceId = sourceIdList[position];
                    sourceName = sourceNameList[position];

                    if (sourceNameList[position].equalsIgnoreCase("Facebook")) {
                        textInputLayouts.get(0).setVisibility(View.VISIBLE);
                    } else if (sourceNameList[position].equalsIgnoreCase("Instagram")) {
                        textInputLayouts.get(0).setVisibility(View.VISIBLE);
                    } else if (sourceNameList[position].equalsIgnoreCase("Digital")) {
                        textInputLayouts.get(0).setVisibility(View.VISIBLE);
                    } else if (sourceNameList[position].equalsIgnoreCase("Instagram")) {
                        textInputLayouts.get(0).setVisibility(View.VISIBLE);
                    } else {
                        textInputLayouts.get(0).setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        customerTypeList = getActivity().getResources().getStringArray(R.array.customer_type);
        try {
            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, customerTypeList);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            materialSpinners.get(10).setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        materialSpinners.get(10).setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                try {

                    customerType = customerTypeList[position];
                    if (customerType.equalsIgnoreCase("Hot")) {
                        customerTypeId = "1";
                    } else if (customerType.equalsIgnoreCase("Warm")) {
                        customerTypeId = "2";
                    } else if (customerType.equalsIgnoreCase("Cold")) {
                        customerTypeId = "3";
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        materialSpinners.get(11).setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                try {

                    branchId = branchIdList[position];
                    branchName = branchNameList[position];

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        financeTypeList = getActivity().getResources().getStringArray(R.array.finance_type);
        try {
            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, financeTypeList);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            materialSpinners.get(12).setAdapter(adapter);
            if (financeTypeName != null) {
                materialSpinners.get(12).setSelectedIndex(adapter.getPosition("" + financeTypeName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        materialSpinners.get(12).setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                try {

                    financeTypeName = financeTypeList[position];
                    if (financeTypeName.equalsIgnoreCase("In-House")) {
                        financeTypeId = "1";
                    } else if (financeTypeName.equalsIgnoreCase("Out-House")) {
                        financeTypeId = "2";
                    }

                    getFinancierList(financeTypeId);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        materialSpinners.get(13).setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                try {
                    financierId = financierIdList[position];
                    financierName = financierNameList[position];
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        return view;

    }

    private void getFinancierList(String financeTypeId) {

        Call<List<FinanceResponse>> call = Api.getClient().getFinancierList();
        call.enqueue(new Callback<List<FinanceResponse>>() {
            @Override
            public void onResponse(Call<List<FinanceResponse>> call, Response<List<FinanceResponse>> response) {
                if (response.isSuccessful()) {
                    financeResponseList = response.body();
                    if (financeResponseList!=null && financeResponseList.size()>0) {

                        financierIdList = new String[financeResponseList.size()];
                        financierNameList = new String[financeResponseList.size()];
                        for (int i=0; i<financeResponseList.size(); i++) {
                            financierIdList[i] = financeResponseList.get(i).getFinanceId();
                            financierNameList[i] = financeResponseList.get(i).getFinanceName();
                        }

                        try {
                            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, financierNameList);
                            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            materialSpinners.get(13).setAdapter(adapter);
                            if (financierName != null) {
                                materialSpinners.get(13).setSelectedIndex(adapter.getPosition("" + financierName));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<List<FinanceResponse>> call, Throwable t) {
                Log.e("FinancierError", "" + t.getMessage());
            }
        });

    }


    private void getModelPriceList(String variantId) {

        Call<List<ModelPriceResponse>> call = Api.getClient().getModelPriceList(variantId);
        call.enqueue(new Callback<List<ModelPriceResponse>>() {
            @Override
            public void onResponse(Call<List<ModelPriceResponse>> call, Response<List<ModelPriceResponse>> response) {

                if (response.isSuccessful()) {

                    try {
                        textInputEditTexts.get(4).setText("" + response.body().get(0).getRegistrationAmount());
                        textInputEditTexts.get(5).setText("" + response.body().get(0).getExShowroomPrice());
                        textInputEditTexts.get(6).setText("" + response.body().get(0).getRoadTax());
                        textInputEditTexts.get(7).setText("" + response.body().get(0).getOnRoadPrice());
                        textInputEditTexts.get(8).setText("0");
                        textInputEditTexts.get(9).setText("" + response.body().get(0).getExtendedWarranty());
                        textInputEditTexts.get(10).setText("" + response.body().get(0).getOtherTaxes());
                        textInputEditTexts.get(17).setText("" + response.body().get(0).getInsuranceAmount());
                        textInputEditTexts.get(11).setText("" + response.body().get(0).getTaxId1Type());
                        textInputEditTexts.get(12).setText("" + response.body().get(0).getTaxId2Type());
                        textInputEditTexts.get(13).setText("" + response.body().get(0).getCessType());
                        textInputEditTexts.get(14).setText("" + response.body().get(0).getTaxId1Name());
                        textInputEditTexts.get(15).setText("" + response.body().get(0).getTaxId2Name());
                        int cessPercentage = Integer.parseInt(response.body().get(0).getCessName().replaceAll("[\\D]", ""));
                        textInputEditTexts.get(16).setText("" + cessPercentage);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<List<ModelPriceResponse>> call, Throwable t) {
                Log.e("priceError", "" + t.getMessage());
            }
        });

    }

    private void getQuotationPrefix(String branchId) {

        Call<List<EnquiryMaxNumberResponse>> call = Api.getClient().getEnquiryPrefixList(branchId);
        call.enqueue(new Callback<List<EnquiryMaxNumberResponse>>() {
            @Override
            public void onResponse(Call<List<EnquiryMaxNumberResponse>> call, Response<List<EnquiryMaxNumberResponse>> response) {

                if (response.isSuccessful()) {

                    try {

                        int CurrentYear = Calendar.getInstance().get(Calendar.YEAR);
                        int CurrentMonth = (Calendar.getInstance().get(Calendar.MONTH) + 1);
                        String financialYearFrom = "";
                        String financialYearTo = "";
                        if (CurrentMonth < 4) {
                            financialYearFrom = "" + (CurrentYear - 1);
                            financialYearTo = "" + (CurrentYear);
                        } else {
                            financialYearFrom = "" + (CurrentYear);
                            financialYearTo = "" + (CurrentYear + 1);
                        }

                        financialYearFrom = financialYearFrom.substring(2, 4);
                        financialYearTo = financialYearTo.substring(2, 4);

                        String enquiryPrefix = "" + response.body().get(0).getQuoPrefix();
                        getQuotationMaxNumber(branchId, "" + financialYearFrom + "" + financialYearTo, enquiryPrefix);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                }

            }

            @Override
            public void onFailure(Call<List<EnquiryMaxNumberResponse>> call, Throwable t) {
                Log.e("prefixError", "" + t.getMessage());
            }
        });

    }

    private void getQuotationMaxNumber(String branchId, String currentYear, String enquiryPrefix) {

        Call<List<EnquiryMaxNumberResponse>> call = Api.getClient().getQuotationMaxNumberList();
        call.enqueue(new Callback<List<EnquiryMaxNumberResponse>>() {
            @Override
            public void onResponse(Call<List<EnquiryMaxNumberResponse>> call, Response<List<EnquiryMaxNumberResponse>> response) {

                if (response.isSuccessful()) {
                    try {
                        int enquiryNumber = Integer.parseInt(response.body().get(0).getQuotationNo());
                        enquiryNumber = enquiryNumber + 1;
                        branchEnquiryNumber = "" + enquiryNumber;
                        branchEnquiryName = "" + enquiryPrefix;
                        textInputEditTexts.get(3).setText("" + enquiryPrefix + "-" + currentYear + "-" + enquiryNumber);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {

                }

            }

            @Override
            public void onFailure(Call<List<EnquiryMaxNumberResponse>> call, Throwable t) {
                Log.e("enquiryNumberError", "" + t.getMessage());
            }
        });
    }

    private void getCloseDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setMessage("Do you want to exit this page ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    ((MainPage) getActivity()).removeCurrentFragmentAndMoveBack();
                    ((MainPage) getActivity()).loadFragment(new Home(), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Create the alert dialog and change Buttons colour
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        dialog.show();

    }

    @OnClick({R.id.addNewContact, R.id.selectDate, R.id.selectTime, R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addNewContact:
                ((MainPage) getActivity()).loadFragment(new QuotationContact(), true);
                break;
            case R.id.selectDate:

                //Get Current Date
                calender = Calendar.getInstance();
                mYear = calender.get(Calendar.YEAR);
                mMonth = calender.get(Calendar.MONTH);
                mDay = calender.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if ((monthOfYear + 1) > 9) {
                            if ((dayOfMonth) > 9) {
                                textView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            } else {
                                textView.setText(year + "-" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
                            }
                        } else {
                            if ((dayOfMonth) > 9) {
                                textView.setText(year + "-" + "0" + (monthOfYear + 1) + "-" + dayOfMonth);
                            } else {
                                textView.setText(year + "-" + "0" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
                            }
                        }
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

                break;

            case R.id.selectTime:

                // Get Current Time
                final Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);
                second = c.get(Calendar.SECOND);
                // Launch Time Picker Dialog
                MyTimePickerDialog mTimePicker = new MyTimePickerDialog(getActivity(), new MyTimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(com.ikovac.timepickerwithseconds.TimePicker view, int hourOfDay, int minute, int seconds) {
                        selectTime.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute) + ":" + String.format("%02d", seconds));
                    }
                }, hour, minute, second, true);
                mTimePicker.show();

                break;

            case R.id.submit:

                if (contactId != null) {
                    if (categoryId != null) {
                        if (modelNameId != null) {
                            if (variantId != null) {
                                if (colorId != null) {
                                    if (!textView.getText().toString().equalsIgnoreCase("")) {
                                        if (!selectTime.getText().toString().equalsIgnoreCase("")) {
                                            if (statusName != null) {
                                                if (purchaseName != null) {
                                                    if (enquiryId != null) {
                                                        if (stageId != null) {
                                                            if (sourceId != null) {
                                                                if (customerType != null) {
                                                                    if (branchId != null) {
                                                                        addQuotation();
                                                                    } else {
                                                                        Toast.makeText(getActivity(), "Select Branch", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                } else {
                                                                    Toast.makeText(getActivity(), "Select Customer Type", Toast.LENGTH_SHORT).show();
                                                                }
                                                            } else {
                                                                Toast.makeText(getActivity(), "Select Source", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(getActivity(), "Select Stage", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(getActivity(), "Select Enquiry type", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(getActivity(), "Select Purchase Type", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(getActivity(), "Select Status", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(getActivity(), "Select Enquiry Time", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), "Select Enquiry Date", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(getActivity(), "Select Color", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Select Variant", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Select Model Name", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Select Category", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Select Contact", Toast.LENGTH_SHORT).show();
                }

                break;
        }

    }

    private void addQuotation() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        pDialog.setTitleText("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();

        String dateTime = textView.getText().toString() + " " + selectTime.getText().toString();
        Log.e("dateTime", "" + dateTime);

        String description = textInputEditTexts.get(1).getText().toString();
        description = "\'" + description + "\'";
        Log.e("description", "" + description);

        String enquiryNumberWithPrefix = "\'" + textInputEditTexts.get(3).getText().toString() + "\'";
        Log.e("branchEnquiryName", "" + enquiryNumberWithPrefix);

        Call<String> call = Api.getClient().addQuotation(dateTime, contactId, categoryId, modelNameId, variantId, colorId, purchaseNameId, enquiryId, customerTypeId, branchId,
                sourceId, statusId, stageId, MainPage.userId, MainPage.userId, description, branchEnquiryNumber, enquiryNumberWithPrefix, textInputEditTexts.get(4).getText().toString(),
                textInputEditTexts.get(5).getText().toString(), textInputEditTexts.get(6).getText().toString(), textInputEditTexts.get(7).getText().toString(), textInputEditTexts.get(8).getText().toString(),
                textInputEditTexts.get(9).getText().toString(), textInputEditTexts.get(10).getText().toString(), textInputEditTexts.get(11).getText().toString(), textInputEditTexts.get(12).getText().toString(),
                textInputEditTexts.get(13).getText().toString(), textInputEditTexts.get(14).getText().toString(), textInputEditTexts.get(15).getText().toString(), textInputEditTexts.get(16).getText().toString(),
                textInputEditTexts.get(17).getText().toString(), textInputEditTexts.get(18).getText().toString(), financeTypeId, financierId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    pDialog.dismiss();

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                    dialog.setContentView(R.layout.quotation_confirmation_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setCancelable(false);
                    TextView txtYes = dialog.findViewById(R.id.yes);

                    txtYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            try {
                                ((MainPage) getActivity()).removeCurrentFragmentAndMoveBack();
                                ((MainPage) getActivity()).loadFragment(new QuotationList(), true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    dialog.show();

                } else {
                    pDialog.dismiss();
                    Toast.makeText(getActivity(), "" + response.body(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                pDialog.dismiss();
                Log.e("enquiryError", "" + t.getMessage());
            }
        });

    }

    private void getBranchList(String branchId) {

        Call<List<BranchResponse>> call;
        if (Integer.parseInt(MainPage.userId) == 1) {
            call = Api.getClient().getAdminBranchList();
        } else {
            if (Integer.parseInt(MainPage.userHeadOffice) == 1) {
                call = Api.getClient().getAdminBranchList();
            } else if (Integer.parseInt(MainPage.userBranchOffice) == 1) {
                call = Api.getClient().getUserBranchList("" + MainPage.userId);
            } else {
                call = Api.getClient().getBranchList("" + branchId);
            }
        }

        call.enqueue(new Callback<List<BranchResponse>>() {
            @Override
            public void onResponse(Call<List<BranchResponse>> call, Response<List<BranchResponse>> response) {

                if (response.isSuccessful()) {

                    branchResponseList = response.body();
                    if (branchResponseList.size() > 0) {

                        branchIdList = new String[branchResponseList.size()];
                        branchNameList = new String[branchResponseList.size()];

                        for (int i = 0; i < branchResponseList.size(); i++) {
                            branchIdList[i] = branchResponseList.get(i).getId();
                            branchNameList[i] = branchResponseList.get(i).getBranch();
                        }

                        try {
                            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, branchNameList);
                            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            materialSpinners.get(11).setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                } else {

                }

            }

            @Override
            public void onFailure(Call<List<BranchResponse>> call, Throwable t) {
                Log.e("branchError", "" + t.getMessage());
            }
        });

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
                            materialSpinners.get(4).setAdapter(adapter);
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
                            materialSpinners.get(3).setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

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
                            materialSpinners.get(2).setAdapter(adapter);
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
            getUserBranchId();
            getContact();
            getCategoryList();
            getEnquiryTypeList();
            getSourceList();
        } else {
            DetectConnection.noInternetConnection(getActivity());
        }
    }

    private void getUserBranchId() {

        Call<List<EnquiryMaxNumberResponse>> call = Api.getClient().getUserBranchId(MainPage.userNumber);
        call.enqueue(new Callback<List<EnquiryMaxNumberResponse>>() {
            @Override
            public void onResponse(Call<List<EnquiryMaxNumberResponse>> call, Response<List<EnquiryMaxNumberResponse>> response) {

                if (response.isSuccessful()) {
                    getQuotationPrefix("" + response.body().get(0).getBranchId());
                    getBranchList("" + response.body().get(0).getBranchId());
                } else {
                    Log.e("userBranchError", "" + response.body());
                }

            }

            @Override
            public void onFailure(Call<List<EnquiryMaxNumberResponse>> call, Throwable t) {
                Log.e("userBranchError", "" + t.getMessage());
            }
        });

    }

    private void getStageList() {

        Call<List<StageResponse>> call = Api.getClient().getStageList();
        call.enqueue(new Callback<List<StageResponse>>() {
            @Override
            public void onResponse(Call<List<StageResponse>> call, Response<List<StageResponse>> response) {

                if (response.isSuccessful()) {

                    stageResponseList = response.body();
                    if (stageResponseList.size() > 0) {

                        stageIdList = new String[stageResponseList.size()];
                        stageNameList = new String[stageResponseList.size()];

                        for (int i = 0; i < stageResponseList.size(); i++) {
                            stageIdList[i] = stageResponseList.get(i).getId();
                            stageNameList[i] = stageResponseList.get(i).getStage();
                        }

                        try {
                            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, stageNameList);
                            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            materialSpinners.get(8).setAdapter(adapter);
                            if (stageName != null) {
                                materialSpinners.get(8).setSelectedIndex(adapter.getPosition("" + stageName));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                } else {

                }

            }

            @Override
            public void onFailure(Call<List<StageResponse>> call, Throwable t) {
                Log.e("stageError", "" + t.getMessage());
            }
        });

    }

    private void getSourceList() {

        Call<List<SourceResponse>> call = Api.getClient().getSourceList();
        call.enqueue(new Callback<List<SourceResponse>>() {
            @Override
            public void onResponse(Call<List<SourceResponse>> call, Response<List<SourceResponse>> response) {

                if (response.isSuccessful()) {

                    sourceResponseList = response.body();
                    if (sourceResponseList.size() > 0) {
                        sourceIdList = new String[sourceResponseList.size()];
                        sourceNameList = new String[sourceResponseList.size()];

                        for (int i = 0; i < sourceResponseList.size(); i++) {
                            sourceIdList[i] = sourceResponseList.get(i).getId();
                            sourceNameList[i] = sourceResponseList.get(i).getSource();
                        }

                        try {
                            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, sourceNameList);
                            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            materialSpinners.get(9).setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                } else {

                }

            }

            @Override
            public void onFailure(Call<List<SourceResponse>> call, Throwable t) {
                Log.e("sourceError", "" + t.getMessage());
            }
        });

    }

    private void getEnquiryTypeList() {

        Call<List<EnquiryResponse>> call = Api.getClient().getEnquiryTypeList();
        call.enqueue(new Callback<List<EnquiryResponse>>() {
            @Override
            public void onResponse(Call<List<EnquiryResponse>> call, Response<List<EnquiryResponse>> response) {

                if (response.isSuccessful()) {

                    enquiryResponseList = response.body();
                    if (enquiryResponseList.size() > 0) {

                        enquiryIdList = new String[enquiryResponseList.size()];
                        enquiryNameList = new String[enquiryResponseList.size()];

                        for (int i = 0; i < enquiryResponseList.size(); i++) {
                            enquiryIdList[i] = enquiryResponseList.get(i).getId();
                            enquiryNameList[i] = enquiryResponseList.get(i).getEnquiryType();
                        }

                        try {
                            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, enquiryNameList);
                            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            materialSpinners.get(7).setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {

                    }


                } else {

                }

            }

            @Override
            public void onFailure(Call<List<EnquiryResponse>> call, Throwable t) {
                Log.e("EnquiryError", "" + t.getMessage());
            }
        });

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
                        materialSpinners.get(1).setAdapter(adapter);
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

    private void getContact() {

        Call<List<ContactResponse>> call;
        if (MainPage.userId.equalsIgnoreCase("1")) {
            call = Api.getClient().getAdminContactList();
        } else {
            call = Api.getClient().getContactList();
        }
        call.enqueue(new Callback<List<ContactResponse>>() {
            @Override
            public void onResponse(Call<List<ContactResponse>> call, Response<List<ContactResponse>> response) {

                if (response.isSuccessful()) {

                    contactResponseList = response.body();

                    if (contactResponseList.size() > 0) {

                        contactIdList = new String[contactResponseList.size()];
                        contactNameList = new String[contactResponseList.size()];
                        contactNumberList = new String[contactResponseList.size()];

                        for (int i = 0; i < contactResponseList.size(); i++) {
                            contactIdList[i] = contactResponseList.get(i).getId();
                            contactNameList[i] = contactResponseList.get(i).getName();
                            contactNumberList[i] = contactResponseList.get(i).getPhone();
                        }

                        try {
                            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, contactNameList);
                            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            searchContactSpinner.setAdapter(adapter);
                            if (contactId != null) {
                                int spinnerPosition = adapter.getPosition(contactId);
                                searchContactSpinner.setSelection(spinnerPosition);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                } else {
                    Toast.makeText(getActivity(), "No Contact Found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ContactResponse>> call, Throwable t) {
                Log.e("contactError", "" + t.getMessage());
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

}