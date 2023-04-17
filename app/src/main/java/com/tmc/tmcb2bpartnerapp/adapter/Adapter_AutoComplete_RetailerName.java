package com.tmc.tmcb2bpartnerapp.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.BillingScreen;
import com.tmc.tmcb2bpartnerapp.fragment.DeliveryCentre_PlaceOrderScreen_Fragment;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class Adapter_AutoComplete_RetailerName extends ArrayAdapter<Modal_B2BRetailerDetails> {
    ArrayList<Modal_B2BRetailerDetails> completeRetailersList = new ArrayList<>();
    Context context;
    private Handler handler;
    BillingScreen billingScreen;
    DeliveryCentre_PlaceOrderScreen_Fragment deliveryCentre_placeOrderScreen_fragment;
    public Adapter_AutoComplete_RetailerName(@NonNull Context context, ArrayList<Modal_B2BRetailerDetails> completeRetailersListt ,  BillingScreen billingScreenn) {
        super(context, 0);
        this.completeRetailersList = completeRetailersListt;
        this.context = context;
        this.billingScreen = billingScreenn;
    }

    public Adapter_AutoComplete_RetailerName(Context mContext, ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayListt, DeliveryCentre_PlaceOrderScreen_Fragment deliveryCentre_placeOrderScreen_fragment) {
        super(mContext, 0);
        this.completeRetailersList = retailerDetailsArrayListt;
        this.context = context;
        this.deliveryCentre_placeOrderScreen_fragment = deliveryCentre_placeOrderScreen_fragment;
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

        TextView retaialerName_widget = convertView.findViewById(R.id.retaialerName);

        Modal_B2BRetailerDetails menuuItem = getItem(position);
        retaialerName_widget.setText(menuuItem.getRetailername());


        retaialerName_widget.setOnClickListener(new View.OnClickListener() {
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
                    BillingScreen.retailerName_autoComplete_Edittext.setText(getItem(position).getRetailername());
                    BillingScreen.retailerName_autoComplete_Edittext.clearFocus();
                }
                else if (deliveryCentre_placeOrderScreen_fragment != null) {

                    if (DeliveryCentre_PlaceOrderScreen_Fragment.isRetailerSelected) {

                       DeliveryCentre_PlaceOrderScreen_Fragment.oldRetailerKey = DeliveryCentre_PlaceOrderScreen_Fragment.retailerKey;
                       DeliveryCentre_PlaceOrderScreen_Fragment.oldretailerMobileno = DeliveryCentre_PlaceOrderScreen_Fragment.retailermobileno;
                       DeliveryCentre_PlaceOrderScreen_Fragment.oldRetailerName = DeliveryCentre_PlaceOrderScreen_Fragment.retailername;
                       DeliveryCentre_PlaceOrderScreen_Fragment.oldretaileraddress = DeliveryCentre_PlaceOrderScreen_Fragment.retaileraddress;
                       DeliveryCentre_PlaceOrderScreen_Fragment.oldretailerGSTIN = DeliveryCentre_PlaceOrderScreen_Fragment.retailerGSTIN;

                        try {
                            DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.showAlert_toUpdateCartOrderDetails("RetailerName");
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    } else {
                        DeliveryCentre_PlaceOrderScreen_Fragment.isRetailerSelected = true;
                    }


                    DeliveryCentre_PlaceOrderScreen_Fragment.retailername = String.valueOf(getItem(position).getRetailername());
                    DeliveryCentre_PlaceOrderScreen_Fragment.retailerKey = String.valueOf(getItem(position).getRetailerkey());
                    DeliveryCentre_PlaceOrderScreen_Fragment.retailermobileno = String.valueOf(getItem(position).getMobileno());
                    DeliveryCentre_PlaceOrderScreen_Fragment.retaileraddress = String.valueOf(getItem(position).getAddress());
                    DeliveryCentre_PlaceOrderScreen_Fragment.retailerGSTIN = String.valueOf(getItem(position).getGstin());
                    DeliveryCentre_PlaceOrderScreen_Fragment.retailerName_autoComplete_Edittext.setText(getItem(position).getRetailername());
                    DeliveryCentre_PlaceOrderScreen_Fragment.retailerName_autoComplete_Edittext.clearFocus();
                }
                }

        });


        return convertView;
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
                    if (item.getRetailername().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
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

            return ((Modal_B2BRetailerDetails) resultValue).getRetailername();
        }
    };
}

