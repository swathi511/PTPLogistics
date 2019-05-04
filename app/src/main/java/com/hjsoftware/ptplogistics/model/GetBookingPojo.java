package com.hjsoftware.ptplogistics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBookingPojo {

    @SerializedName("LRNo")
    @Expose
    private String lRNo;
    @SerializedName("LRDate")
    @Expose
    private String lRDate;
    @SerializedName("LRType")
    @Expose
    private String lRType;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("FromLocation")
    @Expose
    private String fromLocation;
    @SerializedName("FromBranch")
    @Expose
    private String fromBranch;
    @SerializedName("ToLocation")
    @Expose
    private String toLocation;
    @SerializedName("ToBranch")
    @Expose
    private String toBranch;
    @SerializedName("Prepared")
    @Expose
    private String prepared;
    @SerializedName("ForApproval")
    @Expose
    private String forApproval;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("lrentrytype")
    @Expose
    private String lrentrytype;

    public String getLRNo() {
        return lRNo;
    }

    public void setLRNo(String lRNo) {
        this.lRNo = lRNo;
    }

    public String getLRDate() {
        return lRDate;
    }

    public void setLRDate(String lRDate) {
        this.lRDate = lRDate;
    }

    public String getLRType() {
        return lRType;
    }

    public void setLRType(String lRType) {
        this.lRType = lRType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getFromBranch() {
        return fromBranch;
    }

    public void setFromBranch(String fromBranch) {
        this.fromBranch = fromBranch;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getToBranch() {
        return toBranch;
    }

    public void setToBranch(String toBranch) {
        this.toBranch = toBranch;
    }

    public String getPrepared() {
        return prepared;
    }

    public void setPrepared(String prepared) {
        this.prepared = prepared;
    }

    public String getForApproval() {
        return forApproval;
    }

    public void setForApproval(String forApproval) {
        this.forApproval = forApproval;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLrentrytype() {
        return lrentrytype;
    }

    public void setLrentrytype(String lrentrytype) {
        this.lrentrytype = lrentrytype;
    }
}
