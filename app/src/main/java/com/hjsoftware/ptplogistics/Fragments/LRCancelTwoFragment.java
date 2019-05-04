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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hjsoftware.ptplogistics.R;

public class LRCancelTwoFragment extends Fragment {

    View v;
    EditText et_Sgdtin,et_Rgstin,et_Sname,et_Rname,et_Saddress,et_Raddress,et_Smobile;
    EditText et_Rmobile,et_CustNo,et_goodValue,et_Ewaybil;
    Spinner sp_valSurchrge;
    TextView tv_next;
    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.lr_cancel_two, container, false);


        et_Sgdtin=(EditText)v.findViewById(R.id.lr_status_two_et_send_gstin);
        et_Rgstin=(EditText)v.findViewById(R.id.lr_status_two_et_recvr_gstin);
        et_Sname=(EditText)v.findViewById(R.id.lr_status_two_et_sendr_name);
        et_Rname=(EditText)v.findViewById(R.id.lr_status_two_et_recver_name);
        et_Saddress=(EditText)v.findViewById(R.id.lr_status_two_et_sendr_address);
        et_Raddress=(EditText)v.findViewById(R.id.lr_status_two_et_recvr_address);
        et_Smobile=(EditText)v.findViewById(R.id.lr_status_two_et_sendr_mobile_no);
        et_Rmobile=(EditText)v.findViewById(R.id.lr_status_two_et_rec_mobile_no);
        et_CustNo=(EditText)v.findViewById(R.id.lr_status_two_et_cust_inv_no);
        et_goodValue=(EditText)v.findViewById(R.id.nlr_status_two_et_good_value);
        et_Ewaybil=(EditText)v.findViewById(R.id.lr_status_two_et_eway_bill);
        tv_next=(TextView)v.findViewById(R.id.lr_status_two_tv_next);
        sp_valSurchrge=(Spinner) v.findViewById(R.id.lr_status_two_sp_value_sur_charge);

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new LRCancelThreeFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, frag, "specific_ride");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return v;
    }
}
