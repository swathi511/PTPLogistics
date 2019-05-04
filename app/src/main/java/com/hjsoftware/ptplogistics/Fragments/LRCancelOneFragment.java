package com.hjsoftware.ptplogistics.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hjsoftware.ptplogistics.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LRCancelOneFragment extends Fragment {
    View v;
    Spinner sp_lrType,sp_lrCriteria,sp_FromLoc,sp_tarifCri,sp_disType;
    Spinner sp_appTarif,sp_doorDel,sp_status;
    TextView tv_lrDate,tv_lrCanclDate,tv_next;
    EditText et_lrOne,et_lrTwo,et_narration;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.lr_cancel_one, container, false);

        sp_lrType=(Spinner)v.findViewById(R.id.lr_status_one_sp_lrtype);
        sp_lrCriteria=(Spinner)v.findViewById(R.id.lr_status_one_sp_lr_criteria);
        sp_FromLoc=(Spinner)v.findViewById(R.id.lr_status_one_sp_from_loc);
        sp_tarifCri=(Spinner)v.findViewById(R.id.lr_status_one_sp_tarif_criteria);
        sp_disType=(Spinner)v.findViewById(R.id.lr_status_one_sp_dispatch_type);
        sp_appTarif=(Spinner)v.findViewById(R.id.lr_status_one_sp_app_tarif);
        sp_doorDel=(Spinner)v.findViewById(R.id.lr_status_one_sp_door_delivery);
        sp_status=(Spinner)v.findViewById(R.id.lr_status_one_sp_status);
        et_lrOne=(EditText)v.findViewById(R.id.lr_status_one_et_lrno_one);
        et_lrTwo=(EditText)v.findViewById(R.id.lr_status_one_et_lrno_two);
        et_narration=(EditText)v.findViewById(R.id.lr_status_one_et_lr_narration);
        tv_lrDate=(TextView) v.findViewById(R.id.lr_status_one_tv_lrdate);
        tv_lrCanclDate=(TextView) v.findViewById(R.id.lr_status_one_tv_lr_cancel_date);
        tv_next=(TextView)v.findViewById(R.id.lr_status_one_tv_next);




        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new LRCancelTwoFragment();
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
