package com.tmc.tmcb2bpartnerapp.second_version.activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderItemDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal__B2BCreditTransactionHistory;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal__B2BPaymentDetails;
import com.tmc.tmcb2bpartnerapp.second_version.adapter.Adapter_ViewOrdersList_recyclerView;
import com.tmc.tmcb2bpartnerapp.second_version.apiRequestServices.B2BCreditTransactionHistory;
import com.tmc.tmcb2bpartnerapp.second_version.interfaces.B2BCreditTransactionHistoryInterface;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.TMCAlertDialogClass;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;
import static com.itextpdf.text.BaseColor.GRAY;
import static com.itextpdf.text.BaseColor.LIGHT_GRAY;

public class ViewOrderList extends BaseActivity {

    RecyclerView earTagItems_recyclerview;
    LinearLayout  loadingPanel ,loadingpanelmask;
    private ProgressBar loadingPB;
    private NestedScrollView nestedSV;
    EditText retailerName_editText;
    LinearLayout search_IconLayout,back_IconLayout,close_IconLayout;
    TextView ordersCount_textwidget,listviewrelated_instruction_textview,retailerName_textView;




    String deliveryCenterKey ="", deliveryCenterName ="",retailername ="" , tokenNo ="",supervisorname ="" , retailermobileno =""
    ,retaileraddress ="",finalBatchValue_String ="" , finalGoatValue_String ="",feedWeight ="",feedPricePerKg ="0",
            feedTotalPrice ="0",finalgoatValueWithFeed_String ="",discountAmount ="" , orderid ="" , finalgoatValueWithFeed_MinusDiscount_String =""
            ,finalquantity ="",finalWeight ="" ,  orderplacedDate = "" , orderPlacedTime ="",notes ="";
    String FromDate = "", Todate = "" , firstTimeToDate ="";

    double finalPayableprice =0 , totalWeight_double =0 , finalGoatValue_double =0 , finalgoatValueWithFeed_Double =0
            ,discountAmount_double =0 , finalgoatValueWithFeed_MinusDiscount_Double =0 , finalBatchValueDouble=0
            , totalMeatYeild =0 ,totalaApproxLiveWeight = 0  ;

    double oldCreditAmount =0 , newCreditAmount = 0 , transactionAmount = 0 ;


    int no_of_days_before =0;
    int count = 0 ;
    int dateCountToIncreaseOnScroll = 7;
    int dateCountToFetchInStarting = 15;
    int noentryForrequestedDate = 1;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;



    long startingfromDate_long = 0 , newFromDate_long =0;

    static DecimalFormat threeDecimalConverter = new DecimalFormat(Constants.threeDecimalPattern);
    static  DecimalFormat twoDecimalConverter = new DecimalFormat(Constants.twoDecimalPattern);

    Modal_B2BOrderDetails modal_b2BOrderDetails_global = new Modal_B2BOrderDetails();

    ArrayList<String> earTagDetailsArrayList_String = new ArrayList<>();
    ArrayList<Modal_GoatEarTagDetails> earTagDetailsArrayList = new ArrayList<>();
    ArrayList<Modal_B2BOrderDetails> orderDetailsArrayList = new ArrayList<>();
    ArrayList<Modal_B2BOrderDetails> sortedOrderDetailsArrayList = new ArrayList<>();

    boolean isB2BOrderTableServiceCalled = false;
    boolean isTheOrderPlacedForTheLastRequestedDate = false;
    boolean isSearchButtonClicked = false;
    boolean isfetchMoreDateAlertShown = false;
    boolean isPDF_FIle = false;
    boolean isB2BOrderItemDetailsTableServiceCalled = false;
    public  boolean isGeneratePDFCalled = false;
    boolean isRetailerCreditTransactionHistoryCalled = false;


    B2BOrderDetailsInterface callback_b2bOrderDetails;
    B2BOrderItemDetailsInterface callback_b2bOrderItemDetails;
    B2BCreditTransactionHistoryInterface callB2BCreditTransactionHistoryInterface = null;

    static DecimalFormat twoDecimalConverterWithComma = new DecimalFormat(Constants.twoDecimalWithCommaPattern);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_list);
        earTagItems_recyclerview = findViewById(R.id.earTagItems_recyclerview);
        loadingpanelmask = findViewById(R.id.loadingpanelmask);
        loadingPanel = findViewById(R.id.loadingPanel);
        loadingPB = findViewById(R.id.idPBLoading);
        nestedSV = findViewById(R.id.idNestedSV);
        back_IconLayout = findViewById(R.id.back_IconLayout);
        retailerName_editText = findViewById(R.id.retailerName_editText);
        search_IconLayout = findViewById(R.id.search_IconLayout);
        close_IconLayout = findViewById(R.id.close_IconLayout);
        retailerName_textView = findViewById(R.id.retailerName_textView);
        ordersCount_textwidget = findViewById(R.id.ordersCount_textwidget);
        listviewrelated_instruction_textview = findViewById(R.id.listviewrelated_instruction_textview);


        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);
        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");


        
        Todate = String.valueOf(DateParser.getDate_newFormat());
        no_of_days_before = dateCountToFetchInStarting;
        firstTimeToDate  = Todate;

        FromDate = String.valueOf(DateParser.getDateTextFor_OldDays(no_of_days_before));
        orderDetailsArrayList.clear();
        listviewrelated_instruction_textview.setText(String.valueOf("# This List contains orders from " + FromDate + " date to "+ firstTimeToDate+" . "));

        startingfromDate_long = 0;

        Create_and_SharePdf(true,new Modal_B2BOrderDetails());

        Intialize_and_Execute_OrderDetailsList(Constants.CallGETListMethod,FromDate , Todate,true);


        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




        retailerName_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sortedOrderDetailsArrayList.clear();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                sortedOrderDetailsArrayList.clear();
                isSearchButtonClicked =true;
                String name = (editable.toString());
                if(!name.equals("")) {
                    String orderstatus = "";

                    for (int i = 0; i < orderDetailsArrayList.size(); i++) {
                        try {
                            //Log.d(Constants.TAG, "displayorderDetailsinListview ordersList: " + ordersList.get(i));
                            final Modal_B2BOrderDetails modal_b2BOrderDetails = orderDetailsArrayList.get(i);
                            String retailername = modal_b2BOrderDetails.getRetailername();
                            if (retailername.toUpperCase().contains( name.toUpperCase())) {

                                sortedOrderDetailsArrayList.add(modal_b2BOrderDetails);


                            }

                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                    try {
                        callAdapter(sortedOrderDetailsArrayList);

                    } catch (Exception E) {
                        E.printStackTrace();
                    }


                }
                else{
                    isTheOrderPlacedForTheLastRequestedDate = false;
                    showProgressBar(false);
                    loadingPB.setVisibility(View.INVISIBLE);

                }

            }
        });


        search_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int textlength = retailerName_editText.getText().toString().length();
                isSearchButtonClicked =true;
                showKeyboard(retailerName_editText);
                showSearchBarEditText();
            }
        });

        close_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(retailerName_editText);
                closeSearchBarEditText();
                retailerName_editText.setText("");
                isSearchButtonClicked =false;
                callAdapter(orderDetailsArrayList);
            }
        });




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

                            new TMCAlertDialogClass(ViewOrderList.this, R.string.app_name, R.string.do_you_need_fetch_dataFor_more_days_Instruction,
                                    R.string.Yes_Text, R.string.No_Text,
                                    new TMCAlertDialogClass.AlertListener() {
                                        @Override
                                        public void onYes() {

                                            if (isTheOrderPlacedForTheLastRequestedDate) {
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
                                            listviewrelated_instruction_textview.setText(String.valueOf("# This List contains orders from " + FromDate + " date to " + firstTimeToDate + " . "));

                                            Intialize_and_Execute_OrderDetailsList(Constants.CallGETListMethod, FromDate, Todate, false);


                                        }

                                        @Override
                                        public void onNo() {
                                            loadingPB.setVisibility(View.INVISIBLE);
                                            earTagItems_recyclerview.setVisibility(View.VISIBLE);
                                            isfetchMoreDateAlertShown = false;


                                        }
                                    });


                        }
                        else {
                            loadingPB.setVisibility(View.INVISIBLE);
                            Toast.makeText(ViewOrderList.this, "You have reached the limit", Toast.LENGTH_SHORT).show();
                            isfetchMoreDateAlertShown = false;
                        }


                    }
                    }
                }
            }
        });


    }

    public void Create_and_SharePdf(boolean isJustNeedTOAskPermission, Modal_B2BOrderDetails modal_b2BOrderDetail) {
        showProgressBar(true);
        modal_b2BOrderDetails_global = new Modal_B2BOrderDetails();
        modal_b2BOrderDetails_global = modal_b2BOrderDetail;
        if(isJustNeedTOAskPermission){
            try{

                if (Build.VERSION.SDK_INT >= 30){
                    try {
                        if (!Environment.isExternalStorageManager()) {
                            Intent getpermission = new Intent();
                            getpermission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                            startActivity(getpermission);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else {


                    if (SDK_INT >= Build.VERSION_CODES.R) {

                        if (Environment.isExternalStorageManager()) {

                        } else {
                            try {
                                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                                intent.addCategory("android.intent.category.DEFAULT");
                                intent.setData(Uri.parse(String.format("package:%s", getPackageName())));
                                startActivityForResult(intent, 2296);
                            } catch (Exception e) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                                startActivityForResult(intent, 2296);
                            }
                        }

                    } else {


                        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(ViewOrderList.this, WRITE_EXTERNAL_STORAGE);
                        //Log.d("ExportInvoiceActivity", "writeExternalStoragePermission "+writeExternalStoragePermission);
                        // If do not grant write external storage permission.
                        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                            // Request user to grant write external storage permission.
                            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE},
                                    REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
                        } else {

                        }
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            if (Build.VERSION.SDK_INT >= 30){
                try {
                    if (!Environment.isExternalStorageManager()) {
                        Intent getpermission = new Intent();
                        getpermission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                        startActivity(getpermission);
                    }
                    else{
                        try {

                            FetchOrderItemDetails_And_GeneratePDF(modal_b2BOrderDetail);


                        } catch (Exception e) {
                            e.printStackTrace();
                            ;
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else {
                try {

                    if (SDK_INT >= Build.VERSION_CODES.R) {

                        if (Environment.isExternalStorageManager()) {
                            try {

                                FetchOrderItemDetails_And_GeneratePDF(modal_b2BOrderDetail);


                            } catch (Exception e) {
                                e.printStackTrace();
                                ;
                            }
                        } else {
                            try {
                                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                                intent.addCategory("android.intent.category.DEFAULT");
                                intent.setData(Uri.parse(String.format("package:%s", getPackageName())));
                                startActivityForResult(intent, 2296);
                            } catch (Exception e) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                                startActivityForResult(intent, 2296);
                            }
                        }

                    } else {


                        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(ViewOrderList.this, WRITE_EXTERNAL_STORAGE);
                        //Log.d("ExportInvoiceActivity", "writeExternalStoragePermission "+writeExternalStoragePermission);
                        // If do not grant write external storage permission.
                        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                            // Request user to grant write external storage permission.
                            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE},
                                    REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
                        } else {
                            showProgressBar(true);
                            try {
                                FetchOrderItemDetails_And_GeneratePDF(modal_b2BOrderDetail);


                            } catch (Exception e) {
                                e.printStackTrace();
                                ;
                            }
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if(!String.valueOf(modal_b2BOrderDetails_global.getOrderid()).equals("")) {
                        FetchOrderItemDetails_And_GeneratePDF(modal_b2BOrderDetails_global);
                    }


                    Toast.makeText(this, "Permission given",
                            Toast.LENGTH_SHORT).show();
                    //saveImage(finalBitmap, image_name); <- or whatever you want to do after permission was given . For instance, open gallery or something
                } else {

                    Toast.makeText(this, "Permission Denied ",
                            Toast.LENGTH_SHORT).show();

                    Toast.makeText(this, "Please Give Permission For Storage ",
                            Toast.LENGTH_SHORT).show();
                    Create_and_SharePdf(false, modal_b2BOrderDetails_global);

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }
    public void FetchOrderItemDetails_And_GeneratePDF(Modal_B2BOrderDetails modal_b2BOrderDetails) {

        retailername ="" ; tokenNo ="";supervisorname ="" ; retailermobileno ="";finalquantity ="";finalWeight="";
        retaileraddress ="";finalBatchValue_String ="" ; finalGoatValue_String ="";feedWeight ="";feedPricePerKg ="";
        feedTotalPrice ="";finalgoatValueWithFeed_String ="";discountAmount ="";orderid ="";finalgoatValueWithFeed_MinusDiscount_String ="";
        orderplacedDate =""; orderPlacedTime = "";
        totalWeight_double =0;finalGoatValue_double=0;finalgoatValueWithFeed_Double =0;totalMeatYeild =0;
        discountAmount_double =0 ;finalgoatValueWithFeed_MinusDiscount_Double =0 ;finalBatchValueDouble=0;
        totalaApproxLiveWeight = 0;

        earTagDetailsArrayList_String.clear();
        earTagDetailsArrayList.clear();


        retailername = String.valueOf(modal_b2BOrderDetails.getRetailername().toString());
        supervisorname = String.valueOf(modal_b2BOrderDetails.getSupervisorname().toString());
        tokenNo = String.valueOf(modal_b2BOrderDetails.getBillno().toString());
        retailermobileno = String.valueOf(modal_b2BOrderDetails.getRetailermobileno().toString());
        retaileraddress = String.valueOf(modal_b2BOrderDetails.getRetaileraddress().toString());
        feedWeight = String.valueOf(modal_b2BOrderDetails.getFeedWeight().toString());
        feedPricePerKg = String.valueOf(modal_b2BOrderDetails.getFeedPriceperkg().toString());
        feedTotalPrice = String.valueOf(modal_b2BOrderDetails.getFeedPrice().toString());
        discountAmount = String.valueOf(modal_b2BOrderDetails.getDiscountamount().toString());
        orderid = String.valueOf(modal_b2BOrderDetails.getOrderid().toString());
        finalquantity = String.valueOf(modal_b2BOrderDetails.getTotalquantity().toString());
        finalPayableprice = Double.parseDouble(String.valueOf(modal_b2BOrderDetails.getTotalPrice()));
        finalWeight = String.valueOf(modal_b2BOrderDetails.getTotalweight());
        orderplacedDate = DateParser.convertOldFormatDateTimeintoNewFormatDateTime(String.valueOf(modal_b2BOrderDetails.getOrderplaceddate()));
        notes = (String.valueOf(modal_b2BOrderDetails.getNotes()));

        try{
            String[] str = orderplacedDate.split(" ");
            orderPlacedTime = str[1];
        }
        catch (Exception e){
            orderplacedDate = String.valueOf(modal_b2BOrderDetails.getOrderplaceddate());
            e.printStackTrace();
        }



        try{
            String[] str = orderplacedDate.split(" ");
            orderplacedDate = str[0];
        }
        catch (Exception e){
            orderplacedDate = String.valueOf(modal_b2BOrderDetails.getOrderplaceddate());
            e.printStackTrace();
        }



        try {
            showProgressBar(true);
            if (isB2BOrderItemDetailsTableServiceCalled) {
                // showProgressBar(false);
                return;
            }
            isB2BOrderItemDetailsTableServiceCalled = true;
            callback_b2bOrderItemDetails = new B2BOrderItemDetailsInterface() {


                @Override
                public void notifySuccessForGettingListItem(ArrayList<Modal_B2BOrderItemDetails> arrayList) {
                    for (int iterator = 0; iterator < arrayList.size(); iterator++) {
                        Modal_B2BOrderItemDetails modal_b2BOrderItemDetails = arrayList.get(iterator);
                        Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();
                        modal_goatEarTagDetails.barcodeno = modal_b2BOrderItemDetails.getBarcodeno();
                        modal_goatEarTagDetails.batchno = modal_b2BOrderItemDetails.getBatchno();
                        modal_goatEarTagDetails.status = modal_b2BOrderItemDetails.getStatus();
                        modal_goatEarTagDetails.itemaddeddate = modal_b2BOrderItemDetails.getItemaddeddate();
                        modal_goatEarTagDetails.currentweightingrams = modal_b2BOrderItemDetails.getWeightingrams();
                        modal_goatEarTagDetails.gender = modal_b2BOrderItemDetails.getGender();
                        modal_goatEarTagDetails.breedtype = modal_b2BOrderItemDetails.getBreedtype();
                        modal_goatEarTagDetails.status = modal_b2BOrderItemDetails.getStatus();
                        modal_goatEarTagDetails.stockedweightingrams = modal_b2BOrderItemDetails.getWeightingrams();
                        modal_goatEarTagDetails.loadedweightingrams = modal_b2BOrderItemDetails.getWeightingrams();
                        modal_goatEarTagDetails.newWeight_forBillingScreen = modal_b2BOrderItemDetails.getWeightingrams();
                        modal_goatEarTagDetails.b2bctgykey = modal_b2BOrderItemDetails.getB2bctgykey();
                        modal_goatEarTagDetails.b2bsubctgykey = modal_b2BOrderItemDetails.getB2bsubctgykey();
                        modal_goatEarTagDetails.orderid_forBillingScreen = modal_b2BOrderItemDetails.getOrderid();
                        modal_goatEarTagDetails.approxliveweight = modal_b2BOrderItemDetails.getApproxliveweight();
                        modal_goatEarTagDetails.meatyieldweight = modal_b2BOrderItemDetails.getMeatyieldweight();
                        modal_goatEarTagDetails.partsweight = modal_b2BOrderItemDetails.getPartsweight();
                        modal_goatEarTagDetails.totalPrice_ofItem = modal_b2BOrderItemDetails.getTotalPrice_ofItem();
                        modal_goatEarTagDetails.discount = modal_b2BOrderItemDetails.getDiscount();
                        modal_goatEarTagDetails.itemPrice = modal_b2BOrderItemDetails.getItemPrice();
                        modal_goatEarTagDetails.totalItemWeight = modal_b2BOrderItemDetails.getTotalItemWeight();

                        double meatYield_double =0 , parts_double = 0;
                        try{
                            String text = String.valueOf(modal_b2BOrderItemDetails.getMeatyieldweight());
                            text = text.replaceAll("[^\\d.]", "");
                            if(text.equals("")){
                                text = "0";
                            }
                            meatYield_double = Double.parseDouble(text);
                        }
                        catch (Exception e){
                            meatYield_double = 0;
                            e.printStackTrace();
                        }


                        try{
                            String text = String.valueOf(modal_b2BOrderItemDetails.getPartsweight());
                            text = text.replaceAll("[^\\d.]", "");
                            if(text.equals("")){
                                text = "0";
                            }
                            parts_double = Double.parseDouble(text);
                        }
                        catch (Exception e){
                            meatYield_double = 0;
                            e.printStackTrace();
                        }

                        try{
                            totalWeight_double = meatYield_double+parts_double;
                        }
                        catch (Exception e){
                            totalWeight_double = 0;
                            e.printStackTrace();
                        }
                        modal_b2BOrderItemDetails.setTotalItemWeight(String.valueOf(totalWeight_double));
                        modal_goatEarTagDetails.totalItemWeight = (String.valueOf(totalWeight_double));



                        ProcessDataAndAddItIntoArray(modal_goatEarTagDetails , iterator , arrayList.size());


                    }
                    isB2BOrderItemDetailsTableServiceCalled = false;
                    showProgressBar(false);
                }

                @Override
                public void notifySuccess(String result) {
                    isB2BOrderItemDetailsTableServiceCalled = false;
                }

                @Override
                public void notifyVolleyError(VolleyError error) {
                    isB2BOrderItemDetailsTableServiceCalled = false;
                    Log.i("INIT", "FetchDataFromOrderItemDetails:  " + String.valueOf(error));

                }

                @Override
                public void notifyProcessingError(Exception error) {

                    Log.i("INIT", "FetchDataFromOrderItemDetails:  " + String.valueOf(error));
                    isB2BOrderItemDetailsTableServiceCalled = false;
                }
            };


                //String getApiToCall = API_Manager.getOrderDetailsForBatchno+"?batchno="+batchno ;
                String getApiToCall = API_Manager.getOrderItemDetailsForOrderid + orderid;

                B2BOrderItemDetails asyncTask = new B2BOrderItemDetails(callback_b2bOrderItemDetails, getApiToCall, Constants.CallGETListMethod);
                asyncTask.execute();



        }
        catch (Exception e){
            e.printStackTrace();
        }




    }

    private void ProcessDataAndAddItIntoArray(Modal_GoatEarTagDetails modal_goatEarTagDetails, int iterator, int size) {





        if(!earTagDetailsArrayList_String.contains(String.valueOf(modal_goatEarTagDetails.getBarcodeno())))
        {
            earTagDetailsArrayList_String.add(String.valueOf(modal_goatEarTagDetails.getBarcodeno()));



            String approxLiveWeight_String="";
            double approxLiveWeight_Double =0,meatYield_double =0 , parts_double = 0 , itemPriceDouble= 0 , feedTotalPrice_double =0 , maleQtyAvg = 0 ,femaleQtyAvg = 0 , meatYieldAvg =0 , approxLiveWeightAvg=0;
            double  finalquantitydouble = 0;
            try{
                String text = String.valueOf(modal_goatEarTagDetails.getMeatyieldweight());
                text = text.replaceAll("[^\\d.]", "");
                if(text.equals("")){
                    text = "0";
                }
                meatYield_double = Double.parseDouble(text);
            }
            catch (Exception e){
                meatYield_double = 0;
                e.printStackTrace();
            }


            try{
                String text = String.valueOf(modal_goatEarTagDetails.getPartsweight());
                text = text.replaceAll("[^\\d.]", "");
                if(text.equals("")){
                    text = "0";
                }
                parts_double = Double.parseDouble(text);
            }
            catch (Exception e){
                parts_double = 0;
                e.printStackTrace();
            }

            try{
                totalWeight_double = meatYield_double+parts_double;
            }
            catch (Exception e){
                totalWeight_double = 0;
                e.printStackTrace();
            }
            modal_goatEarTagDetails.setTotalItemWeight(String.valueOf(threeDecimalConverter.format(totalWeight_double)));


            earTagDetailsArrayList.add(modal_goatEarTagDetails);


            try{
                String text = String.valueOf(modal_goatEarTagDetails.getItemPrice());
                text = text.replaceAll("[^\\d.]", "");
                if(text.equals("")){
                    text = "0";
                }
                itemPriceDouble = Double.parseDouble(text);
            }
            catch (Exception e){
                itemPriceDouble = 0;
                e.printStackTrace();
            }


            try{
                finalGoatValue_double = itemPriceDouble + finalGoatValue_double;
                try{
                    finalGoatValue_String = String.valueOf(twoDecimalConverter.format(finalGoatValue_double));

                }
                catch (Exception e){
                    finalGoatValue_String = String.valueOf(finalGoatValue_double);

                    e.printStackTrace();
                }

            }
            catch (Exception e){
                finalGoatValue_double = 0;
                finalGoatValue_String ="0";
                e.printStackTrace();
            }

            try{
                feedTotalPrice = feedTotalPrice.replaceAll("[^\\d.]", "");
                if(feedTotalPrice.equals("")){
                    feedTotalPrice = "0";
                }
                feedTotalPrice_double = Double.parseDouble(feedTotalPrice);
            }
            catch (Exception e){
                feedTotalPrice_double = 0;
                e.printStackTrace();
            }
            try{
                finalgoatValueWithFeed_Double = finalGoatValue_double + feedTotalPrice_double ;
                try{
                    finalgoatValueWithFeed_String = String.valueOf(twoDecimalConverter.format(finalgoatValueWithFeed_Double));

                }
                catch (Exception e){
                    finalgoatValueWithFeed_String = String.valueOf(finalgoatValueWithFeed_Double);

                    e.printStackTrace();
                }


            }
            catch (Exception e){
                finalgoatValueWithFeed_Double = 0;
                finalgoatValueWithFeed_String ="0";
                e.printStackTrace();
            }



            try{
                discountAmount = discountAmount.replaceAll("[^\\d.]", "");
                if(discountAmount.equals("")){
                    discountAmount = "0";
                }
                discountAmount_double = Double.parseDouble(discountAmount);
            }
            catch (Exception e){
                discountAmount_double = 0;
                e.printStackTrace();
            }
            try{
                finalgoatValueWithFeed_MinusDiscount_Double = finalgoatValueWithFeed_Double - discountAmount_double ;
                try{
                    finalgoatValueWithFeed_MinusDiscount_String = String.valueOf(twoDecimalConverter.format(finalgoatValueWithFeed_MinusDiscount_Double));

                }
                catch (Exception e){
                    finalgoatValueWithFeed_MinusDiscount_String = String.valueOf(finalgoatValueWithFeed_MinusDiscount_Double);

                    e.printStackTrace();
                }



            }
            catch (Exception e){
                finalgoatValueWithFeed_MinusDiscount_Double = 0;
                finalgoatValueWithFeed_MinusDiscount_String ="0";
                e.printStackTrace();
            }

            try{
                finalquantity = finalquantity.replaceAll("[^\\d.]", "");
                if(finalquantity.equals("")){
                    finalquantity = "0";
                }
                finalquantitydouble = Integer.parseInt((finalquantity));
            }
            catch (Exception e){
                finalquantitydouble = 0;
                e.printStackTrace();
            }



            try{
                finalBatchValueDouble = finalgoatValueWithFeed_Double / finalquantitydouble;
                finalBatchValue_String = String.valueOf(twoDecimalConverter.format(finalBatchValueDouble));
            }
            catch (Exception e) {
                e.printStackTrace();
            }





            modal_goatEarTagDetails.setTotalItemWeight(String.valueOf(threeDecimalConverter.format(totalWeight_double)));





            try{
                String text = String.valueOf(modal_goatEarTagDetails.getApproxliveweight());
                if(text.equals("")){
                    approxLiveWeight_String = "0";
                }
                else{
                    approxLiveWeight_String = text;
                }
            }
            catch (Exception e){
                approxLiveWeight_String ="0";
                e.printStackTrace();
            }





            try{
                totalMeatYeild = totalMeatYeild + meatYield_double;
            }
            catch (Exception e){
                e.printStackTrace();
            }


            try{
                approxLiveWeight_Double = Double.parseDouble(approxLiveWeight_String);
            }
            catch (Exception e){
                approxLiveWeight_Double  = 0;
                e.printStackTrace();

            }


            try{
                totalaApproxLiveWeight = totalaApproxLiveWeight + approxLiveWeight_Double;
            }
            catch (Exception e){
                e.printStackTrace();
            }




            try{
                meatYieldAvg = totalMeatYeild /finalquantitydouble;
            }
            catch (Exception e){
                e.printStackTrace();
            }

            try{
                approxLiveWeightAvg = totalaApproxLiveWeight /finalquantitydouble;

            }
            catch (Exception e){
                e.printStackTrace();
            }




            try{
                earTagDetailsArrayList_String = sortThisArrayUsingBarcode(earTagDetailsArrayList_String);
            }
            catch (Exception e){
                e.printStackTrace();
            }


            if(iterator == (size-1)){
                prepareDataForPDF();
            }






        }
        else{
            // Toast.makeText(PlacedOrderDetailsScreen.this, "Please Scan the New Item", Toast.LENGTH_SHORT).show();

        }




    }

    private void prepareDataForPDF() {
        isPDF_FIle = true;
        Log.d(Constants.TAG, "prepareDataFor PDF Response: " );

        boolean generatePdf = false;


        String extstoragedir = Environment.getExternalStorageDirectory().toString();
        String state = Environment.getExternalStorageState();
        //Log.d("PdfUtil", "external storage state "+state+" extstoragedir "+extstoragedir);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        File folder = new File(path);
        //  File folder = new File(fol, "pdf");
        if (!folder.exists()) {
            boolean bool = folder.mkdirs();
        }
        try {
            String date = DateParser.getDate_newFormat();
            String timeinLong = DateParser.getTime_newunderscoreFormat();
            //  String filename = "OrderReceipt for  " +retailername+" on "+String.valueOf( DateParser.getDate_and_time_newFormat())+".pdf";
            String filename = "OrderReceipt for "+retailername+" on "+date+" "+timeinLong+".pdf";
            final File file = new File(folder, filename);
            file.createNewFile();
            try {
                FileOutputStream fOut = new FileOutputStream(file);
                Document layoutDocument = new Document();
                PdfWriter.getInstance(layoutDocument, fOut);
                layoutDocument.open();

                //  addItemRows(layoutDocument);
                addItemRowsInNewPDFFormat(layoutDocument);
                isGeneratePDFCalled = false;
                // addItemRowsInOLDPDFFormat(layoutDocument);
                layoutDocument.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            // document = new PdfDocument(new PdfWriter("MyFirstInvoice.pdf"));

            isGeneratePDFCalled = false;
            showProgressBar(false);

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

           /* Intent pdfViewIntent = new Intent(Intent.ACTION_VIEW);
            pdfViewIntent.setDataAndType(Uri.fromFile(file), "application/pdf");
            pdfViewIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            pdfViewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Intent intent = Intent.createChooser(pdfViewIntent, "Open File");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                showProgressBar(false);

                startActivityForResult(intent, OPENPDF_ACTIVITY_REQUEST_CODE);
            } catch (ActivityNotFoundException e) {
                // Instruct the user to install a PDF reader here, or something
            }

            */

         /*   Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setType("application/pdf");
            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            startActivity(Intent.createChooser(share, "Share"));


          */
            isGeneratePDFCalled = false;
            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri outputPdfUri = FileProvider.getUriForFile(this, ViewOrderList.this.getPackageName() + ".provider", file);

            share.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            share.setType("application/pdf");
            share.putExtra(Intent.EXTRA_STREAM,outputPdfUri);
            startActivity(Intent.createChooser(share, "Share"));


            // }
        } catch (IOException e) {
            showProgressBar(false);

            Log.i("error", e.getLocalizedMessage());
        } catch (Exception ex) {
            showProgressBar(false);

            ex.printStackTrace();
        }


    }

    private void addItemRowsInNewPDFFormat(Document layoutDocument) {

        Font StoretitleFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
                Font.BOLD);
        Font subtitleFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,
                Font.BOLD);

        Font valueFont_10Bold= new Font(Font.FontFamily.TIMES_ROMAN, 12,
                Font.BOLD);
        Font valueFont_8Bold= new Font(Font.FontFamily.TIMES_ROMAN, 10,
                Font.BOLD);

        Font valueFont_10= new Font(Font.FontFamily.TIMES_ROMAN, 12,
                Font.NORMAL);
        Font valueFont_8= new Font(Font.FontFamily.TIMES_ROMAN, 10,
                Font.NORMAL);




        RoundRectangle roundRectange = new RoundRectangle();
        PdfPTable wholePDFContentOutline_table = new PdfPTable(1);
        PdfPTable tmcLogoImage_table = new PdfPTable(new float[] { 50, 25 ,25 });


        try {
            PdfPCell table_Cell = new PdfPCell();
            table_Cell.setPaddingTop(5);
            table_Cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table_Cell.setVerticalAlignment(Element.ALIGN_RIGHT);
            table_Cell.setBorder(Rectangle.NO_BORDER);
            tmcLogoImage_table.addCell(table_Cell);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PdfPCell table_Cell = new PdfPCell();
            table_Cell.setPaddingTop(5);
            table_Cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table_Cell.setVerticalAlignment(Element.ALIGN_RIGHT);

            table_Cell.setBorder(Rectangle.NO_BORDER);
            tmcLogoImage_table.addCell(table_Cell);

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            PdfPCell table_Cell = new PdfPCell(addLogo(layoutDocument));
            table_Cell.setPaddingTop(5);
            table_Cell.setBorder(Rectangle.NO_BORDER);
            table_Cell.setPaddingRight(10);

            table_Cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table_Cell.setVerticalAlignment(Element.ALIGN_RIGHT);
            tmcLogoImage_table.addCell(table_Cell);

        } catch (Exception e) {
            e.printStackTrace();
        }




        PdfPTable wholePDFContentWithOut_Outline_table = new PdfPTable(1);
        PdfPTable billtimeDetails_table = new PdfPTable(2);
        try {

            Phrase phrasebilltimeDetails = new Phrase("Billno : "+tokenNo+"      DATE : "+String.valueOf(orderplacedDate)+"      TIME : "+String.valueOf(orderPlacedTime), valueFont_8);
            PdfPCell phrasebilltimedetailscell = new PdfPCell(phrasebilltimeDetails);
            phrasebilltimedetailscell.setBorder(Rectangle.NO_BORDER);
            phrasebilltimedetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phrasebilltimedetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
            phrasebilltimedetailscell.setPaddingLeft(10);
            phrasebilltimedetailscell.setPaddingBottom(6);
            billtimeDetails_table.addCell(phrasebilltimedetailscell);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {

            PdfPTable supervisorNameDetails_table = new PdfPTable(1);

            Phrase phraseSupervisorNameLabelTitle = new Phrase("Supervisor Name : "+String.valueOf(supervisorname) , valueFont_8Bold);

            PdfPCell phraseSupervisorNameLabelTitlecell = new PdfPCell(phraseSupervisorNameLabelTitle);
            phraseSupervisorNameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
            phraseSupervisorNameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            phraseSupervisorNameLabelTitlecell.setVerticalAlignment(Element.ALIGN_RIGHT);
            phraseSupervisorNameLabelTitlecell.setPaddingLeft(6);
            phraseSupervisorNameLabelTitlecell.setPaddingBottom(3);
            phraseSupervisorNameLabelTitlecell.setPaddingRight(25);
            supervisorNameDetails_table.addCell(phraseSupervisorNameLabelTitlecell);






            try {
                PdfPCell supervisorDetails = new PdfPCell(supervisorNameDetails_table);

                supervisorDetails.setBorder(Rectangle.NO_BORDER);

                billtimeDetails_table.addCell(supervisorDetails);

            } catch (Exception e) {
                e.printStackTrace();
            }




        } catch (Exception e) {
            e.printStackTrace();
        }



        try {
            PdfPCell addBorder_billTimeDetails = new PdfPCell(billtimeDetails_table);
            addBorder_billTimeDetails.setBorder(Rectangle.NO_BORDER);
            addBorder_billTimeDetails.setPaddingTop(5);
            addBorder_billTimeDetails.setBorderWidthBottom(01);
            addBorder_billTimeDetails.setBorderColor(GRAY);


            wholePDFContentWithOut_Outline_table.addCell(addBorder_billTimeDetails);

        } catch (Exception e) {
            e.printStackTrace();
        }

        PdfPTable Whole_Warehouse_and_RetailerDetails_table = new PdfPTable(new float[] { 40, 60 });

        try{
            PdfPTable Whole_WarehouseDetails_table = new PdfPTable(1);


            try {


                Phrase phrasecompanyDetailsTitle = new Phrase("The Meat Chop  ", subtitleFont);

                PdfPCell phrasecompanyDetailsTitlecell = new PdfPCell(phrasecompanyDetailsTitle);
                phrasecompanyDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                phrasecompanyDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasecompanyDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasecompanyDetailsTitlecell.setPaddingBottom(4);
                phrasecompanyDetailsTitlecell.setPaddingLeft(6);
                Whole_WarehouseDetails_table.addCell(phrasecompanyDetailsTitlecell);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                Phrase phrasecompanyAddressDetails = new Phrase("(Unit of Culinary Triangle Private Ltd)\n \nOld No 4, New No 50, Kumaraswamy\nStreet, Lakshmipuram, Chromepet,\nChennai – 44 ,India.\nGSTIN 33AAJCC0055D1Z9", valueFont_10);

                PdfPCell phrasecompanyAddressDetailscell = new PdfPCell(phrasecompanyAddressDetails);
                phrasecompanyAddressDetailscell.setBorder(Rectangle.NO_BORDER);
                phrasecompanyAddressDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasecompanyAddressDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasecompanyAddressDetailscell.setPaddingBottom(10);
                phrasecompanyAddressDetailscell.setPaddingLeft(6);

                Whole_WarehouseDetails_table.addCell(phrasecompanyAddressDetailscell);

            } catch (Exception e) {
                e.printStackTrace();
            }











            PdfPTable Whole_SupplerDetails_table = new PdfPTable(new float[] { 40,60  });
            try{

                try {


                    Phrase phraseretailerNameLabelTitle = new Phrase("Store Name :  ", valueFont_10Bold);

                    PdfPCell phraseretailerNameLabelTitlecell = new PdfPCell(phraseretailerNameLabelTitle);
                    phraseretailerNameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseretailerNameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phraseretailerNameLabelTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phraseretailerNameLabelTitlecell.setPaddingLeft(6);
                    phraseretailerNameLabelTitlecell.setPaddingBottom(10);
                    Whole_SupplerDetails_table.addCell(phraseretailerNameLabelTitlecell);


                    Phrase phraseRetailerNameTitle = new Phrase(retailername+"\n", valueFont_10);

                    PdfPCell phraseretailerNameTitlecell = new PdfPCell(phraseRetailerNameTitle);
                    phraseretailerNameTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseretailerNameTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phraseretailerNameTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phraseretailerNameTitlecell.setPaddingBottom(10);


                    Whole_SupplerDetails_table.addCell(phraseretailerNameTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {


                    Phrase phraseretailerNameLabelTitle = new Phrase("Mobile Number :  ", valueFont_10Bold);

                    PdfPCell phraseretailerNameLabelTitlecell = new PdfPCell(phraseretailerNameLabelTitle);
                    phraseretailerNameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseretailerNameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phraseretailerNameLabelTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phraseretailerNameLabelTitlecell.setPaddingLeft(6);
                    phraseretailerNameLabelTitlecell.setPaddingBottom(10);
                    Whole_SupplerDetails_table.addCell(phraseretailerNameLabelTitlecell);

                    String text = "";
                    text = retailermobileno.replaceAll("[+]91","");
                    Phrase phraseRetailerNameTitle = new Phrase(text+"\n", valueFont_10);

                    PdfPCell phraseretailerNameTitlecell = new PdfPCell(phraseRetailerNameTitle);
                    phraseretailerNameTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseretailerNameTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phraseretailerNameTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phraseretailerNameTitlecell.setPaddingBottom(10);


                    Whole_SupplerDetails_table.addCell(phraseretailerNameTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {


                    Phrase phraseretailerNameLabelTitle = new Phrase("Address :   ", valueFont_10Bold);

                    PdfPCell phraseretailerNameLabelTitlecell = new PdfPCell(phraseretailerNameLabelTitle);
                    phraseretailerNameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseretailerNameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phraseretailerNameLabelTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phraseretailerNameLabelTitlecell.setPaddingLeft(6);
                    phraseretailerNameLabelTitlecell.setPaddingBottom(10);
                    Whole_SupplerDetails_table.addCell(phraseretailerNameLabelTitlecell);


                    Phrase phraseRetailerNameTitle = new Phrase(retaileraddress+"\n", valueFont_10);

                    PdfPCell phraseretailerNameTitlecell = new PdfPCell(phraseRetailerNameTitle);
                    phraseretailerNameTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseretailerNameTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phraseretailerNameTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phraseretailerNameTitlecell.setPaddingBottom(10);


                    Whole_SupplerDetails_table.addCell(phraseretailerNameTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }



            } catch (Exception e) {
                e.printStackTrace();
            }





            try {
                PdfPCell Whole_WarehouseDetails_table_Cell = new PdfPCell(Whole_WarehouseDetails_table);
                Whole_WarehouseDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                Whole_WarehouseDetails_table_Cell.setBorderWidthRight(01);
                Whole_Warehouse_and_RetailerDetails_table.addCell(Whole_WarehouseDetails_table_Cell);



                PdfPCell Whole_SupplerDetails_table_Cell = new PdfPCell(Whole_SupplerDetails_table);
                Whole_SupplerDetails_table_Cell.setPaddingTop(5);
                Whole_SupplerDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                Whole_Warehouse_and_RetailerDetails_table.addCell(Whole_SupplerDetails_table_Cell);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                PdfPCell Whole_Warehouse_and_RetailerDetails_table_Cell = new PdfPCell(Whole_Warehouse_and_RetailerDetails_table);
                Whole_Warehouse_and_RetailerDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                wholePDFContentWithOut_Outline_table.addCell(Whole_Warehouse_and_RetailerDetails_table_Cell);

            } catch (Exception e) {
                e.printStackTrace();
            }




            try{
                PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] { 35, 20 , 22 , 23  });



                try {


                    Phrase phraseEarTagDetailsLabelTitle = new Phrase("Ear Tag Numbers    ", valueFont_10Bold);

                    PdfPCell phraseEarTagDetailsLabelTitlecell = new PdfPCell(phraseEarTagDetailsLabelTitle);
                    phraseEarTagDetailsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseEarTagDetailsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phraseEarTagDetailsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                    phraseEarTagDetailsLabelTitlecell.setBorderWidthRight(01);
                    phraseEarTagDetailsLabelTitlecell.setPaddingLeft(6);
                    phraseEarTagDetailsLabelTitlecell.setPaddingTop(5);
                    phraseEarTagDetailsLabelTitlecell.setPaddingBottom(10);
                    itemDetailsLabel_table.addCell(phraseEarTagDetailsLabelTitlecell);




                    Phrase phrasQuantityLabelTitle = new Phrase("Quantity    ", valueFont_10Bold);

                    PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                    phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                    phraseQuantityLabelTitlecell.setBorderWidthRight(01);
                    phraseQuantityLabelTitlecell.setPaddingTop(5);
                    phraseQuantityLabelTitlecell.setPaddingLeft(6);
                    phraseQuantityLabelTitlecell.setPaddingBottom(10);
                    itemDetailsLabel_table.addCell(phraseQuantityLabelTitlecell);




                    Phrase phrasBatchpriceLabelTitle = new Phrase("Batch Price  ( Rs )  ", valueFont_10Bold);

                    PdfPCell phraseBatchPriceLabelTitlecell = new PdfPCell(phrasBatchpriceLabelTitle);
                    phraseBatchPriceLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseBatchPriceLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phraseBatchPriceLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                    phraseBatchPriceLabelTitlecell.setBorderWidthRight(01);
                    phraseBatchPriceLabelTitlecell.setPaddingLeft(6);
                    phraseBatchPriceLabelTitlecell.setPaddingTop(5);
                    phraseBatchPriceLabelTitlecell.setPaddingBottom(10);
                    itemDetailsLabel_table.addCell(phraseBatchPriceLabelTitlecell);


                    Phrase phrasTotalLabelTitle = new Phrase("Total  ( Rs )  ", valueFont_10Bold);

                    PdfPCell phraseTotalLabelTitlecell = new PdfPCell(phrasTotalLabelTitle);
                    phraseTotalLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseTotalLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phraseTotalLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                    phraseTotalLabelTitlecell.setPaddingTop(5);
                    phraseTotalLabelTitlecell.setPaddingLeft(6);
                    phraseTotalLabelTitlecell.setPaddingBottom(10);
                    itemDetailsLabel_table.addCell(phraseTotalLabelTitlecell);

                }
                catch (Exception e){
                    e.printStackTrace();
                }




                try {
                    PdfPCell itemDetails_table_Cell = new PdfPCell(itemDetailsLabel_table);
                    itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                    itemDetails_table_Cell.setBorderWidthTop(1);
                    itemDetails_table_Cell.setBorderWidthBottom(1);
                    itemDetails_table_Cell.setBackgroundColor(LIGHT_GRAY);
                    wholePDFContentWithOut_Outline_table.addCell(itemDetails_table_Cell);


                }
                catch (Exception e) {
                    e.printStackTrace();
                }





                try{
                    PdfPTable itemDetailstext_table = new PdfPTable(new float[] { 35, 20 , 22 , 23  });

                    String barcodeArrayIntoString ="";
                    try{
                        if (SDK_INT >= Build.VERSION_CODES.O) {
                            barcodeArrayIntoString = String.join(", ", earTagDetailsArrayList_String);
                        }
                        else{
                            for(String barcode : earTagDetailsArrayList_String){
                                if(barcodeArrayIntoString.equals("")){
                                    barcodeArrayIntoString = barcode;
                                }
                                else{
                                    barcodeArrayIntoString = barcodeArrayIntoString +", "+barcode;
                                }
                            }
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                    try{

                        Phrase phraseEarTagDetailsTextTitle = new Phrase(barcodeArrayIntoString, valueFont_10);

                        PdfPCell phraseEarTagDetailsTextTitlecell = new PdfPCell(phraseEarTagDetailsTextTitle);
                        phraseEarTagDetailsTextTitlecell.setBorder(Rectangle.NO_BORDER);
                        phraseEarTagDetailsTextTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        phraseEarTagDetailsTextTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phraseEarTagDetailsTextTitlecell.setPaddingLeft(6);
                        phraseEarTagDetailsTextTitlecell.setPaddingTop(5);
                        phraseEarTagDetailsTextTitlecell.setBorderWidthRight(01);

                        phraseEarTagDetailsTextTitlecell.setPaddingBottom(105);
                        itemDetailstext_table.addCell(phraseEarTagDetailsTextTitlecell);




                        Phrase phrasQuantityTextTitle = new Phrase(String.valueOf(finalquantity), valueFont_8);

                        PdfPCell phraseQuantityTextTitlecell = new PdfPCell(phrasQuantityTextTitle);
                        phraseQuantityTextTitlecell.setBorder(Rectangle.NO_BORDER);
                        phraseQuantityTextTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phraseQuantityTextTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phraseQuantityTextTitlecell.setPaddingTop(5);
                        phraseQuantityTextTitlecell.setPaddingLeft(6);
                        phraseQuantityTextTitlecell.setPaddingBottom(25);
                        phraseQuantityTextTitlecell.setBorderWidthRight(01);

                        itemDetailstext_table.addCell(phraseQuantityTextTitlecell);




                        Phrase phrasBatchpriceTextTitle = new Phrase(String.valueOf(twoDecimalConverterWithComma.format(Double.parseDouble(String.valueOf(finalBatchValue_String)))), valueFont_8);

                        PdfPCell phraseBatchPriceTextTitlecell = new PdfPCell(phrasBatchpriceTextTitle);
                        phraseBatchPriceTextTitlecell.setBorder(Rectangle.NO_BORDER);
                        phraseBatchPriceTextTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phraseBatchPriceTextTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phraseBatchPriceTextTitlecell.setPaddingLeft(6);
                        phraseBatchPriceTextTitlecell.setPaddingTop(5);
                        phraseBatchPriceTextTitlecell.setBorderWidthRight(01);
                        phraseBatchPriceTextTitlecell.setPaddingBottom(25);
                        itemDetailstext_table.addCell(phraseBatchPriceTextTitlecell);


                        Phrase phrasTotalTextTitle = new Phrase(String.valueOf(twoDecimalConverterWithComma.format(Double.parseDouble(String.valueOf(finalGoatValue_String)))), valueFont_8);

                        PdfPCell phraseTotalTextTitlecell = new PdfPCell(phrasTotalTextTitle);
                        phraseTotalTextTitlecell.setBorder(Rectangle.NO_BORDER);
                        phraseTotalTextTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phraseTotalTextTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phraseTotalTextTitlecell.setPaddingTop(5);
                        phraseTotalTextTitlecell.setPaddingLeft(6);
                        phraseTotalTextTitlecell.setPaddingBottom(25);
                        itemDetailstext_table.addCell(phraseTotalTextTitlecell);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }







                    try {
                        PdfPCell itemDetails_table_Cell = new PdfPCell(itemDetailstext_table);
                        itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                        itemDetails_table_Cell.setBorderWidthBottom(1);

                        wholePDFContentWithOut_Outline_table.addCell(itemDetails_table_Cell);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                }
                catch (Exception e){
                    e.printStackTrace();
                }



                try{
                    PdfPTable feedDetailstext_table = new PdfPTable(new float[] { 35, 20 , 22 , 23  });


                    try{

                        Phrase phraseFeedTextTitle = new Phrase("Feed    ", valueFont_10);
                        PdfPCell phraseFeedDetailsTextTitlecell = new PdfPCell(phraseFeedTextTitle);
                        phraseFeedDetailsTextTitlecell.setBorder(Rectangle.NO_BORDER);
                        phraseFeedDetailsTextTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        phraseFeedDetailsTextTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phraseFeedDetailsTextTitlecell.setPaddingLeft(6);
                        phraseFeedDetailsTextTitlecell.setPaddingTop(8);
                        phraseFeedDetailsTextTitlecell.setBorderWidthRight(01);
                        phraseFeedDetailsTextTitlecell.setPaddingBottom(8);
                        feedDetailstext_table.addCell(phraseFeedDetailsTextTitlecell);


                        if(feedWeight.equals("")){
                            feedWeight = "0";
                        }
                        Phrase phrasQuantityTextTitle = new Phrase(String.valueOf(feedWeight), valueFont_8);

                        PdfPCell phraseQuantityTextTitlecell = new PdfPCell(phrasQuantityTextTitle);
                        phraseQuantityTextTitlecell.setBorder(Rectangle.NO_BORDER);
                        phraseQuantityTextTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phraseQuantityTextTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phraseQuantityTextTitlecell.setPaddingTop(8);
                        phraseQuantityTextTitlecell.setPaddingLeft(6);
                        phraseQuantityTextTitlecell.setPaddingBottom(8);
                        phraseQuantityTextTitlecell.setBorderWidthRight(01);

                        feedDetailstext_table.addCell(phraseQuantityTextTitlecell);


                        if(feedPricePerKg.equals("")){
                            feedPricePerKg = "0";
                        }
                        Phrase phrasBatchpriceTextTitle = new Phrase(twoDecimalConverterWithComma.format(Double.parseDouble(String.valueOf(feedPricePerKg))), valueFont_8);

                        PdfPCell phraseBatchPriceTextTitlecell = new PdfPCell(phrasBatchpriceTextTitle);
                        phraseBatchPriceTextTitlecell.setBorder(Rectangle.NO_BORDER);
                        phraseBatchPriceTextTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phraseBatchPriceTextTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phraseBatchPriceTextTitlecell.setPaddingLeft(6);
                        phraseBatchPriceTextTitlecell.setPaddingTop(8);
                        phraseBatchPriceTextTitlecell.setBorderWidthRight(01);
                        phraseBatchPriceTextTitlecell.setPaddingBottom(8);
                        feedDetailstext_table.addCell(phraseBatchPriceTextTitlecell);

                        if(feedTotalPrice.equals("")){
                            feedTotalPrice = "0";
                        }
                        Phrase phrasTotalTextTitle = new Phrase(twoDecimalConverterWithComma.format(Double.parseDouble(String.valueOf(feedTotalPrice))), valueFont_8);

                        PdfPCell phraseTotalTextTitlecell = new PdfPCell(phrasTotalTextTitle);
                        phraseTotalTextTitlecell.setBorder(Rectangle.NO_BORDER);
                        phraseTotalTextTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phraseTotalTextTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phraseTotalTextTitlecell.setPaddingTop(8);
                        phraseTotalTextTitlecell.setPaddingLeft(6);
                        phraseTotalTextTitlecell.setPaddingBottom(8);
                        feedDetailstext_table.addCell(phraseTotalTextTitlecell);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }







                    try {
                        PdfPCell itemDetails_table_Cell = new PdfPCell(feedDetailstext_table);
                        itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                        itemDetails_table_Cell.setBorderWidthBottom(1);

                        wholePDFContentWithOut_Outline_table.addCell(itemDetails_table_Cell);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                }
                catch (Exception e){
                    e.printStackTrace();
                }



            }
            catch (Exception e){
                e.printStackTrace();
            }




            PdfPTable totalAmountDetails_table = new PdfPTable(new float[] { 35, 42 , 23 });

            try{

                try {


                    Phrase phrasetotalDetailsTitle = new Phrase("Total ( Rs ) ", valueFont_10);

                    PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                    phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                    phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setPaddingBottom(4);
                    phrasetotalDetailsTitlecell.setBorderWidthRight(1);
                    phrasetotalDetailsTitlecell.setPaddingLeft(6);
                    totalAmountDetails_table.addCell(phrasetotalDetailsTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {


                    Phrase phrasetotalDetailsTitle = new Phrase("  ", valueFont_10Bold);

                    PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                    phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                    phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setPaddingBottom(4);
                    phrasetotalDetailsTitlecell.setPaddingLeft(6);
                    totalAmountDetails_table.addCell(phrasetotalDetailsTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {

                    Phrase phrasetotalDetailsTitle = new Phrase(twoDecimalConverterWithComma.format(Double.parseDouble(String.valueOf(finalgoatValueWithFeed_String))), valueFont_10);

                    PdfPCell phraseTotalDetailscell = new PdfPCell(phrasetotalDetailsTitle);
                    phraseTotalDetailscell.setBorder(Rectangle.NO_BORDER);
                    phraseTotalDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phraseTotalDetailscell.setVerticalAlignment(Element.ALIGN_CENTER);
                    phraseTotalDetailscell.setPaddingBottom(10);
                    phraseTotalDetailscell.setPaddingLeft(6);

                    totalAmountDetails_table.addCell(phraseTotalDetailscell);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            catch (Exception e){
                e.printStackTrace();
            }


            try {
                PdfPCell totalAmountDetails_table_Cell = new PdfPCell(totalAmountDetails_table);
                totalAmountDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                totalAmountDetails_table_Cell.setBorderWidthBottom(1);
                totalAmountDetails_table_Cell.setBackgroundColor(LIGHT_GRAY);
                wholePDFContentWithOut_Outline_table.addCell(totalAmountDetails_table_Cell);

            } catch (Exception e) {
                e.printStackTrace();
            }



            double discountDouble = 0 ;
            try {
                String text_finalfeedprice = (String.valueOf(discountAmount.toString()));
                text_finalfeedprice = text_finalfeedprice.replaceAll("[^\\d.]", "");
                if (text_finalfeedprice.equals("")) {
                    text_finalfeedprice = "0";
                }
                discountDouble = Double.parseDouble(text_finalfeedprice);

            } catch (Exception e) {
                e.printStackTrace();
            }




            if(discountDouble>0) {
                PdfPTable discountAmountDetails_table = new PdfPTable(new float[]{35, 42, 23});

                try {

                    try {


                        Phrase phrasetotalDetailsTitle = new Phrase("Discount ( Rs )  ", valueFont_10);

                        PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                        phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                        phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                        phrasetotalDetailsTitlecell.setPaddingBottom(4);
                        phrasetotalDetailsTitlecell.setBorderWidthRight(1);
                        phrasetotalDetailsTitlecell.setPaddingLeft(6);
                        discountAmountDetails_table.addCell(phrasetotalDetailsTitlecell);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    try {


                        Phrase phrasetotalDetailsTitle = new Phrase("  ", valueFont_10Bold);

                        PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                        phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                        phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                        phrasetotalDetailsTitlecell.setPaddingBottom(4);
                        phrasetotalDetailsTitlecell.setPaddingLeft(6);
                        discountAmountDetails_table.addCell(phrasetotalDetailsTitlecell);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {

                        Phrase phrasetotalDetailsTitle = new Phrase(twoDecimalConverterWithComma.format(Double.parseDouble(String.valueOf(discountAmount))), valueFont_10);

                        PdfPCell phraseTotalDetailscell = new PdfPCell(phrasetotalDetailsTitle);
                        phraseTotalDetailscell.setBorder(Rectangle.NO_BORDER);
                        phraseTotalDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phraseTotalDetailscell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phraseTotalDetailscell.setPaddingBottom(10);
                        phraseTotalDetailscell.setPaddingLeft(6);

                        discountAmountDetails_table.addCell(phraseTotalDetailscell);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        PdfPCell discountAmountDetails_table_Cell = new PdfPCell(discountAmountDetails_table);
                        discountAmountDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                        discountAmountDetails_table_Cell.setBorderWidthBottom(1);
                        wholePDFContentWithOut_Outline_table.addCell(discountAmountDetails_table_Cell);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }





            PdfPTable payableAmountDetails_table = new PdfPTable(new float[] { 35, 42 , 23 });

            try{

                try {


                    Phrase phrasePayableAmountDetailsTitle = new Phrase("Payable Amount ( Rs ) ", valueFont_10);

                    PdfPCell phrasePayableAmountTitlecell = new PdfPCell(phrasePayableAmountDetailsTitle);
                    phrasePayableAmountTitlecell.setBorder(Rectangle.NO_BORDER);
                    phrasePayableAmountTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phrasePayableAmountTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phrasePayableAmountTitlecell.setPaddingBottom(4);
                    phrasePayableAmountTitlecell.setBorderWidthRight(1);
                    phrasePayableAmountTitlecell.setPaddingLeft(6);
                    payableAmountDetails_table.addCell(phrasePayableAmountTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {


                    Phrase phrasetotalDetailsTitle = new Phrase("  ", valueFont_10Bold);

                    PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                    phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                    phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setPaddingBottom(4);
                    phrasetotalDetailsTitlecell.setPaddingLeft(6);
                    payableAmountDetails_table.addCell(phrasetotalDetailsTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {

                    Phrase phrasetotalDetailsTitle = new Phrase(String.valueOf(twoDecimalConverterWithComma.format(finalPayableprice)), valueFont_10);

                    PdfPCell phraseTotalDetailscell = new PdfPCell(phrasetotalDetailsTitle);
                    phraseTotalDetailscell.setBorder(Rectangle.NO_BORDER);
                    phraseTotalDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phraseTotalDetailscell.setVerticalAlignment(Element.ALIGN_CENTER);
                    phraseTotalDetailscell.setPaddingBottom(10);
                    phraseTotalDetailscell.setPaddingLeft(6);

                    payableAmountDetails_table.addCell(phraseTotalDetailscell);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
                    PdfPCell payableAmountDetails_table_Cell = new PdfPCell(payableAmountDetails_table);
                    payableAmountDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                    payableAmountDetails_table_Cell.setBackgroundColor(LIGHT_GRAY);
                    payableAmountDetails_table_Cell.setBorderWidthBottom(1);
                    wholePDFContentWithOut_Outline_table.addCell(payableAmountDetails_table_Cell);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            catch (Exception e){
                e.printStackTrace();
            }







        }
        catch (Exception e){
            e.printStackTrace();
        }




        PdfPTable receiverName_table = new PdfPTable(new float[] { 35, 33 , 32 });

        try{
            try {


                Phrase phrasePaymentModeDetailsTitle = new Phrase("Notes   ", valueFont_10);

                PdfPCell phrasePaymentmodeTitlecell = new PdfPCell(phrasePaymentModeDetailsTitle);
                phrasePaymentmodeTitlecell.setBorder(Rectangle.NO_BORDER);
                phrasePaymentmodeTitlecell.setHorizontalAlignment(Element.ALIGN_TOP);
                phrasePaymentmodeTitlecell.setVerticalAlignment(Element.ALIGN_RIGHT);
                phrasePaymentmodeTitlecell.setPaddingBottom(4);
                phrasePaymentmodeTitlecell.setBorderWidthRight(1);
                phrasePaymentmodeTitlecell.setPaddingLeft(6);
                receiverName_table.addCell(phrasePaymentmodeTitlecell);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {


                Phrase phrasePaymentModeDetailsTitle = new Phrase("For Culinary Triangle Pvt Ltd.  ", valueFont_10);

                PdfPCell phrasePaymentmodeTitlecell = new PdfPCell(phrasePaymentModeDetailsTitle);
                phrasePaymentmodeTitlecell.setBorder(Rectangle.NO_BORDER);
                phrasePaymentmodeTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setPaddingBottom(4);
                phrasePaymentmodeTitlecell.setBorderWidthRight(1);
                phrasePaymentmodeTitlecell.setPaddingLeft(6);
                receiverName_table.addCell(phrasePaymentmodeTitlecell);

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {


                Phrase phrasePaymentModeDetailsTitle = new Phrase(" ", valueFont_10);

                PdfPCell phrasePaymentmodeTitlecell = new PdfPCell(phrasePaymentModeDetailsTitle);
                phrasePaymentmodeTitlecell.setBorder(Rectangle.NO_BORDER);
                phrasePaymentmodeTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setPaddingBottom(4);
                phrasePaymentmodeTitlecell.setPaddingLeft(6);
                receiverName_table.addCell(phrasePaymentmodeTitlecell);

            } catch (Exception e) {
                e.printStackTrace();
            }



            try {


                Phrase phrasePaymentModeDetailsTitle = new Phrase(String.valueOf(notes), valueFont_10);

                PdfPCell phrasePaymentmodeTitlecell = new PdfPCell(phrasePaymentModeDetailsTitle);
                phrasePaymentmodeTitlecell.setBorder(Rectangle.NO_BORDER);
                phrasePaymentmodeTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setPaddingBottom(4);
                phrasePaymentmodeTitlecell.setBorderWidthRight(1);
                phrasePaymentmodeTitlecell.setPaddingLeft(6);
                receiverName_table.addCell(phrasePaymentmodeTitlecell);

            } catch (Exception e) {
                e.printStackTrace();
            }


            try {


                Phrase phrasetotalDetailsTitle = new Phrase("", valueFont_10);

                PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasetotalDetailsTitlecell.setPaddingBottom(4);
                phrasetotalDetailsTitlecell.setPaddingLeft(6);
                phrasetotalDetailsTitlecell.setBorderWidthRight(1);
                receiverName_table.addCell(phrasetotalDetailsTitlecell);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                Phrase paymentModeDetails_Title = new Phrase("", valueFont_10);

                PdfPCell phrasepaymentModecell = new PdfPCell(paymentModeDetails_Title);
                phrasepaymentModecell.setBorder(Rectangle.NO_BORDER);
                phrasepaymentModecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                phrasepaymentModecell.setVerticalAlignment(Element.ALIGN_CENTER);
                phrasepaymentModecell.setPaddingBottom(10);
                phrasepaymentModecell.setPaddingLeft(6);

                receiverName_table.addCell(phrasepaymentModecell);

            } catch (Exception e) {
                e.printStackTrace();
            }


            try {


                Phrase phrasePaymentModeDetailsTitle = new Phrase(" ", valueFont_10);

                PdfPCell phrasePaymentmodeTitlecell = new PdfPCell(phrasePaymentModeDetailsTitle);
                phrasePaymentmodeTitlecell.setBorder(Rectangle.NO_BORDER);
                phrasePaymentmodeTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setPaddingBottom(4);
                phrasePaymentmodeTitlecell.setPaddingLeft(6);
                phrasePaymentmodeTitlecell.setBorderWidthRight(1);
                receiverName_table.addCell(phrasePaymentmodeTitlecell);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {


                Phrase phrasePaymentModeDetailsTitle = new Phrase(" ", valueFont_10);

                PdfPCell phrasePaymentmodeTitlecell = new PdfPCell(phrasePaymentModeDetailsTitle);
                phrasePaymentmodeTitlecell.setBorder(Rectangle.NO_BORDER);
                phrasePaymentmodeTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setPaddingBottom(4);
                phrasePaymentmodeTitlecell.setPaddingLeft(6);
                phrasePaymentmodeTitlecell.setBorderWidthRight(1);
                receiverName_table.addCell(phrasePaymentmodeTitlecell);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {


                Phrase phrasePaymentModeDetailsTitle = new Phrase(" ", valueFont_10);

                PdfPCell phrasePaymentmodeTitlecell = new PdfPCell(phrasePaymentModeDetailsTitle);
                phrasePaymentmodeTitlecell.setBorder(Rectangle.NO_BORDER);
                phrasePaymentmodeTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setPaddingBottom(4);
                phrasePaymentmodeTitlecell.setPaddingLeft(6);
                receiverName_table.addCell(phrasePaymentmodeTitlecell);

            } catch (Exception e) {
                e.printStackTrace();
            }



            try {


                Phrase phrasePaymentModeDetailsTitle = new Phrase(" ", valueFont_10);

                PdfPCell phrasePaymentmodeTitlecell = new PdfPCell(phrasePaymentModeDetailsTitle);
                phrasePaymentmodeTitlecell.setBorder(Rectangle.NO_BORDER);
                phrasePaymentmodeTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setPaddingBottom(4);
                phrasePaymentmodeTitlecell.setPaddingLeft(6);
                phrasePaymentmodeTitlecell.setBorderWidthRight(1);
                receiverName_table.addCell(phrasePaymentmodeTitlecell);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {


                Phrase phrasePaymentModeDetailsTitle = new Phrase(" ", valueFont_10);

                PdfPCell phrasePaymentmodeTitlecell = new PdfPCell(phrasePaymentModeDetailsTitle);
                phrasePaymentmodeTitlecell.setBorder(Rectangle.NO_BORDER);
                phrasePaymentmodeTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setPaddingBottom(4);
                phrasePaymentmodeTitlecell.setPaddingLeft(6);
                phrasePaymentmodeTitlecell.setBorderWidthRight(1);
                receiverName_table.addCell(phrasePaymentmodeTitlecell);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {


                Phrase phrasePaymentModeDetailsTitle = new Phrase(" ", valueFont_10);

                PdfPCell phrasePaymentmodeTitlecell = new PdfPCell(phrasePaymentModeDetailsTitle);
                phrasePaymentmodeTitlecell.setBorder(Rectangle.NO_BORDER);
                phrasePaymentmodeTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setPaddingBottom(4);
                phrasePaymentmodeTitlecell.setPaddingLeft(6);
                receiverName_table.addCell(phrasePaymentmodeTitlecell);

            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                PdfPCell paymentModeDetails_table_Cell = new PdfPCell(receiverName_table);
                paymentModeDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                wholePDFContentWithOut_Outline_table.addCell(paymentModeDetails_table_Cell);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
//////////
        PdfPTable receiverSignature_table = new PdfPTable(new float[] { 35, 33 , 32 });

        try{



            try {


                Phrase phrasePaymentModeDetailsTitle = new Phrase(" ", valueFont_10);

                PdfPCell phrasePaymentmodeTitlecell = new PdfPCell(phrasePaymentModeDetailsTitle);
                phrasePaymentmodeTitlecell.setBorder(Rectangle.NO_BORDER);
                phrasePaymentmodeTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setPaddingLeft(6);
                phrasePaymentmodeTitlecell.setBorderWidthRight(1);
                receiverSignature_table.addCell(phrasePaymentmodeTitlecell);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {


                Phrase phrasePaymentModeDetailsTitle = new Phrase(" ", valueFont_10);

                PdfPCell phrasePaymentmodeTitlecell = new PdfPCell(phrasePaymentModeDetailsTitle);
                phrasePaymentmodeTitlecell.setBorder(Rectangle.NO_BORDER);
                phrasePaymentmodeTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setPaddingLeft(6);
                phrasePaymentmodeTitlecell.setBorderWidthRight(1);
                receiverSignature_table.addCell(phrasePaymentmodeTitlecell);

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {


                Phrase phrasePaymentModeDetailsTitle = new Phrase(" ", valueFont_10);

                PdfPCell phrasePaymentmodeTitlecell = new PdfPCell(phrasePaymentModeDetailsTitle);
                phrasePaymentmodeTitlecell.setBorder(Rectangle.NO_BORDER);
                phrasePaymentmodeTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setPaddingLeft(6);
                receiverSignature_table.addCell(phrasePaymentmodeTitlecell);

            } catch (Exception e) {
                e.printStackTrace();
            }



            try {


                Phrase phrasePaymentModeDetailsTitle = new Phrase(" ", valueFont_10);

                PdfPCell phrasePaymentmodeTitlecell = new PdfPCell(phrasePaymentModeDetailsTitle);
                phrasePaymentmodeTitlecell.setBorder(Rectangle.NO_BORDER);
                phrasePaymentmodeTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasePaymentmodeTitlecell.setPaddingBottom(4);
                phrasePaymentmodeTitlecell.setPaddingLeft(6);
                phrasePaymentmodeTitlecell.setBorderWidthRight(1);
                receiverSignature_table.addCell(phrasePaymentmodeTitlecell);

            } catch (Exception e) {
                e.printStackTrace();
            }


            try {


                Phrase phrasetotalDetailsTitle = new Phrase("Supervisor's Signature", valueFont_10);

                PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasetotalDetailsTitlecell.setPaddingBottom(4);
                phrasetotalDetailsTitlecell.setPaddingLeft(6);
                phrasetotalDetailsTitlecell.setBorderWidthRight(1);
                receiverSignature_table.addCell(phrasetotalDetailsTitlecell);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                Phrase paymentModeDetails_Title = new Phrase("Recipient's Name & Signature", valueFont_10);

                PdfPCell phrasepaymentModecell = new PdfPCell(paymentModeDetails_Title);
                phrasepaymentModecell.setBorder(Rectangle.NO_BORDER);
                phrasepaymentModecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasepaymentModecell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasepaymentModecell.setPaddingLeft(6);
                phrasepaymentModecell.setPaddingBottom(4);
                receiverSignature_table.addCell(phrasepaymentModecell);

            } catch (Exception e) {
                e.printStackTrace();
            }








            try {
                PdfPCell paymentModeDetails_table_Cell = new PdfPCell(receiverSignature_table);
                paymentModeDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                wholePDFContentWithOut_Outline_table.addCell(paymentModeDetails_table_Cell);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }




    /*

        try{
            PdfPTable thankyounoteDetails_table = new PdfPTable(new float[]{35, 42, 23});

            try {

                try {


                    Phrase phrasetotalDetailsTitle = new Phrase("   ", valueFont_10);

                    PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                    phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                    phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setPaddingBottom(4);
                    phrasetotalDetailsTitlecell.setPaddingLeft(6);
                    thankyounoteDetails_table.addCell(phrasetotalDetailsTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {


                    Phrase phrasetotalDetailsTitle = new Phrase("  ", valueFont_10Bold);

                    PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                    phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                    phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setPaddingBottom(4);
                    phrasetotalDetailsTitlecell.setPaddingLeft(6);
                    thankyounoteDetails_table.addCell(phrasetotalDetailsTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {

                    Phrase phrasetotalDetailsTitle = new Phrase(String.valueOf(""), valueFont_10);

                    PdfPCell phraseTotalDetailscell = new PdfPCell(phrasetotalDetailsTitle);
                    phraseTotalDetailscell.setBorder(Rectangle.NO_BORDER);
                    phraseTotalDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phraseTotalDetailscell.setVerticalAlignment(Element.ALIGN_CENTER);
                    phraseTotalDetailscell.setPaddingBottom(10);
                    phraseTotalDetailscell.setPaddingLeft(6);

                    thankyounoteDetails_table.addCell(phraseTotalDetailscell);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {


                    Phrase phrasetotalDetailsTitle = new Phrase("   ", valueFont_10);

                    PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                    phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                    phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setPaddingBottom(4);
                    phrasetotalDetailsTitlecell.setPaddingLeft(6);
                    thankyounoteDetails_table.addCell(phrasetotalDetailsTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {


                    Phrase phrasetotalDetailsTitle = new Phrase("  ", valueFont_10Bold);

                    PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                    phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                    phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setPaddingBottom(4);
                    phrasetotalDetailsTitlecell.setPaddingLeft(6);
                    thankyounoteDetails_table.addCell(phrasetotalDetailsTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {

                    Phrase phrasetotalDetailsTitle = new Phrase(String.valueOf(""), valueFont_10);

                    PdfPCell phraseTotalDetailscell = new PdfPCell(phrasetotalDetailsTitle);
                    phraseTotalDetailscell.setBorder(Rectangle.NO_BORDER);
                    phraseTotalDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phraseTotalDetailscell.setVerticalAlignment(Element.ALIGN_CENTER);
                    phraseTotalDetailscell.setPaddingBottom(10);
                    phraseTotalDetailscell.setPaddingLeft(6);

                    thankyounoteDetails_table.addCell(phraseTotalDetailscell);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    PdfPCell thankyouDetails_table_Cell = new PdfPCell(thankyounoteDetails_table);
                    thankyouDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                    thankyouDetails_table_Cell.setPaddingBottom(20);
                    wholePDFContentWithOut_Outline_table.addCell(thankyouDetails_table_Cell);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


     */







        try {
            tmcLogoImage_table.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tmcLogoImage_table.setTotalWidth(100f);

            layoutDocument.add(tmcLogoImage_table);



            PdfPCell wholePDFWithOutBordercell = new PdfPCell(wholePDFContentWithOut_Outline_table);
            wholePDFWithOutBordercell.setCellEvent(roundRectange);
            wholePDFWithOutBordercell.setPadding(1);
            wholePDFWithOutBordercell.setBorder(Rectangle.NO_BORDER);
            wholePDFContentOutline_table.addCell(wholePDFWithOutBordercell);
            wholePDFContentOutline_table.setWidthPercentage(100);


            layoutDocument.add(wholePDFContentOutline_table);

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            PdfPTable CreditDetails_table_withBorder = new PdfPTable(1);

            PdfPTable CreditDetails_table_withOutBorder = new PdfPTable(1);

            //EMPTY ROWS STARTED
            //3 EMPTYROWS WILL BE ADDED
            try {
                PdfPTable EMPTYROWS_table = new PdfPTable(new float[]{50, 50});
                // 1st line
                try {


                    Phrase phrasetotalDetailsTitle = new Phrase("  ", valueFont_10Bold);

                    PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                    phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                    phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setPaddingBottom(4);
                    phrasetotalDetailsTitlecell.setPaddingLeft(6);
                    EMPTYROWS_table.addCell(phrasetotalDetailsTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {


                    Phrase phrasetotalDetailsTitle = new Phrase("  ", valueFont_10Bold);

                    PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                    phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                    phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setPaddingBottom(4);
                    phrasetotalDetailsTitlecell.setPaddingLeft(6);
                    EMPTYROWS_table.addCell(phrasetotalDetailsTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }



                try {


                    EMPTYROWS_table.setWidthPercentage(100);

                    layoutDocument.add(EMPTYROWS_table);
                } catch (Exception e) {
                    e.printStackTrace();
                }



                /*
                PdfPTable EMPTYROWS_table = new PdfPTable(new float[]{50, 50});
                // 1st line
                try {


                    Phrase phrasetotalDetailsTitle = new Phrase("  ", valueFont_10Bold);

                    PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                    phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                    phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setPaddingBottom(4);
                    phrasetotalDetailsTitlecell.setPaddingLeft(6);
                    EMPTYROWS_table.addCell(phrasetotalDetailsTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {


                    Phrase phrasetotalDetailsTitle = new Phrase("  ", valueFont_10Bold);

                    PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                    phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                    phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setPaddingBottom(4);
                    phrasetotalDetailsTitlecell.setPaddingLeft(6);
                    EMPTYROWS_table.addCell(phrasetotalDetailsTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //2nd line

                try {


                    Phrase phrasetotalDetailsTitle = new Phrase("  ", valueFont_10Bold);

                    PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                    phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                    phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setPaddingBottom(4);
                    phrasetotalDetailsTitlecell.setPaddingLeft(6);
                    EMPTYROWS_table.addCell(phrasetotalDetailsTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {


                    Phrase phrasetotalDetailsTitle = new Phrase("  ", valueFont_10Bold);

                    PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                    phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                    phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setPaddingBottom(4);
                    phrasetotalDetailsTitlecell.setPaddingLeft(6);
                    EMPTYROWS_table.addCell(phrasetotalDetailsTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //3rd line
                try {


                    Phrase phrasetotalDetailsTitle = new Phrase("  ", valueFont_10Bold);

                    PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                    phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                    phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setPaddingBottom(4);
                    phrasetotalDetailsTitlecell.setPaddingLeft(6);
                    EMPTYROWS_table.addCell(phrasetotalDetailsTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {


                    Phrase phrasetotalDetailsTitle = new Phrase("  ", valueFont_10Bold);

                    PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                    phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                    phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phrasetotalDetailsTitlecell.setPaddingBottom(4);
                    phrasetotalDetailsTitlecell.setPaddingLeft(6);
                    EMPTYROWS_table.addCell(phrasetotalDetailsTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    PdfPCell paymentModeDetails_table_Cell = new PdfPCell(EMPTYROWS_table);
                    paymentModeDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                    paymentModeDetails_table_Cell.setBackgroundColor(LIGHT_GRAY);
                    paymentModeDetails_table_Cell.setBorderWidthBottom(1);
                    CreditDetails_table_withOutBorder.addCell(paymentModeDetails_table_Cell);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                 */

            } catch (Exception e) {
                e.printStackTrace();
            }


            PdfPTable creditAmountDetails_table_label = new PdfPTable(new float[]{35, 33, 32});


            try {
                try {


                    Phrase phraseoutstandingDetailsTitle = new Phrase("Opening Balance (Rs) ", valueFont_10);

                    PdfPCell phraseoutstandingTitlecell = new PdfPCell(phraseoutstandingDetailsTitle);
                    phraseoutstandingTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseoutstandingTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phraseoutstandingTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                    phraseoutstandingTitlecell.setPaddingBottom(4);
                    phraseoutstandingTitlecell.setBorderWidthRight(1);
                    phraseoutstandingTitlecell.setBorderWidthBottom(1);
                    phraseoutstandingTitlecell.setPaddingLeft(6);
                    phraseoutstandingTitlecell.setBackgroundColor(LIGHT_GRAY);
                    creditAmountDetails_table_label.addCell(phraseoutstandingTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {


                    Phrase phraseCurrentBillValueTitle = new Phrase("Bill Amount (Rs) ", valueFont_10);

                    PdfPCell phraseCurrentBillValuecell = new PdfPCell(phraseCurrentBillValueTitle);
                    phraseCurrentBillValuecell.setBorder(Rectangle.NO_BORDER);
                    phraseCurrentBillValuecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phraseCurrentBillValuecell.setVerticalAlignment(Element.ALIGN_CENTER);
                    phraseCurrentBillValuecell.setPaddingBottom(4);
                    phraseCurrentBillValuecell.setBorderWidthBottom(1);
                    phraseCurrentBillValuecell.setBackgroundColor(LIGHT_GRAY);
                    phraseCurrentBillValuecell.setBorderWidthRight(1);
                    phraseCurrentBillValuecell.setPaddingLeft(6);
                    creditAmountDetails_table_label.addCell(phraseCurrentBillValuecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {


                    Phrase phrasenewoutstandingDetailsTitle = new Phrase("Closing Balance (Rs)", valueFont_10);

                    PdfPCell phraseoutstandingTitlecell = new PdfPCell(phrasenewoutstandingDetailsTitle);
                    phraseoutstandingTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseoutstandingTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phraseoutstandingTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                    phraseoutstandingTitlecell.setPaddingBottom(4);
                    phraseoutstandingTitlecell.setBorderWidthRight(1);
                    phraseoutstandingTitlecell.setBorderWidthBottom(1);
                    phraseoutstandingTitlecell.setBackgroundColor(LIGHT_GRAY);
                    phraseoutstandingTitlecell.setPaddingLeft(6);
                    creditAmountDetails_table_label.addCell(phraseoutstandingTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {

                    Phrase outStandingAmountDetails_Title = new Phrase(String.valueOf(twoDecimalConverterWithComma.format(oldCreditAmount)), valueFont_10);

                    PdfPCell phraseoutStandingAmountcell = new PdfPCell(outStandingAmountDetails_Title);
                    phraseoutStandingAmountcell.setBorder(Rectangle.NO_BORDER);
                    phraseoutStandingAmountcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phraseoutStandingAmountcell.setVerticalAlignment(Element.ALIGN_CENTER);
                    phraseoutStandingAmountcell.setPaddingBottom(10);
                    phraseoutStandingAmountcell.setPaddingLeft(1);
                    phraseoutStandingAmountcell.setBorderWidthRight(1);

                    creditAmountDetails_table_label.addCell(phraseoutStandingAmountcell);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {

                    Phrase currenBillValue_Title = new Phrase(String.valueOf(twoDecimalConverterWithComma.format(transactionAmount)), valueFont_10);

                    PdfPCell phrasecurrenBillValuecell = new PdfPCell(currenBillValue_Title);
                    phrasecurrenBillValuecell.setBorder(Rectangle.NO_BORDER);
                    phrasecurrenBillValuecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phrasecurrenBillValuecell.setVerticalAlignment(Element.ALIGN_CENTER);
                    phrasecurrenBillValuecell.setPaddingBottom(10);
                    phrasecurrenBillValuecell.setPaddingLeft(1);
                    phrasecurrenBillValuecell.setBorderWidthRight(1);

                    creditAmountDetails_table_label.addCell(phrasecurrenBillValuecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {

                    Phrase outStandingAmountDetails_Title = new Phrase(String.valueOf(twoDecimalConverterWithComma.format(newCreditAmount)), valueFont_10);

                    PdfPCell phraseoutStandingAmountcell = new PdfPCell(outStandingAmountDetails_Title);
                    phraseoutStandingAmountcell.setBorder(Rectangle.NO_BORDER);
                    phraseoutStandingAmountcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phraseoutStandingAmountcell.setVerticalAlignment(Element.ALIGN_CENTER);
                    phraseoutStandingAmountcell.setPaddingBottom(10);
                    phraseoutStandingAmountcell.setPaddingLeft(1);
                    phraseoutStandingAmountcell.setBorderWidthRight(1);
                    creditAmountDetails_table_label.addCell(phraseoutStandingAmountcell);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    PdfPCell paymentModeDetails_table_Cell = new PdfPCell(creditAmountDetails_table_label);
                    paymentModeDetails_table_Cell.setBorder(Rectangle.NO_BORDER);

                    CreditDetails_table_withOutBorder.addCell(paymentModeDetails_table_Cell);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            try {


                PdfPCell wholePDFWithOutBordercell = new PdfPCell(CreditDetails_table_withOutBorder);
                wholePDFWithOutBordercell.setCellEvent(roundRectange);
                wholePDFWithOutBordercell.setPadding(1);
                wholePDFWithOutBordercell.setBorder(Rectangle.NO_BORDER);
                CreditDetails_table_withBorder.addCell(wholePDFWithOutBordercell);
                CreditDetails_table_withBorder.setWidthPercentage(100);


                layoutDocument.add(CreditDetails_table_withBorder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void call_and_ExecuteCreditTransactionHistory(String callMethod, Modal_B2BOrderDetails modal_b2BOrderDetails) {
        showProgressBar(true);
        if(isRetailerCreditTransactionHistoryCalled){
            return;
        }
        transactionAmount = 0 ;
        oldCreditAmount = 0;
        newCreditAmount = 0;
        isRetailerCreditTransactionHistoryCalled = true;


        callB2BCreditTransactionHistoryInterface  = new B2BCreditTransactionHistoryInterface() {
            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal__B2BCreditTransactionHistory> creditTransactionHistoryArrayListt) {
                isRetailerCreditTransactionHistoryCalled = false;

                showProgressBar(false);


            }

            @Override
            public void notifySuccess(String result) {
                if(result.equals(Constants.emptyResult_volley)){
                 //   showProgressBar(false);
                    Toast.makeText(ViewOrderList.this, "There is no Credit transaction details for this date", Toast.LENGTH_SHORT).show();
                }

                try{
                    String text = String.valueOf(Modal__B2BCreditTransactionHistory.getOldamountincredit_static());
                    text = text.replaceAll("[^\\d.]", "");
                    if(text.equals("")){
                        text = "0";
                    }

                    oldCreditAmount  = Double.parseDouble(text);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                try{
                    String text = String.valueOf(Modal__B2BCreditTransactionHistory.getNewamountincredit_static());
                    text = text.replaceAll("[^\\d.]", "");
                    if(text.equals("")){
                        text = "0";
                    }

                    newCreditAmount  = Double.parseDouble(text);
                }
                catch (Exception e){
                    e.printStackTrace();
                }


                try{
                    String text = String.valueOf(Modal__B2BCreditTransactionHistory.getTransactionvalue_static());
                    text = text.replaceAll("[^\\d.]", "");
                    if(text.equals("")){
                        text = "0";
                    }

                    transactionAmount  = Double.parseDouble(text);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                Create_and_SharePdf(false, modal_b2BOrderDetails);

                isRetailerCreditTransactionHistoryCalled = false;
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                Create_and_SharePdf(false, modal_b2BOrderDetails);
                isRetailerCreditTransactionHistoryCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                isRetailerCreditTransactionHistoryCalled = false;
                Create_and_SharePdf(false, modal_b2BOrderDetails);
            }
        };

        if(callMethod.equals(Constants.CallGETMethod)){

            String getApiToCall = API_Manager.getCreditTransactionHistoryUsingDeliveryCentrekeyWithOrderid +"?deliverycentrekey=" +deliveryCenterKey+"&orderid="+modal_b2BOrderDetails.getOrderid();
            B2BCreditTransactionHistory asyncTask = new B2BCreditTransactionHistory(callB2BCreditTransactionHistoryInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }
    }

    public class RoundRectangle implements PdfPCellEvent {
        public void cellLayout(PdfPCell cell, Rectangle rect,
                               PdfContentByte[] canvas) {
            PdfContentByte cb = canvas[PdfPTable.LINECANVAS];
            cb.setColorStroke(GRAY);
            cb.roundRectangle(
                    rect.getLeft() + 1.5f, rect.getBottom() + 1.5f, rect.getWidth() - 3,
                    rect.getHeight() - 3, 4);
            cb.stroke();
        }
    }

    public PdfPCell addLogo(Document document) throws DocumentException {
        PdfPCell cellImage ;
        try { // Get user Settings GeneralSettings getUserSettings =

            Rectangle rectDoc = document.getPageSize();
            float width = rectDoc.getWidth();
            float height = rectDoc.getHeight()+90;
            float imageStartX = width - document.rightMargin() - 3315f;
            float imageStartY = height - document.topMargin() - 280f;

            System.gc();

            InputStream ims = getAssets().open("tmc_logo_purple.png"); // image from assets folder
            Bitmap bmp = BitmapFactory.decodeStream(ims);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bmp.compress(Bitmap.CompressFormat.PNG, 80, stream);

            byte[] byteArray = stream.toByteArray();
            // PdfImage img = new PdfImage(arg0, arg1, arg2)

            // Converting byte array into image Image img =
            Image img = Image.getInstance(byteArray); // img.scalePercent(50);
            img.setAlignment(Image.ALIGN_RIGHT );
            img.scaleAbsolute(130f, 130f);
            img.setAbsolutePosition(90f, 120f); // Adding Image
            img.setTransparency (new int [] { 0x00, 0x10 });
            img.setWidthPercentage(100);
            img.setScaleToFitHeight(true);
            cellImage= new PdfPCell(img, false);
            cellImage.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellImage.setVerticalAlignment(Element.ALIGN_RIGHT);
            return cellImage;

        } catch (Exception e) {
            e.printStackTrace();
            cellImage= new PdfPCell();
            return cellImage;

        }

    }


    private void Intialize_and_Execute_OrderDetailsList(String callMethod, String fromDate, String todate, boolean isCalledFromOnCreate) {



        // newFromDate_long<startingfromDate_long
        if(isCalledFromOnCreate  ) {
            startingfromDate_long = Long.parseLong(DateParser.getLongValuefortheDate(fromDate+" 00:00:00"));

            showProgressBar(true);
            if (isB2BOrderTableServiceCalled) {
                // showProgressBar(false);
                return;
            }
            isB2BOrderTableServiceCalled = true;
            callback_b2bOrderDetails = new B2BOrderDetailsInterface() {


                @Override
                public void notifySuccessForGettingListItem(ArrayList<Modal_B2BOrderDetails> arrayList) {
                    if (arrayList.size() > 0) {
                        noentryForrequestedDate = 1;
                        isTheOrderPlacedForTheLastRequestedDate = true;
                        orderDetailsArrayList.addAll(arrayList);
                        // showProgressBar(false);
                        isB2BOrderTableServiceCalled = false;
                        callAdapter(orderDetailsArrayList);
                    } else {
                        noentryForrequestedDate = noentryForrequestedDate +1;

                        isTheOrderPlacedForTheLastRequestedDate = false;
                        showProgressBar(false);
                        loadingPB.setVisibility(View.INVISIBLE);
                       isfetchMoreDateAlertShown = false;
                    }
                }

                @Override
                public void notifySuccess(String result) {
                    showProgressBar(false);
                    isTheOrderPlacedForTheLastRequestedDate = false;
                    isB2BOrderTableServiceCalled = false;
                    isfetchMoreDateAlertShown = false;
                }

                @Override
                public void notifyVolleyError(VolleyError error) {
                    noentryForrequestedDate = noentryForrequestedDate +1;
                    Toast.makeText(ViewOrderList.this, "There is an volley error while Fetching Order Details", Toast.LENGTH_SHORT).show();
                    showProgressBar(false);
                    isB2BOrderTableServiceCalled = false;
                    isfetchMoreDateAlertShown = false;
                    loadingPB.setVisibility(View.INVISIBLE);
                }

                @Override
                public void notifyProcessingError(Exception error) {
                    noentryForrequestedDate = noentryForrequestedDate +1;
                    Toast.makeText(ViewOrderList.this, "There is an Process error while Fetching Order Details", Toast.LENGTH_SHORT).show();
                    loadingPB.setVisibility(View.INVISIBLE);
                    isfetchMoreDateAlertShown = false;
                    showProgressBar(false);
                    isB2BOrderTableServiceCalled = false;


                }


            };

            if (callMethod.equals(Constants.CallGETListMethod)) {
                //String getApiToCall = API_Manager.getOrderDetailsForBatchno+"?batchno="+batchno ;
              //  String getApiToCall = API_Manager.getOrderDetailsForDeliveryCentrekeyFromTodateWithStatus + "?deliverycentrekey=" + deliveryCenterKey + "&fromdate=" + fromDate + " 00:00:00" + "&todate=" + todate + " 23:59:59" + "&status=" + Constants.orderDetailsStatus_Delivered;
                String getApiToCall = API_Manager.getOrderDetailsForDeliveryCentrekeyFromTodateWithoutCancelledStatus + "?deliverycentrekey=" + deliveryCenterKey + "&fromdate=" + fromDate + " 00:00:00" + "&todate=" + todate + " 23:59:59" ;

                B2BOrderDetails asyncTask = new B2BOrderDetails(callback_b2bOrderDetails, getApiToCall, callMethod);
                asyncTask.execute();

            }
        }
        else{
            newFromDate_long = Long.parseLong(DateParser.getLongValuefortheDate(fromDate+" 00:00:00"));
            if(startingfromDate_long>newFromDate_long) {
                showProgressBar(true);
                if (isB2BOrderTableServiceCalled) {
                    // showProgressBar(false);
                    return;
                }
                isB2BOrderTableServiceCalled = true;
                callback_b2bOrderDetails = new B2BOrderDetailsInterface() {


                    @Override
                    public void notifySuccessForGettingListItem(ArrayList<Modal_B2BOrderDetails> arrayList) {
                        if (arrayList.size() > 0) {
                            noentryForrequestedDate = 1;
                            isTheOrderPlacedForTheLastRequestedDate = true;
                            orderDetailsArrayList.addAll(arrayList);
                            // showProgressBar(false);
                            isB2BOrderTableServiceCalled = false;
                            callAdapter(orderDetailsArrayList);
                        } else {
                            isTheOrderPlacedForTheLastRequestedDate = false;
                            showProgressBar(false);
                            noentryForrequestedDate = noentryForrequestedDate +1;
                            loadingPB.setVisibility(View.INVISIBLE);
                            isB2BOrderTableServiceCalled = false;
                            isfetchMoreDateAlertShown = false;
                        }
                    }

                    @Override
                    public void notifySuccess(String result) {
                        showProgressBar(false);
                        isB2BOrderTableServiceCalled = false;
                        isTheOrderPlacedForTheLastRequestedDate = false;
                        loadingPB.setVisibility(View.INVISIBLE);
                        noentryForrequestedDate = noentryForrequestedDate +1;
                        isfetchMoreDateAlertShown = false;
                    }

                    @Override
                    public void notifyVolleyError(VolleyError error) {
                        Toast.makeText(ViewOrderList.this, "There is an volley error while Fetching Order Details", Toast.LENGTH_SHORT).show();
                        showProgressBar(false);
                        noentryForrequestedDate = noentryForrequestedDate +1;
                        isB2BOrderTableServiceCalled = false;
                        isfetchMoreDateAlertShown = false;
                        isB2BOrderTableServiceCalled = false;
                        isTheOrderPlacedForTheLastRequestedDate = false;
                        loadingPB.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void notifyProcessingError(Exception error) {

                        Toast.makeText(ViewOrderList.this, "There is an Process error while Fetching Order Details", Toast.LENGTH_SHORT).show();
                        loadingPB.setVisibility(View.INVISIBLE);
                        isfetchMoreDateAlertShown = false;
                        showProgressBar(false);
                        noentryForrequestedDate = noentryForrequestedDate +1;
                        isB2BOrderTableServiceCalled = false;
                        isTheOrderPlacedForTheLastRequestedDate = false;
                        isB2BOrderTableServiceCalled = false;


                    }


                };

                if (callMethod.equals(Constants.CallGETListMethod)) {
                    //String getApiToCall = API_Manager.getOrderDetailsForBatchno+"?batchno="+batchno ;
                 //   String getApiToCall = API_Manager.getOrderDetailsForDeliveryCentrekeyFromTodateWithStatus + "?deliverycentrekey=" + deliveryCenterKey + "&fromdate=" + fromDate + " 00:00:00" + "&todate=" + todate + " 23:59:59" + "&status=" + Constants.orderDetailsStatus_Delivered;
                    String getApiToCall = API_Manager.getOrderDetailsForDeliveryCentrekeyFromTodateWithoutCancelledStatus + "?deliverycentrekey=" + deliveryCenterKey + "&fromdate=" + fromDate + " 00:00:00" + "&todate=" + todate + " 23:59:59" ;

                    B2BOrderDetails asyncTask = new B2BOrderDetails(callback_b2bOrderDetails, getApiToCall, callMethod);
                    asyncTask.execute();

                }
            }
        }
    }

    public void callAdapter(ArrayList<Modal_B2BOrderDetails> orderDetailsArrayList) {

        orderDetailsArrayList = sortTheArrayUsingorderplacedDate (orderDetailsArrayList);

        isfetchMoreDateAlertShown = false;


        loadingPB.setVisibility(View.INVISIBLE);
        earTagItems_recyclerview.setVisibility(View.VISIBLE);
        Adapter_ViewOrdersList_recyclerView adapter_viewOrdersList_recyclerView = new Adapter_ViewOrdersList_recyclerView(ViewOrderList.this,orderDetailsArrayList,ViewOrderList.this,"markasdeliveredorderscreen",ViewOrderList.this);
        earTagItems_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        earTagItems_recyclerview.setHasFixedSize(true);




        earTagItems_recyclerview.setAdapter(adapter_viewOrdersList_recyclerView);
        ordersCount_textwidget.setText(String.valueOf(orderDetailsArrayList.size()));
        ///  Adapter_Viewstockbalance_batchwise adapter_viewstockbalance_batchwise = new Adapter_Viewstockbalance_batchwise(ViewStockBalance.this,batchDetailsArrayList,ViewStockBalance.this,"Viewstockbalance",earTagDetailsList);
        //  tableLayout_listview.setAdapter(adapter_viewstockbalance_batchwise);
        showProgressBar(false);

   
   
    }

    private ArrayList<Modal_B2BOrderDetails> sortTheArrayUsingorderplacedDate(ArrayList<Modal_B2BOrderDetails> orderDetailsArrayList) {

        Collections.sort(orderDetailsArrayList, new Comparator<Modal_B2BOrderDetails>() {
            public int compare(final Modal_B2BOrderDetails object1, final Modal_B2BOrderDetails object2) {

                String orderplacedtime1 = "" , orderplacedtime2="";

                String orderplacedlong1 = "0" , orderplacedlong2 = "0";

                orderplacedtime1 = object1.getOrderplaceddate();
                orderplacedtime2 = object2.getOrderplaceddate();

                orderplacedlong1  = (DateParser.getLongValuefortheDate(orderplacedtime1));
                orderplacedlong2  = (DateParser.getLongValuefortheDate(orderplacedtime2));

                object2.setOrderplacedlong(orderplacedlong2);
                object1.setOrderplacedlong(orderplacedlong1);

                return object2.getOrderplacedlong().compareTo(object1.getOrderplacedlong());
            }
        });


    return orderDetailsArrayList;
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


    private void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void closeSearchBarEditText() {
        retailerName_textView.setVisibility(View.VISIBLE);
        close_IconLayout.setVisibility(View.GONE);
        search_IconLayout.setVisibility(View.VISIBLE);
        retailerName_editText.setVisibility(View.GONE);
    }

    private void showSearchBarEditText() {
        retailerName_textView.setVisibility(View.GONE);
        close_IconLayout.setVisibility(View.VISIBLE);
        search_IconLayout.setVisibility(View.GONE);
        retailerName_editText.setVisibility(View.VISIBLE);
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


    private static ArrayList<String> sortThisArrayUsingBarcode(ArrayList<String> earTagDetailsList) {


        final Pattern p = Pattern.compile("^\\d+");
        Comparator<String> c = new Comparator<String>() {
            @Override
            public int compare(String object1, String object2) {
                Matcher m = p.matcher(object1);
                Integer number1 = null;
                if (!m.find()) {
                    Matcher m1 = p.matcher(object2);
                    if (m1.find()) {
                        return object2.compareTo(object1);
                    } else {
                        return object1.compareTo(object2);
                    }
                } else {
                    Integer number2 = null;
                    number1 = Integer.parseInt(m.group());
                    m = p.matcher(object2);
                    if (!m.find()) {
                        // return object1.compareTo(object2);
                        Matcher m1 = p.matcher(object1);
                        if (m1.find()) {
                            return object2.compareTo(object1);
                        } else {
                            return object1.compareTo(object2);
                        }
                    } else {
                        number2 = Integer.parseInt(m.group());
                        int comparison = number1.compareTo(number2);
                        if (comparison != 0) {
                            return comparison;
                        } else {
                            return object1.compareTo(object2);
                        }
                    }
                }
            }
        };

        Collections.sort(earTagDetailsList, c);





        return  earTagDetailsList;
    }



}