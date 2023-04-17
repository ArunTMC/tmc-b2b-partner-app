package com.tmc.tmcb2bpartnerapp.second_version.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;

import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartItemDetails_BulkUpdate;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagTransaction;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartItemDetaillsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartItemDetails_BulkUpdateInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagTransactionInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;

import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagTransaction;
import com.tmc.tmcb2bpartnerapp.modal.Modal_Static_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_UpdatedGoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal_Array_to_PlaceOrder;
import com.tmc.tmcb2bpartnerapp.second_version.adapter.Adapter_PlaceOrder_recyclerView;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.TMCAlertDialogClass;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceNewOrder_activity extends BaseActivity {

    //ArrayList
    private ArrayList<Modal_GoatEarTagDetails> earTagDetailsList = new ArrayList<>();
    private ArrayList<Modal_GoatEarTagDetails> sorted_earTagDetailsList = new ArrayList<>();
    public static ArrayList<Modal_B2BBatchDetails> batchDetailsArrayList = new ArrayList<>();

    public HashMap<String,Modal_B2BCartItemDetails> cartItemDetails_Hashmap = new HashMap<>();
    public ArrayList<String> cartItemDetailsList_String = new ArrayList<>();
    public HashMap<String,ArrayList<String>> batchwiseEartagDetails_Hashmap = new HashMap<>();




    //Widget
    private RecyclerView mRecyclerView;
    static LinearLayout back_IconLayout;
    static LinearLayout searchBarcodeno_layout;
    static LinearLayout loadingPanel;
    static LinearLayout loadingpanelmask;
    static LinearLayout deleteCart_IconLayout;
    static LinearLayout search_IconLayout;
    static LinearLayout close_IconLayout;
    public static LinearLayout placeOrder_Button;
    static LinearLayout goatInfo_IconLayout;
    TextView searchbarcodeNo_textView,selected_goatsCount , male_Qty_textview , female_Qty_textview , male_Percentage_textview_bottomdialog ,
            female_Percentage_textview_bottomdialog;
           // oldfemale_Qty_textview,oldfemale_Percentage_textview , sheepmale_Qty_textview , sheepmale_Percentage_textview
            //sheepfemale_Qty_textview,sheepfemale_Percentage_textview;
    EditText baarcodeNo_search_editText;



    //General
    private Adapter_PlaceOrder_recyclerView mAdapter;
    static DecimalFormat threeDecimalConverter = new DecimalFormat(Constants.threeDecimalPattern);
    static DecimalFormat oneDecimalConverter = new DecimalFormat(Constants.oneDecimalPattern);
    static DecimalFormat twoDecimalConverter = new DecimalFormat(Constants.twoDecimalPattern);
    static DecimalFormat twoDecimalConverterWithComma = new DecimalFormat(Constants.twoDecimalWithCommaPattern);

    //boolean
    boolean  isBatchDetailsTableServiceCalled = false;
    boolean isGoatEarTagDetailsTableServiceCalled = false;
    boolean isSearchButtonClicked =false;
    boolean isB2BCartOrderTableServiceCalled = false;
    boolean isB2BCartItemDetailsCalled = false;
    boolean isGoatEarTagTransactionTableServiceCalled = false;
    boolean isB2BCartOrderDetailsEntryAlreadyCreated = false;
    public boolean isshowCartItemDetails = false , clearcartnow =false ,isB2BCartItemDetailsBulkUpdate = false;

    //STRING
    String deliveryCentreKey ="" , orderid ="" , deliveryCentreName ="" ,usermobileno_string ="";
    String retailerMobileNo ="" , retailerName = "" , retailerKey = "" , feedweight = "" , feedPricePerKg = "" , feedPrice ="" ,
    retaileraddress= "", discount_String = "" ,paymentMode ="";

    //int
    int earTagDetailsresultReceivedCount = 0;
    double goat_maleCount =0 , goat_femaleCount =0, goat_oldFemaleCount = 0 , sheep_maleCount = 0 , sheep_femaleCount = 0 ;


    //double
    double maleRatioValue_double = 0, femaleRatioValue_double = 0, meatYieldAvg = 0, approxLiveWeightAvg = 0;
    double goatmaleRatio_double = 0 , goatfemaleRatio_double = 0 , goatoldfemaleRatio_double = 0 , sheepMaleRatio = 0 , sheepFemaleRatio = 0;
    double maleCount_int = 0, femaleCountInt = 0, totalMeatYeild = 0, totalaApproxLiveWeight = 0, totalFeedDouble = 0 ,final_batchValue =0 ,final_totalPriceWithOutDiscount =0 ,final_totalWeight =0;


    //interface
    B2BBatchDetailsInterface callback_B2BBatchDetailsInterface = null;
    GoatEarTagDetailsInterface callback_GoatEarTagDetails = null;
    B2BCartOrderDetailsInterface callback_b2bCartOrderDetails =null ;
    B2BCartItemDetaillsInterface callback_b2BCartItemDetaillsInterface = null;
    GoatEarTagTransactionInterface callback_GoatEarTagTransactionInterface = null;
    B2BCartItemDetails_BulkUpdateInterface callbackB2BCartItemDetails_bulkUpdateInterface = null;






    public static Dialog show_goatEarTagItemDetails_Dialog = null;
    // ear Tag Details dialog's Component
    TextView barcodeNo_textView ,gradeName_textview ,gender_textview ,totalweight_textview ,totalPrice_textView ,selectedGoatStatus_textview,approxLiveWeight_textview
            ,meatyeild_textview,parts_textView,priceWithoutDiscount_textview,discount_textView;
    double approx_Live_Weight_double = 0 , meatyeild_weight_double = 0 , parts_Weight_double = 0 , totalWeight_double = 0 , discountDouble = 0 ,
            totalPrice_double = 0 ,     pricewithOutdiscount_double = 0;

    EditText barcode_editText,approxLiveWeight_EditText ,parts_editText,discount_edittext,meatyeild_edittext,gradeName_editText,totalPrice_edittext,priceWithoutDiscount_edittext;
    Button save_button;
    LinearLayout back_IconLayout_goatEarTagItemdialog;
    LinearLayout discount_price_layout_label;
    LinearLayout goatstatus_layout;
    static LinearLayout loadingpanelmask_in_dialog;
    static LinearLayout loadingPanel_in_dialog;

    RadioGroup genderRadioGroup ,goatstatusradiogrp ;
    RadioButton male_radioButton , female_radioButton,normal_goat_radio,dead_goat_radio,sick_goat_radio;
    public String selectedItemsStatus = "" , selectedGenderName ="" , selectedGradeName ="" , gender =""  , scannedBarcode ="";
    // dialog's Component




    //totalValue info Dialog Components
    public static Dialog show_total_value_info_Dialog = null;
    TextView finalGoatValue_textView, totalGoats_textView , totalValue_textView , batchValue_textView ,discountValue_textView , finalPayment_textView,
            finaltotalWeight_textView,totalFeedPriceTextview,finalFeedValue_textView,male_Qty_textview_infoDialog,female_Qty_textview_infoDialog,male_female_ratio_textview
            ,female_Percentage_textview,male_percentage_textview ,meatYieldWeightAvg_Textview ,approxLiveWeightAvg_Textview ;
   public  LinearLayout close_IconLayout_goatsInfo_dialog ,itemCountDetails2_LinearLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_new_order);
        loadingPanel = findViewById(R.id.loadingPanel);
        loadingpanelmask = findViewById(R.id.loadingpanelmask);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        search_IconLayout = findViewById(R.id.search_IconLayout);
        close_IconLayout = findViewById(R.id.close_IconLayout);
        back_IconLayout =  findViewById(R.id.back_IconLayout);
        deleteCart_IconLayout  = findViewById(R.id.deleteCart_IconLayout);
        searchbarcodeNo_textView = findViewById(R.id.barcodeNo_textView);
        baarcodeNo_search_editText = findViewById(R.id.baarcodeNo_search_editText);
        searchBarcodeno_layout = findViewById(R.id.searchBarcodeno_layout);
        placeOrder_Button = findViewById(R.id.placeOrder_Button);
        selected_goatsCount = findViewById(R.id.selected_goatsCount);
        male_Qty_textview = findViewById(R.id.male_Qty_textview);
        female_Qty_textview = findViewById(R.id.female_Qty_textview);
      //  oldfemale_Qty_textview =  findViewById(R.id.oldfemale_Qty_textview);
        male_Percentage_textview_bottomdialog = findViewById(R.id.male_Percentage_textview);
        female_Percentage_textview_bottomdialog = findViewById(R.id.female_Percentage_textview);
     //   oldfemale_Percentage_textview = findViewById(R.id.oldfemale_Percentage_textview);
     //   sheepmale_Qty_textview = findViewById(R.id.sheepmale_Qty_textview);
    //    sheepmale_Percentage_textview = findViewById(R.id.sheepmale_Percentage_textview);
     //   sheepfemale_Percentage_textview = findViewById(R.id.sheepfemale_Percentage_textview);
    //    sheepfemale_Qty_textview = findViewById(R.id.sheepfemale_Qty_textview);




        goatInfo_IconLayout  = findViewById(R.id.goatInfo_IconLayout);
        itemCountDetails2_LinearLayout  = findViewById(R.id.itemCountDetails2_LinearLayout);



        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);
        deliveryCentreKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCentreName= sh1.getString("DeliveryCenterName", "");


        SharedPreferences sh = getSharedPreferences("LoginData",MODE_PRIVATE);
        usermobileno_string = sh.getString("UserMobileNumber","");


        Intent intent = getIntent();
        isshowCartItemDetails = intent.getBooleanExtra(String.valueOf("showCartItemDetails"), false);
        isB2BCartOrderDetailsEntryAlreadyCreated = intent.getBooleanExtra(String.valueOf("isB2BCartOrderDetailsEntryAlreadyCreated"), false);
        clearcartnow = intent.getBooleanExtra(String.valueOf("clearcartnow"), false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        show_goatEarTagItemDetails_Dialog = new Dialog(PlaceNewOrder_activity.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_TranslucentDecor);
        show_total_value_info_Dialog = new Dialog(PlaceNewOrder_activity.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_TranslucentDecor);
        if(isB2BCartOrderDetailsEntryAlreadyCreated){
            deleteCart_IconLayout.setVisibility(View.VISIBLE);
        }
        else{
            deleteCart_IconLayout.setVisibility(View.GONE);
        }


        itemCountDetails2_LinearLayout.setVisibility(View.GONE);


        deleteCart_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    if(clearcartnow) {
                        showProgressBar(true);
                        Call_And_Execute_B2BCartOrder_Details(Constants.CallDELETEMethod,false);
                        Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();
                        Call_And_Execute_B2BCartOrderItem_Details(Constants.CallGETListMethod, "", modal_goatEarTagDetails);
                    }
                    else{
                        new TMCAlertDialogClass(PlaceNewOrder_activity.this, R.string.app_name, R.string.Clear_Cart_Instruction,
                                R.string.Yes_Text, R.string.No_Text,
                                new TMCAlertDialogClass.AlertListener() {
                                    @Override
                                    public void onYes() {
                                        if(cartItemDetails_Hashmap.size()>0){

                                            Call_And_Execute_B2BCartOrder_Details(Constants.CallDELETEMethod, true);
                                        }
                                        else{
                                        Toast.makeText(PlaceNewOrder_activity.this, "No item added in the Cart", Toast.LENGTH_SHORT).show();
                                        }


                                    }

                                    @Override
                                    public void onNo() {
                                        showProgressBar(false);
                                    }
                                });


                    }




            }
        });


        if(clearcartnow){
            orderid = intent.getStringExtra(String.valueOf("orderid"));
            deleteCart_IconLayout.performClick();

        }
        else if(isshowCartItemDetails){
            orderid = intent.getStringExtra(String.valueOf("orderid"));
            Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();
            Call_And_Execute_B2BCartOrderItem_Details(Constants.CallGETListMethod,"",modal_goatEarTagDetails);

        }
        else{
            orderid = "";
            Call_and_ExecuteB2BBatchOrderDetails(Constants.CallGETListMethod);
        }


        loadingpanelmask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlaceNewOrder_activity.this, "Please wait until the loading gets finished", Toast.LENGTH_SHORT).show();
            }
        });


        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        goatInfo_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartItemDetails_Hashmap.size()>0){
                    openGoatinfoDialogLayout();
                }
                else{
                    Toast.makeText(PlaceNewOrder_activity.this, "No item added in the Cart", Toast.LENGTH_SHORT).show();
                }

            }
        });


        /*
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(-1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    placeOrder_Button.setVisibility(View.VISIBLE);

                }
                else if(newState==RecyclerView.SCROLL_STATE_DRAGGING) {
                    if (placeOrder_Button.getVisibility() ==  View.GONE){
                        //  Toast.makeText(mContext, "Swipe downwards to make the Settings Button Visible", Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    placeOrder_Button.setVisibility(View.GONE);
                //    Toast.makeText(PlaceNewOrder_activity.this, "Swipe downwards to make the PlaceOrder Button Visible", Toast.LENGTH_SHORT).show();

                }


            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(-1)) {
                    // onScrolledToTop();
                    //  bottomNavigationView.setVisibility(View.VISIBLE);

                } else if (!recyclerView.canScrollVertically(1)) {
                    //    onScrolledToBottom();
                    // bottomNavigationView.setVisibility(View.GONE);

                } else if (dy < 0) {
                    //  onScrolledUp();
                    //  Toast.makeText(mContext, "dy : "+String.valueOf(dy), Toast.LENGTH_SHORT).show();
                    // bottomNavigationView.setVisibility(View.GONE);

                } else if (dy > 0) {
                    //  onScrolledDown();
                    //  Toast.makeText(mContext, "dy : "+String.valueOf(dy), Toast.LENGTH_SHORT).show();

                    //bottomNavigationView.setVisibility(View.VISIBLE);

                }
                else if (dy == 0) {
                    //  onScrolledDown();
                    //  Toast.makeText(mContext, "dy : "+String.valueOf(dy), Toast.LENGTH_SHORT).show();


                }
            }


        });


         */

        search_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int textlength = baarcodeNo_search_editText.getText().toString().length();
                isSearchButtonClicked =true;
                showKeyboard(baarcodeNo_search_editText);
                showSearchBarEditText();
            }

        });

        searchbarcodeNo_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_IconLayout.performClick();
            }

        });

        close_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(baarcodeNo_search_editText);
                closeSearchBarEditText();
                baarcodeNo_search_editText.setText("");
                isSearchButtonClicked =false;
                setAdapterForRecyclerView(earTagDetailsList);

            }
        });



        baarcodeNo_search_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sorted_earTagDetailsList.clear();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                sorted_earTagDetailsList.clear();
                isSearchButtonClicked =true;
                String barcodeEntered = (editable.toString().toUpperCase() );

                if(!barcodeEntered.equals("")) {

                    for (int i = 0; i < earTagDetailsList.size(); i++) {
                        try{
                            final Modal_GoatEarTagDetails modal_b2BRetailerDetails = earTagDetailsList.get(i);
                            String barcodeno = modal_b2BRetailerDetails.getBarcodeno();
                            if (barcodeno.contains(barcodeEntered)) {
                                sorted_earTagDetailsList.add(modal_b2BRetailerDetails);
                            }
                            if (i == (earTagDetailsList.size() - 1)) {

                                setAdapterForRecyclerView(sorted_earTagDetailsList);
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    setAdapterForRecyclerView(earTagDetailsList);


                }
            }
        });



        placeOrder_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                retailerMobileNo = "";
                retaileraddress = "";
                retailerName = "";
                retailerKey = "";
                feedweight = "" ; paymentMode ="";
                feedPricePerKg = "";
                feedPrice = "";
                discount_String = "";
                new Modal_Array_to_PlaceOrder("clearArray") ;

                paymentMode = Modal_B2BCartOrderDetails.getPaymentMode();
                retailerKey = Modal_B2BCartOrderDetails.getRetailerkey();
                retailerMobileNo = Modal_B2BCartOrderDetails.getRetailermobileno();
                retailerName = Modal_B2BCartOrderDetails.getRetailername();
                orderid = Modal_B2BCartOrderDetails.getOrderid();
                retaileraddress = Modal_B2BCartOrderDetails.getRetaileraddress();
                discount_String = Modal_B2BCartOrderDetails.getDiscountAmount();
                paymentMode = Modal_B2BCartOrderDetails.getPaymentMode();

                feedPrice = (String.valueOf(Modal_B2BCartOrderDetails.getFeedPrice()));
                feedPricePerKg = (String.valueOf(Modal_B2BCartOrderDetails.getFeedPriceperkg()));
                feedweight = (String.valueOf(Modal_B2BCartOrderDetails.getFeedWeight()));

                Modal_Array_to_PlaceOrder.setCartItemDetails_Hashmap(cartItemDetails_Hashmap);
                Modal_Array_to_PlaceOrder.setCartItemDetailsList_String(cartItemDetailsList_String);
                Modal_Array_to_PlaceOrder.setRetailerMobileNo(retailerMobileNo);
                Modal_Array_to_PlaceOrder.setRetailerAddress(retaileraddress);
                Modal_Array_to_PlaceOrder.setRetailerName(retailerName);
                Modal_Array_to_PlaceOrder.setRetailerKey(retailerKey);
                Modal_Array_to_PlaceOrder.setFeedPrice(feedPrice);
                Modal_Array_to_PlaceOrder.setFeedPricePerKg(feedPricePerKg);
                Modal_Array_to_PlaceOrder.setFeedweight(feedweight);
                Modal_Array_to_PlaceOrder.setDiscountValue(discount_String);
                Modal_Array_to_PlaceOrder.setBatchwiseEartagDetails_Hashmap(batchwiseEartagDetails_Hashmap);
                Modal_Array_to_PlaceOrder.setPaymentMode(paymentMode);




                Intent intent = new Intent(PlaceNewOrder_activity.this, CheckOut_activity .class);
                intent.putExtra("orderid",orderid);
                startActivity(intent);
            }
        });



    }


    private void openGoatinfoDialogLayout() {


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               try{
                   show_total_value_info_Dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                   try{
                       show_total_value_info_Dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

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
                           show_total_value_info_Dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);


                       } else {

                           show_total_value_info_Dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

                       }
                   } catch (Exception e) {
                       show_total_value_info_Dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

                       e.printStackTrace();

                   }



                   try {
                       if (BaseActivity.isDeviceIsMobilePhone) {
                           show_total_value_info_Dialog.setContentView(R.layout.total_value_details_of_goat_dialog);


                       } else {

                           show_total_value_info_Dialog.setContentView(R.layout.total_value_details_of_goat_dialog);
                       }

                   } catch (Exception e) {
                       show_total_value_info_Dialog.setContentView(R.layout.total_value_details_of_goat_dialog);
                       e.printStackTrace();
                   }


                   // show_scan_barcode_dialog.setCancelable(false);
                   show_total_value_info_Dialog.setCanceledOnTouchOutside(false);
                   show_total_value_info_Dialog.show();





                   female_Qty_textview_infoDialog = show_total_value_info_Dialog.findViewById(R.id.female_Qty_textview);
                   male_Qty_textview_infoDialog = show_total_value_info_Dialog.findViewById(R.id.male_Qty_textview);
                   female_Percentage_textview = show_total_value_info_Dialog.findViewById(R.id.female_Percentage_textview);
                   male_percentage_textview = show_total_value_info_Dialog.findViewById(R.id.male_percentage_textview);
                   approxLiveWeightAvg_Textview  = show_total_value_info_Dialog.findViewById(R.id.approxLiveWeightAvg_Textview);
                   meatYieldWeightAvg_Textview  = show_total_value_info_Dialog.findViewById(R.id.meatYieldWeightAvg_Textview);
                   finalGoatValue_textView = show_total_value_info_Dialog.findViewById(R.id.finalGoatValue_textView);


                   totalGoats_textView  = show_total_value_info_Dialog.findViewById(R.id.totalGoats_textView);
                   totalValue_textView  = show_total_value_info_Dialog.findViewById(R.id.totalValue_textView);
                   batchValue_textView  = show_total_value_info_Dialog.findViewById(R.id.batchValue_textView);
                   discountValue_textView  = show_total_value_info_Dialog.findViewById(R.id.discountValue_textView);
                   finalPayment_textView  = show_total_value_info_Dialog.findViewById(R.id.finalPayment_textView);
                   finaltotalWeight_textView  = show_total_value_info_Dialog.findViewById(R.id.finaltotalWeight_textView);
                   totalFeedPriceTextview =  show_total_value_info_Dialog.findViewById(R.id.totalFeedPriceTextview);

                   close_IconLayout_goatsInfo_dialog =  show_total_value_info_Dialog.findViewById(R.id.close_IconLayout_goatsInfo_dialog);





                   close_IconLayout_goatsInfo_dialog.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           try{
                               show_total_value_info_Dialog.cancel();
                               show_total_value_info_Dialog.dismiss();
                           }
                           catch (Exception e){
                           e.printStackTrace();
                           }


                       }
                   });




                   try {
                       male_Qty_textview_infoDialog.setText(" "+String.valueOf((int) maleCount_int)+" nos ");
                       female_Qty_textview_infoDialog.setText(" "+String.valueOf((int) femaleCountInt)+" nos ");
                       male_percentage_textview.setText(("("+String.valueOf(threeDecimalConverter.format(maleRatioValue_double))+"%)"));
                       female_Percentage_textview.setText("("+String.valueOf(threeDecimalConverter.format(femaleRatioValue_double))+"%)" );

                       approxLiveWeightAvg_Textview.setText(String.valueOf(threeDecimalConverter.format(approxLiveWeightAvg)));
                       meatYieldWeightAvg_Textview.setText(String.valueOf(threeDecimalConverter.format(meatYieldAvg)));

                       totalGoats_textView.setText(" "+String.valueOf(cartItemDetailsList_String.size()));
                       batchValue_textView.setText(String.valueOf(twoDecimalConverterWithComma.format(final_batchValue)));
                       finalGoatValue_textView.setText(String.valueOf(twoDecimalConverterWithComma.format(final_totalPriceWithOutDiscount)));



                   } catch (Exception e) {
                       e.printStackTrace();
                   }







               }
                catch(WindowManager.BadTokenException e){


                    e.printStackTrace();
                }
            }

        });





    }


    public void ShowGoatItemDetailsDialog(Modal_GoatEarTagDetails modal_goatEarTagDetails) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Theme_Dialog
                    //Theme_Design_Light

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
                        show_goatEarTagItemDetails_Dialog.show();




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
                        save_button.setText("Add Item");
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


                        goatstatus_layout.setVisibility(View.GONE);





                        selectedGoatStatus_textview.setVisibility(View.VISIBLE);
                        goatstatusradiogrp.setVisibility(View.GONE);
                        save_button.setVisibility(View.VISIBLE);

                        gradeName_editText.setVisibility(View.GONE);
                        gradeName_textview.setVisibility(View.VISIBLE);

                        genderRadioGroup.setVisibility(View.GONE);
                        gender_textview.setVisibility(View.VISIBLE);

                        approxLiveWeight_textview.setVisibility(View.VISIBLE);
                        approxLiveWeight_EditText.setVisibility(View.GONE);


                        meatyeild_textview.setVisibility(View.VISIBLE);
                        meatyeild_edittext.setVisibility(View.GONE);


                        parts_editText.setVisibility(View.GONE);
                        parts_textView.setVisibility(View.VISIBLE);

                        priceWithoutDiscount_edittext.setVisibility(View.GONE);
                        priceWithoutDiscount_textview.setVisibility(View.VISIBLE);

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


                        if (modal_goatEarTagDetails.getBarcodeno().equals("") || modal_goatEarTagDetails.getBarcodeno().equals(null)) {
                            // show_goatEarTagItemDetails_Dialog.cancel();
                            Toast.makeText(PlaceNewOrder_activity.this, "Please Enter new Barcode Again", Toast.LENGTH_SHORT).show();
                            barcode_editText.setVisibility(View.VISIBLE);
                            barcodeNo_textView.setVisibility(View.GONE);


                        }
                        else {
                            barcode_editText.setVisibility(View.GONE);
                            barcodeNo_textView.setVisibility(View.VISIBLE);

                            barcodeNo_textView.setText(String.valueOf(modal_goatEarTagDetails.getBarcodeno()));
                            gradeName_textview.setText(String.valueOf(modal_goatEarTagDetails.getGradename()));
                            gender_textview.setText(String.valueOf(modal_goatEarTagDetails.getGender()));
                            gender = String.valueOf(modal_goatEarTagDetails.getGender());
                            scannedBarcode  = String.valueOf(modal_goatEarTagDetails.getBarcodeno().toUpperCase());
                            
                            
                            
                            double meatYield_double = 0, parts_double = 0, totalWeight_double = 0, price_double = 0;
                            try {
                                String text = String.valueOf(modal_goatEarTagDetails.getMeatyieldweight());
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
                                String text = String.valueOf(modal_goatEarTagDetails.getPartsweight());
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
                                String text = String.valueOf(modal_goatEarTagDetails.getItemPrice());
                                text = text.replaceAll("[^\\d.]", "");
                                if (text.equals("")) {
                                    text = "0";
                                }
                                price_double = Double.parseDouble(text);

                                if (price_double == 0) {
                                    try {
                                        text = String.valueOf(modal_goatEarTagDetails.getTotalPrice_ofItem());
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
                                        String text = String.valueOf(modal_goatEarTagDetails.getTotalPrice_ofItem());
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

                            approxLiveWeight_EditText.setText(String.valueOf(modal_goatEarTagDetails.getApproxliveweight()));
                            parts_textView.setText(String.valueOf(modal_goatEarTagDetails.getPartsweight()));
                            meatyeild_edittext.setText(String.valueOf(modal_goatEarTagDetails.getMeatyieldweight()));
                            discount_edittext.setText(String.valueOf("0"));
                            parts_editText.setText(String.valueOf(modal_goatEarTagDetails.getPartsweight()));



                            approxLiveWeight_textview.setText(String.valueOf(modal_goatEarTagDetails.getApproxliveweight()));
                            parts_textView.setText(String.valueOf(modal_goatEarTagDetails.getPartsweight()));
                            meatyeild_textview.setText(String.valueOf(modal_goatEarTagDetails.getMeatyieldweight()));
                            discount_textView.setText(String.valueOf("0"));

                            totalPrice_textView.setText(String.valueOf(price_double));
                            priceWithoutDiscount_textview.setText(String.valueOf(price_double));




                        }

                        loadingpanelmask_in_dialog.setVisibility(View.GONE);
                        loadingPanel_in_dialog.setVisibility(View.GONE);





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

                                                            Modal_Static_GoatEarTagDetails.barcodeno = String.valueOf(modal_goatEarTagDetails.getBarcodeno());
                                                            Modal_Static_GoatEarTagDetails.batchno = String.valueOf(modal_goatEarTagDetails.getBatchno());

                                                            Modal_UpdatedGoatEarTagDetails.setUpdated_approxliveweight(String.valueOf(approx_Live_Weight_double));
                                                            Modal_UpdatedGoatEarTagDetails.setUpdated_discount(String.valueOf(discountDouble));
                                                            Modal_UpdatedGoatEarTagDetails.setUpdated_meatyieldweight(String.valueOf(meatyeild_weight_double));
                                                            Modal_UpdatedGoatEarTagDetails.setUpdated_partsweight(String.valueOf(parts_Weight_double));
                                                            Modal_UpdatedGoatEarTagDetails.setUpdated_totalPrice_ofItem(String.valueOf(totalPrice_double));
                                                            Modal_UpdatedGoatEarTagDetails.setUpdated_barcodeno(String.valueOf(barcodeNo_textView.getText()));
                                                            Modal_UpdatedGoatEarTagDetails.setUpdated_Price(String.valueOf(pricewithOutdiscount_double));
                                                            Modal_UpdatedGoatEarTagDetails.setUpdated_totalWeight(String.valueOf(totalWeight_double));

                                                            Call_and_ExecuteGoatEarTagDetails(Constants.CallUPDATEMethod);



                                                            try{
                                                                Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod, Constants.CallUPDATEMethod);
                                                            }
                                                            catch (Exception e ){
                                                                e.printStackTrace();
                                                            }



                                                            modal_goatEarTagDetails.setApproxliveweight(String.valueOf(approx_Live_Weight_double));
                                                            modal_goatEarTagDetails.setDiscount(String.valueOf(discountDouble));
                                                            modal_goatEarTagDetails.setMeatyieldweight(String.valueOf(meatyeild_weight_double));
                                                            modal_goatEarTagDetails.setPartsweight(String.valueOf(parts_Weight_double));
                                                            modal_goatEarTagDetails.setTotalPrice_ofItem(String.valueOf(totalPrice_double));
                                                            modal_goatEarTagDetails.setBarcodeno(String.valueOf(barcodeNo_textView.getText()));
                                                            modal_goatEarTagDetails.setItemPrice(String.valueOf(pricewithOutdiscount_double));
                                                            modal_goatEarTagDetails.setTotalPrice_ofItem(String.valueOf(pricewithOutdiscount_double));
                                                            modal_goatEarTagDetails.setTotalPrice(String.valueOf(pricewithOutdiscount_double));
                                                            modal_goatEarTagDetails.setTotalItemWeight(String.valueOf(totalWeight_double));





                                                            try{
                                                                for(int iterator =0 ; iterator < earTagDetailsList.size(); iterator++ ){
                                                                    String barcodeFromArray = earTagDetailsList.get(iterator).getBarcodeno();
                                                                    if(barcodeFromArray.toUpperCase().equals(scannedBarcode)){
                                                                        earTagDetailsList.get(iterator).setSelected(true);

                                                                        try{
                                                                            mAdapter.notifyDataSetChanged();
                                                                        }
                                                                        catch (Exception e){
                                                                            e.printStackTrace();
                                                                        }

                                                                            if (cartItemDetails_Hashmap.size() == 0) {
                                                                                if (!isshowCartItemDetails) {
                                                                                    Call_And_Execute_B2BCartOrder_Details(Constants.CallADDMethod, false);
                                                                                }


                                                                            }
                                                                            Call_And_Execute_B2BCartOrderItem_Details(Constants.CallADDMethod, scannedBarcode, modal_goatEarTagDetails);



                                                                    }
                                                                }
                                                            }
                                                            catch (Exception e){
                                                                e.printStackTrace();
                                                            }





                                                            try {
                                                                showProgressBar(true);
                                                                show_goatEarTagItemDetails_Dialog.cancel();
                                                                show_goatEarTagItemDetails_Dialog.dismiss();
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                          //  Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod, scannedBarcode, isCalledForPlaceNewOrder);


                                                        } else {
                                                            AlertDialogClass.showDialog(PlaceNewOrder_activity.this, R.string.CannotSaveWithOutBarcodeAlert);

                                                        }
                                                    } else {
                                                        AlertDialogClass.showDialog(PlaceNewOrder_activity.this, R.string.CannotSaveWhenTotalWeightisZeroAlert);

                                                    }
                                                } else {
                                                    AlertDialogClass.showDialog(PlaceNewOrder_activity.this, R.string.CannotSaveWhenTotalPriceisZeroAlert);

                                                }
                                            } else {
                                                AlertDialogClass.showDialog(PlaceNewOrder_activity.this, R.string.CannotSaveWhenTotalPriceWithOutDiscountisZeroAlert);

                                            }
                                        } else {
                                            AlertDialogClass.showDialog(PlaceNewOrder_activity.this, R.string.CannotSaveWhenMeatYieldisZeroAlert);

                                        }

                                    } else {
                                        AlertDialogClass.showDialog(PlaceNewOrder_activity.this, R.string.CannotSaveWhenPartsisZeroAlert);

                                    }
                                } else {
                                    AlertDialogClass.showDialog(PlaceNewOrder_activity.this, R.string.CannotSaveWhenApproxWeightisZeroAlert);

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
                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            Objects.requireNonNull(imm).hideSoftInputFromWindow(textView.getWindowToken(), 0);


                                        } else {
                                            AlertDialogClass.showDialog(PlaceNewOrder_activity.this, R.string.CannotSaveWhenPartsisZeroAlert);

                                        }
                                    } else {
                                        AlertDialogClass.showDialog(PlaceNewOrder_activity.this, R.string.CannotSaveWhenMeatYieldisZeroAlert);

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
                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            Objects.requireNonNull(imm).hideSoftInputFromWindow(textView.getWindowToken(), 0);


                                        } else {
                                            AlertDialogClass.showDialog(PlaceNewOrder_activity.this, R.string.CannotSaveWhenPartsisZeroAlert);

                                        }
                                    } else {
                                        AlertDialogClass.showDialog(PlaceNewOrder_activity.this, R.string.CannotSaveWhenMeatYieldisZeroAlert);

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
                                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                Objects.requireNonNull(imm).hideSoftInputFromWindow(textView.getWindowToken(), 0);

                                            } else {
                                                AlertDialogClass.showDialog(PlaceNewOrder_activity.this, R.string.CannotSaveWhenTotalPriceisZeroAlert);

                                            }
                                        } else {
                                            AlertDialogClass.showDialog(PlaceNewOrder_activity.this, R.string.CannotSaveWhenTotalPriceWithOutDiscountisZeroAlert);

                                        }
                                    }
                                    else {
                                        AlertDialogClass.showDialog(PlaceNewOrder_activity.this, R.string.CannotSaveWhendiscountisgreaterthanpriceisZeroAlert);

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
                                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                Objects.requireNonNull(imm).hideSoftInputFromWindow(textView.getWindowToken(), 0);

                                            } else {
                                                AlertDialogClass.showDialog(PlaceNewOrder_activity.this, R.string.CannotSaveWhenTotalPriceisZeroAlert);

                                            }
                                        } else {
                                            AlertDialogClass.showDialog(PlaceNewOrder_activity.this, R.string.CannotSaveWhenTotalPriceWithOutDiscountisZeroAlert);

                                        }
                                    } else {
                                        AlertDialogClass.showDialog(PlaceNewOrder_activity.this, R.string.CannotSaveWhendiscountisgreaterthanpriceisZeroAlert);

                                    }


                                }
                            } catch (Exception e) {
                                e.printStackTrace();
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






    private void Call_and_ExecuteB2BBatchOrderDetails(String callMethod) {


        showProgressBar(true);
        if (isBatchDetailsTableServiceCalled) {
          //  showProgressBar(false);
            return;
        }
        isBatchDetailsTableServiceCalled = true;
        batchDetailsArrayList.clear();
        earTagDetailsList.clear();
        
        
        callback_B2BBatchDetailsInterface = new B2BBatchDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BBatchDetails> batchDetailsArrayListt) {
                if(batchDetailsArrayListt.size()==0){

                    Toast.makeText(PlaceNewOrder_activity.this, Constants.there_is_noData_volley, Toast.LENGTH_SHORT).show();

                }
                else {

                    batchDetailsArrayList  = batchDetailsArrayListt;


                }


                Call_and_ExecuteGoatEarTagDetails(Constants.CallGETListMethod);


              //  Toast.makeText(PlaceNewOrder_activity.this, String.valueOf(batchDetailsArrayList.size()), Toast.LENGTH_SHORT).show();

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
         earTagDetailsresultReceivedCount = 0;

         if(callMethod.equals(Constants.CallGETListMethod)){

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
                            AlertDialogClass.showDialog(PlaceNewOrder_activity.this, R.string.EarTagDetailsNotFound_Instruction);

                        } catch (Exception e) {
                            Toast.makeText(PlaceNewOrder_activity.this, getString(R.string.EarTagDetailsNotFound_Instruction), Toast.LENGTH_SHORT).show();

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
                        earTagDetailsresultReceivedCount = earTagDetailsresultReceivedCount +1;
                        if (earTagItemsForBatchFromDB.size() > 0) {
                            earTagDetailsList.addAll(earTagItemsForBatchFromDB);


                        } else {

                            //  Toast.makeText(PlaceNewOrder_activity.this, Constants.there_is_noData_volley, Toast.LENGTH_SHORT).show();


                        }

                        if (earTagDetailsresultReceivedCount - (batchDetailsArrayList.size() ) == 0){

                            isGoatEarTagDetailsTableServiceCalled = false;
                            setAdapterForRecyclerView(earTagDetailsList);
                        }
                        else{
                         //   Toast.makeText(PlaceNewOrder_activity.this, "There is an error while Fetching Ear Tag ", Toast.LENGTH_SHORT).show();

                        }


                    } catch (Exception e) {
                        earTagDetailsresultReceivedCount = earTagDetailsresultReceivedCount +1;
                        if (earTagDetailsresultReceivedCount - (batchDetailsArrayList.size() ) == 0){

                            isGoatEarTagDetailsTableServiceCalled = false;
                            setAdapterForRecyclerView(earTagDetailsList);
                        }
                        Toast.makeText(PlaceNewOrder_activity.this, "There is an error while Fetching Ear Tag ", Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    }


                }


                @Override
                public void notifyVolleyError(VolleyError error) {
                    earTagDetailsresultReceivedCount = earTagDetailsresultReceivedCount +1;
                    if (earTagDetailsresultReceivedCount - (batchDetailsArrayList.size() ) == 0){

                        setAdapterForRecyclerView(earTagDetailsList);
                        isGoatEarTagDetailsTableServiceCalled = false;
                    }
                    Toast.makeText(PlaceNewOrder_activity.this, "There is an Volley error while Fetching Ear Tag", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void notifyProcessingError(Exception error) {
                    Toast.makeText(PlaceNewOrder_activity.this, "There is an process error while Fetching Ear Tag", Toast.LENGTH_SHORT).show();
                    earTagDetailsresultReceivedCount = earTagDetailsresultReceivedCount +1;
                    if (earTagDetailsresultReceivedCount - (batchDetailsArrayList.size() ) == 0){

                        setAdapterForRecyclerView(earTagDetailsList);
                        isGoatEarTagDetailsTableServiceCalled = false;
                    }


                }


            };

            if (callMethod.equals(Constants.CallGETListMethod)) {
                String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithVariousStatus + "?batchno=" + batchno + "&status1=" + Constants.goatEarTagStatus_Reviewed_and_READYFORSALE + "&status2=" + Constants.goatEarTagStatus_Goatsick  + "&filtertype=" + Constants.api_filtertype_equals;
                GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
                asyncTask.execute();
            }
        }

         }
         else  if(callMethod.equals(Constants.CallUPDATEMethod)){

             callback_GoatEarTagDetails = new GoatEarTagDetailsInterface() {
                 @Override
                 public void notifySuccess(String result) {
                     showProgressBar(false);
                     isGoatEarTagDetailsTableServiceCalled = false;

                 }

                 @Override
                 public void notifySuccessForGettingListItem(ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch) {
                     showProgressBar(false);
                     isGoatEarTagDetailsTableServiceCalled = false;

                 }

                 @Override
                 public void notifyVolleyError(VolleyError error) {
                     showProgressBar(false);
                     isGoatEarTagDetailsTableServiceCalled = false;

                 }

                 @Override
                 public void notifyProcessingError(Exception error) {
                     showProgressBar(false);
                     isGoatEarTagDetailsTableServiceCalled = false;

                 }
             };



             if (callMethod.equals(Constants.CallUPDATEMethod)) {
                 String addApiToCall = API_Manager.updateGoatEarTag;
                 GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
                 asyncTask.execute();
             }

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
                Toast.makeText(PlaceNewOrder_activity.this, "There is an volley error while updating Ear Tag Transaction", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(PlaceNewOrder_activity.this, "There is an Process error while updating Ear Tag Transaction", Toast.LENGTH_SHORT).show();
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
                    Modal_GoatEarTagTransaction.deliverycenterkey = deliveryCentreKey;
                    Modal_GoatEarTagTransaction.deliverycentername = deliveryCentreName;

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

                        Modal_GoatEarTagTransaction.gradename=(selectedGenderName);

                    }
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_deliverycenterkey_boolean()) {
                        Modal_GoatEarTagTransaction.deliverycenterkey = deliveryCentreKey;
                    }
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_deliverycentername_boolean()) {
                        Modal_GoatEarTagTransaction.deliverycentername = deliveryCentreName;
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






    private void setAdapterForRecyclerView(ArrayList<Modal_GoatEarTagDetails> earTagDetailsList) {

        earTagDetailsList = sortThisArrayUsingBarcode(earTagDetailsList);

        for(int iterator = 0 ; iterator < earTagDetailsList.size(); iterator++){
            if(cartItemDetailsList_String.contains(earTagDetailsList.get(iterator).getBarcodeno())){
                earTagDetailsList.get(iterator).setSelected(true);
                if(!batchwiseEartagDetails_Hashmap.containsKey(earTagDetailsList.get(iterator).getBatchno())) {
                    ArrayList<String>barcodenoList = new ArrayList<>();

                    batchwiseEartagDetails_Hashmap.put(earTagDetailsList.get(iterator).getBatchno(),barcodenoList);
                }

                }
            else{
                if(batchwiseEartagDetails_Hashmap.containsKey(earTagDetailsList.get(iterator).getBatchno())){
                    ArrayList<String>barcodenoList = new ArrayList<>();
                    barcodenoList = batchwiseEartagDetails_Hashmap.get(earTagDetailsList.get(iterator).getBatchno());
                    if(!barcodenoList.contains(earTagDetailsList.get(iterator).getBatchno())){
                        barcodenoList.add(earTagDetailsList.get(iterator).getBarcodeno());
                        batchwiseEartagDetails_Hashmap.put(earTagDetailsList.get(iterator).getBatchno(),barcodenoList);

                    }

                }
                else{
                    ArrayList<String>barcodenoList = new ArrayList<>();
                    barcodenoList.add(earTagDetailsList.get(iterator).getBarcodeno());
                    batchwiseEartagDetails_Hashmap.put(earTagDetailsList.get(iterator).getBatchno(),barcodenoList);
                }
            }
        }
        mAdapter = new Adapter_PlaceOrder_recyclerView(earTagDetailsList,PlaceNewOrder_activity.this ,PlaceNewOrder_activity.this);
        GridLayoutManager manager = new GridLayoutManager(PlaceNewOrder_activity.this,5,RecyclerView.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        showProgressBar(false);

    }

    private ArrayList<Modal_GoatEarTagDetails> sortThisArrayUsingBarcode(ArrayList<Modal_GoatEarTagDetails> earTagDetailsList) {


        final Pattern p = Pattern.compile("^\\d+");
        Comparator<Modal_GoatEarTagDetails> c = new Comparator<Modal_GoatEarTagDetails>() {
            @Override
            public int compare(Modal_GoatEarTagDetails object1, Modal_GoatEarTagDetails object2) {
                Matcher m = p.matcher(object1.getBarcodeno());
                Integer number1 = null;
                if (!m.find()) {
                    Matcher m1 = p.matcher(object2.getBarcodeno());
                    if (m1.find()) {
                        return object2.getBarcodeno().compareTo(object1.getBarcodeno());
                    } else {
                        return object1.getBarcodeno().compareTo(object2.getBarcodeno());
                    }
                } else {
                    Integer number2 = null;
                    number1 = Integer.parseInt(m.group());
                    m = p.matcher(object2.getBarcodeno());
                    if (!m.find()) {
                        // return object1.compareTo(object2);
                        Matcher m1 = p.matcher(object1.getBarcodeno());
                        if (m1.find()) {
                            return object2.getBarcodeno().compareTo(object1.getBarcodeno());
                        } else {
                            return object1.getBarcodeno().compareTo(object2.getBarcodeno());
                        }
                    } else {
                        number2 = Integer.parseInt(m.group());
                        int comparison = number1.compareTo(number2);
                        if (comparison != 0) {
                            return comparison;
                        } else {
                            return object1.getBarcodeno().compareTo(object2.getBarcodeno());
                        }
                    }
                }
            }
        };

        Collections.sort(earTagDetailsList, c);





        return  earTagDetailsList;
    }


    private void closeSearchBarEditText() {
        search_IconLayout.setVisibility(View.VISIBLE);
        searchbarcodeNo_textView.setVisibility(View.VISIBLE);
        baarcodeNo_search_editText.setVisibility(View.GONE);
        close_IconLayout.setVisibility(View.GONE);
    }

    private void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
    private void showSearchBarEditText() {
        baarcodeNo_search_editText.setVisibility(View.VISIBLE);
        searchbarcodeNo_textView.setVisibility(View.GONE);
        close_IconLayout.setVisibility(View.VISIBLE);
        search_IconLayout.setVisibility(View.GONE);
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

    public void Call_And_Execute_B2BCartOrderItem_Details(String callMethod, String barcodeno, Modal_GoatEarTagDetails modal_goatEarTagDetails) {


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
                                if(clearcartnow){

                                    Initialize_and_ExecuteB2BCartItemDetails_BulkUpdate(Constants.CallDELETEMethod);
                                }
                                else{


                                    Calculate_and_showEarTagDetails();

                                    Call_and_ExecuteB2BBatchOrderDetails(Constants.CallGETListMethod);
                                }

                            }
                        }

                    }
                    else{
                        if(clearcartnow) {
                            orderid = "";
                            cartItemDetails_Hashmap.clear();
                            cartItemDetailsList_String.clear();
                        }

                        Calculate_and_showEarTagDetails();

                        Call_and_ExecuteB2BBatchOrderDetails(Constants.CallGETListMethod);

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
                        Calculate_and_showEarTagDetails();

                        Call_and_ExecuteB2BBatchOrderDetails(Constants.CallGETListMethod);
                    }
                    else{

                        showProgressBar(false);
                        Toast.makeText(PlaceNewOrder_activity.this, "There is no Cart Item Details for this batch", Toast.LENGTH_SHORT).show();

                    }


                }
                else {

                    if(callMethod.equals(Constants.CallADDMethod)) {

                        if (!cartItemDetailsList_String.contains(String.valueOf(barcodeno))) {
                            cartItemDetailsList_String.add(String.valueOf(barcodeno));
                            cartItemDetails_Hashmap.put(String.valueOf(barcodeno), modal_b2BCartItemDetails_toAdd);
                        }

                    }
                    else if(callMethod.equals(Constants.CallDELETEMethod)) {
                        if (cartItemDetailsList_String.contains(String.valueOf(barcodeno))) {
                            cartItemDetailsList_String.remove(String.valueOf(barcodeno));
                            cartItemDetails_Hashmap.remove(String.valueOf(barcodeno));

                        }

                    }

                    Calculate_and_showEarTagDetails();
                    isB2BCartItemDetailsCalled = false;
                    showProgressBar(false);

                }



            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isB2BCartItemDetailsCalled = false;
                showProgressBar(false);
                Toast.makeText(PlaceNewOrder_activity.this, "There is Volley error in CartItemDetails", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void notifyProcessingError(Exception error) {
                Toast.makeText(PlaceNewOrder_activity.this, "There is processing error in CartItemDetails", Toast.LENGTH_SHORT).show();

                isB2BCartItemDetailsCalled = false;
                showProgressBar(false);
            }


        };


        if(callMethod.equals(Constants.CallADDMethod)) {
            String getApiToCall = API_Manager.addCartItemDetails;

            try {

                modal_b2BCartItemDetails_toAdd.totalItemWeight = (String.valueOf(modal_goatEarTagDetails.getTotalItemWeight()));
                modal_b2BCartItemDetails_toAdd.approxliveweight = (String.valueOf(modal_goatEarTagDetails.getApproxliveweight()));
                modal_b2BCartItemDetails_toAdd.discount = (String.valueOf(modal_goatEarTagDetails.getDiscount()));
                modal_b2BCartItemDetails_toAdd.meatyieldweight = (String.valueOf(modal_goatEarTagDetails.getMeatyieldweight()));
                modal_b2BCartItemDetails_toAdd.partsweight = (String.valueOf(modal_goatEarTagDetails.getPartsweight()));
                modal_b2BCartItemDetails_toAdd.totalPrice_ofItem = (String.valueOf(modal_goatEarTagDetails.getTotalPrice_ofItem()));
                modal_b2BCartItemDetails_toAdd.gender = String.valueOf(modal_goatEarTagDetails.getGender());
                modal_b2BCartItemDetails_toAdd.barcodeno = String.valueOf(modal_goatEarTagDetails.getBarcodeno());
                modal_b2BCartItemDetails_toAdd.batchno = String.valueOf(modal_goatEarTagDetails.getBatchno());
                modal_b2BCartItemDetails_toAdd.gradename = String.valueOf(modal_goatEarTagDetails.getGradename());
                modal_b2BCartItemDetails_toAdd.itemaddeddate = String.valueOf(DateParser.getDate_and_time_newFormat());
                modal_b2BCartItemDetails_toAdd.itemprice = (String.valueOf(modal_goatEarTagDetails.getItemPrice()));
                modal_b2BCartItemDetails_toAdd.orderid = (String.valueOf(orderid));


            }
            catch (Exception e){
                e.printStackTrace();
            }


            B2BCartItemDetails asyncTask = new B2BCartItemDetails(callback_b2BCartItemDetaillsInterface,  getApiToCall, callMethod,modal_b2BCartItemDetails_toAdd);
            asyncTask.execute();



        }
        else if(callMethod.equals(Constants.CallDELETEMethod)){
            String getApiToCall = API_Manager.deleteCartItemDetails+"?barcodeno="+barcodeno+"&orderid=" + orderid;
            B2BCartItemDetails asyncTask = new B2BCartItemDetails(callback_b2BCartItemDetaillsInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }
        else if(callMethod.equals(Constants.CallGETListMethod)) {
            String getApiToCall = API_Manager.getCartItemDetailsForOrderid + orderid;
            B2BCartItemDetails asyncTask = new B2BCartItemDetails(callback_b2BCartItemDetaillsInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }

    }

    private void Calculate_and_showEarTagDetails() {
         maleRatioValue_double = 0; femaleRatioValue_double = 0; meatYieldAvg = 0; approxLiveWeightAvg = 0;final_totalPriceWithOutDiscount=0;final_totalWeight =0;final_batchValue =0;
         maleCount_int = 0; femaleCountInt = 0; totalMeatYeild = 0; totalaApproxLiveWeight = 0; totalFeedDouble = 0;
        double meatYield_Double =0 , approxLiveWeight_Double =0 , parts_Double =0 , price_Double =0 , discount_Double =0 , totalPrice_Double =0 , totalWeight_Double =0 ;
         goat_maleCount =0 ; goat_femaleCount =0; goat_oldFemaleCount = 0 ; sheep_maleCount = 0 ; sheep_femaleCount = 0 ;
         goatmaleRatio_double = 0 ; goatfemaleRatio_double = 0 ; goatoldfemaleRatio_double = 0;
        sheepMaleRatio = 0 ; sheepFemaleRatio = 0;

        try {
            if(cartItemDetailsList_String.size()>0) {
                for (int iterator = 0; iterator < cartItemDetailsList_String.size(); iterator++) {
                    String barcodeFromArrayList = cartItemDetailsList_String.get(iterator);
                    if (cartItemDetails_Hashmap.containsKey(barcodeFromArrayList)) {


                        try{
                            itemCountDetails2_LinearLayout.setVisibility(View.VISIBLE);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                        Modal_B2BCartItemDetails modal_b2BCartItemDetails = cartItemDetails_Hashmap.get(barcodeFromArrayList);


                        try {

                            String gender_string1 ="";

                            try{
                                gender_string1 = String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getGender()).toUpperCase();
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            try{
                                gender_string1 = gender_string1.replaceAll("GOAT_","");
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            try{
                                gender_string1 = gender_string1.replaceAll("SHEEP_","");
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            try{
                                gender_string1 = gender_string1.replaceAll("OLD " +
                                        "","");
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            try {
                                if (String.valueOf(gender_string1).equals("MALE")) {
                                    maleCount_int = maleCount_int + 1;
                                } else if (String.valueOf(gender_string1).toUpperCase().equals("FEMALE")) {
                                    femaleCountInt = femaleCountInt + 1;

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }



                            try {
                                if (String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getGender()).toUpperCase().contains("GOAT")) {

                                    String gender_string ="";

                                    try{
                                        gender_string = String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getGender()).toUpperCase();
                                    }
                                    catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    try{
                                        gender_string = gender_string.replaceAll("GOAT_","");
                                    }
                                    catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    try {
                                        if (gender_string.equals("MALE")) {
                                            goat_maleCount = goat_maleCount + 1;
                                        } else if (gender_string.equals("FEMALE")) {
                                            goat_femaleCount = goat_femaleCount + 1;

                                        }
                                        else if (gender_string.equals("OLD FEMALE")) {
                                            goat_oldFemaleCount = goat_oldFemaleCount + 1;

                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else if (String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getGender()).toUpperCase().contains("SHEEP")) {
                                    String gender_string ="";

                                    try{
                                        gender_string = String.valueOf(Objects.requireNonNull(modal_b2BCartItemDetails).getGender()).toUpperCase();
                                    }
                                    catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    try{
                                        gender_string = gender_string.replaceAll("SHEEP_","");
                                    }
                                    catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    try {
                                        if (gender_string.equals("MALE")) {
                                            sheep_maleCount = sheep_maleCount + 1;
                                        } else if (gender_string.equals("FEMALE")) {
                                            sheep_femaleCount = sheep_femaleCount + 1;

                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                maleRatioValue_double = ((maleCount_int / cartItemDetails_Hashmap.size()) * 100);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                femaleRatioValue_double = ((femaleCountInt / cartItemDetails_Hashmap.size()) * 100);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }



                            try {
                                goatmaleRatio_double = ((goat_maleCount / cartItemDetails_Hashmap.size()) * 100);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                goatfemaleRatio_double = ((goat_femaleCount / cartItemDetails_Hashmap.size()) * 100);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                goatoldfemaleRatio_double = ((goat_oldFemaleCount / cartItemDetails_Hashmap.size()) * 100);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                sheepMaleRatio = ((sheep_maleCount / cartItemDetails_Hashmap.size()) * 100);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                sheepFemaleRatio = ((sheep_femaleCount / cartItemDetails_Hashmap.size()) * 100);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }





                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        try {
                            selected_goatsCount.setText(String.valueOf(String.valueOf(cartItemDetails_Hashmap.size())));
                            male_Qty_textview.setText(String.valueOf(String.valueOf((int) maleCount_int)));
                            female_Qty_textview.setText(String.valueOf(String.valueOf((int) femaleCountInt)));
                         ///   oldfemale_Qty_textview.setText(String.valueOf(String.valueOf((int) goat_oldFemaleCount)));


                          //  sheepmale_Qty_textview.setText(String.valueOf(String.valueOf((int) sheep_maleCount)));
                           // sheepfemale_Qty_textview.setText(String.valueOf(String.valueOf((int) sheep_femaleCount)));

                            male_Percentage_textview_bottomdialog.setText(String.valueOf("( " + String.valueOf(oneDecimalConverter.format(maleRatioValue_double))+"%)"));
                            female_Percentage_textview_bottomdialog.setText(String.valueOf("( " + String.valueOf(oneDecimalConverter.format(femaleRatioValue_double))+"%)"));
                        //    oldfemale_Percentage_textview.setText(String.valueOf("( " + String.valueOf(oneDecimalConverter.format(goatoldfemaleRatio_double))+"%)"));

                         //   sheepfemale_Percentage_textview.setText(String.valueOf("( " + String.valueOf(oneDecimalConverter.format(sheepFemaleRatio))+"%)"));
                       //     sheepmale_Percentage_textview.setText(String.valueOf("( " + String.valueOf(oneDecimalConverter.format(sheepMaleRatio))+"%)"));



                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                        String meatYield_String ="0" , approxLiveWeight_String = "0"  , parts_String ="0" , price_String = "0",  totalPrice_String="0",totalWeight_String="0";
                        meatYield_Double =0 ; approxLiveWeight_Double =0 ; parts_Double =0 ; price_Double =0; discount_Double =0 ; totalPrice_Double =0 ;


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

                            try{
                                if(final_totalPriceWithOutDiscount ==0 || cartItemDetailsList_String.size() ==0 ){
                                    try{
                                        final_batchValue  =  0;
                                    }
                                    catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }

                                else{

                                    try{
                                        final_batchValue  =  final_totalPriceWithOutDiscount  / cartItemDetailsList_String.size();
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
                                meatYieldAvg = totalMeatYeild /cartItemDetailsList_String.size();
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }

                            try{
                                approxLiveWeightAvg = totalaApproxLiveWeight /cartItemDetailsList_String.size();

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
            }
            else{


                try{
                    itemCountDetails2_LinearLayout.setVisibility(View.GONE);
                }
                catch (Exception e){
                    e.printStackTrace();
                }



                try {
                    selected_goatsCount.setText(String.valueOf(String.valueOf(cartItemDetails_Hashmap.size())));
                    male_Qty_textview.setText(String.valueOf(String.valueOf((int) maleCount_int)));
                    female_Qty_textview.setText(String.valueOf(String.valueOf((int) femaleCountInt)));

                    male_Percentage_textview_bottomdialog.setText(String.valueOf("( " + String.valueOf(oneDecimalConverter.format(maleRatioValue_double))+"%)"));
                    female_Percentage_textview_bottomdialog.setText(String.valueOf("( " + String.valueOf(oneDecimalConverter.format(femaleRatioValue_double))+"%)"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch(Exception e){


            try{
                itemCountDetails2_LinearLayout.setVisibility(View.GONE);
            }
            catch (Exception e1){
                e1.printStackTrace();
            }



            try {
                selected_goatsCount.setText(String.valueOf(String.valueOf(cartItemDetails_Hashmap.size())));
                male_Qty_textview.setText(String.valueOf(String.valueOf((int) maleCount_int)));
                female_Qty_textview.setText(String.valueOf(String.valueOf((int) femaleCountInt)));

                male_Percentage_textview_bottomdialog.setText(String.valueOf("( " + String.valueOf(oneDecimalConverter.format(maleRatioValue_double))+"%)"));
                female_Percentage_textview_bottomdialog.setText(String.valueOf("( " + String.valueOf(oneDecimalConverter.format(femaleRatioValue_double))+"%)"));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }


    }



    public void Call_And_Execute_B2BCartOrder_Details(String callMethod, boolean callBulkUpdate) {



        showProgressBar(true);
        if (isB2BCartOrderTableServiceCalled) {
            // showProgressBar(false);
            return;
        }
        isB2BCartOrderTableServiceCalled = true;
        callback_b2bCartOrderDetails = new B2BCartOrderDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BCartOrderDetails> arrayList) {

            }

            @Override
            public void notifySuccess(String result) {
                if (result.equals(Constants.emptyResult_volley)) {
                    showProgressBar(false);
                    isB2BCartOrderTableServiceCalled = false;

                    Toast.makeText(PlaceNewOrder_activity.this, "There is no Cart Details for this batch", Toast.LENGTH_SHORT).show();


                }
                else {
                    if(callMethod.equals(Constants.CallGETMethod)) {


                        retailerMobileNo = "";
                        retaileraddress = "";
                        retailerName = "";
                        retailerKey = "";
                        feedweight = "";
                        feedPricePerKg = "";
                        feedPrice = "";
                        discount_String = "";

                        retailerKey = Modal_B2BCartOrderDetails.getRetailerkey();
                        retailerMobileNo = Modal_B2BCartOrderDetails.getRetailermobileno();
                        retailerName = Modal_B2BCartOrderDetails.getRetailername();
                        orderid = Modal_B2BCartOrderDetails.getOrderid();
                        retaileraddress = Modal_B2BCartOrderDetails.getRetaileraddress();
                        discount_String = Modal_B2BCartOrderDetails.getDiscountAmount();


                        feedPrice = (String.valueOf(Modal_B2BCartOrderDetails.getFeedPrice()));
                        feedPricePerKg = (String.valueOf(Modal_B2BCartOrderDetails.getFeedPriceperkg()));
                        feedweight = (String.valueOf(Modal_B2BCartOrderDetails.getFeedWeight()));

                    }

                    else   if(callMethod.equals(Constants.CallDELETEMethod)){
                        if(callBulkUpdate){
                            Initialize_and_ExecuteB2BCartItemDetails_BulkUpdate(Constants.CallDELETEMethod);
                        }
                    }






                    isB2BCartOrderTableServiceCalled = false;
                }
            }
            @Override
            public void notifyVolleyError(VolleyError error) {

                Toast.makeText(PlaceNewOrder_activity.this, "There is an volley error while updating CartOrder Details", Toast.LENGTH_SHORT).show();
                showProgressBar(false);
                isB2BCartOrderTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {

                Toast.makeText(PlaceNewOrder_activity.this, "There is an Process error while updating CartOrder Details", Toast.LENGTH_SHORT).show();

                showProgressBar(false);
                isB2BCartOrderTableServiceCalled = false;


            }




        };

        if(callMethod.equals(Constants.CallADDMethod)){
            orderid = String.valueOf(System.currentTimeMillis());
            try{
                Home_Screen.orderid = "";
            }
            catch (Exception e){
                Home_Screen.orderid ="";
                e.printStackTrace();
            }
            Modal_B2BCartOrderDetails.orderid = orderid;
            Modal_B2BCartOrderDetails.deliverycenterkey = deliveryCentreKey;
            Modal_B2BCartOrderDetails.deliverycentername = deliveryCentreName;
            Modal_B2BCartOrderDetails.itemaddeddate = DateParser.getDate_and_time_newFormat();
            String getApiToCall = API_Manager.addCartOrderDetails ;
            B2BCartOrderDetails asyncTask = new B2BCartOrderDetails(callback_b2bCartOrderDetails,  getApiToCall, Constants.CallADDMethod);
            asyncTask.execute();

        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){

        }
        else if(callMethod.equals(Constants.CallGETMethod)){

            String getApiToCall = API_Manager.getCartOrderDetailsForDeliveryCentrekey+"?deliverycentrekey="+ deliveryCentreKey;

            B2BCartOrderDetails asyncTask = new B2BCartOrderDetails(callback_b2bCartOrderDetails,  getApiToCall, Constants.CallGETMethod);
            asyncTask.execute();

        }
        else if(callMethod.equals(Constants.CallGETListMethod)){

        }
        else if(callMethod.equals(Constants.CallDELETEMethod)){
            try {

                String addApiToCall = API_Manager.deleteCartOrderDetails+"?orderid="+orderid+"&deliverycentrekey="+ deliveryCentreKey;
                B2BCartOrderDetails asyncTask = new B2BCartOrderDetails(callback_b2bCartOrderDetails, addApiToCall, Constants.CallDELETEMethod);
                asyncTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

                    orderid = "";
                    cartItemDetails_Hashmap.clear();
                    cartItemDetailsList_String.clear();
                    Calculate_and_showEarTagDetails();
                    if(!clearcartnow) {
                        onBackPressed();
                    }
                    else {
                        clearcartnow = false;
                        Call_and_ExecuteB2BBatchOrderDetails(Constants.CallGETListMethod);
                    }

                }

                @Override
                public void notifySuccessForGettingListItem(ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch) {
                    isB2BCartItemDetailsBulkUpdate = false;
                    showProgressBar(false);

                }

                @Override
                public void notifyVolleyError(VolleyError error) {
                    Toast.makeText(PlaceNewOrder_activity.this, "There is an volley error while updating Ear Tag", Toast.LENGTH_SHORT).show();

                    showProgressBar(false);
                    isGoatEarTagDetailsTableServiceCalled = false;
                }

                @Override
                public void notifyProcessingError(Exception error) {
                    Toast.makeText(PlaceNewOrder_activity.this, "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();
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


    public void UpdateValueInBatchWiseArray(String batchno, String barcodeno , boolean AddData) {


        if(AddData) {
            if (batchwiseEartagDetails_Hashmap.containsKey(batchno)) {
                ArrayList<String> barcodenoList = new ArrayList<>();
                barcodenoList = batchwiseEartagDetails_Hashmap.get(batchno);
                if (!barcodenoList.contains(barcodeno)) {
                    barcodenoList.add(barcodeno);
                    batchwiseEartagDetails_Hashmap.put(batchno, barcodenoList);

                }

            } else {
                ArrayList<String> barcodenoList = new ArrayList<>();
                barcodenoList.add(barcodeno);
                batchwiseEartagDetails_Hashmap.put(batchno, barcodenoList);
            }

        }
        else{
            if (batchwiseEartagDetails_Hashmap.containsKey(batchno)) {
                ArrayList<String> barcodenoList = new ArrayList<>();
                barcodenoList = batchwiseEartagDetails_Hashmap.get(batchno);
                if (barcodenoList.contains(barcodeno)) {
                    barcodenoList.remove(barcodenoList.indexOf(barcodeno));
                    batchwiseEartagDetails_Hashmap.put(batchno, barcodenoList);

                }

            }
        }
    }
}