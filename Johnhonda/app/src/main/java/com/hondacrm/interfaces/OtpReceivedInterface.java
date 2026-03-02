package com.hondacrm.interfaces;


public interface OtpReceivedInterface {
    void onOtpReceived(String otp);

    void onOtpTimeout();
}
