package com.tmc.tmcb2bpartnerapp.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagTransaction;
import com.tmc.tmcb2bpartnerapp.fragment.SwitchEarTag_Fragment_UnscannedItems;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartItemDetaillsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.BarcodeScannerInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagTransactionInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_AppUserAccess;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetailsStatic;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagTransaction;
import com.tmc.tmcb2bpartnerapp.modal.Modal_Static_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_UpdatedGoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class Audit_UnstockedBatch_item  extends BaseActivity {
    TextView barcodeno_textview , genderName_textview, breedType_textview , batchno_textview , currentweight_textview;
   static LinearLayout loadingPanel , loadingpanelmask,back_IconLayout;
    GoatEarTagDetailsInterface callback_GoatEarTagDetails = null;
    GoatEarTagTransactionInterface callback_GoatEarTagTransactionInterface = null;
    FrameLayout batchItemDetailsFrame;
    Button earTagLost_button, goatLost_button;
    Fragment mfragment;


    boolean  isGoatEarTagDetailsTableServiceCalled = false;
    public static BarcodeScannerInterface barcodeScannerInterface = null;
    boolean  isGoatEarTagTransactionTableServiceCalled = false;
   public static String batchno ="",orderid ="";
    public String supplierkey="";
    public String barcodeno ="";
    public String gender ="";
    public String breedtype = "";
    public String currentweightingrams ="";
    public String scannedBarcode="";
    public static String itemsPositioninArray ="" , deliveryCenterKey ="" , deliveryCentername ="";

    boolean isB2BCartDetailsCalled = false;
    B2BCartItemDetaillsInterface callback_b2BCartItemDetaillsInterface = null;

    B2BCartOrderDetailsInterface callback_b2bOrderDetails =null ;
    boolean isB2BCartOrderTableServiceCalled = false;



    HashMap<String,Double> earTagWeightListWithBarcodeAsKey = new HashMap<>();
    DecimalFormat df = new DecimalFormat(Constants.threeDecimalPattern);
    String previous_WeightInGrams ="";

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
                setContentView(R.layout.activity_audit__unstocked_batch_item);
            } else {

                // Inflate the layout for this fragment
                setContentView(R.layout.pos_activity_audit__unstocked_batch_item);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            setContentView(R.layout.activity_audit__unstocked_batch_item);

        }


        currentweight_textview = findViewById(R.id.currentweight_textview);
        batchno_textview = findViewById(R.id.batchno_textview);
        breedType_textview = findViewById(R.id.breedType_textview);
        genderName_textview = findViewById(R.id.genderName_textview);
        barcodeno_textview = findViewById(R.id.barcodeno_textview);
        goatLost_button = findViewById(R.id.goatLost_button);
        earTagLost_button = findViewById(R.id.earTagLost_button);
        loadingpanelmask =  findViewById(R.id.loadingpanelmask);
        loadingPanel =  findViewById(R.id.loadingPanel);
        batchItemDetailsFrame =  findViewById(R.id.batchItemDetailsFrame);
        back_IconLayout =   findViewById(R.id.back_IconLayout);


       // FetchIntialData_And_UpdateUI();


        Intent intent = getIntent();
        batchno = intent.getStringExtra("batchno");
        barcodeno  = intent.getStringExtra("barcodeno");
        supplierkey = intent.getStringExtra("supplierkey");
        itemsPositioninArray = intent.getStringExtra("itemsposition");
        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);
        deliveryCentername = sh1.getString("DeliveryCenterName", "");
        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");





        earTagLost_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mfragment = new SwitchEarTag_Fragment_UnscannedItems();
                    loadMyFragment(mfragment, getString(R.string.audit_unstocked_batch_item));
                    showProgressBar(false);

                } catch (WindowManager.BadTokenException e) {
                    showProgressBar(false);
                    e.printStackTrace();
                }


            }
        });

        goatLost_button.setOnClickListener(view -> {
            Initialize_and_ExecuteInGoatEarTagDetails("GoatLost",Constants.CallUPDATEMethod);
            Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod, Constants.CallUPDATEMethod);

        });



        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
/*

    private void Initialize_and_StartBarcodeScanner(String processtoDOAfterScan) {
        barcodeScannerInterface = new BarcodeScannerInterface() {
            @Override
            public void notifySuccess(String Barcode) {
                scannedBarcode = Barcode;
              //  barcodeNo_textView.setText(Barcode);

               // openDialogToChangeBarcode(Barcode);
                // Toast.makeText(mContext, "Only Scan", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void notifySuccessAndFetchData(String Barcode) {
                scannedBarcode = Barcode;
                //Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod);
                // Toast.makeText(mContext, "Scan And Fetch", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void notifyProcessingError(Exception error) {
                // Toast.makeText(mContext, "Error in scanning ", Toast.LENGTH_SHORT).show();


            }
        };


        Intent intent = new Intent(Audit_UnstockedBatch_item.this, BarcodeScannerScreen.class);
        intent.putExtra(getString(R.string.scanner_called_to_do), processtoDOAfterScan);
        intent.putExtra(getString(R.string.called_from), getString(R.string.audit_unstocked_batch_item));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);



    }

    private void openDialogToChangeBarcode(String scannedbarcode) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Dialog dialog = new Dialog(Audit_UnstockedBatch_item.this);
                    dialog.setContentView(R.layout.change_barcode_dialog_screen);
                    TextView old_barcode__textview = (TextView) dialog.findViewById(R.id.old_barcode__textview);
                    TextView new_barcode__textview =  dialog.findViewById(R.id.new_barcode__textview);
                    Button change_barcode_button =  dialog.findViewById(R.id.change_barcode_button);

                    old_barcode__textview.setText(barcodeno);
                    new_barcode__textview.setText(scannedbarcode);

                    change_barcode_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();

                            showProgressBar(true);
                            Initialize_and_ExecuteInGoatEarTagDetails("EarTagLostDialog", Constants.CallUPDATEMethod);
                          //  Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod,Constants.CallUPDATEMethod);

                        }
                    });

                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }
*/


    public void Initialize_and_ExecuteB2BCartOrderDetails(String callMethod) {


        showProgressBar(true);
        if (isB2BCartOrderTableServiceCalled) {
            // showProgressBar(false);
            return;
        }
        isB2BCartOrderTableServiceCalled = true;
        callback_b2bOrderDetails = new B2BCartOrderDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BCartOrderDetails> arrayList) {

            }

            @Override
            public void notifySuccess(String result) {
                if (result.equals(Constants.emptyResult_volley)) {
                    showProgressBar(false);
                    isB2BCartOrderTableServiceCalled = false;
                   // Toast.makeText(Audit_UnstockedBatch_item.this, "empty result in order details "+ batchno+"   "+ orderid, Toast.LENGTH_SHORT).show();
                    FetchIntialData_And_UpdateUI();

                } else {

                  //  batchno = Modal_B2BCartOrderDetails.getBatchno();
                    orderid = Modal_B2BCartOrderDetails.getOrderid();
                    Toast.makeText(Audit_UnstockedBatch_item.this, "have result in order details "+ batchno+"   "+ orderid, Toast.LENGTH_SHORT).show();


                    Initialize_and_ExecuteB2BOrderCartItemDetails(Constants.CallGETMethod);







                    isB2BCartOrderTableServiceCalled = false;
                }
            }
            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
                isB2BCartOrderTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {

                showProgressBar(false);
                isB2BCartOrderTableServiceCalled = false;


            }




        };


        if(callMethod.equals(Constants.CallGETMethod)){
            //String getApiToCall = API_Manager.getCartOrderDetailsForBatchno+"?batchno="+batchno ;
            String getApiToCall = API_Manager.getCartOrderDetailsForDeliveryCentrekey+"?deliverycentrekey="+deliveryCenterKey ;

            B2BCartOrderDetails asyncTask = new B2BCartOrderDetails(callback_b2bOrderDetails,  getApiToCall, Constants.CallGETMethod);
            asyncTask.execute();

        }


    }

    private void Initialize_and_ExecuteB2BOrderCartItemDetails(String callADDMethod) {

        showProgressBar(true);

        if (isB2BCartDetailsCalled) {
            //  showProgressBar(false);
            return;
        }
        isB2BCartDetailsCalled = true;
        callback_b2BCartItemDetaillsInterface = new B2BCartItemDetaillsInterface()
        {

            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BCartItemDetails> arrayList) {
                showProgressBar(false);



            }

            @Override
            public void notifySuccess(String result) {
                Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 2 " + DateParser.getDate_and_time_newFormat());
                isB2BCartDetailsCalled = false;

                showProgressBar(false);
                if(callADDMethod.equals(Constants.CallGETMethod)){
                    if(!result.equals(Constants.emptyResult_volley)){
                        Toast.makeText(Audit_UnstockedBatch_item.this, "empty result in order Item details "+ batchno+"   "+ orderid + "  "+ barcodeno, Toast.LENGTH_SHORT).show();

                        // showEditabeLayouts(false, false);
                        earTagLost_button.setVisibility(View.GONE);
                        goatLost_button.setVisibility(View.GONE);
                        AlertDialogClass.showDialog(Audit_UnstockedBatch_item.this, R.string.CantEditItem_Which_isInCart_Instruction);

                    }
                    else{
                      //  Toast.makeText(Audit_UnstockedBatch_item.this, "have result in order Item details "+ batchno+"   "+ orderid + "  "+ barcodeno, Toast.LENGTH_SHORT).show();

                        earTagLost_button.setVisibility(View.VISIBLE);
                        goatLost_button.setVisibility(View.VISIBLE);
                        FetchIntialData_And_UpdateUI();

                    }
                }
                else {
                   // Toast.makeText(Audit_UnstockedBatch_item.this, "other method in order Item details "+ batchno+"   "+ orderid + "  "+ barcodeno, Toast.LENGTH_SHORT).show();

                    earTagLost_button.setVisibility(View.VISIBLE);
                    goatLost_button.setVisibility(View.VISIBLE);
                    FetchIntialData_And_UpdateUI();

                }
                showProgressBar(false);
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isB2BCartDetailsCalled = false;
                Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 3 " + DateParser.getDate_and_time_newFormat());
                showProgressBar(false);
            }

            @Override
            public void notifyProcessingError(Exception error) {
                isB2BCartDetailsCalled = false;showProgressBar(false);
                Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());

            }


        };
        if(callADDMethod.equals(Constants.CallGETMethod)) {
            String getApiToCall = API_Manager.getCartDetailsForOrderidWithBarcodeNo+"?orderid="+orderid+"&barcodeno="+barcodeno;

            B2BCartItemDetails asyncTask = new B2BCartItemDetails(callback_b2BCartItemDetaillsInterface,  getApiToCall, callADDMethod);
            asyncTask.execute();

        }



    }




    @Override
    protected void onResume() {

        if(batchno.equals("") || barcodeno .equals("")){
            Intent i = new Intent(this, DeliveryCenterDashboardScreen.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }
        else{
            super.onResume();
            Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallGETMethod);
          //  FetchIntialData_And_UpdateUI();

        }
    }

    @Override
    protected void onRestart() {
;

        if(batchno.equals("") || barcodeno .equals("")){
            Intent i = new Intent(this, DeliveryCenterDashboardScreen.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }
        else{
            super.onRestart();
            Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallGETMethod);
           /// FetchIntialData_And_UpdateUI();

        }
    }

    private void FetchIntialData_And_UpdateUI() {


        if(Modal_Static_GoatEarTagDetails.getBarcodeno().equals("") || Modal_Static_GoatEarTagDetails.getBatchno().equals("")){
            Initialize_and_ExecuteInGoatEarTagDetails("FetchIntialData",Constants.CallGETMethod);

        }
        else{
            gender = Modal_Static_GoatEarTagDetails.getGender();
            breedtype = Modal_Static_GoatEarTagDetails.getBreedtype();
            currentweightingrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();

            barcodeno_textview.setText(barcodeno);
            batchno_textview.setText(batchno);
            genderName_textview.setText(gender);
            breedType_textview.setText(breedtype);
            currentweight_textview.setText(currentweightingrams);
        }




    }





    private void Initialize_and_ExecuteInGoatEarTagDetails(String calledFrom, String callMethod) {

        showProgressBar(true);
        if (isGoatEarTagDetailsTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isGoatEarTagDetailsTableServiceCalled = true;
        callback_GoatEarTagDetails = new GoatEarTagDetailsInterface() {


            @Override
            public void notifySuccess(String result) {

                if (callMethod.equals(Constants.CallGETMethod)){
                    if(result.equals(Constants.emptyResult_volley)){
                        showProgressBar(false);
                        AlertDialogClass.showDialog(Audit_UnstockedBatch_item.this, R.string.EarTagDetailsNotFound_Instruction);
                        return;
                    }
                    else {
                        gender = Modal_Static_GoatEarTagDetails.getGender();
                        breedtype = Modal_Static_GoatEarTagDetails.getBreedtype();
                        currentweightingrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();

                        barcodeno_textview.setText(barcodeno);
                        batchno_textview.setText(batchno);
                        genderName_textview.setText(gender);
                        breedType_textview.setText(breedtype);
                        currentweight_textview.setText(currentweightingrams);
                        isGoatEarTagDetailsTableServiceCalled = false;

                        showProgressBar(false);
                    }
                }
                else if (callMethod.equals(Constants.CallUPDATEMethod)){
                    isGoatEarTagDetailsTableServiceCalled = false;
                    if(result.equals(Constants.item_not_Found_volley)){
                        showProgressBar(false);
                        AlertDialogClass.showDialog(Audit_UnstockedBatch_item.this, R.string.GoatEatTagsCannotUpdated_Instruction);
                        return;
                    }
                    else if(result.equals(Constants.expressionAttribute_is_empty_volley_response)){
                        showProgressBar(false);
                        AlertDialogClass.showDialog(Audit_UnstockedBatch_item.this, R.string.PleaseTryAgainAfterSometime_Instruction);
                        return;
                    }
                    else if(result.equals(Constants.successResult_volley)) {


                        if (calledFrom.equals("AddRequest")) {
                            Initialize_and_ExecuteInGoatEarTagDetails("AddSuccessResponse", Constants.CallUPDATEMethod);
                            Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod, Constants.CallADDMethod);
                        } else if (calledFrom.equals("GoatLost")) {
                            try {
                                if (!itemsPositioninArray.equals("")) {

                                    removeCalculationValueFromSharedPreference();
                                    int positiontoRemove = Integer.parseInt(itemsPositioninArray);
                                    try {
                                        GoatEarTagItemDetailsList.earTagItemsForBatch.remove(positiontoRemove);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        GoatEarTagItemDetailsList.adapter_earTagItemDetails_list.notifyDataSetChanged();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    onBackPressed();
                                } else {
                                    onBackPressed();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {

                            try {
                                if (!itemsPositioninArray.equals("")) {

                                    removeCalculationValueFromSharedPreference();
                                    int positiontoRemove = Integer.parseInt(itemsPositioninArray);
                                    try {
                                        GoatEarTagItemDetailsList.earTagItemsForBatch.remove(positiontoRemove);
                                        GoatEarTagItemDetailsList.earTagItemsBarcodeList.remove(positiontoRemove);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        GoatEarTagItemDetailsList.adapter_earTagItemDetails_list.notifyDataSetChanged();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    onBackPressed();
                                } else {
                                    onBackPressed();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            showProgressBar(false);
                        }

                    }
                }

                //showProgressBar(false);

            }

            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch) {

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
                Toast.makeText(Audit_UnstockedBatch_item.this, "There is an volley error while updating Ear Tag", Toast.LENGTH_SHORT).show();

                isGoatEarTagDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                Toast.makeText(Audit_UnstockedBatch_item.this, "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();

                showProgressBar(false);
                isGoatEarTagDetailsTableServiceCalled = false;


            }




        };

        if (callMethod.equals(Constants.CallUPDATEMethod)){
            try{
                Modal_UpdatedGoatEarTagDetails.setUpdated_barcodeno(barcodeno);
                Modal_UpdatedGoatEarTagDetails.setUpdated_deliverycentername(deliveryCentername);
                Modal_UpdatedGoatEarTagDetails.setUpdated_deliverycenterkey(deliveryCenterKey);
                Modal_UpdatedGoatEarTagDetails.setUpdated_batchno(Modal_B2BBatchDetailsStatic.getBatchno());
                if(calledFrom.equals("EarTagLostDialog")){
                    Modal_UpdatedGoatEarTagDetails.setUpdated_status(Constants.goatEarTagStatus_EarTagLost);
                }
                else{
                    Modal_UpdatedGoatEarTagDetails.setUpdated_status(Constants.goatEarTagStatus_GoatLost);
                }

                String addApiToCall = API_Manager.updateGoatEarTag;
                GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails,  addApiToCall , callMethod);
                asyncTask.execute();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (callMethod.equals(Constants.CallGETMethod)){

            String addApiToCall = API_Manager.getGoatEarTagDetails_forBarcodeWithBatchno +"?barcodeno="+barcodeno+"&batchno="+ batchno;
            GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails,  addApiToCall , callMethod);
            asyncTask.execute();

        }




    }

    private void removeCalculationValueFromSharedPreference() {


        try {
            String batchNo_fromPreference = "";
            int totalCount_int = 0, maleCount_int = 0, femaleCount_int = 0, femaleWithBabyCount_int = 0, totalReviewedItemCount = 0,
                    reviewed_maleCount_int = 0, reviewed_femaleCount_int = 0, reviewed_femaleWithbabyCount_int = 0;
            double totalReviewedItemWeight = 0,total_loadedweight_double = 0, minimum_weight_double = 0, maximum_weight_double = 0, average_weight_double = 0;
            SharedPreferences sharedPreferences_forAdd = getSharedPreferences(Constants.earTagCalculationDeta_DeliveryCenter, MODE_PRIVATE);


            batchNo_fromPreference = sharedPreferences_forAdd.getString(
                    "BatchNo", "0"
            );
            if (batchNo_fromPreference.toUpperCase().equals(Modal_B2BBatchDetailsStatic.getBatchno())) {


                totalCount_int = sharedPreferences_forAdd.getInt(
                        "TotalCount", 0
                );

                maleCount_int = sharedPreferences_forAdd.getInt(
                        "MaleCount", 0
                );
                femaleCount_int = sharedPreferences_forAdd.getInt(
                        "FemaleCount", 0
                );
                femaleWithBabyCount_int = sharedPreferences_forAdd.getInt(
                        "FemaleWithBabyCount", 0
                );


                total_loadedweight_double = (double) sharedPreferences_forAdd.getFloat(
                        "TotalWeight", 0);

                minimum_weight_double = (double) sharedPreferences_forAdd.getFloat(
                        "MinimumWeight", 0
                );

                maximum_weight_double = (double) sharedPreferences_forAdd.getFloat(
                        "MaximumWeight", 0
                );

                average_weight_double = (double) sharedPreferences_forAdd.getFloat(
                        "AverageWeight", 0
                );

                totalReviewedItemCount = sharedPreferences_forAdd.getInt(
                        "ReviewedTotalCount", 0
                );
                reviewed_maleCount_int = sharedPreferences_forAdd.getInt(
                        "ReviewedMaleCount", 0
                );

                reviewed_femaleCount_int = sharedPreferences_forAdd.getInt(
                        "ReviewedFemaleCount", 0
                );
                reviewed_femaleWithbabyCount_int = sharedPreferences_forAdd.getInt(
                        "ReviewedFemaleWithBabyCount", 0
                );
                totalReviewedItemWeight = sharedPreferences_forAdd.getFloat(
                        "ReviewedTotalWeight", 0
                );

                try{
                String defValue = new Gson().toJson(new HashMap<String, Double>());
                String json = sharedPreferences_forAdd.getString("EarTagWeightWithBarcodeAsKey", defValue);
                TypeToken<HashMap<String, Double>> token = new TypeToken<HashMap<String, Double>>() {
                };
                earTagWeightListWithBarcodeAsKey.putAll( new Gson().fromJson(json, token.getType()));
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                SharedPreferences sharedPreferences = getSharedPreferences(Constants.earTagCalculationDeta_DeliveryCenter, MODE_PRIVATE);

                SharedPreferences.Editor myEdit = sharedPreferences.edit();


                    totalCount_int = totalCount_int - 1;
                  //  totalReviewedItemCount = totalReviewedItemCount + 1;

                    try {
                        earTagWeightListWithBarcodeAsKey.remove(scannedBarcode);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    if (Modal_Static_GoatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))) {
                        maleCount_int = maleCount_int - 1;
                      //  reviewed_maleCount_int = reviewed_maleCount_int + 1;
                    }
                    if (Modal_Static_GoatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))) {

                        femaleCount_int = femaleCount_int - 1;
                     //   reviewed_femaleCount_int = reviewed_femaleCount_int + 1;

                    }
                    if (Modal_Static_GoatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {
                        femaleWithBabyCount_int = femaleWithBabyCount_int - 1;
                      //  reviewed_femaleWithbabyCount_int = reviewed_femaleWithbabyCount_int + 1;
                    }
                double previous_WeightInGrams_double  = 0;
                    try {
                        previous_WeightInGrams = (String.valueOf(Modal_Static_GoatEarTagDetails.getCurrentweightingrams()));
                        previous_WeightInGrams = previous_WeightInGrams.replaceAll("[^\\d.]", "");
                        if(previous_WeightInGrams.equals("")){
                            previous_WeightInGrams = "0";
                        }


                         previous_WeightInGrams_double = Double.parseDouble(previous_WeightInGrams);

                        try {
                            total_loadedweight_double = total_loadedweight_double - previous_WeightInGrams_double;
                        }
                        catch (Exception e){
                            total_loadedweight_double =0;
                            e.printStackTrace();
                        }


                        if(total_loadedweight_double<0){
                            total_loadedweight_double = 0;
                        }
                    }
                        catch (Exception e){
                        e.printStackTrace();
                    }
                    try{
                        total_loadedweight_double = Double.parseDouble(df.format(total_loadedweight_double));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    try{
                    if (previous_WeightInGrams_double == minimum_weight_double) {
                        // minimum_weight_double = entered_Weight_double;
                        double firstKeysEntry = 0;
                        for (String key : earTagWeightListWithBarcodeAsKey.keySet()) {
                            double secondKeysEntry = 0;
                            secondKeysEntry = earTagWeightListWithBarcodeAsKey.get(key);
                            if (firstKeysEntry == 0) {
                                firstKeysEntry = earTagWeightListWithBarcodeAsKey.get(key);
                            }
                            if (firstKeysEntry > secondKeysEntry) {
                                firstKeysEntry = secondKeysEntry;
                            }


                        }
                        try{
                        minimum_weight_double = firstKeysEntry;
                        myEdit.putFloat("MinimumWeight", (float) Double.parseDouble(df.format(minimum_weight_double)));
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    try{
                    if (previous_WeightInGrams_double == maximum_weight_double) {
                        //    maximum_weight_double = entered_Weight_double;
                        double firstKeysEntry = 0;
                        for (String key : earTagWeightListWithBarcodeAsKey.keySet()) {
                            double secondKeysEntry = 0;
                            secondKeysEntry = earTagWeightListWithBarcodeAsKey.get(key);
                            if (firstKeysEntry == 0) {
                                firstKeysEntry = earTagWeightListWithBarcodeAsKey.get(key);
                            }
                            if (firstKeysEntry < secondKeysEntry) {
                                firstKeysEntry = secondKeysEntry;
                            }


                        }
                        try{
                        maximum_weight_double = firstKeysEntry;
                        myEdit.putFloat("MaximumWeight", (float) Double.parseDouble(df.format(maximum_weight_double)));
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    try{
                    average_weight_double = total_loadedweight_double / totalCount_int;
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    try{
                        average_weight_double = Double.parseDouble(df.format(average_weight_double));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    try{
                    myEdit.putInt("FemaleWithBabyCount", femaleWithBabyCount_int);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                   try{
                    myEdit.putInt(
                            "FemaleCount", femaleCount_int
                    );

                   }
                   catch (Exception e){
                       e.printStackTrace();
                   }

                   try{
                    myEdit.putInt(
                            "MaleCount", maleCount_int
                    );
                   }
                   catch (Exception e){
                       e.printStackTrace();
                   }

                   try{
                    myEdit.putInt(
                            "TotalCount", totalCount_int
                    );
                   }
                   catch (Exception e){
                       e.printStackTrace();
                   }

///
                /*    myEdit.putInt(
                            "ReviewedFemaleCount", reviewed_femaleWithbabyCount_int
                    );
                    myEdit.putInt(
                            "ReviewedFemaleCount", reviewed_femaleCount_int
                    );
                    myEdit.putInt(
                            "ReviewedMaleCount", reviewed_maleCount_int
                    );
                    myEdit.putInt(
                            "ReviewedTotalCount", totalReviewedItemCount
                    );
                     myEdit.putFloat(
                        "ReviewedTotalWeight", (float) Double.parseDouble(df.format(totalReviewedItemWeight))
                );
                 */




            try{
                String jsonString = new Gson().toJson(earTagWeightListWithBarcodeAsKey);
                myEdit.putString("EarTagWeightWithBarcodeAsKey", jsonString);
            }
            catch (Exception e){
                e.printStackTrace();
            }


               try{
                myEdit.putFloat(
                        "AverageWeight", (float) Double.parseDouble(df.format(average_weight_double))
                );
               }
               catch (Exception e){
                   e.printStackTrace();
               }
                try {

                    myEdit.putFloat(
                            "TotalWeight", (float) Double.parseDouble(df.format(total_loadedweight_double))
                    );
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                myEdit.apply();
                //getCalculationValueFromSharedPreference();
                new Modal_UpdatedGoatEarTagDetails();
                new Modal_Static_GoatEarTagDetails();



            }
        }
        catch (Exception e){
            e.printStackTrace();
        }




    }

    private void Initialize_and_ExecuteInGoatEarTagTransaction(String callMethod, String goatEarTagAdd_OR_Updated) {

        showProgressBar(true);
        if (isGoatEarTagTransactionTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isGoatEarTagTransactionTableServiceCalled = true;
        callback_GoatEarTagTransactionInterface = new GoatEarTagTransactionInterface() {


            @Override
            public void notifySuccess(String result) {
                if(goatEarTagAdd_OR_Updated.equals(Constants.CallUPDATEMethod)) {
                     showProgressBar(false);
                    isGoatEarTagTransactionTableServiceCalled = false;
                }

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                if(goatEarTagAdd_OR_Updated.equals(Constants.CallUPDATEMethod)) {
                    Toast.makeText(Audit_UnstockedBatch_item.this, "There is an volley error while updating Ear Tag Transaction", Toast.LENGTH_SHORT).show();
                    showProgressBar(false);

                    isGoatEarTagTransactionTableServiceCalled = false;
                }
            }

            @Override
            public void notifyProcessingError(Exception error) {
                if(goatEarTagAdd_OR_Updated.equals(Constants.CallUPDATEMethod)) {

                    Toast.makeText(Audit_UnstockedBatch_item.this, "There is an Process error while updating Ear Tag Transaction", Toast.LENGTH_SHORT).show();

                    showProgressBar(false);
                    isGoatEarTagTransactionTableServiceCalled = false;
                }

            }




        };

        if(callMethod.equals(Constants.CallADDMethod)){
            if(goatEarTagAdd_OR_Updated.equals(Constants.CallUPDATEMethod)){
                try{

                    Modal_GoatEarTagTransaction.barcodeno = barcodeno;
                    Modal_GoatEarTagTransaction.batchno = batchno;
                    Modal_GoatEarTagTransaction.updateddate = DateParser.getDate_and_time_newFormat();
                    Modal_GoatEarTagTransaction.mobileno = Modal_AppUserAccess.getMobileno();
                    Modal_GoatEarTagTransaction.status = Modal_UpdatedGoatEarTagDetails.getUpdated_status();
                    Modal_GoatEarTagTransaction.deliverycentername = deliveryCentername;
                    Modal_GoatEarTagTransaction.deliverycenterkey = deliveryCenterKey;
                    String addApiToCall = API_Manager.addGoatEarTagTransactions;
                    GoatEarTagTransaction asyncTask = new GoatEarTagTransaction(callback_GoatEarTagTransactionInterface, addApiToCall, callMethod);
                    asyncTask.execute();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
        else if (callMethod.equals(Constants.CallUPDATEMethod)){

        }
        else if (callMethod.equals(Constants.CallGETMethod)){

        }




    }




    private void loadMyFragment(Fragment fm, String Value) {
        batchItemDetailsFrame.setVisibility(View.VISIBLE);
        if (fm != null) {

            try {

                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null);
                transaction2 .replace(batchItemDetailsFrame.getId(),  SwitchEarTag_Fragment_UnscannedItems.newInstance(getString(R.string.called_from),Value));

                transaction2.commit();


            } catch (Exception e) {

                e.printStackTrace();
            }


        }
    }

    public  void closeFragment() {

        try {
            Fragment fragment = getSupportFragmentManager().findFragmentById(batchItemDetailsFrame.getId());
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            assert fragment != null;
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }


        batchItemDetailsFrame.setVisibility(View.GONE);
    }


    public  static  void showProgressBar(boolean show) {

        if(show){
            loadingPanel.setVisibility(View.VISIBLE);
            loadingpanelmask.setVisibility(View.VISIBLE);

        }
        else{
            loadingPanel.setVisibility(View.GONE);
            loadingpanelmask.setVisibility(View.GONE);

        }


    }

    @Override
    public void onBackPressed() {
        if(batchItemDetailsFrame.getVisibility()==View.VISIBLE){
            closeFragment();
        }
        else{

            finish();

        }




    }

    public void onBackPressedInActivity() {
        finish();

    }
}