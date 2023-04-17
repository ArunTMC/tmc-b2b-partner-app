package com.tmc.tmcb2bpartnerapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.DatewisePlacedOrdersList;
import com.tmc.tmcb2bpartnerapp.activity.PlacedOrderDetailsScreen;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.second_version.activity.MarkDeliveredGoats_in_an_Order;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;

import java.util.ArrayList;

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


    @SuppressLint("SetTextI18n")
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
        final TextView tokenNo_textview = listViewItem.findViewById(R.id.tokenNo_textview);

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
        final TextView priceWithDiscount_textview = listViewItem.findViewById(R.id.priceWithDiscount_textview);
        final TextView discount_textview = listViewItem.findViewById(R.id.discount_textview);
        final TextView supervisorName_textview = listViewItem.findViewById(R.id.supervisorName_textview);
        final TextView supervisorMobileNo_textview = listViewItem.findViewById(R.id.supervisorMobileNo_textview);


        final LinearLayout payableAmount_parentlayout = listViewItem.findViewById(R.id.payableAmount_parentlayout);
        final LinearLayout supervisorNameParentlayout = listViewItem.findViewById(R.id.supervisorNameParentlayout);
        final LinearLayout totalCountParentLayout = listViewItem.findViewById(R.id.totalCountParentLayout);
        final LinearLayout supervisorMobileNoParentlayot = listViewItem.findViewById(R.id.supervisorMobileNoParentlayot);



        if(calledFrom.equals("datewiseorderslistscreen")){

            payableAmount_parentlayout.setVisibility(View.VISIBLE);
            supervisorNameParentlayout.setVisibility(View.VISIBLE);
            totalCountParentLayout.setVisibility(View.VISIBLE);
            supervisorMobileNoParentlayot.setVisibility(View.VISIBLE);

        }
        else if(calledFrom.equals("markasdeliveredorderscreen")){

            payableAmount_parentlayout.setVisibility(View.GONE);
            supervisorNameParentlayout.setVisibility(View.GONE);
            totalCountParentLayout.setVisibility(View.GONE);
            supervisorMobileNoParentlayot.setVisibility(View.GONE);
        }
        else{

            payableAmount_parentlayout.setVisibility(View.VISIBLE);
            supervisorNameParentlayout.setVisibility(View.VISIBLE);
            totalCountParentLayout.setVisibility(View.VISIBLE);
            supervisorMobileNoParentlayot.setVisibility(View.VISIBLE);
        }



        final CardView parent_card_view = listViewItem.findViewById(R.id.parent_card_view);

        Modal_B2BOrderDetails modal_b2BOrderDetails = orderDetailsList.get(pos);


        double payableAmount = 0 , discountAmount = 0, priceWithDiscount = 0;
        try{
            String text =  String.valueOf(modal_b2BOrderDetails.getTotalPrice());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }
            payableAmount  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{
            String text =  String.valueOf(modal_b2BOrderDetails.getDiscountamount());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }
            discountAmount  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }



        try{

            priceWithDiscount  = discountAmount + payableAmount ;

        }
        catch (Exception e){
            e.printStackTrace();
        }



        supervisorMobileNo_textview.setText(modal_b2BOrderDetails.getSupervisormobileno());
        supervisorName_textview.setText(modal_b2BOrderDetails.getSupervisorname());
        total_weight_textview.setText(modal_b2BOrderDetails.getTotalweight()+" Kg");
        female_weight_textview.setText(modal_b2BOrderDetails.getTotalfemaleweight()+" Kg");
        male_weight_textview.setText(modal_b2BOrderDetails.getTotalmaleweight()+" Kg");
        male_quantity_textview.setText(modal_b2BOrderDetails.getTotalmalequantity());
        female_quantity_textview.setText(modal_b2BOrderDetails.getTotalfemalequantity());
        total_qty_textview.setText(modal_b2BOrderDetails.getTotalquantity());


        tokenNo_textview.setText(modal_b2BOrderDetails.getBillno());

        orderid_textview.setText(modal_b2BOrderDetails.getOrderid());
        retailername_textview.setText(modal_b2BOrderDetails.getRetailername());
        retailer_mobileno_textview.setText(modal_b2BOrderDetails.getRetailermobileno());
        orderplacedtime_textview.setText(modal_b2BOrderDetails.getOrderplaceddate());
        paymentmode_textview.setText(modal_b2BOrderDetails.getPaymentmode());
        total_qty_textview.setText(modal_b2BOrderDetails.getTotalquantity());
        payableAmount_textview.setText("Rs. "+modal_b2BOrderDetails.getTotalPrice() );
        discount_textview .setText("Rs. "+discountAmount );
        priceWithDiscount_textview .setText("Rs. "+String.valueOf(priceWithDiscount) );


        parent_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    double payableAmount = 0 , discountAmount = 0, priceWithDiscount = 0;
                    try{
                        String text =  String.valueOf(modal_b2BOrderDetails.getTotalPrice());
                        text = text.replaceAll("[^\\d.]", "");
                        if(text.equals("")){
                            text = "0";
                        }
                        payableAmount  = Double.parseDouble(text);


                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    try{
                        String text =  String.valueOf(modal_b2BOrderDetails.getDiscountamount());
                        text = text.replaceAll("[^\\d.]", "");
                        if(text.equals("")){
                            text = "0";
                        }
                        discountAmount  = Double.parseDouble(text);


                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }



                    try{

                        priceWithDiscount  = discountAmount + payableAmount ;

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }




                    if(calledFrom.equals("datewiseorderslistscreen")){
                        Intent i = new Intent(mContext, PlacedOrderDetailsScreen.class);
                        i.putExtra("orderdetailsPojoClass", orderDetailsList.get(pos));

                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);

                    }
                    else if(calledFrom.equals("markasdeliveredorderscreen")){

                        Intent i = new Intent(mContext, MarkDeliveredGoats_in_an_Order.class);
                        i.putExtra("orderdetailsPojoClass", orderDetailsList.get(pos));

                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                    }
                    else{
                        Intent i = new Intent(mContext, PlacedOrderDetailsScreen.class);
                        i.putExtra("orderdetailsPojoClass", orderDetailsList.get(pos));

                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);

                    }




/*
                    i.putExtra("orderid", orderDetailsList.get(pos).getOrderid());
                    i.putExtra("totalweight",orderDetailsList.get(pos).getTotalweight());
                    i.putExtra("quantity", orderDetailsList.get(pos).getTotalquantity());
                    i.putExtra("retailername", orderDetailsList.get(pos).getRetailername());
                    i.putExtra("retailermobileno", orderDetailsList.get(pos).getRetailermobileno());
                    i.putExtra("price", priceWithDiscount);
                    i.putExtra("totalprice", payableAmount);
                    i.putExtra("discountamount", String.valueOf( discountAmount));
                    i.putExtra("invoiceno", modal_b2BOrderDetails.getInvoiceno());
                    i.putExtra("retaileraddress", modal_b2BOrderDetails.getRetaileraddress());


                     */


                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        return listViewItem;

    }


    }
