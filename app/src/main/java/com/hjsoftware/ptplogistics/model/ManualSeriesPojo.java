package com.hjsoftware.ptplogistics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ManualSeriesPojo {

    @SerializedName("seriesid")
    @Expose
    private String seriesid;
    @SerializedName("lrseries")
    @Expose
    private String lrseries;
    @SerializedName("branchid")
    @Expose
    private String branchid;
    @SerializedName("branchname")
    @Expose
    private String branchname;

    public String getSeriesid() {
        return seriesid;
    }

    public void setSeriesid(String seriesid) {
        this.seriesid = seriesid;
    }

    public String getLrseries() {
        return lrseries;
    }

    public void setLrseries(String lrseries) {
        this.lrseries = lrseries;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }
}
