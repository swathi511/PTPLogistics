package com.hjsoftware.ptplogistics.Fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hjsoftware.ptplogistics.Activity.MainActivity;
import com.hjsoftware.ptplogistics.Adapters.SessionManager;
import com.hjsoftware.ptplogistics.R;
import com.hjsoftware.ptplogistics.Webservices.API;
import com.hjsoftware.ptplogistics.Webservices.RestClient;
import com.hjsoftware.ptplogistics.model.ApplicableTariffPojo;
import com.hjsoftware.ptplogistics.model.ManualSeriesPojo;
import com.hjsoftware.ptplogistics.model.Pojo;
import com.hjsoftware.ptplogistics.model.SenderAddPojo;
import com.hjsoftware.ptplogistics.model.TarifCriteriaPojo;
import com.hjsoftware.ptplogistics.model.locPojo;
import com.hjsoftware.ptplogistics.model.newLrpojo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewLrBookingOneFragment extends Fragment {

    //Detail 1
    View v;
    Spinner sp_lrType,sp_disPatchtype,sp_appTariff,sp_lrCriteria,sp_FrLocation,sp_doorDel,sp_tarif_cri,sp_status,sp_toloc,spManualLrSeries;
    TextView tv_lrDate,tv_next,from_loc, tv_lrOne,tv_lrTwo;
    API REST_CLIENT;
    String [] LrType ={"--select--","PAID","TO PAY","FOC","ON A/C"};
    String [] DType ={"Direct","Tranship"};
    String [] lrCriteria ={"Automatic","Manual"};
    String [] valueSurcg ={"Not Applicable","Applicable"};
    String [] TariffCriteria ={"--select--","Article Based","Regular Based"};
    String [] doorDel ={"Not Required","Required"};
    ArrayList<String> appTariff=new ArrayList<>() ;
    ArrayList<String> manualLRSeries=new ArrayList<>() ;
    String [] status ={"Not Required","Required"};
    //String [] status ={"Required","Not Required"};
    String date,stProfileid;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    ArrayList<String> loclist = new ArrayList<String>();
    private static final String PREF_NAME = "SharedPref";
    SessionManager session;
    HashMap<String, String> user;
    String spItem;
    DatePicker dp;
    int day,mnth,yr;
    String stDate,fromloc,st_Rname,st_goodValue,st_custInvNo,st_ewayBill;
    String stLrType,stLrCriteria,stToLoc,stTariffCrit,stDispatchType,stAppTariff,stDoorDel,stStatus,stValueSurchg,stSendername;
    int lTPos,lCPos,tLPos,tCPos,dTPos,aTPos,dDPos,sPos,sNPos,dePos;
    Boolean isCommitted;
    boolean userSelect=false;
    String stNo,stBrName,stLrDate,stYr;
    ArrayAdapter<String> dataAdapter;
    double GoodsValueLimit=50000;
    double GoodsValueAmount=0;
    LinearLayout ll_eCustomer,ll_oCustomer;
    EditText et_othrSender;
    String yes="";
    String lrprefix="";


    //Detail 2

    EditText tv_Sgstin,tv_Saddress,tv_Smobile,et_FocRemarks;
    EditText tv_Rgstin,tv_Rname,tv_Raddress,tv_Rmobile,tv_CustNo,tv_goodValue,tv_Ewaybil;
    Spinner sp_valSurchrge,sp_Sname;
    TextView tv_Lrone,tv_Lrtwo;
    ArrayList<String> tariflist = new ArrayList<String>();
    String stFromLocId,stToLocId,stBrCode,stCustId="na",stCustName="",stTariffId;
    ArrayAdapter<String> dataAdaptersix;
    LinearLayout llManualSeries,llFocdetails;
    ArrayAdapter<String> dataAdapterSeven;
    CheckBox cbSSms,cbRSms;
    String stCustInvNo;
    List<ManualSeriesPojo> msp;
    String seriesId="";

    String version="1.8";//1.7 prod
    //String version="1.4"; //local

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("onCreate called...");
        pref = getActivity().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.putBoolean("isCommitted",false);
        editor.commit();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater .inflate(R.layout.new_lrbooking_one, container, false);

        //System.out.println("onCreateView called...");

        REST_CLIENT= RestClient.get();

        session=new SessionManager(getContext());
        user=session.getUserDetails();

        //Detail 1

        stProfileid=user.get(SessionManager.KEY_PROFILE_ID);
        Log.i("PROFILEID","is "+stProfileid);
        sp_lrType=(Spinner)v.findViewById(R.id.new_lrb_one_sp_lr_type);
        sp_disPatchtype=(Spinner)v.findViewById(R.id.new_lrb_one_sp_dispatch_type);
        sp_appTariff=(Spinner)v.findViewById(R.id.new_lrb_one_sp_app_tarif);
        sp_lrCriteria=(Spinner)v.findViewById(R.id.new_lrb_one_sp_lr_criteria);
        from_loc=(TextView) v.findViewById(R.id.new_lrb_one_tv_from_loc);
        sp_toloc=(Spinner)v.findViewById(R.id.new_lrb_one_sp_to_loc);
        spManualLrSeries=(Spinner)v.findViewById(R.id.new_lrb_one_sp_manual_lr_series);
        //spManualLrSeries.setVisibility(View.GONE);
        sp_lrCriteria.setSelection(1);

        sp_doorDel=(Spinner)v.findViewById(R.id.new_lrb_one_sp_door_del);
        sp_tarif_cri=(Spinner)v.findViewById(R.id.new_lrb_one_sp_tarif_criter);
        sp_status=(Spinner)v.findViewById(R.id.new_lrb_one_sp_status);
        tv_lrDate=(TextView)v.findViewById(R.id.new_lrb_one_tv_lr_date);
        //et_toLoc=(EditText)v. findViewById(R.id.new_lrb_one_et_to_loc);
        tv_lrTwo=(TextView) v.findViewById(R.id.new_lrb_one_tv_lr_no_two);
        tv_lrOne=(TextView)v.findViewById(R.id.new_lrb_one_tv_lr_no_one);
        tv_next=(TextView)v.findViewById(R.id.new_lrb_one_tv_next);

        //Detail 2

        tv_Sgstin=(EditText) v.findViewById(R.id.new_lrb_one_tv_send_gstin);
        tv_Rgstin=(EditText) v.findViewById(R.id.new_lrb_one_tv_recvr_gstin);
        tv_Rname=(EditText) v.findViewById(R.id.new_lrb_one_tv_recver_name);
        tv_Saddress=(EditText) v.findViewById(R.id.new_lrb_one_tv_sendr_address);
        tv_Raddress=(EditText) v.findViewById(R.id.new_lrb_one_tv_recvr_address);
        tv_Smobile=(EditText) v.findViewById(R.id.new_lrb_one_tv_sendr_mobile_no);
        tv_Rmobile=(EditText) v.findViewById(R.id.new_lrb_one_tv_rec_mobile_no);
        tv_CustNo=(EditText) v.findViewById(R.id.new_lrb_one_tv_cust_inv_no);
        tv_goodValue=(EditText) v.findViewById(R.id.new_lrb_one_tv_good_value);
        tv_Ewaybil=(EditText) v.findViewById(R.id.new_lrb_one_tv_eway_bill);
        sp_valSurchrge=(Spinner)v.findViewById(R.id.new_lrb_one_sp_value_sur_charge);
        sp_Sname=(Spinner) v.findViewById(R.id.new_lrb_one_sp_sender_name);
        tv_next=(TextView)v.findViewById(R.id.new_lrb_one_tv_next);
        tv_Lrone=(TextView)v.findViewById(R.id.new_lrb_one_tv_lr_no_one);
        tv_Lrtwo=(TextView)v.findViewById(R.id.new_lrb_one_tv_lr_no_two);
        et_othrSender=(EditText)v.findViewById(R.id.new_lrb_one_et_sender_name);

        ll_eCustomer=(LinearLayout)v.findViewById(R.id.new_lrb_one_ll_senedr_names);
        ll_oCustomer=(LinearLayout)v.findViewById(R.id.new_lrb_one_ll_senedr_others);

        llFocdetails=(LinearLayout)v.findViewById(R.id.new_lrb_one_ll_foc);

        llFocdetails.setVisibility(View.GONE);
        et_FocRemarks=(EditText)v.findViewById(R.id.new_lrb_one_et_foc_remarks);
        ll_oCustomer.setVisibility(View.GONE);
        llManualSeries=(LinearLayout)v.findViewById(R.id.new_lrb_one_ll_manual_lr_series);
        llManualSeries.setVisibility(View.GONE);

        cbSSms=(CheckBox)v.findViewById(R.id.new_lrb_one_cb_sendr_msg);
        cbRSms=(CheckBox)v.findViewById(R.id.new_lrb_one_cb_recvr_msg);

        tv_goodValue.setText("0");
        loclist.add("--select--");
        tariflist.add("--select--");


        stCustInvNo=tv_CustNo.getText().toString();
        editor.putString("CustInvNo",st_custInvNo);
        editor.commit();

        isCommitted=pref.getBoolean("isCommitted",false);

        dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, tariflist);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_Sname.setAdapter(dataAdapter);


        System.out.println("sendernames@@@@@@@@@@@@"+stCustName);
        System.out.println("tolocation@@@@@@@@@@@@"+stToLoc);


        if(cbSSms.isChecked()){
            editor.putString("ssms","Y");
            editor.putString("msgto","Sender");
            editor.commit();
        }
        else{
            editor.putString("ssms","N");
            editor.commit();
        }
        if(cbRSms.isChecked()){
            editor.putString("rsms","Y");
            editor.putString("msgto","Receiver");
            editor.commit();
        }
        else{
            editor.putString("rsms","N");
            editor.commit();
        }

        tv_lrDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.alert_datepicker, null);
                dialogBuilder.setView(dialogView);

                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                dp=(DatePicker)dialogView.findViewById(R.id.datePicker);
                Button ok=(Button)dialogView.findViewById(R.id.ok);
                Button cancel=(Button)dialogView.findViewById(R.id.cancel);
                cancel.setVisibility(View.GONE);


                long now = System.currentTimeMillis() - 1000;

                //final Calendar c1=new GregorianCalendar(2018, Calendar.APRIL, 1);
                // final Calendar c2=new GregorianCalendar(2019, Calendar.MARCH, 31);

//                dp.setMinDate(now);
//                dp.setMaxDate(now+(1000*60*60*24*7));

                //dp.setMinDate(c1.getTimeInMillis());
                // dp.setMaxDate(c2.getTimeInMillis());

                day = dp.getDayOfMonth();
                mnth = dp.getMonth() + 1;
                yr = dp.getYear();

                //System.out.println("date is "+day+":"+mnth+":"+yr);

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                dp.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        day=dayOfMonth;
                        mnth=month+1;
                        yr=year;

                    }
                });


                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        System.out.println("************* "+mnth);

                        Date d=makeDateGMT(yr,mnth-1,day);
                        Date d1=new Date();

                        System.out.println("d & d1"+d.toString()+"::"+d1.toString());



                        if(d.before(d1))
                        {
                            Toast.makeText(getActivity(),"Please choose date ahead of current date!", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            System.out.println("YYYYYYYYYYYYYYYYYY"+d.getYear()+"::::"+d.getMonth());
                            System.out.println("YEARRRRRRRRRR"+yr);

                            System.out.println("EEEEEEEEEEEEEEEEEEEEE"+mnth+"::::"+String.valueOf(yr).substring(2,4)+"::::"+String.valueOf(yr+1).substring(2,4));
                            System.out.println("d.getMonth.."+d.getMonth());


                            if((d.getMonth()+1)<=3)
                            {
                                stYr=String.valueOf(yr-1).substring(2,4);
                            }
                            else {

                                stYr=String.valueOf(yr).substring(2,4);
                            }

                            stDate=day+"/"+mnth+"/"+yr;
                            stLrDate=mnth+"/"+day+"/"+yr;

                            tv_lrDate.setVisibility(View.VISIBLE);
                            tv_lrDate.setText(stDate);
                            tv_lrDate.setTextColor(Color.parseColor("#000000"));
                            alertDialog.dismiss();
                            tv_Lrone.setText(stBrCode + "/" + stNo + "/" + stYr);

                            loadLrNo();
                        }

                    }
                });
            }
        });


        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                st_Rname=tv_Rname.getText().toString();
                st_custInvNo=tv_CustNo.getText().toString();
                st_goodValue=tv_goodValue.getText().toString();
                st_ewayBill=tv_Ewaybil.getText().toString();

                editor.putString("senderAdd",tv_Saddress.getText().toString().trim());
                editor.putString("sendermobile",tv_Smobile.getText().toString().trim());
                editor.putString("senderGstin",tv_Sgstin.getText().toString().trim());
                //editor.putString("senderartiCh",senderartiCh);
                editor.commit();
                editor.putString("rAddress", tv_Raddress.getText().toString());
                editor.putString("rGStin",tv_Rgstin.getText().toString());
                editor.putString("rMobile",tv_Rmobile.getText().toString());
                editor.commit();

                GoodsValueAmount=Double.parseDouble(st_goodValue);

                if(tv_Smobile.getText().toString().length()!=0&&tv_Smobile.getText().toString().length()!=10)
                {
                    Toast.makeText(getActivity(),"Please enter valid Consignor mobile number!",Toast.LENGTH_SHORT).show();
                }
                else if(tv_Rmobile.getText().toString().length()!=0&&tv_Rmobile.getText().toString().length()!=10)
                {
                    Toast.makeText(getActivity(),"Please enter valid Consignee mobile number!",Toast.LENGTH_SHORT).show();
                }
                else if(tv_Sgstin.getText().toString().length()!=0&&tv_Sgstin.getText().toString().length()!=15)
                {
                    Toast.makeText(getActivity(),"Please enter valid Consignor GSTIN!",Toast.LENGTH_SHORT).show();
                }
                else if(tv_Rgstin.getText().toString().length()!=0&&tv_Rgstin.getText().toString().length()!=15)
                {
                    Toast.makeText(getActivity(),"Please enter valid Consignee GSTIN!",Toast.LENGTH_SHORT).show();
                }
                else if(GoodsValueAmount==GoodsValueLimit||GoodsValueAmount>GoodsValueLimit){

                    //System.out.println("INSIDEeeeeeeeeeeeee"+GoodsValueAmount);

                    if(st_custInvNo.equals("")||st_ewayBill.equals("")){
                        Toast.makeText(getActivity(), "Please enter Invoice & Eway Bill!", Toast.LENGTH_SHORT).show();

                    }
                    else if(GoodsValueAmount>GoodsValueLimit)
                    {
                        if(tv_Sgstin.getText().toString().equals("")||tv_Rgstin.getText().toString().equals(""))
                        {
                            Toast.makeText(getActivity(),"Please enter both Consignor & Consignee GST no's.",Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else if(st_Rname.equals("")){

                    Toast.makeText(getActivity(), "Please enter Consignee name!", Toast.LENGTH_SHORT).show();

                }

//                else if(cbSSms.isChecked())
//                {
//                    editor.putString("ssms","Y");
//                    editor.commit();
//
////                    if(tv_Smobile.getText().toString().equals(""))
////                    {
////                        Toast.makeText(getActivity(),"Please enter Sender Mobile number!",Toast.LENGTH_LONG).show();
////                    }
//                }
//                else if(cbRSms.isChecked())
//                {
//                    if(tv_Rmobile.getText().toString().equals(""))
//                    {
//                        Toast.makeText(getActivity(),"Please enter Receiver Mobile number!",Toast.LENGTH_LONG).show();
//
//                    }
//                }
                else if(stCustName.equals("Other Sender") && et_othrSender.getText().toString().equals("")){

                    Toast.makeText(getActivity(), "Please enter Sender name!", Toast.LENGTH_SHORT).show();

                }


                else if(stLrType.equals("--select--")){
                    Toast.makeText(getActivity(), "Please select Lrtype!", Toast.LENGTH_SHORT).show();

                }
                else if(stCustName.equals("select")){
                    Toast.makeText(getActivity(), "Please select Sender name!", Toast.LENGTH_SHORT).show();

                }

                else if(stTariffCrit.equals("--select--")){
                    Toast.makeText(getActivity(), "Please select Tariff Critera!", Toast.LENGTH_SHORT).show();

                }
                else if(stToLoc.equals("select")){
                    Toast.makeText(getActivity(), "Please select Destination!", Toast.LENGTH_SHORT).show();

                }
                else if(stLrType.equals("FOC")&& et_FocRemarks.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Please  enter Foc Remarks", Toast.LENGTH_SHORT).show();
                }
                else  if(stLrType.equals("FOC") && !stCustName.equals("Other Sender"))
                {/*
                    if(stCustName.equals("Other Sender"))
                    {*//*
                        ll_eCustomer.setVisibility(View.GONE);
                        ll_oCustomer.setVisibility(View.VISIBLE);

                        tv_Saddress.setText("");
                        tv_Smobile.setText("");
                        tv_Sgstin.setText("");
                        cbSSms.setChecked(false);

                        editor.putString("otherSender","yes");
                        editor.commit();*//*
                    }
                    else {*/

                    Toast.makeText(getActivity(), "FOC not applicable to TBB customers.", Toast.LENGTH_SHORT).show();
                    sp_Sname.setSelection(0);
                    stCustName="--select--";


                    // }
                }


                else{

                    editor.putString("focRemarks",et_FocRemarks.getText().toString().trim());
                    editor.putString("lrtype",stLrType);
                    editor.putInt("ltPos",lTPos);

                    System.out.println("StlrDate"+stLrDate);
                    System.out.println("datee"+stDate);
                    System.out.println("GoodsValuee"+st_goodValue);

                    editor.putString("lrDate",stLrDate);

                    editor.putString("lrCriteria",stLrCriteria);
                    editor.putInt("lcPos",lCPos);

                    editor.putString("recName",st_Rname);

                    editor.putString("lrNotwo",tv_Lrtwo.getText().toString());
                    editor.putString("lrNoOne",tv_Lrone.getText().toString());

                    //for fromLoc & fromLocId see above code...

                    //for toLoc & toLocId see above code...
                    editor.putString("tariffCriteria",stTariffCrit);
                    editor.putInt("tcPos",tCPos);

                    editor.putString("dispatchType",stDispatchType);
                    editor.putInt("dtPos",dTPos);

                    editor.putString("appTariff",stAppTariff);
                    editor.putInt("atPos",aTPos);

                    editor.putString("doorDel",stDoorDel);
                    editor.putInt("ddPos",dDPos);

                    editor.putString("destination",stToLoc);

                    editor.putString("othersender",et_othrSender.getText().toString());

                    editor.putInt("dePos",dePos);

                    editor.putString("sendername",stCustName);

                    editor.putInt("snPos",sNPos);
                    editor.putString("status",stStatus);
                    editor.putInt("sPos",sPos);

                    editor.putString("goodsvalue",st_goodValue);
                    editor.putString("ewaybill",st_ewayBill);
                    editor.putString("custInvNo",st_custInvNo);

                    editor.putString("no",stNo+"/"+Calendar.getInstance().get(Calendar.YEAR) % 100);

                    editor.putBoolean("isCommitted",true);
                    editor.commit();

                    if(stLrCriteria.equals("Automatic"))
                    {
                        editor.putString("bookseriesid","-");
                        editor.commit();
                    }

                    System.out.println("ddddddddddd::"+stNo+"/"+Calendar.getInstance().get(Calendar.YEAR) % 100);

                    Fragment frag = new NewLrBookingThreeFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.content_frame, frag, "specific_ride");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }


            }
        });

        loadData();
        //hubstatus();

        return v;

    }

    private Date makeDateGMT(int year, int month, int day) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(year,month, day);
        return calendar.getTime();
    }

    public void loadData()
    {

        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);

        date=mYear+"-"+(mMonth+1)+"-"+mDay;
        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
        tv_lrDate.setText(mDay+"/"+(mMonth+1)+"/"+mYear);
        stLrDate=(mMonth+1)+"/"+mDay+"/"+mYear;
        stYr=String.valueOf((Calendar.getInstance().get(Calendar.YEAR) % 100));

        System.out.println("MONTH ISSS"+(mMonth+1)+"YR"+stYr);

        if((mMonth+1)<=3)
        {
            stYr=String.valueOf(Integer.parseInt(stYr)-1);
        }

        System.out.println("stYr after iss"+stYr);


        final ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, LrType);

        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_lrType.setAdapter(dataAdapter1);
        sp_lrType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                stLrType=sp_lrType.getSelectedItem().toString();
                editor.putString("lrtype",stLrType);
                editor.commit();
                lTPos=position;

                System.out.println("sp lrtype position is"+position);

                if(position==0)
                {

                }
                else {

                    if(stLrCriteria.equals("Automatic")) {



                        if (stLrType.equals("PAID")) {
                            stNo = "P";
                            llFocdetails.setVisibility(View.GONE);
                        } else if (stLrType.equals("FOC")) {
                            llFocdetails.setVisibility(View.VISIBLE);
                            stNo = "F";
                        /*if(et_FocRemarks.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "Please  enter Foc Remarks", Toast.LENGTH_SHORT).show();
                        }*/

                        } else if (stLrType.equals("TO PAY")) {
                            llFocdetails.setVisibility(View.GONE);

                            stNo = "T";
                        }
                        else if (stLrType.equals("ON A/C")) {
                            llFocdetails.setVisibility(View.GONE);

                            stNo = "A";
                        }
                        else {
                            stNo = "F";
                        }

                        tv_Lrone.setText(stBrCode + "/" + stNo + "/" + stYr);

                        if (stLrType.equals("ON A/C")) {
                            //loadLrNo();
                            llFocdetails.setVisibility(View.GONE);
                            tv_Lrtwo.setText("");

                            System.out.println("cust id.." + stCustId);
                            if (stCustId.equals("na")) {

                            } else {
                                loadLrNoForONAccount();
                            }
                        } else {
                            loadLrNo();
                        }
                    }
                    else {


                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        ArrayAdapter<String> dataAdapterfive = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,doorDel);

        dataAdapterfive.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_doorDel.setAdapter(dataAdapterfive);
        sp_doorDel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                stDoorDel=sp_doorDel.getSelectedItem().toString();
                editor.putString("DoorDel",stDoorDel);
                editor.commit();
                dDPos=position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        ArrayAdapter<String> dataAdapt = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,valueSurcg);

        dataAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_valSurchrge.setAdapter(dataAdapt);
        sp_valSurchrge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                stValueSurchg=sp_valSurchrge.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        final ArrayAdapter<String> dataAdaptertwo = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, DType);

        dataAdaptertwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_disPatchtype.setAdapter(dataAdaptertwo);
        sp_disPatchtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                stDispatchType=sp_disPatchtype.getSelectedItem().toString();
                editor.putString(" DispatchType",  stDispatchType);
                editor.commit();
                dTPos=position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        ArrayAdapter<String> dataAdapterthree = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, lrCriteria);

        dataAdapterthree.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_lrCriteria.setAdapter(dataAdapterthree);
        //sp_lrCriteria.setSelection(0);
        sp_lrCriteria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {



                stLrCriteria = sp_lrCriteria.getSelectedItem().toString();
                lCPos = position;

                if(stLrCriteria.equals("Manual"))
                {
                    llManualSeries.setVisibility(View.VISIBLE);
                    //getManualLRSeries();
                    manualSeriesAPI();
                }

                else {
                    llManualSeries.setVisibility(View.GONE);
                    tv_Lrtwo.setVisibility(View.VISIBLE);
                }

            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });



        ArrayAdapter<String> dataAdapterseven = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, status);

        dataAdapterseven.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_status.setAdapter(dataAdapterseven);
        sp_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                stStatus=sp_status.getSelectedItem().toString();
                sPos=position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        ArrayAdapter<String> dataAdapterfour = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, TariffCriteria);

        dataAdapterfour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_tarif_cri.setAdapter(dataAdapterfour);
        sp_tarif_cri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                spItem=sp_tarif_cri.getSelectedItem().toString();
                editor.putString("tarifCriteria",spItem);
                editor.commit();
                tCPos=position;
                stTariffCrit=spItem;

                tv_Sgstin.setClickable(true);
                tv_Sgstin.setEnabled(true);
                tv_Smobile.setClickable(true);
                tv_Smobile.setEnabled(true);
                tv_Saddress.setClickable(true);
                tv_Saddress.setEnabled(true);
                tv_Smobile.setText("");
                tv_Sgstin.setText("");
                tv_Saddress.setText("");


                if(position==0)
                {

                }
                else {

                    if (spItem.equals("Regular Based")) {

                        //System.out.println("@@@@regular Based"+spItem);
                        editor.putString("Regular Based", spItem);
                        editor.commit();

                        loadSenderNames();
                        //loadApplicableTariff(stTariffCrit.substring(0,1));

                    } else {

                        /*tariflist.clear();

                        tariflist.add("--select--");

                        dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, tariflist);

                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        //dataAdapter.clear();
                        dataAdapter.notifyDataSetChanged();

                        sp_Sname.setAdapter(dataAdapter);*/

                        loadSenderNames();

                       /* tv_Sgstin.setClickable(true);
                        tv_Sgstin.setEnabled(true);
                        tv_Smobile.setClickable(true);
                        tv_Smobile.setEnabled(true);
                        tv_Saddress.setClickable(true);
                        tv_Saddress.setEnabled(true);
                        tv_Smobile.setText("");
                        tv_Sgstin.setText("");
                        tv_Saddress.setText("");*/

                        //loadApplicableTariff(stTariffCrit.substring(0,1));

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        final ProgressDialog progressDialog= new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        retrofit2.Call<List<locPojo>>call1=REST_CLIENT.getlocinfo();
        call1.enqueue(new Callback<List<locPojo>>() {
            @Override
            public void onResponse(retrofit2.Call<List<locPojo>> call, Response<List<locPojo>> response) {
                final List<locPojo> locPojostatus;
                locPojo locdata;

                locPojostatus=response.body();
                if(response.isSuccessful()){

                    for(int i=0;i<locPojostatus.size();i++) {
                        locdata = locPojostatus.get(i);

                        String LocationName = locdata.getLocation();

                        editor.putString("tolocationname",LocationName.split("-")[0]);
                        editor.putString("tobranchname",LocationName.split("-")[1]);
                        editor.commit();
                        System.out.println("##########tolocationname"+LocationName.split("-")[0]);
                        System.out.println("##########tobranchname"+LocationName.split("-")[1]);

                        String lid=locdata.getLocationid();
                        System.out.println("LOCATIONS*****"+lid);

                        loclist.add(LocationName);

                    }




                    ArrayAdapter arrayAdapter2=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,loclist);
                    arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_toloc.setAdapter(arrayAdapter2);


                    progressDialog.dismiss();



                    /*if (isCommitted) {
                        tLPos = pref.getInt("tlPos", 0);
                        sp_toloc.setSelection(tLPos);
                    }*/

                    sp_toloc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            System.out.println("position..."+position);

                            if(position==0)
                            {
                                stToLoc="select";
                            }
                            else {

                                stToLoc = sp_toloc.getSelectedItem().toString();
                                dePos=position;
                                System.out.println("@#########321231313stLoc@@@@@@@@&&&&&&&********"+stToLoc);
                                tLPos = position;

                                System.out.println("isCommittedvalue" + isCommitted);


                                locPojo lc;

                                for (int i = 0; i < locPojostatus.size(); i++) {
                                    lc = locPojostatus.get(i);

                                    if (lc.getLocation().equals(stToLoc)) {
                                        editor.putString("toLoc", stToLoc);
                                        editor.putString("toLocId", lc.getLocationid().split("-")[0]);
                                        editor.putString("tobranchid",lc.getLocationid().split("-")[1]);
                                        editor.putString("tobranchname",stToLoc.split("-")[1]);
                                        System.out.println("########tobranchname"+stToLoc.split("-")[1]);
                                        System.out.println("##########toLocId"+ lc.getLocationid().split("-")[0]);
                                        System.out.println("##########tobranchid"+lc.getLocationid().split("-")[1]);
                                        stToLocId=lc.getLocationid().split("-")[0];
                                        editor.putInt("tlPos", tLPos);

                                        editor.commit();

                                        System.out.println("%%%%%%%%%%%" + lc.getLocation() + lc.getLocationid());

                                        System.out.println("tariff criterai"+stTariffCrit);

                                        if(stTariffCrit.equals("Regular Based"))
                                        {
                                            loadSenderNames();
                                        }


                                        break;
                                    }


                                }
                            }


                            //}
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<locPojo>> call, Throwable t) {


                progressDialog.dismiss();

                Toast.makeText(getActivity(),"Please check Internet connection!",Toast.LENGTH_SHORT).show();
                getActivity().finish();

            }
        });

        retrofit2.Call<List<newLrpojo>>call=REST_CLIENT.getUserDetails(stProfileid,version);
        call.enqueue(new Callback<List<newLrpojo>>() {
            @Override
            public void onResponse(retrofit2.Call<List<newLrpojo>> call, Response<List<newLrpojo>> response) {
                List<newLrpojo> newLrpojostatus;
                newLrpojo Lrdata;

                newLrpojostatus=response.body();

                if(response.isSuccessful()){

                    for(int i=0;i<newLrpojostatus.size();i++) {
                        Lrdata = newLrpojostatus.get(i);

                        String agentname = Lrdata.getAgentname();
                        stBrName=Lrdata.getBranchname();
                        String brid = Lrdata.getBrid();
                        String hubstatus = Lrdata.getHubstatus();
                        String servicelocid = Lrdata.getServicelocationid();
                        String typeofagent = Lrdata.getTypeofagent();
                        String deliverystatus = Lrdata.getDeliverystatus();
                        String branchtype = Lrdata.getBranchtype();
                        String rateChstatus = Lrdata.getRatechangestatus();
                        String lrActiv = Lrdata.getLractivity();
                        String userprofile = Lrdata.getUserprofile();
                        String servicelocname=Lrdata.getServicelocationname();

                        if(Lrdata.getModifiedprivilage().equals("Required"))
                        {
                            editor.putBoolean("canModify",true);
                        }
                        else {
                            editor.putBoolean("canModify",false);
                        }


                        String brcode=Lrdata.getBrcode().concat("/");
                        fromloc=Lrdata.getServicelocationname().concat("-") + Lrdata.getBranchname();

                        editor.putString("fromLoc",fromloc);
                        editor.putString("fromLocId",servicelocid);
                        editor.putString("branchId",Lrdata.getBrid());
                        editor.putString("branchname",stBrName);
                        stFromLocId=servicelocid;
                        editor.putString("brCode",Lrdata.getBrcode());
                        stBrCode=Lrdata.getBrcode();
                        editor.commit();

                        hubstatus();
                        from_loc.setText(fromloc);
                        tv_lrOne.setText(brcode);

                        if(lrActiv.equals("Both"))
                        {
                            sp_lrCriteria.setSelection(0);
                        }
                        else if(lrActiv.equals("Automatic"))
                        {
                            sp_lrCriteria.setSelection(0);
                            sp_lrCriteria.setEnabled(false);
                            sp_lrCriteria.setClickable(false);
                        }
                        else {
                            sp_lrCriteria.setSelection(1);
                            sp_lrCriteria.setEnabled(false);
                            sp_lrCriteria.setClickable(false);
                        }





                    }


                }
                else {
                    verisonChecking();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<newLrpojo>> call, Throwable t) {


            }
        });

        //manualSeriesAPI();

        /*if(isCommitted)
        {
            lTPos=pref.getInt("ltPos",0);
            sp_lrType.setSelection(lTPos);


            lCPos=pref.getInt("lcPos",0);
            sp_lrCriteria.setSelection(lCPos);

            *//*tLPos=pref.getInt("tlPos",0);
            sp_toloc.setSelection(tLPos);*//*

            tCPos=pref.getInt("tcPos",0);
            sp_tarif_cri.setSelection(tCPos);

            dTPos=pref.getInt("dtPos",0);
            sp_disPatchtype.setSelection(dTPos);

            aTPos=pref.getInt("atPos",0);
            sp_appTariff.setSelection(aTPos);

            dDPos=pref.getInt("ddPos",0);
            sp_doorDel.setSelection(dDPos);

            sPos=pref.getInt("sPos",0);
            sp_status.setSelection(sPos1);

            tv_lrDate.setText(pref.getString("lrDate",tv_lrDate.getText().toString()));
        }*/
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("on Start called");

    }

    public void loadSenderNames()
    {

        System.out.println("Tariff&FRomId&ToId"+stTariffCrit+"&"+stFromLocId+"&"+stToLocId);


        retrofit2.Call<List<TarifCriteriaPojo>> call=REST_CLIENT.getSenderList(stTariffCrit,stFromLocId,stToLocId);
        call.enqueue(new Callback<List<TarifCriteriaPojo>>() {
            @Override
            public void onResponse(retrofit2.Call<List<TarifCriteriaPojo>> call, Response<List<TarifCriteriaPojo>> response) {
                final List<TarifCriteriaPojo> tarifCriteriaPojostatus;
                TarifCriteriaPojo tarifdata;
                tarifCriteriaPojostatus=response.body();
                if(response.isSuccessful()){


                    for(int i=0;i<tarifCriteriaPojostatus.size();i++){

                        tarifdata=tarifCriteriaPojostatus.get(i);

                        String stCustmerName=tarifdata.getCustomername();
                        String stCustrid=tarifdata.getCustomerId();
                        editor.putString("senderId",stCustrid);
                        editor.putString("senderName",stCustmerName);

                        editor.commit();

                        tariflist.add(stCustmerName);
                        System.out.println("#####################@@"+stCustmerName);

                    }

                    if(stTariffCrit.equals("Regular Based")&&!stLrType.equals("ON A/C")) {

                        tariflist.add("Other Sender");
                    }
                    dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, tariflist);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    sp_Sname.setAdapter(dataAdapter);
                    sp_Sname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {


                            tv_Saddress.setText("");
                            tv_Smobile.setText("");
                            tv_Sgstin.setText("");

                            System.out.println("position is ++++++"+position);

                            stCustName = sp_Sname.getSelectedItem().toString();
                            System.out.println("sendernames????????????"+stCustName);
                            sNPos=position;

                            if(position==0){
                                stCustName="select";
                            }
                            else{
                                if(stLrType.equals("FOC"))
                                {
                                    if(stCustName.equals("Other Sender"))                            {
                                        ll_eCustomer.setVisibility(View.GONE);
                                        ll_oCustomer.setVisibility(View.VISIBLE);

                                        tv_Saddress.setText("");
                                        tv_Smobile.setText("");
                                        tv_Sgstin.setText("");
                                        cbSSms.setChecked(false);

                                        editor.putString("otherSender","yes");
                                        editor.putString("CustomerId", "");

                                        editor.commit();

                                        loadApplicableTariff(stTariffCrit.substring(0,1),"other");

                                    }
                                    else {

                                        Toast.makeText(getActivity(), "FOC not applicable to TBB customers.", Toast.LENGTH_SHORT).show();
                                        sp_Sname.setSelection(0);
                                        stCustName="--select--";


                                    }
                                }
                                else if(stCustName.equals("Other Sender")){
                                    ll_eCustomer.setVisibility(View.GONE);
                                    ll_oCustomer.setVisibility(View.VISIBLE);

                                    tv_Saddress.setText("");
                                    tv_Smobile.setText("");
                                    tv_Sgstin.setText("");
                                    cbSSms.setChecked(false);

                                    editor.putString("otherSender","yes");
                                    editor.putString("CustomerId", "");
                                    editor.commit();

                                    loadApplicableTariff(stTariffCrit.substring(0,1),"other");


//                                if(et_othrSender.getText().toString().equals("")){
//                                    Toast.makeText(getActivity(), "Please enter Sender name", Toast.LENGTH_SHORT).show();
//                                }
                                }
                                else {



                                    TarifCriteriaPojo tp;

                                    for (int i = 0; i < tarifCriteriaPojostatus.size(); i++) {
                                        tp = tarifCriteriaPojostatus.get(i);

                                        if (tp.getCustomername().equals(stCustName)) {
                                            stCustId = tp.getCustomerId();
                                            editor.putString("CustomerId", stCustId);
                                            editor.putString("otherSender","no");

                                            System.out.println("####customerid@@@@@" + stCustId);
                                            System.out.println("####customername@@@@@" + stCustName);
                                            editor.commit();

                                            loadApplicableTariff(stTariffCrit.substring(0,1),stCustId);


                                            if(stLrType.equals("ON A/C")) {
                                                loadLrNoForONAccount();
                                            }

                                            break;

                                        }
                                    }

                                    loadSenderAddress();
                                }
                            }





                          /*  if(position==0)
                            {
                                stCustName="select";
                            }
                            else if(stCustName.equals("Other Sender")){
                                ll_eCustomer.setVisibility(View.GONE);
                                ll_oCustomer.setVisibility(View.VISIBLE);

                                tv_Saddress.setText("");
                                tv_Smobile.setText("");
                                tv_Sgstin.setText("");
                                cbSSms.setChecked(false);

                                editor.putString("otherSender","yes");
                                editor.commit();

//                                if(et_othrSender.getText().toString().equals("")){
//                                    Toast.makeText(getActivity(), "Please enter Sender name", Toast.LENGTH_SHORT).show();
//                                }
                            }
                            else {



                                TarifCriteriaPojo tp;

                                for (int i = 0; i < tarifCriteriaPojostatus.size(); i++) {
                                    tp = tarifCriteriaPojostatus.get(i);

                                    if (tp.getCustomername().equals(stCustName)) {
                                        stCustId = tp.getCustomerId();
                                        editor.putString("CustomerId", stCustId);
                                        System.out.println("####customerid@@@@@" + stCustId);
                                        editor.commit();

                                        if(stLrType.equals("ON A/C")) {
                                            loadLrNoForONAccount();
                                        }

                                        break;

                                    }
                                }

                                loadSenderAddress();
                            }*/

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {


                        }
                    });


                }
                else {
                    String msg=response.message();
                    System.out.println("responce is"+msg);

                    tariflist.clear();
                    tariflist.add("--select--");

                    //newly added
                    //sp_tarif_cri.setSelection(0);


                    stCustName="select";
                    sNPos=0;

                    dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, tariflist);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    sp_Sname.setAdapter(dataAdapter);


                    Toast.makeText(getActivity(),"No Senders found!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<TarifCriteriaPojo>> call, Throwable t) {

                Toast.makeText(getContext(),"Please check Internet connection!",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                System.out.println("@@@@@@@@@@msg"+t.getMessage());
            }
        });
    }

    public void loadSenderAddress()
    {
        String customerid=pref.getString("CustomerId",null);

        if(stLrCriteria.equals("Automatic")) {

            tv_Lrone.setText(stBrCode + "/" + stNo + "/" + stYr);
        }

        System.out.println("customerid@@@@@1234"+customerid+":::::"+stCustId+"::::"+stTariffCrit.substring(0,1));

        retrofit2.Call<List<SenderAddPojo>> call1=REST_CLIENT.getsenderAddress(stCustId,stTariffCrit.substring(0,1));
        call1.enqueue(new Callback<List<SenderAddPojo>>() {
            List<SenderAddPojo> senderAddPojostatus;
            SenderAddPojo senderdata;
            @Override
            public void onResponse(retrofit2.Call<List<SenderAddPojo>> call, Response<List<SenderAddPojo>> response) {
                senderAddPojostatus=response.body();
                if(response.isSuccessful()){

                    System.out.println("&&&&&&&&&SIZE"+senderAddPojostatus.size());

                    appTariff.clear();
                    dataAdaptersix.clear();
                    dataAdaptersix.notifyDataSetChanged();

                    for(int i=0;i<senderAddPojostatus.size();i++){
                        senderdata=senderAddPojostatus.get(i);

                        String senderAdd=senderdata.getAddress();
                        String sendermobile=senderdata.getPhone();
                        String senderGstin=senderdata.getGstno();
                        String senderartiCh=senderdata.getAriclechg();
                        tv_Saddress.setText(senderAdd);
                        tv_Smobile.setText(sendermobile);
                        tv_Sgstin.setText(senderGstin);

                        tv_Saddress.setEnabled(false);
                        tv_Saddress.setClickable(false);
                        tv_Smobile.setEnabled(false);
                        tv_Smobile.setClickable(false);
                        tv_Sgstin.setEnabled(false);
                        tv_Sgstin.setClickable(false);
                        /*editor.putString("senderAdd",senderAdd);
                        editor.putString("sendermobile",sendermobile);
                        editor.putString("senderGstin",senderGstin);
                        //editor.putString("senderartiCh",senderartiCh);
                        editor.commit();*/

                        lrprefix=senderdata.getLrprefix();

                        System.out.println("lr prefix isss"+lrprefix);

                        if(stLrCriteria.equals("Automatic")) {

                            if (stLrType.equals("ON A/C")) {

                                String l[] = tv_lrOne.getText().toString().trim().split("/");

                                String ls = l[0] + "/" + l[1] + "/" + lrprefix + "/" + l[2];

                                tv_Lrone.setText(ls);
                            }
                        }

                        appTariff.add(senderdata.getTariffname());
                        System.out.println("@@@@@@@@@@@TARIFF"+senderdata.getTariffname());
                        stAppTariff=senderdata.getTariffname();
                        stTariffId=senderdata.getTariffid();

                        dataAdaptersix = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,appTariff);

                        dataAdaptersix.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        sp_appTariff.setAdapter(dataAdaptersix);





                    }
                }
                else {

                    System.out.println("err response isss"+response.message());
                }

            }

            @Override
            public void onFailure(retrofit2.Call<List<SenderAddPojo>> call, Throwable t) {

            }
        });
    }

    public void loadApplicableTariff(String tC,String cid)
    {
        appTariff.clear();
        dataAdaptersix = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,appTariff);

        dataAdaptersix.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dataAdaptersix.clear();
        dataAdaptersix.notifyDataSetChanged();

        System.out.println("loading taridfffff....");

        Call<List<ApplicableTariffPojo>> call=REST_CLIENT.getApplicableTariff(tC,cid);
        call.enqueue(new Callback<List<ApplicableTariffPojo>>() {
            @Override
            public void onResponse(Call<List<ApplicableTariffPojo>> call, Response<List<ApplicableTariffPojo>> response) {

                ApplicableTariffPojo atp;
                List<ApplicableTariffPojo> atpList;
                if(response.isSuccessful())
                {
                    atpList=response.body();
                    appTariff.add("--select--");
                    for(int i=0;i<atpList.size();i++)
                    {
                        atp=atpList.get(0);

                        System.out.println("t"+i+":::"+atp.getTariffName());

                        appTariff.add(atp.getTariffName());
                        editor.putString("tariff",atp.getTariffName());
                        editor.commit();
                    }

                    dataAdaptersix = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,appTariff);

                    dataAdaptersix.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    sp_appTariff.setAdapter(dataAdaptersix);

                    sp_appTariff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {

                            //stAppTariff=sp_appTariff.getSelectedItem().toString();
                            // aTPos=position;

                            if(position==0)
                            {

                            }
                            else {

                                stAppTariff=sp_appTariff.getSelectedItem().toString();
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {


                        }
                    });


                }
                else {

                }
            }

            @Override
            public void onFailure(Call<List<ApplicableTariffPojo>> call, Throwable t) {

            }
        });


    }

    public void loadLrNo()
    {

        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println(stBrName+">>"+stLrType+">>"+stLrDate);
        JsonObject v=new JsonObject();
        v.addProperty("branchname",stBrName);
        v.addProperty("LRType",stLrType);
        v.addProperty("SelDate",stLrDate);

        Call<Pojo> call=REST_CLIENT.getLrNo(v);
        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {


                if(response.isSuccessful())
                {
                    System.out.println("resposne is"+response.body().getMessage());

                    tv_Lrtwo.setText(response.body().getMessage());

                    editor.putString("lrNo",response.body().getMessage());
                    editor.commit();
                }
                else {
                    System.out.println("resposne.. is"+response.message());

                }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {

                t.printStackTrace();

            }
        });
    }

    public void hubstatus(){

        JsonObject v=new JsonObject();
        v.addProperty("FromLocId",stFromLocId);
        v.addProperty("BranchName",stBrName);

        //System.out.println("FFFFFFFFFFFFFFFFFFFFFFF"+stFromLocId);
        //System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBB"+stBrName);

        Call<Pojo>call=REST_CLIENT.hubStatus(v);
        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                Pojo hubData;
                String s;
                if(response.isSuccessful()){

                    hubData=response.body();
                    s=hubData.getMessage();
                    String status=s.substring(0,4);
                    System.out.println("STTTTTTTTTTTTTtt"+status);
                    if(!status.equals("Both")){

                        yes=s.split("\\|")[1];

                        if(yes.equals("YES")){

                            sp_disPatchtype.setEnabled(true);
                            sp_disPatchtype.setSelection(0);
                        }
                        else if(yes.equals("NO")){

                            sp_disPatchtype.setEnabled(false);
                            sp_disPatchtype.setSelection(0);
                        }

                    }
                    else{
                        sp_disPatchtype.setEnabled(false);
                        sp_disPatchtype.setSelection(1);
                    }

                    // System.out.println("Hubissssssssss"+yes);



                }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {

            }
        });

    }

    public void loadLrNoForONAccount()
    {

        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println(stBrName+">>"+stLrType+">>"+stLrDate+">>"+stCustId);
        JsonObject v=new JsonObject();
        v.addProperty("branchname",stBrName);
        v.addProperty("LRType",stLrType);
        v.addProperty("Customerid",stCustId);
        v.addProperty("SelDate",stLrDate);

        Call<Pojo> call=REST_CLIENT.getLrNo(v);
        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {


                if(response.isSuccessful())
                {
                    System.out.println("resposne is"+response.body().getMessage());

                    tv_Lrtwo.setText(response.body().getMessage());
                    editor.putString("lrNo",response.body().getMessage());
                    editor.commit();
                }
                else {
                    System.out.println("resposne is"+response.message());


                }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {

            }
        });
    }

    public void getManualLRSeries()
    {
        System.out.println("manualLR size.."+manualLRSeries.size());

        dataAdapterSeven = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,manualLRSeries);

        dataAdapterSeven.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spManualLrSeries.setAdapter(dataAdapterSeven);
        spManualLrSeries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(i==0)
                {

                }
                else {


                    String ms = spManualLrSeries.getItemAtPosition(i).toString();

                    ManualSeriesPojo p;

                    for (int j = 0; j < msp.size(); j++) {
                        p = msp.get(j);

                        System.out.println("p & ms.."+p.getSeriesid()+"::"+ms);

                        if (p.getLrseries().equals(ms)) {
                            seriesId = p.getSeriesid();
                            getManualSeriesLRNO();
                            break;
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getManualSeriesLRNO()
    {
        System.out.println("sereisID.."+seriesId);
        JsonObject j=new JsonObject();
        j.addProperty("profileid",stProfileid);
        j.addProperty("seriesid",seriesId);

        Call<Pojo> call=REST_CLIENT.getMSLRNo(j);
        call.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {

                Pojo p;

                if(response.isSuccessful())
                {
                    p=response.body();

                    System.out.println("success msg..."+p.getMessage());

                    editor.putString("bookseriesid",seriesId);
                    editor.commit();


                    tv_Lrone.setText(p.getMessage());
                    tv_Lrtwo.setText("");
                    tv_Lrtwo.setVisibility(View.GONE);
                }
                else {

                    //System.out.println("err msg..."+response.message());
                    //Series range limit crossed'
                    Toast.makeText(getActivity(),"Error:"+response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {

                t.printStackTrace();

            }
        });
    }

    public void manualSeriesAPI()
    {
        manualLRSeries.clear();
        manualLRSeries.add("--select--");

        Call<List<ManualSeriesPojo>> call=REST_CLIENT.getManualSeries(stProfileid);
        call.enqueue(new Callback<List<ManualSeriesPojo>>() {
            @Override
            public void onResponse(Call<List<ManualSeriesPojo>> call, Response<List<ManualSeriesPojo>> response) {


                ManualSeriesPojo m;

                if(response.isSuccessful())
                {
                    msp=response.body();

                    for(int i=0;i<msp.size();i++)
                    {
                        m=msp.get(i);

                        manualLRSeries.add(m.getLrseries());

                    }

                    getManualLRSeries();
                }
                else {

                    Toast.makeText(getActivity(),"No data found!\n Change the LR Criteria.",Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<List<ManualSeriesPojo>> call, Throwable t) {

            }
        });
    }

    public void verisonChecking()
    {
        android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_version, null);
        dialogBuilder.setView(dialogView);

        Button ok = (Button) dialogView.findViewById(R.id.av_bt_ok);

        final android.support.v7.app.AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
                getActivity().finish();

                openAppRating(getActivity());
            }
        });

    }

    public static void openAppRating(Context context) {
        // you can also use BuildConfig.APPLICATION_ID
        String appId = context.getPackageName();
        Intent rateIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id=" + appId));
        boolean marketFound = false;

        // find all applications able to handle our rateIntent
        final List<ResolveInfo> otherApps = context.getPackageManager()
                .queryIntentActivities(rateIntent, 0);
        for (ResolveInfo otherApp : otherApps) {
            // look for Google Play application
            if (otherApp.activityInfo.applicationInfo.packageName
                    .equals("com.android.vending")) {

                ActivityInfo otherAppActivity = otherApp.activityInfo;
                ComponentName componentName = new ComponentName(
                        otherAppActivity.applicationInfo.packageName,
                        otherAppActivity.name
                );
                // make sure it does NOT open in the stack of your activity
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // task reparenting if needed
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                // if the Google Play was already open in a search result
                //  this make sure it still go to the app page you requested
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // this make sure only the Google Play app is allowed to
                // intercept the intent
                rateIntent.setComponent(componentName);
                context.startActivity(rateIntent);
                marketFound = true;
                break;

            }
        }
    }

}