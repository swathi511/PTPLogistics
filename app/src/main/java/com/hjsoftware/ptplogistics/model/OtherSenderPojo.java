package com.hjsoftware.ptplogistics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtherSenderPojo {
    @SerializedName("LrCharges")
    @Expose
    private String lrCharges;
    @SerializedName("ArticleCharges")
    @Expose
    private String articleCharges;
    @SerializedName("lramount")
    @Expose
    private String lramount;
    @SerializedName("articleamount")
    @Expose
    private String articleamount;
    @SerializedName("hamalichrcriteria")
    @Expose
    private String hamalichrcriteria;
    @SerializedName("hamalicharges")
    @Expose
    private String hamalicharges;
    @SerializedName("minimumweight")
    @Expose
    private String minimumweight;
    @SerializedName("perkgs")
    @Expose
    private String perkgs;
    @SerializedName("surcharges")
    @Expose
    private String surcharges;
    @SerializedName("lrpasscharge")
    @Expose
    private String lrpasscharge;

    public String getLrCharges() {
        return lrCharges;
    }

    public void setLrCharges(String lrCharges) {
        this.lrCharges = lrCharges;
    }

    public String getArticleCharges() {
        return articleCharges;
    }

    public void setArticleCharges(String articleCharges) {
        this.articleCharges = articleCharges;
    }

    public String getLramount() {
        return lramount;
    }

    public void setLramount(String lramount) {
        this.lramount = lramount;
    }

    public String getArticleamount() {
        return articleamount;
    }

    public void setArticleamount(String articleamount) {
        this.articleamount = articleamount;
    }

    public String getHamalichrcriteria() {
        return hamalichrcriteria;
    }

    public void setHamalichrcriteria(String hamalichrcriteria) {
        this.hamalichrcriteria = hamalichrcriteria;
    }

    public String getHamalicharges() {
        return hamalicharges;
    }

    public void setHamalicharges(String hamalicharges) {
        this.hamalicharges = hamalicharges;
    }

    public String getMinimumweight() {
        return minimumweight;
    }

    public void setMinimumweight(String minimumweight) {
        this.minimumweight = minimumweight;
    }

    public String getPerkgs() {
        return perkgs;
    }

    public void setPerkgs(String perkgs) {
        this.perkgs = perkgs;
    }

    public String getSurcharges() {
        return surcharges;
    }

    public void setSurcharges(String surcharges) {
        this.surcharges = surcharges;
    }

    public String getLrpasscharge() {
        return lrpasscharge;
    }

    public void setLrpasscharge(String lrpasscharge) {
        this.lrpasscharge = lrpasscharge;
    }

}