package com.tmc.tmcb2bpartnerapp.second_version.activity;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderItemDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal_ConsolidatedSalesReport;
import com.tmc.tmcb2bpartnerapp.second_version.adapter.Adapter_AutoComplete_ConsolidatedSalesReport;
import com.tmc.tmcb2bpartnerapp.second_version.adapter.Adapter_ConsolidatedSalesReport_recyclerView;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsolidatedSalesReport extends BaseActivity {
    //Widgets
    LinearLayout loadingpanelmask, loadingPanel , back_IconLayout ;
    ScrollView salesValue_parentLayout ;
    TextView toolBarHeader_TextView ,spinner_label ,total_maleCount_textview , total_female_count_textview ,totalCount_textview ,discount_amount_textview,
            total_weight_textview , final_sales_textview , noOrder_inthis_Batch_instructionTextview,total_OrdersCount_textview;
    AutoCompleteTextView     batchwise_Textview;
    View  batchwise_overlapView;
    RecyclerView mRecyclerview;
    private ProgressBar loadingPB;
    private NestedScrollView nestedSV;
    Dialog batchwiseDialog = null;


    //String
    String deliveryCentreKey ="" , orderid ="" , deliveryCentreName ="" ,usermobileno_string ="";
    String FromDate = "", Todate = "" , firstTimeToDate ="" , selectedBatchNo ="";

    
    //long
    long startingfromDate_long = 0 , newFromDate_long =0;



    //int
    int no_of_days_before =0;
    int count = 0 ;
    int dateCountToIncreaseOnScroll = 7;
    int dateCountToFetchInStarting = 15;
    int noentryForrequestedDate = 1;
    
    
    
    
    //boolean
    boolean  isBatchDetailsTableServiceCalled = false;
    boolean isB2BOrderTableServiceCalled = false;
    boolean isTheBatchCretedForTheLastRequestedDate = false , isfetchMoreDateAlertShown = false;
    boolean isBatchFilterStarted = false;
    boolean isBatchSelected = false;
    private Boolean isTextInputLayoutClicked = false;



    //ArrayList
    public static ArrayList<Modal_B2BBatchDetails> batchDetailsArrayList = new ArrayList<>();
    public static ArrayList<Modal_B2BOrderDetails> orderDetailsList = new ArrayList<>();
    public static ArrayList<Modal_ConsolidatedSalesReport> consolidatedSalesReportArrayList = new ArrayList<>();

    //interface
    B2BBatchDetailsInterface callback_B2BBatchDetailsInterface = null;
    B2BOrderDetailsInterface callBack_B2BOrderDetailsInterface;
    B2BOrderItemDetailsInterface callback_b2bOrderItemDetails;



    //general
    private Adapter_ConsolidatedSalesReport_recyclerView mAdapter;
    Adapter_AutoComplete_ConsolidatedSalesReport adapter_autoComplete_consolidatedSalesReport;


    static DecimalFormat threeDecimalConverter = new DecimalFormat(Constants.threeDecimalPattern);
    static DecimalFormat twoDecimalConverter = new DecimalFormat(Constants.twoDecimalPattern);
    static DecimalFormat twoDecimalConverterWithComma = new DecimalFormat(Constants.twoDecimalWithCommaPattern);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consolidated_sales_report);

        noOrder_inthis_Batch_instructionTextview = findViewById(R.id.noOrder_inthis_Batch_instructionTextview);
        total_maleCount_textview = findViewById(R.id.total_maleCount_textview);
        final_sales_textview = findViewById(R.id.final_sales_textview);
        total_weight_textview = findViewById(R.id.total_weight_textview);
        totalCount_textview = findViewById(R.id.totalCount_textview);
        total_female_count_textview = findViewById(R.id.total_female_count_textview);
        total_OrdersCount_textview  = findViewById(R.id.total_OrdersCount_textview);
        discount_amount_textview = findViewById(R.id.discount_amount_textview);
        salesValue_parentLayout = findViewById(R.id.salesValue_parentLayout);
        back_IconLayout  = findViewById(R.id.back_IconLayout);

        batchwise_overlapView = findViewById(R.id.batchwise_overlapView);
        batchwise_Textview = findViewById(R.id.batchwise_Textview);
        spinner_label  = findViewById(R.id.spinner_label);
        mRecyclerview = findViewById(R.id.mRecyclerview);
        loadingPanel = findViewById(R.id.loadingPanel);
        loadingpanelmask = findViewById(R.id.loadingpanelmask);
        toolBarHeader_TextView = findViewById(R.id.toolBarHeader_TextView);
        loadingPB = findViewById(R.id.idPBLoading);
        nestedSV = findViewById(R.id.idNestedSV);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        
        
        
        
        
        
        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);
        deliveryCentreKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCentreName= sh1.getString("DeliveryCenterName", "");


        SharedPreferences sh = getSharedPreferences("LoginData",MODE_PRIVATE);
        usermobileno_string = sh.getString("UserMobileNumber","");


        Todate = String.valueOf(DateParser.getDate_newFormat());
        no_of_days_before = dateCountToFetchInStarting;
        firstTimeToDate  = Todate;

        FromDate = String.valueOf(DateParser.getDateTextFor_OldDays(no_of_days_before));
        batchDetailsArrayList.clear();
        startingfromDate_long = 0;


        Intitalize_And_Execute_B2BBatchDetails();



        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



      //  Intialize_and_Execute_OrderDetailsList(Constants.CallGETListMethod,FromDate , Todate,true);

        /*
        nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // on scroll change we are checking when users scroll as bottom.
                //scrollUp
                if (scrollY < oldScrollY) {
                    isfetchMoreDateAlertShown = false;
                }


                if (!isfetchMoreDateAlertShown) {
                    if(loadingPB.getVisibility()!=View.VISIBLE){
                        if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                            // in this method we are incrementing page number,
                            // making progress bar visible and calling get data method.

                            // on below line we are making our progress bar visible.
                            loadingPB.setVisibility(View.VISIBLE);
                            isfetchMoreDateAlertShown = true;

                            if (count < 20) {
                                // on below line we are again calling
                                // a method to load data in our array list.

                                new TMCAlertDialogClass(ConsolidatedSalesReport.this, R.string.app_name, R.string.do_you_need_fetch_dataFor_more_days_Instruction,
                                        R.string.Yes_Text, R.string.No_Text,
                                        new TMCAlertDialogClass.AlertListener() {
                                            @Override
                                            public void onYes() {

                                                if (isTheBatchCretedForTheLastRequestedDate) {
                                                    Todate = String.valueOf(DateParser.getDateTextFor_OldDaysfrom_given(1, FromDate));
                                                    FromDate = String.valueOf(DateParser.getDateTextFor_OldDaysfrom_given(dateCountToIncreaseOnScroll, Todate));


                                                } else {
                                                    if(noentryForrequestedDate==0){
                                                        noentryForrequestedDate = 1;
                                                    }
                                                    int dateCountToIncreaseOnScroll_temp =0;
                                                    dateCountToIncreaseOnScroll_temp = dateCountToIncreaseOnScroll*noentryForrequestedDate;
                                                    FromDate = String.valueOf(DateParser.getDateTextFor_OldDaysfrom_given(dateCountToIncreaseOnScroll_temp, Todate));
                                                }
                                                count++;

                                                Intialize_and_Execute_OrderDetailsList(Constants.CallGETListMethod, FromDate, Todate, false);


                                            }

                                            @Override
                                            public void onNo() {
                                                loadingPB.setVisibility(View.INVISIBLE);
                                                mRecyclerview.setVisibility(View.VISIBLE);
                                                isfetchMoreDateAlertShown = false;


                                            }
                                        });


                            }
                            else {
                                loadingPB.setVisibility(View.INVISIBLE);
                                Toast.makeText(ConsolidatedSalesReport.this, "You have reached the limit", Toast.LENGTH_SHORT).show();
                                isfetchMoreDateAlertShown = false;
                            }


                        }
                    }
                }
            }
        });


         */


        batchwise_Textview.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    Objects.requireNonNull(imm).hideSoftInputFromWindow(batchwise_Textview.getWindowToken(), 0);



                    if (isBatchFilterStarted) {
                        if (!isBatchSelected) {
                            batchwise_Textview.setText(String.valueOf(selectedBatchNo));
                            isBatchSelected = true;
                            batchwise_Textview.clearFocus();
                            batchwise_Textview.setThreshold(1);
                            isBatchFilterStarted = false;

                        }
                    }
                    batchwise_Textview.clearFocus();
                    batchwise_overlapView.setVisibility(View.VISIBLE);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        batchwise_overlapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = batchwise_Textview.getText().toString();
                batchwise_Textview.setText(" ");
                isBatchFilterStarted = true;
                isBatchSelected  = false;
                batchwise_overlapView.setVisibility(View.GONE);
                batchwise_Textview.requestFocus();
              /*  if(text.equals("")){

                    batchwise_Textview.setText(" ");

                }
                else{

                    batchwise_Textview.setText(text);
                }

               */
                showKeyboard(batchwise_Textview);
            }
        });



    }



    private void Intitalize_And_Execute_B2BBatchDetails() {

        showProgressBar(true);
        if (isBatchDetailsTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isBatchDetailsTableServiceCalled = true;


        callback_B2BBatchDetailsInterface = new B2BBatchDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BBatchDetails> batchDetailsArrayListt) {
                if(batchDetailsArrayListt.size()==0){
                    mRecyclerview.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), Constants.there_is_noData_volley, Toast.LENGTH_SHORT).show();

                }
                else {
                    mRecyclerview.setVisibility(View.GONE);
                    batchDetailsArrayList.addAll( batchDetailsArrayListt);
                    addAdapterforTheSearchableSpinner();
                }
                
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

        String fromDate = DateParser.getDateTextFor_OldDays(30 );
        String toDate = DateParser.getDate_newFormat();
        fromDate = fromDate + Constants.defaultStartTime;
        toDate = toDate + Constants.defaultEndTime;

        String ApiToCall = "";

        ApiToCall =  API_Manager.getBatchDetailsListWithDeliveryCenterKey+deliveryCentreKey;


        B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface,  ApiToCall , Constants.CallGETListMethod);
        asyncTask.execute();







    }

    private void addAdapterforTheSearchableSpinner() {

        
        
        
        batchDetailsArrayList = sortThisArrayUsingbatchCreatedDate(batchDetailsArrayList);
        
        
        try {
            adapter_autoComplete_consolidatedSalesReport = new Adapter_AutoComplete_ConsolidatedSalesReport(getApplicationContext(), batchDetailsArrayList, ConsolidatedSalesReport.this);
            adapter_autoComplete_consolidatedSalesReport.setHandler(newHandler());

            //receiverMobileNo_edittext.setInputType(InputType.TYPE_CLASS_PHONE);

            batchwise_Textview.setAdapter(adapter_autoComplete_consolidatedSalesReport);
            batchwise_Textview.clearFocus();
            batchwise_Textview.setThreshold(1);
            batchwise_Textview.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_CLASS_NUMBER);
        }
        catch (Exception e){
            e.printStackTrace();
        }


        try{
            if(batchDetailsArrayList.size()>0){
                mRecyclerview.setVisibility(View.GONE);
                salesValue_parentLayout.setVisibility(View.VISIBLE);
                noOrder_inthis_Batch_instructionTextview.setVisibility(View.GONE);
                batchwise_Textview.setText(String.valueOf(batchDetailsArrayList.get(0).getBatchno()));
                selectedBatchNo = String.valueOf(batchDetailsArrayList.get(0).getBatchno());
                isBatchSelected = true;
                batchwise_Textview.clearFocus();
                batchwise_Textview.setThreshold(1);
                Intitalize_And_Execute_B2BOrderDetails(selectedBatchNo);
            }
            else{
                mRecyclerview.setVisibility(View.GONE);
                salesValue_parentLayout.setVisibility(View.GONE);
                noOrder_inthis_Batch_instructionTextview.setVisibility(View.VISIBLE);
                noOrder_inthis_Batch_instructionTextview.setText("There is no Batch Created for this delivery centre");
            }
        }
        catch (Exception e ){
            e.printStackTrace();
        }
    }

    private void Intitalize_And_Execute_B2BOrderDetails(String selectedBatchNo) {
        orderDetailsList.clear();
        showProgressBar(true);
        if (isB2BOrderTableServiceCalled) {
            // showProgressBar(false);
            return;
        }
        isB2BOrderTableServiceCalled = true;
        callBack_B2BOrderDetailsInterface = new B2BOrderDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BOrderDetails> arrayList) {
                if (arrayList.size() > 0) {


                    orderDetailsList.addAll( arrayList);

                    isB2BOrderTableServiceCalled = false;
                    processOrderDetailsAndFormSalesReportArray();

                } else {
                    isB2BOrderTableServiceCalled = false;
                    processOrderDetailsAndFormSalesReportArray();
                    showProgressBar(false);
                }
            }

            @Override
            public void notifySuccess(String result) {
                showProgressBar(false);
                processOrderDetailsAndFormSalesReportArray();
                isB2BOrderTableServiceCalled = false;
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                Toast.makeText(ConsolidatedSalesReport.this, "There is an volley error while Fetching Batch Details", Toast.LENGTH_SHORT).show();
                showProgressBar(false);
                isB2BOrderTableServiceCalled = false;

            }

            @Override
            public void notifyProcessingError(Exception error) {
                Toast.makeText(ConsolidatedSalesReport.this, "There is an Process error while Fetching Batch Details", Toast.LENGTH_SHORT).show();
                showProgressBar(false);
                isB2BOrderTableServiceCalled = false;


            }


        };

            //String getApiToCall = API_Manager.getOrderDetailsForBatchno+"?batchno="+batchno ;
            String ApiToCall =  API_Manager.getOrderDetailsForDeliveryCentrekeyBatchwiseitemdespWithOutCancelledStatus +"?deliverycentrekey="+ deliveryCentreKey + "&batchno="+selectedBatchNo ;

            B2BOrderDetails asyncTask = new B2BOrderDetails(callBack_B2BOrderDetailsInterface, ApiToCall, Constants.CallGETListMethod);
            asyncTask.execute();




    }

    private void processOrderDetailsAndFormSalesReportArray() {
        consolidatedSalesReportArrayList.clear();
        double totalPrice_batch =0 , totalWeight_batch = 0 , totalMaleCount =0 , totalFemaleCount = 0 , totalQuantity = 0 , totalDiscountAmount = 0;
        if(orderDetailsList.size()>0) {
            mRecyclerview.setVisibility(View.GONE);
            salesValue_parentLayout.setVisibility(View.VISIBLE);
            noOrder_inthis_Batch_instructionTextview.setVisibility(View.GONE);
            for (int iterator = 0; iterator < orderDetailsList.size(); iterator++) {
                double discountAmount_batch_inloop = 0,  Price_batch_inloop =0 , Weight_batch_inloop = 0 , MaleCount_inloop =0 , FemaleCount_inloop = 0 , quantityinloop = 0;
                Modal_ConsolidatedSalesReport modal_consolidatedSalesReport = new Modal_ConsolidatedSalesReport();

                Modal_B2BOrderDetails modal_b2BOrderDetails = orderDetailsList.get(iterator);
                modal_b2BOrderDetails.setBatchno(selectedBatchNo);
                modal_consolidatedSalesReport.setModal_b2BOrderDetails(modal_b2BOrderDetails);
                try {
                    JSONObject batchwiseItemDespJSON = new JSONObject(modal_b2BOrderDetails.getItemDespjsonArray());


                    if(batchwiseItemDespJSON.has(selectedBatchNo)) {
                        JSONObject jsonObject = batchwiseItemDespJSON.getJSONObject(selectedBatchNo);

                        try {
                            try {
                                if (jsonObject.has("female")) {
                                    FemaleCount_inloop = jsonObject.getDouble("female");

                                } else {
                                    FemaleCount_inloop = 0;
                                }
                            } catch (Exception e) {
                                FemaleCount_inloop = 0;

                                e.printStackTrace();
                            }

                            try {
                                totalFemaleCount = totalFemaleCount + FemaleCount_inloop;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                if (jsonObject.has("male")) {
                                    MaleCount_inloop = jsonObject.getDouble("male");

                                } else {
                                    MaleCount_inloop = 0;
                                }
                            } catch (Exception e) {
                                MaleCount_inloop = 0;
                                e.printStackTrace();
                            }

                            try {
                                totalMaleCount = totalMaleCount + MaleCount_inloop;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                if (jsonObject.has("totalweight")) {
                                    Weight_batch_inloop = jsonObject.getDouble("totalweight");
                                    Weight_batch_inloop = Double.parseDouble(ConvertGramsToKilograms(String.valueOf(Weight_batch_inloop)));
                                } else {
                                    Weight_batch_inloop = 0;
                                }
                            } catch (Exception e) {
                                Weight_batch_inloop = 0;
                                e.printStackTrace();
                            }

                            try {
                                totalWeight_batch = totalWeight_batch + Weight_batch_inloop;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                if (jsonObject.has("totalprice")) {
                                    Price_batch_inloop = jsonObject.getDouble("totalprice");

                                } else {
                                    Price_batch_inloop = 0;
                                }
                            } catch (Exception e) {
                                Price_batch_inloop = 0;
                                e.printStackTrace();
                            }



                            try {
                                if (jsonObject.has("discountamount")) {
                                    discountAmount_batch_inloop = jsonObject.getDouble("discountamount");

                                } else {
                                    discountAmount_batch_inloop = 0;
                                }
                            } catch (Exception e) {
                                discountAmount_batch_inloop = 0;
                                e.printStackTrace();
                            }
                            try {
                                totalDiscountAmount = totalDiscountAmount + discountAmount_batch_inloop;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                Price_batch_inloop = Price_batch_inloop - discountAmount_batch_inloop;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                totalPrice_batch = totalPrice_batch + Price_batch_inloop;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }




                            try {
                                if (jsonObject.has("totalcount")) {
                                    quantityinloop = jsonObject.getDouble("totalcount");

                                } else {
                                    quantityinloop = 0;
                                }
                            } catch (Exception e) {
                                quantityinloop = 0;
                                e.printStackTrace();
                            }

                            try {
                                totalQuantity = totalQuantity + quantityinloop;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try{
                               // totalPrice_batch = totalPrice_batch - totalDiscountAmount;
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }

                            try {

                                totalPrice_batch = Double.parseDouble(twoDecimalConverter.format(totalPrice_batch));


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                totalWeight_batch = Double.parseDouble(threeDecimalConverter.format(totalWeight_batch));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {

                                modal_consolidatedSalesReport.setFemale((int) FemaleCount_inloop);
                                modal_consolidatedSalesReport.setMale((int) MaleCount_inloop);
                                modal_consolidatedSalesReport.setTotalCount((int) quantityinloop);
                                modal_consolidatedSalesReport.setTotalWeight(Weight_batch_inloop);
                                modal_consolidatedSalesReport.setTotalPrice(Price_batch_inloop);
                                modal_consolidatedSalesReport.setTotalWeight(Weight_batch_inloop);
                                modal_consolidatedSalesReport.setRetailername(modal_b2BOrderDetails.getRetailername());
                                modal_consolidatedSalesReport.setRetailerNo(modal_b2BOrderDetails.getRetailermobileno());
                                modal_consolidatedSalesReport.setBillno(modal_b2BOrderDetails.getBillno());
                                modal_consolidatedSalesReport.setPlacedtime(modal_b2BOrderDetails.getOrderplaceddate());
                                modal_consolidatedSalesReport.setBatchno(selectedBatchNo);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    /*
                    for (int i =0 ; i< batchwiseItemDespJSONArray.length(); i++){
                        JSONObject jsonObject = batchwiseItemDespJSONArray.getJSONObject(i);
                        try{
                            if(jsonObject.has("female")){
                                FemaleCount_inloop  = jsonObject.getDouble("female");

                            }
                            else{
                                FemaleCount_inloop = 0;
                            }
                        }
                        catch (Exception e){
                            FemaleCount_inloop = 0;

                            e.printStackTrace();
                        }

                        try{
                            totalFemaleCount = totalFemaleCount + FemaleCount_inloop;
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        try{
                            if(jsonObject.has("male")){
                                MaleCount_inloop  = jsonObject.getDouble("male");

                            }
                            else{
                                MaleCount_inloop = 0;
                            }
                        }
                        catch (Exception e){
                            MaleCount_inloop = 0;
                            e.printStackTrace();
                        }

                        try{
                            totalMaleCount = totalMaleCount + MaleCount_inloop;
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                        try{
                            if(jsonObject.has("totalweight")){
                                Weight_batch_inloop  = jsonObject.getDouble("totalweight");
                                Weight_batch_inloop = Double.parseDouble(ConvertGramsToKilograms(String.valueOf(Weight_batch_inloop)));
                            }
                            else{
                                Weight_batch_inloop = 0;
                            }
                        }
                        catch (Exception e){
                            Weight_batch_inloop = 0;
                            e.printStackTrace();
                        }

                        try{
                            totalWeight_batch = totalWeight_batch + Weight_batch_inloop ;
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                        try{
                            if(jsonObject.has("totalprice")){
                                Price_batch_inloop  = jsonObject.getDouble("totalprice");

                            }
                            else{
                                Price_batch_inloop = 0;
                            }
                        }
                        catch (Exception e){
                            Price_batch_inloop =0;
                            e.printStackTrace();
                        }
                        try{
                            totalPrice_batch = totalPrice_batch +Price_batch_inloop;
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                        try{
                            if(jsonObject.has("totalcount")){
                                quantityinloop  = jsonObject.getDouble("totalcount");

                            }
                            else{
                                quantityinloop = 0;
                            }
                        }
                        catch (Exception e){
                            quantityinloop = 0;
                            e.printStackTrace();
                        }

                        try{
                            totalQuantity = totalQuantity+ quantityinloop;
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                        try{

                            totalPrice_batch = Double.parseDouble(twoDecimalConverter.format(totalPrice_batch));


                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                        try{
                            totalWeight_batch = Double.parseDouble(threeDecimalConverter.format(totalWeight_batch));
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }



                        try{

                            modal_consolidatedSalesReport.setFemale((int) FemaleCount_inloop);
                            modal_consolidatedSalesReport.setMale((int) MaleCount_inloop);
                            modal_consolidatedSalesReport.setTotalCount((int) quantityinloop);
                            modal_consolidatedSalesReport.setTotalWeight(Weight_batch_inloop);
                            modal_consolidatedSalesReport.setTotalPrice(Price_batch_inloop);
                            modal_consolidatedSalesReport.setTotalWeight(Weight_batch_inloop);
                            modal_consolidatedSalesReport.setRetailername(modal_b2BOrderDetails.getRetailername());
                            modal_consolidatedSalesReport.setRetailerNo(modal_b2BOrderDetails.getRetailermobileno());
                            modal_consolidatedSalesReport.setBillno(modal_b2BOrderDetails.getBillno());
                            modal_consolidatedSalesReport.setPlacedtime(modal_b2BOrderDetails.getOrderplaceddate());
                            modal_consolidatedSalesReport.setBatchno(selectedBatchNo);

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }





                    }


                     */


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ;
                modal_consolidatedSalesReport.setBatchno(selectedBatchNo);
                consolidatedSalesReportArrayList.add(modal_consolidatedSalesReport);
                if (orderDetailsList.size() - 1 == iterator) {

                    try{
                        total_OrdersCount_textview.setText(String.valueOf((int) consolidatedSalesReportArrayList.size()));
                        total_maleCount_textview.setText(String.valueOf((int) totalMaleCount));
                        total_female_count_textview.setText(String.valueOf((int) totalFemaleCount));
                        totalCount_textview.setText(String.valueOf((int) totalQuantity));
                        discount_amount_textview.setText(String.valueOf(twoDecimalConverterWithComma.format(totalDiscountAmount )));

                        final_sales_textview.setText(String.valueOf(twoDecimalConverterWithComma.format(totalPrice_batch )));
                        total_weight_textview.setText(String.valueOf(twoDecimalConverterWithComma.format(totalWeight_batch )));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    showProgressBar(false);
                 //   setAdapterForOrderDetails();
                }
            }
        }
        else{
            try{
                total_OrdersCount_textview.setText(String.valueOf((int) consolidatedSalesReportArrayList.size()));

                total_maleCount_textview.setText(String.valueOf((int) totalMaleCount));
                total_female_count_textview.setText(String.valueOf((int) totalFemaleCount));
                totalCount_textview.setText(String.valueOf((int) totalQuantity));
                discount_amount_textview.setText(String.valueOf(twoDecimalConverterWithComma.format(totalDiscountAmount )));
                final_sales_textview.setText(String.valueOf(twoDecimalConverterWithComma.format(totalPrice_batch )));
                total_weight_textview.setText(String.valueOf(threeDecimalConverter.format(totalWeight_batch )));
            }
            catch (Exception e){
                e.printStackTrace();
            }
            mRecyclerview.setVisibility(View.GONE);
            salesValue_parentLayout.setVisibility(View.GONE);
            noOrder_inthis_Batch_instructionTextview.setVisibility(View.VISIBLE);
            noOrder_inthis_Batch_instructionTextview.setText("There is no orders placed in this batch");
            try {
               // mAdapter.notifyDataSetChanged();
                showProgressBar(false);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }


    }

    private void setAdapterForOrderDetails() {
        if(consolidatedSalesReportArrayList.size()>0) {
            mRecyclerview.setVisibility(View.GONE);
            salesValue_parentLayout.setVisibility(View.VISIBLE);
            noOrder_inthis_Batch_instructionTextview.setVisibility(View.GONE);
            mAdapter = new Adapter_ConsolidatedSalesReport_recyclerView(consolidatedSalesReportArrayList, ConsolidatedSalesReport.this, getApplicationContext());
            LinearLayoutManager manager = new LinearLayoutManager(ConsolidatedSalesReport.this);
            //manager.setMeasurementCacheEnabled(true);
            mRecyclerview.setLayoutManager(manager);
         /*   mRecyclerview.setHasFixedSize(false);
            mRecyclerview.setNestedScrollingEnabled(false);
            mRecyclerview.setAdapter(mAdapter);
            mRecyclerview.setHasFixedSize(true);
            mRecyclerview.setItemViewCacheSize(20);
            mRecyclerview.setDrawingCacheEnabled(true);
            mRecyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            mRecyclerview.getRecycledViewPool().setMaxRecycledViews(0, 20);
            mRecyclerview.setNestedScrollingEnabled(false);
            mRecyclerview.setFocusable(false);
            mRecyclerview.setFocusableInTouchMode(false);
            mRecyclerview.setLayoutManager(manager);
            mRecyclerview.setHasFixedSize(false);

          */
            mRecyclerview.setAdapter(mAdapter);
        }
        else{
            try{
                total_OrdersCount_textview.setText(String.valueOf((int) 0));

                total_maleCount_textview.setText(String.valueOf((int) 0));
                total_female_count_textview.setText(String.valueOf((int) 0));
                totalCount_textview.setText(String.valueOf((int) 0));
                discount_amount_textview.setText(String.valueOf(twoDecimalConverterWithComma.format(0.00 )));

                final_sales_textview.setText(String.valueOf(twoDecimalConverterWithComma.format(0.00 )));
                total_weight_textview.setText(String.valueOf(threeDecimalConverter.format(0.00 )));
            }
            catch (Exception e){
                e.printStackTrace();
            }
            mRecyclerview.setVisibility(View.GONE);
            salesValue_parentLayout.setVisibility(View.GONE);
            noOrder_inthis_Batch_instructionTextview.setVisibility(View.VISIBLE);
            noOrder_inthis_Batch_instructionTextview.setText("There is no orders placed in this batch");

        }
        showProgressBar(false);
    }

    private ArrayList<Modal_B2BBatchDetails> sortThisArrayUsingbatchCreatedDate(ArrayList<Modal_B2BBatchDetails> batchDetailsArrayList) {
        final Pattern p = Pattern.compile("^\\d+");

        Comparator<Modal_B2BBatchDetails> c = new Comparator<Modal_B2BBatchDetails>() {
            @Override
            public int compare(Modal_B2BBatchDetails object1, Modal_B2BBatchDetails object2) {
                Matcher m = p.matcher(object1.getCreatedDate_long());
                Integer number1 = null;
                if (!m.find()) {
                    Matcher m1 = p.matcher(object2.getCreatedDate_long());
                    if (m1.find()) {
                        return object2.getCreatedDate_long().compareTo(object1.getCreatedDate_long());
                    } else {
                        return object1.getCreatedDate_long().compareTo(object2.getCreatedDate_long());
                    }
                } else {
                    Integer number2 = null;
                    number1 = Integer.parseInt(m.group());
                    m = p.matcher(object2.getCreatedDate_long());
                    if (!m.find()) {
                        // return object1.compareTo(object2);
                        Matcher m1 = p.matcher(object1.getCreatedDate_long());
                        if (m1.find()) {
                            return object2.getCreatedDate_long().compareTo(object1.getCreatedDate_long());
                        } else {
                            return object1.getCreatedDate_long().compareTo(object2.getCreatedDate_long());
                        }
                    } else {
                        number2 = Integer.parseInt(m.group());
                        int comparison = number1.compareTo(number2);
                        if (comparison != 0) {
                            return comparison;
                        } else {
                            return object1.getCreatedDate_long().compareTo(object2.getCreatedDate_long());
                        }
                    }
                }
            }
        };

        Collections.sort(batchDetailsArrayList, c);
 
 
 
        return batchDetailsArrayList;
    }


    private void Intialize_and_Execute_OrderDetailsList(String callMethod, String fromDate, String todate, boolean isCalledFromOnCreate)  {



        // newFromDate_long<startingfromDate_long
        if(isCalledFromOnCreate  ) {
            startingfromDate_long = Long.parseLong(DateParser.getLongValuefortheDate(fromDate+" 00:00:00"));

            showProgressBar(true);
            if (isBatchDetailsTableServiceCalled) {
                // showProgressBar(false);
                return;
            }
            isBatchDetailsTableServiceCalled = true;
            callback_B2BBatchDetailsInterface = new B2BBatchDetailsInterface() {


                @Override
                public void notifySuccessForGettingListItem(ArrayList<Modal_B2BBatchDetails> batchDetailsArrayListt) {
                    if (batchDetailsArrayListt.size() > 0) {
                        noentryForrequestedDate = 1;
                        isTheBatchCretedForTheLastRequestedDate = true;
                        batchDetailsArrayList.addAll( batchDetailsArrayListt);
                        // showProgressBar(false);
                        isBatchDetailsTableServiceCalled = false;
                       // callAdapter(orderDetailsArrayList);
                    } else {
                        noentryForrequestedDate = noentryForrequestedDate +1;

                        isTheBatchCretedForTheLastRequestedDate = false;
                        showProgressBar(false);
                        loadingPB.setVisibility(View.INVISIBLE);
                        isfetchMoreDateAlertShown = false;
                    }
                }

                @Override
                public void notifySuccess(String result) {
                    showProgressBar(false);
                    isTheBatchCretedForTheLastRequestedDate = false;
                    isBatchDetailsTableServiceCalled = false;
                    isfetchMoreDateAlertShown = false;
                }

                @Override
                public void notifyVolleyError(VolleyError error) {
                    noentryForrequestedDate = noentryForrequestedDate +1;
                    Toast.makeText(ConsolidatedSalesReport.this, "There is an volley error while Fetching Batch Details", Toast.LENGTH_SHORT).show();
                    showProgressBar(false);
                    isBatchDetailsTableServiceCalled = false;
                    isfetchMoreDateAlertShown = false;
                    loadingPB.setVisibility(View.INVISIBLE);
                }

                @Override
                public void notifyProcessingError(Exception error) {
                    noentryForrequestedDate = noentryForrequestedDate +1;
                    Toast.makeText(ConsolidatedSalesReport.this, "There is an Process error while Fetching Batch Details", Toast.LENGTH_SHORT).show();
                    loadingPB.setVisibility(View.INVISIBLE);
                    isfetchMoreDateAlertShown = false;
                    showProgressBar(false);
                    isBatchDetailsTableServiceCalled = false;


                }


            };

            if (callMethod.equals(Constants.CallGETListMethod)) {
                //String getApiToCall = API_Manager.getOrderDetailsForBatchno+"?batchno="+batchno ;
                String ApiToCall =  API_Manager.getBatchDetailsWithDeliveryCenterAndStatusFromToDate +"?deliverycentrekey="+ deliveryCentreKey + "&fromdate="+fromDate+"&todate="+todate ;

                B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface, ApiToCall, callMethod);
                asyncTask.execute();

            }
        }
        else{
            newFromDate_long = Long.parseLong(DateParser.getLongValuefortheDate(fromDate+" 00:00:00"));
            if(startingfromDate_long>newFromDate_long) {
                showProgressBar(true);
                if (isBatchDetailsTableServiceCalled) {
                    // showProgressBar(false);
                    return;
                }
                isBatchDetailsTableServiceCalled = true;
                callback_B2BBatchDetailsInterface = new B2BBatchDetailsInterface() {


                    @Override
                    public void notifySuccessForGettingListItem(ArrayList<Modal_B2BBatchDetails> arrayList) {
                        if (arrayList.size() > 0) {
                            noentryForrequestedDate = 1;
                            isTheBatchCretedForTheLastRequestedDate = true;
                            batchDetailsArrayList.addAll(arrayList);
                            // showProgressBar(false);
                            isBatchDetailsTableServiceCalled = false;
                         //   callAdapter(orderDetailsArrayList);
                        } else {
                            isTheBatchCretedForTheLastRequestedDate = false;
                            showProgressBar(false);
                            noentryForrequestedDate = noentryForrequestedDate +1;
                            loadingPB.setVisibility(View.INVISIBLE);
                            isBatchDetailsTableServiceCalled = false;
                            isfetchMoreDateAlertShown = false;
                        }
                    }

                    @Override
                    public void notifySuccess(String result) {
                        showProgressBar(false);
                        isBatchDetailsTableServiceCalled = false;
                        isTheBatchCretedForTheLastRequestedDate = false;
                        loadingPB.setVisibility(View.INVISIBLE);
                        noentryForrequestedDate = noentryForrequestedDate +1;
                        isfetchMoreDateAlertShown = false;
                    }

                    @Override
                    public void notifyVolleyError(VolleyError error) {
                        Toast.makeText(ConsolidatedSalesReport.this, "There is an volley error while Fetching Batch Details", Toast.LENGTH_SHORT).show();
                        showProgressBar(false);
                        noentryForrequestedDate = noentryForrequestedDate +1;
                        isBatchDetailsTableServiceCalled = false;
                        isfetchMoreDateAlertShown = false;
                        isBatchDetailsTableServiceCalled = false;
                        isTheBatchCretedForTheLastRequestedDate = false;
                        loadingPB.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void notifyProcessingError(Exception error) {

                        Toast.makeText(ConsolidatedSalesReport.this, "There is an Process error while Fetching Batch Details", Toast.LENGTH_SHORT).show();
                        loadingPB.setVisibility(View.INVISIBLE);
                        isfetchMoreDateAlertShown = false;
                        showProgressBar(false);
                        noentryForrequestedDate = noentryForrequestedDate +1;
                        isBatchDetailsTableServiceCalled = false;
                        isTheBatchCretedForTheLastRequestedDate = false;
                        isBatchDetailsTableServiceCalled = false;


                    }


                };

                if (callMethod.equals(Constants.CallGETListMethod)) {
                    //String getApiToCall = API_Manager.getOrderDetailsForBatchno+"?batchno="+batchno ;
                    String ApiToCall =  API_Manager.getBatchDetailsWithDeliveryCenterAndStatusFromToDate +"?deliverycentrekey="+ deliveryCentreKey + "&fromdate="+fromDate+"&todate="+todate ;

                    B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface, ApiToCall, callMethod);
                    asyncTask.execute();

                }
            }
        }
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



    private Handler newHandler() {
        Handler.Callback callback = new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                String data = bundle.getString("dropdown");
                if(data.equals("dropdown")){
                    String data1 = bundle.getString("dropdown");
                    String batchno ="";
                    batchno = bundle.getString("batchno");
                    isBatchFilterStarted = false;
                    isBatchSelected = true;
                    batchwise_Textview.setText(String.valueOf(batchno));
                    if (String.valueOf(data1).equalsIgnoreCase("dropdown")) {
                        //Log.e(TAG, "dismissDropdown");
                        //Log.e(Constants.TAG, "createBillDetails in CartItem 0 ");
                        batchwise_Textview.dismissDropDown();
                        try {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            Objects.requireNonNull(imm).hideSoftInputFromWindow(batchwise_Textview.getWindowToken(), 0);

                            batchwise_Textview.clearFocus();
                            batchwise_overlapView.setVisibility(View.VISIBLE);


                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                        selectedBatchNo = String.valueOf(batchno);

                        Intitalize_And_Execute_B2BOrderDetails(selectedBatchNo);

                    }
                }
                else{

                }

                return false;
            }
        };
        return new Handler(callback);
    }


    private void showKeyboard(final EditText editText) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus();
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                editText.setSelection(editText.getText().length());
            }
        },0);
    }


    private String ConvertGramsToKilograms(String grossWeightingramsString) {
        String weightinKGString = "";
        DecimalFormat df = new DecimalFormat(Constants.threeDecimalPattern);

        try {
            grossWeightingramsString = grossWeightingramsString.replaceAll("[^\\d.]", "");

            if(grossWeightingramsString.equals("") || grossWeightingramsString.equals(null)){
                grossWeightingramsString = "0";
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        float grossweightInGramDouble = 0;
        try{
            grossweightInGramDouble = Float.parseFloat(grossWeightingramsString);
        }
        catch (Exception e){
            grossweightInGramDouble = 0;
            //   e.printStackTrace();
        }
        if(grossweightInGramDouble >0 ) {
            try {
                float temp = grossweightInGramDouble / 1000;
                // double rf = Math.round((temp * 10.0) / 10.0);
                weightinKGString = String.valueOf(temp);
            }
            catch (Exception e){
                weightinKGString = grossWeightingramsString;

                e.printStackTrace();
            }

        }
        else{
            weightinKGString = grossWeightingramsString;
        }
        weightinKGString = df.format(Double.parseDouble(weightinKGString));
        return  weightinKGString;
    }


}