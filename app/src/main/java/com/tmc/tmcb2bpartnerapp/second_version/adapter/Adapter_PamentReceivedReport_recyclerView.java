package com.tmc.tmcb2bpartnerapp.second_version.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal__B2BPaymentDetails;
import com.tmc.tmcb2bpartnerapp.second_version.activity.PaymentReceivedReport;
import com.tmc.tmcb2bpartnerapp.utils.Constants;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_PamentReceivedReport_recyclerView  extends RecyclerView.Adapter<Adapter_PamentReceivedReport_recyclerView.MyViewHolder> {

    ArrayList<Modal__B2BPaymentDetails> paymentDetailsArrayList;
    PaymentReceivedReport paymentReceivedReport;
    Context mContext;
    static DecimalFormat twoDecimalConverter = new DecimalFormat(Constants.twoDecimalPattern);
    static DecimalFormat twoDecimalConverterWithComma = new DecimalFormat(Constants.twoDecimalWithCommaPattern);


    public Adapter_PamentReceivedReport_recyclerView(ArrayList<Modal__B2BPaymentDetails> modelList, PaymentReceivedReport paymentReceivedReport, Context context) {
        paymentDetailsArrayList = modelList;
        this.mContext = context;
        this.paymentReceivedReport = paymentReceivedReport;

    }


    @Override
    public Adapter_PamentReceivedReport_recyclerView.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_receivedreport_recycler_item, parent, false);
        return new Adapter_PamentReceivedReport_recyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final Adapter_PamentReceivedReport_recyclerView.MyViewHolder holder, int position) {
        final Modal__B2BPaymentDetails model = paymentDetailsArrayList.get(position);

        try{
            holder.retailerName_textView.setText(String.valueOf(model.getRetailername()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try{
         String   text = String.valueOf(model.getRetailermobileno().toString()).replaceAll("[+]91", "");

            holder.retailerMobileNo_textView.setText(String.valueOf(text));
        }
        catch (Exception e){
            holder.retailerMobileNo_textView.setText(String.valueOf(model.getRetailermobileno()));

            e.printStackTrace();
        }
        try{
            holder.paymentMode_textView.setText(String.valueOf(model.getPaymentmode()));

        }
        catch (Exception e){
            e.printStackTrace();
        }
        try{
            holder.transactiontime_textView.setText(String.valueOf(model.getTransactiontime()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try{

            String text = ""; double transactionValueDouble= 0;
            try {

                text = String.valueOf(model.getTransactionvalue().toString()).replaceAll("[^\\d.]", "");
                if (text.equals("")) {
                    text = "0";
                } else {
                    text = text;
                }
            } catch (Exception e) {
                text = "0";
                e.printStackTrace();
            }

            try {
                transactionValueDouble = Double.parseDouble(text);
            } catch (Exception e) {
                transactionValueDouble = 0;
                e.printStackTrace();
            }
            try{
                transactionValueDouble = Double.parseDouble(twoDecimalConverter.format(transactionValueDouble));
            }
            catch (Exception e){
                e.printStackTrace();
            }

            try {
                holder.transactionvalue_textView.setText(String.valueOf(twoDecimalConverterWithComma.format(transactionValueDouble)) );
            }
            catch (Exception e){
                holder.transactionvalue_textView.setText(String.valueOf(transactionValueDouble));
                e.printStackTrace();
            }
            try{
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try{
            String text = "";
            try {

                text = String.valueOf(model.getTransactiontype().toString());
                if (text.equals("")) {
                    text = "";
                } else {
                    text = text;
                }
            } catch (Exception e) {
                text = "";
                e.printStackTrace();
            }

            if(text.toUpperCase().equals(Constants.transactiontype_OUTSTANDINGPAYMENT)){
                holder.transactionType_textview.setText(String.valueOf("AMOUNT RECEIVED"));
            }
            else if(text.toUpperCase().equals(Constants.transactiontype_ORDERPAYMENT)){
                holder.transactionType_textview.setText(String.valueOf("ORDER PLACED"));
            }




        }
        catch (Exception e){
            e.printStackTrace();
        }
        try{

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getItemCount() {
        return paymentDetailsArrayList == null ? 0 : paymentDetailsArrayList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View wholeview;
        TextView retailerName_textView , retailerMobileNo_textView , paymentMode_textView , transactiontime_textView,
                transactionvalue_textView, transactionType_textview;

        private MyViewHolder(View itemView) {
            super(itemView);
            wholeview = itemView;
            retailerName_textView  = itemView.findViewById(R.id.retailerName_textView);
            retailerMobileNo_textView  = itemView.findViewById(R.id.retailerMobileNo_textView);
            paymentMode_textView  = itemView.findViewById(R.id.paymentMode_textView);
            transactiontime_textView  = itemView.findViewById(R.id.transactiontime_textView);
            transactionvalue_textView  = itemView.findViewById(R.id.transactionvalue_textView);
            transactionType_textview = itemView.findViewById(R.id.transactionType_textview);

        }
    }






    }
