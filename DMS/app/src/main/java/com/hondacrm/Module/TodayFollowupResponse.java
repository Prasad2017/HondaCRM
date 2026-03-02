package com.hondacrm.Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TodayFollowupResponse {

    @SerializedName("id")
    @Expose
    private String id;
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
    private String status;
    @SerializedName("Details")
    @Expose
    private String details;
    @SerializedName("Owner")
    @Expose
    private String owner;
    @SerializedName("EnquiryID")
    @Expose
    private String enquiryID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("phone")
    @Expose
    private String mobileNumber;
    @SerializedName("StatgeID")
    @Expose
    private String statgeID;
    @SerializedName("SourceID")
    @Expose
    private String sourceID;
    @SerializedName("NameID")
    @Expose
    private String nameID;
    @SerializedName("stage")
    @Expose
    private String stage;
    @SerializedName("EnquiryN")
    @Expose
    private String enquiryN;
    @SerializedName("EnquiryNo")
    @Expose
    private String enquiryNo;
    @SerializedName("ModelNameID")
    @Expose
    private String modelNameID;
    @SerializedName("ModelCatID")
    @Expose
    private String modelCatID;
    @SerializedName("ModelVarientID")
    @Expose
    private String modelVarientID;
    @SerializedName("BranchId")
    @Expose
    private String branchId;
    @SerializedName("ModelName")
    @Expose
    private String modelName;
    @SerializedName("Modelvarient")
    @Expose
    private String modelvarient;
    @SerializedName("ModelCategory")
    @Expose
    private String modelCategory;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("Assignedid")
    @Expose
    private String assignedid;
    @SerializedName("QuotationN")
    @Expose
    private String QuotationN;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatgeID() {
        return statgeID;
    }

    public void setStatgeID(String statgeID) {
        this.statgeID = statgeID;
    }

    public String getSourceID() {
        return sourceID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public String getNameID() {
        return nameID;
    }

    public void setNameID(String nameID) {
        this.nameID = nameID;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getEnquiryN() {
        return enquiryN;
    }

    public void setEnquiryN(String enquiryN) {
        this.enquiryN = enquiryN;
    }

    public String getEnquiryNo() {
        return enquiryNo;
    }

    public void setEnquiryNo(String enquiryNo) {
        this.enquiryNo = enquiryNo;
    }

    public String getModelNameID() {
        return modelNameID;
    }

    public void setModelNameID(String modelNameID) {
        this.modelNameID = modelNameID;
    }

    public String getModelCatID() {
        return modelCatID;
    }

    public void setModelCatID(String modelCatID) {
        this.modelCatID = modelCatID;
    }

    public String getModelVarientID() {
        return modelVarientID;
    }

    public void setModelVarientID(String modelVarientID) {
        this.modelVarientID = modelVarientID;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelvarient() {
        return modelvarient;
    }

    public void setModelvarient(String modelvarient) {
        this.modelvarient = modelvarient;
    }

    public String getModelCategory() {
        return modelCategory;
    }

    public void setModelCategory(String modelCategory) {
        this.modelCategory = modelCategory;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAssignedid() {
        return assignedid;
    }

    public void setAssignedid(String assignedid) {
        this.assignedid = assignedid;
    }

    public String getQuotationID() {
        return QuotationID;
    }

    public void setQuotationID(String quotationID) {
        QuotationID = quotationID;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getQuotationN() {
        return QuotationN;
    }

    public void setQuotationN(String quotationN) {
        QuotationN = quotationN;
    }
}
