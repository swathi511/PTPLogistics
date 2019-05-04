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

public class LRCancelThreeFragment  extends Fragment{
    View v;

    Spinner sp_artDetails;
    EditText et_artDetails,et_chWeight,et_actWeight,et_artDes,et_Quant;
    EditText et_length,et_width,et_height;
    TextView tv_update,tv_next;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       v= inflater.inflate(R.layout.lr_cancel_third, container, false);

        sp_artDetails=(Spinner)v.findViewById(R.id.lr_status_third_sp_artcle_name_one);
        et_artDetails=(EditText)v.findViewById(R.id.lr_status_third_et_artcle_name_two);
        et_chWeight=(EditText)v.findViewById(R.id.lr_status_third_et_charged_wgt);
        et_actWeight=(EditText)v.findViewById(R.id.lr_status_third_et_actual_wgt);
        et_artDes=(EditText)v.findViewById(R.id.lr_status_third_et_artcle_desc);
        et_Quant=(EditText)v.findViewById(R.id.lr_status_third_et_quantity);
        et_length=(EditText)v.findViewById(R.id.lr_status_third_et_length);
        et_width=(EditText)v.findViewById(R.id.lr_status_third_et_width);
        et_height=(EditText)v.findViewById(R.id.lr_status_third_et_height);
        tv_update=(TextView)v.findViewById(R.id.lr_status_third_tv_update);
        tv_next=(TextView)v.findViewById(R.id.lr_status_third_tv_next);


        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new NewLrBookingFourFragment();
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
