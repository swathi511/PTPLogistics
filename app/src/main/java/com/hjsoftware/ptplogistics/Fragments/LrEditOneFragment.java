package com.hjsoftware.ptplogistics.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjsoftware.ptplogistics.R;
import com.hjsoftware.ptplogistics.Webservices.API;
import com.hjsoftware.ptplogistics.Webservices.RestClient;
import com.hjsoftware.ptplogistics.model.LRMasterPojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class LrEditOneFragment extends Fragment{

    View v;
    TextView etLrCrit,etManualLRSeries,etLrType,etFocRemarks,etDest,etTariffCrit,etDispatchType,
    etDoorDelv,etSname;
    EditText etSgst,etSmobile,etSadd,etRname,etRgst,etRadd,etRmobile,etCustInv,etGoodsVal,etEwayBill;
    LRMasterPojo p;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    TextView tv_next;
    API REST_CLIENT;
    List<LRMasterPojo> lList;
    String lrno;
    TextView etLrNoOne,etLrNoTwo,etBdate,etSrc;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "SharedPref";
    LinearLayout llManualLrSeries;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        REST_CLIENT= RestClient.get();
        pref = getActivity().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

        lrno=getActivity().getIntent().getStringExtra("lr");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.lredit_one_fragment,container,false);

        etLrCrit=(TextView)v.findViewById(R.id.lredit_one_sp_lr_criteria);
        etManualLRSeries=(TextView)v.findViewById(R.id.lredit_one_sp_manual_lr_series);
        etLrType=(TextView)v.findViewById(R.id.lredit_one_sp_lr_type);
        etFocRemarks=(TextView)v.findViewById(R.id.lredit_one_et_foc_remarks);
        etLrNoOne=(TextView)v.findViewById(R.id.lredit_one_tv_lr_no_one);
        etLrNoTwo=(TextView)v.findViewById(R.id.lredit_one_tv_lr_no_two);
        etBdate=(TextView)v.findViewById(R.id.lredit_one_tv_lr_date);
        etSrc=(TextView)v.findViewById(R.id.lredit_one_tv_from_loc);
        etDest=(TextView)v.findViewById(R.id.lredit_one_sp_to_loc);
        etTariffCrit=(TextView)v.findViewById(R.id.lredit_one_sp_tarif_criter);
        etDispatchType=(TextView)v.findViewById(R.id.lredit_one_sp_dispatch_type);
        etDoorDelv=(TextView)v.findViewById(R.id.lredit_one_sp_door_del);

        etSname=(TextView)v.findViewById(R.id.lredit_one_sp_sender_name);
        etSgst=(EditText)v.findViewById(R.id.lredit_one_tv_send_gstin);
        etSmobile=(EditText)v.findViewById(R.id.lredit_one_tv_sendr_mobile_no);
        etSadd=(EditText)v.findViewById(R.id.lredit_one_tv_sendr_address);
        etRname=(EditText)v.findViewById(R.id.lredit_one_tv_recver_name);
        etRgst=(EditText)v.findViewById(R.id.lredit_one_tv_recvr_gstin);
        etRadd=(EditText)v.findViewById(R.id.lredit_one_tv_recvr_address);
        etRmobile=(EditText)v.findViewById(R.id.lredit_one_tv_rec_mobile_no);
        etCustInv=(EditText)v.findViewById(R.id.lredit_one_tv_cust_inv_no);
        etGoodsVal=(EditText)v.findViewById(R.id.lredit_one_tv_good_value);
        etEwayBill=(EditText)v.findViewById(R.id.lredit_one_tv_eway_bill);
        tv_next=(TextView)v.findViewById(R.id.lredit_one_tv_next);

        llManualLrSeries=(LinearLayout)v.findViewById(R.id.lredit_one_ll_manual_lr_series);
        llManualLrSeries.setVisibility(View.GONE);

        loadData();


        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                editor.putString("senderAdd",p.getSenderAddress());
                editor.putString("sendermobile",p.getSenderMobileno());
                editor.putString("senderGstin",p.getConsignorGSTIN());
                //editor.putString("senderartiCh",senderartiCh);
                editor.putString("rAddress", p.getReceiverAddress());
                editor.putString("rGStin",p.getConsigneeGSTIN());
                editor.putString("rMobile",p.getReceiverMobileno());

                editor.putString("toLoc", p.getToLocation());
                editor.putString("toLocId", p.getHTolid().split("-")[0]);
                editor.putString("tobranchid",p.getHTolid().split("-")[1]);
                editor.putString("tobranchname",p.getToBranch());

                editor.putString("fromLoc",p.getFromLocation());
                editor.putString("fromLocId",p.getHflid().split("-")[0]);
                editor.putString("branchId",p.getHflid().split("-")[1]);
                editor.putString("branchname",p.getFromBranch());


                editor.putString("focRemarks",p.getFocRemarks());
                editor.putString("lrtype",p.getLRType());
                //editor.putInt("ltPos",lTPos);


                editor.putString("lrDate",p.getLRDate());

                editor.putString("lrCriteria",p.getLrentrytype());
                //editor.putInt("lcPos",lCPos);

                editor.putString("recName",p.getReceiverName());

                editor.putString("lrNotwo","");
                editor.putString("lrNoOne",p.getLRNo());

                //for fromLoc & fromLocId see above code...

                //for toLoc & toLocId see above code...
                editor.putString("tariffCriteria",p.getTariffcriteria());
                //editor.putInt("tcPos",tCPos);

                editor.putString("dispatchType",p.getDispatchType());
                //editor.putInt("dtPos",dTPos);

                editor.putString("appTariff",p.getTariff());
                //editor.putInt("atPos",aTPos);

                editor.putString("doorDel",p.getDoorDeliveryType());
                //editor.putInt("ddPos",dDPos);

                editor.putString("destination",p.getToLocation());

                //editor.putString("othersender",et_othrSender.getText().toString());

                //editor.putInt("dePos",dePos);

                editor.putString("sendername",p.getSenderName());
                editor.putString("CustomerId",p.getSenderId());


                //editor.putInt("snPos",sNPos);
                //editor.putString("status",stStatus);
                //editor.putInt("sPos",sPos);

                editor.putString("goodsvalue",p.getGoodsValue());
                editor.putString("ewaybill",p.getEwaybill());
                editor.putString("custInvNo",p.getCInvno());

                //editor.putString("no",stNo+"/"+ Calendar.getInstance().get(Calendar.YEAR) % 100);

                editor.putBoolean("isCommitted",true);
                editor.commit();

                if(p.getLrentrytype().equals("Automatic"))
                {
                    editor.putString("bookseriesid","-");
                    editor.commit();
                }


                //System.out.println("ddddddddddd::"+stNo+"/"+Calendar.getInstance().get(Calendar.YEAR) % 100);


                Bundle bundle=new Bundle();
                //bundle.putStringArrayList("aList",lList);
                bundle.putSerializable("aList",p);

                Fragment frag = new LrEditThreeFragment();
                frag.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.content_frame, frag, "specific_ride");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });







        return v;
    }


    public void loadData()
    {

        System.out.println("lrno issss"+lrno);
        retrofit2.Call<List<LRMasterPojo>> call=REST_CLIENT.getLRData(lrno);
        call.enqueue(new Callback<List<LRMasterPojo>>() {
            @Override
            public void onResponse(retrofit2.Call<List<LRMasterPojo>> call, Response<List<LRMasterPojo>> response) {


                if(response.isSuccessful())
                {
                    lList=response.body();

                    //for(int i=0;i<lList.size();i++)
                    //{
                        p=lList.get(0);

                        loadLRData();
                   // }
                }
                else {

                    System.out.println("err..."+response.message());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<LRMasterPojo>> call, Throwable t) {

            }
        });
    }

    public void loadLRData()
    {
        etLrCrit.setText(p.getLrentrytype());

        if(p.getLrentrytype().equals("Manual"))
        {
            llManualLrSeries.setVisibility(View.GONE);
        }
        etManualLRSeries.setText(p.getLRNo());
        etLrType.setText(p.getLRType());
        etFocRemarks.setText(p.getFocRemarks());
        etLrNoOne.setText(p.getLRNo());
        etLrNoTwo.setVisibility(View.GONE);
        etBdate.setText(p.getLRDate().split(" ")[0]);
        etSrc.setText(p.getFromLocation()+"-"+p.getFromBranch());
        etDest.setText(p.getToLocation()+"-"+p.getToBranch());
        etTariffCrit.setText(p.getTariffcriteria());
        etDispatchType.setText(p.getDispatchType());
        etDoorDelv.setText(p.getDoorDeliveryType());
        etSname.setText(p.getSenderName());

        if(p.getSenderName().equals("Other Sender"))
        {
            editor.putString("otherSender","yes");
        }
        else {
            editor.putString("otherSender","no");
        }

        editor.commit();


        etSgst.setText(p.getConsignorGSTIN());
        etSmobile.setText(p.getSenderMobileno());
        etSadd.setText(p.getSenderAddress());
        etRname.setText(p.getReceiverName());
        etRgst.setText(p.getConsigneeGSTIN());
        etRadd.setText(p.getReceiverAddress());
        etRmobile.setText(p.getReceiverMobileno());
        etCustInv.setText(p.getCInvno());
        etGoodsVal.setText(p.getGoodsValue());
        etEwayBill.setText(p.getEwaybill());
    }

}
