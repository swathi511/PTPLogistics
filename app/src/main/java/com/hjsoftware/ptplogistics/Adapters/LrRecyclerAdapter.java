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
import com.hjsoftware.ptplogistics.model.LrBookingData;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by hjsoft on 1/12/16.
 */
public class LrRecyclerAdapter extends RecyclerView.Adapter<LrRecyclerAdapter.MyViewHolder> {

    Context context;
    LayoutInflater inflater;
    Geocoder geocoder;
    RecyclerView rview;
    ArrayList<LrBookingData> mResultList;
    LrBookingData data;
    private AdapterCallback mAdapterCallback;
    int pos;


    public LrRecyclerAdapter(Context context, ArrayList<LrBookingData> mResultList, RecyclerView rview)
    {

        this.context=context;
        this.mResultList=mResultList;
        this.rview=rview;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        geocoder=new Geocoder(context, Locale.getDefault());
        try {
            this.mAdapterCallback = ((AdapterCallback) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_lrbooking, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        data=mResultList.get(position);


        holder.tv_Article.setText(data.getArticle());
        holder.tv_ActWt.setText(data.getActwt());
        holder.tv_Chdwt.setText(data.getChdwt());
        holder.tv_Qty.setText(data.getQty());
        holder.tv_Rate.setText(data.getRate());
        holder.tv_Amount.setText(data.getAmount());
        holder.tv_FinalAmt.setText(data.getFinalAmt());









    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Article,tv_ActWt,tv_Chdwt,tv_Qty,tv_Rate;
        TextView tv_Amount,tv_FinalAmt,tv_Select,tv_Delete;
        RelativeLayout r_Layout;


        public MyViewHolder(final View itemView) {
            super(itemView);


            tv_Article=(TextView)itemView.findViewById(R.id.row_list_booking_tv_article);
            tv_ActWt=(TextView)itemView.findViewById(R.id.row_list_booking_tv_act_wt);
            tv_Chdwt=(TextView)itemView.findViewById(R.id.row_list_booking_tv_chd_wt);
            tv_Qty=(TextView)itemView.findViewById(R.id.row_list_booking_tv_qty);
            tv_Rate=(TextView)itemView.findViewById(R.id.row_list_booking_tv_rate);
            tv_Amount=(TextView)itemView.findViewById(R.id.row_list_booking_tv_amount);
            tv_FinalAmt=(TextView)itemView.findViewById(R.id.row_list_booking_tv_final_amt);
            tv_Select=(TextView)itemView.findViewById(R.id.row_list_booking_tv_select);
            tv_Delete=(TextView)itemView.findViewById(R.id.row_list_booking_tv_delete);
            r_Layout=(RelativeLayout)itemView.findViewById(R.id.ll_rlayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        pos= (int) view.getTag();
                        mAdapterCallback.onMethodCallback(pos,mResultList);

                    }
                    catch (ClassCastException e)
                    {
                        e.printStackTrace();
                    }
                    //Toast.makeText(context,"clicked .."+pos,Toast.LENGTH_LONG).show();
                }
            });




        }


    }

    public static interface AdapterCallback {
        void onMethodCallback(int position, ArrayList<LrBookingData> data);
    }



}