package com.hjsoftware.ptplogistics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalAmountPojo {
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("extrarate")
    @Expose
    private String extrarate;
    @SerializedName("regamt")
    @Expose
    private String regamt;
    @SerializedName("extraamt")
    @Expose
    private String extraamt;
    @SerializedName("totamt")
    @Expose
    private String totamt;
    @SerializedName("Kmsforcal")
    @Expose
    private String kmsforcal;
    @SerializedName("Kms")
    @Expose
    private String kms;
    @SerializedName("articlerate1")
    @Expose
    private String articlerate1;
    @SerializedName("articleamt1")
    @Expose
    private String articleamt1;
    @SerializedName("articlerate2")
    @Expose
    private String articlerate2;
    @SerializedName("articleamt2")
    @Expose
    private String articleamt2;
    @SerializedName("articlerate3")
    @Expose
    private String articlerate3;
    @SerializedName("articleamt3")
    @Expose
    private String articleamt3;
    @SerializedName("ratechanges")
    @Expose
    private Object ratechanges;
    @SerializedName("isTariffExist")
    @Expose
    private String isTariffExist;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getExtrarate() {
        return extrarate;
    }

    public void setExtrarate(String extrarate) {
        this.extrarate = extrarate;
    }

    public String getRegamt() {
        return regamt;
    }

    public void setRegamt(String regamt) {
        this.regamt = regamt;
    }

    public String getExtraamt() {
        return extraamt;
    }

    public void setExtraamt(String extraamt) {
        this.extraamt = extraamt;
    }

    public String getTotamt() {
        return totamt;
    }

    public void setTotamt(String totamt) {
        this.totamt = totamt;
    }

    public String getKmsforcal() {
        return kmsforcal;
    }

    public void setKmsforcal(String kmsforcal) {
        this.kmsforcal = kmsforcal;
    }

    public String getKms() {
        return kms;
    }

    public void setKms(String kms) {
        this.kms = kms;
    }

    public String getArticlerate1() {
        return articlerate1;
    }

    public void setArticlerate1(String articlerate1) {
        this.articlerate1 = articlerate1;
    }

    public String getArticleamt1() {
        return articleamt1;
    }

    public void setArticleamt1(String articleamt1) {
        this.articleamt1 = articleamt1;
    }

    public String getArticlerate2() {
        return articlerate2;
    }

    public void setArticlerate2(String articlerate2) {
        this.articlerate2 = articlerate2;
    }

    public String getArticleamt2() {
        return articleamt2;
    }

    public void setArticleamt2(String articleamt2) {
        this.articleamt2 = articleamt2;
    }

    public String getArticlerate3() {
        return articlerate3;
    }

    public void setArticlerate3(String articlerate3) {
        this.articlerate3 = articlerate3;
    }

    public String getArticleamt3() {
        return articleamt3;
    }

    public void setArticleamt3(String articleamt3) {
        this.articleamt3 = articleamt3;
    }

    public Object getRatechanges() {
        return ratechanges;
    }

    public void setRatechanges(Object ratechanges) {
        this.ratechanges = ratechanges;
    }

    public String getIsTariffExist() {
        return isTariffExist;
    }

    public void setIsTariffExist(String isTariffExist) {
        this.isTariffExist = isTariffExist;
    }
}
