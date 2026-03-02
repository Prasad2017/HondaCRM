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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.hondacrm.Activity.MainPage;
import com.hondacrm.Extra.DetectConnection;
import com.hondacrm.Module.FeedbackResponse;
import com.hondacrm.Module.FollowupResponse;
import com.hondacrm.R;
import com.hondacrm.Retrofit.Api;
import com.ikovac.timepickerwithseconds.MyTimePickerDialog;

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


public class AddQuotationFollowUp extends Fragment {

    View view;
    @BindViews({R.id.followupSpinner, R.id.feedbackSpinner, R.id.statusSpinner})
    List<Spinner> materialSpinners;
    @BindView(R.id.description)
    TextInputEditText textInputEditTexts;
    @BindView(R.id.selectDate)
    TextView textView;
    @BindView(R.id.selectTime)
    TextView selectTime;
    List<FollowupResponse> followupResponseList = new ArrayList<>();
    List<FeedbackResponse> feedbackResponseList = new ArrayList<>();
    String[] followUpIdList, followUpNameList, feedBackIdList, feedBackNameList, statusNameList;
    String quotationId, followUpId, followUpName, feedBackId, feedBackName, statusId, statusName;
    //Select Date
    Calendar calender;
    DatePickerDialog datePickerDialog;
    private int mYear, mMonth, mDay;
    private int hour, minute, second;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_quotation_follow_up, container, false);
        ButterKnife.bind(this, view);
        setRetainInstance(true);


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
            quotationId = bundle.getString("quotationId");

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        MainPage.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    ((MainPage) getActivity()).removeCurrentFragmentAndMoveBack();
                    QuotationFollowUpList quotationFollowUpList = new QuotationFollowUpList();
                    Bundle bundle = new Bundle();
                    bundle.putString("quotationId", "" + quotationId);
                    quotationFollowUpList.setArguments(bundle);
                    ((MainPage) getActivity()).loadFragment(quotationFollowUpList, true);
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
                        QuotationFollowUpList quotationFollowUpList = new QuotationFollowUpList();
                        Bundle bundle = new Bundle();
                        bundle.putString("quotationId", "" + quotationId);
                        quotationFollowUpList.setArguments(bundle);
                        ((MainPage) getActivity()).loadFragment(quotationFollowUpList, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });

        materialSpinners.get(0).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {

                    followUpId = followUpIdList[position];
                    followUpName = followUpNameList[position];

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        materialSpinners.get(1).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {

                    feedBackId = feedBackIdList[position];
                    feedBackName = feedBackNameList[position];

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        statusNameList = getActivity().getResources().getStringArray(R.array.status);
        try {
            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, statusNameList);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            materialSpinners.get(2).setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        materialSpinners.get(2).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        return view;

    }

    @OnClick({R.id.selectDate, R.id.selectTime, R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
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

                if (followUpId != null) {
                    if (feedBackId != null) {
                        if (statusName != null) {
                            if (!textView.getText().toString().equalsIgnoreCase("")) {
                                if (!selectTime.getText().toString().equalsIgnoreCase("")) {
                                    addQuotationFollowUP();
                                } else {
                                    Toast.makeText(getActivity(), "Select Follow-Up Time", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Select Follow-Up Date", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Select Status", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Select Feedback", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Select Follow Up", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }

    private void addQuotationFollowUP() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        pDialog.setTitleText("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();

        String dateTime = textView.getText().toString() + " " + selectTime.getText().toString();
        Log.e("dateTime", "" + dateTime);

        Call<String> call = Api.getClient().addQuotationFollowUP(MainPage.userId, quotationId, followUpId, feedBackId, statusId, dateTime, textInputEditTexts.getText().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    pDialog.dismiss();

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                    dialog.setContentView(R.layout.confirmation_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setCancelable(false);

                    TextView created = dialog.findViewById(R.id.created);
                    TextView createdDone = dialog.findViewById(R.id.createdDone);
                    TextView txtYes = dialog.findViewById(R.id.yes);

                    created.setText("Followup Created");
                    createdDone.setText("Followup has been created successfully");

                    txtYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            try {
                                ((MainPage) getActivity()).removeCurrentFragmentAndMoveBack();
                                QuotationFollowUpList quotationFollowUpList = new QuotationFollowUpList();
                                Bundle bundle = new Bundle();
                                bundle.putString("quotationId", "" + quotationId);
                                quotationFollowUpList.setArguments(bundle);
                                ((MainPage) getActivity()).loadFragment(quotationFollowUpList, true);
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
                Log.e("followUpError", "" + t.getMessage());
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
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

    public void onStart() {
        super.onStart();
        Log.e("onStart", "called");
        ((MainPage) getActivity()).lockUnlockDrawer(1);
        if (DetectConnection.checkInternetConnection(getActivity())) {
            getLastQuotationFollowup();
        } else {
            Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG).show();
        }
    }

    private void getLastQuotationFollowup() {

        Call<List<FollowupResponse>> call = Api.getClient().getLastQuotationFollowup(quotationId);
        call.enqueue(new Callback<List<FollowupResponse>>() {
            @Override
            public void onResponse(Call<List<FollowupResponse>> call, Response<List<FollowupResponse>> response) {

                if (response.isSuccessful()) {

                    followupResponseList = response.body();
                    if (followupResponseList.size() > 0) {

                        followUpId = followupResponseList.get(0).getFollowUpId();
                        followUpName = followupResponseList.get(0).getFollowUp();

                        getFollowup();
                        getFeedback();

                    } else {

                    }

                }

            }

            @Override
            public void onFailure(Call<List<FollowupResponse>> call, Throwable t) {
                Log.e("followupBack", "" + t.getMessage());
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getFollowup() {

        Call<List<FollowupResponse>> call = Api.getClient().getFollowupList();
        call.enqueue(new Callback<List<FollowupResponse>>() {
            @Override
            public void onResponse(Call<List<FollowupResponse>> call, Response<List<FollowupResponse>> response) {

                if (response.isSuccessful()) {

                    followupResponseList = response.body();
                    if (followupResponseList.size() > 0) {

                        followUpIdList = new String[followupResponseList.size()];
                        followUpNameList = new String[followupResponseList.size()];

                        for (int i = 0; i < followupResponseList.size(); i++) {
                            followUpIdList[i] = followupResponseList.get(i).getId();
                            followUpNameList[i] = followupResponseList.get(i).getFollowUp();
                        }

                        try {
                            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, followUpNameList);
                            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            materialSpinners.get(0).setAdapter(adapter);
                            if (followUpName != null) {
                                materialSpinners.get(0).setSelection(adapter.getPosition("" + followUpName));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        Toast.makeText(getActivity(), "No FollowUp Found", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<List<FollowupResponse>> call, Throwable t) {
                Log.e("followupBack", "" + t.getMessage());
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getFeedback() {

        Call<List<FeedbackResponse>> call = Api.getClient().getFeedbackList();
        call.enqueue(new Callback<List<FeedbackResponse>>() {
            @Override
            public void onResponse(Call<List<FeedbackResponse>> call, Response<List<FeedbackResponse>> response) {

                if (response.isSuccessful()) {

                    feedbackResponseList = response.body();
                    if (feedbackResponseList.size() > 0) {

                        feedBackIdList = new String[feedbackResponseList.size()];
                        feedBackNameList = new String[feedbackResponseList.size()];

                        for (int i = 0; i < feedbackResponseList.size(); i++) {
                            feedBackIdList[i] = feedbackResponseList.get(i).getId();
                            feedBackNameList[i] = feedbackResponseList.get(i).getFeedback();
                        }

                        try {
                            final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, feedBackNameList);
                            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            materialSpinners.get(1).setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        Toast.makeText(getActivity(), "No Feedback Found", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<List<FeedbackResponse>> call, Throwable t) {
                Log.e("feedBack", "" + t.getMessage());
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}