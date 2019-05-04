package com.hjsoftware.ptplogistics.Adapters;

import android.content.Context;
import android.location.Geocoder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.hjsoftware.ptplogistics.R;
import com.hjsoftware.ptplogistics.model.LRTrackingData;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by hjsoft on 1/12/16.
 */
public class LRTrackingRecyclerAdapter extends RecyclerView.Adapter<LRTrackingRecyclerAdapter.MyViewHolder> {

    Context context;
    LayoutInflater inflater;
    Geocoder geocoder;
    RecyclerView rview;
    ArrayList<LRTrackingData> mResultList;
    LRTrackingData data;
    int pos;


    public LRTrackingRecyclerAdapter(Context context, ArrayList<LRTrackingData> mResultList, RecyclerView rview)
    {

        this.context=context;
        this.mResultList=mResultList;
        this.rview=rview;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        geocoder=new Geocoder(context, Locale.getDefault());

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_lrtracking, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        data=mResultList.get(position);

        holder.tvDate.setText(data.getDate());
        holder.tvTime.setText(data.getTime());
        holder.tvLRStatus.setText(data.getLrStatus());












    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate,tvTime,tvLRStatus;


        public MyViewHolder(final View itemView) {
            super(itemView);

            tvLRStatus=(TextView)itemView.findViewById(R.id.lr_tracking_status_tv_lr_status_one);
            tvTime=(TextView)itemView.findViewById(R.id.lr_tracking_status_tv_time_one);
            tvDate=(TextView)itemView.findViewById(R.id.lr_tracking_status_tv_date_one);









        }


    }





}