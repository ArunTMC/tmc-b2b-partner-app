package com.tmc.tmcb2bpartnerapp.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itextpdf.text.Document;
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
import com.tmc.tmcb2bpartnerapp.activity.DeliveryCenterDashboardScreen;
import com.tmc.tmcb2bpartnerapp.activity.GoatEarTagItemDetailsList;
import com.tmc.tmcb2bpartnerapp.adapter.Adapter_AutoComplete_RetailerMobileNo;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartItemDetails_BulkUpdate;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BInvoiceNoManager;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagDetails_BulkUpdate;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagTransaction;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartItemDetaillsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartItemDetails_BulkUpdateInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BInvoiceNoManagerInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderItemDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BRetailerDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.BarcodeScannerInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagDetails_BulkUpdateInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagTransactionInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagTransaction;
import com.tmc.tmcb2bpartnerapp.modal.Modal_Static_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_UpdatedB2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_UpdatedGoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BarcodeScannerScreen;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.TMCAlertDialogClass;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.Context.MODE_PRIVATE;
import static android.os.Build.VERSION.SDK_INT;
import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;
import static com.itextpdf.text.BaseColor.BLACK;
import static com.itextpdf.text.BaseColor.GRAY;
import static com.itextpdf.text.BaseColor.LIGHT_GRAY;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeliveryCenter_PlaceOrderScreen_SecondVersn#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeliveryCenter_PlaceOrderScreen_SecondVersn extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //String
    public static String  oldretailerGSTIN = "", oldpriceperKg ="" ,oldretaileraddress ="" , oldRetailerName = "", oldretailerMobileno ="" , oldRetailerKey ="", odeliveryCenterKey ="", orderid ="" ,invoiceno = "", deliveryCenterKey ="",deliveryCenterName ="",scannedBarcode ="" ,
            batchno ="" , retailerKey = "" , retailername = "" ,pricePerKg ="",
    orderplaceddate ="", retailermobileno ="",retailerGSTIN ="",paymentMode ="",oldpaymentMode ="" , updatedpaymentMode ="",usermobileno_string =""
            ,retaileraddress =""  ,gradename ="" , gradeprice ="", gender = "",discount_String ="";
    public String value_forFragment = "";
    String retailers_name = "";
    String retailers_mobileno = "";
    String retailers_address = "" ,gstin ="";



    //double
    double approx_Live_Weight_double = 0 , meatyeild_weight_double = 0 , parts_Weight_double = 0 , totalWeight_double = 0 , discountDouble = 0 ,
            totalPrice_double = 0 ,     pricewithOutdiscount_double = 0;
   static int  final_totalGoats =0;static double final_totalPriceWithOutDiscount = 0 , final_totalPriceWithOutDiscountWithFeedAmount =0, final_batchValue = 0, final_discountValue = 0 , final_totalPayment = 0 , final_totalWeight =0 ;




    //ArrayList
    public static ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList = new ArrayList<>();
    public static ArrayList<Modal_GoatEarTagDetails> earTagDetailsArrayList_WholeBatch = new ArrayList<>();



    //Hashmap
    public static HashMap<String, Modal_B2BCartItemDetails> earTagDetails_JSONFinalSalesHashMap = new HashMap<>();
    //   public static HashMap<String, JSONObject> earTagDetails_weightStringHashMap = new HashMap<>();
    public static ArrayList<String> earTagDetailsArrayList_String = new ArrayList<>();


    //Adapter
    Adapter_AutoComplete_RetailerMobileNo adapter_autoComplete_retailerMobileNo;


    //boolean
    public static  boolean  isRetailerSelected = false;
    boolean  isRetailerDetailsServiceCalled = false ;
    boolean isBarcodeScannerServiceCalled = false;
    boolean isCalledForPlaceNewOrder = false;
    boolean isB2BCartOrderTableServiceCalled = false;
    public static boolean isCartAlreadyCreated = false ,priceperKg_not_edited_byUser = true , ispaymentModeSelectedByuser = false ,isPricePerKgUpdated =false ,
            isRetailerUpdated = false ;
    boolean isB2BCartItemDetailsCalled = false;
    boolean isGoatEarTagDetailsTableServiceCalled = false;
    public boolean isTransactionSafe = true;
    public boolean isTransactionPending;
    boolean isOrderItemDetailsServiceCalled = false;
    boolean isOrderDetailsServiceCalled = false;
    boolean isPDF_FIle = false;
    boolean isGoatEarTagTransactionTableServiceCalled = false;
    boolean isB2BCartItemDetailsBulkUpdate = false;





    //interface
    B2BRetailerDetailsInterface callback_retailerDetailsInterface = null;
    public static BarcodeScannerInterface barcodeScannerInterface = null;
    B2BCartOrderDetailsInterface callback_b2bOrderDetails =null ;
    B2BCartItemDetaillsInterface callback_b2BCartItemDetaillsInterface = null;
    B2BInvoiceNoManagerInterface callback_invoiceManagerInterface = null;
    GoatEarTagDetailsInterface callback_GoatEarTagDetails = null;
    B2BOrderItemDetailsInterface callback_b2BOrderItemDetailsInterface ;
    B2BOrderDetailsInterface callback_b2BOrderDetailsInterface ;
    GoatEarTagDetails_BulkUpdateInterface goatEarTagDetailsBulkUpdateInterface;
    GoatEarTagTransactionInterface callback_GoatEarTagTransactionInterface = null;
    B2BCartItemDetails_BulkUpdateInterface callbackB2BCartItemDetails_bulkUpdateInterface;






    //Widgets
    public  static AutoCompleteTextView retailerMobileNo_edittext;
    public  static TextView retailerName_textView;
    public  static TextView retailerAddress_textView;
    static Button addItemInCart_Button ,viewItemInCart_Button ,generateBill_Button;
    static LinearLayout loadingPanel ,paymentMode_spinnerLayout ;
    public static LinearLayout loadingpanelmask ,orderItemDetails_Linearlayout;
    static LinearLayout back_IconLayout;
    static TextView finalGoatValue_textView, totalGoats_textView , totalValue_textView , batchValue_textView ,discountValue_textView , finalPayment_textView,
            finaltotalWeight_textView,totalFeedPriceTextview,finalFeedValue_textView,male_Qty_textview,female_Qty_textview,male_female_ratio_textview
            ,female_Percentage_textview,male_percentage_textview ,meatYieldWeightAvg_Textview ,approxLiveWeightAvg_Textview ;
    public static FrameLayout frameLayout_for_Fragment;
    CardView topCardView;
    EditText discountValue_editText,feedWeight_editText, feedPricePerKg_editText;


    //General
    public static Context mContext;
    Fragment mfragment;

    public static DeliveryCenter_PlaceOrderScreen_SecondVersn deliveryCenter_placeOrderScreen_secondVersn;

    public static Dialog show_retailerItemDetails_Dialog = null;

    static DecimalFormat threeDecimalConverter = new DecimalFormat(Constants.threeDecimalPattern);
    static DecimalFormat twoDecimalConverter = new DecimalFormat(Constants.twoDecimalPattern);
    private static int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;



    public static Dialog show_goatEarTagItemDetails_Dialog = null;
    // dialog's Component
    TextView barcodeNo_textView ,gradeName_textview ,gender_textview ,totalweight_textview ,totalPrice_textView ,selectedGoatStatus_textview,approxLiveWeight_textview
            ,meatyeild_textview,parts_textView,priceWithoutDiscount_textview,discount_textView;

    EditText barcode_editText,approxLiveWeight_EditText ,parts_editText,discount_edittext,meatyeild_edittext,gradeName_editText,totalPrice_edittext,priceWithoutDiscount_edittext;
    Button save_button;
    LinearLayout back_IconLayout_goatEarTagItemdialog;
    LinearLayout discount_price_layout_label;
    LinearLayout goatstatus_layout;
    static LinearLayout loadingpanelmask_in_dialog;
    static LinearLayout loadingPanel_in_dialog;

    RadioGroup genderRadioGroup ,goatstatusradiogrp ;
    RadioButton male_radioButton , female_radioButton,normal_goat_radio,dead_goat_radio,sick_goat_radio;
    public String selectedItemsStatus = "" , selectedGenderName ="" , selectedGradeName ="";
    // dialog's Component


    boolean isNoretailerAlertShown = false;




    public DeliveryCenter_PlaceOrderScreen_SecondVersn() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeliveryCenter_PlaceOrderScreen_SecondVersn.
     */
    // TODO: Rename and change types and number of parameters
    public static DeliveryCenter_PlaceOrderScreen_SecondVersn newInstance(String param1, String param2) {
        DeliveryCenter_PlaceOrderScreen_SecondVersn fragment = new DeliveryCenter_PlaceOrderScreen_SecondVersn();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity().getWindow().getContext();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try {
            BaseActivity.baseActivity.getDeviceName();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            if (BaseActivity.isDeviceIsMobilePhone) {
                // Inflate the layout for this fragment
                return inflater.inflate(R.layout.fragment_delivery_center__place_order_screen__second_versn, container, false);
            } else {

                // Inflate the layout for this fragment
                return inflater.inflate(R.layout.pos_fragment_delivery_center__place_order_screen__second_versn, container, false);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return inflater.inflate(R.layout.fragment_delivery_center__place_order_screen__second_versn, container, false);

        }



    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retailerMobileNo_edittext = view.findViewById(R.id.receiverMobileNo_edittext);
        retailerAddress_textView = view.findViewById(R.id.retailerAddress_textView);
        retailerName_textView = view.findViewById(R.id.retailerName_textView);
        addItemInCart_Button = view.findViewById(R.id.addItemInCart_Button);
        viewItemInCart_Button = view.findViewById(R.id.viewItemInCart_Button);
        generateBill_Button =  view.findViewById(R.id.checkoutFromCart_Button);
        loadingPanel = view.findViewById(R.id.loadingPanel);
        loadingpanelmask = view.findViewById(R.id.loadingpanelmask);
        frameLayout_for_Fragment = view.findViewById(R.id.retailerDetailsFrame);
        topCardView  = view.findViewById(R.id.topCardView);
        totalGoats_textView  = view.findViewById(R.id.totalGoats_textView);
        totalValue_textView  = view.findViewById(R.id.totalValue_textView);
        batchValue_textView  = view.findViewById(R.id.batchValue_textView);
        discountValue_textView  = view.findViewById(R.id.discountValue_textView);
        finalPayment_textView  = view.findViewById(R.id.finalPayment_textView);
        finaltotalWeight_textView  = view.findViewById(R.id.finaltotalWeight_textView);
        discountValue_editText = view.findViewById(R.id.discountValue_editText);
        totalFeedPriceTextview =  view.findViewById(R.id.totalFeedPriceTextview);
        finalFeedValue_textView =  view.findViewById(R.id.finalFeedValue_textView);

        feedWeight_editText =  view.findViewById(R.id.feedWeight_editText);
        feedPricePerKg_editText =  view.findViewById(R.id.feedPricePerKg_editText);
        female_Qty_textview = view.findViewById(R.id.female_Qty_textview);
        male_Qty_textview = view.findViewById(R.id.male_Qty_textview);
        female_Percentage_textview = view.findViewById(R.id.female_Percentage_textview);
        male_percentage_textview = view.findViewById(R.id.male_percentage_textview);
        approxLiveWeightAvg_Textview  = view.findViewById(R.id.approxLiveWeightAvg_Textview);
        meatYieldWeightAvg_Textview  = view.findViewById(R.id.meatYieldWeightAvg_Textview);
        finalGoatValue_textView = view.findViewById(R.id.finalGoatValue_textView);
        orderItemDetails_Linearlayout = view.findViewById(R.id.orderItemDetails_Linearlayout);

        //ClearArrayList
        earTagDetails_JSONFinalSalesHashMap.clear();
        orderItemDetails_Linearlayout.setVisibility(View.GONE);
        generateBill_Button.setVisibility(View.GONE);
        viewItemInCart_Button.setVisibility(View.GONE);



        SharedPreferences sh1 = requireActivity(). getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);

        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");

        SharedPreferences sh = getActivity().getSharedPreferences("LoginData",MODE_PRIVATE);
        usermobileno_string = sh.getString("UserMobileNumber","");



        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

         show_goatEarTagItemDetails_Dialog = new Dialog(mContext,android.R.style.Theme_DeviceDefault_Light_NoActionBar_TranslucentDecor);



        Create_and_SharePdf(true);
        deliveryCenter_placeOrderScreen_secondVersn = this;

        Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallGETMethod, "", false);


        if(DatabaseArrayList_PojoClass.retailerDetailsArrayList.size() == 0){
            try {
                call_and_init_B2BRetailerDetailsService(Constants.CallGETListMethod, false,"");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            retailerDetailsArrayList = DatabaseArrayList_PojoClass.retailerDetailsArrayList;
            setAdapterForRetailerDetails();
        }



        loadingpanelmask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Please Wait", Toast.LENGTH_SHORT).show();
            }
        });

        generateBill_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(earTagDetailsArrayList_String.size()>0) {
                    new TMCAlertDialogClass(requireActivity(), R.string.app_name, R.string.PleaseConfirmPlaceOrder,
                            R.string.Yes_Text, R.string.No_Text,
                            new TMCAlertDialogClass.AlertListener() {
                                @Override
                                public void onYes() {

                                    try {
                                        showProgressBar(true);
                                        orderplaceddate = DateParser.getDate_and_time_newFormat();
                                        orderid = String.valueOf(orderid);
                                        batchno = String.valueOf(batchno);
                                        invoiceno = String.valueOf(invoiceno);

                                        call_and_init_B2BOrderItemDetailsService(Constants.CallADDMethod);
                                        Initialize_and_ExecuteB2BOrderDetails(Constants.CallADDMethod);
                                        Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallDELETEMethod, "", false);
                                        call_and_init_GoatEarTagDetails_BulkUpdate(Constants.CallUPDATEMethod);
                                        Initialize_and_ExecuteB2BCartItemDetails_BulkUpdate(Constants.CallDELETEMethod);


                                       // Create_and_SharePdf(false);


                                        //  UpdateDataInSharedPreference();
                                        //   ((BillingScreen) getActivity()).Create_and_SharePdf();

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }

                                @Override
                                public void onNo() {


                                }
                            });
                }
                else{
                    AlertDialogClass.showDialog(requireActivity(), R.string.PleaseAddGoatItemBrforePlaceOrder);

                }
            }
        });



        viewItemInCart_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, GoatEarTagItemDetailsList.class);
                i.putExtra("TaskToDo", "ItemInCart_SecondVersn");
                i.putExtra("batchno", batchno);
                i.putExtra("orderid", orderid);
                i.putExtra("CalledFrom", getString(R.string.placedOrder_Details_Screen_SecondVersion));
                startActivity(i);
            }
        });

        addItemInCart_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRetailerSelected){
                   new  Modal_Static_GoatEarTagDetails();
                    if (earTagDetails_JSONFinalSalesHashMap.size() == 0) {
                       Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallGETMethod, "",true);

                    }
                    else{

                        if(Modal_B2BCartOrderDetails.getOrderid().equals("") ){


                            Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallADDMethod,"", false);
                        }
                        try{
                            //Initialize_and_StartBarcodeScanner(getString(R.string.scannerCalled_to_FetchData) ,"",true);
                            ShowGoatItemDetailsDialog("",true);



                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }


                }
                else{
                    AlertDialogClass.showDialog(requireActivity(), R.string.Please_Select_Retailer_toAddItem_Instruction);

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

                                 AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWhendiscountisgreaterthanpriceisZeroAlert);

                             }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        return false;
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
                        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        Objects.requireNonNull(imm).hideSoftInputFromWindow(retailerMobileNo_edittext.getWindowToken(), 0);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                     if (isCartAlreadyCreated) {
                         Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallUPDATEMethod, getString(R.string.retailername), false);

                     }
                     else{
                         Initialize_And_Process_InvoiceNoManager("GENERATE",true,false);

                     }

                    /*
                     new TMCAlertDialogClass(requireActivity(), R.string.app_name, R.string.PleaseConfirmUpdateRetailerName,
                            R.string.Yes_Text, R.string.No_Text,
                            new TMCAlertDialogClass.AlertListener() {
                                @Override
                                public void onYes() {

                                        if (isCartAlreadyCreated) {
                                            Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallUPDATEMethod, getString(R.string.retailername), false);

                                        }
                                        else{
                                            Initialize_And_Process_InvoiceNoManager("GENERATE",true,false);

                                        }



                                }

                                @Override
                                public void onNo() {
                                    retailerMobileNo_edittext.setText(oldretailerMobileno);
                                    retailerName_textView.setText(String.valueOf(oldRetailerName));
                                    retailerAddress_textView.setText(String.valueOf(oldretaileraddress));
                                    isRetailerUpdated = true;
                                    retailerKey = oldRetailerKey;
                                    retailername = oldRetailerName;
                                    retailermobileno = oldretailerMobileno;
                                    retaileraddress = oldretaileraddress;
                                    retailerGSTIN = oldretailerGSTIN;
                                    retailerKey = oldRetailerKey;
                                }
                            });


                     */
                }
                 else if(valuetoupdate.equals(getString(R.string.feedvalue))){
                     if (isCartAlreadyCreated) {
                         Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallUPDATEMethod, getString(R.string.feedvalue), false);

                     }
                     else{
                         Initialize_And_Process_InvoiceNoManager("GENERATE",true,false);

                     }

                 }
                 else if(valuetoupdate.equals(getString(R.string.discount))){
                     if (isCartAlreadyCreated) {
                         Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallUPDATEMethod, getString(R.string.discount), false);

                     }
                     else{
                         Initialize_And_Process_InvoiceNoManager("GENERATE",true,false);

                     }

                 }


          //  }
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }


    private void Create_and_SharePdf(boolean isJustNeedTOAskPermission) {

        if(isJustNeedTOAskPermission){
            try{
                if (SDK_INT >= Build.VERSION_CODES.R) {

                    if (Environment.isExternalStorageManager()) {

                    } else {
                        try {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                            intent.addCategory("android.intent.category.DEFAULT");
                            intent.setData(Uri.parse(String.format("package:%s", requireActivity().getPackageName())));
                            startActivityForResult(intent, 2296);
                        } catch (Exception e) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                            startActivityForResult(intent, 2296);
                        }
                    }

                }
                else {


                    int writeExternalStoragePermission = ContextCompat.checkSelfPermission(mContext, WRITE_EXTERNAL_STORAGE);
                    //Log.d("ExportInvoiceActivity", "writeExternalStoragePermission "+writeExternalStoragePermission);
                    // If do not grant write external storage permission.
                    if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                        // Request user to grant write external storage permission.
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{WRITE_EXTERNAL_STORAGE},
                                REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
                    } else {

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
                            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                            intent.addCategory("android.intent.category.DEFAULT");
                            intent.setData(Uri.parse(String.format("package:%s", requireActivity().getPackageName())));
                            startActivityForResult(intent, 2296);
                        } catch (Exception e) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                            startActivityForResult(intent, 2296);
                        }
                    }

                }
                else {


                    int writeExternalStoragePermission = ContextCompat.checkSelfPermission(mContext, WRITE_EXTERNAL_STORAGE);
                    //Log.d("ExportInvoiceActivity", "writeExternalStoragePermission "+writeExternalStoragePermission);
                    // If do not grant write external storage permission.
                    if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                        // Request user to grant write external storage permission.
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{WRITE_EXTERNAL_STORAGE},
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
        PdfPTable wholePDFContentWithOut_Outline_table = new PdfPTable(1);
        PdfPTable billtimeDetails_table = new PdfPTable(1);
        try {

            Phrase phrasebilltimeDetails = new Phrase(" DATE : "+DateParser.getDate_newFormat()+"      TIME : "+DateParser.getTime_newFormat(), valueFont_8);
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
           PdfPCell addBorder_billTimeDetails = new PdfPCell(billtimeDetails_table);
            addBorder_billTimeDetails.setBorder(Rectangle.NO_BORDER);
            addBorder_billTimeDetails.setPaddingTop(5);
            addBorder_billTimeDetails.setBorderWidthBottom(01);
            addBorder_billTimeDetails.setBorderColor(GRAY);


            wholePDFContentWithOut_Outline_table.addCell(addBorder_billTimeDetails);

        } catch (Exception e) {
            e.printStackTrace();
        }

        PdfPTable Whole_Warehouse_and_RetailerDetails_table = new PdfPTable(new float[] { 35, 65 });

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

                Phrase phrasecompanyAddressDetails = new Phrase("(Unit of Culinary Triangle Private Ltd)\n \n Old No 4, New No 50, \n Kumaraswamy Street, Lakshmipuram, \n Chromepet, Chennai – 44 , India. \n GSTIN 33AAJCC0055D1Z9", valueFont_8);

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





            PdfPTable Whole_SupplerDetails_table = new PdfPTable(new float[] { 30,70  });
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


                        Phrase phrasTotalTextTitle = new Phrase(String.valueOf(String.valueOf(finalGoatValue_textView.getText().toString())), valueFont_8);

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
                        Phrase phrasBatchpriceTextTitle = new Phrase(String.valueOf(text_feedpriceperkg), valueFont_8);

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



                        Phrase phrasTotalTextTitle = new Phrase(String.valueOf(finalfeedprice_double), valueFont_8);

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

                        Phrase phrasetotalDetailsTitle = new Phrase(String.valueOf(totalValue_textView.getText().toString()), valueFont_10);

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

                        Phrase phrasetotalDetailsTitle = new Phrase(String.valueOf(final_discountValue), valueFont_10);

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

                    Phrase phrasetotalDetailsTitle = new Phrase(String.valueOf(final_totalPayment), valueFont_10);

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





        try {
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



        resetAlltheValuesAndArrays();



    }

    private void addItemRowsInOldPDFFormat(Document layoutDocument) {




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

        Font itemNameFontBold= new Font(Font.FontFamily.TIMES_ROMAN, 13,
                Font.BOLD);
        Font itemNameFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
                Font.NORMAL);

        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 13,
                Font.NORMAL);

        Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 9,
                Font.NORMAL);
        RoundRectangle roundRectange = new RoundRectangle();
        try {
            PdfPTable wholePDFWithBorder_table = new PdfPTable(1);


            PdfPTable wholePDFWithOutBorder_table = new PdfPTable(1);
            try {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
            PdfPTable companyDetails_table = new PdfPTable(1);

            try {


                Phrase phrasecompanyDetailsTitle = new Phrase(" The Meat Chop (Unit of Culinary Triangle Pvt Ltd) ", subtitleFont);

                PdfPCell phrasecompanyDetailsTitlecell = new PdfPCell(phrasecompanyDetailsTitle);
                phrasecompanyDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                phrasecompanyDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasecompanyDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasecompanyDetailsTitlecell.setPaddingLeft(10);
                phrasecompanyDetailsTitlecell.setPaddingBottom(4);
                companyDetails_table.addCell(phrasecompanyDetailsTitlecell);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                Phrase phrasecompanyAddressDetails = new Phrase(" Old No 4, New No 50, Kumaraswamy Street, \n Lakshmipuram, Chromepet, Chennai – 44 , \n India. \n GSTIN 33AAJCC0055D1Z9", subtitleFontsmall);

                PdfPCell phrasecompanyAddressDetailscell = new PdfPCell(phrasecompanyAddressDetails);
                phrasecompanyAddressDetailscell.setBorder(Rectangle.NO_BORDER);
                phrasecompanyAddressDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasecompanyAddressDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasecompanyAddressDetailscell.setPaddingLeft(10);
                phrasecompanyAddressDetailscell.setPaddingBottom(4);
                companyDetails_table.addCell(phrasecompanyAddressDetailscell);

            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                PdfPCell companyDetailscell = new PdfPCell(companyDetails_table);
                companyDetailscell.setBorder(Rectangle.NO_BORDER);
                companyDetailscell.setPadding(8);
                companyDetailscell.setBorderWidthBottom(01);
                companyDetailscell.setBorderColor(LIGHT_GRAY);
                wholePDFWithOutBorder_table.addCell(companyDetailscell);

            } catch (Exception e) {
                e.printStackTrace();
            }

            PdfPTable billDetails_And_retailerDetailstable = new PdfPTable(2);
            PdfPTable billDetails_table = new PdfPTable(2);
            PdfPTable retailerDetailstable = new PdfPTable(1);
            try {


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


                Phrase phraseinvoiceDetails = new Phrase(": INV-" + invoiceno, itemNameFont1_bold);

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


                Phrase phraseorderidDetails = new Phrase(": " + orderid, itemNameFont1_bold);

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


                Phrase phrasedateDetails = new Phrase(": " + DateParser.getDate_newFormat(), itemNameFont1_bold);

                PdfPCell phrasedateDetailscell = new PdfPCell(phrasedateDetails);
                phrasedateDetailscell.setBorder(Rectangle.NO_BORDER);
                phrasedateDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasedateDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasedateDetailscell.setPaddingBottom(6);
                phrasedateDetailscell.setPaddingLeft(10);

                billDetails_table.addCell(phrasedateDetailscell);




                try {
                    PdfPCell billDetailscell = new PdfPCell(billDetails_table);
                    billDetailscell.setBorder(Rectangle.NO_BORDER);
                    billDetailscell.setPadding(8);
                    billDetailscell.setBorderWidthRight(01);
                    billDetailscell.setBorderColor(LIGHT_GRAY);
                    billDetails_And_retailerDetailstable.addCell(billDetailscell);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {

                Phrase phrasebilldetailssupplierDetails = new Phrase("Bill To ", subtitleFont);
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
                if (!retaileraddress.equals("") && !String.valueOf(retaileraddress).toUpperCase().equals("NULL")) {

                    Phrase phraseretailerAddressDetails = new Phrase(retaileraddress, itemNameFont1);

                    PdfPCell phraseretailerAddressDetailscell = new PdfPCell(phraseretailerAddressDetails);
                    phraseretailerAddressDetailscell.setBorder(Rectangle.NO_BORDER);
                    phraseretailerAddressDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phraseretailerAddressDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phrasebilldetailssupplierDetailscell.setPaddingLeft(6);
                    phraseretailerAddressDetailscell.setPaddingBottom(2);
                    retailerDetailstable.addCell(phraseretailerAddressDetailscell);

                }

                retailermobileno = retailermobileno.replace("+91", "");

                Phrase phraseretailerNoLabelDetails = new Phrase("Mobile: " + retailermobileno, itemNameFont1);
                PdfPCell phraseretailerNoLabelDetailscell = new PdfPCell(phraseretailerNoLabelDetails);
                phraseretailerNoLabelDetailscell.setBorder(Rectangle.NO_BORDER);
                phraseretailerNoLabelDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phraseretailerNoLabelDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasebilldetailssupplierDetailscell.setPaddingLeft(6);

                phraseretailerNoLabelDetailscell.setPaddingBottom(4);
                retailerDetailstable.addCell(phraseretailerNoLabelDetailscell);

                if (!retailerGSTIN.equals("") && !String.valueOf(retailerGSTIN).toUpperCase().equals("NULL")) {
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
                try {
                    PdfPCell retailerDetailscell = new PdfPCell(retailerDetailstable);
                    retailerDetailscell.setBorder(Rectangle.NO_BORDER);
                    retailerDetailscell.setPadding(8);
                    billDetails_And_retailerDetailstable.addCell(retailerDetailscell);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                PdfPCell billDetails_And_retailerDetailscell = new PdfPCell(billDetails_And_retailerDetailstable);
                billDetails_And_retailerDetailscell.setBorder(Rectangle.NO_BORDER);
                billDetails_And_retailerDetailscell.setPadding(8);
                billDetails_And_retailerDetailscell.setBorderWidthBottom(01);
                billDetails_And_retailerDetailscell.setPaddingBottom(20);
                billDetails_And_retailerDetailscell.setBorderColor(LIGHT_GRAY);
                wholePDFWithOutBorder_table.addCell(billDetails_And_retailerDetailscell);

            } catch (Exception e) {
                e.printStackTrace();
            }





            PdfPTable finalSalesBreakup_table = new PdfPTable(2);
            PdfPTable weight_qty_table = new PdfPTable(1);
            PdfPTable price_table = new PdfPTable(1);

              Phrase phraseNo_of_goatsLabel = new Phrase("       ", itemNameFontBold);

          //  Phrase phraseNo_of_goatsLabel = new Phrase(" No.of.Goats    :  "+String.valueOf(final_totalGoats), itemNameFontBold);
            PdfPCell phraseNo_of_Labelcell = new PdfPCell(phraseNo_of_goatsLabel);
           phraseNo_of_Labelcell.setBorder(Rectangle.NO_BORDER);
           phraseNo_of_Labelcell.setHorizontalAlignment(Element.ALIGN_LEFT);
           phraseNo_of_Labelcell.setVerticalAlignment(Element.ALIGN_LEFT);
           phraseNo_of_Labelcell.setPaddingBottom(9);
            weight_qty_table.addCell(phraseNo_of_Labelcell);


          /*  Phrase phraseWeightLabel = new Phrase(" Weight            :  "+String.valueOf(threeDecimalConverter.format(final_totalWeight))+" Kg", itemNameFontBold);
            PdfPCell phraseWeightLabelcell = new PdfPCell(phraseWeightLabel);
            phraseWeightLabelcell.setBorder(Rectangle.NO_BORDER);
            phraseWeightLabelcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseWeightLabelcell.setVerticalAlignment(Element.ALIGN_LEFT);
            phraseWeightLabelcell.setPaddingBottom(9);
            weight_qty_table.addCell(phraseWeightLabelcell);


           */



           // Phrase phrasesSubTotallabelDetails = new Phrase("SubTotal         :  "+String.valueOf(twoDecimalConverter.format(final_totalPriceWithOutDiscount)+" Rs"), itemNameFontBold);
            Phrase phrasesSubTotallabelDetails = new Phrase(" No.of.Goats    :  "+String.valueOf(final_totalGoats), itemNameFontBold);
            PdfPCell phraseSubTotalLabelDetailscell = new PdfPCell(phrasesSubTotallabelDetails);
            phraseSubTotalLabelDetailscell.setBorder(Rectangle.NO_BORDER);
            phraseSubTotalLabelDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseSubTotalLabelDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
            phraseSubTotalLabelDetailscell.setPaddingLeft(6);
            phraseSubTotalLabelDetailscell.setPaddingBottom(9);
            price_table.addCell(phraseSubTotalLabelDetailscell);



            Phrase phrasesDiscountlabelDetails = new Phrase("Discount          :  "+String.valueOf(twoDecimalConverter.format(final_discountValue)+" Rs"), itemNameFontBold);
            PdfPCell phraseDiscountLabelDetailscell = new PdfPCell(phrasesDiscountlabelDetails);
            phraseDiscountLabelDetailscell.setBorder(Rectangle.NO_BORDER);
            phraseDiscountLabelDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseDiscountLabelDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
            phraseDiscountLabelDetailscell.setPaddingLeft(6);
            phraseDiscountLabelDetailscell.setPaddingBottom(9);
            price_table.addCell(phraseDiscountLabelDetailscell);



            Phrase phrasesTotallabelDetails = new Phrase("Total                :  "+String.valueOf(twoDecimalConverter.format(final_totalPayment)+" Rs"), itemNameFontBold);
            PdfPCell phraseTotalLabelDetailscell = new PdfPCell(phrasesTotallabelDetails);
            phraseTotalLabelDetailscell.setBorder(Rectangle.NO_BORDER);
            phraseTotalLabelDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseTotalLabelDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
            phraseTotalLabelDetailscell.setPaddingLeft(6);
            phraseTotalLabelDetailscell.setPaddingBottom(9);
            price_table.addCell(phraseTotalLabelDetailscell);




            /*Phrase phraseCgstDetails = new Phrase(" CGST ( 0 % ) :                  0.00", subtitleFont);
            PdfPCell phraseCgstDetailscell = new PdfPCell(phraseCgstDetails);
            phraseCgstDetailscell.setBorder(Rectangle.NO_BORDER);
            phraseCgstDetailscell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            phraseCgstDetailscell.setVerticalAlignment(Element.ALIGN_RIGHT);
            phraseCgstDetailscell.setPaddingLeft(10);
            phraseCgstDetailscell.setPaddingBottom(6);
            finalSalesBreakup_table.addCell(phraseCgstDetailscell);


            Phrase phraseSgstDetails = new Phrase(" SGST ( 0 % ) :                  0.00 ", subtitleFont);
            PdfPCell phraseSgstDetailscell = new PdfPCell(phraseSgstDetails);
            phraseSgstDetailscell.setBorder(Rectangle.NO_BORDER);
            phraseSgstDetailscell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            phraseSgstDetailscell.setVerticalAlignment(Element.ALIGN_RIGHT);
            phraseSgstDetailscell.setPaddingLeft(10);
            phraseSgstDetailscell.setPaddingBottom(6);
            finalSalesBreakup_table.addCell(phraseSgstDetailscell);


             */


            /*
            String finalTotalGoat_String  = "";
            finalTotalGoat_String = String.valueOf(final_totalGoats);
            if(finalTotalGoat_String.length()==1){
                finalTotalGoat_String = "          "+finalTotalGoat_String;
            }
            else  if(finalTotalGoat_String.length()==2){
                finalTotalGoat_String = "         "+finalTotalGoat_String;

            }
            else  if(finalTotalGoat_String.length()==3){
                finalTotalGoat_String = "        "+finalTotalGoat_String;

            }
            else if(finalTotalGoat_String.length()==4){
                finalTotalGoat_String = "       "+finalTotalGoat_String;

            }
            else if(finalTotalGoat_String.length()==5){
                finalTotalGoat_String = "      "+finalTotalGoat_String;

            }
            else if(finalTotalGoat_String.length()==6){
                finalTotalGoat_String = "     "+finalTotalGoat_String;

            }
            Phrase phraseTotalGoatsDetails = new Phrase(" Total No.of Goats :             " + String.valueOf(finalTotalGoat_String), subtitleFont);
            PdfPCell phraseTotalGoatsDetailscell = new PdfPCell(phraseTotalGoatsDetails);
            phraseTotalGoatsDetailscell.setBorder(Rectangle.NO_BORDER);
            phraseTotalGoatsDetailscell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            phraseTotalGoatsDetailscell.setVerticalAlignment(Element.ALIGN_RIGHT);
            phraseTotalGoatsDetailscell.setPaddingLeft(10);
            phraseTotalGoatsDetailscell.setPaddingBottom(15);
            finalSalesBreakup_table.addCell(phraseTotalGoatsDetailscell);

            String finalTotalWeight_String  = "";
            finalTotalWeight_String = String.valueOf(threeDecimalConverter.format(final_totalWeight))+" Kg";
            if(finalTotalWeight_String.length()==1){
                finalTotalWeight_String = "          "+finalTotalWeight_String;
            }
            else  if(finalTotalWeight_String.length()==2){
                finalTotalWeight_String = "         "+finalTotalWeight_String;

            }
            else  if(finalTotalWeight_String.length()==3){
                finalTotalWeight_String = "        "+finalTotalWeight_String;

            }
            else if(finalTotalWeight_String.length()==4){
                finalTotalWeight_String = "       "+finalTotalWeight_String;

            }
            else if(finalTotalWeight_String.length()==5){
                finalTotalWeight_String = "      "+finalTotalWeight_String;

            }
            else if(finalTotalWeight_String.length()==6){
                finalTotalWeight_String = "     "+finalTotalWeight_String;

            }

            Phrase phrasetotalWeightDetails = new Phrase(" Total Weight :                  " + finalTotalWeight_String, subtitleFont);
            PdfPCell phrasetotalWeightDetailscell = new PdfPCell(phrasetotalWeightDetails);
            phrasetotalWeightDetailscell.setBorder(Rectangle.NO_BORDER);
            phrasetotalWeightDetailscell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            phrasetotalWeightDetailscell.setVerticalAlignment(Element.ALIGN_RIGHT);
            phrasetotalWeightDetailscell.setPaddingLeft(10);
            phrasetotalWeightDetailscell.setPaddingBottom(15);
            finalSalesBreakup_table.addCell(phrasetotalWeightDetailscell);




            String finalTotalPrice_String  = "";
            finalTotalPrice_String = String.valueOf(twoDecimalConverter.format(final_totalPriceWithOutDiscount));
            if(finalTotalPrice_String.length()==1){
                finalTotalPrice_String = "          "+finalTotalPrice_String;
            }
            else  if(finalTotalPrice_String.length()==2){
                finalTotalPrice_String = "         "+finalTotalPrice_String;

            }
            else  if(finalTotalPrice_String.length()==3){
                finalTotalPrice_String = "        "+finalTotalPrice_String;

            }
            else if(finalTotalPrice_String.length()==4){
                finalTotalPrice_String = "       "+finalTotalPrice_String;

            }
            else if(finalTotalPrice_String.length()==5){
                finalTotalPrice_String = "      "+finalTotalPrice_String;

            }
            else if(finalTotalPrice_String.length()==6){
                finalTotalPrice_String = "     "+finalTotalPrice_String;

            }

            Phrase phrasetotalDetails = new Phrase(" SubTotal :                  Rs. " + finalTotalPrice_String, subtitleFont);
            PdfPCell phrasetotalDetailscell = new PdfPCell(phrasetotalDetails);
            phrasetotalDetailscell.setBorder(Rectangle.NO_BORDER);
            phrasetotalDetailscell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            phrasetotalDetailscell.setVerticalAlignment(Element.ALIGN_RIGHT);
            phrasetotalDetailscell.setPaddingLeft(10);
            phrasetotalDetailscell.setPaddingBottom(15);
            finalSalesBreakup_table.addCell(phrasetotalDetailscell);






            String finalDiscount_String  = "";
            finalDiscount_String = String.valueOf(twoDecimalConverter.format(final_discountValue));
            if(finalDiscount_String.length()==1){
                finalDiscount_String = "          "+finalDiscount_String;
            }
            else  if(finalDiscount_String.length()==2){
                finalDiscount_String = "         "+finalDiscount_String;

            }
            else  if(finalDiscount_String.length()==3){
                finalDiscount_String = "        "+finalDiscount_String;

            }
            else if(finalDiscount_String.length()==4){
                finalDiscount_String = "       "+finalDiscount_String;

            }
            else if(finalDiscount_String.length()==5){
                finalDiscount_String = "      "+finalDiscount_String;

            }
            else if(finalDiscount_String.length()==6){
                finalDiscount_String = "     "+finalDiscount_String;

            }



            Phrase phraseDiscounttotalDetails = new Phrase(" Discount :                   Rs. " + finalDiscount_String, subtitleFont);
            PdfPCell phraseDiscounttotalDetailscell = new PdfPCell(phraseDiscounttotalDetails);
            phraseDiscounttotalDetailscell.setBorder(Rectangle.NO_BORDER);
            phraseDiscounttotalDetailscell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            phraseDiscounttotalDetailscell.setVerticalAlignment(Element.ALIGN_RIGHT);
            phraseDiscounttotalDetailscell.setPaddingLeft(10);
            phraseDiscounttotalDetailscell.setPaddingBottom(15);
            finalSalesBreakup_table.addCell(phraseDiscounttotalDetailscell);






            String finalTotalPayment_String  = "";
            finalTotalPayment_String = String.valueOf(twoDecimalConverter.format(final_totalPayment));
            if(finalTotalPayment_String.length()==1){
                finalTotalPayment_String = "          "+finalTotalPayment_String;
            }
            else  if(finalTotalPayment_String.length()==2){
                finalTotalPayment_String = "         "+finalTotalPayment_String;

            }
            else  if(finalTotalPayment_String.length()==3){
                finalTotalPayment_String = "        "+finalTotalPayment_String;

            }
            else if(finalTotalPayment_String.length()==4){
                finalTotalPayment_String = "       "+finalTotalPayment_String;

            }
            else if(finalTotalPayment_String.length()==5){
                finalTotalPayment_String = "      "+finalTotalPayment_String;

            }
            else if(finalTotalPayment_String.length()==6){
                finalTotalPayment_String = "     "+finalTotalPayment_String;

            }



            Phrase phraseFinalPaymenttotalDetails = new Phrase(" Final Payment :             Rs. " +finalTotalPayment_String , subtitleFont);
            PdfPCell phraseFinalPaymenttotalDetailscell = new PdfPCell(phraseFinalPaymenttotalDetails);
            phraseFinalPaymenttotalDetailscell.setBorder(Rectangle.NO_BORDER);
            phraseFinalPaymenttotalDetailscell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            phraseFinalPaymenttotalDetailscell.setVerticalAlignment(Element.ALIGN_RIGHT);
            phraseFinalPaymenttotalDetailscell.setPaddingLeft(10);
            phraseFinalPaymenttotalDetailscell.setPaddingBottom(15);
            finalSalesBreakup_table.addCell(phraseFinalPaymenttotalDetailscell);

            Phrase phrasefinalnotesDetails = new Phrase(" Thanks For Your Business ", subtitleFontsmall);
            PdfPCell phrasefinalnotesDetailscell = new PdfPCell(phrasefinalnotesDetails);
            phrasefinalnotesDetailscell.setBorder(Rectangle.NO_BORDER);
            phrasefinalnotesDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
            phrasefinalnotesDetailscell.setVerticalAlignment(Element.ALIGN_CENTER);
            phrasefinalnotesDetailscell.setPaddingLeft(10);
            phrasefinalnotesDetailscell.setPaddingTop(20);
            phrasefinalnotesDetailscell.setPaddingBottom(40);
            finalSalesBreakup_table.addCell(phrasefinalnotesDetailscell);


            try {
                PdfPCell finalSalesBreakup_tablecell = new PdfPCell(finalSalesBreakup_table);
                finalSalesBreakup_tablecell.setBorder(Rectangle.NO_BORDER);
                finalSalesBreakup_tablecell.setPadding(8);
                finalSalesBreakup_tablecell.setBorderColor(LIGHT_GRAY);
                wholePDFWithOutBorder_table.addCell(finalSalesBreakup_tablecell);

            } catch (Exception e) {
                e.printStackTrace();
            }


             */




            try {
                PdfPCell weight_qty_cell = new PdfPCell(weight_qty_table);
                weight_qty_cell.setBorder(Rectangle.NO_BORDER);
                weight_qty_cell.setPadding(8);
                finalSalesBreakup_table.addCell(weight_qty_cell);

            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                PdfPCell price_tablecell = new PdfPCell(price_table);
                price_tablecell.setBorder(Rectangle.NO_BORDER);
                price_tablecell.setPadding(8);
                finalSalesBreakup_table.addCell(price_tablecell);

            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                PdfPCell finalSalesBreakup_tablecell = new PdfPCell(finalSalesBreakup_table);
                finalSalesBreakup_tablecell.setBorder(Rectangle.NO_BORDER);
                finalSalesBreakup_tablecell.setPadding(8);
                finalSalesBreakup_tablecell.setBorderColor(LIGHT_GRAY);
                wholePDFWithOutBorder_table.addCell(finalSalesBreakup_tablecell);

            } catch (Exception e) {
                e.printStackTrace();
            }


            try{
                Phrase phrasefinalnotesDetails = new Phrase(" Thanks For Your Business ", subtitleFontsmall);
                PdfPCell phrasefinalnotesDetailscell = new PdfPCell(phrasefinalnotesDetails);
                phrasefinalnotesDetailscell.setBorder(Rectangle.NO_BORDER);
                phrasefinalnotesDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                phrasefinalnotesDetailscell.setVerticalAlignment(Element.ALIGN_CENTER);
                phrasefinalnotesDetailscell.setPaddingTop(20);
                phrasefinalnotesDetailscell.setPaddingBottom(40);
                wholePDFWithOutBorder_table.addCell(phrasefinalnotesDetailscell);

            }
            catch (Exception e){
                e.printStackTrace();
            }


            //FINAL
            try {
                PdfPCell wholePDFWithOutBordercell = new PdfPCell(wholePDFWithOutBorder_table);
                wholePDFWithOutBordercell.setCellEvent(roundRectange);
                wholePDFWithOutBordercell.setBorder(Rectangle.NO_BORDER);
                wholePDFWithOutBordercell.setPadding(8);
                wholePDFWithBorder_table.addCell(wholePDFWithOutBordercell);
                wholePDFWithBorder_table.setWidthPercentage(100);


                layoutDocument.add(wholePDFWithBorder_table);

            } catch (Exception e) {
                e.printStackTrace();
            }







        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{
           // layoutDocument.newPage();
        }
        catch (Exception e){
            e.printStackTrace();
        }


         resetAlltheValuesAndArrays();



    }

    public void showAddRetailerLayout() {

        show_retailerItemDetails_Dialog = new Dialog(mContext,R.style.Theme_Dialog);
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
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retailers_mobileno = mobileNo_textView.getText().toString();
                retailers_address = address_edittext.getText().toString();
                retailers_name = retailerName_textView.getText().toString();
                gstin = gstin_editText.getText().toString();

                if(retailers_mobileno.length()==10 ){
                    retailers_mobileno = "+91"+retailers_mobileno;
                    if(retailers_address.length()>2) {
                        if (retailers_name.length() > 0) {
                            call_and_init_B2BRetailerDetailsService(Constants.CallADDMethod, false,"");
                        } else {
                            AlertDialogClass.showDialog(getActivity(), R.string.retailers_name_cant_be_empty);

                        }
                    }
                    else{
                        AlertDialogClass.showDialog(getActivity(), R.string.retailers_Address_cant_be_empty);

                    }
                }
                else{
                    AlertDialogClass.showDialog(getActivity(), R.string.retailers_mobileno_should_be_10digits);

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

    public void shoeRetailerNotFoundAlert() {

        if(!isNoretailerAlertShown) {
            isNoretailerAlertShown = true;
            try {
               // AlertDialogClass.showDialog(requireActivity(), R.string.RetailerNotFound_Instruction);



                new TMCAlertDialogClass(requireActivity(), R.string.app_name, R.string.RetailerNotFound_Instruction,
                        R.string.OK_Text, R.string.Empty_Text,
                        new TMCAlertDialogClass.AlertListener() {
                            @Override
                            public void onYes() {

                                isNoretailerAlertShown = false;

                                retailerMobileNo_edittext.setText(retailermobileno);
                                retailerName_textView.setText(String.valueOf(retailername));
                                retailerAddress_textView.setText(String.valueOf(retaileraddress));
                                retailerMobileNo_edittext.clearFocus();
                                retailerMobileNo_edittext.setThreshold(1);
                                retailerMobileNo_edittext.dismissDropDown();
                                try{
                                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    Objects.requireNonNull(imm).hideSoftInputFromWindow(retailerMobileNo_edittext.getWindowToken(), 0);

                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onNo() {

                            }
                        });




            } catch (Exception e) {
                Toast.makeText(requireActivity(), getString(R.string.RetailerNotFound_Instruction), Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }
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



    public void resetAlltheValuesAndArrays() {
        isRetailerSelected = false;
          isRetailerDetailsServiceCalled = false ;
         isBarcodeScannerServiceCalled = false;
         isCalledForPlaceNewOrder = false;
         isB2BCartOrderTableServiceCalled = false;
         isCartAlreadyCreated = false ;priceperKg_not_edited_byUser = true ; ispaymentModeSelectedByuser = false ;isPricePerKgUpdated =false ;
                isRetailerUpdated = false ;

         approx_Live_Weight_double = 0 ; meatyeild_weight_double = 0 ; parts_Weight_double = 0 ; totalWeight_double = 0 ; discountDouble = 0 ;
                totalPrice_double = 0 ;     pricewithOutdiscount_double = 0;final_totalPriceWithOutDiscountWithFeedAmount =0;
           final_totalGoats =0;  final_totalPriceWithOutDiscount = 0 ; final_batchValue = 0; final_discountValue = 0 ; final_totalPayment = 0 ; final_totalWeight =0 ;

        oldretailerGSTIN = ""; oldpriceperKg ="" ;oldretaileraddress ="" ; oldRetailerName = "";oldretailerMobileno ="" ; oldRetailerKey =""; orderid ="" ;invoiceno = ""; scannedBarcode ="" ;
        batchno ="" ; retailerKey = "" ; retailername = "" ;pricePerKg ="";
                orderplaceddate =""; retailermobileno ="";retailerGSTIN ="";paymentMode ="";oldpaymentMode ="" ; updatedpaymentMode ="";
                retaileraddress =""  ;gradename ="" ; gradeprice =""; gender = "";meatyeild_weight_double = 0; approx_Live_Weight_double =0;

         earTagDetailsArrayList_WholeBatch.clear();
        earTagDetailsArrayList_String.clear();
        earTagDetails_JSONFinalSalesHashMap.clear();

            male_percentage_textview.setText("0%");
        female_Percentage_textview.setText("0%");
        female_Qty_textview.setText("0 nos ");
        male_Qty_textview.setText("0 nos ");
        totalGoats_textView.setText("0");
        approxLiveWeightAvg_Textview.setText("0");
        meatYieldWeightAvg_Textview.setText("0");
        finalGoatValue_textView.setText("0");
        finalFeedValue_textView.setText("0");


        retailerAddress_textView.setText("");
            retailerMobileNo_edittext.setText("");
            retailerName_textView.setText("");
        earTagDetails_JSONFinalSalesHashMap.clear();
        orderItemDetails_Linearlayout.setVisibility(View.GONE);
        generateBill_Button.setVisibility(View.GONE);
        viewItemInCart_Button.setVisibility(View.GONE);

            CalculateAndSetTotal_Quantity_Price_Values();
            showProgressBar(false);
        Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallGETMethod, "", false);


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


                //((BillingScreen)getActivity()).closeFragment();
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



        Modal_B2BOrderDetails.setTotalquantity_Static(String.valueOf(final_totalGoats));
        Modal_B2BOrderDetails.setBatchno_Static(String.valueOf(batchno));
        Modal_B2BOrderDetails.setTotalweight_Static(String.valueOf(threeDecimalConverter.format(final_totalWeight)));
        Modal_B2BOrderDetails.setDeliverycentrekey_Static(String.valueOf(deliveryCenterKey));
        Modal_B2BOrderDetails.setDeliverycentrename_Static(String.valueOf(deliveryCenterName));
        Modal_B2BOrderDetails.setDiscountamount_Static(String.valueOf(twoDecimalConverter.format(final_discountValue)));
        Modal_B2BOrderDetails.setOrderplaceddate_Static(String.valueOf(orderplaceddate));
        Modal_B2BOrderDetails.setPayableAmount_Static(String.valueOf(twoDecimalConverter.format(final_totalPayment)));
        Modal_B2BOrderDetails.setStatus_Static(String.valueOf(Constants.orderDetailsStatus_Delivered));
        Modal_B2BOrderDetails.setTotalPrice_Static(String.valueOf(twoDecimalConverter.format(final_totalPriceWithOutDiscount)));

        Modal_B2BOrderDetails.setOrderid_Static(String.valueOf(orderid));
        Modal_B2BOrderDetails.setInvoiceno_Static(String.valueOf(invoiceno));
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
                    // ((BillingScreen) getActivity()).Create_and_SharePdf();
                //    DeliveryCenter_PlaceOrderScreen_SecondVersn.deliveryCenter_placeOrderScreen_secondVersn.Create_and_SharePdf();
                    //((BillingScreen) getActivity()).neutralizeArray_and_OtherValues();
                }

                @Override
                public void notifySuccessForGettingListItem(ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch) {
                    isGoatEarTagDetailsTableServiceCalled = false;
                    //  ((BillingScreen) getActivity()).neutralizeArray_and_OtherValues();
                    // ((BillingScreen) getActivity()).closeFragment();
                }

                @Override
                public void notifyVolleyError(VolleyError error) {
                    // ((BillingScreen) getActivity()).neutralizeArray_and_OtherValues();
                    Toast.makeText(mContext, "There is an volley error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                    // ((BillingScreen) getActivity()).closeFragment();

                    isGoatEarTagDetailsTableServiceCalled = false;
                }

                @Override
                public void notifyProcessingError(Exception error) {
                    Toast.makeText(mContext, "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                    // ((BillingScreen) getActivity()).neutralizeArray_and_OtherValues();
                    // ((BillingScreen) getActivity()).closeFragment();
                    isGoatEarTagDetailsTableServiceCalled = false;


                }

            };


            Modal_B2BOrderItemDetails modal_b2BOrderItemDetails = new Modal_B2BOrderItemDetails();

            modal_b2BOrderItemDetails.setBatchno_static(String.valueOf(batchno));
            modal_b2BOrderItemDetails.setDeliverycentrekey_static(String.valueOf(deliveryCenterKey));
            modal_b2BOrderItemDetails.setOrderplaceddate_static(String.valueOf(orderplaceddate));
            modal_b2BOrderItemDetails.setStatus(String.valueOf(Constants.goatEarTagStatus_Sold));
            modal_b2BOrderItemDetails.setOrderid_static(String.valueOf(orderid));
            modal_b2BOrderItemDetails.setRetailerkey_static(String.valueOf(retailerKey));
            modal_b2BOrderItemDetails.setRetailermobileno_static(String.valueOf(retailermobileno));
            modal_b2BOrderItemDetails.setEarTagDetailsArrayList_String(earTagDetailsArrayList_String);


            try {

                String addApiToCall = API_Manager.updateGoatEarTag;
                GoatEarTagDetails_BulkUpdate asyncTask = new GoatEarTagDetails_BulkUpdate(goatEarTagDetailsBulkUpdateInterface, addApiToCall, callMethod, modal_b2BOrderItemDetails, orderplaceddate, usermobileno_string,earTagDetails_JSONFinalSalesHashMap);
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

                    retailerKey ="";
                    retailermobileno = "";
                    retailername = "";
                    orderid = "";
                    invoiceno = "";
                    retaileraddress = "";
                    discount_String = "";




                    orderid = String.valueOf(System.currentTimeMillis());
                    if (isneedToGenerateInvoiceNo){
                        Initialize_And_Process_InvoiceNoManager("GENERATE",isneedToGenerateInvoiceNo,true);

                    }
                    else{
                        Initialize_And_Process_InvoiceNoManager("GET", false,false);
                    }
                    // Initialize_And_Process_InvoiceNoManager("GET", false);
                    Toast.makeText(requireContext(), "There is no Cart Details for this batch", Toast.LENGTH_SHORT).show();


                }
                else {
                    //showProgressBar(false);
                    isCartAlreadyCreated = true;
                    priceperKg_not_edited_byUser = true;

                    //batchno = Modal_B2BCartOrderDetails.getBatchno();
                    retailerKey = Modal_B2BCartOrderDetails.getRetailerkey();
                    retailermobileno = Modal_B2BCartOrderDetails.getRetailermobileno();
                    retailername = Modal_B2BCartOrderDetails.getRetailername();
                    orderid = Modal_B2BCartOrderDetails.getOrderid();
                    pricePerKg = Modal_B2BCartOrderDetails.getPriceperkg();
                    invoiceno = Modal_B2BCartOrderDetails.getInvoiceno();
                    paymentMode = Modal_B2BCartOrderDetails.getPaymentMode();
                    oldpaymentMode  = Modal_B2BCartOrderDetails.getPaymentMode();
                    retaileraddress = Modal_B2BCartOrderDetails.getRetaileraddress();
                    discount_String = Modal_B2BCartOrderDetails.getDiscountAmount();


                    finalFeedValue_textView.setText(String.valueOf(Modal_B2BCartOrderDetails.getFeedPrice()));
                    totalFeedPriceTextview.setText(String.valueOf(Modal_B2BCartOrderDetails.getFeedPrice()));
                    feedPricePerKg_editText.setText(String.valueOf(Modal_B2BCartOrderDetails.getFeedPriceperkg()));
                    feedWeight_editText.setText(String.valueOf(Modal_B2BCartOrderDetails.getFeedWeight()));

                    retailerAddress_textView.setText(String.valueOf(retaileraddress));
                    retailerName_textView.setText(String.valueOf(retailername));
                    retailerMobileNo_edittext.setText(String.valueOf(retailermobileno));
                    retailerMobileNo_edittext.clearFocus();
                    retailerMobileNo_edittext.setThreshold(1);
                    retailerMobileNo_edittext.dismissDropDown();
                    discountValue_editText.setText(discount_String);
                    CalculateAndSetTotal_Quantity_Price_Values();

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
                    //batchNo_textview.setText(batchno);


                    isB2BCartOrderTableServiceCalled = false;
                }
            }
            @Override
            public void notifyVolleyError(VolleyError error) {
                isCartAlreadyCreated = false;
                Toast.makeText(requireContext(), "There is an volley error while updating CartOrder Details", Toast.LENGTH_SHORT).show();
                showProgressBar(false);
                isB2BCartOrderTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                isCartAlreadyCreated = false;
                Toast.makeText(requireContext(), "There is an Process error while updating CartOrder Details", Toast.LENGTH_SHORT).show();

                showProgressBar(false);
                isB2BCartOrderTableServiceCalled = false;


            }




        };

        if(callMethod.equals(Constants.CallADDMethod)){
            Modal_B2BCartOrderDetails.invoiceno = invoiceno;

            Modal_B2BCartOrderDetails.orderid = orderid;
            Modal_B2BCartOrderDetails.batchno = "";
            Modal_B2BCartOrderDetails.deliverycenterkey = deliveryCenterKey;
            Modal_B2BCartOrderDetails.deliverycentername = deliveryCenterName;
         //   Modal_B2BCartOrderDetails.priceperkg = pricePerKg_editText.getText().toString();
            Modal_B2BCartOrderDetails.retailerkey = retailerKey;
            Modal_B2BCartOrderDetails.retailermobileno = retailermobileno;
            Modal_B2BCartOrderDetails.retailername = retailername;
            Modal_B2BCartOrderDetails.retaileraddress = retaileraddress;
            Modal_B2BCartOrderDetails.feedPrice =String.valueOf(totalFeedPriceTextview.getText().toString());
            Modal_B2BCartOrderDetails.feedPriceperkg =String.valueOf(feedPricePerKg_editText.getText().toString());
            Modal_B2BCartOrderDetails.feedWeight =String.valueOf(feedWeight_editText.getText().toString());

            Modal_B2BCartOrderDetails.itemaddeddate = DateParser.getDate_and_time_newFormat();
            Modal_B2BCartOrderDetails.paymentMode =updatedpaymentMode;
            Modal_B2BCartOrderDetails.discountAmount = discount_String;
            String getApiToCall = API_Manager.addCartOrderDetails ;
            B2BCartOrderDetails asyncTask = new B2BCartOrderDetails(callback_b2bOrderDetails,  getApiToCall, Constants.CallADDMethod);
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
            Modal_UpdatedB2BCartOrderDetails.deliverycentername = deliveryCenterName;

                Modal_UpdatedB2BCartOrderDetails.deliverycenterkey = deliveryCenterKey;
            Modal_UpdatedB2BCartOrderDetails.orderid =orderid;

            String getApiToCall = API_Manager.updateCartOrderDetails ;
            B2BCartOrderDetails asyncTask = new B2BCartOrderDetails(callback_b2bOrderDetails,  getApiToCall, Constants.CallUPDATEMethod);
            asyncTask.execute();
            Modal_B2BCartOrderDetails .paymentMode = paymentMode;
          //  Modal_B2BCartOrderDetails.priceperkg = pricePerKg_editText.getText().toString();
            Modal_B2BCartOrderDetails.retailerkey = retailerKey;
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
            String getApiToCall = API_Manager.getCartOrderDetailsForDeliveryCentrekey+"?deliverycentrekey="+deliveryCenterKey ;

            B2BCartOrderDetails asyncTask = new B2BCartOrderDetails(callback_b2bOrderDetails,  getApiToCall, Constants.CallGETMethod);
            asyncTask.execute();

        }
        else if(callMethod.equals(Constants.CallGETListMethod)){

        }
        else if(callMethod.equals(Constants.CallDELETEMethod)){
            try {

                String addApiToCall = API_Manager.deleteCartOrderDetails+"?orderid="+orderid+"&deliverycentrekey="+deliveryCenterKey;
                B2BCartOrderDetails asyncTask = new B2BCartOrderDetails(callback_b2bOrderDetails, addApiToCall, Constants.CallDELETEMethod);
                asyncTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
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

        B2BOrderItemDetails asyncTask = new B2BOrderItemDetails(callback_b2BOrderItemDetailsInterface,  getApiToCall, Constants.CallADDMethod,earTagDetails_JSONFinalSalesHashMap,earTagDetailsArrayList_String,orderid,DeliveryCenter_PlaceOrderScreen_SecondVersn.this);
        asyncTask.execute();







    }


    private void Initialize_And_Process_InvoiceNoManager(String methodToCall, boolean isneedToGenerateInvoiceNo , boolean isOpenBarcodeScannerScreen) {
        showProgressBar(true);
        callback_invoiceManagerInterface = new B2BInvoiceNoManagerInterface() {


            @Override
            public void notifySuccess(String result) {
                invoiceno = result;
                if(isneedToGenerateInvoiceNo) {
                    Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallADDMethod, "", false);
                    if(isOpenBarcodeScannerScreen) {
                     //   Initialize_and_StartBarcodeScanner(getString(R.string.scannerCalled_to_FetchData), "", true);
                        ShowGoatItemDetailsDialog("",true);
                    }
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
                    Toast.makeText(getActivity(), "There is an volley error while updating Ear Tag", Toast.LENGTH_SHORT).show();

                    showProgressBar(false);
                    isGoatEarTagDetailsTableServiceCalled = false;
                }

                @Override
                public void notifyProcessingError(Exception error) {
                    Toast.makeText(getActivity(), "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                    showProgressBar(false);
                    isGoatEarTagDetailsTableServiceCalled = false;


                }

            };




            try {

                B2BCartItemDetails_BulkUpdate asyncTask = new B2BCartItemDetails_BulkUpdate(callbackB2BCartItemDetails_bulkUpdateInterface, orderid  , earTagDetailsArrayList_String);
                asyncTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void Intialize_and_ExecuteInB2BCartItemDetails(String callMethod, boolean doesneedtoOpenScanBarcode) {

        Modal_B2BCartItemDetails modal_b2BCartItemDetails_toAdd = new Modal_B2BCartItemDetails();

        showProgressBar(true);

        if (isB2BCartItemDetailsCalled) {
            //  showProgressBar(false);
            return;
        }
        isB2BCartItemDetailsCalled = true;

        callback_b2BCartItemDetaillsInterface = new B2BCartItemDetaillsInterface()
        {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BCartItemDetails> arrayList) {

                //add_And_UpdateChangesInBarcodeItemsList(arrayList);
                earTagDetails_JSONFinalSalesHashMap.clear();
                earTagDetailsArrayList_String.clear();

                for (int iterator = 0; iterator < arrayList.size(); iterator++) {
                    Modal_B2BCartItemDetails modal_b2BCartDetails = arrayList.get(iterator);

                    String ctgykey = "", ctgyname = "", subctgykey = "", suctgyname = "";
                    //gradeWise_count_weightJSONOBJECT = new JSONObject();
                    if(earTagDetailsArrayList_String.contains(modal_b2BCartDetails.getBarcodeno())){

                        JSONObject jsonObject = new JSONObject();
                        try{
                            jsonObject.put("gender",modal_b2BCartDetails.getGender());
                            jsonObject.put("gradename",modal_b2BCartDetails.getGradename());
                            jsonObject.put("approxliveweight",modal_b2BCartDetails.getApproxliveweight());
                            jsonObject.put("meatyield",modal_b2BCartDetails.getMeatyieldweight());
                            jsonObject.put("parts",modal_b2BCartDetails.getPartsweight());
                            jsonObject.put("totalprice",modal_b2BCartDetails.getTotalPrice_ofItem());
                            jsonObject.put("discount",modal_b2BCartDetails.getDiscount());
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }



                        double meatYield_double =0 , parts_double = 0;
                        try{
                            String text = String.valueOf(modal_b2BCartDetails.getMeatyieldweight());
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
                            String text = String.valueOf(modal_b2BCartDetails.getPartsweight());
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
                        modal_b2BCartDetails.setTotalItemWeight(String.valueOf(totalWeight_double));



                        if(earTagDetails_JSONFinalSalesHashMap.containsKey(modal_b2BCartDetails.getBarcodeno())) {
                            if (SDK_INT >= Build.VERSION_CODES.N) {
                                Objects.requireNonNull(earTagDetails_JSONFinalSalesHashMap.replace(modal_b2BCartDetails.getBarcodeno(),modal_b2BCartDetails ));
                            }
                            else{
                                Objects.requireNonNull(earTagDetails_JSONFinalSalesHashMap.put(modal_b2BCartDetails.getBarcodeno() , modal_b2BCartDetails));
                            }

                            // adapter_billingScreen_cartList.notifyDataSetChanged();
                           // calculateTotalweight_Quantity_Price();
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
                            modal_goatEarTagDetails.gradename  = modal_b2BCartDetails.getGradename();
                            modal_goatEarTagDetails.approxliveweight = modal_b2BCartDetails.getApproxliveweight();
                            modal_goatEarTagDetails.meatyieldweight = modal_b2BCartDetails.getMeatyieldweight();
                            modal_goatEarTagDetails.partsweight = modal_b2BCartDetails.getPartsweight();
                            modal_goatEarTagDetails.totalPrice_ofItem = modal_b2BCartDetails.getTotalPrice_ofItem();
                            modal_goatEarTagDetails.discount = modal_b2BCartDetails.getDiscount();
                            modal_goatEarTagDetails.itemPrice = modal_b2BCartDetails.getItemprice();
                            modal_goatEarTagDetails.totalItemWeight = modal_b2BCartDetails.getTotalItemWeight();

                             meatYield_double =0 ; parts_double = 0;
                            try{
                                String text = String.valueOf(modal_b2BCartDetails.getMeatyieldweight());
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
                                String text = String.valueOf(modal_b2BCartDetails.getPartsweight());
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
                            modal_b2BCartDetails.setTotalItemWeight(String.valueOf(totalWeight_double));

                            earTagDetails_JSONFinalSalesHashMap.put(modal_b2BCartDetails.getBarcodeno(), modal_b2BCartDetails);
                            //  earTagDetailsHashMap.put(modal_b2BCartDetails.getBarcodeno(),modal_goatEarTagDetails);
                            //adapter_billingScreen_cartList.notifyDataSetChanged();
                           // calculateTotalweight_Quantity_Price();

                        }
                    }
                    else{
                      //  calculateGradewiseQuantity_and_Weight(modal_b2BCartDetails );
                        JSONObject jsonObject = new JSONObject();

                        try{
                            jsonObject.put("gender",modal_b2BCartDetails.getGender());
                            jsonObject.put("gradename",modal_b2BCartDetails.getGradename());
                            jsonObject.put("approxliveweight",modal_b2BCartDetails.getApproxliveweight());
                            jsonObject.put("meatyield",modal_b2BCartDetails.getMeatyieldweight());
                            jsonObject.put("parts",modal_b2BCartDetails.getPartsweight());
                            jsonObject.put("totalprice",modal_b2BCartDetails.getTotalPrice_ofItem());
                            jsonObject.put("discount",modal_b2BCartDetails.getDiscount());
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }



                       double meatYield_double =0 , parts_double = 0;
                        try{
                            String text = String.valueOf(modal_b2BCartDetails.getMeatyieldweight());
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
                            String text = String.valueOf(modal_b2BCartDetails.getPartsweight());
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
                        modal_b2BCartDetails.setTotalItemWeight(String.valueOf(totalWeight_double));



                        if(earTagDetails_JSONFinalSalesHashMap.containsKey(modal_b2BCartDetails.getBarcodeno())) {
                            if (SDK_INT >= Build.VERSION_CODES.N) {
                                Objects.requireNonNull(earTagDetails_JSONFinalSalesHashMap.replace(modal_b2BCartDetails.getBarcodeno(), modal_b2BCartDetails));
                            }
                            else{
                                Objects.requireNonNull(earTagDetails_JSONFinalSalesHashMap.put(modal_b2BCartDetails.getBarcodeno() , modal_b2BCartDetails));
                            }

                            earTagDetailsArrayList_String.add(modal_b2BCartDetails.getBarcodeno());
                            //  adapter_billingScreen_cartList.notifyDataSetChanged();
                           // calculateTotalweight_Quantity_Price();
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
                            modal_goatEarTagDetails.loadedweightingrams =  String.valueOf(modal_b2BCartDetails.getOldweightingrams());
                            modal_goatEarTagDetails.currentweightingrams = String.valueOf(modal_b2BCartDetails.getOldweightingrams());
                            modal_goatEarTagDetails.newWeight_forBillingScreen = modal_b2BCartDetails.getWeightingrams();
                            modal_goatEarTagDetails.b2bsubctgykey = modal_b2BCartDetails.getB2bsubctgykey();
                            modal_goatEarTagDetails.b2bctgykey = modal_b2BCartDetails.getB2bctgykey();
                            modal_goatEarTagDetails.gradename  =  modal_b2BCartDetails.getGradename();
                            modal_goatEarTagDetails.approxliveweight = modal_b2BCartDetails.getApproxliveweight();
                            modal_goatEarTagDetails.meatyieldweight = modal_b2BCartDetails.getMeatyieldweight();
                            modal_goatEarTagDetails.partsweight = modal_b2BCartDetails.getPartsweight();
                            modal_goatEarTagDetails.itemPrice = modal_b2BCartDetails.getItemprice();
                            modal_goatEarTagDetails.totalItemWeight = modal_b2BCartDetails.getTotalItemWeight();

                            modal_goatEarTagDetails.totalPrice_ofItem = modal_b2BCartDetails.getTotalPrice_ofItem();
                            modal_goatEarTagDetails.discount = modal_b2BCartDetails.getDiscount();


                            meatYield_double =0 ; parts_double = 0;
                            try{
                                String text = String.valueOf(modal_b2BCartDetails.getMeatyieldweight());
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
                                String text = String.valueOf(modal_b2BCartDetails.getPartsweight());
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
                            modal_b2BCartDetails.setTotalItemWeight(String.valueOf(totalWeight_double));


                            earTagDetailsArrayList_String.add(modal_b2BCartDetails.getBarcodeno());
                            earTagDetails_JSONFinalSalesHashMap.put(modal_b2BCartDetails.getBarcodeno(), modal_b2BCartDetails);

                            //  earTagDetailsHashMap.put(modal_b2BCartDetails.getBarcodeno(),modal_goatEarTagDetails);
                            // adapter_billingScreen_cartList.notifyDataSetChanged();
                           // calculateTotalweight_Quantity_Price();

                        }
                    }





                    if(iterator == (arrayList.size() -1)){

                        CalculateAndSetTotal_Quantity_Price_Values();
                      //  setAdapterForCartItem();
                      //  setAdapterForGradewiseTotal();
                    }
                }
                isB2BCartItemDetailsCalled = false;
            }

            @Override
            public void notifySuccess(String result) {

                if(result.toUpperCase().equals(Constants.emptyResult_volley) && doesneedtoOpenScanBarcode) {
                    //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 2 " + DateParser.getDate_and_time_newFormat());
                    try{
                       // Initialize_and_StartBarcodeScanner(getString(R.string.scannerCalled_to_FetchData) ,"",true);
                        ShowGoatItemDetailsDialog("",true);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }




                }
                 if(callMethod.equals(Constants.CallADDMethod)) {


                     String ctgykey = "", ctgyname = "", subctgykey = "", suctgyname = "";
                     //gradeWise_count_weightJSONOBJECT = new JSONObject();
                     if(earTagDetailsArrayList_String.contains(modal_b2BCartItemDetails_toAdd.getBarcodeno())){

                       double  meatYield_double =0 , parts_double = 0;
                         try{
                             String text = String.valueOf(modal_b2BCartItemDetails_toAdd.getMeatyieldweight());
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
                             String text = String.valueOf(modal_b2BCartItemDetails_toAdd.getPartsweight());
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
                         modal_b2BCartItemDetails_toAdd.setTotalItemWeight(String.valueOf(totalWeight_double));



                         if(earTagDetails_JSONFinalSalesHashMap.containsKey(modal_b2BCartItemDetails_toAdd.getBarcodeno())) {
                             if (SDK_INT >= Build.VERSION_CODES.N) {
                                 Objects.requireNonNull(earTagDetails_JSONFinalSalesHashMap.replace(modal_b2BCartItemDetails_toAdd.getBarcodeno(),modal_b2BCartItemDetails_toAdd ));
                             }
                             else{
                                 Objects.requireNonNull(earTagDetails_JSONFinalSalesHashMap.put(modal_b2BCartItemDetails_toAdd.getBarcodeno() , modal_b2BCartItemDetails_toAdd));
                             }

                             // adapter_billingScreen_cartList.notifyDataSetChanged();
                             // calculateTotalweight_Quantity_Price();
                         }
                         else{
                             Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();

                             modal_goatEarTagDetails.barcodeno = modal_b2BCartItemDetails_toAdd.getBarcodeno();
                             modal_goatEarTagDetails.batchno = modal_b2BCartItemDetails_toAdd.getBatchno();
                             modal_goatEarTagDetails.status = modal_b2BCartItemDetails_toAdd.getStatus();
                             modal_goatEarTagDetails.itemaddeddate = modal_b2BCartItemDetails_toAdd.getItemaddeddate();
                             modal_goatEarTagDetails.stockedweightingrams = modal_b2BCartItemDetails_toAdd.getOldweightingrams();
                             modal_goatEarTagDetails.selecteditem = Modal_Static_GoatEarTagDetails.getSelecteditem();
                             modal_goatEarTagDetails.gender = modal_b2BCartItemDetails_toAdd.getGender();
                             modal_goatEarTagDetails.breedtype = modal_b2BCartItemDetails_toAdd.getBreedtype();
                             modal_goatEarTagDetails.loadedweightingrams = modal_b2BCartItemDetails_toAdd.getOldweightingrams();
                             modal_goatEarTagDetails.currentweightingrams = modal_b2BCartItemDetails_toAdd.getOldweightingrams();
                             modal_goatEarTagDetails.newWeight_forBillingScreen = modal_b2BCartItemDetails_toAdd.getWeightingrams();
                             modal_goatEarTagDetails.b2bsubctgykey = modal_b2BCartItemDetails_toAdd.getB2bsubctgykey();
                             modal_goatEarTagDetails.b2bctgykey = modal_b2BCartItemDetails_toAdd.getB2bctgykey();
                             modal_goatEarTagDetails.gradename  = modal_b2BCartItemDetails_toAdd.getGradename();
                             modal_goatEarTagDetails.approxliveweight = modal_b2BCartItemDetails_toAdd.getApproxliveweight();
                             modal_goatEarTagDetails.meatyieldweight = modal_b2BCartItemDetails_toAdd.getMeatyieldweight();
                             modal_goatEarTagDetails.partsweight = modal_b2BCartItemDetails_toAdd.getPartsweight();
                             modal_goatEarTagDetails.totalPrice_ofItem = modal_b2BCartItemDetails_toAdd.getTotalPrice_ofItem();
                             modal_goatEarTagDetails.discount = modal_b2BCartItemDetails_toAdd.getDiscount();
                             modal_goatEarTagDetails.itemPrice = modal_b2BCartItemDetails_toAdd.getItemprice();
                             modal_goatEarTagDetails.totalItemWeight = modal_b2BCartItemDetails_toAdd.getTotalItemWeight();



                               meatYield_double =0 ; parts_double = 0;
                             try{
                                 String text = String.valueOf(modal_b2BCartItemDetails_toAdd.getMeatyieldweight());
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
                                 String text = String.valueOf(modal_b2BCartItemDetails_toAdd.getPartsweight());
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
                             modal_b2BCartItemDetails_toAdd.setTotalItemWeight(String.valueOf(totalWeight_double));




                             earTagDetails_JSONFinalSalesHashMap.put(modal_b2BCartItemDetails_toAdd.getBarcodeno(), modal_b2BCartItemDetails_toAdd);
                             //  earTagDetailsHashMap.put(modal_b2BCartDetails.getBarcodeno(),modal_goatEarTagDetails);
                             //adapter_billingScreen_cartList.notifyDataSetChanged();
                             // calculateTotalweight_Quantity_Price();

                         }
                     }
                     else{

                         double  meatYield_double =0 , parts_double = 0;
                         try{
                             String text = String.valueOf(modal_b2BCartItemDetails_toAdd.getMeatyieldweight());
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
                             String text = String.valueOf(modal_b2BCartItemDetails_toAdd.getPartsweight());
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
                         modal_b2BCartItemDetails_toAdd.setTotalItemWeight(String.valueOf(totalWeight_double));


                         if(earTagDetails_JSONFinalSalesHashMap.containsKey(modal_b2BCartItemDetails_toAdd.getBarcodeno())) {
                             if (SDK_INT >= Build.VERSION_CODES.N) {
                                 Objects.requireNonNull(earTagDetails_JSONFinalSalesHashMap.replace(modal_b2BCartItemDetails_toAdd.getBarcodeno(), modal_b2BCartItemDetails_toAdd));
                             }
                             else{
                                 Objects.requireNonNull(earTagDetails_JSONFinalSalesHashMap.put(modal_b2BCartItemDetails_toAdd.getBarcodeno() , modal_b2BCartItemDetails_toAdd));
                             }

                             earTagDetailsArrayList_String.add(modal_b2BCartItemDetails_toAdd.getBarcodeno());

                         }


                         else{
                             Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();

                             modal_goatEarTagDetails.barcodeno = modal_b2BCartItemDetails_toAdd.getBarcodeno();
                             modal_goatEarTagDetails.batchno = modal_b2BCartItemDetails_toAdd.getBatchno();
                             modal_goatEarTagDetails.status = modal_b2BCartItemDetails_toAdd.getStatus();
                             modal_goatEarTagDetails.itemaddeddate = modal_b2BCartItemDetails_toAdd.getItemaddeddate();
                             modal_goatEarTagDetails.stockedweightingrams = modal_b2BCartItemDetails_toAdd.getOldweightingrams();
                             modal_goatEarTagDetails.selecteditem = Modal_Static_GoatEarTagDetails.getSelecteditem();
                             modal_goatEarTagDetails.gender = modal_b2BCartItemDetails_toAdd.getGender();
                             modal_goatEarTagDetails.breedtype = modal_b2BCartItemDetails_toAdd.getBreedtype();
                             modal_goatEarTagDetails.loadedweightingrams =  String.valueOf(modal_b2BCartItemDetails_toAdd.getOldweightingrams());
                             modal_goatEarTagDetails.currentweightingrams = String.valueOf(modal_b2BCartItemDetails_toAdd.getOldweightingrams());
                             modal_goatEarTagDetails.newWeight_forBillingScreen = modal_b2BCartItemDetails_toAdd.getWeightingrams();
                             modal_goatEarTagDetails.b2bsubctgykey = modal_b2BCartItemDetails_toAdd.getB2bsubctgykey();
                             modal_goatEarTagDetails.b2bctgykey = modal_b2BCartItemDetails_toAdd.getB2bctgykey();
                             modal_goatEarTagDetails.gradename  =  modal_b2BCartItemDetails_toAdd.getGradename();
                             modal_goatEarTagDetails.approxliveweight = modal_b2BCartItemDetails_toAdd.getApproxliveweight();
                             modal_goatEarTagDetails.meatyieldweight = modal_b2BCartItemDetails_toAdd.getMeatyieldweight();
                             modal_goatEarTagDetails.partsweight = modal_b2BCartItemDetails_toAdd.getPartsweight();
                             modal_goatEarTagDetails.itemPrice = modal_b2BCartItemDetails_toAdd.getItemprice();
                             modal_goatEarTagDetails.totalItemWeight = modal_b2BCartItemDetails_toAdd.getTotalItemWeight();

                             modal_goatEarTagDetails.totalPrice_ofItem = modal_b2BCartItemDetails_toAdd.getTotalPrice_ofItem();
                             modal_goatEarTagDetails.discount = modal_b2BCartItemDetails_toAdd.getDiscount();

                               meatYield_double =0 ; parts_double = 0;
                             try{
                                 String text = String.valueOf(modal_b2BCartItemDetails_toAdd.getMeatyieldweight());
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
                                 String text = String.valueOf(modal_b2BCartItemDetails_toAdd.getPartsweight());
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
                             modal_b2BCartItemDetails_toAdd.setTotalItemWeight(String.valueOf(totalWeight_double));


                             earTagDetailsArrayList_String.add(modal_b2BCartItemDetails_toAdd.getBarcodeno());
                             earTagDetails_JSONFinalSalesHashMap.put(modal_b2BCartItemDetails_toAdd.getBarcodeno(), modal_b2BCartItemDetails_toAdd);



                         }
                     }

                 }
                    CalculateAndSetTotal_Quantity_Price_Values();
                    showProgressBar(false);
                isB2BCartItemDetailsCalled = false;
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isB2BCartItemDetailsCalled = false;
                showProgressBar(false);

            }

            @Override
            public void notifyProcessingError(Exception error) {
                isB2BCartItemDetailsCalled = false;showProgressBar(false);
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());
                isB2BCartItemDetailsCalled = false;
            }


        };
        if(callMethod.equals(Constants.CallGETListMethod)) {
            String getApiToCall = API_Manager.getCartItemDetailsForOrderid + orderid;
            B2BCartItemDetails asyncTask = new B2BCartItemDetails(callback_b2BCartItemDetaillsInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }
        else if(callMethod.equals(Constants.CallADDMethod)) {
            String getApiToCall = API_Manager.addCartItemDetails;

            try {

                modal_b2BCartItemDetails_toAdd.totalItemWeight = (String.valueOf(Modal_Static_GoatEarTagDetails.getItemWeight()));
                modal_b2BCartItemDetails_toAdd.approxliveweight = (String.valueOf(Modal_Static_GoatEarTagDetails.getApproxliveweight()));
                modal_b2BCartItemDetails_toAdd.discount = (String.valueOf(Modal_Static_GoatEarTagDetails.getDiscount()));
                modal_b2BCartItemDetails_toAdd.meatyieldweight = (String.valueOf(Modal_Static_GoatEarTagDetails.getMeatyieldweight()));
                modal_b2BCartItemDetails_toAdd.partsweight = (String.valueOf(Modal_Static_GoatEarTagDetails.getPartsweight()));
                modal_b2BCartItemDetails_toAdd.totalPrice_ofItem = (String.valueOf(Modal_Static_GoatEarTagDetails.getTotalPrice_ofItem()));
                modal_b2BCartItemDetails_toAdd.gender = String.valueOf(Modal_Static_GoatEarTagDetails.getGender());
                modal_b2BCartItemDetails_toAdd.barcodeno = String.valueOf(Modal_Static_GoatEarTagDetails.getBarcodeno());
                modal_b2BCartItemDetails_toAdd.batchno = String.valueOf(Modal_Static_GoatEarTagDetails.getBatchno());
                modal_b2BCartItemDetails_toAdd.gradename = String.valueOf(Modal_Static_GoatEarTagDetails.getGradename());
                modal_b2BCartItemDetails_toAdd.itemaddeddate = String.valueOf(DateParser.getDate_and_time_newFormat());
                modal_b2BCartItemDetails_toAdd.itemprice = (String.valueOf(Modal_Static_GoatEarTagDetails.getItemPrice()));
                modal_b2BCartItemDetails_toAdd.orderid = (String.valueOf(orderid));


            }
            catch (Exception e){
                e.printStackTrace();
            }


            B2BCartItemDetails asyncTask = new B2BCartItemDetails(callback_b2BCartItemDetaillsInterface,  getApiToCall, callMethod,modal_b2BCartItemDetails_toAdd);
            asyncTask.execute();



        }





    }

    public static void CalculateAndSetTotal_Quantity_Price_Values() {
        final_totalPriceWithOutDiscountWithFeedAmount =0;
        final_totalWeight = 0  ; final_totalGoats =0; final_totalPriceWithOutDiscount = 0 ; final_batchValue = 0; final_discountValue = 0 ; final_totalPayment = 0 ;
            String meatYield_String ="0" , approxLiveWeight_String = "0"  , parts_String ="0" , price_String = "0", discount_String_itemDetails ="0",
                    totalPrice_String="0",totalWeight_String="0" ,totalFeedString ="0";

            double meatYield_Double =0 , approxLiveWeight_Double =0 , parts_Double =0 , price_Double =0 , discount_Double =0 , totalPrice_Double =0 , totalWeight_Double =0 ;
            double maleRatioValue_double=0 , femaleRatioValue_double =0 ,meatYieldAvg = 0, approxLiveWeightAvg =0; ;
            double maleCount_int = 0 ,femaleCountInt=0 , totalMeatYeild =0 , totalaApproxLiveWeight = 0 ,totalFeedDouble =0;
            String male_FemaleRatio = "";
            if(earTagDetailsArrayList_String.size()>0)
            {
                orderItemDetails_Linearlayout.setVisibility(View.VISIBLE);
                generateBill_Button.setVisibility(View.VISIBLE);
                viewItemInCart_Button.setVisibility(View.VISIBLE);
                for(int i =0 ; i<earTagDetailsArrayList_String.size();i++){
                    meatYield_Double =0 ; approxLiveWeight_Double =0 ; parts_Double =0 ; price_Double =0; discount_Double =0 ; totalPrice_Double =0 ;
                    maleRatioValue_double=0 ; femaleRatioValue_double =0 ;meatYieldAvg = 0; approxLiveWeightAvg =0;totalFeedDouble =0;
                    meatYield_String ="0" ; approxLiveWeight_String = "0"  ; parts_String ="0" ; price_String = "0"; discount_String_itemDetails ="0";
                            totalPrice_String="0";  totalWeight_String="0";totalFeedString ="0";
                    Modal_B2BCartItemDetails modal_b2BCartItemDetails = earTagDetails_JSONFinalSalesHashMap.get(earTagDetailsArrayList_String.get(i));
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
                              maleRatioValue_double  = ((maleCount_int/earTagDetails_JSONFinalSalesHashMap.size()) *100);

                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }



                            try{
                                femaleRatioValue_double  = ((femaleCountInt/earTagDetails_JSONFinalSalesHashMap.size()) *100);

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
                            final_totalPriceWithOutDiscount = price_Double + final_totalPriceWithOutDiscount;
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
                            final_totalPriceWithOutDiscountWithFeedAmount = final_totalPriceWithOutDiscount + totalFeedDouble;
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
                            final_totalGoats = earTagDetails_JSONFinalSalesHashMap.size();
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
                                    final_batchValue  =  final_totalPriceWithOutDiscount  / final_totalGoats;
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
                        totalGoats_textView.setText(String.valueOf(final_totalGoats));
                        finaltotalWeight_textView.setText(String.valueOf(final_totalWeight));
                        totalValue_textView.setText(String.valueOf(final_totalPriceWithOutDiscountWithFeedAmount));
                        batchValue_textView.setText(String.valueOf(final_batchValue));
                        if(String.valueOf(batchValue_textView.getText().toString()).toUpperCase().equals("NAN")){
                            batchValue_textView.setText(String.valueOf("0.000"));

                        }
                        discountValue_textView.setText(String.valueOf(final_discountValue));
                        finalPayment_textView.setText(String.valueOf(final_totalPayment));
                        finalGoatValue_textView.setText(String.valueOf(final_totalPriceWithOutDiscount));
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

                    try{
                       // male_female_ratio_textview.setText(String.valueOf(String.valueOf(maleRatioValue_double) +" : "+String.valueOf(femaleRatioValue_double)));

                      //  male_female_ratio_textview.setText(String.valueOf(male_FemaleRatio));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                }
            }
            else{
                try{
                    totalGoats_textView.setText(String.valueOf(final_totalGoats));
                    finaltotalWeight_textView.setText(String.valueOf(final_totalWeight));
                    totalValue_textView.setText(String.valueOf(final_totalPriceWithOutDiscountWithFeedAmount));
                    batchValue_textView.setText(String.valueOf(final_batchValue));
                    if(String.valueOf(batchValue_textView.getText().toString()).toUpperCase().equals("NAN")){
                        batchValue_textView.setText(String.valueOf("0.000"));

                    }
                    discountValue_textView.setText(String.valueOf(final_discountValue));
                    finalPayment_textView.setText(String.valueOf(final_totalPayment));
                    finalGoatValue_textView.setText(String.valueOf(final_totalPriceWithOutDiscount));
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
    static String ratio(int a, int b) {
      /*  final int gcd = gcd(a,b);
        if(a > b) {
           return showAnswer(a/gcd, b/gcd);
        } else {
            return showAnswer(b/gcd, a/gcd);
        }


       */
        int min = Math.min(a, b);
        int max = Math.max(a, b);
        if(max % min == 0) {
            System.out.println("1" + " " + max / min);
            return ("1" + " " + max / min);
        }
        for (int i = 2; i <= min; i++) {
            while(max % i == 0 && min % i == 0) {
                max /= i;
                min /= i;
            }
        }
        System.out.println(max + " " + min);
        return  (max + " " + min);



    }

    static String showAnswer(int a, int b) {
        System.out.println(a + " " + b);
        return (a + " : " + b);
    }
    static int gcd(int p, int q) {
        if (q == 0) return p;
        else return gcd(q, p % q);
    }
    private void getSelectedRetailerDetails(String retailerKeyy) {

        for (int iterator = 0; iterator < retailerDetailsArrayList.size(); iterator++) {
            Modal_B2BRetailerDetails modal_b2BRetailerDetails = retailerDetailsArrayList.get(iterator);

            if (modal_b2BRetailerDetails.getRetailerkey().toUpperCase().equals(retailerKeyy.toUpperCase())) {
                retailername = modal_b2BRetailerDetails.getRetailername();
                retailerKey = modal_b2BRetailerDetails.getRetailerkey();
                retaileraddress = modal_b2BRetailerDetails.getAddress();
                retailermobileno = modal_b2BRetailerDetails.getMobileno();
                retailerGSTIN = modal_b2BRetailerDetails.getGstin();
                retailerMobileNo_edittext.setText(retailermobileno);
            }

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
                  //  processArray_And_AddItem_InTheCart(scannedBarcode);

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
                    Toast.makeText(mContext, "Error in scanning ", Toast.LENGTH_SHORT).show();


                }
            };

            showProgressBar(false);
            Intent intent = new Intent(mContext, BarcodeScannerScreen.class);
            intent.putExtra(getString(R.string.scanner_called_to_do), processtoDOAfterScan);
            intent.putExtra(getString(R.string.called_from), getString(R.string.placedOrder_Details_Screen_SecondVersion));
            //intent.putExtra(getString(R.string.isScannerModeTurnedOn), isScannerModeTurnedOn);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            // startActivity(intent);
            startActivityForResult(intent,2);


        }
        catch (Exception e){
            showProgressBar(false);
            e.printStackTrace();
        }


    }


    private void Initialize_and_ExecuteInGoatEarTagTransaction(String callMethod, String goatEarTagAdd_OR_Updated) {

        showProgressBar(true);
        if (isGoatEarTagTransactionTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isGoatEarTagTransactionTableServiceCalled = true;
        callback_GoatEarTagTransactionInterface = new GoatEarTagTransactionInterface() {


            @Override
            public void notifySuccess(String result) {
                showProgressBar(false);


                isGoatEarTagTransactionTableServiceCalled = false;
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                //  UpdateCalculationDataINSharedPref(goatEarTagAdd_OR_Updated);
                //   Modal_UpdatedGoatEarTagDetails modal_updatedGoatEarTagDetails = new Modal_UpdatedGoatEarTagDetails();
                //   Modal_Static_GoatEarTagDetails modal_goatEarTagDetails = new Modal_Static_GoatEarTagDetails();
                //   entered_Weight_double =0;
                //  previous_WeightInGrams ="";
                Toast.makeText(mContext, "There is an volley error while updating Ear Tag Transaction", Toast.LENGTH_SHORT).show();
                //   showProgressBar(false);
                // ((GoatEarTagItemDetailsList)getActivity()).closeFragment();
                isGoatEarTagTransactionTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                //       UpdateCalculationDataINSharedPref(goatEarTagAdd_OR_Updated);
                // Modal_UpdatedGoatEarTagDetails modal_updatedGoatEarTagDetails = new Modal_UpdatedGoatEarTagDetails();
                //  Modal_Static_GoatEarTagDetails modal_goatEarTagDetails = new Modal_Static_GoatEarTagDetails();
                //  entered_Weight_double =0;
                //   previous_WeightInGrams ="";
                Toast.makeText(mContext, "There is an Process error while updating Ear Tag Transaction", Toast.LENGTH_SHORT).show();
                BaseActivity. isAdding_Or_UpdatingEntriesInDB_Service = false;
                showProgressBar(false);
                isGoatEarTagTransactionTableServiceCalled = false;
                //  ((GoatEarTagItemDetailsList)getActivity()).closeFragment();


            }




        };

        if(callMethod.equals(Constants.CallADDMethod)){
            if(goatEarTagAdd_OR_Updated.equals(Constants.CallADDMethod)) {
                try {
                    Modal_GoatEarTagTransaction.barcodeno = scannedBarcode;
                    Modal_GoatEarTagTransaction.batchno = Modal_Static_GoatEarTagDetails.getBatchno();
                    Modal_GoatEarTagTransaction.updateddate = DateParser.getDate_and_time_newFormat();
                    Modal_GoatEarTagTransaction.previousweightingrams = Modal_Static_GoatEarTagDetails.getNewWeight_forBillingScreen();
                    Modal_GoatEarTagTransaction.newweightingrams = String.valueOf(Modal_Static_GoatEarTagDetails.getNewWeight_forBillingScreen());
                    Modal_GoatEarTagTransaction.weighingpurpose = Constants.goatEarTagWeighingPurpose_RegularAudit;
                    Modal_GoatEarTagTransaction.status = Modal_Static_GoatEarTagDetails.getStatus();
                    Modal_GoatEarTagTransaction.gender = Modal_Static_GoatEarTagDetails.getGender();
                    Modal_GoatEarTagTransaction.breedtype = Modal_Static_GoatEarTagDetails.getBreedtype();
                    Modal_GoatEarTagTransaction.mobileno = usermobileno_string;
                    Modal_GoatEarTagTransaction.description = Modal_Static_GoatEarTagDetails.getDescription();
                    Modal_GoatEarTagTransaction.gradeprice=(Modal_Static_GoatEarTagDetails.getGradeprice());
                    Modal_GoatEarTagTransaction.gradekey=(Modal_Static_GoatEarTagDetails.getGradekey());
                    Modal_GoatEarTagTransaction.deliverycenterkey = deliveryCenterKey;
                    Modal_GoatEarTagTransaction.deliverycentername = deliveryCenterName;

                    String addApiToCall = API_Manager.addGoatEarTagTransactions;
                    GoatEarTagTransaction asyncTask = new GoatEarTagTransaction(callback_GoatEarTagTransactionInterface, addApiToCall, callMethod);
                    asyncTask.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(goatEarTagAdd_OR_Updated.equals(Constants.CallUPDATEMethod)){
                try{

                    Modal_GoatEarTagTransaction.barcodeno = scannedBarcode;
                    Modal_GoatEarTagTransaction.batchno = Modal_Static_GoatEarTagDetails.getBatchno();
                    Modal_GoatEarTagTransaction.updateddate = DateParser.getDate_and_time_newFormat();
                    Modal_GoatEarTagTransaction.mobileno = usermobileno_string;
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_gradename_boolean()){

                        Modal_GoatEarTagTransaction.gradename=(gradename);

                    }
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_deliverycenterkey_boolean()) {
                        Modal_GoatEarTagTransaction.deliverycenterkey = deliveryCenterKey;
                    }
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_deliverycentername_boolean()) {
                        Modal_GoatEarTagTransaction.deliverycentername = deliveryCenterName;
                    }
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_approxliveweight_boolean()){
                        try {
                            Modal_GoatEarTagTransaction.approxliveweight = (String.valueOf(approx_Live_Weight_double));
                        }
                        catch (Exception e){
                            Modal_GoatEarTagTransaction.approxliveweight =  "0";
                            e.printStackTrace();
                        }
                    }
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_status_boolean()) {
                        Modal_GoatEarTagTransaction.status = Modal_Static_GoatEarTagDetails.getStatus();
                    }
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_gender_boolean()) {
                        Modal_GoatEarTagTransaction.gender = gender;
                    }



                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_meatyieldweight_boolean()){
                        try {
                            Modal_GoatEarTagTransaction.meatyieldweight = (String.valueOf(meatyeild_weight_double));
                        }
                        catch (Exception e){
                            Modal_GoatEarTagTransaction.meatyieldweight = "0";
                            e.printStackTrace();
                        }
                    }


                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_partsweight_boolean()){
                        try {
                            Modal_GoatEarTagTransaction.partsweight = (String.valueOf(parts_Weight_double));
                        }
                        catch (Exception e){
                            Modal_GoatEarTagTransaction.partsweight = "0";
                            e.printStackTrace();
                        }
                    }




                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_Price_boolean()){
                        try {
                            Modal_GoatEarTagTransaction.itemPrice = (String.valueOf(pricewithOutdiscount_double));
                        }
                        catch (Exception e){
                            Modal_GoatEarTagTransaction.itemPrice = "0";
                            e.printStackTrace();
                        }
                    }


                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_totalWeight_boolean()){
                        try {
                            Modal_GoatEarTagTransaction.itemWeight = (String.valueOf(totalWeight_double));
                        }
                        catch (Exception e){
                            Modal_GoatEarTagTransaction.itemWeight = "0";
                            e.printStackTrace();
                        }
                    }



                    String addApiToCall = API_Manager.addGoatEarTagTransactions;
                    GoatEarTagTransaction asyncTask = new GoatEarTagTransaction(callback_GoatEarTagTransactionInterface, addApiToCall, callMethod);
                    asyncTask.execute();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
        else if (callMethod.equals(Constants.CallUPDATEMethod)){

        }
        else if (callMethod.equals(Constants.CallGETMethod)){

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


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        // Stuff that updates the UI
                        showProgressBar(false);
                        if (result.equals(Constants.emptyResult_volley)) {
                            showProgressBar(false);

                            try {
                                AlertDialogClass.showDialog(requireActivity(), R.string.EarTagDetailsNotFound_Instruction);

                            } catch (Exception e) {
                                Toast.makeText(requireActivity(), getString(R.string.EarTagDetailsNotFound_Instruction), Toast.LENGTH_SHORT).show();

                                e.printStackTrace();
                            }

                        }
                        else  if (result.equals(Constants.expressionAttribute_is_empty_volley_response)){
                            if(callMethod.toUpperCase().equals(Constants.CallUPDATEMethod)){
                                Intialize_and_ExecuteInB2BCartItemDetails(Constants.CallADDMethod,false);
                            }
                        }
                        else {
                            if (Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_EarTagLost)) {

                                try{
                                    AlertDialogClass.showDialog(requireActivity(),R.string.EarTagLost_Instruction);

                                }
                                catch (Exception e){
                                    Toast.makeText(requireActivity(), getString(R.string.EarTagLost_Instruction), Toast.LENGTH_LONG).show();

                                    e.printStackTrace();
                                }

                                return;
                            }
                            else if (Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_GoatLost)) {


                                try{
                                    AlertDialogClass.showDialog(requireActivity(),R.string.GoatLost_Instruction);

                                }
                                catch (Exception e){
                                    Toast.makeText(mContext, getString(R.string.GoatLost_Instruction), Toast.LENGTH_LONG).show();

                                    e.printStackTrace();
                                }

                                return;

                            }
                            else if (Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatdead)) {

                                try{
                                    AlertDialogClass.showDialog(requireActivity(),R.string.GoatDead_Instruction);

                                }
                                catch (Exception e){
                                    Toast.makeText(mContext, getString(R.string.GoatDead_Instruction), Toast.LENGTH_LONG).show();

                                    e.printStackTrace();
                                }

                                return;

                            }
                            else if (Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Sold)) {

                                try{
                                    AlertDialogClass.showDialog(requireActivity(),R.string.EarTagSold_Instruction);

                                }
                                catch (Exception e){
                                    Toast.makeText(mContext, getString(R.string.EarTagSold_Instruction), Toast.LENGTH_LONG).show();

                                    e.printStackTrace();
                                }
                                return;

                            }
                            else if (Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatsick)) {

                                try{
                                    AlertDialogClass.showDialog(requireActivity(),R.string.GoatSick_Instruction);

                                }
                                catch (Exception e){
                                    Toast.makeText(mContext, getString(R.string.GoatSick_Instruction), Toast.LENGTH_LONG).show();

                                    e.printStackTrace();
                                }


                            }


                            if(callMethod.toUpperCase().equals(Constants.CallUPDATEMethod)){

                                try{
                                    Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod, callMethod);
                                }
                                catch (Exception e ){
                                    e.printStackTrace();
                                }

                                Intialize_and_ExecuteInB2BCartItemDetails(Constants.CallADDMethod,false);
                            }
                            else{
                                if(!isCalledForPlaceNewOrder) {


                                    if (!earTagDetails_JSONFinalSalesHashMap.containsKey(Modal_Static_GoatEarTagDetails.getBarcodeno()))
                                    {
                                        Modal_B2BCartItemDetails modal_b2BCartItemDetails = new Modal_B2BCartItemDetails();
                                        modal_b2BCartItemDetails.barcodeno = Modal_Static_GoatEarTagDetails.getBarcodeno();
                                        modal_b2BCartItemDetails.batchno = Modal_Static_GoatEarTagDetails.getBatchno();
                                        modal_b2BCartItemDetails.status = Modal_Static_GoatEarTagDetails.getStatus();
                                        modal_b2BCartItemDetails.suppliername = Modal_Static_GoatEarTagDetails.getSuppliername();
                                        modal_b2BCartItemDetails.supplierkey = Modal_Static_GoatEarTagDetails.getSupplierkey();
                                        modal_b2BCartItemDetails.itemaddeddate = Modal_Static_GoatEarTagDetails.getItemaddeddate();
                                        modal_b2BCartItemDetails.gender = Modal_Static_GoatEarTagDetails.getGender();
                                        modal_b2BCartItemDetails.breedtype = Modal_Static_GoatEarTagDetails.getBreedtype();
                                        modal_b2BCartItemDetails.gradeprice = Modal_Static_GoatEarTagDetails.getGradeprice();
                                        modal_b2BCartItemDetails.gradename = Modal_Static_GoatEarTagDetails.getGradename();
                                        modal_b2BCartItemDetails.gradekey = Modal_Static_GoatEarTagDetails.getGradekey();
                                        modal_b2BCartItemDetails.approxliveweight = Modal_Static_GoatEarTagDetails.getApproxliveweight();
                                        modal_b2BCartItemDetails.meatyieldweight = Modal_Static_GoatEarTagDetails.getMeatyieldweight();
                                        modal_b2BCartItemDetails.partsweight = Modal_Static_GoatEarTagDetails.getPartsweight();
                                        modal_b2BCartItemDetails.totalPrice_ofItem = Modal_Static_GoatEarTagDetails.getTotalPrice_ofItem();
                                        modal_b2BCartItemDetails.discount = Modal_Static_GoatEarTagDetails.getDiscount();



                                        double meatYield_double =0 , parts_double = 0;
                                        try{
                                            String text = String.valueOf(Modal_Static_GoatEarTagDetails.getMeatyieldweight());
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
                                            String text = String.valueOf(Modal_Static_GoatEarTagDetails.getPartsweight());
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
                                        modal_b2BCartItemDetails.setTotalItemWeight(String.valueOf(totalWeight_double));



                                        earTagDetailsArrayList_String.add(Modal_Static_GoatEarTagDetails.getBarcodeno());
                                        earTagDetails_JSONFinalSalesHashMap.put(Modal_Static_GoatEarTagDetails.getBarcodeno(),modal_b2BCartItemDetails);

                                        //    calculateTotalweight_Quantity_Price();

                                    }
                                    else{
                                        Toast.makeText(mContext, "Please Scan the New Item", Toast.LENGTH_SHORT).show();

                                    }



                                    earTagDetails_JSONFinalSalesHashMap.remove(barcodeFromStringArray);
                                    //  earTagDetailsHashMap.remove(barcodeFromStringArray);
                                    earTagDetailsArrayList_String.remove(barcodeFromStringArray);
                                    //   adapter_billingScreen_cartList.notifyDataSetChanged();
                                    //   calculateTotalweight_Quantity_Price();


                                }
                                else{
                                    Modal_B2BCartItemDetails modal_b2BCartItemDetails = new Modal_B2BCartItemDetails();
                                    modal_b2BCartItemDetails.barcodeno = Modal_Static_GoatEarTagDetails.getBarcodeno();
                                    modal_b2BCartItemDetails.batchno = Modal_Static_GoatEarTagDetails.getBatchno();
                                    modal_b2BCartItemDetails.status = Modal_Static_GoatEarTagDetails.getStatus();
                                    modal_b2BCartItemDetails.suppliername = Modal_Static_GoatEarTagDetails.getSuppliername();
                                    modal_b2BCartItemDetails.supplierkey = Modal_Static_GoatEarTagDetails.getSupplierkey();
                                    modal_b2BCartItemDetails.itemaddeddate = Modal_Static_GoatEarTagDetails.getItemaddeddate();
                                    modal_b2BCartItemDetails.gender = Modal_Static_GoatEarTagDetails.getGender();
                                    modal_b2BCartItemDetails.breedtype = Modal_Static_GoatEarTagDetails.getBreedtype();
                                    modal_b2BCartItemDetails.gradeprice = Modal_Static_GoatEarTagDetails.getGradeprice();
                                    modal_b2BCartItemDetails.gradename = Modal_Static_GoatEarTagDetails.getGradename();
                                    modal_b2BCartItemDetails.gradekey = Modal_Static_GoatEarTagDetails.getGradekey();
                                    modal_b2BCartItemDetails.approxliveweight = Modal_Static_GoatEarTagDetails.getApproxliveweight();
                                    modal_b2BCartItemDetails.meatyieldweight = Modal_Static_GoatEarTagDetails.getMeatyieldweight();
                                    modal_b2BCartItemDetails.partsweight = Modal_Static_GoatEarTagDetails.getPartsweight();
                                    modal_b2BCartItemDetails.totalPrice_ofItem = Modal_Static_GoatEarTagDetails.getTotalPrice_ofItem();
                                    modal_b2BCartItemDetails.discount = Modal_Static_GoatEarTagDetails.getDiscount();



                                    double meatYield_double =0 , parts_double = 0;
                                    try{
                                        String text = String.valueOf(Modal_Static_GoatEarTagDetails.getMeatyieldweight());
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
                                        String text = String.valueOf(Modal_Static_GoatEarTagDetails.getPartsweight());
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
                                    modal_b2BCartItemDetails.setTotalItemWeight(String.valueOf(totalWeight_double));



                                    if (!earTagDetails_JSONFinalSalesHashMap.containsKey(Modal_Static_GoatEarTagDetails.getBarcodeno())) {
                                        if (earTagDetailsArrayList_String.contains(Modal_Static_GoatEarTagDetails.getBarcodeno())) {
                                            earTagDetailsArrayList_String.remove(Modal_Static_GoatEarTagDetails.getBarcodeno());
                                        }

                                        try {
                                            BaseActivity.baseActivity.getDeviceName();
                                        }
                                        catch (Exception e){
                                            e.printStackTrace();
                                        }
                                        try {
                                            if (BaseActivity.isDeviceIsMobilePhone) {
                                                value_forFragment = getString(R.string.placedOrder_Details_Screen_SecondVersion);

                                            }
                                            else{
                                                value_forFragment = getString(R.string.pos_placedOrder_Details_Screen_SecondVersion);

                                            }


                                            mfragment = new GoatItemDetailsFragment_SecondVersn();
                                            try{
                                                ShowGoatItemDetailsDialog("",true);
                                                //    loadMyFragment();
                                            }
                                            catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                        catch (Exception e){
                                            e.printStackTrace();
                                        }

                                    }
                                    else{
                                        Toast.makeText(mContext, "Please Scan the New Item", Toast.LENGTH_SHORT).show();

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
                            mfragment = new GoatItemDetailsFragment_SecondVersn();
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
                    }
                });




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
                        Toast.makeText(mContext, Constants.there_is_noData_volley, Toast.LENGTH_SHORT).show();


                    }

                } catch (Exception e) {
                    showProgressBar(false);
                    isGoatEarTagDetailsTableServiceCalled = false;

                    Toast.makeText(mContext, "There is an error while generate report", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }

                showProgressBar(false);
                isGoatEarTagDetailsTableServiceCalled = false;



            }


            @Override
            public void notifyVolleyError(VolleyError error) {
                try{
                    showProgressBar(false);
                    Toast.makeText(mContext, "There is an volley error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                    isGoatEarTagDetailsTableServiceCalled = false;
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyProcessingError(Exception error) {
                try{
                    Toast.makeText(mContext, "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();

                    showProgressBar(false);
                    isGoatEarTagDetailsTableServiceCalled = false;
                }
                catch (Exception e){
                    e.printStackTrace();
                }



            }


        };

        if (callMethod.equals(Constants.CallGETMethod)) {
            String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBarcodeNoWithDeliveryCentreKey + "?barcodeno=" + scannedBarcode + "&deliverycentrekey="+deliveryCenterKey;
            GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
            asyncTask.execute();
        } else if (callMethod.equals(Constants.CallGETListMethod)) {
            //String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchno + batchno;
            // GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
            //  asyncTask.execute();
        }
        else if (callMethod.equals(Constants.CallUPDATEMethod)) {
            String addApiToCall = API_Manager.updateGoatEarTag;
            GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
            asyncTask.execute();
        }


    }

    private void ShowGoatItemDetailsDialog(String barcodeFromStringArray , boolean isCalledForPlaceNewOrder) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Theme_Dialog
                    //Theme_Design_Light
                    if (show_goatEarTagItemDetails_Dialog.isShowing()) {
                        approx_Live_Weight_double = 0;
                        meatyeild_weight_double = 0;
                        parts_Weight_double = 0;
                        totalWeight_double = 0;
                        discountDouble = 0;
                        totalPrice_double = 0;


                        goatstatus_layout.setVisibility(View.GONE);


                        save_button.setText(String.valueOf(" Add Item "));



                        selectedGoatStatus_textview.setVisibility(View.VISIBLE);
                        goatstatusradiogrp.setVisibility(View.GONE);
                        save_button.setVisibility(View.VISIBLE);

                        gradeName_editText.setVisibility(View.GONE);
                        gradeName_textview.setVisibility(View.VISIBLE);

                        genderRadioGroup.setVisibility(View.GONE);
                        gender_textview.setVisibility(View.VISIBLE);

                        approxLiveWeight_textview.setVisibility(View.GONE);
                        approxLiveWeight_EditText.setVisibility(View.VISIBLE);


                        meatyeild_textview.setVisibility(View.GONE);
                        meatyeild_edittext.setVisibility(View.VISIBLE);


                        parts_editText.setVisibility(View.VISIBLE);
                        parts_textView.setVisibility(View.GONE);

                        priceWithoutDiscount_edittext.setVisibility(View.VISIBLE);
                        priceWithoutDiscount_textview.setVisibility(View.GONE);

                        discount_edittext.setVisibility(View.GONE);
                        discount_textView.setVisibility(View.GONE);


                        totalPrice_edittext.setVisibility(View.GONE);
                        totalPrice_textView.setVisibility(View.GONE);


                        if(selectedGenderName.toUpperCase().equals(getString(R.string.MALE))){
                            male_radioButton.setChecked(true);
                            female_radioButton.setChecked(false);
                        }
                        else  if(selectedGenderName.toUpperCase().equals(getString(R.string.FEMALE))){
                            male_radioButton.setChecked(false);
                            female_radioButton.setChecked(true);
                        }

                        if(selectedItemsStatus.toUpperCase().equals(Constants.goatEarTagStatus_Goatsick)){
                            sick_goat_radio.setChecked(true);
                            normal_goat_radio.setChecked(false);
                            dead_goat_radio.setChecked(false);
                        }
                        else if(selectedItemsStatus.toUpperCase().equals(Constants.goatEarTagStatus_Goatdead)){
                            sick_goat_radio.setChecked(false);
                            normal_goat_radio.setChecked(false);
                            dead_goat_radio.setChecked(true);
                        }
                        else{
                            sick_goat_radio.setChecked(false);
                            normal_goat_radio.setChecked(true);
                            dead_goat_radio.setChecked(false);
                        }


                        if (Modal_Static_GoatEarTagDetails.getBarcodeno().equals("") || Modal_Static_GoatEarTagDetails.getBarcodeno().equals(null)) {
                            // show_goatEarTagItemDetails_Dialog.cancel();
                             Toast.makeText(mContext, "Please Enter new Barcode Again", Toast.LENGTH_SHORT).show();
                            barcode_editText.setVisibility(View.VISIBLE);
                            barcodeNo_textView.setVisibility(View.GONE);


                        }
                        else {
                            barcode_editText.setVisibility(View.GONE);
                            barcodeNo_textView.setVisibility(View.VISIBLE);

                            barcodeNo_textView.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getBarcodeno()));
                            gradeName_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getGradename()));
                            gender_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getGender()));
                            gender = String.valueOf(Modal_Static_GoatEarTagDetails.getGender());
                            double meatYield_double = 0, parts_double = 0, totalWeight_double = 0, price_double = 0;
                            try {
                                String text = String.valueOf(Modal_Static_GoatEarTagDetails.getMeatyieldweight());
                                text = text.replaceAll("[^\\d.]", "");
                                if (text.equals("")) {
                                    text = "0";
                                }
                                meatYield_double = Double.parseDouble(text);
                            } catch (Exception e) {
                                meatYield_double = 0;
                                e.printStackTrace();
                            }


                            try {
                                String text = String.valueOf(Modal_Static_GoatEarTagDetails.getPartsweight());
                                text = text.replaceAll("[^\\d.]", "");
                                if (text.equals("")) {
                                    text = "0";
                                }
                                parts_double = Double.parseDouble(text);
                            } catch (Exception e) {
                                meatYield_double = 0;
                                e.printStackTrace();
                            }

                            try {
                                totalWeight_double = meatYield_double + parts_double;
                            } catch (Exception e) {
                                totalWeight_double = 0;
                                e.printStackTrace();
                            }


                            try {
                                String text = String.valueOf(Modal_Static_GoatEarTagDetails.getItemPrice());
                                text = text.replaceAll("[^\\d.]", "");
                                if (text.equals("")) {
                                    text = "0";
                                }
                                price_double = Double.parseDouble(text);

                                if (price_double == 0) {
                                    try {
                                        text = String.valueOf(Modal_Static_GoatEarTagDetails.getTotalPrice_ofItem());
                                        text = text.replaceAll("[^\\d.]", "");
                                        if (text.equals("")) {
                                            text = "0";
                                        }
                                        price_double = Double.parseDouble(text);

                                    } catch (Exception e) {
                                        price_double = 0;
                                        e.printStackTrace();
                                    }
                                }
                            }
                            catch (Exception e) {
                                price_double = 0;
                                if (price_double == 0) {
                                    try {
                                        String text = String.valueOf(Modal_Static_GoatEarTagDetails.getTotalPrice_ofItem());
                                        text = text.replaceAll("[^\\d.]", "");
                                        if (text.equals("")) {
                                            text = "0";
                                        }
                                        price_double = Double.parseDouble(text);

                                    } catch (Exception e1) {
                                        price_double = 0;
                                        e1.printStackTrace();
                                    }
                                }
                                e.printStackTrace();
                            }


                            totalweight_textview.setText(String.valueOf(totalWeight_double));

                            totalPrice_textView.setText(String.valueOf(price_double));
                            priceWithoutDiscount_edittext.setText(String.valueOf(price_double));

                            approxLiveWeight_EditText.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getApproxliveweight()));
                            parts_textView.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getPartsweight()));
                            meatyeild_edittext.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getMeatyieldweight()));
                            discount_edittext.setText(String.valueOf("0"));
                            parts_editText.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getPartsweight()));

                        }

                        loadingpanelmask_in_dialog.setVisibility(View.GONE);
                        loadingPanel_in_dialog.setVisibility(View.GONE);

                    }
                    else {
                        //  show_scan_barcode_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        show_goatEarTagItemDetails_Dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                      //  show_goatEarTagItemDetails_Dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

                        try{
                            show_goatEarTagItemDetails_Dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                        try {
                            BaseActivity.baseActivity.getDeviceName();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            if (BaseActivity.isDeviceIsMobilePhone) {
                                show_goatEarTagItemDetails_Dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);


                            } else {

                                show_goatEarTagItemDetails_Dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

                            }
                        } catch (Exception e) {
                            show_goatEarTagItemDetails_Dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

                            e.printStackTrace();

                        }



                        try {
                            if (BaseActivity.isDeviceIsMobilePhone) {
                                show_goatEarTagItemDetails_Dialog.setContentView(R.layout.fragment_goat_item_details__second_versn);


                            } else {

                                show_goatEarTagItemDetails_Dialog.setContentView(R.layout.pos_fragment_goat_item_details__second_versn);
                            }

                        } catch (Exception e) {
                            show_goatEarTagItemDetails_Dialog.setContentView(R.layout.fragment_goat_item_details__second_versn);
                            e.printStackTrace();
                        }


                        // show_scan_barcode_dialog.setCancelable(false);
                        show_goatEarTagItemDetails_Dialog.setCanceledOnTouchOutside(false);





                        barcodeNo_textView = show_goatEarTagItemDetails_Dialog.findViewById(R.id.barcodeNo_textView);
                         gradeName_textview = show_goatEarTagItemDetails_Dialog.findViewById(R.id.gradeName_textview);
                         gender_textview = show_goatEarTagItemDetails_Dialog.findViewById(R.id.gender_textview);
                         totalweight_textview = show_goatEarTagItemDetails_Dialog.findViewById(R.id.totalweight_textview);
                         totalPrice_textView = show_goatEarTagItemDetails_Dialog.findViewById(R.id.totalPrice_textView);


                         selectedGoatStatus_textview = show_goatEarTagItemDetails_Dialog.findViewById(R.id.selectedGoatStatus_textview);
                         approxLiveWeight_textview = show_goatEarTagItemDetails_Dialog.findViewById(R.id.approxLiveWeight_textview);
                         meatyeild_textview = show_goatEarTagItemDetails_Dialog.findViewById(R.id.meatyeild_textview);
                         parts_textView = show_goatEarTagItemDetails_Dialog.findViewById(R.id.parts_textView);
                         priceWithoutDiscount_textview = show_goatEarTagItemDetails_Dialog.findViewById(R.id.priceWithoutDiscount_textview);
                         discount_textView = show_goatEarTagItemDetails_Dialog.findViewById(R.id.discount_textView);

                         barcode_editText =  show_goatEarTagItemDetails_Dialog.findViewById(R.id.barcode_editText);
                         approxLiveWeight_EditText = show_goatEarTagItemDetails_Dialog.findViewById(R.id.approxLiveWeight_EditText);
                         parts_editText = show_goatEarTagItemDetails_Dialog.findViewById(R.id.parts_editText);
                         discount_edittext = show_goatEarTagItemDetails_Dialog.findViewById(R.id.discount_edittext);
                         meatyeild_edittext = show_goatEarTagItemDetails_Dialog.findViewById(R.id.meatyeild_edittext);
                         gradeName_editText = show_goatEarTagItemDetails_Dialog.findViewById(R.id.gradeName_editText);
                         totalPrice_edittext = show_goatEarTagItemDetails_Dialog.findViewById(R.id.totalPrice_edittext);
                         priceWithoutDiscount_edittext = show_goatEarTagItemDetails_Dialog.findViewById(R.id.priceWithoutDiscount_edittext);


                         save_button = show_goatEarTagItemDetails_Dialog.findViewById(R.id.save_button);

                        back_IconLayout_goatEarTagItemdialog = show_goatEarTagItemDetails_Dialog.findViewById(R.id.back_IconLayout);
                         discount_price_layout_label = show_goatEarTagItemDetails_Dialog.findViewById(R.id.discount_price_layout_label);

                         goatstatus_layout = show_goatEarTagItemDetails_Dialog.findViewById(R.id.goatstatus_layout);

                        loadingPanel_in_dialog = show_goatEarTagItemDetails_Dialog.findViewById(R.id.loadingPanel_in_dialog);
                        loadingpanelmask_in_dialog = show_goatEarTagItemDetails_Dialog.findViewById(R.id.loadingpanelmask_in_dialog);
                         genderRadioGroup = show_goatEarTagItemDetails_Dialog.findViewById(R.id.genderRadioGroup);
                         male_radioButton = show_goatEarTagItemDetails_Dialog.findViewById(R.id.male_radioButton);
                         female_radioButton = show_goatEarTagItemDetails_Dialog.findViewById(R.id.female_radioButton);


                         goatstatusradiogrp = show_goatEarTagItemDetails_Dialog.findViewById(R.id.goatstatusradiogrp);
                         normal_goat_radio = show_goatEarTagItemDetails_Dialog.findViewById(R.id.normal_goat_radio);
                         dead_goat_radio = show_goatEarTagItemDetails_Dialog.findViewById(R.id.dead_goat_radio);
                         sick_goat_radio = show_goatEarTagItemDetails_Dialog.findViewById(R.id.sick_goat_radio);


                        goatstatus_layout.setVisibility(View.GONE);

                        approx_Live_Weight_double = 0;
                        meatyeild_weight_double = 0;
                        parts_Weight_double = 0;
                        totalWeight_double = 0;
                        discountDouble = 0;
                        totalPrice_double = 0;


                        show_goatEarTagItemDetails_Dialog.show();


                        if (Modal_Static_GoatEarTagDetails.getBarcodeno().equals("") || Modal_Static_GoatEarTagDetails.getBarcodeno().equals(null)) {
                            // show_goatEarTagItemDetails_Dialog.cancel();
                            // Toast.makeText(mContext, "Please Scan / Type Barcode Again", Toast.LENGTH_SHORT).show();

                            barcode_editText.setVisibility(View.VISIBLE);
                            barcodeNo_textView.setVisibility(View.GONE);


                            gradeName_editText.setVisibility(View.GONE);
                            gradeName_textview.setVisibility(View.VISIBLE);
                            genderRadioGroup.setVisibility(View.GONE);
                            gender_textview.setVisibility(View.VISIBLE);
                            selectedGoatStatus_textview.setVisibility(View.VISIBLE);
                            goatstatusradiogrp.setVisibility(View.GONE);
                            approxLiveWeight_textview.setVisibility(View.VISIBLE);
                            approxLiveWeight_EditText.setVisibility(View.GONE);

                            totalPrice_edittext.setVisibility(View.GONE);
                            totalPrice_textView.setVisibility(View.GONE);


                            meatyeild_textview.setVisibility(View.VISIBLE);
                            meatyeild_edittext.setVisibility(View.GONE);


                            parts_editText.setVisibility(View.GONE);
                            parts_textView.setVisibility(View.VISIBLE);

                            priceWithoutDiscount_edittext.setVisibility(View.GONE);
                            priceWithoutDiscount_textview.setVisibility(View.VISIBLE);

                            discount_edittext.setVisibility(View.GONE);
                            discount_textView.setVisibility(View.GONE);


                            selectedGoatStatus_textview.setText(Constants.goatEarTagStatus_Sold);
                            save_button.setVisibility(View.GONE);


                            goatstatus_layout.setVisibility(View.GONE);
                        }
                        else {


                            save_button.setText(String.valueOf("Save"));

                            barcodeNo_textView.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getBarcodeno()));
                            gradeName_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getGradename()));
                            gender_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getGender()));
                            gender = String.valueOf(Modal_Static_GoatEarTagDetails.getGender());
                            double meatYield_double = 0, parts_double = 0, totalWeight_double = 0, price_double = 0;
                            try {
                                String text = String.valueOf(Modal_Static_GoatEarTagDetails.getMeatyieldweight());
                                text = text.replaceAll("[^\\d.]", "");
                                if (text.equals("")) {
                                    text = "0";
                                }
                                meatYield_double = Double.parseDouble(text);
                            } catch (Exception e) {
                                meatYield_double = 0;
                                e.printStackTrace();
                            }


                            try {
                                String text = String.valueOf(Modal_Static_GoatEarTagDetails.getPartsweight());
                                text = text.replaceAll("[^\\d.]", "");
                                if (text.equals("")) {
                                    text = "0";
                                }
                                parts_double = Double.parseDouble(text);
                            } catch (Exception e) {
                                meatYield_double = 0;
                                e.printStackTrace();
                            }

                            try {
                                totalWeight_double = meatYield_double + parts_double;
                            } catch (Exception e) {
                                totalWeight_double = 0;
                                e.printStackTrace();
                            }


                            try {
                                String text = String.valueOf(Modal_Static_GoatEarTagDetails.getItemPrice());
                                text = text.replaceAll("[^\\d.]", "");
                                if (text.equals("")) {
                                    text = "0";
                                }
                                price_double = Double.parseDouble(text);

                                if (price_double == 0) {
                                    try {
                                        text = String.valueOf(Modal_Static_GoatEarTagDetails.getTotalPrice_ofItem());
                                        text = text.replaceAll("[^\\d.]", "");
                                        if (text.equals("")) {
                                            text = "0";
                                        }
                                        price_double = Double.parseDouble(text);

                                    } catch (Exception e) {
                                        price_double = 0;
                                        e.printStackTrace();
                                    }
                                }
                            } catch (Exception e) {
                                price_double = 0;
                                if (price_double == 0) {
                                    try {
                                        String text = String.valueOf(Modal_Static_GoatEarTagDetails.getTotalPrice_ofItem());
                                        text = text.replaceAll("[^\\d.]", "");
                                        if (text.equals("")) {
                                            text = "0";
                                        }
                                        price_double = Double.parseDouble(text);

                                    } catch (Exception e1) {
                                        price_double = 0;
                                        e1.printStackTrace();
                                    }
                                }
                                e.printStackTrace();
                            }


                            totalweight_textview.setText(String.valueOf(totalWeight_double));

                            totalPrice_textView.setText(String.valueOf(price_double));
                            priceWithoutDiscount_edittext.setText(String.valueOf(price_double));

                            approxLiveWeight_EditText.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getApproxliveweight()));
                            parts_textView.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getPartsweight()));
                            meatyeild_edittext.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getMeatyieldweight()));
                            discount_edittext.setText(String.valueOf("0"));
                            parts_editText.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getPartsweight()));

                        }

                    }


                    back_IconLayout_goatEarTagItemdialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            show_goatEarTagItemDetails_Dialog.cancel();
                            try {
                                //DeliveryCenterDashboardScreen.loadingpanelmask.setVisibility(GONE);
                                // DeliveryCenterDashboardScreen.loadingpanelmask.setAlpha((float) 0.7);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });


                    save_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            try {
                                String text = String.valueOf(approxLiveWeight_EditText.getText().toString());
                                text = text.replaceAll("[^\\d.]", "");
                                if (text.equals("")) {
                                    text = "0";
                                }
                                approx_Live_Weight_double = Double.parseDouble(text);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                String text = String.valueOf(meatyeild_edittext.getText().toString());
                                text = text.replaceAll("[^\\d.]", "");
                                if (text.equals("")) {
                                    text = "0";
                                }
                                meatyeild_weight_double = Double.parseDouble(text);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                String text = String.valueOf(parts_editText.getText().toString());
                                text = text.replaceAll("[^\\d.]", "");
                                if (text.equals("")) {
                                    text = "0";
                                }
                                parts_Weight_double = Double.parseDouble(text);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                String text = String.valueOf(priceWithoutDiscount_edittext.getText().toString());
                                text = text.replaceAll("[^\\d.]", "");
                                if (text.equals("")) {
                                    text = "0";
                                }
                                pricewithOutdiscount_double = Double.parseDouble(text);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                String text = String.valueOf(discount_edittext.getText().toString());
                                text = text.replaceAll("[^\\d.]", "");
                                if (text.equals("")) {
                                    text = "0";
                                }

                                discountDouble = Double.parseDouble(text);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {

                                totalWeight_double = meatyeild_weight_double + parts_Weight_double;

                                totalPrice_double = pricewithOutdiscount_double - discountDouble;


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                if (approx_Live_Weight_double > 0) {
                                    if (parts_Weight_double > 0) {
                                        if (meatyeild_weight_double > 0) {
                                            if (pricewithOutdiscount_double > 0) {
                                                if (totalPrice_double > 0) {
                                                    if (totalWeight_double > 0) {
                                                        if (scannedBarcode.length() > 0) {

                                                           /* Modal_Static_GoatEarTagDetails.setApproxliveweight(String.valueOf(approx_Live_Weight_double));
                                                            Modal_Static_GoatEarTagDetails.setDiscount(String.valueOf(discountDouble));
                                                            Modal_Static_GoatEarTagDetails.setMeatyieldweight(String.valueOf(meatyeild_weight_double));
                                                            Modal_Static_GoatEarTagDetails.setPartsweight(String.valueOf(parts_Weight_double));
                                                            Modal_Static_GoatEarTagDetails.setTotalPrice_ofItem(String.valueOf(totalPrice_double));
                                                            Modal_Static_GoatEarTagDetails.gender = String.valueOf(gender_textview.getText());
                                                            Modal_Static_GoatEarTagDetails.barcodeno = String.valueOf(barcodeNo_textView.getText());
                                                            Modal_Static_GoatEarTagDetails.itemPrice = String.valueOf(pricewithOutdiscount_double);
                                                            Modal_Static_GoatEarTagDetails.itemWeight = String.valueOf(totalWeight_double);


                                                            */

                                                            Modal_UpdatedGoatEarTagDetails.setUpdated_approxliveweight(String.valueOf(approx_Live_Weight_double));
                                                            Modal_UpdatedGoatEarTagDetails.setUpdated_discount(String.valueOf(discountDouble));
                                                            Modal_UpdatedGoatEarTagDetails.setUpdated_meatyieldweight(String.valueOf(meatyeild_weight_double));
                                                            Modal_UpdatedGoatEarTagDetails.setUpdated_partsweight(String.valueOf(parts_Weight_double));
                                                            Modal_UpdatedGoatEarTagDetails.setUpdated_totalPrice_ofItem(String.valueOf(totalPrice_double));
                                                            Modal_UpdatedGoatEarTagDetails.setUpdated_gender(String.valueOf(gender_textview.getText()));
                                                            Modal_UpdatedGoatEarTagDetails.setUpdated_barcodeno(String.valueOf(barcodeNo_textView.getText()));
                                                            Modal_UpdatedGoatEarTagDetails.setUpdated_Price(String.valueOf(pricewithOutdiscount_double));
                                                            Modal_UpdatedGoatEarTagDetails.setUpdated_totalWeight(String.valueOf(totalWeight_double));


                                                            try {
                                                                showProgressBar(true);
                                                                show_goatEarTagItemDetails_Dialog.cancel();
                                                                show_goatEarTagItemDetails_Dialog.dismiss();
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                            //   Intialize_and_ExecuteInB2BCartItemDetails(Constants.CallADDMethod,false);
                                                            Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod, scannedBarcode, isCalledForPlaceNewOrder);


                                                        } else {
                                                            AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWithOutBarcodeAlert);

                                                        }
                                                    } else {
                                                        AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWhenTotalWeightisZeroAlert);

                                                    }
                                                } else {
                                                    AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWhenTotalPriceisZeroAlert);

                                                }
                                            } else {
                                                AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWhenTotalPriceWithOutDiscountisZeroAlert);

                                            }
                                        } else {
                                            AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWhenMeatYieldisZeroAlert);

                                        }

                                    } else {
                                        AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWhenPartsisZeroAlert);

                                    }
                                } else {
                                    AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWhenApproxWeightisZeroAlert);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    });


                    parts_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {


                            try {

                                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                                    //do what you want on the press of 'done'


                                    try {
                                        String text = String.valueOf(meatyeild_edittext.getText());
                                        text = text.replaceAll("[^\\d.]", "");
                                        if (text.equals("")) {
                                            text = "0";
                                        }
                                        meatyeild_weight_double = Double.parseDouble(text);
                                    } catch (Exception e) {
                                        meatyeild_weight_double = 0;
                                        e.printStackTrace();
                                    }


                                    try {
                                        String text = String.valueOf(parts_editText.getText());
                                        text = text.replaceAll("[^\\d.]", "");
                                        if (text.equals("")) {
                                            text = "0";
                                        }
                                        parts_Weight_double = Double.parseDouble(text);
                                    } catch (Exception e) {
                                        parts_Weight_double = 0;
                                        e.printStackTrace();
                                    }


                                    if (meatyeild_weight_double > 0) {
                                        if (parts_Weight_double > 0) {
                                            try {
                                                totalWeight_double = meatyeild_weight_double + parts_Weight_double;
                                            } catch (Exception e) {
                                                totalWeight_double = 0;
                                                e.printStackTrace();
                                            }

                                            totalweight_textview.setText(String.valueOf(totalWeight_double));
                                            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                            Objects.requireNonNull(imm).hideSoftInputFromWindow(textView.getWindowToken(), 0);


                                        } else {
                                            AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWhenPartsisZeroAlert);

                                        }
                                    } else {
                                        AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWhenMeatYieldisZeroAlert);

                                    }


                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            return false;
                        }
                    });

                    meatyeild_edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {


                            try {

                                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                                    //do what you want on the press of 'done'


                                    try {
                                        String text = String.valueOf(meatyeild_edittext.getText());
                                        text = text.replaceAll("[^\\d.]", "");
                                        if (text.equals("")) {
                                            text = "0";
                                        }
                                        meatyeild_weight_double = Double.parseDouble(text);
                                    } catch (Exception e) {
                                        meatyeild_weight_double = 0;
                                        e.printStackTrace();
                                    }


                                    try {
                                        String text = String.valueOf(parts_editText.getText());
                                        text = text.replaceAll("[^\\d.]", "");
                                        if (text.equals("")) {
                                            text = "0";
                                        }
                                        parts_Weight_double = Double.parseDouble(text);
                                    } catch (Exception e) {
                                        parts_Weight_double = 0;
                                        e.printStackTrace();
                                    }


                                    if (meatyeild_weight_double > 0) {
                                        if (parts_Weight_double > 0) {
                                            try {
                                                totalWeight_double = meatyeild_weight_double + parts_Weight_double;
                                            } catch (Exception e) {
                                                totalWeight_double = 0;
                                                e.printStackTrace();
                                            }

                                            totalweight_textview.setText(String.valueOf(totalWeight_double));
                                            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                            Objects.requireNonNull(imm).hideSoftInputFromWindow(textView.getWindowToken(), 0);


                                        } else {
                                            AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWhenPartsisZeroAlert);

                                        }
                                    } else {
                                        AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWhenMeatYieldisZeroAlert);

                                    }


                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            return false;
                        }
                    });

                    discount_edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {


                            try {

                                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                                    //do what you want on the press of 'done'


                                    try {
                                        String text = String.valueOf(discount_edittext.getText());
                                        text = text.replaceAll("[^\\d.]", "");
                                        if (text.equals("")) {
                                            text = "0";
                                        }
                                        discountDouble = Double.parseDouble(text);
                                    } catch (Exception e) {
                                        discountDouble = 0;
                                        e.printStackTrace();
                                    }


                                    try {
                                        String text = String.valueOf(priceWithoutDiscount_edittext.getText());
                                        text = text.replaceAll("[^\\d.]", "");
                                        if (text.equals("")) {
                                            text = "0";
                                        }
                                        pricewithOutdiscount_double = Double.parseDouble(text);
                                    } catch (Exception e) {
                                        pricewithOutdiscount_double = 0;
                                        e.printStackTrace();
                                    }
                                    if (discountDouble <= pricewithOutdiscount_double) {
                                        if (pricewithOutdiscount_double > 0) {
                                            try {
                                                totalPrice_double = pricewithOutdiscount_double - discountDouble;
                                            } catch (Exception e) {
                                                totalWeight_double = 0;
                                                e.printStackTrace();
                                            }


                                            if (totalPrice_double > 0) {
                                                totalPrice_textView.setText(String.valueOf(totalPrice_double));
                                                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                Objects.requireNonNull(imm).hideSoftInputFromWindow(textView.getWindowToken(), 0);

                                            } else {
                                                AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWhenTotalPriceisZeroAlert);

                                            }
                                        } else {
                                            AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWhenTotalPriceWithOutDiscountisZeroAlert);

                                        }
                                    }
                                    else {
                                        AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWhendiscountisgreaterthanpriceisZeroAlert);

                                    }


                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            return false;
                        }
                    });

                    priceWithoutDiscount_edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {


                            try {

                                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                                    //do what you want on the press of 'done'


                                    try {
                                        String text = String.valueOf(discount_edittext.getText());
                                        text = text.replaceAll("[^\\d.]", "");
                                        if (text.equals("")) {
                                            text = "0";
                                        }
                                        discountDouble = Double.parseDouble(text);
                                    } catch (Exception e) {
                                        discountDouble = 0;
                                        e.printStackTrace();
                                    }


                                    try {
                                        String text = String.valueOf(priceWithoutDiscount_edittext.getText());
                                        text = text.replaceAll("[^\\d.]", "");
                                        if (text.equals("")) {
                                            text = "0";
                                        }
                                        pricewithOutdiscount_double = Double.parseDouble(text);
                                    } catch (Exception e) {
                                        pricewithOutdiscount_double = 0;
                                        e.printStackTrace();
                                    }

                                    if (discountDouble <= pricewithOutdiscount_double) {
                                        if (pricewithOutdiscount_double > 0) {
                                            try {
                                                totalPrice_double = pricewithOutdiscount_double - discountDouble;
                                            } catch (Exception e) {
                                                totalWeight_double = 0;
                                                e.printStackTrace();
                                            }


                                            if (totalPrice_double > 0) {
                                                totalPrice_textView.setText(String.valueOf(totalPrice_double));
                                                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                Objects.requireNonNull(imm).hideSoftInputFromWindow(textView.getWindowToken(), 0);

                                            } else {
                                                AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWhenTotalPriceisZeroAlert);

                                            }
                                        } else {
                                            AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWhenTotalPriceWithOutDiscountisZeroAlert);

                                        }
                                    } else {
                                        AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWhendiscountisgreaterthanpriceisZeroAlert);

                                    }


                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            return false;
                        }
                    });

                    barcode_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                            loadingpanelmask_in_dialog.setVisibility(View.VISIBLE);
                            loadingPanel_in_dialog.setVisibility(View.VISIBLE);

                            scannedBarcode = barcode_editText.getText().toString().toUpperCase();






                            if (isCalledForPlaceNewOrder) {
                                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod, scannedBarcode, isCalledForPlaceNewOrder);

                            } else {
                                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod, barcodeFromStringArray, false);
                            }
                            return false;
                        }
                    });



                    }
                catch(WindowManager.BadTokenException e){


                        e.printStackTrace();
                    }
                }

        });


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
                setAdapterForRetailerDetails();

          /*      if(updateCartOrderDetails){
                    for(int i =0 ;i < retailerDetailsArrayList.size(); i++){
                        if(retailerDetailsArrayList.contains(mobilenoToUpdate)){
                            if (DeliveryCenter_PlaceOrderScreen_SecondVersn.isRetailerSelected) {

                                DeliveryCenter_PlaceOrderScreen_SecondVersn.oldRetailerKey = DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerKey;
                                DeliveryCenter_PlaceOrderScreen_SecondVersn.oldretailerMobileno = DeliveryCenter_PlaceOrderScreen_SecondVersn.retailermobileno;
                                DeliveryCenter_PlaceOrderScreen_SecondVersn.oldRetailerName = DeliveryCenter_PlaceOrderScreen_SecondVersn.retailername;
                                DeliveryCenter_PlaceOrderScreen_SecondVersn.oldretaileraddress = DeliveryCenter_PlaceOrderScreen_SecondVersn.retaileraddress;
                                DeliveryCenter_PlaceOrderScreen_SecondVersn.oldretailerGSTIN = DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerGSTIN;


                            } else {
                                DeliveryCenter_PlaceOrderScreen_SecondVersn.isRetailerSelected = true;
                            }


                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retailername = String.valueOf(retailerDetailsArrayList.get(i).getRetailername());
                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerKey = String.valueOf(retailerDetailsArrayList.get(i).getRetailerkey());
                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retailermobileno = String.valueOf(retailerDetailsArrayList.get(i).getMobileno());
                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retaileraddress = String.valueOf(retailerDetailsArrayList.get(i).getAddress());
                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerGSTIN = String.valueOf(retailerDetailsArrayList.get(i).getGstin());
                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerMobileNo_edittext.setText(retailerDetailsArrayList.get(i).getMobileno());
                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerMobileNo_edittext.clearFocus();
                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerName_textView.setText(retailerDetailsArrayList.get(i).getRetailername());
                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerName_textView.clearFocus();
                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerAddress_textView.setText(retailerDetailsArrayList.get(i).getAddress());
                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerAddress_textView.clearFocus();


                            showAlert_toUpdateCartOrderDetails("retailer");


                        }
                    }
                }

           */

                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 1 " + DateParser.getDate_and_time_newFormat());

            }

            @Override
            public void notifySuccess(String result) {
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 2 " + DateParser.getDate_and_time_newFormat());
                if(result.equals(Constants.item_Already_Added_volley)){
                    AlertDialogClass.showDialog(getActivity(), R.string.retailersAlreadyCreated_Instruction);
                    isRetailerDetailsServiceCalled = false;
                    showProgressBar(false);

                }
                else if(result.equals(Constants.successResult_volley)){
                    retailerDetailsArrayList = DatabaseArrayList_PojoClass.retailerDetailsArrayList;

                    for(int i =0 ;i < retailerDetailsArrayList.size(); i++) {
                        if(retailerDetailsArrayList.get(i).getMobileno().contains(retailers_mobileno)) {
                            if (DeliveryCenter_PlaceOrderScreen_SecondVersn.isRetailerSelected) {

                                DeliveryCenter_PlaceOrderScreen_SecondVersn.oldRetailerKey = DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerKey;
                                DeliveryCenter_PlaceOrderScreen_SecondVersn.oldretailerMobileno = DeliveryCenter_PlaceOrderScreen_SecondVersn.retailermobileno;
                                DeliveryCenter_PlaceOrderScreen_SecondVersn.oldRetailerName = DeliveryCenter_PlaceOrderScreen_SecondVersn.retailername;
                                DeliveryCenter_PlaceOrderScreen_SecondVersn.oldretaileraddress = DeliveryCenter_PlaceOrderScreen_SecondVersn.retaileraddress;
                                DeliveryCenter_PlaceOrderScreen_SecondVersn.oldretailerGSTIN = DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerGSTIN;


                            } else {
                                DeliveryCenter_PlaceOrderScreen_SecondVersn.isRetailerSelected = true;
                            }


                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retailername = String.valueOf(retailerDetailsArrayList.get(i).getRetailername());
                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerKey = String.valueOf(retailerDetailsArrayList.get(i).getRetailerkey());
                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retailermobileno = String.valueOf(retailerDetailsArrayList.get(i).getMobileno());
                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retaileraddress = String.valueOf(retailerDetailsArrayList.get(i).getAddress());
                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerGSTIN = String.valueOf(retailerDetailsArrayList.get(i).getGstin());
                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerMobileNo_edittext.setText(retailerDetailsArrayList.get(i).getMobileno());
                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerMobileNo_edittext.clearFocus();
                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerName_textView.setText(retailerDetailsArrayList.get(i).getRetailername());
                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerName_textView.clearFocus();
                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerAddress_textView.setText(retailerDetailsArrayList.get(i).getAddress());
                            DeliveryCenter_PlaceOrderScreen_SecondVersn.retailerAddress_textView.clearFocus();


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
                    // ((BillingScreen)getActivity()).closeFragment();
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
            Modal_B2BRetailerDetails.setAddress_static(retailers_address);
            Modal_B2BRetailerDetails.setMobileno_static(retailers_mobileno);
            Modal_B2BRetailerDetails.setRetailername_static(retailers_name);
            Modal_B2BRetailerDetails.setDeliveryCenterKey_static(deliveryCenterKey);
            Modal_B2BRetailerDetails.setDeliveryCenterName_static(deliveryCenterName);
            Modal_B2BRetailerDetails.setGstin_static(gstin);

            String getApiToCall = API_Manager.addRetailerDetailsList ;

            B2BRetailerDetails asyncTask = new B2BRetailerDetails(callback_retailerDetailsInterface,  getApiToCall, Constants.CallADDMethod);
            asyncTask.execute();
        }
        else  if(CallMethod.equals(Constants.CallGETListMethod)){
            String getApiToCall = API_Manager.getretailerDetailsListWithDeliveryCentreKey+deliveryCenterKey  ;

            B2BRetailerDetails asyncTask = new B2BRetailerDetails(callback_retailerDetailsInterface,  getApiToCall, Constants.CallGETListMethod);
            asyncTask.execute();
        }





    }

    private void setAdapterForRetailerDetails() {
        try {
            adapter_autoComplete_retailerMobileNo = new Adapter_AutoComplete_RetailerMobileNo(mContext, retailerDetailsArrayList, DeliveryCenter_PlaceOrderScreen_SecondVersn.this);
            adapter_autoComplete_retailerMobileNo.setHandler(newHandler());


            retailerMobileNo_edittext.setAdapter(adapter_autoComplete_retailerMobileNo);
            retailerMobileNo_edittext.clearFocus();
            retailerMobileNo_edittext.setThreshold(1);
            retailerMobileNo_edittext.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_CLASS_NUMBER);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }




    public static void showProgressBar(boolean show) {

        if(show){
            loadingPanel.setVisibility(View.VISIBLE);
            loadingpanelmask.setVisibility(View.VISIBLE);

        }
        else {

            loadingPanel.setVisibility(View.GONE);
            loadingpanelmask.setVisibility(View.GONE);
            try {
                if(show_goatEarTagItemDetails_Dialog.isShowing()) {
                    loadingpanelmask_in_dialog.setVisibility(View.GONE);
                    loadingPanel_in_dialog.setVisibility(View.GONE);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

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
                            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            Objects.requireNonNull(imm).hideSoftInputFromWindow(retailerMobileNo_edittext.getWindowToken(), 0);

                            retailerMobileNo_edittext.clearFocus();

                            retailerMobileNo_edittext.dismissDropDown();


                            try {
                                showAlert_toUpdateCartOrderDetails("Retailer");
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }

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



    public void loadMyFragment() {

        if(isTransactionSafe) {
            isTransactionPending=false;

            if (mfragment != null) {
                Fragment fragment = null;
                frameLayout_for_Fragment.setVisibility(View.GONE);
                try {
                    fragment = requireActivity().getSupportFragmentManager().findFragmentById(frameLayout_for_Fragment.getId());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                if(fragment!=null) {
                    try {
                        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.remove(fragment);
                        fragmentTransaction.commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                try {

                    FragmentTransaction transaction2 = requireActivity().getSupportFragmentManager().beginTransaction()
                            .addToBackStack(null);
                    if(value_forFragment.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion))){
                        transaction2.replace(frameLayout_for_Fragment.getId(), GoatItemDetailsFragment_SecondVersn.newInstance(getString(R.string.called_from), value_forFragment));

                    }
                    else if(value_forFragment.equals(getString(R.string.pos_placedOrder_Details_Screen_SecondVersion))){
                        transaction2.replace(frameLayout_for_Fragment.getId(), GoatItemDetailsFragment_SecondVersn.newInstance(getString(R.string.called_from), value_forFragment));

                    }
                    else if(value_forFragment.equals(getString(R.string.billing_Screen_placeOrder))){
                        transaction2.replace(frameLayout_for_Fragment.getId(), GoatItemDetailsFragment_SecondVersn.newInstance(getString(R.string.called_from), value_forFragment));

                    }
                    else if(value_forFragment.equals(getString(R.string.pos_billing_Screen_placeOrder))){
                        transaction2.replace(frameLayout_for_Fragment.getId(), GoatItemDetailsFragment_SecondVersn.newInstance(getString(R.string.called_from), value_forFragment));

                    }
                    transaction2.remove(mfragment).commit();
                    topCardView.setVisibility(View.VISIBLE);
                    DeliveryCenterDashboardScreen.deliveryCenterFrame_backgroundMask.setVisibility(View.VISIBLE);
                    DeliveryCenterDashboardScreen.navigationBar_cardView .setVisibility(View.VISIBLE);
                    frameLayout_for_Fragment.setVisibility(View.VISIBLE);


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
                Fragment fragment = requireActivity().getSupportFragmentManager().findFragmentById(frameLayout_for_Fragment.getId());
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();

                fragmentTransaction.remove(fragment);
                fragmentTransaction.commit();

                showProgressBar(false);
                topCardView.setVisibility(View.VISIBLE);
                DeliveryCenterDashboardScreen.deliveryCenterFrame_backgroundMask.setVisibility(View.GONE);
                DeliveryCenterDashboardScreen.navigationBar_cardView .setVisibility(View.VISIBLE);
                frameLayout_for_Fragment.setVisibility(View.GONE);
                setAdapterForRetailerDetails();

            }
            catch (Exception e){
                DeliveryCenterDashboardScreen.deliveryCenterFrame_backgroundMask.setVisibility(View.GONE);

                onResume();
                closeFragment();
                e.printStackTrace();
            }


        }
        else {

            isTransactionPending=true;

        }


    }










}