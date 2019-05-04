package com.hjsoftware.ptplogistics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class locPojo {
    @SerializedName("locationid")
    @Expose
    private String locationid;
    @SerializedName("location")
    @Expose
    private String location;

    public String getLocationid() {
        return locationid;
    }

    public void setLocationid(String locationid) {
        this.locationid = locationid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
