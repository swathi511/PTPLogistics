package com.hjsoftware.ptplogistics.Webservices;

import com.google.gson.JsonObject;
import com.hjsoftware.ptplogistics.model.ApplicableTariffPojo;
import com.hjsoftware.ptplogistics.model.ArticleDetailsPojo;
import com.hjsoftware.ptplogistics.model.CalAmountPojo;
import com.hjsoftware.ptplogistics.model.GetBookingPojo;
import com.hjsoftware.ptplogistics.model.LRDetailsPojo;
import com.hjsoftware.ptplogistics.model.LRMasterPojo;
import com.hjsoftware.ptplogistics.model.LRPodUpdatePojo;
import com.hjsoftware.ptplogistics.model.LRTrackingPojo;
import com.hjsoftware.ptplogistics.model.ManualSeriesPojo;
import com.hjsoftware.ptplogistics.model.OtherSenderPojo;
import com.hjsoftware.ptplogistics.model.Pojo;
import com.hjsoftware.ptplogistics.model.SenderAddPojo;
import com.hjsoftware.ptplogistics.model.TarifCriteriaPojo;
import com.hjsoftware.ptplogistics.model.locPojo;
import com.hjsoftware.ptplogistics.model.newLrpojo;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @POST("Login/CheckLogin")
    Call<Pojo> sendLoginDetails(@Body JsonObject v);

    @POST("ChangePassword/Update")
    Call<Pojo> changePassword(@Body JsonObject v);

    @POST ("Logout/Update")
    Call<Pojo> logout(@Body JsonObject v);

    @GET("UserDetails/GetDetails?")
    Call<List<newLrpojo>> getUserDetails(@Query("ProfileId") String pid,
                                         @Query("version") String version);

    @GET("Locations/GetLocations")
    Call<List<locPojo>> getlocinfo();



    @GET("Senders/GetList?")
    Call<List<TarifCriteriaPojo>> getSenderList(
            @Query("TariffCriteria") String tarifcriteria,
            @Query("FromlocId") String flocid,
            @Query("TolocId") String tolocid);

    @GET("CustomerDet/GetDetails?")
    Call<List<SenderAddPojo>> getsenderAddress(
            @Query("CustomerId") String cid,
            @Query("TariffCriteria") String tcri);


    @GET("Articles/GetList?")
    Call<List<ArticleDetailsPojo>> getarticleDetails(@Query("CustomerId") String cid);

    @POST("LRNo/Generate")
    Call<Pojo> getLrNo(@Body JsonObject v);

    @GET("Tariff/GetList") //not used in app
    Call<List<ApplicableTariffPojo>> getApplicableTariff(@Query("TariffCriteria") String tC,
                                                         @Query("customerid") String cId);


    @GET("OtherCustomerCharges/GetCharges?")
    Call<List<OtherSenderPojo>> getOtherSender(@Query("FromlocId") String frmLocId);


    @POST ("ServiceTax/CalTax")
    Call<Pojo> serviceTax(@Body JsonObject v);


    @POST ("BranchHubStatus/Status")
    Call<Pojo> hubStatus(@Body JsonObject v);

    @POST ("LRCalc/CalcAmt")
    Call<CalAmountPojo> calAmount(@Body JsonObject v);

    @POST ("LRSave/Create")
    Call<Pojo> LRSave(@Body JsonObject v);


    @POST ("LRDetailsSave/Create")
    Call<Pojo> LRDetailsSave(@Body JsonObject v);


    @GET("Crno/GetMaxCrno?")
    Call<String> getCrNo(
            @Query("BranchID") String stBranchId,
            @Query("CrDate") String fromdate);

    @POST("LRTracking/Update")
    Call<Pojo> LRTracking(@Body JsonObject v);

    @GET("LRTracking/GetDetails")
    Call<List<LRTrackingPojo>> showLRTracking(@Query("Lrno") String LRNo);

    /* http://192.168.1.7:3434/api/PODCheck/GetDetails?Lrno=100000072&ProfileId=UP013*/

    @GET("PODCheck/GetDetails")
    Call<List<LRPodUpdatePojo>> updatePODStatus(@Query("Lrno") String Lrno,
                                          @Query("ProfileId") String profileId);

    @POST("BranchPOD/Update")
    Call<List<String>> uploadData(@Query("Lrno") String lrno,
                          @Query("ProfileId") String profileId,
                          @Query("PodDate") String podDate,
                          @Body RequestBody b);

    @GET("LRSeries/GetSeries")
    Call<List<ManualSeriesPojo>> getManualSeries(@Query("profileid") String profileId);

    @POST("LRNoByLrSeries/MaxLRno")
    Call<Pojo> getMSLRNo(@Body JsonObject v);

    @GET("LRDetails/GetDetails")
    Call<List<LRDetailsPojo>> getArticles(@Query("Lrno") String lrno);

    @GET("LRMaster/GetDetails")
    Call<List<LRMasterPojo>> getLRData(@Query("Lrno") String lrno);

    @GET("LRByDate/GetDetails")
    Call<List<GetBookingPojo>> getLRBookingList(@Query("fromdate") String fromdate,
                                                @Query("todate") String todate,
                                                @Query("branchname") String bname);




}