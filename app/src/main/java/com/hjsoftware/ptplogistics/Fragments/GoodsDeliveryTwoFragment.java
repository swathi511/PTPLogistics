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

public class GoodsDeliveryTwoFragment extends Fragment {
    View v;
    EditText et_bFright,et_surcharge,et_lrChrges,et_aCharges,et_DD,et_Dc;
    EditText et_hamaliCg,et_Cc,et_cartageCh,et_withpassch,et_Pod;
    EditText et_lrVendr,et_bTax,et_gstOne,et_gstTwo,et_statnryCh,et_lrwithtwo;
    Spinner sp_Lrwitone,sp_time;
    EditText et_delHamali,et_demurageCh,et_salstax,et_grndtol;
    EditText et_goodsRecBy,et_contNo,et_remark;
    TextView tv_Del,tv_cancl;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       v= inflater.inflate(R.layout.delivery_note_two, container, false);


        et_bFright=(EditText)v.findViewById(R.id.dnt_et_basic_frght);
        et_surcharge=(EditText)v.findViewById(R.id.dnt_et_surcharge);
        et_lrChrges=(EditText)v.findViewById(R.id.dnt_et_lr_charg);
        et_aCharges=(EditText)v.findViewById(R.id.dnt_et_art_chrg);
        et_DD=(EditText)v.findViewById(R.id.dnt_et_dd_chrg);
        et_Dc=(EditText)v.findViewById(R.id.dnt_et_dc_chrg);
        et_hamaliCg=(EditText)v.findViewById(R.id.dnt_et_hamali_chrg);
        et_Cc=(EditText)v.findViewById(R.id.dnt_et_cc_chrg);
        et_cartageCh=(EditText)v.findViewById(R.id.dnt_et_cart_chrg);
        et_withpassch=(EditText)v.findViewById(R.id.dnt_et_with_pass_chrg);
        et_Pod=(EditText)v.findViewById(R.id.dnt_et_pod_chrg);
        et_lrVendr=(EditText)v.findViewById(R.id.dnt_et_vendr_amt);
        et_bTax=(EditText)v.findViewById(R.id.dnt_et_b_tax);
        et_gstOne=(EditText)v.findViewById(R.id.dnt_et_gst_one);
        et_gstTwo=(EditText)v.findViewById(R.id.dnt_et_gst_two);
        et_statnryCh=(EditText)v.findViewById(R.id.dnt_et_stat_chrg);
        et_lrwithtwo=(EditText)v.findViewById(R.id.dnt_et_lr_withpass_two);
        et_delHamali=(EditText)v.findViewById(R.id.dnt_et_del_hamali_chrg);
        et_demurageCh=(EditText)v.findViewById(R.id.dnt_et_demm_chrg);
        et_salstax=(EditText)v.findViewById(R.id.dnt_et_sales_amt);
        et_goodsRecBy=(EditText)v.findViewById(R.id.dnt_et_goods_rec_by);
        et_contNo=(EditText)v.findViewById(R.id.dnt_et_contact_no);
        et_remark=(EditText)v.findViewById(R.id.dnt_et_remarks);
        et_grndtol=(EditText)v.findViewById(R.id.dnt_et_grand_tot);
        sp_Lrwitone=(Spinner) v.findViewById(R.id.dnt_sp_lr_withpass_one);
        sp_time=(Spinner) v.findViewById(R.id.dnt_sp_time);
        tv_Del=(TextView) v.findViewById(R.id.dnt_tv_del);
        tv_cancl=(TextView)v.findViewById(R.id.dnt_tv_cancel);


        return v;
    }
}
