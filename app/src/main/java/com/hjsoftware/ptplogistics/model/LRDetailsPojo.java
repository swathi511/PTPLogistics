package com.hjsoftware.ptplogistics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LRDetailsPojo {

    @SerializedName("LRNo")
    @Expose
    private String lRNo;
    @SerializedName("srno")
    @Expose
    private String srno;
    @SerializedName("Article")
    @Expose
    private String article;
    @SerializedName("Weight")
    @Expose
    private String weight;
    @SerializedName("actweight")
    @Expose
    private String actweight;
    @SerializedName("ArticleType")
    @Expose
    private String articleType;
    @SerializedName("ArticleDescription")
    @Expose
    private String articleDescription;
    @SerializedName("length")
    @Expose
    private String length;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("cftwt")
    @Expose
    private String cftwt;
    @SerializedName("Qty")
    @Expose
    private String qty;
    @SerializedName("measurments")
    @Expose
    private String measurments;
    @SerializedName("barcode")
    @Expose
    private String barcode;

    public String getLRNo() {
        return lRNo;
    }

    public void setLRNo(String lRNo) {
        this.lRNo = lRNo;
    }

    public String getSrno() {
        return srno;
    }

    public void setSrno(String srno) {
        this.srno = srno;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getActweight() {
        return actweight;
    }

    public void setActweight(String actweight) {
        this.actweight = actweight;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getCftwt() {
        return cftwt;
    }

    public void setCftwt(String cftwt) {
        this.cftwt = cftwt;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getMeasurments() {
        return measurments;
    }

    public void setMeasurments(String measurments) {
        this.measurments = measurments;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
