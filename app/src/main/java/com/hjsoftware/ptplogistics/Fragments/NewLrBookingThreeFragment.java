package com.hjsoftware.ptplogistics.Fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hjsoftware.ptplogistics.Activity.BarcodeActivity;
import com.hjsoftware.ptplogistics.Activity.ChangePasswordActivity;
import com.hjsoftware.ptplogistics.Activity.HomeActivity;
import com.hjsoftware.ptplogistics.Activity.MainActivity;
import com.hjsoftware.ptplogistics.Adapters.SessionManager;
import com.hjsoftware.ptplogistics.R;
import com.hjsoftware.ptplogistics.Webservices.API;
import com.hjsoftware.ptplogistics.Webservices.RestClient;
import com.hjsoftware.ptplogistics.model.ArticleDetailsPojo;
import com.hjsoftware.ptplogistics.model.CalAmountPojo;
import com.hjsoftware.ptplogistics.model.Pojo;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class NewLrBookingThreeFragment extends Fragment {

    private static final String PREF_NAME = "SharedPref";
    View v;
    Spinner sp_artDetailsOne;
    EditText et_chWeightOne,et_actWeightOne,et_artDesOne,et_QuantOne;
    EditText et_artDetailsOne,et_lengthOne,et_widthOne,et_heightOne;
    Spinner sp_artDetailsTwo;
    EditText et_chWeightTwo,et_actWeightTwo,et_artDesTwo,et_QuantTwo;
    EditText et_artDetailsTwo,et_lengthTwo,et_widthTwo,et_heightTwo;
    Spinner sp_artDetailsThree;
    EditText et_chWeightThree,et_actWeightThree,et_artDesThree,et_QuantThree;
    EditText et_artDetailsThree,et_lengthThree,et_widthThree,et_heightThree;
    Spinner sp_barCode;
    TextView tv_barCodeVOne,tv_barCodeVTwo,tv_barCodeVThree;
    EditText et_artOtherDec,et_artOtherDecOne,et_artOtherDecTwo;
    LinearLayout ll_LayoutOne,ll_LayoutTwo,ll_LayoutThree;
    TextView tv_artOne,tv_artTwo,tv_cal;
    SharedPreferences pref;
    String stArtdesOne="",stArtdesTwo="",stArtdesThree="";
    String stBarcodeOne="",stBarcodeTwo="",stBarcodeThree="";
    API REST_CLIENT;
    final static int REQUEST_LOCATION = 199;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    SessionManager session;
    ArrayList<String> articlelistOne = new ArrayList<String>();
    ArrayList<String> articlelistTwo = new ArrayList<String>();
    ArrayList<String> articlelistThree = new ArrayList<String>();
    HashMap<String, String> user;
    String[] barcodes={"Not Required","Required"};
    String stProfileid,barItem;
    String stCustomerId="",artItemOne="na",artItemTwo="na",artItemThree="na";
    Integer aPOne=0,aPTwo=0,aPThree=0;
    Boolean isCommitted;
    String stactWeightOne="",stactWeightTwo="",stactWeightThree="";
    String stchWeightOne="",stchWeightTwo="",stchWeightthree="";
    String stQuantOne="",stQuantTwo="",stQuantThree="";

    Integer actWtOne=0,actWtTwo = 0,actWtThree = 0;
    Integer qtyOne=0,qtyTwo = 0,qtyThree = 0;
    Integer chdwtOne=0,chdwtTwo = 0,chdwtThree = 0;
    Integer totalActwt=0,totalqty=0,totalchdwt=0;

    String stBranchid,stTarif,stlrType,stFroLocid,stToLocid,stTarifcritera;
    Button btOk,btCancel;
    private ZXingScannerView scannerView;
    DatePickerDialog datePickerDialog;
    String fromdate,todate;
    List<ArticleDetailsPojo> articleStatus;

    /*

    If Tariff Criteria is Regular then no need to pass Customerid.

If Tariff Criteria is Article based then Pass customer id

     */




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        System.out.println("onCreate called...");
        pref = getActivity().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.putBoolean("isCommitted",false);
        editor.commit();

        stCustomerId=pref.getString("CustomerId","");
        stTarifcritera=pref.getString("tarifCriteria","");

    }

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.new_lrbooking_three, container, false);

        et_artDetailsOne=(EditText)v.findViewById(R.id.new_lrb_third_et_artcle_name_one);
        et_chWeightOne=(EditText)v.findViewById(R.id.new_lrb_third_et_charged_wgt);
        et_actWeightOne=(EditText)v.findViewById(R.id.new_lrb_third_et_actual_wgt);
        et_artDesOne=(EditText)v.findViewById(R.id.new_lrb_third_et_artcle_desc);
        et_QuantOne=(EditText)v.findViewById(R.id.new_lrb_third_et_quantity);
        et_lengthOne=(EditText)v.findViewById(R.id.new_lrb_third_et_length);
        et_widthOne=(EditText)v.findViewById(R.id.new_lrb_third_et_width);
        et_heightOne=(EditText)v.findViewById(R.id.new_lrb_third_et_height);

        et_artDetailsTwo=(EditText)v.findViewById(R.id.new_lrb_third_et_artcle_name_two);
        et_chWeightTwo=(EditText)v.findViewById(R.id.new_lrb_third_et_charged_wgt_two);
        et_actWeightTwo=(EditText)v.findViewById(R.id.new_lrb_third_et_actual_wgt_two);
        et_artDesTwo=(EditText)v.findViewById(R.id.new_lrb_third_et_artcle_desc_two);
        et_QuantTwo=(EditText)v.findViewById(R.id.new_lrb_third_et_quantity_two);
        et_lengthTwo=(EditText)v.findViewById(R.id.new_lrb_third_et_length_two);
        et_widthTwo=(EditText)v.findViewById(R.id.new_lrb_third_et_width_two);
        et_heightTwo=(EditText)v.findViewById(R.id.new_lrb_third_et_height_two);

        et_artDetailsThree=(EditText)v.findViewById(R.id.new_lrb_third_et_artcle_name_three);
        et_chWeightThree=(EditText)v.findViewById(R.id.new_lrb_third_et_charged_wgt_three);
        et_actWeightThree=(EditText)v.findViewById(R.id.new_lrb_third_et_actual_wgt_three);
        et_artDesThree=(EditText)v.findViewById(R.id.new_lrb_third_et_artcle_desc_three);
        et_QuantThree=(EditText)v.findViewById(R.id.new_lrb_third_et_quantity_three);
        et_lengthThree=(EditText)v.findViewById(R.id.new_lrb_third_et_length_three);
        et_widthThree=(EditText)v.findViewById(R.id.new_lrb_third_et_width_three);
        et_heightThree=(EditText)v.findViewById(R.id.new_lrb_third_et_height_three);

        sp_artDetailsOne=(Spinner)v.findViewById(R.id.new_lrb_third_sp_artcle_name_one);
        sp_artDetailsTwo=(Spinner)v.findViewById(R.id.new_lrb_third_sp_artcle_name_two);
        sp_artDetailsThree=(Spinner)v.findViewById(R.id.new_lrb_third_sp_artcle_name_three);

        tv_artOne=(TextView)v.findViewById(R.id.new_lrb_third_tv_add_article);
        tv_artTwo=(TextView)v.findViewById(R.id.new_lrb_third_tv_add_article_two);
        tv_cal=(TextView)v.findViewById(R.id.new_lrb_third_tv_cal);

        ll_LayoutOne=(LinearLayout)v.findViewById(R.id.new_lrb_third_ll_one_layout);
        ll_LayoutTwo=(LinearLayout)v.findViewById(R.id.new_lrb_third_ll_two_layout);
        ll_LayoutThree=(LinearLayout)v.findViewById(R.id.new_lrb_third_ll_three_layout);

        isCommitted=pref.getBoolean("isCommitted",false);

        sp_barCode=(Spinner)v.findViewById(R.id.new_lrb_third_sp_barcode);

        et_artOtherDec=(EditText)v.findViewById(R.id.new_lrb_third_et_artcle_other);
        et_artOtherDecOne=(EditText)v.findViewById(R.id.new_lrb_third_et_artcle_other_one);
        et_artOtherDecTwo=(EditText)v.findViewById(R.id.new_lrb_third_et_artcle_other_two);


        et_artOtherDec.setVisibility(View.GONE);
        et_artOtherDecTwo.setVisibility(View.GONE);
        et_artOtherDecOne.setVisibility(View.GONE);
        tv_barCodeVOne=(TextView)v.findViewById(R.id.new_lrb_third_tv_barcode_value_one);
        tv_barCodeVTwo=(TextView)v.findViewById(R.id.new_lrb_third_tv_barcode_value_two);
        tv_barCodeVThree=(TextView)v.findViewById(R.id.new_lrb_third_tv_barcode_value_three);

        sp_barCode.setEnabled(false);
        sp_barCode.setClickable(false);


        REST_CLIENT= RestClient.get();
        pref = getActivity().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        session=new SessionManager(getContext());
        user=session.getUserDetails();
        stProfileid=user.get(SessionManager.KEY_PROFILE_ID);


        ArticleDetails();

        //  stCustomerId=pref.getString("CustomerId",null);



        tv_artOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(artItemOne.equals("--select--")){
                    Toast.makeText(getActivity(), "Please select article name!", Toast.LENGTH_SHORT).show();

                }
                else if(et_actWeightOne.getText().toString().equals("")||et_actWeightOne.getText().toString().equals("0")){

                    Toast.makeText(getActivity(), "Please enter Actual weight!", Toast.LENGTH_SHORT).show();

                }
                else if(et_chWeightOne.getText().toString().equals("")||et_chWeightOne.getText().toString().equals("0")){
                    Toast.makeText(getActivity(), "Please enter Charged weight!", Toast.LENGTH_SHORT).show();

                }
                else if(et_QuantOne.getText().toString().equals("")|| et_QuantOne.getText().toString().equals("0")){
                    Toast.makeText(getActivity(), "Please enter Quantity!", Toast.LENGTH_SHORT).show();

                }
                /*else if(artItemOne.equals("Others"))
                {

                    System.out.print("@@@@@@@@@@@@?????????"+artItemOne);
                    et_artOtherDec.setVisibility(View.VISIBLE);
                    et_artDetailsOne.setVisibility(View.GONE);

                    if(et_artOtherDec.getText().toString().equals("")){
                        Toast.makeText(getActivity(), "Please mention other article name!", Toast.LENGTH_SHORT).show();
                    }
                }*/

                else{
                    tv_artOne.setVisibility(View.GONE);
                    ll_LayoutTwo.setVisibility(View.VISIBLE);
                }

            }
        });


        tv_artTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(artItemTwo.equals("--select--")){
                    Toast.makeText(getActivity(), "Please select article name!", Toast.LENGTH_SHORT).show();

                }
                else if(et_actWeightTwo.getText().toString().equals("")||et_actWeightTwo.getText().toString().equals("0")){

                    Toast.makeText(getActivity(), "Please enter Actual weight!", Toast.LENGTH_SHORT).show();

                }
                else if(et_chWeightTwo.getText().toString().equals("")||et_chWeightTwo.getText().toString().equals("0")){
                    Toast.makeText(getActivity(), "Please enter Charged weight!", Toast.LENGTH_SHORT).show();

                }
                else if(et_QuantTwo.getText().toString().equals("")|| et_QuantTwo.getText().toString().equals("0")){
                    Toast.makeText(getActivity(), "Please enter Quantity!", Toast.LENGTH_SHORT).show();

                }
                /*else if(artItemTwo.equals("Others"))
                {
                    et_artOtherDecOne.setVisibility(View.VISIBLE);
                    et_artDetailsTwo.setVisibility(View.GONE);

                    if(et_artOtherDecOne.getText().toString().equals("")){
                        Toast.makeText(getActivity(), "Please mention other article name!", Toast.LENGTH_SHORT).show();
                    }
                }*/

                else{
                    tv_artTwo.setVisibility(View.GONE);
                    ll_LayoutThree.setVisibility(View.VISIBLE);
                }

            }
        });

        tv_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if(artItemOne.equals("--select--")&&artItemTwo.equals("--select--")&&artItemThree.equals("--select--")){
                //if(artItemOne.equals("--select--")||artItemTwo.equals("--select--")||artItemThree.equals("--select--")){
                if(artItemOne.equals("--select--")){
                    Toast.makeText(getActivity(), "Please select Article name!", Toast.LENGTH_SHORT).show();

                }
                else if(et_actWeightOne.getText().toString().equals("")||et_actWeightOne.getText().toString().equals("0")){

                    Toast.makeText(getActivity(), "Please enter Actual weight!", Toast.LENGTH_SHORT).show();

                }
                else if(et_chWeightOne.getText().toString().equals("")||et_chWeightOne.getText().toString().equals("0")){
                    Toast.makeText(getActivity(), "Please enter Charged weight!", Toast.LENGTH_SHORT).show();

                }
                else if(et_QuantOne.getText().toString().equals("")|| et_QuantOne.getText().toString().equals("0")){
                    Toast.makeText(getActivity(), "Please enter Quantity!", Toast.LENGTH_SHORT).show();

                }
                //*****************

                else if(artItemTwo.equals("--select--")&&(!et_actWeightTwo.getText().toString().equals("")||!et_chWeightTwo.getText().toString().equals("")||
                !et_QuantTwo.getText().toString().equals(""))) {

                    Toast.makeText(getActivity(), "Please select article name!", Toast.LENGTH_SHORT).show();

                }
                else if((!artItemTwo.equals("--select--")&&!artItemTwo.equals("na"))&&(et_actWeightTwo.getText().toString().equals("")||et_actWeightTwo.getText().toString().equals("0"))){

                    Toast.makeText(getActivity(), "Please enter Actual weight!", Toast.LENGTH_SHORT).show();

                }
                else if((!artItemTwo.equals("--select--")&&!artItemTwo.equals("na"))&&(et_chWeightTwo.getText().toString().equals("")||et_chWeightTwo.getText().toString().equals("0"))){
                    Toast.makeText(getActivity(), "Please enter Charged weight!", Toast.LENGTH_SHORT).show();

                }
                else if((!artItemTwo.equals("--select--")&&!artItemTwo.equals("na"))&&(et_QuantTwo.getText().toString().equals("")|| et_QuantTwo.getText().toString().equals("0"))){
                    Toast.makeText(getActivity(), "Please enter Quantity!", Toast.LENGTH_SHORT).show();

                }
                //&&&&&&&&&&&&&&&&&&&&&&&&
                else if(artItemThree.equals("--select--")&&(!et_actWeightThree.getText().toString().equals("")||!et_chWeightThree.getText().toString().equals("")||
                        !et_QuantThree.getText().toString().equals(""))) {

                    Toast.makeText(getActivity(), "Please select article name!", Toast.LENGTH_SHORT).show();

                }
                else if((!artItemThree.equals("--select--")&&!artItemThree.equals("na"))&&(et_actWeightThree.getText().toString().equals("")||et_actWeightThree.getText().toString().equals("0"))){

                    Toast.makeText(getActivity(), "Please enter Actual weight!", Toast.LENGTH_SHORT).show();
                }
                else if((!artItemThree.equals("--select--")&&!artItemThree.equals("na"))&&(et_chWeightThree.getText().toString().equals("")||et_chWeightThree.getText().toString().equals("0"))){
                    Toast.makeText(getActivity(), "Please enter Charged weight!", Toast.LENGTH_SHORT).show();

                }
                else if((!artItemThree.equals("--select--")&&!artItemThree.equals("na"))&&(et_QuantThree.getText().toString().equals("")|| et_QuantThree.getText().toString().equals("0"))){
                    Toast.makeText(getActivity(), "Please enter Quantity!", Toast.LENGTH_SHORT).show();

                }

                //*****************
                else if(artItemOne.equals("Others")&&et_artOtherDec.getText().toString().equals(""))
                {
                   // if(et_artOtherDec.getText().toString().equals("")){
                        Toast.makeText(getActivity(), "Please mention other article name!", Toast.LENGTH_SHORT).show();
                   // }
                }
                else if(artItemTwo.equals("Others")&&et_artOtherDecOne.getText().toString().equals(""))
                {
                    // if(et_artOtherDec.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Please mention other article name!", Toast.LENGTH_SHORT).show();
                    // }
                }
                else if(artItemThree.equals("Others")&&et_artOtherDecTwo.getText().toString().equals(""))
                {
                    // if(et_artOtherDec.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Please mention other article name!", Toast.LENGTH_SHORT).show();
                    // }
                }


                else{

                    editor.putString("artOne",artItemOne);
                    editor.putInt("aPone",aPOne);
                    editor.putString("actwtOne",et_actWeightOne.getText().toString());
                    editor.putString("chargdwtOne",et_chWeightOne.getText().toString());
                    editor.putString("qtyOne",et_QuantOne.getText().toString());
                    editor.putString("otherArticleOne",et_artOtherDec.getText().toString());
                    editor.putString("artDecOne",et_artDesOne.getText().toString());
                    editor.putString("barcodeOne",tv_barCodeVOne.getText().toString());

                    editor.putString("artTwo",artItemTwo);
                    // editor.putInt("aPtwo",aPTwo);
                    editor.putString("actwtTwo",et_actWeightTwo.getText().toString());
                    editor.putString("chargdwtTwo",et_chWeightTwo.getText().toString());
                    editor.putString("qtyTwo",et_QuantTwo.getText().toString());
                    editor.putString("otherArticleTwo",et_artOtherDecOne.getText().toString());
                    editor.putString("artDecTwo",et_artDesTwo.getText().toString());
                    editor.putString("barcodeTwo",tv_barCodeVTwo.getText().toString());

                    editor.putString("artThree",artItemThree);
                    //  editor.putInt("aPThree",aPThree);
                    editor.putString("actwtThree",et_actWeightThree.getText().toString());
                    editor.putString("chargdwtThree",et_chWeightThree.getText().toString());
                    editor.putString("qtyThree",et_QuantThree.getText().toString());
                    editor.putString("otherArticleThree",et_artOtherDecTwo.getText().toString());
                    editor.putString("artDecThree",et_artDesThree.getText().toString());
                    editor.putString("barcodeThree",tv_barCodeVThree.getText().toString());

                    editor.putBoolean("isCommitted",true);
                    editor.commit();

                    if(artItemOne.equals(et_artOtherDecOne.getText().toString())||artItemOne.equals(et_artOtherDecTwo.getText().toString()))
                    {
                        Toast.makeText(getActivity(),"Redundant Articles selected",Toast.LENGTH_SHORT).show();
                    }
                    else if(artItemTwo.equals(et_artOtherDec.getText().toString())||artItemOne.equals(et_artOtherDecTwo.getText().toString()))
                    {
                        Toast.makeText(getActivity(),"Redundant Articles selected",Toast.LENGTH_SHORT).show();
                    }
                    else if(artItemThree.equals(et_artOtherDecOne.getText().toString())||artItemOne.equals(et_artOtherDec.getText().toString()))
                    {
                        Toast.makeText(getActivity(),"Redundant Articles selected",Toast.LENGTH_SHORT).show();
                    }
                    else {

                        calAmount();
                    }
                }
                //calAmount();
            }
        });




        ArrayAdapter<String> Adapterbarcodes = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, barcodes);
        Adapterbarcodes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_barCode.setAdapter(Adapterbarcodes);

        sp_barCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


                barItem=sp_barCode.getSelectedItem().toString();

                editor.putString("barcode",barItem);
                editor.commit();



                if(barItem.equals("Required")){

                    tv_barCodeVOne.setVisibility(View.VISIBLE);
                    tv_barCodeVTwo.setVisibility(View.VISIBLE);
                    tv_barCodeVThree.setVisibility(View.VISIBLE);

                    if(Build.VERSION.SDK_INT<23)
                    {
                        Intent i=new Intent(getActivity(),BarcodeActivity.class);
                        startActivityForResult(i, 1);
                        // startActivity(i);


                        // scanCode();
                    }
                    else
                    {
                        if(getActivity().checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)
                        {
                            Intent i=new Intent(getActivity(),BarcodeActivity.class);
                            startActivityForResult(i, 1);
                            startActivityForResult(i, 2);
                            startActivityForResult(i, 3);
                            //  startActivity(i);


                        }
                        else
                        {
                            if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
                            {
                                Toast.makeText(getActivity()," Permission is required to scan the Barcode!",Toast.LENGTH_LONG).show();
                            }
                            requestPermissions(new String[]{Manifest.permission.CAMERA},REQUEST_LOCATION);
                        }
                    }
                }
                else{
                    tv_barCodeVOne.setVisibility(View.GONE);
                    tv_barCodeVTwo.setVisibility(View.GONE);
                    tv_barCodeVThree.setVisibility(View.GONE);                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        return v;



    }

    public void calAmount(){


        stCustomerId=pref.getString("CustomerId","");
        stlrType=pref.getString("lrtype","");
        stFroLocid=pref.getString("fromLocId","");
        stToLocid=pref.getString("toLocId","");
        stBranchid=pref.getString("branchId","");
        stTarif=pref.getString("tariff","");
        stTarifcritera=pref.getString("tarifCriteria","");

        System.out.println("@@@@@@@@@"+"CustomerId"+stCustomerId);
        System.out.println("@@@@@@@@@"+"lrType"+stlrType);
        System.out.println("@@@@@@@@@"+"FroLocid"+stFroLocid);
        System.out.println("@@@@@@@@@"+"ToLocid"+stToLocid);
        System.out.println("@@@@@@@@@"+"Branchid"+stBranchid);
        System.out.println("@@@@@@@@@"+"Tarif"+stTarif);
        System.out.println("@@@@@@@@@"+"Tarifcritera"+stTarifcritera);


        stactWeightOne=et_actWeightOne.getText().toString();
        stactWeightTwo=et_actWeightTwo.getText().toString();
        stactWeightThree=et_actWeightThree.getText().toString();

        stchWeightOne=et_chWeightOne.getText().toString();
        stchWeightTwo=et_chWeightTwo.getText().toString();
        stchWeightthree=et_chWeightThree.getText().toString();

        stQuantOne=et_QuantOne.getText().toString();
        stQuantTwo=et_QuantTwo.getText().toString();
        stQuantThree=et_QuantThree.getText().toString();

        System.out.println(stactWeightOne+"::"+stactWeightTwo+"::"+stactWeightThree);
        System.out.println(stactWeightOne.equals("")+":"+stactWeightTwo.equals(""));
        System.out.println(stchWeightOne+"::"+stchWeightTwo+"::"+stchWeightthree);
        System.out.println(stchWeightOne.equals("")+"????"+stchWeightOne.equals(null)+"????"+stchWeightOne.equals(" "));


        try{
            if(!stactWeightOne.equals("")) {
                actWtOne = Integer.parseInt(stactWeightOne);
            }

            if(!stactWeightTwo.equals("")) {
                actWtTwo = Integer.parseInt(stactWeightTwo);
            }

            if(!stactWeightThree.equals("")) {
                actWtThree = Integer.parseInt(stactWeightThree);
            }

            totalActwt=actWtOne+actWtTwo+actWtThree;

            if(!stchWeightOne.equals("")) {

                chdwtOne = Integer.parseInt(stchWeightOne);
            }

            if(!stchWeightTwo.equals("")) {
                chdwtTwo = Integer.parseInt(stchWeightTwo);
            }

            if(!stchWeightthree.equals("")) {
                chdwtThree = Integer.parseInt(stchWeightthree);
            }

            totalchdwt=chdwtOne+chdwtTwo+chdwtThree;


            if(!stQuantOne.equals("")) {
                qtyOne = Integer.parseInt(stQuantOne);
            }
            if(!stQuantTwo.equals("")) {
                qtyTwo = Integer.parseInt(stQuantTwo);
            }
            if(!stQuantThree.equals("")) {
                qtyThree = Integer.parseInt(stQuantThree);
            }
            totalqty=qtyOne+qtyTwo+qtyThree;
            totalActwt=actWtOne+actWtTwo+actWtThree;
            totalchdwt=chdwtOne+chdwtTwo+chdwtThree;


        }catch(NumberFormatException ex) {
            // handle your exception

            ex.printStackTrace();
        }
        System.out.println("?????????????"+"Total actg"+totalActwt);
        System.out.println("?????????????"+"Total chdwt"+totalchdwt);
        System.out.println("?????????????"+"Total qty"+totalqty);
        System.out.println("?????????????"+"qtyOne"+qtyOne);
        System.out.println("?????????????"+"qtyTwo"+qtyTwo);
        System.out.println("?????????????"+"qtyThree"+qtyThree);

        if(totalchdwt<totalActwt){
            Toast.makeText(getContext(), "Charged Weight must be greaterthan Act wgt", Toast.LENGTH_SHORT).show();
        }
        else{

            JsonObject v=new JsonObject();
            v.addProperty("TariffCriteria",stTarifcritera);
            v.addProperty("Weight",totalchdwt);
            v.addProperty("Qty",totalqty);
            v.addProperty("fromlocid",stFroLocid);
            v.addProperty("tolocid",stToLocid);
            v.addProperty("custid",stCustomerId);
            v.addProperty("LRType",stlrType);
            v.addProperty("deliverytype","Normal");
            v.addProperty("calcriteria",stTarifcritera);
            v.addProperty("tariff",stTarif);
            v.addProperty("article1",artItemOne);
            v.addProperty("article2",artItemTwo);
            v.addProperty("article3",artItemThree);
            v.addProperty("qty1",qtyOne);
            v.addProperty("qty2",qtyTwo);
            v.addProperty("qty3",qtyThree);
            v.addProperty("branchId",stBranchid);

            System.out.println("TariffCriteria"+stTarifcritera);
            System.out.println("weight"+totalchdwt);
            System.out.println("qty"+totalqty);
            System.out.println("fromlocid"+stFroLocid);
            System.out.println("tolocid"+stToLocid);
            System.out.println("custid"+stCustomerId);
            System.out.println("lrtype"+stlrType);
            System.out.println("delivertype"+"");
            System.out.println("calcriteria"+stTarifcritera);
            System.out.println("tariff"+stTarif);
            System.out.println("article1"+artItemOne);
            System.out.println("article2"+artItemTwo);
            System.out.println("article3"+artItemThree);
            System.out.println("qty1"+qtyOne);
            System.out.println("qty2"+qtyTwo);
            System.out.println("qty3"+qtyThree);
            System.out.println("branchId"+stBranchid);

            editor.putString("totalActualWeight", String.valueOf(totalActwt));
            editor.putString("totalQty", String.valueOf(totalqty));
            editor.putString("totalWt", String.valueOf(totalchdwt));
            editor.commit();


            Call<CalAmountPojo>call=REST_CLIENT.calAmount(v);
            call.enqueue(new Callback<CalAmountPojo>() {
                @Override
                public void onResponse(Call<CalAmountPojo> call, Response<CalAmountPojo> response) {
                    CalAmountPojo calAmt;
                    calAmt=response.body();
                    if(response.isSuccessful()){



                        String calDetails=calAmt.getTotamt();


                        String isTariffExist=calAmt.getIsTariffExist();


                        if(isTariffExist.equals("No")){
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

                            LayoutInflater inflater = getLayoutInflater();
                            final View dialogView = inflater.inflate(R.layout.alert_tariff, null);

                            dialogBuilder.setView(dialogView);

                            final AlertDialog alertDialog = dialogBuilder.create();
                            alertDialog.show();
                            alertDialog.setCancelable(false);
                            alertDialog.setCanceledOnTouchOutside(false);

                            btOk=(Button)dialogView.findViewById(R.id.tarif_bt_ok);
                            btCancel=(Button)dialogView.findViewById(R.id.tarif_bt_cancel);
                            btOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i=new Intent(getActivity(), HomeActivity.class);
                                    startActivity(i);
                                    getActivity().finish();
                                }
                            });
                            btCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.dismiss();
                                }
                            });
                        }
                        else{

                            System.out.println("amounttt..."+calDetails);

                            editor.putString("totalamt",calDetails);
                            editor.putString("rate",calAmt.getRate());
                            editor.putString("extraRate",calAmt.getExtrarate());
                            editor.commit();
                            Fragment frag = new NewLrBookingFourFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(R.id.content_frame, frag, "specific_ride");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }




                    }
                    else {

                        System.out.println("?????????????"+"elseeee isssssss"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<CalAmountPojo> call, Throwable t) {

                    Toast.makeText(getActivity(),"Please check Internet connection!",Toast.LENGTH_SHORT).show();

                }
            });

        }


    }

    public  void ArticleDetails(){

        System.out.println("-------------articles--------");
        System.out.println("custId"+stCustomerId);
        System.out.println("sttariff"+stTarifcritera);

        if(stTarifcritera.equals("Article Based"))
        {

        }
        else {

            stCustomerId="";
        }

        Call<List<ArticleDetailsPojo>>call=REST_CLIENT.getarticleDetails(stCustomerId);
        call.enqueue(new Callback<List<ArticleDetailsPojo>>() {
            @Override
            public void onResponse(Call<List<ArticleDetailsPojo>> call, final Response<List<ArticleDetailsPojo>> response) {

               ArticleDetailsPojo articleData;


                if(response.isSuccessful()){

                    articleStatus=response.body();
                    articlelistOne.add("--select--");
                    articlelistTwo.add("--select--");
                    articlelistThree.add("--select--");
                    for (int i=0;i<articleStatus.size();i++){

                        articleData=articleStatus.get(i);

                        String articleName=articleData.getArticleName();
                        //editor.putString("articleid",articleData.getArticleId());
                        //editor.commit();

                        articlelistOne.add(articleName);
                        articlelistTwo.add(articleName);
                        articlelistThree.add(articleName);

                    }
                    articlelistOne.add("Others");
                    articlelistTwo.add("Others");
                    articlelistThree.add("Others");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, articlelistOne);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_artDetailsOne.setAdapter(dataAdapter);

                    sp_artDetailsOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {


                            artItemOne=sp_artDetailsOne.getSelectedItem().toString();
                            aPOne=position;

                            if(artItemOne.equals("Others"))
                            {
                                et_artOtherDec.setVisibility(View.VISIBLE);
                                et_artDesOne.setVisibility(View.GONE);

                            }
                            else{
                                et_artOtherDec.setVisibility(View.GONE);
                                et_artDesOne.setVisibility(View.VISIBLE);

                                System.out.println("art err.."+response.message());


                            }

                            editor.putString("artOneId",artItemOne);
                            editor.commit();




                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                    }
                });



                final ArrayAdapter<String> dataAdapterone = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, articlelistTwo);
                dataAdapterone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_artDetailsTwo.setAdapter(dataAdapterone);
                sp_artDetailsTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {


                        artItemTwo=sp_artDetailsTwo.getSelectedItem().toString();
                        aPTwo=position;
                        System.out.println("????????????????"+position);


                        if(artItemTwo.equals(artItemOne))
                        {


                            Toast.makeText(getActivity(),"Article is already selected!",Toast.LENGTH_SHORT).show();
                            sp_artDetailsTwo.setSelection(0);
                        }
                        else{

                            if(artItemTwo.equals("Others"))
                            {
                                et_artOtherDecOne.setVisibility(View.VISIBLE);
                                et_artDesTwo.setVisibility(View.GONE);
                            }
                            else
                            {
                                et_artOtherDecOne.setVisibility(View.GONE);
                                et_artDesTwo.setVisibility(View.VISIBLE);
                            }

                            editor.putString("artTwoId",artItemTwo);
                            editor.commit();



                        }




                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                    }
                });
                final ArrayAdapter<String> dataAdaptertwo = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, articlelistThree
                );
                dataAdaptertwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_artDetailsThree.setAdapter(dataAdaptertwo);
                sp_artDetailsThree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {



                        artItemThree=sp_artDetailsThree.getSelectedItem().toString();
                        aPThree=position;

                        if(artItemThree.equals(artItemTwo))
                        {


                            Toast.makeText(getActivity(),"Article is already selected!",Toast.LENGTH_SHORT).show();
                            sp_artDetailsThree.setSelection(0);
                        }
                        else if(artItemThree.equals(artItemOne)){



                            Toast.makeText(getActivity(),"Article is already selected!",Toast.LENGTH_SHORT).show();
                            sp_artDetailsThree.setSelection(0);

                        }
                        else if(artItemThree.equals("Others"))
                        {
                            if(et_artOtherDecTwo.getText().toString().equals("")){
                                Toast.makeText(getActivity(), "Please mention other article name!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{

                            if(artItemThree.equals("Others"))
                            {
                                et_artOtherDecTwo.setVisibility(View.VISIBLE);
                                et_artDesThree.setVisibility(View.GONE);
                            }
                            else
                            {
                                et_artDesThree.setVisibility(View.VISIBLE);
                            }

                            editor.putString("artThreeId",artItemThree);
                            editor.commit();

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                    }
                });
            }

        }

        @Override
        public void onFailure(Call<List<ArticleDetailsPojo>> call, Throwable t) {

            Toast.makeText(getActivity(),"Please check Internet connection!",Toast.LENGTH_SHORT).show();

        }
    });

}


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==REQUEST_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(getActivity(), "Permission not granted", Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        if (scannerView != null) {
            scannerView.stopCamera();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("scannerCode");

                tv_barCodeVOne.setText(strEditText);
                editor.putString("barCOne",strEditText);
                editor.commit();
            }
        }
        else if (requestCode == 2) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("scannerCode");

                tv_barCodeVTwo.setText(strEditText);
                editor.putString("barCTwo",strEditText);
                editor.commit();

            }
        }
        else if (requestCode == 3) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("scannerCode");

                tv_barCodeVThree.setText(strEditText);
                editor.putString("barCThree",strEditText);
                editor.commit();

            }
        }
    }

//    public  void LRDetailsSave(){
//
//
//        stArtdesOne=et_artDesOne.getText().toString();
//        stArtdesTwo=et_artDesTwo.getText().toString();
//        stArtdesThree=et_artDesThree.getText().toString();
//
//        stBarcodeOne=tv_barCodeVOne.getText().toString();
//        stBarcodeTwo=tv_barCodeVTwo.getText().toString();
//        stBarcodeOne=tv_barCodeVOne.getText().toString();
//
//
//
//        JsonObject v=new JsonObject();
//        v.addProperty("srno");
//        v.addProperty("LRNo" ,pref.getString("lrNo",null));
//        v.addProperty("articleId",pref.getString("articleid",null));
//        v.addProperty("fromlocid",stFroLocid);
//        v.addProperty("calcriteria",stTarifcritera);
//        v.addProperty("units","0");
//        v.addProperty("Length","0");
//        v.addProperty("Width","0");
//        v.addProperty("Height","0");
//        v.addProperty("CFT","0");
//        v.addProperty("ActWeight",totalActwt);
//        v.addProperty("ChgWeight",totalchdwt);
//        v.addProperty("Qty",totalqty);
//        v.addProperty("Barcodes",stBarcodeOne+stBarcodeTwo+stBarcodeThree);
//        v.addProperty("rate");
//        v.addProperty("extrarate");
//        v.addProperty("amount");
//        v.addProperty("ArticleDescription",stArtdesOne+stArtdesTwo+stArtdesThree);
//        v.addProperty("source","LRBooking" );
//
//
//        Call<Pojo>call=REST_CLIENT.LRDetailsSave(v);
//        call.enqueue(new Callback<Pojo>() {
//            @Override
//            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
//                Pojo detailData;
//                if(response.isSuccessful()){
//                    detailData=response.body();
//
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Pojo> call, Throwable t) {
//
//            }
//        });
//    }
}