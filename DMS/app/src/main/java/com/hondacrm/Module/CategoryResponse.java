package com.hondacrm.Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ModelCategory")
    @Expose
    private String modelCategory;
    @SerializedName("Closure")
    @Expose
    private String Closure;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelCategory() {
        return modelCategory;
    }

    public void setModelCategory(String modelCategory) {
        this.modelCategory = modelCategory;
    }

    public String getClosure() {
        return Closure;
    }

    public void setClosure(String closure) {
        Closure = closure;
    }
}
