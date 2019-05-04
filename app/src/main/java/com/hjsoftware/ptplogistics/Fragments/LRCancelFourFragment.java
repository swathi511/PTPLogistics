package com.hjsoftware.ptplogistics.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hjsoftware.ptplogistics.Activity.HomeActivity;
import com.hjsoftware.ptplogistics.R;

public class LRCancelFourFragment extends Fragment {

    EditText et_bFright,et_frightAfter,et_aCharges,et_valCharges,et_newDD;
    EditText et_Dc,et_hamaliCg,et_Cc,et_oldDD,et_LrCg,et_Pod;
    EditText et_BeGst,et_withpassch,et_Gstone,et_Gsttwo,et_grndTotal,et_surcharge;
    Spinner sp_LrPass;
    TextView tv_save,tv_cancel,tv_home;

    View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.lr_cancel_fourth, container,false);

        et_bFright=(EditText)v.findViewById(R.id.lr_status_fourth_et_basic_frieght);
        et_frightAfter=(EditText)v.findViewById(R.id.lr_status_fourth_et_frigt_aftr_disc);
        et_aCharges=(EditText)v.findViewById(R.id.lr_status_fourth_et_art_charges);
        et_valCharges=(EditText)v.findViewById(R.id.lr_status_fourth_et_valsur_charges);
        et_newDD=(EditText)v.findViewById(R.id.lr_status_fourth_et_ndd_charges);
        et_Dc=(EditText)v.findViewById(R.id.lr_status_fourth_et_dc_charges);
        et_hamaliCg=(EditText)v.findViewById(R.id.lr_status_fourth_et_ham_charges);
        et_Cc=(EditText)v.findViewById(R.id.lr_status_fourth_et_cc_charges);
        et_oldDD=(EditText)v.findViewById(R.id.lr_status_fourth_et_olddd_charges);
        et_LrCg=(EditText)v.findViewById(R.id.lr_status_fourth_et_lr_charges);
        et_Pod=(EditText)v.findViewById(R.id.lr_status_fourth_et_pod_charges);
        et_BeGst=(EditText)v.findViewById(R.id.lr_status_fourth_et_gst);
        et_withpassch=(EditText)v.findViewById(R.id.lr_status_fourth_et_with_pass_charges);
        et_Gstone=(EditText)v.findViewById(R.id.lr_status_fourth_et_stax_one);
        et_Gsttwo=(EditText)v.findViewById(R.id.lr_status_fourth_et_stax_two);
        et_grndTotal=(EditText)v.findViewById(R.id.lr_status_fourth_et_grand_total);
        et_surcharge=(EditText)v.findViewById(R.id.lr_status_fourth_et_sur_charges);
        sp_LrPass=(Spinner)v.findViewById(R.id.lr_status_fourth_sp_lr_with_pass);
        tv_cancel=(TextView)v.findViewById(R.id.lr_status_fourth_tv_cancel);
        tv_save=(TextView)v.findViewById(R.id.lr_status_fourth_tv_save);
        tv_home=(TextView)v.findViewById(R.id.lr_status_fourth_tv_home);

        tv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(), HomeActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        return v;
    }
}
