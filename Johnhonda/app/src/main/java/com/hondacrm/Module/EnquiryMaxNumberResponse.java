package com.hondacrm.Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EnquiryMaxNumberResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("BranchId")
    @Expose
    private String BranchId;
    @SerializedName("Branch")
    @Expose
    private String branch;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("EnqPrefix")
    @Expose
    private String enqPrefix;
    @SerializedName("EnqSuffix")
    @Expose
    private String enqSuffix;
    @SerializedName("QuoPrefix")
    @Expose
    private String QuoPrefix;
    @SerializedName("EnquiryNo")
    @Expose
    private String EnquiryNo;
    @SerializedName("QuotationN")
    @Expose
    private String QuotationN;
    @SerializedName("QuotationNo")
    @Expose
    private String QuotationNo;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEnqPrefix() {
        return enqPrefix;
    }

    public void setEnqPrefix(String enqPrefix) {
        this.enqPrefix = enqPrefix;
    }

    public String getEnqSuffix() {
        return enqSuffix;
    }

    public void setEnqSuffix(String enqSuffix) {
        this.enqSuffix = enqSuffix;
    }

    public String getEnquiryNo() {
        return EnquiryNo;
    }

    public void setEnquiryNo(String enquiryNo) {
        EnquiryNo = enquiryNo;
    }


    public String getBranchId() {
        return BranchId;
    }

    public void setBranchId(String branchId) {
        BranchId = branchId;
    }

    public String getQuotationN() {
        return QuotationN;
    }

    public void setQuotationN(String quotationN) {
        QuotationN = quotationN;
    }

    public String getQuotationNo() {
        return QuotationNo;
    }

    public void setQuotationNo(String quotationNo) {
        QuotationNo = quotationNo;
    }

    public String getQuoPrefix() {
        return QuoPrefix;
    }

    public void setQuoPrefix(String quoPrefix) {
        QuoPrefix = quoPrefix;
    }
}
