package com.hjsoftware.ptplogistics.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hjsoftware.ptplogistics.Activity.HomeActivity;

import com.hjsoftware.ptplogistics.Adapters.SessionManager;
import com.hjsoftware.ptplogistics.R;
import com.hjsoftware.ptplogistics.Webservices.API;
import com.hjsoftware.ptplogistics.Webservices.RestClient;
import com.hjsoftware.ptplogistics.model.OtherSenderPojo;
import com.hjsoftware.ptplogistics.model.Pojo;
import com.hjsoftware.ptplogistics.model.SenderAddPojo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewLrBookingFourFragment extends Fragment {

    View v;
    EditText et_old_bFright,et_new_bFright,et_aCharges,et_valCharges,et_newDD,et_oldDD;
    EditText et_Dc,et_hamaliCg,et_Cc,et_LrCg,et_Pod,etGoodsValue;
    EditText et_BeGst,et_withpassch,et_SGstone,et_SGsttwo,et_grndTotal,et_surcharge,etCGstOne,etCGstTwo,etIGstOne,etIGstTwo;
    Spinner sp_LrPass,spVSStatus;
    TextView tv_Cal,tv_save,tv_cancel,tv_home;

    SharedPreferences pref;
    String stLrPass="";
    API REST_CLIENT;
    RecyclerView rView;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "SharedPref";
    SessionManager session;
    ArrayList<String> articlelist = new ArrayList<String>();
    HashMap<String, String> user;
    String stProfileid,stFromLocId,stotherSender,yes;
    String stCustomerId,stTariffCri,stlrDate,stTolocId,stLrType;
    String stTotalAmount="0",stBefGSTAmnt="0",stDCCharg,stCCCharg,stbFright,stFrightAfter,stAcharges,stValCharges="0",stNewDD,stHamaliCh,stOldDD,
            stLrCh,stPODCh,stSurch="0",stWithPassCh="0",stSGSTOne,stSGSTtwo,stCGSTOne,stCGSTtwo,stIGSTOne,stIGSTtwo;
    String [] lrWithPass ={"Not Applicable","Applicable"};
    String [] valueSurchareStatus ={"Not Applicable","Applicable"};



    Integer value=750;
    Integer Amount=0;

    String totalQty,totalWeight;
    double bf=0.0,ac=0.0,vs=0.0,ndd=0.0,dc=0.0,hc=0.0,cc=0.0,lrch=0.0,podch=0.0,sc=0.0,wpc=0.0,sgst=0.0,cgstnt=0.0,igstnt=0.0;
    String cnorGST="",cneeGST="";
    String stax="0",cgst="0",igst="0",servicetax="",stGrandTotal="0",stGoodsValue,stLrNo,stLrNoOne,stLrNoTwo;

    DatePickerDialog datePickerDialog;
    String fromdate;
    String formattedDate;
    String fromLoc,toLoc,lrDate,stArticleCh,stPod;
    String plrValue="",vsStatus="";
    ProgressDialog progressDialog;
    String rate,extraRate,stLrCriteria;

    //TODO: New DD Charges and Old DD Charges logic implementation & Old Basic and New Basic calc

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.new_lrbooking_fourth_other, container, false);


        et_old_bFright = (EditText) v.findViewById(R.id.new_lrbo_fourth_et_basic_frieght);
        et_new_bFright = (EditText) v.findViewById(R.id.new_lrbo_fourth_et_frigt_aftr_disc);
        et_aCharges = (EditText) v.findViewById(R.id.new_lrbo_fourth_et_art_charges);
        et_valCharges = (EditText) v.findViewById(R.id.new_lrbo_fourth_et_valsur_charges);
        et_newDD = (EditText) v.findViewById(R.id.new_lrbo_fourth_et_ndd_charges);
        et_Dc = (EditText) v.findViewById(R.id.new_lrbo_fourth_et_dc_charges);
        et_hamaliCg = (EditText) v.findViewById(R.id.new_lrbo_fourth_et_ham_charges);
        et_Cc = (EditText) v.findViewById(R.id.new_lrbo_fourth_et_cc_charges);
        et_oldDD = (EditText) v.findViewById(R.id.new_lrbo_fourth_et_odd_charges);
        et_LrCg = (EditText) v.findViewById(R.id.new_lrbo_fourth_et_lr_charges);
        et_Pod = (EditText) v.findViewById(R.id.new_lrbo_fourth_et_pod_charges);
        et_BeGst = (EditText) v.findViewById(R.id.new_lrbo_fourth_et_gst);
        et_withpassch = (EditText) v.findViewById(R.id.new_lrbo_fourth_et_with_pass_charges);
        et_SGstone = (EditText) v.findViewById(R.id.new_lrbo_fourth_et_stax_one);
        et_SGsttwo = (EditText) v.findViewById(R.id.new_lrbo_fourth_et_stax_two);
        et_grndTotal = (EditText) v.findViewById(R.id.new_lrbo_fourth_et_grand_total);
        et_surcharge = (EditText) v.findViewById(R.id.new_lrbo_fourth_et_sur_charges);
        sp_LrPass = (Spinner) v.findViewById(R.id.new_lrbo_fourth_sp_lr_with_pass);
        tv_Cal = (TextView) v.findViewById(R.id.new_lrbo_fourth_tv_cal);
        //tv_cancel = (TextView) v.findViewById(R.id.new_lrb_fourth_tv_cancel);
        tv_save = (TextView) v.findViewById(R.id.new_lrbo_fourth_tv_save);
        tv_home = (TextView) v.findViewById(R.id.new_lrbo_fourth_tv_home);
        tv_home.setVisibility(View.GONE);
        spVSStatus=(Spinner)v.findViewById(R.id.new_lrbo_fourth_sp_vs_status);
        etGoodsValue=(EditText)v.findViewById(R.id.new_lrbo_fourth_tv_goods_value);

        etCGstOne=(EditText) v.findViewById(R.id.new_lrbo_fourth_et_ctax_one);
        etCGstTwo=(EditText) v.findViewById(R.id.new_lrbo_fourth_et_ctax_two);
        etIGstOne=(EditText) v.findViewById(R.id.new_lrbo_fourth_et_itax_one);
        etIGstTwo=(EditText) v.findViewById(R.id.new_lrbo_fourth_et_itax_two);


        REST_CLIENT = RestClient.get();
        pref = getActivity().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        session = new SessionManager(getContext());
        user = session.getUserDetails();

        stProfileid = user.get(SessionManager.KEY_PROFILE_ID);
        stFromLocId = pref.getString("fromLocId", null);
        stCustomerId = pref.getString("CustomerId", null);
        stlrDate = pref.getString("lrDate", null);
        stTariffCri = pref.getString("tariffCriteria", null);
        stTolocId = pref.getString("toLocId", null);
        stTotalAmount = pref.getString("totalamt", null);
        totalQty=pref.getString("totalQty","0");
        totalWeight=pref.getString("totalWt","0");
        stGoodsValue=pref.getString("goodsValue",null);
        stLrNoOne=pref.getString("lrNoOne",null);
        stLrNoTwo=pref.getString("lrNotwo",null);
        fromLoc=pref.getString("fromLoc",null);
        toLoc=pref.getString("destination",null);
        lrDate=pref.getString("lrDate",null);
        stArticleCh=pref.getString("senderartiCh",null);
        stPod=pref.getString("pod",null);
        cnorGST=pref.getString("senderGstin","");
        cneeGST=pref.getString("rGStin","");
        stLrType=pref.getString("lrtype","");
        stLrCriteria=pref.getString("lrCriteria","");

        rate=pref.getString("rate","0");
        extraRate=pref.getString("extraRate","0");

        System.out.println("r & er & ta.."+rate+"::"+extraRate+"::"+stTotalAmount);

        System.out.println("custid..."+stCustomerId);
        System.out.println("custname..."+pref.getString("sendername",""));

        bf=Double.parseDouble(stTotalAmount);

        System.out.println("lrrrrrrr"+stLrCriteria);

        editor.putString("lrCharges","0");
        editor.commit();

        if(stLrCriteria.equals("Automatic")) {
            stLrNo = stLrNoOne + "/" + stLrNoTwo;
        }
        else {
            stLrNo=stLrNoOne;
        }

        progressDialog= new ProgressDialog(v.getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait ...");

        serviceTaxData();

        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);

        //  System.out.println("Current time => "+c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        formattedDate = df.format(c.getTime());

        System.out.println("@@@@@%%%%%%currentTimeString"+" "+formattedDate);

        fromdate=(mMonth+1)+"/"+mDay+"/"+mYear;

        datePickerDialog = new DatePickerDialog(getContext(),

                new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int dayOfMonth,
                                          int monthOfYear, int year) {


                        fromdate=(monthOfYear+1)+"/"+dayOfMonth+"/"+year;


                    }
                }, mMonth, mDay, mYear);
        // datePickerDialog.show();
        System.out.println("fromdateeeeeeeeeeeeeeeee"+fromdate);

        etGoodsValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor.putString("goodsValue",etGoodsValue.getText().toString().trim());
                editor.commit();
            }
        });

        final ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, lrWithPass);

        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_LrPass.setAdapter(dataAdapter1);
        sp_LrPass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                //stLrPass=sp_LrPass.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        tv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l=new Intent(getActivity(),HomeActivity.class);
                startActivity(l);
                getActivity().finish();

            }
        });

        tv_Cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog= new ProgressDialog(v.getContext());
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Please wait ...");

                loadDataForSender();

                /*stbFright=et_bFright.getText().toString().trim();
                stFrightAfter=et_frightAfter.getText().toString().trim();
                stAcharges=et_aCharges.getText().toString().trim();
                stValCharges=et_valCharges.getText().toString().trim();
                stNewDD=et_newDD.getText().toString().trim();
                stDCCharg=et_Dc.getText().toString().trim();
                stHamaliCh=et_hamaliCg.getText().toString().trim();
                stCCCharg=et_Cc.getText().toString().trim();
                stOldDD=et_oldDD.getText().toString().trim();
                stLrCh=et_LrCg.getText().toString().trim();
                stPODCh=et_Pod.getText().toString().trim();
                stSurch=et_surcharge.getText().toString().trim();
                stWithPassCh=et_withpassch.getText().toString().trim();
                stBefGSTAmnt=et_BeGst.getText().toString().trim();
                stSGSTtwo=et_SGsttwo.getText().toString().trim();
                stCGSTtwo=etCGstTwo.getText().toString().trim();
                stIGSTtwo=etIGstTwo.getText().toString().trim();

                et_BeGst.setText(String.valueOf(
                        Integer.parseInt(stbFright) +
                                Integer.parseInt(stFrightAfter) +
                                Integer.parseInt(stAcharges) +
                                Integer.parseInt(stValCharges)+
                                Integer.parseInt(stNewDD)+
                                Integer.parseInt(stDCCharg)+
                                Integer.parseInt(stHamaliCh)+
                                Integer.parseInt(stCCCharg)+
                                Integer.parseInt(stOldDD)+
                                Integer.parseInt(stLrCh)+
                                Integer.parseInt(stPODCh)+
                                Integer.parseInt(stSurch)+
                                Integer.parseInt(stWithPassCh)
                               *//* Integer.parseInt(stBefGSTAmnt)+
                                Integer.parseInt(stSGSTtwo)+
                                Integer.parseInt(stCGSTtwo)+
                                Integer.parseInt(stIGSTtwo)*//*
                ));

                stBefGSTAmnt=et_BeGst.getText().toString().trim();

                et_grndTotal.setText(String.valueOf(
                        Integer.parseInt(stBefGSTAmnt)+
                                Integer.parseInt(stSGSTtwo)+
                                Integer.parseInt(stCGSTtwo)+
                                Integer.parseInt(stIGSTtwo)
                ));

                stTotalAmount=et_grndTotal.getText().toString().trim();*/
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveLRBookingDetails();
            }
        });



        return v;
    }


    public  void serviceTaxData(){

        System.out.println("service tax detials::"+stProfileid+"::"+stFromLocId+"::"+stTolocId+"::"+stCustomerId+"::"+stlrDate);
        JsonObject v=new JsonObject();
        v.addProperty("ProfileID",stProfileid);
        v.addProperty("FromLocId",stFromLocId);
        v.addProperty("ToLocId",stTolocId);
        v.addProperty("CustomerId",stCustomerId);
        v.addProperty("LRDate",stlrDate);

        Call<Pojo>call=REST_CLIENT.serviceTax(v);
        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                Pojo taxData;
                if(response.isSuccessful()){
                    taxData=response.body();
                    String s=taxData.getMessage();

                    System.out.println("Service tax response iss"+s);

                    String sgst=s.split(",")[0];
                    String cgst=s.split(",")[1];
                    String igst=s.split(",")[2];
                    String flag=s.split(",")[3];

                    System.out.println("Service tax new response iss"+sgst+"::"+cgst+"::"+igst+"::"+flag);




                    et_SGstone.setText(sgst);
                    etCGstOne.setText(cgst);
                    etIGstOne.setText(igst);

                    editor.putString("sgst",sgst);
                    editor.putString("cgst",cgst);
                    editor.putString("igst",igst);
                    editor.putString("flag",flag);
                    editor.commit();


                    loadDataForSender();

                }
                else {

                    loadDataForSender();
                }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {

            }
        });
    }
    public void TBBCustomer()
    {
        System.out.println("TBBCust.."+stCustomerId+":::::"+stTariffCri.substring(0, 1));

        Call<List<SenderAddPojo>> call1 = REST_CLIENT.getsenderAddress(stCustomerId, stTariffCri.substring(0, 1));
        call1.enqueue(new Callback<List<SenderAddPojo>>() {
            @Override
            public void onResponse(Call<List<SenderAddPojo>> call, Response<List<SenderAddPojo>> response) {
                List<SenderAddPojo> senderStatus;
                SenderAddPojo senderData;
                senderStatus = response.body();

                if (response.isSuccessful()) {
                    for (int i = 0; i < senderStatus.size(); i++) {

                        senderData = senderStatus.get(i);
                        String hamalich;
                        String surcharges;
                        String articlech;
                        String pod = senderData.getPodcharges();
                        String valueSurcg = senderData.getValuesurcharge();
                        String lrCharges;
                        String withPassChg=senderData.getWithpassstatus();
                        servicetax=senderData.getServicetax();
                        plrValue=senderData.getPlrvalue();

                        String artChgStatus,lrChgStatus,hamaliChgStatus,surChargeStatus;

                        et_Pod.setText(pod);
                        podch=Double.parseDouble(et_Pod.getText().toString().trim());
                        et_valCharges.setText(valueSurcg);
                        stValCharges=et_valCharges.getText().toString().trim();
                        vs=Double.parseDouble(et_valCharges.getText().toString().trim());

                        final ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, valueSurchareStatus);

                        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spVSStatus.setAdapter(dataAdapter1);

                        vsStatus=senderData.getValuesurchargestatus();

                        if(senderData.getValuesurchargestatus().equals("Applicable"))
                        {
                            spVSStatus.setSelection(1);
                            spVSStatus.setEnabled(false);
                            spVSStatus.setClickable(false);
                        }
                        else {
                            spVSStatus.setSelection(0);
                            spVSStatus.setEnabled(false);
                            spVSStatus.setClickable(false);
                        }

                        System.out.println("TARIFFCRIT..."+stTariffCri);

                        if(stTariffCri.equals("Regular Based")) {

                            lrCharges=senderData.getLrcharges();

                            System.out.println("lrch...."+lrCharges);
                            et_LrCg.setText(lrCharges);
                            lrch=Double.parseDouble(lrCharges);

                            articlech=senderData.getArticlecharges();
                            //et_aCharges.setText(articlech);

                            hamalich = senderData.getHmchrgsregular();
                            //et_hamaliCg.setText(hamalich);

                            surcharges = senderData.getSurchargesregular();
                            //et_surcharge.setText(surcharges);

                            artChgStatus=senderData.getAriclechg();
                            lrChgStatus=senderData.getLrchg();
                            hamaliChgStatus=senderData.getHmlcriteriaregular();
                            surChargeStatus=senderData.getSurchargestatusreg();

                            editor.putString("lrCharges",lrCharges);
                            editor.putString("lrStatus",lrChgStatus);
                            editor.commit();


                        }
                        else {

                            lrCharges=senderData.getLrchargesart();
                            et_LrCg.setText(lrCharges);
                            lrch=Double.parseDouble(lrCharges);

                            articlech=senderData.getArticlechargesart();
                            //et_aCharges.setText(articlech);

                            hamalich = senderData.getRate();
                            //et_hamaliCg.setText(hamalich);

                            surcharges = senderData.getSurcharges();
                            //et_surcharge.setText(surcharges);

                            artChgStatus=senderData.getArticlechrgsstatusart();
                            lrChgStatus=senderData.getLrchargesstatusart();
                            hamaliChgStatus=senderData.getHamalicharges();
                            surChargeStatus=senderData.getSurchargestatusart();

                            editor.putString("lrCharges",lrCharges);
                            editor.putString("lrStatus",lrChgStatus);
                            editor.commit();

                        }

                        if(senderData.getValuesurchargestatus().equals("Applicable"))
                        {
                            //Goods value==0 or " " then msg:enter goods value
                            if(stGoodsValue.equals("0")||stGoodsValue.equals(""))
                            {
                                Toast.makeText(getActivity(),"Please enter Goods value!",Toast.LENGTH_SHORT).show();
                            }
                        }

                        //Article Charge

                        if(artChgStatus.equals("Applicable"))
                        {
                            et_aCharges.setText(String.valueOf(
                                    Integer.parseInt(articlech)*Integer.parseInt(totalQty)));
                            ac=Double.parseDouble(et_aCharges.getText().toString().trim());
                        }
                        else {
                            et_aCharges.setText("0");
                            ac=0;
                        }

                        //LR Charge

                        if(lrChgStatus.equals("Applicable"))
                        {

                        }
                        else {
                            et_LrCg.setText("0");
                            lrch=0;
                        }

                        //Hamali Charge

                        if(hamaliChgStatus.equals("Applicable"))
                        {
                            et_hamaliCg.setText(String.valueOf(
                                    Integer.parseInt(hamalich)*Integer.parseInt(totalQty)));
                            hc=Double.parseDouble(et_hamaliCg.getText().toString().trim());
                        }
                        else {
                            et_hamaliCg.setText("0");
                            hc=0;
                        }

                        //Surcharge

                        if(surChargeStatus.equals("Applicable"))
                        {
                            if(stTariffCri.equals("Article Based"))
                            {
                                if(stLrType.equals("ON A/C"))
                                {
                                    et_surcharge.setText(String.valueOf(
                                            Integer.parseInt(surcharges)*Integer.parseInt(totalQty)
                                    ));
                                    stSurch=et_surcharge.getText().toString().trim();
                                    sc=Double.parseDouble(et_surcharge.getText().toString().trim());
                                }
                                else {
                                    et_surcharge.setText("0");
                                    stSurch="0";
                                    sc=0;
                                }
                            }
                            else {
                                et_surcharge.setText(String.valueOf(
                                        (Integer.parseInt(surcharges)*Integer.parseInt(stTotalAmount))/100
                                ));
                                stSurch=et_surcharge.getText().toString().trim();
                                sc=Double.parseDouble(et_surcharge.getText().toString().trim());

                            }
                        }
                        else {
                            et_surcharge.setText("0");
                            stSurch="0";
                            sc=0;
                        }



                        //Door Delivery Charges Remaining  -- Logic to be checked

                        if(senderData.getDoordelvstatus().equals("Applicable"))
                        {
                            et_newDD.setText(String.valueOf(
                                    Integer.parseInt(senderData.getDoordelvcharges())*Integer.parseInt(totalQty)
                            ));

                            ndd=Double.parseDouble(et_newDD.getText().toString().trim());


                        }
                        else {

                            et_newDD.setText("0");
                            ndd=0;
                        }

                        if(stTariffCri.equals("Regular Based")) {
                            if (senderData.getWithpassstatus().equals("Applicable"))
                            {
                                stWithPassCh="Applicable";
                                sp_LrPass.setSelection(1);
                                sp_LrPass.setEnabled(false);
                                sp_LrPass.setClickable(false);
                            }
                            else {
                                stWithPassCh="Not Applicable";
                                sp_LrPass.setSelection(0);
                                sp_LrPass.setEnabled(false);
                                sp_LrPass.setClickable(false);

                            }
                        }
                        else {
                            if (senderData.getWithpassstatusart().equals("Applicable"))
                            {
                                stWithPassCh="Applicable";
                                sp_LrPass.setSelection(1);
                                sp_LrPass.setEnabled(false);
                                sp_LrPass.setClickable(false);
                            }
                            else {
                                stWithPassCh="Not Applicable";
                                sp_LrPass.setSelection(0);
                                sp_LrPass.setEnabled(false);
                                sp_LrPass.setClickable(false);

                            }

                        }














                        /*et_BeGst.setText(String.valueOf(
                                Integer.parseInt(stTotalAmount) +
                                        Integer.parseInt(hamalich) +
                                        Integer.parseInt(surcharges) +
                                        Integer.parseInt(articlech) +
                                        Integer.parseInt(pod)
                        ));*/

                        et_BeGst.setText(String.valueOf(bf+ac+vs+ndd+dc+hc+cc+lrch+podch+sc+wpc));

                        stBefGSTAmnt = et_BeGst.getText().toString().trim();

                        calculateTax(Double.parseDouble(stBefGSTAmnt));

                        /*if (senderData.getServicetax().equals("Applicable")) {

                            JsonObject v = new JsonObject();
                            v.addProperty("ProfileID", stProfileid);
                            v.addProperty("FromLocId", stFromLocId);
                            v.addProperty("ToLocId", stTolocId);
                            v.addProperty("CustomerId", stCustomerId);
                            v.addProperty("LRDate", stlrDate);

                            Call<Pojo> call2 = REST_CLIENT.serviceTax(v);
                            call2.enqueue(new Callback<Pojo>() {
                                @Override
                                public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                                    Pojo serData;
                                    serData = response.body();
                                    if (response.isSuccessful()) {

                                        String stServiceTax = serData.getMessage();
                                        //et_BeGst.setText(stServi i9
                                        // ceTax);

                                        et_SGstone.setText(stServiceTax);
                                        et_SGsttwo.setText(String.valueOf(
                                                (Double) (Double.parseDouble(stServiceTax) % Double.parseDouble(stBefGSTAmnt)
                                                )));
                                    }
                                }

                                @Override
                                public void onFailure(Call<Pojo> call, Throwable t) {

                                }
                            });

                        }*/
                    }
                }
                else



                {

                    System.out.println("TBB err.."+response.message());
                }
            }

            @Override
            public void onFailure(Call<List<SenderAddPojo>> call, Throwable t) {

            }
        });
    }

    public void OtherSenderCustomer()
    {

        System.out.println("other sender rrrrrr..........."+stFromLocId);
        Call<List<OtherSenderPojo>> call = REST_CLIENT.getOtherSender(stFromLocId);
        call.enqueue(new Callback<List<OtherSenderPojo>>() {
            @Override
            public void onResponse(Call<List<OtherSenderPojo>> call, Response<List<OtherSenderPojo>> response) {
                List<OtherSenderPojo> otherSenderStatus;
                OtherSenderPojo otherSenderData;

                otherSenderStatus = response.body();
                if (response.isSuccessful()) {


                    for (int i = 0; i < otherSenderStatus.size(); i++) {

                        otherSenderData = otherSenderStatus.get(i);

                        String oSurcharge = otherSenderData.getSurcharges();
                        String olramt = otherSenderData.getLramount();
                        String olrCh = otherSenderData.getLrCharges();

                        System.out.println("lrchHHHH.."+olrCh);

                        //editor.putString("lrCharges",olrCh);
                        //editor.commit();


                        String oArtch = otherSenderData.getArticleCharges();
                        String ohamaliCh = otherSenderData.getHamalicharges();
                        String ohamaliCr = otherSenderData.getHamalichrcriteria();
                        String ominWt = otherSenderData.getMinimumweight();
                        String olrpassch = otherSenderData.getLrpasscharge();
                        String operkgs = otherSenderData.getPerkgs();

                        sp_LrPass.setSelection(1);
                        //et_LrCg.setText(olrpassch);



                        if(olrCh.equals("Applicable"))
                        {
                            et_LrCg.setText(olramt);
                            lrch=Double.parseDouble(olramt);
                        }
                        else {
                            et_LrCg.setText("0");
                            lrch=0;
                        }

                        if(oArtch.equals("Applicable"))
                        {
                            et_aCharges.setText(String.valueOf(
                                    Double.parseDouble(otherSenderData.getArticleamount())
                                            *Double.parseDouble(totalQty)
                            ));
                            ac=Double.parseDouble(et_aCharges.getText().toString().trim());
                        }
                        else {
                            et_aCharges.setText("0");
                            ac=0;
                        }

                        if(ohamaliCr.equals("Applicable"))
                        {
                            if(Double.parseDouble(totalWeight)>=100)
                            {
                                et_hamaliCg.setText(String.valueOf(
                                        Double.parseDouble(ohamaliCh)*
                                                (Double.parseDouble(totalWeight)/Integer.parseInt(operkgs))
                                ));
                                hc=Double.parseDouble(et_hamaliCg.getText().toString().trim());
                            }
                            else {
                                et_hamaliCg.setText("0");
                                hc=0;
                            }
                        }
                        else {
                            et_hamaliCg.setText("0");
                            hc=0;
                        }

                        if(oSurcharge!=""||oSurcharge.equals("0"))
                        {
                            if(stTariffCri.equals("Article Based"))
                            {
                                if(stLrType.equals("ON A/C"))
                                {
                                    et_surcharge.setText(String.valueOf(
                                            Double.parseDouble(totalQty)*
                                                    Double.parseDouble(oSurcharge)
                                    ));
                                    sc=Double.parseDouble(et_surcharge.getText().toString().trim());
                                }
                                else {

                                    et_surcharge.setText("0");
                                    sc=0;
                                }

                            }
                            else {
                                et_surcharge.setText(String.valueOf(
                                        (Double.parseDouble(stTotalAmount)*Integer.parseInt(oSurcharge)/100)
                                ));
                                sc=Double.parseDouble(et_surcharge.getText().toString().trim());

                            }
                        }
                        else {
                            et_surcharge.setText("0");
                            sc=0;
                        }

                        /*et_BeGst.setText(String.valueOf(
                                Integer.parseInt(stTotalAmount) +
                                        Integer.parseInt(ohamaliCh) +
                                        Integer.parseInt(oSurcharge) +
                                        Integer.parseInt(olramt)+
                                        Integer.parseInt(oArtch)
                        ));*/

                        et_BeGst.setText(String.valueOf(bf+ac+vs+ndd+dc+hc+cc+lrch+podch+sc+wpc));

                        if(olrpassch.equals("0"))
                        {
                            sp_LrPass.setSelection(0);
                        }
                        else {
                            sp_LrPass.setSelection(1);
                        }

                        stBefGSTAmnt = et_BeGst.getText().toString().trim();

                        calculateTax(Double.parseDouble(stBefGSTAmnt));


                        /*Amount = Integer.parseInt(otherSenderData.getArticleamount());
//if totalAmount > 750 ask for logic here
                        if (Amount > value) {

                            JsonObject v = new JsonObject();
                            v.addProperty("ProfileID", stProfileid);
                            v.addProperty("FromLocId", stFromLocId);
                            v.addProperty("ToLocId", stTolocId);
                            v.addProperty("CustomerId", stCustomerId);
                            v.addProperty("LRDate", stlrDate);

                            Call<Pojo> pojoCall = REST_CLIENT.serviceTax(v);
                            pojoCall.enqueue(new Callback<Pojo>() {
                                @Override
                                public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                                    Pojo serviceData;
                                    serviceData = response.body();
                                    if (response.isSuccessful()) {

                                        String serviceTax = serviceData.getMessage();

                                        et_SGstone.setText(serviceTax);
                                        et_SGsttwo.setText(String.valueOf(
                                                (Double) (Double.parseDouble(serviceTax) % Double.parseDouble(stBefGSTAmnt)
                                                )));


                                        //et_BeGst.setText(serviceTax);

                                    }
                                }

                                @Override
                                public void onFailure(Call<Pojo> call, Throwable t) {

                                }
                            });
                        }*/

                    }
                }
            }

            @Override
            public void onFailure(Call<List<OtherSenderPojo>> call, Throwable t) {


            }
        });
    }

    public void calculateTax(double beforeGSTAmount)
    {

        System.out.println("@@@@@@@@@@@@@beforegst"+beforeGSTAmount);
        System.out.println("^^^^^details"+beforeGSTAmount+"::"+cnorGST+"::"+cneeGST+"::"+pref.getString("otherSender", "no")+stTariffCri+stLrType+"::"+servicetax);
        if(beforeGSTAmount>750)
        {
            if(cnorGST.equals("")&&cneeGST.equals(""))
            {
                if(pref.getString("otherSender", "no").equals("no"))
                {
                    if(stTariffCri.equals("Article Based"))
                    {
                        if(stLrType.equals("ON A/C"))
                        {
                            if(servicetax.equals("Not Applicable"))
                            {
                                stax="0.00";
                                cgst="0.00";
                                igst="0.00";
                            }
                            else {


                                 /*stax=hstax.value;
                                 cgst=hcgst.value;
                                 igst=higst.value;*/

                                stax=pref.getString("sgst",null);
                                cgst=pref.getString("cgst",null);
                                igst=pref.getString("igst",null);


                            }
                        }
                        else {
                            stax="0.00";
                            cgst="0.00";
                            igst="0.00";
                        }
                    }
                    else {

                        if(servicetax.equals("Not Applicable"))
                        {
                            stax="0.00";
                            cgst="0.00";
                            igst="0.00";
                        }
                        else {
                                 /*stax=hstax.value;
                                 cgst=hcgst.value;
                                 igst=higst.value;*/

                            stax=pref.getString("sgst",null);
                            cgst=pref.getString("cgst",null);
                            igst=pref.getString("igst",null);

                            System.out.println("tAxxxx"+stax+"::"+cgst+"::"+igst);
                        }
                    }
                }
                else {

                    if(stTariffCri.equals("Article Based"))
                    {
                        stax="0.00";
                        cgst="0.00";
                        igst="0.00";
                    }
                    else {
                                 /*stax=hstax.value;
                                 cgst=hcgst.value;
                                 igst=higst.value;*/

                        stax=pref.getString("sgst",null);
                        cgst=pref.getString("cgst",null);
                        igst=pref.getString("igst",null);
                    }
                }
            }
            else {

                stax="0.00";
                cgst="0.00";
                igst="0.00";

            }
        }
        else {

            stax="0.00";
            cgst="0.00";
            igst="0.00";
        }

        et_SGstone.setText(stax);
        etCGstOne.setText(cgst);
        etIGstOne.setText(igst);

        System.out.println("SSSSTTTAXAXAX"+(Double.parseDouble(stax)*beforeGSTAmount)/100);

        if(!stax.equals("0.00"))
        {
            et_SGsttwo.setText(String.valueOf(
                    (Double.parseDouble(stax)*beforeGSTAmount)/100));
            sgst=  Double.parseDouble(et_SGsttwo.getText().toString().trim());
        }

        if(!cgst.equals("0.00"))
        {
            etCGstTwo.setText(String.valueOf(
                    (Double.parseDouble(cgst)*beforeGSTAmount)/100
            ));
            cgstnt=Double.parseDouble(etCGstTwo.getText().toString().trim());
        }

        if(!igst.equals("0.00"))
        {
            etIGstTwo.setText(String.valueOf(
                    (Double.parseDouble(igst)*beforeGSTAmount)/100
            ));
            igstnt=Double.parseDouble(etIGstTwo.getText().toString().trim());
        }

        stGrandTotal=String.valueOf(beforeGSTAmount+sgst+cgstnt+igstnt);
        et_grndTotal.setText(stGrandTotal);

        progressDialog.dismiss();

    }

    public void saveLRBookingDetails()
    {
        progressDialog= new ProgressDialog(v.getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait ...");

        progressDialog.show();

        tv_save.setVisibility(View.GONE);
        tv_Cal.setVisibility(View.GONE);

        System.out.println("%%%%%%%%%%%%subtotal"+(stGrandTotal));

        System.out.println("@@@@####subtotal"+Double.parseDouble(stGrandTotal));

        JsonObject v=new JsonObject();
        v.addProperty("FLAG","insrt");
        v.addProperty("NewEditFLAG","NEW");
        v.addProperty("LRNo",stLrNo);
        v.addProperty("LRDate",pref.getString("lrDate",null));
        v.addProperty("lrentrydate",fromdate);
        v.addProperty("LRType",pref.getString("lrtype",null));
        v.addProperty("SenderID",stCustomerId);
        v.addProperty("SenderName",pref.getString("sendername",""));
        v.addProperty("dptype",pref.getString("dispatchType",null));
        v.addProperty("dddltype",pref.getString("DoorDel",null));
        v.addProperty("SenderAddress",pref.getString("senderAdd",null));
        v.addProperty("SenderMobileno",pref.getString("sendermobile",null));
        v.addProperty("ReceiverName",pref.getString("recName",null));
        v.addProperty("ReceiverAddress",pref.getString("rAddress",null));
        v.addProperty("ReceiverMobileno",pref.getString("rMobile",null));
        v.addProperty("FromBranchID",pref.getString("branchId",null));
        v.addProperty("ToLocationID",pref.getString("toLocId",null));
        v.addProperty("FromLocationID",pref.getString("fromLocId",null));
        v.addProperty("ToBranchName",pref.getString("tobranchid",null));
        v.addProperty("tariff",pref.getString("tariff",null));
        v.addProperty("GoodsValue",Double.parseDouble(pref.getString("goodsvalue",null)));
        v.addProperty("SubTotal",Double.parseDouble(stTotalAmount));
        v.addProperty("Lrcharges",pref.getString("lrCharges","0"));
        v.addProperty("ddcharges",ndd);
        v.addProperty("hmalicharges",hc);
        v.addProperty("ccchrges",cc);
        v.addProperty("dccharges",dc);
        v.addProperty("Totamount",Double.parseDouble(stGrandTotal));
        //v.addProperty("frbranch",pref.getString("branchId",null));
        v.addProperty("frbranch",pref.getString("branchname",null));
        //v.addProperty("tobranch",pref.getString("tobranchid",null));
        v.addProperty("tobranch",pref.getString("tobranchname",null));
        v.addProperty("dispatch","N");
        v.addProperty("logid",stProfileid);
        v.addProperty("deltype","Normal");
        v.addProperty("calcret",pref.getString("tarifCriteria",null));
        v.addProperty("status","");
        v.addProperty("lrnarration","");
        v.addProperty("lrcdate",fromdate);
        v.addProperty("time",formattedDate);
        v.addProperty("cinvno",pref.getString("CustInvNo",""));
        v.addProperty("ssms",pref.getString("ssms","N"));
        v.addProperty("rsms",pref.getString("rsms","N"));
        v.addProperty("smobile",pref.getString("sendermobile",""));
        v.addProperty("rmobile",pref.getString("rMobile",""));
        /*
         * ("Booked " + ddlLRType.Text + " " + " consignment with LR No:" + LRNo + " from " + split1[0] + "-" + split1[1] + " to " + br22 + " " + "with freight Rs."
         * + txtGrandTot.Text + " " + "on date" + " " + tmpdate + " Track your LR at http://agent.ptplogistics.in/LrTracking.aspx?ID=" + LRNo);*/
        String bsms=("Booked " + pref.getString("lrtype",null) + " " + " consignment with LR No:" + stLrNo + " from " + stFromLocId + "-" + fromLoc + " to "
                + toLoc + " " + "with freight Rs." +Double.parseDouble(stGrandTotal)  + " " + "on date" + " " + lrDate + " Track your LR at http://agent.ptplogistics.in/LrTracking.aspx?ID=" + stLrNo);
        v.addProperty("bsms",bsms);
        v.addProperty("surcharges",Double.parseDouble(stSurch));//check
        v.addProperty("withpasschg",wpc);//check
        v.addProperty("stationarychg",0.0);
        v.addProperty("articlechg",ac);
        v.addProperty("cartagechg",0.0);//check
        v.addProperty("servicetax",stax);
        v.addProperty("staxamnt",sgst);
        v.addProperty("transpchg",0.0);
        v.addProperty("focrmks",pref.getString("focRemarks",""));//c
        v.addProperty("plrvalue",plrValue);
        v.addProperty("msgto","");
        v.addProperty("msgtype","Booking");
        v.addProperty("discount",0.0);
        v.addProperty("afterdiscount",0.0);//na
        //check
        v.addProperty("forapproval","Approved" );
        v.addProperty("bookingcommissionpercentage",0.0);//c
        v.addProperty("bookingcommissionamount",0.0);//c
        v.addProperty("bookseriesid","");//na
        v.addProperty("lrentrytype",pref.getString("lrCriteria","Automatic"));
        v.addProperty("discountremarks","-");
        v.addProperty("Oldlramount",0.0);//na
        v.addProperty("beforestaxamt",Double.parseDouble(stBefGSTAmnt));//c
        v.addProperty("LrPassStatus",stLrPass);//c
        v.addProperty("LrPassCharges",0.0);//c
        v.addProperty("Bookingsource","Company");
        v.addProperty("Vendor","-");//c o
        v.addProperty("VrLRType","-");//c o
        v.addProperty("VrLRNo","-");//c o
        v.addProperty("VrLRAmt",0.0);
        v.addProperty("VrFLoc","");//na
        v.addProperty("VrTLoc","");//na
        v.addProperty("TariffCriteria",pref.getString("tarifCriteria",""));
        v.addProperty("ValueSurcharge",Double.parseDouble(stValCharges));//c
        v.addProperty("AgentDDcharges",0.0);
        v.addProperty("Valuesurchargestat",vsStatus);//c
        v.addProperty("miscellaneouscomm",0.0);//c
        v.addProperty("TotalSubAmt",Double.parseDouble(stTotalAmount));
        v.addProperty("deliverycommission",0.0);//c
        v.addProperty("deliverycommissionamount",0.0);//c
        v.addProperty("CommOnAmt",0.0);//na
        v.addProperty("ConsignorGSTIN",pref.getString("senderGstin",""));
        v.addProperty("ConsigneeGSTIN",pref.getString("rGStin",""));
        v.addProperty("PodCharges",podch);
        v.addProperty("OldDDCharges",0.0);
        v.addProperty("EWayBill",pref.getString("ewaybill",""));
        v.addProperty("BarcodeCriteria",pref.getString("barcode",""));
        System.out.println("$$$$$$$$$$$"+" "+cgstnt+"^^^^^^^^^"+igstnt+"%%%%%%%%%%%"+Double.parseDouble(cgst)+"L"+Double.parseDouble(igst));
        v.addProperty("cgstPer",cgstnt);//c
        v.addProperty("cgstAmount",Double.parseDouble(cgst));//c
        v.addProperty("igstPer",igstnt);//c
        v.addProperty("igstAmount",Double.parseDouble(igst));
        v.addProperty("oldSubTotal",0.0);//c
        v.addProperty("Crid","-");//c
        v.addProperty("bookseriesid",pref.getString("bookseriesid","-"));

        System.out.println("FLAG"+"insrt");
        System.out.println("LRNo"+stLrNo);
        System.out.println("LRDate"+pref.getString("lrDate",null));
        System.out.println("lrentrydate"+fromdate);
        System.out.println("LRType"+pref.getString("lrtype",null));
        System.out.println("SenderID"+stCustomerId);
        System.out.println("SenderName"+pref.getString("sendername",""));
        System.out.println("dptype"+pref.getString("dispatchType",null));
        System.out.println("dddltype"+pref.getString("DoorDel",null));
        System.out.println("SenderAddress"+pref.getString("senderAdd",null));
        System.out.println("SenderMobileno"+ pref.getString("sendermobile",null));
        System.out.println("ReceiverName"+pref.getString("recName",null));
        System.out.println("ReceiverAddress"+ pref.getString("rAddress",null));
        System.out.println("ReceiverMobileno"+ pref.getString("rMobile",null));
        System.out.println("FromBranchID"+pref.getString("branchId",null));
        System.out.println("ToLocationID"+pref.getString("toLocId",null));
        System.out.println("FromLocationID"+pref.getString("fromLocId",null));
        System.out.println("ToBranchName"+pref.getString("tobranchid",null));
        System.out.println("tariff"+pref.getString("tariff",null));
        System.out.println("GoodsValue"+Double.parseDouble( pref.getString("goodsvalue",null)));
        System.out.println("SubTotal"+Double.parseDouble(stTotalAmount));
        System.out.println("Lrcharges"+pref.getString("lrCharges","0"));
        System.out.println("ddcharges"+ndd);
        System.out.println("hmalicharges"+hc);
        System.out.println("ccchrges"+cc);
        System.out.println("dccharges"+dc);
        System.out.println("Totamount"+Double.parseDouble(stGrandTotal));
        System.out.println("frbranch"+pref.getString("branchname",null));
        System.out.println("tobranch"+pref.getString("tobranchname",null));
        System.out.println("dispatch"+"N");
        System.out.println("logid"+stProfileid);
        System.out.println("deltype"+"Normal");
        System.out.println("calcret"+pref.getString("tarifCriteria",null));
        System.out.println("status"+"");
        System.out.println("lrnarration"+"");
        System.out.println("lrcdate"+fromdate);
        System.out.println("time"+formattedDate);
        System.out.println("cinvno"+pref.getString("CustInvNo",""));
        System.out.println("ssms"+pref.getString("ssms","N"));
        System.out.println("rsms"+pref.getString("rsms","N"));
        System.out.println("smobile"+pref.getString("sendermobile",""));
        System.out.println("rmobile"+pref.getString("rMobile",""));
        /*
         * ("Booked " + ddlLRType.Text + " " + " consignment with LR No:" + LRNo + " from " + split1[0] + "-" + split1[1] + " to " + br22 + " " + "with freight Rs."
         * + txtGrandTot.Text + " " + "on date" + " " + tmpdate + " Track your LR at http://agent.ptplogistics.in/LrTracking.aspx?ID=" + LRNo);*/
        String bsms1=("Booked " +  pref.getString("lrtype",null) + " " + " consignment with LR No:" + stLrNo + " from " + stFromLocId + "-" + fromLoc + " to "
                + toLoc + " " + "with freight Rs." +Double.parseDouble(stGrandTotal)  + " " + "on date" + " " + lrDate + " Track your LR at http://agent.ptplogistics.in/LrTracking.aspx?ID=" + stLrNo);
        System.out.println("bsms"+bsms1);
        System.out.println("surcharges"+Double.parseDouble(stSurch));//check
        System.out.println("withpasschg"+wpc);//check
        System.out.println("stationarychg"+0.0);
        System.out.println("articlechg"+ac);
        System.out.println("cartagechg"+0.0);//check
        System.out.println("servicetax"+stax);
        System.out.println("staxamnt"+sgst);
        System.out.println("transpchg"+0.0);
        System.out.println("focrmks"+pref.getString("focRemarks",""));//c
        System.out.println("plrvalue"+plrValue);
        System.out.println("msgto"+"");
        System.out.println("msgtype"+"Booking");
        System.out.println("discount"+0.0);
        System.out.println("afterdiscount"+0.0);//na
//check
        System.out.println("forapproval"+"Approved" );
        System.out.println("bookingcommissionpercentage"+0.0);//c
        System.out.println("bookingcommissionamount"+0.0);//c
        System.out.println("bookseriesid"+"");//na
        System.out.println("lrentrytype"+pref.getString("lrCriteria","Automatic"));
        System.out.println("discountremarks"+"-");
        System.out.println("Oldlramount"+0.0);//na
        System.out.println("beforestaxamt"+Double.parseDouble(stBefGSTAmnt));//c
        System.out.println("LrPassStatus"+stLrPass);//c
        System.out.println("LrPassCharges"+0.0);//c
        System.out.println("Bookingsource"+"Company");
        System.out.println("Vendor"+"-");//c o
        System.out.println("VrLRType"+"-");//c o
        System.out.println("VrLRNo"+"-");//c o
        System.out.println("VrLRAmt"+0.0);
        System.out.println("VrFLoc"+"");//na
        System.out.println("VrTLoc"+"");//na
        System.out.println("TariffCriteria"+ pref.getString("tarifCriteria",""));
        System.out.println("ValueSurcharge"+Double.parseDouble(stValCharges));//c
        System.out.println("AgentDDcharges"+0.0);
        System.out.println("Valuesurchargestat"+vsStatus);//c
        System.out.println("miscellaneouscomm"+0.0);//c
        System.out.println("TotalSubAmt"+Double.parseDouble(stTotalAmount));
        System.out.println("deliverycommission"+0.0);//c
        System.out.println("deliverycommissionamount"+0.0);//c
        System.out.println("CommOnAmt"+0.0);//na
        System.out.println("ConsignorGSTIN"+ pref.getString("senderGstin",""));
        System.out.println("ConsigneeGSTIN"+pref.getString("rGStin",""));
        System.out.println("PodCharges"+podch);
        System.out.println("OldDDCharges"+0.0);
        System.out.println("EWayBill"+pref.getString("ewaybill",""));
        System.out.println("BarcodeCriteria"+pref.getString("barcode",""));
        System.out.println("$$$$$$$$$$$"+" "+cgstnt+"^^^^^^^^^"+igstnt+"%%%%%%%%%%%"+Double.parseDouble(cgst)+"L"+Double.parseDouble(igst));
        System.out.println("cgstPer"+cgstnt);//c
        System.out.println("cgstAmount"+Double.parseDouble(cgst));//c
        System.out.println("igstPer"+igstnt);//c
        System.out.println("igstAmount"+Double.parseDouble(igst));
        System.out.println("oldSubTotal"+0.0);//c
        System.out.println("Crid"+"-");//c
        System.out.println("bookseriesid"+pref.getString("bookseriesid","-"));//c


        /*System.out.println("FLAG"+"new");
        System.out.println("LRNo"+stLrNo);
        System.out.println("LRDate"+pref.getString("lrDate",null));
        System.out.println("lrentrydate"+fromdate);
        System.out.println("LRType"+pref.getString("lrtype",null));
        System.out.println("SenderID"+pref.getString("senderId",null));
        System.out.println("SenderName"+pref.getString("senderName",null));
        System.out.println("dptype"+pref.getString("dispatchType",null));
        System.out.println("dddltype"+pref.getString("DoorDel",null));
        System.out.println("SenderAddress"+pref.getString("senderAdd",null));
        System.out.println("SenderMobileno"+pref.getString("sendermobile",null));
        System.out.println("ReceiverName"+pref.getString("recName",null));
        System.out.println("ReceiverAddress"+pref.getString("rAddress",null));
        System.out.println("ReceiverMobileno"+pref.getString("rMobile",null));
        System.out.println("FromBranchID"+pref.getString("branchId",null));
        System.out.println("ToLocationID"+pref.getString("toLocId",null));
        System.out.println("FromLocationID"+pref.getString("fromLocId",null));
        System.out.println("ToBranchName"+pref.getString("tobranchid",null));
        System.out.println("tariff"+pref.getString("tariff",null));
        System.out.println("GoodsValue"+Double.parseDouble(pref.getString("goodsvalue",null)));
        System.out.println("SubTotal"+Double.parseDouble(stTotalAmount));
        System.out.println("Lrcharges"+pref.getString("lrCharges",null));
        System.out.println("ddcharges"+ndd);
        System.out.println("hmalicharges"+hc);
        System.out.println("ccchrges"+cc);
        System.out.println("dccharges"+dc);
        System.out.println("Totamount"+Double.parseDouble(stGrandTotal));
        System.out.println("frbranch"+pref.getString("branchId",null));
        System.out.println("tobranch"+pref.getString("tobranchid",null));
        System.out.println("dispatch"+"N");
        System.out.println("logid"+stProfileid);
        System.out.println("deltype"+"Normal");
        System.out.println("calcret"+pref.getString("tarifCriteria",null));
        System.out.println("status"+"");
        System.out.println("lrnarration"+"");
        System.out.println("lrcdate"+fromdate);
        System.out.println("time"+formattedDate);
        System.out.println("cinvno"+pref.getString("CustInvNo",""));
        System.out.println("ssms"+pref.getString("ssms","N"));
        System.out.println("rsms"+pref.getString("rsms","N"));
        System.out.println("smobile"+pref.getString("sendermobile",""));
        System.out.println("rmobile"+pref.getString("rMobile",""));
        System.out.println("bsms"+bsms);
        System.out.println("surcharges"+Double.parseDouble(stSurch));
        System.out.println("withpasschg"+0.0);
        System.out.println("stationarychg"+0.0);
        System.out.println("articlechg"+stArticleCh);
        System.out.println("cartagechg"+0.0);
        System.out.println("servicetax"+servicetax);
        System.out.println("staxamnt"+sgst);
        System.out.println("transpchg"+0.0);
        System.out.println("focrmks"+pref.getString("focRemarks",""));
        System.out.println("plrvalue"+plrValue);
        System.out.println("msgto"+"");
        System.out.println("msgtype"+"Booking");
        System.out.println("discount"+0.0);
        System.out.println("afterdiscount"+0.0);
        System.out.println("forapproval"+"Approved");
        System.out.println("bookingcommissionpercentage"+0.0);
        System.out.println("bookingcommissionamount"+0.0);
        System.out.println("bookseriesid"+"");
        System.out.println("lrentrytype"+"Automatic");
        System.out.println("discountremarks"+"-");
        System.out.println("Oldlramount"+0.0);
        System.out.println("beforestaxamt"+Double.parseDouble(stBefGSTAmnt));
        System.out.println("LrPassStatus"+stLrPass);
        System.out.println("LrPassCharges"+0.0);
        System.out.println("Bookingsource"+"Company");
        System.out.println("Vendor"+"-");
        System.out.println("VrLRType"+"-");
        System.out.println("VrLRNo"+"-");
        System.out.println("VrLRAmt"+0.0);
        System.out.println("VrFLoc"+"");
        System.out.println("VrTLoc"+"");
        System.out.println("TariffCriteria"+pref.getString("tarifCriteria",""));
        System.out.println("ValueSurcharge"+Double.parseDouble(stValCharges));
        System.out.println("AgentDDcharges"+0.0);
        System.out.println("Valuesurchargestat"+vsStatus);
        System.out.println("miscellaneouscomm"+0.0);
        System.out.println("TotalSubAmt"+Double.parseDouble(stTotalAmount));
        System.out.println("deliverycommission"+0.0);
        System.out.println("deliverycommissionamount"+0.0);
        System.out.println("CommOnAmt"+0.0);
        System.out.println("ConsignorGSTIN"+pref.getString("senderGstin",""));
        System.out.println("ConsigneeGSTIN"+pref.getString("rGStin",""));
        System.out.println("PodCharges"+pref.getString("pod",""));
        System.out.println("OldDDCharges"+0.0);
        System.out.println("EWayBill"+pref.getString("ewaybill",""));
        System.out.println("BarcodeCriteria"+pref.getString("barcode",""));
        System.out.println("cgstPer"+cgstnt);
        System.out.println("cgstAmount"+Double.parseDouble(cgst));
        System.out.println("igstPer"+igstnt);
        System.out.println("igstAmount"+Double.parseDouble(igst));
        System.out.println("oldSubTotal"+0.0);
        System.out.println("Crid"+"-");*/

        Call<Pojo>call=REST_CLIENT.LRSave(v);
        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                Pojo saveData;
                if(response.isSuccessful()){

                    progressDialog.dismiss();
                    saveData=response.body();


                    //System.out.println("API response is "+saveData.getMessage());

                    LRDetailsSaveAPI();
                }
                else {
                    progressDialog.dismiss();
                    tv_save.setVisibility(View.VISIBLE);
                    tv_Cal.setVisibility(View.VISIBLE);
                    System.out.println("API  ERROR response is "+response.message());

                    Toast.makeText(getActivity(),"Error:"+response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {

                tv_save.setVisibility(View.VISIBLE);
                tv_Cal.setVisibility(View.VISIBLE);

                progressDialog.dismiss();

                Toast.makeText(getActivity(),"Please check Internet connection!",Toast.LENGTH_SHORT).show();


            }
        });



    }

    public void loadDataForSender()
    {
        if (pref.getString("lrtype", null).equals("FOC")) {
            et_old_bFright.setText("0");
            et_new_bFright.setText("0");
            et_aCharges.setText("0");
            et_valCharges.setText("0");
            et_newDD.setText("0");
            et_hamaliCg.setText("0");
            //et_oldDD.setText("0");
            et_LrCg.setText("0");
            et_Pod.setText("0");
            et_surcharge.setText("0");
            et_withpassch.setText("0");
            et_BeGst.setText("0");
            et_Dc.setText("0");
            et_Cc.setText("0");
            et_Dc.setEnabled(false);
            et_Dc.setClickable(false);
            et_Cc.setEnabled(false);
            et_Cc.setEnabled(false);

            et_SGstone.setText("0");
            et_SGsttwo.setText("0");
            etCGstOne.setText("0");
            etCGstTwo.setText("0");
            etIGstOne.setText("0");
            etIGstTwo.setText("0");
            et_grndTotal.setText("0");
            sp_LrPass.setSelection(0);
            sp_LrPass.setEnabled(false);
            sp_LrPass.setClickable(false);

            stBefGSTAmnt="0";
            stGrandTotal="0";
            stValCharges="0";
            stTotalAmount="0";
            stSurch="0";
            cgst="0";
            sgst=0;
            igst="0";
            stax="0";
            cgstnt=0;
            igstnt=0;



        }
        else {

            et_old_bFright.setText(stTotalAmount);
            et_new_bFright.setText(stTotalAmount);

            if(!et_Dc.getText().toString().equals("")){
                dc=Double.parseDouble(et_Dc.getText().toString().trim());

            }
            if(!et_Cc.getText().toString().equals("")){
                cc=Double.parseDouble(et_Cc.getText().toString().trim());

            }


            if (pref.getString("otherSender", "no").equals("yes")) {

                OtherSenderCustomer();

            } else {

                TBBCustomer();

            }
        }
    }

    public void LRDetailsSaveAPI()
    {
        String artOne=pref.getString("artOne","--select--");

        System.out.println("&&&&&&&&&&&& Article 1 data isssss "+artOne);

        if(artOne.equals("--select--"))
        {

        }
        else {

            callArticleOneData();

        }

        /*String artTwo=pref.getString("artTwo","--select--");

        if(artTwo.equals("--select--"))
        {

        }
        else {

            callArticleTwoData();

        }*/

       /* String artThree=pref.getString("artThree","--select--");

        if(artThree.equals("--select--"))
        {

        }
        else {

            callArticleThreeData();

        }
*/
    }

    public void callArticleOneData()
    {
        System.out.println("&&&&&&&&&&&&&&&7");
        System.out.println(stLrNo+":"+pref.getString("artOneId","")+"::"+stFromLocId+":"+stTariffCri+":"+pref.getString("actwtOne","")+":"+pref.getString("chargdwtOne","")+":"+pref.getString("qtyOne","")+":"+pref.getString("artDecOne","")+":"+pref.getString("barCOne",""));
        JsonObject v=new JsonObject();
        v.addProperty("srno","1");
        v.addProperty("LRNo" ,stLrNo);
        v.addProperty("articleId",pref.getString("artOneId",""));
        v.addProperty("fromlocid",stFromLocId);
        v.addProperty("calcriteria",stTariffCri);
        v.addProperty("units",0);
        v.addProperty("Length",0);
        v.addProperty("Width",0);
        v.addProperty("Height",0);
        v.addProperty("CFT",0);
        v.addProperty("ActWeight",pref.getString("actwtOne",""));
        v.addProperty("ChgWeight",pref.getString("chargdwtOne",""));
        v.addProperty("Qty",pref.getString("qtyOne",""));
        //String barCodes=pref.getString("barCOne","")+pref.getString("barCTwo","")+pref.getString("barCThree","");
        v.addProperty("Barcodes",pref.getString("barCOne",""));
        v.addProperty("rate",rate);
        v.addProperty("extrarate",extraRate);
        v.addProperty("amount",stTotalAmount);
        //String artDesc=pref.getString("artDecOne","")+pref.getString("artDecTwo","")+pref.getString("artDecThree","");
        v.addProperty("ArticleDescription",pref.getString("artDecOne",""));
        v.addProperty("source","LRBooking" );


        Call<Pojo>call=REST_CLIENT.LRDetailsSave(v);
        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                Pojo detailData;
                if(response.isSuccessful()){
                    detailData=response.body();

                    Toast.makeText(getActivity(),"Article 1 details saved successfully!",Toast.LENGTH_SHORT).show();

                    String artTwo=pref.getString("artTwo","--select--");

                    System.out.println("&&&&&&&&&&&& Article 2 data isssss "+artTwo);

                    if(artTwo.equals("na")||artTwo.equals("--select--"))
                    {
                        //Code to navigate to home activity
                        Toast.makeText(getActivity(),"LR booking saved successfully!",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        Intent i=new Intent(getActivity(),HomeActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    }
                    else {

                        callArticleTwoData();

                    }

                }
                else {

                    System.out.println("art err.."+response.message());
                }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {

            }
        });
    }

    public void callArticleTwoData()
    {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@7");
        System.out.println(stLrNo+":"+pref.getString("artTwoId","")+"::"+stFromLocId+":"+stTariffCri+":"+pref.getString("actwtTwo","")+":"+pref.getString("chargdwtTwo","")+":"+pref.getString("qtyTwo","")+":"+pref.getString("artDecTwo","")+":"+pref.getString("barCTwo",""));


        JsonObject v=new JsonObject();
        v.addProperty("srno","2");
        v.addProperty("LRNo" ,stLrNo);
        v.addProperty("articleId",pref.getString("artTwoId",""));
        v.addProperty("fromlocid",stFromLocId);
        v.addProperty("calcriteria",stTariffCri);
        v.addProperty("units",0);
        v.addProperty("Length",0);
        v.addProperty("Width",0);
        v.addProperty("Height",0);
        v.addProperty("CFT",0);
        v.addProperty("ActWeight",pref.getString("actwtTwo",""));
        v.addProperty("ChgWeight",pref.getString("chargdwtTwo",""));
        v.addProperty("Qty",pref.getString("qtyTwo",""));
        //String barCodes=pref.getString("barCOne","")+pref.getString("barCTwo","")+pref.getString("barCThree","");
        v.addProperty("Barcodes",pref.getString("barCTwo",""));
        v.addProperty("rate",rate);
        v.addProperty("extrarate",extraRate);
        v.addProperty("amount",stTotalAmount);
        //String artDesc=pref.getString("artDecOne","")+pref.getString("artDecTwo","")+pref.getString("artDecThree","");
        v.addProperty("ArticleDescription",pref.getString("artDecTwo",""));
        v.addProperty("source","LRBooking" );


        Call<Pojo>call=REST_CLIENT.LRDetailsSave(v);
        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                Pojo detailData;
                if(response.isSuccessful()){
                    detailData=response.body();

                    Toast.makeText(getActivity(),"Article 2 details saved successfully!",Toast.LENGTH_SHORT).show();

                    String artThree=pref.getString("artThree","--select--");

                    //System.out.println("&&&&&&&&&&&& Article 3 data isssss "+artThree);


                    if(artThree.equals("na")||artThree.equals("--select--"))
                    {
                        Toast.makeText(getActivity(),"LR booking saved successfully!",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        Intent i=new Intent(getActivity(),HomeActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    }
                    else {

                        callArticleThreeData();

                    }


                }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {

            }
        });

    }

    public void callArticleThreeData()
    {
        JsonObject v=new JsonObject();
        v.addProperty("srno","3");
        v.addProperty("LRNo" ,stLrNo);
        v.addProperty("articleId",pref.getString("artThreeId",""));
        v.addProperty("fromlocid",stFromLocId);
        v.addProperty("calcriteria",stTariffCri);
        v.addProperty("units",0);
        v.addProperty("Length",0);
        v.addProperty("Width",0);
        v.addProperty("Height",0);
        v.addProperty("CFT",0);
        v.addProperty("ActWeight",pref.getString("actwtThree",""));
        v.addProperty("ChgWeight",pref.getString("chargdwtThree",""));
        v.addProperty("Qty",pref.getString("qtyThree",""));
        //String barCodes=pref.getString("barCOne","")+pref.getString("barCTwo","")+pref.getString("barCThree","");
        v.addProperty("Barcodes",pref.getString("barCThree",""));
        v.addProperty("rate",rate);
        v.addProperty("extrarate",extraRate);
        v.addProperty("amount",stTotalAmount);
        //String artDesc=pref.getString("artDecOne","")+pref.getString("artDecTwo","")+pref.getString("artDecThree","");
        v.addProperty("ArticleDescription",pref.getString("artDecThree",""));
        v.addProperty("source","LRBooking" );


        Call<Pojo>call=REST_CLIENT.LRDetailsSave(v);
        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                Pojo detailData;
                if(response.isSuccessful()){
                    detailData=response.body();

                    Toast.makeText(getActivity(),"Article 3 details saved successfully!",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(),"LR booking saved successfully!",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    Intent i=new Intent(getActivity(),HomeActivity.class);
                    startActivity(i);
                    getActivity().finish();

                }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {

            }
        });
    }

}