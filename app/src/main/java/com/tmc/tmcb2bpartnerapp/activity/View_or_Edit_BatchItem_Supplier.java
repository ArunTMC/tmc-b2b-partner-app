package com.tmc.tmcb2bpartnerapp.activity;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.fragment.BatchItemDetailsScreenFragment;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.BarcodeScannerInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetailsStatic;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetailsUpdate;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BarcodeScannerScreen;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.TMCAlertDialogClass;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.tmc.tmcb2bpartnerapp.utils.Constants.CallADDMethod;
import static com.tmc.tmcb2bpartnerapp.utils.Constants.CallGETMethod;
import static com.tmc.tmcb2bpartnerapp.utils.Constants.CallUPDATEMethod;

public class View_or_Edit_BatchItem_Supplier extends BaseActivity {
    FrameLayout batchItemDetailsFrame;
    ImageView backButton_icon;
    static LinearLayout loadingPanel , loadingpanelmask ,back_IconLayout ,deletebatchLayout,femaleWithBaby_Layout;
    TextView toolBarHeader_TextView,totalCount_textview,maleCount_textview,femaleCount_textview,batchNo_textview,
            female_with_babyCount_textview,totalWeight_textview,minimum_weight_textview,maximum_weight_textview,average_weight_textview;
    Fragment mfragment;
    Button addNewItem_ButtonExistingBatch,editOldItem_ButtonExistingBatch,finishBatch_Button;

    GoatEarTagDetailsInterface callback_GoatEarTagDetails = null;
    B2BBatchDetailsInterface callback_B2BBatchDetailsInterface = null;
    private static final int REQUEST_CAMERA_PERMISSION = 201;

    DecimalFormat df = new DecimalFormat(Constants.threeDecimalPattern);


    double total_loadedweight_double =0 , minimum_weight_double =0, average_weight_double=0 , maximum_weight_double =0;
    int maleCount_int =0 , femaleCount_int =0, femaleWithBabyCount_int =0 , totalCount_int =0;

   public static String activityCalledFrom = "", selectDeliveryCenterKey ="", batchno = "" , selectDeliveryCenterName ="" , value_forFragment="",
            supplierKey ="", supplierName ="",userType ="",calculationValueUpdatedTimeInSharePref ="";
    static  public String scannedBarcode ="";
    boolean iscalledFromCreateNewbatch =false;
    boolean  isBatchDetailsTableServiceCalled = false;
    boolean isGoatEarTagDetailsTableServiceCalled  = false;
    //Boolean variable to mark if the transaction is safe
    private boolean isTransactionSafe;

    //Boolean variable to mark if there is any transaction pending
    private boolean isTransactionPending;
    public static ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch = new ArrayList<>();
    public static BarcodeScannerInterface barcodeScannerInterface ;
    boolean isBarcodeScannerServiceCalled = false;
    boolean isMobileDevice = false;
    Dialog show_scan_barcode_dialog = null;
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
                isMobileDevice = true;
                setContentView(R.layout.activity_view_or__edit__batch_item__supplier);
            } else {
                isMobileDevice = false;
                // Inflate the layout for this fragment
                setContentView(R.layout.pos_activity_view_or__edit__batch_item__supplier);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            isMobileDevice = false;
            setContentView(R.layout.activity_view_or__edit__batch_item__supplier);

        }


        toolBarHeader_TextView = findViewById(R.id.toolBarHeader_TextView);
        batchItemDetailsFrame  = findViewById(R.id.batchItemDetailsFrame);
        backButton_icon = findViewById(R.id.backButton_icon);
        loadingpanelmask =  findViewById(R.id.loadingpanelmask);
        loadingPanel =  findViewById(R.id.loadingPanel);
        back_IconLayout =  findViewById(R.id.back_IconLayout);
        deletebatchLayout =  findViewById(R.id.deletebatchLayout);
        addNewItem_ButtonExistingBatch =  findViewById(R.id.addNewItem_ButtonExistingBatch);
        editOldItem_ButtonExistingBatch =  findViewById(R.id.editOldItem_ButtonExistingBatch);
        finishBatch_Button =  findViewById(R.id.finishBatch_Button);
        totalCount_textview =  findViewById(R.id.totalCount_textview);
        maleCount_textview =  findViewById(R.id.maleCount_textview);
        femaleCount_textview = findViewById(R.id.femaleCount_textview);
        female_with_babyCount_textview = findViewById(R.id.female_with_babyCount_textview);
        totalWeight_textview = findViewById(R.id.totalWeight_textview);
        minimum_weight_textview = findViewById(R.id.minimum_weight_textview);
        maximum_weight_textview = findViewById(R.id.maximum_weight_textview);
        average_weight_textview = findViewById(R.id.average_weight_textview);
        batchNo_textview  = findViewById(R.id.batchNo_textview);
        femaleWithBaby_Layout = findViewById(R.id.femaleWithBaby_Layout);

        iscalledFromCreateNewbatch = false;

        SharedPreferences sh= getSharedPreferences("LoginData", MODE_PRIVATE);
        userType = sh.getString("UserType", "");
        SharedPreferences sh1 = getSharedPreferences("SupplierData",MODE_PRIVATE);
        supplierKey = sh1.getString("SupplierKey","");
        supplierName = sh1.getString("SupplierName","");




        Intent intent = getIntent();
        activityCalledFrom = intent.getStringExtra(String.valueOf(getString(R.string.called_from)));
        iscalledFromCreateNewbatch = intent.getBooleanExtra("iscalledFromCreateNewBatch",false);



        checkPermissionForCamera();


        if(!Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Loading)){

            deletebatchLayout.setVisibility(View.GONE);
            addNewItem_ButtonExistingBatch.setVisibility(View.GONE);
            finishBatch_Button.setText(getString(R.string.Generate_Report));
        }
        else{
            addNewItem_ButtonExistingBatch.setVisibility(View.VISIBLE);
            finishBatch_Button.setText(getString(R.string.generateReport_and_finish_batch));
            if(iscalledFromCreateNewbatch){
                deletebatchLayout.setVisibility(View.GONE);
            }
            else{
                deletebatchLayout.setVisibility(View.VISIBLE);
            }

        }






        if(!String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno()).equals("") && !String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno()).toUpperCase().equals("NULL")){
            batchno = String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno());
            batchNo_textview .setText(String.valueOf(batchno));
            selectDeliveryCenterKey = String.valueOf(Modal_B2BBatchDetailsStatic.getDeliverycenterkey());
            selectDeliveryCenterName = String.valueOf(Modal_B2BBatchDetailsStatic.getDeliverycentername());

            // Toast.makeText(this, DateParser.checkForTimeDifference("",calculationValueUpdatedTimeInSharePref), Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences_forAdd  = getSharedPreferences(Constants.earTagCalculationDeta_SupplierCenter,MODE_PRIVATE);
            calculationValueUpdatedTimeInSharePref = sharedPreferences_forAdd.getString(
                    "UpdatedTime", ""
            );
            String  timeDifference = DateParser.checkForTimeDifference("",calculationValueUpdatedTimeInSharePref);
           // if(timeDifference.toLowerCase().contains("minutes")) {
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
                  //  Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETListMethod);
                    getCalculationValueFromSharedPreference();
                }
                else {
                    getCalculationValueFromSharedPreference();
                  //  Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETListMethod);

                }
            /*}
            else{
                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETListMethod);

            }

             */


        }
        else{
            onBackPressed();
        }




        if(!batchno.equals("")){
            Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETListMethod);

        }
        else{
            onBackPressed();
        }





        addNewItem_ButtonExistingBatch.setOnClickListener(view -> runOnUiThread(() -> {
            if ((!String.valueOf(Modal_B2BBatchDetailsStatic.getStatus()).toUpperCase().equals(Constants.batchDetailsStatus_Loading)) && (!String.valueOf(Modal_B2BBatchDetailsStatic.getStatus()).toUpperCase().equals(Constants.batchDetailsStatus_Fully_Loaded))) {
                Toast.makeText(this, "Can't Add new Item while status is " + Modal_B2BBatchDetailsStatic.getStatus(), Toast.LENGTH_SHORT).show();
                return;
            }
            Initialize_and_StartBarcodeScanner(getString(R.string.scannerCalled_to_FetchData));

           /* if(isMobileDevice) {

                Initialize_and_StartBarcodeScanner(getString(R.string.scannerCalled_to_FetchData));

            }
            else{
                showScanBarcodeDialog();
            }

            */
            /*
            try {
                mfragment = new BatchItemDetailsScreenFragment();
                loadMyFragment(mfragment,getString(R.string.add_new_item_existing_batch));


                showProgressBar(false);

            } catch (WindowManager.BadTokenException e) {
                showProgressBar(false);

                e.printStackTrace();

            }
             */
        }));

        editOldItem_ButtonExistingBatch.setOnClickListener(view -> runOnUiThread(() -> {

            Intent i =new Intent(View_or_Edit_BatchItem_Supplier.this,GoatEarTagItemDetailsList.class);
            i.putExtra("batchno",batchno);
            i.putExtra("CalledFrom",Constants.userType_SupplierCenter);
            if(!Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Loading) ) {
                i.putExtra("TaskToDo","Oldtem");
            }
            else{
                i.putExtra("TaskToDo","NewAddedItem");
            }
            startActivity(i);


         /*   try {
                mfragment = new BatchItemDetailsScreenFragment();
                loadMyFragment(mfragment,getString(R.string.view_edit_existing_item));


                showProgressBar(false);

            } catch (WindowManager.BadTokenException e) {
                showProgressBar(false);

                e.printStackTrace();
            }

          */
        }));

        finishBatch_Button.setOnClickListener(view -> runOnUiThread(() -> {
            try {
                Intent intent1 = new Intent(View_or_Edit_BatchItem_Supplier.this,FinishBatch_ConsolidatedReport.class);
                intent1.putExtra("batchno", batchno) ;
                intent1.putExtra("deliveryCenterKey", selectDeliveryCenterKey) ;
                intent1.putExtra("deliveryCenterName", selectDeliveryCenterName) ;
                intent1.putExtra("supplierkey", supplierKey) ;
                intent1.putExtra("suppliername", supplierName) ;
                intent1.putExtra(getString(R.string.called_from), getString(R.string.supplier)) ;
                startActivity(intent1);
                showProgressBar(false);

            } catch (WindowManager.BadTokenException e) {
                showProgressBar(false);

                e.printStackTrace();
            }
        }));

        deletebatchLayout.setOnClickListener(view -> {
            new TMCAlertDialogClass(this, R.string.app_name, R.string.DeleteBatch_Instruction,
                    R.string.Yes_Text, R.string.No_Text,
                    new TMCAlertDialogClass.AlertListener() {
                        @Override
                        public void onYes() {
                            Intialize_And_Process_BatchDetails(batchno,CallUPDATEMethod);

                        }

                        @Override
                        public void onNo() {

                        }
                    });
        });









        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




    }

    private void showScanBarcodeDialog() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {

                         show_scan_barcode_dialog = new Dialog(View_or_Edit_BatchItem_Supplier.this);
                    show_scan_barcode_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    //  dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    show_scan_barcode_dialog.setContentView(R.layout.show_scan_barcode_activity);

                    showProgressBar(false);
                    show_scan_barcode_dialog.show();
                    EditText scannedbarcode_EditText = show_scan_barcode_dialog.findViewById(R.id.scannedbarcode_EditText);
                    EditTextListener editTextListener = new EditTextListener();
                    scannedbarcode_EditText.addTextChangedListener(editTextListener);

                } catch (WindowManager.BadTokenException e) {
                    showProgressBar(false);

                    e.printStackTrace();
                }
            }
        });





    }

    public class EditTextListener implements TextWatcher {
        Timer timer = new Timer();
        final long DELAY = 1000;
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String barcodeData = (editable.toString());
            if(barcodeData.contains("\n")){
                barcodeData.replace("\n","");
            }
            Log.i("tag1 barcodeData",barcodeData);
            if(barcodeData.length() > 3) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                        if (barcodeData.length() <= 6) {

                                            if (barcodeData.length() == 4) {


                                                if (Character.isLetter(barcodeData.charAt(0)) && Character.isLetter(barcodeData.charAt(1))) {
                                                    String barcodeSubString = barcodeData.substring(2, 4);
                                                    for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                        if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                            throwAlertMsg();
                                                            //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                            //onRestart();

                                                            return;
                                                        } else {
                                                            if (iterator - (barcodeSubString.length() - 1) == 0) {
                                                                scannedBarcode = barcodeData;
                                                                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod);

                                                                return;
                                                            }
                                                        }
                                                    }

                                                } else if (Character.isLetter(barcodeData.charAt(0))) {
                                                    String barcodeSubString = barcodeData.substring(1, 4);
                                                    for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                        if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                            // Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                            //onRestart();
                                                            throwAlertMsg();
                                                            return;
                                                        } else {
                                                            if (iterator - (barcodeSubString.length() - 1) == 0) {

                                                                scannedBarcode = barcodeData;
                                                                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod);
                                                                return;
                                                            }
                                                        }
                                                    }

                                                } else {
                                                    //  Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();

                                                    //onRestart();
                                                    throwAlertMsg();
                                                }
                                            } else if (barcodeData.length() == 5) {
                                                if (Character.isLetter(barcodeData.charAt(0)) && Character.isLetter(barcodeData.charAt(1))) {
                                                    String barcodeSubString = barcodeData.substring(2, 5);
                                                    for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                        if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                            throwAlertMsg();
                                                            //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                            //onRestart();

                                                            return;
                                                        } else {
                                                            if (iterator - (barcodeSubString.length() - 1) == 0) {

                                                                scannedBarcode = barcodeData;
                                                                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod);
                                                                return;
                                                            }
                                                        }
                                                    }

                                                } else if (Character.isLetter(barcodeData.charAt(0))) {
                                                    String barcodeSubString = barcodeData.substring(1, 5);
                                                    for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                        if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                            //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                            // onRestart();
                                                            throwAlertMsg();
                                                            return;
                                                        } else {
                                                            if (iterator - (barcodeSubString.length() - 1) == 0) {
                                                                scannedBarcode = barcodeData;
                                                                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod);
                                                                return;
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    // Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();

                                                    // onRestart();
                                                    throwAlertMsg();
                                                }
                                            } else if (barcodeData.length() == 6) {
                                                if (Character.isLetter(barcodeData.charAt(0)) && Character.isLetter(barcodeData.charAt(1))) {
                                                    String barcodeSubString = barcodeData.substring(2, 6);
                                                    for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                        if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                            throwAlertMsg();
                                                            //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                            //onRestart();

                                                            return;
                                                        } else {
                                                            if (iterator - (barcodeSubString.length() - 1) == 0) {

                                                                scannedBarcode = barcodeData;
                                                                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod);
                                                                return;
                                                            }
                                                        }
                                                    }

                                                } else if (Character.isLetter(barcodeData.charAt(0))) {
                                                    String barcodeSubString = barcodeData.substring(1, 6);
                                                    for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                        if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                            //  Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                            // onRestart();
                                                            throwAlertMsg();
                                                            return;
                                                        } else {
                                                            if (iterator - (barcodeSubString.length() - 1) == 0) {
                                                                scannedBarcode = barcodeData;
                                                                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod);
                                                                return;
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                    //onRestart();
                                                    throwAlertMsg();
                                                }
                                            } else {
                                                //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                //onRestart();
                                                throwAlertMsg();
                                            }
                                        }
                                        else {
                                            throwAlertMsg();
                                        }

                                    }
                                });


                            }
                        },
                        DELAY
                );


            }
        }
    }


    private void throwAlertMsg() {

        new TMCAlertDialogClass( View_or_Edit_BatchItem_Supplier.this, R.string.app_name, R.string.ScanAgain_Instruction,
                R.string.OK_Text, R.string.Empty_Text,
                new TMCAlertDialogClass.AlertListener() {
                    @Override
                    public void onYes() {
                        //    onRestart();

                    }

                    @Override
                    public void onNo() {

                    }
                });


    }

    private void Initialize_and_StartBarcodeScanner(String processtoDOAfterScan) {
        if(isBarcodeScannerServiceCalled){
            showProgressBar(false);
            return;
        }
        isBarcodeScannerServiceCalled = false ;
        barcodeScannerInterface = new BarcodeScannerInterface() {
            @Override
            public void notifySuccess(String Barcode) {
                scannedBarcode = Barcode;
                // filterDataBasedOnBarcode(scannedBarcode);
                //barcodeNo_textView.setText(Barcode);
                // Toast.makeText(mContext, "Only Scan", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void notifySuccessAndFetchData(String Barcode) {
                showProgressBar(true);
                scannedBarcode = Barcode;
                try {
                    value_forFragment = getString(R.string.stock_batch_item_withoutScan_allowedScan);
                    mfragment = new BatchItemDetailsScreenFragment();
                    loadMyFragment();


                    showProgressBar(false);

                } catch (WindowManager.BadTokenException e) {

                    showProgressBar(false);

                    e.printStackTrace();
                }


                // Toast.makeText(mContext, "Scan And Fetch", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void notifyProcessingError(Exception error) {
                // Toast.makeText(mContext, "Error in scanning ", Toast.LENGTH_SHORT).show();


            }
        };


        Intent intent = new Intent(View_or_Edit_BatchItem_Supplier.this, BarcodeScannerScreen.class);
        intent.putExtra(getString(R.string.scanner_called_to_do), processtoDOAfterScan);
        intent.putExtra(getString(R.string.called_from), getString(R.string.view_edit_batch_item_supplier));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);



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
                isGoatEarTagDetailsTableServiceCalled =false;
                showProgressBar(true);
                try{
                    if(show_scan_barcode_dialog.isShowing()){
                        show_scan_barcode_dialog.cancel();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                if(result.equals(Constants.emptyResult_volley)){
                    try {
                        value_forFragment = getString(R.string.stock_batch_item_withoutScan_allowedScan);
                        mfragment = new BatchItemDetailsScreenFragment();
                        loadMyFragment();


                        showProgressBar(false);

                    } catch (WindowManager.BadTokenException e) {

                        showProgressBar(false);

                        e.printStackTrace();
                    }

                }
                else{
                    showProgressBar(false);
                    AlertDialogClass.showDialog(View_or_Edit_BatchItem_Supplier.this, R.string.GoatEarTagAlreadyCreated_Instruction);
                }







            }

            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatchFromDB) {
                try{
                    if(earTagItemsForBatchFromDB.size()>0) {
                        earTagItemsForBatch = earTagItemsForBatchFromDB;

                        isGoatEarTagDetailsTableServiceCalled = false;
                        ProcessArray_And_DisplayData();

                    }
                    else{
                        showProgressBar(false);
                        isGoatEarTagDetailsTableServiceCalled = false;
                        Toast.makeText(View_or_Edit_BatchItem_Supplier.this, "There is no item in this batch", Toast.LENGTH_SHORT).show();

                        //   Toast.makeText(View_or_Edit_BatchItem_Supplier.this, Constants.there_is_noData_volley, Toast.LENGTH_SHORT).show();


                    }

                }
                catch (Exception e){
                    showProgressBar(false);
                    isGoatEarTagDetailsTableServiceCalled = false;

                    Toast.makeText(View_or_Edit_BatchItem_Supplier.this, "There is an error while generate report", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }



            }



            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
                Toast.makeText(View_or_Edit_BatchItem_Supplier.this, "There is an volley error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                isGoatEarTagDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                Toast.makeText(View_or_Edit_BatchItem_Supplier.this, "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();

                showProgressBar(false);
                isGoatEarTagDetailsTableServiceCalled = false;


            }




        };

        if(callMethod.equals(Constants.CallGETListMethod)) {

            String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithVariousStatus + "?batchno=" + batchno + "&status1=" + Constants.goatEarTagStatus_EarTagLost + "&filtertype=" + Constants.api_filtertype_notequals;
            GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
            asyncTask.execute();

        }
        else if(callMethod.equals(CallGETMethod)) {
            String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithVariousStatus + "?batchno=" + batchno + "&status1=" + Constants.goatEarTagStatus_EarTagLost + "&filtertype=" + Constants.api_filtertype_notequals;
            GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
            asyncTask.execute();

        }



    }

    private void Intialize_And_Process_BatchDetails(String batchId, String callADDMethod) {
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

                if(callADDMethod.equals(CallADDMethod)){
                    if(result.equals(Constants.item_Already_Added_volley)){
                        AlertDialogClass.showDialog(View_or_Edit_BatchItem_Supplier.this, R.string.BatchDetailsAlreadyCreated_Instruction);

                    }
                    else if(result.equals(Constants.successResult_volley)){


                    }
                    else{
                        //Toast.makeText(CreateNew_Or_EditOldBatchScreen.this, Constants.unknown_API_Result_volley, Toast.LENGTH_SHORT).show();
                    }
                }
                else if(callADDMethod.equals(CallUPDATEMethod)){
                    onBackPressed();
                }



                showProgressBar(false);
                isBatchDetailsTableServiceCalled = false;

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);

                isBatchDetailsTableServiceCalled = false;
                Toast.makeText(View_or_Edit_BatchItem_Supplier.this, Constants.volleyError_Result_volley, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void notifyProcessingError(Exception error) {
                showProgressBar(false);
                Toast.makeText(View_or_Edit_BatchItem_Supplier.this, Constants.processingErrorResult_volley, Toast.LENGTH_SHORT).show();

                isBatchDetailsTableServiceCalled = false;
            }




        };
        if(callADDMethod.equals(CallUPDATEMethod)) {
            Modal_B2BBatchDetailsUpdate modal_b2BBatchDetailsUpdate = new Modal_B2BBatchDetailsUpdate();
            modal_b2BBatchDetailsUpdate.setBatchno(batchno);
            modal_b2BBatchDetailsUpdate.setSupplierkey(supplierKey);
            modal_b2BBatchDetailsUpdate.setStatus(Constants.batchDetailsStatus_Cancelled);
            modal_b2BBatchDetailsUpdate.setDeliverycenterkey(selectDeliveryCenterKey);


            String addApiToCall = API_Manager.updateBatchDetailsWithSupplierkeyBatchNo;

            B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface, addApiToCall, CallUPDATEMethod);
            asyncTask.execute();

        }






    }



    private void ProcessArray_And_DisplayData() {
        total_loadedweight_double =0 ;minimum_weight_double =0; average_weight_double=0 ; maximum_weight_double =0;
        maleCount_int =0 ; femaleCount_int =0; femaleWithBabyCount_int =0 ; totalCount_int =0;
        for(int iterator =0; iterator<earTagItemsForBatch.size(); iterator++){
            Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagItemsForBatch.get(iterator);
            String LoadedWeight ="0";
            double LoadedWeight_double = 0 ;

            try{
                totalCount_int = totalCount_int+1;
            }
            catch (Exception e){
                e.printStackTrace();
            }
            try{
                if(modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))){
                    maleCount_int = maleCount_int+1;

                }
                if(modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))){
                    femaleCount_int = femaleCount_int+1;
                }
                if(modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))){
                    femaleWithBabyCount_int = femaleWithBabyCount_int+1;

                }
            }
            catch (Exception e){
                e.printStackTrace();
            }


            try{
                //getLoadedweightingrams
                LoadedWeight = String.valueOf(modal_goatEarTagDetails.getCurrentweightingrams());
                LoadedWeight = LoadedWeight.replaceAll("[^\\d.]", "");
                LoadedWeight_double = Double.parseDouble(LoadedWeight);
                LoadedWeight_double = Double.parseDouble(df.format(LoadedWeight_double)) ;
            }
            catch (Exception e){
                e.printStackTrace();
            }

            try{
                total_loadedweight_double = total_loadedweight_double + LoadedWeight_double;
            }
            catch (Exception e){
                e.printStackTrace();

            }


            try{
                total_loadedweight_double = Double.parseDouble(df.format(total_loadedweight_double)) ;
            }
            catch (Exception e){
                e.printStackTrace();

            }

            try{

                if(minimum_weight_double == 0) {
                    minimum_weight_double = Double.parseDouble(df.format(LoadedWeight_double));

                }
                if(minimum_weight_double>LoadedWeight_double){
                    minimum_weight_double = Double.parseDouble(df.format(LoadedWeight_double));
                }


                if(LoadedWeight_double>maximum_weight_double){
                    maximum_weight_double = Double.parseDouble(df.format(LoadedWeight_double));
                }


            }
            catch (Exception e){
                e.printStackTrace();
            }

            if(iterator - (earTagItemsForBatch.size()-1 )== 0){
                average_weight_double =(total_loadedweight_double/totalCount_int);
                average_weight_double =  Double.parseDouble(df.format( average_weight_double));

                setDataInUI();
                SaveCalculationDatasInSharedPref();

            }

        }


    }

    private void setDataInUI() {

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
        totalWeight_textview.setText(String.valueOf(df.format(total_loadedweight_double)));
        minimum_weight_textview.setText(String.valueOf(df.format(minimum_weight_double)));
        maximum_weight_textview.setText(String.valueOf(df.format(maximum_weight_double)));
        average_weight_textview.setText(String.valueOf(df.format(average_weight_double)));



        showProgressBar(false);

    }





    private void getCalculationValueFromSharedPreference() {

        SharedPreferences sharedPreferences_forAdd  = getSharedPreferences(Constants.earTagCalculationDeta_SupplierCenter,MODE_PRIVATE);

        String batchNo_sharedPreference  =    sharedPreferences_forAdd.getString(
                "BatchNo", "0"
        );
        if(batchNo_sharedPreference.equals(batchno)) {

            calculationValueUpdatedTimeInSharePref = sharedPreferences_forAdd.getString(
                    "UpdatedTime", ""
            );



                    //   if(timeDifference.contains())

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


                    setDataInUI();


        }
        else{
            Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETListMethod);
        }

    }

    private void SaveCalculationDatasInSharedPref() {


        SharedPreferences sharedPreferences
                = getSharedPreferences(Constants.earTagCalculationDeta_SupplierCenter,
                MODE_PRIVATE);

        SharedPreferences.Editor myEdit
                = sharedPreferences.edit();

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
                "MinimumWeight", (float)  Double.parseDouble(df.format(minimum_weight_double))
        );


        myEdit.putFloat(
                "MaximumWeight", (float) Double.parseDouble(df.format((maximum_weight_double)))
        );


        myEdit.putFloat(
                "AverageWeight",(float)  Double.parseDouble(df.format((average_weight_double)))
        );

        myEdit.putString(
                "UpdatedTime", String.valueOf(DateParser.getDate_and_time_newFormat())
        );
        myEdit.putString(
                "BatchNo", String.valueOf(batchno)
        );



        myEdit.apply();





    }



    private void checkPermissionForCamera() {
        try {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {


            } else {
                ActivityCompat.requestPermissions(View_or_Edit_BatchItem_Supplier.this, new
                        String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);


            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    private void loadMyFragment() {
        if(isTransactionSafe) {

            batchItemDetailsFrame.setVisibility(View.VISIBLE);
        if (mfragment != null) {
            isTransactionPending=false;
            try {

                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null);
                transaction2 .replace(batchItemDetailsFrame.getId(),  BatchItemDetailsScreenFragment.newInstance(getString(R.string.called_from),value_forFragment));

                transaction2.commit();


            } catch (Exception e) {
                onResume();
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

    public  void closeFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(batchItemDetailsFrame.getId());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();



        batchItemDetailsFrame.setVisibility(View.GONE);

        getCalculationValueFromSharedPreference();
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





    @Override
    public void onBackPressed() {
        if(batchItemDetailsFrame.getVisibility() == View.VISIBLE){
            closeFragment();
            batchItemDetailsFrame.setVisibility(View.GONE);
            return;
        }

        finish();
        overridePendingTransition(0,0);

    }

    public void onPause(){
        super.onPause();
        isTransactionSafe=false;

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


/*
    @Override
    protected void onRestart() {
        if(String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno()).equals("") || String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno()).toUpperCase().equals("NULL")) {
            finish();
        }
        else{
            getCalculationValueFromSharedPreference();
            super.onRestart();
        }
    }

 */



    @Override
    protected void onResume() {

        try {
            if (String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno()).equals("")) {
                if (!activityCalledFrom.equals(String.valueOf(getString(R.string.create_new_batch)))) {

                    Log.d("Tag", "On Resume Createnew if");
                    Intent i = new Intent(this, SupplierDashboardScreen.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } else {
                    super.onResume();
                    getCalculationValueFromSharedPreference();
                }
            }
            else {
                Log.d("Tag", "On Resume Createnew else");

                super.onResume();
                SharedPreferences sh = getSharedPreferences("SupplierData", MODE_PRIVATE);
                supplierKey = sh.getString("SupplierKey", "");
                supplierName = sh.getString("SupplierName", "");

                getCalculationValueFromSharedPreference();
                SharedPreferences sh1
                        = getSharedPreferences("LoginData",
                        MODE_PRIVATE);
                userType = sh1.getString("UserType", "");

                if (!String.valueOf(Modal_B2BBatchDetailsStatic.getStatus()).equals("")) {
                    if(String.valueOf(Modal_B2BBatchDetailsStatic.getStatus()).equals(Constants.batchDetailsStatus_Loading)){
                        finishBatch_Button.setText(String.valueOf(getString(R.string.Generate_Report_FinishBatch)));
                        deletebatchLayout.setVisibility(View.VISIBLE);
                        addNewItem_ButtonExistingBatch.setVisibility(View.VISIBLE);
                    }
                    else{
                        deletebatchLayout.setVisibility(View.GONE);
                        addNewItem_ButtonExistingBatch.setVisibility(View.GONE);

                        finishBatch_Button.setText(String.valueOf(getString(R.string.Generate_Report)));
                    }
                }


            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }




}