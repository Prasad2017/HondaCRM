package com.hondacrm.Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FollowupResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("FollowUp")
    @Expose
    private String FollowUp;
    @SerializedName("FollowUpid")
    @Expose
    private String FollowUpid;
    @SerializedName("Feedbackid")
    @Expose
    private String Feedbackid;
    @SerializedName("FollowUpId")
    @Expose
    private String FollowUpId;
    @SerializedName("FeedbackId")
    @Expose
    private String FeedbackId;
    @SerializedName("Feedback")
    @Expose
    private String feedback;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Status")
    @Expose
    private String status;
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFollowUp() {
        return FollowUp;
    }

    public void setFollowUp(String followUp) {
        FollowUp = followUp;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getFollowUpid() {
        return FollowUpid;
    }

    public void setFollowUpid(String followUpid) {
        FollowUpid = followUpid;
    }

    public String getFeedbackid() {
        return Feedbackid;
    }

    public void setFeedbackid(String feedbackid) {
        Feedbackid = feedbackid;
    }

    public String getFollowUpId() {
        return FollowUpId;
    }

    public void setFollowUpId(String followUpId) {
        FollowUpId = followUpId;
    }

    public String getFeedbackId() {
        return FeedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        FeedbackId = feedbackId;
    }
}
