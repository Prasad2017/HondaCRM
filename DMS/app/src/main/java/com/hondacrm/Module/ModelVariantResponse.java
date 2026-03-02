package com.hondacrm.Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelVariantResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ModelName")
    @Expose
    private String modelName;

    //Products
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("ModelSegment")
    @Expose
    private String modelSegment;
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
    private String description;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
