package com.tmc.tmcb2bpartnerapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.adapter.Adapter_UnstockedItemsList;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagDetailsInterface;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BBatchDetailsStatic;
import com.tmc.tmcb2bpartnerapp.model.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;

import java.util.ArrayList;

public class UnStockedBatchEarTagItemList extends BaseActivity {
    TextView toolBarHeader_TextView,batchid_textview,supplierName_textview,itemCount_textview,itemunscannedCount_textview, sentDate_textview;
    ImageView backButton_icon;
    LinearLayout loadingPanel , loadingpanelmask,back_IconLayout;
    GoatEarTagDetailsInterface callback_GoatEarTagDetails = null;
    B2BBatchDetailsInterface callback_B2BBatchDetailsInterface = null;
    ListView earTagItems_listview;
    public static Adapter_UnstockedItemsList adapter_unstockedItemsList = null;


    boolean  isBatchDetailsTableServiceCalled = false;
    boolean isGoatEarTagDetailsTableServiceCalled = false;
    String supplierKey ="", supplierName ="",userType ="" , sentdate= "",loadedweightInGrams ="",itemCount ="";
    String activityCalledFrom = "", deliveryCenterKey ="", batchno = "" , deliveryCenterName ="";

    public static ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_stocked_batch_item);

        backButton_icon = findViewById(R.id.backButton_icon);
        batchid_textview = findViewById(R.id.batchid_textview);
        supplierName_textview = findViewById(R.id.supplierName_textview);
        loadingpanelmask =  findViewById(R.id.loadingpanelmask);
        loadingPanel =  findViewById(R.id.loadingPanel);
        itemCount_textview =  findViewById(R.id.itemCount_textview);
        itemunscannedCount_textview  =  findViewById(R.id.itemunscannedCount_textview);
        sentDate_textview =  findViewById(R.id.sentDate_textview);
        earTagItems_listview =  findViewById(R.id.earTagItems_listview);
        back_IconLayout =  findViewById(R.id.back_IconLayout);


        Intent intent = getIntent();
        batchno = intent.getStringExtra(String.valueOf("batchno"));
        supplierKey = intent.getStringExtra(String.valueOf("supplierkey"));






        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



    }

    @Override
    protected void onRestart() {

        SharedPreferences sh = getSharedPreferences("LoginData", MODE_PRIVATE);
        userType = sh.getString("UserType", "");
        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);

        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");

        if(batchno.equals("") || supplierKey .equals("") || deliveryCenterKey .equals("")) {
            Intent i = new Intent(this, DeliveryCenterDashboardScreen.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();

        }
        else{
            if (Modal_B2BBatchDetailsStatic.getBatchno().equals("") || Modal_B2BBatchDetailsStatic.getSupplierkey().equals("")) {
                Intent i = new Intent(this, DeliveryCenterDashboardScreen.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            } else {
                super.onRestart();
                getBatchDetailsfromPojoClass_or_NavigateBack();

            }
        }

    }


    @Override
    protected void onResume() {


        SharedPreferences sh = getSharedPreferences("LoginData", MODE_PRIVATE);
        userType = sh.getString("UserType", "");
        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);

        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");


        if(batchno.equals("") || supplierKey .equals("") || deliveryCenterKey .equals("")) {
            Intent i = new Intent(this, DeliveryCenterDashboardScreen.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();

        }
        else{
            if (Modal_B2BBatchDetailsStatic.getBatchno().equals("") || Modal_B2BBatchDetailsStatic.getSupplierkey().equals("")) {
                Intent i = new Intent(this, DeliveryCenterDashboardScreen.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            } else {
                super.onResume();
                getBatchDetailsfromPojoClass_or_NavigateBack();

            }
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

                if(result.equals(Constants.item_Already_Added_volley)){
                    AlertDialogClass.showDialog(UnStockedBatchEarTagItemList.this, R.string.BatchDetailsAlreadyCreated_Instruction);

                }
                else if(result.equals(Constants.successResult_volley)){

                    batchno = Modal_B2BBatchDetailsStatic.getBatchno().toString();
                    supplierKey = Modal_B2BBatchDetailsStatic.getSupplierkey().toString();
                    supplierName = Modal_B2BBatchDetailsStatic.getSuppliername().toString();
                    sentdate = Modal_B2BBatchDetailsStatic.getSentdate().toString();
                    loadedweightInGrams  = Modal_B2BBatchDetailsStatic.getLoadedweightingrams().toString();
                    itemCount = Modal_B2BBatchDetailsStatic.getItemcount().toString();

                    UpdateUI();

                }
                else{
                    Toast.makeText(UnStockedBatchEarTagItemList.this, Constants.unknown_API_Result_volley, Toast.LENGTH_SHORT).show();
                }





                showProgressBar(false);
                isBatchDetailsTableServiceCalled = false;

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
                Toast.makeText(UnStockedBatchEarTagItemList.this, Constants.volleyError_Result_volley, Toast.LENGTH_SHORT).show();

                isBatchDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                showProgressBar(false);
                Toast.makeText(UnStockedBatchEarTagItemList.this, Constants.processingErrorResult_volley, Toast.LENGTH_SHORT).show();

                isBatchDetailsTableServiceCalled = false;
            }




        };
        String addApiToCall = API_Manager.getBatchDetailsWithSupplierkeyBatchNo+"?supplierkey="+ supplierKey+"&batchno="+batchId ;

        B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface, addApiToCall, Constants.CallGETMethod);
        asyncTask.execute();


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

                //showProgressBar(false);

            }

            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatchFromDB) {
                try{
                    if(earTagItemsForBatchFromDB.size()>0) {
                        earTagItemsForBatch = earTagItemsForBatchFromDB;

                        isGoatEarTagDetailsTableServiceCalled = false;

                        itemunscannedCount_textview.setText(String.valueOf(earTagItemsForBatch.size()));

                    }
                    else{
                        showProgressBar(false);
                        isGoatEarTagDetailsTableServiceCalled = false;
                        Toast.makeText(UnStockedBatchEarTagItemList.this, Constants.there_is_noData_volley, Toast.LENGTH_SHORT).show();


                    }
                    setAdapter();
                }
                catch (Exception e){
                    setAdapter();
                    showProgressBar(false);
                    isGoatEarTagDetailsTableServiceCalled = false;

                    Toast.makeText(UnStockedBatchEarTagItemList.this, "There is an error while generate report", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }



            }



            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
                Toast.makeText(UnStockedBatchEarTagItemList.this, "There is an volley error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                setAdapter();
                isGoatEarTagDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                Toast.makeText(UnStockedBatchEarTagItemList.this, "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                setAdapter();
                showProgressBar(false);
                isGoatEarTagDetailsTableServiceCalled = false;


            }




        };


        String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithStatus +"?batchno="+batchno +"&status="+Constants.goatEarTagStatus_Loading;
        GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails,  addApiToCall , callMethod);
        asyncTask.execute();





    }

    private void setAdapter() {
         adapter_unstockedItemsList = new Adapter_UnstockedItemsList(UnStockedBatchEarTagItemList.this,earTagItemsForBatch, UnStockedBatchEarTagItemList.this);
        earTagItems_listview.setAdapter(adapter_unstockedItemsList);
        showProgressBar(false);
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

            UpdateUI();
        }





    }


    private void UpdateUI() {

        batchid_textview.setText(batchno);
        supplierName_textview.setText(supplierName);
        itemCount_textview.setText(itemCount);
     //   itemunscannedCount_textview.setText(itemCount);
        sentDate_textview.setText(sentdate);

        Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETListMethod);
    }






    public   void showProgressBar(boolean show) {

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
        super.onBackPressed();
    }
}