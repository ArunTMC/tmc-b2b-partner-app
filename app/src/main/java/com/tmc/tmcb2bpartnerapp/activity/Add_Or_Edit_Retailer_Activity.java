package com.tmc.tmcb2bpartnerapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.adapter.Adapter_RetailersList;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BRetailerDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Add_Or_Edit_Retailer_Activity extends AppCompatActivity {


    static LinearLayout back_IconLayout, searchmobileno_layout , loadingPanel , loadingpanelmask , search_IconLayout,close_IconLayout;
    TextView mobileNo_textView ,instruction_Textview;
    EditText retailermobileno_search_editText;
    ListView retailers_listview;
    public static Dialog show_retailerItemDetails_Dialog = null;
    static LinearLayout addNewRetailer_Button ;


    ///interface
    B2BRetailerDetailsInterface callback_retailerDetailsInterface = null;





    //boolean
    boolean  isRetailerDetailsServiceCalled = false ,isSearchButtonClicked =false;



    //ArrayList
    public static ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList = new ArrayList<>();
    public static ArrayList<Modal_B2BRetailerDetails> sorted_retailerDetailsArrayList = new ArrayList<>();





    //String

    String retailers_name = "";
    String retailers_mobileno = "" , retailerKey ="";
    String retailers_address = "" ,gstin ="",deliveryCenterKey ="",deliveryCenterName ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            BaseActivity.baseActivity.getDeviceName();
        }
        catch (Exception e){
            e.printStackTrace();
        }


        try{
            if (BaseActivity.isDeviceIsMobilePhone) {
                setContentView(R.layout.activity_add__or__edit__retailer);


            } else {

                setContentView(R.layout.activity_add__or__edit__retailer);
            }

        }
        catch (Exception e){
            setContentView(R.layout.activity_add__or__edit__retailer);
            e.printStackTrace();
        }




        retailers_listview = findViewById(R.id.retailers_listview);
        loadingPanel = findViewById(R.id.loadingPanel);
        loadingpanelmask = findViewById(R.id.loadingpanelmask);
        back_IconLayout = findViewById(R.id.back_IconLayout);
        search_IconLayout = findViewById(R.id.search_IconLayout);
        close_IconLayout = findViewById(R.id.close_IconLayout);
        mobileNo_textView = findViewById(R.id.mobileNo_textView);
        retailermobileno_search_editText = findViewById(R.id.retailermobileno_search_editText);
        addNewRetailer_Button = findViewById(R.id.addNewRetailer_Button);
        searchmobileno_layout = findViewById(R.id.searchmobileno_layout);
        instruction_Textview = findViewById(R.id.instruction_Textview);

        showProgressBar(true);

        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);

        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");


        if(DatabaseArrayList_PojoClass.retailerDetailsArrayList.size() == 0){
            try {
                call_and_init_B2BRetailerDetailsService(Constants.CallGETListMethod);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            retailerDetailsArrayList = DatabaseArrayList_PojoClass.retailerDetailsArrayList;
            setAdapterForRetailerDetails(retailerDetailsArrayList);
        }

        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        addNewRetailer_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRetaierDetailsDialog(new Modal_B2BRetailerDetails() , false);
            }
        });


        search_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int textlength = retailermobileno_search_editText.getText().toString().length();
                isSearchButtonClicked =true;
                showKeyboard(retailermobileno_search_editText);
                showSearchBarEditText();
            }

        });

        mobileNo_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_IconLayout.performClick();
            }

        });

        close_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(retailermobileno_search_editText);
                closeSearchBarEditText();
                retailermobileno_search_editText.setText("");
                isSearchButtonClicked =false;
                setAdapterForRetailerDetails(retailerDetailsArrayList);

            }
        });



        retailermobileno_search_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sorted_retailerDetailsArrayList.clear();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                sorted_retailerDetailsArrayList.clear();
                isSearchButtonClicked =true;
                String mobileUserEntered = (editable.toString());
                mobileUserEntered  = mobileUserEntered.replaceAll("[+]91","");
                if(!mobileUserEntered.equals("")) {

                    for (int i = 0; i < retailerDetailsArrayList.size(); i++) {
                        try{
                            final Modal_B2BRetailerDetails modal_b2BRetailerDetails = retailerDetailsArrayList.get(i);
                            String mobileno = modal_b2BRetailerDetails.getMobileno();
                            mobileno = mobileno.replaceAll("[+]91","");
                            if (mobileno.contains(mobileUserEntered)) {
                                sorted_retailerDetailsArrayList.add(modal_b2BRetailerDetails);
                            }
                            if (i == (retailerDetailsArrayList.size() - 1)) {

                                setAdapterForRetailerDetails(sorted_retailerDetailsArrayList);
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    setAdapterForRetailerDetails(retailerDetailsArrayList);                }
            }
        });






    }






    private void closeSearchBarEditText() {
        search_IconLayout.setVisibility(View.VISIBLE);
        mobileNo_textView.setVisibility(View.VISIBLE);
        retailermobileno_search_editText.setVisibility(View.GONE);
        close_IconLayout.setVisibility(View.GONE);
    }

    private void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
    private void showSearchBarEditText() {
        retailermobileno_search_editText.setVisibility(View.VISIBLE);
        mobileNo_textView.setVisibility(View.GONE);
        close_IconLayout.setVisibility(View.VISIBLE);
        search_IconLayout.setVisibility(View.GONE);
    }
    private void showKeyboard(final EditText editText) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus();
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                editText.setSelection(editText.getText().length());
            }
        },0);
    }



    public void call_and_init_B2BRetailerDetailsService(String CallMethod) {

        if (isRetailerDetailsServiceCalled) {
            //  showProgressBar(false);
            return;
        }
        isRetailerDetailsServiceCalled = true;
        callback_retailerDetailsInterface = new B2BRetailerDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayListt) {
                isRetailerDetailsServiceCalled = false;
                retailerDetailsArrayList = retailerDetailsArrayListt;
                hideKeyboard(retailermobileno_search_editText);
                closeSearchBarEditText();
                retailermobileno_search_editText.setText("");
                isSearchButtonClicked =false;
                setAdapterForRetailerDetails(retailerDetailsArrayList);


            }

            @Override
            public void notifySuccess(String result) {
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 2 " + DateParser.getDate_and_time_newFormat());
                if(result.equals(Constants.item_Already_Added_volley)){
                    AlertDialogClass.showDialog(Add_Or_Edit_Retailer_Activity.this, R.string.retailersAlreadyCreated_Instruction);
                    isRetailerDetailsServiceCalled = false;
                    showProgressBar(false);

                }
                else if(result.equals(Constants.successResult_volley)){
                    if(CallMethod.equals(Constants.CallADDMethod)){
                        retailerDetailsArrayList = DatabaseArrayList_PojoClass.retailerDetailsArrayList;
                        hideKeyboard(retailermobileno_search_editText);
                        closeSearchBarEditText();
                        retailermobileno_search_editText.setText("");
                        isSearchButtonClicked =false;
                        setAdapterForRetailerDetails(retailerDetailsArrayList);


                    }
                    else  if(CallMethod.equals(Constants.CallUPDATEMethod)){
                        for(int i = 0 ;i < retailerDetailsArrayList.size() ; i++){
                            if(retailerKey.equals(retailerDetailsArrayList.get(i).getRetailerkey())){
                                retailerDetailsArrayList.get(i).setAddress(retailers_address);
                                retailerDetailsArrayList.get(i).setMobileno(retailers_mobileno);
                                retailerDetailsArrayList.get(i).setRetailername(retailers_name);
                                retailerDetailsArrayList.get(i).setDeliveryCenterKey(deliveryCenterKey);
                                retailerDetailsArrayList.get(i).setDeliveryCenterName(deliveryCenterName);
                                retailerDetailsArrayList.get(i).setGstin(gstin);
                                DatabaseArrayList_PojoClass.retailerDetailsArrayList = retailerDetailsArrayList;
                                hideKeyboard(retailermobileno_search_editText);
                                closeSearchBarEditText();
                                retailermobileno_search_editText.setText("");
                                isSearchButtonClicked =false;
                                setAdapterForRetailerDetails(retailerDetailsArrayList);
                            }
                        }


                    }
                    isRetailerDetailsServiceCalled = false;
                    hideKeyboard(retailermobileno_search_editText);
                    closeSearchBarEditText();
                    retailermobileno_search_editText.setText("");
                    isSearchButtonClicked =false;
                    setAdapterForRetailerDetails(retailerDetailsArrayList);

                }
                else{
                    isRetailerDetailsServiceCalled = false;
                    showProgressBar(false);



                }

                isRetailerDetailsServiceCalled = false;
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isRetailerDetailsServiceCalled = false;
                showProgressBar(false);
            }

            @Override
            public void notifyProcessingError(Exception error) {
                isRetailerDetailsServiceCalled = false;
                showProgressBar(false);
            }


        };
        if(CallMethod.equals(Constants.CallADDMethod)){
            Modal_B2BRetailerDetails.setAddress_static(retailers_address);
            Modal_B2BRetailerDetails.setMobileno_static(retailers_mobileno);
            Modal_B2BRetailerDetails.setRetailername_static(retailers_name);
            Modal_B2BRetailerDetails.setDeliveryCenterKey_static(deliveryCenterKey);
            Modal_B2BRetailerDetails.setDeliveryCenterName_static(deliveryCenterName);
            Modal_B2BRetailerDetails.setGstin_static(gstin);

            String getApiToCall = API_Manager.addRetailerDetailsList ;

            B2BRetailerDetails asyncTask = new B2BRetailerDetails(callback_retailerDetailsInterface,  getApiToCall, Constants.CallADDMethod);
            asyncTask.execute();
        }
        else  if(CallMethod.equals(Constants.CallGETListMethod)){
            String getApiToCall = API_Manager.getretailerDetailsListWithDeliveryCentreKey+deliveryCenterKey ;

            B2BRetailerDetails asyncTask = new B2BRetailerDetails(callback_retailerDetailsInterface,  getApiToCall, Constants.CallGETListMethod);
            asyncTask.execute();
        }
        else  if(CallMethod.equals(Constants.CallUPDATEMethod)){
            Modal_B2BRetailerDetails.setAddress_static(retailers_address);
            Modal_B2BRetailerDetails.setMobileno_static(retailers_mobileno);
            Modal_B2BRetailerDetails.setRetailername_static(retailers_name);
            Modal_B2BRetailerDetails.setDeliveryCenterKey_static(deliveryCenterKey);
            Modal_B2BRetailerDetails.setDeliveryCenterName_static(deliveryCenterName);
            Modal_B2BRetailerDetails.setGstin_static(gstin);
            Modal_B2BRetailerDetails.setRetailerkey_static(retailerKey);
            String getApiToCall = API_Manager.updateRetailerDetailsList ;

            B2BRetailerDetails asyncTask = new B2BRetailerDetails(callback_retailerDetailsInterface,  getApiToCall, Constants.CallUPDATEMethod);
            asyncTask.execute();
        }





    }

    private void setAdapterForRetailerDetails(ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList) {




        if(retailerDetailsArrayList.size()>0){
            retailerDetailsArrayList = sortThisArrayUsingRetailer_mobileNo(retailerDetailsArrayList);

            Adapter_RetailersList adapter_retailersList = new Adapter_RetailersList(getApplicationContext() , retailerDetailsArrayList,Add_Or_Edit_Retailer_Activity.this);
            retailers_listview.setAdapter(adapter_retailersList);
            retailers_listview.setVisibility(View.VISIBLE);
            instruction_Textview.setVisibility(View.GONE);
            addNewRetailer_Button.setVisibility(View.VISIBLE);
        }
        else{
            retailers_listview.setVisibility(View.GONE);
            instruction_Textview.setVisibility(View.VISIBLE);
            addNewRetailer_Button.setVisibility(View.VISIBLE);
        }


        showProgressBar(false);
    }

    private ArrayList<Modal_B2BRetailerDetails> sortThisArrayUsingRetailer_mobileNo(ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList) {
        final Pattern p = Pattern.compile("^\\d+");

            Comparator<Modal_B2BRetailerDetails> c = new Comparator<Modal_B2BRetailerDetails>() {
                @Override
                public int compare(Modal_B2BRetailerDetails object1, Modal_B2BRetailerDetails object2) {
                    Matcher m = p.matcher(object1.getMobileno());
                    Integer number1 = null;
                    if (!m.find()) {
                        Matcher m1 = p.matcher(object2.getMobileno());
                        if (m1.find()) {
                            return object2.getMobileno().compareTo(object1.getMobileno());
                        } else {
                            return object1.getMobileno().compareTo(object2.getMobileno());
                        }
                    } else {
                        Integer number2 = null;
                        number1 = Integer.parseInt(m.group());
                        m = p.matcher(object2.getMobileno());
                        if (!m.find()) {
                            // return object1.compareTo(object2);
                            Matcher m1 = p.matcher(object1.getMobileno());
                            if (m1.find()) {
                                return object2.getMobileno().compareTo(object1.getMobileno());
                            } else {
                                return object1.getMobileno().compareTo(object2.getMobileno());
                            }
                        } else {
                            number2 = Integer.parseInt(m.group());
                            int comparison = number1.compareTo(number2);
                            if (comparison != 0) {
                                return comparison;
                            } else {
                                return object1.getMobileno().compareTo(object2.getMobileno());
                            }
                        }
                    }
                }
            };

            Collections.sort(retailerDetailsArrayList, c);



        return  retailerDetailsArrayList;

    }


    public static void showProgressBar(boolean show) {

        if(show){
            loadingPanel.setVisibility(View.VISIBLE);
            loadingpanelmask.setVisibility(View.VISIBLE);
            addNewRetailer_Button.setVisibility(View.GONE);
        }
        else{
            loadingPanel.setVisibility(View.GONE);
            loadingpanelmask.setVisibility(View.GONE);
            addNewRetailer_Button.setVisibility(View.VISIBLE);
        }


    }

    public void showRetaierDetailsDialog(Modal_B2BRetailerDetails modal_b2BRetailerDetails, boolean isUpdateExistingRetailer) {
        try {
            show_retailerItemDetails_Dialog = new Dialog(Add_Or_Edit_Retailer_Activity.this, R.style.Theme_Dialog);
            //  show_scan_barcode_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            show_retailerItemDetails_Dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            show_retailerItemDetails_Dialog.setContentView(R.layout.fragment_add_retailer_);
            // show_scan_barcode_dialog.setCancelable(false);
            show_retailerItemDetails_Dialog.setCanceledOnTouchOutside(false);

            LinearLayout back_IconLayout;
            EditText mobileNo_textView, address_edittext, retailerName_textView, gstin_editText;
            Button save_button;


            retailerName_textView = show_retailerItemDetails_Dialog.findViewById(R.id.retailerName_textView);
            address_edittext = show_retailerItemDetails_Dialog.findViewById(R.id.address_edittext);
            mobileNo_textView = show_retailerItemDetails_Dialog.findViewById(R.id.mobileNo_textView);
            gstin_editText = show_retailerItemDetails_Dialog.findViewById(R.id.gstin_editText);
            save_button = show_retailerItemDetails_Dialog.findViewById(R.id.save_button);
            back_IconLayout = show_retailerItemDetails_Dialog.findViewById(R.id.back_IconLayout);

            mobileNo_textView.setText(String.valueOf(modal_b2BRetailerDetails.getMobileno()).replaceAll("[+]91",""));
            retailerName_textView.setText(String.valueOf(modal_b2BRetailerDetails.getRetailername()));
            address_edittext.setText(String.valueOf(modal_b2BRetailerDetails.getAddress()));
            gstin_editText.setText(String.valueOf(modal_b2BRetailerDetails.getGstin()));
            retailerKey = String.valueOf(modal_b2BRetailerDetails.getRetailerkey());

            show_retailerItemDetails_Dialog.show();

            if (isUpdateExistingRetailer) {
                save_button.setText(String.valueOf(" Update "));
            }
            else{
                save_button.setText(String.valueOf(" Add "));

            }


                save_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    retailers_mobileno = mobileNo_textView.getText().toString().replaceAll("[+]91","");
                    retailers_address = address_edittext.getText().toString();
                    retailers_name = retailerName_textView.getText().toString();

                    gstin = gstin_editText.getText().toString();

                    if (retailers_mobileno.length() == 10) {
                        retailers_mobileno = "+91" + retailers_mobileno;
                        if (retailers_address.length() > 2) {
                            if (retailers_name.length() > 0) {
                                showProgressBar(true);
                                try{
                                    show_retailerItemDetails_Dialog.cancel();
                                    show_retailerItemDetails_Dialog.dismiss();
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                                if (isUpdateExistingRetailer) {
                                    call_and_init_B2BRetailerDetailsService(Constants.CallUPDATEMethod);
                                } else {
                                    call_and_init_B2BRetailerDetailsService(Constants.CallADDMethod);
                                }

                            } else {
                                AlertDialogClass.showDialog(Add_Or_Edit_Retailer_Activity.this, R.string.retailers_name_cant_be_empty);

                            }
                        } else {
                            AlertDialogClass.showDialog(Add_Or_Edit_Retailer_Activity.this, R.string.retailers_Address_cant_be_empty);

                        }
                    } else {
                        AlertDialogClass.showDialog(Add_Or_Edit_Retailer_Activity.this, R.string.retailers_mobileno_should_be_10digits);

                    }


                }
            });
            back_IconLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    show_retailerItemDetails_Dialog.dismiss();


                }
            });


        }
        catch(Exception e){
        e.printStackTrace();
        }
    }

}