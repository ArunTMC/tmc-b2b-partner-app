package com.tmc.tmcb2bpartnerapp.adapter;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.BillingScreen;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.utils.Constants;

import java.util.HashMap;
import java.util.Objects;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

public class Adapter_BillingScreen_CartRecyclerList extends  RecyclerView.Adapter<Adapter_BillingScreen_CartRecyclerList.ViewHolder>{

      HashMap<String,Modal_GoatEarTagDetails> earTagDetailsHashMap = new HashMap<>();

    BillingScreen billingScreen;
    Context context;



    @NonNull
    @Override
    public Adapter_BillingScreen_CartRecyclerList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_billing_screen_itemlist, parent, false);

        return new Adapter_BillingScreen_CartRecyclerList.ViewHolder(view);

    }



    public Adapter_BillingScreen_CartRecyclerList(Context mContext, HashMap<String, Modal_GoatEarTagDetails> earTagDetailsHashMapp,  BillingScreen billingScreenn) {
        this.earTagDetailsHashMap = earTagDetailsHashMapp;
        this.billingScreen = billingScreenn;
        this.context = mContext;

    }




    @Override
    public void onBindViewHolder(@NonNull Adapter_BillingScreen_CartRecyclerList.ViewHolder holder, int position) {
        holder.itemCount_textview.setText(String.valueOf(position + 1));



        if (position == (earTagDetailsHashMap.size() - 1)) {
            holder.add_new_details_button.setVisibility(View.VISIBLE);
            holder.barcode_editText.setFocusable(true);
            holder.barcode_editText.requestFocus();

        } else {
            holder.add_new_details_button.setVisibility(View.GONE);

        }
      /*  Modal_GoatEarTagDetails modal_goatEarTagDetails = BillingScreen.earTagDetailsHashMap.get(BillingScreen.earTagDetailsArrayList_String.get(position));


        if(modal_goatEarTagDetails.getBarcodeno().toString().equals("")){
            holder.barcode_editText.setVisibility(View.VISIBLE);
            holder.barcodeNo_textview.setVisibility(View.GONE);
            holder.scan_layout.setVisibility(View.VISIBLE);
            holder.barcodeNo_textview.setText(String.valueOf(modal_goatEarTagDetails.getBarcodeno()));
            holder.barcode_editText.setText(String.valueOf(modal_goatEarTagDetails.getBarcodeno()));
            holder.newWeight_editText.setBackground(getDrawable(context,R.drawable.grey_backgroundwith_black_border));

            holder.newWeight_editText.setText(String.valueOf(modal_goatEarTagDetails.getNewWeight_forBillingScreen()));
            holder.currentWeight_textview.setText(String.valueOf(modal_goatEarTagDetails.getCurrentweightingrams()) +" Kg");
            holder.gender_textview.setText(String.valueOf(modal_goatEarTagDetails.getGender()));
            holder.breed_textview.setText(String.valueOf(modal_goatEarTagDetails.getBreedtype()));
            holder.itemCount_textview.setText(String.valueOf(1 + position));


        }
        else {

            holder.barcode_editText.setVisibility(View.GONE);
            holder.barcodeNo_textview.setVisibility(View.VISIBLE);
            holder.scan_layout.setVisibility(View.INVISIBLE);
            holder.barcodeNo_textview.setText(String.valueOf(modal_goatEarTagDetails.getBarcodeno()));
            holder.barcode_editText.setText(String.valueOf(modal_goatEarTagDetails.getBarcodeno()));

            holder.newWeight_editText.setText(String.valueOf(modal_goatEarTagDetails.getNewWeight_forBillingScreen()));
            holder.currentWeight_textview.setText(String.valueOf(modal_goatEarTagDetails.getCurrentweightingrams()));
            holder.gender_textview.setText(String.valueOf(modal_goatEarTagDetails.getGender()));
            holder.breed_textview.setText(String.valueOf(modal_goatEarTagDetails.getBreedtype()));
            holder.itemCount_textview.setText(String.valueOf(1 + position));



        }

       */
    }

    @Override
    public int getItemCount() {
        return earTagDetailsHashMap.size();
    }








    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemCount_textview;
        LinearLayout scan_layout ,delete_layout;
        EditText barcode_editText,newWeight_editText;
        TextView currentWeight_textview,gender_textview,breed_textview,barcodeNo_textview;
        Button add_new_details_button;

        public ViewHolder(View view) {
            super(view);

             scan_layout = view.findViewById(R.id.scan_layout);
             delete_layout = view.findViewById(R.id.delete_layout);
             barcode_editText = view.findViewById(R.id.barcode_editText);
             newWeight_editText = view.findViewById(R.id.newWeight_editText);
             currentWeight_textview = view.findViewById(R.id.currentWeight_textview);
             itemCount_textview = view.findViewById(R.id.itemCount_textview);
             gender_textview = view.findViewById(R.id.gender_textview);
             breed_textview = view.findViewById(R.id.breed_textview);
             barcodeNo_textview = view.findViewById(R.id.barcodeNo_textview);
            add_new_details_button  = view.findViewById(R.id.add_new_details_button);
            newWeight_editText.setBackground(getDrawable(context,R.drawable.grey_backgroundwith_black_border));

            scan_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    billingScreen.Initialize_and_StartBarcodeScanner(context.getString(R.string.scannerCalled_to_FetchData), BillingScreen.earTagDetailsArrayList_String.get(getPosition()) , false );
                }
            });
            delete_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                /*
                    if(BillingScreen.earTagDetailsHashMap.size() == 1){
                        Modal_GoatEarTagDetails modal_goatEarTagDetails  = new Modal_GoatEarTagDetails();

                        if(!BillingScreen.earTagDetailsHashMap.containsKey("")){
                            if(!BillingScreen.earTagDetailsArrayList_String.contains("")) {

                                BillingScreen.earTagDetailsArrayList_String.add("");
                            }
                            BillingScreen.earTagDetailsHashMap.put("", modal_goatEarTagDetails);
                            BillingScreen.earTagDetailsHashMap.remove(BillingScreen.earTagDetailsArrayList_String.get(getPosition()));
                            BillingScreen.earTagDetailsArrayList_String.remove(getPosition());
                            notifyDataSetChanged();
                            billingScreen.calculateTotalweight_Quantity_Price();
                        }
                        else{
                            Toast.makeText(context, "Can't Delete the last empty item", Toast.LENGTH_SHORT).show();

                        }

                    }
                    else{
                        BillingScreen.earTagDetailsHashMap.remove(BillingScreen.earTagDetailsArrayList_String.get(getPosition()));
                        BillingScreen.earTagDetailsArrayList_String.remove(getPosition());
                        notifyDataSetChanged();
                        billingScreen.calculateTotalweight_Quantity_Price();
                    }


                 */

                }
            });


            add_new_details_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Modal_GoatEarTagDetails modal_goatEarTagDetails  = new Modal_GoatEarTagDetails();

                    /*
                    if(!BillingScreen.earTagDetailsHashMap.containsKey("")){
                        boolean isArrayContainsZeroForNewWeightInGrams = billingScreen.doesArrayContainsZeroForNewWeightInGrams();
                        if(!isArrayContainsZeroForNewWeightInGrams) {
                            if (!BillingScreen.earTagDetailsArrayList_String.contains("")) {

                                BillingScreen.earTagDetailsArrayList_String.add("");
                            }
                            BillingScreen.earTagDetailsHashMap.put("", modal_goatEarTagDetails);
                            billingScreen.calculateTotalweight_Quantity_Price();
                            notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(context, "New Weight cannot be zero / empty. So Please Add weight click enter for every item", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else{
                        Toast.makeText(context, "Please Fill the Data in last item", Toast.LENGTH_SHORT).show();

                    }

                     */


                }
            });


            newWeight_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {


                    try {

                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                            //do what you want on the press of 'done'

                            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            Objects.requireNonNull(imm).hideSoftInputFromWindow(newWeight_editText.getWindowToken(), 0);

                            String weightFromNewEditText = "";

                            weightFromNewEditText = newWeight_editText.getText().toString();
                            weightFromNewEditText = weightFromNewEditText .replaceAll("[^\\d.]", "");
                            if(weightFromNewEditText.equals("") || weightFromNewEditText.equals(null)){
                                weightFromNewEditText = "0";
                            }
                            newWeight_editText.setBackground(getDrawable(context,R.drawable.grey_edittext_withoutborder));

                           // BillingScreen.earTagDetailsHashMap.get(BillingScreen.earTagDetailsArrayList_String.get(getPosition())).setNewWeight_forBillingScreen(weightFromNewEditText);
                          //  BillingScreen.adapter_billingScreen_cartList.notifyDataSetChanged();
                         //   billingScreen.calculateTotalweight_Quantity_Price();


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });

            barcode_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {


                    try {

                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                            //do what you want on the press of 'done'

                            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            Objects.requireNonNull(imm).hideSoftInputFromWindow(barcode_editText.getWindowToken(), 0);

                            String BarcodeFromNewEditText = "";

                            BarcodeFromNewEditText = barcode_editText.getText().toString();
                            BarcodeFromNewEditText = BarcodeFromNewEditText .replaceAll("[^a-zA-Z0-9]", "");
                            if(BarcodeFromNewEditText.equals("") || BarcodeFromNewEditText.equals(null)){
                                BarcodeFromNewEditText = "0";
                            }

                            billingScreen.scannedBarcode = BarcodeFromNewEditText;
                            billingScreen. Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod, BillingScreen.earTagDetailsArrayList_String.get(getPosition()),false);


                            //BillingScreen.earTagDetailsHashMap.get(BillingScreen.earTagDetailsArrayList_String.get(getPosition())).setNewWeight_forBillingScreen(weightFromNewEditText);
                            //BillingScreen.adapter_billingScreen_cartList.notifyDataSetChanged();
                            //billingScreen.calculateTotalweight_Quantity_Price();


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });







        }
    }




}
