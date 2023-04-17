package com.tmc.tmcb2bpartnerapp.second_version.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.second_version.adapter.Adapter_Viewstockbalance_batchwise_Recyclerview;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewStockBalance extends BaseActivity {

    
    String deliveryCentreKey ="" , deliveryCentreName = "" , usermobileno_string = "";


    int totalItemCount = 0 , totalBatchCount = 0 ,totalBatchItemCalled = 0, totalSoldItemCount = 0  ,totalUnsoldItemCount = 0;



    public ArrayList<Modal_GoatEarTagDetails> earTagDetailsList = new ArrayList<>();
    private  ArrayList<Modal_B2BBatchDetails> batchDetailsArrayList = new ArrayList<>();







    boolean  isBatchDetailsTableServiceCalled = false;
    boolean isGoatEarTagDetailsTableServiceCalled = false;




    B2BBatchDetailsInterface callback_B2BBatchDetailsInterface = null;
    GoatEarTagDetailsInterface callback_GoatEarTagDetails = null;


    LinearLayout back_IconLayout , loadingPanel ,loadingpanelmask;
    TextView unsold_totalCount_textview,noofbatchesnotsoldyet_Textview ,totalnoofstockinhand_Textview,noofitemsunsold_Textview,totalnoofsolditem_Textview;
    ListView list_view ;
   public static ViewStockBalance viewStockBalance;
    RecyclerView tableLayout_recyclerview;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stock_balance);

        back_IconLayout  = findViewById(R.id.back_IconLayout);
        noofbatchesnotsoldyet_Textview = findViewById(R.id.noofbatchesnotsoldyet_Textview);
        totalnoofstockinhand_Textview  = findViewById(R.id.totalnoofstockinhand_Textview);
        noofitemsunsold_Textview  = findViewById(R.id.noofitemsunsold_Textview);
        totalnoofsolditem_Textview  = findViewById(R.id.totalnoofsolditem_Textview);
        unsold_totalCount_textview = findViewById(R.id.unsold_totalCount_textview);

        tableLayout_recyclerview = findViewById(R.id.tableLayout_recyclerview);
        list_view = findViewById(R.id.list_view);
        loadingPanel  = findViewById(R.id.loadingPanel);
        loadingpanelmask = findViewById(R.id.loadingpanelmask);

        viewStockBalance = this;
        
        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);
        deliveryCentreKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCentreName= sh1.getString("DeliveryCenterName", "");


        SharedPreferences sh = getSharedPreferences("LoginData",MODE_PRIVATE);
        usermobileno_string = sh.getString("UserMobileNumber","");



        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Call_and_ExecuteB2BBatchOrderDetails(Constants.CallGETListMethod);

        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }












    private void Call_and_ExecuteB2BBatchOrderDetails(String callMethod) {


        showProgressBar(true);
        if (isBatchDetailsTableServiceCalled) {
            //  showProgressBar(false);
            return;
        }
        isBatchDetailsTableServiceCalled = true;
        batchDetailsArrayList.clear();
        earTagDetailsList.clear();
        totalBatchCount =0;totalItemCount=0;

        callback_B2BBatchDetailsInterface = new B2BBatchDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BBatchDetails> batchDetailsArrayListt) {
                if(batchDetailsArrayListt.size()==0){

                    Toast.makeText(ViewStockBalance.this, Constants.there_is_noData_volley, Toast.LENGTH_SHORT).show();

                }
                else {

                    batchDetailsArrayList  = batchDetailsArrayListt;

                    totalBatchCount = batchDetailsArrayListt.size();
                }


                Call_and_ExecuteGoatEarTagDetails(Constants.CallGETListMethod);


                //  Toast.makeText(ViewStockBalance.this, String.valueOf(batchDetailsArrayList.size()), Toast.LENGTH_SHORT).show();

                // showProgressBar(false);
                isBatchDetailsTableServiceCalled = false;
            }

            @Override
            public void notifySuccess(String result) {

                showProgressBar(false);
                isBatchDetailsTableServiceCalled = false;

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);

                isBatchDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                showProgressBar(false);

                isBatchDetailsTableServiceCalled = false;
            }




        };

        String ApiToCall = "";

        ApiToCall =  API_Manager.getBatchDetailsWithSupplierkeyDeliveryCenterAndStatus +"?deliverycentrekey="+ deliveryCentreKey + "&status1="+Constants.batchDetailsStatus_Reviewed_and_READYFORSALE  ;


        B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface,  ApiToCall , Constants.CallGETListMethod);
        asyncTask.execute();








    }

    private void Call_and_ExecuteGoatEarTagDetails(String callMethod) {

        showProgressBar(true);
        if (isGoatEarTagDetailsTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isGoatEarTagDetailsTableServiceCalled = true;
        totalBatchItemCalled = 0;


            for(int i =0 ; i< batchDetailsArrayList.size() ; i++) {
                int finalI = i;
                String batchno ="";

                try{
                    batchno = batchDetailsArrayList.get(i).getBatchno();
                }
                catch (Exception e ){
                    batchno = "";
                    e.printStackTrace();
                }

                callback_GoatEarTagDetails = new GoatEarTagDetailsInterface() {


                    @Override
                    public void notifySuccess(String result) {
                        isGoatEarTagDetailsTableServiceCalled = false;

                        if (result.equals(Constants.emptyResult_volley)) {
                            try {
                                AlertDialogClass.showDialog(ViewStockBalance.this, R.string.EarTagDetailsNotFound_Instruction);

                            } catch (Exception e) {
                                Toast.makeText(ViewStockBalance.this, getString(R.string.EarTagDetailsNotFound_Instruction), Toast.LENGTH_SHORT).show();

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
                            totalBatchItemCalled = totalBatchItemCalled +1;
                            if (earTagItemsForBatchFromDB.size() > 0) {
                                earTagDetailsList.addAll(earTagItemsForBatchFromDB);
                                totalItemCount = earTagItemsForBatchFromDB.size() + totalItemCount;

                            } else {

                                //  Toast.makeText(ViewStockBalance.this, Constants.there_is_noData_volley, Toast.LENGTH_SHORT).show();


                            }

                            if (totalBatchItemCalled - (batchDetailsArrayList.size() ) == 0){

                                isGoatEarTagDetailsTableServiceCalled = false;
                                processArrayAndCalculateValue();
                            }
                            else{
                                //   Toast.makeText(ViewStockBalance.this, "There is an error while Fetching Ear Tag ", Toast.LENGTH_SHORT).show();

                            }


                        } catch (Exception e) {
                            totalBatchItemCalled = totalBatchItemCalled +1;
                            if (totalBatchItemCalled - (batchDetailsArrayList.size() ) == 0){

                                isGoatEarTagDetailsTableServiceCalled = false;
                                processArrayAndCalculateValue();

                            }
                            Toast.makeText(ViewStockBalance.this, "There is an error while Fetching Ear Tag ", Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }


                    }


                    @Override
                    public void notifyVolleyError(VolleyError error) {
                        totalBatchItemCalled = totalBatchItemCalled +1;
                        if (totalBatchItemCalled - (batchDetailsArrayList.size() ) == 0){

                            processArrayAndCalculateValue();

                            isGoatEarTagDetailsTableServiceCalled = false;
                        }
                        Toast.makeText(ViewStockBalance.this, "There is an Volley error while Fetching Ear Tag", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void notifyProcessingError(Exception error) {
                        Toast.makeText(ViewStockBalance.this, "There is an process error while Fetching Ear Tag", Toast.LENGTH_SHORT).show();
                        totalBatchItemCalled = totalBatchItemCalled +1;
                        if (totalBatchItemCalled - (batchDetailsArrayList.size() ) == 0){

                            processArrayAndCalculateValue();

                            isGoatEarTagDetailsTableServiceCalled = false;
                        }


                    }


                };

                if (callMethod.equals(Constants.CallGETListMethod)) {
                    String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithVariousStatus + "?batchno=" + batchno + "&status1=" + Constants.goatEarTagStatus_EarTagLost + "&status2=" + Constants.goatEarTagStatus_Cancelled  + "&filtertype=" + Constants.api_filtertype_notequals;
                    GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
                    asyncTask.execute();
                }
            }

        }

    private void processArrayAndCalculateValue() {


/*
        for(int iteratorbatch = 15 ; iteratorbatch<300; iteratorbatch++){
            Modal_B2BBatchDetails modal_b2BBatchDetails = batchDetailsArrayList.get(1);
            Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagDetailsList.get(1);

            modal_b2BBatchDetails.setBatchno(String.valueOf(iteratorbatch));
            modal_goatEarTagDetails.setBatchno(String.valueOf(iteratorbatch));

            batchDetailsArrayList.add(modal_b2BBatchDetails);
            earTagDetailsList.add(modal_goatEarTagDetails);

        }

 */









        for(int iteratorBatchList = 0 ; iteratorBatchList< batchDetailsArrayList.size(); iteratorBatchList++){
            Modal_B2BBatchDetails modal_b2BBatchDetails = batchDetailsArrayList.get(iteratorBatchList);

            for(int iteratorEarTagList = 0 ; iteratorEarTagList < earTagDetailsList.size(); iteratorEarTagList++){
                Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagDetailsList.get(iteratorEarTagList);

                if(modal_goatEarTagDetails.getBatchno().toUpperCase().equals(modal_b2BBatchDetails.getBatchno().toUpperCase())){


                    try{


                        if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE ) || modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatsick )){
                            totalUnsoldItemCount = totalUnsoldItemCount +1;
                        }
                        else  if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Sold)){
                            totalSoldItemCount = totalSoldItemCount+1;
                        }
                        else  if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatdead)){
                            totalSoldItemCount = totalSoldItemCount+1;
                        }
                        else{

                        }


                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }





                }


            }

            if(iteratorBatchList  == (batchDetailsArrayList.size() - 1)){
                noofbatchesnotsoldyet_Textview.setText(String.valueOf(totalBatchCount));
                noofitemsunsold_Textview.setText(String.valueOf(totalUnsoldItemCount));
                totalnoofsolditem_Textview.setText(String.valueOf(totalSoldItemCount));
                unsold_totalCount_textview.setText(String.valueOf(totalUnsoldItemCount));
                setAdapterForListView();
            }


        }


    }

    private void setAdapterForListView() {

        batchDetailsArrayList = sortThisArrayUsingBatchno(batchDetailsArrayList);


        Adapter_Viewstockbalance_batchwise_Recyclerview adapter_viewstockbalance_batchwise_recyclerview = new Adapter_Viewstockbalance_batchwise_Recyclerview(ViewStockBalance.this,batchDetailsArrayList,ViewStockBalance.this,"Viewstockbalance",earTagDetailsList);
        tableLayout_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        tableLayout_recyclerview.setHasFixedSize(true);




        tableLayout_recyclerview.setAdapter(adapter_viewstockbalance_batchwise_recyclerview);

        ///  Adapter_Viewstockbalance_batchwise adapter_viewstockbalance_batchwise = new Adapter_Viewstockbalance_batchwise(ViewStockBalance.this,batchDetailsArrayList,ViewStockBalance.this,"Viewstockbalance",earTagDetailsList);
      //  tableLayout_listview.setAdapter(adapter_viewstockbalance_batchwise);
        showProgressBar(false);
    }




    private ArrayList<Modal_B2BBatchDetails> sortThisArrayUsingBatchno(ArrayList<Modal_B2BBatchDetails> earTagDetailsList) {


        final Pattern p = Pattern.compile("^\\d+");
        Comparator<Modal_B2BBatchDetails> c = new Comparator<Modal_B2BBatchDetails>() {
            @Override
            public int compare(Modal_B2BBatchDetails object1, Modal_B2BBatchDetails object2) {
                Matcher m = p.matcher(object1.getBatchno());
                Integer number1 = null;
                if (!m.find()) {
                    Matcher m1 = p.matcher(object2.getBatchno());
                    if (m1.find()) {
                        return object2.getBatchno().compareTo(object1.getBatchno());
                    } else {
                        return object1.getBatchno().compareTo(object2.getBatchno());
                    }
                } else {
                    Integer number2 = null;
                    number1 = Integer.parseInt(m.group());
                    m = p.matcher(object2.getBatchno());
                    if (!m.find()) {
                        // return object1.compareTo(object2);
                        Matcher m1 = p.matcher(object1.getBatchno());
                        if (m1.find()) {
                            return object2.getBatchno().compareTo(object1.getBatchno());
                        } else {
                            return object1.getBatchno().compareTo(object2.getBatchno());
                        }
                    } else {
                        number2 = Integer.parseInt(m.group());
                        int comparison = number1.compareTo(number2);
                        if (comparison != 0) {
                            return comparison;
                        } else {
                            return object1.getBatchno().compareTo(object2.getBatchno());
                        }
                    }
                }
            }
        };

        Collections.sort(earTagDetailsList, c);
        System.out.println(earTagDetailsList);




        return  earTagDetailsList;
    }


    private void showProgressBar(boolean show) {

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