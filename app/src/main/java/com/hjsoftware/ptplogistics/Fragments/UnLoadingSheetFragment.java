package com.hjsoftware.ptplogistics.Fragments;

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

import com.hjsoftware.ptplogistics.R;

public class UnLoadingSheetFragment extends Fragment {
    View v;
    EditText et_dateCri,et_vehNo,et_disKm,et_unloadBy,et_vehKm;
    Spinner sp_disNos,sp_time;
    TextView tv_load;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.unloading_sheet_details, container, false);


        et_dateCri=(EditText)v.findViewById(R.id.usd_et_date_criteria);
        et_vehNo=(EditText)v.findViewById(R.id.usd_et_vehicle_no);
        et_disKm=(EditText)v.findViewById(R.id.usd_et_dispatch_km);
        et_unloadBy=(EditText)v.findViewById(R.id.usd_et_unloading_by);
        et_vehKm=(EditText)v.findViewById(R.id.usd_et_vehicle_kms);
        sp_disNos=(Spinner)v.findViewById(R.id.usd_sp_dispacth_nos);
        sp_time=(Spinner)v.findViewById(R.id.usd_sp_time);
        tv_load=(TextView)v.findViewById(R.id.usd_tv_load);
        return v;
    }
}
