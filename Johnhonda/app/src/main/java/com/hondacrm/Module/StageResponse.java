package com.hondacrm.Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StageResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("Stage")
    @Expose
    private String Stage;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStage() {
        return Stage;
    }

    public void setStage(String stage) {
        Stage = stage;
    }
}
