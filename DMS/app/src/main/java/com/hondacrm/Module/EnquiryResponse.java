package com.hondacrm.Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EnquiryResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("EnquiryType")
    @Expose
    private String enquiryType;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("stage")
    @Expose
    private String stage;
    @SerializedName("ModelName")
    @Expose
    private String modelName;
    @SerializedName("Modelvarient")
    @Expose
    private String modelvarient;
    @SerializedName("ModelCategory")
    @Expose
    private String modelCategory;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("Owner")
    @Expose
    private String owner;
    @SerializedName("Assignedid")
    @Expose
    private String assignedid;
    @SerializedName("EnquiryN")
    @Expose
    private String enquiryN;
    @SerializedName("EnquiryNo")
    @Expose
    private String enquiryNo;

    @SerializedName("StatgeID")
    @Expose
    private String statgeID;
    @SerializedName("SourceID")
    @Expose
    private String sourceID;
    @SerializedName("NameID")
    @Expose
    private String nameID;
    @SerializedName("ModelNameID")
    @Expose
    private String modelNameID;
    @SerializedName("ModelCatID")
    @Expose
    private String modelCatID;
    @SerializedName("ModelVarientID")
    @Expose
    private String modelVarientID;
    @SerializedName("ModelColorID")
    @Expose
    private String ModelColorID;
    @SerializedName("ModelColor")
    @Expose
    private String ModelColor;
    @SerializedName("BranchId")
    @Expose
    private String branchId;

    @SerializedName("Type")
    @Expose
    private int Type;
    @SerializedName("Closure")
    @Expose
    private int Closure;
    @SerializedName("ClosingDate")
    @Expose
    private String ClosingDate;

    @SerializedName("PurchaseType")
    @Expose
    private String PurchaseType;
    @SerializedName("CustomerType")
    @Expose
    private int CustomerType;
    @SerializedName("EnqType")
    @Expose
    private String EnqType;

    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("AdditionalInfo")
    @Expose
    private String AdditionalInfo;

    @SerializedName("FinanceID")
    @Expose
    private String FinanceID;
    @SerializedName("FinanceName")
    @Expose
    private String FinanceName;
    @SerializedName("FinanceType")
    @Expose
    private String FinanceType;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnquiryType() {
        return enquiryType;
    }

    public void setEnquiryType(String enquiryType) {
        this.enquiryType = enquiryType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAssignedid() {
        return assignedid;
    }

    public void setAssignedid(String assignedid) {
        this.assignedid = assignedid;
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

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getClosure() {
        return Closure;
    }

    public void setClosure(int closure) {
        Closure = closure;
    }

    public String getClosingDate() {
        return ClosingDate;
    }

    public void setClosingDate(String closingDate) {
        ClosingDate = closingDate;
    }

    public String getPurchaseType() {
        return PurchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        PurchaseType = purchaseType;
    }

    public int getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(int customerType) {
        CustomerType = customerType;
    }

    public String getEnqType() {
        return EnqType;
    }

    public void setEnqType(String enqType) {
        EnqType = enqType;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getModelColorID() {
        return ModelColorID;
    }

    public void setModelColorID(String modelColorID) {
        ModelColorID = modelColorID;
    }

    public String getModelColor() {
        return ModelColor;
    }

    public void setModelColor(String modelColor) {
        ModelColor = modelColor;
    }

    public String getAdditionalInfo() {
        return AdditionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        AdditionalInfo = additionalInfo;
    }

    public String getFinanceID() {
        return FinanceID;
    }

    public void setFinanceID(String financeID) {
        FinanceID = financeID;
    }

    public String getFinanceName() {
        return FinanceName;
    }

    public void setFinanceName(String financeName) {
        FinanceName = financeName;
    }

    public String getFinanceType() {
        return FinanceType;
    }

    public void setFinanceType(String financeType) {
        FinanceType = financeType;
    }
}
