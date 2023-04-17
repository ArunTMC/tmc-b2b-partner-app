package com.tmc.tmcb2bpartnerapp.activity;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.tmc.tmcb2bpartnerapp.adapter.Adapter_GradeWiseTotal_BillingScreen;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BItemCtgy;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BOrderItemDetails_BulkUpdate;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagDetails_BulkUpdate;
import com.tmc.tmcb2bpartnerapp.fragment.DeliveryCentre_PlaceOrderScreen_Fragment;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BGoatGradeDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BItemCtgyInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderItemDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderItemDetails_BulkUpdateInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BRetailerDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagDetails_BulkUpdateInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetailsStatic;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetailsUpdate;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BItemCtgy;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_POJOClassForFinalSalesHashmap;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.ListItemSizeSetter;
import com.tmc.tmcb2bpartnerapp.utils.TMCAlertDialogClass;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;
import static com.itextpdf.text.BaseColor.GRAY;
import static com.itextpdf.text.BaseColor.LIGHT_GRAY;

public class PlacedOrderDetailsScreen extends BaseActivity {
    String batchno = "",orderid ="",invoiceno ="",callMethod ="",deliveryCenterKey="",deliveryCenterName ="",retailername="",retaileraddress ="",
    usermobileno_string ="",    paymentmode="",retailerkey="",discountAmount,retailers_address ="",retailers_mobileno ="",retailers_name="",gstin ="",
    retailermobileno ="" ,retailerGSTIN ="" , feedWeight  = "" , feedTotalPrice ="" , feedPricePerKg ="" , finalGoatValue_String ="" , finalgoatValueWithFeed_String =""
            ,finalgoatValueWithFeed_MinusDiscount_String ="" , finalBatchValue_String ="";
   double price = 0 , finalPayableprice =0 , finalGoatValue_double =0  , finalgoatValueWithFeed_Double =0 , finalgoatValueWithFeed_MinusDiscount_Double =0 , finalBatchValueDouble = 0;
   String finalWeight="0",calledFrom ="";
   String finalquantity ="0" , supervisorname ="" , tokenNo = "";
    boolean isB2BOrderItemDetailsTableServiceCalled = false;
    B2BOrderItemDetailsInterface callback_b2bOrderItemDetails;
    public static Adapter_GradeWiseTotal_BillingScreen adapter_gradeWiseTotal_billingScreen ;

    ArrayList<Modal_B2BOrderItemDetails> eartagitemDetailsArrayList = new ArrayList<>();
    public static ArrayList<String> earTagDetailsArrayList_String = new ArrayList<>();
    public static ArrayList<Modal_B2BGoatGradeDetails> selected_gradeDetailss_arrayList = new ArrayList<>();
    public static ArrayList<Modal_B2BOrderItemDetails> earTagDetailsArrayList_WholeBatch = new ArrayList<>();
    public static ArrayList<Modal_B2BGoatGradeDetails> goatGrade_arrayLsit = new ArrayList<>();
    public static ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList = new ArrayList<>();
    public static ArrayList<Modal_B2BItemCtgy> ctgy_subCtgy_DetailsArrayList = new ArrayList<>();
    public static ArrayList<String> batchNOList = new ArrayList<>();
  //  public static HashMap<String, Modal_POJOClassForFinalSalesHashmap> earTagDetails_JSONFinalSalesHashMap = new HashMap<>();
    public static HashMap<String, Modal_B2BCartItemDetails> earTagDetails_JSONFinalSalesHashMap = new HashMap<>();

    public static HashMap<String, JSONObject> earTagDetails_weightStringHashMap = new HashMap<>();
    public static HashMap<String,Modal_GoatEarTagDetails> earTagDetailsHashMap = new HashMap<>();


    B2BGoatGradeDetailsInterface callback_goatGradeDetailsInterface = null;
    boolean isGoatGradeDetailsServiceCalled = false;
    public ListView gradewisetotalCount_listview;
    PlacedOrderDetailsScreen placedOrderDetailsScreen;

    static double  weight_double = 0 ,totalWeight_double =0;
    static double pricePerKg_double = 0;
    static double discountAmount_double = 0 , gradeprice_double =0;
    static double totalPrice_double = 0;
    static double totalPrice_doubleWithoutDiscount = 0;
    static DecimalFormat threeDecimalConverter = new DecimalFormat(Constants.threeDecimalPattern);
    static  DecimalFormat twoDecimalConverter = new DecimalFormat(Constants.twoDecimalPattern);
    static LinearLayout back_IconLayout;
    public static TextView batchNo_textview,totalItem_CountTextview,totalWeight_textview,totalPrice_textview,paymentModeTextview,discount_textView,
            retailerName_Textview,payableAmount_textview,retailermobileno_Textview;
    Button printBill_Button,viewItemsIntheBill_Button,cancelBill_Button;

    private static int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;
    boolean isPDF_FIle = false;

    boolean  isRetailerDetailsServiceCalled = false ;
    B2BRetailerDetailsInterface callback_retailerDetailsInterface = null;
    boolean isB2BItemCtgyTableServiceCalled = false;
    B2BItemCtgyInterface callback_B2BItemCtgyInterface;

    boolean isGoatEarTagDetailsTableServiceCalled = false;
    GoatEarTagDetails_BulkUpdateInterface goatEarTagDetailsBulkUpdateInterface;

    B2BOrderDetailsInterface callback_b2BOrderDetailsInterface ;
    boolean isOrderDetailsServiceCalled = false;

    Modal_B2BOrderDetails modal_b2BOrderDetails = new Modal_B2BOrderDetails();


    boolean isOrderItemDetailsBulkUpdateServiceCalled = false;
    B2BOrderItemDetails_BulkUpdateInterface b2BOrderItemDetails_bulkUpdateInterface;


    boolean  isBatchDetailsTableServiceCalled = false;
    B2BBatchDetailsInterface callback_B2BBatchDetailsInterface = null;



    TextView retailerAddress_textview , feedWeight_textview , feedPrice_textview , feedPricePerKg_textview , maleCount_textview , femaleCount_textview , totalCount_textview ,
    malecountAvg_textview , femaleCountAvg_textview ,  approxliveWeightAvg_textview , meatyieldWeightAvg_textview , finalgoatValue_textview , final_feedValue_textview , finaltotalValue_textview  ,
    finalBatchValue_textview , finalpaymentValue_textview ,cancelledBill_textview;

    LinearLayout loadingpanelmask ,loadingPanel ,totalAmountLayout,totalDiscountLayout , totalfeedAmountLayout;

    CardView feed_CardView;

    int maleQuantityInt = 0 , femaleQuantityInt =0 ;
    double totalMeatYeild =0 ,totalaApproxLiveWeight =0;

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
                setContentView(R.layout.activity_placed_order_details_screen);
            } else {

                // Inflate the layout for this fragment
                setContentView(R.layout.pos_activity_placed_order_details_screen);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            setContentView(R.layout.activity_placed_order_details_screen);

        }
        modal_b2BOrderDetails = new Modal_B2BOrderDetails();
        Create_and_SharePdf(true);
        modal_b2BOrderDetails = (Modal_B2BOrderDetails) getIntent().getSerializableExtra("orderdetailsPojoClass");
        calledFrom = getIntent().getStringExtra("calledFrom");
        try {
            if (calledFrom == null) {
                calledFrom = "";
            }
        }
        catch (Exception e){
            calledFrom ="";
            e.printStackTrace();
        }
        tokenNo = String.valueOf(modal_b2BOrderDetails.getBillno());
        supervisorname = String.valueOf(modal_b2BOrderDetails.getSupervisorname());
        batchno = String.valueOf(modal_b2BOrderDetails.getBatchno());
        orderid = String.valueOf(modal_b2BOrderDetails.getOrderid());
        retailername = String.valueOf(modal_b2BOrderDetails.getRetailername());
        retailermobileno = String.valueOf(modal_b2BOrderDetails.getRetailermobileno());
        paymentmode = String.valueOf(modal_b2BOrderDetails.getPaymentmode());
        finalPayableprice = Double.parseDouble(String.valueOf(modal_b2BOrderDetails.getTotalPrice()));
        finalWeight = String.valueOf(modal_b2BOrderDetails.getTotalweight());
       // finalquantity = String.valueOf(modal_b2BOrderDetails.getTotalquantity());
        retaileraddress = String.valueOf(modal_b2BOrderDetails.getRetaileraddress());
        discountAmount = String.valueOf(modal_b2BOrderDetails.getDiscountamount());
        invoiceno = String.valueOf(modal_b2BOrderDetails.getInvoiceno());
        feedWeight = String.valueOf(modal_b2BOrderDetails.getFeedWeight());
        feedPricePerKg = String.valueOf(modal_b2BOrderDetails.getFeedPriceperkg());
        feedTotalPrice = String.valueOf(modal_b2BOrderDetails.getFeedPrice());
        if(feedWeight.equals("")){
            feedWeight ="0";
        }
        if(feedPricePerKg.equals("")){
            feedPricePerKg ="0";
        }
        if(feedTotalPrice.equals("")){
            feedTotalPrice ="0";
        }
        if(discountAmount.equals("")){
            discountAmount ="0";
        }

      /*  orderid = getIntent().getStringExtra("orderid");
        retailername = getIntent().getStringExtra("retailername");
        retailermobileno = getIntent().getStringExtra("retailermobileno");
        paymentmode = getIntent().getStringExtra("paymentmode");
        price = getIntent().getDoubleExtra("price",0);
        finalPayableprice = getIntent().getDoubleExtra("totalprice",0);
        finalWeight = getIntent().getStringExtra("totalweight");
        finalquantity = getIntent().getStringExtra("quantity");
        retaileraddress = getIntent().getStringExtra("retaileraddress");
        discountAmount = getIntent().getStringExtra("discountamount");
        invoiceno =  getIntent().getStringExtra("invoiceno");


       */



        SharedPreferences sh1 =  getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);

        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");


        SharedPreferences sh = getSharedPreferences("LoginData",MODE_PRIVATE);
        usermobileno_string = sh.getString("UserMobileNumber","");

        
        try{
            placedOrderDetailsScreen = this;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{
            loadingpanelmask  = findViewById(R.id.loadingpanelmask);
            loadingPanel  = findViewById(R.id.loadingPanel);
            gradewisetotalCount_listview = findViewById(R.id.gradewisetotalCount_listview);
            batchNo_textview = findViewById(R.id.batchNo_textview);
            totalItem_CountTextview =  findViewById(R.id.totalItem_CountTextview);
            totalWeight_textview = findViewById(R.id.totalWeight_textview);
            totalPrice_textview = findViewById(R.id.totalPrice_textview);
            retailerName_Textview = findViewById(R.id.retailerName_Textview);
            discount_textView = findViewById(R.id.discountValue_textView);
            paymentModeTextview = findViewById(R.id.paymentModeTextview);
            cancelBill_Button = findViewById(R.id.cancelBill_Button);
            viewItemsIntheBill_Button = findViewById(R.id.viewItemsIntheBill_Button);
            printBill_Button = findViewById(R.id.printBill_Button);
            back_IconLayout = findViewById(R.id.back_IconLayout);
            payableAmount_textview = findViewById(R.id.payableAmount_textview);
            retailermobileno_Textview  = findViewById(R.id.retailermobileno_Textview);
            cancelledBill_textview  = findViewById(R.id.cancelledBill_textview);
            feed_CardView = findViewById(R.id.feed_CardView);
            totalAmountLayout = findViewById(R.id.totalAmountLayout);
            totalDiscountLayout = findViewById(R.id.totalDiscountLayout);
            totalfeedAmountLayout = findViewById(R.id.totalfeedAmountLayout);

            retailerAddress_textview  = findViewById(R.id.retailerAddress_Textview);
            feedWeight_textview  = findViewById(R.id.feedWeight_textview);
            feedPrice_textview = findViewById(R.id.totalFeedPriceTextview);
            feedPricePerKg_textview = findViewById(R.id.feedPricePerKg_textview);
            maleCount_textview =  findViewById(R.id.male_Qty_textview);
            femaleCount_textview = findViewById(R.id.female_Qty_textview);
            totalCount_textview = findViewById(R.id.totalGoats_textView);
            malecountAvg_textview = findViewById(R.id.male_percentage_textview);
            femaleCountAvg_textview = findViewById(R.id.female_Percentage_textview);
            approxliveWeightAvg_textview = findViewById(R.id.approxLiveWeightAvg_Textview);
            meatyieldWeightAvg_textview = findViewById(R.id.meatYieldWeightAvg_Textview);
            finalgoatValue_textview = findViewById(R.id.finalGoatValue_textView);
            final_feedValue_textview = findViewById(R.id.finalFeedValue_textView);
            finaltotalValue_textview = findViewById(R.id.totalValue_textView);
            finalBatchValue_textview = findViewById(R.id.batchValue_textView);
            finalpaymentValue_textview  = findViewById(R.id.finalPayment_textView);






            //viewItemsIntheBill_Button.setVisibility(View.GONE);
           // cancelBill_Button.setVisibility(View.GONE);
        }
        catch (Exception e){
            e.printStackTrace();
        }




        try{
            batchNOList.clear();
            eartagitemDetailsArrayList.clear();
            earTagDetailsArrayList_String.clear();
            selected_gradeDetailss_arrayList.clear();
            earTagDetailsArrayList_WholeBatch.clear();
            goatGrade_arrayLsit.clear();
            retailerDetailsArrayList.clear();
            ctgy_subCtgy_DetailsArrayList.clear();
            earTagDetails_JSONFinalSalesHashMap.clear();
            earTagDetails_weightStringHashMap.clear();
            earTagDetailsHashMap.clear();
        }
        catch (Exception e){
            e.printStackTrace();
        }



        try{


            discount_textView.setText(discountAmount);
            paymentModeTextview.setText(paymentmode);
            retailerName_Textview.setText(retailername);
            retailermobileno_Textview .setText(retailermobileno);
            totalItem_CountTextview.setText(String.valueOf(finalquantity));
            totalPrice_textview.setText(String.valueOf(twoDecimalConverter.format(price)));
            totalWeight_textview.setText(String.valueOf((finalWeight)));
            payableAmount_textview.setText(String.valueOf(twoDecimalConverter.format(finalPayableprice)));
            retailerAddress_textview.setText(String.valueOf(retaileraddress));
            feedWeight_textview.setText(String.valueOf(feedWeight));
            feedPrice_textview.setText(String.valueOf(feedTotalPrice));
            feedPricePerKg_textview.setText(String.valueOf(feedPricePerKg));
        }
        catch (Exception e){
            e.printStackTrace();
        }


     /*   if(DatabaseArrayList_PojoClass.breedType_arrayList.size() == 0){
            try {
                Initialize_and_ExecuteB2BCtgyItem();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            ctgy_subCtgy_DetailsArrayList = DatabaseArrayList_PojoClass.breedType_arrayList;
        }



      */

    /*    if(DatabaseArrayList_PojoClass.retailerDetailsArrayList.size() == 0){
            try {
                call_and_init_B2BRetailerDetailsService();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            retailerDetailsArrayList = DatabaseArrayList_PojoClass.retailerDetailsArrayList;
            try {
                for (int iterator = 0; iterator < retailerDetailsArrayList.size(); iterator++) {
                    Modal_B2BRetailerDetails modal_b2BRetailerDetails = retailerDetailsArrayList.get(iterator);

                    if (modal_b2BRetailerDetails.getRetailerkey().toUpperCase().equals(retailerkey.toUpperCase())) {
                        retailername = modal_b2BRetailerDetails.getRetailername();
                        retaileraddress = modal_b2BRetailerDetails.getAddress();
                        retailermobileno = modal_b2BRetailerDetails.getMobileno();
                        retailerGSTIN = modal_b2BRetailerDetails.getGstin();
                        retailerName_Textview.setText(retailername);
                    }

                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }



     */





     /*   try {

            if (DatabaseArrayList_PojoClass.goatGradeDetailsArrayList.size() == 0) {
                try {
                    Call_and_Initialize_GoatGradeDetails(Constants.CallGETListMethod);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                goatGrade_arrayLsit = DatabaseArrayList_PojoClass.getGoatGradeDetailsArrayList();
                callMethod = Constants.CallGETListMethod;

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

      */




        if(calledFrom.equals("consolidatedSalesReport")){
            printBill_Button.setVisibility(View.GONE );
            cancelBill_Button.setVisibility(View.GONE);
            viewItemsIntheBill_Button.setVisibility(View.VISIBLE);
            viewItemsIntheBill_Button.setText("View Ear Tag's List");
            feed_CardView.setVisibility(View.GONE);
            totalfeedAmountLayout.setVisibility(View.GONE);
            totalAmountLayout.setVisibility(View.GONE);
            totalDiscountLayout.setVisibility(View.GONE);

        }
        else{
            printBill_Button.setVisibility(View.VISIBLE);
            cancelBill_Button.setVisibility(View.VISIBLE);
            viewItemsIntheBill_Button.setVisibility(View.VISIBLE);
            viewItemsIntheBill_Button.setText("View Items in the Bill");
            feed_CardView.setVisibility(View.VISIBLE);
            totalfeedAmountLayout.setVisibility(View.VISIBLE);
            totalAmountLayout.setVisibility(View.VISIBLE);
            totalDiscountLayout.setVisibility(View.VISIBLE);
        }

        callMethod = Constants.CallGETListMethod;
        FetchDataFromOrderItemDetails(orderid);



        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        printBill_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressBar(true);
                //Create_and_SharePdf_old();
                Create_and_SharePdf(false);

            }
        });


        cancelBill_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressBar(true);
                new TMCAlertDialogClass(PlacedOrderDetailsScreen.this, R.string.app_name, R.string.Cancel_bill_Instruction,
                        R.string.OK_Text, R.string.Cancel_Text,
                        new TMCAlertDialogClass.AlertListener() {
                            @Override
                            public void onYes() {

                                call_and_init_B2BOrderDetailsService(Constants.CallUPDATEMethod);




                            }

                            @Override
                            public void onNo() {
                                showProgressBar(false);
                            }
                        });


            }
        });


        viewItemsIntheBill_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PlacedOrderDetailsScreen.this, GoatEarTagItemDetailsList.class);
                i.putExtra("TaskToDo", "PlacedOrderItemDetails");
                i.putExtra("batchno", batchno);
                i.putExtra("orderid", orderid);
                i.putExtra("doeshavetocallitbatchwise",true);

                i.putExtra("CalledFrom", getString(R.string.datewise_placedOrder_Details_Screen_SecondVersion));


                // i.putExtra("CalledFrom", getString(R.string.placedOrder_Details_Screen));
                startActivity(i);
            }
        });



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


    private void call_and_init_B2BRetailerDetailsService() {

        showProgressBar(true);
        if (isRetailerDetailsServiceCalled) {
            showProgressBar(false);
            return;
        }
        isRetailerDetailsServiceCalled = true;
        callback_retailerDetailsInterface = new B2BRetailerDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayListt) {


            }

            @Override
            public void notifySuccess(String result) {



                if(result.equals(Constants.item_Already_Added_volley)){
                    AlertDialogClass.showDialog(getParent(), R.string.retailersAlreadyCreated_Instruction);
                    isRetailerDetailsServiceCalled = false;
                    showProgressBar(false);

                }
                else if(result.equals(Constants.successResult_volley)){
                    DeliveryCentre_PlaceOrderScreen_Fragment.retailerDetailsArrayList = DatabaseArrayList_PojoClass.retailerDetailsArrayList;
                    try {
                        for (int iterator = 0; iterator < retailerDetailsArrayList.size(); iterator++) {
                            Modal_B2BRetailerDetails modal_b2BRetailerDetails = retailerDetailsArrayList.get(iterator);

                            if (modal_b2BRetailerDetails.getRetailerkey().toUpperCase().equals(retailerkey.toUpperCase())) {
                                retailername = modal_b2BRetailerDetails.getRetailername();
                                retaileraddress = modal_b2BRetailerDetails.getAddress();
                                retailermobileno = modal_b2BRetailerDetails.getMobileno();
                                retailerGSTIN = modal_b2BRetailerDetails.getGstin();
                                retailerName_Textview.setText(retailername);
                            }

                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    //  BillingScreen.retailerDetailsArrayList = DatabaseArrayList_PojoClass.retailerDetailsArrayList;
                    isRetailerDetailsServiceCalled = false;
                    showProgressBar(false);
                    // ((BillingScreen)getActivity()).closeFragment();
                    DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.closeFragment();

                }
                else{
                    isRetailerDetailsServiceCalled = false;
                    showProgressBar(false);
                }



            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isRetailerDetailsServiceCalled = false;
                showProgressBar(false);
            }

            @Override
            public void notifyProcessingError(Exception error) {
                isRetailerDetailsServiceCalled = false;
                showProgressBar(false);
            }


        };

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

    private void FetchDataFromOrderItemDetails(String orderid) {
    try {
        showProgressBar(true);
        if (isB2BOrderItemDetailsTableServiceCalled) {
            // showProgressBar(false);
            return;
        }
        eartagitemDetailsArrayList.clear();
        isB2BOrderItemDetailsTableServiceCalled = true;
        callback_b2bOrderItemDetails = new B2BOrderItemDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BOrderItemDetails> arrayList) {
                earTagDetails_JSONFinalSalesHashMap = new HashMap<>();
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

                        
                    /*
                      earTagDetailsHashMap.put(modal_b2BOrderItemDetails.getBarcodeno(),modal_goatEarTagDetails);
                    earTagDetailsArrayList_String.add(modal_b2BOrderItemDetails.getBarcodeno());
                    String ctgykey = "", ctgyname = "", subctgykey = "", suctgyname = "";
                    //gradeWise_count_weightJSONOBJECT = new JSONObject();

                    if (earTagDetailsArrayList_String.contains(modal_b2BOrderItemDetails.getBarcodeno())) {

                        calculateGradewiseQuantity_and_Weight(modal_b2BOrderItemDetails);
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("weight", modal_b2BOrderItemDetails.getWeightingrams());
                            jsonObject.put("gradekey", modal_b2BOrderItemDetails.getGradekey());
                            jsonObject.put("gradeprice", modal_b2BOrderItemDetails.getGradeprice());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (earTagDetails_weightStringHashMap.containsKey(modal_b2BOrderItemDetails.getBarcodeno())) {
                            if (SDK_INT >= Build.VERSION_CODES.N) {
                                Objects.requireNonNull(earTagDetails_weightStringHashMap.replace(modal_b2BOrderItemDetails.getBarcodeno(), jsonObject));
                            } else {
                                Objects.requireNonNull(earTagDetails_weightStringHashMap.put(modal_b2BOrderItemDetails.getBarcodeno(), jsonObject));
                            }
                            calculateTotalweight_Quantity_Price();
                            // adapter_billingScreen_cartList.notifyDataSetChanged();

                        }
                        else {
                          
                            earTagDetails_weightStringHashMap.put(modal_b2BOrderItemDetails.getBarcodeno(), jsonObject);
                             earTagDetailsHashMap.put(modal_b2BOrderItemDetails.getBarcodeno(),modal_goatEarTagDetails);
                            //adapter_billingScreen_cartList.notifyDataSetChanged();
                            calculateTotalweight_Quantity_Price();

                        }
                    }
                    else {
                        calculateGradewiseQuantity_and_Weight(modal_b2BOrderItemDetails);
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("weight", modal_b2BOrderItemDetails.getWeightingrams());
                            jsonObject.put("gradekey", modal_b2BOrderItemDetails.getGradekey());
                            jsonObject.put("gradeprice", modal_b2BOrderItemDetails.getGradeprice());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (earTagDetails_weightStringHashMap.containsKey(modal_b2BOrderItemDetails.getBarcodeno())) {
                            if (SDK_INT >= Build.VERSION_CODES.N) {
                                Objects.requireNonNull(earTagDetails_weightStringHashMap.replace(modal_b2BOrderItemDetails.getBarcodeno(), jsonObject));
                            } else {
                                Objects.requireNonNull(earTagDetails_weightStringHashMap.put(modal_b2BOrderItemDetails.getBarcodeno(), jsonObject));
                            }
                            earTagDetailsArrayList_String.add(modal_b2BOrderItemDetails.getBarcodeno());
                            //  adapter_billingScreen_cartList.notifyDataSetChanged();
                            calculateTotalweight_Quantity_Price();
                        } else {
                            
                            earTagDetailsArrayList_String.add(modal_b2BOrderItemDetails.getBarcodeno());
                            earTagDetails_weightStringHashMap.put(modal_b2BOrderItemDetails.getBarcodeno(), jsonObject);

                              earTagDetailsHashMap.put(modal_b2BOrderItemDetails.getBarcodeno(),modal_goatEarTagDetails);
                            // adapter_billingScreen_cartList.notifyDataSetChanged();
                            calculateTotalweight_Quantity_Price();

                        }
                    }


                    if (iterator == (arrayList.size() - 1)) {

                        calculateTotalweight_Quantity_Price();
                        setAdapterForGradewiseTotal();
                    }
                    */
                }
                isB2BOrderItemDetailsTableServiceCalled = false;
                if(arrayList.size()==0) {
                    Toast.makeText(PlacedOrderDetailsScreen.this, "There is no eartag item for in this orderid with"+batchno+" as batchno", Toast.LENGTH_SHORT).show();
                    showProgressBar(false);
                }
            }

            @Override
            public void notifySuccess(String result) {
                isGoatGradeDetailsServiceCalled = false;
                showProgressBar(false);
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isB2BOrderItemDetailsTableServiceCalled = false;showProgressBar(false);
              Log.i("INIT", "FetchDataFromOrderItemDetails:  " + String.valueOf(error));

            }

            @Override
            public void notifyProcessingError(Exception error) {
                showProgressBar(false);
                Log.i("INIT", "FetchDataFromOrderItemDetails:  " + String.valueOf(error));
                isB2BOrderItemDetailsTableServiceCalled = false;
            }
        };
        if(calledFrom.equals("consolidatedSalesReport")){
            if (callMethod.equals(Constants.CallGETListMethod)) {
                //String getApiToCall = API_Manager.getOrderDetailsForBatchno+"?batchno="+batchno ;
                String getApiToCall = API_Manager.getOrderItemDetailsForOrderidWithBatchnoWithoutCancelledStatus +"?orderid=" + orderid+"&batchno="+batchno;

                B2BOrderItemDetails asyncTask = new B2BOrderItemDetails(callback_b2bOrderItemDetails, getApiToCall, callMethod);
                asyncTask.execute();

            }
        }
        else{
            if (callMethod.equals(Constants.CallGETListMethod)) {
                //String getApiToCall = API_Manager.getOrderDetailsForBatchno+"?batchno="+batchno ;
                String getApiToCall = API_Manager.getOrderItemDetailsForOrderid + orderid;

                B2BOrderItemDetails asyncTask = new B2BOrderItemDetails(callback_b2bOrderItemDetails, getApiToCall, callMethod);
                asyncTask.execute();

            }
        }


    }
    catch (Exception e){
        e.printStackTrace();
    }

    }

    private void ProcessDataAndAddItIntoArray(Modal_GoatEarTagDetails modal_goatEarTagDetails, int iterator, int size) {

        if (!earTagDetails_JSONFinalSalesHashMap.containsKey(modal_goatEarTagDetails.getBarcodeno()))
        {
            Modal_B2BCartItemDetails modal_b2BCartItemDetails = new Modal_B2BCartItemDetails();
            modal_b2BCartItemDetails.barcodeno = modal_goatEarTagDetails.getBarcodeno();
            modal_b2BCartItemDetails.batchno = modal_goatEarTagDetails.getBatchno();
            modal_b2BCartItemDetails.status = modal_goatEarTagDetails.getStatus();
            modal_b2BCartItemDetails.suppliername = modal_goatEarTagDetails.getSuppliername();
            modal_b2BCartItemDetails.supplierkey = modal_goatEarTagDetails.getSupplierkey();
            modal_b2BCartItemDetails.itemaddeddate = modal_goatEarTagDetails.getItemaddeddate();
            modal_b2BCartItemDetails.gender = modal_goatEarTagDetails.getGender();
            modal_b2BCartItemDetails.breedtype = modal_goatEarTagDetails.getBreedtype();
            modal_b2BCartItemDetails.gradeprice = modal_goatEarTagDetails.getGradeprice();
            modal_b2BCartItemDetails.gradename = modal_goatEarTagDetails.getGradename();
            modal_b2BCartItemDetails.gradekey = modal_goatEarTagDetails.getGradekey();
            modal_b2BCartItemDetails.approxliveweight = modal_goatEarTagDetails.getApproxliveweight();
            modal_b2BCartItemDetails.meatyieldweight = modal_goatEarTagDetails.getMeatyieldweight();
            modal_b2BCartItemDetails.partsweight = modal_goatEarTagDetails.getPartsweight();
            modal_b2BCartItemDetails.totalPrice_ofItem = modal_goatEarTagDetails.getTotalPrice_ofItem();
            modal_b2BCartItemDetails.discount = modal_goatEarTagDetails.getDiscount();


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
            modal_b2BCartItemDetails.setTotalItemWeight(String.valueOf(threeDecimalConverter.format(totalWeight_double)));


            earTagDetailsHashMap.put(modal_goatEarTagDetails.getBarcodeno(),modal_goatEarTagDetails);
            earTagDetailsArrayList_String.add(modal_goatEarTagDetails.getBarcodeno());
            earTagDetails_JSONFinalSalesHashMap.put(modal_goatEarTagDetails.getBarcodeno(),modal_b2BCartItemDetails);


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
                finalquantity = String.valueOf(earTagDetailsArrayList_String.size());
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





            modal_b2BCartItemDetails.setTotalItemWeight(String.valueOf(threeDecimalConverter.format(totalWeight_double)));

            try{
                totalPrice_textview.setText(String.valueOf(finalgoatValueWithFeed_String));
            }
            catch (Exception e){
                e.printStackTrace();
            }



            try{
                if(String.valueOf(Objects.requireNonNull(modal_goatEarTagDetails).getGender()).toUpperCase().equals("MALE")){
                    maleQuantityInt = maleQuantityInt +1 ;
                }
                else  if(String.valueOf(Objects.requireNonNull(modal_goatEarTagDetails).getGender()).toUpperCase().equals("FEMALE")){
                    femaleQuantityInt = femaleQuantityInt +1 ;

                }
            }
            catch (Exception e){
                e.printStackTrace();
            }


            try{
                maleQtyAvg  = ((maleQuantityInt/finalquantitydouble) *100);

            }
            catch (Exception e){
                e.printStackTrace();
            }



            try{
                femaleQtyAvg  = ((femaleQuantityInt/finalquantitydouble) *100);

            }
            catch (Exception e){
                e.printStackTrace();
            }



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



            //maleCount_textview.setText(String.valueOf(maleQuantityInt));
            //femaleCount_textview.setText(String.valueOf(femaleQuantityInt));
            totalCount_textview.setText(String.valueOf(finalquantity));
            finaltotalValue_textview.setText(String.valueOf(finalgoatValueWithFeed_String));
         //   femaleCountAvg_textview.setText(String.valueOf(threeDecimalConverter.format(femaleQtyAvg)));
           // malecountAvg_textview.setText(String.valueOf(threeDecimalConverter.format(maleQtyAvg)));
            maleCount_textview.setText(" "+String.valueOf((int) maleQuantityInt)+" nos ");
            femaleCount_textview.setText(" "+String.valueOf((int) femaleQuantityInt)+" nos ");
            malecountAvg_textview.setText(("("+String.valueOf(threeDecimalConverter.format(maleQtyAvg))+"%)"));
            femaleCountAvg_textview.setText("("+String.valueOf(threeDecimalConverter.format(femaleQtyAvg))+"%)" );

            finalgoatValue_textview.setText(String.valueOf(finalGoatValue_String));
            final_feedValue_textview.setText(String.valueOf(feedTotalPrice));
            finalBatchValue_textview.setText(String.valueOf(finalBatchValue_String));
            finalpaymentValue_textview.setText(String.valueOf(finalgoatValueWithFeed_MinusDiscount_String));


            meatyieldWeightAvg_textview.setText(String.valueOf(threeDecimalConverter.format(meatYieldAvg)));
            approxliveWeightAvg_textview.setText(String.valueOf(threeDecimalConverter.format(approxLiveWeightAvg)));


                try{
                    earTagDetailsArrayList_String = sortThisArrayUsingBarcode(earTagDetailsArrayList_String);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            //    calculateTotalweight_Quantity_Price();

        }
        else{
           // Toast.makeText(PlacedOrderDetailsScreen.this, "Please Scan the New Item", Toast.LENGTH_SHORT).show();

        }

        showProgressBar(false);


        
        
        
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



    private void call_and_init_B2BOrderDetailsService(String callMethod) {

        if (isOrderDetailsServiceCalled) {
            // showProgressBar(false);
            return;
        }
        isOrderDetailsServiceCalled = true;
        callback_b2BOrderDetailsInterface = new B2BOrderDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BOrderDetails> retailerDetailsArrayListt) {
                isOrderDetailsServiceCalled = false;

                showProgressBar(false);

            }

            @Override
            public void notifySuccess(String result) {

                isOrderDetailsServiceCalled = false;

                call_and_init_GoatEarTagDetails_BulkUpdate(Constants.CallUPDATEMethod);
                call_and_init_OrderItemDetails_BulkUpdate(Constants.CallUPDATEMethod);
                call_and_init_BatchItemDetails_BulkUpdate(Constants.CallUPDATEMethod);



                
                //((BillingScreen)getActivity()).closeFragment();
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isOrderDetailsServiceCalled = false;
                //    //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 3 " + DateParser.getDate_and_time_newFormat());
                showProgressBar(false);

            }

            @Override
            public void notifyProcessingError(Exception error) {
                isOrderDetailsServiceCalled = false;
                ////Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());
                showProgressBar(false);

            }


        };



        Modal_B2BOrderDetails.setDeliverycentrekey_Static(String.valueOf(deliveryCenterKey));
        Modal_B2BOrderDetails.setStatus_Static(String.valueOf(Constants.batchDetailsStatus_Cancelled));
        Modal_B2BOrderDetails.setOrderid_Static(String.valueOf(orderid));
        Modal_B2BOrderDetails.setOrdercanceledtime_Static(String.valueOf(DateParser.getDate_and_time_newFormat()));
        String getApiToCall = API_Manager.updateOrderDetails ;

        B2BOrderDetails asyncTask = new B2BOrderDetails(callback_b2BOrderDetailsInterface,  getApiToCall, callMethod);
        asyncTask.execute();







    }


    private void call_and_init_BatchItemDetails_BulkUpdate(String callMethod) {


        showProgressBar(true);
        if (isBatchDetailsTableServiceCalled) {
            //  showProgressBar(false);
            return;
        }
        isBatchDetailsTableServiceCalled = true;

        for(int i =0 ; i<earTagDetailsArrayList_String.size() ; i++){
            String barcodeNo = earTagDetailsArrayList_String.get(i);

            Modal_GoatEarTagDetails  modal_b2BCartItemDetails  = earTagDetailsHashMap.get(barcodeNo);
            if(!batchNOList.contains(modal_b2BCartItemDetails.getBatchno())){
                batchNOList.add(modal_b2BCartItemDetails.getBatchno());




                    callback_B2BBatchDetailsInterface = new B2BBatchDetailsInterface() {


                        @Override
                        public void notifySuccessForGettingListItem(ArrayList<Modal_B2BBatchDetails> batchDetailsArrayListt) {


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
                    modal_b2BBatchDetailsUpdate.setStatus(Constants.batchDetailsStatus_Reviewed_and_READYFORSALE);
                    modal_b2BBatchDetailsUpdate.setDeliverycenterkey(deliveryCenterKey);



                    String addApiToCall = API_Manager.updateBatchDetailsWithSupplierkeyBatchNo;

                    B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface, addApiToCall, Constants.CallUPDATEMethod);
                    asyncTask.execute();




            }

        }




    }


    private void call_and_init_OrderItemDetails_BulkUpdate(String callUPDATEMethod) {
        try {

            if (isOrderItemDetailsBulkUpdateServiceCalled) {

                return;
            }
            isOrderItemDetailsBulkUpdateServiceCalled = true;
            b2BOrderItemDetails_bulkUpdateInterface = new B2BOrderItemDetails_BulkUpdateInterface() {


                @Override
                public void notifySuccess(String result) {
                    isOrderItemDetailsBulkUpdateServiceCalled = false;
                    showProgressBar(false);

                    try{
                            for(int i = 0 ; i<DatewisePlacedOrdersList.orderDetailsArrayList.size(); i++){
                                if(DatewisePlacedOrdersList.orderDetailsArrayList.get(i).orderid.toString().equals(orderid)){
                                    DatewisePlacedOrdersList.orderDetailsArrayList.remove(i);
                                    DatewisePlacedOrdersList.datewisePlacedOrdersList.callAdapter(DatewisePlacedOrdersList.orderDetailsArrayList);
                                }
                                cancelBill_Button.setVisibility(View.GONE);
                                cancelledBill_textview.setVisibility(View.VISIBLE);
                            }

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void notifySuccessForGettingListItem(ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch) {
                    isOrderItemDetailsBulkUpdateServiceCalled = false;
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


            Modal_B2BOrderItemDetails modal_b2BOrderItemDetails = new Modal_B2BOrderItemDetails();
            modal_b2BOrderItemDetails.setDeliverycentrekey_static(String.valueOf(deliveryCenterKey));
            modal_b2BOrderItemDetails.setStatus(String.valueOf(Constants.batchDetailsStatus_Cancelled));
            modal_b2BOrderItemDetails.setOrderid_static(String.valueOf(orderid));
            modal_b2BOrderItemDetails.setEarTagDetailsArrayList_String(earTagDetailsArrayList_String);
            modal_b2BOrderItemDetails.setEarTagDetailsHashMap(earTagDetailsHashMap);
            // modal_b2BOrderItemDetails.setEarTagDetails_weightStringHashMap(earTagDetails_JSONFinalSalesHashMap);
            String orderplaceddate = DateParser.getDate_and_time_newFormat();




            try {

                B2BOrderItemDetails_BulkUpdate asyncTask = new B2BOrderItemDetails_BulkUpdate(b2BOrderItemDetails_bulkUpdateInterface,  Constants.CallUPDATEMethod, orderid , Constants.goatEarTagStatus_Cancelled , earTagDetailsArrayList_String);
                asyncTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }



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
                    showProgressBar(false);
                }

                @Override
                public void notifySuccessForGettingListItem(ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch) {
                    isGoatEarTagDetailsTableServiceCalled = false;
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


            Modal_B2BOrderItemDetails modal_b2BOrderItemDetails = new Modal_B2BOrderItemDetails();

            modal_b2BOrderItemDetails.setDeliverycentrekey_static(String.valueOf(deliveryCenterKey));
            modal_b2BOrderItemDetails.setStatus(String.valueOf(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE));
            modal_b2BOrderItemDetails.setOrderid_static(String.valueOf(orderid));
            modal_b2BOrderItemDetails.setEarTagDetailsArrayList_String(earTagDetailsArrayList_String);
            modal_b2BOrderItemDetails.setEarTagDetailsHashMap(earTagDetailsHashMap);
          // modal_b2BOrderItemDetails.setEarTagDetails_weightStringHashMap(earTagDetails_JSONFinalSalesHashMap);
            String orderplaceddate = DateParser.getDate_and_time_newFormat();
            try {

                String addApiToCall = API_Manager.updateGoatEarTag;
                GoatEarTagDetails_BulkUpdate asyncTask = new GoatEarTagDetails_BulkUpdate(goatEarTagDetailsBulkUpdateInterface, addApiToCall, callMethod, modal_b2BOrderItemDetails, orderplaceddate, usermobileno_string);
                asyncTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }




    }





    public  void setAdapterForGradewiseTotal() {
        try {
         //   adapter_gradeWiseTotal_billingScreen = new Adapter_GradeWiseTotal_BillingScreen(PlacedOrderDetailsScreen.this, selected_gradeDetailss_arrayList, earTagDetails_JSONFinalSalesHashMap, PlacedOrderDetailsScreen.this);
          //  gradewisetotalCount_listview.setAdapter(adapter_gradeWiseTotal_billingScreen);

           // ListItemSizeSetter.getListViewSize(gradewisetotalCount_listview);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public void calculateGradewiseQuantity_and_Weight(Modal_B2BOrderItemDetails modal_b2BOrderItemDetails){

        try {
            for (int iterator = 0; iterator < goatGrade_arrayLsit.size(); iterator++) {
                Modal_B2BGoatGradeDetails modal_b2BGoatGradeDetails = goatGrade_arrayLsit.get(iterator);

                String maleQty = "0", femaleQty = "0", maleWeight = "0", femaleWeight = "0", totalWeight = "0", malePrice = "0", femalePrice = "0", toalPrice = "";
                double maleWeightdouble = 0, femaleWeightdouble = 0, totalWeightdouble = 0, malePricedouble = 0,
                        femalePricedouble = 0, toalPricedouble = 0, oldWeight_inGramsdouble = 0, oldPrice_inGramsdouble = 0;
                int femaleCount = 0, maleCount = 0, totalCount = 0;
                if (modal_b2BGoatGradeDetails.getKey().equals(modal_b2BOrderItemDetails.getGradekey())) {
                    String weightString = "0";
                    double weightDouble = 0;
                    double price_for_this_entry = 0;
                    weightString = modal_b2BOrderItemDetails.getWeightingrams();

                    try {
                        weightString = weightString.replaceAll("[^\\d.]", "");
                        if (weightString.equals("") || weightString.toString().toUpperCase().equals("NULL")) {
                            weightString = "0";
                            Toast.makeText(placedOrderDetailsScreen, "Error from recalculateGradewise weightString 0: ", Toast.LENGTH_SHORT).show();

                        }
                        weightDouble = Double.parseDouble(weightString);
                    } catch (Exception e) {
                        if(placedOrderDetailsScreen != null) {
                            Toast.makeText(placedOrderDetailsScreen, "Error from recalculateGradewise item 1: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
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
                        if(placedOrderDetailsScreen != null) {
                            Toast.makeText(placedOrderDetailsScreen, "Error from recalculateGradewise item 2: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
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
                        if (earTagDetails_JSONFinalSalesHashMap.containsKey(modal_b2BOrderItemDetails.getGradekey())) {
                           // Modal_POJOClassForFinalSalesHashmap modal_pojoClassForFinalSalesHashmap = earTagDetails_JSONFinalSalesHashMap.get(modal_b2BOrderItemDetails.getGradekey());
                            Modal_POJOClassForFinalSalesHashmap modal_pojoClassForFinalSalesHashmap =new Modal_POJOClassForFinalSalesHashmap() ;
                            double weight = 0, price = 0, maleweight = 0, femaleweight = 0, maleprice = 0, femaleprice = 0;
                            int maleQtyy = 0, femaleQtyy = 0, quantity = 0;


                            weight = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalweight();
                            weight = weight + weightDouble;

                            quantity = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalqty();
                            quantity = quantity + 1;

                            price = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalprice();
                            price = toalPricedouble + price;

                            modal_b2BOrderItemDetails.setTotalPrice(String.valueOf(price));
                            modal_pojoClassForFinalSalesHashmap.setTotalprice(price);
                            modal_pojoClassForFinalSalesHashmap.setTotalqty(quantity);
                            modal_pojoClassForFinalSalesHashmap.setTotalweight(weight);


                            if (modal_b2BOrderItemDetails.getGender().toUpperCase().equals("MALE")) {
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
                            if (modal_b2BOrderItemDetails.getGender().toUpperCase().equals("FEMALE")) {
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
                          ///  earTagDetails_JSONFinalSalesHashMap.put(modal_b2BOrderItemDetails.getGradekey(), modal_pojoClassForFinalSalesHashmap);


                        } else {

                            Modal_POJOClassForFinalSalesHashmap modal_pojoClassForFinalSalesHashmap = new Modal_POJOClassForFinalSalesHashmap();

                            modal_b2BOrderItemDetails.setTotalPrice(String.valueOf(toalPricedouble));

                            modal_pojoClassForFinalSalesHashmap.setTotalprice(toalPricedouble);
                            modal_pojoClassForFinalSalesHashmap.setTotalqty(1);
                            modal_pojoClassForFinalSalesHashmap.setTotalweight(weightDouble);


                            if (modal_b2BOrderItemDetails.getGender().toUpperCase().equals("MALE")) {


                                modal_pojoClassForFinalSalesHashmap.setTotalmaleweight(weightDouble);
                                modal_pojoClassForFinalSalesHashmap.setMaleqty(1);
                                modal_pojoClassForFinalSalesHashmap.setMaleprice(toalPricedouble);


                            }
                            if (modal_b2BOrderItemDetails.getGender().toUpperCase().equals("FEMALE")) {

                                modal_pojoClassForFinalSalesHashmap.setTotalfemaleweight(weightDouble);
                                modal_pojoClassForFinalSalesHashmap.setFemaleqty(1);
                                modal_pojoClassForFinalSalesHashmap.setFemaleprice(toalPricedouble);


                            }
                          //  earTagDetails_JSONFinalSalesHashMap.put(modal_b2BOrderItemDetails.getGradekey(), modal_pojoClassForFinalSalesHashmap);
                            selected_gradeDetailss_arrayList.add(modal_b2BGoatGradeDetails);


                        }
                    }
                    catch (Exception e){
                        if(placedOrderDetailsScreen != null) {
                            Toast.makeText(placedOrderDetailsScreen, "Error from recalculateGradewise item 3: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                        }
                        e.printStackTrace();
                    }
             /*   if (gradeWise_count_weightJSONOBJECT.has(modal_b2BOrderItemDetails.gradekey)) {

                    try {
                        JSONObject jsonObject = gradeWise_count_weightJSONOBJECT.getJSONObject(modal_b2BOrderItemDetails.gradekey);
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


                        if (modal_b2BOrderItemDetails.getGender().equals("MALE")) {

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
                        } else if (modal_b2BOrderItemDetails.getGender().equals("FEMALE")) {
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
                        if (modal_b2BOrderItemDetails.getGender().equals("MALE")) {
                            jsonObject.put("maleQty", 1);
                            jsonObject.put("maleWeight", threeDecimalConverter.format(weightDouble));
                            jsonObject.put("maleprice", twoDecimalConverter.format(toalPricedouble));
                        } else if (modal_b2BOrderItemDetails.getGender().equals("FEMALE")) {
                            jsonObject.put("femaleWeight", threeDecimalConverter.format(weightDouble));
                            jsonObject.put("femaleQty", 1);
                            jsonObject.put("femaleprice", twoDecimalConverter.format(toalPricedouble));
                        }
                        jsonObject.put("totalQty", 1);
                        jsonObject.put("totalweight", threeDecimalConverter.format(weightDouble));
                        jsonObject.put("totalprice", twoDecimalConverter.format(toalPricedouble));

                        gradeWise_count_weightJSONOBJECT.put(modal_b2BOrderItemDetails.gradekey, jsonObject);
                        selected_gradeDetailss_arrayList.add(modal_b2BGoatGradeDetails);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

              */

                try{

                    for(int ctgyIterator =0 ; ctgyIterator < ctgy_subCtgy_DetailsArrayList.size(); ctgyIterator++){
                        if(String.valueOf( modal_b2BOrderItemDetails.getB2bctgykey()).equals(ctgy_subCtgy_DetailsArrayList.get(ctgyIterator).getKey())){
                            modal_b2BOrderItemDetails.b2bctgyName =  ctgy_subCtgy_DetailsArrayList.get(ctgyIterator).getName();

                        }
                        if(String.valueOf( modal_b2BOrderItemDetails.getB2bsubctgykey()).equals(ctgy_subCtgy_DetailsArrayList.get(ctgyIterator).getSubctgy_key())){
                            modal_b2BOrderItemDetails.b2bSubctgyName =  ctgy_subCtgy_DetailsArrayList.get(ctgyIterator).getSubctgy_name();
                        }
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }

                try{
                    try {
                        for (int iterator1 = 0; iterator1 < goatGrade_arrayLsit.size(); iterator1++) {
                            if (String.valueOf(modal_b2BOrderItemDetails.getGradekey()).equals(goatGrade_arrayLsit.get(iterator1).getKey())) {
                                modal_b2BOrderItemDetails.gradeprice = goatGrade_arrayLsit.get(iterator1).getPrice();
                                modal_b2BOrderItemDetails.gradename  = goatGrade_arrayLsit.get(iterator1).getName();
                                modal_b2BOrderItemDetails.gradekey   = goatGrade_arrayLsit.get(iterator1).getKey();

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }



                try{
                    earTagDetailsArrayList_WholeBatch.add(modal_b2BOrderItemDetails);
                }
                catch (Exception e){
                    e.printStackTrace();
                }


                }
                try {
                    if (iterator - (goatGrade_arrayLsit.size() - 1) == 0) {
                        if (adapter_gradeWiseTotal_billingScreen != null) {
                            adapter_gradeWiseTotal_billingScreen.notifyDataSetChanged();
                            ListItemSizeSetter.getListViewSize(gradewisetotalCount_listview);

                        }
                        else{
                            if(placedOrderDetailsScreen != null) {

                         //       setAdapterForGradewiseTotal();

                            }
                        }


                    }
                } catch (Exception e) {
                    if(placedOrderDetailsScreen != null) {
                        Toast.makeText(placedOrderDetailsScreen, "Error from recalculateGradewise item 4: " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                    }
                    e.printStackTrace();
                }
            }
            showProgressBar(false);
        }
        catch (Exception e){
            showProgressBar(false);
            if(placedOrderDetailsScreen != null) {
                Toast.makeText(placedOrderDetailsScreen, "Error from recalculateGradewise item : " + String.valueOf(e), Toast.LENGTH_SHORT).show();
            }
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





        String Weight ="" , pricePerKg  = "" , discountAmount = "",gradeprice_string ="", totalPrice =  "" ,gradename ="" , gradeprice ="";
        JSONObject jsonObject = new JSONObject();
        totalWeight_double =0 ; weight_double = 0 ; pricePerKg_double = 0 ; discountAmount_double = 0 ; totalPrice_double = 0 ; totalPrice_doubleWithoutDiscount =0 ; gradeprice_double =0;

        for(int iterator =0 ; iterator < earTagDetailsArrayList_String .size(); iterator ++){

            if(earTagDetailsHashMap.containsKey(earTagDetailsArrayList_String .get(iterator))) {
                try {
                  String  totalWeight = earTagDetailsHashMap.get(earTagDetailsArrayList_String.get(iterator)).getNewWeight_forBillingScreen();
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


   /*     try{

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


    */




        try{

            discountAmount = discount_textView.getText().toString();
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
                callMethod = Constants.CallGETListMethod;
                FetchDataFromOrderItemDetails(orderid);
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


                        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(PlacedOrderDetailsScreen.this, WRITE_EXTERNAL_STORAGE);
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


                        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(PlacedOrderDetailsScreen.this, WRITE_EXTERNAL_STORAGE);
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
               // addItemRowsInOLDPDFFormat(layoutDocument);
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

         /*   Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setType("application/pdf");
            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            startActivity(Intent.createChooser(share, "Share"));


          */

            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri outputPdfUri = FileProvider.getUriForFile(this, PlacedOrderDetailsScreen.this.getPackageName() + ".provider", file);

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

            Phrase phrasebilltimeDetails = new Phrase("BillNo : "+tokenNo+"      DATE : "+DateParser.getDate_newFormat()+"      TIME : "+DateParser.getTime_newFormat(), valueFont_8);
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

            Phrase phraseSupervisorNameLabelTitle = new Phrase("Supervisor Name :   "+String.valueOf(supervisorname) , valueFont_8Bold);

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




                        Phrase phrasBatchpriceTextTitle = new Phrase(String.valueOf(finalBatchValue_String), valueFont_8);

                        PdfPCell phraseBatchPriceTextTitlecell = new PdfPCell(phrasBatchpriceTextTitle);
                        phraseBatchPriceTextTitlecell.setBorder(Rectangle.NO_BORDER);
                        phraseBatchPriceTextTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phraseBatchPriceTextTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phraseBatchPriceTextTitlecell.setPaddingLeft(6);
                        phraseBatchPriceTextTitlecell.setPaddingTop(5);
                        phraseBatchPriceTextTitlecell.setBorderWidthRight(01);
                        phraseBatchPriceTextTitlecell.setPaddingBottom(25);
                        itemDetailstext_table.addCell(phraseBatchPriceTextTitlecell);


                        Phrase phrasTotalTextTitle = new Phrase(String.valueOf(String.valueOf(finalGoatValue_String)), valueFont_8);

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
                        Phrase phrasBatchpriceTextTitle = new Phrase(String.valueOf(feedPricePerKg), valueFont_8);

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
                        Phrase phrasTotalTextTitle = new Phrase(String.valueOf(feedTotalPrice), valueFont_8);

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

                    Phrase phrasetotalDetailsTitle = new Phrase(String.valueOf(finalgoatValueWithFeed_String), valueFont_10);

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
               String text_finalfeedprice = String.valueOf(discountAmount.toString());
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

                        Phrase phrasetotalDetailsTitle = new Phrase(String.valueOf(discountAmount), valueFont_10);

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

                    Phrase phrasetotalDetailsTitle = new Phrase(String.valueOf(twoDecimalConverter.format(finalPayableprice)), valueFont_10);

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



    }


    private void addItemRowsInOLDPDFFormat(Document layoutDocument) {


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
            Phrase phrasesSubTotallabelDetails = new Phrase(" No.of.Goats    :  "+String.valueOf(finalquantity), itemNameFontBold);
            PdfPCell phraseSubTotalLabelDetailscell = new PdfPCell(phrasesSubTotallabelDetails);
            phraseSubTotalLabelDetailscell.setBorder(Rectangle.NO_BORDER);
            phraseSubTotalLabelDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseSubTotalLabelDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
            phraseSubTotalLabelDetailscell.setPaddingLeft(6);
            phraseSubTotalLabelDetailscell.setPaddingBottom(9);
            price_table.addCell(phraseSubTotalLabelDetailscell);



            Phrase phrasesDiscountlabelDetails = new Phrase("Discount          :  "+String.valueOf(discountAmount+" Rs"), itemNameFontBold);
            PdfPCell phraseDiscountLabelDetailscell = new PdfPCell(phrasesDiscountlabelDetails);
            phraseDiscountLabelDetailscell.setBorder(Rectangle.NO_BORDER);
            phraseDiscountLabelDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseDiscountLabelDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
            phraseDiscountLabelDetailscell.setPaddingLeft(6);
            phraseDiscountLabelDetailscell.setPaddingBottom(9);
            price_table.addCell(phraseDiscountLabelDetailscell);



            Phrase phrasesTotallabelDetails = new Phrase("Total                :  "+String.valueOf(twoDecimalConverter.format(finalPayableprice)+" Rs"), itemNameFontBold);
            PdfPCell phraseTotalLabelDetailscell = new PdfPCell(phrasesTotallabelDetails);
            phraseTotalLabelDetailscell.setBorder(Rectangle.NO_BORDER);
            phraseTotalLabelDetailscell.setHorizontalAlignment(Element.ALIGN_LEFT);
            phraseTotalLabelDetailscell.setVerticalAlignment(Element.ALIGN_LEFT);
            phraseTotalLabelDetailscell.setPaddingLeft(6);
            phraseTotalLabelDetailscell.setPaddingBottom(9);
            price_table.addCell(phraseTotalLabelDetailscell);





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






    }









    public void Create_and_SharePdf_old() {
        try {

            if (SDK_INT >= Build.VERSION_CODES.R) {

                if (Environment.isExternalStorageManager()) {
                    try {

                        prepareDataForPDF_old();


                    } catch (Exception e) {
                        e.printStackTrace();
                        ;
                    }
                } else {
                    try {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
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


                int writeExternalStoragePermission = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
                //Log.d("ExportInvoiceActivity", "writeExternalStoragePermission "+writeExternalStoragePermission);
                // If do not grant write external storage permission.
                if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                    // Request user to grant write external storage permission.
                    ActivityCompat.requestPermissions(getParent(), new String[]{WRITE_EXTERNAL_STORAGE},
                            REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
                } else {
                    showProgressBar(true);
                    try {
                        prepareDataForPDF_old();


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

    private void prepareDataForPDF_old() {
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
                addItemRowsInNewPDFFormat_old(layoutDocument);
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
            share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri outputPdfUri = FileProvider.getUriForFile(this, PlacedOrderDetailsScreen.this.getPackageName() + ".provider", file);

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

    private void addItemRowsInNewPDFFormat_old(Document layoutDocument) {


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


                Phrase phrasepaymentModeDetails = new Phrase(": " + paymentmode, itemNameFont1_bold);

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
                       // Modal_POJOClassForFinalSalesHashmap modal_pojoClassForFinalSalesHashmap = earTagDetails_JSONFinalSalesHashMap.get(modal_b2BGoatGradeDetails.getKey());
                        Modal_POJOClassForFinalSalesHashmap modal_pojoClassForFinalSalesHashmap = new Modal_POJOClassForFinalSalesHashmap();
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


                           // Modal_POJOClassForFinalSalesHashmap modal_pojoClassForFinalSalesHashmap = earTagDetails_JSONFinalSalesHashMap.get(modal_b2BGoatGradeDetails.getKey());
                            Modal_POJOClassForFinalSalesHashmap modal_pojoClassForFinalSalesHashmap = new Modal_POJOClassForFinalSalesHashmap();
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
                        Modal_B2BOrderItemDetails modal_goatEarTagDetails = earTagDetailsArrayList_WholeBatch.get(iterator);
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

                                Phrase phraseDetails = new Phrase(String.valueOf(modal_goatEarTagDetails.getWeightingrams()+" Kg"), smallFont);
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