package com.hondacrm.Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelPriceResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("ModelSegment")
    @Expose
    private String modelSegment;
    @SerializedName("ModelName")
    @Expose
    private String modelName;
    @SerializedName("ModelCat")
    @Expose
    private String modelCat;
    @SerializedName("ModelVarient")
    @Expose
    private String modelVarient;
    @SerializedName("SerialNo")
    @Expose
    private String serialNo;
    @SerializedName("ExShowroomPrice")
    @Expose
    private String exShowroomPrice;
    @SerializedName("InsuranceAmount")
    @Expose
    private String insuranceAmount;
    @SerializedName("RegistrationAmount")
    @Expose
    private String registrationAmount;
    @SerializedName("RoadTax")
    @Expose
    private String roadTax;
    @SerializedName("OnRoadPrice")
    @Expose
    private String onRoadPrice;
    @SerializedName("OtherTaxes")
    @Expose
    private String otherTaxes;
    @SerializedName("ExtendedWarranty")
    @Expose
    private String extendedWarranty;
    @SerializedName("Rsa")
    @Expose
    private String rsa;
    @SerializedName("TaxId1Name")
    @Expose
    private String taxId1Name;
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
    private String description;


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

    public String getModelSegment() {
        return modelSegment;
    }

    public void setModelSegment(String modelSegment) {
        this.modelSegment = modelSegment;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelCat() {
        return modelCat;
    }

    public void setModelCat(String modelCat) {
        this.modelCat = modelCat;
    }

    public String getModelVarient() {
        return modelVarient;
    }

    public void setModelVarient(String modelVarient) {
        this.modelVarient = modelVarient;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getExShowroomPrice() {
        return exShowroomPrice;
    }

    public void setExShowroomPrice(String exShowroomPrice) {
        this.exShowroomPrice = exShowroomPrice;
    }

    public String getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(String insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public String getRegistrationAmount() {
        return registrationAmount;
    }

    public void setRegistrationAmount(String registrationAmount) {
        this.registrationAmount = registrationAmount;
    }

    public String getRoadTax() {
        return roadTax;
    }

    public void setRoadTax(String roadTax) {
        this.roadTax = roadTax;
    }

    public String getOnRoadPrice() {
        return onRoadPrice;
    }

    public void setOnRoadPrice(String onRoadPrice) {
        this.onRoadPrice = onRoadPrice;
    }

    public String getOtherTaxes() {
        return otherTaxes;
    }

    public void setOtherTaxes(String otherTaxes) {
        this.otherTaxes = otherTaxes;
    }

    public String getExtendedWarranty() {
        return extendedWarranty;
    }

    public void setExtendedWarranty(String extendedWarranty) {
        this.extendedWarranty = extendedWarranty;
    }

    public String getRsa() {
        return rsa;
    }

    public void setRsa(String rsa) {
        this.rsa = rsa;
    }

    public String getTaxId1Name() {
        return taxId1Name;
    }

    public void setTaxId1Name(String taxId1Name) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
