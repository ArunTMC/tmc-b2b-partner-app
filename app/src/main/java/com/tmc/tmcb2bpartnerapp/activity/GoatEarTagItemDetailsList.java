package com.tmc.tmcb2bpartnerapp.activity;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.adapter.Adapter_EarTagItemDetails_List;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartItemDetails_BulkUpdate;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BItemCtgy;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagTransaction;
import com.tmc.tmcb2bpartnerapp.fragment.BatchItemDetailsFragment_withoutScanBarcode;
import com.tmc.tmcb2bpartnerapp.fragment.DeliveryCenter_PlaceOrderScreen_SecondVersn;
import com.tmc.tmcb2bpartnerapp.fragment.DeliveryCentre_PlaceOrderScreen_Fragment;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartItemDetaillsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartItemDetails_BulkUpdateInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BGoatGradeDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BItemCtgyInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderItemDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.BarcodeScannerInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagTransactionInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetailsStatic;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BItemCtgy;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagTransaction;
import com.tmc.tmcb2bpartnerapp.modal.Modal_Static_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_UpdatedGoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BarcodeScannerScreen;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.TMCAlertDialogClass;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoatEarTagItemDetailsList extends BaseActivity {
    public static ListView earTagItems_listview;
    CardView scanBarcode_view;
    public static LinearLayout loadingPanel , loadingpanelmask,back_IconLayout , search_IconLayout , close_IconLayout;
    TextView toolBarHeader_TextView,scanBarcodeLayout_textview  ,barcodeNo_textView,instruction_Textview;
    FrameLayout batchItemDetailsFrame;
    public Fragment mfragment;
    EditText barcode_editText;
    LinearLayout cartclearing_IconLayout;
    public static BarcodeScannerInterface barcodeScannerInterface = null;


    public static Adapter_EarTagItemDetails_List adapter_earTagItemDetails_list =null;
    boolean isGoatEarTagDetailsTableServiceCalled = false , isSearchButtonClicked =false;
    boolean isB2BCartDetailsCalled = false;
    B2BCartItemDetaillsInterface callback_b2BCartItemDetaillsInterface = null;

    B2BCartOrderDetailsInterface callback_b2bOrderDetails =null ;
    boolean isB2BCartOrderTableServiceCalled = false;

    String batchno ="";
    public String CalledFrom ="";
    String TaskToDo ="";
    String scannedBarcode="";
    public String supplierKey ="" , deliveryCenterKey ="" , deliveryCenterName ="",value_forFragment ="" ,userMobileNo = "" ,batchStatus ="";
    public  String statusToFilter ="";public String orderid ="";
    public static ArrayList<String> earTagItemsBarcodeList = new ArrayList<>();

    static ArrayList<Modal_B2BGoatGradeDetails> goatGrade_arrayLsit = new ArrayList<>();

    B2BGoatGradeDetailsInterface callback_goatGradeDetailsInterface = null;
    boolean isGoatGradeDetailsServiceCalled = false;
    private boolean isTransactionSafe;
    private boolean isTransactionPending , isGoatEarTagTransactionTableServiceCalled = false;
    GoatEarTagTransactionInterface callback_GoatEarTagTransactionInterface = null;


    public static ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch = new ArrayList<>();
    public static ArrayList<Modal_GoatEarTagDetails> sorted_earTagItemsForBatch = new ArrayList<>();


    GoatEarTagDetailsInterface callback_GoatEarTagDetails = null;
    boolean isB2BOrderItemDetailsTableServiceCalled = false;
    B2BOrderItemDetailsInterface callback_b2bOrderItemDetails;
    boolean isB2BItemCtgyTableServiceCalled = false;
    B2BCartItemDetails_BulkUpdateInterface callbackB2BCartItemDetails_bulkUpdateInterface;
    boolean isB2BCartItemDetailsBulkUpdate = false;

    boolean doeshavetocallitbatchwise = false;

    B2BItemCtgyInterface callback_B2BItemCtgyInterface;
    public static ArrayList<Modal_B2BItemCtgy> ctgy_subCtgy_DetailsArrayList = new ArrayList<>();


    public static Dialog show_goatEarTagItemDetails_Dialog = null;
    DecimalFormat df2 = new DecimalFormat(Constants.twoDecimalPattern);
    DecimalFormat df3 = new DecimalFormat(Constants.threeDecimalPattern);



    //double
    double approx_Live_Weight_double = 0 , meatyeild_weight_double = 0 , parts_Weight_double = 0 , totalWeight_double = 0 , discountDouble = 0 ,
            totalPrice_double = 0 ,     pricewithOutdiscount_double = 0;
    static int  final_totalGoats =0;static double final_totalPriceWithOutDiscount = 0 , final_batchValue = 0, final_discountValue = 0 , final_totalPayment = 0 ;


    String gender ="";
    public String selectedItemsStatus = "" , selectedGenderName ="" , selectedGradeName ="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);




        try {
            BaseActivity.baseActivity.getDeviceName();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            if (BaseActivity.isDeviceIsMobilePhone) {
                // Inflate the layout for this fragment
                setContentView(R.layout.activity_goat_ear_tag_item_details_list);
            } else {

                // Inflate the layout for this fragment
                setContentView(R.layout.pos_activity_goat_ear_tag_item_details_list);

            }
        }
        catch (Exception e){
            e.printStackTrace();
            setContentView(R.layout.activity_goat_ear_tag_item_details_list);

        }





        earTagItems_listview = findViewById(R.id.earTagItems_listview);
        scanBarcode_view = findViewById(R.id.scanBarcode_view);
        loadingPanel = findViewById(R.id.loadingPanel);
        loadingpanelmask = findViewById(R.id.loadingpanelmask);
        back_IconLayout = findViewById(R.id.back_IconLayout);
        search_IconLayout = findViewById(R.id.search_IconLayout);
        close_IconLayout = findViewById(R.id.close_IconLayout);
        barcode_editText = findViewById(R.id.barcode_editText);
        barcodeNo_textView = findViewById(R.id.barcodeNo_textView);

        toolBarHeader_TextView = findViewById(R.id.toolBarHeader_TextView);
        batchItemDetailsFrame  = findViewById(R.id.batchItemDetailsFrame);
        scanBarcodeLayout_textview = findViewById(R.id.scanBarcodeLayout_textview);
        instruction_Textview = findViewById(R.id.instruction_Textview);
        cartclearing_IconLayout  = findViewById(R.id.cartclearing_IconLayout);



        Intent intent = getIntent();
        batchno = intent.getStringExtra(String.valueOf("batchno"));
        CalledFrom = intent.getStringExtra(String.valueOf("CalledFrom"));
        doeshavetocallitbatchwise = intent.getBooleanExtra("doeshavetocallitbatchwise",false);
        TaskToDo  = intent.getStringExtra(String.valueOf("TaskToDo"));


        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);

        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");

        if(CalledFrom.equals(Constants.userType_DeliveryCenter)){
            supplierKey  = intent.getStringExtra(String.valueOf("supplierkey"));


            SharedPreferences sh
                    = getSharedPreferences("LoginData",
                    MODE_PRIVATE);
            userMobileNo = sh.getString("UserMobileNumber", "");

        }
        if(CalledFrom.equals(Constants.userType_SupplierCenter)){
            if(Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Loading)){

                scanBarcodeLayout_textview.setText(getString(R.string.search_edit_barcode));
            }
            else{
                scanBarcodeLayout_textview.setText(getString(R.string.search_view_barcode));

            }
        }
        else{


            if(TaskToDo.equals(getString(R.string.view_sold_items_in_batch)) || TaskToDo.equals(getString(R.string.view_unsold_items_in_batch)) ) {
                scanBarcodeLayout_textview.setText(getString(R.string.search_view_barcode));
            }
            else  if(TaskToDo.equals(getString(R.string.edit_unsold_items_in_batch)) ) {
                scanBarcodeLayout_textview.setText(getString(R.string.search_edit_barcode));
            }
            else {
                if (Modal_B2BBatchDetailsStatic.getStatus().equals(Constants.batchDetailsStatus_Sold) || Modal_B2BBatchDetailsStatic.getStatus().equals(Constants.batchDetailsStatus_Cancelled)) {
                    scanBarcodeLayout_textview.setText(getString(R.string.search_view_barcode));

                } else {
                    scanBarcodeLayout_textview.setText(getString(R.string.search_edit_barcode));

                }
            }
        }

        if(CalledFrom.equals(getString(R.string.billing_Screen))){
            orderid = intent.getStringExtra(String.valueOf("orderid"));
            scanBarcodeLayout_textview.setText(getString(R.string.search_view_barcode));
        }
        else   if(CalledFrom.equals(getString(R.string.placedOrder_Details_Screen))){
            orderid = intent.getStringExtra(String.valueOf("orderid"));
            scanBarcodeLayout_textview.setText(getString(R.string.search_view_barcode));
        }
        else   if(CalledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion))){
            orderid = intent.getStringExtra(String.valueOf("orderid"));
            scanBarcodeLayout_textview.setText(getString(R.string.search_view_barcode_secondVersn));
        }
        else   if(CalledFrom.equals(getString(R.string.datewise_placedOrder_Details_Screen_SecondVersion))){
            orderid = intent.getStringExtra(String.valueOf("orderid"));
            scanBarcodeLayout_textview.setText(getString(R.string.search_view_barcode_secondVersn));
        }
        else   if(CalledFrom.equals(getString(R.string.markdeliveredOrders_detailsScreen))){
            orderid = intent.getStringExtra(String.valueOf("orderid"));
            scanBarcodeLayout_textview.setText(getString(R.string.search_view_barcode_secondVersn));
        }

        if(TaskToDo.equals("NewAddedItem")){
            if(Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Loading)){
                toolBarHeader_TextView.setText(getString(R.string.edit_batch_item_details));

            }
            else{
                toolBarHeader_TextView.setText(getString(R.string.view_batch_item_details));

            }




        }
        else  if(TaskToDo.equals("ItemInCart_SecondVersn")){
                toolBarHeader_TextView.setText(getString(R.string.items_in_the_cart));
                cartclearing_IconLayout.setVisibility(View.VISIBLE);
        }
        else  if (TaskToDo.equals("Oldtem")) {
            toolBarHeader_TextView.setText(getString(R.string.view_batch_item_details));

        }
        else  if(TaskToDo.equals("ViewSoldItem")){
            toolBarHeader_TextView.setText(getString(R.string.sold_items_list));

        }
        else  if(TaskToDo.equals("ViewUnSoldItem")){
            toolBarHeader_TextView.setText(getString(R.string.unsold_items_list));


        }
        else  if(TaskToDo.equals("ViewUnReviewedItem")){
            toolBarHeader_TextView.setText(getString(R.string.unreviewed_items_list));


        }
        else  if(TaskToDo.equals("ViewReviewedItem")){
            toolBarHeader_TextView.setText(getString(R.string.reviewed_items_list));


        }
        else  if(TaskToDo.equals("ItemInCart")){
            toolBarHeader_TextView.setText(getString(R.string.items_in_the_cart));


        }
        else  if(TaskToDo.equals("ItemInOrderDetails")){
            toolBarHeader_TextView.setText(getString(R.string.items_in_the_order));


        }
        else  if(TaskToDo.equals(getString(R.string.edit_unsold_items_in_batch))){
            toolBarHeader_TextView.setText(getString(R.string.unsold_items_list));


        }
        else  if(TaskToDo.equals(getString(R.string.view_sold_items_in_batch))){
            toolBarHeader_TextView.setText(getString(R.string.sold_items_list));


        }
        else  if(TaskToDo.equals("PlacedOrderItemDetails")){
            if(CalledFrom.equals(getString(R.string.datewise_placedOrder_Details_Screen_SecondVersion))) {

                toolBarHeader_TextView.setText(getString(R.string.items_in_the_order));
            }
            else   if(CalledFrom.equals(getString(R.string.markdeliveredOrders_detailsScreen))) {

                            toolBarHeader_TextView.setText(getString(R.string.delivered_items_in_the_list));

            }

        }
        else if(TaskToDo.equals(getString(R.string.view_unsold_items_in_batch))){
            if(CalledFrom.equals(Constants.viewStockBalance)){
                toolBarHeader_TextView.setText(String.valueOf(getString(R.string.unsold_item_details)) +" ( BatchNo: "+ batchno+" ) " );
            }
            else{
                toolBarHeader_TextView.setText(getString(R.string.unsold_item_details));
            }

        }


        if(TaskToDo.equals("NewAddedItem")){
            //statusToFilter = Constants.

        }
        else  if (TaskToDo.equals("Oldtem")) {

        }
        else  if(TaskToDo.equals("ViewSoldItem")){
            statusToFilter = Constants.goatEarTagStatus_Sold;
        }
        else  if(TaskToDo.equals("ViewUnSoldItem")){
            statusToFilter = Constants.goatEarTagStatus_Reviewed_and_READYFORSALE;


        }
        else  if(TaskToDo.equals("ViewUnReviewedItem")){
            statusToFilter = Constants.goatEarTagStatus_Loading;


        }
        else  if(TaskToDo.equals("ViewReviewedItem")){
            statusToFilter = Constants.goatEarTagStatus_Reviewed_and_READYFORSALE;


        }
        else  if(TaskToDo.equals(getString(R.string.view_sold_items_in_batch))){
            statusToFilter = Constants.goatEarTagStatus_Sold;
        }
        else if(TaskToDo.equals(getString(R.string.view_unsold_items_in_batch))){
            statusToFilter = Constants.goatEarTagStatus_Reviewed_and_READYFORSALE;
        }
        else  if(TaskToDo.equals(getString(R.string.edit_unsold_items_in_batch))){
            statusToFilter = Constants.goatEarTagStatus_Reviewed_and_READYFORSALE;


        }





        batchStatus = String.valueOf(Modal_B2BBatchDetailsStatic.getStatus().toUpperCase());

        cartclearing_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TMCAlertDialogClass(GoatEarTagItemDetailsList.this, R.string.app_name, R.string.Clear_Cart_Instruction,
                        R.string.Clear_Text, R.string.Cancel_Text,
                        new TMCAlertDialogClass.AlertListener() {
                            @Override
                            public void onYes() {

                                Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallDELETEMethod);




                            }

                            @Override
                            public void onNo() {

                            }
                        });

            }
        });






        search_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int textlength = barcode_editText.getText().toString().length();
                isSearchButtonClicked =true;
                showKeyboard(barcode_editText);
                showSearchBarEditText();
            }

        });

        barcodeNo_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_IconLayout.performClick();
            }

        });

        close_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(barcode_editText);
                closeSearchBarEditText();
                barcode_editText.setText("");
                isSearchButtonClicked =false;
                setAdapter(earTagItemsForBatch);
            }
        });

        batchItemDetailsFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        scanBarcode_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Initialize_and_StartBarcodeScanner(getString(R.string.scannerCalled_Not_to_FetchData));
            }
        });


        barcode_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sorted_earTagItemsForBatch.clear();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                sorted_earTagItemsForBatch.clear();
                isSearchButtonClicked =true;
                String barcode = (editable.toString());
                if(!barcode.equals("")) {

                    for (int i = 0; i < earTagItemsForBatch.size(); i++) {
                        try{
                            final Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagItemsForBatch.get(i);
                            String barcodeno = modal_goatEarTagDetails.getBarcodeno();

                            if (barcodeno.toUpperCase().contains(barcode.toUpperCase())) {
                                sorted_earTagItemsForBatch.add(modal_goatEarTagDetails);
                            }
                            if (i == (earTagItemsForBatch.size() - 1)) {

                                setAdapter(sorted_earTagItemsForBatch);
                            }
                            }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    setAdapter(earTagItemsForBatch);
                }
            }
        });




        if(CalledFrom.equals(getString(R.string.billing_Screen)) || CalledFrom.equals(getString(R.string.placedOrder_Details_Screen))){

            try {

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






                if (DatabaseArrayList_PojoClass.goatGradeDetailsArrayList.size() == 0) {
                    try {
                        Call_and_Initialize_GoatGradeDetails(Constants.CallGETListMethod);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    goatGrade_arrayLsit = DatabaseArrayList_PojoClass.getGoatGradeDetailsArrayList();
                    if(CalledFrom.equals(getString(R.string.placedOrder_Details_Screen))){
                        Intialize_and_ExecuteInB2BOrderItemDetails(Constants.CallGETListMethod, orderid);

                    }
                    else{
                        Intialize_and_ExecuteInB2BCartItemDetails(Constants.CallGETListMethod, "", "");

                    }

                }

            }
            catch (Exception e){
                e.printStackTrace();
            }

              //  getDataFromBillingScreenArray();


        }
        else  if( CalledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion))) {
            Intialize_and_ExecuteInB2BCartItemDetails(Constants.CallGETListMethod, "", "");

        }
        else   if(CalledFrom.equals(getString(R.string.datewise_placedOrder_Details_Screen_SecondVersion))) {
            Intialize_and_ExecuteInB2BOrderItemDetails(Constants.CallGETListMethod, orderid);
        }
        else   if(CalledFrom.equals(getString(R.string.markdeliveredOrders_detailsScreen))) {
            Intialize_and_ExecuteInB2BOrderItemDetails(Constants.CallGETListMethod, orderid);
        }

        else{
            Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETListMethod);
        }



    }




    private void closeSearchBarEditText() {
        search_IconLayout.setVisibility(View.VISIBLE);
        barcodeNo_textView.setVisibility(View.VISIBLE);
        barcode_editText.setVisibility(View.GONE);
        close_IconLayout.setVisibility(View.GONE);
    }

    private void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
    private void showSearchBarEditText() {
        barcode_editText.setVisibility(View.VISIBLE);
        barcodeNo_textView.setVisibility(View.GONE);
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

    private void Intialize_and_ExecuteInB2BOrderItemDetails(String callMethod, String orderid) {
        try {
            showProgressBar(true);
            if (isB2BOrderItemDetailsTableServiceCalled) {
                // showProgressBar(false);
                return;
            }
            if(callMethod.equals(Constants.CallGETListMethod)) {

                earTagItemsForBatch.clear();
                earTagItemsBarcodeList.clear();
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

                        modal_goatEarTagDetails.status =modal_b2BOrderItemDetails.getStatus();
                        modal_goatEarTagDetails.stockedweightingrams = modal_b2BOrderItemDetails.getWeightingrams();
                        modal_goatEarTagDetails.loadedweightingrams = modal_b2BOrderItemDetails.getWeightingrams();
                        modal_goatEarTagDetails.newWeight_forBillingScreen = modal_b2BOrderItemDetails.getWeightingrams();
                        modal_goatEarTagDetails.b2bctgykey = modal_b2BOrderItemDetails.getB2bctgykey();
                        modal_goatEarTagDetails.b2bsubctgykey = modal_b2BOrderItemDetails.getB2bsubctgykey();
                        modal_goatEarTagDetails.orderid_forBillingScreen = modal_b2BOrderItemDetails.getOrderid();
                        modal_goatEarTagDetails.gender = String.valueOf(modal_b2BOrderItemDetails.getGender());
                        modal_goatEarTagDetails.gradename = String.valueOf(modal_b2BOrderItemDetails.getGradename());
                        modal_goatEarTagDetails.partsweight = String.valueOf(modal_b2BOrderItemDetails.getPartsweight());
                        modal_goatEarTagDetails.meatyieldweight = String.valueOf(modal_b2BOrderItemDetails.getMeatyieldweight());
                        modal_goatEarTagDetails.approxliveweight = String.valueOf(modal_b2BOrderItemDetails.getApproxliveweight());
                        modal_goatEarTagDetails.itemPrice = String.valueOf(modal_b2BOrderItemDetails.getItemPrice());
                        modal_goatEarTagDetails.totalPrice_ofItem = String.valueOf(modal_b2BOrderItemDetails.getTotalPrice_ofItem());
                        modal_goatEarTagDetails.totalItemWeight = String.valueOf(modal_b2BOrderItemDetails.getTotalItemWeight());



                        /*
                        try{
                            for (int iterator1 = 0; iterator1 < ctgy_subCtgy_DetailsArrayList.size(); iterator1++) {
                            if(ctgy_subCtgy_DetailsArrayList.get(iterator1).getKey().equals(modal_b2BOrderItemDetails.getB2bctgykey())){
                                modal_goatEarTagDetails.setB2bctgyName(ctgy_subCtgy_DetailsArrayList.get(iterator1).getName());
                            }

                            if(ctgy_subCtgy_DetailsArrayList.get(iterator1).getSubctgy_key().equals(modal_b2BOrderItemDetails.getB2bsubctgykey())){
                                modal_goatEarTagDetails.setB2bSubctgyName(ctgy_subCtgy_DetailsArrayList.get(iterator1).getSubctgy_name());
                                modal_goatEarTagDetails.setBreedtype(ctgy_subCtgy_DetailsArrayList.get(iterator1).getSubctgy_name());
                            }

                            }

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }



                        try{
                            if(modal_b2BOrderItemDetails.getGradekey().equals("")) {
                                try {
                                    for (int iterator1 = 0; iterator1 < goatGrade_arrayLsit.size(); iterator1++) {
                                        if ("b6c47093-8f55-4bd6-b6c7-ab9a19258437".equals(goatGrade_arrayLsit.get(iterator1).getKey()) || "e1ed04cf-ecd1-4db3-99a2-d7104931c277".equals(goatGrade_arrayLsit.get(iterator1).getKey())) {
                                            modal_b2BOrderItemDetails.gradeprice = goatGrade_arrayLsit.get(iterator1).getPrice();
                                            modal_b2BOrderItemDetails.gradename = goatGrade_arrayLsit.get(iterator1).getName();
                                            modal_b2BOrderItemDetails.gradekey = goatGrade_arrayLsit.get(iterator1).getKey();

                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else{
                                try {
                                    for (int iterator1 = 0; iterator1 < goatGrade_arrayLsit.size(); iterator1++) {
                                        if (String.valueOf(modal_b2BOrderItemDetails.getGradekey()).equals(goatGrade_arrayLsit.get(iterator1).getKey())) {
                                            modal_goatEarTagDetails.gradeprice = goatGrade_arrayLsit.get(iterator1).getPrice();
                                            modal_goatEarTagDetails.gradename = goatGrade_arrayLsit.get(iterator1).getName();
                                            modal_goatEarTagDetails.gradekey = goatGrade_arrayLsit.get(iterator1).getKey();

                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                         */
                        earTagItemsForBatch.add(modal_goatEarTagDetails);

                        if (iterator == (arrayList.size() - 1)) {

                            setAdapter(earTagItemsForBatch);
                        }
                    }
                    isB2BOrderItemDetailsTableServiceCalled = false;
                }

                @Override
                public void notifySuccess(String result) {
                    isGoatGradeDetailsServiceCalled = false;
                }

                @Override
                public void notifyVolleyError(VolleyError error) {
                    isGoatGradeDetailsServiceCalled = false;
                    Log.i("INIT", "FetchDataFromOrderItemDetails:  " + String.valueOf(error));
                    isGoatGradeDetailsServiceCalled = false;
                }

                @Override
                public void notifyProcessingError(Exception error) {

                    Log.i("INIT", "FetchDataFromOrderItemDetails:  " + String.valueOf(error));
                    isGoatGradeDetailsServiceCalled = false;
                }
            };
            if(CalledFrom.equals(getString(R.string.datewise_placedOrder_Details_Screen_SecondVersion))) {
                if(doeshavetocallitbatchwise){
                     if (callMethod.equals(Constants.CallGETListMethod)) {
                        //String getApiToCall = API_Manager.getOrderDetailsForBatchno+"?batchno="+batchno ;
                         String getApiToCall = API_Manager.getOrderItemDetailsForOrderidWithBatchnoWithoutCancelledStatus +"?orderid=" + orderid+"&batchno="+batchno;

                        B2BOrderItemDetails asyncTask = new B2BOrderItemDetails(callback_b2bOrderItemDetails, getApiToCall, callMethod);
                        asyncTask.execute();

                    }
                }
                else {
                    if (callMethod.equals(Constants.CallGETListMethod)) {
                        //String getApiToCall = API_Manager.getOrderDetailsForBatchno+"?batchno="+batchno ;
                        String getApiToCall = API_Manager.getOrderItemDetailsForOrderid + orderid;

                        B2BOrderItemDetails asyncTask = new B2BOrderItemDetails(callback_b2bOrderItemDetails, getApiToCall, callMethod);
                        asyncTask.execute();

                    }
                }

            }
            else   if(CalledFrom.equals(getString(R.string.markdeliveredOrders_detailsScreen))) {
                if (callMethod.equals(Constants.CallGETListMethod)) {
                    //String getApiToCall = API_Manager.getOrderDetailsForBatchno+"?batchno="+batchno ;
                    String getApiToCall = API_Manager.getOrderItemDetailsForOrderidWithStatus+"?orderid=" + orderid+"&status="+Constants.orderItemDetailsStatus_Delivered;

                    B2BOrderItemDetails asyncTask = new B2BOrderItemDetails(callback_b2bOrderItemDetails, getApiToCall, callMethod);
                    asyncTask.execute();

                }
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void Initialize_and_ExecuteB2BCartOrderDetails(String callMethod) {


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


                if(callMethod.equals(Constants.CallGETMethod)){
                    if (result.equals(Constants.emptyResult_volley)) {
                        isB2BCartOrderTableServiceCalled = false;
                        Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);
                    } else {

                        orderid = Modal_B2BCartOrderDetails.getOrderid();
                        Initialize_and_ExecuteB2BCartITEMDetails(Constants.CallGETMethod);
                        isB2BCartOrderTableServiceCalled = false;
                    }
                }
                else  if(callMethod.equals(Constants.CallDELETEMethod)){

                    try{
                        DeliveryCenter_PlaceOrderScreen_SecondVersn.deliveryCenter_placeOrderScreen_secondVersn.resetAlltheValuesAndArrays();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                    if (result.equals(Constants.emptyResult_volley)) {
                        showProgressBar(false);
                        isB2BCartOrderTableServiceCalled = false;


                    } else {

                        if(earTagItemsForBatch.size()>0) {
                            Initialize_and_ExecuteB2BCartItemDetails(Constants.CallDELETEMethod);
                        }
                        else{
                            showProgressBar(false);
                        }
                        isB2BCartOrderTableServiceCalled = false;
                    }
                }


            }
            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
                isB2BCartOrderTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {

                showProgressBar(false);
                isB2BCartOrderTableServiceCalled = false;


            }




        };


         if(callMethod.equals(Constants.CallGETMethod)){
             //String getApiToCall = API_Manager.getCartOrderDetailsForBatchno+"?batchno="+batchno ;
             String getApiToCall = API_Manager.getCartOrderDetailsForDeliveryCentrekey+"?deliverycentrekey="+deliveryCenterKey ;

             B2BCartOrderDetails asyncTask = new B2BCartOrderDetails(callback_b2bOrderDetails,  getApiToCall, Constants.CallGETMethod);
            asyncTask.execute();

        }
         else  if(callMethod.equals(Constants.CallDELETEMethod)){
             try {

                 String addApiToCall = API_Manager.deleteCartOrderDetails+"?orderid="+orderid+"&deliverycentrekey="+deliveryCenterKey;
                 B2BCartOrderDetails asyncTask = new B2BCartOrderDetails(callback_b2bOrderDetails, addApiToCall, Constants.CallDELETEMethod);
                 asyncTask.execute();
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }


    }

    private void Initialize_and_ExecuteB2BCartItemDetails(String callDELETEMethod) {
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
                    earTagItemsForBatch.clear();
                    setAdapter(earTagItemsForBatch);
                    showProgressBar(false);
                }

                @Override
                public void notifySuccessForGettingListItem(ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch) {
                    isB2BCartItemDetailsBulkUpdate = false;
                    showProgressBar(false);

                }

                @Override
                public void notifyVolleyError(VolleyError error) {
                    Toast.makeText(getParent(), "There is an volley error while updating Ear Tag", Toast.LENGTH_SHORT).show();

                    showProgressBar(false);
                    isGoatEarTagDetailsTableServiceCalled = false;
                }

                @Override
                public void notifyProcessingError(Exception error) {
                    Toast.makeText(getParent(), "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                    showProgressBar(false);
                    isGoatEarTagDetailsTableServiceCalled = false;


                }

            };




            try {

                B2BCartItemDetails_BulkUpdate asyncTask = new B2BCartItemDetails_BulkUpdate(callbackB2BCartItemDetails_bulkUpdateInterface,  Constants.CallDELETEMethod, orderid  , earTagItemsForBatch);
                asyncTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void Initialize_and_ExecuteB2BCartITEMDetails(String callADDMethod) {

            showProgressBar(true);

        if (isB2BCartDetailsCalled) {
            //  showProgressBar(false);
            return;
        }
        isB2BCartDetailsCalled = true;
        callback_b2BCartItemDetaillsInterface = new B2BCartItemDetaillsInterface()
        {

            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BCartItemDetails> arrayList) {
                showProgressBar(false);



            }

            @Override
            public void notifySuccess(String result) {
                Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 2 " + DateParser.getDate_and_time_newFormat());
                isB2BCartDetailsCalled = false;

                showProgressBar(false);
                if(callADDMethod.equals(Constants.CallGETMethod)){
                    if(result.equals(Constants.emptyResult_volley)){
                       // showEditabeLayouts(false, false);
                        
                        Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);
                    }
                    else{
                        AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this, R.string.CantEditItem_Which_isInCart_Instruction);
                    }
                }
                else {


                }
                showProgressBar(false);
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isB2BCartDetailsCalled = false;
                Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 3 " + DateParser.getDate_and_time_newFormat());
                showProgressBar(false);
            }

            @Override
            public void notifyProcessingError(Exception error) {
                isB2BCartDetailsCalled = false;showProgressBar(false);
                Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());

            }


        };
      if(callADDMethod.equals(Constants.CallGETMethod)) {
            String getApiToCall = API_Manager.getCartDetailsForOrderidWithBarcodeNo+"?orderid="+orderid+"&barcodeno="+scannedBarcode;

            B2BCartItemDetails asyncTask = new B2BCartItemDetails(callback_b2BCartItemDetaillsInterface,  getApiToCall, callADDMethod);
            asyncTask.execute();

        }



    }

    private void Initialize_and_ExecuteB2BCtgyItem() {

        showProgressBar(true);
        if (isB2BItemCtgyTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        ctgy_subCtgy_DetailsArrayList.clear();
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
                if(CalledFrom.equals(getString(R.string.placedOrder_Details_Screen))){
                    Intialize_and_ExecuteInB2BOrderItemDetails(Constants.CallGETListMethod, orderid);

                }
                else{
                    Intialize_and_ExecuteInB2BCartItemDetails(Constants.CallGETListMethod, "", "");

                }

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

    public  void Intialize_and_ExecuteInB2BCartItemDetails(String callMethod, String barcodeno, String orderid_forBillingScreen) {

        if(callMethod.equals(Constants.CallGETListMethod)) {

            earTagItemsForBatch.clear();
            earTagItemsBarcodeList.clear();
        }

            showProgressBar(true);

        if (isB2BCartDetailsCalled) {
            //  showProgressBar(false);
            return;
        }
        isB2BCartDetailsCalled = true;
        callback_b2BCartItemDetaillsInterface = new B2BCartItemDetaillsInterface()
        {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BCartItemDetails> arrayList) {
                try {
                    if (!callMethod.equals(Constants.CallDELETEMethod)) {
                        isB2BCartDetailsCalled = false;
                        for (int iterator = 0; iterator < arrayList.size(); iterator++) {
                            Modal_B2BCartItemDetails modal_b2BCartDetails = arrayList.get(iterator);
                            Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();
                            modal_goatEarTagDetails.barcodeno = modal_b2BCartDetails.getBarcodeno();
                            modal_goatEarTagDetails.batchno = modal_b2BCartDetails.getBatchno();
                            modal_goatEarTagDetails.status = modal_b2BCartDetails.getStatus();
                            modal_goatEarTagDetails.itemaddeddate = modal_b2BCartDetails.getItemaddeddate();
                            modal_goatEarTagDetails.currentweightingrams = modal_b2BCartDetails.getWeightingrams();
                            modal_goatEarTagDetails.gender = modal_b2BCartDetails.getGender();
                            modal_goatEarTagDetails.breedtype = modal_b2BCartDetails.getBreedtype();
                            modal_goatEarTagDetails.stockedweightingrams = modal_b2BCartDetails.getOldweightingrams();
                            modal_goatEarTagDetails.loadedweightingrams = modal_b2BCartDetails.getOldweightingrams();
                            modal_goatEarTagDetails.orderid_forBillingScreen = modal_b2BCartDetails.getOrderid();
                            modal_goatEarTagDetails.newWeight_forBillingScreen = modal_b2BCartDetails.getWeightingrams();
                            modal_goatEarTagDetails.b2bctgykey = modal_b2BCartDetails.getB2bctgykey();
                            modal_goatEarTagDetails.b2bsubctgykey = modal_b2BCartDetails.getB2bsubctgykey();
                            modal_goatEarTagDetails.supplierkey = modal_b2BCartDetails.getSupplierkey();
                            modal_goatEarTagDetails.suppliername = modal_b2BCartDetails.getSuppliername();


                            modal_goatEarTagDetails.approxliveweight = modal_b2BCartDetails.getApproxliveweight();
                            modal_goatEarTagDetails.meatyieldweight = modal_b2BCartDetails.getMeatyieldweight();
                            modal_goatEarTagDetails.partsweight = modal_b2BCartDetails.getPartsweight();
                            modal_goatEarTagDetails.itemPrice = modal_b2BCartDetails.getItemprice();
                            modal_goatEarTagDetails.totalItemWeight = modal_b2BCartDetails.getTotalItemWeight();
                            modal_goatEarTagDetails.gradename = modal_b2BCartDetails.getGradename();

                            modal_goatEarTagDetails.totalPrice_ofItem = modal_b2BCartDetails.getTotalPrice_ofItem();
                            modal_goatEarTagDetails.discount = modal_b2BCartDetails.getDiscount();



                          /*  try {
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

                           */

                            earTagItemsForBatch.add(modal_goatEarTagDetails);
                            if (iterator == (arrayList.size() - 1)) {
                                setAdapter(earTagItemsForBatch);
                            }
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void notifySuccess(String result) {
                Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 2 " + DateParser.getDate_and_time_newFormat());

                if(callMethod.equals(Constants.CallUPDATEMethod)) {

                    try{
                        for(int i = 0 ; i < earTagItemsForBatch.size() ; i++){
                            if(earTagItemsForBatch.get(i).getBarcodeno().equals(barcodeno)){
                                Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagItemsForBatch.get(i);
                                try {
                                    modal_goatEarTagDetails.approxliveweight = Modal_Static_GoatEarTagDetails.getApproxliveweight();
                                    modal_goatEarTagDetails.meatyieldweight = Modal_Static_GoatEarTagDetails.getMeatyieldweight();
                                    modal_goatEarTagDetails.partsweight = Modal_Static_GoatEarTagDetails.getPartsweight();
                                    modal_goatEarTagDetails.itemPrice = Modal_Static_GoatEarTagDetails.getItemPrice();
                                    modal_goatEarTagDetails.totalItemWeight = Modal_Static_GoatEarTagDetails.getItemWeight();

                                    modal_goatEarTagDetails.totalPrice_ofItem = Modal_Static_GoatEarTagDetails.getTotalPrice_ofItem();
                                    modal_goatEarTagDetails.discount = Modal_Static_GoatEarTagDetails.getDiscount();

                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }

                                try {
                                adapter_earTagItemDetails_list.notifyDataSetChanged();
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

                    try{

                       if( DeliveryCenter_PlaceOrderScreen_SecondVersn .earTagDetails_JSONFinalSalesHashMap.containsKey(barcodeno)){
                            Modal_B2BCartItemDetails modal_b2BCartItemDetails = DeliveryCenter_PlaceOrderScreen_SecondVersn.earTagDetails_JSONFinalSalesHashMap.get(barcodeno);


                          Objects.requireNonNull(modal_b2BCartItemDetails).approxliveweight = Modal_Static_GoatEarTagDetails.getApproxliveweight();
                          modal_b2BCartItemDetails.meatyieldweight = Modal_Static_GoatEarTagDetails.getMeatyieldweight();
                          modal_b2BCartItemDetails.partsweight = Modal_Static_GoatEarTagDetails.getPartsweight();
                          modal_b2BCartItemDetails.itemprice = Modal_Static_GoatEarTagDetails.getItemPrice();
                          modal_b2BCartItemDetails.totalItemWeight = Modal_Static_GoatEarTagDetails.getItemWeight();

                          modal_b2BCartItemDetails.totalPrice_ofItem = Modal_Static_GoatEarTagDetails.getTotalPrice_ofItem();
                          modal_b2BCartItemDetails.discount = Modal_Static_GoatEarTagDetails.getDiscount();
                           DeliveryCenter_PlaceOrderScreen_SecondVersn.CalculateAndSetTotal_Quantity_Price_Values();

                       }


                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
 


                }
                else if(callMethod.equals(Constants.CallDELETEMethod)){
                    try{
                        for(int i = 0 ; i < earTagItemsForBatch.size() ; i++){
                            if(earTagItemsForBatch.get(i).getBarcodeno().equals(barcodeno)){



                                try {

                                    earTagItemsForBatch.remove(i);

                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }



                                try {
                                    adapter_earTagItemDetails_list.notifyDataSetChanged();
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        }
                        try {
                            if(earTagItemsForBatch.size()>0) {
                                adapter_earTagItemDetails_list.notifyDataSetChanged();
                            }
                            else{
                                setAdapter(earTagItemsForBatch);
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        adapter_earTagItemDetails_list.notifyDataSetChanged();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }

                    showProgressBar(false);
                isB2BCartDetailsCalled = false;
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
                isB2BCartDetailsCalled = false;
                Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 3 " + DateParser.getDate_and_time_newFormat());

            }

            @Override
            public void notifyProcessingError(Exception error) {
                isB2BCartDetailsCalled = false;
                showProgressBar(false);
                Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());

            }


        };
        if(callMethod.equals(Constants.CallDELETEMethod)){
            String getApiToCall = API_Manager.deleteCartItemDetails+"?barcodeno="+barcodeno+"&orderid=" + orderid;
            B2BCartItemDetails asyncTask = new B2BCartItemDetails(callback_b2BCartItemDetaillsInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){
            Modal_B2BCartItemDetails modal_b2BCartDetails = new Modal_B2BCartItemDetails();
            try {
                modal_b2BCartDetails.barcodeno = Modal_Static_GoatEarTagDetails.getBarcodeno();
                modal_b2BCartDetails.orderid = DeliveryCenter_PlaceOrderScreen_SecondVersn.orderid;

                modal_b2BCartDetails.approxliveweight = Modal_Static_GoatEarTagDetails.getApproxliveweight();
                modal_b2BCartDetails.meatyieldweight = Modal_Static_GoatEarTagDetails.getMeatyieldweight();
                modal_b2BCartDetails.partsweight = Modal_Static_GoatEarTagDetails.getPartsweight();
                modal_b2BCartDetails.itemprice = Modal_Static_GoatEarTagDetails.getItemPrice();
                modal_b2BCartDetails.totalItemWeight = Modal_Static_GoatEarTagDetails.getItemWeight();

                modal_b2BCartDetails.totalPrice_ofItem = Modal_Static_GoatEarTagDetails.getTotalPrice_ofItem();
                modal_b2BCartDetails.discount = Modal_Static_GoatEarTagDetails.getDiscount();

            }
            catch (Exception e){
                e.printStackTrace();
            }
            String getApiToCall = API_Manager.updateCartItemDetails;
            B2BCartItemDetails asyncTask = new B2BCartItemDetails(callback_b2BCartItemDetaillsInterface,  getApiToCall, callMethod,modal_b2BCartDetails);
            asyncTask.execute();
        }
        else if(callMethod.equals(Constants.CallGETMethod)){

        }
        else {

            String getApiToCall = API_Manager.getCartItemDetailsForOrderid + orderid;
            B2BCartItemDetails asyncTask = new B2BCartItemDetails(callback_b2BCartItemDetaillsInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }

    }

    private void getDataFromBillingScreenArray() {
        earTagItemsForBatch.clear();
        try{
            for(int iterator = 0; iterator < DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetailsArrayList_String.size(); iterator++){
                String barcode = DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetailsArrayList_String.get(iterator);
               if(!barcode.equals("")){
                  /* Modal_GoatEarTagDetails modal_goatEarTagDetails = BillingScreen.earTagDetailsHashMap.get(barcode);
                   modal_goatEarTagDetails.setCurrentweightingrams(modal_goatEarTagDetails.getNewWeight_forBillingScreen());



                   earTagItemsForBatch.add(modal_goatEarTagDetails);

                   */
               }




            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        setAdapter(earTagItemsForBatch);



    }

    private void Initialize_and_StartBarcodeScanner(String processtoDOAfterScan) {
        barcodeScannerInterface = new BarcodeScannerInterface() {
            @Override
            public void notifySuccess(String Barcode) {
                scannedBarcode = Barcode;

                filterDataBasedOnBarcode(scannedBarcode);
                //barcodeNo_textView.setText(Barcode);
                // Toast.makeText(mContext, "Only Scan", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void notifySuccessAndFetchData(String Barcode) {
                scannedBarcode = Barcode;
                //Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod);
                // Toast.makeText(mContext, "Scan And Fetch", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void notifyProcessingError(Exception error) {
                // Toast.makeText(mContext, "Error in scanning ", Toast.LENGTH_SHORT).show();


            }
        };


        Intent intent = new Intent(GoatEarTagItemDetailsList.this, BarcodeScannerScreen.class);
        intent.putExtra(getString(R.string.scanner_called_to_do), processtoDOAfterScan);
        intent.putExtra(getString(R.string.called_from), getString(R.string.supplier_goatItemList));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);



    }

    private void filterDataBasedOnBarcode(String scannedBarcode) {
        if(earTagItemsForBatch.size()>0) {
            for (int iterator = 0; iterator < earTagItemsForBatch.size(); iterator++) {
                Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagItemsForBatch.get(iterator);
                if (modal_goatEarTagDetails.getBarcodeno().toUpperCase().equals(scannedBarcode)) {

                    if (CalledFrom.equals(Constants.userType_DeliveryCenter)  ){

                     if(TaskToDo.equals("ViewUnSoldItem")){
                         if (modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE) || modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatsick))
                         {
                                 Modal_Static_GoatEarTagDetails.barcodeno = modal_goatEarTagDetails.getBarcodeno();
                                 Modal_Static_GoatEarTagDetails.batchno = modal_goatEarTagDetails.getBatchno();
                                 Modal_Static_GoatEarTagDetails.stockedweightingrams = modal_goatEarTagDetails.getStockedweightingrams();
                                 Modal_Static_GoatEarTagDetails.description = modal_goatEarTagDetails.getDescription();
                                 Modal_Static_GoatEarTagDetails.suppliername = modal_goatEarTagDetails.getSuppliername();
                                 Modal_Static_GoatEarTagDetails.supplierkey = modal_goatEarTagDetails.getSupplierkey();
                                 Modal_Static_GoatEarTagDetails.breedtype = modal_goatEarTagDetails.getBreedtype();
                                 Modal_Static_GoatEarTagDetails.gender = modal_goatEarTagDetails.getGender();
                                 Modal_Static_GoatEarTagDetails.status = modal_goatEarTagDetails.getStatus();
                                 Modal_Static_GoatEarTagDetails.currentweightingrams = modal_goatEarTagDetails.getCurrentweightingrams();
                                 Modal_Static_GoatEarTagDetails.loadedweightingrams = modal_goatEarTagDetails.getLoadedweightingrams();

                             try {
                                 for (int iterator1 = 0; iterator1 < goatGrade_arrayLsit.size(); iterator1++) {
                                     if (String.valueOf(modal_goatEarTagDetails.getGradekey()).equals(goatGrade_arrayLsit.get(iterator1).getKey())) {
                                         Modal_Static_GoatEarTagDetails.gradeprice = goatGrade_arrayLsit.get(iterator1).getPrice();
                                         Modal_Static_GoatEarTagDetails.gradename  = goatGrade_arrayLsit.get(iterator1).getName();
                                         Modal_Static_GoatEarTagDetails.gradekey   = goatGrade_arrayLsit.get(iterator1).getKey();

                                     }
                                 }
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }




                             try {
                                             mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                                             value_forFragment = getString(R.string.deliverycenter_UnsoldgoatItemList);
                                             loadMyFragment();


                                             showProgressBar(false);
                                             return;
                                         } catch (WindowManager.BadTokenException e) {
                                             showProgressBar(false);

                                             e.printStackTrace();
                                         }

                                 }

                             else{
                                 if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_EarTagLost)){
                                     Toast.makeText(this, getString(R.string.EarTagLost_Instruction), Toast.LENGTH_LONG).show();
                                     // AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.EarTagLost_Instruction);
                                     return;
                                 }
                                 else if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_GoatLost)){
                                     Toast.makeText(this, getString(R.string.GoatLost_Instruction), Toast.LENGTH_LONG).show();
                                     return;
                                     // AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.GoatLost_Instruction);

                                 }
                                 else if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatdead)){
                                     Toast.makeText(this, getString(R.string.GoatDead_Instruction), Toast.LENGTH_LONG).show();
                                     return;
                                     // AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.GoatLost_Instruction);

                                 }
                                 else{
                                     Toast.makeText(this, getString(R.string.EarTagDetailsNotFound_Instruction), Toast.LENGTH_LONG).show();
                                     //   AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.EarTagDetailsNotFound_Instruction);
                                     return;
                                 }


                             }

                        }

                        else  if(TaskToDo.equals("ViewReviewedItem")){
                         if (modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE) || modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatsick))
                         {
                             Modal_Static_GoatEarTagDetails.barcodeno = modal_goatEarTagDetails.getBarcodeno();
                             Modal_Static_GoatEarTagDetails.batchno = modal_goatEarTagDetails.getBatchno();
                             Modal_Static_GoatEarTagDetails.stockedweightingrams = modal_goatEarTagDetails.getStockedweightingrams();
                             Modal_Static_GoatEarTagDetails.description = modal_goatEarTagDetails.getDescription();
                             Modal_Static_GoatEarTagDetails.suppliername = modal_goatEarTagDetails.getSuppliername();
                             Modal_Static_GoatEarTagDetails.supplierkey = modal_goatEarTagDetails.getSupplierkey();
                             Modal_Static_GoatEarTagDetails.breedtype = modal_goatEarTagDetails.getBreedtype();
                             Modal_Static_GoatEarTagDetails.gender = modal_goatEarTagDetails.getGender();
                             Modal_Static_GoatEarTagDetails.status = modal_goatEarTagDetails.getStatus();
                             Modal_Static_GoatEarTagDetails.currentweightingrams = modal_goatEarTagDetails.getCurrentweightingrams();
                             Modal_Static_GoatEarTagDetails.loadedweightingrams = modal_goatEarTagDetails.getLoadedweightingrams();
                             try {
                                 for (int iterator1 = 0; iterator1 < goatGrade_arrayLsit.size(); iterator1++) {
                                     if (String.valueOf(modal_goatEarTagDetails.getGradekey()).equals(goatGrade_arrayLsit.get(iterator1).getKey())) {
                                         Modal_Static_GoatEarTagDetails.gradeprice = goatGrade_arrayLsit.get(iterator1).getPrice();
                                         Modal_Static_GoatEarTagDetails.gradename  = goatGrade_arrayLsit.get(iterator1).getName();
                                         Modal_Static_GoatEarTagDetails.gradekey   = goatGrade_arrayLsit.get(iterator1).getKey();

                                     }
                                 }
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }
                             try {
                                 mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                                 value_forFragment = getString(R.string.deliverycenter_ReviewedgoatItemList);
                                 loadMyFragment();


                                 showProgressBar(false);
                                 return;
                             } catch (WindowManager.BadTokenException e) {
                                 showProgressBar(false);

                                 e.printStackTrace();
                             }


                         }
                         else{
                             if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_EarTagLost)){
                                 Toast.makeText(this, getString(R.string.EarTagLost_Instruction), Toast.LENGTH_LONG).show();
                                 // AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.EarTagLost_Instruction);
                                 return;
                             }
                             else if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_GoatLost)){
                                 Toast.makeText(this, getString(R.string.GoatLost_Instruction), Toast.LENGTH_LONG).show();
                                 return;
                                 // AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.GoatLost_Instruction);

                             }
                             else if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatdead)){
                                 Toast.makeText(this, getString(R.string.GoatDead_Instruction), Toast.LENGTH_LONG).show();
                                 return;
                                 // AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.GoatLost_Instruction);

                             }
                             else{
                                 Toast.makeText(this, getString(R.string.EarTagDetailsNotFound_Instruction), Toast.LENGTH_LONG).show();
                                 //   AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.EarTagDetailsNotFound_Instruction);
                                 return;
                             }


                         }


                        }

                     else  if(TaskToDo.equals(getString(R.string.edit_unsold_items_in_batch))){
                         if (modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE) || modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatsick))
                         {
                             Modal_Static_GoatEarTagDetails.approxliveweight = modal_goatEarTagDetails.getApproxliveweight();
                             Modal_Static_GoatEarTagDetails.meatyieldweight = modal_goatEarTagDetails.getMeatyieldweight();
                             Modal_Static_GoatEarTagDetails.partsweight = modal_goatEarTagDetails.getPartsweight();
                             Modal_Static_GoatEarTagDetails.itemPrice = modal_goatEarTagDetails.getItemPrice();
                             Modal_Static_GoatEarTagDetails.itemWeight = modal_goatEarTagDetails.getTotalItemWeight();

                             Modal_Static_GoatEarTagDetails.totalPrice_ofItem = modal_goatEarTagDetails.getTotalPrice_ofItem();
                             Modal_Static_GoatEarTagDetails.discount = modal_goatEarTagDetails.getDiscount();
                             Modal_Static_GoatEarTagDetails.barcodeno = modal_goatEarTagDetails.getBarcodeno();
                             Modal_Static_GoatEarTagDetails.batchno = modal_goatEarTagDetails.getBatchno();
                             Modal_Static_GoatEarTagDetails.stockedweightingrams = modal_goatEarTagDetails.getStockedweightingrams();
                             Modal_Static_GoatEarTagDetails.description = modal_goatEarTagDetails.getDescription();
                             Modal_Static_GoatEarTagDetails.suppliername = modal_goatEarTagDetails.getSuppliername();
                             Modal_Static_GoatEarTagDetails.supplierkey = modal_goatEarTagDetails.getSupplierkey();
                             Modal_Static_GoatEarTagDetails.breedtype = modal_goatEarTagDetails.getBreedtype();
                             Modal_Static_GoatEarTagDetails.gender = modal_goatEarTagDetails.getGender();
                             Modal_Static_GoatEarTagDetails.status = modal_goatEarTagDetails.getStatus();
                             Modal_Static_GoatEarTagDetails.currentweightingrams = modal_goatEarTagDetails.getCurrentweightingrams();
                             Modal_Static_GoatEarTagDetails.loadedweightingrams = modal_goatEarTagDetails.getLoadedweightingrams();
                             Modal_Static_GoatEarTagDetails.gradename = modal_goatEarTagDetails.getGradename();


                             ShowGoatItemDetailsDialog();


                         }

                         else{
                             if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_EarTagLost)){
                                 Toast.makeText(this, getString(R.string.EarTagLost_Instruction), Toast.LENGTH_LONG).show();
                                 // AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.EarTagLost_Instruction);
                                 return;
                             }
                             else if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_GoatLost)){
                                 Toast.makeText(this, getString(R.string.GoatLost_Instruction), Toast.LENGTH_LONG).show();
                                 return;
                                 // AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.GoatLost_Instruction);

                             }
                             else if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatdead)){
                                 Toast.makeText(this, getString(R.string.GoatDead_Instruction), Toast.LENGTH_LONG).show();
                                 return;
                                 // AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.GoatLost_Instruction);

                             }
                             else{
                                 Toast.makeText(this, getString(R.string.EarTagDetailsNotFound_Instruction), Toast.LENGTH_LONG).show();
                                 //   AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.EarTagDetailsNotFound_Instruction);
                                 return;
                             }


                         }

                     }

                        else {


                         if (modal_goatEarTagDetails.getStatus().toUpperCase().equals(statusToFilter)) {
                             Modal_Static_GoatEarTagDetails.barcodeno = modal_goatEarTagDetails.getBarcodeno();
                             Modal_Static_GoatEarTagDetails.batchno = modal_goatEarTagDetails.getBatchno();
                             Modal_Static_GoatEarTagDetails.stockedweightingrams = modal_goatEarTagDetails.getStockedweightingrams();
                             Modal_Static_GoatEarTagDetails.description = modal_goatEarTagDetails.getDescription();
                             Modal_Static_GoatEarTagDetails.suppliername = modal_goatEarTagDetails.getSuppliername();
                             Modal_Static_GoatEarTagDetails.supplierkey = modal_goatEarTagDetails.getSupplierkey();
                             Modal_Static_GoatEarTagDetails.breedtype = modal_goatEarTagDetails.getBreedtype();
                             Modal_Static_GoatEarTagDetails.gender = modal_goatEarTagDetails.getGender();
                             Modal_Static_GoatEarTagDetails.status = modal_goatEarTagDetails.getStatus();
                             Modal_Static_GoatEarTagDetails.currentweightingrams = modal_goatEarTagDetails.getCurrentweightingrams();
                             Modal_Static_GoatEarTagDetails.loadedweightingrams = modal_goatEarTagDetails.getLoadedweightingrams();
                             try {
                                 for (int iterator1 = 0; iterator1 < goatGrade_arrayLsit.size(); iterator1++) {
                                     if (String.valueOf(modal_goatEarTagDetails.getGradekey()).equals(goatGrade_arrayLsit.get(iterator1).getKey())) {
                                         Modal_Static_GoatEarTagDetails.gradeprice = goatGrade_arrayLsit.get(iterator1).getPrice();
                                         Modal_Static_GoatEarTagDetails.gradename  = goatGrade_arrayLsit.get(iterator1).getName();
                                         Modal_Static_GoatEarTagDetails.gradekey   = goatGrade_arrayLsit.get(iterator1).getKey();

                                     }
                                 }
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }

                             if (CalledFrom.equals(Constants.userType_DeliveryCenter)) {
                                 if (TaskToDo.equals("ViewUnReviewedItem")) {
                                     Intent intent = new Intent(this, Audit_UnstockedBatch_item.class);
                                     intent.putExtra("batchno", modal_goatEarTagDetails.getBatchno());
                                     intent.putExtra("barcodeno", modal_goatEarTagDetails.getBarcodeno());
                                     intent.putExtra("supplierkey", modal_goatEarTagDetails.getSupplierkey());
                                     intent.putExtra("itemsposition", String.valueOf(iterator));
                                     startActivity(intent);
                                     return;


                                 }
                                 else if (TaskToDo.equals("ViewSoldItem")) {
                                     try {
                                         mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                                         value_forFragment = getString(R.string.deliverycenter_SoldgoatItemList);
                                         loadMyFragment();


                                         showProgressBar(false);
                                         return;
                                     } catch (WindowManager.BadTokenException e) {
                                         showProgressBar(false);

                                         e.printStackTrace();
                                     }
                                 } else {
                                     showProgressBar(false);
                                     return;
                                 }
                             }


                         }
                         else {
                             if (modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_EarTagLost)) {
                                 Toast.makeText(this, getString(R.string.EarTagLost_Instruction), Toast.LENGTH_LONG).show();
                                 // AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.EarTagLost_Instruction);
                                 return;
                             } else if (modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_GoatLost)) {
                                 Toast.makeText(this, getString(R.string.GoatLost_Instruction), Toast.LENGTH_LONG).show();
                                 return;
                                 // AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.GoatLost_Instruction);

                             } else if (modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatdead)) {
                                 Toast.makeText(this, getString(R.string.GoatDead_Instruction), Toast.LENGTH_LONG).show();
                                 return;
                                 // AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.GoatLost_Instruction);

                             } else {
                                 Toast.makeText(this, getString(R.string.EarTagDetailsNotFound_Instruction), Toast.LENGTH_LONG).show();
                                 //   AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.EarTagDetailsNotFound_Instruction);
                                 return;
                             }


                         }
                     }

                    }
                    else if (CalledFrom.equals(Constants.userType_SupplierCenter)){
                        Modal_Static_GoatEarTagDetails.barcodeno = modal_goatEarTagDetails.getBarcodeno();
                        Modal_Static_GoatEarTagDetails.batchno = modal_goatEarTagDetails.getBatchno();
                        Modal_Static_GoatEarTagDetails.stockedweightingrams = modal_goatEarTagDetails.getStockedweightingrams();
                        Modal_Static_GoatEarTagDetails.description = modal_goatEarTagDetails.getDescription();
                        Modal_Static_GoatEarTagDetails.suppliername = modal_goatEarTagDetails.getSuppliername();
                        Modal_Static_GoatEarTagDetails.supplierkey = modal_goatEarTagDetails.getSupplierkey();
                        Modal_Static_GoatEarTagDetails.breedtype = modal_goatEarTagDetails.getBreedtype();
                        Modal_Static_GoatEarTagDetails.gender = modal_goatEarTagDetails.getGender();
                        Modal_Static_GoatEarTagDetails.status = modal_goatEarTagDetails.getStatus();
                        Modal_Static_GoatEarTagDetails.currentweightingrams = modal_goatEarTagDetails.getCurrentweightingrams();
                        Modal_Static_GoatEarTagDetails.loadedweightingrams = modal_goatEarTagDetails.getLoadedweightingrams();


                        if (CalledFrom.equals(Constants.userType_SupplierCenter)) {
                            if (TaskToDo.equals("NewAddedItem")) {
                                try {
                                    mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                                    value_forFragment=  getString(R.string.supplier_goatItemList);
                                    loadMyFragment();


                                    showProgressBar(false);
                                    return;
                                } catch (WindowManager.BadTokenException e) {
                                    showProgressBar(false);

                                    e.printStackTrace();
                                }
                            }
                            else  if (TaskToDo.equals("Oldtem")) {
                                try {
                                    mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                                    value_forFragment=  getString(R.string.supplier_goatItemList);
                                    loadMyFragment();


                                    showProgressBar(false);
                                    return;
                                } catch (WindowManager.BadTokenException e) {
                                    showProgressBar(false);

                                    e.printStackTrace();
                                }
                            }
                        }
                        else if (CalledFrom.equals(Constants.userType_DeliveryCenter)) {
                            if (TaskToDo.equals("ViewUnReviewedItem")) {
                                Intent intent = new Intent(this, Audit_UnstockedBatch_item.class);
                                intent.putExtra("batchno", modal_goatEarTagDetails.getBatchno());
                                intent.putExtra("barcodeno", modal_goatEarTagDetails.getBarcodeno());
                                intent.putExtra("supplierkey", modal_goatEarTagDetails.getSupplierkey());
                                intent.putExtra("itemsposition", String.valueOf(iterator));
                                startActivity(intent);
                                return;


                            } else if (TaskToDo.equals("ViewReviewedItem")) {
                                try {
                                    mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                                    value_forFragment=   getString(R.string.deliverycenter_ReviewedgoatItemList);
                                    loadMyFragment();


                                    showProgressBar(false);
                                    return;
                                } catch (WindowManager.BadTokenException e) {
                                    showProgressBar(false);

                                    e.printStackTrace();
                                }
                            } else if (TaskToDo.equals("ViewUnSoldItem")) {
                                try {
                                    mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                                    value_forFragment=   getString(R.string.deliverycenter_UnsoldgoatItemList);
                                    loadMyFragment();


                                    showProgressBar(false);
                                    return;
                                } catch (WindowManager.BadTokenException e) {
                                    showProgressBar(false);

                                    e.printStackTrace();
                                }
                            } else if (TaskToDo.equals("ViewSoldItem")) {
                                try {
                                    mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                                    value_forFragment=   getString(R.string.deliverycenter_SoldgoatItemList);
                                    loadMyFragment();


                                    showProgressBar(false);
                                    return;
                                } catch (WindowManager.BadTokenException e) {
                                    showProgressBar(false);

                                    e.printStackTrace();
                                }
                            } else {
                                showProgressBar(false);
                                return;
                            }
                        }


                    }
                    else if (CalledFrom.equals(getString(R.string.billing_Screen))){
                        {
                            Modal_Static_GoatEarTagDetails.barcodeno = modal_goatEarTagDetails.getBarcodeno();
                            Modal_Static_GoatEarTagDetails.batchno = modal_goatEarTagDetails.getBatchno();
                            Modal_Static_GoatEarTagDetails.stockedweightingrams = modal_goatEarTagDetails.getStockedweightingrams();
                            Modal_Static_GoatEarTagDetails.description = modal_goatEarTagDetails.getDescription();
                            Modal_Static_GoatEarTagDetails.suppliername = modal_goatEarTagDetails.getSuppliername();
                            Modal_Static_GoatEarTagDetails.supplierkey = modal_goatEarTagDetails.getSupplierkey();
                            Modal_Static_GoatEarTagDetails.breedtype = modal_goatEarTagDetails.getBreedtype();
                            Modal_Static_GoatEarTagDetails.gender = modal_goatEarTagDetails.getGender();
                            Modal_Static_GoatEarTagDetails.status = modal_goatEarTagDetails.getStatus();
                            Modal_Static_GoatEarTagDetails.currentweightingrams = modal_goatEarTagDetails.getCurrentweightingrams();
                            Modal_Static_GoatEarTagDetails.loadedweightingrams = modal_goatEarTagDetails.getLoadedweightingrams();
                            try {
                                for (int iterator1 = 0; iterator1 < goatGrade_arrayLsit.size(); iterator1++) {
                                    if (String.valueOf(modal_goatEarTagDetails.getGradekey()).equals(goatGrade_arrayLsit.get(iterator1).getKey())) {
                                        Modal_Static_GoatEarTagDetails.gradeprice = goatGrade_arrayLsit.get(iterator1).getPrice();
                                        Modal_Static_GoatEarTagDetails.gradename  = goatGrade_arrayLsit.get(iterator1).getName();
                                        Modal_Static_GoatEarTagDetails.gradekey   = goatGrade_arrayLsit.get(iterator1).getKey();

                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                                value_forFragment=   getString(R.string.billing_Screen_editOrder);
                                loadMyFragment();


                                showProgressBar(false);
                                return;
                            } catch (WindowManager.BadTokenException e) {
                                showProgressBar(false);

                                e.printStackTrace();
                            }

                        }
                    }
                    else if (CalledFrom.equals(getString(R.string.placedOrder_Details_Screen))) {
                            Modal_Static_GoatEarTagDetails.barcodeno = modal_goatEarTagDetails.getBarcodeno();
                            Modal_Static_GoatEarTagDetails.batchno = modal_goatEarTagDetails.getBatchno();
                            Modal_Static_GoatEarTagDetails.stockedweightingrams = modal_goatEarTagDetails.getStockedweightingrams();
                            Modal_Static_GoatEarTagDetails.description = modal_goatEarTagDetails.getDescription();
                            Modal_Static_GoatEarTagDetails.suppliername = modal_goatEarTagDetails.getSuppliername();
                            Modal_Static_GoatEarTagDetails.supplierkey = modal_goatEarTagDetails.getSupplierkey();
                            Modal_Static_GoatEarTagDetails.breedtype = modal_goatEarTagDetails.getBreedtype();
                            Modal_Static_GoatEarTagDetails.gender = modal_goatEarTagDetails.getGender();
                            Modal_Static_GoatEarTagDetails.status = modal_goatEarTagDetails.getStatus();
                            Modal_Static_GoatEarTagDetails.currentweightingrams = modal_goatEarTagDetails.getCurrentweightingrams();
                            Modal_Static_GoatEarTagDetails.loadedweightingrams = modal_goatEarTagDetails.getLoadedweightingrams();
                            try {
                                for (int iterator1 = 0; iterator1 < goatGrade_arrayLsit.size(); iterator1++) {
                                    if (String.valueOf(modal_goatEarTagDetails.getGradekey()).equals(goatGrade_arrayLsit.get(iterator1).getKey())) {
                                        Modal_Static_GoatEarTagDetails.gradeprice = goatGrade_arrayLsit.get(iterator1).getPrice();
                                        Modal_Static_GoatEarTagDetails.gradename  = goatGrade_arrayLsit.get(iterator1).getName();
                                        Modal_Static_GoatEarTagDetails.gradekey   = goatGrade_arrayLsit.get(iterator1).getKey();

                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                                value_forFragment=   getString(R.string.billing_Screen_editOrder);
                                loadMyFragment();


                                showProgressBar(false);
                                return;
                            } catch (WindowManager.BadTokenException e) {
                                showProgressBar(false);

                                e.printStackTrace();
                            }

                        }
                    else   if(CalledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion))) {
                        Modal_Static_GoatEarTagDetails.approxliveweight = modal_goatEarTagDetails.getApproxliveweight();
                        Modal_Static_GoatEarTagDetails.meatyieldweight = modal_goatEarTagDetails.getMeatyieldweight();
                        Modal_Static_GoatEarTagDetails.partsweight = modal_goatEarTagDetails.getPartsweight();
                        Modal_Static_GoatEarTagDetails.itemPrice = modal_goatEarTagDetails.getItemPrice();
                        Modal_Static_GoatEarTagDetails.itemWeight = modal_goatEarTagDetails.getTotalItemWeight();

                        Modal_Static_GoatEarTagDetails.totalPrice_ofItem = modal_goatEarTagDetails.getTotalPrice_ofItem();
                        Modal_Static_GoatEarTagDetails.discount = modal_goatEarTagDetails.getDiscount();
                        Modal_Static_GoatEarTagDetails.barcodeno = modal_goatEarTagDetails.getBarcodeno();
                        Modal_Static_GoatEarTagDetails.batchno = modal_goatEarTagDetails.getBatchno();
                        Modal_Static_GoatEarTagDetails.stockedweightingrams = modal_goatEarTagDetails.getStockedweightingrams();
                        Modal_Static_GoatEarTagDetails.description = modal_goatEarTagDetails.getDescription();
                        Modal_Static_GoatEarTagDetails.suppliername = modal_goatEarTagDetails.getSuppliername();
                        Modal_Static_GoatEarTagDetails.supplierkey = modal_goatEarTagDetails.getSupplierkey();
                        Modal_Static_GoatEarTagDetails.breedtype = modal_goatEarTagDetails.getBreedtype();
                        Modal_Static_GoatEarTagDetails.gender = modal_goatEarTagDetails.getGender();
                        Modal_Static_GoatEarTagDetails.status = modal_goatEarTagDetails.getStatus();
                        Modal_Static_GoatEarTagDetails.currentweightingrams = modal_goatEarTagDetails.getCurrentweightingrams();
                        Modal_Static_GoatEarTagDetails.loadedweightingrams = modal_goatEarTagDetails.getLoadedweightingrams();
                        Modal_Static_GoatEarTagDetails.gradename = modal_goatEarTagDetails.getGradename();


                        ShowGoatItemDetailsDialog();



                    }
                    else   if(CalledFrom.equals(getString(R.string.datewise_placedOrder_Details_Screen_SecondVersion))) {
                        Modal_Static_GoatEarTagDetails.approxliveweight = modal_goatEarTagDetails.getApproxliveweight();
                        Modal_Static_GoatEarTagDetails.meatyieldweight = modal_goatEarTagDetails.getMeatyieldweight();
                        Modal_Static_GoatEarTagDetails.partsweight = modal_goatEarTagDetails.getPartsweight();
                        Modal_Static_GoatEarTagDetails.itemPrice = modal_goatEarTagDetails.getItemPrice();
                        Modal_Static_GoatEarTagDetails.itemWeight = modal_goatEarTagDetails.getTotalItemWeight();

                        Modal_Static_GoatEarTagDetails.totalPrice_ofItem = modal_goatEarTagDetails.getTotalPrice_ofItem();
                        Modal_Static_GoatEarTagDetails.discount = modal_goatEarTagDetails.getDiscount();
                        Modal_Static_GoatEarTagDetails.barcodeno = modal_goatEarTagDetails.getBarcodeno();
                        Modal_Static_GoatEarTagDetails.batchno = modal_goatEarTagDetails.getBatchno();
                        Modal_Static_GoatEarTagDetails.stockedweightingrams = modal_goatEarTagDetails.getStockedweightingrams();
                        Modal_Static_GoatEarTagDetails.description = modal_goatEarTagDetails.getDescription();
                        Modal_Static_GoatEarTagDetails.suppliername = modal_goatEarTagDetails.getSuppliername();
                        Modal_Static_GoatEarTagDetails.supplierkey = modal_goatEarTagDetails.getSupplierkey();
                        Modal_Static_GoatEarTagDetails.breedtype = modal_goatEarTagDetails.getBreedtype();
                        Modal_Static_GoatEarTagDetails.gender = modal_goatEarTagDetails.getGender();
                        Modal_Static_GoatEarTagDetails.status = modal_goatEarTagDetails.getStatus();
                        Modal_Static_GoatEarTagDetails.currentweightingrams = modal_goatEarTagDetails.getCurrentweightingrams();
                        Modal_Static_GoatEarTagDetails.loadedweightingrams = modal_goatEarTagDetails.getLoadedweightingrams();
                        Modal_Static_GoatEarTagDetails.gradename = modal_goatEarTagDetails.getGradename();


                        ShowGoatItemDetailsDialog();

                    }


                    else   if(CalledFrom.equals(getString(R.string.markdeliveredOrders_detailsScreen))) {
                        Modal_Static_GoatEarTagDetails.approxliveweight = modal_goatEarTagDetails.getApproxliveweight();
                        Modal_Static_GoatEarTagDetails.meatyieldweight = modal_goatEarTagDetails.getMeatyieldweight();
                        Modal_Static_GoatEarTagDetails.partsweight = modal_goatEarTagDetails.getPartsweight();
                        Modal_Static_GoatEarTagDetails.itemPrice = modal_goatEarTagDetails.getItemPrice();
                        Modal_Static_GoatEarTagDetails.itemWeight = modal_goatEarTagDetails.getTotalItemWeight();

                        Modal_Static_GoatEarTagDetails.totalPrice_ofItem = modal_goatEarTagDetails.getTotalPrice_ofItem();
                        Modal_Static_GoatEarTagDetails.discount = modal_goatEarTagDetails.getDiscount();
                        Modal_Static_GoatEarTagDetails.barcodeno = modal_goatEarTagDetails.getBarcodeno();
                        Modal_Static_GoatEarTagDetails.batchno = modal_goatEarTagDetails.getBatchno();
                        Modal_Static_GoatEarTagDetails.stockedweightingrams = modal_goatEarTagDetails.getStockedweightingrams();
                        Modal_Static_GoatEarTagDetails.description = modal_goatEarTagDetails.getDescription();
                        Modal_Static_GoatEarTagDetails.suppliername = modal_goatEarTagDetails.getSuppliername();
                        Modal_Static_GoatEarTagDetails.supplierkey = modal_goatEarTagDetails.getSupplierkey();
                        Modal_Static_GoatEarTagDetails.breedtype = modal_goatEarTagDetails.getBreedtype();
                        Modal_Static_GoatEarTagDetails.gender = modal_goatEarTagDetails.getGender();
                        Modal_Static_GoatEarTagDetails.status = modal_goatEarTagDetails.getStatus();
                        Modal_Static_GoatEarTagDetails.currentweightingrams = modal_goatEarTagDetails.getCurrentweightingrams();
                        Modal_Static_GoatEarTagDetails.loadedweightingrams = modal_goatEarTagDetails.getLoadedweightingrams();
                        Modal_Static_GoatEarTagDetails.gradename = modal_goatEarTagDetails.getGradename();


                        ShowGoatItemDetailsDialog();

                    }


                    else if( CalledFrom.equals(Constants.viewStockBalance)){
                        Modal_Static_GoatEarTagDetails.approxliveweight = modal_goatEarTagDetails.getApproxliveweight();
                        Modal_Static_GoatEarTagDetails.meatyieldweight = modal_goatEarTagDetails.getMeatyieldweight();
                        Modal_Static_GoatEarTagDetails.partsweight = modal_goatEarTagDetails.getPartsweight();
                        Modal_Static_GoatEarTagDetails.itemPrice = modal_goatEarTagDetails.getItemPrice();
                        Modal_Static_GoatEarTagDetails.itemWeight = modal_goatEarTagDetails.getTotalItemWeight();

                        Modal_Static_GoatEarTagDetails.totalPrice_ofItem = modal_goatEarTagDetails.getTotalPrice_ofItem();
                        Modal_Static_GoatEarTagDetails.discount = modal_goatEarTagDetails.getDiscount();
                        Modal_Static_GoatEarTagDetails.barcodeno = modal_goatEarTagDetails.getBarcodeno();
                        Modal_Static_GoatEarTagDetails.batchno = modal_goatEarTagDetails.getBatchno();
                        Modal_Static_GoatEarTagDetails.stockedweightingrams = modal_goatEarTagDetails.getStockedweightingrams();
                        Modal_Static_GoatEarTagDetails.description = modal_goatEarTagDetails.getDescription();
                        Modal_Static_GoatEarTagDetails.suppliername = modal_goatEarTagDetails.getSuppliername();
                        Modal_Static_GoatEarTagDetails.supplierkey = modal_goatEarTagDetails.getSupplierkey();
                        Modal_Static_GoatEarTagDetails.breedtype = modal_goatEarTagDetails.getBreedtype();
                        Modal_Static_GoatEarTagDetails.gender = modal_goatEarTagDetails.getGender();
                        Modal_Static_GoatEarTagDetails.status = modal_goatEarTagDetails.getStatus();
                        Modal_Static_GoatEarTagDetails.currentweightingrams = modal_goatEarTagDetails.getCurrentweightingrams();
                        Modal_Static_GoatEarTagDetails.loadedweightingrams = modal_goatEarTagDetails.getLoadedweightingrams();
                        Modal_Static_GoatEarTagDetails.gradename = modal_goatEarTagDetails.getGradename();


                        ShowGoatItemDetailsDialog();
                    }

                        return;

                    }
                else {
                    if (iterator - (earTagItemsForBatch.size() - 1) == 0) {
                        showProgressBar(false);
                        if(TaskToDo.equals("NewAddedItem")){
                            //statusToFilter = Constants.
                            Toast.makeText(this, "This Barcode is not in the Loaded Items List", Toast.LENGTH_SHORT).show();

                        }
                         else  if (TaskToDo.equals("Oldtem")) {
                            Toast.makeText(this, "This Barcode is not in the Loaded Items List", Toast.LENGTH_SHORT).show();

                        }
                        else  if(TaskToDo.equals("ViewSoldItem")){
                            Toast.makeText(this, "This Barcode is not in the Sold Items List", Toast.LENGTH_SHORT).show();

                        }
                        else  if(TaskToDo.equals("ViewUnSoldItem")){
                            Toast.makeText(this, "This Barcode is not in the UnSold Items List", Toast.LENGTH_SHORT).show();



                        }
                        else  if(TaskToDo.equals("ViewUnReviewedItem")){
                            Toast.makeText(this, "This Barcode is not in the UnReviewed Items List", Toast.LENGTH_SHORT).show();
                        }
                        else  if(TaskToDo.equals("ViewReviewedItem")){
                            Toast.makeText(this, "This Barcode is not in the Reviewed Items List", Toast.LENGTH_SHORT).show();
                        }
                        else  if(TaskToDo.equals(getString(R.string.view_sold_items_in_batch))) {
                            Toast.makeText(this, "This Barcode is not in the Sold Items List", Toast.LENGTH_SHORT).show();

                        }
                        else  if(TaskToDo.equals(getString(R.string.edit_unsold_items_in_batch)) || TaskToDo.equals(getString(R.string.view_unsold_items_in_batch))) {
                            Toast.makeText(this, "This Barcode is not in the UnSold Items List", Toast.LENGTH_SHORT).show();



                        }
                        //AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.item_not_scannedinstruction);
                        //  return;
                    }
                }
            }
        }
        else{
            showProgressBar(false);
            if(TaskToDo.equals("NewAddedItem")){
                //statusToFilter = Constants.
                Toast.makeText(this, "This Barcode is not in the Loaded Items List", Toast.LENGTH_SHORT).show();

            }
            else  if (TaskToDo.equals("Oldtem")) {
                Toast.makeText(this, "This Barcode is not in the Loaded Items List", Toast.LENGTH_SHORT).show();

            }
            else  if(TaskToDo.equals("ViewSoldItem")){
                Toast.makeText(this, "This Barcode is not in the Sold Items List", Toast.LENGTH_SHORT).show();

            }
            else  if(TaskToDo.equals("ViewUnSoldItem")){
                Toast.makeText(this, "This Barcode is not in the UnSold Items List", Toast.LENGTH_SHORT).show();



            }
            else  if(TaskToDo.equals("ViewUnReviewedItem")){
                Toast.makeText(this, "This Barcode is not in the UnReviewed Items List", Toast.LENGTH_SHORT).show();
            }
            else  if(TaskToDo.equals("ViewReviewedItem")){
                Toast.makeText(this, "This Barcode is not in the Reviewed Items List", Toast.LENGTH_SHORT).show();
            }

            else  if(TaskToDo.equals(getString(R.string.view_sold_items_in_batch)) || TaskToDo.equals(getString(R.string.view_unsold_items_in_batch))) {
                Toast.makeText(this, "This Barcode is not in the Sold Items List", Toast.LENGTH_SHORT).show();

            }
            else  if(TaskToDo.equals(getString(R.string.edit_unsold_items_in_batch))) {
                Toast.makeText(this, "This Barcode is not in the UnSold Items List", Toast.LENGTH_SHORT).show();



            }

            }


    }


    private void Initialize_and_ExecuteInGoatEarTagDetails(String callMethod) {

        showProgressBar(true);
        if (isGoatEarTagDetailsTableServiceCalled) {
            showProgressBar(false);
            return;
        }

        if(callMethod.equals(Constants.CallGETListMethod)){

            earTagItemsForBatch.clear();
            earTagItemsBarcodeList.clear();

            earTagItemsForBatch.clear();
        }

        isGoatEarTagDetailsTableServiceCalled = true;

        callback_GoatEarTagDetails = new GoatEarTagDetailsInterface() {


            @Override
            public void notifySuccess(String result) {

                //showProgressBar(false);


                         if(callMethod.equals(Constants.CallUPDATEMethod)) {
                             
                             try{
                                 Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod, callMethod);
                             }
                             catch (Exception e ){
                                 e.printStackTrace();
                             }
                             
                             
                            try{
                                for(int i = 0 ; i < earTagItemsForBatch.size() ; i++){
                                    String barcode = String.valueOf(earTagItemsForBatch.get(i).getBarcodeno());
                                    if(Modal_Static_GoatEarTagDetails.getBarcodeno().toString().equals(barcode)){
                                        try{
                                        earTagItemsForBatch.get(i).status = String.valueOf(Modal_Static_GoatEarTagDetails.getStatus());
                                        earTagItemsForBatch.get(i).gender = String.valueOf(Modal_Static_GoatEarTagDetails.getGender());
                                        earTagItemsForBatch.get(i).gradename = String.valueOf(Modal_Static_GoatEarTagDetails.getGradename());
                                        earTagItemsForBatch.get(i).partsweight = String.valueOf(Modal_Static_GoatEarTagDetails.getPartsweight());
                                        earTagItemsForBatch.get(i).meatyieldweight = String.valueOf(Modal_Static_GoatEarTagDetails.getMeatyieldweight());
                                        earTagItemsForBatch.get(i).approxliveweight = String.valueOf(Modal_Static_GoatEarTagDetails.getApproxliveweight());
                                        earTagItemsForBatch.get(i).itemPrice = String.valueOf(Modal_Static_GoatEarTagDetails.getItemPrice());
                                        earTagItemsForBatch.get(i).totalPrice_ofItem = String.valueOf(Modal_Static_GoatEarTagDetails.getTotalPrice_ofItem());
                                        earTagItemsForBatch.get(i).totalItemWeight = String.valueOf(Modal_Static_GoatEarTagDetails.getItemWeight());



                                       }
                                       catch (Exception e){
                                           e.printStackTrace();
                                       }



                                    }
                                    if(i == (earTagItemsForBatch.size() - 1)){
                                        try{
                                            adapter_earTagItemDetails_list.notifyDataSetChanged();
                                            try{
                                                  if(TaskToDo.equals(getString(R.string.edit_unsold_items_in_batch)) ||TaskToDo.equals(getString(R.string.view_sold_items_in_batch))   || TaskToDo.equals("PlacedOrderItemDetails") ) {

                                                    View_or_Edit_BatchItem_deliveryCenter_secondVersn.earTagItemsForBatch = earTagItemsForBatch;
                                                      View_or_Edit_BatchItem_deliveryCenter_secondVersn.view_or_edit_batchItem_deliveryCenter_secondVersn. ProcessArray_And_DisplayData();
                                                  }
                                                  else  if(TaskToDo.equals(getString(R.string.view_unsold_items_in_batch))){

                                                  }


                                            }
                                            catch (Exception e){
                                                e.printStackTrace();
                                            }


                                            if(CalledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion))) {
                                                Intialize_and_ExecuteInB2BCartItemDetails(Constants.CallUPDATEMethod,Modal_Static_GoatEarTagDetails.getBarcodeno(), DeliveryCenter_PlaceOrderScreen_SecondVersn.orderid);

                                            }

                                            }
                                        catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }


                                }
                                showProgressBar(false);
                                isGoatEarTagDetailsTableServiceCalled = false;
                             }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                         }
                         else{
                             showProgressBar(false);
                             isGoatEarTagDetailsTableServiceCalled = false;
                         }
                }

            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatchFromDB) {
                try{
                    if(earTagItemsForBatchFromDB.size()>0) {
                        earTagItemsForBatch = earTagItemsForBatchFromDB;


                        isGoatEarTagDetailsTableServiceCalled = false;

                     //   itemunscannedCount_textview.setText(String.valueOf(earTagItemsForBatch.size()));

                    }
                    else{





                        showProgressBar(false);
                        isGoatEarTagDetailsTableServiceCalled = false;
                        earTagItems_listview.setVisibility(View.GONE);
                        Toast.makeText(GoatEarTagItemDetailsList.this, Constants.there_is_noData_volley, Toast.LENGTH_SHORT).show();


                    }
                    setAdapter(earTagItemsForBatch);
                }
                catch (Exception e){
                    setAdapter(earTagItemsForBatch);
                    showProgressBar(false);
                    isGoatEarTagDetailsTableServiceCalled = false;

                    Toast.makeText(GoatEarTagItemDetailsList.this, "There is an error while generate report", Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }



            }



            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
                Toast.makeText(GoatEarTagItemDetailsList.this, "There is an volley error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                setAdapter(earTagItemsForBatch);
                isGoatEarTagDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                Toast.makeText(GoatEarTagItemDetailsList.this, "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                setAdapter(earTagItemsForBatch);
                showProgressBar(false);
                isGoatEarTagDetailsTableServiceCalled = false;


            }




        };
        
        if(callMethod.equals(Constants.CallGETListMethod)) {
            if (CalledFrom.equals(Constants.userType_SupplierCenter)) {
                if (TaskToDo.equals("NewAddedItem")) {
                    String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithVariousStatus + "?batchno=" + batchno + "&status1=" + Constants.goatEarTagStatus_EarTagLost + "&filtertype=" + Constants.api_filtertype_notequals;
                    GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
                    asyncTask.execute();

                } else if (TaskToDo.equals("Oldtem")) {
                    String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithVariousStatus + "?batchno=" + batchno + "&status1=" + Constants.goatEarTagStatus_EarTagLost + "&filtertype=" + Constants.api_filtertype_notequals;
                    GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
                    asyncTask.execute();
                }

            }
            else if (CalledFrom.equals(Constants.userType_DeliveryCenter) || CalledFrom.equals(Constants.viewStockBalance)) {
                if (TaskToDo.equals("ViewUnReviewedItem")) {
                    String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithStatus + "?batchno=" + batchno + "&status=" + Constants.goatEarTagStatus_Loading;
                    GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
                    asyncTask.execute();

                } else if (TaskToDo.equals("ViewReviewedItem")) {
                    String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithVariousStatus + "?batchno=" + batchno + "&status1=" + Constants.goatEarTagStatus_Reviewed_and_READYFORSALE + "&status2=" + Constants.goatEarTagStatus_Goatsick + "&filtertype=" + Constants.api_filtertype_equals;
                    GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
                    asyncTask.execute();

                } else if (TaskToDo.equals("ViewSoldItem")) {
                    String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithStatus + "?batchno=" + batchno + "&status=" + Constants.goatEarTagStatus_Sold;
                    GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
                    asyncTask.execute();

                } else if (TaskToDo.equals("ViewUnSoldItem")) {
                    String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithVariousStatus + "?batchno=" + batchno + "&status1=" + Constants.goatEarTagStatus_Reviewed_and_READYFORSALE + "&status2=" + Constants.goatEarTagStatus_Goatsick + "&filtertype=" + Constants.api_filtertype_equals;
                    GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
                    asyncTask.execute();

                } else if (TaskToDo.equals(getString(R.string.view_sold_items_in_batch))) {
                    String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithStatus + "?batchno=" + batchno + "&status=" + Constants.goatEarTagStatus_Sold;
                    GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
                    asyncTask.execute();

                } else if (TaskToDo.equals(getString(R.string.edit_unsold_items_in_batch))) {
                    String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithVariousStatus + "?batchno=" + batchno + "&status1=" + Constants.goatEarTagStatus_Reviewed_and_READYFORSALE + "&status2=" + Constants.goatEarTagStatus_Goatsick + "&status3=" + Constants.goatEarTagStatus_Goatdead + "&filtertype=" + Constants.api_filtertype_equals;
                    GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
                    asyncTask.execute();

                }
                else if(TaskToDo.equals(getString(R.string.view_unsold_items_in_batch))) {
                    String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithVariousStatus + "?batchno=" + batchno + "&status1=" + Constants.goatEarTagStatus_Reviewed_and_READYFORSALE + "&status2=" + Constants.goatEarTagStatus_Goatsick + "&status3=" + Constants.goatEarTagStatus_Goatdead + "&filtertype=" + Constants.api_filtertype_equals;
                    GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails, addApiToCall, callMethod);
                    asyncTask.execute();

                }

            }

        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)) {
            DecimalFormat df = new DecimalFormat(Constants.threeDecimalPattern);
            DecimalFormat df2 = new DecimalFormat(Constants.twoDecimalPattern);
            try{
                // Modal_UpdatedGoatEarTagDetails.setUpdated_selectedItem(selectedCategoryItem);
                Modal_Static_GoatEarTagDetails.barcodeno = scannedBarcode;
                Modal_UpdatedGoatEarTagDetails.setUpdated_gender(selectedGenderName);
                Modal_UpdatedGoatEarTagDetails.setUpdated_approxliveweight(String.valueOf(df.format(approx_Live_Weight_double)));
                Modal_UpdatedGoatEarTagDetails.setUpdated_meatyieldweight(String.valueOf(df.format(meatyeild_weight_double)));
                Modal_UpdatedGoatEarTagDetails.setUpdated_partsweight(String.valueOf(df.format(parts_Weight_double)));
                Modal_UpdatedGoatEarTagDetails.setUpdated_totalWeight(String.valueOf(df.format(totalWeight_double)));
                Modal_UpdatedGoatEarTagDetails.setUpdated_gradename(selectedGradeName);
                Modal_UpdatedGoatEarTagDetails.setUpdated_status(selectedItemsStatus);
                Modal_UpdatedGoatEarTagDetails.setUpdated_Price(String.valueOf(df2.format(pricewithOutdiscount_double)));


            }
            catch (Exception e){
                e.printStackTrace();
            }
            
            
            
            String addApiToCall = API_Manager.updateGoatEarTag ;
            GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails,  addApiToCall , callMethod);
            asyncTask.execute();
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
                Toast.makeText(GoatEarTagItemDetailsList.this, "There is an volley error while updating Ear Tag Transaction", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(GoatEarTagItemDetailsList.this, "There is an Process error while updating Ear Tag Transaction", Toast.LENGTH_SHORT).show();
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
                    Modal_GoatEarTagTransaction.mobileno = userMobileNo;
                    Modal_GoatEarTagTransaction.description = Modal_Static_GoatEarTagDetails.getDescription();
                    Modal_GoatEarTagTransaction.gradeprice=(Modal_Static_GoatEarTagDetails.getGradeprice());
                    Modal_GoatEarTagTransaction.gradename=(selectedGradeName);
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
                    Modal_GoatEarTagTransaction.mobileno = userMobileNo;
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_gradename_boolean()){

                        Modal_GoatEarTagTransaction.gradename=(selectedGradeName);

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
                        Modal_GoatEarTagTransaction.gender = selectedGenderName;
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


    private void setAdapter(ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch) {


        earTagItemsForBatch =  sortThisArrayUsingBarcode(earTagItemsForBatch);

        if(earTagItemsForBatch.size()>0){
            earTagItems_listview.setVisibility(View.VISIBLE);
            instruction_Textview.setVisibility(View.GONE);

        }
        else{
            earTagItems_listview.setVisibility(View.GONE);
            instruction_Textview.setVisibility(View.VISIBLE);
        }


        adapter_earTagItemDetails_list = new Adapter_EarTagItemDetails_List(GoatEarTagItemDetailsList.this, earTagItemsForBatch, GoatEarTagItemDetailsList.this,CalledFrom,TaskToDo,batchStatus);
        earTagItems_listview.setAdapter(adapter_earTagItemDetails_list);
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





    public void loadMyFragment() {
        if(isTransactionSafe) {
            isTransactionPending=false;
        if (mfragment != null) {
            Fragment fragment = null;
            batchItemDetailsFrame.setVisibility(View.GONE);
            try {
                fragment = getSupportFragmentManager().findFragmentById(batchItemDetailsFrame.getId());
            }
            catch (Exception e){
                e.printStackTrace();
            }
            if(fragment!=null) {
                try {
                    batchItemDetailsFrame.setVisibility(View.GONE);
                     fragment = getSupportFragmentManager().findFragmentById(batchItemDetailsFrame.getId());
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
                transaction2 .replace(batchItemDetailsFrame.getId(),  BatchItemDetailsFragment_withoutScanBarcode.newInstance(getString(R.string.called_from),value_forFragment));

                transaction2.remove(mfragment).commit();
                batchItemDetailsFrame.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                onResume();
                batchItemDetailsFrame.setVisibility(View.GONE);
                loadMyFragment();
                e.printStackTrace();
            }


        }

        }
        else {

            isTransactionPending = true;
        }
    }




    public  void closeFragment() {

        if(isTransactionSafe) {
            isTransactionPending=false;
        try {
            Fragment fragment = getSupportFragmentManager().findFragmentById(batchItemDetailsFrame.getId());
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();


            showProgressBar(false);
            batchItemDetailsFrame.setVisibility(View.GONE);
        }catch (Exception e){
            onResume();
            closeFragment();
            e.printStackTrace();
        }
        }
        else {

            isTransactionPending=true;

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

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (earTagItemsForBatch.size() == 0) {
                earTagItems_listview.setVisibility(View.GONE);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
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


    @Override
    public void onBackPressed() {

            try {
                if (batchItemDetailsFrame.getVisibility() == View.VISIBLE) {

                    batchItemDetailsFrame.setVisibility(View.GONE);

                } else {


                    finish();

                }
            } catch (Exception e) {
                e.printStackTrace();

                finish();

            }

    }

    public void deleteItemFromGoatEarTagItemList(String batchno, String barcodeno, String orderid_forBillingScreen) {

        try {
            for (int i = 0; i < earTagItemsForBatch.size(); i++) {
                if (earTagItemsForBatch.get(i).getBarcodeno().equals(barcodeno)) {

                    earTagItemsForBatch.remove(i);
                    adapter_earTagItemDetails_list.notifyDataSetChanged();
                    if(earTagItemsForBatch.size()==0){
                        earTagItems_listview.setVisibility(View.GONE);
                    }



                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void ShowGoatItemDetailsDialog() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {

                    //Theme_Dialog
                    //Theme_Design_Light



                    show_goatEarTagItemDetails_Dialog = new Dialog(GoatEarTagItemDetailsList.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_TranslucentDecor);
                    //  show_scan_barcode_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    show_goatEarTagItemDetails_Dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    show_goatEarTagItemDetails_Dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


                    try {
                        BaseActivity.baseActivity.getDeviceName();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        if (BaseActivity.isDeviceIsMobilePhone) {
                            show_goatEarTagItemDetails_Dialog.getWindow().setLayout(WindowManager.LayoutParams.  MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);



                        } else {

                            show_goatEarTagItemDetails_Dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

                        }
                    }
                    catch (Exception e){
                        show_goatEarTagItemDetails_Dialog.getWindow().setLayout(WindowManager.LayoutParams.  MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

                        e.printStackTrace();

                    }

                    try{
                        if (BaseActivity.isDeviceIsMobilePhone) {
                            show_goatEarTagItemDetails_Dialog.setContentView(R.layout.fragment_goat_item_details__second_versn);


                        } else {

                            show_goatEarTagItemDetails_Dialog.setContentView(R.layout.pos_fragment_goat_item_details__second_versn);
                        }

                    }
                    catch (Exception e){
                        show_goatEarTagItemDetails_Dialog.setContentView(R.layout.fragment_goat_item_details__second_versn);
                        e.printStackTrace();
                    }


                    show_goatEarTagItemDetails_Dialog.setCanceledOnTouchOutside(false);


                    TextView barcodeNo_textView = show_goatEarTagItemDetails_Dialog.findViewById(R.id.barcodeNo_textView);
                    TextView gradeName_textview = show_goatEarTagItemDetails_Dialog.findViewById(R.id.gradeName_textview);
                    TextView gender_textview = show_goatEarTagItemDetails_Dialog.findViewById(R.id.gender_textview);
                    TextView totalweight_textview = show_goatEarTagItemDetails_Dialog.findViewById(R.id.totalweight_textview);
                    TextView totalPrice_textView = show_goatEarTagItemDetails_Dialog.findViewById(R.id.totalPrice_textView);



                    TextView selectedGoatStatus_textview = show_goatEarTagItemDetails_Dialog.findViewById(R.id.selectedGoatStatus_textview);
                    TextView approxLiveWeight_textview = show_goatEarTagItemDetails_Dialog.findViewById(R.id.approxLiveWeight_textview);
                    TextView meatyeild_textview = show_goatEarTagItemDetails_Dialog.findViewById(R.id.meatyeild_textview);
                    TextView parts_textView = show_goatEarTagItemDetails_Dialog.findViewById(R.id.parts_textView);
                    TextView priceWithoutDiscount_textview = show_goatEarTagItemDetails_Dialog.findViewById(R.id.priceWithoutDiscount_textview);
                    TextView discount_textView = show_goatEarTagItemDetails_Dialog.findViewById(R.id.discount_textView);



                    EditText approxLiveWeight_EditText = show_goatEarTagItemDetails_Dialog.findViewById(R.id.approxLiveWeight_EditText);
                    EditText parts_editText = show_goatEarTagItemDetails_Dialog.findViewById(R.id.parts_editText);
                    EditText discount_edittext = show_goatEarTagItemDetails_Dialog.findViewById(R.id.discount_edittext);
                    EditText meatyeild_edittext = show_goatEarTagItemDetails_Dialog.findViewById(R.id.meatyeild_edittext);
                    EditText gradeName_editText = show_goatEarTagItemDetails_Dialog.findViewById(R.id.gradeName_editText);
                    EditText  totalPrice_edittext = show_goatEarTagItemDetails_Dialog.findViewById(R.id. totalPrice_edittext);
                    EditText  priceWithoutDiscount_edittext =  show_goatEarTagItemDetails_Dialog.findViewById(R.id. priceWithoutDiscount_edittext);



                    Button save_button = show_goatEarTagItemDetails_Dialog.findViewById(R.id.save_button);

                    LinearLayout back_IconLayout  = show_goatEarTagItemDetails_Dialog.findViewById(R.id.back_IconLayout);
                    LinearLayout discount_price_layout_label = show_goatEarTagItemDetails_Dialog.findViewById(R.id.discount_price_layout_label);

                    LinearLayout goatstatus_layout  = show_goatEarTagItemDetails_Dialog.findViewById(R.id.goatstatus_layout);



                    RadioGroup genderRadioGroup = show_goatEarTagItemDetails_Dialog.findViewById(R.id.genderRadioGroup);
                    RadioButton male_radioButton = show_goatEarTagItemDetails_Dialog.findViewById(R.id.male_radioButton);
                    RadioButton female_radioButton = show_goatEarTagItemDetails_Dialog.findViewById(R.id.female_radioButton);


                    RadioGroup goatstatusradiogrp = show_goatEarTagItemDetails_Dialog.findViewById(R.id.goatstatusradiogrp);
                    RadioButton normal_goat_radio = show_goatEarTagItemDetails_Dialog.findViewById(R.id.normal_goat_radio);
                    RadioButton dead_goat_radio = show_goatEarTagItemDetails_Dialog.findViewById(R.id.dead_goat_radio);
                    RadioButton sick_goat_radio = show_goatEarTagItemDetails_Dialog.findViewById(R.id.sick_goat_radio);



                    if(TaskToDo.equals(getString(R.string.edit_unsold_items_in_batch)) ||TaskToDo.equals(getString(R.string.view_sold_items_in_batch))){
                      //  discount_price_layout_label.setVisibility(View.GONE);
                      //  discount_price_layout_edittext.setVisibility(View.GONE);
                    }

                    if(TaskToDo.equals(getString(R.string.edit_unsold_items_in_batch))) {
                        if (batchStatus.equals(Constants.batchDetailsStatus_Sold) || batchStatus.equals(Constants.batchDetailsStatus_Cancelled)) {
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


                            goatstatus_layout .setVisibility(View.GONE);
                        }
                        else {
                        selectedGoatStatus_textview.setVisibility(View.GONE);
                        goatstatusradiogrp.setVisibility(View.VISIBLE);
                        save_button.setVisibility(View.VISIBLE);

                        gradeName_editText.setVisibility(View.VISIBLE);
                        gradeName_textview.setVisibility(View.GONE);

                        genderRadioGroup.setVisibility(View.VISIBLE);
                        gender_textview.setVisibility(View.GONE);

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

                        goatstatus_layout.setVisibility(View.VISIBLE);

                        if (selectedGenderName.toUpperCase().equals(getString(R.string.MALE))) {
                            male_radioButton.setChecked(true);
                            female_radioButton.setChecked(false);
                        } else if (selectedGenderName.toUpperCase().equals(getString(R.string.FEMALE))) {
                            male_radioButton.setChecked(false);
                            female_radioButton.setChecked(true);
                        }

                        if (selectedItemsStatus.toUpperCase().equals(Constants.goatEarTagStatus_Goatsick)) {
                            sick_goat_radio.setChecked(true);
                            normal_goat_radio.setChecked(false);
                            dead_goat_radio.setChecked(false);
                        } else if (selectedItemsStatus.toUpperCase().equals(Constants.goatEarTagStatus_Goatdead)) {
                            sick_goat_radio.setChecked(false);
                            normal_goat_radio.setChecked(false);
                            dead_goat_radio.setChecked(true);
                        } else {
                            sick_goat_radio.setChecked(false);
                            normal_goat_radio.setChecked(true);
                            dead_goat_radio.setChecked(false);
                        }
                    }
                    }
                    else if (TaskToDo.equals(getString(R.string.view_sold_items_in_batch))  || TaskToDo.equals("PlacedOrderItemDetails") ||  TaskToDo.equals(getString(R.string.view_unsold_items_in_batch)) )  {
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


                        goatstatus_layout .setVisibility(View.GONE);
                    }


                    approx_Live_Weight_double = 0 ; meatyeild_weight_double = 0 ; parts_Weight_double = 0 ; totalWeight_double = 0 ;
                    discountDouble = 0 ; totalPrice_double = 0;
                    back_IconLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            show_goatEarTagItemDetails_Dialog.cancel();
                        }
                    });



                    goatstatusradiogrp.setOnCheckedChangeListener((group, checkedId) -> {

                        switch (checkedId){
                            case R.id.sick_goat_radio:
                                sick_goat_radio.setChecked(true);
                                normal_goat_radio.setChecked(false);
                                dead_goat_radio.setChecked(false);
                                selectedItemsStatus = Constants.goatEarTagStatus_Goatsick;
                                break;
                            case R.id.normal_goat_radio:
                                sick_goat_radio.setChecked(false);
                                normal_goat_radio.setChecked(true);
                                dead_goat_radio.setChecked(false);
                                selectedItemsStatus = Constants.goatEarTagStatus_Reviewed_and_READYFORSALE;
                                break;
                            case R.id.dead_goat_radio:

                                sick_goat_radio.setChecked(false);
                                normal_goat_radio.setChecked(false);
                                dead_goat_radio.setChecked(true);
                                selectedItemsStatus = Constants.goatEarTagStatus_Goatdead;

                                break;
                            default:
                                sick_goat_radio.setChecked(false);
                                normal_goat_radio.setChecked(true);
                                dead_goat_radio.setChecked(false);
                                selectedItemsStatus = Constants.goatEarTagStatus_Reviewed_and_READYFORSALE;
                                break;
                        }



                            });

                    genderRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {

                        switch (checkedId){
                            case R.id.female_radioButton:
                                female_radioButton.setChecked(true);
                                male_radioButton.setChecked(false);
                                selectedGenderName = getString(R.string.FEMALE);
                                break;
                            case R.id.male_radioButton:
                                female_radioButton.setChecked(false);
                                male_radioButton.setChecked(true);
                                selectedGenderName = getString(R.string.MALE);
                                break;
                            default:
                                female_radioButton.setChecked(false);
                                male_radioButton.setChecked(true);
                                selectedGenderName = getString(R.string.MALE);
                                break;
                        }



                    });


                    save_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            try{
                                String text =  String.valueOf(approxLiveWeight_EditText.getText().toString());
                                text = text.replaceAll("[^\\d.]", "");
                                if(text.equals("")){
                                    text = "0";
                                }
                                approx_Live_Weight_double  = Double.parseDouble(text);


                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }

                            try{
                                String text =  String.valueOf(meatyeild_edittext.getText().toString());
                                text = text.replaceAll("[^\\d.]", "");
                                if(text.equals("")){
                                    text = "0";
                                }
                                meatyeild_weight_double  = Double.parseDouble(text);


                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }

                            try{
                                String text =  String.valueOf(parts_editText.getText().toString());
                                text = text.replaceAll("[^\\d.]", "");
                                if(text.equals("")){
                                    text = "0";
                                }
                                parts_Weight_double  = Double.parseDouble(text);

                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }


                            try{
                                String text =  String.valueOf(priceWithoutDiscount_edittext.getText().toString());
                                text = text.replaceAll("[^\\d.]", "");
                                if(text.equals("")){
                                    text = "0";
                                }
                                pricewithOutdiscount_double  = Double.parseDouble(text);


                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }

                            try{
                                String text =  String.valueOf(discount_edittext.getText().toString());
                                text = text.replaceAll("[^\\d.]", "");
                                if(text.equals("")){
                                    text = "0";
                                }

                                discountDouble  = Double.parseDouble(text);


                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }


                            try{
                                scannedBarcode = barcodeNo_textView.getText().toString();
                            }
                            catch (Exception e ){
                                e.printStackTrace();
                            }
                            if(TaskToDo.equals(getString(R.string.edit_unsold_items_in_batch)) ||TaskToDo.equals(getString(R.string.view_sold_items_in_batch)) ||TaskToDo.equals(getString(R.string.view_unsold_items_in_batch))  || TaskToDo.equals("PlacedOrderItemDetails")) {

                                try{
                                    String text =  String.valueOf(priceWithoutDiscount_edittext.getText().toString());
                                    text = text.replaceAll("[^\\d.]", "");
                                    if(text.equals("")){
                                        text = "0";
                                    }
                                    pricewithOutdiscount_double  = Double.parseDouble(text);
                                    totalPrice_double = Double.parseDouble(text);

                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                            }


                            try{

                                totalWeight_double = meatyeild_weight_double + parts_Weight_double;

                                totalPrice_double = pricewithOutdiscount_double - discountDouble ;


                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }











                            try{
                                if(approx_Live_Weight_double>0){
                                    if(parts_Weight_double>0){
                                        if(meatyeild_weight_double>0){
                                            if(pricewithOutdiscount_double>0){
                                                if(totalPrice_double>0){
                                                    if(totalWeight_double>0){
                                                        if(scannedBarcode.length()>0){
                                                            selectedGradeName  = String.valueOf(gradeName_editText.getText());

                                                            try{
                                                                showProgressBar(true);
                                                                show_goatEarTagItemDetails_Dialog.cancel();
                                                                show_goatEarTagItemDetails_Dialog.dismiss();
                                                            }
                                                            catch (Exception e){
                                                                e.printStackTrace();
                                                            }


                                                            if(CalledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion))) {



                                                               /* Modal_Static_GoatEarTagDetails.setApproxliveweight(String.valueOf(approx_Live_Weight_double));
                                                                Modal_Static_GoatEarTagDetails.setDiscount(String.valueOf(discountDouble));
                                                                Modal_Static_GoatEarTagDetails.setMeatyieldweight(String.valueOf(meatyeild_weight_double));
                                                                Modal_Static_GoatEarTagDetails.setPartsweight(String.valueOf(parts_Weight_double));
                                                                Modal_Static_GoatEarTagDetails.setTotalPrice_ofItem(String.valueOf(totalPrice_double));
                                                                Modal_Static_GoatEarTagDetails.setItemWeight(String.valueOf(totalWeight_double));
                                                                Modal_Static_GoatEarTagDetails.setGradename(String.valueOf(gradeName_editText.getText()));

                                                                if(selectedItemsStatus.toUpperCase().equals(Constants.goatEarTagStatus_Goatsick)){
                                                                    Modal_Static_GoatEarTagDetails.status = Constants.goatEarTagStatus_Goatsick;
                                                                }
                                                                else if(selectedItemsStatus.toUpperCase().equals(Constants.goatEarTagStatus_Goatdead)){
                                                                    Modal_Static_GoatEarTagDetails.status = Constants.goatEarTagStatus_Goatdead;

                                                                }
                                                                else{
                                                                    Modal_Static_GoatEarTagDetails.status = Constants.goatEarTagStatus_Reviewed_and_READYFORSALE;
                                                                }

                                                                Modal_Static_GoatEarTagDetails.gender = selectedGenderName;
                                                                Modal_Static_GoatEarTagDetails.barcodeno = String.valueOf(barcodeNo_textView.getText());
                                                                Modal_Static_GoatEarTagDetails.itemPrice = String.valueOf(pricewithOutdiscount_double);


                                                                */

                                                                try{
                                                                    Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);
                                                                }
                                                                catch (Exception e){
                                                                    e.printStackTrace();
                                                                }


                                                            }
                                                            else if(TaskToDo.equals(getString(R.string.edit_unsold_items_in_batch)) ||TaskToDo.equals(getString(R.string.view_sold_items_in_batch)) || TaskToDo.equals(getString(R.string.view_unsold_items_in_batch))  || TaskToDo.equals("PlacedOrderItemDetails") ) {
                                                                
                                                                Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallGETMethod);
                                                            }





                                                        }
                                                        else {
                                                            AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this, R.string.CannotSaveWithOutBarcodeAlert);

                                                        }
                                                    }
                                                    else{
                                                        AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.CannotSaveWhenTotalWeightisZeroAlert);

                                                    }
                                                }
                                                else{
                                                    AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.CannotSaveWhenTotalPriceisZeroAlert);

                                                }
                                            }
                                            else{
                                                AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.CannotSaveWhenTotalPriceWithOutDiscountisZeroAlert);

                                            }
                                        }
                                        else{
                                            AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.CannotSaveWhenMeatYieldisZeroAlert);

                                        }

                                    }
                                    else{
                                        AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.CannotSaveWhenPartsisZeroAlert);

                                    }
                                }
                                else{
                                    AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.CannotSaveWhenApproxWeightisZeroAlert);

                                }
                            }
                            catch (Exception e){
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


                                    try{
                                        String text = String.valueOf(meatyeild_edittext.getText());
                                        text = text.replaceAll("[^\\d.]", "");
                                        if(text.equals("")){
                                            text = "0";
                                        }
                                        meatyeild_weight_double = Double.parseDouble(text);
                                    }
                                    catch (Exception e){
                                        meatyeild_weight_double = 0;
                                        e.printStackTrace();
                                    }


                                    try{
                                        String text = String.valueOf(parts_editText.getText());
                                        text = text.replaceAll("[^\\d.]", "");
                                        if(text.equals("")){
                                            text = "0";
                                        }
                                        parts_Weight_double = Double.parseDouble(text);
                                    }
                                    catch (Exception e){
                                        parts_Weight_double = 0;
                                        e.printStackTrace();
                                    }


                                    if(meatyeild_weight_double>0) {
                                        if(parts_Weight_double>0) {
                                            try{
                                                totalWeight_double = meatyeild_weight_double +  parts_Weight_double;
                                            }
                                            catch (Exception e){
                                                totalWeight_double = 0;
                                                e.printStackTrace();
                                            }

                                            totalweight_textview.setText(String.valueOf(totalWeight_double));
                                            InputMethodManager imm = (InputMethodManager) GoatEarTagItemDetailsList.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                            Objects.requireNonNull(imm).hideSoftInputFromWindow(textView.getWindowToken(), 0);


                                        }
                                        else{
                                            AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.CannotSaveWhenPartsisZeroAlert);

                                        }
                                    }
                                    else{
                                        AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.CannotSaveWhenMeatYieldisZeroAlert);

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


                                    try{
                                        String text = String.valueOf(meatyeild_edittext.getText());
                                        text = text.replaceAll("[^\\d.]", "");
                                        if(text.equals("")){
                                            text = "0";
                                        }
                                        meatyeild_weight_double = Double.parseDouble(text);
                                    }
                                    catch (Exception e){
                                        meatyeild_weight_double = 0;
                                        e.printStackTrace();
                                    }


                                    try{
                                        String text = String.valueOf(parts_editText.getText());
                                        text = text.replaceAll("[^\\d.]", "");
                                        if(text.equals("")){
                                            text = "0";
                                        }
                                        parts_Weight_double = Double.parseDouble(text);
                                    }
                                    catch (Exception e){
                                        parts_Weight_double = 0;
                                        e.printStackTrace();
                                    }


                                    if(meatyeild_weight_double>0) {
                                        if(parts_Weight_double>0) {
                                            try{
                                                totalWeight_double = meatyeild_weight_double +  parts_Weight_double;
                                            }
                                            catch (Exception e){
                                                totalWeight_double = 0;
                                                e.printStackTrace();
                                            }

                                            totalweight_textview.setText(String.valueOf(totalWeight_double));
                                            InputMethodManager imm = (InputMethodManager) GoatEarTagItemDetailsList.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                            Objects.requireNonNull(imm).hideSoftInputFromWindow(textView.getWindowToken(), 0);


                                        }
                                        else{
                                            AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.CannotSaveWhenPartsisZeroAlert);

                                        }
                                    }
                                    else{
                                        AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this,R.string.CannotSaveWhenMeatYieldisZeroAlert);

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


                                    try{
                                        String text = String.valueOf(discount_edittext.getText());
                                        text = text.replaceAll("[^\\d.]", "");
                                        if(text.equals("")){
                                            text = "0";
                                        }
                                        discountDouble = Double.parseDouble(text);
                                    }
                                    catch (Exception e){
                                        discountDouble = 0;
                                        e.printStackTrace();
                                    }


                                    try{
                                        String text = String.valueOf(priceWithoutDiscount_edittext.getText());
                                        text = text.replaceAll("[^\\d.]", "");
                                        if(text.equals("")){
                                            text = "0";
                                        }
                                        pricewithOutdiscount_double = Double.parseDouble(text);
                                    }
                                    catch (Exception e){
                                        pricewithOutdiscount_double = 0;
                                        e.printStackTrace();
                                    }
                                    if(discountDouble <= pricewithOutdiscount_double) {
                                        if (pricewithOutdiscount_double > 0) {
                                            try {
                                                totalPrice_double = pricewithOutdiscount_double - discountDouble;
                                            } catch (Exception e) {
                                                totalWeight_double = 0;
                                                e.printStackTrace();
                                            }


                                            if (totalPrice_double > 0) {
                                                totalPrice_textView.setText(String.valueOf(totalPrice_double));
                                                InputMethodManager imm = (InputMethodManager) GoatEarTagItemDetailsList.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                                Objects.requireNonNull(imm).hideSoftInputFromWindow(textView.getWindowToken(), 0);

                                            } else {
                                                AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this, R.string.CannotSaveWhenTotalPriceisZeroAlert);

                                            }
                                        } else {
                                            AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this, R.string.CannotSaveWhenTotalPriceWithOutDiscountisZeroAlert);

                                        }
                                    }
                                    else{
                                        AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this, R.string.CannotSaveWhendiscountisgreaterthanpriceisZeroAlert);

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


                                    try{
                                        String text = String.valueOf(discount_edittext.getText());
                                        text = text.replaceAll("[^\\d.]", "");
                                        if(text.equals("")){
                                            text = "0";
                                        }
                                        discountDouble = Double.parseDouble(text);
                                    }
                                    catch (Exception e){
                                        discountDouble = 0;
                                        e.printStackTrace();
                                    }


                                    try{
                                        String text = String.valueOf(priceWithoutDiscount_edittext.getText());
                                        text = text.replaceAll("[^\\d.]", "");
                                        if(text.equals("")){
                                            text = "0";
                                        }
                                        pricewithOutdiscount_double = Double.parseDouble(text);
                                    }
                                    catch (Exception e){
                                        pricewithOutdiscount_double = 0;
                                        e.printStackTrace();
                                    }

                                    if(discountDouble <= pricewithOutdiscount_double) {
                                        if (pricewithOutdiscount_double > 0) {
                                            try {
                                                totalPrice_double = pricewithOutdiscount_double - discountDouble;
                                            } catch (Exception e) {
                                                totalWeight_double = 0;
                                                e.printStackTrace();
                                            }


                                            if (totalPrice_double > 0) {
                                                totalPrice_textView.setText(String.valueOf(totalPrice_double));
                                                InputMethodManager imm = (InputMethodManager) GoatEarTagItemDetailsList.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                                Objects.requireNonNull(imm).hideSoftInputFromWindow(textView.getWindowToken(), 0);

                                            } else {
                                                AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this, R.string.CannotSaveWhenTotalPriceisZeroAlert);

                                            }
                                        } else {
                                            AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this, R.string.CannotSaveWhenTotalPriceWithOutDiscountisZeroAlert);

                                        }
                                    }
                                    else{
                                        AlertDialogClass.showDialog(GoatEarTagItemDetailsList.this, R.string.CannotSaveWhendiscountisgreaterthanpriceisZeroAlert);

                                    }








                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            return false;
                        }
                    });

                    try {
                        show_goatEarTagItemDetails_Dialog.show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                    if(Modal_Static_GoatEarTagDetails.getBarcodeno().equals("") || Modal_Static_GoatEarTagDetails.getBarcodeno().equals(null)){
                        show_goatEarTagItemDetails_Dialog.cancel();
                        Toast.makeText(getApplicationContext(), "Please Scan / Type Barcode Again", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        barcodeNo_textView.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getBarcodeno()));
                        gradeName_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getGradename()));
                        gender_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getGender()));
                        gender = String.valueOf(Modal_Static_GoatEarTagDetails.getGender());
                        gradeName_editText.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getGradename()));

                        double meatYield_double = 0 , parts_double  = 0 ,totalWeight_double = 0;double price_double=0 , discount_double =0 ;
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






                        double temp_totalWeight = 0;
                        try{
                            temp_totalWeight = totalWeight_double;
                            totalWeight_double = Double.parseDouble(df3.format(totalWeight_double));
                        }
                        catch (Exception e){
                            totalWeight_double = temp_totalWeight;
                            e.printStackTrace();
                        }


                        totalweight_textview.setText(String.valueOf(totalWeight_double));


                        try{
                            String text = String.valueOf(Modal_Static_GoatEarTagDetails.getDiscount());
                            text = text.replaceAll("[^\\d.]", "");
                            if(text.equals("")){
                                text = "0";
                            }
                            discount_double = Double.parseDouble(text);
                        }
                        catch (Exception e){
                            discount_double = 0;
                            e.printStackTrace();
                        }
                        try{
                            String text = String.valueOf(Modal_Static_GoatEarTagDetails.getItemPrice());
                            text = text.replaceAll("[^\\d.]", "");
                            if(text.equals("")){
                                text = "0";
                            }
                            price_double = Double.parseDouble(text);

                            if(price_double==0){
                                try{
                                     text = String.valueOf(Modal_Static_GoatEarTagDetails.getTotalPrice_ofItem());
                                    text = text.replaceAll("[^\\d.]", "");
                                    if(text.equals("")){
                                        text = "0";
                                    }
                                    price_double = Double.parseDouble(text);

                                }
                                catch (Exception e){
                                    price_double = 0;
                                    e.printStackTrace();
                                }
                            }
                        }
                        catch (Exception e){
                            price_double = 0;
                            if(price_double==0){
                                try{
                                    String text = String.valueOf(Modal_Static_GoatEarTagDetails.getTotalPrice_ofItem());
                                    text = text.replaceAll("[^\\d.]", "");
                                    if(text.equals("")){
                                        text = "0";
                                    }
                                    price_double = Double.parseDouble(text);

                                }
                                catch (Exception e1){
                                    price_double = 0;
                                    e1.printStackTrace();
                                }
                            }
                            e.printStackTrace();
                        }

                        double temp_price = 0;
                        try{
                            temp_price = price_double;
                            price_double = Double.parseDouble(df2.format(price_double));
                        }
                        catch (Exception e){
                            price_double = temp_price;
                            e.printStackTrace();
                        }


                        totalPrice_textView.setText(String.valueOf(price_double));
                        priceWithoutDiscount_edittext.setText(String.valueOf(price_double));
                        totalPrice_edittext .setText(String.valueOf(price_double));
                        priceWithoutDiscount_textview.setText(String.valueOf(price_double));


                        approxLiveWeight_EditText.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getApproxliveweight()));
                        parts_editText.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getPartsweight()));
                        meatyeild_edittext.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getMeatyieldweight()));
                        discount_edittext.setText(String.valueOf(discount_double));

                        meatyeild_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getMeatyieldweight()));
                        approxLiveWeight_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getApproxliveweight()));
                        parts_textView.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getPartsweight()));
                        discount_textView.setText(String.valueOf(discount_double));
                    }


                }
                catch (WindowManager.BadTokenException e) {


                    e.printStackTrace();
                }
            }
        });


    }







}