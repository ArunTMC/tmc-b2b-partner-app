package com.tmc.tmcb2bpartnerapp.activity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.adapter.Adapter_AutoComplete_RetailerName;
import com.tmc.tmcb2bpartnerapp.adapter.Adapter_BillingScreen_CartRecyclerList;
import com.tmc.tmcb2bpartnerapp.adapter.Adapter_GradeWiseTotal_BillingScreen;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BInvoiceNoManager;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BItemCtgy;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.fragment.AddRetailer_Fragment;
import com.tmc.tmcb2bpartnerapp.fragment.BatchItemDetailsFragment_withoutScanBarcode;
import com.tmc.tmcb2bpartnerapp.fragment.OrderSummary_fragement;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartItemDetaillsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BGoatGradeDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BInvoiceNoManagerInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BItemCtgyInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BRetailerDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.BarcodeScannerInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BItemCtgy;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_POJOClassForFinalSalesHashmap;
import com.tmc.tmcb2bpartnerapp.modal.Modal_Static_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_UpdatedB2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BarcodeScannerScreen;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.ListItemSizeSetter;
import com.tmc.tmcb2bpartnerapp.utils.TMCAlertDialogClass;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;
import static com.itextpdf.text.BaseColor.*;

public class BillingScreen extends BaseActivity {
    public static AutoCompleteTextView retailerName_autoComplete_Edittext;
    Button addRetailer_button,checkOut_Button , addItemInCart_Button ,viewItemInCart_Button ,checkoutFromCart_Button ;
    FrameLayout retailerDetailsFrame;
    Fragment mfragment;
    RecyclerView cartItem_recyclerView;
    static LinearLayout loadingPanel ,paymentMode_spinnerLayout;
    static LinearLayout loadingpanelmask,back_IconLayout;
    public static TextView batchNo_textview,totalItem_CountTextview,totalWeight_textview,totalPrice_textview;
    public static EditText discount_editText;
    public static EditText pricePerKg_editText;
    public static ListView gradewisetotalCount_listview;
    static BarcodeScannerInterface barcodeScannerInterface = null;
    B2BRetailerDetailsInterface callback_retailerDetailsInterface = null;
    GoatEarTagDetailsInterface callback_GoatEarTagDetails = null;
    boolean isB2BItemCtgyTableServiceCalled = false;
    B2BCartOrderDetailsInterface callback_b2bOrderDetails =null ;
    boolean isB2BCartOrderTableServiceCalled = false;
    B2BItemCtgyInterface callback_B2BItemCtgyInterface;
    static  DecimalFormat threeDecimalConverter = new DecimalFormat(Constants.threeDecimalPattern);
    static  DecimalFormat twoDecimalConverter = new DecimalFormat(Constants.twoDecimalPattern);
    Spinner paymentMode_spinner;



    private Handler handler;
    B2BGoatGradeDetailsInterface callback_goatGradeDetailsInterface = null;
    boolean isGoatGradeDetailsServiceCalled = false;

    
    B2BInvoiceNoManager b2BInvoiceNoManager = null;
    B2BInvoiceNoManagerInterface callback_invoiceManagerInterface = null;
    

    boolean isGoatEarTagDetailsTableServiceCalled = false;
    boolean isBarcodeScannerServiceCalled = false;
    boolean  isRetailerDetailsServiceCalled = false ;



    public static boolean isorderSummary_checkoutClicked = false;
    public static  boolean  isRetailerSelected = false ,isRetailerEditedByUser = false ,ispaymentModeSelectedByuser = false ,isAdapter_for_paymentModeSetted =false;
    public static boolean isCartAlreadyCreated = false ,isRetailerUpdated = false ,isPricePerKgUpdated = false , priceperKg_not_edited_byUser = true  ;

    private boolean isTransactionSafe;
    private boolean isTransactionPending;

    public String value_forFragment = "";

    public static Adapter_GradeWiseTotal_BillingScreen adapter_gradeWiseTotal_billingScreen ;
    public static Adapter_AutoComplete_RetailerName adapter_autoComplete_retailerName ;
    public static Adapter_BillingScreen_CartRecyclerList adapter_billingScreen_cartList ;

    public static ArrayList<Modal_GoatEarTagDetails> earTagDetailsArrayList_WholeBatch = new ArrayList<>();
    public static ArrayList<String> earTagDetailsArrayList_String = new ArrayList<>();
    public static ArrayList<Modal_B2BItemCtgy> ctgy_subCtgy_DetailsArrayList = new ArrayList<>();
    public static ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList = new ArrayList<>();
   // public static HashMap<String,Modal_GoatEarTagDetails> earTagDetailsHashMap = new HashMap<>();
    static ArrayList<Modal_B2BGoatGradeDetails> goatGrade_arrayLsit = new ArrayList<>();

    public static HashMap<String,JSONObject> earTagDetails_weightStringHashMap = new HashMap<>();

    public static ArrayList<Modal_B2BGoatGradeDetails> selected_gradeDetailss_arrayList = new ArrayList<>();
    public static HashMap<String, Modal_POJOClassForFinalSalesHashmap> earTagDetails_JSONFinalSalesHashMap = new HashMap<>();


    public static String  oldretailerGSTIN = "", oldpriceperKg ="" ,oldretaileraddress ="" , oldRetailerName = "", oldretailerMobileno ="" , oldRetailerKey ="", odeliveryCenterKey ="", orderid ="" ,invoiceno = "", deliveryCenterKey ="",deliveryCenterName ="",scannedBarcode ="" , batchno ="" , retailerKey = "" , retailername = "" ,pricePerKg ="",
            retailermobileno ="",retailerGSTIN ="",paymentMode ="",oldpaymentMode ="" , updatedpaymentMode =""
    ,retaileraddress =""  ,gradename ="" , gradeprice ="";

    boolean isB2BCartDetailsCalled = false;
    B2BCartItemDetaillsInterface callback_b2BCartItemDetaillsInterface = null;

    private static final int OPENPDF_ACTIVITY_REQUEST_CODE = 2;
    private static int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;
    boolean isPDF_FIle = false;

    static double  weight_double = 0 ,totalWeight_double =0;
    static double pricePerKg_double = 0;
    static double discountAmount_double = 0 , gradeprice_double =0;
    static double totalPrice_double = 0;
    static double totalPrice_doubleWithoutDiscount = 0;
  //  static JSONObject gradeWise_count_weightJSONOBJECT = new JSONObject();
    String[] paymentmode_StringArray = new String[0];

    static Activity BillingScreenActivity = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_screen);
        retailerName_autoComplete_Edittext = findViewById(R.id.receiverName_edittext);
        addRetailer_button = findViewById(R.id.addRetailer_button);
        batchNo_textview = findViewById(R.id.batchNo_textview);
        totalItem_CountTextview =  findViewById(R.id.totalItem_CountTextview);
        totalWeight_textview = findViewById(R.id.totalWeight_textview);
        totalPrice_textview = findViewById(R.id.totalPrice_textview);
        discount_editText = findViewById(R.id.discount_editText);
        pricePerKg_editText =   findViewById(R.id.pricePerKg_editText);
        checkOut_Button  =   findViewById(R.id.checkOut_Button);
        addItemInCart_Button = findViewById(R.id.addItemInCart_Button);
        viewItemInCart_Button = findViewById(R.id.viewItemInCart_Button);
        checkoutFromCart_Button = findViewById(R.id.checkoutFromCart_Button);
        gradewisetotalCount_listview = findViewById(R.id.gradewisetotalCount_listview);
        back_IconLayout  = findViewById(R.id.back_IconLayout);
        retailerDetailsFrame  = findViewById(R.id.retailerDetailsFrame);
        loadingpanelmask =  findViewById(R.id.loadingpanelmask);
        loadingPanel =  findViewById(R.id.loadingPanel);
        cartItem_recyclerView =  findViewById(R.id.cartItem_recyclerView);
        paymentMode_spinner =  findViewById(R.id.paymentMode_spinner);
        paymentMode_spinnerLayout  = findViewById(R.id.paymentMode_spinnerLayout);
        BillingScreenActivity = this;


        Intent intent = getIntent();
        batchno = intent.getStringExtra(String.valueOf("batchno"));
        batchNo_textview .setText(batchno);

        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);

        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");

        new Modal_B2BCartOrderDetails();
        earTagDetails_JSONFinalSalesHashMap = new HashMap<>();
    //    gradeWise_count_weightJSONOBJECT = new JSONObject();
        earTagDetailsArrayList_String.clear();
        retailerDetailsArrayList.clear();
        ctgy_subCtgy_DetailsArrayList.clear();
        earTagDetailsArrayList_WholeBatch.clear();
        goatGrade_arrayLsit .clear();
        //earTagDetailsHashMap.clear();
        earTagDetails_weightStringHashMap.clear();
        earTagDetails_weightStringHashMap.clear();
        earTagDetails_JSONFinalSalesHashMap.clear();
        selected_gradeDetailss_arrayList.clear();
        paymentmode_StringArray = new String[0];
        isorderSummary_checkoutClicked = false;



      try {
          addDataToPaymentModeSpinner();
          setAdapterForCartItem();
          setAdapterForGradewiseTotal();
      }
      catch (Exception e){
          e.printStackTrace();
      }
        showProgressBar(true);


      try {

          if (DatabaseArrayList_PojoClass.goatGradeDetailsArrayList.size() == 0) {
              try {
                  Call_and_Initialize_GoatGradeDetails(Constants.CallGETListMethod);
              } catch (Exception e) {
                  e.printStackTrace();
              }
          } else {
              goatGrade_arrayLsit = DatabaseArrayList_PojoClass.getGoatGradeDetailsArrayList();
              Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallGETMethod, "", false);
          }
      }
      catch (Exception e){
          e.printStackTrace();
      }








       /* Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();
        earTagDetailsHashMap.put("",modal_goatEarTagDetails);
        earTagDetailsArrayList_String.add("");
        setAdapterForCartItem();

        */


        if(DatabaseArrayList_PojoClass.retailerDetailsArrayList.size() == 0){
            try {
                call_and_init_B2BRetailerDetailsService();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            retailerDetailsArrayList = DatabaseArrayList_PojoClass.retailerDetailsArrayList;
            setAdapterForRetailerDetails();
        }

        if(DatabaseArrayList_PojoClass.breedType_arrayList.size() == 0){
            try {
                Initialize_and_ExecuteB2BCtgyItem();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            ctgy_subCtgy_DetailsArrayList = DatabaseArrayList_PojoClass.breedType_arrayList;
        }



        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ispaymentModeSelectedByuser = false;
        paymentMode_spinnerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ispaymentModeSelectedByuser = true;
                paymentMode_spinner.performClick();
            }
        });


        paymentMode_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                updatedpaymentMode = String.valueOf(paymentmode_StringArray[position]);


                    if (ispaymentModeSelectedByuser) {
                        // updatedpaymentMode = String.valueOf(paymentmode_StringArray[position]);
                        showAlert_toUpdateCartOrderDetails(getString(R.string.paymentmode));

                    } else {
                        updatedpaymentMode = paymentMode;
                        if (!isAdapter_for_paymentModeSetted) {
                            ispaymentModeSelectedByuser = true;
                        }
                        else{
                            isAdapter_for_paymentModeSetted = false;
                        }
                    }


            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        addRetailer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                value_forFragment = getString(R.string.billing_Screen_retailer);
                mfragment = new AddRetailer_Fragment();
                try{
                    loadMyFragment();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });









        addItemInCart_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRetailerSelected){

                    if (earTagDetails_JSONFinalSalesHashMap.size() == 0) {
                        Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallGETMethod, "",true);


                    }
                    else{

                        if(Modal_B2BCartOrderDetails.getOrderid().equals("") ){


                            Initialize_And_Process_InvoiceNoManager("GENERATE", false);
                            Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallADDMethod,"", false);
                        }


                        Initialize_and_StartBarcodeScanner(getString(R.string.scannerCalled_to_FetchData) ,"",true);

                    }


                }
                else{
                    AlertDialogClass.showDialog(BillingScreen.this, R.string.Please_Select_Retailer_toAddItem_Instruction);

                }
                /*
                value_forFragment = getString(R.string.billing_Screen_placeOrder);
                mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                try{
                    loadMyFragment();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                 */

            }
        });




        pricePerKg_editText.addTextChangedListener(new TextWatcher() {
            Timer timer = new Timer();
            final long DELAY = 1000; // Milliseconds
            boolean isTextChanged = false;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!isTextChanged) {
                    isTextChanged = true;
                    SpannableString spannableString = new SpannableString(charSequence);
                    oldpriceperKg = String.valueOf(spannableString);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(pricePerKg_editText.isFocused()){

                    priceperKg_not_edited_byUser = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {

                                 BillingScreen.this.runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        isTextChanged = false;
                                        String priceperkg = (editable.toString());
                                        pricePerKg = pricePerKg.replaceAll("[^\\d.]", "");
                                        if(pricePerKg.equals("") || pricePerKg.equals(null)){
                                            pricePerKg = "0";
                                        }
                                        if(!priceperKg_not_edited_byUser) {
                                            if (!priceperkg.equals("") && !priceperkg.equals("0")) {

                                                isPricePerKgUpdated = true;

                                                calculateTotalweight_Quantity_Price();
                                                showAlert_toUpdateCartOrderDetails(getString(R.string.priceperkg));

                                            } else {

                                                AlertDialogClass.showDialog(BillingScreen.this, R.string.Please_Add_PricePerKg_Instruction);

                                            }
                                        }


                                    }
                                });


                            }
                        },
                        DELAY
                );

            }
        });



        pricePerKg_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {


                try {

                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        //do what you want on the press of 'done'

                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        Objects.requireNonNull(imm).hideSoftInputFromWindow(pricePerKg_editText.getWindowToken(), 0);



                        calculateTotalweight_Quantity_Price();


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });


        discount_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {


                try {

                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        //do what you want on the press of 'done'

                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        Objects.requireNonNull(imm).hideSoftInputFromWindow(discount_editText.getWindowToken(), 0);



                        calculateTotalweight_Quantity_Price();


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        checkOut_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   OpenDialogToShowOrderSummary();
                if(isRetailerSelected) {
                        String pricePerKg = pricePerKg_editText.getText().toString();
                        pricePerKg = pricePerKg.replaceAll("[^\\d.]", "");
                        if(pricePerKg.equals("") || pricePerKg.equals(null)){
                            pricePerKg = "0";
                        }

                        boolean isArrayContainsZeroForNewWeightInGrams = doesArrayContainsZeroForNewWeightInGrams();
                        if(!isArrayContainsZeroForNewWeightInGrams) {

                            try {

                                if (earTagDetailsArrayList_String.contains("")) {


                                    if (earTagDetails_weightStringHashMap.containsKey("")) {
                                        earTagDetails_weightStringHashMap.remove("");
                                        earTagDetailsArrayList_String.remove("");
                                        adapter_billingScreen_cartList.notifyDataSetChanged();
                                    }
                                    /*
                                    if (earTagDetailsHashMap.containsKey("")) {
                                        earTagDetailsHashMap.remove("");
                                        earTagDetailsArrayList_String.remove("");
                                        adapter_billingScreen_cartList.notifyDataSetChanged();
                                    }

                                     */
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (earTagDetails_weightStringHashMap.size() > 0) {
                                value_forFragment = getString(R.string.billing_Screen_orderSummary);
                                mfragment = new OrderSummary_fragement();
                                loadMyFragment();
                            } else {
                                Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();
                                earTagDetails_weightStringHashMap.put("", new JSONObject());
                                earTagDetailsArrayList_String.add("");
                                Toast.makeText(BillingScreen.this, "Please add atleast one item in the Cart", Toast.LENGTH_SHORT).show();
                            }


                            /*
                            if (earTagDetailsHashMap.size() > 0) {
                                value_forFragment = getString(R.string.billing_Screen_orderSummary);
                                mfragment = new OrderSummary_fragement();
                                loadMyFragment();
                            } else {
                                Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();
                                earTagDetailsHashMap.put("", modal_goatEarTagDetails);
                                earTagDetailsArrayList_String.add("");
                                Toast.makeText(BillingScreen.this, "Please add atleast one item in the Cart", Toast.LENGTH_SHORT).show();
                            }

                             */
                        }
                        else{
                            Toast.makeText(BillingScreen.this, "New Weight cannot be zero / empty. So Please Add weight and click enter for every item ", Toast.LENGTH_SHORT).show();

                        }

                }
                else{
                    AlertDialogClass.showDialog(BillingScreen.this, R.string.Please_Select_Retailer_Instruction);

                }

            }
        });
        checkoutFromCart_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isRetailerSelected) {

                    String pricePerKg = pricePerKg_editText.getText().toString();
                    pricePerKg = pricePerKg.replaceAll("[^\\d.]", "");
                    if(pricePerKg.equals("") || pricePerKg.equals(null)){
                        pricePerKg = "0";
                    }

                        boolean isArrayContainsZeroForNewWeightInGrams = true;
                                //doesArrayContainsZeroForNewWeightInGrams();
                        if(isArrayContainsZeroForNewWeightInGrams) {

                            try {

                                if (earTagDetailsArrayList_String.contains("")) {

                                    if (earTagDetails_weightStringHashMap.containsKey("")) {
                                        earTagDetails_weightStringHashMap.remove("");
                                        earTagDetailsArrayList_String.remove("");
                                        adapter_billingScreen_cartList.notifyDataSetChanged();
                                    }




                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }



                            if (earTagDetails_weightStringHashMap.size() > 0) {
                                value_forFragment = getString(R.string.billing_Screen_orderSummary);
                                mfragment = new OrderSummary_fragement();
                                loadMyFragment();
                            } else {
                                Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();
                                earTagDetails_weightStringHashMap.put("", new JSONObject());
                                earTagDetailsArrayList_String.add("");
                                Toast.makeText(BillingScreen.this, "Please add atleast one item in the Cart", Toast.LENGTH_SHORT).show();
                            }



                        }
                        else{
                            Toast.makeText(BillingScreen.this, "New Weight cannot be zero / empty. So Please Add weight and click enter for every item ", Toast.LENGTH_SHORT).show();

                        }



                }
                else{
                    AlertDialogClass.showDialog(BillingScreen.this, R.string.Please_Select_Retailer_Instruction);

                }



                //Create_and_SharePdf();



            }
        });
        viewItemInCart_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BillingScreen.this, GoatEarTagItemDetailsList.class);
                i.putExtra("TaskToDo", "ItemInCart");
                i.putExtra("batchno", batchno);
                i.putExtra("orderid", orderid);
                i.putExtra("CalledFrom", getString(R.string.billing_Screen));
                startActivity(i);
            }
        });

    }

    private void addDataToPaymentModeSpinner() {
        paymentMode = getString(R.string.CASH);
        oldpaymentMode  = getString(R.string.CASH);
        updatedpaymentMode = getString(R.string.CASH);
       paymentmode_StringArray = new String[0];

        try{

            paymentmode_StringArray =  getResources().getStringArray(R.array.paymentmode);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        ispaymentModeSelectedByuser = false;
        isAdapter_for_paymentModeSetted = true;
        ArrayAdapter<String> arrayAdapterordertype = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, paymentmode_StringArray);
        arrayAdapterordertype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Objects.requireNonNull(paymentMode_spinner).setAdapter(arrayAdapterordertype);


    }

    private void Intialize_and_ExecuteInB2BCartItemDetails(String callGETListMethod, boolean doesneedtoOpenScanBarcode) {


        showProgressBar(true);

        if (isB2BCartDetailsCalled) {
            //  showProgressBar(false);
            return;
        }
        isB2BCartDetailsCalled = true;
        earTagDetails_weightStringHashMap.clear();
        earTagDetailsArrayList_String.clear();
        selected_gradeDetailss_arrayList.clear();
        callback_b2BCartItemDetaillsInterface = new B2BCartItemDetaillsInterface()
        {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BCartItemDetails> arrayList) {
              //  gradeWise_count_weightJSONOBJECT = new JSONObject();
                earTagDetails_JSONFinalSalesHashMap = new HashMap<>();
                for (int iterator = 0; iterator < arrayList.size(); iterator++) {
                    Modal_B2BCartItemDetails modal_b2BCartDetails = arrayList.get(iterator);
                 /*   Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();
                    modal_goatEarTagDetails.barcodeno = modal_b2BCartDetails.getBarcodeno();
                    modal_goatEarTagDetails.batchno = modal_b2BCartDetails.getBatchno();
                    modal_goatEarTagDetails.status = modal_b2BCartDetails.getStatus();
                    modal_goatEarTagDetails.itemaddeddate = modal_b2BCartDetails.getItemaddeddate();
                    modal_goatEarTagDetails.currentweightingrams = modal_b2BCartDetails.getWeightingrams();
                    modal_goatEarTagDetails.gender = modal_b2BCartDetails.getGender();
                    modal_goatEarTagDetails.breedtype = modal_b2BCartDetails.getBreedtype();
                    modal_goatEarTagDetails.status = Constants.goatEarTagStatus_Reviewed_and_READYFORSALE;
                    modal_goatEarTagDetails.stockedweightingrams = modal_b2BCartDetails.getWeightingrams();
                    modal_goatEarTagDetails.loadedweightingrams = modal_b2BCartDetails.getWeightingrams();
                    modal_goatEarTagDetails.newWeight_forBillingScreen = modal_b2BCartDetails.getWeightingrams();
                    modal_goatEarTagDetails.b2bctgykey = modal_b2BCartDetails.getB2bctgykey();
                    modal_goatEarTagDetails.b2bsubctgykey = modal_b2BCartDetails.getB2bsubctgykey();
                    modal_goatEarTagDetails.orderid_forBillingScreen = modal_b2BCartDetails.getOrderid();
                    earTagDetailsHashMap.put(modal_b2BCartDetails.getBarcodeno(),modal_goatEarTagDetails);
                    earTagDetailsArrayList_String.add(modal_b2BCartDetails.getBarcodeno());


                  */
                    String ctgykey = "", ctgyname = "", subctgykey = "", suctgyname = "";
                    //gradeWise_count_weightJSONOBJECT = new JSONObject();
                    if(BillingScreen.earTagDetailsArrayList_String.contains(modal_b2BCartDetails.getBarcodeno())){

                        calculateGradewiseQuantity_and_Weight(modal_b2BCartDetails);
                        JSONObject jsonObject = new JSONObject();
                        try{
                            jsonObject.put("weight",modal_b2BCartDetails.getWeightingrams());
                            jsonObject.put("gradekey",modal_b2BCartDetails.getGradekey());
                            jsonObject.put("gradeprice",modal_b2BCartDetails.getGradeprice());
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        /*
                        if(BillingScreen.earTagDetailsHashMap.containsKey(modal_b2BCartDetails.getBarcodeno())) {
                            Objects.requireNonNull(BillingScreen.earTagDetailsHashMap.get(modal_b2BCartDetails.getBarcodeno())).setB2bctgykey( ctgykey);
                            Objects.requireNonNull(BillingScreen.earTagDetailsHashMap.get(modal_b2BCartDetails.getBarcodeno())).setB2bsubctgykey( subctgykey);
                            Objects.requireNonNull(BillingScreen.earTagDetailsHashMap.get(modal_b2BCartDetails.getBarcodeno())).setNewWeight_forBillingScreen( modal_b2BCartDetails.getWeightingrams());

                            Objects.requireNonNull(BillingScreen.earTagDetailsHashMap.get(modal_b2BCartDetails.getBarcodeno())).setNewWeight_forBillingScreen( modal_b2BCartDetails.getWeightingrams());
                           // adapter_billingScreen_cartList.notifyDataSetChanged();
                            calculateTotalweight_Quantity_Price();
                        }

                         */
                        if(BillingScreen.earTagDetails_weightStringHashMap.containsKey(modal_b2BCartDetails.getBarcodeno())) {
                            if (SDK_INT >= Build.VERSION_CODES.N) {
                                Objects.requireNonNull(BillingScreen.earTagDetails_weightStringHashMap.replace(modal_b2BCartDetails.getBarcodeno(),jsonObject ));
                            }
                            else{
                                Objects.requireNonNull(BillingScreen.earTagDetails_weightStringHashMap.put(modal_b2BCartDetails.getBarcodeno() , jsonObject));
                            }

                            // adapter_billingScreen_cartList.notifyDataSetChanged();
                            calculateTotalweight_Quantity_Price();
                        }
                        else{
                            Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();

                            modal_goatEarTagDetails.barcodeno = modal_b2BCartDetails.getBarcodeno();
                            modal_goatEarTagDetails.batchno = modal_b2BCartDetails.getBatchno();
                            modal_goatEarTagDetails.status = modal_b2BCartDetails.getStatus();
                            modal_goatEarTagDetails.itemaddeddate = modal_b2BCartDetails.getItemaddeddate();
                            modal_goatEarTagDetails.stockedweightingrams = modal_b2BCartDetails.getOldweightingrams();
                            modal_goatEarTagDetails.selecteditem = Modal_Static_GoatEarTagDetails.getSelecteditem();
                            modal_goatEarTagDetails.gender = modal_b2BCartDetails.getGender();
                            modal_goatEarTagDetails.breedtype = modal_b2BCartDetails.getBreedtype();
                            modal_goatEarTagDetails.loadedweightingrams = modal_b2BCartDetails.getOldweightingrams();
                            modal_goatEarTagDetails.currentweightingrams = modal_b2BCartDetails.getOldweightingrams();
                            modal_goatEarTagDetails.newWeight_forBillingScreen = modal_b2BCartDetails.getWeightingrams();
                            modal_goatEarTagDetails.b2bsubctgykey = modal_b2BCartDetails.getB2bsubctgykey();
                            modal_goatEarTagDetails.b2bctgykey = modal_b2BCartDetails.getB2bctgykey();
                            try {
                                for (int iterator1 = 0; iterator1 < goatGrade_arrayLsit.size(); iterator1++) {
                                    if (String.valueOf(modal_b2BCartDetails.getGradekey()).equals(goatGrade_arrayLsit.get(iterator1).getKey())) {
                                        modal_goatEarTagDetails.gradeprice = goatGrade_arrayLsit.get(iterator1).getPrice();
                                        modal_goatEarTagDetails.gradename  = goatGrade_arrayLsit.get(iterator1).getName();
                                        modal_goatEarTagDetails.gradekey   = goatGrade_arrayLsit.get(iterator1).getKey();

                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            earTagDetails_weightStringHashMap.put(modal_b2BCartDetails.getBarcodeno(), jsonObject);
                          //  earTagDetailsHashMap.put(modal_b2BCartDetails.getBarcodeno(),modal_goatEarTagDetails);
                            //adapter_billingScreen_cartList.notifyDataSetChanged();
                            calculateTotalweight_Quantity_Price();

                        }
                    }
                    else{
                        calculateGradewiseQuantity_and_Weight(modal_b2BCartDetails );
                        JSONObject jsonObject = new JSONObject();
                        try{
                            jsonObject.put("weight",modal_b2BCartDetails.getWeightingrams());
                            jsonObject.put("gradekey",modal_b2BCartDetails.getGradekey());
                            jsonObject.put("gradeprice",modal_b2BCartDetails.getGradeprice());
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                        if(BillingScreen.earTagDetails_weightStringHashMap.containsKey(modal_b2BCartDetails.getBarcodeno())) {
                            if (SDK_INT >= Build.VERSION_CODES.N) {
                                Objects.requireNonNull(BillingScreen.earTagDetails_weightStringHashMap.replace(modal_b2BCartDetails.getBarcodeno(), jsonObject));
                            }
                            else{
                                Objects.requireNonNull(BillingScreen.earTagDetails_weightStringHashMap.put(modal_b2BCartDetails.getBarcodeno() , jsonObject));
                            }                            earTagDetailsArrayList_String.add(modal_b2BCartDetails.getBarcodeno());
                            //  adapter_billingScreen_cartList.notifyDataSetChanged();
                            calculateTotalweight_Quantity_Price();
                        }


                        /*
                        if(BillingScreen.earTagDetailsHashMap.containsKey(modal_b2BCartDetails.getBarcodeno())) {
                            Objects.requireNonNull(BillingScreen.earTagDetailsHashMap.get(modal_b2BCartDetails.getBarcodeno())).setNewWeight_forBillingScreen(modal_b2BCartDetails.getWeightingrams());
                            earTagDetailsArrayList_String.add(modal_b2BCartDetails.getBarcodeno());
                          //  adapter_billingScreen_cartList.notifyDataSetChanged();
                            calculateTotalweight_Quantity_Price();
                        }

                         */
                        else{
                            Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();

                            modal_goatEarTagDetails.barcodeno = modal_b2BCartDetails.getBarcodeno();
                            modal_goatEarTagDetails.batchno = modal_b2BCartDetails.getBatchno();
                            modal_goatEarTagDetails.status = modal_b2BCartDetails.getStatus();
                            modal_goatEarTagDetails.itemaddeddate = modal_b2BCartDetails.getItemaddeddate();
                            modal_goatEarTagDetails.stockedweightingrams = modal_b2BCartDetails.getOldweightingrams();
                            modal_goatEarTagDetails.selecteditem = Modal_Static_GoatEarTagDetails.getSelecteditem();
                            modal_goatEarTagDetails.gender = modal_b2BCartDetails.getGender();
                            modal_goatEarTagDetails.breedtype = modal_b2BCartDetails.getBreedtype();
                            modal_goatEarTagDetails.loadedweightingrams =  String.valueOf(modal_b2BCartDetails.getOldweightingrams());
                            modal_goatEarTagDetails.currentweightingrams = String.valueOf(modal_b2BCartDetails.getOldweightingrams());
                            modal_goatEarTagDetails.newWeight_forBillingScreen = modal_b2BCartDetails.getWeightingrams();
                            modal_goatEarTagDetails.b2bsubctgykey = modal_b2BCartDetails.getB2bsubctgykey();
                            modal_goatEarTagDetails.b2bctgykey = modal_b2BCartDetails.getB2bctgykey();
                            try {
                                for (int iterator1 = 0; iterator1 < goatGrade_arrayLsit.size(); iterator1++) {
                                    if (String.valueOf(modal_b2BCartDetails.getGradekey()).equals(goatGrade_arrayLsit.get(iterator1).getKey())) {
                                        modal_goatEarTagDetails.gradeprice = goatGrade_arrayLsit.get(iterator1).getPrice();
                                        modal_goatEarTagDetails.gradename  = goatGrade_arrayLsit.get(iterator1).getName();
                                        modal_goatEarTagDetails.gradekey   = goatGrade_arrayLsit.get(iterator1).getKey();

                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            earTagDetailsArrayList_String.add(modal_b2BCartDetails.getBarcodeno());
                            earTagDetails_weightStringHashMap.put(modal_b2BCartDetails.getBarcodeno(), jsonObject);

                            //  earTagDetailsHashMap.put(modal_b2BCartDetails.getBarcodeno(),modal_goatEarTagDetails);
                           // adapter_billingScreen_cartList.notifyDataSetChanged();
                            calculateTotalweight_Quantity_Price();

                        }
                    }





                        if(iterator == (arrayList.size() -1)){


                                setAdapterForCartItem();
                                setAdapterForGradewiseTotal();
                    }
                }
                isB2BCartDetailsCalled = false;
            }

            @Override
            public void notifySuccess(String result) {

                if(result.toUpperCase().equals(Constants.emptyResult_volley) && doesneedtoOpenScanBarcode) {
                    //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 2 " + DateParser.getDate_and_time_newFormat());
                    Initialize_and_StartBarcodeScanner(getString(R.string.scannerCalled_to_FetchData) ,"",true);

                }
                showProgressBar(false);
                isB2BCartDetailsCalled = false;
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isB2BCartDetailsCalled = false;
                showProgressBar(false);
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 3 " + DateParser.getDate_and_time_newFormat());

            }

            @Override
            public void notifyProcessingError(Exception error) {
                isB2BCartDetailsCalled = false;showProgressBar(false);
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());
                isB2BCartDetailsCalled = false;
            }


        };

        String getApiToCall = API_Manager.getCartItemDetailsForOrderid + orderid ;
        B2BCartItemDetails asyncTask = new B2BCartItemDetails(callback_b2BCartItemDetaillsInterface,  getApiToCall, callGETListMethod);
        asyncTask.execute();





    }

    public  void setAdapterForGradewiseTotal() {
        try {
            adapter_gradeWiseTotal_billingScreen = new Adapter_GradeWiseTotal_BillingScreen(BillingScreen.this, selected_gradeDetailss_arrayList, earTagDetails_JSONFinalSalesHashMap, BillingScreen.this, "");
            gradewisetotalCount_listview.setAdapter(adapter_gradeWiseTotal_billingScreen);

            ListItemSizeSetter.getListViewSize(gradewisetotalCount_listview);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void removeEntryFromGradewiseQuantity_and_Weight(Modal_B2BCartItemDetails modal_b2BCartDetails , boolean need_to_call_CalculationMethod) {
        int quantity =0;

        /*
        if (gradeWise_count_weightJSONOBJECT.has(modal_b2BCartDetails.getOldgradekey())) {

            try {
                JSONObject jsonObject = gradeWise_count_weightJSONOBJECT.getJSONObject(modal_b2BCartDetails.getOldgradekey());
                String maleQty = "0" , femaleQty ="0" , maleWeight = "0" , femaleWeight ="0" , totalWeight ="0" , totalCount ="0" ,malePrice ="0" , femalePrice ="0" , toalPrice = "";
                double maleQtydouble = 0 , femaleQtydouble = 0 , maleWeightdouble = 0 , femaleWeightdouble = 0 , totalWeightdouble = 0 , totalCountdouble = 0 ,malePricedouble = 0 ,
                 newtoalPricedouble =0,       femalePricedouble = 0 , oldtoalPricedouble = 0,oldWeight_inGramsdouble = 0, oldPrice_inGramsdouble =0;

                String newweightString = "0"; double neweightDouble = 0; double neprice =0;

                String oldweightString = "0"; double oldweightDouble = 0; double oldprice =0;
                oldweightString = modal_b2BCartDetails.getOldweightingrams();
                if(oldweightString.equals("") || oldweightString.toString().toUpperCase().equals("NULL")){
                    oldweightString = "0";

                }
                try{
                    oldweightString = oldweightString.replaceAll("[^\\d.]", "");
                    oldweightDouble = Double.parseDouble(oldweightString);
                }
                catch (Exception e){
                    e.printStackTrace();
                }


                String newgradePriceString = "0";
                double newgradePriceDouble = 0;

                String oldgradePriceString = "0";
                double oldgradePriceDouble = 0;
                oldgradePriceString = modal_b2BCartDetails.getOldgradeprice();
                if (oldgradePriceString.equals("") || oldgradePriceString.toString().toUpperCase().equals("NULL")) {
                    oldgradePriceString = "0";

                }
                try {
                    oldgradePriceString = oldgradePriceString.replaceAll("[^\\d.]", "");
                    oldgradePriceDouble = Double.parseDouble(oldgradePriceString);
                } catch (Exception e) {
                    e.printStackTrace();
                }






                newweightString = modal_b2BCartDetails.getWeightingrams();
                if(newweightString.equals("") || newweightString.toString().toUpperCase().equals("NULL")){
                    newweightString = "0";

                }
                try{
                    newweightString = newweightString.replaceAll("[^\\d.]", "");
                    neweightDouble = Double.parseDouble(newweightString);
                }
                catch (Exception e){
                    e.printStackTrace();
                }




                newgradePriceString = modal_b2BCartDetails.getGradeprice();
                if (newgradePriceString.equals("") || newgradePriceString.toString().toUpperCase().equals("NULL")) {
                    newgradePriceString = "0";

                }
                try {
                    newgradePriceString = newgradePriceString.replaceAll("[^\\d.]", "");
                    newgradePriceDouble = Double.parseDouble(newgradePriceString);
                } catch (Exception e) {
                    e.printStackTrace();
                }





                try {
                    oldtoalPricedouble = oldgradePriceDouble * oldweightDouble;
                    newtoalPricedouble = newgradePriceDouble * neweightDouble;
                } catch (Exception e) {
                    e.printStackTrace();
                }


                double weight = 0,  price = 0, maleweight = 0, femaleweight = 0, maleprice = 0, femaleprice = 0;
                int maleQtyy = 0, femaleQtyy = 0 ;quantity =0;
                if (jsonObject.has("totalprice")) {
                    price = jsonObject.getDouble("totalprice");
                    price = price - oldtoalPricedouble;
                }

                if (jsonObject.has("totalQty")) {
                    quantity = jsonObject.getInt("totalQty");
                    quantity = quantity - 1;
                }

                if (jsonObject.has("totalweight")) {
                    weight = jsonObject.getDouble("totalweight");
                    weight =  weight - oldweightDouble;
                }


                jsonObject.put("totalQty", quantity);
                jsonObject.put("totalweight", weight);
                jsonObject.put("totalprice", price);


                if (modal_b2BCartDetails.getGender().equals("MALE")) {

                    if (jsonObject.has("maleQty")) {
                        maleQtyy = jsonObject.getInt("maleQty");
                        maleQtyy = maleQtyy - 1;
                    }
                    else{
                        maleQtyy =  1;
                    }


                    if (jsonObject.has("maleWeight")) {
                        maleweight = jsonObject.getDouble("maleWeight");
                        maleweight = maleweight - oldweightDouble;
                    }


                    if (jsonObject.has("maleprice")) {
                        maleprice = jsonObject.getDouble("maleprice");
                        maleprice = maleprice - oldtoalPricedouble;
                    }


                    jsonObject.put("maleprice", maleprice);
                    jsonObject.put("maleQty", maleQtyy);
                    jsonObject.put("maleWeight", maleweight);
                } else if (modal_b2BCartDetails.getGender().equals("FEMALE")) {
                    if (jsonObject.has("femaleQty")) {
                        femaleQtyy = jsonObject.getInt("femaleQty");
                        femaleQtyy = femaleQtyy - 1;
                    }
                    else{
                        femaleQtyy =  1;

                    }


                    if (jsonObject.has("femaleWeight")) {
                        femaleweight = jsonObject.getDouble("femaleWeight");
                        femaleweight = femaleweight - oldweightDouble;
                    }

                    if (jsonObject.has("femaleprice")) {
                        femaleprice = jsonObject.getDouble("femaleprice");
                        femaleprice = femaleprice - oldtoalPricedouble;
                    }


                    jsonObject.put("femaleprice", femaleprice);
                    jsonObject.put("femaleQty", femaleQtyy);
                    jsonObject.put("femaleWeight", femaleweight);

                }
                if(gradeWise_count_weightJSONOBJECT.has(modal_b2BCartDetails.getOldgradekey())){
                    gradeWise_count_weightJSONOBJECT.remove(modal_b2BCartDetails.getOldgradekey());
                }
                if(quantity>0){
                    gradeWise_count_weightJSONOBJECT.put(modal_b2BCartDetails.getOldgradekey(), jsonObject);
                }

            }
            catch (JSONException e) {
                e.printStackTrace();
            }


        }


         */
        try {
            String newweightString = "0";
            double neweightDouble = 0;
            double newprice = 0;

            String oldweightString = "0";
            double oldweightDouble = 0;
            double oldprice = 0;
            oldweightString = modal_b2BCartDetails.getOldweightingrams();
            if (oldweightString.equals("") || oldweightString.toString().toUpperCase().equals("NULL")) {
                oldweightString = "0";

            }
            try {
                oldweightString = oldweightString.replaceAll("[^\\d.]", "");
                oldweightDouble = Double.parseDouble(oldweightString);
            } catch (Exception e) {
                if(BillingScreenActivity != null) {
                    Toast.makeText(BillingScreenActivity, "Error from remove item 1: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
                e.printStackTrace();
            }


            String newgradePriceString = "0";
            double newgradePriceDouble = 0;

            String oldgradePriceString = "0";
            double oldgradePriceDouble = 0;

            double oldtotalPricedouble = 0, newtotalPricedouble = 0;

            oldgradePriceString = modal_b2BCartDetails.getOldgradeprice();
            if (oldgradePriceString.equals("") || oldgradePriceString.toString().toUpperCase().equals("NULL")) {
                oldgradePriceString = "0";

            }
            try {
                oldgradePriceString = oldgradePriceString.replaceAll("[^\\d.]", "");
                oldgradePriceDouble = Double.parseDouble(oldgradePriceString);
            } catch (Exception e) {
                if(BillingScreenActivity != null) {
                    Toast.makeText(BillingScreenActivity, "Error from remove item 2: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
                e.printStackTrace();
            }


            newweightString = modal_b2BCartDetails.getWeightingrams();
            if (newweightString.equals("") || newweightString.toString().toUpperCase().equals("NULL")) {
                newweightString = "0";

            }
            try {
                newweightString = newweightString.replaceAll("[^\\d.]", "");
                neweightDouble = Double.parseDouble(newweightString);
            } catch (Exception e) {
                if(BillingScreenActivity != null) {
                    Toast.makeText(BillingScreenActivity, "Error from remove item 3: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
                e.printStackTrace();
            }


            newgradePriceString = modal_b2BCartDetails.getGradeprice();
            if (newgradePriceString.equals("") || newgradePriceString.toString().toUpperCase().equals("NULL")) {
                newgradePriceString = "0";

            }
            try {
                newgradePriceString = newgradePriceString.replaceAll("[^\\d.]", "");
                newgradePriceDouble = Double.parseDouble(newgradePriceString);
            } catch (Exception e) {
                if(BillingScreenActivity != null) {
                    Toast.makeText(BillingScreenActivity, "Error from remove item 4: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
                e.printStackTrace();
            }


            try {
                oldtotalPricedouble = oldgradePriceDouble * oldweightDouble;
                newtotalPricedouble = newgradePriceDouble * neweightDouble;
            } catch (Exception e) {
                if(BillingScreenActivity != null) {
                    Toast.makeText(BillingScreenActivity, "Error from remove item 5: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
                e.printStackTrace();
            }

              try {
                if (earTagDetails_JSONFinalSalesHashMap.containsKey(modal_b2BCartDetails.getOldgradekey())) {

                    Modal_POJOClassForFinalSalesHashmap modal_pojoClassForFinalSalesHashmap = earTagDetails_JSONFinalSalesHashMap.get(modal_b2BCartDetails.getOldgradekey());
                    double weight = 0, price = 0, maleweight = 0, femaleweight = 0, maleprice = 0, femaleprice = 0;
                    int maleCount = 0, femaleCount = 0, totalquantity = 0;


                    weight = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalweight();
                    weight = weight - oldweightDouble;

                    quantity = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalqty();
                    quantity = quantity - 1;

                    price = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalprice();
                    price = price - oldtotalPricedouble;


                    modal_pojoClassForFinalSalesHashmap.setTotalprice(price);
                    modal_pojoClassForFinalSalesHashmap.setTotalqty(quantity);
                    modal_pojoClassForFinalSalesHashmap.setTotalweight(weight);


                    if (modal_b2BCartDetails.getGender().toUpperCase().equals("MALE")) {
                        maleprice = modal_pojoClassForFinalSalesHashmap.getMaleprice();
                        maleprice = maleprice - oldtotalPricedouble;
                        maleCount = modal_pojoClassForFinalSalesHashmap.getMaleqty();
                        maleCount = maleCount - 1;
                        maleweight = modal_pojoClassForFinalSalesHashmap.getTotalmaleweight();
                        maleweight = maleweight - oldweightDouble;


                        modal_pojoClassForFinalSalesHashmap.setTotalmaleweight(maleweight);
                        modal_pojoClassForFinalSalesHashmap.setMaleqty(maleCount);


                        String maleprice_String = twoDecimalConverter.format(maleprice);

                        modal_pojoClassForFinalSalesHashmap.setMaleprice(maleprice);


                    }

                    String femaleprice_String = twoDecimalConverter.format(price);
                    if (modal_b2BCartDetails.getGender().toUpperCase().equals("FEMALE")) {
                        femaleprice = modal_pojoClassForFinalSalesHashmap.getFemaleprice();
                        femaleprice = femaleprice - oldtotalPricedouble;
                        femaleCount = modal_pojoClassForFinalSalesHashmap.getFemaleqty();
                        femaleCount = femaleCount - 1;
                        femaleweight = modal_pojoClassForFinalSalesHashmap.getTotalfemaleweight();
                        femaleweight = femaleweight - oldweightDouble;

                        modal_pojoClassForFinalSalesHashmap.setTotalfemaleweight(femaleweight);
                        modal_pojoClassForFinalSalesHashmap.setFemaleqty(femaleCount);
                        modal_pojoClassForFinalSalesHashmap.setFemaleprice(femaleprice);


                    }


                    try {
                        earTagDetails_JSONFinalSalesHashMap.remove(modal_b2BCartDetails.getOldgradekey());
                        if (quantity > 0) {
                            earTagDetails_JSONFinalSalesHashMap.put(modal_b2BCartDetails.getOldgradekey(), modal_pojoClassForFinalSalesHashmap);
                        } else {
                            try {

                                for (int i = 0; i < selected_gradeDetailss_arrayList.size(); i++) {
                                    if (selected_gradeDetailss_arrayList.get(i).getKey().equals(modal_b2BCartDetails.getOldgradekey())) {
                                        selected_gradeDetailss_arrayList.remove(i);
                                    }
                                }


                            } catch (Exception e) {
                                if (BillingScreenActivity != null) {
                                    Toast.makeText(BillingScreenActivity, "Error from remove item 6: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                                }
                                showProgressBar(false);
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        if (BillingScreenActivity != null) {
                            Toast.makeText(BillingScreenActivity, "Error from remove item 7: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                        }
                        showProgressBar(false);
                        e.printStackTrace();
                    }
                }
            }
              catch (Exception e){
                  if(BillingScreenActivity != null) {
                      Toast.makeText(BillingScreenActivity, "Error from remove item 8: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                  }
                  e.printStackTrace();
              }

            if (need_to_call_CalculationMethod) {

                calculateGradewiseQuantity_and_Weight(modal_b2BCartDetails);
            } else {
                try {
                    if (adapter_gradeWiseTotal_billingScreen != null) {
                        adapter_gradeWiseTotal_billingScreen.notifyDataSetChanged();
                        ListItemSizeSetter.getListViewSize(gradewisetotalCount_listview);

                    }
                    else{
                        if(BillingScreenActivity != null) {
                            Toast.makeText(BillingScreenActivity, "Error from remove item adapter null : ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    showProgressBar(false);

                } catch (Exception e) {
                    if(BillingScreenActivity != null) {
                        Toast.makeText(BillingScreenActivity, "Error from remove item 9: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                    }
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e){
            showProgressBar(false);
        if(BillingScreenActivity != null) {
            Toast.makeText(BillingScreenActivity, "Error from remove item : " + String.valueOf(e), Toast.LENGTH_SHORT).show();
        }
            e.printStackTrace();
        }
    }

    public static void calculateGradewiseQuantity_and_Weight(Modal_B2BCartItemDetails modal_b2BCartDetails )
    {

        try {
            for (int iterator = 0; iterator < goatGrade_arrayLsit.size(); iterator++) {
                Modal_B2BGoatGradeDetails modal_b2BGoatGradeDetails = goatGrade_arrayLsit.get(iterator);

                String maleQty = "0", femaleQty = "0", maleWeight = "0", femaleWeight = "0", totalWeight = "0", malePrice = "0", femalePrice = "0", toalPrice = "";
                double maleWeightdouble = 0, femaleWeightdouble = 0, totalWeightdouble = 0, malePricedouble = 0,
                        femalePricedouble = 0, toalPricedouble = 0, oldWeight_inGramsdouble = 0, oldPrice_inGramsdouble = 0;
                int femaleCount = 0, maleCount = 0, totalCount = 0;
                if (modal_b2BGoatGradeDetails.getKey().equals(modal_b2BCartDetails.getGradekey())) {
                    String weightString = "0";
                    double weightDouble = 0;
                    double price_for_this_entry = 0;
                    weightString = modal_b2BCartDetails.getWeightingrams();

                    try {
                        weightString = weightString.replaceAll("[^\\d.]", "");
                        if (weightString.equals("") || weightString.toString().toUpperCase().equals("NULL")) {
                            weightString = "0";
                            Toast.makeText(BillingScreenActivity, "Error from recalculateGradewise weightString 0: ", Toast.LENGTH_SHORT).show();

                        }
                        weightDouble = Double.parseDouble(weightString);
                    } catch (Exception e) {
                        if(BillingScreenActivity != null) {
                            Toast.makeText(BillingScreenActivity, "Error from recalculateGradewise item 1: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                        }
                        e.printStackTrace();
                    }


                    String gradePriceString = "0";
                    double gradePriceDouble = 0;
                    gradePriceString = modal_b2BGoatGradeDetails.getPrice();
                    try {
                        gradePriceString = gradePriceString.replaceAll("[^\\d.]", "");


                        if (gradePriceString.equals("") || gradePriceString.toString().toUpperCase().equals("NULL")) {
                            gradePriceString = "0";

                        }



                        gradePriceDouble = Double.parseDouble(gradePriceString);
                    } catch (Exception e) {
                        if(BillingScreenActivity != null) {
                            Toast.makeText(BillingScreenActivity, "Error from recalculateGradewise item 2: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                        }
                        e.printStackTrace();
                    }

                    try {
                        toalPricedouble = gradePriceDouble * weightDouble;
                        price_for_this_entry = gradePriceDouble * weightDouble;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (earTagDetails_JSONFinalSalesHashMap.containsKey(modal_b2BCartDetails.gradekey)) {
                            Modal_POJOClassForFinalSalesHashmap modal_pojoClassForFinalSalesHashmap = earTagDetails_JSONFinalSalesHashMap.get(modal_b2BCartDetails.gradekey);
                            double weight = 0, price = 0, maleweight = 0, femaleweight = 0, maleprice = 0, femaleprice = 0;
                            int maleQtyy = 0, femaleQtyy = 0, quantity = 0;


                            weight = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalweight();
                            weight = weight + weightDouble;

                            quantity = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalqty();
                            quantity = quantity + 1;

                            price = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalprice();
                            price = toalPricedouble + price;


                            modal_pojoClassForFinalSalesHashmap.setTotalprice(price);
                            modal_pojoClassForFinalSalesHashmap.setTotalqty(quantity);
                            modal_pojoClassForFinalSalesHashmap.setTotalweight(weight);


                            if (modal_b2BCartDetails.getGender().toUpperCase().equals("MALE")) {
                                maleprice = modal_pojoClassForFinalSalesHashmap.getMaleprice();
                                maleprice = maleprice + toalPricedouble;
                                maleCount = modal_pojoClassForFinalSalesHashmap.getMaleqty();
                                maleCount = maleCount + 1;
                                maleweight = modal_pojoClassForFinalSalesHashmap.getTotalmaleweight();
                                maleweight = maleweight + weightDouble;


                                modal_pojoClassForFinalSalesHashmap.setTotalmaleweight(maleweight);
                                modal_pojoClassForFinalSalesHashmap.setMaleqty(maleCount);
                                modal_pojoClassForFinalSalesHashmap.setMaleprice(maleprice);


                            }
                            if (modal_b2BCartDetails.getGender().toUpperCase().equals("FEMALE")) {
                                femaleprice = modal_pojoClassForFinalSalesHashmap.getFemaleprice();
                                femaleprice = femaleprice + toalPricedouble;
                                femaleCount = modal_pojoClassForFinalSalesHashmap.getFemaleqty();
                                femaleCount = femaleCount + 1;
                                femaleweight = modal_pojoClassForFinalSalesHashmap.getTotalfemaleweight();
                                femaleweight = femaleweight + weightDouble;

                                modal_pojoClassForFinalSalesHashmap.setTotalfemaleweight(femaleweight);
                                modal_pojoClassForFinalSalesHashmap.setFemaleqty(femaleCount);
                                modal_pojoClassForFinalSalesHashmap.setFemaleprice(femaleprice);


                            }
                            earTagDetails_JSONFinalSalesHashMap.put(modal_b2BCartDetails.gradekey, modal_pojoClassForFinalSalesHashmap);


                        } else {

                            Modal_POJOClassForFinalSalesHashmap modal_pojoClassForFinalSalesHashmap = new Modal_POJOClassForFinalSalesHashmap();


                            modal_pojoClassForFinalSalesHashmap.setTotalprice(toalPricedouble);
                            modal_pojoClassForFinalSalesHashmap.setTotalqty(1);
                            modal_pojoClassForFinalSalesHashmap.setTotalweight(weightDouble);


                            if (modal_b2BCartDetails.getGender().toUpperCase().equals("MALE")) {


                                modal_pojoClassForFinalSalesHashmap.setTotalmaleweight(weightDouble);
                                modal_pojoClassForFinalSalesHashmap.setMaleqty(1);
                                modal_pojoClassForFinalSalesHashmap.setMaleprice(toalPricedouble);


                            }
                            if (modal_b2BCartDetails.getGender().toUpperCase().equals("FEMALE")) {

                                modal_pojoClassForFinalSalesHashmap.setTotalfemaleweight(weightDouble);
                                modal_pojoClassForFinalSalesHashmap.setFemaleqty(1);
                                modal_pojoClassForFinalSalesHashmap.setFemaleprice(toalPricedouble);


                            }
                            earTagDetails_JSONFinalSalesHashMap.put(modal_b2BCartDetails.gradekey, modal_pojoClassForFinalSalesHashmap);
                            selected_gradeDetailss_arrayList.add(modal_b2BGoatGradeDetails);


                        }
                    }
                    catch (Exception e){
                        if(BillingScreenActivity != null) {
                            Toast.makeText(BillingScreenActivity, "Error from recalculateGradewise item 3: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                        }
                        e.printStackTrace();
                    }
             /*   if (gradeWise_count_weightJSONOBJECT.has(modal_b2BCartDetails.gradekey)) {

                    try {
                        JSONObject jsonObject = gradeWise_count_weightJSONOBJECT.getJSONObject(modal_b2BCartDetails.gradekey);
                        double weight = 0,  price = 0, maleweight = 0, femaleweight = 0, maleprice = 0, femaleprice = 0;
                                int maleQtyy = 0, femaleQtyy = 0 ,quantity =0;
                        if (jsonObject.has("totalprice")) {
                            price = jsonObject.getDouble("totalprice");
                            toalPricedouble = toalPricedouble + price;
                        }

                        if (jsonObject.has("totalQty")) {
                            quantity = jsonObject.getInt("totalQty");
                            quantity = quantity + 1;
                        }

                        if (jsonObject.has("totalweight")) {
                            weight = jsonObject.getDouble("totalweight");
                            weight = weightDouble + weight;
                        }


                        jsonObject.put("totalQty", quantity);
                        jsonObject.put("totalweight", threeDecimalConverter.format(weight));
                        jsonObject.put("totalprice", twoDecimalConverter.format(toalPricedouble));


                        if (modal_b2BCartDetails.getGender().equals("MALE")) {

                            if (jsonObject.has("maleQty")) {
                                maleQtyy = jsonObject.getInt("maleQty");
                                maleQtyy = maleQtyy + 1;
                            }
                            else{
                                maleQtyy =  1;
                            }


                            if (jsonObject.has("maleWeight")) {
                                maleweight = jsonObject.getDouble("maleWeight");
                                maleweight = maleweight + weightDouble;
                            }
                            else{
                                maleweight =  weightDouble;
                            }

                            if (jsonObject.has("maleprice")) {
                                maleprice = jsonObject.getDouble("maleprice");
                                maleprice = maleprice + price_for_this_entry;
                            }
                            else{
                                maleprice =  price_for_this_entry;

                            }

                            jsonObject.put("maleprice", twoDecimalConverter.format(maleprice));
                            jsonObject.put("maleQty", maleQtyy);
                            jsonObject.put("maleWeight", threeDecimalConverter.format(maleweight));
                        } else if (modal_b2BCartDetails.getGender().equals("FEMALE")) {
                            if (jsonObject.has("femaleQty")) {
                                femaleQtyy = jsonObject.getInt("femaleQty");
                                femaleQtyy = femaleQtyy + 1;
                            }
                            else{
                                femaleQtyy =  1;

                            }


                            if (jsonObject.has("femaleWeight")) {
                                femaleweight = jsonObject.getDouble("femaleWeight");
                                femaleweight = femaleweight + weightDouble;
                            }
                            else{
                                femaleweight = weightDouble;
                            }

                            if (jsonObject.has("femaleprice")) {
                                femaleprice = jsonObject.getDouble("femaleprice");
                                femaleprice = femaleprice + price_for_this_entry;
                            }
                            else{
                                femaleprice = price_for_this_entry;
                            }

                            jsonObject.put("femaleprice", twoDecimalConverter.format(femaleprice));
                            jsonObject.put("femaleQty", femaleQtyy);
                            jsonObject.put("femaleWeight", threeDecimalConverter.format(femaleweight));

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        if (modal_b2BCartDetails.getGender().equals("MALE")) {
                            jsonObject.put("maleQty", 1);
                            jsonObject.put("maleWeight", threeDecimalConverter.format(weightDouble));
                            jsonObject.put("maleprice", twoDecimalConverter.format(toalPricedouble));
                        } else if (modal_b2BCartDetails.getGender().equals("FEMALE")) {
                            jsonObject.put("femaleWeight", threeDecimalConverter.format(weightDouble));
                            jsonObject.put("femaleQty", 1);
                            jsonObject.put("femaleprice", twoDecimalConverter.format(toalPricedouble));
                        }
                        jsonObject.put("totalQty", 1);
                        jsonObject.put("totalweight", threeDecimalConverter.format(weightDouble));
                        jsonObject.put("totalprice", twoDecimalConverter.format(toalPricedouble));

                        gradeWise_count_weightJSONOBJECT.put(modal_b2BCartDetails.gradekey, jsonObject);
                        selected_gradeDetailss_arrayList.add(modal_b2BGoatGradeDetails);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

              */


                }
                try {
                    if (iterator - (goatGrade_arrayLsit.size() - 1) == 0) {
                        if (adapter_gradeWiseTotal_billingScreen != null) {
                            adapter_gradeWiseTotal_billingScreen.notifyDataSetChanged();
                            ListItemSizeSetter.getListViewSize(gradewisetotalCount_listview);

                        }
                        else{
                            if(BillingScreenActivity != null) {
                                Toast.makeText(BillingScreenActivity, "Error from recalculateGradewise adapter null : " + String.valueOf(iterator), Toast.LENGTH_SHORT).show();
                            }
                        }


                    }
                } catch (Exception e) {
                    if(BillingScreenActivity != null) {
                        Toast.makeText(BillingScreenActivity, "Error from recalculateGradewise item 4: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                    }
                    e.printStackTrace();
                }
            }
            showProgressBar(false);
        }
        catch (Exception e){
            showProgressBar(false);
            if(BillingScreenActivity != null) {
                Toast.makeText(BillingScreenActivity, "Error from recalculateGradewise item : " + String.valueOf(e), Toast.LENGTH_SHORT).show();
            }
            e.printStackTrace();
        }
    }


    public void showAlert_toUpdateCartOrderDetails(String updatingvariable) {

       if(isCartAlreadyCreated ) {

           if (updatingvariable.equals(getString(R.string.priceperkg))) {

               if(!priceperKg_not_edited_byUser) {
                   InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                   Objects.requireNonNull(imm).hideSoftInputFromWindow(pricePerKg_editText.getWindowToken(), 0);


                   new TMCAlertDialogClass(this, R.string.app_name, R.string.PleaseConfirmUpdatePricePerKg,
                           R.string.Yes_Text, R.string.No_Text,
                           new TMCAlertDialogClass.AlertListener() {
                               @Override
                               public void onYes() {

                                   Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallUPDATEMethod, getString(R.string.priceperkg), false);

                               }

                               @Override
                               public void onNo() {
                                   pricePerKg_editText.setText(oldpriceperKg);
                                   pricePerKg_editText.clearFocus();
                                   priceperKg_not_edited_byUser = true;

                               }
                           });
               }
           }
           else if (updatingvariable.equals(getString(R.string.paymentmode))) {



               new TMCAlertDialogClass(this, R.string.app_name, R.string.PleaseConfirmPaymentMode,
                       R.string.Yes_Text, R.string.No_Text,
                       new TMCAlertDialogClass.AlertListener() {
                           @Override
                           public void onYes() {
                               oldpaymentMode  = paymentMode;
                               paymentMode = updatedpaymentMode;
                               Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallUPDATEMethod, getString(R.string.paymentmode), false);


                           }

                           @Override
                           public void onNo() {
                             paymentMode  = oldpaymentMode;
                             updatedpaymentMode = oldpaymentMode;
                             ispaymentModeSelectedByuser = false;
                               for(int i=0 ; i<paymentmode_StringArray.length ; i++){
                                   if(paymentMode.toUpperCase().equals(paymentmode_StringArray[i].toUpperCase())){
                                       paymentMode_spinner.setSelection(i);
                                   }
                               }

                           }
                       });

           }



           else if (updatingvariable.equals(getString(R.string.retailername))) {
               try {
                   InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                   Objects.requireNonNull(imm).hideSoftInputFromWindow(pricePerKg_editText.getWindowToken(), 0);


               } catch (Exception e) {
                   e.printStackTrace();
               }


               try {
                   InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                   Objects.requireNonNull(imm).hideSoftInputFromWindow(retailerName_autoComplete_Edittext.getWindowToken(), 0);


               } catch (Exception e) {
                   e.printStackTrace();
               }

               new TMCAlertDialogClass(this, R.string.app_name, R.string.PleaseConfirmUpdateRetailerName,
                       R.string.Yes_Text, R.string.No_Text,
                       new TMCAlertDialogClass.AlertListener() {
                           @Override
                           public void onYes() {


                               Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallUPDATEMethod, getString(R.string.retailername), false);


                           }

                           @Override
                           public void onNo() {
                               retailerName_autoComplete_Edittext.setText(oldRetailerName);
                               isRetailerUpdated = true;
                               retailerKey = oldRetailerKey;
                               retailername = oldRetailerName;
                               retailermobileno = oldretailerMobileno;
                               retaileraddress = oldretaileraddress;
                               retailerGSTIN = oldretailerGSTIN;
                               retailerKey = oldRetailerKey;
                           }
                       });

           }
           else if (updatingvariable.equals(getString(R.string.retailername_priceperkg))) {

               if(!priceperKg_not_edited_byUser) {

                  try {

                      InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                      Objects.requireNonNull(imm).hideSoftInputFromWindow(pricePerKg_editText.getWindowToken(), 0);

                      InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                      Objects.requireNonNull(imm1).hideSoftInputFromWindow(retailerName_autoComplete_Edittext.getWindowToken(), 0);
                  }
                  catch (Exception e){
                      e.printStackTrace();
                  }
                   new TMCAlertDialogClass(this, R.string.app_name, R.string.PleaseConfirmUpdateRetailer_pricePerKg,
                           R.string.Yes_Text, R.string.No_Text,
                           new TMCAlertDialogClass.AlertListener() {
                               @Override
                               public void onYes() {


                                   Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallUPDATEMethod, getString(R.string.retailername_priceperkg), false);


                               }

                               @Override
                               public void onNo() {
                                   retailerName_autoComplete_Edittext.setText(oldRetailerName);
                                   isRetailerUpdated = true;
                                   retailerKey = oldRetailerKey;
                                   retailername = oldRetailerName;
                                   retailermobileno = oldretailerMobileno;
                                   retaileraddress = oldretaileraddress;
                                   retailerGSTIN = oldretailerGSTIN;
                                   retailerKey = oldRetailerKey;
                                   pricePerKg_editText.setText(oldpriceperKg);
                                   priceperKg_not_edited_byUser = true;
                               }
                           });
               }
           }


       }
    }
    private void Call_and_Initialize_GoatGradeDetails(String ApiMethod) {
        if (isGoatGradeDetailsServiceCalled) {
            return;
        }
        isGoatGradeDetailsServiceCalled = true;
        callback_goatGradeDetailsInterface = new B2BGoatGradeDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BGoatGradeDetails> arrayListt) {
                isGoatGradeDetailsServiceCalled = false;
                goatGrade_arrayLsit = arrayListt;
                Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallGETMethod, "", false);
            }

            @Override
            public void notifySuccess(String key) {
                showProgressBar(false);
                isGoatGradeDetailsServiceCalled = false;

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isGoatGradeDetailsServiceCalled = false;
                //    //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 3 " + DateParser.getDate_and_time_newFormat());

            }

            @Override
            public void notifyProcessingError(Exception error) {
                isGoatGradeDetailsServiceCalled = false;
                ////Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());

            }


        };


        if(ApiMethod.equals(Constants.CallGETListMethod)){
            goatGrade_arrayLsit.clear();
            String getApiToCall = API_Manager.getgoatGradeForDeliveryCentreKey +deliveryCenterKey;

            B2BGoatGradeDetails asyncTask = new B2BGoatGradeDetails(callback_goatGradeDetailsInterface,  getApiToCall, Constants.CallGETListMethod);
            asyncTask.execute();



        }



    }


    public void Initialize_and_ExecuteB2BCartOrderDetails(String callMethod, String valuetoUpdate, boolean isneedToGenerateInvoiceNo) {


        showProgressBar(true);
        if (isB2BCartOrderTableServiceCalled) {
           // showProgressBar(false);
            return;
        }
        isB2BCartOrderTableServiceCalled = true;
        callback_b2bOrderDetails = new B2BCartOrderDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BCartOrderDetails> arrayList) {

            }

            @Override
            public void notifySuccess(String result) {
                if (result.equals(Constants.emptyResult_volley)) {
                    showProgressBar(false);
                    isCartAlreadyCreated = false;
                    isB2BCartOrderTableServiceCalled = false;
                    orderid = String.valueOf(System.currentTimeMillis());
                    if (isneedToGenerateInvoiceNo){
                        Initialize_And_Process_InvoiceNoManager("GENERATE",isneedToGenerateInvoiceNo);

                    }
                    else{
                        Initialize_And_Process_InvoiceNoManager("GET", false);
                    }
                   // Initialize_And_Process_InvoiceNoManager("GET", false);
                    Toast.makeText(BillingScreen.this, "There is no Cart Details for this batch", Toast.LENGTH_SHORT).show();


                    } else {
                    //showProgressBar(false);
                    isCartAlreadyCreated = true;
                    priceperKg_not_edited_byUser = true;

                    batchno = Modal_B2BCartOrderDetails.getBatchno();
                    retailerKey = Modal_B2BCartOrderDetails.getRetailerkey();
                    retailermobileno = Modal_B2BCartOrderDetails.getRetailermobileno();
                    retailername = Modal_B2BCartOrderDetails.getRetailername();
                    orderid = Modal_B2BCartOrderDetails.getOrderid();
                    pricePerKg = Modal_B2BCartOrderDetails.getPriceperkg();
                    invoiceno = Modal_B2BCartOrderDetails.getInvoiceno();
                    paymentMode = Modal_B2BCartOrderDetails.getPaymentMode();
                    oldpaymentMode  = Modal_B2BCartOrderDetails.getPaymentMode();
                    getSelectedRetailerDetails(retailerKey);
                    ispaymentModeSelectedByuser = false;
                    for(int i=0 ; i<paymentmode_StringArray.length ; i++){
                        if(paymentMode.toUpperCase().equals(paymentmode_StringArray[i].toUpperCase())){
                            paymentMode_spinner.setSelection(i);
                        }
                    }




                    if(callMethod.equals(Constants.CallGETMethod)) {
                        if(isneedToGenerateInvoiceNo){
                            Intialize_and_ExecuteInB2BCartItemDetails(Constants.CallGETListMethod,true);
                        }
                        else{
                            Intialize_and_ExecuteInB2BCartItemDetails(Constants.CallGETListMethod,false);
                        }


                    }
                    else {
                        showProgressBar(false);

                    }

                    isPricePerKgUpdated = false;
                    isRetailerUpdated = false;
                    isRetailerSelected = true;
                    batchNo_textview.setText(batchno);

                    pricePerKg_editText.clearFocus();
                    pricePerKg_editText.setText(pricePerKg);

                    isB2BCartOrderTableServiceCalled = false;
                }
            }
            @Override
            public void notifyVolleyError(VolleyError error) {
                isCartAlreadyCreated = false;
                Toast.makeText(BillingScreen.this, "There is an volley error while updating CartOrder Details", Toast.LENGTH_SHORT).show();
                showProgressBar(false);
                isB2BCartOrderTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                isCartAlreadyCreated = false;
                Toast.makeText(BillingScreen.this, "There is an Process error while updating CartOrder Details", Toast.LENGTH_SHORT).show();

                showProgressBar(false);
                isB2BCartOrderTableServiceCalled = false;


            }




        };

        if(callMethod.equals(Constants.CallADDMethod)){
            Modal_B2BCartOrderDetails.invoiceno = invoiceno;

            Modal_B2BCartOrderDetails.orderid = orderid;
            Modal_B2BCartOrderDetails.batchno = batchno;
            Modal_B2BCartOrderDetails.deliverycenterkey = deliveryCenterKey;
            Modal_B2BCartOrderDetails.deliverycentername = deliveryCenterName;
            Modal_B2BCartOrderDetails.priceperkg = pricePerKg_editText.getText().toString();
            Modal_B2BCartOrderDetails.retailerkey = retailerKey;
            //Modal_B2BCartOrderDetails.retailermobileno = retailermobileno;
            //Modal_B2BCartOrderDetails.retailername = retailername;
            Modal_B2BCartOrderDetails.itemaddeddate = DateParser.getDate_and_time_newFormat();
            Modal_B2BCartOrderDetails.paymentMode =updatedpaymentMode;

            String getApiToCall = API_Manager.addCartOrderDetails ;
            B2BCartOrderDetails asyncTask = new B2BCartOrderDetails(callback_b2bOrderDetails,  getApiToCall, Constants.CallADDMethod);
            asyncTask.execute();

        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){

            if(valuetoUpdate.equals(getString(R.string.retailername))){
                Modal_UpdatedB2BCartOrderDetails.setRetailername( retailername);
                Modal_UpdatedB2BCartOrderDetails.setRetailerkey( retailerKey);
                Modal_UpdatedB2BCartOrderDetails.setRetailermobileno(retailermobileno);
               // Modal_UpdatedB2BCartOrderDetails.isRetailerNameUpdated = true;
            }
            else if(valuetoUpdate.equals(getString(R.string.priceperkg))){
                Modal_UpdatedB2BCartOrderDetails.setPriceperkg( pricePerKg_editText.getText().toString());
               // Modal_UpdatedB2BCartOrderDetails.ispriceperkgUpdated = true;
            }

            else if(valuetoUpdate.equals(getString(R.string.paymentmode))){
                Modal_UpdatedB2BCartOrderDetails.setPaymentMode( paymentMode);
                // Modal_UpdatedB2BCartOrderDetails.ispriceperkgUpdated = true;
            }
            else if(valuetoUpdate.equals(getString(R.string.retailername_priceperkg))){
                Modal_UpdatedB2BCartOrderDetails.setPriceperkg(pricePerKg_editText.getText().toString());
              //  Modal_UpdatedB2BCartOrderDetails.ispriceperkgUpdated = true;
                Modal_UpdatedB2BCartOrderDetails.setRetailername(retailername);
                Modal_UpdatedB2BCartOrderDetails.setRetailerkey( retailerKey);
                Modal_UpdatedB2BCartOrderDetails.setRetailermobileno(retailermobileno);
             //   Modal_B2BCartOrderDetails.isRetailerNameUpdated = true;
            }
            Modal_UpdatedB2BCartOrderDetails.deliverycenterkey = deliveryCenterKey;
            Modal_UpdatedB2BCartOrderDetails.orderid =orderid;

            String getApiToCall = API_Manager.updateCartOrderDetails ;
            B2BCartOrderDetails asyncTask = new B2BCartOrderDetails(callback_b2bOrderDetails,  getApiToCall, Constants.CallUPDATEMethod);
            asyncTask.execute();
            Modal_B2BCartOrderDetails .paymentMode = paymentMode;
            Modal_B2BCartOrderDetails.priceperkg = pricePerKg_editText.getText().toString();
            Modal_B2BCartOrderDetails.retailerkey = retailerKey;
            Modal_B2BCartOrderDetails.retailermobileno = retailermobileno;
            Modal_B2BCartOrderDetails.retailername = retailername;
        }
        else if(callMethod.equals(Constants.CallGETMethod)){
            //String getApiToCall = API_Manager.getCartOrderDetailsForBatchno+"?batchno="+batchno ;
            String getApiToCall = API_Manager.getCartOrderDetailsForDeliveryCentrekey+"?deliverycentrekey="+deliveryCenterKey ;

            B2BCartOrderDetails asyncTask = new B2BCartOrderDetails(callback_b2bOrderDetails,  getApiToCall, Constants.CallGETMethod);
            asyncTask.execute();

        }
         else if(callMethod.equals(Constants.CallGETListMethod)){

        }

    }

    private void getSelectedRetailerDetails(String retailerKeyy) {

        for(int iterator =0 ; iterator < retailerDetailsArrayList.size(); iterator ++){
            Modal_B2BRetailerDetails  modal_b2BRetailerDetails = retailerDetailsArrayList.get(iterator);

            if(modal_b2BRetailerDetails.getRetailerkey().toUpperCase().equals(retailerKeyy.toUpperCase())){
                retailername = modal_b2BRetailerDetails.getRetailername();
                retailerKey = modal_b2BRetailerDetails.getRetailerkey();
                retaileraddress = modal_b2BRetailerDetails.getAddress();
                retailermobileno = modal_b2BRetailerDetails.getMobileno();
                retailerGSTIN = modal_b2BRetailerDetails.getGstin();
                retailerName_autoComplete_Edittext.setText(retailername);
            }

        }



    }

    private void OpenDialogToShowOrderSummary() {


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {

                    Dialog dialog = new Dialog(BillingScreen.this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_TranslucentDecor);
                //    Dialog dialog = new Dialog(BillingScreen.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                  //  dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    dialog.setContentView(R.layout.order_summary_screen_dialog);
                    showProgressBar(false);
                    dialog.show();


                } catch (WindowManager.BadTokenException e) {
                    showProgressBar(false);

                    e.printStackTrace();
                }
            }
        });

    }

    public void onPause(){
        super.onPause();
        isTransactionSafe=false;

    }

    @Override
    protected void onResume() {
        super.onResume();
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
    private void loadMyFragment() {

        if(isTransactionSafe) {
            isTransactionPending=false;

            if (mfragment != null) {
                Fragment fragment = null;
                retailerDetailsFrame.setVisibility(View.GONE);
                try {
                    fragment = getSupportFragmentManager().findFragmentById(retailerDetailsFrame.getId());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                if(fragment!=null) {
                    try {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.remove(fragment);
                        fragmentTransaction.commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                try {

                    FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction()
                            .addToBackStack(null);
                    if(value_forFragment.equals(getString(R.string.billing_Screen_orderSummary))){
                        transaction2.replace(retailerDetailsFrame.getId(), OrderSummary_fragement.newInstance(getString(R.string.called_from), value_forFragment));

                    }
                    else if(value_forFragment.equals(getString(R.string.billing_Screen_retailer))){
                        transaction2.replace(retailerDetailsFrame.getId(), AddRetailer_Fragment.newInstance(getString(R.string.called_from), value_forFragment));

                    }
                    else if(value_forFragment.equals(getString(R.string.billing_Screen_placeOrder))){
                        transaction2.replace(retailerDetailsFrame.getId(), BatchItemDetailsFragment_withoutScanBarcode.newInstance(getString(R.string.called_from), value_forFragment));

                    }
                        transaction2.remove(mfragment).commit();
                        retailerDetailsFrame.setVisibility(View.VISIBLE);


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
    public  void closeFragment() {

        if(isTransactionSafe) {
            isTransactionPending=false;
            try {
                Fragment fragment = getSupportFragmentManager().findFragmentById(retailerDetailsFrame.getId());
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                fragmentTransaction.remove(fragment);
                fragmentTransaction.commit();

                showProgressBar(false);
                retailerDetailsFrame.setVisibility(View.GONE);
                setAdapterForRetailerDetails();

            }
            catch (Exception e){
                onResume();
                closeFragment();
                e.printStackTrace();
            }


        }
        else {

            isTransactionPending=true;

        }


    }


    private void Initialize_And_Process_InvoiceNoManager(String methodToCall, boolean isneedToGenerateInvoiceNo) {
        showProgressBar(true);
        callback_invoiceManagerInterface = new B2BInvoiceNoManagerInterface() {


            @Override
            public void notifySuccess(String result) {
                invoiceno = result;
                if(isneedToGenerateInvoiceNo) {
                    Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallADDMethod, "", false);
                    Initialize_and_StartBarcodeScanner(getString(R.string.scannerCalled_to_FetchData) ,"",true);

                }
                else{
                    showProgressBar(false);
                }

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
            }

            @Override
            public void notifyProcessingError(Exception error) {
                showProgressBar(false);
            }



        };
        if(methodToCall.equals("GENERATE"))
            B2BInvoiceNoManager.generateNewInvoiceNo(callback_invoiceManagerInterface);
        else if(methodToCall.equals("GET"))
            B2BInvoiceNoManager.getInvoiceNo(callback_invoiceManagerInterface,true);


    }







    public void Create_and_SharePdf() {
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
                        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                        intent.addCategory("android.intent.category.DEFAULT");
                        intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                        startActivityForResult(intent, 2296);
                    } catch (Exception e) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                        startActivityForResult(intent, 2296);
                    }
                }

            } else {


                int writeExternalStoragePermission = ContextCompat.checkSelfPermission(BillingScreen.this, WRITE_EXTERNAL_STORAGE);
                //Log.d("ExportInvoiceActivity", "writeExternalStoragePermission "+writeExternalStoragePermission);
                // If do not grant write external storage permission.
                if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                    // Request user to grant write external storage permission.
                    ActivityCompat.requestPermissions(BillingScreen.this, new String[]{WRITE_EXTERNAL_STORAGE},
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


        }
        catch (Exception e) {
            e.printStackTrace();
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
            String filename = "OrderReceipt for : " +retailername+" - on : "+ DateParser.getDate_and_time_newFormat() + ".pdf";
            final File file = new File(folder, filename);
            file.createNewFile();
            try {
                FileOutputStream fOut = new FileOutputStream(file);
                Document layoutDocument = new Document();
                PdfWriter.getInstance(layoutDocument, fOut);
                layoutDocument.open();
                
              //  addItemRows(layoutDocument);
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

            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setType("application/pdf");
            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
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


        Font font = new Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 39, Font.BOLD);


        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
                Font.BOLD);

        Font subtitleFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,
                Font.BOLD);
        Font subtitleFontsmall = new Font(Font.FontFamily.TIMES_ROMAN, 13,
                Font.NORMAL);



        Font itemNameFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 11,
                Font.NORMAL);
        Font itemNameFont1_bold = new Font(Font.FontFamily.TIMES_ROMAN, 11,
                Font.BOLD);

        Font itemNameFontBold= new Font(Font.FontFamily.TIMES_ROMAN, 10,
                Font.BOLD);
        Font itemNameFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
                Font.NORMAL);

        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 13,
                Font.NORMAL);

        Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                Font.NORMAL);
        PdfPTable wholePDFWithBorder_table = new PdfPTable(1);
        RoundRectangle roundRectange = new RoundRectangle();

        PdfPTable wholePDFWithOutBorder_table = new PdfPTable(1);
        try{
            PdfPTable billInvoiceTitle_table = new PdfPTable(1);


            Phrase phrasebillInvoiceTitle = new Phrase(" Bill Invoice \n\n", titleFont);
            PdfPCell phrasephrasebillInvoiceTitlecell = new PdfPCell(phrasebillInvoiceTitle);
            phrasephrasebillInvoiceTitlecell.setBorder(Rectangle.NO_BORDER);
            phrasephrasebillInvoiceTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
            phrasephrasebillInvoiceTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
            phrasephrasebillInvoiceTitlecell.setPaddingLeft(10);
            phrasephrasebillInvoiceTitlecell.setPaddingBottom(6);
            billInvoiceTitle_table.addCell(phrasephrasebillInvoiceTitlecell);
            layoutDocument.add(billInvoiceTitle_table);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        PdfPTable companyDetails_table = new PdfPTable(1);

        try{


            Phrase phrasecompanyDetailsTitle = new Phrase(" The Meat Chop (Unit of Culinary Triangle Pvt Ltd) ", subtitleFont);

            PdfPCell phrasecompanyDetailsTitlecell = new PdfPCell(phrasecompanyDetailsTitle);
            phrasecompanyDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
            phrasecompanyDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phrasecompanyDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
            phrasecompanyDetailsTitlecell.setPaddingLeft(10);
            phrasecompanyDetailsTitlecell.setPaddingBottom(4);
            companyDetails_table.addCell(phrasecompanyDetailsTitlecell);

        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{

            Phrase phrasecompanyAddressDetails = new Phrase(" Old No 4, New No 50, Kumaraswamy Street, \n Lakshmipuram, Chromepet, Chennai – 44 , \n India. \n GSTIN 33AAJCC0055D1Z9", subtitleFontsmall);

            PdfPCell phrasecompanyAddressDetailscell = new PdfPCell(phrasecompanyAddressDetails);
            phrasecompanyAddressDetailscell.setBorder(Rectangle.NO_BORDER);
            phrasecompanyAddressDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phrasecompanyAddressDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
            phrasecompanyAddressDetailscell.setPaddingLeft(10);
            phrasecompanyAddressDetailscell.setPaddingBottom(4);
            companyDetails_table.addCell(phrasecompanyAddressDetailscell);

        }
        catch (Exception e){
            e.printStackTrace();
        }



        try{
            PdfPCell  companyDetailscell = new PdfPCell(companyDetails_table);
            companyDetailscell.setBorder(Rectangle.NO_BORDER);
            companyDetailscell.setPadding(8);
            companyDetailscell.setBorderWidthBottom(01);
            companyDetailscell.setBorderColor(LIGHT_GRAY);
            wholePDFWithOutBorder_table.addCell(companyDetailscell);

        }
        catch (Exception e){
            e.printStackTrace();
        }

        PdfPTable billDetails_And_retailerDetailstable = new PdfPTable(2);
        PdfPTable billDetails_table = new PdfPTable(2);
        PdfPTable retailerDetailstable = new PdfPTable(1);
        try{




            Phrase phrasebilldetailssupplierDetails = new Phrase(" Bill Details ", subtitleFont);
            PdfPCell phrasebilldetailssupplierDetailscell = new PdfPCell(phrasebilldetailssupplierDetails);
           phrasebilldetailssupplierDetailscell.setBorder(Rectangle.NO_BORDER);
           phrasebilldetailssupplierDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
           phrasebilldetailssupplierDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
           phrasebilldetailssupplierDetailscell.setPaddingBottom(8);

            phrasebilldetailssupplierDetailscell.setBorderColor(LIGHT_GRAY);
            billDetails_table.addCell(phrasebilldetailssupplierDetailscell);

            Phrase phrasebilldetailssupplierLabel = new Phrase("  ", subtitleFontsmall);

            PdfPCell phrasebilldetailssupplierLabelcell = new PdfPCell(phrasebilldetailssupplierLabel);
            phrasebilldetailssupplierLabelcell.setBorder(Rectangle.NO_BORDER);
            phrasebilldetailssupplierLabelcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phrasebilldetailssupplierLabelcell.setVerticalAlignment(Element.ALIGN_LEFT);
            phrasebilldetailssupplierLabelcell.setPaddingLeft(10);
            phrasebilldetailssupplierLabelcell.setPaddingBottom(4);
            phrasebilldetailssupplierDetailscell.setBorderColor(LIGHT_GRAY);
            billDetails_table.addCell(phrasebilldetailssupplierLabelcell);






            Phrase phraseinvoiceLabel = new Phrase(" # ", itemNameFont1);

            PdfPCell phraseinvoiceLabelcell = new PdfPCell(phraseinvoiceLabel);
            phraseinvoiceLabelcell.setBorder(Rectangle.NO_BORDER);
            phraseinvoiceLabelcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseinvoiceLabelcell.setVerticalAlignment(Element.ALIGN_LEFT);
            phraseinvoiceLabelcell.setPaddingBottom(6);
            billDetails_table.addCell(phraseinvoiceLabelcell);


            Phrase phraseinvoiceDetails = new Phrase(": INV-"+invoiceno, itemNameFont1_bold);

            PdfPCell phraseinvoiceDetailscell = new PdfPCell(phraseinvoiceDetails);
            phraseinvoiceDetailscell.setBorder(Rectangle.NO_BORDER);
            phraseinvoiceDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseinvoiceDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
            phraseinvoiceDetailscell.setPaddingLeft(10);
            phraseinvoiceDetailscell.setPaddingBottom(6);
            billDetails_table.addCell(phraseinvoiceDetailscell);



            Phrase phraseorderidLabel = new Phrase(" Orderid ", itemNameFont1);

            PdfPCell phraseorderidLabelcell = new PdfPCell(phraseorderidLabel);
            phraseorderidLabelcell.setBorder(Rectangle.NO_BORDER);
            phraseorderidLabelcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseorderidLabelcell.setVerticalAlignment(Element.ALIGN_LEFT);
            phraseorderidLabelcell.setPaddingBottom(6);
            billDetails_table.addCell(phraseorderidLabelcell);


            Phrase phraseorderidDetails = new Phrase(": "+orderid, itemNameFont1_bold);

            PdfPCell phraseorderidDetailscell = new PdfPCell(phraseorderidDetails);
            phraseorderidDetailscell.setBorder(Rectangle.NO_BORDER);
            phraseorderidDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseorderidDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
            phraseorderidDetailscell.setPaddingLeft(10);
            phraseorderidDetailscell.setPaddingBottom(6);
            billDetails_table.addCell(phraseorderidDetailscell);



            Phrase phraseDateLabel = new Phrase(" Date ", itemNameFont1);

            PdfPCell phraseDateLabelcell = new PdfPCell(phraseDateLabel);
            phraseDateLabelcell.setBorder(Rectangle.NO_BORDER);
            phraseDateLabelcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseDateLabelcell.setVerticalAlignment(Element.ALIGN_LEFT);
            phraseDateLabelcell.setPaddingBottom(6);
            billDetails_table.addCell(phraseDateLabelcell);


            Phrase phrasedateDetails = new Phrase(": "+DateParser.getDate_newFormat(), itemNameFont1_bold);

            PdfPCell phrasedateDetailscell = new PdfPCell(phrasedateDetails);
            phrasedateDetailscell.setBorder(Rectangle.NO_BORDER);
            phrasedateDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phrasedateDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
            phrasedateDetailscell.setPaddingBottom(6);
            phrasedateDetailscell.setPaddingLeft(10);

            billDetails_table.addCell(phrasedateDetailscell);



            Phrase phrasepaymentModeLabel = new Phrase(" Payment Mode ", itemNameFont1);

            PdfPCell phrasepaymentModeLabelcell = new PdfPCell(phrasepaymentModeLabel);
            phrasepaymentModeLabelcell.setBorder(Rectangle.NO_BORDER);
            phrasepaymentModeLabelcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phrasepaymentModeLabelcell.setVerticalAlignment(Element.ALIGN_LEFT);
            phrasepaymentModeLabelcell.setPaddingBottom(6);
            billDetails_table.addCell(phrasepaymentModeLabelcell);


            Phrase phrasepaymentModeDetails = new Phrase(": "+BillingScreen.paymentMode, itemNameFont1_bold);

            PdfPCell phrasepaymentModeDetailscell = new PdfPCell(phrasepaymentModeDetails);
            phrasepaymentModeDetailscell.setBorder(Rectangle.NO_BORDER);
            phrasepaymentModeDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phrasepaymentModeDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
            phrasepaymentModeDetailscell.setPaddingLeft(10);
            phrasepaymentModeDetailscell.setPaddingBottom(6);
            billDetails_table.addCell(phrasepaymentModeDetailscell);





            try{
                PdfPCell  billDetailscell = new PdfPCell(billDetails_table);
                billDetailscell.setBorder(Rectangle.NO_BORDER);
                billDetailscell.setPadding(8);
                billDetailscell.setBorderWidthRight(01);
                billDetailscell.setBorderColor(LIGHT_GRAY);
                billDetails_And_retailerDetailstable.addCell(billDetailscell);

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


        try{

            Phrase phrasebilldetailssupplierDetails = new Phrase("Buyer Details ", subtitleFont);
            PdfPCell phrasebilldetailssupplierDetailscell = new PdfPCell(phrasebilldetailssupplierDetails);
            phrasebilldetailssupplierDetailscell.setBorder(Rectangle.NO_BORDER);
            phrasebilldetailssupplierDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phrasebilldetailssupplierDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
            phrasebilldetailssupplierDetailscell.setPaddingBottom(7);
            phrasebilldetailssupplierDetailscell.setBorderColor(LIGHT_GRAY);
            retailerDetailstable.addCell(phrasebilldetailssupplierDetailscell);


            Phrase phraseretailerNamelabelDetails = new Phrase(retailername, itemNameFont1_bold);
            PdfPCell phraseretailerNameLabelDetailscell = new PdfPCell(phraseretailerNamelabelDetails);
            phraseretailerNameLabelDetailscell.setBorder(Rectangle.NO_BORDER);
            phraseretailerNameLabelDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseretailerNameLabelDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
            phrasebilldetailssupplierDetailscell.setPaddingLeft(6);
            phraseretailerNameLabelDetailscell.setPaddingBottom(2);
            retailerDetailstable.addCell(phraseretailerNameLabelDetailscell);





         /*   Phrase phraseretailerNamelabelDetails = new Phrase( "   Name       ", itemNameFont1_bold);
            PdfPCell phraseretailerNameLabelDetailscell = new PdfPCell(phraseretailerNamelabelDetails);
            phraseretailerNameLabelDetailscell.setBorder(Rectangle.NO_BORDER);
            phraseretailerNameLabelDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseretailerNameLabelDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
            phraseretailerNameLabelDetailscell.setPaddingLeft(2);
            phraseretailerNameLabelDetailscell.setPaddingBottom(4);
            buyerDetails_table.addCell(phraseretailerNameLabelDetailscell);



            Phrase phraseretailerNameDetails = new Phrase( " : "+retailername, itemNameFont1);
            PdfPCell phraseretailerNameDetailscell = new PdfPCell(phraseretailerNameDetails);
            phraseretailerNameDetailscell.setBorder(Rectangle.NO_BORDER);
            phraseretailerNameDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseretailerNameDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
            phraseretailerNameDetailscell.setPaddingLeft(10);
            phraseretailerNameDetailscell.setPaddingBottom(4);
            buyerDetails_table.addCell(phraseretailerNameDetailscell);

*/







            /*Phrase phraseretailerAddresslabelDetails = new Phrase(" Address : ", itemNameFont1);

            PdfPCell phraseretailerAddresslabelDetailscell = new PdfPCell(phraseretailerAddresslabelDetails);
            phraseretailerAddresslabelDetailscell.setBorder(Rectangle.NO_BORDER);
            phraseretailerAddresslabelDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseretailerAddresslabelDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
            phraseretailerAddresslabelDetailscell.setPaddingLeft(10);
            phraseretailerAddresslabelDetailscell.setPaddingBottom(4);
            retailerDetailstable.addCell(phraseretailerAddresslabelDetailscell);


             */
            if(!retaileraddress.equals("") && !String.valueOf(retaileraddress).toUpperCase().equals("NULL")) {

                Phrase phraseretailerAddressDetails = new Phrase(retaileraddress, itemNameFont1);

                PdfPCell phraseretailerAddressDetailscell = new PdfPCell(phraseretailerAddressDetails);
                phraseretailerAddressDetailscell.setBorder(Rectangle.NO_BORDER);
                phraseretailerAddressDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phraseretailerAddressDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasebilldetailssupplierDetailscell.setPaddingLeft(6);
                phraseretailerAddressDetailscell.setPaddingBottom(2);
                retailerDetailstable.addCell(phraseretailerAddressDetailscell);

            }

            retailermobileno = retailermobileno.replace("+91","");

            Phrase phraseretailerNoLabelDetails = new Phrase("Mobile: "+retailermobileno, itemNameFont1);
            PdfPCell phraseretailerNoLabelDetailscell = new PdfPCell(phraseretailerNoLabelDetails);
            phraseretailerNoLabelDetailscell.setBorder(Rectangle.NO_BORDER);
            phraseretailerNoLabelDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseretailerNoLabelDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
            phrasebilldetailssupplierDetailscell.setPaddingLeft(6);

            phraseretailerNoLabelDetailscell.setPaddingBottom(4);
            retailerDetailstable.addCell(phraseretailerNoLabelDetailscell);

            if(!retailerGSTIN.equals("") && !String.valueOf(retailerGSTIN).toUpperCase().equals("NULL"))  {
                Phrase phraseretailergstinLabelDetails = new Phrase("GSTIN : " + retailerGSTIN, itemNameFont1);
                PdfPCell phraseretailergstinLabelDetailscell = new PdfPCell(phraseretailergstinLabelDetails);
                phraseretailergstinLabelDetailscell.setBorder(Rectangle.NO_BORDER);
                phraseretailergstinLabelDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phraseretailergstinLabelDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasebilldetailssupplierDetailscell.setPaddingLeft(6);
                phraseretailergstinLabelDetailscell.setPaddingBottom(10);
                retailerDetailstable.addCell(phraseretailergstinLabelDetailscell);
            }
            /*
            PdfPTable buyerDetails_table = new PdfPTable(2);
            retailermobileno = retailermobileno.replace("+91","");



            Phrase phraseretailerNoLabelDetails = new Phrase(" Mobile No  ", itemNameFont1_bold);
            PdfPCell phraseretailerNoLabelDetailscell = new PdfPCell(phraseretailerNoLabelDetails);
            phraseretailerNoLabelDetailscell.setBorder(Rectangle.NO_BORDER);
            phraseretailerNoLabelDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseretailerNoLabelDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
            phraseretailerNoLabelDetailscell.setPaddingLeft(10);
            phraseretailerNoLabelDetailscell.setPaddingBottom(10);
            buyerDetails_table.addCell(phraseretailerNoLabelDetailscell);





            Phrase phraseretailerNoDetails = new Phrase(" : "+ retailermobileno, itemNameFont1);
            PdfPCell phraseretailerNoDetailscell = new PdfPCell(phraseretailerNoDetails);
            phraseretailerNoDetailscell.setBorder(Rectangle.NO_BORDER);
            phraseretailerNoDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseretailerNoDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
            phraseretailerNoDetailscell.setPaddingBottom(10);
            buyerDetails_table.addCell(phraseretailerNoDetailscell);

            try{
                PdfPCell  retailerDetailstablecell = new PdfPCell(buyerDetails_table);
                retailerDetailstablecell.setBorder(Rectangle.NO_BORDER);
                retailerDetailstable.addCell(retailerDetailstablecell);

            }
            catch (Exception e){
                e.printStackTrace();
            }

             */
            try{
                PdfPCell  retailerDetailscell = new PdfPCell(retailerDetailstable);
                retailerDetailscell.setBorder(Rectangle.NO_BORDER);
                retailerDetailscell.setPadding(8);
                billDetails_And_retailerDetailstable.addCell(retailerDetailscell);

            }
            catch (Exception e){
                e.printStackTrace();
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }



        try{
            PdfPCell  billDetails_And_retailerDetailscell = new PdfPCell(billDetails_And_retailerDetailstable);
           billDetails_And_retailerDetailscell.setBorder(Rectangle.NO_BORDER);
           billDetails_And_retailerDetailscell.setPadding(8);
           billDetails_And_retailerDetailscell.setBorderWidthBottom(01);
            billDetails_And_retailerDetailscell.setPaddingBottom(20);
           billDetails_And_retailerDetailscell.setBorderColor(LIGHT_GRAY);
           wholePDFWithOutBorder_table.addCell(billDetails_And_retailerDetailscell);

        }
        catch (Exception e){
            e.printStackTrace();
        }





        PdfPTable gradewiseItemListWithOutBorder_table = new PdfPTable(8);


        try {
            gradewiseItemListWithOutBorder_table.setWidths(new float[] { 2.5f, 6.5f, 4.8f, 4.5f, 3.1f, 4.5f, 5f, 5f });
        } catch (DocumentException e) {
            Log.i("INIT", "call_and_init_B2BReceiverDetailsService: setWidths  " + String.valueOf(e));

            e.printStackTrace();
        }


        PdfPTable gradewiseItemListWithBorder_table = new PdfPTable(1);


        Phrase phraseSnoDetails = new Phrase(" No ", itemNameFontBold);
        PdfPCell phraseSnoDetailscell = new PdfPCell(phraseSnoDetails);
        phraseSnoDetailscell.setBorder(Rectangle.NO_BORDER);
        phraseSnoDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
        phraseSnoDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
        phraseSnoDetailscell.setBorderWidthRight(01);
        phraseSnoDetailscell.setBorderWidthBottom(01);
        phraseSnoDetailscell.setBorderColor(LIGHT_GRAY);
        phraseSnoDetailscell.setPaddingTop(8);
        phraseSnoDetailscell.setPaddingBottom(8);
        gradewiseItemListWithOutBorder_table.addCell(phraseSnoDetailscell);

        Phrase phraseitemnameDescrDetails = new Phrase(" Item & Description ", itemNameFontBold);
        PdfPCell phraseitemnameDescrDetailscell = new PdfPCell(phraseitemnameDescrDetails);
        phraseitemnameDescrDetailscell.setBorder(Rectangle.NO_BORDER);
        phraseitemnameDescrDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
        phraseitemnameDescrDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
        phraseitemnameDescrDetailscell.setPaddingTop(8);
        phraseitemnameDescrDetailscell.setPaddingBottom(8);
        phraseitemnameDescrDetailscell.setPaddingRight(1);
        phraseitemnameDescrDetailscell.setPaddingLeft(1);
        phraseitemnameDescrDetailscell.setBorderWidthRight(01);
        phraseitemnameDescrDetailscell.setBorderWidthBottom(01);
        phraseitemnameDescrDetailscell.setBorderColor(LIGHT_GRAY);
        gradewiseItemListWithOutBorder_table.addCell(phraseitemnameDescrDetailscell);

        Phrase phraseHSNDescrDetails = new Phrase(" HSN / SAC  ", itemNameFontBold);
        PdfPCell phraseHSNDetailscell = new PdfPCell(phraseHSNDescrDetails);
        phraseHSNDetailscell.setBorder(Rectangle.NO_BORDER);
        phraseHSNDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
        phraseHSNDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
        phraseHSNDetailscell.setPaddingTop(8);
        phraseHSNDetailscell.setPaddingBottom(8);

        phraseHSNDetailscell.setBorderWidthRight(01);
        phraseHSNDetailscell.setBorderWidthBottom(01);
        phraseHSNDetailscell.setBorderColor(LIGHT_GRAY);
        gradewiseItemListWithOutBorder_table.addCell(phraseHSNDetailscell);


        Phrase phraseTypeDetails = new Phrase(" Type ", itemNameFontBold);
        PdfPCell phraseTypeDetailscell = new PdfPCell(phraseTypeDetails);
        phraseTypeDetailscell.setBorder(Rectangle.NO_BORDER);
        phraseTypeDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
        phraseTypeDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
        phraseTypeDetailscell.setBorderWidthRight(01);
        phraseTypeDetailscell.setBorderWidthBottom(01);
        phraseTypeDetailscell.setBorderColor(LIGHT_GRAY);
        phraseTypeDetailscell.setPaddingTop(8);
        phraseTypeDetailscell.setPaddingBottom(8);
        phraseTypeDetailscell.setPaddingRight(5);
        phraseTypeDetailscell.setPaddingLeft(5);
        gradewiseItemListWithOutBorder_table.addCell(phraseTypeDetailscell);


        Phrase phraseQTYDetails = new Phrase(" Qty ", itemNameFontBold);
        PdfPCell phraseQTYcell = new PdfPCell(phraseQTYDetails);
        phraseQTYcell.setBorder(Rectangle.NO_BORDER);
        phraseQTYcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        phraseQTYcell.setVerticalAlignment(Element.ALIGN_LEFT);
        phraseQTYcell.setPaddingTop(8);
        phraseQTYcell.setPaddingBottom(8);
        phraseQTYcell.setPaddingRight(5);
        phraseQTYcell.setPaddingLeft(5);
        phraseQTYcell.setBorderWidthRight(01);
        phraseQTYcell.setBorderWidthBottom(01);
        phraseQTYcell.setBorderColor(LIGHT_GRAY);
        gradewiseItemListWithOutBorder_table.addCell(phraseQTYcell);


        Phrase phraseWeightDetails = new Phrase(" Weight ", itemNameFontBold);
        PdfPCell phraseWeightcell = new PdfPCell(phraseWeightDetails);
        phraseWeightcell.setBorder(Rectangle.NO_BORDER);
        phraseWeightcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        phraseWeightcell.setVerticalAlignment(Element.ALIGN_LEFT);
        phraseWeightcell.setPaddingTop(8);
        phraseWeightcell.setPaddingBottom(8);
        phraseWeightcell.setPaddingRight(5);
        phraseWeightcell.setPaddingLeft(5);
        phraseWeightcell.setBorderWidthRight(01);
        phraseWeightcell.setBorderWidthBottom(01);
        phraseWeightcell.setBorderColor(LIGHT_GRAY);
        gradewiseItemListWithOutBorder_table.addCell(phraseWeightcell);




        Phrase phrasepriceperkgDetails = new Phrase(" Price PerKg ", itemNameFontBold);
        PdfPCell phrasepriceperkgcell = new PdfPCell(phrasepriceperkgDetails);
        phrasepriceperkgcell.setBorder(Rectangle.NO_BORDER);
        phrasepriceperkgcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        phrasepriceperkgcell.setVerticalAlignment(Element.ALIGN_LEFT);
        phrasepriceperkgcell.setBorderWidthRight(01);
        phrasepriceperkgcell.setBorderWidthBottom(01);
        phrasepriceperkgcell.setBorderColor(LIGHT_GRAY);
        phrasepriceperkgcell.setPaddingTop(8);
        phrasepriceperkgcell.setPaddingBottom(8);
        phrasepriceperkgcell.setPaddingRight(1);
        phrasepriceperkgcell.setPaddingLeft(1);
        gradewiseItemListWithOutBorder_table.addCell(phrasepriceperkgcell);


        Phrase phraseamountDetails = new Phrase(" Amount ", itemNameFontBold);
        PdfPCell phraseamountcell = new PdfPCell(phraseamountDetails);
        phraseamountcell.setBorder(Rectangle.NO_BORDER);
        phraseamountcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        phraseamountcell.setVerticalAlignment(Element.ALIGN_LEFT);
        phraseamountcell.setBorderWidthBottom(01);
        phraseamountcell.setBorderColor(LIGHT_GRAY);
        phraseamountcell.setPaddingTop(8);
        phraseamountcell.setPaddingBottom(8);
        phraseamountcell.setPaddingRight(5);
        phraseamountcell.setPaddingLeft(5);
        gradewiseItemListWithOutBorder_table.addCell(phraseamountcell);

        int item_Count =0 ;

        try{
            for(int iterator = 0 ; iterator < selected_gradeDetailss_arrayList.size(); iterator++){
                Modal_B2BGoatGradeDetails modal_b2BGoatGradeDetails = selected_gradeDetailss_arrayList.get(iterator);
                    String totalCount_string ="0" ;
                    int  totalCount_int =0  , itemPrintedCount =0;

                //////FOR Male Item
                //if(gradeWise_count_weightJSONOBJECT.has(modal_b2BGoatGradeDetails.getKey())){
                    if(earTagDetails_JSONFinalSalesHashMap.containsKey(modal_b2BGoatGradeDetails.getKey())){
                        Modal_POJOClassForFinalSalesHashmap modal_pojoClassForFinalSalesHashmap = earTagDetails_JSONFinalSalesHashMap.get(modal_b2BGoatGradeDetails.getKey());
                        int maleQty  = 0 ,totalqty  = 0  ;
                        double maleWeight = 0 , totalweight =0 ,maleprice =0 ,totalprice = 0;

                        try{
                            maleQty = modal_pojoClassForFinalSalesHashmap.getMaleqty();
                            totalqty = modal_pojoClassForFinalSalesHashmap.getTotalqty();

                            maleWeight = modal_pojoClassForFinalSalesHashmap.getTotalmaleweight();
                            totalweight = modal_pojoClassForFinalSalesHashmap.getTotalweight();

                            maleprice = modal_pojoClassForFinalSalesHashmap.getMaleprice();
                            totalprice = modal_pojoClassForFinalSalesHashmap.getTotalprice();

                        }
                        catch (Exception e) {
                         e.printStackTrace();
                        }


                        try {
                     /*   JSONObject jsonObject = gradeWise_count_weightJSONOBJECT.getJSONObject(modal_b2BGoatGradeDetails.getKey());


                        String maleQty = "" , femaleQty ="" , femaleWithBabyQty = "" ;
                        int maleQty_double  = 0 ,femaleQty_double  = 0 , femaleWithBabyQty_double  = 0  ;


                        if(jsonObject.has("maleQty")) {

                            maleQty = String.valueOf(jsonObject.getString("maleQty"));
                            try {
                                maleQty = maleQty.replaceAll("[^\\d.]", "");
                                if (maleQty.equals("") || String.valueOf(maleQty).toUpperCase().equals("NULL")) {
                                    maleQty = "0";
                                }
                                maleQty_double = Integer.parseInt((maleQty));


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                        else{
                          //  male_layout.setVisibility(View.GONE);
                        }
                        if(jsonObject.has("femaleQty")) {
                            femaleQty = String.valueOf(jsonObject.getString("femaleQty"));
                            try {
                                femaleQty = femaleQty.replaceAll("[^\\d.]", "");
                                if (femaleQty.equals("") || String.valueOf(femaleQty).toUpperCase().equals("NULL")) {
                                    femaleQty = "0";
                                }
                                femaleQty_double = Integer.parseInt(femaleQty);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                        else{
                            //female_layout.setVisibility(View.GONE);
                        }

                        if(jsonObject.has("totalQty")){
                            totalCount_string = String.valueOf(jsonObject.getString("totalQty"));
                            try {
                                totalCount_string = totalCount_string.replaceAll("[^\\d.]", "");
                                if (totalCount_string.equals("") || String.valueOf(totalCount_string).toUpperCase().equals("NULL")) {
                                    totalCount_string = "0";
                                }
                                totalCount_int = Integer.parseInt(totalCount_string);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else{

                        }

                      */

                        if(maleQty>0){
                            itemPrintedCount = maleQty + itemPrintedCount;
                            item_Count = item_Count +1 ;
                            Phrase phrasenoDetails = new Phrase( String.valueOf(item_Count), itemNameFontBold );
                            PdfPCell phrasenoDetailscell = new PdfPCell(phrasenoDetails);
                            phrasenoDetailscell.setBorder(Rectangle.NO_BORDER);
                            phrasenoDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phrasenoDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                            phrasenoDetailscell.setBorderWidthRight(01);
                            if(selected_gradeDetailss_arrayList.size()>1){
                                if(iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                    phrasenoDetailscell.setBorderWidthBottom(01);
                                }
                            }
                           else{
                               if( itemPrintedCount != totalCount_int){
                                   phrasenoDetailscell.setBorderWidthBottom(01);
                               }
                            }

                            phrasenoDetailscell.setBorderColor(LIGHT_GRAY);
                            phrasenoDetailscell.setPaddingTop(8);
                            phrasenoDetailscell.setPaddingBottom(8);
                            gradewiseItemListWithOutBorder_table.addCell(phrasenoDetailscell);





                            Phrase phrasemalenameDetails = new Phrase(" Male Goats ", itemNameFont);
                            PdfPCell phrasemalenameDetailscell = new PdfPCell(phrasemalenameDetails);
                            phrasemalenameDetailscell.setBorder(Rectangle.NO_BORDER);
                            phrasemalenameDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phrasemalenameDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                            phrasemalenameDetailscell.setPaddingTop(8);
                            phrasemalenameDetailscell.setPaddingBottom(8);
                            phrasemalenameDetailscell.setPaddingRight(5);
                            phrasemalenameDetailscell.setPaddingLeft(5);
                            phrasemalenameDetailscell.setBorderWidthRight(01);
                            if(selected_gradeDetailss_arrayList.size()>1){
                                if(iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 ||  itemPrintedCount != totalCount_int) {
                                    phrasemalenameDetailscell.setBorderWidthBottom(01);
                                }
                            }
                            else{
                                if( itemPrintedCount != totalCount_int){
                                    phrasemalenameDetailscell.setBorderWidthBottom(01);
                                }
                            }

                            phrasemalenameDetailscell.setBorderColor(LIGHT_GRAY);
                            gradewiseItemListWithOutBorder_table.addCell(phrasemalenameDetailscell);

                            Phrase phrasehsnDetails = new Phrase(" 009992 ", itemNameFont);
                            PdfPCell phrasehsnDetailscell = new PdfPCell(phrasehsnDetails);
                            phrasehsnDetailscell.setBorder(Rectangle.NO_BORDER);
                            phrasehsnDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phrasehsnDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                            phrasehsnDetailscell.setPaddingTop(8);
                            phrasehsnDetailscell.setPaddingBottom(8);
                            phrasehsnDetailscell.setPaddingRight(5);
                            phrasehsnDetailscell.setPaddingLeft(5);
                            phrasehsnDetailscell.setBorderWidthRight(01);
                            if(selected_gradeDetailss_arrayList.size()>1){
                                if(iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 ||  itemPrintedCount != totalCount_int) {
                                    phrasehsnDetailscell.setBorderWidthBottom(01);
                                }
                            }
                            else{
                                if( itemPrintedCount != totalCount_int){
                                    phrasehsnDetailscell.setBorderWidthBottom(01);
                                }
                            }
                            phrasehsnDetailscell.setBorderColor(LIGHT_GRAY);
                            gradewiseItemListWithOutBorder_table.addCell(phrasehsnDetailscell);


                            Phrase phrasegradenameValueDetails = new Phrase(" " + String.valueOf(modal_b2BGoatGradeDetails.getName()) +" " + " ", itemNameFont);
                            PdfPCell phrasegradenameValueDetailscell = new PdfPCell(phrasegradenameValueDetails);
                            phrasegradenameValueDetailscell.setBorder(Rectangle.NO_BORDER);
                            phrasegradenameValueDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phrasegradenameValueDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                            phrasegradenameValueDetailscell.setPaddingTop(8);
                            phrasegradenameValueDetailscell.setPaddingBottom(8);
                            phrasegradenameValueDetailscell.setPaddingRight(5);
                            phrasegradenameValueDetailscell.setPaddingLeft(5);
                            phrasegradenameValueDetailscell.setBorderWidthRight(01);
                            if(selected_gradeDetailss_arrayList.size()>1){
                                if(iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 ||  itemPrintedCount != totalCount_int) {
                                    phrasegradenameValueDetailscell.setBorderWidthBottom(01);
                                }
                            }
                            else{
                                if( itemPrintedCount != totalCount_int){
                                    phrasegradenameValueDetailscell.setBorderWidthBottom(01);
                                }
                            }

                            phrasegradenameValueDetailscell.setBorderColor(LIGHT_GRAY);
                            gradewiseItemListWithOutBorder_table.addCell(phrasegradenameValueDetailscell);


                            Phrase phraseQtyDetails = new Phrase(" " + String.valueOf(maleQty) + " ", itemNameFont);
                            PdfPCell phraseQtyDetailscell = new PdfPCell(phraseQtyDetails);
                            phraseQtyDetailscell.setBorder(Rectangle.NO_BORDER);
                            phraseQtyDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseQtyDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                           phraseQtyDetailscell.setPaddingTop(8);
                           phraseQtyDetailscell.setPaddingBottom(8);
                           phraseQtyDetailscell.setPaddingRight(5);
                           phraseQtyDetailscell.setPaddingLeft(5);
                            phraseQtyDetailscell.setBorderWidthRight(01);
                            if(selected_gradeDetailss_arrayList.size()>1){
                                if(iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 ||  itemPrintedCount != totalCount_int) {
                                    phraseQtyDetailscell.setBorderWidthBottom(01);
                                }
                            }
                            else{
                                if( itemPrintedCount != totalCount_int){
                                    phraseQtyDetailscell.setBorderWidthBottom(01);
                                }
                            }

                            phraseQtyDetailscell.setBorderColor(LIGHT_GRAY);
                            gradewiseItemListWithOutBorder_table.addCell(phraseQtyDetailscell);


                            Phrase phraseWeightValueDetails = new Phrase(" " + String.valueOf(maleWeight) +" Kg" + " ", itemNameFont);
                            PdfPCell phraseWeightValueDetailscell = new PdfPCell(phraseWeightValueDetails);
                            phraseWeightValueDetailscell.setBorder(Rectangle.NO_BORDER);
                            phraseWeightValueDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseWeightValueDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                           phraseWeightValueDetailscell.setPaddingTop(8);
                           phraseWeightValueDetailscell.setPaddingBottom(8);
                           phraseWeightValueDetailscell.setPaddingRight(5);
                           phraseWeightValueDetailscell.setPaddingLeft(5);
                            phraseWeightValueDetailscell.setBorderWidthRight(01);
                            if(selected_gradeDetailss_arrayList.size()>1){
                                if(iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 ||  itemPrintedCount != totalCount_int) {
                                    phraseWeightValueDetailscell.setBorderWidthBottom(01);
                                }
                            }
                            else{
                                if( itemPrintedCount != totalCount_int){
                                    phraseWeightValueDetailscell.setBorderWidthBottom(01);
                                }
                            }

                            phraseWeightValueDetailscell.setBorderColor(LIGHT_GRAY);
                            gradewiseItemListWithOutBorder_table.addCell(phraseWeightValueDetailscell);


                            Phrase phrasepriceperkgValueDetails = new Phrase(" Rs." + String.valueOf(modal_b2BGoatGradeDetails.getPrice()) +" " + " ", itemNameFont);
                            PdfPCell phrasepriceperkgValueDetailscell = new PdfPCell(phrasepriceperkgValueDetails);
                            phrasepriceperkgValueDetailscell.setBorder(Rectangle.NO_BORDER);
                            phrasepriceperkgValueDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phrasepriceperkgValueDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                            phrasepriceperkgValueDetailscell.setPaddingTop(8);
                            phrasepriceperkgValueDetailscell.setPaddingBottom(8);
                            phrasepriceperkgValueDetailscell.setPaddingRight(5);
                            phrasepriceperkgValueDetailscell.setPaddingLeft(5);
                            phrasepriceperkgValueDetailscell.setBorderWidthRight(01);
                            if(selected_gradeDetailss_arrayList.size()>1){
                                if(iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 ||  itemPrintedCount != totalCount_int) {
                                    phrasepriceperkgValueDetailscell.setBorderWidthBottom(01);
                                }
                            }
                            else{
                                if( itemPrintedCount != totalCount_int){
                                    phrasepriceperkgValueDetailscell.setBorderWidthBottom(01);
                                }
                            }

                            phrasepriceperkgValueDetailscell.setBorderColor(LIGHT_GRAY);
                            gradewiseItemListWithOutBorder_table.addCell(phrasepriceperkgValueDetailscell);

                            Phrase phraseAmountValueDetails = new Phrase(" Rs." + String.valueOf(maleprice) +" " + " ", itemNameFont);
                            PdfPCell phraseAmountValueDetailscell = new PdfPCell(phraseAmountValueDetails);
                            phraseAmountValueDetailscell.setBorder(Rectangle.NO_BORDER);
                            phraseAmountValueDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseAmountValueDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                            phraseAmountValueDetailscell.setPaddingTop(8);
                            phraseAmountValueDetailscell.setPaddingBottom(8);
                            phraseAmountValueDetailscell.setPaddingRight(5);
                            phraseAmountValueDetailscell.setPaddingLeft(5);
                            if(selected_gradeDetailss_arrayList.size()>1){
                                if(iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 ||  itemPrintedCount != totalCount_int) {
                                    phraseAmountValueDetailscell.setBorderWidthBottom(01);
                                }
                            }
                            else{
                                if( itemPrintedCount != totalCount_int){
                                    phraseAmountValueDetailscell.setBorderWidthBottom(01);
                                }
                            }

                            phraseAmountValueDetailscell.setBorderColor(LIGHT_GRAY);
                            gradewiseItemListWithOutBorder_table.addCell(phraseAmountValueDetailscell);



                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //// FOR Female Item
               // if(gradeWise_count_weightJSONOBJECT.has(modal_b2BGoatGradeDetails.getKey())){
                if(earTagDetails_JSONFinalSalesHashMap.containsKey(modal_b2BGoatGradeDetails.getKey())){

                    try {
                    /*
                        JSONObject jsonObject = gradeWise_count_weightJSONOBJECT.getJSONObject(modal_b2BGoatGradeDetails.getKey());


                        String maleQty = "" , femaleQty ="" , femaleWithBabyQty = "" ;
                        int maleQty_double  = 0 ,femaleQty_double  = 0 , femaleWithBabyQty_double  = 0  ;

                        if(jsonObject.has("maleQty")) {

                            maleQty = String.valueOf(jsonObject.getString("maleQty"));
                            try {
                                maleQty = maleQty.replaceAll("[^\\d.]", "");
                                if (maleQty.equals("") || String.valueOf(maleQty).toUpperCase().equals("NULL")) {
                                    maleQty = "0";
                                }
                                maleQty_double = Integer.parseInt((maleQty));


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if(jsonObject.has("totalQty")){
                                totalCount_string = String.valueOf(jsonObject.getString("totalQty"));
                                try {
                                    totalCount_string = totalCount_string.replaceAll("[^\\d.]", "");
                                    if (totalCount_string.equals("") || String.valueOf(totalCount_string).toUpperCase().equals("NULL")) {
                                        totalCount_string = "0";
                                    }
                                    totalCount_int = Integer.parseInt(totalCount_string);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else{

                            }


                        }
                        else{
                            //  male_layout.setVisibility(View.GONE);
                        }
                        if(jsonObject.has("femaleQty")) {
                            femaleQty = String.valueOf(jsonObject.getString("femaleQty"));
                            try {
                                femaleQty = femaleQty.replaceAll("[^\\d.]", "");
                                if (femaleQty.equals("") || String.valueOf(femaleQty).toUpperCase().equals("NULL")) {
                                    femaleQty = "0";
                                }
                                femaleQty_double = Integer.parseInt(femaleQty);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                        else{
                            //female_layout.setVisibility(View.GONE);
                        }

                        */



                        Modal_POJOClassForFinalSalesHashmap modal_pojoClassForFinalSalesHashmap = earTagDetails_JSONFinalSalesHashMap.get(modal_b2BGoatGradeDetails.getKey());
                        int femaleQty  = 0 ,totalqty  = 0  ;
                        double femaleWeight = 0 , totalweight =0 ,femaleprice =0 ,totalprice = 0;

                        try{
                            femaleQty = modal_pojoClassForFinalSalesHashmap.getFemaleqty();
                            totalqty = modal_pojoClassForFinalSalesHashmap.getTotalqty();

                            femaleWeight = modal_pojoClassForFinalSalesHashmap.getTotalfemaleweight();
                            totalweight = modal_pojoClassForFinalSalesHashmap.getTotalweight();

                            femaleprice = modal_pojoClassForFinalSalesHashmap.getFemaleprice();
                            totalprice = modal_pojoClassForFinalSalesHashmap.getTotalprice();

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }




                        if(femaleQty>0){
                            itemPrintedCount = femaleQty + itemPrintedCount;
                            item_Count = item_Count +1 ;
                            Phrase phrasenoDetails = new Phrase(String.valueOf(item_Count), itemNameFontBold);
                            PdfPCell phrasenoDetailscell = new PdfPCell(phrasenoDetails);
                            phrasenoDetailscell.setBorder(Rectangle.NO_BORDER);
                            phrasenoDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phrasenoDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                            phrasenoDetailscell.setBorderWidthRight(01);
                            if(selected_gradeDetailss_arrayList.size()>1){
                                if(iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 ||  itemPrintedCount != totalCount_int) {
                                    phrasenoDetailscell.setBorderWidthBottom(01);
                                }
                            }
                            else{
                                if( itemPrintedCount != totalCount_int){
                                    phrasenoDetailscell.setBorderWidthBottom(01);
                                }
                            }
                            phrasenoDetailscell.setBorderColor(LIGHT_GRAY);
                            phrasenoDetailscell.setPaddingTop(8);
                            phrasenoDetailscell.setPaddingBottom(8);
                            gradewiseItemListWithOutBorder_table.addCell(phrasenoDetailscell);




                            Phrase phrasemalenameDetails = new Phrase(" Female Goats ", itemNameFont);
                            PdfPCell phrasemalenameDetailscell = new PdfPCell(phrasemalenameDetails);
                            phrasemalenameDetailscell.setBorder(Rectangle.NO_BORDER);
                            phrasemalenameDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phrasemalenameDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                            phrasemalenameDetailscell.setPaddingTop(8);
                            phrasemalenameDetailscell.setPaddingBottom(8);
                            phrasemalenameDetailscell.setPaddingRight(1);
                            phrasemalenameDetailscell.setPaddingLeft(1);
                            phrasemalenameDetailscell.setBorderWidthRight(01);
                            if(selected_gradeDetailss_arrayList.size()>1){
                                if(iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 ||  itemPrintedCount != totalCount_int) {
                                    phrasemalenameDetailscell.setBorderWidthBottom(01);
                                }
                            }
                            else{
                                if( itemPrintedCount != totalCount_int){
                                    phrasemalenameDetailscell.setBorderWidthBottom(01);
                                }
                            }

                            phrasemalenameDetailscell.setBorderColor(LIGHT_GRAY);
                            gradewiseItemListWithOutBorder_table.addCell(phrasemalenameDetailscell);

                            Phrase phrasehsnDetails = new Phrase(" 009992 ", itemNameFont);
                            PdfPCell phrasehsnDetailscell = new PdfPCell(phrasehsnDetails);
                            phrasehsnDetailscell.setBorder(Rectangle.NO_BORDER);
                            phrasehsnDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phrasehsnDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                            phrasehsnDetailscell.setPaddingTop(8);
                            phrasehsnDetailscell.setPaddingBottom(8);
                            phrasehsnDetailscell.setPaddingRight(5);
                            phrasehsnDetailscell.setPaddingLeft(5);
                            phrasehsnDetailscell.setBorderWidthRight(01);
                            if(selected_gradeDetailss_arrayList.size()>1){
                                if(iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 ||  itemPrintedCount != totalCount_int) {
                                    phrasehsnDetailscell.setBorderWidthBottom(01);
                                }
                            }
                            else{
                                if( itemPrintedCount != totalCount_int){
                                    phrasehsnDetailscell.setBorderWidthBottom(01);
                                }
                            }

                            phrasehsnDetailscell.setBorderColor(LIGHT_GRAY);
                            gradewiseItemListWithOutBorder_table.addCell(phrasehsnDetailscell);


                            Phrase phrasegradenameValueDetails = new Phrase(" " + String.valueOf(modal_b2BGoatGradeDetails.getName()) +" " + " ", itemNameFont);
                            PdfPCell phrasegradenameValueDetailscell = new PdfPCell(phrasegradenameValueDetails);
                            phrasegradenameValueDetailscell.setBorder(Rectangle.NO_BORDER);
                            phrasegradenameValueDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phrasegradenameValueDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                            phrasegradenameValueDetailscell.setPaddingTop(8);
                            phrasegradenameValueDetailscell.setPaddingBottom(8);
                            phrasegradenameValueDetailscell.setPaddingRight(5);
                            phrasegradenameValueDetailscell.setPaddingLeft(5);
                            phrasegradenameValueDetailscell.setBorderWidthRight(01);
                            if(selected_gradeDetailss_arrayList.size()>1){
                                if(iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 ||  itemPrintedCount != totalCount_int) {
                                    phrasegradenameValueDetailscell.setBorderWidthBottom(01);
                                }
                            }
                            else{
                                if( itemPrintedCount != totalCount_int){
                                    phrasegradenameValueDetailscell.setBorderWidthBottom(01);
                                }
                            }

                            phrasegradenameValueDetailscell.setBorderColor(LIGHT_GRAY);
                            gradewiseItemListWithOutBorder_table.addCell(phrasegradenameValueDetailscell);


                            Phrase phraseQtyDetails = new Phrase(" " + String.valueOf(femaleQty) + " ", itemNameFont);
                            PdfPCell phraseQtyDetailscell = new PdfPCell(phraseQtyDetails);
                            phraseQtyDetailscell.setBorder(Rectangle.NO_BORDER);
                            phraseQtyDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseQtyDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                            phraseQtyDetailscell.setPaddingTop(8);
                            phraseQtyDetailscell.setPaddingBottom(8);
                            phraseQtyDetailscell.setPaddingRight(5);
                            phraseQtyDetailscell.setPaddingLeft(5);
                            phraseQtyDetailscell.setBorderWidthRight(01);
                            if(selected_gradeDetailss_arrayList.size()>1){
                                if(iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 ||  itemPrintedCount != totalCount_int) {
                                    phraseQtyDetailscell.setBorderWidthBottom(01);
                                }
                            }
                            else{
                                if( itemPrintedCount != totalCount_int){
                                    phraseQtyDetailscell.setBorderWidthBottom(01);
                                }
                            }

                            phraseQtyDetailscell.setBorderColor(LIGHT_GRAY);
                            gradewiseItemListWithOutBorder_table.addCell(phraseQtyDetailscell);


                            Phrase phraseWeightValueDetails = new Phrase(" " + String.valueOf(femaleWeight) +" Kg" + " ", itemNameFont);
                            PdfPCell phraseWeightValueDetailscell = new PdfPCell(phraseWeightValueDetails);
                            phraseWeightValueDetailscell.setBorder(Rectangle.NO_BORDER);
                            phraseWeightValueDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseWeightValueDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                            phraseWeightValueDetailscell.setPaddingTop(8);
                            phraseWeightValueDetailscell.setPaddingBottom(8);
                            phraseWeightValueDetailscell.setPaddingRight(5);
                            phraseWeightValueDetailscell.setPaddingLeft(5);
                            phraseWeightValueDetailscell.setBorderWidthRight(01);
                            if(selected_gradeDetailss_arrayList.size()>1){
                                if(iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 ||  itemPrintedCount != totalCount_int) {
                                    phraseWeightValueDetailscell.setBorderWidthBottom(01);
                                }
                            }
                            else{
                                if( itemPrintedCount != totalCount_int){
                                    phraseWeightValueDetailscell.setBorderWidthBottom(01);
                                }
                            }

                            phraseWeightValueDetailscell.setBorderColor(LIGHT_GRAY);
                            gradewiseItemListWithOutBorder_table.addCell(phraseWeightValueDetailscell);


                            Phrase phrasepriceperkgValueDetails = new Phrase(" Rs." + String.valueOf(modal_b2BGoatGradeDetails.getPrice()) +" " + " ", itemNameFont);
                            PdfPCell phrasepriceperkgValueDetailscell = new PdfPCell(phrasepriceperkgValueDetails);
                            phrasepriceperkgValueDetailscell.setBorder(Rectangle.NO_BORDER);
                            phrasepriceperkgValueDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phrasepriceperkgValueDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                            phrasepriceperkgValueDetailscell.setPaddingTop(8);
                            phrasepriceperkgValueDetailscell.setPaddingBottom(8);
                            phrasepriceperkgValueDetailscell.setPaddingRight(5);
                            phrasepriceperkgValueDetailscell.setPaddingLeft(5);
                            phrasepriceperkgValueDetailscell.setBorderWidthRight(01);
                            if(selected_gradeDetailss_arrayList.size()>1){
                                if(iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 ||  itemPrintedCount != totalCount_int) {
                                    phrasepriceperkgValueDetailscell.setBorderWidthBottom(01);
                                }
                            }
                            else{
                                if( itemPrintedCount != totalCount_int){
                                    phrasepriceperkgValueDetailscell.setBorderWidthBottom(01);
                                }
                            }


                            phrasepriceperkgValueDetailscell.setBorderColor(LIGHT_GRAY);
                            gradewiseItemListWithOutBorder_table.addCell(phrasepriceperkgValueDetailscell);

                            Phrase phraseAmountValueDetails = new Phrase(" Rs." + String.valueOf(femaleprice) +" " + " ", itemNameFont);
                            PdfPCell phraseAmountValueDetailscell = new PdfPCell(phraseAmountValueDetails);
                            phraseAmountValueDetailscell.setBorder(Rectangle.NO_BORDER);
                            phraseAmountValueDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseAmountValueDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                            phraseAmountValueDetailscell.setPaddingTop(8);
                            phraseAmountValueDetailscell.setPaddingBottom(8);
                            phraseAmountValueDetailscell.setPaddingRight(5);
                            phraseAmountValueDetailscell.setPaddingLeft(5);
                            if(selected_gradeDetailss_arrayList.size()>1){
                                if(iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 ||  itemPrintedCount != totalCount_int) {
                                    phraseAmountValueDetailscell.setBorderWidthBottom(01);
                                }
                            }
                            else{
                                if( itemPrintedCount != totalCount_int){
                                    phraseAmountValueDetailscell.setBorderWidthBottom(01);
                                }
                            }

                            phraseAmountValueDetailscell.setBorderColor(LIGHT_GRAY);
                            gradewiseItemListWithOutBorder_table.addCell(phraseAmountValueDetailscell);







                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                if(iterator - (selected_gradeDetailss_arrayList.size() - 1) == 0){

                    try{
                        PdfPCell  gradewisewithbordercall = new PdfPCell(gradewiseItemListWithOutBorder_table);
                        gradewisewithbordercall.setBorder(Rectangle.NO_BORDER);
                        gradewisewithbordercall.setCellEvent(roundRectange);
                        gradewiseItemListWithBorder_table.addCell(gradewisewithbordercall);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    try{
                        PdfPCell  gradewiseItemListWithBorder_tablecell = new PdfPCell(gradewiseItemListWithBorder_table);
                        gradewiseItemListWithBorder_tablecell.setBorder(Rectangle.NO_BORDER);
                        gradewiseItemListWithBorder_tablecell.setPadding(8);
                        gradewiseItemListWithBorder_tablecell.setBorderColor(LIGHT_GRAY);
                        wholePDFWithOutBorder_table.addCell(gradewiseItemListWithBorder_tablecell);

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }





        PdfPTable finalSalesBreakup_table = new PdfPTable(1);

        Phrase phraseSubtotalDetails = new Phrase(" Sub Total :            Rs. "+ String.valueOf(twoDecimalConverter.format(totalPrice_doubleWithoutDiscount)), subtitleFont);
        PdfPCell phrasesubtotalDetailscell = new PdfPCell(phraseSubtotalDetails);
        phrasesubtotalDetailscell.setBorder(Rectangle.NO_BORDER);
        phrasesubtotalDetailscell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        phrasesubtotalDetailscell.setVerticalAlignment(Element.ALIGN_RIGHT);
        phrasesubtotalDetailscell.setPaddingLeft(10);
        phrasesubtotalDetailscell.setPaddingTop(20);
        phrasesubtotalDetailscell.setPaddingBottom(6);
        finalSalesBreakup_table.addCell(phrasesubtotalDetailscell);
       if(discountAmount_double>0) {
           Phrase phrasediscountDetails = new Phrase(" Discount   :               Rs. " + String.valueOf(twoDecimalConverter.format(discountAmount_double)), subtitleFont);
           PdfPCell phrasediscountDetailscell = new PdfPCell(phrasediscountDetails);
           phrasediscountDetailscell.setBorder(Rectangle.NO_BORDER);
           phrasediscountDetailscell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           phrasediscountDetailscell.setVerticalAlignment(Element.ALIGN_RIGHT);
           phrasediscountDetailscell.setPaddingLeft(10);
           phrasediscountDetailscell.setPaddingBottom(6);
           finalSalesBreakup_table.addCell(phrasediscountDetailscell);
       }


        Phrase phraseCgstDetails = new Phrase(" CGST ( 0 % ) :                  0.00" , subtitleFont);
        PdfPCell phraseCgstDetailscell = new PdfPCell(phraseCgstDetails);
        phraseCgstDetailscell.setBorder(Rectangle.NO_BORDER);
        phraseCgstDetailscell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        phraseCgstDetailscell.setVerticalAlignment(Element.ALIGN_RIGHT);
        phraseCgstDetailscell.setPaddingLeft(10);
        phraseCgstDetailscell.setPaddingBottom(6);
        finalSalesBreakup_table.addCell(phraseCgstDetailscell);




        Phrase phraseSgstDetails = new Phrase(" SGST ( 0 % ) :                  0.00 " , subtitleFont);
        PdfPCell phraseSgstDetailscell = new PdfPCell(phraseSgstDetails);
        phraseSgstDetailscell.setBorder(Rectangle.NO_BORDER);
        phraseSgstDetailscell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        phraseSgstDetailscell.setVerticalAlignment(Element.ALIGN_RIGHT);
        phraseSgstDetailscell.setPaddingLeft(10);
        phraseSgstDetailscell.setPaddingBottom(6);
        finalSalesBreakup_table.addCell(phraseSgstDetailscell);


        Phrase phrasetotalDetails = new Phrase(" Total :          Rs. "+ String.valueOf(twoDecimalConverter.format(totalPrice_double)), subtitleFont);
        PdfPCell phrasetotalDetailscell = new PdfPCell(phrasetotalDetails);
        phrasetotalDetailscell.setBorder(Rectangle.NO_BORDER);
        phrasetotalDetailscell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        phrasetotalDetailscell.setVerticalAlignment(Element.ALIGN_RIGHT);
        phrasetotalDetailscell.setPaddingLeft(10);
        phrasetotalDetailscell.setPaddingBottom(15);
        finalSalesBreakup_table.addCell(phrasetotalDetailscell);

        Phrase phrasefinalnotesDetails = new Phrase(" Thanks For Your Business ", subtitleFontsmall);
        PdfPCell phrasefinalnotesDetailscell = new PdfPCell(phrasefinalnotesDetails);
        phrasefinalnotesDetailscell.setBorder(Rectangle.NO_BORDER);
        phrasefinalnotesDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
        phrasefinalnotesDetailscell.setVerticalAlignment(Element.ALIGN_CENTER);
        phrasefinalnotesDetailscell.setPaddingLeft(10);
        phrasefinalnotesDetailscell.setPaddingTop(20);
        phrasefinalnotesDetailscell.setPaddingBottom(40);
        finalSalesBreakup_table.addCell(phrasefinalnotesDetailscell);


        try{
            PdfPCell  finalSalesBreakup_tablecell = new PdfPCell(finalSalesBreakup_table);
            finalSalesBreakup_tablecell.setBorder(Rectangle.NO_BORDER);
            finalSalesBreakup_tablecell.setPadding(8);
            finalSalesBreakup_tablecell.setBorderColor(LIGHT_GRAY);
            wholePDFWithOutBorder_table.addCell(finalSalesBreakup_tablecell);

        }
        catch (Exception e){
            e.printStackTrace();
        }



        //FINAL
        try{
            PdfPCell  wholePDFWithOutBordercell = new PdfPCell(wholePDFWithOutBorder_table);
            wholePDFWithOutBordercell.setCellEvent(roundRectange);
            wholePDFWithOutBordercell.setBorder(Rectangle.NO_BORDER);
            wholePDFWithOutBordercell.setPadding(8);
            wholePDFWithBorder_table.addCell(wholePDFWithOutBordercell);
            wholePDFWithBorder_table.setWidthPercentage(100);


            layoutDocument.add(wholePDFWithBorder_table);

        }
        catch (Exception e){
            e.printStackTrace();
        }




    }

    public void neutralizeArray_and_OtherValues() {
        isB2BItemCtgyTableServiceCalled = false;
        isGoatEarTagDetailsTableServiceCalled = false;
        isRetailerDetailsServiceCalled = false;
        isGoatGradeDetailsServiceCalled = false;
        isBarcodeScannerServiceCalled = false;
        isRetailerSelected = false ;
        ispaymentModeSelectedByuser = false;
        isB2BCartDetailsCalled = false;
        isCartAlreadyCreated = false;
        isPDF_FIle = false;
        earTagDetailsArrayList_WholeBatch.clear();
        earTagDetailsArrayList_String.clear();
        selected_gradeDetailss_arrayList.clear();
       // earTagDetailsHashMap.clear();
        earTagDetails_weightStringHashMap.clear();
        totalWeight_double =0 ; weight_double = 0 ; pricePerKg_double = 0 ; discountAmount_double = 0 ; totalPrice_double = 0 ; totalPrice_doubleWithoutDiscount =0 ; gradeprice_double =0;
        orderid ="";oldpriceperKg ="";invoiceno = "";pricePerKg ="";paymentMode ="";oldpaymentMode ="" ; updatedpaymentMode ="";
        scannedBarcode ="" ;retailerKey = "";retailername = "" ;retailermobileno ="" ;retaileraddress ="";retailerGSTIN="";gradeprice_double=0;
        oldretaileraddress = "";oldRetailerName =""; oldretailerMobileno ="" ; oldRetailerKey ="" ;oldretailerGSTIN ="";
        retailerName_autoComplete_Edittext.setText("");
        retailerName_autoComplete_Edittext.setText("");
        retailerName_autoComplete_Edittext.setText("");
        retailerName_autoComplete_Edittext.clearFocus();
        discount_editText.setText("");
        setAdapterForRetailerDetails();
        earTagDetails_JSONFinalSalesHashMap.clear();
        //gradeWise_count_weightJSONOBJECT = new JSONObject();
        isRetailerUpdated = false ;isPricePerKgUpdated = false ;
        priceperKg_not_edited_byUser = true  ;

        Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();
        //earTagDetailsHashMap.put("",modal_goatEarTagDetails);
        earTagDetails_weightStringHashMap.put("",new JSONObject());
        earTagDetailsArrayList_String.add("");
        try {
//            adapter_billingScreen_cartList.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            adapter_gradeWiseTotal_billingScreen.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try{
            addDataToPaymentModeSpinner();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        gradewisetotalCount_listview.setVisibility(View.GONE);
        pricePerKg_editText.clearFocus();
        pricePerKg_editText.setText("");
        pricePerKg_editText.clearFocus();
        calculateTotalweight_Quantity_Price();
        closeFragment();
        showProgressBar(false);

    }

    private void call_and_init_B2BRetailerDetailsService() {

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
                setAdapterForRetailerDetails();
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 1 " + DateParser.getDate_and_time_newFormat());

            }

            @Override
            public void notifySuccess(String result) {
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 2 " + DateParser.getDate_and_time_newFormat());

                isRetailerDetailsServiceCalled = false;
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isRetailerDetailsServiceCalled = false;
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 3 " + DateParser.getDate_and_time_newFormat());

            }

            @Override
            public void notifyProcessingError(Exception error) {
                isRetailerDetailsServiceCalled = false;
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());

            }


        };

        String getApiToCall = API_Manager.getretailerDetailsListWithDeliveryCentreKey+deliveryCenterKey ; ;

        B2BRetailerDetails asyncTask = new B2BRetailerDetails(callback_retailerDetailsInterface,  getApiToCall, Constants.CallGETListMethod);
        asyncTask.execute();




    }

    private void setAdapterForRetailerDetails() {
        try {
            adapter_autoComplete_retailerName = new Adapter_AutoComplete_RetailerName(BillingScreen.this, retailerDetailsArrayList, BillingScreen.this);
            adapter_autoComplete_retailerName.setHandler(newHandler());


            retailerName_autoComplete_Edittext.setAdapter(adapter_autoComplete_retailerName);
            retailerName_autoComplete_Edittext.clearFocus();
            retailerName_autoComplete_Edittext.setThreshold(1);
            retailerName_autoComplete_Edittext.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public  void Initialize_and_StartBarcodeScanner(String processtoDOAfterScan, String barcodeFromStringArray ,boolean isCalledForPlaceNewOrder) {
     try {

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
                 processArray_And_AddItem_InTheCart(scannedBarcode);

             }

             @Override
             public void notifySuccessAndFetchData(String Barcode) {
                 showProgressBar(true);
                 scannedBarcode = Barcode;
                 isBarcodeScannerServiceCalled = false;
                 if (isCalledForPlaceNewOrder) {
                     Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod, scannedBarcode, isCalledForPlaceNewOrder);

                 } else {
                     Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod, barcodeFromStringArray, false);
                 }

                 // Toast.makeText(mContext, "Scan And Fetch", Toast.LENGTH_SHORT).show();

             }

             @Override
             public void notifyProcessingError(Exception error) {
                 Toast.makeText(BillingScreen.this, "Error in scanning ", Toast.LENGTH_SHORT).show();


             }
         };

         showProgressBar(false);
         Intent intent = new Intent(BillingScreen.this, BarcodeScannerScreen.class);
         intent.putExtra(getString(R.string.scanner_called_to_do), processtoDOAfterScan);
         intent.putExtra(getString(R.string.called_from), getString(R.string.billing_Screen));
         intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
         startActivity(intent);
     }
     catch (Exception e){
         showProgressBar(false);
         e.printStackTrace();
     }


    }

    private void processArray_And_AddItem_InTheCart(String scannedBarcode) {
        String ctgykey ="", ctgyname ="" , subctgykey ="",suctgyname ="";
        for(int iterator = 0; iterator < earTagDetailsArrayList_WholeBatch.size() ; iterator ++){

            if(earTagDetailsArrayList_WholeBatch.get(iterator).getBarcodeno().equals(scannedBarcode)){
                earTagDetailsArrayList_String.add(scannedBarcode);
                if(suctgyname .toUpperCase().equals(earTagDetailsArrayList_WholeBatch.get(iterator).getBreedtype().toUpperCase())){
                    earTagDetailsArrayList_WholeBatch.get(iterator).setB2bctgykey(ctgykey);
                    earTagDetailsArrayList_WholeBatch.get(iterator).setB2bsubctgykey(subctgykey);
                }
                else{
                    for(int iterator2 = 0; iterator2 < ctgy_subCtgy_DetailsArrayList.size(); iterator2++){
                        if(ctgy_subCtgy_DetailsArrayList.get(iterator2).getSubctgy_name().toUpperCase() .equals(earTagDetailsArrayList_WholeBatch.get(iterator).getBreedtype().toUpperCase())){

                            ctgykey = ctgy_subCtgy_DetailsArrayList.get(iterator2).getKey();
                            ctgyname = ctgy_subCtgy_DetailsArrayList.get(iterator2).getName();
                            subctgykey = ctgy_subCtgy_DetailsArrayList.get(iterator2).getSubctgy_key();
                            suctgyname = ctgy_subCtgy_DetailsArrayList.get(iterator2).getSubctgy_name();



                            earTagDetailsArrayList_WholeBatch.get(iterator).setB2bctgykey(ctgykey);
                            earTagDetailsArrayList_WholeBatch.get(iterator).setB2bsubctgykey(subctgykey);



                        }
                    }
                }

                //earTagDetailsHashMap.put(scannedBarcode,earTagDetailsArrayList_WholeBatch.get(iterator));
                //adapter_billingScreen_cartList.notifyDataSetChanged();
            }


        }

    }

    public void Initialize_and_ExecuteInGoatEarTagDetails(String callMethod, String barcodeFromStringArray , boolean isCalledForPlaceNewOrder) {


        showProgressBar(true);
        if (isGoatEarTagDetailsTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isGoatEarTagDetailsTableServiceCalled = true;

        callback_GoatEarTagDetails = new GoatEarTagDetailsInterface() {


            @Override
            public void notifySuccess(String result) {
                isGoatEarTagDetailsTableServiceCalled = false;
                showProgressBar(false);
                if (result.equals(Constants.emptyResult_volley)) {
                    try {
                        AlertDialogClass.showDialog(BillingScreen.this, R.string.EarTagDetailsNotFound_Instruction);

                    } catch (Exception e) {
                        Toast.makeText(BillingScreen.this, getString(R.string.EarTagDetailsNotFound_Instruction), Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    }

                }
                else {

                    if (Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_EarTagLost)) {

                      try{
                           AlertDialogClass.showDialog(BillingScreen.this,R.string.EarTagLost_Instruction);

                      }
                      catch (Exception e){
                          Toast.makeText(BillingScreen.this, getString(R.string.EarTagLost_Instruction), Toast.LENGTH_LONG).show();

                          e.printStackTrace();
                      }

                        return;
                    } else if (Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_GoatLost)) {

                        try{
                            AlertDialogClass.showDialog(BillingScreen.this,R.string.GoatLost_Instruction);

                        }
                        catch (Exception e){
                            Toast.makeText(BillingScreen.this, getString(R.string.GoatLost_Instruction), Toast.LENGTH_LONG).show();

                            e.printStackTrace();
                        }

                        return;

                    } else if (Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatdead)) {

                        try{
                             AlertDialogClass.showDialog(BillingScreen.this,R.string.GoatDead_Instruction);

                        }
                        catch (Exception e){
                            Toast.makeText(BillingScreen.this, getString(R.string.GoatDead_Instruction), Toast.LENGTH_LONG).show();

                            e.printStackTrace();
                        }

                        return;

                    }
                    else if (Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Sold)) {

                        try{
                            AlertDialogClass.showDialog(BillingScreen.this,R.string.EarTagSold_Instruction);

                        }
                        catch (Exception e){
                            Toast.makeText(BillingScreen.this, getString(R.string.EarTagSold_Instruction), Toast.LENGTH_LONG).show();

                            e.printStackTrace();
                        }
                        return;

                    }
                    else if (Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatsick)) {

                        try{
                            AlertDialogClass.showDialog(BillingScreen.this,R.string.GoatSick_Instruction);

                        }
                        catch (Exception e){
                            Toast.makeText(BillingScreen.this, getString(R.string.GoatSick_Instruction), Toast.LENGTH_LONG).show();

                            e.printStackTrace();
                        }


                    }

                    if(!isCalledForPlaceNewOrder) {

/*
                        Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();

                        modal_goatEarTagDetails.barcodeno = Modal_Static_GoatEarTagDetails.getBarcodeno();
                        modal_goatEarTagDetails.batchno = Modal_Static_GoatEarTagDetails.getBatchno();
                        modal_goatEarTagDetails.status = Modal_Static_GoatEarTagDetails.getStatus();
                        modal_goatEarTagDetails.suppliername = Modal_Static_GoatEarTagDetails.getSuppliername();
                        modal_goatEarTagDetails.supplierkey = Modal_Static_GoatEarTagDetails.getSupplierkey();
                        modal_goatEarTagDetails.description = Modal_Static_GoatEarTagDetails.getDescription();
                        modal_goatEarTagDetails.itemaddeddate = Modal_Static_GoatEarTagDetails.getItemaddeddate();
                        modal_goatEarTagDetails.stockedweightingrams = Modal_Static_GoatEarTagDetails.getStockedweightingrams();
                        modal_goatEarTagDetails.selecteditem = Modal_Static_GoatEarTagDetails.getSelecteditem();
                        modal_goatEarTagDetails.gender = Modal_Static_GoatEarTagDetails.getGender();
                        modal_goatEarTagDetails.breedtype = Modal_Static_GoatEarTagDetails.getBreedtype();
                        modal_goatEarTagDetails.loadedweightingrams = Modal_Static_GoatEarTagDetails.getLoadedweightingrams();
                        modal_goatEarTagDetails.currentweightingrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();


                        if (!earTagDetailsHashMap.containsKey(Modal_Static_GoatEarTagDetails.getBarcodeno())) {
                            if (!earTagDetailsArrayList_String.contains(Modal_Static_GoatEarTagDetails.getBarcodeno())) {
                                String ctgykey = "", ctgyname = "", subctgykey = "", suctgyname = "";

                                for (int iterator2 = 0; iterator2 < ctgy_subCtgy_DetailsArrayList.size(); iterator2++) {
                                    String subctgyname = String.valueOf(ctgy_subCtgy_DetailsArrayList.get(iterator2).getSubctgy_name().toUpperCase());
                                    if (subctgyname.equals(Modal_Static_GoatEarTagDetails.getBreedtype().toUpperCase())) {

                                        ctgykey = ctgy_subCtgy_DetailsArrayList.get(iterator2).getKey();
                                        ctgyname = ctgy_subCtgy_DetailsArrayList.get(iterator2).getName();
                                        subctgykey = ctgy_subCtgy_DetailsArrayList.get(iterator2).getSubctgy_key();
                                        suctgyname = ctgy_subCtgy_DetailsArrayList.get(iterator2).getSubctgy_name();


                                        modal_goatEarTagDetails.setB2bctgykey(ctgykey);
                                        modal_goatEarTagDetails.setB2bsubctgykey(subctgykey);


                                    }
                                }

                                earTagDetailsArrayList_String.add(Modal_Static_GoatEarTagDetails.getBarcodeno());
                            }
                            earTagDetailsHashMap.put(Modal_Static_GoatEarTagDetails.getBarcodeno(), modal_goatEarTagDetails);
                            calculateTotalweight_Quantity_Price();
                            adapter_billingScreen_cartList.notifyDataSetChanged();
                        } else {
                            Toast.makeText(BillingScreen.this, "Please Scan the New Item", Toast.LENGTH_SHORT).show();

                        }


 */
                        if (!earTagDetails_weightStringHashMap.containsKey(Modal_Static_GoatEarTagDetails.getBarcodeno()))
                        {
                            JSONObject jsonObject = new JSONObject();
                            try{
                                jsonObject.put("weight",Modal_Static_GoatEarTagDetails.getCurrentweightingrams());
                                jsonObject.put("gradekey",Modal_Static_GoatEarTagDetails.getGradekey());
                                jsonObject.put("gradeprice",Modal_Static_GoatEarTagDetails.getGradeprice());
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            earTagDetailsArrayList_String.add(Modal_Static_GoatEarTagDetails.getBarcodeno());
                            earTagDetails_weightStringHashMap.put(Modal_Static_GoatEarTagDetails.getBarcodeno(),jsonObject);

                            calculateTotalweight_Quantity_Price();

                        }
                        else{
                            Toast.makeText(BillingScreen.this, "Please Scan the New Item", Toast.LENGTH_SHORT).show();

                        }



                            earTagDetails_weightStringHashMap.remove(barcodeFromStringArray);
                      //  earTagDetailsHashMap.remove(barcodeFromStringArray);
                        earTagDetailsArrayList_String.remove(barcodeFromStringArray);
                        adapter_billingScreen_cartList.notifyDataSetChanged();
                        calculateTotalweight_Quantity_Price();


                    }
                    else{
                        Modal_Static_GoatEarTagDetails.barcodeno = Modal_Static_GoatEarTagDetails.getBarcodeno();
                        Modal_Static_GoatEarTagDetails.batchno = Modal_Static_GoatEarTagDetails.getBatchno();
                        Modal_Static_GoatEarTagDetails.status = Modal_Static_GoatEarTagDetails.getStatus();
                        Modal_Static_GoatEarTagDetails.suppliername = Modal_Static_GoatEarTagDetails.getSuppliername();
                        Modal_Static_GoatEarTagDetails.supplierkey = Modal_Static_GoatEarTagDetails.getSupplierkey();
                        Modal_Static_GoatEarTagDetails.description = Modal_Static_GoatEarTagDetails.getDescription();
                        Modal_Static_GoatEarTagDetails.itemaddeddate = Modal_Static_GoatEarTagDetails.getItemaddeddate();
                        Modal_Static_GoatEarTagDetails.stockedweightingrams = Modal_Static_GoatEarTagDetails.getStockedweightingrams();
                        Modal_Static_GoatEarTagDetails.selecteditem = Modal_Static_GoatEarTagDetails.getSelecteditem();
                        Modal_Static_GoatEarTagDetails.gender = Modal_Static_GoatEarTagDetails.getGender();
                        Modal_Static_GoatEarTagDetails.breedtype = Modal_Static_GoatEarTagDetails.getBreedtype();
                        Modal_Static_GoatEarTagDetails.loadedweightingrams = Modal_Static_GoatEarTagDetails.getLoadedweightingrams();
                        Modal_Static_GoatEarTagDetails.currentweightingrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();
                        Modal_Static_GoatEarTagDetails.gradeprice = Modal_Static_GoatEarTagDetails.getGradeprice();
                        Modal_Static_GoatEarTagDetails.gradename = Modal_Static_GoatEarTagDetails.getGradename();
                        Modal_Static_GoatEarTagDetails.gradekey = Modal_Static_GoatEarTagDetails.getGradekey();



                        if (!earTagDetails_weightStringHashMap.containsKey(Modal_Static_GoatEarTagDetails.getBarcodeno())) {
                            if (earTagDetailsArrayList_String.contains(Modal_Static_GoatEarTagDetails.getBarcodeno())) {
                                earTagDetailsArrayList_String.remove(Modal_Static_GoatEarTagDetails.getBarcodeno());
                            }
                            value_forFragment = getString(R.string.billing_Screen_placeOrder);
                            mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                            try{
                                loadMyFragment();
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        else{
                            Toast.makeText(BillingScreen.this, "Please Scan the New Item", Toast.LENGTH_SHORT).show();

                        }

                    /*
                        if (!earTagDetailsHashMap.containsKey(Modal_Static_GoatEarTagDetails.getBarcodeno())) {
                            if (!earTagDetailsArrayList_String.contains(Modal_Static_GoatEarTagDetails.getBarcodeno())) {
                                String ctgykey = "", ctgyname = "", subctgykey = "", suctgyname = "";

                                for (int iterator2 = 0; iterator2 < ctgy_subCtgy_DetailsArrayList.size(); iterator2++) {
                                    String subctgyname = String.valueOf(ctgy_subCtgy_DetailsArrayList.get(iterator2).getSubctgy_name().toUpperCase());
                                    if (subctgyname.equals(Modal_Static_GoatEarTagDetails.getBreedtype().toUpperCase())) {

                                        ctgykey = ctgy_subCtgy_DetailsArrayList.get(iterator2).getKey();
                                        ctgyname = ctgy_subCtgy_DetailsArrayList.get(iterator2).getName();
                                        subctgykey = ctgy_subCtgy_DetailsArrayList.get(iterator2).getSubctgy_key();
                                        suctgyname = ctgy_subCtgy_DetailsArrayList.get(iterator2).getSubctgy_name();


                                        Modal_Static_GoatEarTagDetails.setB2bctgykey(ctgykey);
                                        Modal_Static_GoatEarTagDetails.setB2bsubctgykey(subctgykey);


                                    }
                                }

                            }

                            value_forFragment = getString(R.string.billing_Screen_placeOrder);
                            mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                            try{
                                loadMyFragment();
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }




                        }
                        else {
                            Toast.makeText(BillingScreen.this, "Please Scan the New Item", Toast.LENGTH_SHORT).show();

                        }


                     */



                    }


                }

            }

            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatchFromDB) {
                try {
                    if (earTagItemsForBatchFromDB.size() > 0) {
                        earTagDetailsArrayList_WholeBatch = earTagItemsForBatchFromDB;
                        isGoatEarTagDetailsTableServiceCalled = false;
                        showProgressBar(false);

                    } else {
                        showProgressBar(false);
                        isGoatEarTagDetailsTableServiceCalled = false;
                        Toast.makeText(BillingScreen.this, Constants.there_is_noData_volley, Toast.LENGTH_SHORT).show();


                    }

                } catch (Exception e) {
                    showProgressBar(false);
                    isGoatEarTagDetailsTableServiceCalled = false;

                    Toast.makeText(BillingScreen.this, "There is an error while generate report", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }

                showProgressBar(false);
                isGoatEarTagDetailsTableServiceCalled = false;



            }


            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
                Toast.makeText(BillingScreen.this, "There is an volley error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                isGoatEarTagDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                Toast.makeText(BillingScreen.this, "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();

                showProgressBar(false);
                isGoatEarTagDetailsTableServiceCalled = false;


            }


        };

        if (callMethod.equals(Constants.CallGETMethod)) {
            String addApiToCall = API_Manager.getGoatEarTagDetails_forBarcodeWithBatchno + "?barcodeno=" + scannedBarcode + "&batchno=" + batchno;
            GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
            asyncTask.execute();
        } else if (callMethod.equals(Constants.CallGETListMethod)) {
            //String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchno + batchno;
           // GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
          //  asyncTask.execute();
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

    private Handler newHandler() {
        Handler.Callback callback = new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                String data = bundle.getString("CartItem");
                if(data.equals("")){
                    String data1 = bundle.getString("dropdown");
                    if (data1.equalsIgnoreCase("addNewItem")) {

                    }

                    if (data1.equalsIgnoreCase("addBillDetails")) {
                        //   createBillDetails(cart_Item_List);

                    }
                    if (String.valueOf(data1).equalsIgnoreCase("dropdown")) {
                        //Log.e(TAG, "dismissDropdown");
                        //Log.e(Constants.TAG, "createBillDetails in CartItem 0 ");



                        retailerName_autoComplete_Edittext.clearFocus();

                        retailerName_autoComplete_Edittext.dismissDropDown();



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

    private void setAdapterForCartItem() {
        try {
            cartItem_recyclerView.setVisibility(View.GONE);
            //  adapter_billingScreen_cartList = new Adapter_BillingScreen_CartRecyclerList(BillingScreen.this,earTagDetailsHashMap,BillingScreen.this);
            // cartItem_recyclerView.setLayoutManager(new LinearLayoutManager(BillingScreen.this));
            // int last_index=NewOrderScreenFragment_mobile.cartItem_hashmap.size()-1;
            // cartItem_recyclerView.scrollToPosition(last_index);
            //   cartItem_recyclerView.setAdapter(adapter_billingScreen_cartList);

            calculateTotalweight_Quantity_Price();
            showProgressBar(false);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void calculateTotalweight_Quantity_Price() {
        String totalCount = "";
        if(earTagDetails_weightStringHashMap.containsKey("")){
            totalCount = String.valueOf(earTagDetails_weightStringHashMap.size() - 1);
        }
        else{
            totalCount = String.valueOf(earTagDetails_weightStringHashMap.size());
        }




        /*if(earTagDetailsHashMap.containsKey("")){
            totalCount = String.valueOf(earTagDetailsHashMap.size() - 1);
        }
        else{
            totalCount = String.valueOf(earTagDetailsHashMap.size());
        }

         */

        String Weight ="" , pricePerKg  = "" , discountAmount = "",gradeprice_string ="", totalPrice =  "" ,gradename ="" , gradeprice ="";
        JSONObject jsonObject = new JSONObject();
        totalWeight_double =0 ; weight_double = 0 ; pricePerKg_double = 0 ; discountAmount_double = 0 ; totalPrice_double = 0 ; totalPrice_doubleWithoutDiscount =0 ; gradeprice_double =0;

        for(int iterator =0 ; iterator < earTagDetailsArrayList_String .size(); iterator ++){
/*
            if(earTagDetailsHashMap.containsKey(earTagDetailsArrayList_String .get(iterator))) {
                try {
                    totalWeight = earTagDetailsHashMap.get(earTagDetailsArrayList_String.get(iterator)).getNewWeight_forBillingScreen();
                    totalWeight = totalWeight.replaceAll("[^\\d.]", "");
                    if (totalWeight.equals("") || totalWeight.equals(null)) {
                        totalWeight = "0";
                    }
                    totalWeight_double = totalWeight_double + Double.parseDouble(totalWeight);
                    totalWeight_double = Double.parseDouble(threeDecimalConverter.format(totalWeight_double));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

 */


            if(earTagDetails_weightStringHashMap.containsKey(earTagDetailsArrayList_String .get(iterator))) {

                try{
                    jsonObject = earTagDetails_weightStringHashMap.get(earTagDetailsArrayList_String.get(iterator));

                }
                catch (Exception e){
                    e.printStackTrace();
                }


                try {

                   if(jsonObject.has("weight")){
                       Weight = jsonObject.getString("weight");
                       Weight = Weight.replaceAll("[^\\d.]", "");
                       if (Weight.equals("") || Weight.equals(null)) {
                           Weight = "0";
                       }
                   }
                   else{
                       Weight ="0";
                   }


                    weight_double = Double.parseDouble(Weight);
                    weight_double = Double.parseDouble(threeDecimalConverter.format(weight_double));

                    totalWeight_double = weight_double + totalWeight_double;
                    totalWeight_double = Double.parseDouble(threeDecimalConverter.format(totalWeight_double));

                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
                    if(jsonObject.has("gradeprice")){
                        gradeprice_string = jsonObject.getString("gradeprice");
                        gradeprice_string = gradeprice_string.replaceAll("[^\\d.]", "");
                        if (gradeprice_string.equals("") || gradeprice_string.equals(null)) {
                            gradeprice_string = "0";
                        }
                    }
                    else{
                        gradeprice_string ="0";
                    }

                    gradeprice_double =  Double.parseDouble(gradeprice_string);
                    gradeprice_double = Double.parseDouble(threeDecimalConverter.format(gradeprice_double));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try{
                    totalPrice_doubleWithoutDiscount = totalPrice_doubleWithoutDiscount + (weight_double * gradeprice_double);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                try{
                    totalPrice_doubleWithoutDiscount = Double.parseDouble(threeDecimalConverter.format( totalPrice_doubleWithoutDiscount));
                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }
        }


                try{

                    pricePerKg = pricePerKg_editText.getText().toString();
                    pricePerKg = pricePerKg.replaceAll("[^\\d.]", "");
                    if(pricePerKg.equals("") ||pricePerKg.equals(null) ){
                        pricePerKg = "0";
                   //     Toast.makeText(this, "Please set the value of price per kg", Toast.LENGTH_SHORT).show();
                    }
                    pricePerKg_double = Double.parseDouble(pricePerKg);
                    pricePerKg_double = Double.parseDouble(twoDecimalConverter.format(pricePerKg_double));
                }
                catch (Exception e){
                    e.printStackTrace();
                }





                try{

                    discountAmount = discount_editText.getText().toString();
                    discountAmount = discountAmount.replaceAll("[^\\d.]", "");
                    if(discountAmount.equals("") ||discountAmount.equals(null) ){
                        discountAmount = "0.00";
                       // Toast.makeText(this, "Please set the value of dicount Amount", Toast.LENGTH_SHORT).show();
                    }
                    discountAmount_double = Double.parseDouble(discountAmount);

                    discountAmount_double = Double.parseDouble(twoDecimalConverter.format(discountAmount_double));
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                try{
                  //  int weight = Integer.parseInt(String.valueOf(Math.round(totalWeight_double)));
                   /* if (weight < 1000) {
                        totalPrice_double = (pricePerKg_double * weight);


                        totalPrice_double = totalPrice_double / 1000;

                        totalPrice_double = Double.parseDouble(df.format(totalPrice_double));


                    }

                    if (weight == 1000) {
                        totalPrice_double =  Double.parseDouble(df.format(totalPrice_double));


                    }

                    if (weight > 1000) {


                        int itemquantity = weight - 1000;
                        //Log.e("TAg", "weight itemquantity" + itemquantity);

                        totalPrice_double = (pricePerKg_double * itemquantity) / 1000;
                        totalPrice_double = Double.parseDouble(df.format(totalPrice_double));


                        //Log.e("TAg", "weight item_total" + item_total);

                        totalPrice_double= pricePerKg_double + totalPrice_double;
                        totalPrice_double = Double.parseDouble(df.format((totalPrice_double)));




                    }


                    */
                  //  totalPrice_doubleWithoutDiscount= pricePerKg_double *totalWeight_double ;
                    totalPrice_double= totalPrice_doubleWithoutDiscount - discountAmount_double;
                    totalPrice_double = Double.parseDouble(twoDecimalConverter.format((totalPrice_double)));


                }
                catch (Exception e){
                    e.printStackTrace();
                }






                totalItem_CountTextview.setText(String.valueOf(totalCount));
                totalPrice_textview.setText(String.valueOf(twoDecimalConverter.format(totalPrice_double)));
                totalWeight_textview.setText(String.valueOf(threeDecimalConverter.format(totalWeight_double)));



    }

    private void Initialize_and_ExecuteB2BCtgyItem() {

        showProgressBar(true);
        if (isB2BItemCtgyTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isB2BItemCtgyTableServiceCalled = true;
        callback_B2BItemCtgyInterface = new B2BItemCtgyInterface() {
            @Override
            public void notifySuccess(String result) {
                 // showProgressBar(false);
                isB2BItemCtgyTableServiceCalled = false;
                ctgy_subCtgy_DetailsArrayList = DatabaseArrayList_PojoClass.breedType_arrayList;
            }

            @Override
            public void notifyError(VolleyError error) {
                   showProgressBar(false);
                isB2BItemCtgyTableServiceCalled = false;

            }
        };
        String addApiToCall = API_Manager.getB2BItemCtgy ;
        B2BItemCtgy asyncTask = new B2BItemCtgy(callback_B2BItemCtgyInterface,  addApiToCall );
        asyncTask.execute();




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

            if (retailerDetailsFrame.getVisibility() == View.VISIBLE) {
                retailerDetailsFrame.setVisibility(View.GONE);

                if (isorderSummary_checkoutClicked) {
                    showProgressBar(true);
                    neutralizeArray_and_OtherValues();
                    isorderSummary_checkoutClicked = false;
                    return;


                } else {
                    return;
                }

            }
            //Intent intent = new Intent(View_or_Edit_BatchItem_deliveryCenter.this, DeliveryCenterDashboardScreen.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //startActivity(intent);
            finish();
            overridePendingTransition(0, 0);

    }

    public boolean doesArrayContainsZeroForNewWeightInGrams() {
        boolean ishaveZero = false;
        for(int iterator =0; iterator < earTagDetailsArrayList_String.size() ; iterator ++){

           if(earTagDetails_weightStringHashMap.containsKey(earTagDetailsArrayList_String.get(iterator))){
               String weightinGrams = String.valueOf(earTagDetails_weightStringHashMap.get(earTagDetailsArrayList_String.get(iterator)));
               if(weightinGrams.equals("") ||weightinGrams.equals(null) || weightinGrams.equals("0")){
                   weightinGrams = "0";
                   ishaveZero = true;
               }
           }


           /* if(earTagDetailsHashMap.containsKey(earTagDetailsArrayList_String.get(iterator))){
                String weightinGrams = String.valueOf(earTagDetailsHashMap.get(earTagDetailsArrayList_String.get(iterator)).getNewWeight_forBillingScreen());
                if(weightinGrams.equals("") ||weightinGrams.equals(null) || weightinGrams.equals("0")){
                    weightinGrams = "0";
                    ishaveZero = true;
                }
            }

            */
        }


        return  ishaveZero;
    }


    /*
    private void addItemRows(Document layoutDocument) {




        Font font = new Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 39, Font.BOLD);


        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
                Font.BOLD);

        Font subtitleFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,
                Font.NORMAL);
        Font subtitleFontLabel = new Font(Font.FontFamily.TIMES_ROMAN, 13,
                Font.BOLD);

        Font itemNameFont = new Font(Font.FontFamily.TIMES_ROMAN, 15,
                Font.NORMAL);

        Font itemNameFontLabel = new Font(Font.FontFamily.TIMES_ROMAN, 14,
                Font.BOLD);

        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 13,
                Font.NORMAL);

        Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                Font.NORMAL);


        try {

            PdfPTable phraseTitle_table = new PdfPTable(1);


            Phrase phraseTitle = new Phrase(" The Meat Chop ", titleFont);
            PdfPCell phraseTitlecell = new PdfPCell(phraseTitle);
            phraseTitlecell.setBorder(Rectangle.NO_BORDER);
            phraseTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
            phraseTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
            phraseTitlecell.setPaddingLeft(10);
            phraseTitlecell.setPaddingBottom(3);
            phraseTitle_table.addCell(phraseTitlecell);




            Phrase phraseTitle2 = new Phrase("(Unit of Culinary Triangle Pvt Ltd)", subtitleFont);
            PdfPCell phraseTitle2cell = new PdfPCell(phraseTitle2);
            phraseTitle2cell.setBorder(Rectangle.NO_BORDER);
            phraseTitle2cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            phraseTitle2cell.setVerticalAlignment(Element.ALIGN_CENTER);
            phraseTitle2cell.setPaddingLeft(10);
            phraseTitle2cell.setPaddingBottom(12);
            phraseTitle_table.addCell(phraseTitle2cell);


            Phrase phraseTitle3 = new Phrase("Old No.4, New No.50, Kumaraswamy Street\n Lakshmipuram, Chrompet,\n  Chennai - 600 044 \n Contact no: 9962072027.", subtitleFontLabel);
            PdfPCell phraseTitle3cell = new PdfPCell(phraseTitle3);
            phraseTitle3cell.setBorder(Rectangle.NO_BORDER);
            phraseTitle3cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            phraseTitle3cell.setVerticalAlignment(Element.ALIGN_CENTER);
            phraseTitle3cell.setPaddingLeft(10);
            phraseTitle_table.addCell(phraseTitle3cell);


            Phrase phraseGSTIN = new Phrase("GSTIN : 33AAJCC0055D1Z9 ", smallFont);
            PdfPCell phraseGSTINcell = new PdfPCell(phraseGSTIN);
            phraseGSTINcell.setBorder(Rectangle.NO_BORDER);
            phraseGSTINcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            phraseGSTINcell.setVerticalAlignment(Element.ALIGN_CENTER);
            phraseGSTINcell.setPaddingLeft(10);
            phraseGSTINcell.setPaddingBottom(25);
            phraseTitle_table.addCell(phraseGSTINcell);
            layoutDocument.add(phraseTitle_table);

        //   PdfPTable phraseSecodaryTitle_table = new PdfPTable(1);

        //    Phrase phraseAddresslabel = new Phrase("B2B Delivery Centre :  " , subtitleFontLabel);
           // PdfPCell phraseAddresslabelCell   = new PdfPCell(phraseAddresslabel);
         //   phraseAddresslabelCell.setBorder(Rectangle.NO_BORDER);
         //   phraseAddresslabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
         //   phraseAddresslabelCell.setVerticalAlignment(Element.ALIGN_CENTER);
       //     phraseAddresslabelCell.setPaddingLeft(10);
      //      phraseAddresslabelCell.setPaddingBottom(4);
       //     phraseSecodaryTitle_table.addCell(phraseAddresslabelCell);
      //      layoutDocument.add(phraseSecodaryTitle_table);



            //PdfPTable phraseSecodaryTitle_table1 = new PdfPTable(1);

          //  Phrase phraseAddress = new Phrase( deliveryCenterName, normalFont);
        //    PdfPCell phraseAddressCell = new PdfPCell(phraseAddress);
       //     phraseAddressCell.setBorder(Rectangle.NO_BORDER);
       //     phraseAddressCell.setHorizontalAlignment(Element.ALIGN_LEFT);
       //     phraseAddressCell.setVerticalAlignment(Element.ALIGN_CENTER);
     //       phraseAddressCell.setPaddingLeft(10);
      //      phraseAddressCell.setPaddingBottom(10);
      //      phraseSecodaryTitle_table1.addCell(phraseAddressCell);


            layoutDocument.add(phraseSecodaryTitle_table1);




    PdfPTable phraseSecodaryTitle_table = new PdfPTable(1);

    Phrase phraseAddresslabel = new Phrase("Retailer Name" , subtitleFontLabel);
    PdfPCell phraseAddresslabelCell   = new PdfPCell(phraseAddresslabel);
            phraseAddresslabelCell.setBorder(Rectangle.NO_BORDER);
            phraseAddresslabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseAddresslabelCell.setVerticalAlignment(Element.ALIGN_CENTER);
            phraseAddresslabelCell.setPaddingLeft(10);
            phraseAddresslabelCell.setPaddingBottom(4);
            phraseSecodaryTitle_table.addCell(phraseAddresslabelCell);
            layoutDocument.add(phraseSecodaryTitle_table);



    PdfPTable phraseSecodaryTitle_table1 = new PdfPTable(1);

    Phrase phraseAddress = new Phrase( retailername, normalFont);
    PdfPCell phraseAddressCell = new PdfPCell(phraseAddress);
            phraseAddressCell.setBorder(Rectangle.NO_BORDER);
            phraseAddressCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseAddressCell.setVerticalAlignment(Element.ALIGN_CENTER);
            phraseAddressCell.setPaddingLeft(10);
            phraseAddressCell.setPaddingBottom(10);
            phraseSecodaryTitle_table1.addCell(phraseAddressCell);


            layoutDocument.add(phraseSecodaryTitle_table1);

    PdfPTable phraseSecodaryTitle2_table = new PdfPTable(2);


    Phrase phraseretailernamelabel = new Phrase("Mobile No : ", subtitleFontLabel);
    PdfPCell phraseretailernameLabelcell = new PdfPCell(phraseretailernamelabel);
            phraseretailernameLabelcell.setBorder(Rectangle.NO_BORDER);
            phraseretailernameLabelcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseretailernameLabelcell.setVerticalAlignment(Element.ALIGN_CENTER);
            phraseretailernameLabelcell.setPaddingLeft(10);


            phraseSecodaryTitle2_table.addCell(phraseretailernameLabelcell);




    Phrase phraseorderid = new Phrase("#" + orderid, normalFont);
    PdfPCell phraseOrderidcell = new PdfPCell(phraseorderid);
            phraseOrderidcell.setBorder(Rectangle.NO_BORDER);
            phraseOrderidcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            phraseOrderidcell.setVerticalAlignment(Element.ALIGN_CENTER);
            phraseOrderidcell.setPaddingLeft(10);
            phraseOrderidcell.setPaddingBottom(4);
            phraseSecodaryTitle2_table.addCell(phraseOrderidcell);


            layoutDocument.add(phraseSecodaryTitle2_table);


    PdfPTable phraseSecodaryTitle2_table1 = new PdfPTable(2);
    Phrase phraseretailername = new Phrase(retailermobileno, normalFont);
    PdfPCell phraseretailernamecell = new PdfPCell(phraseretailername);
            phraseretailernamecell.setBorder(Rectangle.NO_BORDER);
            phraseretailernamecell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseretailernamecell.setVerticalAlignment(Element.ALIGN_CENTER);
            phraseretailernamecell.setPaddingLeft(10);
            phraseSecodaryTitle2_table1.addCell(phraseretailernamecell);




    Phrase phrasedate = new Phrase( DateParser.convertTime_to_DisplayingFormat(DateParser.getDate_and_time_newFormat()), normalFont);
    PdfPCell phrasedateincell = new PdfPCell(phrasedate);
            phrasedateincell.setBorder(Rectangle.NO_BORDER);
            phrasedateincell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            phrasedateincell.setVerticalAlignment(Element.ALIGN_CENTER);
            phrasedateincell.setPaddingLeft(10);
            phraseSecodaryTitle2_table1.addCell(phrasedateincell);

            layoutDocument.add(phraseSecodaryTitle2_table1);





    PdfPTable phraseempty_table = new PdfPTable(1);

    Phrase phraseDots = new Phrase("                  ");
    PdfPCell phraseDotscell = new PdfPCell(phraseDots);
            phraseDotscell.setBorder(Rectangle.NO_BORDER);
            phraseDotscell.setPaddingLeft(10);
            phraseDotscell.setBorderWidthBottom(01);
            phraseempty_table.addCell(phraseDotscell);
            layoutDocument.add(phraseempty_table);

}
        catch (Exception ex) {
                ex.printStackTrace();
                }

                try{

                PdfPTable phrasebodyItemDetails_table = new PdfPTable(4);

                Phrase phrasebodygenderLabel = new Phrase("Gender  ", itemNameFontLabel);
                PdfPCell itemgenderLabelcell = new PdfPCell(new Phrase(phrasebodygenderLabel));
                itemgenderLabelcell.setBorder(Rectangle.NO_BORDER);
                itemgenderLabelcell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                itemgenderLabelcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                itemgenderLabelcell.setPaddingLeft(10);
                itemgenderLabelcell.setPaddingRight(10);
                itemgenderLabelcell.setPaddingBottom(5);
                phrasebodyItemDetails_table.addCell(itemgenderLabelcell);


                Phrase phrasebodyquantityLabel = new Phrase("Price /Kg  ", itemNameFontLabel);
                PdfPCell itemquantityLabelcell = new PdfPCell(new Phrase(phrasebodyquantityLabel));
                itemquantityLabelcell.setBorder(Rectangle.NO_BORDER);
                itemquantityLabelcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                itemquantityLabelcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                itemquantityLabelcell.setPaddingLeft(10);
                itemquantityLabelcell.setPaddingRight(10);
                itemquantityLabelcell.setPaddingBottom(5);
                phrasebodyItemDetails_table.addCell(itemquantityLabelcell);



                Phrase phrasebodyweightLabel = new Phrase("Weight (Qty)", itemNameFontLabel);
                PdfPCell itemweightLabelcell = new PdfPCell(new Phrase(phrasebodyweightLabel));
                itemweightLabelcell.setBorder(Rectangle.NO_BORDER);
                itemweightLabelcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                itemweightLabelcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                itemweightLabelcell.setPaddingLeft(10);
                itemweightLabelcell.setPaddingRight(10);
                itemweightLabelcell.setPaddingBottom(5);
                phrasebodyItemDetails_table.addCell(itemweightLabelcell);



                Phrase phrasebodyweightLabel1 = new Phrase("Total Price", itemNameFontLabel);
                PdfPCell itemweightLabelcell1 = new PdfPCell(new Phrase(phrasebodyweightLabel1));
                itemweightLabelcell1.setBorder(Rectangle.NO_BORDER);
                itemweightLabelcell1.setHorizontalAlignment(Element.ALIGN_LEFT);
                itemweightLabelcell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                itemweightLabelcell1.setPaddingLeft(10);
                itemweightLabelcell1.setPaddingRight(10);
                itemweightLabelcell1.setPaddingBottom(5);
                phrasebodyItemDetails_table.addCell(itemweightLabelcell1);

                layoutDocument.add(phrasebodyItemDetails_table);


                PdfPTable phrase_table = new PdfPTable(1);

                Phrase phraseEmptyLine2 = new Phrase("                  ");
                PdfPCell phraseEmptycell2 = new PdfPCell(phraseEmptyLine2);
                phraseEmptycell2.setBorder(Rectangle.NO_BORDER);
                phraseEmptycell2.setPaddingLeft(10);
                phraseEmptycell2.setBorderWidthBottom(01);
                phrase_table.addCell(phraseEmptycell2);
                layoutDocument.add(phrase_table);






                PdfPTable phrasebody1_table = new PdfPTable(4);

                String Weight = Modal_B2BOrderDetails.getTotalmaleweight_Static();
                Weight = Weight.replaceAll("[^\\d.]", "");
                if(Weight.equals("")){
                Weight ="0";
                }
                double pricePerKg_double = 0;
                String pricePerKg_string = "0";
                pricePerKg_string = Modal_B2BOrderDetails.getPriceperkg_Static();
                try{



                pricePerKg_string = Modal_B2BOrderDetails.getPriceperkg_Static();
                pricePerKg_string = pricePerKg_string.replaceAll("[^\\d.]", "");
                if (pricePerKg_string.equals("") || String.valueOf(pricePerKg_string).toUpperCase().equals("NULL")) {
                pricePerKg_string = "0";
                }
                pricePerKg_double = Double.parseDouble(pricePerKg_string);

                }
                catch (Exception e){
                e.printStackTrace();
                }

                if(Double.parseDouble(Weight) > 0) {


                Phrase phraseSubTotalLabel = new Phrase(" Male   ", itemNameFontLabel);
                PdfPCell subTotalLabelcell = new PdfPCell(new Phrase(phraseSubTotalLabel));
                subTotalLabelcell.setBorder(Rectangle.NO_BORDER);
                subTotalLabelcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                subTotalLabelcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                subTotalLabelcell.setPaddingLeft(10);
                subTotalLabelcell.setPaddingRight(20);
                subTotalLabelcell.setPaddingBottom(5);
                subTotalLabelcell.setPaddingTop(10);
                phrasebody1_table.addCell(subTotalLabelcell);


                Phrase phrasemaletotal2 = new Phrase("Rs. " + twoDecimalConverter.format((pricePerKg_double)), normalFont);
                PdfPCell malletotalcell2 = new PdfPCell(new Phrase(phrasemaletotal2));
                malletotalcell2.setBorder(Rectangle.NO_BORDER);
                malletotalcell2.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                malletotalcell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                malletotalcell2.setPaddingLeft(10);
                malletotalcell2.setPaddingRight(10);
                malletotalcell2.setPaddingBottom(5);
                malletotalcell2.setPaddingTop(10);
                phrasebody1_table.addCell(malletotalcell2);

                Phrase phrasemaleQuantity1 = new Phrase(threeDecimalConverter.format(Double.parseDouble(Weight)) + " Kg ( " + Modal_B2BOrderDetails.getTotalmalequantity_Static() + " ) ", normalFont);
                PdfPCell maleQuantitycell1 = new PdfPCell(new Phrase(phrasemaleQuantity1));
                maleQuantitycell1.setBorder(Rectangle.NO_BORDER);
                maleQuantitycell1.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                maleQuantitycell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                maleQuantitycell1.setPaddingLeft(10);
                maleQuantitycell1.setPaddingRight(10);
                maleQuantitycell1.setPaddingBottom(5);
                maleQuantitycell1.setPaddingTop(10);
                phrasebody1_table.addCell(maleQuantitycell1);


                double total_maleprice = 0;
                try {
                double weight_double = 0;
                weight_double = (Double.parseDouble(Weight));

                total_maleprice = weight_double * pricePerKg_double;

                } catch (Exception e) {
                e.printStackTrace();
                }


                Phrase phrasemaletotal = new Phrase("Rs. " + twoDecimalConverter.format((total_maleprice)), normalFont);
                PdfPCell maletotalcell = new PdfPCell(new Phrase(phrasemaletotal));
                maletotalcell.setBorder(Rectangle.NO_BORDER);
                maletotalcell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                maletotalcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                maletotalcell.setPaddingLeft(10);
                maletotalcell.setPaddingRight(10);
                maletotalcell.setPaddingBottom(5);
                maletotalcell.setPaddingTop(10);
                phrasebody1_table.addCell(maletotalcell);
                }

           /// Phrase phrasemaleQuantity = new Phrase( Modal_B2BOrderDetails.getTotalmalequantity_Static() , normalFont);
           // PdfPCell maleQuantitycell = new PdfPCell(new Phrase(phrasemaleQuantity));
          //  maleQuantitycell.setBorder(Rectangle.NO_BORDER);
          //  maleQuantitycell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        //   maleQuantitycell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         //   maleQuantitycell.setPaddingLeft(10);
          //  maleQuantitycell.setPaddingRight(10);
           // maleQuantitycell.setPaddingBottom(5);
          ///  maleQuantitycell.setPaddingTop(10);
         //   phrasebody1_table.addCell(maleQuantitycell);



         //   String Weight = Modal_B2BOrderDetails.getTotalmaleweight_Static();
         //   Weight = Weight.replaceAll("[^\\d.]", "");
         //   if(Weight.equals("")){
         //       Weight ="0";
        //    }
      ///      Phrase phrasemaleWeight = new Phrase( threeDecimalConverter.format(Double.parseDouble(Weight)) +" Kg", normalFont);
     //       PdfPCell maleWeightcell = new PdfPCell(new Phrase(phrasemaleWeight));
      ///      maleWeightcell.setBorder(Rectangle.NO_BORDER);
         //   maleWeightcell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
         //   maleWeightcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         //   maleWeightcell.setPaddingLeft(10);
        //    maleWeightcell.setPaddingRight(10);
       ///     maleWeightcell.setPaddingBottom(5);
        //    maleWeightcell.setPaddingTop(10);
       //     phrasebody1_table.addCell(maleWeightcell);




            //    String femaleWeight = Modal_B2BOrderDetails.getTotalfemaleweight_Static();
            ///    femaleWeight = femaleWeight.replaceAll("[^\\d.]", "");
             //   if(femaleWeight.equals("")){
            //    femaleWeight ="0";
         //       }


            //    if(Double.parseDouble(femaleWeight) > 0) {

          //      Phrase phraseFemaleLabel = new Phrase(" Female   ", itemNameFontLabel);
        //  //      PdfPCell femaleLabelcell = new PdfPCell(new Phrase(phraseFemaleLabel));
           //     femaleLabelcell.setBorder(Rectangle.NO_BORDER);
          //      femaleLabelcell.setHorizontalAlignment(Element.ALIGN_LEFT);
           //     femaleLabelcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
          //      femaleLabelcell.setPaddingLeft(10);
           //     femaleLabelcell.setPaddingRight(20);
          //      femaleLabelcell.setPaddingBottom(5);
          //      femaleLabelcell.setPaddingTop(10);
       //         phrasebody1_table.addCell(femaleLabelcell);


          //  Phrase phrasefemaleQuantity = new Phrase( Modal_B2BOrderDetails.getTotalfemalequantity_Static() , normalFont);
       //     PdfPCell femaleQuantitycell = new PdfPCell(new Phrase(phrasefemaleQuantity));
      //      femaleQuantitycell.setBorder(Rectangle.NO_BORDER);
     ///       femaleQuantitycell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
    //        femaleQuantitycell.setVerticalAlignment(Element.ALIGN_MIDDLE);
      //      femaleQuantitycell.setPaddingLeft(10);
        //    femaleQuantitycell.setPaddingRight(10);
       //     femaleQuantitycell.setPaddingBottom(5);
      /      femaleQuantitycell.setPaddingTop(10);
      ///      phrasebody1_table.addCell(femaleQuantitycell);
       //     String femaleWeight = Modal_B2BOrderDetails.getTotalfemaleweight_Static();
        //    femaleWeight = femaleWeight.replaceAll("[^\\d.]", "");
         //   if(femaleWeight.equals("")){
         //       femaleWeight ="0";
      //      }
        //    Phrase phrasefemaleWeight = new Phrase( threeDecimalConverter.format(Double.parseDouble(femaleWeight)) +" Kg", normalFont);
       //     PdfPCell femaleWeightcell = new PdfPCell(new Phrase(phrasefemaleWeight));
       //     femaleWeightcell.setBorder(Rectangle.NO_BORDER);
          //  femaleWeightcell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        //    femaleWeightcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         //   femaleWeightcell.setPaddingLeft(10);
         //   femaleWeightcell.setPaddingRight(10);
       //     femaleWeightcell.setPaddingBottom(5);
       //     femaleWeightcell.setPaddingTop(10);
     //       phrasebody1_table.addCell(femaleWeightcell);





               Phrase phrasemaletotal2 = new Phrase("Rs. " + twoDecimalConverter.format((pricePerKg_double)), normalFont);
               PdfPCell malletotalcell2 = new PdfPCell(new Phrase(phrasemaletotal2));
                malletotalcell2.setBorder(Rectangle.NO_BORDER);
                malletotalcell2.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                malletotalcell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                malletotalcell2.setPaddingLeft(10);
                malletotalcell2.setPaddingRight(10);
                malletotalcell2.setPaddingBottom(5);
                malletotalcell2.setPaddingTop(10);
                phrasebody1_table.addCell(malletotalcell2);

                Phrase phrasefemaleQuantity1 = new Phrase(threeDecimalConverter.format(Double.parseDouble(femaleWeight)) + " Kg ( " + Modal_B2BOrderDetails.getTotalfemalequantity_Static() + " ) ", normalFont);
                PdfPCell femaleQuantitycell1 = new PdfPCell(new Phrase(phrasefemaleQuantity1));
                femaleQuantitycell1.setBorder(Rectangle.NO_BORDER);
                femaleQuantitycell1.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                femaleQuantitycell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                femaleQuantitycell1.setPaddingLeft(10);
                femaleQuantitycell1.setPaddingRight(10);
                femaleQuantitycell1.setPaddingBottom(5);
                femaleQuantitycell1.setPaddingTop(10);
                phrasebody1_table.addCell(femaleQuantitycell1);


                double total_femaleprice = 0;
                try {
                double weight_double = 0;
                weight_double = (Double.parseDouble(femaleWeight));

                    /*double pricePerKg = 0;
                    String pricePerKg_string = "0";
                    pricePerKg_string = Modal_B2BOrderDetails.getPriceperkg_Static();
                    pricePerKg_string = pricePerKg_string.replaceAll("[^\\d.]", "");
                    if (pricePerKg_string.equals("") || String.valueOf(pricePerKg_string).toUpperCase().equals("NULL")) {
                        pricePerKg_string = "0";
                    }
                    pricePerKg = Double.parseDouble(pricePerKg_string);



                //total_femaleprice = weight_double * pricePerKg_double;

                } catch (Exception e) {
                e.printStackTrace();
                }
                Phrase phrasefemaletotal = new Phrase("Rs. " + twoDecimalConverter.format((total_femaleprice)), normalFont);
                PdfPCell femaletotalcell = new PdfPCell(new Phrase(phrasefemaletotal));
                femaletotalcell.setBorder(Rectangle.NO_BORDER);
                femaletotalcell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                femaletotalcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                femaletotalcell.setPaddingLeft(10);
                femaletotalcell.setPaddingRight(10);
                femaletotalcell.setPaddingBottom(5);
                femaletotalcell.setPaddingTop(10);
                phrasebody1_table.addCell(femaletotalcell);

                }

                if(String.valueOf(Modal_B2BOrderDetails.getTotalfemalewithbabyquantity_Static()) .equals("") ||String.valueOf(Modal_B2BOrderDetails.getTotalfemalewithbabyquantity_Static()) .equals("0") || String.valueOf(Modal_B2BOrderDetails.getTotalfemalewithbabyquantity_Static()) .toUpperCase().equals("NULL") ){

                }
                else {

                String femalewithbabyWeight = Modal_B2BOrderDetails.getTotalfemalewithbabyweight_Static();
                femalewithbabyWeight = femalewithbabyWeight.replaceAll("[^\\d.]", "");
                if(femalewithbabyWeight.equals("")){
                femalewithbabyWeight ="0";
                }

                if(Double.parseDouble(femalewithbabyWeight) > 0) {

                Phrase femaleWithBabyLabel = new Phrase(" Female With Baby   ", itemNameFontLabel);
                PdfPCell femaleWIthBabyLabelcell = new PdfPCell(new Phrase(femaleWithBabyLabel));
                femaleWIthBabyLabelcell.setBorder(Rectangle.NO_BORDER);
                femaleWIthBabyLabelcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                femaleWIthBabyLabelcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                femaleWIthBabyLabelcell.setPaddingLeft(10);
                femaleWIthBabyLabelcell.setPaddingRight(20);
                femaleWIthBabyLabelcell.setPaddingBottom(5);
                femaleWIthBabyLabelcell.setPaddingTop(10);
                phrasebody1_table.addCell(femaleWIthBabyLabelcell);



                Phrase phrasemaletotal2 = new Phrase("Rs. " + twoDecimalConverter.format(pricePerKg_double), normalFont);
                PdfPCell malletotalcell2 = new PdfPCell(new Phrase(phrasemaletotal2));
                malletotalcell2.setBorder(Rectangle.NO_BORDER);
                malletotalcell2.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                malletotalcell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                malletotalcell2.setPaddingLeft(10);
                malletotalcell2.setPaddingRight(10);
                malletotalcell2.setPaddingBottom(5);
                malletotalcell2.setPaddingTop(10);
                phrasebody1_table.addCell(malletotalcell2);

                Phrase phrasefemaleQuantity2 = new Phrase(threeDecimalConverter.format(Double.parseDouble(femalewithbabyWeight)) + " Kg ( " + Modal_B2BOrderDetails.getTotalfemalewithbabyquantity_Static() + " ) ", normalFont);
                PdfPCell femaleQuantitycell2 = new PdfPCell(new Phrase(phrasefemaleQuantity2));
                femaleQuantitycell2.setBorder(Rectangle.NO_BORDER);
                femaleQuantitycell2.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                femaleQuantitycell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                femaleQuantitycell2.setPaddingLeft(10);
                femaleQuantitycell2.setPaddingRight(10);
                femaleQuantitycell2.setPaddingBottom(5);
                femaleQuantitycell2.setPaddingTop(10);
                phrasebody1_table.addCell(femaleQuantitycell2);


                double total_femalewithbabyprice = 0;
                try {
                double weight_double = 0;
                weight_double = (Double.parseDouble(femalewithbabyWeight));

                        /*(double pricePerKg = 0;
                        String pricePerKg_string = "0";
                        pricePerKg_string = Modal_B2BOrderDetails.getPriceperkg_Static();
                        pricePerKg_string = pricePerKg_string.replaceAll("[^\\d.]", "");
                        if (pricePerKg_string.equals("") || String.valueOf(pricePerKg_string).toUpperCase().equals("NULL")) {
                            pricePerKg_string = "0";
                        }
                        pricePerKg = Double.parseDouble(pricePerKg_string);


                total_femalewithbabyprice = weight_double * pricePerKg_double;

                } catch (Exception e) {
                e.printStackTrace();
                }

                Phrase phrasefemalewithbabytotal = new Phrase("Rs. " + twoDecimalConverter.format((total_femalewithbabyprice)), normalFont);
                PdfPCell femalewithbabytotalcell = new PdfPCell(new Phrase(phrasefemalewithbabytotal));
                femalewithbabytotalcell.setBorder(Rectangle.NO_BORDER);
                femalewithbabytotalcell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                femalewithbabytotalcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                femalewithbabytotalcell.setPaddingLeft(10);
                femalewithbabytotalcell.setPaddingRight(10);
                femalewithbabytotalcell.setPaddingBottom(5);
                femalewithbabytotalcell.setPaddingTop(10);
                phrasebody1_table.addCell(femalewithbabytotalcell);



                //Phrase femaleWithBabyQuantity = new Phrase(Modal_B2BOrderDetails.getTotalfemalewithbabyquantity_Static(), normalFont);
             //   PdfPCell femaleWithBabyQuantitycell = new PdfPCell(new Phrase(femaleWithBabyQuantity));
            ////    femaleWithBabyQuantitycell.setBorder(Rectangle.NO_BORDER);
              //  femaleWithBabyQuantitycell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
              //  femaleWithBabyQuantitycell.setVerticalAlignment(Element.ALIGN_MIDDLE);
               // femaleWithBabyQuantitycell.setPaddingLeft(10);
               // femaleWithBabyQuantitycell.setPaddingRight(10);
               /// femaleWithBabyQuantitycell.setPaddingBottom(5);
              //  femaleWithBabyQuantitycell.setPaddingTop(10);
            ///    phrasebody1_table.addCell(femaleWithBabyQuantitycell);


             //   String femalewithbabyWeight = Modal_B2BOrderDetails.getTotalfemalewithbabyweight_Static();
              //  femalewithbabyWeight = femalewithbabyWeight.replaceAll("[^\\d.]", "");
             / //  if(femalewithbabyWeight.equals("")){
            //        femalewithbabyWeight ="0";
            //    }

              //  Phrase femaleWithBabyWeight = new Phrase(threeDecimalConverter.format( Double.parseDouble(femalewithbabyWeight) )+ " Kg", normalFont);
              //  PdfPCell femaleWithBabyWeightcell = new PdfPCell(new Phrase(femaleWithBabyWeight));
              //  femaleWithBabyWeightcell.setBorder(Rectangle.NO_BORDER);
              //  femaleWithBabyWeightcell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
              //  femaleWithBabyWeightcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
              //  femaleWithBabyWeightcell.setPaddingLeft(10);
             //   femaleWithBabyWeightcell.setPaddingRight(10);
             //   femaleWithBabyWeightcell.setPaddingBottom(5);
            //    femaleWithBabyWeightcell.setPaddingTop(10);
              //  phrasebody1_table.addCell(femaleWithBabyWeightcell);

                 }


                }
                layoutDocument.add(phrasebody1_table);
                PdfPTable phrase_table1 = new PdfPTable(1);

                Phrase phraseEmptyLine3 = new Phrase("                  ");
                PdfPCell phraseEmptycell3 = new PdfPCell(phraseEmptyLine3);
                phraseEmptycell3.setBorder(Rectangle.NO_BORDER);
                phraseEmptycell3.setPaddingLeft(10);
                phraseEmptycell3.setBorderWidthBottom(01);
                phrase_table1.addCell(phraseEmptycell3);
                layoutDocument.add(phrase_table1);

                PdfPTable phrase_table2 = new PdfPTable(4);

                Phrase phraseTotalLabel = new Phrase( " Item Total ", itemNameFontLabel);
                PdfPCell TotalLabelcell = new PdfPCell(new Phrase(phraseTotalLabel));
                TotalLabelcell.setBorder(Rectangle.NO_BORDER);
                TotalLabelcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                TotalLabelcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                TotalLabelcell.setPaddingLeft(10);
                TotalLabelcell.setPaddingRight(20);
                TotalLabelcell.setPaddingTop(10);
                phrase_table2.addCell(TotalLabelcell);


                Phrase phraseTotalQuantity = new Phrase( "", normalFont);
                PdfPCell TotalQuantitycell = new PdfPCell(new Phrase(phraseTotalQuantity));
                TotalQuantitycell.setBorder(Rectangle.NO_BORDER);
                TotalQuantitycell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                TotalQuantitycell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                TotalQuantitycell.setPaddingTop(10);
                phrase_table2.addCell(TotalQuantitycell);



                String totalWeight = Modal_B2BOrderDetails.getTotalweight_Static();
                totalWeight = totalWeight.replaceAll("[^\\d.]", "");
                if(totalWeight.equals("")){
                totalWeight ="0";
                }
                Phrase phrasetotalWeight = new Phrase( threeDecimalConverter.format(Double.parseDouble(totalWeight)) +" Kg ( "+Modal_B2BOrderDetails.getTotalquantity_Static()+" ) ", normalFont);
                PdfPCell totalWeightcell = new PdfPCell(new Phrase(phrasetotalWeight));
                totalWeightcell.setBorder(Rectangle.NO_BORDER);
                totalWeightcell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                totalWeightcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                totalWeightcell.setPaddingLeft(10);
                totalWeightcell.setPaddingRight(10);
                totalWeightcell.setPaddingTop(10);
                phrase_table2.addCell(totalWeightcell);


                Phrase phrasetotal = new Phrase("Rs. " + twoDecimalConverter.format((totalPrice_doubleWithoutDiscount)), normalFont);
                PdfPCell femaletotalcell = new PdfPCell(new Phrase(phrasetotal));
                femaletotalcell.setBorder(Rectangle.NO_BORDER);
                femaletotalcell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                femaletotalcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                femaletotalcell.setPaddingLeft(10);
                femaletotalcell.setPaddingRight(10);
                femaletotalcell.setPaddingTop(10);
                phrase_table2.addCell(femaletotalcell);




                layoutDocument.add(phrase_table2);



                PdfPTable phrase_table3 = new PdfPTable(1);

                Phrase phraseEmptyLine4 = new Phrase("                  ");
                PdfPCell phraseEmptycell4 = new PdfPCell(phraseEmptyLine4);
                phraseEmptycell4.setBorder(Rectangle.NO_BORDER);
                phraseEmptycell4.setPaddingLeft(10);
                phraseEmptycell4.setBorderWidthBottom(01);
                phraseEmptycell4.setPaddingBottom(20);
                phrase_table3.addCell(phraseEmptycell4);
                layoutDocument.add(phrase_table3);



                PdfPTable phrasebody2_table = new PdfPTable(2);

          //  Phrase phraseNetTotal = new Phrase( "Price Per Kg :               ", normalFont);
           // PdfPCell netTotalcell = new PdfPCell(new Phrase(phraseNetTotal));
           // netTotalcell.setBorder(Rectangle.NO_BORDER);
          //  netTotalcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            //netTotalcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           /// netTotalcell.setPaddingLeft(10);
          //  netTotalcell.setPaddingRight(20);
          //  netTotalcell.setPaddingBottom(10);
           // phrasebody2_table.addCell(netTotalcell);

         //   Phrase phraseNetTotalValue = new Phrase( "Rs. "+ String.valueOf(twoDecimalConverter.format(pricePerKg_double)) , normalFont);
         //   PdfPCell netTotalcellValue = new PdfPCell(new Phrase(phraseNetTotalValue));
         ///   netTotalcellValue.setBorder(Rectangle.NO_BORDER);
       / //    netTotalcellValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
        //    netTotalcellValue.setVerticalAlignment(Element.ALIGN_MIDDLE);
       //     netTotalcellValue.setPaddingLeft(10);
        //    netTotalcellValue.setPaddingRight(40);
       //     netTotalcellValue.setPaddingBottom(10);
       //     phrasebody2_table.addCell(netTotalcellValue);




          ////  Phrase phraseTotalPrice = new Phrase( "Total Price (Weight * Price Per Kg) :           ", normalFont);
          //  PdfPCell TotalPricecell = new PdfPCell(new Phrase(phraseTotalPrice));
          //  TotalPricecell.setBorder(Rectangle.NO_BORDER);
          //  TotalPricecell.setHorizontalAlignment(Element.ALIGN_LEFT);
           // TotalPricecell.setVerticalAlignment(Element.ALIGN_MIDDLE);
          //  TotalPricecell.setPaddingLeft(10);
          //  TotalPricecell.setPaddingBottom(10);
        //    phrasebody2_table.addCell(TotalPricecell);

          //  Phrase phraseTotalPricevalue = new Phrase(  "Rs. "+String.valueOf(twoDecimalConverter.format(totalPrice_doubleWithoutDiscount)) , normalFont);
        //    PdfPCell TotalPricevaluecell = new PdfPCell(new Phrase(phraseTotalPricevalue));
         //   TotalPricevaluecell.setBorder(Rectangle.NO_BORDER);
        //    TotalPricevaluecell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        //    TotalPricevaluecell.setVerticalAlignment(Element.ALIGN_MIDDLE);
      //  //    TotalPricevaluecell.setPaddingLeft(10);
      //      TotalPricevaluecell.setPaddingRight(40);
         //   TotalPricevaluecell.setPaddingBottom(10);
           // phrasebody2_table.addCell(TotalPricevaluecell);
//



                if(discountAmount_double>0) {
                Phrase phrasediscountPrice = new Phrase(" DISCOUNT PRICE  :               ", itemNameFontLabel);
                PdfPCell discountPricecell = new PdfPCell(new Phrase(phrasediscountPrice));
                discountPricecell.setBorder(Rectangle.NO_BORDER);
                discountPricecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                discountPricecell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                discountPricecell.setPaddingLeft(10);
                discountPricecell.setPaddingRight(20);
                discountPricecell.setPaddingBottom(10);
                phrasebody2_table.addCell(discountPricecell);

                Phrase phrasediscountPricevalue = new Phrase("Rs. " + String.valueOf(twoDecimalConverter.format(discountAmount_double)), normalFont);
                PdfPCell discountPricevaluecell = new PdfPCell(new Phrase(phrasediscountPricevalue));
                discountPricevaluecell.setBorder(Rectangle.NO_BORDER);
                discountPricevaluecell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                discountPricevaluecell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                discountPricevaluecell.setPaddingLeft(10);
                discountPricevaluecell.setPaddingRight(40);
                discountPricevaluecell.setPaddingBottom(10);
                phrasebody2_table.addCell(discountPricevaluecell);
                }

                Phrase phraseFinalPrice = new Phrase( " FINAL PRICE  :               ",itemNameFontLabel);
                PdfPCell FinalPricecell = new PdfPCell(new Phrase(phraseFinalPrice));
                FinalPricecell.setBorder(Rectangle.NO_BORDER);
                FinalPricecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                FinalPricecell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                FinalPricecell.setPaddingLeft(10);
                FinalPricecell.setPaddingRight(20);
                FinalPricecell.setPaddingBottom(30);
                FinalPricecell.setPaddingTop(15);
                phrasebody2_table.addCell(FinalPricecell);


                Phrase phraseFinalPricevalue = new Phrase(  "    Rs. "+String.valueOf(twoDecimalConverter.format(totalPrice_double)) , normalFont);
                PdfPCell FinalPriceValuecell = new PdfPCell(new Phrase(phraseFinalPricevalue));
                FinalPriceValuecell.setBorder(Rectangle.NO_BORDER);
                FinalPriceValuecell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                FinalPriceValuecell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                FinalPriceValuecell.setPaddingLeft(10);
                FinalPriceValuecell.setPaddingRight(40);
                FinalPriceValuecell.setPaddingBottom(30);
                FinalPricecell.setPaddingTop(15);

                phrasebody2_table.addCell(FinalPriceValuecell);
                layoutDocument.add(phrasebody2_table);


                PdfPTable phrasebodythankyou_table = new PdfPTable(1);
                Phrase phraseFinalNote = new Phrase("Thank You for Choosing US !!!! ", normalFont);
                PdfPCell phraseFinalNotecell = new PdfPCell(phraseFinalNote);
                phraseFinalNotecell.setBorder(Rectangle.NO_BORDER);
                phraseFinalNotecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                phraseFinalNotecell.setVerticalAlignment(Element.ALIGN_CENTER);
                phraseFinalNotecell.setPaddingLeft(10);
                phraseFinalNotecell.setPaddingTop(10);
                phrasebodythankyou_table.addCell(phraseFinalNotecell);

                layoutDocument.add(phrasebodythankyou_table);






                neutralizeArray_and_OtherValues();

                // closeFragment();



                }
                catch (Exception e){
                e.printStackTrace();
                }



                }


     */

}