package com.hondacrm.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.hondacrm.Extra.DetectConnection;
import com.hondacrm.R;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class LoginEmail extends AppCompatActivity {

    @BindViews({R.id.email, R.id.password})
    List<TextInputEditText> textInputEditTexts;
    @BindViews({R.id.iv_passShow})
    List<ImageView> imageViews;
    @BindView(R.id.appVersion)
    TextView appVersion;
    SharedPreferences.Editor editor;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        ButterKnife.bind(this);

        File file = new File("data/data/"+getPackageName()+"/shared_prefs/user.xml");
        if (file.exists()) {
            Intent intent = new Intent(LoginEmail.this, MainPage.class);
            startActivity(intent);
            finish();
        }

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            appVersion.setText("v" + version + " Live");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        textInputEditTexts.get(1).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {

                    if (textInputEditTexts.get(1).getText().toString().length() > 0) {
                        imageViews.get(0).setVisibility(View.VISIBLE);
                    } else {
                        imageViews.get(0).setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @OnClick({R.id.login_button_card_view, R.id.iv_passShow})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.login_button_card_view:
                if (DetectConnection.checkInternetConnection(LoginEmail.this)) {
                    validateLogin();
                } else {
                    Toasty.warning(LoginEmail.this, "No Internet Connection", Toasty.LENGTH_SHORT, true).show();
                }

                break;

            case R.id.iv_passShow:

                if (textInputEditTexts.get(1).getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    imageViews.get(0).setImageResource(R.drawable.ic_hide);
                    textInputEditTexts.get(1).setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    imageViews.get(0).setImageResource(R.drawable.ic_look);
                    textInputEditTexts.get(1).setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                textInputEditTexts.get(1).setSelection(textInputEditTexts.get(1).getText().toString().length());

                break;

        }

    }

    private void validateLogin() {

        if (textInputEditTexts.get(0).getText().toString().equals("")) {
            textInputEditTexts.get(0).setError("Please Enter mobile number");
        } else if (textInputEditTexts.get(1).getText().toString().equals("")) {
            textInputEditTexts.get(1).setError("Please enter password");
        } else {
            loginUser(textInputEditTexts.get(0).getText().toString(), textInputEditTexts.get(1).getText().toString());
        }

    }

    private void loginUser(String userId, String password) {

        ProgressDialog progressDialog = new ProgressDialog(LoginEmail.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Account is in verification");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);


    }

}