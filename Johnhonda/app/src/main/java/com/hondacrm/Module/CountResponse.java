package com.hondacrm.Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountResponse {

    //Enquiry
    @SerializedName("OpenEnquiry")
    @Expose
    private String openEnquiry;
    @SerializedName("WonEnquiry")
    @Expose
    private String WonEnquiry;
    @SerializedName("LostEnquiry")
    @Expose
    private String LostEnquiry;
    @SerializedName("CloseEnquiry")
    @Expose
    private String closeEnquiry;

    //Quotation
    @SerializedName("OpenQuotation")
    @Expose
    private String OpenQuotation;
    @SerializedName("WonQuotation")
    @Expose
    private String WonQuotation;
    @SerializedName("LostQuotation")
    @Expose
    private String LostQuotation;
    @SerializedName("CloseQuotation")
    @Expose
    private String CloseQuotation;


    //Activity
    @SerializedName("PendingEnquiryFollowups")
    @Expose
    private String PendingEnquiryFollowups;
    @SerializedName("ClosedEnquiryFollowups")
    @Expose
    private String ClosedEnquiryFollowups;
    @SerializedName("TotalEnquiryFollowups")
    @Expose
    private String TotalEnquiryFollowups;


    public String getOpenEnquiry() {
        return openEnquiry;
    }

    public void setOpenEnquiry(String openEnquiry) {
        this.openEnquiry = openEnquiry;
    }

    public String getOpenQuotation() {
        return OpenQuotation;
    }

    public void setOpenQuotation(String openQuotation) {
        OpenQuotation = openQuotation;
    }

    public String getLostEnquiry() {
        return LostEnquiry;
    }

    public void setLostEnquiry(String lostEnquiry) {
        LostEnquiry = lostEnquiry;
    }

    public String getWonQuotation() {
        return WonQuotation;
    }

    public void setWonQuotation(String wonQuotation) {
        WonQuotation = wonQuotation;
    }

    public String getWonEnquiry() {
        return WonEnquiry;
    }

    public void setWonEnquiry(String wonEnquiry) {
        WonEnquiry = wonEnquiry;
    }

    public String getCloseEnquiry() {
        return closeEnquiry;
    }

    public void setCloseEnquiry(String closeEnquiry) {
        this.closeEnquiry = closeEnquiry;
    }

    public String getLostQuotation() {
        return LostQuotation;
    }

    public void setLostQuotation(String lostQuotation) {
        LostQuotation = lostQuotation;
    }

    public String getCloseQuotation() {
        return CloseQuotation;
    }

    public void setCloseQuotation(String closeQuotation) {
        CloseQuotation = closeQuotation;
    }


    public String getPendingEnquiryFollowups() {
        return PendingEnquiryFollowups;
    }

    public void setPendingEnquiryFollowups(String pendingEnquiryFollowups) {
        PendingEnquiryFollowups = pendingEnquiryFollowups;
    }

    public String getClosedEnquiryFollowups() {
        return ClosedEnquiryFollowups;
    }

    public void setClosedEnquiryFollowups(String closedEnquiryFollowups) {
        ClosedEnquiryFollowups = closedEnquiryFollowups;
    }

    public String getTotalEnquiryFollowups() {
        return TotalEnquiryFollowups;
    }

    public void setTotalEnquiryFollowups(String totalEnquiryFollowups) {
        TotalEnquiryFollowups = totalEnquiryFollowups;
    }
}
