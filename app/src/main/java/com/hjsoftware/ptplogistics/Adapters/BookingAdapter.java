package com.hjsoftware.ptplogistics.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hjsoftware.ptplogistics.R;
import com.hjsoftware.ptplogistics.model.BookingData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.MyViewHolder> {
    private List<BookingData> bookingDataList;
    BookingData bookingData;
    private AdapterCallback mAdapterCallback;
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
    Context c;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvLrno;


        // SimpleDateFormat format1 = new SimpleDateFormat("dd/mm/yyyy");


        public MyViewHolder(View itemView) {
            super(itemView);

            tvLrno=(TextView)itemView.findViewById(R.id.rl_tv_lrno);


           tvLrno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        String bookingId= (String) v.getTag();

                        System.out.println("v.getTag.."+v.getTag());
                        mAdapterCallback.onMethodCallback(bookingId);

                    }
                    catch (ClassCastException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    public BookingAdapter(Context context, List<BookingData> bookingDataList, RecyclerView rView) {
        this.bookingDataList = bookingDataList;
        this.c=context;

        try {
            this.mAdapterCallback = ((AdapterCallback) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }
    @Override
    public BookingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_lr, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(BookingAdapter.MyViewHolder holder, int position) {

        // System.out.println("data is "+position+bookingDataList.size());
        //System.out.println("****"+bookingData.getBookingId());
        //System.out.println("****"+bookingData.getBooking_date());
        //System.out.println("****"+bookingData.getReporting_date());
        //System.out.println("****"+bookingData.getReporting_time());
        //System.out.println("****"+bookingData.getGuest_name());
        //holder.ref_number.setText(bookingData.getRef_no());
        //holder.booking_date.setText(bookingData.getBooking_date());
        bookingData = bookingDataList.get(position);

        System.out.println("booking lrno..."+bookingData.getLrno());

        holder.tvLrno.setText(bookingData.getLrno());
        holder.tvLrno.setTag(bookingData.getLrno());



    }

    @Override
    public int getItemCount() {
        return bookingDataList.size();
    }

    public static interface AdapterCallback {
        void onMethodCallback(String bookingId);
    }

}
