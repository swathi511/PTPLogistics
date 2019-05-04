package com.hjsoftware.ptplogistics.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.hjsoftware.ptplogistics.Adapters.LRTrackingRecyclerAdapter;
import com.hjsoftware.ptplogistics.R;
import com.hjsoftware.ptplogistics.Webservices.API;
import com.hjsoftware.ptplogistics.Webservices.RestClient;
import com.hjsoftware.ptplogistics.model.LRTrackingData;
import com.hjsoftware.ptplogistics.model.LRTrackingPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LRTrackingFragment extends Fragment {
    View v;
    EditText etLRNo;
    TextView tvFromLoc,tvToLoc,tvLoadingNo,tvVehNo,tvShowDetails;
    API REST_CLIENT;
    String stLrNo;
    List<LRTrackingPojo> lrData;
    ArrayList<LRTrackingData> lrList=new ArrayList<>();
    RecyclerView recyclerView;
    LRTrackingRecyclerAdapter recyclerAdapter;
    LinearLayout ll1,ll2;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.lr_tracking_status, container, false);

        etLRNo=(EditText)v.findViewById(R.id.lr_shipping_status_et_lr_no);
        tvFromLoc=(TextView)v.findViewById(R.id.lr_shipping_status_tv_from_loc);
        tvToLoc=(TextView)v.findViewById(R.id.lr_shipping_status_tv_to_loc);
        tvLoadingNo=(TextView)v.findViewById(R.id.lr_shipping_status_tv_loading_no);
        tvVehNo=(TextView)v.findViewById(R.id.lr_shipping_status_tv_vehicle_no);
        tvShowDetails=(TextView)v.findViewById(R.id.lr_shipping_status_tv_show_details);
        recyclerView=(RecyclerView)v.findViewById(R.id.lr_shipping_status_rView);

        ll1=(LinearLayout)v.findViewById(R.id.ll_ll1);
        ll2=(LinearLayout)v.findViewById(R.id.ll_ll2);

        recyclerView.setVisibility(View.GONE);

        tvShowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stLrNo=etLRNo.getText().toString().trim();

                if(stLrNo.equals(""))
                {
                    Toast.makeText(getActivity(),"Please enter LRNo!",Toast.LENGTH_SHORT).show();
                }
                else {

                    lrList.clear();

                    Call<List<LRTrackingPojo>> call=REST_CLIENT.showLRTracking(stLrNo);
                    call.enqueue(new Callback<List<LRTrackingPojo>>() {
                        @Override
                        public void onResponse(Call<List<LRTrackingPojo>> call, Response<List<LRTrackingPojo>> response) {

                            LRTrackingPojo l;

                            if(response.isSuccessful())
                            {
                                lrData=response.body();

                                ll1.setVisibility(View.VISIBLE);
                                ll2.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.VISIBLE);

                               // for(int i=0;i<lrData.size();i++)
                                //{
                                    l=lrData.get(0);

                                    tvFromLoc.setText(l.getFromLoc());
                                    tvToLoc.setText(l.getToLoc());
                                    tvLoadingNo.setText(l.getDispatchno());
                                    tvVehNo.setText(l.getVehNo());

                                    String d[]=l.getDescription().split("#");

                                    System.out.println("d.length..."+d.length);

                                    //if(d.length!=1) {

                                        for (int j = 0; j < d.length; j++) {

                                            String dd[] = d[j].split("\\*");

                                            lrList.add(new LRTrackingData(dd[0], dd[2], dd[1]));
                                        }
                                    //}

                               // }


                            }
                            else {

                                System.out.println("err..."+response.message()+"::"+response.isSuccessful());
                            }

                            if(lrList.size()!=0)
                            {
                                recyclerAdapter = new LRTrackingRecyclerAdapter(getActivity(), lrList, recyclerView);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(recyclerAdapter);
                                recyclerAdapter.notifyDataSetChanged();

                            }
                        }

                        @Override
                        public void onFailure(Call<List<LRTrackingPojo>> call, Throwable t) {

                            t.printStackTrace();

                        }
                    });


                }
            }
        });







        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        REST_CLIENT= RestClient.get();

    }
}
