package com.hjsoftware.ptplogistics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class newLrpojo {

    @SerializedName("agentname")
    @Expose
    private String agentname;
    @SerializedName("branchname")
    @Expose
    private String branchname;
    @SerializedName("brid")
    @Expose
    private String brid;
    @SerializedName("hubstatus")
    @Expose
    private String hubstatus;
    @SerializedName("servicelocationid")
    @Expose
    private String servicelocationid;
    @SerializedName("servicelocationname")
    @Expose
    private String servicelocationname;
    @SerializedName("typeofagent")
    @Expose
    private String typeofagent;
    @SerializedName("deliverystatus")
    @Expose
    private String deliverystatus;
    @SerializedName("branchtype")
    @Expose
    private String branchtype;
    @SerializedName("ratechangestatus")
    @Expose
    private String ratechangestatus;
    @SerializedName("lractivity")
    @Expose
    private String lractivity;
    @SerializedName("userprofile")
    @Expose
    private String userprofile;
    @SerializedName("Profileid")
    @Expose
    private String profileid;
    @SerializedName("brcode")
    @Expose
    private String brcode;
    //modifiedprivilage
    @SerializedName("modifiedprivilage")
    @Expose
    private String modifiedprivilage;

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getBrid() {
        return brid;
    }

    public void setBrid(String brid) {
        this.brid = brid;
    }

    public String getHubstatus() {
        return hubstatus;
    }

    public void setHubstatus(String hubstatus) {
        this.hubstatus = hubstatus;
    }

    public String getServicelocationid() {
        return servicelocationid;
    }

    public void setServicelocationid(String servicelocationid) {
        this.servicelocationid = servicelocationid;
    }

    public String getServicelocationname() {
        return servicelocationname;
    }

    public void setServicelocationname(String servicelocationname) {
        this.servicelocationname = servicelocationname;
    }

    public String getTypeofagent() {
        return typeofagent;
    }

    public void setTypeofagent(String typeofagent) {
        this.typeofagent = typeofagent;
    }

    public String getDeliverystatus() {
        return deliverystatus;
    }

    public void setDeliverystatus(String deliverystatus) {
        this.deliverystatus = deliverystatus;
    }

    public String getBranchtype() {
        return branchtype;
    }

    public void setBranchtype(String branchtype) {
        this.branchtype = branchtype;
    }

    public String getRatechangestatus() {
        return ratechangestatus;
    }

    public void setRatechangestatus(String ratechangestatus) {
        this.ratechangestatus = ratechangestatus;
    }

    public String getLractivity() {
        return lractivity;
    }

    public void setLractivity(String lractivity) {
        this.lractivity = lractivity;
    }

    public String getUserprofile() {
        return userprofile;
    }

    public void setUserprofile(String userprofile) {
        this.userprofile = userprofile;
    }

    public String getProfileid() {
        return profileid;
    }

    public void setProfileid(String profileid) {
        this.profileid = profileid;
    }

    public String getBrcode() {
        return brcode;
    }

    public void setBrcode(String brcode) {
        this.brcode = brcode;
    }

    public String getModifiedprivilage() {
        return modifiedprivilage;
    }

    public void setModifiedprivilage(String modifiedprivilage) {
        this.modifiedprivilage = modifiedprivilage;
    }
}
