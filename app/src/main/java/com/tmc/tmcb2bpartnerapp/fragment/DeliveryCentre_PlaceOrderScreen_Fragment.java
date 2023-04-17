package com.tmc.tmcb2bpartnerapp.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import androidx.recyclerview.widget.RecyclerView;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
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
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartItemDetaillsInterface;
import com.tmc.tmcb2bpartnerapp.utils.BarcodeScannerScreen;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.activity.DeliveryCenterDashboardScreen;
import com.tmc.tmcb2bpartnerapp.activity.GoatEarTagItemDetailsList;
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
import static android.content.Context.MODE_PRIVATE;
import static android.os.Build.VERSION.SDK_INT;
import static com.itextpdf.text.BaseColor.GRAY;
import static com.itextpdf.text.BaseColor.LIGHT_GRAY;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeliveryCentre_PlaceOrderScreen_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeliveryCentre_PlaceOrderScreen_Fragment extends Fragment {

    public static AutoCompleteTextView retailerName_autoComplete_Edittext;
    Button addRetailer_button,checkOut_Button , addItemInCart_Button ,viewItemInCart_Button ,checkoutFromCart_Button ;
    public static  FrameLayout frameLayout_for_Fragment;
    Fragment mfragment;
    RecyclerView cartItem_recyclerView;
    static LinearLayout loadingPanel ,paymentMode_spinnerLayout ;
    static LinearLayout loadingpanelmask,back_IconLayout;
    public static TextView batchNo_textview,totalItem_CountTextview,totalWeight_textview,totalPrice_textview;
    public static EditText discount_editText;
    public static EditText pricePerKg_editText;
    public static ListView gradewisetotalCount_listview;
    public static BarcodeScannerInterface barcodeScannerInterface = null;
    B2BRetailerDetailsInterface callback_retailerDetailsInterface = null;
    GoatEarTagDetailsInterface callback_GoatEarTagDetails = null;
    boolean isB2BItemCtgyTableServiceCalled = false;
    B2BCartOrderDetailsInterface callback_b2bOrderDetails =null ;
    boolean isB2BCartOrderTableServiceCalled = false;
    B2BItemCtgyInterface callback_B2BItemCtgyInterface;
    static DecimalFormat threeDecimalConverter = new DecimalFormat(Constants.threeDecimalPattern);
    static  DecimalFormat twoDecimalConverter = new DecimalFormat(Constants.twoDecimalPattern);
    Spinner paymentMode_spinner;
    CardView topCardView;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch scannerModeSwitch;

    private Handler handler;
    B2BGoatGradeDetailsInterface callback_goatGradeDetailsInterface = null;
    boolean isGoatGradeDetailsServiceCalled = false;


    B2BInvoiceNoManager b2BInvoiceNoManager = null;
    B2BInvoiceNoManagerInterface callback_invoiceManagerInterface = null;


    boolean isGoatEarTagDetailsTableServiceCalled = false;
    boolean isBarcodeScannerServiceCalled = false;
    boolean  isRetailerDetailsServiceCalled = false ;


    public static boolean isScannerModeTurnedOn = false;
    public static boolean isorderSummary_checkoutClicked = false;
    public static  boolean  isRetailerSelected = false ,isRetailerEditedByUser = false ,ispaymentModeSelectedByuser = false ,isAdapter_for_paymentModeSetted =false;
    public static boolean isCartAlreadyCreated = false ,isRetailerUpdated = false ,isPricePerKgUpdated = false , priceperKg_not_edited_byUser = true  ;

    public boolean isTransactionSafe = true;
    public boolean isTransactionPending;

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

    public static HashMap<String, JSONObject> earTagDetails_weightStringHashMap = new HashMap<>();

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
    Context mContext;
    public static DeliveryCentre_PlaceOrderScreen_Fragment deliveryCentre_placeOrderScreen_fragment;




    public DeliveryCentre_PlaceOrderScreen_Fragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static DeliveryCentre_PlaceOrderScreen_Fragment newInstance(String param1, String param2) {
        DeliveryCentre_PlaceOrderScreen_Fragment fragment = new DeliveryCentre_PlaceOrderScreen_Fragment();
        Bundle args = new Bundle();
     //   args.putString(ARG_PARAM1, param1);
     //   args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity().getWindow().getContext();
        if (getArguments() != null) {
        ///    mParam1 = getArguments().getString(ARG_PARAM1);
         //   mParam2 = getArguments().getString(ARG_PARAM2);
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
                return inflater.inflate(R.layout.fragment_delivery_centre__place_order_screen_, container, false);
            } else {

                // Inflate the layout for this fragment
                return inflater.inflate(R.layout.pos_fragment_delivery_centre__place_order_screen_, container, false);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return inflater.inflate(R.layout.fragment_delivery_centre__place_order_screen_, container, false);

        }




    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        retailerName_autoComplete_Edittext = view.findViewById(R.id.receiverName_edittext);
        addRetailer_button = view.findViewById(R.id.addRetailer_button);
        batchNo_textview = view.findViewById(R.id.batchNo_textview);
        totalItem_CountTextview =  view.findViewById(R.id.totalItem_CountTextview);
        totalWeight_textview = view.findViewById(R.id.totalWeight_textview);
        totalPrice_textview = view.findViewById(R.id.totalPrice_textview);
        discount_editText = view.findViewById(R.id.discount_editText);
        pricePerKg_editText =   view.findViewById(R.id.pricePerKg_editText);
        checkOut_Button  =   view.findViewById(R.id.checkOut_Button);
        addItemInCart_Button = view.findViewById(R.id.addItemInCart_Button);
        viewItemInCart_Button = view.findViewById(R.id.viewItemInCart_Button);
        checkoutFromCart_Button = view.findViewById(R.id.checkoutFromCart_Button);
        gradewisetotalCount_listview = view.findViewById(R.id.gradewisetotalCount_listview);
        back_IconLayout  = view.findViewById(R.id.back_IconLayout);
        frameLayout_for_Fragment = view.findViewById(R.id.retailerDetailsFrame);
        loadingpanelmask =  view.findViewById(R.id.loadingpanelmask);
        loadingPanel =  view.findViewById(R.id.loadingPanel);
        cartItem_recyclerView =  view.findViewById(R.id.cartItem_recyclerView);
        paymentMode_spinner =  view.findViewById(R.id.paymentMode_spinner);
        paymentMode_spinnerLayout  = view.findViewById(R.id.paymentMode_spinnerLayout);
        topCardView = view.findViewById(R.id.topCardView);
        scannerModeSwitch = view.findViewById(R.id.scannerModeSwitch);

        deliveryCentre_placeOrderScreen_fragment =this;


        Intent intent = requireActivity().getIntent();
        batchno = intent.getStringExtra(String.valueOf("batchno"));
        batchNo_textview .setText(batchno);

        SharedPreferences sh1 = requireActivity(). getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);

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



        scannerModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if(isChecked){
                    isScannerModeTurnedOn = true;
                    Toast.makeText(mContext, "Turned On", Toast.LENGTH_SHORT).show();
                }
                else{
                    isScannerModeTurnedOn = false;
                    Toast.makeText(mContext, "Turned Off", Toast.LENGTH_SHORT).show();

                }


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
                        try{
                            Initialize_and_StartBarcodeScanner(getString(R.string.scannerCalled_to_FetchData) ,"",true);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                      /*  try {
                            BaseActivity.baseActivity.getDeviceName();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        try {
                            if (BaseActivity.isDeviceIsMobilePhone) {
                                Initialize_and_StartBarcodeScanner(getString(R.string.scannerCalled_to_FetchData) ,"",true);

                            }
                            else{
                                value_forFragment = getString(R.string.pos_billing_Screen_placeOrder);
                                mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                                try{
                                    loadMyFragment();
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                       */

                    }


                }
                else{
                    AlertDialogClass.showDialog(requireActivity(), R.string.Please_Select_Retailer_toAddItem_Instruction);

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

                                requireActivity().runOnUiThread(new Runnable() {

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

                                                AlertDialogClass.showDialog(requireActivity(), R.string.Please_Add_PricePerKg_Instruction);

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

                        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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

                        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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
                            Toast.makeText(mContext, "Please add atleast one item in the Cart", Toast.LENGTH_SHORT).show();
                        }



                    }
                    else{
                        Toast.makeText(mContext, "New Weight cannot be zero / empty. So Please Add weight and click enter for every item ", Toast.LENGTH_SHORT).show();

                    }

                }
                else{
                    AlertDialogClass.showDialog(requireActivity(), R.string.Please_Select_Retailer_Instruction);

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
                            Toast.makeText(mContext, "Please add atleast one item in the Cart", Toast.LENGTH_SHORT).show();
                        }



                    }
                    else{
                        Toast.makeText(mContext, "New Weight cannot be zero / empty. So Please Add weight and click enter for every item ", Toast.LENGTH_SHORT).show();

                    }



                }
                else{
                    AlertDialogClass.showDialog(requireActivity(), R.string.Please_Select_Retailer_Instruction);

                }



                //Create_and_SharePdf();



            }
        });
        viewItemInCart_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, GoatEarTagItemDetailsList.class);
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




        try {
            BaseActivity.baseActivity.getDeviceName();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            if (BaseActivity.isDeviceIsMobilePhone) {
                // Inflate the layout for this fragment
                ArrayAdapter<String> arrayAdapterordertype = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, paymentmode_StringArray);
                arrayAdapterordertype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Objects.requireNonNull(paymentMode_spinner).setAdapter(arrayAdapterordertype);
            } else {

                // Inflate the layout for this fragment
                ArrayAdapter<String> arrayAdapterordertype = new ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_dropdown_item, paymentmode_StringArray);
                arrayAdapterordertype.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                Objects.requireNonNull(paymentMode_spinner).setAdapter(arrayAdapterordertype);            }
        }
        catch (Exception e){
            e.printStackTrace();
            ArrayAdapter<String> arrayAdapterordertype = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, paymentmode_StringArray);
            arrayAdapterordertype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Objects.requireNonNull(paymentMode_spinner).setAdapter(arrayAdapterordertype);

        }



    }

    private void setAdapterForCartItem() {
        try {
            cartItem_recyclerView.setVisibility(View.GONE);

            calculateTotalweight_Quantity_Price();
            showProgressBar(false);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public  void setAdapterForGradewiseTotal() {
        try {
            gradewisetotalCount_listview.setVisibility(View.VISIBLE);
            adapter_gradeWiseTotal_billingScreen = new Adapter_GradeWiseTotal_BillingScreen(mContext, selected_gradeDetailss_arrayList, earTagDetails_JSONFinalSalesHashMap, DeliveryCentre_PlaceOrderScreen_Fragment.this, "");
            gradewisetotalCount_listview.setAdapter(adapter_gradeWiseTotal_billingScreen);

            ListItemSizeSetter.getListViewSize(gradewisetotalCount_listview);

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

                }
                catch (Exception e) {
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

                }
                catch (Exception e) {
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
                    Toast.makeText(requireContext(), "There is no Cart Details for this batch", Toast.LENGTH_SHORT).show();


                } else {
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


    private void getSelectedRetailerDetails(String retailerKeyy) {

        for (int iterator = 0; iterator < retailerDetailsArrayList.size(); iterator++) {
            Modal_B2BRetailerDetails modal_b2BRetailerDetails = retailerDetailsArrayList.get(iterator);

            if (modal_b2BRetailerDetails.getRetailerkey().toUpperCase().equals(retailerKeyy.toUpperCase())) {
                retailername = modal_b2BRetailerDetails.getRetailername();
                retailerKey = modal_b2BRetailerDetails.getRetailerkey();
                retaileraddress = modal_b2BRetailerDetails.getAddress();
                retailermobileno = modal_b2BRetailerDetails.getMobileno();
                retailerGSTIN = modal_b2BRetailerDetails.getGstin();
                retailerName_autoComplete_Edittext.setText(retailername);
            }

        }
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

                add_And_UpdateChangesInBarcodeItemsList(arrayList);




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
                    if(earTagDetailsArrayList_String.contains(modal_b2BCartDetails.getBarcodeno())){

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

                        if(earTagDetails_weightStringHashMap.containsKey(modal_b2BCartDetails.getBarcodeno())) {
                            if (SDK_INT >= Build.VERSION_CODES.N) {
                                Objects.requireNonNull(earTagDetails_weightStringHashMap.replace(modal_b2BCartDetails.getBarcodeno(),jsonObject ));
                            }
                            else{
                                Objects.requireNonNull(earTagDetails_weightStringHashMap.put(modal_b2BCartDetails.getBarcodeno() , jsonObject));
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

                        if(earTagDetails_weightStringHashMap.containsKey(modal_b2BCartDetails.getBarcodeno())) {
                            if (SDK_INT >= Build.VERSION_CODES.N) {
                                Objects.requireNonNull(earTagDetails_weightStringHashMap.replace(modal_b2BCartDetails.getBarcodeno(), jsonObject));
                            }
                            else{
                                Objects.requireNonNull(earTagDetails_weightStringHashMap.put(modal_b2BCartDetails.getBarcodeno() , jsonObject));
                            }                            earTagDetailsArrayList_String.add(modal_b2BCartDetails.getBarcodeno());
                            //  adapter_billingScreen_cartList.notifyDataSetChanged();
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
                       try{
                           Initialize_and_StartBarcodeScanner(getString(R.string.scannerCalled_to_FetchData) ,"",true);

                       }
                       catch (Exception e){
                           e.printStackTrace();
                       }


                    /*      try {
                        BaseActivity.baseActivity.getDeviceName();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        if (BaseActivity.isDeviceIsMobilePhone) {
                            Initialize_and_StartBarcodeScanner(getString(R.string.scannerCalled_to_FetchData) ,"",true);

                        }
                        else{
                            value_forFragment = getString(R.string.pos_billing_Screen_placeOrder);
                            mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                            try{
                                loadMyFragment();
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                        */


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

    public void add_And_UpdateChangesInBarcodeItemsList(ArrayList<Modal_B2BCartItemDetails> arrayList) {
        earTagDetailsArrayList_WholeBatch.clear();

        for(int i =0 ;i< arrayList.size(); i++){
            Modal_B2BCartItemDetails modal_b2BCartItemDetails = arrayList.get(i);
            Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();
            modal_goatEarTagDetails.barcodeno = modal_b2BCartItemDetails.getBarcodeno();
            modal_goatEarTagDetails.batchno = modal_b2BCartItemDetails.getBatchno();
            modal_goatEarTagDetails.status = modal_b2BCartItemDetails.getStatus();
            modal_goatEarTagDetails.b2bctgykey = modal_b2BCartItemDetails.getB2bctgykey();
            modal_goatEarTagDetails.b2bsubctgykey = modal_b2BCartItemDetails.getB2bsubctgykey();
            for(int ctgyIterator =0 ; ctgyIterator < ctgy_subCtgy_DetailsArrayList.size(); ctgyIterator++){
                if(String.valueOf( modal_b2BCartItemDetails.getB2bctgykey()).equals(ctgy_subCtgy_DetailsArrayList.get(ctgyIterator).getKey())){
                    modal_goatEarTagDetails.b2bctgyName =  ctgy_subCtgy_DetailsArrayList.get(ctgyIterator).getName();

                }
                if(String.valueOf( modal_b2BCartItemDetails.getB2bsubctgykey()).equals(ctgy_subCtgy_DetailsArrayList.get(ctgyIterator).getSubctgy_key())){
                    modal_goatEarTagDetails.b2bSubctgyName =  ctgy_subCtgy_DetailsArrayList.get(ctgyIterator).getSubctgy_name();
                }
            }
            modal_goatEarTagDetails.suppliername = modal_b2BCartItemDetails.getSuppliername();
            modal_goatEarTagDetails.supplierkey = modal_b2BCartItemDetails.getSupplierkey();
            //   modal_goatEarTagDetails.description = modal_b2BCartItemDetails.getDescription();
            modal_goatEarTagDetails.itemaddeddate = modal_b2BCartItemDetails.getItemaddeddate();
            modal_goatEarTagDetails.stockedweightingrams = modal_b2BCartItemDetails.getWeightingrams();
            modal_goatEarTagDetails.selecteditem = modal_b2BCartItemDetails.getB2bctgykey();
            modal_goatEarTagDetails.gender = modal_b2BCartItemDetails.getGender();
            modal_goatEarTagDetails.breedtype = modal_b2BCartItemDetails.getBreedtype();
            modal_goatEarTagDetails.loadedweightingrams = modal_b2BCartItemDetails.getWeightingrams();
            modal_goatEarTagDetails.currentweightingrams = modal_b2BCartItemDetails.getWeightingrams();
            modal_goatEarTagDetails.gradeprice = modal_b2BCartItemDetails.getGradeprice();
            modal_goatEarTagDetails.gradename = modal_b2BCartItemDetails.getGradename();
            modal_goatEarTagDetails.gradekey = modal_b2BCartItemDetails.getGradekey();
            modal_goatEarTagDetails.newWeight_forBillingScreen = modal_b2BCartItemDetails.getWeightingrams();


            for(int gradeiterator =0 ; gradeiterator<goatGrade_arrayLsit.size(); gradeiterator ++){
                if(String.valueOf( modal_b2BCartItemDetails.getGradekey()).equals(goatGrade_arrayLsit.get(gradeiterator).getKey())){
                    modal_goatEarTagDetails.gradename =goatGrade_arrayLsit.get(gradeiterator).getName();
                }
            }



            String newgradePriceString = "0",newweightString ="";
            double newgradePriceDouble = 0;



            double newtotalPricedouble = 0,neweightDouble =0;




            newweightString = modal_b2BCartItemDetails.getWeightingrams();
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


            newgradePriceString = modal_b2BCartItemDetails.getGradeprice();
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
                newtotalPricedouble = newgradePriceDouble * neweightDouble;
            } catch (Exception e) {
                if(BillingScreenActivity != null) {
                    Toast.makeText(BillingScreenActivity, "Error from remove item 5: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
                e.printStackTrace();
            }


            try{
                newtotalPricedouble = Double.parseDouble(twoDecimalConverter.format(newtotalPricedouble));
            }
            catch (Exception e){
                e.printStackTrace();
            }


            modal_goatEarTagDetails.setTotalPrice(String.valueOf(newtotalPricedouble));
            earTagDetailsArrayList_WholeBatch.add(modal_goatEarTagDetails);

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
                    Toast.makeText(mContext, "Error in scanning ", Toast.LENGTH_SHORT).show();


                }
            };

            showProgressBar(false);
            Intent intent = new Intent(mContext, BarcodeScannerScreen.class);
            intent.putExtra(getString(R.string.scanner_called_to_do), processtoDOAfterScan);
            intent.putExtra(getString(R.string.called_from), getString(R.string.billing_Screen));
            intent.putExtra(getString(R.string.isScannerModeTurnedOn), isScannerModeTurnedOn);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
           // startActivity(intent);
            startActivityForResult(intent,2);


        }
        catch (Exception e){
            showProgressBar(false);
            e.printStackTrace();
        }


    }


    public static void calculateGradewiseQuantity_and_Weight(Modal_B2BCartItemDetails modal_b2BCartDetails ){

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


                        }
                        else {

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
                        AlertDialogClass.showDialog(requireActivity(), R.string.EarTagDetailsNotFound_Instruction);

                    } catch (Exception e) {
                        Toast.makeText(requireActivity(), getString(R.string.EarTagDetailsNotFound_Instruction), Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
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
                            Toast.makeText(mContext, "Please Scan the New Item", Toast.LENGTH_SHORT).show();

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

                        try {
                            BaseActivity.baseActivity.getDeviceName();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        try {
                            if (BaseActivity.isDeviceIsMobilePhone) {
                                value_forFragment = getString(R.string.billing_Screen_placeOrder);

                            }
                            else{
                                value_forFragment = getString(R.string.pos_billing_Screen_placeOrder);

                            }
                            mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                            try{
                                loadMyFragment();
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
                showProgressBar(false);
                Toast.makeText(mContext, "There is an volley error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                isGoatEarTagDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                Toast.makeText(mContext, "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();

                showProgressBar(false);
                isGoatEarTagDetailsTableServiceCalled = false;


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

        String getApiToCall = API_Manager.getretailerDetailsListWithDeliveryCentreKey+deliveryCenterKey ;
        B2BRetailerDetails asyncTask = new B2BRetailerDetails(callback_retailerDetailsInterface,  getApiToCall, Constants.CallGETListMethod);
        asyncTask.execute();




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

    public void showAlert_toUpdateCartOrderDetails(String updatingvariable) {
        try {
            if(updatingvariable.equals("RetailerName")){
                updatingvariable = getString(R.string.retailername);
            }
            if (isCartAlreadyCreated) {

                if (updatingvariable.equals(getString(R.string.priceperkg))) {

                    if (!priceperKg_not_edited_byUser) {
                        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        Objects.requireNonNull(imm).hideSoftInputFromWindow(pricePerKg_editText.getWindowToken(), 0);


                        new TMCAlertDialogClass(requireActivity(), R.string.app_name, R.string.PleaseConfirmUpdatePricePerKg,
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
                } else if (updatingvariable.equals(getString(R.string.paymentmode))) {


                    new TMCAlertDialogClass(requireActivity(), R.string.app_name, R.string.PleaseConfirmPaymentMode,
                            R.string.Yes_Text, R.string.No_Text,
                            new TMCAlertDialogClass.AlertListener() {
                                @Override
                                public void onYes() {
                                    oldpaymentMode = paymentMode;
                                    paymentMode = updatedpaymentMode;
                                    Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallUPDATEMethod, getString(R.string.paymentmode), false);


                                }

                                @Override
                                public void onNo() {
                                    paymentMode = oldpaymentMode;
                                    updatedpaymentMode = oldpaymentMode;
                                    ispaymentModeSelectedByuser = false;
                                    for (int i = 0; i < paymentmode_StringArray.length; i++) {
                                        if (paymentMode.toUpperCase().equals(paymentmode_StringArray[i].toUpperCase())) {
                                            paymentMode_spinner.setSelection(i);
                                        }
                                    }

                                }
                            });

                } else if (updatingvariable.equals(getString(R.string.retailername))) {


                    try {
                        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        Objects.requireNonNull(imm).hideSoftInputFromWindow(retailerName_autoComplete_Edittext.getWindowToken(), 0);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    new TMCAlertDialogClass(requireActivity(), R.string.app_name, R.string.PleaseConfirmUpdateRetailerName,
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

                } else if (updatingvariable.equals(getString(R.string.retailername_priceperkg))) {

                    if (!priceperKg_not_edited_byUser) {

                        try {

                            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            Objects.requireNonNull(imm).hideSoftInputFromWindow(pricePerKg_editText.getWindowToken(), 0);

                            InputMethodManager imm1 = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            Objects.requireNonNull(imm1).hideSoftInputFromWindow(retailerName_autoComplete_Edittext.getWindowToken(), 0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        new TMCAlertDialogClass(requireActivity(), R.string.app_name, R.string.PleaseConfirmUpdateRetailer_pricePerKg,
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
        catch (Exception e){
            e.printStackTrace();
        }
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
                    if(value_forFragment.equals(getString(R.string.billing_Screen_orderSummary))){
                        transaction2.replace(frameLayout_for_Fragment.getId(), OrderSummary_fragement.newInstance(getString(R.string.called_from), value_forFragment));

                    }
                    else if(value_forFragment.equals(getString(R.string.billing_Screen_retailer))){
                        transaction2.replace(frameLayout_for_Fragment.getId(), AddRetailer_Fragment.newInstance(getString(R.string.called_from), value_forFragment));

                    }
                    else if(value_forFragment.equals(getString(R.string.billing_Screen_placeOrder))){
                        transaction2.replace(frameLayout_for_Fragment.getId(), BatchItemDetailsFragment_withoutScanBarcode.newInstance(getString(R.string.called_from), value_forFragment));

                    }
                    else if(value_forFragment.equals(getString(R.string.pos_billing_Screen_placeOrder))){
                        transaction2.replace(frameLayout_for_Fragment.getId(), BatchItemDetailsFragment_withoutScanBarcode.newInstance(getString(R.string.called_from), value_forFragment));

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

    private void setAdapterForRetailerDetails() {
        try {
            adapter_autoComplete_retailerName = new Adapter_AutoComplete_RetailerName(mContext, retailerDetailsArrayList, DeliveryCentre_PlaceOrderScreen_Fragment.this);
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
                        intent.setData(Uri.parse(String.format("package:%s", requireActivity().getPackageName())));
                        startActivityForResult(intent, 2296);
                    } catch (Exception e) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                        startActivityForResult(intent, 2296);
                    }
                }

            } else {


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


                Phrase phrasepaymentModeLabel = new Phrase(" Payment Mode ", itemNameFont1);

                PdfPCell phrasepaymentModeLabelcell = new PdfPCell(phrasepaymentModeLabel);
                phrasepaymentModeLabelcell.setBorder(Rectangle.NO_BORDER);
                phrasepaymentModeLabelcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasepaymentModeLabelcell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasepaymentModeLabelcell.setPaddingBottom(6);
                billDetails_table.addCell(phrasepaymentModeLabelcell);


                Phrase phrasepaymentModeDetails = new Phrase(": " + paymentMode, itemNameFont1_bold);

                PdfPCell phrasepaymentModeDetailscell = new PdfPCell(phrasepaymentModeDetails);
                phrasepaymentModeDetailscell.setBorder(Rectangle.NO_BORDER);
                phrasepaymentModeDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phrasepaymentModeDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasepaymentModeDetailscell.setPaddingLeft(10);
                phrasepaymentModeDetailscell.setPaddingBottom(6);
                billDetails_table.addCell(phrasepaymentModeDetailscell);


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


            PdfPTable gradewiseItemListWithOutBorder_table = new PdfPTable(8);


            try {
                gradewiseItemListWithOutBorder_table.setWidths(new float[]{2.5f, 6.5f, 4.8f, 4.5f, 3.1f, 4.5f, 5f, 5f});
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

            int item_Count = 0;

            try {
                for (int iterator = 0; iterator < selected_gradeDetailss_arrayList.size(); iterator++) {
                    Modal_B2BGoatGradeDetails modal_b2BGoatGradeDetails = selected_gradeDetailss_arrayList.get(iterator);
                    String totalCount_string = "0";
                    int totalCount_int = 0, itemPrintedCount = 0;

                    //////FOR Male Item
                    //if(gradeWise_count_weightJSONOBJECT.has(modal_b2BGoatGradeDetails.getKey())){
                    if (earTagDetails_JSONFinalSalesHashMap.containsKey(modal_b2BGoatGradeDetails.getKey())) {
                        Modal_POJOClassForFinalSalesHashmap modal_pojoClassForFinalSalesHashmap = earTagDetails_JSONFinalSalesHashMap.get(modal_b2BGoatGradeDetails.getKey());
                        int maleQty = 0, totalqty = 0;
                        double maleWeight = 0, totalweight = 0, maleprice = 0, totalprice = 0;

                        try {
                            maleQty = modal_pojoClassForFinalSalesHashmap.getMaleqty();
                            totalqty = modal_pojoClassForFinalSalesHashmap.getTotalqty();

                            maleWeight = modal_pojoClassForFinalSalesHashmap.getTotalmaleweight();
                            totalweight = modal_pojoClassForFinalSalesHashmap.getTotalweight();

                            maleprice = modal_pojoClassForFinalSalesHashmap.getMaleprice();
                            totalprice = modal_pojoClassForFinalSalesHashmap.getTotalprice();

                        } catch (Exception e) {
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

                            if (maleQty > 0) {
                                itemPrintedCount = maleQty + itemPrintedCount;
                                item_Count = item_Count + 1;
                                Phrase phrasenoDetails = new Phrase(String.valueOf(item_Count), itemNameFontBold);
                                PdfPCell phrasenoDetailscell = new PdfPCell(phrasenoDetails);
                                phrasenoDetailscell.setBorder(Rectangle.NO_BORDER);
                                phrasenoDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phrasenoDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                                phrasenoDetailscell.setBorderWidthRight(01);
                                if (selected_gradeDetailss_arrayList.size() > 1) {
                                    if (iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phrasenoDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
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
                                if (selected_gradeDetailss_arrayList.size() > 1) {
                                    if (iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phrasemalenameDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
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
                                if (selected_gradeDetailss_arrayList.size() > 1) {
                                    if (iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phrasehsnDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
                                        phrasehsnDetailscell.setBorderWidthBottom(01);
                                    }
                                }
                                phrasehsnDetailscell.setBorderColor(LIGHT_GRAY);
                                gradewiseItemListWithOutBorder_table.addCell(phrasehsnDetailscell);


                                Phrase phrasegradenameValueDetails = new Phrase(" " + String.valueOf(modal_b2BGoatGradeDetails.getName()) + " " + " ", itemNameFont);
                                PdfPCell phrasegradenameValueDetailscell = new PdfPCell(phrasegradenameValueDetails);
                                phrasegradenameValueDetailscell.setBorder(Rectangle.NO_BORDER);
                                phrasegradenameValueDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phrasegradenameValueDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                                phrasegradenameValueDetailscell.setPaddingTop(8);
                                phrasegradenameValueDetailscell.setPaddingBottom(8);
                                phrasegradenameValueDetailscell.setPaddingRight(5);
                                phrasegradenameValueDetailscell.setPaddingLeft(5);
                                phrasegradenameValueDetailscell.setBorderWidthRight(01);
                                if (selected_gradeDetailss_arrayList.size() > 1) {
                                    if (iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phrasegradenameValueDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
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
                                if (selected_gradeDetailss_arrayList.size() > 1) {
                                    if (iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phraseQtyDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
                                        phraseQtyDetailscell.setBorderWidthBottom(01);
                                    }
                                }

                                phraseQtyDetailscell.setBorderColor(LIGHT_GRAY);
                                gradewiseItemListWithOutBorder_table.addCell(phraseQtyDetailscell);


                                Phrase phraseWeightValueDetails = new Phrase(" " + String.valueOf(maleWeight) + " Kg" + " ", itemNameFont);
                                PdfPCell phraseWeightValueDetailscell = new PdfPCell(phraseWeightValueDetails);
                                phraseWeightValueDetailscell.setBorder(Rectangle.NO_BORDER);
                                phraseWeightValueDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseWeightValueDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                                phraseWeightValueDetailscell.setPaddingTop(8);
                                phraseWeightValueDetailscell.setPaddingBottom(8);
                                phraseWeightValueDetailscell.setPaddingRight(5);
                                phraseWeightValueDetailscell.setPaddingLeft(5);
                                phraseWeightValueDetailscell.setBorderWidthRight(01);
                                if (selected_gradeDetailss_arrayList.size() > 1) {
                                    if (iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phraseWeightValueDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
                                        phraseWeightValueDetailscell.setBorderWidthBottom(01);
                                    }
                                }

                                phraseWeightValueDetailscell.setBorderColor(LIGHT_GRAY);
                                gradewiseItemListWithOutBorder_table.addCell(phraseWeightValueDetailscell);


                                Phrase phrasepriceperkgValueDetails = new Phrase(" Rs." + String.valueOf(modal_b2BGoatGradeDetails.getPrice()) + " " + " ", itemNameFont);
                                PdfPCell phrasepriceperkgValueDetailscell = new PdfPCell(phrasepriceperkgValueDetails);
                                phrasepriceperkgValueDetailscell.setBorder(Rectangle.NO_BORDER);
                                phrasepriceperkgValueDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phrasepriceperkgValueDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                                phrasepriceperkgValueDetailscell.setPaddingTop(8);
                                phrasepriceperkgValueDetailscell.setPaddingBottom(8);
                                phrasepriceperkgValueDetailscell.setPaddingRight(5);
                                phrasepriceperkgValueDetailscell.setPaddingLeft(5);
                                phrasepriceperkgValueDetailscell.setBorderWidthRight(01);
                                if (selected_gradeDetailss_arrayList.size() > 1) {
                                    if (iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phrasepriceperkgValueDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
                                        phrasepriceperkgValueDetailscell.setBorderWidthBottom(01);
                                    }
                                }

                                phrasepriceperkgValueDetailscell.setBorderColor(LIGHT_GRAY);
                                gradewiseItemListWithOutBorder_table.addCell(phrasepriceperkgValueDetailscell);

                                Phrase phraseAmountValueDetails = new Phrase(" Rs." + String.valueOf(maleprice) + " " + " ", itemNameFont);
                                PdfPCell phraseAmountValueDetailscell = new PdfPCell(phraseAmountValueDetails);
                                phraseAmountValueDetailscell.setBorder(Rectangle.NO_BORDER);
                                phraseAmountValueDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseAmountValueDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                                phraseAmountValueDetailscell.setPaddingTop(8);
                                phraseAmountValueDetailscell.setPaddingBottom(8);
                                phraseAmountValueDetailscell.setPaddingRight(5);
                                phraseAmountValueDetailscell.setPaddingLeft(5);
                                if (selected_gradeDetailss_arrayList.size() > 1) {
                                    if (iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phraseAmountValueDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
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
                    if (earTagDetails_JSONFinalSalesHashMap.containsKey(modal_b2BGoatGradeDetails.getKey())) {

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
                            int femaleQty = 0, totalqty = 0;
                            double femaleWeight = 0, totalweight = 0, femaleprice = 0, totalprice = 0;

                            try {
                                femaleQty = modal_pojoClassForFinalSalesHashmap.getFemaleqty();
                                totalqty = modal_pojoClassForFinalSalesHashmap.getTotalqty();

                                femaleWeight = modal_pojoClassForFinalSalesHashmap.getTotalfemaleweight();
                                totalweight = modal_pojoClassForFinalSalesHashmap.getTotalweight();

                                femaleprice = modal_pojoClassForFinalSalesHashmap.getFemaleprice();
                                totalprice = modal_pojoClassForFinalSalesHashmap.getTotalprice();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            if (femaleQty > 0) {
                                itemPrintedCount = femaleQty + itemPrintedCount;
                                item_Count = item_Count + 1;
                                Phrase phrasenoDetails = new Phrase(String.valueOf(item_Count), itemNameFontBold);
                                PdfPCell phrasenoDetailscell = new PdfPCell(phrasenoDetails);
                                phrasenoDetailscell.setBorder(Rectangle.NO_BORDER);
                                phrasenoDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phrasenoDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                                phrasenoDetailscell.setBorderWidthRight(01);
                                if (selected_gradeDetailss_arrayList.size() > 1) {
                                    if (iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phrasenoDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
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
                                if (selected_gradeDetailss_arrayList.size() > 1) {
                                    if (iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phrasemalenameDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
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
                                if (selected_gradeDetailss_arrayList.size() > 1) {
                                    if (iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phrasehsnDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
                                        phrasehsnDetailscell.setBorderWidthBottom(01);
                                    }
                                }

                                phrasehsnDetailscell.setBorderColor(LIGHT_GRAY);
                                gradewiseItemListWithOutBorder_table.addCell(phrasehsnDetailscell);


                                Phrase phrasegradenameValueDetails = new Phrase(" " + String.valueOf(modal_b2BGoatGradeDetails.getName()) + " " + " ", itemNameFont);
                                PdfPCell phrasegradenameValueDetailscell = new PdfPCell(phrasegradenameValueDetails);
                                phrasegradenameValueDetailscell.setBorder(Rectangle.NO_BORDER);
                                phrasegradenameValueDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phrasegradenameValueDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                                phrasegradenameValueDetailscell.setPaddingTop(8);
                                phrasegradenameValueDetailscell.setPaddingBottom(8);
                                phrasegradenameValueDetailscell.setPaddingRight(5);
                                phrasegradenameValueDetailscell.setPaddingLeft(5);
                                phrasegradenameValueDetailscell.setBorderWidthRight(01);
                                if (selected_gradeDetailss_arrayList.size() > 1) {
                                    if (iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phrasegradenameValueDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
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
                                if (selected_gradeDetailss_arrayList.size() > 1) {
                                    if (iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phraseQtyDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
                                        phraseQtyDetailscell.setBorderWidthBottom(01);
                                    }
                                }

                                phraseQtyDetailscell.setBorderColor(LIGHT_GRAY);
                                gradewiseItemListWithOutBorder_table.addCell(phraseQtyDetailscell);


                                Phrase phraseWeightValueDetails = new Phrase(" " + String.valueOf(femaleWeight) + " Kg" + " ", itemNameFont);
                                PdfPCell phraseWeightValueDetailscell = new PdfPCell(phraseWeightValueDetails);
                                phraseWeightValueDetailscell.setBorder(Rectangle.NO_BORDER);
                                phraseWeightValueDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseWeightValueDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                                phraseWeightValueDetailscell.setPaddingTop(8);
                                phraseWeightValueDetailscell.setPaddingBottom(8);
                                phraseWeightValueDetailscell.setPaddingRight(5);
                                phraseWeightValueDetailscell.setPaddingLeft(5);
                                phraseWeightValueDetailscell.setBorderWidthRight(01);
                                if (selected_gradeDetailss_arrayList.size() > 1) {
                                    if (iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phraseWeightValueDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
                                        phraseWeightValueDetailscell.setBorderWidthBottom(01);
                                    }
                                }

                                phraseWeightValueDetailscell.setBorderColor(LIGHT_GRAY);
                                gradewiseItemListWithOutBorder_table.addCell(phraseWeightValueDetailscell);


                                Phrase phrasepriceperkgValueDetails = new Phrase(" Rs." + String.valueOf(modal_b2BGoatGradeDetails.getPrice()) + " " + " ", itemNameFont);
                                PdfPCell phrasepriceperkgValueDetailscell = new PdfPCell(phrasepriceperkgValueDetails);
                                phrasepriceperkgValueDetailscell.setBorder(Rectangle.NO_BORDER);
                                phrasepriceperkgValueDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phrasepriceperkgValueDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                                phrasepriceperkgValueDetailscell.setPaddingTop(8);
                                phrasepriceperkgValueDetailscell.setPaddingBottom(8);
                                phrasepriceperkgValueDetailscell.setPaddingRight(5);
                                phrasepriceperkgValueDetailscell.setPaddingLeft(5);
                                phrasepriceperkgValueDetailscell.setBorderWidthRight(01);
                                if (selected_gradeDetailss_arrayList.size() > 1) {
                                    if (iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phrasepriceperkgValueDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
                                        phrasepriceperkgValueDetailscell.setBorderWidthBottom(01);
                                    }
                                }


                                phrasepriceperkgValueDetailscell.setBorderColor(LIGHT_GRAY);
                                gradewiseItemListWithOutBorder_table.addCell(phrasepriceperkgValueDetailscell);

                                Phrase phraseAmountValueDetails = new Phrase(" Rs." + String.valueOf(femaleprice) + " " + " ", itemNameFont);
                                PdfPCell phraseAmountValueDetailscell = new PdfPCell(phraseAmountValueDetails);
                                phraseAmountValueDetailscell.setBorder(Rectangle.NO_BORDER);
                                phraseAmountValueDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseAmountValueDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                                phraseAmountValueDetailscell.setPaddingTop(8);
                                phraseAmountValueDetailscell.setPaddingBottom(8);
                                phraseAmountValueDetailscell.setPaddingRight(5);
                                phraseAmountValueDetailscell.setPaddingLeft(5);
                                if (selected_gradeDetailss_arrayList.size() > 1) {
                                    if (iterator - (selected_gradeDetailss_arrayList.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phraseAmountValueDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
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


                    if (iterator - (selected_gradeDetailss_arrayList.size() - 1) == 0) {

                        try {
                            PdfPCell gradewisewithbordercall = new PdfPCell(gradewiseItemListWithOutBorder_table);
                            gradewisewithbordercall.setBorder(Rectangle.NO_BORDER);
                            gradewisewithbordercall.setCellEvent(roundRectange);
                            gradewiseItemListWithBorder_table.addCell(gradewisewithbordercall);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            PdfPCell gradewiseItemListWithBorder_tablecell = new PdfPCell(gradewiseItemListWithBorder_table);
                            gradewiseItemListWithBorder_tablecell.setBorder(Rectangle.NO_BORDER);
                            gradewiseItemListWithBorder_tablecell.setPadding(8);
                            gradewiseItemListWithBorder_tablecell.setBorderColor(LIGHT_GRAY);
                            wholePDFWithOutBorder_table.addCell(gradewiseItemListWithBorder_tablecell);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            PdfPTable finalSalesBreakup_table = new PdfPTable(1);

            Phrase phraseSubtotalDetails = new Phrase(" Sub Total :            Rs. " + String.valueOf(twoDecimalConverter.format(totalPrice_doubleWithoutDiscount)), subtitleFont);
            PdfPCell phrasesubtotalDetailscell = new PdfPCell(phraseSubtotalDetails);
            phrasesubtotalDetailscell.setBorder(Rectangle.NO_BORDER);
            phrasesubtotalDetailscell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            phrasesubtotalDetailscell.setVerticalAlignment(Element.ALIGN_RIGHT);
            phrasesubtotalDetailscell.setPaddingLeft(10);
            phrasesubtotalDetailscell.setPaddingTop(20);
            phrasesubtotalDetailscell.setPaddingBottom(6);
            finalSalesBreakup_table.addCell(phrasesubtotalDetailscell);
            if (discountAmount_double > 0) {
                Phrase phrasediscountDetails = new Phrase(" Discount   :               Rs. " + String.valueOf(twoDecimalConverter.format(discountAmount_double)), subtitleFont);
                PdfPCell phrasediscountDetailscell = new PdfPCell(phrasediscountDetails);
                phrasediscountDetailscell.setBorder(Rectangle.NO_BORDER);
                phrasediscountDetailscell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                phrasediscountDetailscell.setVerticalAlignment(Element.ALIGN_RIGHT);
                phrasediscountDetailscell.setPaddingLeft(10);
                phrasediscountDetailscell.setPaddingBottom(6);
                finalSalesBreakup_table.addCell(phrasediscountDetailscell);
            }


            Phrase phraseCgstDetails = new Phrase(" CGST ( 0 % ) :                  0.00", subtitleFont);
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


            Phrase phrasetotalDetails = new Phrase(" Total :          Rs. " + String.valueOf(twoDecimalConverter.format(totalPrice_double)), subtitleFont);
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


            try {
                PdfPCell finalSalesBreakup_tablecell = new PdfPCell(finalSalesBreakup_table);
                finalSalesBreakup_tablecell.setBorder(Rectangle.NO_BORDER);
                finalSalesBreakup_tablecell.setPadding(8);
                finalSalesBreakup_tablecell.setBorderColor(LIGHT_GRAY);
                wholePDFWithOutBorder_table.addCell(finalSalesBreakup_tablecell);

            } catch (Exception e) {
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
            layoutDocument.newPage();
        }
        catch (Exception e){
            e.printStackTrace();
        }


        try{
            PdfPTable wholePDFWithBorder_table = new PdfPTable(1);


            PdfPTable wholePDFWithOutBorder_table = new PdfPTable(1);
            try {




                PdfPTable earTagItemListbillTitle_table = new PdfPTable(1);


                Phrase phraseEarTagItemListbillTitle = new Phrase(" \n Bill's Ear Tag Item List \n\n", titleFont);
                PdfPCell phraseEarTagItemListTitlecell = new PdfPCell(phraseEarTagItemListbillTitle);
                phraseEarTagItemListTitlecell.setBorder(Rectangle.NO_BORDER);
                phraseEarTagItemListTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                phraseEarTagItemListTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                phraseEarTagItemListTitlecell.setPaddingLeft(10);
                phraseEarTagItemListTitlecell.setPaddingBottom(6);
                earTagItemListbillTitle_table.addCell(phraseEarTagItemListTitlecell);
                layoutDocument.add(earTagItemListbillTitle_table);
            } catch (Exception e) {
                e.printStackTrace();
            }


            PdfPTable earTagItemsListWithOutBorder_table = new PdfPTable(8);
            PdfPTable earTagItemsListWithBorder_table = new PdfPTable(1);
            try{





                try {
                    earTagItemsListWithOutBorder_table.setWidths(new float[]{2.5f,  4.5f, 4.5f, 4.2f, 5f, 5f , 5f, 5f});
                } catch (DocumentException e) {
                    Log.i("INIT", "call_and_init_B2BReceiverDetailsService: setWidths  " + String.valueOf(e));

                    e.printStackTrace();
                }




                Phrase phraseSnoDetails = new Phrase(" No ", itemNameFont);
                PdfPCell phraseSnoDetailscell = new PdfPCell(phraseSnoDetails);
                phraseSnoDetailscell.setBorder(Rectangle.NO_BORDER);
                phraseSnoDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                phraseSnoDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                phraseSnoDetailscell.setBorderWidthRight(01);
                phraseSnoDetailscell.setBorderWidthBottom(01);
                phraseSnoDetailscell.setBorderColor(LIGHT_GRAY);
                phraseSnoDetailscell.setPaddingTop(8);
                phraseSnoDetailscell.setPaddingBottom(8);
                earTagItemsListWithOutBorder_table.addCell(phraseSnoDetailscell);

              /*  Phrase phraseitemnameDescrDetails = new Phrase("BatchNo", itemNameFont);
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
                earTagItemsListWithOutBorder_table.addCell(phraseitemnameDescrDetailscell);


               */
                Phrase phraseHSNDescrDetails = new Phrase("BarcodeNo", itemNameFont);
                PdfPCell phraseHSNDetailscell = new PdfPCell(phraseHSNDescrDetails);
                phraseHSNDetailscell.setBorder(Rectangle.NO_BORDER);
                phraseHSNDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                phraseHSNDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                phraseHSNDetailscell.setPaddingTop(8);
                phraseHSNDetailscell.setPaddingBottom(8);

                phraseHSNDetailscell.setBorderWidthRight(01);
                phraseHSNDetailscell.setBorderWidthBottom(01);
                phraseHSNDetailscell.setBorderColor(LIGHT_GRAY);
                earTagItemsListWithOutBorder_table.addCell(phraseHSNDetailscell);


             /*   Phrase phraseTypeDetails = new Phrase("Ctgy", itemNameFont);
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
                earTagItemsListWithOutBorder_table.addCell(phraseTypeDetailscell);


              */

                Phrase phraseQTYDetails = new Phrase("BreedType", itemNameFont);
                PdfPCell phraseQTYcell = new PdfPCell(phraseQTYDetails);
                phraseQTYcell.setBorder(Rectangle.NO_BORDER);
                phraseQTYcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                phraseQTYcell.setVerticalAlignment(Element.ALIGN_LEFT);
                phraseQTYcell.setPaddingTop(8);
                phraseQTYcell.setPaddingBottom(8);
                phraseQTYcell.setPaddingRight(1);
                phraseQTYcell.setPaddingLeft(1);
                phraseQTYcell.setBorderWidthRight(01);
                phraseQTYcell.setBorderWidthBottom(01);
                phraseQTYcell.setBorderColor(LIGHT_GRAY);
                earTagItemsListWithOutBorder_table.addCell(phraseQTYcell);


                Phrase phraseGenderDetails = new Phrase("Gender", itemNameFont);
                PdfPCell phraseGendercell = new PdfPCell(phraseGenderDetails);
                phraseGendercell.setBorder(Rectangle.NO_BORDER);
                phraseGendercell.setHorizontalAlignment(Element.ALIGN_CENTER);
                phraseGendercell.setVerticalAlignment(Element.ALIGN_LEFT);
                phraseGendercell.setPaddingTop(8);
                phraseGendercell.setPaddingBottom(8);
                phraseGendercell.setPaddingRight(5);
                phraseGendercell.setPaddingLeft(5);
                phraseGendercell.setBorderWidthRight(01);
                phraseGendercell.setBorderWidthBottom(01);
                phraseGendercell.setBorderColor(LIGHT_GRAY);
                earTagItemsListWithOutBorder_table.addCell(phraseGendercell);


                Phrase phraseWeightDetails = new Phrase("GradeName", itemNameFont);
                PdfPCell phraseWeightcell = new PdfPCell(phraseWeightDetails);
                phraseWeightcell.setBorder(Rectangle.NO_BORDER);
                phraseWeightcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                phraseWeightcell.setVerticalAlignment(Element.ALIGN_LEFT);
                phraseWeightcell.setPaddingTop(8);
                phraseWeightcell.setPaddingBottom(8);
                phraseWeightcell.setPaddingRight(5);
                phraseWeightcell.setPaddingLeft(1);
                phraseWeightcell.setBorderWidthRight(01);
                phraseWeightcell.setBorderWidthBottom(01);
                phraseWeightcell.setBorderColor(LIGHT_GRAY);
                earTagItemsListWithOutBorder_table.addCell(phraseWeightcell);


                Phrase phrasepriceperkgDetails = new Phrase("GradePrice/Kg", itemNameFont);
                PdfPCell phrasepriceperkgcell = new PdfPCell(phrasepriceperkgDetails);
                phrasepriceperkgcell.setBorder(Rectangle.NO_BORDER);
                phrasepriceperkgcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                phrasepriceperkgcell.setVerticalAlignment(Element.ALIGN_LEFT);
                phrasepriceperkgcell.setBorderWidthRight(01);
                phrasepriceperkgcell.setBorderWidthBottom(01);
                phrasepriceperkgcell.setBorderColor(LIGHT_GRAY);
                phrasepriceperkgcell.setPaddingTop(8);
                phrasepriceperkgcell.setPaddingBottom(8);
                phrasepriceperkgcell.setPaddingRight(1);
                phrasepriceperkgcell.setPaddingLeft(1);
                earTagItemsListWithOutBorder_table.addCell(phrasepriceperkgcell);


                Phrase phraseamountDetails = new Phrase("Weight(Kg)", itemNameFont);
                PdfPCell phraseamountcell = new PdfPCell(phraseamountDetails);
                phraseamountcell.setBorder(Rectangle.NO_BORDER);
                phraseamountcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                phraseamountcell.setVerticalAlignment(Element.ALIGN_LEFT);
                phraseamountcell.setBorderWidthBottom(01);
                phraseamountcell.setBorderWidthRight(01);
                phraseamountcell.setBorderColor(LIGHT_GRAY);
                phraseamountcell.setPaddingTop(8);
                phraseamountcell.setPaddingBottom(8);
                phraseamountcell.setPaddingRight(5);
                phraseamountcell.setPaddingLeft(1);
                earTagItemsListWithOutBorder_table.addCell(phraseamountcell);

                Phrase phraseTotalamountDetails = new Phrase("TotalPrice", itemNameFont);
                PdfPCell phraseTotalamountcell = new PdfPCell(phraseTotalamountDetails);
                phraseTotalamountcell.setBorder(Rectangle.NO_BORDER);
                phraseTotalamountcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                phraseTotalamountcell.setVerticalAlignment(Element.ALIGN_LEFT);
                phraseTotalamountcell.setBorderWidthBottom(01);
                phraseTotalamountcell.setBorderColor(LIGHT_GRAY);
                phraseTotalamountcell.setPaddingTop(8);
                phraseTotalamountcell.setPaddingBottom(8);
                phraseTotalamountcell.setPaddingRight(5);
                phraseTotalamountcell.setPaddingLeft(1);
                earTagItemsListWithOutBorder_table.addCell(phraseTotalamountcell);

            }
            catch (Exception e){
                e.printStackTrace();
            }

            int item_Count = 0;

            try {
                if(earTagDetailsArrayList_WholeBatch.size()>0) {
                    for (int iterator = 0; iterator < earTagDetailsArrayList_WholeBatch.size(); iterator++) {
                        Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagDetailsArrayList_WholeBatch.get(iterator);
                        String totalCount_string = "0";
                        int totalCount_int = 0, itemPrintedCount = 0;

                        try {
                            try {
                                item_Count = item_Count + 1;
                                Phrase phrasenoDetails = new Phrase(String.valueOf(item_Count), smallFont);
                                PdfPCell phrasenoDetailscell = new PdfPCell(phrasenoDetails);
                                phrasenoDetailscell.setBorder(Rectangle.NO_BORDER);
                                phrasenoDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phrasenoDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                                phrasenoDetailscell.setBorderWidthRight(01);
                                if (earTagDetailsArrayList_WholeBatch.size() > 1) {
                                    if (iterator - (earTagDetailsArrayList_WholeBatch.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phrasenoDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
                                        phrasenoDetailscell.setBorderWidthBottom(01);
                                    }
                                }

                                phrasenoDetailscell.setBorderColor(LIGHT_GRAY);
                                phrasenoDetailscell.setPaddingTop(8);
                                phrasenoDetailscell.setPaddingBottom(8);
                                earTagItemsListWithOutBorder_table.addCell(phrasenoDetailscell);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            /*try {

                                Phrase phrasemalenameDetails = new Phrase(String.valueOf(modal_goatEarTagDetails.getBatchno()), smallFont);
                                PdfPCell phrasemalenameDetailscell = new PdfPCell(phrasemalenameDetails);
                                phrasemalenameDetailscell.setBorder(Rectangle.NO_BORDER);
                                phrasemalenameDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phrasemalenameDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                                phrasemalenameDetailscell.setPaddingTop(8);
                                phrasemalenameDetailscell.setPaddingBottom(8);
                                phrasemalenameDetailscell.setPaddingRight(5);
                                phrasemalenameDetailscell.setPaddingLeft(5);
                                phrasemalenameDetailscell.setBorderWidthRight(01);
                                if (earTagDetailsArrayList_WholeBatch.size() > 1) {
                                    if (iterator - (earTagDetailsArrayList_WholeBatch.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phrasemalenameDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
                                        phrasemalenameDetailscell.setBorderWidthBottom(01);
                                    }
                                }

                                phrasemalenameDetailscell.setBorderColor(LIGHT_GRAY);
                                earTagItemsListWithOutBorder_table.addCell(phrasemalenameDetailscell);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                             */
                            try {

                                Phrase phraseDetails = new Phrase(String.valueOf(modal_goatEarTagDetails.getBarcodeno()), smallFont);
                                PdfPCell phraseDetailscell = new PdfPCell(phraseDetails);
                                phraseDetailscell.setBorder(Rectangle.NO_BORDER);
                                phraseDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                                phraseDetailscell.setPaddingTop(8);
                                phraseDetailscell.setPaddingBottom(8);
                                phraseDetailscell.setPaddingRight(5);
                                phraseDetailscell.setPaddingLeft(5);
                                phraseDetailscell.setBorderWidthRight(01);
                                if (earTagDetailsArrayList_WholeBatch.size() > 1) {
                                    if (iterator - (earTagDetailsArrayList_WholeBatch.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phraseDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
                                        phraseDetailscell.setBorderWidthBottom(01);
                                    }
                                }

                                phraseDetailscell.setBorderColor(LIGHT_GRAY);
                                earTagItemsListWithOutBorder_table.addCell(phraseDetailscell);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                           /* try {

                                Phrase phraseDetails = new Phrase(String.valueOf(modal_goatEarTagDetails.getB2bctgyName()), smallFont);
                                PdfPCell phraseDetailscell = new PdfPCell(phraseDetails);
                                phraseDetailscell.setBorder(Rectangle.NO_BORDER);
                                phraseDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                                phraseDetailscell.setPaddingTop(8);
                                phraseDetailscell.setPaddingBottom(8);
                                phraseDetailscell.setPaddingRight(5);
                                phraseDetailscell.setPaddingLeft(5);
                                phraseDetailscell.setBorderWidthRight(01);
                                if (earTagDetailsArrayList_WholeBatch.size() > 1) {
                                    if (iterator - (earTagDetailsArrayList_WholeBatch.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phraseDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
                                        phraseDetailscell.setBorderWidthBottom(01);
                                    }
                                }

                                phraseDetailscell.setBorderColor(LIGHT_GRAY);
                                earTagItemsListWithOutBorder_table.addCell(phraseDetailscell);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            */

                            try {

                                Phrase phraseDetails = new Phrase(String.valueOf(modal_goatEarTagDetails.getB2bSubctgyName()), smallFont);
                                PdfPCell phraseDetailscell = new PdfPCell(phraseDetails);
                                phraseDetailscell.setBorder(Rectangle.NO_BORDER);
                                phraseDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                                phraseDetailscell.setPaddingTop(8);
                                phraseDetailscell.setPaddingBottom(8);
                                phraseDetailscell.setPaddingRight(5);
                                phraseDetailscell.setPaddingLeft(5);
                                phraseDetailscell.setBorderWidthRight(01);
                                if (earTagDetailsArrayList_WholeBatch.size() > 1) {
                                    if (iterator - (earTagDetailsArrayList_WholeBatch.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phraseDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
                                        phraseDetailscell.setBorderWidthBottom(01);
                                    }
                                }

                                phraseDetailscell.setBorderColor(LIGHT_GRAY);
                                earTagItemsListWithOutBorder_table.addCell(phraseDetailscell);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {

                                Phrase phraseDetails = new Phrase(String.valueOf(modal_goatEarTagDetails.getGender()), smallFont);
                                PdfPCell phraseDetailscell = new PdfPCell(phraseDetails);
                                phraseDetailscell.setBorder(Rectangle.NO_BORDER);
                                phraseDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                                phraseDetailscell.setPaddingTop(8);
                                phraseDetailscell.setPaddingBottom(8);
                                phraseDetailscell.setPaddingRight(5);
                                phraseDetailscell.setPaddingLeft(5);
                                phraseDetailscell.setBorderWidthRight(01);
                                if (earTagDetailsArrayList_WholeBatch.size() > 1) {
                                    if (iterator - (earTagDetailsArrayList_WholeBatch.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phraseDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
                                        phraseDetailscell.setBorderWidthBottom(01);
                                    }
                                }

                                phraseDetailscell.setBorderColor(LIGHT_GRAY);
                                earTagItemsListWithOutBorder_table.addCell(phraseDetailscell);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {

                                Phrase phraseDetails = new Phrase(String.valueOf(modal_goatEarTagDetails.getGradename()), smallFont);
                                PdfPCell phraseDetailscell = new PdfPCell(phraseDetails);
                                phraseDetailscell.setBorder(Rectangle.NO_BORDER);
                                phraseDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                                phraseDetailscell.setPaddingTop(8);
                                phraseDetailscell.setPaddingBottom(8);
                                phraseDetailscell.setPaddingRight(5);
                                phraseDetailscell.setPaddingLeft(5);
                                phraseDetailscell.setBorderWidthRight(01);
                                if (earTagDetailsArrayList_WholeBatch.size() > 1) {
                                    if (iterator - (earTagDetailsArrayList_WholeBatch.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phraseDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
                                        phraseDetailscell.setBorderWidthBottom(01);
                                    }
                                }

                                phraseDetailscell.setBorderColor(LIGHT_GRAY);
                                earTagItemsListWithOutBorder_table.addCell(phraseDetailscell);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {

                                Phrase phraseDetails = new Phrase("Rs."+String.valueOf(modal_goatEarTagDetails.getGradeprice()), smallFont);
                                PdfPCell phraseDetailscell = new PdfPCell(phraseDetails);
                                phraseDetailscell.setBorder(Rectangle.NO_BORDER);
                                phraseDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                                phraseDetailscell.setPaddingTop(8);
                                phraseDetailscell.setPaddingBottom(8);
                                phraseDetailscell.setPaddingRight(5);
                                phraseDetailscell.setPaddingLeft(5);
                                phraseDetailscell.setBorderWidthRight(01);
                                if (earTagDetailsArrayList_WholeBatch.size() > 1) {
                                    if (iterator - (earTagDetailsArrayList_WholeBatch.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phraseDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
                                        phraseDetailscell.setBorderWidthBottom(01);
                                    }
                                }

                                phraseDetailscell.setBorderColor(LIGHT_GRAY);
                                earTagItemsListWithOutBorder_table.addCell(phraseDetailscell);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {

                                Phrase phraseDetails = new Phrase(String.valueOf(modal_goatEarTagDetails.getNewWeight_forBillingScreen()+" Kg"), smallFont);
                                PdfPCell phraseDetailscell = new PdfPCell(phraseDetails);
                                phraseDetailscell.setBorder(Rectangle.NO_BORDER);
                                phraseDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                                phraseDetailscell.setPaddingTop(8);
                                phraseDetailscell.setPaddingBottom(8);
                                phraseDetailscell.setPaddingRight(5);
                                phraseDetailscell.setPaddingLeft(5);
                                phraseDetailscell.setBorderWidthRight(01);
                                if (earTagDetailsArrayList_WholeBatch.size() > 1) {
                                    if (iterator - (earTagDetailsArrayList_WholeBatch.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phraseDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
                                        phraseDetailscell.setBorderWidthBottom(01);
                                    }
                                }

                                phraseDetailscell.setBorderColor(LIGHT_GRAY);
                                earTagItemsListWithOutBorder_table.addCell(phraseDetailscell);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {

                                Phrase phraseDetails = new Phrase("Rs."+String.valueOf(modal_goatEarTagDetails.getTotalPrice()), smallFont);
                                PdfPCell phraseDetailscell = new PdfPCell(phraseDetails);
                                phraseDetailscell.setBorder(Rectangle.NO_BORDER);
                                phraseDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
                                phraseDetailscell.setPaddingTop(8);
                                phraseDetailscell.setPaddingBottom(8);

                                phraseDetailscell.setPaddingLeft(5);
                                phraseDetailscell.setBorderWidthRight(01);
                                if (earTagDetailsArrayList_WholeBatch.size() > 1) {
                                    if (iterator - (earTagDetailsArrayList_WholeBatch.size() - 1) != 0 || itemPrintedCount != totalCount_int) {
                                        phraseDetailscell.setBorderWidthBottom(01);
                                    }
                                } else {
                                    if (itemPrintedCount != totalCount_int) {
                                        phraseDetailscell.setBorderWidthBottom(01);
                                    }
                                }

                                phraseDetailscell.setBorderColor(LIGHT_GRAY);
                                earTagItemsListWithOutBorder_table.addCell(phraseDetailscell);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        if (iterator - (earTagDetailsArrayList_WholeBatch.size() - 1) == 0) {

                            try {
                                PdfPCell gradewisewithbordercall = new PdfPCell(earTagItemsListWithOutBorder_table);
                                gradewisewithbordercall.setBorder(Rectangle.NO_BORDER);
                                gradewisewithbordercall.setCellEvent(roundRectange);
                                earTagItemsListWithBorder_table.addCell(gradewisewithbordercall);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                PdfPCell earTagItemsListWithBorder_tablecell = new PdfPCell(earTagItemsListWithBorder_table);
                                earTagItemsListWithBorder_tablecell.setBorder(Rectangle.NO_BORDER);
                                earTagItemsListWithBorder_tablecell.setPadding(8);
                                earTagItemsListWithBorder_tablecell.setBorderColor(LIGHT_GRAY);
                                wholePDFWithOutBorder_table.addCell(earTagItemsListWithBorder_tablecell);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    }
                }
            } catch (Exception e) {
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
}


