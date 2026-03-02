package com.hondacrm.Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelNameResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ModelName")
    @Expose
    private String modelName;

    @SerializedName("")

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
}
