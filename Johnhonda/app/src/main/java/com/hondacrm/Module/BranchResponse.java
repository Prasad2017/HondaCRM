package com.hondacrm.Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchResponse {

    @SerializedName("Id")
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranchId() {
        return BranchId;
    }

    public void setBranchId(String branchId) {
        BranchId = branchId;
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
}
