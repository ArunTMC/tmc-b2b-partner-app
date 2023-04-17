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
import com.tmc.tmcb2bpartnerapp.activity.Audit_UnstockedBatch_item;
import com.tmc.tmcb2bpartnerapp.activity.UnStockedBatchEarTagItemList;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_Static_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;

import java.util.List;

public class Adapter_UnstockedItemsList extends ArrayAdapter<Modal_GoatEarTagDetails> {
    Context mContext;
    List<Modal_GoatEarTagDetails> earTagDetailsList;
    UnStockedBatchEarTagItemList unStockedBatchEarTagItemList;

    public Adapter_UnstockedItemsList(Context mContext, List<Modal_GoatEarTagDetails> earTagDetailsListt, UnStockedBatchEarTagItemList unStockedBatchEarTagItemmList) {
        super(mContext, R.layout.adapter_goat_eartag_details_item_list, earTagDetailsListt);
        this.mContext=mContext;
        this.earTagDetailsList=earTagDetailsListt;
        this.unStockedBatchEarTagItemList = unStockedBatchEarTagItemmList;
    }


    @Nullable
    @Override
    public Modal_GoatEarTagDetails getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable Modal_GoatEarTagDetails item) {
        return super.getPosition(item);
    }

    public View getView(final int pos, View view, ViewGroup v) {
        @SuppressLint("ViewHolder") final View listViewItem = LayoutInflater.from(mContext).inflate(R.layout.adapter_goat_eartag_details_item_list, (ViewGroup) view, false);

        final TextView barcodeno_textview = listViewItem.findViewById(R.id.barcodeno_textview);
        final TextView genderName_textview = listViewItem.findViewById(R.id.genderName_textview);
        final TextView breedType_textview = listViewItem.findViewById(R.id.breedType_textview);
        final TextView currentweight_textview = listViewItem.findViewById(R.id.currentweight_textview);
        final Button auditItem_button = listViewItem.findViewById(R.id.auditItem_button);


        Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagDetailsList.get(pos);
        barcodeno_textview.setText(modal_goatEarTagDetails.getBarcodeno());
        genderName_textview.setText(modal_goatEarTagDetails.getGender());
        breedType_textview.setText(modal_goatEarTagDetails.getBreedtype());
        currentweight_textview.setText(modal_goatEarTagDetails.getCurrentweightingrams());





        auditItem_button.setOnClickListener(view1 -> {


            Modal_Static_GoatEarTagDetails.batchno = modal_goatEarTagDetails.getBatchno();
            Modal_Static_GoatEarTagDetails.barcodeno = modal_goatEarTagDetails.getBarcodeno();
            Modal_Static_GoatEarTagDetails.currentweightingrams = modal_goatEarTagDetails.getCurrentweightingrams();
            Modal_Static_GoatEarTagDetails.loadedweightingrams = modal_goatEarTagDetails.getLoadedweightingrams();
            Modal_Static_GoatEarTagDetails.stockedweightingrams = modal_goatEarTagDetails.getStockedweightingrams();
            Modal_Static_GoatEarTagDetails.itemaddeddate = DateParser.getDate_and_time_newFormat();
            Modal_Static_GoatEarTagDetails.status = modal_goatEarTagDetails.getStatus();
            Modal_Static_GoatEarTagDetails.gender = modal_goatEarTagDetails.getGender();
            Modal_Static_GoatEarTagDetails.breedtype = modal_goatEarTagDetails.getBreedtype();
            Modal_Static_GoatEarTagDetails.supplierkey = modal_goatEarTagDetails.getSupplierkey();
            Modal_Static_GoatEarTagDetails.suppliername = modal_goatEarTagDetails.getSuppliername();
            Modal_Static_GoatEarTagDetails.description = modal_goatEarTagDetails.getDescription();


            Intent intent = new Intent(mContext, Audit_UnstockedBatch_item.class);
            intent.putExtra("batchno",modal_goatEarTagDetails.getBatchno());
            intent.putExtra("barcodeno",modal_goatEarTagDetails.getBarcodeno());
            intent.putExtra("supplierkey",modal_goatEarTagDetails.getSupplierkey());
            intent.putExtra("itemsposition",String.valueOf(pos));
            mContext.startActivity(intent);

        });


        return listViewItem;
    }
    }
