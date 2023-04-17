package com.tmc.tmcb2bpartnerapp.second_version.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.second_version.activity.ConsolidatedSalesReport;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class Adapter_AutoComplete_ConsolidatedSalesReport extends ArrayAdapter<Modal_B2BBatchDetails> {


    ArrayList<Modal_B2BBatchDetails> batchDetailsArrayList = new ArrayList<>();
    Boolean isResult_is_Zero = false , filterMobileNo = false;
    Context context;
    private Handler handler;
    ConsolidatedSalesReport consolidatedSalesReport;


    public Adapter_AutoComplete_ConsolidatedSalesReport(Context mContext, ArrayList<Modal_B2BBatchDetails> batchDetailsArrayList, ConsolidatedSalesReport consolidatedSalesReport) {
        super(mContext, 0);
        this.batchDetailsArrayList = batchDetailsArrayList;
        this.context = mContext;
        this.consolidatedSalesReport = consolidatedSalesReport;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return menuFilter;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }




    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if (convertView == null) {

            try {
                BaseActivity.baseActivity.getDeviceName();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            try {
                if (BaseActivity.isDeviceIsMobilePhone) {

                    convertView = LayoutInflater.from(getContext()).inflate(
                            R.layout.batch_list_spinner_item, parent, false
                    );
                }
            }
            catch (Exception e){
                e.printStackTrace();
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.batch_list_spinner_item, parent, false
                );

            }

        }
        TextView batchNonotFound_textview = convertView.findViewById(R.id.batchNonotFound_textview);
        TextView batchNo_textview = convertView.findViewById(R.id.batchNo_textview);

        View divider = convertView.findViewById(R.id.divider);
        ConstraintLayout parentLayout  = convertView.findViewById(R.id.parentLayout);
        Modal_B2BBatchDetails menuuItem = getItem(position);
        batchNo_textview.setText(String.valueOf(menuuItem.getBatchno()));

        if(getCount()>0) {
            batchNonotFound_textview.setVisibility(View.GONE);
            if (getCount() > 1) {
                divider.setVisibility(View.VISIBLE);
            } else {
                divider.setVisibility(View.GONE);
            }
        }
        else{
            batchNonotFound_textview.setVisibility(View.VISIBLE);
        }



        batchNo_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendHandlerMessage("dropdown",getItem(position).getBatchno());

            }
        });




        return convertView;
    }



    private void sendHandlerMessage(String bundlestr, String batchno) {
        //Log.e(Constants.TAG, "createBillDetails in cartaItem 1");

        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("dropdown", bundlestr);
        bundle.putString("batchno",batchno);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    private Filter menuFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Modal_B2BBatchDetails> suggestions = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(batchDetailsArrayList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Modal_B2BBatchDetails item : batchDetailsArrayList) {
                    if (item.getBatchno().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);

                    }

                }
            }

            if(suggestions.size()==0){
                Modal_B2BBatchDetails item = new Modal_B2BBatchDetails();
                suggestions.add(item);
                isResult_is_Zero = true;
            }
            else{
                isResult_is_Zero = false;

            }
            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }
        @Override
        public CharSequence convertResultToString(Object resultValue) {


                return ((Modal_B2BBatchDetails) resultValue).getBatchno();
        }
    };
}