package com.hjsoftware.ptplogistics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LRTrackingPojo {

    @SerializedName("FromLoc")
    @Expose
    private String fromLoc;
    @SerializedName("ToLoc")
    @Expose
    private String toLoc;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Dispatchno")
    @Expose
    private String dispatchno;
    @SerializedName("vehNo")
    @Expose
    private String vehNo;

    public String getFromLoc() {
        return fromLoc;
    }

    public void setFromLoc(String fromLoc) {
        this.fromLoc = fromLoc;
    }

    public String getToLoc() {
        return toLoc;
    }

    public void setToLoc(String toLoc) {
        this.toLoc = toLoc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDispatchno() {
        return dispatchno;
    }

    public void setDispatchno(String dispatchno) {
        this.dispatchno = dispatchno;
    }

    public String getVehNo() {
        return vehNo;
    }

    public void setVehNo(String vehNo) {
        this.vehNo = vehNo;
    }
}
