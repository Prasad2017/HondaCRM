package com.hondacrm.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.hondacrm.Extra.Common;
import com.hondacrm.R;
import com.hondacrm.Retrofit.SMSApi;
import com.hondacrm.SMS.SmsBroadcastReceiver;
import com.loopj.android.http.AsyncHttpClient;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerify extends AppCompatActivity {

    private static final int REQ_USER_CONSENT = 200;
    SmsBroadcastReceiver smsBroadcastReceiver;
    public String HASH_KEY, OTP, otpCode, mobileNumber, userId, userName, userMobile, userBranchOffice, userHeadOffice;
    @BindView(R.id.otpView)
    OtpView otpView;
    @BindView(R.id.resendOTP)
    TextView resendOTP;
    GoogleApiClient mGoogleApiClient;
    Intent intent;
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    //Declare timer
    CountDownTimer cTimer = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        try {

            intent = getIntent();
            mobileNumber = intent.getStringExtra("mobileNumber");
            userId = intent.getStringExtra("userId");
            userName = intent.getStringExtra("userName");
            userMobile = intent.getStringExtra("userMobile");
            userBranchOffice = intent.getStringExtra("userBranchOffice");
            userHeadOffice = intent.getStringExtra("userHeadOffice");

            if (mobileNumber != null) {
                try {
                    sendOTP(mobileNumber);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                try {

                    otpCode = otp;
                    int length = otpCode.trim().length();
                    if (length == 6) {

                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        View view = getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @OnClick({R.id.changeNumber, R.id.resendOTP, R.id.login_button_card_view, R.id.login_button})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.changeNumber:

                intent = new Intent(OtpVerify.this, Login.class);
                startActivity(intent);
                finishAffinity();

                break;

            case R.id.resendOTP:
                if (resendOTP.getText().toString().equalsIgnoreCase("Resend OTP")) {
                    try {
                        sendOTP(mobileNumber);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.login_button_card_view:
            case R.id.login_button:

                if (otpView.getText().toString() != null) {
                    if (otpCode.equalsIgnoreCase(OTP)) {

                        SharedPreferences pref;
                        SharedPreferences.Editor editor;

                        pref = getSharedPreferences("user", Context.MODE_PRIVATE);
                        editor = pref.edit();
                        editor.putString("UserLogin", "UserLoginSuccessful");
                        editor.commit();

                        Common.saveUserData(OtpVerify.this, "userId", userId);
                        Common.saveUserData(OtpVerify.this, "userName", userName);
                        Common.saveUserData(OtpVerify.this, "userMobile", userMobile);
                        Common.saveUserData(OtpVerify.this, "userBranchOffice", userBranchOffice);
                        Common.saveUserData(OtpVerify.this, "userHeadOffice", userHeadOffice);

                        intent = new Intent(OtpVerify.this, MainPage.class);
                        startActivity(intent);
                        finishAffinity();

                    } else {
                        Toasty.error(OtpVerify.this, "Please enter valid OTP", Toasty.LENGTH_SHORT, true).show();
                        otpView.requestFocus();
                    }
                } else {
                    Toasty.warning(OtpVerify.this, "Please enter OTP", Toasty.LENGTH_SHORT, true).show();
                    otpView.requestFocus();
                }

                break;

        }
    }

    private void sendOTP(String mobileNumber) {

        OTP = new DecimalFormat("0000").format(new Random().nextInt(9999));
        Log.e("OTP", OTP);

        String message = "Your JOHNS HONDA DMS verification code is " + OTP + " Please DO NOT share this OTP with anyone.";

        ProgressDialog progressDialog = new ProgressDialog(OtpVerify.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("OTP is sending");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        Call<String> call = SMSApi.getClient().sendSMS("Johnsbiwheelers", "Johnstcr2020", "JOHNSB", mobileNumber, message, "T", "1701158056098886904", "1707169389849023879");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(OtpVerify.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                    toSetOTPTimer();
                    startSmsUserConsent();
                } else {
                    Toast.makeText(OtpVerify.this, response.body(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("error", t.getMessage());
                progressDialog.dismiss();
            }
        });

    }

    private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        //We can add sender phone number or leave it blank
        // I'm adding null here
        client.startSmsUserConsent(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Toast.makeText(getApplicationContext(), "On Success", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Toast.makeText(getApplicationContext(), "On OnFailure", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_USER_CONSENT) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                //That gives all message to us.
                // We need to get the code from inside with regex
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                getOtpFromMessage(message);
            }
        }
    }

    private void getOtpFromMessage(String message) {
        // This will match any 6 digit number in the message
        Pattern pattern = Pattern.compile("(|^)\\d{4}");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            otpView.setText(matcher.group(0));
        }
    }


    public void toSetOTPTimer() {

        new CountDownTimer(300000 + 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                String timer;
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                if (minutes > 0 && seconds > 0) {
                    timer = "RESEND NEW CODE IN <font color='#ff0000'>" + String.format("%02d", minutes)
                            + ":" + String.format("%02d", seconds) + "</font>";
                } else {
                    timer = "RESEND NEW CODE";
                }

                resendOTP.setText(Html.fromHtml(timer));
            }

            public void onFinish() {
                String timer = "RESEND NEW CODE";
                resendOTP.setText(Html.fromHtml(timer));
            }

        }.start();

    }

    private void registerBroadcastReceiver() {
        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener =
                new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, REQ_USER_CONSENT);
                    }

                    @Override
                    public void onFailure() {
                    }
                };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cancelTimer();
        unregisterReceiver(smsBroadcastReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancelTimer();
    }


    //cancel timer
    public void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }

}