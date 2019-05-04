package com.hjsoftware.ptplogistics.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hjsoftware.ptplogistics.R;

public class GoodsDeliveryOneFragment extends Fragment {
    View v;
    Spinner sp_seType,sp_disType,sp_status,sp_bSource,sp_deType;
    Spinner sp_lrNo,sp_lrType,sp_fromloc,sp_toloc;
    Spinner sp_selTarif,sp_calCri,sp_doorDel;
    EditText et_sName,et_sMobile,et_sAddress,et_rName,et_rMobile,et_rAddress;
    EditText et_custNo,et_goodsValue;
    CheckBox cb_sender,cb_receiver;
    TextView tv_next;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.delivery_note_one, container, false);


        sp_seType=(Spinner)v.findViewById(R.id.dno_sp_search_type);
        sp_disType=(Spinner)v.findViewById(R.id.dno_sp_dispatch_type);
        sp_status=(Spinner)v.findViewById(R.id.dno_sp_status);
        sp_bSource=(Spinner)v.findViewById(R.id.dno_sp_booking_source);
        sp_deType=(Spinner)v.findViewById(R.id.dno_sp_delivery_type);
        sp_lrNo=(Spinner)v.findViewById(R.id.dno_sp_lr_no);
        sp_lrType=(Spinner)v.findViewById(R.id.dno_sp_lr_type);
        sp_fromloc=(Spinner)v.findViewById(R.id.dno_sp_from_loc);
        sp_toloc=(Spinner)v.findViewById(R.id.dno_sp_to_loc);
        sp_selTarif=(Spinner)v.findViewById(R.id.dno_sp_select_tarif);
        sp_calCri=(Spinner)v.findViewById(R.id.dno_sp_cal_criteria);
        sp_doorDel=(Spinner)v.findViewById(R.id.dno_sp_door_delivery);
        tv_next=(TextView)v.findViewById(R.id.dno_tv_next);
        et_sName=(EditText)v.findViewById(R.id.dno_et_sender_name);
        et_sMobile=(EditText)v.findViewById(R.id.dno_et_sender_mobile);
        et_sAddress=(EditText)v.findViewById(R.id.dno_et_sender_address);
        et_rName=(EditText)v.findViewById(R.id.dno_et_receiver_name);
        et_rMobile=(EditText)v.findViewById(R.id.dno_et_receiver_mobile);
        et_rAddress=(EditText)v.findViewById(R.id.dno_et_receiver_address);
        et_custNo=(EditText)v.findViewById(R.id.dno_et_cust_inv_no);
        et_goodsValue=(EditText)v.findViewById(R.id.dno_et_goods_value);
        cb_sender=(CheckBox) v.findViewById(R.id.dno_cb_sms_sender);
        cb_receiver=(CheckBox) v.findViewById(R.id.dno_cb_sms_receiver);

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new GoodsDeliveryTwoFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, frag, "specific_ride");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return  v;
    }
}
