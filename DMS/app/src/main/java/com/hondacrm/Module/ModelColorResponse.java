package com.hondacrm.Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelColorResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ModelColor")
    @Expose
    private String modelColor;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelColor() {
        return modelColor;
    }

    public void setModelColor(String modelColor) {
        this.modelColor = modelColor;
    }
}
