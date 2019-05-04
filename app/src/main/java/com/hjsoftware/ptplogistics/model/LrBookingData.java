package com.hjsoftware.ptplogistics.model;

import java.io.Serializable;

public class LrBookingData implements Serializable {

    String Article,Actwt,Chdwt,Qty,Rate,Amount,finalAmt;

    public LrBookingData(  String Article,String Actwt,String Chdwt,String Qty,String Rate,String Amount,String finalAmt ){


        this.Article=Article;
        this.Actwt=Actwt;
        this.Chdwt=Chdwt;
        this.Qty=Qty;
        this.Rate=Rate;
        this.Amount=Amount;
        this.finalAmt=finalAmt;

    }

    public String getArticle() {
        return Article;
    }

    public void setArticle(String article) {
        Article = article;
    }

    public String getActwt() {
        return Actwt;
    }

    public void setActwt(String actwt) {
        Actwt = actwt;
    }

    public String getChdwt() {
        return Chdwt;
    }

    public void setChdwt(String chdwt) {
        Chdwt = chdwt;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getFinalAmt() {
        return finalAmt;
    }

    public void setFinalAmt(String finalAmt) {
        this.finalAmt = finalAmt;
    }
}
