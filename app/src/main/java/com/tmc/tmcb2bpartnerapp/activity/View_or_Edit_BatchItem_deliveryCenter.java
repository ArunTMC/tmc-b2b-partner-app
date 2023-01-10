package com.tmc.tmcb2bpartnerapp.activity;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.fragment.BatchItemDetailsDeliveryCenterScreenfragment;
import com.tmc.tmcb2bpartnerapp.fragment.DeliveryCenterHomeScreenFragment;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.BarcodeScannerInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagDetailsInterface;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BBatchDetailsStatic;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BBatchDetailsUpdate;
import com.tmc.tmcb2bpartnerapp.model.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BarcodeScannerScreen;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static com.tmc.tmcb2bpartnerapp.utils.Constants.CallUPDATEMethod;

public class View_or_Edit_BatchItem_deliveryCenter extends BaseActivity {


    FrameLayout batchItemDetailsFrame;
    ImageView backButton_icon;
    static LinearLayout loadingPanel, loadingpanelmask, back_IconLayout, femaleWithBaby_Layout, reviewed_femaleWithBaby_Layout,reviewedDetails_layout,
            unSoldFemaleWithBaby_Layout,sold_femaleWithBaby_Layout,unReviewedDetails_layout,soldDetails_layout, unSoldDetails_layout;
    TextView toolBarHeader_TextView, totalCount_textview, maleCount_textview, femaleCount_textview, batchNo_textview,
            female_with_babyCount_textview, totalWeight_textview, minimum_weight_textview, maximum_weight_textview,
            average_weight_textview, total_revieweditem_textview, reviewed_maleCount_textview, reviewed_femaleWithBabyCount_textview,
            reviewed_femaleCount_textview, reviewed_totalWeight_textview;
    TextView total_solditem_textview , sold_maleCount_textview ,sold_femaleCount_textview , sold_femaleWithBabyCount_textview , sold_totalWeight_textview,unSoldtotalWeight_textview;
    TextView total_unsolditem_textview , unsold_maleCount_textview ,unsold_femaleCount_textview , unsold_femaleWithBabyCount_textview;

    Fragment mfragment;
    String value_forFragment = "";
    Button placeOrder_Button,closeBatch_Button, view_unStockedBatch_item, view_StockedBatch_item, stockBatchItem_Button,view_unSoldBatch_item,view_soldBatch_item;

    GoatEarTagDetailsInterface callback_GoatEarTagDetails = null;
    B2BBatchDetailsInterface callback_B2BBatchDetailsInterface = null;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    int pos;


    double total_loadedweight_double = 0, minimum_weight_double = 0, average_weight_double = 0,
            maximum_weight_double = 0, totalReviewedItemWeight = 0 , total_UnSoldweight_double , total_Soldweight_double;

    int maleCount_int = 0, femaleCount_int = 0, femaleWithBabyCount_int = 0, totalCount_int = 0;
    int unSoldmaleCount_int = 0, unSoldfemaleCount_int = 0, unSoldfemaleWithBabyCount_int = 0, unSoldtotalCount_int = 0;
    int soldmaleCount_int = 0, soldfemaleCount_int = 0, soldfemaleWithBabyCount_int = 0, soldtotalCount_int = 0;


    int totalReviewedItemCount = 0, reviewed_maleCount_int = 0, reviewed_femaleCount_int = 0, reviewed_femaleWithbabyCount_int = 0;

    String activityCalledFrom = "", selectDeliveryCenterKey = "", batchno = "", selectDeliveryCenterName = "",
            supplierKey = "", supplierName = "", userType = "", usermobileno = "", deliveryCenterKey = "", deliveryCenterName = "", sentdate = "", loadedweightInGrams = "", status = "", calculationValueUpdatedTimeInSharePref = "", scannedBarcode = "";
    boolean iscalledFromCreateNewbatch = false;
    boolean isBatchDetailsTableServiceCalled = false;
    boolean isGoatEarTagDetailsTableServiceCalled = false;
    //Boolean variable to mark if the transaction is safe
    private boolean isTransactionSafe;

    //Boolean variable to mark if there is any transaction pending
    private boolean isTransactionPending;
    private boolean havetoGetCalulationDataFromSharedPref = false;


    public static ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch = new ArrayList<>();
    HashMap<String, Double> earTagWeightListWithBarcodeAsKey = new HashMap<>();
    DecimalFormat df = new DecimalFormat(Constants.threeDecimalPattern);
    public static BarcodeScannerInterface barcodeScannerInterface;
    boolean isBarcodeScannerServiceCalled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeviceName();

        if(isDeviceIsMobilePhone){
            setContentView(R.layout.activity_view_or__edit__batch_item_delivery_center);
        }
        else{

            setContentView(R.layout.pos_activity_view_or__edit__batch_item_delivery_center);
        }


        havetoGetCalulationDataFromSharedPref = false;
        Log.d(Constants.TAG, "tex changed for crassh  on create " + DateParser.getDate_and_time_newFormat());

        placeOrder_Button = findViewById(R.id.placeOrder_Button);
        closeBatch_Button = findViewById(R.id.closeBatch_Button);
        view_unStockedBatch_item = findViewById(R.id.view_unStockedBatch_item);
        view_StockedBatch_item = findViewById(R.id.view_StockedBatch_item);
        stockBatchItem_Button = findViewById(R.id.stockBatchItem_Button);
        view_soldBatch_item = findViewById(R.id.view_soldBatch_item);
        view_unSoldBatch_item = findViewById(R.id.view_unSoldBatch_item);



        toolBarHeader_TextView = findViewById(R.id.toolBarHeader_TextView);
        batchItemDetailsFrame = findViewById(R.id.batchItemDetailsFrame);
        backButton_icon = findViewById(R.id.backButton_icon);
        loadingpanelmask = findViewById(R.id.loadingpanelmask);
        loadingPanel = findViewById(R.id.loadingPanel);
        back_IconLayout = findViewById(R.id.back_IconLayout);

        totalCount_textview = findViewById(R.id.totalCount_textview);
        maleCount_textview = findViewById(R.id.maleCount_textview);
        femaleCount_textview = findViewById(R.id.femaleCount_textview);
        female_with_babyCount_textview = findViewById(R.id.female_with_babyCount_textview);
        totalWeight_textview = findViewById(R.id.totalWeight_textview);
        minimum_weight_textview = findViewById(R.id.minimum_weight_textview);
        maximum_weight_textview = findViewById(R.id.maximum_weight_textview);
        average_weight_textview = findViewById(R.id.average_weight_textview);
        total_solditem_textview = findViewById(R.id.total_solditem_textview);
        sold_femaleCount_textview = findViewById(R.id.sold_femaleCount_textview);
        sold_femaleWithBabyCount_textview = findViewById(R.id.sold_femaleWithBabyCount_textview);
        sold_maleCount_textview = findViewById(R.id.sold_maleCount_textview);
        total_unsolditem_textview = findViewById(R.id.unSoldCount_textview);
        unsold_femaleCount_textview = findViewById(R.id.unSoldFemaleCount_textview);
        unsold_femaleWithBabyCount_textview = findViewById(R.id.unSoldFemale_with_babyCount_textview);
        sold_totalWeight_textview = findViewById(R.id.sold_totalWeight_textview);
        unSoldtotalWeight_textview = findViewById(R.id.unSoldtotalWeight_textview);


        unsold_maleCount_textview = findViewById(R.id.unSoldMaleCount_textview);



       // firstCardLabel = findViewById(R.id.firstCardLabel);
        //secondCardLabel = findViewById(R.id.secondCardLabel);
        batchNo_textview = findViewById(R.id.batchNo_textview);
        total_revieweditem_textview = findViewById(R.id.total_revieweditem_textview);
        reviewed_maleCount_textview = findViewById(R.id.reviewed_maleCount_textview);
        reviewed_femaleCount_textview = findViewById(R.id.reviewed_femaleCount_textview);
        reviewed_femaleWithBabyCount_textview = findViewById(R.id.reviewed_femaleWithBabyCount_textview);
        reviewed_totalWeight_textview = findViewById(R.id.reviewed_totalWeight_textview);
        femaleWithBaby_Layout = findViewById(R.id.femaleWithBaby_Layout);
        reviewed_femaleWithBaby_Layout = findViewById(R.id.reviewed_femaleWithBaby_Layout);

        sold_femaleWithBaby_Layout  = findViewById(R.id.sold_femaleWithBaby_Layout);
        unSoldFemaleWithBaby_Layout  = findViewById(R.id.unSoldFemaleWithBaby_Layout);
        soldDetails_layout  = findViewById(R.id.soldDetails_layout);
        unSoldDetails_layout = findViewById(R.id.unSoldDetails_layout);
        unReviewedDetails_layout  = findViewById(R.id.unReviewedDetails_layout);
        reviewedDetails_layout  = findViewById(R.id.reviewedDetails_layout);

        iscalledFromCreateNewbatch = false;

        SharedPreferences sh = getSharedPreferences("LoginData", MODE_PRIVATE);
        userType = sh.getString("UserType", "");
        usermobileno = sh.getString("UserMobileNumber", "");


        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);

        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");

        Intent intent = getIntent();
        activityCalledFrom = intent.getStringExtra(String.valueOf(getString(R.string.called_from)));
        batchno = intent.getStringExtra(String.valueOf("batchno"));
        supplierKey = intent.getStringExtra(String.valueOf("supplierkey"));
        supplierName = intent.getStringExtra(String.valueOf("suppliername"));



        SharedPreferences sharedPreferences
                = getSharedPreferences(Constants.earTagCalculationDeta_DeliveryCenter,
                MODE_PRIVATE);

        SharedPreferences.Editor myEdit
                = sharedPreferences.edit();
        myEdit.clear();
        myEdit.apply();
        showProgressBar(true);




        checkPermissionForCamera();
        //  getBatchDetailsfromPojoClass_or_FetchData();


        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });





        status = String.valueOf(Modal_B2BBatchDetailsStatic.getStatus());
        batchno = String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno());
        batchNo_textview.setText(String.valueOf(batchno));


      //  setDataInUI();



/*





        selectDeliveryCenterKey = String.valueOf(Modal_B2BBatchDetailsStatic.getDeliverycenterkey());
        selectDeliveryCenterName = String.valueOf(Modal_B2BBatchDetailsStatic.getDeliverycentername());

        if(!batchno.equals("")){
            Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETListMethod);

        }
        else{
            onBackPressed();
        }

 */

        placeOrder_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(View_or_Edit_BatchItem_deliveryCenter.this, BillingScreen.class);
                intent.putExtra("batchno", Modal_B2BBatchDetailsStatic.getBatchno() );
                intent.putExtra("supplierkey", Modal_B2BBatchDetailsStatic.getSupplierkey() );
                intent.putExtra("suppliername", Modal_B2BBatchDetailsStatic.getSuppliername() );
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });


        stockBatchItem_Button.setOnClickListener(view -> {


            Initialize_and_StartBarcodeScanner(getString(R.string.scannerCalled_to_FetchData));




            /*try {
                mfragment = new BatchItemDetailsDeliveryCenterScreenfragment();
                loadMyFragment(mfragment, getString(R.string.stock_batch_item));


                showProgressBar(false);

            } catch (WindowManager.BadTokenException e) {
                showProgressBar(false);

                e.printStackTrace();
            }

             */
        });

        view_unSoldBatch_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(View_or_Edit_BatchItem_deliveryCenter.this, GoatEarTagItemDetailsList.class);

                i.putExtra("TaskToDo", "ViewUnSoldItem");
                i.putExtra("batchno", batchno);
                i.putExtra("CalledFrom", Constants.userType_DeliveryCenter);
                i.putExtra("supplierkey", supplierKey);

                startActivity(i);

            }
        });


        view_soldBatch_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(View_or_Edit_BatchItem_deliveryCenter.this, GoatEarTagItemDetailsList.class);
                i.putExtra("TaskToDo", "ViewSoldItem");
                i.putExtra("batchno", batchno);
                i.putExtra("CalledFrom", Constants.userType_DeliveryCenter);
                startActivity(i);

            }
        });


        view_StockedBatch_item.setOnClickListener(view -> {
            Intent i = new Intent(View_or_Edit_BatchItem_deliveryCenter.this, GoatEarTagItemDetailsList.class);

            if (Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Reviewed_and_READYFORSALE)) {
                i.putExtra("TaskToDo", "ViewSoldItem");

            } else {
                i.putExtra("TaskToDo", "ViewReviewedItem");


            }
            i.putExtra("batchno", batchno);
            i.putExtra("CalledFrom", Constants.userType_DeliveryCenter);
            startActivity(i);



            /*try {
                mfragment = new BatchItemDetailsDeliveryCenterScreenfragment();
                loadMyFragment(mfragment, getString(R.string.view_stockedBatch_item));


                showProgressBar(false);

            } catch (WindowManager.BadTokenException e) {
                showProgressBar(false);

                e.printStackTrace();
            }

             */
        });


        view_unStockedBatch_item.setOnClickListener(view -> {


            try {
               /* Intent intent1 = new Intent(this, UnStockedBatchEarTagItemList.class);
                intent1.putExtra("batchno", batchno );
                intent1.putExtra("supplierkey", supplierKey );
                intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent1);

                */
                Intent i = new Intent(View_or_Edit_BatchItem_deliveryCenter.this, GoatEarTagItemDetailsList.class);

                if (Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Reviewed_and_READYFORSALE)) {
                    i.putExtra("TaskToDo", "ViewUnSoldItem");

                } else {
                    i.putExtra("TaskToDo", "ViewUnReviewedItem");

                }
                i.putExtra("batchno", batchno);
                i.putExtra("CalledFrom", Constants.userType_DeliveryCenter);
                i.putExtra("supplierkey", supplierKey);

                startActivity(i);


            } catch (Exception e) {

                e.printStackTrace();
            }
        });


        closeBatch_Button.setOnClickListener(view -> {

            try {
                Intent intent1 = new Intent(View_or_Edit_BatchItem_deliveryCenter.this, FinishBatch_ConsolidatedReport.class);
                intent1.putExtra("batchno", batchno);
                intent1.putExtra("deliveryCenterKey", deliveryCenterKey);
                intent1.putExtra("deliveryCenterName", deliveryCenterName);
                intent1.putExtra("supplierkey", supplierKey);
                intent1.putExtra("suppliername", supplierName);
                intent1.putExtra(getString(R.string.called_from), getString(R.string.delivery_center));
                startActivity(intent1);
                showProgressBar(false);

            } catch (WindowManager.BadTokenException e) {
                showProgressBar(false);

                e.printStackTrace();
            }

        });


    }


    private void Initialize_and_StartBarcodeScanner(String processtoDOAfterScan) {
        if (isBarcodeScannerServiceCalled) {
            showProgressBar(false);
            return;
        }
        barcodeScannerInterface = new BarcodeScannerInterface() {
            @Override
            public void notifySuccess(String Barcode) {
                scannedBarcode = Barcode;
                // filterDataBasedOnBarcode(scannedBarcode);
                //barcodeNo_textView.setText(Barcode);
                // Toast.makeText(mContext, "Only Scan", Toast.LENGTH_SHORT).show();
                isBarcodeScannerServiceCalled = false;

            }

            @Override
            public void notifySuccessAndFetchData(String Barcode) {
                showProgressBar(true);
                scannedBarcode = Barcode;
                isBarcodeScannerServiceCalled = false;

                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod);
                // Toast.makeText(mContext, "Scan And Fetch", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void notifyProcessingError(Exception error) {
                // Toast.makeText(mContext, "Error in scanning ", Toast.LENGTH_SHORT).show();


            }
        };


        Intent intent = new Intent(View_or_Edit_BatchItem_deliveryCenter.this, BarcodeScannerScreen.class);
        intent.putExtra(getString(R.string.scanner_called_to_do), processtoDOAfterScan);
        intent.putExtra(getString(R.string.called_from), getString(R.string.view_edit_batch_item_deliveryCenter));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);


    }


    private void getBatchDetailsfromPojoClass_or_FetchData() {

        showProgressBar(true);

        if (!String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno()).equals("") && !String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno()).toUpperCase().equals("NULL")) {
            batchno = String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno());
            batchNo_textview.setText(String.valueOf(batchno));
            selectDeliveryCenterKey = String.valueOf(Modal_B2BBatchDetailsStatic.getDeliverycenterkey());
            selectDeliveryCenterName = String.valueOf(Modal_B2BBatchDetailsStatic.getDeliverycentername());
            batchno = Modal_B2BBatchDetailsStatic.getBatchno().toString();
            supplierKey = Modal_B2BBatchDetailsStatic.getSupplierkey().toString();
            supplierName = Modal_B2BBatchDetailsStatic.getSuppliername().toString();
            sentdate = Modal_B2BBatchDetailsStatic.getSentdate().toString();
            loadedweightInGrams = Modal_B2BBatchDetailsStatic.getLoadedweightingrams().toString();
            status = Modal_B2BBatchDetailsStatic.getStatus().toString();
            if (Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Reviewed_and_READYFORSALE)) {
                closeBatch_Button.setText(getString(R.string.Generate_Report_CompleteBatch));
                soldDetails_layout.setVisibility(View.VISIBLE);
                unSoldDetails_layout.setVisibility(View.VISIBLE);
                reviewedDetails_layout.setVisibility(View.GONE);
                unReviewedDetails_layout.setVisibility(View.GONE);
                stockBatchItem_Button.setVisibility(View.GONE);
                placeOrder_Button.setVisibility(View.GONE);
            }
            else if (Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Sold)) {
                closeBatch_Button.setText(getString(R.string.Generate_Report));
                soldDetails_layout.setVisibility(View.VISIBLE);
                unSoldDetails_layout.setVisibility(View.VISIBLE);
                placeOrder_Button.setVisibility(View.GONE);
                reviewedDetails_layout.setVisibility(View.GONE);
                unReviewedDetails_layout.setVisibility(View.GONE);
                stockBatchItem_Button.setVisibility(View.GONE);
            }

            else {
                closeBatch_Button.setText(getString(R.string.Generate_Report_FinishBatch));
                soldDetails_layout.setVisibility(View.GONE);
                unSoldDetails_layout.setVisibility(View.GONE);
                reviewedDetails_layout.setVisibility(View.VISIBLE);
                unReviewedDetails_layout.setVisibility(View.VISIBLE);
                stockBatchItem_Button.setVisibility(View.VISIBLE);
                placeOrder_Button.setVisibility(View.GONE);
            }
            //  UpdateUI();
            /*if(status.toUpperCase().equals(Constants.batchDetailsStatus_Fully_Loaded)){
                Intialize_And_Process_BatchDetails(batchno, Constants.CallUPDATEMethod);

            }

             */
            // Toast.makeText(this, DateParser.checkForTimeDifference("",calculationValueUpdatedTimeInSharePref), Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences_forAdd = getSharedPreferences(Constants.earTagCalculationDeta_DeliveryCenter, MODE_PRIVATE);
            calculationValueUpdatedTimeInSharePref = sharedPreferences_forAdd.getString(
                    "UpdatedTime", ""
            );
            String timeDifference = DateParser.checkForTimeDifference("", calculationValueUpdatedTimeInSharePref);
            // if(timeDifference.toLowerCase().contains("minute")) {
            double minutes_double = 0;
            String minutes_string = "";
            minutes_string = timeDifference.replaceAll("[^\\d.]", "");

            if(minutes_string.equals("") || minutes_string.equals(null)){
                minutes_string = "0";
            }

            if (minutes_string.equals("")) {
                minutes_string = "0";
            }
            minutes_double = Double.parseDouble(minutes_string);
            if (minutes_double > 5) {
                // Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETListMethod);
                getCalculationValueFromSharedPreference();

            } else {
                getCalculationValueFromSharedPreference();
                //Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETListMethod);

            }
          /*  }
            else{
                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETListMethod);

            }

           */


        } else {
            Intialize_And_Process_BatchDetails(batchno, Constants.CallGETMethod);

            //   onBackPressed();
        }


    }


    private void Intialize_And_Process_BatchDetails(String batchId, String callMethod) {
        showProgressBar(true);
        if (isBatchDetailsTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isBatchDetailsTableServiceCalled = true;
        callback_B2BBatchDetailsInterface = new B2BBatchDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BBatchDetails> batchDetailsArrayList) {

            }

            @Override
            public void notifySuccess(String result) {


                if (callMethod.equals(Constants.CallGETMethod)) {

                    if (result.equals(Constants.item_Already_Added_volley)) {
                        AlertDialogClass.showDialog(View_or_Edit_BatchItem_deliveryCenter.this, R.string.BatchDetailsAlreadyCreated_Instruction);

                    } else if (result.equals(Constants.successResult_volley)) {

                        batchno = Modal_B2BBatchDetailsStatic.getBatchno().toString();
                        supplierKey = Modal_B2BBatchDetailsStatic.getSupplierkey().toString();
                        supplierName = Modal_B2BBatchDetailsStatic.getSuppliername().toString();
                        sentdate = Modal_B2BBatchDetailsStatic.getSentdate().toString();
                        loadedweightInGrams = Modal_B2BBatchDetailsStatic.getLoadedweightingrams().toString();
                        status = Modal_B2BBatchDetailsStatic.getStatus().toString();


                        //UpdateUI();

                    } else {
                        // Toast.makeText(View_OR_Audit_BatchItem.this, Constants.unknown_API_Result_volley, Toast.LENGTH_SHORT).show();
                    }

                } else if (callMethod.equals(Constants.CallUPDATEMethod)) {
                    try {
                        DeliveryCenterHomeScreenFragment.batchDetailsArrayList.get(pos).setStatus(Constants.batchDetailsStatus_Reviewing);
                        DeliveryCenterHomeScreenFragment.batchDetailsArrayList.get(pos).setReceiveddate(DateParser.getDate_and_time_newFormat());
                        DeliveryCenterHomeScreenFragment.batchDetailsArrayList.get(pos).setReceivermobileno(usermobileno);
                        DeliveryCenterHomeScreenFragment.adapter_b2BBatchItemsList.notifyDataSetChanged();

                        Modal_B2BBatchDetailsStatic.setStatus(Constants.batchDetailsStatus_Reviewing);
                        Modal_B2BBatchDetailsStatic.setReceiveddate(DateParser.getDate_and_time_newFormat());
                        Modal_B2BBatchDetailsStatic.setReceivermobileno(usermobileno);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                showProgressBar(false);
                isBatchDetailsTableServiceCalled = false;

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
                Toast.makeText(View_or_Edit_BatchItem_deliveryCenter.this, Constants.volleyError_Result_volley, Toast.LENGTH_SHORT).show();

                isBatchDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                showProgressBar(false);
                Toast.makeText(View_or_Edit_BatchItem_deliveryCenter.this, Constants.processingErrorResult_volley, Toast.LENGTH_SHORT).show();

                isBatchDetailsTableServiceCalled = false;
            }


        };

        if (callMethod.equals(Constants.CallGETMethod)) {


            String addApiToCall = API_Manager.getBatchDetailsWithSupplierkeyBatchNo + "?supplierkey=" + supplierKey + "&batchno=" + batchId;

            B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface, addApiToCall, Constants.CallGETMethod);
            asyncTask.execute();
        } else if (callMethod.equals(Constants.CallUPDATEMethod)) {
            Modal_B2BBatchDetailsUpdate modal_b2BBatchDetailsUpdate = new Modal_B2BBatchDetailsUpdate();
            modal_b2BBatchDetailsUpdate.setBatchno(batchno);
            modal_b2BBatchDetailsUpdate.setSupplierkey(supplierKey);
            modal_b2BBatchDetailsUpdate.setStatus(Constants.batchDetailsStatus_Reviewing);
            modal_b2BBatchDetailsUpdate.setReceiveddate(DateParser.getDate_and_time_newFormat());
            modal_b2BBatchDetailsUpdate.setReceivermobileno(usermobileno);

            String addApiToCall = API_Manager.updateBatchDetailsWithSupplierkeyBatchNo;

            B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface, addApiToCall, CallUPDATEMethod);
            asyncTask.execute();

        }

    }


    private void Initialize_and_ExecuteInGoatEarTagDetails(String callMethod) {
        earTagItemsForBatch.clear();


        showProgressBar(true);
        if (isGoatEarTagDetailsTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isGoatEarTagDetailsTableServiceCalled = true;
        earTagItemsForBatch.clear();
        callback_GoatEarTagDetails = new GoatEarTagDetailsInterface() {


            @Override
            public void notifySuccess(String result) {
                isGoatEarTagDetailsTableServiceCalled = false;

                if (result.equals(Constants.emptyResult_volley)) {
                    try {
                        AlertDialogClass.showDialog(View_or_Edit_BatchItem_deliveryCenter.this, R.string.EarTagDetailsNotFound_Instruction);

                    } catch (Exception e) {
                        Toast.makeText(View_or_Edit_BatchItem_deliveryCenter.this, getString(R.string.EarTagDetailsNotFound_Instruction), Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    }

                } else {
                    try {
                        value_forFragment = getString(R.string.stock_batch_item_withoutScan_allowedScan);
                        mfragment = new BatchItemDetailsDeliveryCenterScreenfragment();
                        loadMyFragment();


                        showProgressBar(false);

                    } catch (WindowManager.BadTokenException e) {

                        showProgressBar(false);

                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatchFromDB) {
                try {
                    if (earTagItemsForBatchFromDB.size() > 0) {
                        earTagItemsForBatch = earTagItemsForBatchFromDB;

                        isGoatEarTagDetailsTableServiceCalled = false;
                        earTagWeightListWithBarcodeAsKey.clear();
                        if (Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Reviewed_and_READYFORSALE) || Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Sold) ) {

                            ProcessArray_And_DisplayUnSoldStatusData();
                            ProcessArray_And_DisplaySoldStatusData();
                        }
                        else{

                            ProcessArray_And_DisplayUnReviewedStatusData();
                            ProcessArray_And_DisplayReviewedStatusData();

                        }


                    } else {
                        showProgressBar(false);
                        isGoatEarTagDetailsTableServiceCalled = false;
                        Toast.makeText(View_or_Edit_BatchItem_deliveryCenter.this, Constants.there_is_noData_volley, Toast.LENGTH_SHORT).show();


                    }

                } catch (Exception e) {
                    showProgressBar(false);
                    isGoatEarTagDetailsTableServiceCalled = false;

                    Toast.makeText(View_or_Edit_BatchItem_deliveryCenter.this, "There is an error while generate report", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }


            }


            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
                Toast.makeText(View_or_Edit_BatchItem_deliveryCenter.this, "There is an volley error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                isGoatEarTagDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                Toast.makeText(View_or_Edit_BatchItem_deliveryCenter.this, "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();

                showProgressBar(false);
                isGoatEarTagDetailsTableServiceCalled = false;


            }


        };

        if (callMethod.equals(Constants.CallGETMethod)) {
            String addApiToCall = API_Manager.getGoatEarTagDetails_forBarcodeWithBatchno + "?barcodeno=" + scannedBarcode + "&batchno=" + batchno;
            GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
            asyncTask.execute();
        } else if (callMethod.equals(Constants.CallGETListMethod)) {
            String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchno + batchno;
            GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
            asyncTask.execute();
        }


    }

    private void ProcessArray_And_DisplaySoldStatusData() {

        total_Soldweight_double = 0;
        soldtotalCount_int = 0;
        soldmaleCount_int = 0;
        soldfemaleCount_int = 0;
        soldfemaleWithBabyCount_int = 0;


        for (int iterator = 0; iterator < earTagItemsForBatch.size(); iterator++) {
            Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagItemsForBatch.get(iterator);


            String statusToCompare = "";
         if (Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Sold) || Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Reviewed_and_READYFORSALE)) {
                statusToCompare = Constants.goatEarTagStatus_Sold;
                if (modal_goatEarTagDetails.getStatus().equals(statusToCompare) ){

                    String LoadedWeight = "0";
                    double LoadedWeight_double = 0;

                    try {
                        soldtotalCount_int = soldtotalCount_int + 1;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))) {
                            soldmaleCount_int = soldmaleCount_int + 1;

                        }
                        if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))) {
                            soldfemaleCount_int =  soldfemaleCount_int + 1;
                        }
                        if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {
                            soldfemaleWithBabyCount_int = soldfemaleWithBabyCount_int + 1;

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    try {
                        LoadedWeight = String.valueOf(modal_goatEarTagDetails.getCurrentweightingrams());
                        LoadedWeight = LoadedWeight.replaceAll("[^\\d.]", "");
                        if(LoadedWeight.equals("") || LoadedWeight.equals(null)){
                            LoadedWeight = "0";
                        }

                        LoadedWeight_double = Double.parseDouble(LoadedWeight);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        total_Soldweight_double = total_Soldweight_double + LoadedWeight_double;
                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                    try {
                        total_Soldweight_double = Double.parseDouble(df.format(total_Soldweight_double));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }


            if (iterator - (earTagItemsForBatch.size() - 1) == 0) {
                // average_weight_double = (total_loadedweight_double / totalCount_int);


                SaveSold_UnSoldCalculationDatasInSharedPref(false);

                setDataInUI();


            }

        }


    }

    private void ProcessArray_And_DisplayUnSoldStatusData() {

        total_UnSoldweight_double = 0;

        unSoldmaleCount_int = 0;
        unSoldfemaleCount_int = 0;
        unSoldfemaleWithBabyCount_int = 0;
        unSoldtotalCount_int = 0;
        for (int iterator = 0; iterator < earTagItemsForBatch.size(); iterator++) {
            Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagItemsForBatch.get(iterator);
            String statusToCompare = "", status_2_toCompare;


            if (modal_goatEarTagDetails.getStatus().equals(Constants.goatEarTagStatus_Goatsick) || (modal_goatEarTagDetails.getStatus().equals(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE))  ) {


                String LoadedWeight = "0";
                double LoadedWeight_double = 0;

                try {
                    unSoldtotalCount_int = unSoldtotalCount_int + 1;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))) {
                        unSoldmaleCount_int = unSoldmaleCount_int + 1;

                    }
                    if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))) {
                        unSoldfemaleCount_int = unSoldfemaleCount_int + 1;
                    }
                    if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {
                        unSoldfemaleWithBabyCount_int = unSoldfemaleWithBabyCount_int + 1;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
                    LoadedWeight = String.valueOf(modal_goatEarTagDetails.getCurrentweightingrams());
                    LoadedWeight = LoadedWeight.replaceAll("[^\\d.]", "");
                    if(LoadedWeight.equals("") || LoadedWeight.equals(null)){
                        LoadedWeight = "0";
                    }


                    LoadedWeight_double = Double.parseDouble(LoadedWeight);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                earTagWeightListWithBarcodeAsKey.put(modal_goatEarTagDetails.getBarcodeno(), LoadedWeight_double);


                try {
                    total_UnSoldweight_double = total_UnSoldweight_double + LoadedWeight_double;
                } catch (Exception e) {
                    e.printStackTrace();

                }


                try {
                    total_UnSoldweight_double = Double.parseDouble(df.format(total_UnSoldweight_double));
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            if (iterator - (earTagItemsForBatch.size() - 1) == 0) {


                SaveSold_UnSoldCalculationDatasInSharedPref(true);
                setDataInUI();

            }

        }






    }


    private void ProcessArray_And_DisplayReviewedStatusData() {

        totalReviewedItemWeight = 0;
        totalReviewedItemCount = 0;
        reviewed_maleCount_int = 0;
        reviewed_femaleCount_int = 0;
        reviewed_femaleWithbabyCount_int = 0;


        for (int iterator = 0; iterator < earTagItemsForBatch.size(); iterator++) {
            Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagItemsForBatch.get(iterator);


            String statusToCompare = "";

            if (Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Reviewed_and_READYFORSALE)) {
                statusToCompare = Constants.goatEarTagStatus_Sold;
                if (modal_goatEarTagDetails.getStatus().equals(statusToCompare)) {


                    String LoadedWeight = "0";
                    double LoadedWeight_double = 0;

                    try {
                        totalReviewedItemCount = totalReviewedItemCount + 1;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))) {
                            reviewed_maleCount_int = reviewed_maleCount_int + 1;

                        }
                        if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))) {
                            reviewed_femaleCount_int = reviewed_femaleCount_int + 1;
                        }
                        if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {
                            reviewed_femaleWithbabyCount_int = reviewed_femaleWithbabyCount_int + 1;

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    try {
                        LoadedWeight = String.valueOf(modal_goatEarTagDetails.getCurrentweightingrams());
                        LoadedWeight = LoadedWeight.replaceAll("[^\\d.]", "");

                        if(LoadedWeight.equals("") || LoadedWeight.equals(null)){
                            LoadedWeight = "0";
                        }
                        LoadedWeight_double = Double.parseDouble(LoadedWeight);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        totalReviewedItemWeight = totalReviewedItemWeight + LoadedWeight_double;
                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                    try {
                        totalReviewedItemWeight = Double.parseDouble(df.format(totalReviewedItemWeight));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
            else if (Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Reviewing) || Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Fully_Loaded)) {
                statusToCompare = Constants.goatEarTagStatus_Loading;
                if (!modal_goatEarTagDetails.getStatus().equals(statusToCompare) && (!modal_goatEarTagDetails.getStatus().equals(Constants.goatEarTagStatus_EarTagLost))  && (!modal_goatEarTagDetails.getStatus().equals(Constants.goatEarTagStatus_GoatLost)) && (!modal_goatEarTagDetails.getStatus().equals(Constants.goatEarTagStatus_Goatdead)) && (!modal_goatEarTagDetails.getStatus().equals(Constants.goatEarTagStatus_Cancelled))) {


                    String LoadedWeight = "0";
                    double LoadedWeight_double = 0;

                    try {
                        totalReviewedItemCount = totalReviewedItemCount + 1;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))) {
                            reviewed_maleCount_int = reviewed_maleCount_int + 1;

                        }
                        if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))) {
                            reviewed_femaleCount_int = reviewed_femaleCount_int + 1;
                        }
                        if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {
                            reviewed_femaleWithbabyCount_int = reviewed_femaleWithbabyCount_int + 1;

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    try {
                        LoadedWeight = String.valueOf(modal_goatEarTagDetails.getCurrentweightingrams());
                        LoadedWeight = LoadedWeight.replaceAll("[^\\d.]", "");
                        if(LoadedWeight.equals("") || LoadedWeight.equals(null)){
                            LoadedWeight = "0";
                        }

                        LoadedWeight_double = Double.parseDouble(LoadedWeight);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        totalReviewedItemWeight = totalReviewedItemWeight + LoadedWeight_double;
                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                    try {
                        totalReviewedItemWeight = Double.parseDouble(df.format(totalReviewedItemWeight));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }


            if (iterator - (earTagItemsForBatch.size() - 1) == 0) {
                // average_weight_double = (total_loadedweight_double / totalCount_int);


                SaveCalculationDatasInSharedPref(false);

                setDataInUI();


            }

        }


    }

    private void ProcessArray_And_DisplayUnReviewedStatusData() {


        total_loadedweight_double = 0;
        minimum_weight_double = 0;
        average_weight_double = 0;
        maximum_weight_double = 0;
        maleCount_int = 0;
        femaleCount_int = 0;
        femaleWithBabyCount_int = 0;
        totalCount_int = 0;
        for (int iterator = 0; iterator < earTagItemsForBatch.size(); iterator++) {
            Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagItemsForBatch.get(iterator);
            String statusToCompare = "", status_2_toCompare;

            if (Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Reviewed_and_READYFORSALE)) {
                statusToCompare = Constants.goatEarTagStatus_Reviewed_and_READYFORSALE;
            } else if (Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Reviewing) || Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Fully_Loaded)) {
                statusToCompare = Constants.goatEarTagStatus_Loading;
            }

            if (modal_goatEarTagDetails.getStatus().equals(statusToCompare)) {


                String LoadedWeight = "0";
                double LoadedWeight_double = 0;

                try {
                    totalCount_int = totalCount_int + 1;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))) {
                        maleCount_int = maleCount_int + 1;

                    }
                    if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))) {
                        femaleCount_int = femaleCount_int + 1;
                    }
                    if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {
                        femaleWithBabyCount_int = femaleWithBabyCount_int + 1;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
                    LoadedWeight = String.valueOf(modal_goatEarTagDetails.getCurrentweightingrams());
                    LoadedWeight = LoadedWeight.replaceAll("[^\\d.]", "");
                    if(LoadedWeight.equals("") || LoadedWeight.equals(null)){
                        LoadedWeight = "0";
                    }

                    LoadedWeight_double = Double.parseDouble(LoadedWeight);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                earTagWeightListWithBarcodeAsKey.put(modal_goatEarTagDetails.getBarcodeno(), LoadedWeight_double);


                try {
                    total_loadedweight_double = total_loadedweight_double + LoadedWeight_double;
                } catch (Exception e) {
                    e.printStackTrace();

                }


                try {
                    total_loadedweight_double = Double.parseDouble(df.format(total_loadedweight_double));
                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {

                    if (minimum_weight_double == 0) {
                        minimum_weight_double = LoadedWeight_double;

                    }
                    if (minimum_weight_double > LoadedWeight_double) {
                        minimum_weight_double = LoadedWeight_double;
                    }


                    if (LoadedWeight_double > maximum_weight_double) {
                        maximum_weight_double = LoadedWeight_double;
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (iterator - (earTagItemsForBatch.size() - 1) == 0) {
                average_weight_double = (total_loadedweight_double / totalCount_int);

                try {
                    average_weight_double = Double.parseDouble(df.format(average_weight_double));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                SaveCalculationDatasInSharedPref(true);
                setDataInUI();

            }

        }


    }


    private void SaveSold_UnSoldCalculationDatasInSharedPref(boolean isCalledFromUnSoldData) {
        SharedPreferences sharedPreferences
                = getSharedPreferences(Constants.earTagCalculationDeta_DeliveryCenter,
                MODE_PRIVATE);

        SharedPreferences.Editor myEdit
                = sharedPreferences.edit();
        myEdit.putString(
                "BatchNo", String.valueOf(batchno)
        );

        myEdit.putString(
                "UpdatedTime", String.valueOf(DateParser.getDate_and_time_newFormat())
        );

        if(isCalledFromUnSoldData) {
            myEdit.putInt(
                    "TotalUnsoldCount", unSoldtotalCount_int
            );

            myEdit.putInt(
                    "UnsoldMaleCount", unSoldmaleCount_int
            );
            myEdit.putInt(
                    "UnsoldFemaleCount", unSoldfemaleCount_int
            );
            myEdit.putInt(
                    "UnsoldFemaleWithBabyCount", unSoldfemaleWithBabyCount_int
            );

            myEdit.putFloat(
                    "TotalUnsoldWeight", (float) Double.parseDouble(df.format(total_UnSoldweight_double))
            );

            myEdit.apply();
        }
        else{

            myEdit.putFloat(
                    "TotalSoldWeight",(float) Double.parseDouble(df.format( total_Soldweight_double))
            );


            myEdit.putInt(
                    "TotalSoldCount", soldtotalCount_int
            );

            myEdit.putInt(
                    "SoldMaleCount", soldmaleCount_int
            );
            myEdit.putInt(
                    "SoldFemaleCount", soldfemaleCount_int
            );
            myEdit.putInt(
                    "SoldFemaleWithBabyCount", soldfemaleWithBabyCount_int
            );
            myEdit.apply();
        }

    }
    private void SaveCalculationDatasInSharedPref(boolean isCalledFromUnReviewedData) {

        SharedPreferences sharedPreferences
                = getSharedPreferences(Constants.earTagCalculationDeta_DeliveryCenter,
                MODE_PRIVATE);

        SharedPreferences.Editor myEdit
                = sharedPreferences.edit();
        myEdit.putString(
                "BatchNo", String.valueOf(batchno)
        );

        myEdit.putString(
                "UpdatedTime", String.valueOf(DateParser.getDate_and_time_newFormat())
        );

        if(isCalledFromUnReviewedData) {
            myEdit.putInt(
                    "TotalCount", totalCount_int
            );

            myEdit.putInt(
                    "MaleCount", maleCount_int
            );
            myEdit.putInt(
                    "FemaleCount", femaleCount_int
            );
            myEdit.putInt(
                    "FemaleWithBabyCount", femaleWithBabyCount_int
            );

            myEdit.putFloat(
                    "TotalWeight", (float) Double.parseDouble(df.format(total_loadedweight_double))
            );

            myEdit.putFloat(
                    "MinimumWeight", (float) Double.parseDouble(df.format(minimum_weight_double))
            );


            myEdit.putFloat(
                    "MaximumWeight", (float) Double.parseDouble(df.format((maximum_weight_double)))
            );


            myEdit.putFloat(
                    "AverageWeight", (float) Double.parseDouble(df.format(average_weight_double))

            );


            String jsonString = new Gson().toJson(earTagWeightListWithBarcodeAsKey);
            myEdit.putString("EarTagWeightWithBarcodeAsKey", jsonString);
            myEdit.apply();
        }
        else{

            myEdit.putFloat(
                    "ReviewedTotalWeight",(float) Double.parseDouble(df.format( totalReviewedItemWeight))
            );


            myEdit.putInt(
                    "ReviewedTotalCount", totalReviewedItemCount
            );

            myEdit.putInt(
                    "ReviewedMaleCount", reviewed_maleCount_int
            );
            myEdit.putInt(
                    "ReviewedFemaleCount", reviewed_femaleCount_int
            );
            myEdit.putInt(
                    "ReviewedFemaleWithBabyCount", reviewed_femaleWithbabyCount_int
            );
            myEdit.apply();
        }








    }






    private boolean isItemShouldAddedInCount(String statusToCompare, String statusFromArray) {

        if (statusToCompare.toUpperCase().equals(Constants.goatEarTagStatus_Loading.toUpperCase())) {
            if (statusFromArray.toUpperCase().equals(Constants.goatEarTagStatus_Loading.toUpperCase()) || statusFromArray.toUpperCase().equals(Constants.goatEarTagStatus_Goatsick.toUpperCase())) {
                return true;
            } else {
                return false;
            }
        } else if (statusToCompare.toUpperCase().equals(Constants.batchDetailsStatus_Reviewed_and_READYFORSALE)) {
            if (statusFromArray.toUpperCase().equals(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE.toUpperCase())) {
                return true;
            } else {
                return false;
            }

        }
        return false;
    }




    private void setDataInUI() {

        if (Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Reviewed_and_READYFORSALE)) {
            closeBatch_Button.setText(getString(R.string.Generate_Report_CompleteBatch));
            soldDetails_layout.setVisibility(View.VISIBLE);
            unSoldDetails_layout.setVisibility(View.VISIBLE);
            reviewedDetails_layout.setVisibility(View.GONE);
            unReviewedDetails_layout.setVisibility(View.GONE);
            placeOrder_Button.setVisibility(View.GONE );
            stockBatchItem_Button.setVisibility(View.GONE);
        }
        else if (Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Sold)) {
            closeBatch_Button.setText(getString(R.string.Generate_Report));
            soldDetails_layout.setVisibility(View.VISIBLE);
            unSoldDetails_layout.setVisibility(View.VISIBLE);
            reviewedDetails_layout.setVisibility(View.GONE);
            unReviewedDetails_layout.setVisibility(View.GONE);

            stockBatchItem_Button.setVisibility(View.GONE);
        }

        else {
            closeBatch_Button.setText(getString(R.string.Generate_Report_FinishBatch));
            soldDetails_layout.setVisibility(View.GONE);
            unSoldDetails_layout.setVisibility(View.GONE);
            reviewedDetails_layout.setVisibility(View.VISIBLE);
            unReviewedDetails_layout.setVisibility(View.VISIBLE);
            stockBatchItem_Button.setVisibility(View.VISIBLE);
            placeOrder_Button.setVisibility(View.GONE);
        }

        totalCount_textview.setText(String.valueOf(totalCount_int));
        maleCount_textview.setText(String.valueOf(maleCount_int));
        femaleCount_textview.setText(String.valueOf(femaleCount_int));
        if(femaleWithBabyCount_int>0){
            femaleWithBaby_Layout.setVisibility(View.VISIBLE);
        }
        else{
            femaleWithBaby_Layout.setVisibility(View.GONE);
        }
        female_with_babyCount_textview.setText(String.valueOf(femaleWithBabyCount_int));
        totalWeight_textview.setText(String.valueOf(Double.parseDouble(df.format(total_loadedweight_double))));
        reviewed_totalWeight_textview .setText(String.valueOf(Double.parseDouble(df.format(totalReviewedItemWeight))));
        minimum_weight_textview.setText(String.valueOf(Double.parseDouble(df.format(minimum_weight_double))));
        maximum_weight_textview.setText(String.valueOf(Double.parseDouble(df.format(maximum_weight_double))));
        average_weight_textview.setText(String.valueOf(df.format(average_weight_double)));


        if(reviewed_femaleWithbabyCount_int>0){
            reviewed_femaleWithBaby_Layout.setVisibility(View.VISIBLE);
        }
        else{
            reviewed_femaleWithBaby_Layout.setVisibility(View.GONE);
        }

        total_revieweditem_textview.setText(String.valueOf(totalReviewedItemCount));
        reviewed_maleCount_textview.setText(String.valueOf(reviewed_maleCount_int));
        reviewed_femaleCount_textview.setText(String.valueOf(reviewed_femaleCount_int));
        reviewed_femaleWithBabyCount_textview.setText(String.valueOf(reviewed_femaleWithbabyCount_int));


        if(soldfemaleWithBabyCount_int>0){
            sold_femaleWithBaby_Layout.setVisibility(View.VISIBLE);
        }
        else{
            sold_femaleWithBaby_Layout.setVisibility(View.GONE);
        }




        total_solditem_textview.setText(String.valueOf(soldtotalCount_int));
        sold_maleCount_textview.setText(String.valueOf(soldmaleCount_int));
        sold_femaleCount_textview.setText(String.valueOf(soldfemaleCount_int));
        sold_femaleWithBabyCount_textview.setText(String.valueOf(soldfemaleWithBabyCount_int));
        sold_femaleWithBabyCount_textview.setText(String.valueOf(soldfemaleWithBabyCount_int));
        sold_totalWeight_textview .setText(String.valueOf(df.format(total_Soldweight_double)));

        if(unSoldfemaleWithBabyCount_int>0){
            unSoldFemaleWithBaby_Layout.setVisibility(View.VISIBLE);
        }
        else{
            unSoldFemaleWithBaby_Layout.setVisibility(View.GONE);
        }


        total_unsolditem_textview.setText(String.valueOf(unSoldtotalCount_int));
        unsold_maleCount_textview.setText(String.valueOf(unSoldmaleCount_int));
        unsold_femaleCount_textview.setText(String.valueOf(unSoldfemaleCount_int));
        unsold_femaleWithBabyCount_textview.setText(String.valueOf(unSoldfemaleWithBabyCount_int));
        unSoldtotalWeight_textview .setText(String.valueOf(df.format(total_UnSoldweight_double)));


        showProgressBar(false);

    }



    public void getCalculationValueFromSharedPreference() {
        showProgressBar(true);
        SharedPreferences sharedPreferences_forAdd  = getSharedPreferences(Constants.earTagCalculationDeta_DeliveryCenter,MODE_PRIVATE);

        String batchNo_sharedPreference  =    sharedPreferences_forAdd.getString(
                "BatchNo", "0"
        );
        if(batchNo_sharedPreference.equals(batchno)) {
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
            totalReviewedItemWeight = sharedPreferences_forAdd.getFloat(
                    "ReviewedTotalWeight", 0
            );


            reviewed_maleCount_int = sharedPreferences_forAdd.getInt(
                    "ReviewedMaleCount", 0
            );
            reviewed_femaleCount_int = sharedPreferences_forAdd.getInt(
                    "ReviewedFemaleCount", 0
            );
            reviewed_femaleCount_int = sharedPreferences_forAdd.getInt(
                    "ReviewedFemaleCount", 0
            );
            reviewed_femaleWithbabyCount_int = sharedPreferences_forAdd.getInt(
                    "ReviewedFemaleWithBabyCount", 0
            );



            unSoldtotalCount_int = sharedPreferences_forAdd.getInt(
                    "TotalUnsoldCount", 0
            );

            unSoldmaleCount_int = sharedPreferences_forAdd.getInt(
                    "UnsoldMaleCount", 0
            );
            unSoldfemaleCount_int = sharedPreferences_forAdd.getInt(
                    "UnsoldFemaleCount", 0
            );
            unSoldfemaleWithBabyCount_int = sharedPreferences_forAdd.getInt(
                    "UnsoldFemaleWithBabyCount", 0
            );


            total_UnSoldweight_double = (double) sharedPreferences_forAdd.getFloat(
                    "TotalUnsoldWeight", 0);




            soldtotalCount_int = sharedPreferences_forAdd.getInt(
                    "TotalSoldCount", 0
            );

            soldmaleCount_int = sharedPreferences_forAdd.getInt(
                    "SoldMaleCount", 0
            );
            soldfemaleCount_int = sharedPreferences_forAdd.getInt(
                    "SoldFemaleCount", 0
            );
            soldfemaleWithBabyCount_int = sharedPreferences_forAdd.getInt(
                    "SoldFemaleWithBabyCount", 0
            );


            total_Soldweight_double = (double) sharedPreferences_forAdd.getFloat(
                    "TotalSoldWeight", 0);




            try {
              String defValue = new Gson().toJson(new HashMap<String, Double>());
              String json = sharedPreferences_forAdd.getString("EarTagWeightWithBarcodeAsKey", defValue);
              TypeToken<HashMap<String, Double>> token = new TypeToken<HashMap<String, Double>>() {
              };
              earTagWeightListWithBarcodeAsKey.putAll(new Gson().fromJson(json, token.getType()));
          }
          catch (Exception e){
              e.printStackTrace();
          }

            setDataInUI();
        }
        else{
            Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETListMethod);
        }

    }

    
    
    
    private void checkPermissionForCamera() {
        try {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {


            } else {
                ActivityCompat.requestPermissions(View_or_Edit_BatchItem_deliveryCenter.this, new
                        String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);


            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    private void loadMyFragment() {
        if(isTransactionSafe) {
            isTransactionPending=false;
        batchItemDetailsFrame.setVisibility(View.VISIBLE);
        if (mfragment != null) {

            try {

                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null);
                transaction2.replace(batchItemDetailsFrame.getId(), BatchItemDetailsDeliveryCenterScreenfragment.newInstance(getString(R.string.called_from), value_forFragment));

                transaction2.remove(mfragment).commit();



            } catch (Exception e) {
                onResume();
                loadMyFragment();
                e.printStackTrace();
            }


        }
        }else {
     /*
     If any transaction is not done because the activity is in background. We set the
     isTransactionPending variable to true so that we can pick this up when we come back to
foreground
     */
            isTransactionPending=true;
        }
    }

    public static void showProgressBar(boolean show) {

        if(show){
            loadingPanel.setVisibility(View.VISIBLE);
            loadingpanelmask.setVisibility(View.VISIBLE);

        }
        else{
            loadingPanel.setVisibility(View.GONE);
            loadingpanelmask.setVisibility(View.GONE);

        }


    }

    public  void closeFragment() {
        if(isTransactionSafe) {
            isTransactionPending=false;

        try {
            Fragment fragment = getSupportFragmentManager().findFragmentById(batchItemDetailsFrame.getId());
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();


            batchItemDetailsFrame.setVisibility(View.GONE);

            getCalculationValueFromSharedPreference();
        }
        catch(Exception e){
            onResume();
            closeFragment();
            e.printStackTrace();
        }

    }
        else {

        isTransactionPending=true;

    }
    }


    public void onPause(){
        super.onPause();
        isTransactionSafe=false;
        havetoGetCalulationDataFromSharedPref = true;
    }


    public void onPostResume(){
        super.onPostResume();
        isTransactionSafe=true;
            /* Here after the activity is restored we check if there is any transaction pending from
            the last restoration
            */
        if (isTransactionPending) {
            loadMyFragment();
        }
    }



    @Override
    protected void onRestart() {
        Log.d(Constants.TAG, "tex changed for crassh onrestart " + DateParser.getDate_and_time_newFormat());

        if (String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno()).equals("") || String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno()).toUpperCase().equals("NULL")) {
                finish();
            } else {
                super.onRestart();
            }
        }








    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Constants.TAG, "tex changed for crassh  onresume " + DateParser.getDate_and_time_newFormat());

        if(havetoGetCalulationDataFromSharedPref) {
            if (!String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno()).equals("") && !String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno()).toUpperCase().equals("NULL")) {


                if(status.toUpperCase().equals(String.valueOf(Modal_B2BBatchDetailsStatic.getStatus()))){
                    getBatchDetailsfromPojoClass_or_FetchData();
                }
                else{
                    Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETListMethod);


                }





            } else {

                onBackPressed();
            }
        }
        else {
            if (batchno.equals("") || batchno.toUpperCase().equals("NULL")) {
                if (!String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno()).equals("") && !String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno()).toUpperCase().equals("NULL")) {

                    Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETListMethod);
                } else {
                    onBackPressed();
                }

                Log.d(Constants.TAG, "tex changed for crassh  onresume else part" + DateParser.getDate_and_time_newFormat());

            }
            else{
                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETListMethod);
            }
        }

    }


    @Override
    public void onBackPressed() {
        if(batchItemDetailsFrame.getVisibility() == View.VISIBLE){
            closeFragment();
            batchItemDetailsFrame.setVisibility(View.GONE);
            return;
        }
        //Intent intent = new Intent(View_or_Edit_BatchItem_deliveryCenter.this, DeliveryCenterDashboardScreen.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(intent);
        finish();
        overridePendingTransition(0,0);

    }





}