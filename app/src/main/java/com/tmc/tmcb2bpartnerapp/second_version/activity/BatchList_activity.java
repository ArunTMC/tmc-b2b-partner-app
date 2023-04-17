package com.tmc.tmcb2bpartnerapp.second_version.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.adapter.Adapter_B2BBatchItemsList;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;

import java.util.ArrayList;

public class BatchList_activity extends BaseActivity {


    //widgets
    ListView batches_listview;
    LinearLayout loadingpanelmask,loadingPanel,back_IconLayout;
    TextView toolbar_textview;


    //String
    public String deliveryCenterKey="";
    public String deliveryCenterName ="";
    public String supplierkey="";


    //ArrayList
    public static ArrayList<Modal_B2BBatchDetails> batchDetailsArrayList = new ArrayList<>();


    //boolean
    boolean  isBatchDetailsTableServiceCalled = false;




    //interface
    B2BBatchDetailsInterface callback_B2BBatchDetailsInterface = null;


    //general

    public static Adapter_B2BBatchItemsList adapter_b2BBatchItemsList = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_list);




        batches_listview = findViewById(R.id.batches_listview);
        loadingPanel = findViewById(R.id.loadingPanel);
        loadingpanelmask = findViewById(R.id.loadingpanelmask);
        toolbar_textview = findViewById(R.id.toolBarHeader_TextView);
        back_IconLayout = findViewById(R.id.back_IconLayout);

        SharedPreferences sh = getSharedPreferences("DeliveryCenterData",MODE_PRIVATE);
        deliveryCenterKey = sh.getString("DeliveryCenterKey","");
        deliveryCenterName = sh.getString("DeliveryCenterName","");
        toolbar_textview.setText(String.valueOf(deliveryCenterName));


        Intitalize_And_Execute_B2BBatchDetails("testsupplier_1");



        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        loadingpanelmask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BatchList_activity.this, "Please wait until the loading gets finished", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void Intitalize_And_Execute_B2BBatchDetails(String selectedSupplierKey) {

        showProgressBar(true);
        if (isBatchDetailsTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isBatchDetailsTableServiceCalled = true;
        batchDetailsArrayList.clear();

        callback_B2BBatchDetailsInterface = new B2BBatchDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BBatchDetails> batchDetailsArrayListt) {
                if(batchDetailsArrayListt.size()==0){
                    batches_listview.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), Constants.there_is_noData_volley, Toast.LENGTH_SHORT).show();

                }
                else {
                    batches_listview.setVisibility(View.VISIBLE);
                    batchDetailsArrayList  = batchDetailsArrayListt;
                    setAdapter();

                }
                setAdapter();
                showProgressBar(false);
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
                setAdapter();
                isBatchDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                showProgressBar(false);
                setAdapter();
                isBatchDetailsTableServiceCalled = false;
            }




        };

        String fromDate = DateParser.getDateTextFor_OldDays(30 );
        String toDate = DateParser.getDate_newFormat();
        fromDate = fromDate + Constants.defaultStartTime;
        toDate = toDate + Constants.defaultEndTime;

        String ApiToCall = "";

        ApiToCall =  API_Manager.getBatchDetailsWithDeliveryCenterAndStatusFromToDate +"?deliverycentrekey="+ deliveryCenterKey + "&fromdate="+fromDate+"&todate="+toDate ;


        B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface,  ApiToCall , Constants.CallGETListMethod);
        asyncTask.execute();







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


    private void setAdapter() {

        adapter_b2BBatchItemsList = new Adapter_B2BBatchItemsList(BatchList_activity.this, batchDetailsArrayList, BatchList_activity.this);
        batches_listview.setAdapter(adapter_b2BBatchItemsList);

    }






}