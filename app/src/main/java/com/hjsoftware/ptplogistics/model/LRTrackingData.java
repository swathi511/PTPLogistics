package com.hjsoftware.ptplogistics.model;

public class LRTrackingData {

    String date,time,lrStatus;

    public LRTrackingData(String date,String time,String lrStatus)
    {
        this.date=date;
        this.time=time;
        this.lrStatus=lrStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLrStatus() {
        return lrStatus;
    }

    public void setLrStatus(String lrStatus) {
        this.lrStatus = lrStatus;
    }
}
