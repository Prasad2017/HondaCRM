package com.hondacrm.Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FinanceResponse {

    @SerializedName("id")
    @Expose
    private String financeId;
    @SerializedName("Finance")
    @Expose
    private String financeName;


    public String getFinanceId() {
        return financeId;
    }

    public void setFinanceId(String financeId) {
        this.financeId = financeId;
    }

    public String getFinanceName() {
        return financeName;
    }

    public void setFinanceName(String financeName) {
        this.financeName = financeName;
    }
}
