package com.hjsoftware.ptplogistics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LRPodUpdatePojo {

    @SerializedName("FromLoc")
    @Expose
    private String fromLoc;
    @SerializedName("ToLoc")
    @Expose
    private String toLoc;
    @SerializedName("sendername")
    @Expose
    private String sendername;
    @SerializedName("ReceiverName")
    @Expose
    private String receiverName;
    @SerializedName("lrtype")
    @Expose
    private String lrtype;
    @SerializedName("PODStatus")
    @Expose
    private String pODStatus;
    @SerializedName("filepath")
    @Expose
    private String filepath;

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

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getLrtype() {
        return lrtype;
    }

    public void setLrtype(String lrtype) {
        this.lrtype = lrtype;
    }

    public String getPODStatus() {
        return pODStatus;
    }

    public void setPODStatus(String pODStatus) {
        this.pODStatus = pODStatus;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
