package com.tmc.tmcb2bpartnerapp.adapter;

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
import com.tmc.tmcb2bpartnerapp.second_version.activity.CheckOut_activity;
import com.tmc.tmcb2bpartnerapp.second_version.activity.ClearOutstandingCreditAmountScreen;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.tmc.tmcb2bpartnerapp.utils.Constants.TAG;

public class Adapter_AutoComplete_RetailerMobileNo extends ArrayAdapter<Modal_B2BRetailerDetails> {
    ArrayList<Modal_B2BRetailerDetails> completeRetailersList = new ArrayList<>();
    Boolean isResult_is_Zero = false , filterMobileNo = false;
    Context context;
    private Handler handler;
    BillingScreen billingScreen;
    CheckOut_activity checkOut_activity;
    ClearOutstandingCreditAmountScreen clearOutstandingCreditAmountScreen;
    DeliveryCenter_PlaceOrderScreen_SecondVersn deliveryCenter_placeOrderScreen_SecondVersn;
    public Adapter_AutoComplete_RetailerMobileNo(@NonNull Context context, ArrayList<Modal_B2BRetailerDetails> completeRetailersListt , BillingScreen billingScreenn) {
        super(context, 0);
        this.completeRetailersList = completeRetailersListt;
        this.context = context;
        this.billingScreen = billingScreenn;
    }

    public Adapter_AutoComplete_RetailerMobileNo(Context mContext, ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayListt, DeliveryCenter_PlaceOrderScreen_SecondVersn deliveryCenter_placeOrderScreen_SecondVersn) {
        super(mContext, 0);
        this.completeRetailersList = retailerDetailsArrayListt;
        this.context = context;
        this.deliveryCenter_placeOrderScreen_SecondVersn = deliveryCenter_placeOrderScreen_SecondVersn;
    }

    public Adapter_AutoComplete_RetailerMobileNo(Context mContext, ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList, CheckOut_activity checkOut_activity1, boolean flterMobilenNo) {
        super(mContext, 0);
        this.completeRetailersList = retailerDetailsArrayList;
        this.context = mContext;
        this.filterMobileNo = flterMobilenNo;
        this.checkOut_activity = checkOut_activity1;
    }

    public Adapter_AutoComplete_RetailerMobileNo(ClearOutstandingCreditAmountScreen mContext, ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList, ClearOutstandingCreditAmountScreen clearOutstandingCreditAmountScreen, boolean filterMobileNoo) {
        super(mContext, 0);
        this.completeRetailersList = retailerDetailsArrayList;
        this.context = mContext;
        this.filterMobileNo = filterMobileNoo;
        this.clearOutstandingCreditAmountScreen = clearOutstandingCreditAmountScreen;

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
                            R.layout.retailername_autocomplete_list_item, parent, false
                    );
                } else {

                    convertView = LayoutInflater.from(getContext()).inflate(
                            R.layout.pos_retailername_autocomplete_list_item, parent, false
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

        if(isResult_is_Zero){
            Log.d(TAG, "getCount in if : " + String.valueOf(getCount()));
            if(deliveryCenter_placeOrderScreen_SecondVersn!= null) {
                deliveryCenter_placeOrderScreen_SecondVersn.shoeRetailerNotFoundAlert();
            }
            else if(checkOut_activity != null){
                checkOut_activity.showRetailerNotFoundAlert(filterMobileNo);
            }
            noRetailerAlertLayout.setVisibility(View.GONE);
        }
        else{

            Log.d(TAG, "getCount in else : " + String.valueOf(getCount()));

            noRetailerAlertLayout.setVisibility(View.GONE);




        }


        Modal_B2BRetailerDetails menuuItem = getItem(position);
        if(filterMobileNo){
            retailerNumber_widget.setText(menuuItem.getMobileno());
        }
        else{
            retailerNumber_widget.setText(menuuItem.getRetailername());
        }



        if(noRetailerAlertLayout.getVisibility()==View.VISIBLE){
            add_retailerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{
                        if(deliveryCenter_placeOrderScreen_SecondVersn!= null) {
                            deliveryCenter_placeOrderScreen_SecondVersn. showAddRetailerLayout();
                        }
                        else if(checkOut_activity != null){
                            checkOut_activity. showAddRetailerLayout();
                        }

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }


        retailerNumber_widget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (billingScreen != null) {

                    if (BillingScreen.isRetailerSelected) {

                        BillingScreen.oldRetailerKey = BillingScreen.retailerKey;
                        BillingScreen.oldretailerMobileno = BillingScreen.retailermobileno;
                        BillingScreen.oldRetailerName = BillingScreen.retailername;
                        BillingScreen.oldretaileraddress = BillingScreen.retaileraddress;
                        BillingScreen.oldretailerGSTIN = BillingScreen.retailerGSTIN;
                        billingScreen.showAlert_toUpdateCartOrderDetails(context.getString(R.string.retailername));
                    } else {
                        BillingScreen.isRetailerSelected = true;
                    }


                    BillingScreen.retailername = String.valueOf(getItem(position).getRetailername());
                    BillingScreen.retailerKey = String.valueOf(getItem(position).getRetailerkey());
                    BillingScreen.retailermobileno = String.valueOf(getItem(position).getMobileno());
                    BillingScreen.retaileraddress = String.valueOf(getItem(position).getAddress());
                    BillingScreen.retailerGSTIN = String.valueOf(getItem(position).getGstin());
                    BillingScreen.retailerName_autoComplete_Edittext.setText(getItem(position).getMobileno());
                    BillingScreen.retailerName_autoComplete_Edittext.clearFocus();
                }
                else if (deliveryCenter_placeOrderScreen_SecondVersn != null) {

                    if (DeliveryCenter_PlaceOrderScreen_SecondVersn.isRetailerSelected) {

                        DeliveryCenter_PlaceOrderScreen_SecondVersn.oldRetailerKey = DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerKey;
                        DeliveryCenter_PlaceOrderScreen_SecondVersn.oldretailerMobileno = DeliveryCenter_PlaceOrderScreen_SecondVersn.retailermobileno;
                        DeliveryCenter_PlaceOrderScreen_SecondVersn.oldRetailerName = DeliveryCenter_PlaceOrderScreen_SecondVersn.retailername;
                        DeliveryCenter_PlaceOrderScreen_SecondVersn.oldretaileraddress = DeliveryCenter_PlaceOrderScreen_SecondVersn.retaileraddress;
                        DeliveryCenter_PlaceOrderScreen_SecondVersn.oldretailerGSTIN = DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerGSTIN;


                    } else {
                        DeliveryCenter_PlaceOrderScreen_SecondVersn.isRetailerSelected = true;
                    }


                    DeliveryCenter_PlaceOrderScreen_SecondVersn.retailername = String.valueOf(getItem(position).getRetailername());
                    DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerKey = String.valueOf(getItem(position).getRetailerkey());
                    DeliveryCenter_PlaceOrderScreen_SecondVersn.retailermobileno = String.valueOf(getItem(position).getMobileno());
                    DeliveryCenter_PlaceOrderScreen_SecondVersn.retaileraddress = String.valueOf(getItem(position).getAddress());
                    DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerGSTIN = String.valueOf(getItem(position).getGstin());
                    DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerMobileNo_edittext.setText(getItem(position).getMobileno());
                    DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerMobileNo_edittext.clearFocus();
                    DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerName_textView.setText(getItem(position).getRetailername());
                    DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerName_textView.clearFocus();
                    DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerAddress_textView.setText(getItem(position).getAddress());
                    DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerAddress_textView.clearFocus();
                    try {
                        InputMethodManager imm = (InputMethodManager) DeliveryCenter_PlaceOrderScreen_SecondVersn.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                        Objects.requireNonNull(imm).hideSoftInputFromWindow(DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerMobileNo_edittext.getWindowToken(), 0);
                        sendHandlerMessage("dropdown");

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                }
                else if (checkOut_activity != null) {

                    if (checkOut_activity.isRetailerSelected) {

                        checkOut_activity.oldRetailerKey = checkOut_activity.retailerKey;
                        checkOut_activity.oldretailerMobileno = checkOut_activity.retailermobileno;
                        checkOut_activity.oldRetailerName = checkOut_activity.retailername;
                        checkOut_activity.oldretaileraddress = checkOut_activity.retaileraddress;
                        checkOut_activity.oldretailerGSTIN = checkOut_activity.retailerGSTIN;


                    } else {
                        checkOut_activity.isRetailerSelected = true;
                    }


                    checkOut_activity.retailername = String.valueOf(getItem(position).getRetailername());
                    checkOut_activity.retailerKey = String.valueOf(getItem(position).getRetailerkey());
                    checkOut_activity.retailermobileno = String.valueOf(getItem(position).getMobileno());
                    checkOut_activity.retaileraddress = String.valueOf(getItem(position).getAddress());
                    checkOut_activity.retailerGSTIN = String.valueOf(getItem(position).getGstin());
                    checkOut_activity.retailerMobileNo_edittext.setText(getItem(position).getMobileno());
                    checkOut_activity.retailerMobileNo_edittext.clearFocus();
                    checkOut_activity.retailerName_textView.setText(getItem(position).getRetailername());
                    checkOut_activity.retailerName_textView.clearFocus();
                    checkOut_activity.retailerAddress_textView.setText(getItem(position).getAddress());
                    checkOut_activity.retailerAddress_textView.clearFocus();
                    try {
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        Objects.requireNonNull(imm).hideSoftInputFromWindow(checkOut_activity.retailerMobileNo_edittext.getWindowToken(), 0);
                        sendHandlerMessage("dropdown");

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                }
            }

        });


        return convertView;
    }



    private void sendHandlerMessage(String bundlestr) {
        //Log.e(Constants.TAG, "createBillDetails in cartaItem 1");

        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("dropdown", bundlestr);

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
                for (Modal_B2BRetailerDetails item : completeRetailersList) {
                    if(filterMobileNo) {

                        if (item.getMobileno().toLowerCase().contains(filterPattern)) {
                            suggestions.add(item);

                        }
                    }
                    else{
                        if (item.getRetailername().toLowerCase().contains(filterPattern)) {
                            suggestions.add(item);

                        }
                    }
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
            if(filterMobileNo) {

                return ((Modal_B2BRetailerDetails) resultValue).getMobileno();
            }
            else{
                return ((Modal_B2BRetailerDetails) resultValue).getRetailername();
            }
        }
    };
}

