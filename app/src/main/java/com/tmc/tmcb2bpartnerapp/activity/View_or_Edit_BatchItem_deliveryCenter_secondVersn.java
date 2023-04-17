package com.tmc.tmcb2bpartnerapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetailsStatic;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetailsUpdate;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.second_version.activity.BatchList_activity;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.Constants;

import java.util.ArrayList;

public class View_or_Edit_BatchItem_deliveryCenter_secondVersn extends AppCompatActivity {

    //String
    String
    batchno = "" ,deliveryCenterKey ="";

    //int
    int
    maleCount_Sold = 0, femaleCount_Sold = 0 , maleCount_Unsold = 0 , femaleCount_Unsold = 0 , totalCount_Sold = 0 , totalCount_Unsold = 0 , totaldeadCount = 0;

    //boolean
    boolean isGoatEarTagDetailsTableServiceCalled = false;
    boolean isBatchDetailsTableServiceCalled = false;
    boolean isB2BCartOrderTableServiceCalled = false;

    //ArrayList 
    public static ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch = new ArrayList<>();



    //widgets
    LinearLayout loadingpanelmask,loadingPanel , back_IconLayout ,cartclearing_IconLayout;
    TextView batchNo_textview,total_solditem_textview,sold_maleCount_textview,sold_femaleCount_textview,total_UnSoldCount_textview,
            unSoldMaleCount_textview , unSoldFemaleCount_textview,ItemMarkedAsSold_instruction;
    Button view_soldBatch_item , view_unSoldBatch_item , markBatch_as_Sold_Button;
    
    
    //interface
    GoatEarTagDetailsInterface callback_GoatEarTagDetails = null;
    B2BBatchDetailsInterface callback_B2BBatchDetailsInterface = null;
    B2BCartOrderDetailsInterface callback_b2bOrderDetails =null ;




    static View_or_Edit_BatchItem_deliveryCenter_secondVersn view_or_edit_batchItem_deliveryCenter_secondVersn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_or__edit__batch_item_delivery_center_second_versn);


        batchNo_textview = findViewById(R.id.batchNo_textview);

        total_solditem_textview = findViewById(R.id.total_solditem_textview);
        sold_maleCount_textview = findViewById(R.id.sold_maleCount_textview);
        sold_femaleCount_textview = findViewById(R.id.sold_femaleCount_textview);
        total_UnSoldCount_textview = findViewById(R.id.total_UnSoldCount_textview);
        unSoldMaleCount_textview = findViewById(R.id.unSoldMaleCount_textview);
        unSoldFemaleCount_textview = findViewById(R.id.unSoldFemaleCount_textview);
        ItemMarkedAsSold_instruction = findViewById(R.id.ItemMarkedAsSold_instruction);


        view_unSoldBatch_item = findViewById(R.id.view_unSoldBatch_item);
        view_soldBatch_item  = findViewById(R.id.view_soldBatch_item);
        markBatch_as_Sold_Button = findViewById(R.id.markBatch_as_Sold_Button);
        back_IconLayout  = findViewById(R.id.back_IconLayout);
        cartclearing_IconLayout = findViewById(R.id.cartclearing_IconLayout);
        loadingPanel = findViewById(R.id.loadingPanel);
        loadingpanelmask = findViewById(R.id.loadingpanelmask);

        view_or_edit_batchItem_deliveryCenter_secondVersn = this;
        Intent intent = getIntent();
        batchno = intent.getStringExtra(String.valueOf("batchno"));

        batchNo_textview.setText(String.valueOf(batchno));

        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);

        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");

        if(Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Sold)){
            markBatch_as_Sold_Button.setVisibility(View.GONE);
            ItemMarkedAsSold_instruction.setVisibility(View.VISIBLE);
        }
        else{
            markBatch_as_Sold_Button.setVisibility(View.VISIBLE);
            ItemMarkedAsSold_instruction.setVisibility(View.GONE);
        }

        // Toast.makeText(this, batchno, Toast.LENGTH_SHORT).show();
        Initialize_and_ExecuteInGoatEarTagDetails (Constants.CallGETListMethod);


        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        markBatch_as_Sold_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (totaldeadCount > 0) {
                    if ((totalCount_Unsold - totaldeadCount) > 0) {
                        AlertDialogClass.showDialog(View_or_Edit_BatchItem_deliveryCenter_secondVersn.this, R.string.cant_mark_batch_as_sold_instruction);

                    } else {
                        Initialize_and_ExecuteInBatchDetails();
                    }
                }
                else{
                    if(totalCount_Unsold>0){
                        AlertDialogClass.showDialog(View_or_Edit_BatchItem_deliveryCenter_secondVersn.this, R.string.cant_mark_batch_as_sold_instruction);

                    }
                    else{
                        Initialize_and_ExecuteInBatchDetails();
                    }
                }
            }
        });


        view_unSoldBatch_item .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(View_or_Edit_BatchItem_deliveryCenter_secondVersn.this, GoatEarTagItemDetailsList.class);
                i.putExtra("TaskToDo", getString(R.string.edit_unsold_items_in_batch));
                i.putExtra("batchno", batchno);
                i.putExtra("CalledFrom", Constants.userType_DeliveryCenter);
                startActivity(i);
            }
        });


        view_soldBatch_item .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(View_or_Edit_BatchItem_deliveryCenter_secondVersn.this, GoatEarTagItemDetailsList.class);
                i.putExtra("TaskToDo", getString(R.string.view_sold_items_in_batch));
                i.putExtra("batchno", batchno);
                i.putExtra("CalledFrom", Constants.userType_DeliveryCenter);
                startActivity(i);
            }
        });






    }






    private void Initialize_and_ExecuteInBatchDetails() {
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
                    AlertDialogClass.showDialog(View_or_Edit_BatchItem_deliveryCenter_secondVersn.this, R.string.BatchDetailsAlreadyCreated_Instruction);

                }
                else if(result.equals(Constants.successResult_volley)){
                    new Modal_B2BBatchDetailsUpdate();
                    showProgressBar(false);
                    markBatch_as_Sold_Button.setVisibility(View.GONE);
                    ItemMarkedAsSold_instruction.setVisibility(View.VISIBLE);


                    try{

                                for(int i =0 ; i< BatchList_activity.batchDetailsArrayList.size(); i++){
                                    if(batchno.toUpperCase().equals(BatchList_activity.batchDetailsArrayList.get(i).getBatchno())){
                                        BatchList_activity.batchDetailsArrayList.get(i).setStatus(Constants.batchDetailsStatus_Sold);
                                        BatchList_activity.adapter_b2BBatchItemsList.notifyDataSetChanged();
                                    }
                                }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                }
                else{
                    Toast.makeText(View_or_Edit_BatchItem_deliveryCenter_secondVersn.this, Constants.unknown_API_Result_volley, Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
                Toast.makeText(View_or_Edit_BatchItem_deliveryCenter_secondVersn.this, Constants.volleyError_Result_volley, Toast.LENGTH_SHORT).show();

                isBatchDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                showProgressBar(false);
                Toast.makeText(View_or_Edit_BatchItem_deliveryCenter_secondVersn.this, Constants.processingErrorResult_volley, Toast.LENGTH_SHORT).show();

                isBatchDetailsTableServiceCalled = false;
            }


        };


        Modal_B2BBatchDetailsUpdate modal_b2BBatchDetailsUpdate = new Modal_B2BBatchDetailsUpdate();
        modal_b2BBatchDetailsUpdate.setBatchno(batchno);
        modal_b2BBatchDetailsUpdate.setSupplierkey(Modal_B2BBatchDetailsStatic.getSupplierkey());
        modal_b2BBatchDetailsUpdate.setStatus(Constants.batchDetailsStatus_Sold);
        modal_b2BBatchDetailsUpdate.setDeliverycenterkey(deliveryCenterKey);



        String addApiToCall = API_Manager.updateBatchDetailsWithSupplierkeyBatchNo;

        B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface, addApiToCall, Constants.CallUPDATEMethod);
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
                isGoatEarTagDetailsTableServiceCalled = false;

                if (result.equals(Constants.emptyResult_volley)) {
                    try {
                        AlertDialogClass.showDialog(View_or_Edit_BatchItem_deliveryCenter_secondVersn.this, R.string.EarTagDetailsNotFound_Instruction);

                    } catch (Exception e) {
                        Toast.makeText(View_or_Edit_BatchItem_deliveryCenter_secondVersn.this, getString(R.string.EarTagDetailsNotFound_Instruction), Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    }

                } else {
                    try {


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
                            ProcessArray_And_DisplayData();

                    } else {
                        showProgressBar(false);
                        isGoatEarTagDetailsTableServiceCalled = false;
                        Toast.makeText(View_or_Edit_BatchItem_deliveryCenter_secondVersn.this, Constants.there_is_noData_volley, Toast.LENGTH_SHORT).show();


                    }

                } catch (Exception e) {
                    showProgressBar(false);
                    isGoatEarTagDetailsTableServiceCalled = false;

                    Toast.makeText(View_or_Edit_BatchItem_deliveryCenter_secondVersn.this, "There is an error while generate report", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }


            }


            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
                Toast.makeText(View_or_Edit_BatchItem_deliveryCenter_secondVersn.this, "There is an volley error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                isGoatEarTagDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                Toast.makeText(View_or_Edit_BatchItem_deliveryCenter_secondVersn.this, "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();

                showProgressBar(false);
                isGoatEarTagDetailsTableServiceCalled = false;


            }


        };

     if (callMethod.equals(Constants.CallGETListMethod)) {
            String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchno + batchno;
            GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
            asyncTask.execute();
        }


    }

    public void ProcessArray_And_DisplayData() {
        maleCount_Sold = 0; femaleCount_Sold = 0;  totalCount_Sold = 0 ;totaldeadCount = 0;
        maleCount_Unsold = 0 ; femaleCount_Unsold = 0 ; totalCount_Unsold = 0;

        for(int i =0 ; i<earTagItemsForBatch.size(); i ++ ){
            Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagItemsForBatch.get(i);
            if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE)){
                totalCount_Unsold = totalCount_Unsold +1 ;
                if(modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))){
                    maleCount_Unsold = maleCount_Unsold + 1 ;
                }
                if(modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))){
                    femaleCount_Unsold = femaleCount_Unsold + 1 ;
                }


            }
            else  if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Sold)){
                totalCount_Sold  = totalCount_Sold + 1 ;
                if(modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))){
                    maleCount_Sold   = maleCount_Sold + 1 ;
                }
                if(modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))){
                    femaleCount_Sold = femaleCount_Sold + 1;
                }



            }
            else  if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatsick)){
                totalCount_Unsold = totalCount_Unsold +1 ;
                if(modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))){
                    maleCount_Unsold = maleCount_Unsold + 1 ;
                }
                if(modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))){
                    femaleCount_Unsold = femaleCount_Unsold + 1 ;
                }


            }
            else  if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatdead)){
                totaldeadCount = totaldeadCount +1 ;
                totalCount_Unsold = totalCount_Unsold +1 ;
                if(modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))){
                    maleCount_Unsold = maleCount_Unsold + 1 ;
                }
                if(modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))){
                    femaleCount_Unsold = femaleCount_Unsold + 1 ;
                }

            }



            if(i == (earTagItemsForBatch.size()-1)){
                total_solditem_textview.setText(String.valueOf(totalCount_Sold));
                total_UnSoldCount_textview.setText(String.valueOf(totalCount_Unsold));
                sold_maleCount_textview.setText(String.valueOf(maleCount_Sold));
                sold_femaleCount_textview.setText(String.valueOf(femaleCount_Sold));
                unSoldMaleCount_textview.setText(String.valueOf(maleCount_Unsold));
                unSoldFemaleCount_textview.setText(String.valueOf(femaleCount_Unsold));

                showProgressBar(false);
            }
        }



    }



    public  void showProgressBar(boolean show) {

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