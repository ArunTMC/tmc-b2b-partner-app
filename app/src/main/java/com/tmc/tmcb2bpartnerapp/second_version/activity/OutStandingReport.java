package com.tmc.tmcb2bpartnerapp.second_version.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BRetailerDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal_B2BRetailerCreditDetails;
import com.tmc.tmcb2bpartnerapp.second_version.adapter.Adapter_OutStandingAmountRetailerwiseList;
import com.tmc.tmcb2bpartnerapp.second_version.adapter.Adapter_ViewOrdersList_recyclerView;
import com.tmc.tmcb2bpartnerapp.second_version.apiRequestServices.B2BRetailerCreditDetails;
import com.tmc.tmcb2bpartnerapp.second_version.interfaces.B2BRetailerCreditDetailsInterface;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OutStandingReport extends AppCompatActivity {


    public static LinearLayout back_IconLayout , loadingpanelmask ,loadingPanel;
    TextView as_of_date_text, deliverycentreName_textview , no_of_buyers_withoutstandingAmount , totalOutStandingAmount_textview ,instructionTextview;
    RecyclerView outstandingAmountlist_recyclerview;


    //String
    String deliveryCenterKey ="" , deliveryCenterName ="";



    //double
    double totalCreditAmountForDeliveryCentre =0 ;
    //int
    int buyerWithOutstandingAmountCount = 0;

    //boolean
    boolean  isRetailerDetailsServiceCalled = false ;
    boolean isRetailerCreditDetailsCalled = false;




    //interface
    B2BRetailerDetailsInterface callback_retailerDetailsInterface = null;
    B2BRetailerCreditDetailsInterface callBackb2BRetailerCreditDetailsInterface = null;




    //ArrayList
    public static ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList = new ArrayList<>();
    ArrayList<Modal_B2BRetailerCreditDetails> retailerCreditDetailsArrayList  = new ArrayList<>();

    static DecimalFormat twoDecimalConverter = new DecimalFormat(Constants.twoDecimalPattern);
    static DecimalFormat twoDecimalConverterWithComma = new DecimalFormat(Constants.twoDecimalWithCommaPattern);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_standing_report);

        back_IconLayout = findViewById(R.id.back_IconLayout);
        deliverycentreName_textview = findViewById(R.id.deliverycentreName_textview);
        no_of_buyers_withoutstandingAmount = findViewById(R.id.no_of_buyers_withoutstandingAmount);
        totalOutStandingAmount_textview = findViewById(R.id.totalOutStandingAmount_textview);
        outstandingAmountlist_recyclerview = findViewById(R.id.outstandingAmountlist_recyclerview);
        instructionTextview = findViewById(R.id.instructionTextview);
        loadingpanelmask = findViewById(R.id.loadingpanelmask);
        loadingPanel = findViewById(R.id.loadingPanel);
        as_of_date_text = findViewById(R.id.as_of_date_text);

        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);
        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");

        deliverycentreName_textview.setText(String.valueOf(deliveryCenterName));

        if(DatabaseArrayList_PojoClass.retailerDetailsArrayList.size() == 0){
            try {
                call_and_init_B2BRetailerDetailsService(Constants.CallGETListMethod);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            retailerDetailsArrayList = DatabaseArrayList_PojoClass.retailerDetailsArrayList;



        }


        as_of_date_text.setText(String.valueOf(DateParser.getDate_newFormat()));

        Call_and_Execute_RetailerCreditDetails(Constants.CallGETListMethod);




        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }








    public void call_and_init_B2BRetailerDetailsService(String CallMethod) {
        showProgressBar(true);
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
                 //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 1 " + DateParser.getDate_and_time_newFormat());

            }

            @Override
            public void notifySuccess(String result) {
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 2 " + DateParser.getDate_and_time_newFormat());
                if(result.equals(Constants.item_Already_Added_volley)){
                    AlertDialogClass.showDialog(OutStandingReport.this, R.string.retailersAlreadyCreated_Instruction);
                    isRetailerDetailsServiceCalled = false;
                    showProgressBar(false);

                }
                else if(result.equals(Constants.successResult_volley)){
                    retailerDetailsArrayList = DatabaseArrayList_PojoClass.retailerDetailsArrayList;

                    isRetailerDetailsServiceCalled = false;

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
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 3 " + DateParser.getDate_and_time_newFormat());
                showProgressBar(false);
            }

            @Override
            public void notifyProcessingError(Exception error) {
                isRetailerDetailsServiceCalled = false;
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());
                showProgressBar(false);
            }


        };

        if(CallMethod.equals(Constants.CallGETListMethod)){
            String getApiToCall = API_Manager.getretailerDetailsListWithDeliveryCentreKey+deliveryCenterKey ;

            B2BRetailerDetails asyncTask = new B2BRetailerDetails(callback_retailerDetailsInterface,  getApiToCall, Constants.CallGETListMethod);
            asyncTask.execute();
        }





    }


    private void Call_and_Execute_RetailerCreditDetails(String callMethod) {


        if(isRetailerCreditDetailsCalled){
            return;
        }

        showProgressBar(true);
        totalCreditAmountForDeliveryCentre = 0;
        buyerWithOutstandingAmountCount = 0;
        isRetailerCreditDetailsCalled = true;
        callBackb2BRetailerCreditDetailsInterface = new B2BRetailerCreditDetailsInterface() {
            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BRetailerCreditDetails> arrayList) {
            if(arrayList.size()==0){
                isRetailerCreditDetailsCalled = false;
                showProgressBar(false);
                Toast.makeText(OutStandingReport.this, "There is no Outstanding Amount", Toast.LENGTH_SHORT).show();
            }
            else{
                isRetailerCreditDetailsCalled = false;
                try {
                    for (int i = 0; i < arrayList.size(); i++) {
                        Modal_B2BRetailerCreditDetails modal_b2BRetailerCreditDetails = arrayList.get(i);
                        try{


                            String text = ""; double amountFromDB = 0 ;
                            try {

                                text = String.valueOf(modal_b2BRetailerCreditDetails.getTotalamountincredit().toString()).replaceAll("[^\\d.]", "");
                                if (text.equals("")) {
                                    text = "0";
                                } else {
                                    text = text;
                                }
                            } catch (Exception e) {
                                text = "0";
                                e.printStackTrace();
                            }

                            try {
                                amountFromDB = Double.parseDouble(text);
                            } catch (Exception e) {
                                amountFromDB = 0;
                                e.printStackTrace();
                            }
                            if(amountFromDB>0){
                                retailerCreditDetailsArrayList.add(modal_b2BRetailerCreditDetails);
                                buyerWithOutstandingAmountCount = buyerWithOutstandingAmountCount +1;
                            }

                            try {
                                totalCreditAmountForDeliveryCentre = totalCreditAmountForDeliveryCentre + amountFromDB;
                            } catch (Exception e) {
                                totalCreditAmountForDeliveryCentre = amountFromDB;
                                e.printStackTrace();
                            }



                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        if(i + 1 == arrayList.size()){
                            totalOutStandingAmount_textview.setText("Rs."+String.valueOf(twoDecimalConverterWithComma.format(totalCreditAmountForDeliveryCentre)));
                            no_of_buyers_withoutstandingAmount.setText(String.valueOf(buyerWithOutstandingAmountCount));

                            setAdapterForRecyclerview(retailerCreditDetailsArrayList);
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }
            }

            @Override
            public void notifySuccess(String result) {

                isRetailerCreditDetailsCalled = false;

                showProgressBar(false);


            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isRetailerCreditDetailsCalled = false;showProgressBar(false);
                Toast.makeText(OutStandingReport.this, "Volley error in Executing RetailerCredit Details", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void notifyProcessingError(Exception error) {
                isRetailerCreditDetailsCalled = false;showProgressBar(false);
                Toast.makeText(OutStandingReport.this, "Processing error in Executing RetailerCredit Details", Toast.LENGTH_SHORT).show();

            }
        };

        if(callMethod.equals(Constants.CallGETListMethod)) {
            String getApiToCall = API_Manager.getRetailerCreditDetailsUsingDeliveryCentrekeyWithIndex +"?deliverycentrekey="+ deliveryCenterKey;
            B2BRetailerCreditDetails asyncTask = new B2BRetailerCreditDetails(callBackb2BRetailerCreditDetailsInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }


    }

    private void setAdapterForRecyclerview(ArrayList<Modal_B2BRetailerCreditDetails> retailerCreditDetailsArrayList) {


        outstandingAmountlist_recyclerview.setVisibility(View.VISIBLE);
        Adapter_OutStandingAmountRetailerwiseList adapter_outStandingAmountRetailerwiseList = new Adapter_OutStandingAmountRetailerwiseList(retailerCreditDetailsArrayList,OutStandingReport.this,OutStandingReport.this);
        outstandingAmountlist_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        outstandingAmountlist_recyclerview.setHasFixedSize(true);




        outstandingAmountlist_recyclerview.setAdapter(adapter_outStandingAmountRetailerwiseList);


        showProgressBar(false);

    }


    public static void showProgressBar(boolean show) {

        if(show){
            loadingPanel.setVisibility(View.VISIBLE);
            loadingpanelmask.setVisibility(View.VISIBLE);

        }
        else {

            loadingPanel.setVisibility(View.GONE);
            loadingpanelmask.setVisibility(View.GONE);


        }


    }




}