package com.hjsoftware.ptplogistics.Fragments;

import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hjsoftware.ptplogistics.Activity.MainActivity;
import com.hjsoftware.ptplogistics.Adapters.SessionManager;
import com.hjsoftware.ptplogistics.R;
import com.hjsoftware.ptplogistics.Webservices.API;
import com.hjsoftware.ptplogistics.Webservices.RestClient;
import com.hjsoftware.ptplogistics.model.SenderAddPojo;
import com.hjsoftware.ptplogistics.model.TarifCriteriaPojo;
import com.hjsoftware.ptplogistics.model.locPojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewLrBookingTwoFragment extends Fragment{

    View v;
    TextView tv_Sgdtin,tv_Rgstin,tv_Rname,tv_Saddress,tv_Raddress,tv_Smobile;
    TextView tv_Rmobile,tv_CustNo,tv_goodValue,tv_Ewaybil;
    Spinner sp_valSurchrge,sp_Sname;
    TextView tv_next,tv_Lrone,tv_Lrtwo;
    SharedPreferences pref;
    API REST_CLIENT;
    SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "SharedPref";
    SessionManager session;
    ArrayList<String> tariflist = new ArrayList<String>();
    HashMap<String, String> user;
    String stProfileid,stTariffCrit,stFromLocId,stToLocId,stBrCode;
    String stDate,stLrType,stNo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.new_lrbooking_two, container,false);

        tv_Sgdtin=(TextView)v.findViewById(R.id.new_lrb_two_tv_send_gstin);
        tv_Rgstin=(TextView)v.findViewById(R.id.new_lrb_two_tv_recvr_gstin);
        tv_Rname=(TextView)v.findViewById(R.id.new_lrb_two_tv_recver_name);
        tv_Saddress=(TextView)v.findViewById(R.id.new_lrb_two_tv_sendr_address);
        tv_Raddress=(TextView)v.findViewById(R.id.new_lrb_two_tv_recvr_address);
        tv_Smobile=(TextView)v.findViewById(R.id.new_lrb_two_tv_sendr_mobile_no);
        tv_Rmobile=(TextView)v.findViewById(R.id.new_lrb_two_tv_rec_mobile_no);
        tv_CustNo=(TextView)v.findViewById(R.id.new_lrb_two_tv_cust_inv_no);
        tv_goodValue=(TextView)v.findViewById(R.id.new_lrb_two_tv_good_value);
        tv_Ewaybil=(TextView)v.findViewById(R.id.new_lrb_two_tv_eway_bill);
        sp_valSurchrge=(Spinner)v.findViewById(R.id.new_lrb_two_sp_value_sur_charge);
        sp_Sname=(Spinner) v.findViewById(R.id.new_lrb_two_sp_sender_name);
        tv_next=(TextView)v.findViewById(R.id.new_lrb_two_tv_next);
        tv_Lrone=(TextView)v.findViewById(R.id.new_lrb_two_tv_lr_no_one);
        tv_Lrtwo=(TextView)v.findViewById(R.id.new_lrb_two_tv_lr_no_two);

        REST_CLIENT= RestClient.get();
        pref = getActivity().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        session=new SessionManager(getContext());
        user=session.getUserDetails();
        stProfileid=user.get(SessionManager.KEY_PROFILE_ID);

        stTariffCrit=pref.getString("Regular Based",null);
        stToLocId=pref.getString("toLocId",null);
        stFromLocId=pref.getString("fromLocId",null);
        stBrCode=pref.getString("brCode",null);
        stDate=pref.getString("lrDate",null);
        stLrType=pref.getString("lrtype",null);
        stNo=pref.getString("no",null);
        tv_Lrone.setText(stBrCode+"/"+stNo);



        System.out.println("@@@profileid"+stProfileid);
        System.out.println("@@@tarifCriteria"+stTariffCrit);
        System.out.println("@@@fromlocid"+"  "+stFromLocId);
        System.out.println("@@@tolocationid"+" "+stToLocId);


        Call<List<TarifCriteriaPojo>>call=REST_CLIENT.getSenderList(stTariffCrit,stFromLocId,stToLocId);
        call.enqueue(new Callback<List<TarifCriteriaPojo>>() {
            @Override
            public void onResponse(Call<List<TarifCriteriaPojo>> call, Response<List<TarifCriteriaPojo>> response) {
                List<TarifCriteriaPojo> tarifCriteriaPojostatus;
                TarifCriteriaPojo tarifdata;
                tarifCriteriaPojostatus=response.body();
                if(response.isSuccessful()){

                    for(int i=0;i<tarifCriteriaPojostatus.size();i++){

                        tarifdata=tarifCriteriaPojostatus.get(i);

                        String stCustmerName=tarifdata.getCustomername();
                        String stCustrid=tarifdata.getCustomerId();
                        editor.putString("CustomerId",stCustrid);
                        System.out.println("####customerid@@@@@"+stCustrid);
                        editor.commit();
                        tariflist.add(stCustmerName);
                        System.out.println("#####################@@"+stCustmerName);

                    }

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, tariflist);

                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    sp_Sname.setAdapter(dataAdapter);
                    sp_Sname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {



                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {


                        }
                    });
                }
                else {
                    String msg=response.message();
                    System.out.println("responce is"+msg);
                }
            }

            @Override
            public void onFailure(Call<List<TarifCriteriaPojo>> call, Throwable t) {

                Toast.makeText(getContext(),"Please check Internet connection!",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                System.out.println("@@@@@@@@@@msg"+t.getMessage());
            }
        });

        String customerid=pref.getString("CustomerId",null);

        System.out.println("customerid@@@@@1234"+customerid);

        Call<List<SenderAddPojo>>call1=REST_CLIENT.getsenderAddress(customerid,"");
        call1.enqueue(new Callback<List<SenderAddPojo>>() {
            List<SenderAddPojo> senderAddPojostatus;
            SenderAddPojo senderdata;
            @Override
            public void onResponse(Call<List<SenderAddPojo>> call, Response<List<SenderAddPojo>> response) {
                senderAddPojostatus=response.body();
                if(response.isSuccessful()){

                    for(int i=0;i<senderAddPojostatus.size();i++){
                        senderdata=senderAddPojostatus.get(i);

                        String senderAdd=senderdata.getAddress();
                        String sendermobile=senderdata.getPhone();
                        String senderGstin=senderdata.getGstno();
                        String senderartiCh=senderdata.getAriclechg();
                        tv_Saddress.setText(senderAdd);
                        tv_Smobile.setText(sendermobile);
                        tv_Sgdtin.setText(senderGstin);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<SenderAddPojo>> call, Throwable t) {

            }
        });



        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new NewLrBookingThreeFragment();
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