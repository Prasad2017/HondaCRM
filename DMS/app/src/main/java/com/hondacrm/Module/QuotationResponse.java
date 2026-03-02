package com.hondacrm.Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuotationResponse {

    @SerializedName("id")
    @Expose
    private String id;
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
    @SerializedName("QuotationN")
    @Expose
    private String quotationN;
    @SerializedName("QuotationNo")
    @Expose
    private String quotationNo;
    @SerializedName("ModelName")
    @Expose
    private String modelName;
    @SerializedName("Modelvarient")
    @Expose
    private String modelvarient;
    @SerializedName("ModelCategory")
    @Expose
    private String modelCategory;
    @SerializedName("ModelColorID")
    @Expose
    private String ModelColorID;
    @SerializedName("ModelColor")
    @Expose
    private String ModelColor;
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
    @SerializedName("StatgeID")
    @Expose
    private String statgeID;
    @SerializedName("SourceID")
    @Expose
    private String sourceID;
    @SerializedName("NameID")
    @Expose
    private String nameID;
    @SerializedName("CustomerType")
    @Expose
    private int CustomerType;
    @SerializedName("EnqType")
    @Expose
    private String EnqType;



    @SerializedName("PurchaseType")
    @Expose
    private Integer purchaseType;
    @SerializedName("SerialNo")
    @Expose
    private String serialNo;
    @SerializedName("ExShowroomPrice")
    @Expose
    private Double exShowroomPrice;
    @SerializedName("InsuranceAmount")
    @Expose
    private Double insuranceAmount;
    @SerializedName("RegistrationAmount")
    @Expose
    private Double registrationAmount;
    @SerializedName("RoadTax")
    @Expose
    private Double roadTax;
    @SerializedName("OnRoadPrice")
    @Expose
    private Double onRoadPrice;
    @SerializedName("OtherTaxes")
    @Expose
    private Double otherTaxes;
    @SerializedName("ExtendedWarranty")
    @Expose
    private Double extendedWarranty;
    @SerializedName("Rsa")
    @Expose
    private Double rsa;
    @SerializedName("TaxId1Name")
    @Expose
    private Double taxId1Name;
    @SerializedName("TaxId1Type")
    @Expose
    private String taxId1Type;
    @SerializedName("TaxId2Name")
    @Expose
    private String taxId2Name;
    @SerializedName("TaxId2Type")
    @Expose
    private String taxId2Type;
    @SerializedName("CessName")
    @Expose
    private String cessName;
    @SerializedName("CessType")
    @Expose
    private String cessType;

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

    @SerializedName("UnitPrice")
    @Expose
    private String UnitPrice;
    @SerializedName("Subtotal")
    @Expose
    private String Subtotal;
    @SerializedName("Total")
    @Expose
    private String Total;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getQuotationN() {
        return quotationN;
    }

    public void setQuotationN(String quotationN) {
        this.quotationN = quotationN;
    }

    public String getQuotationNo() {
        return quotationNo;
    }

    public void setQuotationNo(String quotationNo) {
        this.quotationNo = quotationNo;
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

    public Integer getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(Integer purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Double getExShowroomPrice() {
        return exShowroomPrice;
    }

    public void setExShowroomPrice(Double exShowroomPrice) {
        this.exShowroomPrice = exShowroomPrice;
    }

    public Double getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(Double insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public Double getRegistrationAmount() {
        return registrationAmount;
    }

    public void setRegistrationAmount(Double registrationAmount) {
        this.registrationAmount = registrationAmount;
    }

    public Double getRoadTax() {
        return roadTax;
    }

    public void setRoadTax(Double roadTax) {
        this.roadTax = roadTax;
    }

    public Double getOnRoadPrice() {
        return onRoadPrice;
    }

    public void setOnRoadPrice(Double onRoadPrice) {
        this.onRoadPrice = onRoadPrice;
    }

    public Double getOtherTaxes() {
        return otherTaxes;
    }

    public void setOtherTaxes(Double otherTaxes) {
        this.otherTaxes = otherTaxes;
    }

    public Double getExtendedWarranty() {
        return extendedWarranty;
    }

    public void setExtendedWarranty(Double extendedWarranty) {
        this.extendedWarranty = extendedWarranty;
    }

    public Double getRsa() {
        return rsa;
    }

    public void setRsa(Double rsa) {
        this.rsa = rsa;
    }

    public Double getTaxId1Name() {
        return taxId1Name;
    }

    public void setTaxId1Name(Double taxId1Name) {
        this.taxId1Name = taxId1Name;
    }

    public String getTaxId1Type() {
        return taxId1Type;
    }

    public void setTaxId1Type(String taxId1Type) {
        this.taxId1Type = taxId1Type;
    }

    public String getTaxId2Name() {
        return taxId2Name;
    }

    public void setTaxId2Name(String taxId2Name) {
        this.taxId2Name = taxId2Name;
    }

    public String getTaxId2Type() {
        return taxId2Type;
    }

    public void setTaxId2Type(String taxId2Type) {
        this.taxId2Type = taxId2Type;
    }

    public String getCessName() {
        return cessName;
    }

    public void setCessName(String cessName) {
        this.cessName = cessName;
    }

    public String getCessType() {
        return cessType;
    }

    public void setCessType(String cessType) {
        this.cessType = cessType;
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

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(String subtotal) {
        Subtotal = subtotal;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }
}
