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

public class LoadingSheetFragment extends Fragment {
    Spinner sp_vehAdv,sp_driverOrg,sp_driverPayType,sp_disType;
    Spinner sp_driverOne,sp_fromLoc,sp_driverTwo,sp_fromBranch;
    Spinner sp_attenderName,sp_vehSup,sp_vehNo,sp_route;
    EditText et_vehKM,et_payAmt,et_loadingBy,et_disOne,et_disTwo;
    TextView tv_date,tv_Fromdate,tv_Todate;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       v=inflater.inflate(R.layout.loading_sheet_new, container, false);


        sp_vehAdv=(Spinner)v.findViewById(R.id.ls_sp_vehicle_adv_status);
        sp_driverOrg=(Spinner)v.findViewById(R.id.ls_sp_driver_org);
        sp_driverPayType=(Spinner)v.findViewById(R.id.ls_sp_driver_pay_type);
        sp_disType=(Spinner)v.findViewById(R.id.ls_sp_dispatch_type);
        sp_driverOne=(Spinner)v.findViewById(R.id.ls_sp_driver_name_one);
        sp_fromLoc=(Spinner)v.findViewById(R.id.ls_sp_from_loc);
        sp_driverTwo=(Spinner)v.findViewById(R.id.ls_sp_driver_name_two);
        sp_fromBranch=(Spinner)v.findViewById(R.id.ls_sp_from_branch);
        sp_attenderName=(Spinner)v.findViewById(R.id.ls_sp_attender_name);
        sp_vehSup=(Spinner)v.findViewById(R.id.ls_sp_vehicle_sup);
        sp_vehNo=(Spinner)v.findViewById(R.id.ls_sp_vehicle_no);
        sp_route=(Spinner)v.findViewById(R.id.ls_sp_route);
        et_vehKM=(EditText) v.findViewById(R.id.ls_et_vehicle_km);
        et_payAmt=(EditText)v.findViewById(R.id.ls_et_payment_amount);
        et_loadingBy=(EditText)v.findViewById(R.id.ls_et_loaded_by);
        et_disOne=(EditText)v.findViewById(R.id.ls_et_dispatch_one);
        et_disTwo=(EditText)v.findViewById(R.id.ls_et_dispatch_two);
        tv_date=(TextView) v.findViewById(R.id.ls_tv_date);
        tv_Fromdate=(TextView)v.findViewById(R.id.ls_tv_from_date);
        tv_Todate=(TextView)v.findViewById(R.id.ls_tv_to_date);
       return v;
    }
}
