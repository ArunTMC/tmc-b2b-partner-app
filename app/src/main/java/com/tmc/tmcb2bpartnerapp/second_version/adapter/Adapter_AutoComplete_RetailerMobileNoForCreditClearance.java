package com.tmc.tmcb2bpartnerapp.second_version.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.BillingScreen;
import com.tmc.tmcb2bpartnerapp.fragment.DeliveryCenter_PlaceOrderScreen_SecondVersn;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.second_version.activity.ClearOutstandingCreditAmountScreen;
import com.tmc.tmcb2bpartnerapp.second_version.activity.Payment_Reports_by_Buyer;
import com.tmc.tmcb2bpartnerapp.second_version.activity.TransactionDetails_by_buyer;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.tmc.tmcb2bpartnerapp.utils.Constants.TAG;

public class Adapter_AutoComplete_RetailerMobileNoForCreditClearance extends ArrayAdapter<Modal_B2BRetailerDetails> {

    ArrayList<Modal_B2BRetailerDetails> completeRetailersList = new ArrayList<>();
    Boolean isResult_is_Zero = false ;
    Context context;
    boolean isFilterUsingMobileNo = false;
    private Handler handler;
    ClearOutstandingCreditAmountScreen clearOutstandingCreditAmountScreen;
    Payment_Reports_by_Buyer payment_reports_by_buyer;
    TransactionDetails_by_buyer transactionDetails_by_buyer;


    public Adapter_AutoComplete_RetailerMobileNoForCreditClearance(Context mContext, ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList, ClearOutstandingCreditAmountScreen clearOutstandingCreditAmountScreenn, boolean isFilterUsingMobileNoo) {
        super(mContext, 0);
        this.completeRetailersList = retailerDetailsArrayList;
        this.context = mContext;
        this.isFilterUsingMobileNo = isFilterUsingMobileNoo;
        this.clearOutstandingCreditAmountScreen = clearOutstandingCreditAmountScreenn;
    }

    public Adapter_AutoComplete_RetailerMobileNoForCreditClearance(Context mContext, ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList, Payment_Reports_by_Buyer payment_reports_by_buyer, boolean isFilterUsingMobileNoo) {
        super(mContext, 0);
        this.completeRetailersList = retailerDetailsArrayList;
        this.context = mContext;
        this.isFilterUsingMobileNo = isFilterUsingMobileNoo;
        this.payment_reports_by_buyer = payment_reports_by_buyer;
    }

    public Adapter_AutoComplete_RetailerMobileNoForCreditClearance(Context mContext, ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList, TransactionDetails_by_buyer transactionDetails_by_buyer1, boolean isFilterUsingMobileNoo) {
        super(mContext, 0);
        this.completeRetailersList = retailerDetailsArrayList;
        this.context = mContext;
        this.isFilterUsingMobileNo = isFilterUsingMobileNoo;
        this.transactionDetails_by_buyer = transactionDetails_by_buyer1;    }


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
                            R.layout.retailername_autocomplete_list_item, parent, false
                    );
                } else {

                    convertView = LayoutInflater.from(getContext()).inflate(
                            R.layout.retailername_autocomplete_list_item, parent, false
                    );
                }
            }
            catch (Exception e){
                e.printStackTrace();
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.retailername_autocomplete_list_item, parent, false
                );

            }

        }

        TextView retailerNumber_widget = convertView.findViewById(R.id.retaialerName);
        LinearLayout noRetailerAlertLayout = convertView.findViewById(R.id.noRetailerAlertLayout);
        Button add_retailerButton = convertView.findViewById(R.id.add_retailerButton);




        Modal_B2BRetailerDetails menuuItem = getItem(position);
        if(isFilterUsingMobileNo){
            retailerNumber_widget.setText(menuuItem.getMobileno());
        }
        else{
            retailerNumber_widget.setText(menuuItem.getRetailername());
        }

        retailerNumber_widget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendHandlerMessage("dropdown",getItem(position).getRetailerkey());

            }
        });




        return convertView;
    }



    private void sendHandlerMessage(String bundlestr,String retailerkey) {
        //Log.e(Constants.TAG, "createBillDetails in cartaItem 1");

        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("dropdown", bundlestr);
        bundle.putString("retailerkey", retailerkey);

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
            List<Modal_B2BRetailerDetails> suggestions = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(completeRetailersList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                if(filterPattern.length()>0){
                for (Modal_B2BRetailerDetails item : completeRetailersList) {
                    if (isFilterUsingMobileNo) {

                        if (item.getMobileno().toLowerCase().contains(filterPattern)) {
                            suggestions.add(item);

                        }
                    } else {
                        if (item.getRetailername().toLowerCase().contains(filterPattern)) {
                            suggestions.add(item);

                        }
                    }
                }
                }
                else{
                    suggestions.addAll(completeRetailersList);
                }
            }

            if(suggestions.size()==0){
                Modal_B2BRetailerDetails item = new Modal_B2BRetailerDetails();
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
            if(isFilterUsingMobileNo) {

                return ((Modal_B2BRetailerDetails) resultValue).getMobileno();
            }
            else{
                return ((Modal_B2BRetailerDetails) resultValue).getRetailername();
            }
        }
    };
}

