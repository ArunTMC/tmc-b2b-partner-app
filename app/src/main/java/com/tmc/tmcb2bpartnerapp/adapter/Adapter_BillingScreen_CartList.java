package com.tmc.tmcb2bpartnerapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.BillingScreen;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;

import java.util.HashMap;

public class Adapter_BillingScreen_CartList extends ArrayAdapter<Modal_GoatEarTagDetails> {

    Context mContext;
     HashMap<String,Modal_GoatEarTagDetails> earTagDetailsHashMap = new HashMap<>();
    BillingScreen billingScreen;
    String calledFrom = "";

    public Adapter_BillingScreen_CartList(@NonNull Context context, int resource) {
        super(context, resource);
    }


  /*  public Adapter_BillingScreen_CartList(Context mContext, HashMap<String,Modal_GoatEarTagDetails>  earTagDetailsHashMapp, BillingScreen billingScreen1, String calledFrom) {
        super(mContext, R.layout.adapter_billing_screen_itemlist, earTagDetailsHashMapp);
        this.mContext=mContext;
        this.billingScreen=billingScreen1;
        this.earTagDetailsHashMap = earTagDetailsHashMapp;
        this.calledFrom = calledFrom;
    }

   */





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
        @SuppressLint("ViewHolder") final View listViewItem = LayoutInflater.from(mContext).inflate(R.layout.adapter_billing_screen_itemlist, (ViewGroup) view, false);
        LinearLayout scan_layout = listViewItem.findViewById(R.id.scan_layout);
        LinearLayout delete_layout = listViewItem.findViewById(R.id.delete_layout);
        EditText barcode_editText = listViewItem.findViewById(R.id.barcode_editText);
        EditText newWeight_editText = listViewItem.findViewById(R.id.newWeight_editText);
        TextView currentWeight_textview = listViewItem.findViewById(R.id.currentWeight_textview);
        TextView itemCount_textview = listViewItem.findViewById(R.id.itemCount_textview);
        TextView gender_textview = listViewItem.findViewById(R.id.gender_textview);
        TextView breed_textview = listViewItem.findViewById(R.id.breed_textview);
        TextView barcodeNo_textview = listViewItem.findViewById(R.id.barcodeNo_textview);



        Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();
        if(modal_goatEarTagDetails.getBarcodeno().equals("")){
            barcodeNo_textview.setVisibility(View.GONE);
            barcode_editText.setVisibility(View.VISIBLE);
        }
        else{
            barcodeNo_textview.setVisibility(View.VISIBLE);
            barcode_editText.setVisibility(View.GONE);
        }

        newWeight_editText.setText(String.valueOf(modal_goatEarTagDetails.getNewWeight_forBillingScreen()));
        currentWeight_textview.setText(String.valueOf(modal_goatEarTagDetails.getCurrentweightingrams()));
        gender_textview.setText(String.valueOf(modal_goatEarTagDetails.getGender()));
        breed_textview.setText(String.valueOf(modal_goatEarTagDetails.getBreedtype()));
        itemCount_textview.setText(String.valueOf( 1 + pos ));

        scan_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // billingScreen. Initialize_and_StartBarcodeScanner(mContext.getString(R.string.scannerCalled_Not_to_FetchData));
            }
        });



        return  listViewItem;
    }


}
