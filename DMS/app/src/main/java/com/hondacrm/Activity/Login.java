package com.hondacrm.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.andreabaccega.widget.FormEditText;
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.material.textfield.TextInputEditText;
import com.hondacrm.Module.LoginResponse;
import com.hondacrm.R;
import com.hondacrm.Retrofit.Api;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    @BindView(R.id.mobileNumber)
    TextInputEditText textInputEditText;
    @BindView(R.id.loginLinearLayout)
    RelativeLayout loginLinearLayout;
    private RelativeLayout login_button;
    private CardView login_button_card_view;


    public ActivityResultLauncher<IntentSenderRequest> phoneNumberHintIntentResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartIntentSenderForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    try {
                        String phoneNumber = Identity.getSignInClient(getApplicationContext()).getPhoneNumberFromIntent(result.getData());
                        textInputEditText.setText(getMy10DigitPhoneNumber(phoneNumber));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        login_button = findViewById(R.id.login_button);
        login_button_card_view = findViewById(R.id.login_button_card_view);
        inputChange();
        textInputEditText.setSelection(textInputEditText.getText().toString().length());

        loginLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
            }
        });

        File file = new File("data/data/"+getPackageName()+"/shared_prefs/user.xml");
        if (file.exists()) {
            Intent intent = new Intent(Login.this, MainPage.class);
            startActivity(intent);
            finish();
        }

        getHintPhoneNumber();

    }

    public void getHintPhoneNumber() {

        GetPhoneNumberHintIntentRequest request = GetPhoneNumberHintIntentRequest.builder().build();

        Identity.getSignInClient(this)
                .getPhoneNumberHintIntent(request)
                .addOnSuccessListener(result -> {
                    try {
                        IntentSender intentSender = result.getIntentSender();
                        phoneNumberHintIntentResultLauncher.launch(new IntentSenderRequest.Builder(intentSender).build());
                    } catch (Exception e) {
                        Log.i("Error launching", "error occurred in launching Activity result");
                    }
                })
                .addOnFailureListener(e -> Log.i("Failure occurred", "Failure getting phone number"));

    }

    private String getMy10DigitPhoneNumber(String phoneNumber) {

        if (phoneNumber.length() == 13) {
            return phoneNumber.substring(3);
        } else if (phoneNumber.length() == 12) {
            return phoneNumber.substring(2);
        } else if (phoneNumber.length() == 11) {
            return phoneNumber.substring(1);
        } else {
            return phoneNumber;
        }

    }

    protected void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @OnClick({R.id.login_button_card_view, R.id.login_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button_card_view:
            case R.id.login_button:
                    if (textInputEditText.getText().toString().length() == 10) {

                        /*Intent intent = new Intent(Login.this, OtpVerify.class);
                        intent.putExtra("mobileNumber", textInputEditText.getText().toString().trim());
                        startActivity(intent);
                        finishAffinity();*/

                        checkMobileNumber(textInputEditText.getText().toString().trim());

                    } else {

                        textInputEditText.requestFocus();
                        textInputEditText.setError("Enter valid mobile number");

                    }

                break;
        }
    }

    private void checkMobileNumber(String mobileNumber) {

        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<List<LoginResponse>> call = Api.getClient().checkMobileNumber(mobileNumber);
        call.enqueue(new Callback<List<LoginResponse>>() {
            @Override
            public void onResponse(Call<List<LoginResponse>> call, Response<List<LoginResponse>> response) {

                if (response.isSuccessful()) {
                    pDialog.dismiss();

                    if (response.body() != null) {
                        try {
                            Intent intent = new Intent(Login.this, OtpVerify.class);
                            intent.putExtra("mobileNumber", "" + mobileNumber);
                            intent.putExtra("userId", "" + response.body().get(0).getId());
                            intent.putExtra("userName", "" + response.body().get(0).getName());
                            intent.putExtra("userMobile", "" + response.body().get(0).getMobileNo());
                            intent.putExtra("userBranchId", "" + response.body().get(0).getBranchId());
                            intent.putExtra("userBranch", "" + response.body().get(0).getBranch());
                            intent.putExtra("userBranchOffice", "" + response.body().get(0).getBranchOffice());
                            intent.putExtra("userHeadOffice", "" + response.body().get(0).getHeadoffice());
                            startActivity(intent);
                            finishAffinity();
                        } catch (Exception e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                            Toasty.error(Login.this, "Login Failed", Toasty.LENGTH_SHORT, true).show();
                        }
                    } else {
                        pDialog.dismiss();
                        Toasty.error(Login.this, "Login Failed", Toasty.LENGTH_SHORT, true).show();
                    }

                } else {
                    pDialog.dismiss();
                    Toasty.error(Login.this, "Login Failed", Toasty.LENGTH_SHORT, true).show();
                }

            }

            @Override
            public void onFailure(Call<List<LoginResponse>> call, Throwable t) {
                pDialog.dismiss();
                Log.e("mobileNumberError", "" + t.getMessage());
                Toasty.error(Login.this, "Server Error", Toasty.LENGTH_SHORT, true).show();
            }
        });

    }

    @SuppressLint("ResourceType")
    private void loginButtonStyle() {
        if (textInputEditText.getText().length() == 10) {
            if (!login_button.isFocusable()) {
                login_button.setFocusable(true);
                login_button.setClickable(true);
                login_button_card_view.setCardBackgroundColor(Color.parseColor(getString(R.color.colorPrimary)));
                TypedValue outValue = new TypedValue();
                getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
                login_button.setBackgroundResource(outValue.resourceId);
            }
        } else {
            if (login_button.isFocusable()) {
                login_button.setFocusable(false);
                login_button.setClickable(false);
                login_button_card_view.setCardBackgroundColor(Color.parseColor(getString(R.color.colorPrimary)));
                login_button.setBackgroundResource(0);
            }
        }
    }

    private void inputChange() {

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                loginButtonStyle();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                loginButtonStyle();
            }
        });


    }

}