package com.hjsoftware.ptplogistics.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hjsoftware.ptplogistics.Adapters.SessionManager;
import com.hjsoftware.ptplogistics.R;
import com.hjsoftware.ptplogistics.Webservices.API;
import com.hjsoftware.ptplogistics.Webservices.RestClient;
import com.hjsoftware.ptplogistics.model.LRMasterPojo;

import java.util.ArrayList;
import java.util.HashMap;

public class LREditFourOtherFragment extends Fragment {

    View v;
    EditText et_old_bFright,et_new_bFright,et_aCharges,et_valCharges,et_newDD,et_oldDD;
    EditText et_Dc,et_hamaliCg,et_Cc,et_LrCg,et_Pod,etGoodsValue,sp_LrPass;
    EditText et_BeGst,et_withpassch,et_SGstone,et_SGsttwo,et_grndTotal,et_surcharge,etCGstOne,etCGstTwo,etIGstOne,etIGstTwo;
    Spinner spVSStatus;
    TextView tv_Cal,tv_save,tv_cancel,tv_home;

    SharedPreferences pref;
    String stLrPass="";
    API REST_CLIENT;
    RecyclerView rView;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "SharedPref";
    LRMasterPojo p;

    String stProfileid,stFromLocId,stotherSender,yes;
    String stCustomerId,stTariffCri,stlrDate,stTolocId,stLrType;
    String stTotalAmount,stBefGSTAmnt,stDCCharg,stCCCharg,stbFright,stFrightAfter,stAcharges,stValCharges,stNewDD,stHamaliCh,stOldDD,
            stLrCh,stPODCh,stSurch,stWithPassCh="",stSGSTOne,stSGSTtwo,stCGSTOne,stCGSTtwo,stIGSTOne,stIGSTtwo;
    String [] lrWithPass ={"Not Applicable","Applicable"};
    String [] valueSurchareStatus ={"Not Applicable","Applicable"};



    Integer value=750;
    Integer Amount=0;

    String totalQty,totalWeight;
    double bf=0.0,ac=0.0,vs=0.0,ndd=0.0,dc=0.0,hc=0.0,cc=0.0,lrch=0.0,podch=0.0,sc=0.0,wpc=0.0,sgst=0.0,cgstnt=0.0,igstnt=0.0;
    String cnorGST="",cneeGST="";
    String stax="",cgst="",igst="",servicetax="",stGrandTotal,stGoodsValue,stLrNo,stLrNoOne,stLrNoTwo;

    DatePickerDialog datePickerDialog;
    String fromdate;
    String formattedDate;
    String fromLoc,toLoc,lrDate,stArticleCh,stPod;
    String plrValue="",vsStatus="";
    ProgressDialog progressDialog;
    String rate,extraRate;
    SessionManager session;
    ArrayList<String> articlelist = new ArrayList<String>();
    HashMap<String, String> user;

    //LRMasterPojo p;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.lredit_four_other_fragment, container, false);


        et_old_bFright = (EditText) v.findViewById(R.id.lredit_fourth_other_et_basic_frieght);
        et_new_bFright = (EditText) v.findViewById(R.id.lredit_fourth_other_et_frigt_aftr_disc);
        et_aCharges = (EditText) v.findViewById(R.id.lredit_fourth_other_et_art_charges);
        et_valCharges = (EditText) v.findViewById(R.id.lredit_fourth_other_et_valsur_charges);
        et_newDD = (EditText) v.findViewById(R.id.lredit_fourth_other_et_ndd_charges);
        et_Dc = (EditText) v.findViewById(R.id.lredit_fourth_other_et_dc_charges);
        et_hamaliCg = (EditText) v.findViewById(R.id.lredit_fourth_other_et_ham_charges);
        et_Cc = (EditText) v.findViewById(R.id.lredit_fourth_other_et_cc_charges);
        et_oldDD = (EditText) v.findViewById(R.id.lredit_fourth_other_et_odd_charges);
        et_LrCg = (EditText) v.findViewById(R.id.lredit_fourth_other_et_lr_charges);
        et_Pod = (EditText) v.findViewById(R.id.lredit_fourth_other_et_pod_charges);
        et_BeGst = (EditText) v.findViewById(R.id.lredit_fourth_other_et_gst);
        et_withpassch = (EditText) v.findViewById(R.id.lredit_fourth_other_et_with_pass_charges);
        et_SGstone = (EditText) v.findViewById(R.id.lredit_fourth_other_et_stax_one);
        et_SGsttwo = (EditText) v.findViewById(R.id.lredit_fourth_other_et_stax_two);
        et_grndTotal = (EditText) v.findViewById(R.id.lredit_fourth_other_et_grand_total);
        et_surcharge = (EditText) v.findViewById(R.id.lredit_fourth_other_et_sur_charges);
        sp_LrPass = (EditText) v.findViewById(R.id.lredit_fourth_other_sp_lr_with_pass);
        tv_Cal = (TextView) v.findViewById(R.id.lredit_fourth_other_tv_cal);
        //tv_cancel = (TextView) v.findViewById(R.id.new_lrb_fourth_tv_cancel);
        tv_save = (TextView) v.findViewById(R.id.lredit_fourth_other_tv_save);
        tv_home = (TextView) v.findViewById(R.id.lredit_fourth_other_tv_home);
        spVSStatus = (Spinner) v.findViewById(R.id.lredit_fourth_other_sp_vs_status);
        etGoodsValue = (EditText) v.findViewById(R.id.lredit_fourth_other_tv_goods_value);

        etCGstOne = (EditText) v.findViewById(R.id.lredit_fourth_other_et_ctax_one);
        etCGstTwo = (EditText) v.findViewById(R.id.lredit_fourth_other_et_ctax_two);
        etIGstOne = (EditText) v.findViewById(R.id.lredit_fourth_other_et_itax_one);
        etIGstTwo = (EditText) v.findViewById(R.id.lredit_fourth_other_et_itax_two);

        p=(LRMasterPojo) getArguments().getSerializable("aList");

        et_old_bFright.setText(p.getSubTotal());
        et_new_bFright.setText(p.getSubTotal());
        et_aCharges.setText(p.getArticleChg());
        et_valCharges.setText(p.getValuesurcharge());
        sp_LrPass.setText(p.getLRPassStatus());
        et_newDD.setText(p.getDdCharges());
        et_Dc.setText(p.getDcCharges());
        et_hamaliCg.setText(p.getHamaliCharges());
        et_Cc.setText(p.getCcCharges());
        //et_oldDD.setText(p.getDdCharges());
        et_oldDD.setText("");
        et_LrCg.setText(p.getLrcharges());
        et_Pod.setText(p.getPodcharges());
        et_BeGst.setText(p.getBeforeStax());
        et_withpassch.setText(p.getWithPasschg());
        et_SGstone.setText(p.getServiceTax());
        etCGstOne.setText(p.getCgstper());
        etCGstTwo.setText(p.getCgstamt());
        etIGstOne.setText(p.getIgstper());
        etIGstTwo.setText(p.getIgstamt());
        et_grndTotal.setText(p.getTotAmount());
        et_surcharge.setText(p.getSurCharges());

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
        //stLrNoTwo=pref.getString("lrNotwo",null);
        fromLoc=pref.getString("fromLoc",null);
        toLoc=pref.getString("destination",null);
        lrDate=pref.getString("lrDate",null);
        stArticleCh=pref.getString("senderartiCh",null);
        stPod=pref.getString("pod",null);
        cnorGST=pref.getString("senderGstin","");
        cneeGST=pref.getString("rGStin","");
        stLrType=pref.getString("lrtype","");

        rate=pref.getString("rate","0");
        extraRate=pref.getString("extraRate","0");

        System.out.println("r & er & ta.."+rate+"::"+extraRate+"::"+stTotalAmount);

        System.out.println("custid..."+stCustomerId);
        System.out.println("custname..."+pref.getString("sendername",""));

        //bf=Double.parseDouble(stTotalAmount);
        //stLrNo=stLrNoOne;


        return v;

    }
}
