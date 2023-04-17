package com.tmc.tmcb2bpartnerapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.adapter.Adapter_GoatGradeDetailsList;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BGoatGradeDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChangeGoatGradeDetails extends AppCompatActivity {
ListView grade_details_listView;
Button addNewGrade_Button;
static LinearLayout loadingPanel , loadingpanelmask;
B2BGoatGradeDetailsInterface callback_goatGradeDetailsInterface = null;
boolean isGoatGradeDetailsServiceCalled = false;


Adapter_GoatGradeDetailsList adapter_goatGradeDetailsList =  null ;
DecimalFormat twoDecimalConverter = new DecimalFormat(Constants.twoDecimalPattern);
String deliverycentrekeyFromSharedPref ="", deliverycentrenameFromSharedPref="";
String nameString ="", descriptionString = "", deliveryCentreKeyString ="" ,deliveryCentreNameString =""  ,keyString ="";
String updatednameString ="";
    String updateddescriptionString = "";
    String updateddeliveryCentreKeyString ="";
    String updateddeliveryCentreNameString ="";

    public String updatedkeyString ="";
    double priceDouble = 0 , updatedpriceDouble =0 ;
    ArrayList<Modal_B2BGoatGradeDetails> goatGradeDetailsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        try {
            BaseActivity.baseActivity.getDeviceName();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            if (BaseActivity.isDeviceIsMobilePhone) {
                // Inflate the layout for this fragment
                setContentView(R.layout.activity_change_goat_grade_details);
            } else {

                // Inflate the layout for this fragment
                setContentView(R.layout.pos_activity_change_goat_grade_details);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            setContentView(R.layout.activity_change_goat_grade_details);

        }


        addNewGrade_Button = findViewById(R.id.addNewGrade_Button);
        grade_details_listView = findViewById(R.id.grade_details_listView);
        loadingpanelmask =  findViewById(R.id.loadingpanelmask);
        loadingPanel =  findViewById(R.id.loadingPanel);

        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData",MODE_PRIVATE);
        deliverycentrenameFromSharedPref = sh1.getString("DeliveryCenterName","");
        deliverycentrekeyFromSharedPref = sh1.getString("DeliveryCenterKey","");


        adapter_goatGradeDetailsList =  new Adapter_GoatGradeDetailsList(ChangeGoatGradeDetails.this , 0 ) ;

        Call_and_Initialize_GoatGradeDetails(Constants.CallGETListMethod);

        addNewGrade_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddNewGradeDialog(true);
            }
        });



    }

    public void openAddNewGradeDialog(boolean isAddNewGrade) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                   Dialog dialog = new Dialog(ChangeGoatGradeDetails.this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_TranslucentDecor);

                   // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_add_update_goatgradedetails);

                    EditText gradeName_editText = dialog.findViewById(R.id.gradeName_editText);
                    EditText gradePrice_edittext = dialog.findViewById(R.id.gradePrice_edittext);
                    EditText description_edittext = dialog.findViewById(R.id.description_edittext);



                    Button  save_button =  dialog.findViewById(R.id.save_button);
                    LinearLayout back_IconLayout = dialog.findViewById(R.id.back_IconLayout);


                    if(isAddNewGrade){
                        gradeName_editText.setText("");
                        gradePrice_edittext.setText("");
                        description_edittext.setText("");

                    }
                    else{
                        gradeName_editText.setText(Modal_B2BGoatGradeDetails.name_static);
                        gradePrice_edittext.setText("  "+Modal_B2BGoatGradeDetails.price_static);
                        description_edittext.setText(Modal_B2BGoatGradeDetails.description_static);

                    }


                    back_IconLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });


                    save_button.setOnClickListener(new View.OnClickListener() {
                        @Override

                        public void onClick(View view) {
                            showProgressBar(true);

                            updatednameString = String.valueOf(gradeName_editText.getText());
                            String price_string = "";
                            price_string = String.valueOf(gradePrice_edittext.getText());
                            price_string = price_string.replaceAll("[^\\d.]", "");
                            if(price_string.equals("") || price_string.equals("0")){
                                updatedpriceDouble = 0;
                            }
                            updatedpriceDouble = Double.parseDouble(price_string);
                            updateddescriptionString = String.valueOf(description_edittext.getText());
                            if(isAddNewGrade){
                                Call_and_Initialize_GoatGradeDetails(Constants.CallADDMethod);
                            }
                            else{
                                Call_and_Initialize_GoatGradeDetails(Constants.CallUPDATEMethod);
                            }
                            dialog.cancel();
                        }
                    });



                    showProgressBar(false);
                    dialog.show();


                } catch (WindowManager.BadTokenException e) {
                    showProgressBar(false);

                    e.printStackTrace();
                }
            }
        });

    }

    private void Call_and_Initialize_GoatGradeDetails(String ApiMethod) {
        showProgressBar(true);
        if (isGoatGradeDetailsServiceCalled) {
             showProgressBar(false);
            return;
        }
        isGoatGradeDetailsServiceCalled = true;
        callback_goatGradeDetailsInterface = new B2BGoatGradeDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BGoatGradeDetails> arrayListt) {
                isGoatGradeDetailsServiceCalled = false;
                goatGradeDetailsArrayList = arrayListt;
                setgradeListAdapter();
            }

            @Override
            public void notifySuccess(String key) {
                if(ApiMethod.equals(Constants.CallADDMethod)) {
                    Modal_B2BGoatGradeDetails modal_b2BGoatGradeDetails = new Modal_B2BGoatGradeDetails();
                    modal_b2BGoatGradeDetails.setDeliverycenterkey(Modal_B2BGoatGradeDetails.getDeliverycenterkey_static());
                    modal_b2BGoatGradeDetails.setDeliverycentername(Modal_B2BGoatGradeDetails.getDeliverycentername_static());
                    modal_b2BGoatGradeDetails.setDescription(Modal_B2BGoatGradeDetails.getDescription_static());
                    modal_b2BGoatGradeDetails.setName(Modal_B2BGoatGradeDetails.getName_static());
                    modal_b2BGoatGradeDetails.setPrice(String.valueOf(Modal_B2BGoatGradeDetails.getPrice_static()));
                    modal_b2BGoatGradeDetails.setKey(Modal_B2BGoatGradeDetails.getKey_static());
                    goatGradeDetailsArrayList.add(modal_b2BGoatGradeDetails);


                    setgradeListAdapter();


                }
                else  if(ApiMethod.equals(Constants.CallUPDATEMethod)) {
                    for(int iterator = 0 ; iterator < goatGradeDetailsArrayList.size(); iterator++){
                        Modal_B2BGoatGradeDetails modal_b2BGoatGradeDetails = goatGradeDetailsArrayList.get(iterator);
                        if(modal_b2BGoatGradeDetails.getKey().equals(updatedkeyString)){
                            modal_b2BGoatGradeDetails.setPrice(String.valueOf(updatedpriceDouble));
                            modal_b2BGoatGradeDetails.setName(updatednameString);
                            modal_b2BGoatGradeDetails.setDescription(updateddescriptionString);



                        }
                    }
                }

                try{

                    adapter_goatGradeDetailsList.notifyDataSetChanged();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                    isGoatGradeDetailsServiceCalled = false;
                showProgressBar(false);

                //((BillingScreen)getActivity()).closeFragment();
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isGoatGradeDetailsServiceCalled = false;
                //    Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 3 " + DateParser.getDate_and_time_newFormat());
                showProgressBar(false);
            }

            @Override
            public void notifyProcessingError(Exception error) {
                isGoatGradeDetailsServiceCalled = false;
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());
                showProgressBar(false);
            }


        };


        if(ApiMethod.equals(Constants.CallADDMethod)){

            Modal_B2BGoatGradeDetails.setName_static(String.valueOf(updatednameString));
            Modal_B2BGoatGradeDetails.setDescription_static(String.valueOf(updateddescriptionString));
            Modal_B2BGoatGradeDetails.setPrice_static(String.valueOf(updatedpriceDouble));
            Modal_B2BGoatGradeDetails.setDeliverycenterkey_static(String.valueOf(deliverycentrekeyFromSharedPref));
            Modal_B2BGoatGradeDetails.setDeliverycentername_static(String.valueOf(deliverycentrenameFromSharedPref));

            String getApiToCall = API_Manager.addgoatGradeDetails ;

            B2BGoatGradeDetails asyncTask = new B2BGoatGradeDetails(callback_goatGradeDetailsInterface,  getApiToCall, Constants.CallADDMethod);
            asyncTask.execute();



        }
        else  if(ApiMethod.equals(Constants.CallDELETEMethod)){
            String getApiToCall = API_Manager.deletegoatGradeDetails+keyString ;

            B2BGoatGradeDetails asyncTask = new B2BGoatGradeDetails(callback_goatGradeDetailsInterface,  getApiToCall, Constants.CallDELETEMethod);
            asyncTask.execute();


        }
        else  if(ApiMethod.equals(Constants.CallGETListMethod)){
            goatGradeDetailsArrayList.clear();
            String getApiToCall = API_Manager.getgoatGradeForDeliveryCentreKey +deliverycentrekeyFromSharedPref;

            B2BGoatGradeDetails asyncTask = new B2BGoatGradeDetails(callback_goatGradeDetailsInterface,  getApiToCall, Constants.CallGETListMethod);
            asyncTask.execute();



        }
        else  if(ApiMethod.equals(Constants.CallGETMethod)){

        }
        else  if(ApiMethod.equals(Constants.CallUPDATEMethod)){
                Modal_B2BGoatGradeDetails.setName_static(String.valueOf(updatednameString));
                Modal_B2BGoatGradeDetails.setDescription_static(String.valueOf(updateddescriptionString));
                Modal_B2BGoatGradeDetails.setDeliverycenterkey_static(String.valueOf(updateddeliveryCentreKeyString));
                Modal_B2BGoatGradeDetails.setDeliverycentername_static(String.valueOf(updateddeliveryCentreNameString));
                 Modal_B2BGoatGradeDetails.setKey_static(String.valueOf(updatedkeyString));
                 Modal_B2BGoatGradeDetails.setPrice_static(String.valueOf(twoDecimalConverter.format(updatedpriceDouble)));




            String getApiToCall = API_Manager.updategoatGradeDetails ;

            B2BGoatGradeDetails asyncTask = new B2BGoatGradeDetails(callback_goatGradeDetailsInterface,  getApiToCall, Constants.CallUPDATEMethod);
            asyncTask.execute();


        }
     


    }

    private void setgradeListAdapter() {

        adapter_goatGradeDetailsList = new Adapter_GoatGradeDetailsList(ChangeGoatGradeDetails.this,goatGradeDetailsArrayList,ChangeGoatGradeDetails.this,"");
        grade_details_listView.setAdapter(adapter_goatGradeDetailsList);
        showProgressBar(false);


    }


    public static  void showProgressBar(boolean show) {

        if(show){
            loadingPanel.setVisibility(View.VISIBLE);
            loadingpanelmask.setVisibility(View.VISIBLE);

        }
        else{
            loadingPanel.setVisibility(View.GONE);
            loadingpanelmask.setVisibility(View.GONE);

        }


    }




}