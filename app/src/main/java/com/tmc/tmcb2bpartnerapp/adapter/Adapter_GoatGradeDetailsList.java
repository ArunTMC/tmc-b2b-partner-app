package com.tmc.tmcb2bpartnerapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.ChangeGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;

import java.util.ArrayList;

public class Adapter_GoatGradeDetailsList extends ArrayAdapter<Modal_B2BGoatGradeDetails> {
    Context mContext;
    ArrayList<Modal_B2BGoatGradeDetails> goatgradelist;
    ChangeGoatGradeDetails changeGoatGradeDetails;
    String calledFrom = "";


    public Adapter_GoatGradeDetailsList(Context mContext, ArrayList<Modal_B2BGoatGradeDetails> goatgradelistt, ChangeGoatGradeDetails changeGoatGradeDetailss, String calledFrom) {
        super(mContext, R.layout.adapter_batch_item_list, goatgradelistt);
        this.mContext=mContext;
        this.goatgradelist =goatgradelistt;
        this.changeGoatGradeDetails = changeGoatGradeDetailss;
        this.calledFrom = calledFrom;
    }



    public Adapter_GoatGradeDetailsList(@NonNull Context context, int resource) {
        super(context, resource);
    }


    @Nullable
    @Override
    public Modal_B2BGoatGradeDetails getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable Modal_B2BGoatGradeDetails item) {
        return super.getPosition(item);
    }

    public View getView(final int pos, View view, ViewGroup v) {
        @SuppressLint("ViewHolder")  View listViewItem = null;

        try {
            BaseActivity.baseActivity.getDeviceName();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            if (BaseActivity.isDeviceIsMobilePhone) {
                // Inflate the layout for this fragment
                listViewItem =  LayoutInflater.from(mContext).inflate(R.layout.adapter_batch_item_list, (ViewGroup) view, false);
            } else {

                // Inflate the layout for this fragment
                listViewItem =  LayoutInflater.from(mContext).inflate(R.layout.pos_adapter_batch_item_list, (ViewGroup) view, false);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            listViewItem =  LayoutInflater.from(mContext).inflate(R.layout.adapter_batch_item_list, (ViewGroup) view, false);

        }



        final TextView gradename_textview = listViewItem.findViewById(R.id.gradename_textview);
        final TextView description_textview = listViewItem.findViewById(R.id.description_textview);
        final TextView gradeprice_textview = listViewItem.findViewById(R.id.gradeprice_textview);
        final Button editGrade_Button = listViewItem.findViewById(R.id.editGrade_Button);
        final CardView goatgradedetailsCardView = listViewItem.findViewById(R.id.goatgradedetailsCardView);
        final CardView batchDetails_CardView = listViewItem.findViewById(R.id.batchDetails_CardView);

        batchDetails_CardView.setVisibility(View.GONE);
        goatgradedetailsCardView.setVisibility(View.VISIBLE);

        Modal_B2BGoatGradeDetails modal_b2BGoatGradeDetails = goatgradelist.get(pos);

        gradename_textview.setText(modal_b2BGoatGradeDetails.getName());
        description_textview.setText(modal_b2BGoatGradeDetails.getDescription());
        gradeprice_textview.setText("  Rs.  "+modal_b2BGoatGradeDetails.getPrice());

        editGrade_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeGoatGradeDetails.updatedkeyString = goatgradelist.get(pos).getKey();
                Modal_B2BGoatGradeDetails.setKey_static(goatgradelist.get(pos).getKey());
                Modal_B2BGoatGradeDetails.setName_static(goatgradelist.get(pos).getName());
                Modal_B2BGoatGradeDetails.setDescription_static(goatgradelist.get(pos).getDescription());
                Modal_B2BGoatGradeDetails.setPrice_static(goatgradelist.get(pos).getPrice());
                Modal_B2BGoatGradeDetails.setDeliverycenterkey_static(goatgradelist.get(pos).getDeliverycenterkey());
                Modal_B2BGoatGradeDetails.setDeliverycentername_static(goatgradelist.get(pos).getDeliverycentername());
                changeGoatGradeDetails. openAddNewGradeDialog(false);

            }
        });


        return listViewItem;
    }
}
