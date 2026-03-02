package com.hondacrm.Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TodayEnquiryResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("FollowUp")
    @Expose
    private String followUp;
    @SerializedName("Feedback")
    @Expose
    private String feedback;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("Details")
    @Expose
    private String details;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Owner")
    @Expose
    private String owner;
    @SerializedName("EnquiryID")
    @Expose
    private String enquiryID;
    @SerializedName("QuotationID")
    @Expose
    private String QuotationID;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFollowUp() {
        return followUp;
    }

    public void setFollowUp(String followUp) {
        this.followUp = followUp;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getEnquiryID() {
        return enquiryID;
    }

    public void setEnquiryID(String enquiryID) {
        this.enquiryID = enquiryID;
    }

    public String getQuotationID() {
        return QuotationID;
    }

    public void setQuotationID(String quotationID) {
        QuotationID = quotationID;
    }
}
