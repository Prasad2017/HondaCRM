package com.hondacrm.Applications;

import android.app.Application;

import com.hondacrm.helper.AppSignatureHelper;


public class SmsVerificationApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        AppSignatureHelper appSignatureHelper = new AppSignatureHelper(this);
        appSignatureHelper.getAppSignatures();
    }

}
