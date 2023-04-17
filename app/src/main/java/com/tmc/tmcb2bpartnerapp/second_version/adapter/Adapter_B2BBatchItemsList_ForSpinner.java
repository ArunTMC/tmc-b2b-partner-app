package com.tmc.tmcb2bpartnerapp.second_version.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.second_version.activity.ConsolidatedSalesReport;

import java.util.ArrayList;

public class Adapter_B2BBatchItemsList_ForSpinner extends ArrayAdapter<Modal_B2BBatchDetails> {
    Context mContext ;
    ArrayList<Modal_B2BBatchDetails> batchItemArrayList = new ArrayList<>();
    ConsolidatedSalesReport consolidatedSalesReport;

    public Adapter_B2BBatchItemsList_ForSpinner(Context context,ArrayList<Modal_B2BBatchDetails> list, ConsolidatedSalesReport cconsolidatedSalesReport)
    {
        super(context, 0, list);
        this.batchItemArrayList  = list;
        this.consolidatedSalesReport = cconsolidatedSalesReport;
        this.mContext = context;


    }

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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.batch_list_spinner_item, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.batchNo_textview);
        Modal_B2BBatchDetails currentItem = getItem(position);

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            textViewName.setText(currentItem.getBatchno());
        }
        return convertView;
    }
}
