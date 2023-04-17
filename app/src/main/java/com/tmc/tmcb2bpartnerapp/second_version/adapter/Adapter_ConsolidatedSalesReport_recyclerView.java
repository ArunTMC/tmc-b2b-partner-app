package com.tmc.tmcb2bpartnerapp.second_version.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.PlacedOrderDetailsScreen;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal_ConsolidatedSalesReport;
import com.tmc.tmcb2bpartnerapp.second_version.activity.ConsolidatedSalesReport;
import com.tmc.tmcb2bpartnerapp.utils.Constants;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_ConsolidatedSalesReport_recyclerView extends RecyclerView.Adapter<Adapter_ConsolidatedSalesReport_recyclerView.MyViewHolder> {


     ArrayList<Modal_ConsolidatedSalesReport> orderDetailsArrayList;
    ConsolidatedSalesReport consolidatedSalesReport;
    Context mContext;
    static DecimalFormat twoDecimalConverterWithComma = new DecimalFormat(Constants.twoDecimalWithCommaPattern);


    public Adapter_ConsolidatedSalesReport_recyclerView(ArrayList< Modal_ConsolidatedSalesReport > modelList, ConsolidatedSalesReport consolidatedSalesReportt ,Context context) {
        orderDetailsArrayList = modelList;
        this.mContext = context;
        this.consolidatedSalesReport = consolidatedSalesReportt;

    }



    @Override
    public Adapter_ConsolidatedSalesReport_recyclerView.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_report_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final Adapter_ConsolidatedSalesReport_recyclerView.MyViewHolder holder, int position)
    {
        final Modal_ConsolidatedSalesReport model = orderDetailsArrayList.get(position);

        holder.placedtime_textview.setText(String.valueOf(model.getPlacedtime()));
        holder.billNo_textview.setText(String.valueOf(model.getBillno()));
        try {
            holder.totalAmount_textview.setText(twoDecimalConverterWithComma.format(Double.parseDouble(String.valueOf(model.getTotalPrice()))));
        }
        catch (Exception e){
            holder.totalAmount_textview.setText(String.valueOf(model.getTotalPrice()));
            e.printStackTrace();
        }
        holder.batchno_textview.setText(String.valueOf(model.getBatchno()));
        holder.maleCount_textview.setText(String.valueOf(model.getMale()));
        holder.femaleCount_textview.setText(String.valueOf(model.getFemale()));
        holder.totalCount_textview.setText(String.valueOf(model.getTotalCount()));
        try {
            holder.saleValue_textview.setText(twoDecimalConverterWithComma.format(Double.parseDouble(String.valueOf(model.getTotalPrice()))));
        }
        catch (Exception e){
            holder.saleValue_textview.setText(String.valueOf(model.getTotalPrice()));
            e.printStackTrace();
        }
       // holder.saleValue_textview.setText(String.valueOf(model.getTotalPrice()));
        holder.totalWeight_textview.setText(String.valueOf(model.getTotalWeight())+" ");
        holder.retailerName_textview.setText(String.valueOf(model.getRetailername()));
        holder.retailermobileno_textview.setText(String.valueOf(model.getRetailerNo()).replaceAll("[+]91",""));



        holder.orderDetails_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, PlacedOrderDetailsScreen.class);
                i.putExtra("orderdetailsPojoClass", orderDetailsArrayList.get(position).getModal_b2BOrderDetails());
                i.putExtra("calledFrom","consolidatedSalesReport");
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            }
        });




        holder.parentCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, PlacedOrderDetailsScreen.class);
                i.putExtra("orderdetailsPojoClass", orderDetailsArrayList.get(position).getModal_b2BOrderDetails());
                i.putExtra("calledFrom","consolidatedSalesReport");
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            }
        });


    }
    public int getItemCount() {
        return orderDetailsArrayList == null ? 0 : orderDetailsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private View view;
        ImageView orderDetails_icon;
        private TextView batchno_textview ,maleCount_textview ,femaleCount_textview ,totalCount_textview,placedtime_textview,
                saleValue_textview,totalWeight_textview,retailerName_textview,retailermobileno_textview , billNo_textview,totalAmount_textview;
        CardView cardview;
        ConstraintLayout constraintlayout;
        ImageView closeButton_icon;
        CardView parentCardview;
        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            //  cardview = (CardView) itemView.findViewById(R.id.cardview);
            placedtime_textview  = itemView.findViewById(R.id.placedtime_textview);
            totalAmount_textview  = itemView.findViewById(R.id.totalAmount_textview);
            billNo_textview = (TextView) itemView.findViewById(R.id.BillNo_textview);
            batchno_textview = (TextView) itemView.findViewById(R.id.batchno_textview);
            maleCount_textview = itemView.findViewById(R.id.maleCount_textview);
            femaleCount_textview = itemView.findViewById(R.id.femaleCount_textview);
            totalCount_textview = itemView.findViewById(R.id.totalCount_textview);
            saleValue_textview = itemView.findViewById(R.id.saleValue_textview);
            totalWeight_textview = itemView.findViewById(R.id.totalWeight_textview);
            orderDetails_icon = itemView.findViewById(R.id.orderDetails_icon);
            retailerName_textview = itemView.findViewById(R.id.retailerName_textview);
            retailermobileno_textview = itemView.findViewById(R.id.retailermobileno_textview);
            parentCardview = itemView.findViewById(R.id.parentCardview);
        }
    }
    }
