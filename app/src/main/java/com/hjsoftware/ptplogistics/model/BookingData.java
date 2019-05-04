package com.hjsoftware.ptplogistics.model;

public class BookingData {

    String lrno;

    public BookingData(String lrno)
    {
        this.lrno=lrno;
    }

    public String getLrno() {
        return lrno;
    }

    public void setLrno(String lrno) {
        this.lrno = lrno;
    }
}
