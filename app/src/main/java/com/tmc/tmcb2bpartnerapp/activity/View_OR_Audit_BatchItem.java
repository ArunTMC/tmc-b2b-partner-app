package com.tmc.tmcb2bpartnerapp.activity;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.fragment.BatchItemDetailsDeliveryCenterScreenfragment;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchDetailsInterface;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BBatchDetailsStatic;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BBatchDetailsUpdate;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;

import java.util.ArrayList;

import static com.tmc.tmcb2bpartnerapp.utils.Constants.CallUPDATEMethod;

public class View_OR_Audit_BatchItem extends BaseActivity {
    TextView toolBarHeader_TextView,batchid_textview,supplierName_textview,itemCount_textview,itemWeight_textview, sentDate_textview;
    Button closeBatch_Button,view_unStockedBatch_item,view_StockedBatch_item,stockBatchItem_Button;
    Fragment mfragment;
    FrameLayout batchItemDetailsFrame;
    ImageView backButton_icon;
    static LinearLayout loadingPanel , loadingpanelmask;
    LinearLayout back_IconLayout;



    B2BBatchDetailsInterface callback_B2BBatchDetailsInterface = null;
    boolean  isBatchDetailsTableServiceCalled = false;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    String supplierKey ="", supplierName ="",userType ="" ,usermobileno ="", sentdate= "",loadedweightInGrams ="",itemCount ="";
    String activityCalledFrom = "", deliveryCenterKey ="", batchno = "" , deliveryCenterName ="",status ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__or__audit__batch_item);


        closeBatch_Button = findViewById(R.id.closeBatch_Button);
        view_unStockedBatch_item = findViewById(R.id.view_unStockedBatch_item);
        toolBarHeader_TextView = findViewById(R.id.toolBarHeader_TextView);
        view_StockedBatch_item = findViewById(R.id.view_StockedBatch_item);
        batchItemDetailsFrame  = findViewById(R.id.batchItemDetailsFrame);
        stockBatchItem_Button = findViewById(R.id.stockBatchItem_Button);
        backButton_icon = findViewById(R.id.backButton_icon);
        batchid_textview = findViewById(R.id.batchid_textview);
        supplierName_textview = findViewById(R.id.supplierName_textview);
        loadingpanelmask =  findViewById(R.id.loadingpanelmask);
        loadingPanel =  findViewById(R.id.loadingPanel);
        itemCount_textview =  findViewById(R.id.itemCount_textview);
        itemWeight_textview  =  findViewById(R.id.itemWeight_textview);
        sentDate_textview =  findViewById(R.id.sentDate_textview);
        back_IconLayout  =  findViewById(R.id.back_IconLayout);


        SharedPreferences sh = getSharedPreferences("LoginData", MODE_PRIVATE);
        userType = sh.getString("UserType", "");
        usermobileno = sh.getString("UserMobileNumber","");



        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);

        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");

        Intent intent = getIntent();
        activityCalledFrom = intent.getStringExtra(String.valueOf(getString(R.string.called_from)));
        batchno = intent.getStringExtra(String.valueOf("batchno"));
        supplierKey = intent.getStringExtra(String.valueOf("supplierkey"));


        checkPermissionForCamera();

        getBatchDetailsfromPojoClass_or_NavigateBack();


        stockBatchItem_Button.setOnClickListener(view -> {
            try {
                mfragment = new BatchItemDetailsDeliveryCenterScreenfragment();
                loadMyFragment(mfragment, getString(R.string.stock_batch_item));


                showProgressBar(false);

            } catch (WindowManager.BadTokenException e) {
                showProgressBar(false);

                e.printStackTrace();
            }
        });


        view_StockedBatch_item.setOnClickListener(view -> {
            try {
                mfragment = new BatchItemDetailsDeliveryCenterScreenfragment();
                loadMyFragment(mfragment, getString(R.string.view_stockedBatch_item));


                showProgressBar(false);

            } catch (WindowManager.BadTokenException e) {
                showProgressBar(false);

                e.printStackTrace();
            }
        });


        view_unStockedBatch_item.setOnClickListener(view -> {
            try {
                Intent intent1 = new Intent(this, UnStockedBatchEarTagItemList.class);
                intent1.putExtra("batchno", batchno );
                intent1.putExtra("supplierkey", supplierKey );
                intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent1);

            } catch (Exception e) {

                e.printStackTrace();
            }
        });
        if (Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Reviewed_and_READYFORSALE)) {
            closeBatch_Button.setText(getString(R.string.Generate_Report));
        }
        else{
            closeBatch_Button.setText(getString(R.string.Generate_Report_FinishBatch));
        }

        closeBatch_Button.setOnClickListener(view -> {

            try {
                Intent intent1 = new Intent(View_OR_Audit_BatchItem.this,FinishBatch_ConsolidatedReport.class);
                intent1.putExtra("batchno", batchno) ;
                intent1.putExtra("deliveryCenterKey", deliveryCenterKey) ;
                intent1.putExtra("supplierkey", supplierKey) ;
                intent1.putExtra("suppliername", supplierName) ;
                intent1.putExtra(getString(R.string.called_from), getString(R.string.delivery_center)) ;
                startActivity(intent1);
                showProgressBar(false);

            } catch (WindowManager.BadTokenException e) {
                showProgressBar(false);

                e.printStackTrace();
            }

        });


        back_IconLayout.setOnClickListener(view -> {
            onBackPressed();
        });



    }

    private void getBatchDetailsfromPojoClass_or_NavigateBack() {

        if(Modal_B2BBatchDetailsStatic.getBatchno().equals("")){
            Intialize_And_Process_BatchDetails(batchno, Constants.CallGETMethod);
        }
        else{
            batchno = Modal_B2BBatchDetailsStatic.getBatchno().toString();
            supplierKey = Modal_B2BBatchDetailsStatic.getSupplierkey().toString();
            supplierName = Modal_B2BBatchDetailsStatic.getSuppliername().toString();
            sentdate = Modal_B2BBatchDetailsStatic.getSentdate().toString();
            loadedweightInGrams  = Modal_B2BBatchDetailsStatic.getLoadedweightingrams().toString();
            itemCount = Modal_B2BBatchDetailsStatic.getItemcount().toString();
            status  = Modal_B2BBatchDetailsStatic.getStatus().toString();
            UpdateUI();
            if(status.toUpperCase().equals(Constants.batchDetailsStatus_Fully_Loaded)){
                Intialize_And_Process_BatchDetails(batchno, Constants.CallUPDATEMethod);

            }
        }





    }


    private void UpdateUI() {

        batchid_textview.setText(batchno);
        supplierName_textview.setText(supplierName);
        itemCount_textview.setText(itemCount);
        itemWeight_textview.setText(loadedweightInGrams);
        sentDate_textview.setText(sentdate);

        if(status.toUpperCase().equals(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE)){
            view_StockedBatch_item.setVisibility(View.VISIBLE);
            view_unStockedBatch_item.setVisibility(View.GONE);
            stockBatchItem_Button.setVisibility(View.GONE);
            closeBatch_Button.setVisibility(View.VISIBLE);
            closeBatch_Button.setText(R.string.Generate_Report);
        }
        else{

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



                if(callMethod.equals(Constants.CallGETMethod)) {

                    if (result.equals(Constants.item_Already_Added_volley)) {
                        AlertDialogClass.showDialog(View_OR_Audit_BatchItem.this, R.string.BatchDetailsAlreadyCreated_Instruction);

                    } else if (result.equals(Constants.successResult_volley)) {

                        batchno = Modal_B2BBatchDetailsStatic.getBatchno().toString();
                        supplierKey = Modal_B2BBatchDetailsStatic.getSupplierkey().toString();
                        supplierName = Modal_B2BBatchDetailsStatic.getSuppliername().toString();
                        sentdate = Modal_B2BBatchDetailsStatic.getSentdate().toString();
                        loadedweightInGrams = Modal_B2BBatchDetailsStatic.getLoadedweightingrams().toString();
                        itemCount = Modal_B2BBatchDetailsStatic.getItemcount().toString();
                        status = Modal_B2BBatchDetailsStatic.getStatus().toString();


                        UpdateUI();

                    } else {
                        // Toast.makeText(View_OR_Audit_BatchItem.this, Constants.unknown_API_Result_volley, Toast.LENGTH_SHORT).show();
                    }

                }
                else if(callMethod.equals(Constants.CallUPDATEMethod))
                {

                }

                showProgressBar(false);
                isBatchDetailsTableServiceCalled = false;

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
                Toast.makeText(View_OR_Audit_BatchItem.this, Constants.volleyError_Result_volley, Toast.LENGTH_SHORT).show();

                isBatchDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                showProgressBar(false);
                Toast.makeText(View_OR_Audit_BatchItem.this, Constants.processingErrorResult_volley, Toast.LENGTH_SHORT).show();

                isBatchDetailsTableServiceCalled = false;
            }




        };

        if(callMethod.equals(Constants.CallGETMethod)) {


            String addApiToCall = API_Manager.getBatchDetailsWithSupplierkeyBatchNo + "?supplierkey=" + supplierKey + "&batchno=" + batchId;

            B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface, addApiToCall, Constants.CallGETMethod);
            asyncTask.execute();
        }
        else if(callMethod.equals(Constants.CallUPDATEMethod))
        {
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



    public  void closeFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(batchItemDetailsFrame.getId());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        assert fragment != null;
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();



       batchItemDetailsFrame.setVisibility(View.GONE);
    }



    private void loadMyFragment(Fragment fm, String Value) {
        batchItemDetailsFrame.setVisibility(View.VISIBLE);
        if (fm != null) {

            try {

                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null);
                transaction2 .replace(batchItemDetailsFrame.getId(),  BatchItemDetailsDeliveryCenterScreenfragment.newInstance(getString(R.string.called_from),Value));

                transaction2.commit();


            } catch (Exception e) {

                e.printStackTrace();
            }


        }
    }




    private void checkPermissionForCamera() {
        try {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {


            } else {
                ActivityCompat.requestPermissions(View_OR_Audit_BatchItem.this, new
                        String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);


            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        if(batchItemDetailsFrame.getVisibility() == View.VISIBLE){
            batchItemDetailsFrame.setVisibility(View.GONE);
            return;
        }
        //Intent intent = new Intent(View_OR_Audit_BatchItem.this, DeliveryCenterDashboardScreen.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(intent);
        finish();
        overridePendingTransition(0,0);

    }
}