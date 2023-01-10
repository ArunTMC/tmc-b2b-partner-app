package com.tmc.tmcb2bpartnerapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.ChangeGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;

import java.util.ArrayList;

public class Adapter_goatGradeDetailsSpinnerItems extends ArrayAdapter<Modal_B2BGoatGradeDetails> {

    Context mContext;
    ArrayList<Modal_B2BGoatGradeDetails> goatgradelist;

    String calledFrom = "";


    public Adapter_goatGradeDetailsSpinnerItems(Context mContext, ArrayList<Modal_B2BGoatGradeDetails> goatgradelistt, String calledFrom) {
        super(mContext, R.layout.adapter_goat_gradedetails_spinner_item, goatgradelistt);

        this.calledFrom = calledFrom;
        this.goatgradelist = goatgradelistt;
        this.mContext = mContext;
    }

/*
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
        @SuppressLint("ViewHolder") final View listViewItem = LayoutInflater.from(mContext).inflate(R.layout.adapter_goat_gradedetails_spinner_item, (ViewGroup) view, false);

        final TextView gradename_textview = listViewItem.findViewById(R.id.gradeName_textview);
        final TextView description_textview = listViewItem.findViewById(R.id.gradeDescription_textview);
        final TextView gradeprice_textview = listViewItem.findViewById(R.id.gradePrice_textview);

        Modal_B2BGoatGradeDetails modal_b2BGoatGradeDetails = goatgradelist.get(pos);

        gradename_textview.setText(String.valueOf(modal_b2BGoatGradeDetails.getName()));
        description_textview.setText(String.valueOf(modal_b2BGoatGradeDetails.getDescription()));
        gradeprice_textview.setText(" Rs. "+String.valueOf(modal_b2BGoatGradeDetails.getPrice()));


        return listViewItem;
    }

 */




    @NonNull
    @Override
    public View getView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView,
                          ViewGroup parent)
    {
        // It is used to set our custom view.
        if (convertView == null) {



            try {
                BaseActivity.baseActivity.getDeviceName();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            try {
                if (BaseActivity.isDeviceIsMobilePhone) {
                    // Inflate the layout for this fragment
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_goat_gradedetails_spinner_item, parent, false);
                } else {

                    // Inflate the layout for this fragment
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.pos_adapter_goat_gradedetails_spinner_item, parent, false);
                }
            }
            catch (Exception e){
                e.printStackTrace();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_goat_gradedetails_spinner_item, parent, false);

            }




        }

        TextView textViewName = convertView.findViewById(R.id.gradeName_textview);
     //   TextView gradeDescription_textview = convertView.findViewById(R.id.gradeDescription_textview);
       // TextView gradeprice_textview = convertView.findViewById(R.id.gradeprice_textview);


        Modal_B2BGoatGradeDetails currentItem = getItem(position);

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            textViewName.setText(currentItem.getName().toUpperCase() +" ( Rs. "+currentItem.getPrice() +"  )\nDescription : " + currentItem
            .getDescription());
      //      gradeDescription_textview.setText(currentItem.getDescription());
        //    gradeprice_textview.setText(currentItem.getPrice());

        }
        return convertView;
    }


}

