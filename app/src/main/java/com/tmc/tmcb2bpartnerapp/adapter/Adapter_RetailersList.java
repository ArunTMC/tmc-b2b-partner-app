package com.tmc.tmcb2bpartnerapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.Add_Or_Edit_Retailer_Activity;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;

import java.util.List;

public class Adapter_RetailersList extends ArrayAdapter<Modal_B2BRetailerDetails> {
    Context mContext;
    List<Modal_B2BRetailerDetails> retailerDetailsList;
    Add_Or_Edit_Retailer_Activity add_or_edit_retailer_activity;

    public Adapter_RetailersList(Context mContext, List<Modal_B2BRetailerDetails> earTagDetailsListt, Add_Or_Edit_Retailer_Activity add_or_edit_retailer_activityy) {
        super(mContext, R.layout.adapter_retailer_details_item_list, earTagDetailsListt);
        this.mContext = mContext;
        this.retailerDetailsList = earTagDetailsListt;
        this.add_or_edit_retailer_activity = add_or_edit_retailer_activityy;

    }


    @Nullable
    @Override
    public Modal_B2BRetailerDetails getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable Modal_B2BRetailerDetails item) {
        return super.getPosition(item);
    }




    public View getView(final int pos, View view, ViewGroup v) {
        @SuppressLint("ViewHolder") View listViewItem = null;
        try {
            BaseActivity.baseActivity.getDeviceName();
            for (int i = 0; i < 500; i++) {
                if (i == 499) {
                    if (BaseActivity.isDeviceIsMobilePhone) {


                        listViewItem = LayoutInflater.from(mContext).inflate(R.layout.adapter_retailer_details_item_list, (ViewGroup) view, false);


                    } else {
                        listViewItem = LayoutInflater.from(mContext).inflate(R.layout.pos_adapter_retailer_details_item_list, (ViewGroup) view, false);

                    }
                }

            }
        } catch (Exception e) {
            listViewItem = LayoutInflater.from(mContext).inflate(R.layout.adapter_retailer_details_item_list, (ViewGroup) view, false);

            e.printStackTrace();
        }



        final TextView retailerMobileNo_edittext = listViewItem.findViewById(R.id.retailerMobileNo_edittext);
        final TextView retailerName_edittext = listViewItem.findViewById(R.id.retailerName_edittext);
        final TextView edit_button = listViewItem.findViewById(R.id.edit_button);


        retailerMobileNo_edittext.setText(String.valueOf(retailerDetailsList.get(pos).getMobileno()).replaceAll("[+]91",""));
        retailerName_edittext.setText(String.valueOf(retailerDetailsList.get(pos).getRetailername()));

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_or_edit_retailer_activity.showRetaierDetailsDialog(retailerDetailsList.get(pos) , true);
            }
        });







        return listViewItem;
    }
}
