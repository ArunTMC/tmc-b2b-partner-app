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

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.activity.BillingScreen;
import com.tmc.tmcb2bpartnerapp.activity.FinishBatch_ConsolidatedReport;
import com.tmc.tmcb2bpartnerapp.activity.View_or_Edit_BatchItem_deliveryCenter;
import com.tmc.tmcb2bpartnerapp.fragment.DeliveryCenterHomeScreenFragment;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BBatchDetailsStatic;
import com.tmc.tmcb2bpartnerapp.utils.Constants;

import java.util.List;

public class Adapter_B2BBatchItemsList  extends ArrayAdapter<Modal_B2BBatchDetails> {
    Context mContext;
    List<Modal_B2BBatchDetails> batchDetailsList;
    DeliveryCenterHomeScreenFragment deliveryCenterHomeScreenFragment;
    String calledFrom = "";


    public Adapter_B2BBatchItemsList(Context mContext, List<Modal_B2BBatchDetails> batchDetailsListt, DeliveryCenterHomeScreenFragment deliveryCenterHomeScreenFragmentt, String calledFrom) {
        super(mContext, R.layout.adapter_batch_item_list, batchDetailsListt);
        this.mContext=mContext;
        this.batchDetailsList=batchDetailsListt;
        this.deliveryCenterHomeScreenFragment = deliveryCenterHomeScreenFragmentt;
        this.calledFrom = calledFrom;
    }


    @Nullable
    @Override
    public Modal_B2BBatchDetails getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable Modal_B2BBatchDetails item) {
        return super.getPosition(item);
    }

    public View getView(final int pos, View view, ViewGroup v) {
        @SuppressLint("ViewHolder")  View listViewItem = null;
        try{
            BaseActivity.baseActivity.getDeviceName();
            for(int i =0 ; i<500; i++){
                if(i==499){
                    if(BaseActivity.isDeviceIsMobilePhone){
                        listViewItem =     LayoutInflater.from(mContext).inflate(R.layout.adapter_batch_item_list, (ViewGroup) view, false);

                    }
                    else{
                        listViewItem = LayoutInflater.from(mContext).inflate(R.layout.pos_adapter_batch_item_list, (ViewGroup) view, false);

                    }
                }

            }
        }
        catch (Exception e){
            listViewItem = LayoutInflater.from(mContext).inflate(R.layout.adapter_batch_item_list, (ViewGroup) view, false);

            e.printStackTrace();
        }

        final TextView batchNo_textview = listViewItem.findViewById(R.id.batchNo_textview);
        final TextView batchStatus_textview = listViewItem.findViewById(R.id.batchStatus_textview);
        final TextView batch_SentDate = listViewItem.findViewById(R.id.batch_SentDate);
        final Button viewBatchDetails_Button = listViewItem.findViewById(R.id.viewBatchDetails_Button);
        final Button viewReports_Button = listViewItem.findViewById(R.id.viewReports_Button);
        Modal_B2BBatchDetails modal_b2BBatchDetails = batchDetailsList.get(pos);


        batchNo_textview.setText(modal_b2BBatchDetails.getBatchno());

        if(calledFrom . equals(mContext.getString(R.string.placeOrder))){
            viewBatchDetails_Button.setText(mContext.getString(R.string.placeOrder));
        }
        else {
            viewBatchDetails_Button.setText(mContext.getString(R.string.view));

        }


        if(modal_b2BBatchDetails.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Fully_Loaded)){
            batchStatus_textview.setText(Constants.batchDetailsStatus_UnReviewed_DeliveryCenter);
        }
        else {
            batchStatus_textview.setText(modal_b2BBatchDetails.getStatus());
        }
        //batchStatus_textview.setText(modal_b2BBatchDetails.getStatus());


        batch_SentDate.setText(modal_b2BBatchDetails.getSentdate());

        viewReports_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Modal_B2BBatchDetailsStatic.setBatchno(modal_b2BBatchDetails.getBatchno());
                Modal_B2BBatchDetailsStatic.setCreateddate(modal_b2BBatchDetails.getCreateddate());
                Modal_B2BBatchDetailsStatic.setDeliverycenterkey(modal_b2BBatchDetails.getDeliverycenterkey());
                Modal_B2BBatchDetailsStatic.setDeliverycentername(modal_b2BBatchDetails.getDeliverycentername());
                Modal_B2BBatchDetailsStatic.setItemcount(modal_b2BBatchDetails.getItemcount());
                Modal_B2BBatchDetailsStatic.setLoadedweightingrams(modal_b2BBatchDetails.getLoadedweightingrams());
                Modal_B2BBatchDetailsStatic.setReceiveddate(modal_b2BBatchDetails.getReceiveddate());
                Modal_B2BBatchDetailsStatic.setReceivermobileno(modal_b2BBatchDetails.getReceivermobileno());
                Modal_B2BBatchDetailsStatic.setStatus(modal_b2BBatchDetails.getStatus());
                Modal_B2BBatchDetailsStatic.setSentdate(modal_b2BBatchDetails.getSentdate());
                Modal_B2BBatchDetailsStatic.setStockedweightingrams(modal_b2BBatchDetails.getStockedweightingrams());
                Modal_B2BBatchDetailsStatic.setSupplierkey(modal_b2BBatchDetails.getSupplierkey());
                Modal_B2BBatchDetailsStatic.setSuppliermobileno(modal_b2BBatchDetails.getSuppliermobileno());
                Modal_B2BBatchDetailsStatic.setSuppliername(modal_b2BBatchDetails.getSuppliername());





                Intent intent1 = new Intent(mContext, FinishBatch_ConsolidatedReport.class);
                intent1.putExtra("batchno", modal_b2BBatchDetails.getBatchno()) ;
                intent1.putExtra("deliveryCenterKey",  deliveryCenterHomeScreenFragment.deliveryCenterKey) ;
                intent1.putExtra("deliveryCenterName", deliveryCenterHomeScreenFragment.deliveryCenterName) ;
                intent1.putExtra(mContext.getString(R.string.called_from), mContext.getString(R.string.delivery_center_batchDetails)) ;
                mContext.startActivity(intent1);


            }
        });

        viewBatchDetails_Button.setOnClickListener(view1 -> {

            Modal_B2BBatchDetailsStatic.setBatchno(modal_b2BBatchDetails.getBatchno());
            Modal_B2BBatchDetailsStatic.setCreateddate(modal_b2BBatchDetails.getCreateddate());
            Modal_B2BBatchDetailsStatic.setDeliverycenterkey(modal_b2BBatchDetails.getDeliverycenterkey());
            Modal_B2BBatchDetailsStatic.setDeliverycentername(modal_b2BBatchDetails.getDeliverycentername());
            Modal_B2BBatchDetailsStatic.setItemcount(modal_b2BBatchDetails.getItemcount());
            Modal_B2BBatchDetailsStatic.setLoadedweightingrams(modal_b2BBatchDetails.getLoadedweightingrams());
            Modal_B2BBatchDetailsStatic.setReceiveddate(modal_b2BBatchDetails.getReceiveddate());
            Modal_B2BBatchDetailsStatic.setReceivermobileno(modal_b2BBatchDetails.getReceivermobileno());
            Modal_B2BBatchDetailsStatic.setStatus(modal_b2BBatchDetails.getStatus());
            Modal_B2BBatchDetailsStatic.setSentdate(modal_b2BBatchDetails.getSentdate());
            Modal_B2BBatchDetailsStatic.setStockedweightingrams(modal_b2BBatchDetails.getStockedweightingrams());
            Modal_B2BBatchDetailsStatic.setSupplierkey(modal_b2BBatchDetails.getSupplierkey());
            Modal_B2BBatchDetailsStatic.setSuppliermobileno(modal_b2BBatchDetails.getSuppliermobileno());
            Modal_B2BBatchDetailsStatic.setSuppliername(modal_b2BBatchDetails.getSuppliername());


              if(calledFrom . equals(mContext.getString(R.string.placeOrder))){
                  Intent intent = new Intent(mContext, BillingScreen.class);
                  intent.putExtra("batchno", modal_b2BBatchDetails.getBatchno() );
                  intent.putExtra("supplierkey", modal_b2BBatchDetails.getSupplierkey() );
                  intent.putExtra("suppliername", modal_b2BBatchDetails.getSuppliername() );
                  intent.putExtra("itemposition", pos );
                  intent.putExtra(mContext.getString(R.string.called_from), mContext.getString(R.string.view_audit_batch_item));
                  intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                  mContext.startActivity(intent);
            }
              else{
                  Intent intent = new Intent(mContext, View_or_Edit_BatchItem_deliveryCenter.class);
                  intent.putExtra("batchno", modal_b2BBatchDetails.getBatchno() );
                  intent.putExtra("supplierkey", modal_b2BBatchDetails.getSupplierkey() );
                  intent.putExtra("suppliername", modal_b2BBatchDetails.getSuppliername() );
                  intent.putExtra("itemposition", pos );
                  intent.putExtra(mContext.getString(R.string.called_from), mContext.getString(R.string.view_audit_batch_item));
                  intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                  mContext.startActivity(intent);
              }


        });

        return listViewItem;
    }
    }
