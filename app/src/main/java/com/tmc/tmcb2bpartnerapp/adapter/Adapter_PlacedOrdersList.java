package com.tmc.tmcb2bpartnerapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.DatewisePlacedOrdersList;
import com.tmc.tmcb2bpartnerapp.activity.PlacedOrderDetailsScreen;
import com.tmc.tmcb2bpartnerapp.fragment.DeliveryCenterHomeScreenFragment;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class Adapter_PlacedOrdersList extends ArrayAdapter<Modal_B2BOrderDetails> {
    Context mContext;
    ArrayList<Modal_B2BOrderDetails> orderDetailsList = new ArrayList<>();
    DatewisePlacedOrdersList datewisePlacedOrdersList;
    String calledFrom;
    public Adapter_PlacedOrdersList(Context mContext, ArrayList<Modal_B2BOrderDetails> orderDetailsListt, DatewisePlacedOrdersList datewisePlacedOrdersList, String calledFrom) {
        super(mContext, R.layout.adapter_batch_item_list, orderDetailsListt);
        this.mContext=mContext;
        this.orderDetailsList=orderDetailsListt;
        this.datewisePlacedOrdersList = datewisePlacedOrdersList;
        this.calledFrom = calledFrom;
    }




    @Nullable
    @Override
    public Modal_B2BOrderDetails getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable Modal_B2BOrderDetails item) {
        return super.getPosition(item);
    }


    public View getView(final int pos, View view, ViewGroup v) {
        @SuppressLint("ViewHolder") View listViewItem = null;
        try {
            BaseActivity.baseActivity.getDeviceName();
            for (int i = 0; i < 500; i++) {
                if (i == 499) {
                    if (BaseActivity.isDeviceIsMobilePhone) {
                        listViewItem = LayoutInflater.from(mContext).inflate(R.layout.adapter_order_details_item_list, (ViewGroup) view, false);

                    } else {
                        listViewItem = LayoutInflater.from(mContext).inflate(R.layout.pos_adapter_order_details_item_list, (ViewGroup) view, false);

                    }
                }

            }
        } catch (Exception e) {
            listViewItem = LayoutInflater.from(mContext).inflate(R.layout.adapter_order_details_item_list, (ViewGroup) view, false);

            e.printStackTrace();
        }

        final TextView orderid_textview = listViewItem.findViewById(R.id.orderid_textview);
        final TextView retailername_textview = listViewItem.findViewById(R.id.retailername_textview);
        final TextView retailer_mobileno_textview = listViewItem.findViewById(R.id.retailer_mobileno_textview);
        final TextView orderplacedtime_textview = listViewItem.findViewById(R.id.orderplacedtime_textview);
        final TextView payableAmount_textview = listViewItem.findViewById(R.id.payableAmount_textview);
        final TextView paymentmode_textview = listViewItem.findViewById(R.id.paymentmode_textview);
        final TextView total_qty_textview = listViewItem.findViewById(R.id.total_qty_textview);
        final TextView female_quantity_textview = listViewItem.findViewById(R.id.female_quantity_textview);
        final TextView male_quantity_textview = listViewItem.findViewById(R.id.male_quantity_textview);
        final TextView male_weight_textview = listViewItem.findViewById(R.id.male_weight_textview);
        final TextView female_weight_textview = listViewItem.findViewById(R.id.female_weight_textview);
        final TextView total_weight_textview = listViewItem.findViewById(R.id.total_weight_textview);
        final CardView parent_card_view = listViewItem.findViewById(R.id.parent_card_view);

        Modal_B2BOrderDetails modal_b2BOrderDetails = orderDetailsList.get(pos);

        total_weight_textview.setText(modal_b2BOrderDetails.getTotalweight()+" Kg");
        female_weight_textview.setText(modal_b2BOrderDetails.getTotalfemaleweight()+" Kg");
        male_weight_textview.setText(modal_b2BOrderDetails.getTotalmaleweight()+" Kg");
        male_quantity_textview.setText(modal_b2BOrderDetails.getTotalmalequantity());
        female_quantity_textview.setText(modal_b2BOrderDetails.getTotalfemalequantity());
        total_qty_textview.setText(modal_b2BOrderDetails.getTotalquantity());

        orderid_textview.setText(modal_b2BOrderDetails.getOrderid());
        retailername_textview.setText(modal_b2BOrderDetails.getRetailername());
        retailer_mobileno_textview.setText(modal_b2BOrderDetails.getRetailermobileno());
        orderplacedtime_textview.setText(modal_b2BOrderDetails.getOrderplaceddate());
        paymentmode_textview.setText(modal_b2BOrderDetails.getPaymentmode());
        total_qty_textview.setText(modal_b2BOrderDetails.getTotalquantity());
        payableAmount_textview.setText("Rs. "+modal_b2BOrderDetails.getPayableAmount() );

        parent_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(mContext, PlacedOrderDetailsScreen.class);
                    i.putExtra("orderid", modal_b2BOrderDetails.getOrderid());
                    i.putExtra("paymentmode", modal_b2BOrderDetails.getPaymentmode());
                    i.putExtra("retailerkey", modal_b2BOrderDetails.getRetailerkey());
                    i.putExtra("discountamount", modal_b2BOrderDetails.getDiscountamount()  );
                    i.putExtra("invoiceno", modal_b2BOrderDetails.getInvoiceno());

                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(i);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });



        return listViewItem;

    }


    }
