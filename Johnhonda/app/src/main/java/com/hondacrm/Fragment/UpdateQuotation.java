package com.hondacrm.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.hondacrm.Module.QuotationResponse;
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


public class UpdateQuotation extends Fragment {

    View view;
    @BindViews({R.id.contactSpinner, R.id.categorySpinner, R.id.nameSpinner, R.id.variantSpinner, R.id.colorSpinner, R.id.statusSpinner,
                R.id.purchaseTypeSpinner, R.id.enquiryTypeSpinner, R.id.stageSpinner, R.id.sourceSpinner, R.id.customerTypeSpinner, R.id.branchSpinner,
                R.id.financeTypeSpinner, R.id.financierSpinner})
    List<Spinner> materialSpinners;
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
    List<QuotationResponse> quotationResponseList = new ArrayList<>();
    List<FinanceResponse> financeResponseList = new ArrayList<>();
    //String of array List
    String[] contactIdList, contactNameList, contactNumberList, categoryIdList, categoryNameList, modelNameIdList, modelNameList, variantIdList, variantNameList, colorIdList,
            colorNameList, enquiryIdList, enquiryNameList, statusNameList, purchaseNameList, sourceIdList, sourceNameList, stageIdList, stageNameList, customerTypeList,
            branchIdList, branchNameList, financeTypeList, financierIdList, financierNameList;
    String contactId, contactName, contactNumber, categoryId, categoryName, modelNameId, modelName, variantId, variantName, colorId, colorName, enquiryId, enquiryName, statusId, statusName = "Open", purchaseNameId, purchaseName,
            sourceId, sourceName, stageId = "2", stageName = "Quotation", customerTypeId, customerType, branchId, branchName, financeTypeId="0", financeTypeName, financierId="0", financierName;
    String branchEnquiryNumber, branchEnquiryName, quotationId;
    //Select Date
    Calendar calender;
    DatePickerDialog datePickerDialog;
    private int mYear, mMonth, mDay;
    private int hour, minute, second;
    @BindView(R.id.contactPerson)
    TextView contactPerson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_update_quotation, container, false);
        ButterKnife.bind(this, view);
        MainPage.title.setText("Update");

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
                quotationId = bundle.getString("quotationId");
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

       /* MainPage.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCloseDialog();
            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("tag", "onKey Back listener is working!!!");
                    getCloseDialog();
                    return true;
                }
                return false;
            }
        });*/

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


        materialSpinners.get(0).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {

                    categoryId = categoryIdList[position];
                    Log.e("categoryId", ""+categoryId);
                    categoryName = categoryNameList[position];
                    getModelNameList(categoryId);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        materialSpinners.get(2).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {

                    modelNameId = modelNameIdList[position];
                    modelName = modelNameList[position];
                    getModelVariantList(modelNameId);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        materialSpinners.get(3).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {

                    variantId = variantIdList[position];
                    variantName = variantNameList[position];
                    Log.e("variantId", ""+variantId);

                    getModelColorList(variantId);
                    getModelPriceList(variantId);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        materialSpinners.get(4).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {

                    colorId = colorIdList[position];
                    colorName = colorNameList[position];

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        materialSpinners.get(5).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        materialSpinners.get(6).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        materialSpinners.get(7).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {

                    enquiryId = enquiryIdList[position];
                    enquiryName = enquiryNameList[position];

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        materialSpinners.get(8).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {

                    stageName = stageNameList[position];
                    stageId = "2";

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        materialSpinners.get(9).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        materialSpinners.get(10).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        materialSpinners.get(11).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {

                    branchId = branchIdList[position];
                    branchName = branchNameList[position];

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        financeTypeList = getActivity().getResources().getStringArray(R.array.finance_type);
        try {
            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, financeTypeList);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            materialSpinners.get(12).setAdapter(adapter);
            if (financeTypeName != null) {
                materialSpinners.get(12).setSelection(adapter.getPosition("" + financeTypeName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        materialSpinners.get(12).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        materialSpinners.get(13).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    financierId = financierIdList[position];
                    financierName = financierNameList[position];
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                                materialSpinners.get(13).setSelection(adapter.getPosition("" + financierName));
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
                    if (response.body() != null) {
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

            }

            @Override
            public void onFailure(Call<List<ModelPriceResponse>> call, Throwable t) {
                Log.e("priceError", "" + t.getMessage());
            }
        });

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
                                                                        updateQuotation();
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

    private void updateQuotation() {

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

        Call<String> call = Api.getClient().updateQuotation(quotationId, dateTime, contactId, categoryId, modelNameId, variantId, colorId, purchaseNameId, enquiryId, customerTypeId, branchId,
                sourceId, statusId, stageId, MainPage.userId, MainPage.userId, description, branchEnquiryNumber, enquiryNumberWithPrefix, textInputEditTexts.get(4).getText().toString(),
                textInputEditTexts.get(5).getText().toString(), textInputEditTexts.get(6).getText().toString(), textInputEditTexts.get(7).getText().toString(), textInputEditTexts.get(8).getText().toString(),
                textInputEditTexts.get(9).getText().toString(), textInputEditTexts.get(10).getText().toString(), textInputEditTexts.get(11).getText().toString(), textInputEditTexts.get(12).getText().toString(),
                textInputEditTexts.get(13).getText().toString(), textInputEditTexts.get(14).getText().toString(), textInputEditTexts.get(15).getText().toString(), textInputEditTexts.get(16).getText().toString(),
                textInputEditTexts.get(17).getText().toString(), textInputEditTexts.get(18).getText().toString(), "", "2", "1", financierId, financeTypeId);
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
                    TextView updateQuotation = dialog.findViewById(R.id.updateQuotation);
                    TextView updateQuotationDetail = dialog.findViewById(R.id.updateQuotationDetail);

                    updateQuotation.setText("Quotation Updated");
                    updateQuotationDetail.setText("Quotation has been updated successfully");

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
                            if (branchName != null) {
                                materialSpinners.get(11).setSelection(adapter.getPosition("" + branchName));
                            }
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
                            if (colorName != null) {
                                materialSpinners.get(4).setSelection(adapter.getPosition("" + colorName));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

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
                            if (variantName != null) {
                                materialSpinners.get(3).setSelection(adapter.getPosition("" + variantName));
                                getModelColorList(variantId);
                                getModelPriceList(variantId);
                            }
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
                            if (modelName != null) {
                                materialSpinners.get(2).setSelection(adapter.getPosition("" + modelName));
                                getModelVariantList(modelNameId);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

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
            getQuotationDetails();
        } else {
            DetectConnection.noInternetConnection(getActivity());
        }
    }

    private void getQuotationDetails() {

        Call<List<QuotationResponse>> call = Api.getClient().getQuotationDetails("" + quotationId);
        call.enqueue(new Callback<List<QuotationResponse>>() {
            @Override
            public void onResponse(Call<List<QuotationResponse>> call, Response<List<QuotationResponse>> response) {

                if (response.isSuccessful()) {
                    quotationResponseList = response.body();
                    if (quotationResponseList.size() > 0) {

                        sourceId = response.body().get(0).getSourceID();
                        sourceName = response.body().get(0).getSource();
                        enquiryName = response.body().get(0).getEnqType();
                        customerTypeId = ""+response.body().get(0).getCustomerType();
                        categoryId = response.body().get(0).getModelCatID();
                        categoryName = response.body().get(0).getModelCategory();
                        modelNameId = response.body().get(0).getModelNameID();
                        modelName = response.body().get(0).getModelName();
                        variantId = response.body().get(0).getModelVarientID();
                        variantName = response.body().get(0).getModelvarient();
                        colorId = response.body().get(0).getModelColorID();
                        colorName = response.body().get(0).getModelColor();
                        statusId = response.body().get(0).getStatus();
                        stageId = response.body().get(0).getStatgeID();
                        contactId = response.body().get(0).getNameID();
                        contactName = response.body().get(0).getName();
                        contactPerson.setText("" + contactName);
                        purchaseNameId = ""+response.body().get(0).getPurchaseType();
                        financeTypeId = response.body().get(0).getFinanceID();
                        financierId = response.body().get(0).getFinanceID();
                        financierName = response.body().get(0).getFinanceName();
                        //Status
                        if (Integer.parseInt(response.body().get(0).getStatus()) == 1) {
                            statusName = "Open";
                        } else {
                            statusName = "Close";
                        }

                        //Purchase Name
                        if (response.body().get(0).getPurchaseType() == 1) {
                            purchaseName = "Cash";
                        } else if (response.body().get(0).getPurchaseType() == 2) {
                            purchaseName = "Finance";
                        } else if (response.body().get(0).getPurchaseType() == 3) {
                            purchaseName = "Best Deal";
                        }

                        //Customer Type
                        if (response.body().get(0).getCustomerType() == 1) {
                            customerType = "Hot";
                        } else if (response.body().get(0).getCustomerType() == 2) {
                            customerType = "Warm";
                        } else if (response.body().get(0).getCustomerType() == 3) {
                            customerType = "Cold";
                        }
                        customerTypeList = getActivity().getResources().getStringArray(R.array.customer_type);
                        try {
                            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, customerTypeList);
                            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            materialSpinners.get(10).setAdapter(adapter);
                            if (customerType!=null) {
                                materialSpinners.get(10).setSelection(adapter.getPosition(""+customerType));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //Status Name
                        statusNameList = getActivity().getResources().getStringArray(R.array.status);
                        try {
                            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, statusNameList);
                            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            materialSpinners.get(5).setAdapter(adapter);
                            if (statusName != null) {
                                materialSpinners.get(8).setSelection(adapter.getPosition("" + statusName));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //Purpose Name
                        purchaseNameList = getActivity().getResources().getStringArray(R.array.purchase_type);
                        try {
                            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, purchaseNameList);
                            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            materialSpinners.get(6).setAdapter(adapter);
                            if (purchaseName!=null) {
                                materialSpinners.get(6).setSelection(adapter.getPosition(""+purchaseName));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //Stage Name
                        stageNameList = getActivity().getResources().getStringArray(R.array.stage_quotation);
                        try {
                            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, stageNameList);
                            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            materialSpinners.get(8).setAdapter(adapter);
                            if (stageName != null) {
                                materialSpinners.get(8).setSelection(adapter.getPosition("" + stageName));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //Finance Type
                        if (Integer.parseInt(response.body().get(0).getFinanceType()) == 1) {
                            financeTypeName= "In-House";
                        } else if (Integer.parseInt(response.body().get(0).getFinanceType()) == 2) {
                            financeTypeName= "Out-House";
                        } else {
                            financeTypeName= "In-House";
                        }

                        financeTypeList = getActivity().getResources().getStringArray(R.array.finance_type);
                        try {
                            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, financeTypeList);
                            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            materialSpinners.get(12).setAdapter(adapter);
                            if (financeTypeName != null) {
                                materialSpinners.get(12).setSelection(adapter.getPosition("" + financeTypeName));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        textInputEditTexts.get(0).setText("" + response.body().get(0).getPhone());
                        textInputEditTexts.get(3).setText("" + response.body().get(0).getQuotationN());
                        textView.setText("" + response.body().get(0).getDate().substring(0, 10));
                        selectTime.setText("" + response.body().get(0).getDate().substring(12, 19));

                        textInputEditTexts.get(1).setText(""+response.body().get(0).getAdditionalInfo());

                        getUserBranchId();
                        getContact();
                        getCategoryList();
                        getEnquiryTypeList();
                        getSourceList();
                        getFinancierList(financeTypeId);

                    } else {

                    }
                } else {

                }

            }

            @Override
            public void onFailure(Call<List<QuotationResponse>> call, Throwable t) {
                Log.e("quotationDetailsError", "" + t.getMessage());
            }
        });


    }

    private void getUserBranchId() {

        Call<List<EnquiryMaxNumberResponse>> call = Api.getClient().getUserBranchId(MainPage.userNumber);
        call.enqueue(new Callback<List<EnquiryMaxNumberResponse>>() {
            @Override
            public void onResponse(Call<List<EnquiryMaxNumberResponse>> call, Response<List<EnquiryMaxNumberResponse>> response) {

                if (response.isSuccessful()) {
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
                                materialSpinners.get(8).setSelection(adapter.getPosition("" + stageName));
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
                            if (sourceName != null) {
                                materialSpinners.get(9).setSelection(adapter.getPosition("" + sourceName));
                            }
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
                            if (enquiryName!=null) {
                                materialSpinners.get(7).setSelection(adapter.getPosition(""+enquiryName));
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
                        if (categoryName!=null) {
                            materialSpinners.get(1).setSelection(adapter.getPosition(""+categoryName));
                            getModelNameList(categoryId);
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

    //private method of your class
    public int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;
    }

}