package com.tmc.tmcb2bpartnerapp.second_version.activity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.app.Dialog;
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
import android.os.Message;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.tmc.tmcb2bpartnerapp.adapter.Adapter_AutoComplete_RetailerMobileNo;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartItemDetails_BulkUpdate;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BTokenNoManager;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagDetails_BulkUpdate;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartItemDetaillsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartItemDetails_BulkUpdateInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderItemDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BRetailerDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BTokenNoManagerInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagDetails_BulkUpdateInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagTransactionInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetailsStatic;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetailsUpdate;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_UpdatedB2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal_Array_to_PlaceOrder;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal_B2BRetailerCreditDetails;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal_B2BRetailerCreditDetailsUpdate;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal__B2BCreditTransactionHistory;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal__B2BPaymentDetails;
import com.tmc.tmcb2bpartnerapp.second_version.apiRequestServices.B2BCreditTransactionHistory;
import com.tmc.tmcb2bpartnerapp.second_version.apiRequestServices.B2BPaymentDetails;
import com.tmc.tmcb2bpartnerapp.second_version.apiRequestServices.B2BRetailerCreditDetails;
import com.tmc.tmcb2bpartnerapp.second_version.interfaces.B2BCreditTransactionHistoryInterface;
import com.tmc.tmcb2bpartnerapp.second_version.interfaces.B2BPaymentDetailsInterface;
import com.tmc.tmcb2bpartnerapp.second_version.interfaces.B2BRetailerCreditDetailsInterface;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.TMCAlertDialogClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;
import static com.itextpdf.text.BaseColor.BLACK;
import static com.itextpdf.text.BaseColor.GRAY;
import static com.itextpdf.text.BaseColor.LIGHT_GRAY;

public class CheckOut_activity extends BaseActivity {

    //widgets
    public AutoCompleteTextView retailerMobileNo_edittext , retailerName_edittext;
    public  static TextView retailerName_textView , retailerAddress_textView  ,male_Qty_textview , male_percentage_textview ,finaltotalWeight_textView,
            female_Qty_textview,female_Percentage_textview ,approxLiveWeightAvg_Textview ,meatYieldWeightAvg_Textview,totalValue_textView,male_female_ratio_textview
            ,totalGoats_textView,batchValue_textView , finalGoatValue_textView , finalFeedValue_textView,finalPayment_textView;
    static  TextView totalFeedPriceTextview , totalPaymentmode_textView;

    static Spinner totalPaymentmode_Spinner;

    public EditText feedWeight_editText , feedPricePerKg_editText ,discountValue_editText ,notes_edittext  ;
    Button checkoutFromCart_Button ;
    static LinearLayout loadingPanel  ;
    public static LinearLayout loadingpanelmask ,orderItemDetails_Linearlayout;
    static LinearLayout back_IconLayout;
    
    
    //String 
    public String deliveryCentreKey ="" , supervisormobileno ="" , supervisorName = "" , selectedPaymentMode =Constants.CREDIT;
    public String orderid ="";
    public String deliveryCentreName ="";
    public String usermobileno_string ="";
    public String retailername="" , feedweight ="";
    public  String retailermobileno ="" , feedPricePerKg ="";
    public String retaileraddress ="" , feedPrice ="";
    public String retailerGSTIN ="";
    public  String retailerKey ="";
    public String oldRetailerKey ="";
    public String oldretailerMobileno = "";
    public String oldRetailerName = "";
    public String oldretaileraddress = "";
    public String oldretailerGSTIN ="";
    public String orderplaceddate ="";
    public static String discount_String ="";
    String tokenNo ="";

    //double
    double approx_Live_Weight_double = 0 , meatyeild_weight_double = 0 , parts_Weight_double = 0 , totalWeight_double = 0 , discountDouble = 0 ,
            totalPrice_double = 0 ,     pricewithOutdiscount_double = 0;
    static double final_totalPriceWithOutDiscountWithoutFeed = 0 , final_totalPriceWithOutDiscountWithFeedAmount =0, final_batchValue = 0, final_discountValue = 0 , final_totalPayment = 0 , final_totalWeight =0 ;
    double oldCreditAmountOfUser= 0 , newCreditAmountOfUser = 0;

    //int
    static int  final_totalGoats =0;



    //boolean 
    boolean isPDF_FIle = false;
    boolean  isRetailerDetailsServiceCalled = false ;
    public boolean isRetailerSelected = false;
    boolean isB2BCartOrderTableServiceCalled = false;
    boolean isOrderItemDetailsServiceCalled = false;
    boolean isOrderDetailsServiceCalled = false;
    boolean isGoatEarTagDetailsTableServiceCalled = false;
    boolean isGoatEarTagTransactionTableServiceCalled = false;
    boolean isB2BCartItemDetailsBulkUpdate = false;
    boolean isNoretailerAlertShown = false;
    boolean isB2BCartItemDetailsCalled = false;
    boolean  isBatchDetailsTableServiceCalled = false;
    boolean  isB2BTokenNoCalled  = false;
    boolean isPaymentNotSelectedManually = false;
    boolean isRetailerCreditDetailsCalled = false;
    boolean isRetailerCreditDetailsIsNotCreated = false;
    boolean isRetailerCreditTransactionHistoryCalled = false;
    boolean isPaymentDetailsCalled = false;


    //ArrayList & Hashmap
    public static HashMap<String, Modal_B2BCartItemDetails> cartItemDetails_Hashmap = new HashMap<>();
    public static ArrayList<String> cartItemDetailsList_String = new ArrayList<>();
    public static ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList = new ArrayList<>();
    public static ArrayList<Modal_GoatEarTagDetails> earTagDetailsArrayList_WholeBatch = new ArrayList<>();
    public static HashMap<String,ArrayList<String>> batchwiseEartagDetails_Hashmap = new HashMap<>();
    public static TreeMap<String, JSONObject> batchwise_itemDespHashmap = new TreeMap<>();
    public static ArrayList<String> batchList_String = new ArrayList<>();








    //int
    private static int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;


    //interface
    B2BRetailerDetailsInterface callback_retailerDetailsInterface = null;
    B2BCartOrderDetailsInterface b2BCartOrderDetailsInterface =null ;
    B2BOrderItemDetailsInterface callback_b2BOrderItemDetailsInterface ;
    B2BOrderDetailsInterface callback_b2BOrderDetailsInterface ;
    B2BCartItemDetaillsInterface callback_b2BCartItemDetaillsInterface = null;
    GoatEarTagDetails_BulkUpdateInterface goatEarTagDetailsBulkUpdateInterface;
    GoatEarTagTransactionInterface callback_GoatEarTagTransactionInterface = null;
    B2BCartItemDetails_BulkUpdateInterface callbackB2BCartItemDetails_bulkUpdateInterface;
    B2BBatchDetailsInterface callback_B2BBatchDetailsInterface = null;
    B2BTokenNoManagerInterface callB2BTokenNoManagerInterface = null;
    B2BRetailerCreditDetailsInterface callBackb2BRetailerCreditDetailsInterface = null;
    B2BCreditTransactionHistoryInterface callB2BCreditTransactionHistoryInterface = null;
    B2BPaymentDetailsInterface callB2BPaymentDetailsInterface = null;

    
    
    
    //Adapter
    Adapter_AutoComplete_RetailerMobileNo adapter_autoComplete_retailerMobileNo;


    public static Dialog show_retailerItemDetails_Dialog = null;


    static DecimalFormat threeDecimalConverter = new DecimalFormat(Constants.threeDecimalPattern);
    static DecimalFormat twoDecimalConverter = new DecimalFormat(Constants.twoDecimalPattern);
    static DecimalFormat twoDecimalConverterWithComma = new DecimalFormat(Constants.twoDecimalWithCommaPattern);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        retailerAddress_textView = findViewById(R.id.retailerAddress_textView);
        retailerName_textView = findViewById(R.id.retailerName_textView);
        retailerMobileNo_edittext = findViewById(R.id.receiverMobileNo_edittext);
        totalFeedPriceTextview = findViewById(R.id.totalFeedPriceTextview);
        male_Qty_textview = findViewById(R.id.male_Qty_textview);
        male_percentage_textview = findViewById(R.id.male_percentage_textview);
        female_Qty_textview = findViewById(R.id.female_Qty_textview);
        female_Percentage_textview = findViewById(R.id.female_Percentage_textview);
        approxLiveWeightAvg_Textview = findViewById(R.id.approxLiveWeightAvg_Textview);
        meatYieldWeightAvg_Textview = findViewById(R.id.meatYieldWeightAvg_Textview);
        totalGoats_textView = findViewById(R.id.totalGoats_textView);
        batchValue_textView = findViewById(R.id.batchValue_textView);
        finaltotalWeight_textView  = findViewById(R.id.finaltotalWeight_textView);
        finalGoatValue_textView = findViewById(R.id.finalGoatValue_textView);
        finalFeedValue_textView = findViewById(R.id.finalFeedValue_textView);
        feedWeight_editText = findViewById(R.id.feedWeight_editText);
        feedPricePerKg_editText = findViewById(R.id.feedPricePerKg_editText);
        discountValue_editText = findViewById(R.id.discountValue_editText);
        checkoutFromCart_Button = findViewById(R.id.checkoutFromCart_Button);
        finalPayment_textView = findViewById(R.id.finalPayment_textView);
        loadingPanel = findViewById(R.id.loadingPanel);
        loadingpanelmask = findViewById(R.id.loadingpanelmask);
        totalValue_textView  = findViewById(R.id.totalValue_textView);
        batchValue_textView  = findViewById(R.id.batchValue_textView);
        retailerName_edittext  = findViewById(R.id.retailerName_edittext);
        back_IconLayout = findViewById(R.id.back_IconLayout);
        totalPaymentmode_Spinner = findViewById(R.id.totalPaymentmode_Spinner);
        totalPaymentmode_textView = findViewById(R.id.totalPaymentmode_textView);
        notes_edittext  = findViewById(R.id.notes_edittext);


        cartItemDetails_Hashmap .clear();
        retailerDetailsArrayList.clear();
        cartItemDetailsList_String.clear();
        batchwiseEartagDetails_Hashmap.clear();
        retailermobileno ="" ;retaileraddress ="";
        retailername = "" ; retailerKey = "" ;
        feedweight = "" ; feedPricePerKg = "" ;
        feedPrice ="" ; discount_String = "";

        selectedPaymentMode = String.valueOf(Modal_Array_to_PlaceOrder.getPaymentMode());
        if(selectedPaymentMode.equals("")){
            selectedPaymentMode = Constants.CREDIT;
        }


        retailermobileno = String.valueOf(Modal_Array_to_PlaceOrder.getRetailerMobileNo());
        retaileraddress = String.valueOf(Modal_Array_to_PlaceOrder.getRetailerAddress());
        retailername = String.valueOf(Modal_Array_to_PlaceOrder.getRetailerName());
        retailerKey = String.valueOf(Modal_Array_to_PlaceOrder.getRetailerKey());
        feedweight = String.valueOf(Modal_Array_to_PlaceOrder.getFeedweight());
        feedPricePerKg = String.valueOf(Modal_Array_to_PlaceOrder.getFeedPricePerKg());
        feedPrice = String.valueOf(Modal_Array_to_PlaceOrder.getFeedPrice());
        discount_String = String.valueOf(Modal_Array_to_PlaceOrder.getDiscountValue());


        cartItemDetails_Hashmap = Modal_Array_to_PlaceOrder.getCartItemDetails_Hashmap();
        cartItemDetailsList_String = Modal_Array_to_PlaceOrder.getCartItemDetailsList_String();
        batchwiseEartagDetails_Hashmap= Modal_Array_to_PlaceOrder.getBatchwiseEartagDetails_Hashmap();

        finalFeedValue_textView.setText(String.valueOf(feedPrice));
        totalFeedPriceTextview.setText(String.valueOf(feedPrice));
        feedPricePerKg_editText.setText(String.valueOf(feedPricePerKg));
        feedWeight_editText.setText(String.valueOf(feedweight));
        if(retailermobileno.replaceAll("[+]91","").length() ==10){
            isRetailerSelected = true;
        }
        else{
            isRetailerSelected = false;
        }

        retailerAddress_textView.setText(String.valueOf(retaileraddress));
        retailerName_textView.setText(String.valueOf(retailername));

        retailerName_edittext.setText(String.valueOf(retailername));
        retailerName_edittext.clearFocus();
        retailerName_edittext.setThreshold(1);
        retailerName_edittext.dismissDropDown();
        retailerMobileNo_edittext.setText(String.valueOf(retailermobileno));
        retailerMobileNo_edittext.clearFocus();
        retailerMobileNo_edittext.setThreshold(1);
        retailerMobileNo_edittext.dismissDropDown();
        discountValue_editText.setText(discount_String);


        SharedPreferences sh1 =  getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);

        deliveryCentreKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCentreName = sh1.getString("DeliveryCenterName", "");

        SharedPreferences sh = getSharedPreferences("LoginData",MODE_PRIVATE);
        usermobileno_string = sh.getString("UserMobileNumber","");
        supervisormobileno = sh.getString("UserMobileNumber","");
        supervisorName = sh.getString("UserName","");
      
        Intent intent = getIntent();
        orderid = intent.getStringExtra(String.valueOf("orderid"));

        addDatatoOrderTypeSpinner();
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Create_and_SharePdf(true);

        if(DatabaseArrayList_PojoClass.retailerDetailsArrayList.size() == 0){
            try {
                call_and_init_B2BRetailerDetailsService(Constants.CallGETListMethod, false,"");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            retailerDetailsArrayList = DatabaseArrayList_PojoClass.retailerDetailsArrayList;
            setAdapterForRetailerDetails(true);
            setAdapterForRetailerDetails(false);

        }
        
        
        if(cartItemDetails_Hashmap.size() == 0 || cartItemDetailsList_String.size() == 0 || orderid .equals("")){
            if((cartItemDetails_Hashmap.size() == 0 || cartItemDetailsList_String.size() == 0 ) && !orderid .equals("")) {
                Call_And_Execute_B2BCartOrderItem_Details(Constants.CallGETListMethod);

            }
            else{
                Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallGETMethod, "", false);

            }
            }
        else{
            CalculateAndSetTotal_Quantity_Price_Values();
        }


        totalPaymentmode_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if (isPaymentNotSelectedManually) {
                    isPaymentNotSelectedManually = false;
                    showProgressBar(false);
                } else {
                    selectedPaymentMode = parent.getItemAtPosition(position).toString();
                    showAlert_toUpdateCartOrderDetails(getString(R.string.paymentmode));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });
        
        
        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




        loadingpanelmask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CheckOut_activity.this, "Please Wait", Toast.LENGTH_SHORT).show();
            }
        });

        checkoutFromCart_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRetailerSelected) {


                    double feedWeight_double = 0 , feedPrice_double =0 , feedPricePerKg_double = 0;

                    try {
                        String text = String.valueOf(discountValue_editText.getText().toString());
                        text = text.replaceAll("[^\\d.]", "");

                        if (text.equals("")) {
                            text = "0";
                        }
                        discountDouble = Double.parseDouble(text);


                    } catch (Exception e) {
                        discountDouble =0;
                        e.printStackTrace();
                    }


                    try{
                        discountDouble = Double.parseDouble( twoDecimalConverter.format(discountDouble));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                    try {
                        String text = String.valueOf(feedWeight_editText.getText().toString());
                        text = text.replaceAll("[^\\d.]", "");

                        if (text.equals("")) {
                            text = "0";
                        }
                        feedWeight_double = Double.parseDouble(text);


                    } catch (Exception e) {
                        feedWeight_double =0;
                        e.printStackTrace();
                    }


                    try {
                        String text = String.valueOf(feedPricePerKg_editText.getText().toString());
                        text = text.replaceAll("[^\\d.]", "");

                        if (text.equals("")) {
                            text = "0";
                        }
                        feedPricePerKg_double = Double.parseDouble(text);


                    } catch (Exception e) {
                        feedPricePerKg_double =0;
                        e.printStackTrace();
                    }
                    try{
                        feedPricePerKg_double = Double.parseDouble( twoDecimalConverter.format(feedPricePerKg_double));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                    try {
                        String text = String.valueOf(finalFeedValue_textView.getText().toString());
                        text = text.replaceAll("[^\\d.]", "");

                        if (text.equals("")) {
                            text = "0";
                        }
                        feedPrice_double = Double.parseDouble(text);


                    } catch (Exception e) {
                        feedPricePerKg_double =0;
                        e.printStackTrace();
                    }

                    try{
                        feedPrice_double = Double.parseDouble( twoDecimalConverter.format(feedPrice_double));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                    if(feedPricePerKg_double >0 && feedWeight_double > 0 ){
                        if(feedPrice_double>0){
                            if (cartItemDetailsList_String.size() > 0) {
                                new TMCAlertDialogClass(CheckOut_activity.this, R.string.app_name, R.string.PleaseConfirmPlaceOrder,
                                        R.string.Yes_Text, R.string.No_Text,
                                        new TMCAlertDialogClass.AlertListener() {
                                            @Override
                                            public void onYes() {

                                                try {
                                                    showProgressBar(true);
                                                    orderplaceddate = DateParser.getDate_and_time_newFormat();
                                                    orderid = String.valueOf(orderid);


                                                    Call_and_Generate_tokenNo(Constants.CallGENERATEMethod);
                                                   // Call_and_Execute_RetailerCreditDetails(Constants.CallGETMethod);
                                                  //  Call_and_ExecuteB2BBatchOrderDetails(Constants.CallUPDATEMethod);
                                                    //call_and_init_B2BOrderItemDetailsService(Constants.CallADDMethod);
                                                  //  Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallDELETEMethod, "", false);
                                                  //  Initialize_and_ExecuteB2BCartItemDetails_BulkUpdate(Constants.CallDELETEMethod);
                                                 //   call_and_init_GoatEarTagDetails_BulkUpdate(Constants.CallUPDATEMethod);


                                                    // Create_and_SharePdf(false);


                                                    //  UpdateDataInSharedPreference();
                                                    //   ((BillingScreen) CheckOut_activity.this).Create_and_SharePdf();

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }


                                            }

                                            @Override
                                            public void onNo() {


                                            }
                                        });
                            } else {
                                AlertDialogClass.showDialog(CheckOut_activity.this, R.string.PleaseAddGoatItemBrforePlaceOrder);

                            }

                        }
                        else{
                            AlertDialogClass.showDialog(CheckOut_activity.this, R.string.Please_Fill_correct_FeedValueDetails_Instruction);

                        }
                    }
                    else{
                        if (cartItemDetailsList_String.size() > 0) {
                            new TMCAlertDialogClass(CheckOut_activity.this, R.string.app_name, R.string.PleaseConfirmPlaceOrder,
                                    R.string.Yes_Text, R.string.No_Text,
                                    new TMCAlertDialogClass.AlertListener() {
                                        @Override
                                        public void onYes() {

                                            try {
                                                showProgressBar(true);
                                                orderplaceddate = DateParser.getDate_and_time_newFormat();
                                                orderid = String.valueOf(orderid);


                                                Call_and_Generate_tokenNo(Constants.CallGENERATEMethod);
                                               // Call_and_Execute_RetailerCreditDetails(Constants.CallGETMethod);
                                             //   Call_and_ExecuteB2BBatchOrderDetails(Constants.CallUPDATEMethod);

                                           //     call_and_init_B2BOrderItemDetailsService(Constants.CallADDMethod);
                                                //Initialize_and_ExecuteB2BOrderDetails(Constants.CallADDMethod);
                                            //    Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallDELETEMethod, "", false);
                                              //  Initialize_and_ExecuteB2BCartItemDetails_BulkUpdate(Constants.CallDELETEMethod);
                                                //call_and_init_GoatEarTagDetails_BulkUpdate(Constants.CallUPDATEMethod);


                                                // Create_and_SharePdf(false);


                                                //  UpdateDataInSharedPreference();
                                                //   ((BillingScreen) CheckOut_activity.this).Create_and_SharePdf();

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                        }

                                        @Override
                                        public void onNo() {


                                        }
                                    });
                        } else {
                            AlertDialogClass.showDialog(CheckOut_activity.this, R.string.PleaseAddGoatItemBrforePlaceOrder);

                        }
                    }


                } else {
                    AlertDialogClass.showDialog(CheckOut_activity.this, R.string.Please_Select_Retailer_Instruction);

                }
            }
        });



        feedWeight_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                double feedWeight_double = 0 , feedPrice_double =0 , feedPricePerKg_double = 0;


                try {
                    String text = String.valueOf(feedWeight_editText.getText().toString());
                    text = text.replaceAll("[^\\d.]", "");

                    if (text.equals("")) {
                        text = "0";
                    }
                    feedWeight_double = Double.parseDouble(text);


                } catch (Exception e) {
                    feedWeight_double =0;
                    e.printStackTrace();
                }


                try {
                    String text = String.valueOf(feedPricePerKg_editText.getText().toString());
                    text = text.replaceAll("[^\\d.]", "");

                    if (text.equals("")) {
                        text = "0";
                    }
                    feedPricePerKg_double = Double.parseDouble(text);


                } catch (Exception e) {
                    feedPricePerKg_double =0;
                    e.printStackTrace();
                }


                try {

                    feedPrice_double = feedWeight_double * feedPricePerKg_double;

                } catch (Exception e) {
                    feedPrice_double  = 0;
                    e.printStackTrace();
                }



                totalFeedPriceTextview.setText(String.valueOf(twoDecimalConverter.format(feedPrice_double)));
                finalFeedValue_textView.setText(String.valueOf(twoDecimalConverter.format(feedPrice_double)));

                try {

                    showAlert_toUpdateCartOrderDetails(getString(R.string.feedvalue));
                    //    Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallUPDATEMethod, getString(R.string.feedvalue), false);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                CalculateAndSetTotal_Quantity_Price_Values();

                return false;
            }
        });



        feedPricePerKg_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {


                double feedWeight_double = 0 , feedPrice_double =0 , feedPricePerKg_double = 0;


                try {
                    String text = String.valueOf(feedWeight_editText.getText().toString());
                    text = text.replaceAll("[^\\d.]", "");

                    if (text.equals("")) {
                        text = "0";
                    }
                    feedWeight_double = Double.parseDouble(text);


                } catch (Exception e) {
                    feedWeight_double =0;
                    e.printStackTrace();
                }


                try {
                    String text = String.valueOf(feedPricePerKg_editText.getText().toString());
                    text = text.replaceAll("[^\\d.]", "");

                    if (text.equals("")) {
                        text = "0";
                    }
                    feedPricePerKg_double = Double.parseDouble(text);


                } catch (Exception e) {
                    feedPricePerKg_double =0;
                    e.printStackTrace();
                }


                try {

                    feedPrice_double = feedWeight_double * feedPricePerKg_double;

                } catch (Exception e) {
                    feedPrice_double  = 0;
                    e.printStackTrace();
                }



                totalFeedPriceTextview.setText(String.valueOf(twoDecimalConverter.format(feedPrice_double)));
                finalFeedValue_textView.setText(String.valueOf(twoDecimalConverter.format(feedPrice_double)));

                try {
                    showAlert_toUpdateCartOrderDetails(getString(R.string.feedvalue));

                    // Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallUPDATEMethod, getString(R.string.feedvalue), false);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                CalculateAndSetTotal_Quantity_Price_Values();


                return false;
            }
        });

        discountValue_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                try {
                    try {
                        String text = String.valueOf(discountValue_editText.getText().toString());
                        text = text.replaceAll("[^\\d.]", "");

                        if (text.equals("")) {
                            text = "0";
                        }
                        discount_String = text;
                        final_discountValue = Double.parseDouble(text);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    try {
                        String text = String.valueOf(totalValue_textView.getText().toString());
                        text = text.replaceAll("[^\\d.]", "");
                        if (text.equals("")) {
                            text = "0";
                        }
                        final_totalPriceWithOutDiscountWithFeedAmount = Double.parseDouble(text);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(final_discountValue<=final_totalPriceWithOutDiscountWithFeedAmount){



                        try {
                            final_totalPayment = final_totalPriceWithOutDiscountWithFeedAmount - final_discountValue;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }




                        try{
                            final_totalPayment = Double.parseDouble( twoDecimalConverter.format(final_totalPayment));
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                        try {


                            finalPayment_textView.setText(String.valueOf(final_totalPayment));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                        try {

                            showAlert_toUpdateCartOrderDetails(getString(R.string.discount));
                            //    Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallUPDATEMethod, getString(R.string.discount), false);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                    else {

                        AlertDialogClass.showDialog(CheckOut_activity.this, R.string.CannotSaveWhendiscountisgreaterthanpriceisZeroAlert);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                return false;
            }
        });




    }

    private void Call_and_Execute_RetailerCreditDetails(String callMethod) {


        if(isRetailerCreditDetailsCalled){
            return;
        }

        showProgressBar(true);
        isRetailerCreditDetailsCalled = true;
        callBackb2BRetailerCreditDetailsInterface = new B2BRetailerCreditDetailsInterface() {
            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BRetailerCreditDetails> retailerDetailsArrayList) {

            }

            @Override
            public void notifySuccess(String result) {

                isRetailerCreditDetailsCalled = false;

                if(result.equals(Constants.emptyResult_volley)){
                    isRetailerCreditDetailsIsNotCreated = true;
                    oldCreditAmountOfUser = 0;
                    newCreditAmountOfUser = Double.parseDouble(String.valueOf(twoDecimalConverter.format(final_totalPayment)));
                    Call_All_Other_OrderDetailsMethod();
                }
                else {
                    if (callMethod.equals(Constants.CallGETMethod)) {
                        isRetailerCreditDetailsIsNotCreated = false;
                        String text = "";
                        try {

                            text = String.valueOf(Modal_B2BRetailerCreditDetails.getTotalamountincredit_static().toString()).replaceAll("[^\\d.]", "");
                            if (text.equals("")) {
                                text = "0";
                            } else {
                                text = text;
                            }
                        } catch (Exception e) {
                            text = "0";
                            e.printStackTrace();
                        }

                        try {
                            oldCreditAmountOfUser = Double.parseDouble(text);
                        } catch (Exception e) {
                            oldCreditAmountOfUser = 0;
                            e.printStackTrace();
                        }


                        try{
                            oldCreditAmountOfUser = Double.parseDouble( twoDecimalConverter.format(oldCreditAmountOfUser));
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                        try {
                            newCreditAmountOfUser = oldCreditAmountOfUser + Double.parseDouble(String.valueOf(twoDecimalConverter.format(final_totalPayment)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try{
                            newCreditAmountOfUser = Double.parseDouble( twoDecimalConverter.format(newCreditAmountOfUser));
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        do {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                  @Override
                                public void run() {
                                    Call_All_Other_OrderDetailsMethod();
                                }
                            }, 1000);
                        }
                        while (isB2BTokenNoCalled);
                    }
                }

                //Initialize_and_ExecuteB2BOrderDetails(Constants.CallADDMethod);
               // Call_and_ExecuteB2BBatchOrderDetails(Constants.CallUPDATEMethod);
                //this method called inside B2BOrder Details
                // call_and_init_B2BOrderItemDetailsService(Constants.CallADDMethod);
                //Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallDELETEMethod, "", false);
                //Initialize_and_ExecuteB2BCartItemDetails_BulkUpdate(Constants.CallDELETEMethod);
                //call_and_init_GoatEarTagDetails_BulkUpdate(Constants.CallUPDATEMethod);

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isRetailerCreditDetailsCalled = false;
                Toast.makeText(CheckOut_activity.this, "Volley error in Executing RetailerCredit Details", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void notifyProcessingError(Exception error) {
                isRetailerCreditDetailsCalled = false;
                Toast.makeText(CheckOut_activity.this, "Processing error in Executing RetailerCredit Details", Toast.LENGTH_SHORT).show();

            }
        };

        if(callMethod.equals(Constants.CallGETMethod)) {
            oldCreditAmountOfUser = 0;
            newCreditAmountOfUser = 0;
            String getApiToCall = API_Manager.getRetailerCreditDetailsUsingRetailerKeyDeliveryCentrekey +"?deliverycentrekey="+ deliveryCentreKey+"&retailerkey="+retailerKey;
            B2BRetailerCreditDetails asyncTask = new B2BRetailerCreditDetails(callBackb2BRetailerCreditDetailsInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }
        else   if(callMethod.equals(Constants.CallADDMethod)) {
            Modal_B2BRetailerCreditDetails .setDeliverycentrekey_static(deliveryCentreKey);
            Modal_B2BRetailerCreditDetails.setLastupdatedtime_static(DateParser.getDate_and_time_newFormat());
            Modal_B2BRetailerCreditDetails.setRetailerkey_static(retailerKey);
            Modal_B2BRetailerCreditDetails.setRetailermobileno_static(retailermobileno);
            Modal_B2BRetailerCreditDetails.setRetailername_static(retailername);
            Modal_B2BRetailerCreditDetails.setTotalamountincredit_static(String.valueOf(twoDecimalConverter.format(final_totalPayment)));
            String getApiToCall = API_Manager.addRetailerCreditDetails;
            B2BRetailerCreditDetails asyncTask = new B2BRetailerCreditDetails(callBackb2BRetailerCreditDetailsInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }
        else   if(callMethod.equals(Constants.CallUPDATEMethod)) {
            Modal_B2BRetailerCreditDetails.setDeliverycentrekey_static(deliveryCentreKey);
            Modal_B2BRetailerCreditDetails.setLastupdatedtime_static(DateParser.getDate_and_time_newFormat());
            Modal_B2BRetailerCreditDetails.setRetailerkey_static(retailerKey);
            Modal_B2BRetailerCreditDetails.setRetailermobileno_static(retailermobileno);
            Modal_B2BRetailerCreditDetails.setRetailername_static(retailername);
            Modal_B2BRetailerCreditDetails.setTotalamountincredit_static(String.valueOf(twoDecimalConverter.format(newCreditAmountOfUser)));
            String getApiToCall = API_Manager.updateRetailerCreditDetails;
            B2BRetailerCreditDetails asyncTask = new B2BRetailerCreditDetails(callBackb2BRetailerCreditDetailsInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }
    }

    private void Call_All_Other_OrderDetailsMethod() {
        Initialize_and_ExecuteB2BOrderDetails(Constants.CallADDMethod);
        Call_and_ExecuteB2BBatchOrderDetails(Constants.CallUPDATEMethod);
        if(selectedPaymentMode.toUpperCase().equals("CREDIT")){
            if(isRetailerCreditDetailsIsNotCreated){
                Call_and_Execute_RetailerCreditDetails(Constants.CallADDMethod);
                Call_and_Execute_CreditTransactionHistory(Constants.CallADDMethod);
               // Call_and_Execute_PaymentDetails(Constants.CallADDMethod);
            }
            else{
                Call_and_Execute_RetailerCreditDetails(Constants.CallUPDATEMethod);
             //   Call_and_Execute_PaymentDetails(Constants.CallADDMethod);
                Call_and_Execute_CreditTransactionHistory(Constants.CallADDMethod);
            }

        }
        else{
          //  Call_and_Execute_PaymentDetails(Constants.CallADDMethod);
        }
        //this method called inside B2BOrder Details
        // call_and_init_B2BOrderItemDetailsService(Constants.CallADDMethod);
        Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallDELETEMethod, "", false);
        Initialize_and_ExecuteB2BCartItemDetails_BulkUpdate(Constants.CallDELETEMethod);
        call_and_init_GoatEarTagDetails_BulkUpdate(Constants.CallUPDATEMethod);

    }



    private void Call_and_Execute_PaymentDetails(String callMethod) {


        if(isPaymentDetailsCalled){
            return;
        }

        isPaymentDetailsCalled = true;

        callB2BPaymentDetailsInterface  = new B2BPaymentDetailsInterface() {
            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal__B2BPaymentDetails> retailerDetailsArrayList) {
                isPaymentDetailsCalled = false;
            }

            @Override
            public void notifySuccess(String result) {
                isPaymentDetailsCalled = false;
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isPaymentDetailsCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                isPaymentDetailsCalled = false;
            }
        };

        if(callMethod.equals(Constants.CallADDMethod)){
            Modal__B2BPaymentDetails.retailerkey_static = retailerKey;
            Modal__B2BPaymentDetails.retailermobileno_static = retailermobileno;
            Modal__B2BPaymentDetails.transactiontime_static = orderplaceddate;
            Modal__B2BPaymentDetails.retailername_static = retailername;
            Modal__B2BPaymentDetails.orderid_static = orderid;

            Modal__B2BPaymentDetails.deliverycentrekey_static = deliveryCentreKey;
            Modal__B2BPaymentDetails.transactionvalue_static = (String.valueOf(twoDecimalConverter.format(final_totalPayment)));
            Modal__B2BPaymentDetails.notes_static = "";
            Modal__B2BPaymentDetails.paymentmode_static = selectedPaymentMode;
            Modal__B2BPaymentDetails.transactiontype_static = Constants.transactiontype_ORDERPAYMENT;

            String getApiToCall = API_Manager.addPaymentDetails ;
            B2BPaymentDetails asyncTask = new B2BPaymentDetails(callB2BPaymentDetailsInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }
    }



    private void Call_and_Execute_CreditTransactionHistory(String callMethod) {


        if(isRetailerCreditTransactionHistoryCalled){
            return;
        }

        isRetailerCreditTransactionHistoryCalled = true;

        callB2BCreditTransactionHistoryInterface  = new B2BCreditTransactionHistoryInterface() {
            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal__B2BCreditTransactionHistory> retailerDetailsArrayList) {
                isRetailerCreditTransactionHistoryCalled = false;
            }

            @Override
            public void notifySuccess(String result) {
                isRetailerCreditTransactionHistoryCalled = false;
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isRetailerCreditTransactionHistoryCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                isRetailerCreditTransactionHistoryCalled = false;
            }
        };

        if(callMethod.equals(Constants.CallADDMethod)){
            new Modal__B2BCreditTransactionHistory("resetall");
            Modal__B2BCreditTransactionHistory.paymentid_static="";

            Modal__B2BCreditTransactionHistory.setOrderid_static(String.valueOf(orderid));
            Modal__B2BCreditTransactionHistory.retailerkey_static = retailerKey;
            Modal__B2BCreditTransactionHistory.retailername_static = retailername;
            Modal__B2BCreditTransactionHistory.transactiontime_static = orderplaceddate;
            Modal__B2BCreditTransactionHistory.retailermobileno_static = retailermobileno;
            Modal__B2BCreditTransactionHistory.deliverycentrekey_static = deliveryCentreKey;
            Modal__B2BCreditTransactionHistory.transactionvalue_static = (String.valueOf(twoDecimalConverter.format(final_totalPayment)));
            Modal__B2BCreditTransactionHistory.supervisormobileno_static = supervisormobileno;
            Modal__B2BCreditTransactionHistory.oldamountincredit_static = String.valueOf(twoDecimalConverter.format(oldCreditAmountOfUser));
            Modal__B2BCreditTransactionHistory.newamountincredit_static = String.valueOf(twoDecimalConverter.format(newCreditAmountOfUser));

            String getApiToCall = API_Manager.addCreditTransactionHistory ;
            B2BCreditTransactionHistory asyncTask = new B2BCreditTransactionHistory(callB2BCreditTransactionHistoryInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }
    }

    private void Call_and_Generate_tokenNo(String callMethod) {


        if(isB2BTokenNoCalled){
            return;
        }
        showProgressBar(true);
        isB2BTokenNoCalled = true;
        callB2BTokenNoManagerInterface = new B2BTokenNoManagerInterface() {
            @Override
            public void notifySuccess(String result) {

                tokenNo = result;
                isB2BTokenNoCalled = false;

             /*   do {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Call_All_Other_OrderDetailsMethod();
                        }
                    }, 1000);
                }
                while (isRetailerCreditDetailsCalled);

              */

                Call_and_Execute_RetailerCreditDetails(Constants.CallGETMethod);


               // Initialize_and_ExecuteB2BOrderDetails(Constants.CallADDMethod);
               // Call_and_ExecuteB2BBatchOrderDetails(Constants.CallUPDATEMethod);
                //this method called inside B2BOrder Details
               // call_and_init_B2BOrderItemDetailsService(Constants.CallADDMethod);
              //  Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallDELETEMethod, "", false);
              //  Initialize_and_ExecuteB2BCartItemDetails_BulkUpdate(Constants.CallDELETEMethod);
              //  call_and_init_GoatEarTagDetails_BulkUpdate(Constants.CallUPDATEMethod);

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isB2BTokenNoCalled = false;
                Toast.makeText(CheckOut_activity.this, "Volley error in generating tokenno", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void notifyProcessingError(Exception error) {
                isB2BTokenNoCalled = false;
                Toast.makeText(CheckOut_activity.this, "Processing error in generating tokenno", Toast.LENGTH_SHORT).show();

            }
        };

            if(callMethod.equals(Constants.CallGENERATEMethod)) {
                String getApiToCall = API_Manager.generateTokenNo + deliveryCentreKey;
                B2BTokenNoManager asyncTask = new B2BTokenNoManager(callB2BTokenNoManagerInterface, getApiToCall, callMethod);
                asyncTask.execute();
            }



    }


    public void Call_And_Execute_B2BCartOrderItem_Details(String callMethod) {


        Modal_B2BCartItemDetails modal_b2BCartItemDetails_toAdd = new Modal_B2BCartItemDetails();

        showProgressBar(true);

        if (isB2BCartItemDetailsCalled) {
            //  showProgressBar(false);
            return;
        }
        isB2BCartItemDetailsCalled = true;

        callback_b2BCartItemDetaillsInterface = new B2BCartItemDetaillsInterface() {
            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BCartItemDetails> arrayList) {

                if(callMethod.equals(Constants.CallGETListMethod)) {
                    if(arrayList.size()>0) {
                        for (int iterator = 0; iterator < arrayList.size(); iterator++) {
                            if (!cartItemDetailsList_String.contains(String.valueOf(arrayList.get(iterator).getBarcodeno()))) {
                                cartItemDetailsList_String.add(String.valueOf(arrayList.get(iterator).getBarcodeno()));
                                cartItemDetails_Hashmap.put(String.valueOf(arrayList.get(iterator).getBarcodeno()), arrayList.get(iterator));
                            }

                            if (iterator - (arrayList.size() - 1) == 0) {
                                CalculateAndSetTotal_Quantity_Price_Values();

                            }
                        }

                    }
                    else{
                        CalculateAndSetTotal_Quantity_Price_Values();

                    }


                }


                isB2BCartItemDetailsCalled = false;
                //showProgressBar(false);
            }

            @Override
            public void notifySuccess(String result) {
                isB2BCartItemDetailsCalled = false;
                if (result.equals(Constants.emptyResult_volley)) {
                    if(callMethod.equals(Constants.CallGETListMethod)){
                        CalculateAndSetTotal_Quantity_Price_Values();
                    }
                    else{

                        showProgressBar(false);
                        Toast.makeText(CheckOut_activity.this, "There is no Cart Item Details for this batch", Toast.LENGTH_SHORT).show();

                    }


                }
                else {



                    CalculateAndSetTotal_Quantity_Price_Values();

                    isB2BCartItemDetailsCalled = false;
                    showProgressBar(false);

                }



            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isB2BCartItemDetailsCalled = false;
                showProgressBar(false);
                Toast.makeText(CheckOut_activity.this, "There is Volley error in CartItemDetails", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void notifyProcessingError(Exception error) {
                Toast.makeText(CheckOut_activity.this, "There is processing error in CartItemDetails", Toast.LENGTH_SHORT).show();

                isB2BCartItemDetailsCalled = false;
                showProgressBar(false);
            }


        };


      if(callMethod.equals(Constants.CallGETListMethod)) {
            String getApiToCall = API_Manager.getCartItemDetailsForOrderid + orderid;
            B2BCartItemDetails asyncTask = new B2BCartItemDetails(callback_b2BCartItemDetaillsInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }

    }


    private void Call_and_ExecuteB2BBatchOrderDetails(String callMethod) {


        showProgressBar(true);
        if (isBatchDetailsTableServiceCalled) {
            //  showProgressBar(false);
            return;
        }
        isBatchDetailsTableServiceCalled = true;

        for(int i =0 ; i<cartItemDetailsList_String.size() ; i++){
            Modal_B2BCartItemDetails  modal_b2BCartItemDetails  = cartItemDetails_Hashmap.get(cartItemDetailsList_String.get(i));
            if(batchwiseEartagDetails_Hashmap.containsKey(modal_b2BCartItemDetails.getBatchno())){
                ArrayList<String> barcodenoList = new ArrayList<>();
                barcodenoList  = batchwiseEartagDetails_Hashmap.get(modal_b2BCartItemDetails.getBatchno());

                if(barcodenoList.size()==0){



        callback_B2BBatchDetailsInterface = new B2BBatchDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BBatchDetails> batchDetailsArrayListt) {
                if(batchDetailsArrayListt.size()==0){

                    Toast.makeText(CheckOut_activity.this, Constants.there_is_noData_volley, Toast.LENGTH_SHORT).show();

                }
                else {



                }


                // showProgressBar(false);
                isBatchDetailsTableServiceCalled = false;
            }

            @Override
            public void notifySuccess(String result) {


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

                    Modal_B2BBatchDetailsUpdate modal_b2BBatchDetailsUpdate = new Modal_B2BBatchDetailsUpdate();
                    modal_b2BBatchDetailsUpdate.setBatchno(modal_b2BCartItemDetails.getBatchno());
                    modal_b2BBatchDetailsUpdate.setSupplierkey(Modal_B2BBatchDetailsStatic.getSupplierkey());
                    modal_b2BBatchDetailsUpdate.setStatus(Constants.batchDetailsStatus_Sold);
                    modal_b2BBatchDetailsUpdate.setDeliverycenterkey(deliveryCentreKey);
                    modal_b2BBatchDetailsUpdate.setSolddate(orderplaceddate);


                    String addApiToCall = API_Manager.updateBatchDetailsWithSupplierkeyBatchNo;

                    B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface, addApiToCall, Constants.CallUPDATEMethod);
                    asyncTask.execute();



                }
            }

        }




    }

    private void call_and_init_B2BOrderItemDetailsService(String callADDMethod) {

        if (isOrderItemDetailsServiceCalled) {
            // showProgressBar(false);
            return;
        }
        isOrderItemDetailsServiceCalled = true;
        callback_b2BOrderItemDetailsInterface = new B2BOrderItemDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BOrderItemDetails> arrayList) {

            }

            @Override
            public void notifySuccess(String result) {

                isOrderItemDetailsServiceCalled = false;



                // showProgressBar(false);


            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isOrderItemDetailsServiceCalled = false;
                //    //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 3 " + DateParser.getDate_and_time_newFormat());

            }

            @Override
            public void notifyProcessingError(Exception error) {
                isOrderItemDetailsServiceCalled = false;
                ////Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());

            }


        };


        String getApiToCall = API_Manager.addOrderItemDetails ;

        B2BOrderItemDetails asyncTask = new B2BOrderItemDetails(callback_b2BOrderItemDetailsInterface,  getApiToCall, Constants.CallADDMethod,cartItemDetails_Hashmap,cartItemDetailsList_String,orderid, CheckOut_activity.this);
        asyncTask.execute();







    }




    public void call_and_init_B2BRetailerDetailsService(String CallMethod, boolean updateCartOrderDetails, String mobilenoToUpdate) {

        if (isRetailerDetailsServiceCalled) {
            //  showProgressBar(false);
            return;
        }
        isRetailerDetailsServiceCalled = true;
        callback_retailerDetailsInterface = new B2BRetailerDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayListt) {
                isRetailerDetailsServiceCalled = false;
                retailerDetailsArrayList = retailerDetailsArrayListt;
                setAdapterForRetailerDetails(true);
                setAdapterForRetailerDetails(false);

        
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 1 " + DateParser.getDate_and_time_newFormat());

            }

            @Override
            public void notifySuccess(String result) {
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 2 " + DateParser.getDate_and_time_newFormat());
                if(result.equals(Constants.item_Already_Added_volley)){
                    AlertDialogClass.showDialog(CheckOut_activity.this, R.string.retailersAlreadyCreated_Instruction);
                    isRetailerDetailsServiceCalled = false;
                    showProgressBar(false);

                }
                else if(result.equals(Constants.successResult_volley)){
                    retailerDetailsArrayList = DatabaseArrayList_PojoClass.retailerDetailsArrayList;

                    for(int i =0 ;i < retailerDetailsArrayList.size(); i++) {
                        if(retailerDetailsArrayList.get(i).getMobileno().contains(retailermobileno)) {
                            if (isRetailerSelected) {

                                oldRetailerKey = retailerKey;
                                oldretailerMobileno = retailermobileno;
                                oldRetailerName = retailername;
                                oldretaileraddress = retaileraddress;
                                oldretailerGSTIN = retailerGSTIN;


                            } else {
                                isRetailerSelected = true;
                            }


                            retailername = String.valueOf(retailerDetailsArrayList.get(i).getRetailername());
                            retailerKey = String.valueOf(retailerDetailsArrayList.get(i).getRetailerkey());
                            retailermobileno = String.valueOf(retailerDetailsArrayList.get(i).getMobileno());
                            retaileraddress = String.valueOf(retailerDetailsArrayList.get(i).getAddress());
                            retailerGSTIN = String.valueOf(retailerDetailsArrayList.get(i).getGstin());

                            retailerName_edittext.setText(retailerDetailsArrayList.get(i).getRetailername());
                            retailerName_edittext.clearFocus();
                            retailerMobileNo_edittext.setText(retailerDetailsArrayList.get(i).getMobileno());
                            retailerMobileNo_edittext.clearFocus();
                            retailerName_textView.setText(retailerDetailsArrayList.get(i).getRetailername());
                            retailerName_textView.clearFocus();
                            retailerAddress_textView.setText(retailerDetailsArrayList.get(i).getAddress());
                            retailerAddress_textView.clearFocus();


                            showAlert_toUpdateCartOrderDetails("retailer");
                        }
                    }

                    //   retailerAddress_textView.setText(String.valueOf(retailers_address));
                    //retailerName_textView.setText(String.valueOf(retailers_name));
                    //retailerMobileNo_edittext.setText(String.valueOf(retailers_mobileno));
                    //retailerMobileNo_edittext.clearFocus();
                    //retailerMobileNo_edittext.setThreshold(1);
                    //retailerMobileNo_edittext.dismissDropDown();

                    //  BillingScreen.retailerDetailsArrayList = DatabaseArrayList_PojoClass.retailerDetailsArrayList;
                    isRetailerDetailsServiceCalled = false;
                    //showProgressBar(false);
                    // ((BillingScreen)CheckOut_activity.this).closeFragment();
                    try {
                        show_retailerItemDetails_Dialog.cancel();
                        show_retailerItemDetails_Dialog.dismiss();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    isRetailerDetailsServiceCalled = false;
                    showProgressBar(false);
                }

                isRetailerDetailsServiceCalled = false;
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isRetailerDetailsServiceCalled = false;
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 3 " + DateParser.getDate_and_time_newFormat());
                showProgressBar(false);
            }

            @Override
            public void notifyProcessingError(Exception error) {
                isRetailerDetailsServiceCalled = false;
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());
                showProgressBar(false);
            }


        };
        if(CallMethod.equals(Constants.CallADDMethod)){
            Modal_B2BRetailerDetails.setAddress_static(retaileraddress);
            Modal_B2BRetailerDetails.setMobileno_static(retailermobileno);
            Modal_B2BRetailerDetails.setRetailername_static(retailername);
            Modal_B2BRetailerDetails.setDeliveryCenterKey_static(deliveryCentreKey);
            Modal_B2BRetailerDetails.setDeliveryCenterName_static(deliveryCentreName);
            Modal_B2BRetailerDetails.setGstin_static(retailerGSTIN);

            String getApiToCall = API_Manager.addRetailerDetailsList ;

            B2BRetailerDetails asyncTask = new B2BRetailerDetails(callback_retailerDetailsInterface,  getApiToCall, Constants.CallADDMethod);
            asyncTask.execute();
        }
        else  if(CallMethod.equals(Constants.CallGETListMethod)){
            String getApiToCall = API_Manager.getretailerDetailsListWithDeliveryCentreKey+deliveryCentreKey ; ;

            B2BRetailerDetails asyncTask = new B2BRetailerDetails(callback_retailerDetailsInterface,  getApiToCall, Constants.CallGETListMethod);
            asyncTask.execute();
        }





    }

    private void setAdapterForRetailerDetails(boolean setMobileNoFilter) {



        try{
            if(setMobileNoFilter){
                retailerDetailsArrayList =  sortThisArrayUsingRetailerName_mobileNo(retailerDetailsArrayList,"MOBILENUMBER");

            }
            else{
                retailerDetailsArrayList =  sortThisArrayUsingRetailerName_mobileNo(retailerDetailsArrayList,"NAME");

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


        try {
            adapter_autoComplete_retailerMobileNo = new Adapter_AutoComplete_RetailerMobileNo(CheckOut_activity.this, retailerDetailsArrayList, CheckOut_activity.this , setMobileNoFilter);
            adapter_autoComplete_retailerMobileNo.setHandler(newHandler());

            //receiverMobileNo_edittext.setInputType(InputType.TYPE_CLASS_PHONE);
            if(setMobileNoFilter){
                retailerMobileNo_edittext.setAdapter(adapter_autoComplete_retailerMobileNo);
                retailerMobileNo_edittext.clearFocus();
                retailerMobileNo_edittext.setThreshold(1);
                retailerMobileNo_edittext.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_CLASS_NUMBER);
            }
            else{
                retailerName_edittext.setAdapter(adapter_autoComplete_retailerMobileNo);
                retailerName_edittext.clearFocus();
                retailerName_edittext.setThreshold(1);
                retailerName_edittext.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_CLASS_TEXT);
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void showRetailerNotFoundAlert(boolean filterMobileNo) {

        if(!isNoretailerAlertShown) {
            isNoretailerAlertShown = true;
            try {
                // AlertDialogClass.showDialog(CheckOut_activity.this, R.string.RetailerNotFound_Instruction);

            if(filterMobileNo) {

                new TMCAlertDialogClass(CheckOut_activity.this, R.string.app_name, R.string.RetailerNotFound_AddNewRetailer_Instruction,
                        R.string.Yes_Text, R.string.No_Text,
                        new TMCAlertDialogClass.AlertListener() {
                            @Override
                            public void onYes() {
                                isNoretailerAlertShown = false;
                                retailerName_edittext.setText(String.valueOf(retailername));
                                retailerName_edittext.clearFocus();
                                retailerName_edittext.setThreshold(1);
                                retailerName_edittext.dismissDropDown();
                                retailerMobileNo_edittext.setText(retailermobileno);
                                retailerName_textView.setText(String.valueOf(retailername));
                                retailerAddress_textView.setText(String.valueOf(retaileraddress));
                                retailerMobileNo_edittext.clearFocus();
                                retailerMobileNo_edittext.setThreshold(1);
                                retailerMobileNo_edittext.dismissDropDown();
                                try {
                                    InputMethodManager imm = (InputMethodManager) CheckOut_activity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    Objects.requireNonNull(imm).hideSoftInputFromWindow(retailerMobileNo_edittext.getWindowToken(), 0);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    InputMethodManager imm = (InputMethodManager) CheckOut_activity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    Objects.requireNonNull(imm).hideSoftInputFromWindow(retailerName_edittext.getWindowToken(), 0);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                showAddRetailerLayout();

                            }

                            @Override
                            public void onNo() {
                                isNoretailerAlertShown = false;
                                if (retailermobileno.replaceAll("[+]91", "").length() == 10) {
                                    isRetailerSelected = true;
                                } else {
                                    isRetailerSelected = false;
                                }

                                retailerName_edittext.setText(String.valueOf(retailername));
                                retailerName_edittext.clearFocus();
                                retailerName_edittext.setThreshold(1);
                                retailerName_edittext.dismissDropDown();
                                retailerMobileNo_edittext.setText(retailermobileno);
                                retailerName_textView.setText(String.valueOf(retailername));
                                retailerAddress_textView.setText(String.valueOf(retaileraddress));
                                retailerMobileNo_edittext.clearFocus();
                                retailerMobileNo_edittext.setThreshold(1);
                                retailerMobileNo_edittext.dismissDropDown();
                                try {
                                    InputMethodManager imm = (InputMethodManager) CheckOut_activity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    Objects.requireNonNull(imm).hideSoftInputFromWindow(retailerMobileNo_edittext.getWindowToken(), 0);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    InputMethodManager imm = (InputMethodManager) CheckOut_activity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    Objects.requireNonNull(imm).hideSoftInputFromWindow(retailerName_edittext.getWindowToken(), 0);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

            }
            else{

                new TMCAlertDialogClass(CheckOut_activity.this, R.string.app_name, R.string.RetailerNametFound_AddNewRetailer_Instruction,
                        R.string.Yes_Text, R.string.No_Text,
                        new TMCAlertDialogClass.AlertListener(){
                            @Override
                            public void onYes() {
                                isNoretailerAlertShown = false;
                                retailerName_edittext.setText(String.valueOf(retailername));
                                retailerName_edittext.clearFocus();
                                retailerName_edittext.setThreshold(1);
                                retailerName_edittext.dismissDropDown();
                                retailerMobileNo_edittext.setText(retailermobileno);
                                retailerName_textView.setText(String.valueOf(retailername));
                                retailerAddress_textView.setText(String.valueOf(retaileraddress));
                                retailerMobileNo_edittext.clearFocus();
                                retailerMobileNo_edittext.setThreshold(1);
                                retailerMobileNo_edittext.dismissDropDown();
                                try {
                                    InputMethodManager imm = (InputMethodManager) CheckOut_activity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    Objects.requireNonNull(imm).hideSoftInputFromWindow(retailerMobileNo_edittext.getWindowToken(), 0);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    InputMethodManager imm = (InputMethodManager) CheckOut_activity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    Objects.requireNonNull(imm).hideSoftInputFromWindow(retailerName_edittext.getWindowToken(), 0);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                showAddRetailerLayout();

                            }

                            @Override
                            public void onNo() {
                                isNoretailerAlertShown = false;
                                if (retailermobileno.replaceAll("[+]91", "").length() == 10) {
                                    isRetailerSelected = true;
                                } else {
                                    isRetailerSelected = false;
                                }

                                retailerName_edittext.setText(String.valueOf(retailername));
                                retailerName_edittext.clearFocus();
                                retailerName_edittext.setThreshold(1);
                                retailerName_edittext.dismissDropDown();
                                retailerMobileNo_edittext.setText(retailermobileno);
                                retailerName_textView.setText(String.valueOf(retailername));
                                retailerAddress_textView.setText(String.valueOf(retaileraddress));
                                retailerMobileNo_edittext.clearFocus();
                                retailerMobileNo_edittext.setThreshold(1);
                                retailerMobileNo_edittext.dismissDropDown();
                                try {
                                    InputMethodManager imm = (InputMethodManager) CheckOut_activity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    Objects.requireNonNull(imm).hideSoftInputFromWindow(retailerMobileNo_edittext.getWindowToken(), 0);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    InputMethodManager imm = (InputMethodManager) CheckOut_activity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    Objects.requireNonNull(imm).hideSoftInputFromWindow(retailerName_edittext.getWindowToken(), 0);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

            }


            } catch (Exception e) {
                Toast.makeText(CheckOut_activity.this, getString(R.string.RetailerNotFound_Instruction), Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }
        }





    }


    public void showAddRetailerLayout() {

        show_retailerItemDetails_Dialog = new Dialog(CheckOut_activity.this,R.style.Theme_Dialog);
        //  show_scan_barcode_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        show_retailerItemDetails_Dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        show_retailerItemDetails_Dialog.setContentView(R.layout.fragment_add_retailer_);
        // show_scan_barcode_dialog.setCancelable(false);
        show_retailerItemDetails_Dialog.setCanceledOnTouchOutside(false);

        LinearLayout back_IconLayout;
        EditText retailerDetailsFrame,mobileNo_textView,address_edittext,retailerName_textView ,gstin_editText ;
        Button save_button ;


        retailerName_textView = show_retailerItemDetails_Dialog.findViewById(R.id.retailerName_textView);
        address_edittext = show_retailerItemDetails_Dialog.findViewById(R.id.address_edittext);
        mobileNo_textView = show_retailerItemDetails_Dialog.findViewById(R.id.mobileNo_textView);
        retailerDetailsFrame = show_retailerItemDetails_Dialog.findViewById(R.id.retailerDetailsFrame);
        gstin_editText =  show_retailerItemDetails_Dialog.findViewById(R.id.gstin_editText);
        save_button  =  show_retailerItemDetails_Dialog.findViewById(R.id.save_button);
        loadingPanel =  show_retailerItemDetails_Dialog.findViewById(R.id.loadingPanel);
        back_IconLayout =  show_retailerItemDetails_Dialog.findViewById(R.id.back_IconLayout);

        show_retailerItemDetails_Dialog.show();
        mobileNo_textView.setText(String.valueOf(retailerMobileNo_edittext.getText().toString()));
        retailerName_textView.setText(String.valueOf(retailerName_edittext.getText().toString()));
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retailermobileno = mobileNo_textView.getText().toString();
                retaileraddress = address_edittext.getText().toString();
                retailername = retailerName_textView.getText().toString();
                retailerGSTIN = gstin_editText.getText().toString();

                if(retailermobileno.length()==10 ){
                    retailermobileno = "+91"+retailermobileno;
                    if(retaileraddress.length()>2) {
                        if (retailername.length() > 0) {
                            call_and_init_B2BRetailerDetailsService(Constants.CallADDMethod, false,"");
                        } else {
                            AlertDialogClass.showDialog(CheckOut_activity.this, R.string.retailers_name_cant_be_empty);

                        }
                    }
                    else{
                        AlertDialogClass.showDialog(CheckOut_activity.this, R.string.retailers_Address_cant_be_empty);

                    }
                }
                else{
                    AlertDialogClass.showDialog(CheckOut_activity.this, R.string.retailers_mobileno_should_be_10digits);

                }


            }
        });
        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                show_retailerItemDetails_Dialog.dismiss();


            }
        });



    }




    public void showAlert_toUpdateCartOrderDetails(String valuetoupdate) {

        try {
            if(valuetoupdate.toUpperCase().equals("RETAILER")){
                valuetoupdate = getString(R.string.retailername);
            }
            //    if (isCartAlreadyCreated) {

            if (valuetoupdate.equals(getString(R.string.retailername))) {


                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    Objects.requireNonNull(imm).hideSoftInputFromWindow(retailerMobileNo_edittext.getWindowToken(), 0);


                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    Objects.requireNonNull(imm).hideSoftInputFromWindow(retailerName_edittext.getWindowToken(), 0);


                } catch (Exception e) {
                    e.printStackTrace();
                }



                Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallUPDATEMethod, getString(R.string.retailername), false);



             

            }

            else if(valuetoupdate.toUpperCase().equals(getString(R.string.paymentmode).toUpperCase())){
                Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallUPDATEMethod, getString(R.string.paymentmode), false);


            }
            else if(valuetoupdate.equals(getString(R.string.feedvalue))){
                Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallUPDATEMethod, getString(R.string.feedvalue), false);


            }
            else if(valuetoupdate.equals(getString(R.string.discount))){
                Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallUPDATEMethod, getString(R.string.discount), false);


            }


            //  }
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }





    private void Initialize_and_ExecuteB2BOrderDetails(String callADDMethod) {

        if (isOrderDetailsServiceCalled) {
            // showProgressBar(false);
            return;
        }
        isOrderDetailsServiceCalled = true;
        callback_b2BOrderDetailsInterface = new B2BOrderDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BOrderDetails> retailerDetailsArrayListt) {
                isOrderDetailsServiceCalled = false;


            }

            @Override
            public void notifySuccess(String result) {

                isOrderDetailsServiceCalled = false;


                //((BillingScreen)CheckOut_activity.this).closeFragment();
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isOrderDetailsServiceCalled = false;
                //    //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 3 " + DateParser.getDate_and_time_newFormat());

            }

            @Override
            public void notifyProcessingError(Exception error) {
                isOrderDetailsServiceCalled = false;
                ////Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());

            }


        };


    //    batchwise_itemDespHashmap = sortThisHashMapUsingTheKey(batchwise_itemDespHashmap);

        batchList_String   = sortThisArrayListUsingTheValue(batchList_String);

        try{
            if(discountDouble>0) {

                for (int iterator = 0; iterator < cartItemDetailsList_String.size(); iterator++) {

                    String barcode = cartItemDetailsList_String.get(iterator);


                    Modal_B2BCartItemDetails modal_b2BCartItemDetails = cartItemDetails_Hashmap.get(barcode);
                    try{


                        double discountWeightagePercentageBasedOnPriceForAnItem =0 ,  itemPrice_Double = 0 ,discountAmountForItemBasedOnWeightage = 0
                                ,itemPriceWithoutDiscount = 0;
                        
                        String itemPrice_String ="";
                        try{
                            String text ="";
                            text = String.valueOf(modal_b2BCartItemDetails.getItemprice().toString()).replaceAll("[^\\d.]", "");
                            if(text.equals("")){
                                itemPrice_String = "0";
                            }
                            else{
                                itemPrice_String = text;
                            }
                        }
                        catch (Exception e){
                            itemPrice_String ="0";
                            e.printStackTrace();
                        }

                        try{
                            itemPrice_Double = Double.parseDouble(itemPrice_String);
                        }
                        catch (Exception e){
                            itemPrice_Double =0;
                            e.printStackTrace();
                        }
                        try{
                                discountWeightagePercentageBasedOnPriceForAnItem = itemPrice_Double/final_totalPriceWithOutDiscountWithoutFeed ;
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        
                        try{
                            discountAmountForItemBasedOnWeightage = discountWeightagePercentageBasedOnPriceForAnItem * discountDouble ;
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        try{
                            itemPriceWithoutDiscount = itemPrice_Double - discountAmountForItemBasedOnWeightage;
                            modal_b2BCartItemDetails.setTotalPrice_ofItem(String.valueOf(twoDecimalConverter.format(itemPriceWithoutDiscount )));
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                        try{
                            modal_b2BCartItemDetails.setDicountweightageAmountPercentage(String.valueOf(discountWeightagePercentageBasedOnPriceForAnItem));
                            modal_b2BCartItemDetails.setDicountweightageAmount(String.valueOf(discountAmountForItemBasedOnWeightage));
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        String batchno ="";
                        batchno = modal_b2BCartItemDetails.getBatchno();
                       // for(String batchno : batchwise_itemDespHashmap.keySet()){
                            JSONObject jsonObject = batchwise_itemDespHashmap.get(batchno);
                            try{
                                if(jsonObject.has("discountamount")){
                                    double batchwiseDiscount = 0;


                                    try{
                                        batchwiseDiscount = (jsonObject.getDouble("discountamount"));

                                    }
                                    catch (Exception e){
                                        batchwiseDiscount =0;
                                        e.printStackTrace();
                                    }

                                    try{
                                        batchwiseDiscount = discountAmountForItemBasedOnWeightage + batchwiseDiscount ;

                                    }
                                    catch (Exception e){
                                        batchwiseDiscount =0;
                                        e.printStackTrace();
                                    }
                                    jsonObject.put("discountamount",Double.parseDouble(twoDecimalConverter.format(batchwiseDiscount)));


                                }
                                else{
                                    jsonObject.put("discountamount",Double.parseDouble(twoDecimalConverter.format(discountAmountForItemBasedOnWeightage)));
                                }
                            }
                            catch (Exception e){
                                jsonObject = batchwise_itemDespHashmap.get(batchno);
                                e.printStackTrace();
                            }
                            try {



                                if (SDK_INT >= Build.VERSION_CODES.N) {
                                    batchwise_itemDespHashmap.replace(batchno,jsonObject);
                                }
                                else{
                                    batchwise_itemDespHashmap.put(batchno,jsonObject);
                                }

                             } catch (Exception e) {
                                e.printStackTrace();
                            }
                       // }




                        if (SDK_INT >= Build.VERSION_CODES.N) {
                            cartItemDetails_Hashmap.replace(barcode,modal_b2BCartItemDetails);
                        }
                        else{
                            cartItemDetails_Hashmap.put(barcode,modal_b2BCartItemDetails);
                        }
                    }
                    catch (Exception e ){
                        e.printStackTrace();
                    }

                }

            }
        }
         catch (Exception e){
            e.printStackTrace();
         }
        
        try{
            call_and_init_B2BOrderItemDetailsService(Constants.CallADDMethod);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        
        
        
        
        
        
        
        
        
        JSONObject itemDespArray = new JSONObject();

        for(String batchno : batchwise_itemDespHashmap.keySet()){
            JSONObject jsonObject = batchwise_itemDespHashmap.get(batchno);
            try{
                if(jsonObject.has("batchno")){
                    jsonObject.remove("batchno");
                }
            }
            catch (Exception e){
                jsonObject = batchwise_itemDespHashmap.get(batchno);
                e.printStackTrace();
            }
            try {
                itemDespArray.put(batchno,jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String batch : batchList_String) {
            sb.append(batch).append(", "); // append each string followed by a comma and a space
        }

        // remove the last comma and space from the string
        String result = sb.toString().trim();
        if (result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }
        Modal_B2BOrderDetails.setItemDespBatchListjsonArray_static(itemDespArray.toString());
        Modal_B2BOrderDetails.setBatchnoList_static(result);
        Modal_B2BOrderDetails.setBillno_static(tokenNo);
        Modal_B2BOrderDetails.setNotes_static(String.valueOf(notes_edittext.getText().toString()));


        Modal_B2BOrderDetails.setSupervisorname_Static(String.valueOf(supervisorName));
        Modal_B2BOrderDetails.setSupervisormobileno_Static(String.valueOf(supervisormobileno));

        Modal_B2BOrderDetails.setTotalquantity_Static(String.valueOf(final_totalGoats));
        Modal_B2BOrderDetails.setTotalweight_Static(String.valueOf(threeDecimalConverter.format(final_totalWeight)));
        Modal_B2BOrderDetails.setDeliverycentrekey_Static(String.valueOf(deliveryCentreKey));
        Modal_B2BOrderDetails.setDeliverycentrename_Static(String.valueOf(deliveryCentreName));
        Modal_B2BOrderDetails.setDiscountamount_Static(String.valueOf(twoDecimalConverter.format(final_discountValue)));
        Modal_B2BOrderDetails.setOrderplaceddate_Static(String.valueOf(orderplaceddate));
        Modal_B2BOrderDetails.setPaymentMode_Static(String.valueOf(selectedPaymentMode));

        Modal_B2BOrderDetails.setPayableAmount_Static(String.valueOf(twoDecimalConverter.format(final_totalPayment)));
        Modal_B2BOrderDetails.setStatus_Static(String.valueOf(Constants.orderDetailsStatus_NOTDelivered));
        Modal_B2BOrderDetails.setTotalPrice_Static(String.valueOf(twoDecimalConverter.format(final_totalPriceWithOutDiscountWithoutFeed)));

        Modal_B2BOrderDetails.setOrderid_Static(String.valueOf(orderid));
        Modal_B2BOrderDetails.setRetailerkey_Static( String.valueOf(retailerKey));
        Modal_B2BOrderDetails.setRetailername_Static(String.valueOf(retailername));
        Modal_B2BOrderDetails.setRetailermobileno_Static(String.valueOf(retailermobileno));
        Modal_B2BOrderDetails.setRetaileraddress_static(String.valueOf(retaileraddress));
        Modal_B2BOrderDetails.setFeedPrice_Static(String.valueOf(totalFeedPriceTextview.getText().toString()));
        Modal_B2BOrderDetails.setFeedPricePerKG_Static(String.valueOf(feedPricePerKg_editText.getText().toString()));
        Modal_B2BOrderDetails.setFeedWeight_Static(String.valueOf(feedWeight_editText.getText().toString()));







        String getApiToCall = API_Manager.addOrderDetailsList ;

        B2BOrderDetails asyncTask = new B2BOrderDetails(callback_b2BOrderDetailsInterface,  getApiToCall, Constants.CallADDMethod);
        asyncTask.execute();







    }

    private ArrayList<String> sortThisArrayListUsingTheValue(ArrayList<String> batchList_stringg) {
        try{


            // create a custom Comparator for sorting mixed-format keys
            Comparator<String> keyComparator = new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    // compare the first character of each key
                    char c1 = s1.charAt(0);
                    char c2 = s2.charAt(0);

                    if (Character.isLetter(c1) && !Character.isLetter(c2)) {
                        return -1; // s1 is a letter, s2 is not; s1 comes first
                    } else if (!Character.isLetter(c1) && Character.isLetter(c2)) {
                        return 1; // s1 is not a letter, s2 is; s2 comes first
                    } else if (Character.isLetter(c1) && Character.isLetter(c2)) {
                        return s1.compareTo(s2); // both s1 and s2 are letters; compare them normally
                    } else {
                        // neither s1 nor s2 is a letter; parse them as integers and compare
                        int i1 = Integer.parseInt(s1);
                        int i2 = Integer.parseInt(s2);
                        return Integer.compare(i1, i2);
                    }
                }
            };
            // sort the list using the custom Comparator
            Collections.sort(batchList_stringg, keyComparator);

            System.out.println(batchList_stringg);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return batchList_stringg;
    }

    private HashMap<String, JSONObject> sortThisHashMapUsingTheKey(HashMap<String, JSONObject> batchwise_itemDespHashmapp) {
        HashMap<String, JSONObject> batchwise_itemDespHashmap_local = new HashMap<>();


       /*
        try{


            ArrayList<String> keys = new ArrayList<>(batchwise_itemDespHashmapp.keySet());

// create a custom Comparator for sorting mixed-format keys
            Comparator<String> keyComparator = new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    // compare the first character of each key
                    char c1 = s1.charAt(0);
                    char c2 = s2.charAt(0);

                    if (Character.isLetter(c1) && !Character.isLetter(c2)) {
                        return -1; // s1 is a letter, s2 is not; s1 comes first
                    } else if (!Character.isLetter(c1) && Character.isLetter(c2)) {
                        return 1; // s1 is not a letter, s2 is; s2 comes first
                    } else if (Character.isLetter(c1) && Character.isLetter(c2)) {
                        return s2.compareTo(s1); // both s1 and s2 are letters; compare them normally
                    } else {
                        // neither s1 nor s2 is a letter; parse them as integers and compare
                        int i1 = Integer.parseInt(s1);
                        int i2 = Integer.parseInt(s2);
                        return Integer.compare(i2, i1);
                    }
                }
            };

// sort the keys using the custom Comparator
            Collections.sort(keys, keyComparator);

// create a LinkedHashMap to preserve the order of the sorted keys
            for (String key : keys) {
                batchwise_itemDespHashmap_local.put(key, batchwise_itemDespHashmapp.get(key));
            }

            System.out.println(batchwise_itemDespHashmap_local);

        }
        catch (Exception e){
            e.printStackTrace();
        }

        */

/*
        ArrayList<String> sortedKeys
                = new ArrayList<String>(batchwise_itemDespHashmapp.keySet());
// create a custom Comparator for sorting mixed-format keys
        Comparator<String> keyComparator = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                // compare the first character of each key
                char c1 = s1.charAt(0);
                char c2 = s2.charAt(0);

                if (Character.isLetter(c1) && !Character.isLetter(c2)) {
                    return -1; // s1 is a letter, s2 is not; s1 comes first
                } else if (!Character.isLetter(c1) && Character.isLetter(c2)) {
                    return 1; // s1 is not a letter, s2 is; s2 comes first
                } else if (Character.isLetter(c1) && Character.isLetter(c2)) {
                    return s1.compareTo(s2); // both s1 and s2 are letters; compare them normally
                } else {
                    // neither s1 nor s2 is a letter; parse them as integers and compare
                    int i1 = Integer.parseInt(s1);
                    int i2 = Integer.parseInt(s2);
                    return Integer.compare(i1, i2);
                }
            }
        };
        Collections.sort(sortedKeys, keyComparator);

        // Display the TreeMap which is naturally sorted
        for (String x : sortedKeys) {
            System.out.println("Key = " + x + ", Value = " + batchwise_itemDespHashmapp.get(x));
            batchwise_itemDespHashmap_local.put(x,batchwise_itemDespHashmapp.get(x));

        }

 */


        TreeMap<String, JSONObject> sortedMap = new TreeMap<>(batchwise_itemDespHashmapp);


        batchwise_itemDespHashmap.clear();
        batchwise_itemDespHashmap_local = new HashMap<>(sortedMap);
        return batchwise_itemDespHashmap_local;
    }

    private void call_and_init_GoatEarTagDetails_BulkUpdate(String callMethod) {

        try {

            if (isGoatEarTagDetailsTableServiceCalled) {

                return;
            }
            isGoatEarTagDetailsTableServiceCalled = true;
            goatEarTagDetailsBulkUpdateInterface = new GoatEarTagDetails_BulkUpdateInterface() {


                @Override
                public void notifySuccess(String result) {
                    isGoatEarTagDetailsTableServiceCalled = false;
                    Create_and_SharePdf(false);
                    showProgressBar(false);
                    // ((BillingScreen) CheckOut_activity.this).Create_and_SharePdf();
                    //    DeliveryCenter_PlaceOrderScreen_SecondVersn.deliveryCenter_placeOrderScreen_secondVersn.Create_and_SharePdf();
                    //((BillingScreen) CheckOut_activity.this).neutralizeArray_and_OtherValues();
                }

                @Override
                public void notifySuccessForGettingListItem(ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch) {
                    isGoatEarTagDetailsTableServiceCalled = false;
                    //  ((BillingScreen) CheckOut_activity.this).neutralizeArray_and_OtherValues();
                    // ((BillingScreen) CheckOut_activity.this).closeFragment();
                }

                @Override
                public void notifyVolleyError(VolleyError error) {
                    // ((BillingScreen) CheckOut_activity.this).neutralizeArray_and_OtherValues();
                    Toast.makeText(CheckOut_activity.this, "There is an volley error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                    // ((BillingScreen) CheckOut_activity.this).closeFragment();

                    isGoatEarTagDetailsTableServiceCalled = false;
                }

                @Override
                public void notifyProcessingError(Exception error) {
                    Toast.makeText(CheckOut_activity.this, "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                    // ((BillingScreen) CheckOut_activity.this).neutralizeArray_and_OtherValues();
                    // ((BillingScreen) CheckOut_activity.this).closeFragment();
                    isGoatEarTagDetailsTableServiceCalled = false;


                }

            };


            Modal_B2BOrderItemDetails modal_b2BOrderItemDetails = new Modal_B2BOrderItemDetails();

            modal_b2BOrderItemDetails.setDeliverycentrekey_static(String.valueOf(deliveryCentreKey));
            modal_b2BOrderItemDetails.setOrderplaceddate_static(String.valueOf(orderplaceddate));
            modal_b2BOrderItemDetails.setStatus(String.valueOf(Constants.goatEarTagStatus_Sold));
            modal_b2BOrderItemDetails.setOrderid_static(String.valueOf(orderid));
            modal_b2BOrderItemDetails.setRetailerkey_static(String.valueOf(retailerKey));
            modal_b2BOrderItemDetails.setRetailermobileno_static(String.valueOf(retailermobileno));
            modal_b2BOrderItemDetails.setEarTagDetailsArrayList_String(cartItemDetailsList_String);


            try {

                String addApiToCall = API_Manager.updateGoatEarTag;
                GoatEarTagDetails_BulkUpdate asyncTask = new GoatEarTagDetails_BulkUpdate(goatEarTagDetailsBulkUpdateInterface, addApiToCall, callMethod, modal_b2BOrderItemDetails, orderplaceddate, usermobileno_string,cartItemDetails_Hashmap);
                asyncTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }




    }

    private void Initialize_and_ExecuteB2BCartItemDetails_BulkUpdate(String callDELETEMethod) {
        try {
            showProgressBar(true);
            if (isB2BCartItemDetailsBulkUpdate) {

                return;
            }
            isB2BCartItemDetailsBulkUpdate = true;
            callbackB2BCartItemDetails_bulkUpdateInterface = new B2BCartItemDetails_BulkUpdateInterface() {


                @Override
                public void notifySuccess(String result) {
                    isB2BCartItemDetailsBulkUpdate = false;

                }

                @Override
                public void notifySuccessForGettingListItem(ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch) {
                    isB2BCartItemDetailsBulkUpdate = false;
                    showProgressBar(false);


                }

                @Override
                public void notifyVolleyError(VolleyError error) {
                    Toast.makeText(CheckOut_activity.this, "There is an volley error while updating Ear Tag", Toast.LENGTH_SHORT).show();

                    showProgressBar(false);
                    isGoatEarTagDetailsTableServiceCalled = false;
                }

                @Override
                public void notifyProcessingError(Exception error) {
                    Toast.makeText(CheckOut_activity.this, "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                    showProgressBar(false);
                    isGoatEarTagDetailsTableServiceCalled = false;


                }

            };




            try {

                B2BCartItemDetails_BulkUpdate asyncTask = new B2BCartItemDetails_BulkUpdate(callbackB2BCartItemDetails_bulkUpdateInterface, orderid  , cartItemDetailsList_String);
                asyncTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }




    public void Initialize_and_ExecuteB2BCartOrderDetails(String callMethod, String valuetoUpdate, boolean isneedToGenerateInvoiceNo) {


        showProgressBar(true);
        if (isB2BCartOrderTableServiceCalled) {
            // showProgressBar(false);
            return;
        }
        isB2BCartOrderTableServiceCalled = true;
        b2BCartOrderDetailsInterface = new B2BCartOrderDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BCartOrderDetails> arrayList) {

            }

            @Override
            public void notifySuccess(String result) {
                if (result.equals(Constants.emptyResult_volley)) {
                    showProgressBar(false);
                    
                    isB2BCartOrderTableServiceCalled = false;

                    retailerKey ="";
                    retailermobileno = "";
                    retailername = "";
                    orderid = "";
                    retaileraddress = "";
                    selectedPaymentMode = "";

                    CalculateAndSetTotal_Quantity_Price_Values();



                    orderid = String.valueOf(System.currentTimeMillis());
                  
                    // Initialize_And_Process_InvoiceNoManager("GET", false);
                    Toast.makeText(CheckOut_activity.this, "There is no Cart Details for this batch", Toast.LENGTH_SHORT).show();


                }
                else {
                    //showProgressBar(false);
                   
                    //batchno = Modal_B2BCartOrderDetails.getBatchno();
                     if(!callMethod.equals(Constants.CallDELETEMethod)) {

                         selectedPaymentMode = Modal_B2BCartOrderDetails.getPaymentMode();

                         retailerKey = Modal_B2BCartOrderDetails.getRetailerkey();
                         retailermobileno = Modal_B2BCartOrderDetails.getRetailermobileno();
                         retailername = Modal_B2BCartOrderDetails.getRetailername();
                         orderid = Modal_B2BCartOrderDetails.getOrderid();
                         retaileraddress = Modal_B2BCartOrderDetails.getRetaileraddress();
                         discount_String = Modal_B2BCartOrderDetails.getDiscountAmount();

                         if(!callMethod.equals(Constants.CallUPDATEMethod)) {

                             String[] paymentMode = getResources().getStringArray(R.array.paymentmode);
                             for (int i = 0; i < paymentMode.length; i++) {
                                 if (paymentMode[i].toUpperCase().equals(selectedPaymentMode.toUpperCase())) {
                                     isPaymentNotSelectedManually = true;
                                     totalPaymentmode_Spinner.setSelection(i);

                                 }
                             }
                         }
                         finalFeedValue_textView.setText(String.valueOf(Modal_B2BCartOrderDetails.getFeedPrice()));
                         totalFeedPriceTextview.setText(String.valueOf(Modal_B2BCartOrderDetails.getFeedPrice()));
                         feedPricePerKg_editText.setText(String.valueOf(Modal_B2BCartOrderDetails.getFeedPriceperkg()));
                         feedWeight_editText.setText(String.valueOf(Modal_B2BCartOrderDetails.getFeedWeight()));

                         retailerAddress_textView.setText(String.valueOf(retaileraddress));
                         retailerName_textView.setText(String.valueOf(retailername));


                        retailerName_edittext.setText(String.valueOf(retailername));
                        retailerName_edittext.clearFocus();
                        retailerName_edittext.setThreshold(1);
                        retailerName_edittext.dismissDropDown();

                         retailerMobileNo_edittext.setText(String.valueOf(retailermobileno));
                         retailerMobileNo_edittext.clearFocus();
                         retailerMobileNo_edittext.setThreshold(1);
                         retailerMobileNo_edittext.dismissDropDown();
                         discountValue_editText.setText(discount_String);
                         if(retailermobileno.replaceAll("[+]91","").length() ==10){
                             isRetailerSelected = true;
                         }
                         else{
                             isRetailerSelected = false;
                         }


                         Call_And_Execute_B2BCartOrderItem_Details(Constants.CallGETListMethod);

                     }




                    if(retailermobileno.replaceAll("[+]91","").length() ==10){
                        isRetailerSelected = true;
                    }
                    else{
                        isRetailerSelected = false;
                    }

                    //batchNo_textview.setText(batchno);


                    isB2BCartOrderTableServiceCalled = false;
                }
            }
            @Override
            public void notifyVolleyError(VolleyError error) {
                
                Toast.makeText(CheckOut_activity.this, "There is an volley error while updating CartOrder Details", Toast.LENGTH_SHORT).show();
                showProgressBar(false);
                isB2BCartOrderTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                
                Toast.makeText(CheckOut_activity.this, "There is an Process error while updating CartOrder Details", Toast.LENGTH_SHORT).show();

                showProgressBar(false);
                isB2BCartOrderTableServiceCalled = false;


            }




        };

        if(callMethod.equals(Constants.CallADDMethod)){

            Modal_B2BCartOrderDetails.orderid = orderid;
            Modal_B2BCartOrderDetails.batchno = "";
            Modal_B2BCartOrderDetails.deliverycenterkey = deliveryCentreKey;
            Modal_B2BCartOrderDetails.deliverycentername = deliveryCentreName;
            //   Modal_B2BCartOrderDetails.priceperkg = pricePerKg_editText.getText().toString();
            Modal_B2BCartOrderDetails.paymentMode = selectedPaymentMode;

            Modal_B2BCartOrderDetails.retailerkey = retailerKey;
            Modal_B2BCartOrderDetails.retailermobileno = retailermobileno;
            Modal_B2BCartOrderDetails.retailername = retailername;
            Modal_B2BCartOrderDetails.retaileraddress = retaileraddress;
            Modal_B2BCartOrderDetails.feedPrice =String.valueOf(totalFeedPriceTextview.getText().toString());
            Modal_B2BCartOrderDetails.feedPriceperkg =String.valueOf(feedPricePerKg_editText.getText().toString());
            Modal_B2BCartOrderDetails.feedWeight =String.valueOf(feedWeight_editText.getText().toString());

            Modal_B2BCartOrderDetails.itemaddeddate = DateParser.getDate_and_time_newFormat();
            Modal_B2BCartOrderDetails.discountAmount = discount_String;
            String getApiToCall = API_Manager.addCartOrderDetails ;
            B2BCartOrderDetails asyncTask = new B2BCartOrderDetails(b2BCartOrderDetailsInterface,  getApiToCall, Constants.CallADDMethod);
            asyncTask.execute();

        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){

            if(valuetoUpdate.equals(getString(R.string.retailername))){
                Modal_UpdatedB2BCartOrderDetails.setRetailername( retailername);
                Modal_UpdatedB2BCartOrderDetails.setRetailerkey( retailerKey);
                Modal_UpdatedB2BCartOrderDetails.setRetailermobileno(retailermobileno);
                Modal_UpdatedB2BCartOrderDetails.setRetaileraddress(retaileraddress);


            }
            else if(valuetoUpdate.equals(getString(R.string.discount))) {
                Modal_UpdatedB2BCartOrderDetails.setDiscount(discount_String);
            }

            else if(valuetoUpdate.equals(getString(R.string.feedvalue))) {

                Modal_UpdatedB2BCartOrderDetails.setFeedPrice(String.valueOf(totalFeedPriceTextview.getText().toString()));
                Modal_UpdatedB2BCartOrderDetails.setFeedPriceperkg(String.valueOf(feedPricePerKg_editText.getText().toString()));
                Modal_UpdatedB2BCartOrderDetails.setFeedWeight(String.valueOf(feedWeight_editText.getText().toString()));


            }
            else if(valuetoUpdate.equals(getString(R.string.paymentmode))) {
                Modal_UpdatedB2BCartOrderDetails.setPaymentMode(selectedPaymentMode);
            }
            Modal_UpdatedB2BCartOrderDetails.deliverycentername = deliveryCentreName;

            Modal_UpdatedB2BCartOrderDetails.deliverycenterkey = deliveryCentreKey;
            Modal_UpdatedB2BCartOrderDetails.orderid =orderid;

            String getApiToCall = API_Manager.updateCartOrderDetails ;
            B2BCartOrderDetails asyncTask = new B2BCartOrderDetails(b2BCartOrderDetailsInterface,  getApiToCall, Constants.CallUPDATEMethod);
            asyncTask.execute();
            //  Modal_B2BCartOrderDetails.priceperkg = pricePerKg_editText.getText().toString();
            Modal_B2BCartOrderDetails.retailerkey = retailerKey;
            Modal_B2BCartOrderDetails.paymentMode = selectedPaymentMode;
            Modal_B2BCartOrderDetails.retailermobileno = retailermobileno;
            Modal_B2BCartOrderDetails.retailername = retailername;
            Modal_B2BCartOrderDetails.discountAmount = discount_String;
            Modal_B2BCartOrderDetails.retaileraddress = retaileraddress;
            Modal_B2BCartOrderDetails.feedPrice =String.valueOf(totalFeedPriceTextview.getText().toString());
            Modal_B2BCartOrderDetails.feedPriceperkg =String.valueOf(feedPricePerKg_editText.getText().toString());
            Modal_B2BCartOrderDetails.feedWeight =String.valueOf(feedWeight_editText.getText().toString());
        }
        else if(callMethod.equals(Constants.CallGETMethod)){
            //String getApiToCall = API_Manager.getCartOrderDetailsForBatchno+"?batchno="+batchno ;
            String getApiToCall = API_Manager.getCartOrderDetailsForDeliveryCentrekey+"?deliverycentrekey="+deliveryCentreKey ;

            B2BCartOrderDetails asyncTask = new B2BCartOrderDetails(b2BCartOrderDetailsInterface,  getApiToCall, Constants.CallGETMethod);
            asyncTask.execute();

        }
        else if(callMethod.equals(Constants.CallGETListMethod)){

        }
        else if(callMethod.equals(Constants.CallDELETEMethod)){
            try {

                String addApiToCall = API_Manager.deleteCartOrderDetails+"?orderid="+orderid+"&deliverycentrekey="+deliveryCentreKey;
                B2BCartOrderDetails asyncTask = new B2BCartOrderDetails(b2BCartOrderDetailsInterface, addApiToCall, Constants.CallDELETEMethod);
                asyncTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }




    private void addDatatoOrderTypeSpinner() {
        isPaymentNotSelectedManually = true;
        String[] ordertype=getResources().getStringArray(R.array.paymentmode);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CheckOut_activity.this, android.R.layout.simple_spinner_item, ordertype);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        totalPaymentmode_Spinner.setAdapter(arrayAdapter);

        String[] paymentMode=getResources().getStringArray(R.array.paymentmode);
        for(int i =0 ; i<paymentMode.length;i++){
            if(paymentMode[i].toUpperCase().equals(selectedPaymentMode.toUpperCase())){
                isPaymentNotSelectedManually =true;
                totalPaymentmode_Spinner.setSelection(i);

            }
        }

    }

    public static void CalculateAndSetTotal_Quantity_Price_Values() {
        final_totalPriceWithOutDiscountWithFeedAmount =0;
        final_totalWeight = 0  ; final_totalGoats =0; final_totalPriceWithOutDiscountWithoutFeed = 0 ; final_batchValue = 0; final_discountValue = 0 ; final_totalPayment = 0 ;
        String meatYield_String ="0" , approxLiveWeight_String = "0"  , parts_String ="0" , price_String = "0", discount_String_itemDetails ="0",
                totalPrice_String="0",totalWeight_String="0" ,totalFeedString ="0";

        double meatYield_Double =0 , approxLiveWeight_Double =0 , parts_Double =0 , price_Double =0 , discount_Double =0 , totalPrice_Double =0 , totalWeight_Double =0 ;
        double maleRatioValue_double=0 , femaleRatioValue_double =0 ,meatYieldAvg = 0, approxLiveWeightAvg =0; ;
        double maleCount_int = 0 ,femaleCountInt=0 , totalMeatYeild =0 , totalaApproxLiveWeight = 0 ,totalFeedDouble =0;
        String male_FemaleRatio = "";
        batchwise_itemDespHashmap .clear();
        batchList_String.clear();
        if(cartItemDetailsList_String.size()>0)
        {
            for(int i =0 ; i<cartItemDetailsList_String.size();i++){
                double maleCount_Batchwise =0  , femaleCount_Batchwise = 0 , totalCount_Batchwise = 0,totalWeight_Batchwise =0,
                        discount_batchwise =0 , totalAmount_batchwise =0 ;
                meatYield_Double =0 ; approxLiveWeight_Double =0 ; parts_Double =0 ; price_Double =0; discount_Double =0 ; totalPrice_Double =0 ;
                maleRatioValue_double=0 ; femaleRatioValue_double =0 ;meatYieldAvg = 0; approxLiveWeightAvg =0;totalFeedDouble =0;
                meatYield_String ="0" ; approxLiveWeight_String = "0"  ; parts_String ="0" ; price_String = "0"; discount_String_itemDetails ="0";
                totalPrice_String="0";  totalWeight_String="0";totalFeedString ="0";
                Modal_B2BCartItemDetails modal_b2BCartItemDetails = cartItemDetails_Hashmap.get(cartItemDetailsList_String.get(i));
                try{
                    try{
                        try{
                            meatYield_String = String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getMeatyieldweight());
                        }
                        catch (Exception e){
                            meatYield_String ="0";
                            e.printStackTrace();
                        }






                        try{
                            approxLiveWeight_String = String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getApproxliveweight());
                        }
                        catch (Exception e){
                            approxLiveWeight_String ="0";
                            e.printStackTrace();
                        }
                        try{
                            parts_String = String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getPartsweight());
                        }
                        catch (Exception e){
                            parts_String ="0";
                            e.printStackTrace();
                        }
                        try{
                            price_String = String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getItemprice());
                        }
                        catch (Exception e){
                            price_String ="0";
                            e.printStackTrace();
                        }
                        try{
                            discount_String_itemDetails = String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getDiscount());
                        }
                        catch (Exception e){
                            discount_String_itemDetails ="0";
                            e.printStackTrace();
                        }
                        try{
                            totalPrice_String = String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getTotalPrice_ofItem());
                        }
                        catch (Exception e){
                            totalPrice_String ="0";
                            e.printStackTrace();
                        }

                        try{
                            totalWeight_String = String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getTotalItemWeight());
                        }
                        catch (Exception e){
                            totalWeight_String ="0";
                            e.printStackTrace();
                        }






                        try{
                            if(String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getGender()).toUpperCase().equals("MALE")){
                                maleCount_int = maleCount_int +1 ;
                            }
                            else  if(String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getGender()).toUpperCase().equals("FEMALE")){
                                femaleCountInt = femaleCountInt +1 ;

                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                        try{
                            maleRatioValue_double  = ((maleCount_int/cartItemDetailsList_String.size()) *100);

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }



                        try{
                            femaleRatioValue_double  = ((femaleCountInt/cartItemDetailsList_String.size()) *100);

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }



                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    try{
                        try{
                            String text ="";
                            text = totalWeight_String.replaceAll("[^\\d.]", "");
                            if(text.equals("")){
                                totalWeight_String = "0";
                            }
                            else{
                                totalWeight_String = text;
                            }
                        }
                        catch (Exception e){
                            totalWeight_String ="0";
                            e.printStackTrace();
                        }

                        try{
                            String text ="";
                            text = totalPrice_String.replaceAll("[^\\d.]", "");
                            if(text.equals("")){
                                totalPrice_String = "0";
                            }
                            else{
                                totalPrice_String = text;
                            }
                        }
                        catch (Exception e){
                            totalPrice_String ="0";
                            e.printStackTrace();
                        }

                        try{
                            String text ="";
                            text = discount_String.replaceAll("[^\\d.]", "");
                            if(text.equals("")){
                                discount_String = "0";
                            }
                            else{
                                discount_String = text;
                            }
                        }
                        catch (Exception e){
                            discount_String ="0";
                            e.printStackTrace();
                        }
                        try{
                            String text ="";
                            text = price_String.replaceAll("[^\\d.]", "");
                            if(text.equals("")){
                                price_String = "0";
                            }
                            else{
                                price_String = text;
                            }
                        }
                        catch (Exception e){
                            price_String ="0";
                            e.printStackTrace();
                        }
                        try{
                            String text ="";
                            text = parts_String.replaceAll("[^\\d.]", "");
                            if(text.equals("")){
                                parts_String = "0";
                            }
                            else{
                                parts_String = text;
                            }
                        }
                        catch (Exception e){
                            parts_String ="0";
                            e.printStackTrace();
                        }

                        try{
                            String text ="";
                            text = approxLiveWeight_String.replaceAll("[^\\d.]", "");
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
                            String text ="";
                            text = meatYield_String.replaceAll("[^\\d.]", "");
                            if(text.equals("")){
                                meatYield_String = "0";
                            }
                            else{
                                meatYield_String = text;
                            }
                        }
                        catch (Exception e){
                            meatYield_String ="0";
                            e.printStackTrace();
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    try{
                        try{
                            meatYield_Double = Double.parseDouble(meatYield_String);
                        }
                        catch (Exception e){
                            meatYield_Double  = 0;
                            e.printStackTrace();

                        }


                        try{
                            totalMeatYeild = totalMeatYeild + meatYield_Double;
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
                            parts_Double = Double.parseDouble(parts_String);
                        }
                        catch (Exception e){
                            parts_Double  = 0;
                            e.printStackTrace();

                        }
                        try{
                            price_Double = Double.parseDouble(price_String);
                        }
                        catch (Exception e){
                            price_Double  = 0;
                            e.printStackTrace();

                        }

                        try{
                            discount_Double = Double.parseDouble(discount_String);
                        }
                        catch (Exception e){
                            discount_Double  = 0;
                            e.printStackTrace();

                        }
                        try{
                            totalPrice_Double = Double.parseDouble(totalPrice_String);
                        }
                        catch (Exception e){
                            totalPrice_Double  = 0;
                            e.printStackTrace();

                        }


                        try{
                            totalWeight_Double = Double.parseDouble(totalWeight_String);
                        }
                        catch (Exception e){
                            totalWeight_Double  = 0;
                            e.printStackTrace();

                        }



                    }
                    catch (Exception e){
                        e.printStackTrace();

                    }



                    try{
                        //       final_discountValue = discount_Double + final_discountValue;


                        try{
                            String text =  String.valueOf(discount_String);
                            text = text.replaceAll("[^\\d.]", "");
                            if(text.equals("")){
                                text = "0";
                            }
                            final_discountValue  = Double.parseDouble(text);


                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                    try{
                        final_totalPriceWithOutDiscountWithoutFeed = price_Double + final_totalPriceWithOutDiscountWithoutFeed;
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    try{
                        final_totalWeight  = totalWeight_Double + final_totalWeight;
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }




                    try{
                        String text ="";
                        text = String.valueOf(totalFeedPriceTextview.getText().toString()).replaceAll("[^\\d.]", "");
                        if(text.equals("")){
                            totalFeedString = "0";
                        }
                        else{
                            totalFeedString = text;
                        }
                    }
                    catch (Exception e){
                        totalFeedString ="0";
                        e.printStackTrace();
                    }

                    try{
                        totalFeedDouble = Double.parseDouble(totalFeedString);
                    }
                    catch (Exception e){
                        totalFeedDouble =0;
                        e.printStackTrace();
                    }


                    try{
                        final_totalPriceWithOutDiscountWithFeedAmount = final_totalPriceWithOutDiscountWithoutFeed + totalFeedDouble;
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }



                    try{
                        final_totalPayment  =  final_totalPriceWithOutDiscountWithFeedAmount  -  final_discountValue;
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                    try{
                        final_totalGoats = cartItemDetailsList_String.size();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    try{
                        if(final_totalPriceWithOutDiscountWithFeedAmount ==0 || final_totalGoats ==0 ){
                            try{
                                final_batchValue  =  0;
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        else{

                            try{
                                final_batchValue  =  final_totalPriceWithOutDiscountWithoutFeed / final_totalGoats;
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
                        final_batchValue  =  Double.parseDouble(twoDecimalConverter.format(final_batchValue));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }



                    try{
                        meatYieldAvg = totalMeatYeild /final_totalGoats;
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    try{
                        approxLiveWeightAvg = totalaApproxLiveWeight /final_totalGoats;

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                }
                catch (Exception e){
                    e.printStackTrace();
                }

                try{
                    finaltotalWeight_textView.setText(String.valueOf(twoDecimalConverter.format(final_totalWeight)));
                }
                catch (Exception e){
                    finaltotalWeight_textView.setText(String.valueOf(final_totalWeight));
                    e.printStackTrace();
                }

                try{
                    totalValue_textView.setText(String.valueOf(twoDecimalConverterWithComma.format(final_totalPriceWithOutDiscountWithFeedAmount)));
                }
                catch (Exception e){
                    totalValue_textView.setText(String.valueOf(final_totalPriceWithOutDiscountWithFeedAmount));
                    e.printStackTrace();
                }

                if(String.valueOf(batchValue_textView.getText().toString()).toUpperCase().equals("NAN")){
                    batchValue_textView.setText(String.valueOf("0.000"));

                }
                try{
                    batchValue_textView.setText(String.valueOf(twoDecimalConverterWithComma.format(final_batchValue)));
                }
                catch (Exception e){
                    batchValue_textView.setText(String.valueOf(final_batchValue));
                    e.printStackTrace();
                }


                    try{
                        if(!batchList_String.contains(String.valueOf(modal_b2BCartItemDetails.getBatchno()))) {
                            if (batchwise_itemDespHashmap.containsKey(String.valueOf(modal_b2BCartItemDetails.getBatchno()))) {
                                JSONObject jsonObject = new JSONObject();

                                jsonObject = batchwise_itemDespHashmap.get(String.valueOf(modal_b2BCartItemDetails.getBatchno()));

                                try{
                                    if(String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getGender()).toUpperCase().equals("MALE")){
                                        try {
                                            if (jsonObject.has("male")) {
                                                maleCount_Batchwise = jsonObject.getDouble("male");
                                                maleCount_Batchwise = maleCount_Batchwise + 1;
                                            } else {
                                                maleCount_Batchwise = 1;

                                            }
                                        } catch (Exception e) {
                                            maleCount_Batchwise = 1;
                                            e.printStackTrace();
                                        }

                                        try {

                                            jsonObject.put("male", (int) maleCount_Batchwise);


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }


                                    }
                                    else  if(String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getGender()).toUpperCase().equals("FEMALE")){
                                        try {
                                            if (jsonObject.has("female")) {
                                                femaleCount_Batchwise = jsonObject.getDouble("female");
                                                femaleCount_Batchwise = femaleCount_Batchwise + 1;
                                            } else {
                                                femaleCount_Batchwise = 1;

                                            }
                                        } catch (Exception e) {
                                            femaleCount_Batchwise = 1;
                                            e.printStackTrace();
                                        }

                                        try {

                                            jsonObject.put("female", (int) femaleCount_Batchwise);


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }


                                    }
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }




                                try {
                                    if (jsonObject.has("totalcount")) {
                                        totalCount_Batchwise = jsonObject.getDouble("totalcount");
                                        totalCount_Batchwise = totalCount_Batchwise + 1;
                                    } else {
                                        totalCount_Batchwise = 1;

                                    }
                                } catch (Exception e) {
                                    totalCount_Batchwise = 1;
                                    e.printStackTrace();
                                }

                                try {

                                    jsonObject.put("totalcount", (int) totalCount_Batchwise);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }



                                try {
                                    if (jsonObject.has("totalweight")) {
                                        totalWeight_Batchwise = jsonObject.getDouble("totalweight");
                                        totalWeight_Batchwise = totalWeight_Batchwise + Double.parseDouble(ConvertKilogramstoGrams(String.valueOf(totalWeight_Double)));
                                    } else {
                                        totalWeight_Batchwise = Double.parseDouble(ConvertKilogramstoGrams(String.valueOf(totalWeight_Double)));

                                    }
                                } catch (Exception e) {
                                    totalWeight_Batchwise = Double.parseDouble(ConvertKilogramstoGrams(String.valueOf(totalWeight_Double)));
                                    e.printStackTrace();
                                }

                                try {

                                    jsonObject.put("totalweight", (totalWeight_Batchwise));


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }



                                try {
                                    if (jsonObject.has("totalprice")) {
                                        totalAmount_batchwise = jsonObject.getDouble("totalprice");
                                        totalAmount_batchwise = totalAmount_batchwise + price_Double;
                                    } else {
                                        totalAmount_batchwise = price_Double;

                                    }
                                } catch (Exception e) {
                                    totalAmount_batchwise = price_Double;
                                    e.printStackTrace();
                                }

                                try {

                                    jsonObject.put("totalprice", Double.parseDouble(twoDecimalConverter.format(totalAmount_batchwise)));


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                try {

                                    jsonObject.put("batchno", String.valueOf(modal_b2BCartItemDetails.getBatchno()));


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                try {
                                    batchList_String.add(String.valueOf(modal_b2BCartItemDetails.getBatchno()));
                                    batchwise_itemDespHashmap.put(String.valueOf(modal_b2BCartItemDetails.getBatchno()), jsonObject);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            } else {
                                JSONObject jsonObject = new JSONObject();

                                try {

                                    jsonObject.put("totalprice", Double.parseDouble(twoDecimalConverter.format(price_Double)));


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                try {

                                    jsonObject.put("totalweight", Double.parseDouble(ConvertKilogramstoGrams(String.valueOf(twoDecimalConverter.format(totalWeight_Double)))));


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                try {

                                    jsonObject.put("totalcount", (int) 1);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                try{
                                    if(String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getGender()).toUpperCase().equals("MALE")){
                                        try {

                                            jsonObject.put("male", (int) 1);


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }                                    }
                                    else  if(String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getGender()).toUpperCase().equals("FEMALE")){
                                        try {

                                            jsonObject.put("female", (int) 1);


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }





                                try {

                                    jsonObject.put("batchno", String.valueOf(modal_b2BCartItemDetails.getBatchno()));


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                try {
                                    batchList_String.add(String.valueOf(modal_b2BCartItemDetails.getBatchno()));
                                    batchwise_itemDespHashmap.put(String.valueOf(modal_b2BCartItemDetails.getBatchno()), jsonObject);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                        else{
                            if (batchwise_itemDespHashmap.containsKey(String.valueOf(modal_b2BCartItemDetails.getBatchno()))) {
                                JSONObject jsonObject = new JSONObject();

                                jsonObject = batchwise_itemDespHashmap.get(String.valueOf(modal_b2BCartItemDetails.getBatchno()));


                                try{
                                    if(String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getGender()).toUpperCase().equals("MALE")){
                                        try {
                                            if (jsonObject.has("male")) {
                                                maleCount_Batchwise = jsonObject.getDouble("male");
                                                maleCount_Batchwise = maleCount_Batchwise + 1;
                                            } else {
                                                maleCount_Batchwise = 1;

                                            }
                                        } catch (Exception e) {
                                            maleCount_Batchwise = 1;
                                            e.printStackTrace();
                                        }
                                        try {

                                            jsonObject.put("male", (int) maleCount_Batchwise);


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    else  if(String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getGender()).toUpperCase().equals("FEMALE")){
                                        try {
                                            if (jsonObject.has("female")) {
                                                femaleCount_Batchwise = jsonObject.getDouble("female");
                                                femaleCount_Batchwise = femaleCount_Batchwise + 1;
                                            } else {
                                                femaleCount_Batchwise = 1;

                                            }
                                        } catch (Exception e) {
                                            femaleCount_Batchwise = 1;
                                            e.printStackTrace();
                                        }


                                        try {

                                            jsonObject.put("female", (int) femaleCount_Batchwise);


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }





                                try {
                                    if (jsonObject.has("totalcount")) {
                                        totalCount_Batchwise = jsonObject.getDouble("totalcount");
                                        totalCount_Batchwise = totalCount_Batchwise + 1;
                                    } else {
                                        totalCount_Batchwise = 1;

                                    }
                                } catch (Exception e) {
                                    totalCount_Batchwise = 1;
                                    e.printStackTrace();
                                }

                                try {

                                    jsonObject.put("totalcount", (int) totalCount_Batchwise);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                try {
                                    if (jsonObject.has("totalweight")) {
                                        totalWeight_Batchwise = jsonObject.getDouble("totalweight");
                                        totalWeight_Batchwise = totalWeight_Batchwise + Double.parseDouble(ConvertKilogramstoGrams(String.valueOf(totalWeight_Double)));
                                    } else {
                                        totalWeight_Batchwise = Double.parseDouble(ConvertKilogramstoGrams(String.valueOf(totalWeight_Double)));

                                    }
                                } catch (Exception e) {
                                    totalWeight_Batchwise = Double.parseDouble(ConvertKilogramstoGrams(String.valueOf(totalWeight_Double)));
                                    e.printStackTrace();
                                }

                                try {

                                    jsonObject.put("totalweight", (totalWeight_Batchwise));


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                try {
                                    if (jsonObject.has("totalprice")) {
                                        totalAmount_batchwise = jsonObject.getDouble("totalprice");
                                        totalAmount_batchwise = totalAmount_batchwise + price_Double;
                                    } else {
                                        totalAmount_batchwise = price_Double;

                                    }
                                } catch (Exception e) {
                                    totalAmount_batchwise = price_Double;
                                    e.printStackTrace();
                                }

                                try {

                                    jsonObject.put("totalprice", Double.parseDouble(twoDecimalConverter.format(totalAmount_batchwise)));


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                try {

                                    jsonObject.put("batchno", String.valueOf(modal_b2BCartItemDetails.getBatchno()));


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                try {
                                   // batchList_String.add(String.valueOf(modal_b2BCartItemDetails.getBatchno()));
                                    batchwise_itemDespHashmap.put(String.valueOf(modal_b2BCartItemDetails.getBatchno()), jsonObject);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                            else {
                                JSONObject jsonObject = new JSONObject();

                                try {

                                    jsonObject.put("totalprice", Double.parseDouble(twoDecimalConverter.format(price_Double)));


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                try {

                                    jsonObject.put("totalweight", Double.parseDouble(ConvertKilogramstoGrams(String.valueOf(twoDecimalConverter.format(totalWeight_Double)))));


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                try {

                                    jsonObject.put("totalcount", (int) 1);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                try{
                                    if(String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getGender()).toUpperCase().equals("MALE")){
                                        try {

                                            jsonObject.put("male", (int) 1);


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }                                    }
                                    else  if(String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getGender()).toUpperCase().equals("FEMALE")){
                                        try {

                                            jsonObject.put("female", (int) 1);


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }


                                try {

                                    jsonObject.put("batchno", String.valueOf(modal_b2BCartItemDetails.getBatchno()));


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                try {
                              //      batchList_String.add(String.valueOf(modal_b2BCartItemDetails.getBatchno()));
                                    batchwise_itemDespHashmap.put(String.valueOf(modal_b2BCartItemDetails.getBatchno()), jsonObject);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }



                try{
                    totalGoats_textView.setText(String.valueOf(final_totalGoats));

                    finalPayment_textView.setText(String.valueOf(twoDecimalConverterWithComma.format(final_totalPayment)));
                    finalGoatValue_textView.setText(String.valueOf(twoDecimalConverterWithComma.format(final_totalPriceWithOutDiscountWithoutFeed)));
                    male_Qty_textview.setText(" "+String.valueOf((int) maleCount_int)+" nos ");
                    female_Qty_textview.setText(" "+String.valueOf((int) femaleCountInt)+" nos ");
                    male_percentage_textview.setText(("("+String.valueOf(threeDecimalConverter.format(maleRatioValue_double))+"%)"));
                    female_Percentage_textview.setText("("+String.valueOf(threeDecimalConverter.format(femaleRatioValue_double))+"%)" );

                    approxLiveWeightAvg_Textview.setText(String.valueOf(threeDecimalConverter.format(approxLiveWeightAvg)));
                    meatYieldWeightAvg_Textview.setText(String.valueOf(threeDecimalConverter.format(meatYieldAvg)));


                }
                catch (Exception e){
                    e.printStackTrace();
                }




            }
        }
        else{
            try{
                final_totalGoats = 0;
                final_totalWeight = 0;
                final_totalPriceWithOutDiscountWithFeedAmount = 0 ;
                final_batchValue = 0;
                final_totalPayment = 0;
                final_totalPriceWithOutDiscountWithoutFeed =0;
                batchwise_itemDespHashmap.clear();batchList_String.clear();
                totalGoats_textView.setText(String.valueOf(final_totalGoats));
                finaltotalWeight_textView.setText(String.valueOf(final_totalWeight));
                totalValue_textView.setText(String.valueOf(twoDecimalConverterWithComma.format(final_totalPriceWithOutDiscountWithFeedAmount)));
                batchValue_textView.setText(String.valueOf(final_batchValue));
                if(String.valueOf(batchValue_textView.getText().toString()).toUpperCase().equals("NAN")){
                    batchValue_textView.setText(String.valueOf("0.000"));

                }
                finalPayment_textView.setText(String.valueOf(final_totalPayment));
                finalGoatValue_textView.setText(String.valueOf(final_totalPriceWithOutDiscountWithoutFeed));
                male_Qty_textview.setText(" 0 nos ");
                female_Qty_textview.setText(" 0 nos ");
                male_percentage_textview.setText(("(0%)"));
                female_Percentage_textview.setText("(0%)" );

                approxLiveWeightAvg_Textview.setText("0");
                meatYieldWeightAvg_Textview.setText("0");

            }
            catch (Exception e){
                e.printStackTrace();
            }


        }


        try{



            cartItemDetailsList_String =  sortThisArrayUsingBarcode (cartItemDetailsList_String);
            showProgressBar(false);
            try{
                //  if(show_goatEarTagItemDetails_Dialog.isShowing()){
                //     show_goatEarTagItemDetails_Dialog.cancel();
                //    show_goatEarTagItemDetails_Dialog.dismiss();
                //  }
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }






    private static String ConvertKilogramstoGrams(String weightInGrams) {
        String weightinGramsString = "";

        try {
            weightInGrams = weightInGrams.replaceAll("[^\\d.]", "");

            if(weightInGrams.equals("") || weightInGrams.equals(null)){
                weightInGrams = "0";
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        double grossweightInKiloGramDouble = 0;
        try{
            grossweightInKiloGramDouble = Double.parseDouble(weightInGrams);
        }
        catch (Exception e){
            grossweightInKiloGramDouble = 0;
            e.printStackTrace();
        }
        if(grossweightInKiloGramDouble >0 ) {
            try {
                double temp = grossweightInKiloGramDouble * 1000;
                // double rf = Math.round((temp * 10.0) / 10.0);
                weightinGramsString = String.valueOf(temp);
            }
            catch (Exception e){
                weightinGramsString = weightInGrams;

                e.printStackTrace();
            }

        }
        else{
            weightinGramsString = weightInGrams;
        }
        return  weightinGramsString;


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


    private ArrayList<Modal_B2BRetailerDetails> sortThisArrayUsingRetailerName_mobileNo(ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList, String valuetoSort) {


        final Pattern p = Pattern.compile("^\\d+");
        if(valuetoSort.equals("NAME")){

            Comparator<Modal_B2BRetailerDetails> c = new Comparator<Modal_B2BRetailerDetails>() {
                @Override
                public int compare(Modal_B2BRetailerDetails object1, Modal_B2BRetailerDetails object2) {
                    Matcher m = p.matcher(object1.getRetailername());
                    Integer number1 = null;
                    if (!m.find()) {
                        Matcher m1 = p.matcher(object2.getRetailername());
                        if (m1.find()) {
                            return object2.getRetailername().compareTo(object1.getRetailername());
                        } else {
                            return object1.getRetailername().compareTo(object2.getRetailername());
                        }
                    } else {
                        Integer number2 = null;
                        number1 = Integer.parseInt(m.group());
                        m = p.matcher(object2.getRetailername());
                        if (!m.find()) {
                            // return object1.compareTo(object2);
                            Matcher m1 = p.matcher(object1.getRetailername());
                            if (m1.find()) {
                                return object2.getRetailername().compareTo(object1.getRetailername());
                            } else {
                                return object1.getRetailername().compareTo(object2.getRetailername());
                            }
                        } else {
                            number2 = Integer.parseInt(m.group());
                            int comparison = number1.compareTo(number2);
                            if (comparison != 0) {
                                return comparison;
                            } else {
                                return object1.getRetailername().compareTo(object2.getRetailername());
                            }
                        }
                    }
                }
            };

            Collections.sort(retailerDetailsArrayList, c);

        }
        else if(valuetoSort.equals("MOBILENUMBER")){

            Comparator<Modal_B2BRetailerDetails> c = new Comparator<Modal_B2BRetailerDetails>() {
                @Override
                public int compare(Modal_B2BRetailerDetails object1, Modal_B2BRetailerDetails object2) {
                    Matcher m = p.matcher(object1.getMobileno());
                    Integer number1 = null;
                    if (!m.find()) {
                        Matcher m1 = p.matcher(object2.getMobileno());
                        if (m1.find()) {
                            return object2.getMobileno().compareTo(object1.getMobileno());
                        } else {
                            return object1.getMobileno().compareTo(object2.getMobileno());
                        }
                    } else {
                        Integer number2 = null;
                        number1 = Integer.parseInt(m.group());
                        m = p.matcher(object2.getMobileno());
                        if (!m.find()) {
                            // return object1.compareTo(object2);
                            Matcher m1 = p.matcher(object1.getMobileno());
                            if (m1.find()) {
                                return object2.getMobileno().compareTo(object1.getMobileno());
                            } else {
                                return object1.getMobileno().compareTo(object2.getMobileno());
                            }
                        } else {
                            number2 = Integer.parseInt(m.group());
                            int comparison = number1.compareTo(number2);
                            if (comparison != 0) {
                                return comparison;
                            } else {
                                return object1.getMobileno().compareTo(object2.getMobileno());
                            }
                        }
                    }
                }
            };

            Collections.sort(retailerDetailsArrayList, c);

        }






        return  retailerDetailsArrayList;
    }




    private void Create_and_SharePdf(boolean isJustNeedTOAskPermission) {

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


                        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(CheckOut_activity.this, WRITE_EXTERNAL_STORAGE);
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

                            prepareDataForPDF();


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

                                prepareDataForPDF();


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


                        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(CheckOut_activity.this, WRITE_EXTERNAL_STORAGE);
                        //Log.d("ExportInvoiceActivity", "writeExternalStoragePermission "+writeExternalStoragePermission);
                        // If do not grant write external storage permission.
                        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                            // Request user to grant write external storage permission.
                            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE},
                                    REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
                        } else {
                            showProgressBar(true);
                            try {
                                prepareDataForPDF();


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
           // String filename = "OrderReceipt for : " +retailername+" - on : "+ DateParser.getDate_and_time_newFormat() + ".pdf";
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
                // addItemRowsInOldPDFFormat(layoutDocument);
                addItemRowsInNewPDFFormat(layoutDocument);
                layoutDocument.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            // document = new PdfDocument(new PdfWriter("MyFirstInvoice.pdf"));


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

           /* Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setType("application/pdf");
            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            startActivity(Intent.createChooser(share, "Share"));

            */

            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri outputPdfUri = FileProvider.getUriForFile(this, CheckOut_activity.this.getPackageName() + ".provider", file);

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

            Phrase phrasebilltimeDetails = new Phrase("Billno : "+tokenNo+"      DATE : "+DateParser.getDate_newFormat()+"      TIME : "+DateParser.getTime_newFormat(), valueFont_8);
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

            Phrase phraseSupervisorNameLabelTitle = new Phrase("Supervisor Name : "+String.valueOf(supervisorName) +"  ", valueFont_8Bold);

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

/*
            try {


                Phrase phraseSupervisorNameLabelTitle = new Phrase("Supervisor Name :  ", valueFont_10Bold);

                PdfPCell phraseSupervisorNameLabelTitlecell = new PdfPCell(phraseSupervisorNameLabelTitle);
                phraseSupervisorNameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                phraseSupervisorNameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phraseSupervisorNameLabelTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                phraseSupervisorNameLabelTitlecell.setPaddingLeft(6);
                phraseSupervisorNameLabelTitlecell.setPaddingBottom(3);
                Whole_WarehouseDetails_table.addCell(phraseSupervisorNameLabelTitlecell);


                Phrase phrasesupervisorNameTitle = new Phrase(supervisorName +"\n", valueFont_10);

                PdfPCell phrasesupervisorNameTitlecell = new PdfPCell(phrasesupervisorNameTitle);
                phrasesupervisorNameTitlecell.setBorder(Rectangle.NO_BORDER);
                phrasesupervisorNameTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasesupervisorNameTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasesupervisorNameTitlecell.setPaddingBottom(10);
                phrasesupervisorNameTitlecell.setPaddingLeft(6);


                Whole_WarehouseDetails_table.addCell(phrasesupervisorNameTitlecell);

            } catch (Exception e) {
                e.printStackTrace();
            }

 */





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
                            barcodeArrayIntoString = String.join(", ", cartItemDetailsList_String);
                        }
                        else{
                            for(String barcode : cartItemDetailsList_String){
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




                        Phrase phrasQuantityTextTitle = new Phrase(String.valueOf(totalGoats_textView.getText().toString()), valueFont_8);

                        PdfPCell phraseQuantityTextTitlecell = new PdfPCell(phrasQuantityTextTitle);
                        phraseQuantityTextTitlecell.setBorder(Rectangle.NO_BORDER);
                        phraseQuantityTextTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phraseQuantityTextTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phraseQuantityTextTitlecell.setPaddingTop(5);
                        phraseQuantityTextTitlecell.setPaddingLeft(6);
                        phraseQuantityTextTitlecell.setPaddingBottom(25);
                        phraseQuantityTextTitlecell.setBorderWidthRight(01);

                        itemDetailstext_table.addCell(phraseQuantityTextTitlecell);




                        Phrase phrasBatchpriceTextTitle = new Phrase(String.valueOf(batchValue_textView.getText().toString()), valueFont_8);

                        PdfPCell phraseBatchPriceTextTitlecell = new PdfPCell(phrasBatchpriceTextTitle);
                        phraseBatchPriceTextTitlecell.setBorder(Rectangle.NO_BORDER);
                        phraseBatchPriceTextTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phraseBatchPriceTextTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phraseBatchPriceTextTitlecell.setPaddingLeft(6);
                        phraseBatchPriceTextTitlecell.setPaddingTop(5);
                        phraseBatchPriceTextTitlecell.setBorderWidthRight(01);
                        phraseBatchPriceTextTitlecell.setPaddingBottom(25);
                        itemDetailstext_table.addCell(phraseBatchPriceTextTitlecell);


                        Phrase phrasTotalTextTitle = new Phrase(String.valueOf(finalGoatValue_textView.getText().toString()), valueFont_8);

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



                        String text_feedWeight ="";
                        try {
                            text_feedWeight = String.valueOf(feedWeight_editText.getText().toString());
                            text_feedWeight = text_feedWeight.replaceAll("[^\\d.]", "");
                            if (text_feedWeight.equals("")) {
                                text_feedWeight = "0";
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                        Phrase phrasQuantityTextTitle = new Phrase(String.valueOf(text_feedWeight), valueFont_8);

                        PdfPCell phraseQuantityTextTitlecell = new PdfPCell(phrasQuantityTextTitle);
                        phraseQuantityTextTitlecell.setBorder(Rectangle.NO_BORDER);
                        phraseQuantityTextTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phraseQuantityTextTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phraseQuantityTextTitlecell.setPaddingTop(8);
                        phraseQuantityTextTitlecell.setPaddingLeft(6);
                        phraseQuantityTextTitlecell.setPaddingBottom(8);
                        phraseQuantityTextTitlecell.setBorderWidthRight(01);

                        feedDetailstext_table.addCell(phraseQuantityTextTitlecell);


                        String text_feedpriceperkg ="";
                        try {
                            text_feedpriceperkg = String.valueOf(feedPricePerKg_editText.getText().toString());
                            text_feedpriceperkg = text_feedpriceperkg.replaceAll("[^\\d.]", "");
                            if (text_feedpriceperkg.equals("")) {
                                text_feedpriceperkg = "0";
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Phrase phrasBatchpriceTextTitle = new Phrase(twoDecimalConverterWithComma.format(Double.parseDouble(String.valueOf(text_feedpriceperkg))), valueFont_8);

                        PdfPCell phraseBatchPriceTextTitlecell = new PdfPCell(phrasBatchpriceTextTitle);
                        phraseBatchPriceTextTitlecell.setBorder(Rectangle.NO_BORDER);
                        phraseBatchPriceTextTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phraseBatchPriceTextTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phraseBatchPriceTextTitlecell.setPaddingLeft(6);
                        phraseBatchPriceTextTitlecell.setPaddingTop(8);
                        phraseBatchPriceTextTitlecell.setBorderWidthRight(01);
                        phraseBatchPriceTextTitlecell.setPaddingBottom(8);
                        feedDetailstext_table.addCell(phraseBatchPriceTextTitlecell);



                        String text_finalfeedprice ="";double finalfeedprice_double =0;
                        try {
                            text_finalfeedprice = String.valueOf(totalFeedPriceTextview.getText().toString());
                            text_finalfeedprice = text_finalfeedprice.replaceAll("[^\\d.]", "");
                            if (text_finalfeedprice.equals("")) {
                                text_finalfeedprice = "0";
                            }

                            finalfeedprice_double = Double.parseDouble(text_finalfeedprice);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                        Phrase phrasTotalTextTitle = new Phrase(twoDecimalConverterWithComma.format((finalfeedprice_double)), valueFont_8);

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

                    Phrase phrasetotalDetailsTitle = new Phrase((String.valueOf(totalValue_textView.getText().toString())), valueFont_10);

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



            if(final_discountValue>0) {
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

                        Phrase phrasetotalDetailsTitle = new Phrase(String.valueOf(twoDecimalConverterWithComma.format(final_discountValue)), valueFont_10);

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

                    Phrase phrasetotalDetailsTitle = new Phrase(String.valueOf(twoDecimalConverterWithComma.format(final_totalPayment)), valueFont_10);

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

/*
            PdfPTable paymentModeDetails_table = new PdfPTable(new float[] { 35, 42 , 23 });

            try{

                try {


                    Phrase phrasePaymentModeDetailsTitle = new Phrase("Payment Mode ", valueFont_10);

                    PdfPCell phrasePaymentmodeTitlecell = new PdfPCell(phrasePaymentModeDetailsTitle);
                    phrasePaymentmodeTitlecell.setBorder(Rectangle.NO_BORDER);
                    phrasePaymentmodeTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phrasePaymentmodeTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phrasePaymentmodeTitlecell.setPaddingBottom(4);
                    phrasePaymentmodeTitlecell.setBorderWidthRight(1);
                    phrasePaymentmodeTitlecell.setPaddingLeft(6);
                    paymentModeDetails_table.addCell(phrasePaymentmodeTitlecell);

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
                    paymentModeDetails_table.addCell(phrasetotalDetailsTitlecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {

                    Phrase paymentModeDetails_Title = new Phrase(String.valueOf(selectedPaymentMode), valueFont_10);

                    PdfPCell phrasepaymentModecell = new PdfPCell(paymentModeDetails_Title);
                   phrasepaymentModecell.setBorder(Rectangle.NO_BORDER);
                   phrasepaymentModecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                   phrasepaymentModecell.setVerticalAlignment(Element.ALIGN_CENTER);
                   phrasepaymentModecell.setPaddingBottom(10);
                   phrasepaymentModecell.setPaddingLeft(6);

                    paymentModeDetails_table.addCell(phrasepaymentModecell);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    PdfPCell paymentModeDetails_table_Cell = new PdfPCell(paymentModeDetails_table);
                    paymentModeDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                    paymentModeDetails_table_Cell.setBackgroundColor(LIGHT_GRAY);
                    paymentModeDetails_table_Cell.setBorderWidthBottom(1);
                    wholePDFContentWithOut_Outline_table.addCell(paymentModeDetails_table_Cell);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            catch (Exception e){
                e.printStackTrace();
            }


 */



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
                    String notes = "";
                    try{
                        notes = String.valueOf(notes_edittext.getText().toString());
                    }
                    catch (Exception e){
                        e.printStackTrace();

                    }

                    Phrase phrasePaymentModeDetailsTitle = new Phrase(notes, valueFont_10);

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







        }
        catch (Exception e){
            e.printStackTrace();
        }





/*
        try{
          //  PdfPTable thankyounoteDetails_table = new PdfPTable(new float[]{35, 42, 23});

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


        if(selectedPaymentMode.toUpperCase().equals(Constants.CREDIT)) {
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


                PdfPTable creditAmountDetails_table_label = new PdfPTable(new float[]{35, 30, 35});


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

                        Phrase outStandingAmountDetails_Title = new Phrase(String.valueOf(twoDecimalConverterWithComma.format(oldCreditAmountOfUser)), valueFont_10);

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

                        Phrase currenBillValue_Title = new Phrase(String.valueOf(twoDecimalConverterWithComma.format(final_totalPayment)), valueFont_10);

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

                        Phrase outStandingAmountDetails_Title = new Phrase(String.valueOf(twoDecimalConverterWithComma.format(newCreditAmountOfUser)), valueFont_10);

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



        Intent intent = new Intent(CheckOut_activity.this, Home_Screen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();


  ///      resetAlltheValuesAndArrays();



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



    public class RoundRectangle implements PdfPCellEvent {
        public void cellLayout(PdfPCell cell, Rectangle rect,
                               PdfContentByte[] canvas) {
            PdfContentByte cb = canvas[PdfPTable.LINECANVAS];
            cb.setColorStroke(BLACK);
            cb.roundRectangle(
                    rect.getLeft() + 1.5f, rect.getBottom() + 1.5f, rect.getWidth() - 3,
                    rect.getHeight() - 3, 4);
            cb.stroke();
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

                    if (String.valueOf(data1).equalsIgnoreCase("dropdown")) {
                        //Log.e(TAG, "dismissDropdown");
                        //Log.e(Constants.TAG, "createBillDetails in CartItem 0 ");

                        try {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            Objects.requireNonNull(imm).hideSoftInputFromWindow(retailerMobileNo_edittext.getWindowToken(), 0);

                            retailerMobileNo_edittext.clearFocus();

                            retailerMobileNo_edittext.dismissDropDown();

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }



                        try {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            Objects.requireNonNull(imm).hideSoftInputFromWindow(retailerName_edittext.getWindowToken(), 0);

                            retailerName_edittext.clearFocus();

                            retailerName_edittext.dismissDropDown();

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                        try {
                            showAlert_toUpdateCartOrderDetails("Retailer");
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    if (data.equalsIgnoreCase("addNewItem")) {

                    }

                    if (data.equalsIgnoreCase("addBillDetails")) {
                        //   createBillDetails(cart_Item_List);

                    }
                    if (String.valueOf(data).equalsIgnoreCase("dropdown")) {
                        //Log.e(TAG, "dismissDropdown");
                        //Log.e(Constants.TAG, "createBillDetails in CartItem 0 ");

                    }
                }

                return false;
            }
        };
        return new Handler(callback);
    }



    public static void showProgressBar(boolean show) {

        if(show){
            loadingPanel.setVisibility(View.VISIBLE);
            loadingpanelmask.setVisibility(View.VISIBLE);

        }
        else {

            loadingPanel.setVisibility(View.GONE);
            loadingpanelmask.setVisibility(View.GONE);
          

        }


    }



}