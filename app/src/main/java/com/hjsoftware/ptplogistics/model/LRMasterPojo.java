package com.hjsoftware.ptplogistics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LRMasterPojo implements Serializable {

    @SerializedName("LRNo")
    @Expose
    private String lRNo;
    @SerializedName("LRDate")
    @Expose
    private String lRDate;
    @SerializedName("LrEntrydate")
    @Expose
    private String lrEntrydate;
    @SerializedName("LRType")
    @Expose
    private String lRType;
    @SerializedName("SenderId")
    @Expose
    private String senderId;
    @SerializedName("SenderName")
    @Expose
    private String senderName;
    @SerializedName("SenderAddress")
    @Expose
    private String senderAddress;
    @SerializedName("DispatchType")
    @Expose
    private String dispatchType;
    @SerializedName("DoorDeliveryType")
    @Expose
    private String doorDeliveryType;
    @SerializedName("SenderMobileno")
    @Expose
    private String senderMobileno;
    @SerializedName("ReceiverName")
    @Expose
    private String receiverName;
    @SerializedName("ReceiverAddress")
    @Expose
    private String receiverAddress;
    @SerializedName("ReceiverMobileno")
    @Expose
    private String receiverMobileno;
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
    @SerializedName("Tariff")
    @Expose
    private String tariff;
    @SerializedName("GoodsValue")
    @Expose
    private String goodsValue;
    @SerializedName("SubTotal")
    @Expose
    private String subTotal;
    @SerializedName("Lrcharges")
    @Expose
    private String lrcharges;
    @SerializedName("DdCharges")
    @Expose
    private String ddCharges;
    @SerializedName("HamaliCharges")
    @Expose
    private String hamaliCharges;
    @SerializedName("CcCharges")
    @Expose
    private String ccCharges;
    @SerializedName("DcCharges")
    @Expose
    private String dcCharges;
    @SerializedName("TotAmount")
    @Expose
    private String totAmount;
    @SerializedName("Del_Type")
    @Expose
    private String delType;
    @SerializedName("Calc_Cret")
    @Expose
    private String calcCret;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("LRCancel_Date")
    @Expose
    private String lRCancelDate;
    @SerializedName("LRNaration")
    @Expose
    private String lRNaration;
    @SerializedName("CInvno")
    @Expose
    private String cInvno;
    @SerializedName("SSMS")
    @Expose
    private String sSMS;
    @SerializedName("RSMS")
    @Expose
    private String rSMS;
    @SerializedName("SMobileno")
    @Expose
    private String sMobileno;
    @SerializedName("RMobileno")
    @Expose
    private String rMobileno;
    @SerializedName("SurCharges")
    @Expose
    private String surCharges;
    @SerializedName("WithPasschg")
    @Expose
    private String withPasschg;
    @SerializedName("StationaryChg")
    @Expose
    private String stationaryChg;
    @SerializedName("ArticleChg")
    @Expose
    private String articleChg;
    @SerializedName("CartageChg")
    @Expose
    private String cartageChg;
    @SerializedName("ServiceTax")
    @Expose
    private String serviceTax;
    @SerializedName("TransportChg")
    @Expose
    private String transportChg;
    @SerializedName("FocRemarks")
    @Expose
    private String focRemarks;
    @SerializedName("Discount")
    @Expose
    private String discount;
    @SerializedName("AfterDiscount")
    @Expose
    private String afterDiscount;
    @SerializedName("ForApproval")
    @Expose
    private String forApproval;
    @SerializedName("BookSeriesId")
    @Expose
    private String bookSeriesId;
    @SerializedName("Preparedby")
    @Expose
    private String preparedby;
    @SerializedName("Hflid")
    @Expose
    private String hflid;
    @SerializedName("HTolid")
    @Expose
    private String hTolid;
    @SerializedName("LrDiscRemarks")
    @Expose
    private String lrDiscRemarks;
    @SerializedName("BeforeStax")
    @Expose
    private String beforeStax;
    @SerializedName("StaxCriteria")
    @Expose
    private String staxCriteria;
    @SerializedName("OldLRno")
    @Expose
    private String oldLRno;
    @SerializedName("OldLRAmount")
    @Expose
    private String oldLRAmount;
    @SerializedName("LRPassStatus")
    @Expose
    private String lRPassStatus;
    @SerializedName("LRPassCharges")
    @Expose
    private String lRPassCharges;
    @SerializedName("BookingSource")
    @Expose
    private String bookingSource;
    @SerializedName("vendorid")
    @Expose
    private String vendorid;
    @SerializedName("vendorlrtype")
    @Expose
    private String vendorlrtype;
    @SerializedName("vendorlrno")
    @Expose
    private String vendorlrno;
    @SerializedName("vendorlramt")
    @Expose
    private String vendorlramt;
    @SerializedName("vendorname")
    @Expose
    private String vendorname;
    @SerializedName("vrfrmlocation")
    @Expose
    private String vrfrmlocation;
    @SerializedName("vrtolocation")
    @Expose
    private String vrtolocation;
    @SerializedName("tariffcriteria")
    @Expose
    private String tariffcriteria;
    @SerializedName("valuesurchrgstatus")
    @Expose
    private String valuesurchrgstatus;
    @SerializedName("valuesurcharge")
    @Expose
    private String valuesurcharge;
    @SerializedName("ConsignorGSTIN")
    @Expose
    private String consignorGSTIN;
    @SerializedName("ConsigneeGSTIN")
    @Expose
    private String consigneeGSTIN;
    @SerializedName("podcharges")
    @Expose
    private String podcharges;
    @SerializedName("ewaybill")
    @Expose
    private String ewaybill;
    @SerializedName("barcodecriteria")
    @Expose
    private String barcodecriteria;
    @SerializedName("cgstper")
    @Expose
    private String cgstper;
    @SerializedName("cgstamt")
    @Expose
    private String cgstamt;
    @SerializedName("igstper")
    @Expose
    private String igstper;
    @SerializedName("igstamt")
    @Expose
    private String igstamt;
    @SerializedName("oldsubtotal")
    @Expose
    private String oldsubtotal;
    @SerializedName("lrentrytype")
    @Expose
    private String lrentrytype;
    @SerializedName("Dispatch")
    @Expose
    private String Dispatch;

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

    public String getLrEntrydate() {
        return lrEntrydate;
    }

    public void setLrEntrydate(String lrEntrydate) {
        this.lrEntrydate = lrEntrydate;
    }

    public String getLRType() {
        return lRType;
    }

    public void setLRType(String lRType) {
        this.lRType = lRType;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getDispatchType() {
        return dispatchType;
    }

    public void setDispatchType(String dispatchType) {
        this.dispatchType = dispatchType;
    }

    public String getDoorDeliveryType() {
        return doorDeliveryType;
    }

    public void setDoorDeliveryType(String doorDeliveryType) {
        this.doorDeliveryType = doorDeliveryType;
    }

    public String getSenderMobileno() {
        return senderMobileno;
    }

    public void setSenderMobileno(String senderMobileno) {
        this.senderMobileno = senderMobileno;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverMobileno() {
        return receiverMobileno;
    }

    public void setReceiverMobileno(String receiverMobileno) {
        this.receiverMobileno = receiverMobileno;
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

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public String getGoodsValue() {
        return goodsValue;
    }

    public void setGoodsValue(String goodsValue) {
        this.goodsValue = goodsValue;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getLrcharges() {
        return lrcharges;
    }

    public void setLrcharges(String lrcharges) {
        this.lrcharges = lrcharges;
    }

    public String getDdCharges() {
        return ddCharges;
    }

    public void setDdCharges(String ddCharges) {
        this.ddCharges = ddCharges;
    }

    public String getHamaliCharges() {
        return hamaliCharges;
    }

    public void setHamaliCharges(String hamaliCharges) {
        this.hamaliCharges = hamaliCharges;
    }

    public String getCcCharges() {
        return ccCharges;
    }

    public void setCcCharges(String ccCharges) {
        this.ccCharges = ccCharges;
    }

    public String getDcCharges() {
        return dcCharges;
    }

    public void setDcCharges(String dcCharges) {
        this.dcCharges = dcCharges;
    }

    public String getTotAmount() {
        return totAmount;
    }

    public void setTotAmount(String totAmount) {
        this.totAmount = totAmount;
    }

    public String getDelType() {
        return delType;
    }

    public void setDelType(String delType) {
        this.delType = delType;
    }

    public String getCalcCret() {
        return calcCret;
    }

    public void setCalcCret(String calcCret) {
        this.calcCret = calcCret;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLRCancelDate() {
        return lRCancelDate;
    }

    public void setLRCancelDate(String lRCancelDate) {
        this.lRCancelDate = lRCancelDate;
    }

    public String getLRNaration() {
        return lRNaration;
    }

    public void setLRNaration(String lRNaration) {
        this.lRNaration = lRNaration;
    }

    public String getCInvno() {
        return cInvno;
    }

    public void setCInvno(String cInvno) {
        this.cInvno = cInvno;
    }

    public String getSSMS() {
        return sSMS;
    }

    public void setSSMS(String sSMS) {
        this.sSMS = sSMS;
    }

    public String getRSMS() {
        return rSMS;
    }

    public void setRSMS(String rSMS) {
        this.rSMS = rSMS;
    }

    public String getSMobileno() {
        return sMobileno;
    }

    public void setSMobileno(String sMobileno) {
        this.sMobileno = sMobileno;
    }

    public String getRMobileno() {
        return rMobileno;
    }

    public void setRMobileno(String rMobileno) {
        this.rMobileno = rMobileno;
    }

    public String getSurCharges() {
        return surCharges;
    }

    public void setSurCharges(String surCharges) {
        this.surCharges = surCharges;
    }

    public String getWithPasschg() {
        return withPasschg;
    }

    public void setWithPasschg(String withPasschg) {
        this.withPasschg = withPasschg;
    }

    public String getStationaryChg() {
        return stationaryChg;
    }

    public void setStationaryChg(String stationaryChg) {
        this.stationaryChg = stationaryChg;
    }

    public String getArticleChg() {
        return articleChg;
    }

    public void setArticleChg(String articleChg) {
        this.articleChg = articleChg;
    }

    public String getCartageChg() {
        return cartageChg;
    }

    public void setCartageChg(String cartageChg) {
        this.cartageChg = cartageChg;
    }

    public String getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(String serviceTax) {
        this.serviceTax = serviceTax;
    }

    public String getTransportChg() {
        return transportChg;
    }

    public void setTransportChg(String transportChg) {
        this.transportChg = transportChg;
    }

    public String getFocRemarks() {
        return focRemarks;
    }

    public void setFocRemarks(String focRemarks) {
        this.focRemarks = focRemarks;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getAfterDiscount() {
        return afterDiscount;
    }

    public void setAfterDiscount(String afterDiscount) {
        this.afterDiscount = afterDiscount;
    }

    public String getForApproval() {
        return forApproval;
    }

    public void setForApproval(String forApproval) {
        this.forApproval = forApproval;
    }

    public String getBookSeriesId() {
        return bookSeriesId;
    }

    public void setBookSeriesId(String bookSeriesId) {
        this.bookSeriesId = bookSeriesId;
    }

    public String getPreparedby() {
        return preparedby;
    }

    public void setPreparedby(String preparedby) {
        this.preparedby = preparedby;
    }

    public String getHflid() {
        return hflid;
    }

    public void setHflid(String hflid) {
        this.hflid = hflid;
    }

    public String getHTolid() {
        return hTolid;
    }

    public void setHTolid(String hTolid) {
        this.hTolid = hTolid;
    }

    public String getLrDiscRemarks() {
        return lrDiscRemarks;
    }

    public void setLrDiscRemarks(String lrDiscRemarks) {
        this.lrDiscRemarks = lrDiscRemarks;
    }

    public String getBeforeStax() {
        return beforeStax;
    }

    public void setBeforeStax(String beforeStax) {
        this.beforeStax = beforeStax;
    }

    public String getStaxCriteria() {
        return staxCriteria;
    }

    public void setStaxCriteria(String staxCriteria) {
        this.staxCriteria = staxCriteria;
    }

    public String getOldLRno() {
        return oldLRno;
    }

    public void setOldLRno(String oldLRno) {
        this.oldLRno = oldLRno;
    }

    public String getOldLRAmount() {
        return oldLRAmount;
    }

    public void setOldLRAmount(String oldLRAmount) {
        this.oldLRAmount = oldLRAmount;
    }

    public String getLRPassStatus() {
        return lRPassStatus;
    }

    public void setLRPassStatus(String lRPassStatus) {
        this.lRPassStatus = lRPassStatus;
    }

    public String getLRPassCharges() {
        return lRPassCharges;
    }

    public void setLRPassCharges(String lRPassCharges) {
        this.lRPassCharges = lRPassCharges;
    }

    public String getBookingSource() {
        return bookingSource;
    }

    public void setBookingSource(String bookingSource) {
        this.bookingSource = bookingSource;
    }

    public String getVendorid() {
        return vendorid;
    }

    public void setVendorid(String vendorid) {
        this.vendorid = vendorid;
    }

    public String getVendorlrtype() {
        return vendorlrtype;
    }

    public void setVendorlrtype(String vendorlrtype) {
        this.vendorlrtype = vendorlrtype;
    }

    public String getVendorlrno() {
        return vendorlrno;
    }

    public void setVendorlrno(String vendorlrno) {
        this.vendorlrno = vendorlrno;
    }

    public String getVendorlramt() {
        return vendorlramt;
    }

    public void setVendorlramt(String vendorlramt) {
        this.vendorlramt = vendorlramt;
    }

    public String getVendorname() {
        return vendorname;
    }

    public void setVendorname(String vendorname) {
        this.vendorname = vendorname;
    }

    public String getVrfrmlocation() {
        return vrfrmlocation;
    }

    public void setVrfrmlocation(String vrfrmlocation) {
        this.vrfrmlocation = vrfrmlocation;
    }

    public String getVrtolocation() {
        return vrtolocation;
    }

    public void setVrtolocation(String vrtolocation) {
        this.vrtolocation = vrtolocation;
    }

    public String getTariffcriteria() {
        return tariffcriteria;
    }

    public void setTariffcriteria(String tariffcriteria) {
        this.tariffcriteria = tariffcriteria;
    }

    public String getValuesurchrgstatus() {
        return valuesurchrgstatus;
    }

    public void setValuesurchrgstatus(String valuesurchrgstatus) {
        this.valuesurchrgstatus = valuesurchrgstatus;
    }

    public String getValuesurcharge() {
        return valuesurcharge;
    }

    public void setValuesurcharge(String valuesurcharge) {
        this.valuesurcharge = valuesurcharge;
    }

    public String getConsignorGSTIN() {
        return consignorGSTIN;
    }

    public void setConsignorGSTIN(String consignorGSTIN) {
        this.consignorGSTIN = consignorGSTIN;
    }

    public String getConsigneeGSTIN() {
        return consigneeGSTIN;
    }

    public void setConsigneeGSTIN(String consigneeGSTIN) {
        this.consigneeGSTIN = consigneeGSTIN;
    }

    public String getPodcharges() {
        return podcharges;
    }

    public void setPodcharges(String podcharges) {
        this.podcharges = podcharges;
    }

    public String getEwaybill() {
        return ewaybill;
    }

    public void setEwaybill(String ewaybill) {
        this.ewaybill = ewaybill;
    }

    public String getBarcodecriteria() {
        return barcodecriteria;
    }

    public void setBarcodecriteria(String barcodecriteria) {
        this.barcodecriteria = barcodecriteria;
    }

    public String getCgstper() {
        return cgstper;
    }

    public void setCgstper(String cgstper) {
        this.cgstper = cgstper;
    }

    public String getCgstamt() {
        return cgstamt;
    }

    public void setCgstamt(String cgstamt) {
        this.cgstamt = cgstamt;
    }

    public String getIgstper() {
        return igstper;
    }

    public void setIgstper(String igstper) {
        this.igstper = igstper;
    }

    public String getIgstamt() {
        return igstamt;
    }

    public void setIgstamt(String igstamt) {
        this.igstamt = igstamt;
    }

    public String getOldsubtotal() {
        return oldsubtotal;
    }

    public void setOldsubtotal(String oldsubtotal) {
        this.oldsubtotal = oldsubtotal;
    }

    public String getLrentrytype() {
        return lrentrytype;
    }

    public void setLrentrytype(String lrentrytype) {
        this.lrentrytype = lrentrytype;
    }


    public String getDispatch() {
        return Dispatch;
    }

    public void setDispatch(String dispatch) {
        Dispatch = dispatch;
    }
}
