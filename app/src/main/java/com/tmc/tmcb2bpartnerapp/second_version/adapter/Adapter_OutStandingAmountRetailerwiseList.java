package com.tmc.tmcb2bpartnerapp.second_version.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal_B2BRetailerCreditDetails;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal_ConsolidatedSalesReport;
import com.tmc.tmcb2bpartnerapp.second_version.activity.ConsolidatedSalesReport;
import com.tmc.tmcb2bpartnerapp.second_version.activity.OutStandingReport;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_OutStandingAmountRetailerwiseList extends RecyclerView.Adapter<Adapter_OutStandingAmountRetailerwiseList.MyViewHolder> {


    ArrayList<Modal_B2BRetailerCreditDetails> retailerCreditDetailsArrayList;
    OutStandingReport outStandingReport;
    Context mContext;
    static DecimalFormat twoDecimalConverter = new DecimalFormat(Constants.twoDecimalPattern);
    static DecimalFormat twoDecimalConverterWithComma = new DecimalFormat(Constants.twoDecimalWithCommaPattern);


    public Adapter_OutStandingAmountRetailerwiseList(ArrayList< Modal_B2BRetailerCreditDetails > modelList, OutStandingReport outStandingReportt ,Context context) {
        retailerCreditDetailsArrayList = modelList;
        this.mContext = context;
        this.outStandingReport = outStandingReportt;

    }



    @Override
    public Adapter_OutStandingAmountRetailerwiseList.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_to_record_outstanding_payment, parent, false);
        return new Adapter_OutStandingAmountRetailerwiseList.MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final Adapter_OutStandingAmountRetailerwiseList.MyViewHolder holder, int position)
    {
        final Modal_B2BRetailerCreditDetails model = retailerCreditDetailsArrayList.get(position);
        try {


            String text = ""; double oldCreditAmountOfUser= 0;
            try {

                text = String.valueOf(model.getTotalamountincredit().toString()).replaceAll("[^\\d.]", "");
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
                oldCreditAmountOfUser = Double.parseDouble(text);
            } catch (Exception e) {
                oldCreditAmountOfUser = 0;
                e.printStackTrace();
            }
            try{
                oldCreditAmountOfUser = Double.parseDouble(twoDecimalConverter.format(oldCreditAmountOfUser));
            }
            catch (Exception e){
                e.printStackTrace();
            }

            try {
                holder.totalOutStandingAmount_textview.setText(String.valueOf(twoDecimalConverterWithComma.format(oldCreditAmountOfUser)) );
            }
            catch (Exception e){
                holder.totalOutStandingAmount_textview.setText(String.valueOf(oldCreditAmountOfUser) );
                e.printStackTrace();
            }



            holder.retailerName_textView.setText(String.valueOf(model.getRetailername()));
         //   holder.retailerMobileNo_textView.setText(String.valueOf(model.getRetailermobileno()));
            try {
              //  holder.lastUpdateTimeTextview.setText((String.valueOf(model.getLastupdatedtime())));
            }
            catch (Exception e){
             //   holder.lastUpdateTimeTextview.setText(String.valueOf(model.getLastupdatedtime()));

                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
    public int getItemCount() {
        return retailerCreditDetailsArrayList == null ? 0 : retailerCreditDetailsArrayList.size();
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View wholeview;
        TextView totalOutStandingAmount_textview , retailerName_textView , retailerMobileNo_textView , retaileraddress_textview,
                lastUpdateTimeTextview;

        private MyViewHolder(View itemView) {
            super(itemView);
            wholeview = itemView;
            totalOutStandingAmount_textview  = itemView.findViewById(R.id.totalOutStandingAmount_textview);
            retailerName_textView  = itemView.findViewById(R.id.retailerName_textView);
         //   retailerMobileNo_textView  = itemView.findViewById(R.id.retailerMobileNo_textView);
         //   retaileraddress_textview  = itemView.findViewById(R.id.retaileraddress_textview);
        //    lastUpdateTimeTextview  = itemView.findViewById(R.id.lastUpdateTimeTextview);


        }
    }
}
