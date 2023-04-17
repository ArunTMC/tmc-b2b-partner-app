package com.tmc.tmcb2bpartnerapp.second_version.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.app.DatePickerDialog;
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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
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
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BRetailerDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal__B2BCreditTransactionHistory;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal__B2BPaymentDetails;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal__PaymentDetails_withOrderDetails;
import com.tmc.tmcb2bpartnerapp.second_version.adapter.Adapter_AutoComplete_RetailerMobileNoForCreditClearance;
import com.tmc.tmcb2bpartnerapp.second_version.apiRequestServices.B2BCreditTransactionHistory;
import com.tmc.tmcb2bpartnerapp.second_version.apiRequestServices.B2BPaymentDetails;
import com.tmc.tmcb2bpartnerapp.second_version.interfaces.B2BCreditTransactionHistoryInterface;
import com.tmc.tmcb2bpartnerapp.second_version.interfaces.B2BPaymentDetailsInterface;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;

import org.apache.poi.hssf.util.HSSFColor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;
import static com.itextpdf.text.BaseColor.BLACK;
import static com.itextpdf.text.BaseColor.GRAY;
import static com.itextpdf.text.BaseColor.GREEN;
import static com.itextpdf.text.BaseColor.LIGHT_GRAY;
import static com.itextpdf.text.BaseColor.RED;
import static com.itextpdf.text.BaseColor.WHITE;
import static org.apache.poi.ss.usermodel.IndexedColors.LIGHT_BLUE;

public class TransactionDetails_by_buyer extends AppCompatActivity {
    AutoCompleteTextView buyerName_autoComplete_textview;
    public static LinearLayout loadingpanelmask ,loadingPanel,fetchData_layout,back_IconLayout;
    TextView retailerName_textView ,recordsListinstruction ,startDateValue , endDateValue , filename_textview;
    CardView paymentReportCreated_CardView ;
    Button sharePDf_Button;


    //String
    String  deliveryCenterName = "", supervisorName ="",deliveryCenterKey ="" , supervisorMobileno = "",
            retailerAddress ="" , retailerMobileNo ="" , retailerKey = "" , retailerName ="",selectedPaymentMode ="";
    //String
    String  PreviousDateString ="" , DateString ="" ,selectedStartDate ="",selectedEndDate ="";


    //general
    DatePickerDialog datepicker,enddatepicker;
    File file;



    static DecimalFormat twoDecimalConverter = new DecimalFormat(Constants.twoDecimalPattern);
    static DecimalFormat twoDecimalConverterWithComma = new DecimalFormat(Constants.twoDecimalWithCommaPattern);

    //int
    private static int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;

    //boolean
    boolean isPaymentDetailsCalled = false;
    boolean isB2BOrderTableServiceCalled = false;
    boolean  isRetailerDetailsServiceCalled = false ;
    boolean isRetailerCreditTransactionHistoryCalled = false;
    boolean isBuyerSelected = false;


    //interface
    B2BOrderDetailsInterface callback_b2bOrderDetails;
    B2BPaymentDetailsInterface callB2BPaymentDetailsInterface = null;
    B2BRetailerDetailsInterface callback_retailerDetailsInterface = null;
    B2BCreditTransactionHistoryInterface callB2BCreditTransactionHistoryInterface = null;



    public static ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList = new ArrayList<>();
    public static ArrayList<Modal_B2BOrderDetails> orderDetailsArrayList = new ArrayList<>();
    public static ArrayList<Modal__B2BPaymentDetails> paymentDetailsArrayList = new ArrayList<>();
    public static ArrayList<Modal__PaymentDetails_withOrderDetails> paymentDetails_OrderDetails_ArrayList = new ArrayList<>();
    public static ArrayList<Modal__B2BCreditTransactionHistory> creditTransactionHistoryArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details_by_buyer);




        buyerName_autoComplete_textview = findViewById(R.id.buyer_details_autoCompleteTextview);
        loadingpanelmask = findViewById(R.id.loadingpanelmask);
        loadingPanel = findViewById(R.id.loadingPanel);
        fetchData_layout  = findViewById(R.id.fetchData_layout);
        fetchData_layout = findViewById(R.id.fetchData_layout);
        startDateValue = findViewById(R.id.startDateValue);
        endDateValue = findViewById(R.id.endDateValue);
        recordsListinstruction = findViewById(R.id.recordsListinstruction);
        paymentReportCreated_CardView = findViewById(R.id.paymentReportCreated_CardView);
        sharePDf_Button = findViewById(R.id.sharePDf_Button);
        back_IconLayout  = findViewById(R.id.back_IconLayout);
        loadingpanelmask = findViewById(R.id.loadingpanelmask);
        loadingPanel = findViewById(R.id.loadingPanel);
        filename_textview = findViewById(R.id.filename_textview);

        retailerDetailsArrayList.clear();
        paymentDetailsArrayList.clear();
        orderDetailsArrayList.clear();
        paymentDetails_OrderDetails_ArrayList.clear();
        creditTransactionHistoryArrayList.clear();


        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);
        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");



        SharedPreferences sh = getSharedPreferences("LoginData",MODE_PRIVATE);
        supervisorName = sh.getString("UserName","");
        supervisorMobileno = sh.getString("UserMobileNumber","");



        paymentReportCreated_CardView.setVisibility(View.GONE);
        recordsListinstruction.setText(" Please select Buyer & select From - To Date then click Fetch Data  ");
        recordsListinstruction.setVisibility(View.VISIBLE);

        Create_and_SharePdf(true);



        if(DatabaseArrayList_PojoClass.retailerDetailsArrayList.size() == 0){
            try {
                call_and_init_B2BRetailerDetailsService(Constants.CallGETListMethod);
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

            }
        });

        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        sharePDf_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePdf();
            }
        });



        startDateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    openDatePicker();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        endDateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    openEndDatePicker();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        fetchData_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isBuyerSelected) {

                    if(!startDateValue.getText().toString().contains("/--/")){
                        if(!endDateValue.getText().toString().contains("/--/")){
                            showProgressBar(true);
                            paymentDetails_OrderDetails_ArrayList.clear();
                            Call_and_Execute_PaymentDetails(Constants.CallGETListMethod);
                        }
                        else{
                            AlertDialogClass.showDialog(TransactionDetails_by_buyer.this, R.string.CannotFetchWhenendDateNotSelected);

                        }
                    }
                    else{
                        AlertDialogClass.showDialog(TransactionDetails_by_buyer.this, R.string.CannotFetchWhenstartdateNotSelected);

                    }


                }
                else{
                    AlertDialogClass.showDialog(TransactionDetails_by_buyer.this, R.string.CannotFetchWhenbuyerisNotSelected);

                }



            }
        });


        buyerName_autoComplete_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyerName_autoComplete_textview.setText("  ");
            }
        });



    }

    private void Call_and_Execute_PaymentDetails(String callMethod) {
        showProgressBar(true);

        if(isPaymentDetailsCalled){
            return;
        }

        isPaymentDetailsCalled = true;

        callB2BPaymentDetailsInterface  = new B2BPaymentDetailsInterface() {
            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal__B2BPaymentDetails> arrayList) {
                recordsListinstruction.setText(String.valueOf(" Please Wait !!! "));
                paymentDetailsArrayList = arrayList;
                isPaymentDetailsCalled = false;

                Intialize_and_Execute_OrderDetailsList(Constants.CallGETListMethod);
            }

            @Override
            public void notifySuccess(String result) {
                isPaymentDetailsCalled = false;
                if(result.equals(Constants.emptyResult_volley)){
                    Intialize_and_Execute_OrderDetailsList(Constants.CallGETListMethod);
                    Toast.makeText(TransactionDetails_by_buyer.this, "There is no payment details for this date", Toast.LENGTH_SHORT).show();
                }
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

        if(callMethod.equals(Constants.CallGETListMethod)){

            String getApiToCall = API_Manager.getPaymentDetailsUsingRetailerkeyFromToDate_WithIndex +"?deliverycentrekey=" +deliveryCenterKey+"&retailerkey="+retailerKey+"&fromdate="+String.valueOf(DateParser.convertOldFormatDateintoNewFormat(selectedStartDate))+" 00:00:00" + "&todate="+String.valueOf(DateParser.convertOldFormatDateintoNewFormat(selectedEndDate))+" 23:59:59";
            B2BPaymentDetails asyncTask = new B2BPaymentDetails(callB2BPaymentDetailsInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }
    }

    private void Call_and_Execute_CreditTransactionHistory(String callMethod) {

        if(isRetailerCreditTransactionHistoryCalled){
            return;
        }

        isRetailerCreditTransactionHistoryCalled = true;
        creditTransactionHistoryArrayList.clear();

        callB2BCreditTransactionHistoryInterface  = new B2BCreditTransactionHistoryInterface() {
            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal__B2BCreditTransactionHistory> creditTransactionHistoryArrayListt) {
                isRetailerCreditTransactionHistoryCalled = false;
                recordsListinstruction.setText(String.valueOf(" Creating PDF !!"));
                creditTransactionHistoryArrayList = creditTransactionHistoryArrayListt;
                for(int i =0 ; i<creditTransactionHistoryArrayList.size(); i++ ){
                    Modal__B2BCreditTransactionHistory modal__b2BCreditTransactionHistory = creditTransactionHistoryArrayList.get(i);
                    boolean isPaymentIdFound = false;
                    for(int j =0 ; j<paymentDetailsArrayList.size(); j++ ) {
                        Modal__B2BPaymentDetails modal__b2BPaymentDetails = paymentDetailsArrayList.get(j);
                        try{
                            if(modal__b2BCreditTransactionHistory.getPaymentid().equals(modal__b2BPaymentDetails.getPaymentid())){
                                isPaymentIdFound = true;
                                modal__b2BPaymentDetails.setCreditvalue(String.valueOf(modal__b2BCreditTransactionHistory.getNewamountincredit()));
                            }


                            if((paymentDetailsArrayList.size()-1) == 1 ){
                                if(isPaymentIdFound) {
                                    isPaymentIdFound = false;
                                }
                                else{
                                    modal__b2BPaymentDetails.setCreditvalue(String.valueOf("0"));

                                }
                            }



                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                    boolean isOrderIdFound = false;
                    for(int j =0 ; j<orderDetailsArrayList.size(); j++ ) {
                        Modal_B2BOrderDetails modal_b2BOrderDetails = orderDetailsArrayList.get(j);
                        try{
                            if(modal__b2BCreditTransactionHistory.getOrderid().equals(modal_b2BOrderDetails.getOrderid())){
                                isOrderIdFound = true;
                                modal_b2BOrderDetails.setCreditvalue(String.valueOf(modal__b2BCreditTransactionHistory.getNewamountincredit()));
                            }

                            if((orderDetailsArrayList.size()-1) == 1 ){
                                if(isOrderIdFound) {
                                    isOrderIdFound = false;
                                }
                                else{
                                    modal_b2BOrderDetails.setCreditvalue(String.valueOf("0"));

                                }
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    if((creditTransactionHistoryArrayList.size() -1 ) == i){
                        ///showProgressBar(false);

                        Collections.sort(paymentDetailsArrayList, new Comparator<Modal__B2BPaymentDetails>() {
                            public int compare(final Modal__B2BPaymentDetails object1, final Modal__B2BPaymentDetails object2) {
                                return object1.getTransactiontime_long().compareTo(object2.getTransactiontime_long());
                            }
                        });

                        ProcessBothArray_andCombineIt();
                    }



                }


            }

            @Override
            public void notifySuccess(String result) {
                if(result.equals(Constants.emptyResult_volley)){
                    showProgressBar(false);
                    Toast.makeText(TransactionDetails_by_buyer.this, "There is no Credit transaction details for this date", Toast.LENGTH_SHORT).show();
                }
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

        if(callMethod.equals(Constants.CallGETListMethod)){

            String getApiToCall = API_Manager.getCreditTransactionHistoryUsingRetailerkeyFromToDate_WithIndex +"?deliverycentrekey=" +deliveryCenterKey+"&retailerkey="+retailerKey+"&fromdate="+String.valueOf(DateParser.convertOldFormatDateintoNewFormat(selectedStartDate))+" 00:00:00" + "&todate="+String.valueOf(DateParser.convertOldFormatDateintoNewFormat(selectedEndDate))+" 23:59:59";
            B2BCreditTransactionHistory asyncTask = new B2BCreditTransactionHistory(callB2BCreditTransactionHistoryInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }
    }
    private void Intialize_and_Execute_OrderDetailsList(String callMethod) {


        showProgressBar(true);
        if (isB2BOrderTableServiceCalled) {
            // showProgressBar(false);
            return;
        }
        isB2BOrderTableServiceCalled = true;

        callback_b2bOrderDetails = new B2BOrderDetailsInterface() {
            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BOrderDetails> orderDetailsArrayListt) {

                orderDetailsArrayList =orderDetailsArrayListt;
                Call_and_Execute_CreditTransactionHistory(Constants.CallGETListMethod);


                isB2BOrderTableServiceCalled = false;
            }

            @Override
            public void notifySuccess(String result) {
                isB2BOrderTableServiceCalled = false;
                Toast.makeText(TransactionDetails_by_buyer.this, "There is no Order details for this date", Toast.LENGTH_SHORT).show();

                Call_and_Execute_CreditTransactionHistory(Constants.CallGETListMethod);

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isB2BOrderTableServiceCalled = false;

            }

            @Override
            public void notifyProcessingError(Exception error) {
                isB2BOrderTableServiceCalled = false;

            }
        };

        if (callMethod.equals(Constants.CallGETListMethod)) {
            String getApiToCall = API_Manager.getOrderDetailsForDeliveryCentrekeyRetailerKeyWithOutCancelledStatus + "?deliverycentrekey=" + deliveryCenterKey +"&retailerkey="+retailerKey+ "&fromdate=" + String.valueOf(DateParser.convertOldFormatDateintoNewFormat(selectedStartDate)) + " 00:00:00" + "&todate=" + String.valueOf(DateParser.convertOldFormatDateintoNewFormat(selectedEndDate)) + " 23:59:59" ;

            B2BOrderDetails asyncTask = new B2BOrderDetails(callback_b2bOrderDetails, getApiToCall, callMethod);
            asyncTask.execute();

        }
    }

    private void ProcessBothArray_andCombineIt() {

        for(int iterator =0 ; iterator< paymentDetailsArrayList.size(); iterator++){
            Modal__PaymentDetails_withOrderDetails modal__paymentDetails_withOrderDetails = new Modal__PaymentDetails_withOrderDetails();
            Modal__B2BPaymentDetails modal__b2BPaymentDetails = paymentDetailsArrayList.get(iterator);



            modal__paymentDetails_withOrderDetails.retailerkey = modal__b2BPaymentDetails.getRetailerkey();
            modal__paymentDetails_withOrderDetails.retailermobileno = modal__b2BPaymentDetails.getRetailermobileno();
            modal__paymentDetails_withOrderDetails.retailername = modal__b2BPaymentDetails.getRetailername();
            modal__paymentDetails_withOrderDetails.deliverycentrekey = modal__b2BPaymentDetails.getDeliverycentrekey();
            modal__paymentDetails_withOrderDetails.notes = modal__b2BPaymentDetails.getNotes();
            modal__paymentDetails_withOrderDetails.uniquekey = modal__b2BPaymentDetails.getUniquekey();
            modal__paymentDetails_withOrderDetails.transactiontime_long = modal__b2BPaymentDetails.getTransactiontime_long();
            modal__paymentDetails_withOrderDetails.paymentmode = modal__b2BPaymentDetails.getPaymentmode();
            modal__paymentDetails_withOrderDetails.transactiontime = modal__b2BPaymentDetails.getTransactiontime();
            modal__paymentDetails_withOrderDetails.transactionvalue = modal__b2BPaymentDetails.getTransactionvalue();
            modal__paymentDetails_withOrderDetails.orderid = modal__b2BPaymentDetails.getOrderid();
            modal__paymentDetails_withOrderDetails.paymentid = modal__b2BPaymentDetails.getPaymentid();
            modal__paymentDetails_withOrderDetails.creditvalue = modal__b2BPaymentDetails.getCreditvalue();

            modal__paymentDetails_withOrderDetails.setTransactiontype(Constants.debitTransaction);
            paymentDetails_OrderDetails_ArrayList.add(modal__paymentDetails_withOrderDetails);


        }


        for(int iterator = 0 ; iterator < orderDetailsArrayList.size(); iterator++){
            Modal__PaymentDetails_withOrderDetails modal__paymentDetails_withOrderDetails = new Modal__PaymentDetails_withOrderDetails();
            Modal_B2BOrderDetails modal_b2BOrderDetails = orderDetailsArrayList.get(iterator);

            modal__paymentDetails_withOrderDetails.deliverycentrekey = modal_b2BOrderDetails.getDeliverycentrekey();
            modal__paymentDetails_withOrderDetails.deliverycentrename = modal_b2BOrderDetails.getDeliverycentrename();
            modal__paymentDetails_withOrderDetails.orderid = modal_b2BOrderDetails.getOrderid();
            modal__paymentDetails_withOrderDetails.orderplaceddate = modal_b2BOrderDetails.getOrderplaceddate();
            modal__paymentDetails_withOrderDetails.status = modal_b2BOrderDetails.getStatus();
            modal__paymentDetails_withOrderDetails.payableAmount = modal_b2BOrderDetails.getPayableAmount();
            modal__paymentDetails_withOrderDetails.orderdeliveredtime = modal_b2BOrderDetails.getOrderdeliveredtime();
            modal__paymentDetails_withOrderDetails.priceperkg = modal_b2BOrderDetails.getPriceperkg();
            modal__paymentDetails_withOrderDetails.discountamount = modal_b2BOrderDetails.getDiscountamount();
            modal__paymentDetails_withOrderDetails.batchno = modal_b2BOrderDetails.getBatchno();
            modal__paymentDetails_withOrderDetails.b2bctgykey = modal_b2BOrderDetails.getB2bctgykey();
            modal__paymentDetails_withOrderDetails.totalweight = modal_b2BOrderDetails.getTotalweight();
            modal__paymentDetails_withOrderDetails.totalquantity = modal_b2BOrderDetails.getTotalquantity();
            modal__paymentDetails_withOrderDetails.totalmalequantity = modal_b2BOrderDetails.getTotalmalequantity();
            modal__paymentDetails_withOrderDetails.invoiceno = modal_b2BOrderDetails.getInvoiceno();
            modal__paymentDetails_withOrderDetails.paymentmode = modal_b2BOrderDetails.getPaymentmode();
            modal__paymentDetails_withOrderDetails.ordercanceledtime = modal_b2BOrderDetails.getOrdercanceledtime();
            modal__paymentDetails_withOrderDetails.totalPrice = modal_b2BOrderDetails.getTotalPrice();
            modal__paymentDetails_withOrderDetails.totalmaleweight = modal_b2BOrderDetails.getTotalmaleweight();
            modal__paymentDetails_withOrderDetails.totalfemaleweight = modal_b2BOrderDetails.getTotalfemaleweight();
            modal__paymentDetails_withOrderDetails.totalfemalewithbabyquantity = modal_b2BOrderDetails.getTotalfemalewithbabyquantity();


            modal__paymentDetails_withOrderDetails.totalfemalewithbabyweight = modal_b2BOrderDetails.getTotalfemalewithbabyweight();
            modal__paymentDetails_withOrderDetails.totalfemalequantity = modal_b2BOrderDetails.getTotalfemalequantity();
            modal__paymentDetails_withOrderDetails.retailerkey = modal_b2BOrderDetails.getRetailerkey();
            modal__paymentDetails_withOrderDetails.retailermobileno = modal_b2BOrderDetails.getRetailermobileno();
            modal__paymentDetails_withOrderDetails.notes = modal_b2BOrderDetails.getNotes();
            modal__paymentDetails_withOrderDetails.billno = modal_b2BOrderDetails.getBillno();
            modal__paymentDetails_withOrderDetails.retailername = modal_b2BOrderDetails.getRetailername();
            modal__paymentDetails_withOrderDetails.retaileraddress = modal_b2BOrderDetails.getRetaileraddress();
            modal__paymentDetails_withOrderDetails.feedPrice = modal_b2BOrderDetails.getFeedPrice();
            modal__paymentDetails_withOrderDetails.feedPriceperkg = modal_b2BOrderDetails.getFeedPriceperkg();
            modal__paymentDetails_withOrderDetails.feedWeight = modal_b2BOrderDetails.getFeedWeight();
            modal__paymentDetails_withOrderDetails.supervisorname = modal_b2BOrderDetails.getSupervisorname();
            modal__paymentDetails_withOrderDetails.supervisormobileno = modal_b2BOrderDetails.getSupervisormobileno();

            modal__paymentDetails_withOrderDetails.transactiontime = modal_b2BOrderDetails.getOrderplaceddate();
            modal__paymentDetails_withOrderDetails.transactiontime_long = DateParser.getLongValuefortheDate(modal_b2BOrderDetails.getOrderplaceddate());
            modal__paymentDetails_withOrderDetails.creditvalue = modal_b2BOrderDetails.getCreditvalue();
            modal__paymentDetails_withOrderDetails.transactionvalue = modal_b2BOrderDetails.getTotalPrice();

            modal__paymentDetails_withOrderDetails.setTransactiontype(Constants.creditTransaction);


            paymentDetails_OrderDetails_ArrayList.add(modal__paymentDetails_withOrderDetails);


        }





        Collections.sort(paymentDetails_OrderDetails_ArrayList, new Comparator<Modal__PaymentDetails_withOrderDetails>() {
            public int compare(final Modal__PaymentDetails_withOrderDetails object1, final Modal__PaymentDetails_withOrderDetails object2) {
                return object1.getTransactiontime_long().compareTo(object2.getTransactiontime_long());
            }
        });



        Create_and_SharePdf(false);


    }


    private void setAdapterForRetailerDetails() {


        try{
            retailerDetailsArrayList =  sortThisArrayUsingRetailerName_mobileNo(retailerDetailsArrayList);



        }
        catch (Exception e){
            e.printStackTrace();
        }

        isBuyerSelected = false;



        try {
            Adapter_AutoComplete_RetailerMobileNoForCreditClearance adapter_autoComplete_retailerMobileNo = new Adapter_AutoComplete_RetailerMobileNoForCreditClearance(TransactionDetails_by_buyer.this, retailerDetailsArrayList, TransactionDetails_by_buyer.this,false);
            adapter_autoComplete_retailerMobileNo.setHandler(newHandler());

            showProgressBar(false);
            buyerName_autoComplete_textview.setAdapter(adapter_autoComplete_retailerMobileNo);
            buyerName_autoComplete_textview.clearFocus();
            buyerName_autoComplete_textview.setThreshold(1);
            buyerName_autoComplete_textview.dismissDropDown();
            buyerName_autoComplete_textview.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_CLASS_TEXT);




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
                String data = bundle.getString("dropdown");
                String retailerkeyy = bundle.getString("retailerkey");
                for(Modal_B2BRetailerDetails modal_b2BRetailerDetails : retailerDetailsArrayList){

                    if(modal_b2BRetailerDetails.getRetailerkey().toUpperCase().equals(retailerkeyy.toUpperCase())){
                        retailerMobileNo = String.valueOf(modal_b2BRetailerDetails.getMobileno());
                        retailerKey = String.valueOf(modal_b2BRetailerDetails.getRetailerkey());
                        retailerName = String.valueOf(modal_b2BRetailerDetails.getRetailername());
                        retailerAddress = String.valueOf(modal_b2BRetailerDetails.getAddress());
                        buyerName_autoComplete_textview.setText(retailerName);
                        buyerName_autoComplete_textview.clearFocus();
                        buyerName_autoComplete_textview.setThreshold(1);
                        buyerName_autoComplete_textview.dismissDropDown();
                        isBuyerSelected = true;

                        paymentReportCreated_CardView.setVisibility(View.GONE);
                        recordsListinstruction.setText(" Please Select From - To Date then click Fetch Data  ");
                        recordsListinstruction.setVisibility(View.VISIBLE);
                        paymentDetailsArrayList.clear();

                    }

                }




                if(data.equals("dropdown")){
                    String data1 = bundle.getString("dropdown");

                    if (String.valueOf(data1).equalsIgnoreCase("dropdown")) {


                        try {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            Objects.requireNonNull(imm).hideSoftInputFromWindow(buyerName_autoComplete_textview.getWindowToken(), 0);

                            buyerName_autoComplete_textview.clearFocus();

                            buyerName_autoComplete_textview.dismissDropDown();
                            hideKeyboard(buyerName_autoComplete_textview);
                            closebuyerNameSearchBarEditText();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                        try {




                            selectedPaymentMode = "";

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




    public void call_and_init_B2BRetailerDetailsService(String CallMethod) {
        showProgressBar(true);

        if (isRetailerDetailsServiceCalled) {
            //  showProgressBar(false);
            return;
        }
        isRetailerDetailsServiceCalled = true;
        retailerDetailsArrayList.clear();

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
                if(result.equals(Constants.item_Already_Added_volley)){
                    AlertDialogClass.showDialog(TransactionDetails_by_buyer.this, R.string.retailersAlreadyCreated_Instruction);
                    isRetailerDetailsServiceCalled = false;
                    showProgressBar(false);

                }
                else if(result.equals(Constants.successResult_volley)){
                    retailerDetailsArrayList = DatabaseArrayList_PojoClass.retailerDetailsArrayList;

                    isRetailerDetailsServiceCalled = false;

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

        if(CallMethod.equals(Constants.CallGETListMethod)){
            String getApiToCall = API_Manager.getretailerDetailsListWithDeliveryCentreKey+deliveryCenterKey ;

            B2BRetailerDetails asyncTask = new B2BRetailerDetails(callback_retailerDetailsInterface,  getApiToCall, Constants.CallGETListMethod);
            asyncTask.execute();
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


        }


    }
    private ArrayList<Modal_B2BRetailerDetails> sortThisArrayUsingRetailerName_mobileNo(ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList) {


        final Pattern p = Pattern.compile("^\\d+");



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



        return  retailerDetailsArrayList;



    }






    private void hideKeyboard(AutoCompleteTextView editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
    private void closebuyerNameSearchBarEditText() {

        buyerName_autoComplete_textview.setText(retailerName);
        buyerName_autoComplete_textview.clearFocus();
        buyerName_autoComplete_textview.setThreshold(1);
        buyerName_autoComplete_textview.dismissDropDown();
    }


    private void openDatePicker() {



        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        datepicker = new DatePickerDialog(TransactionDetails_by_buyer.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        try {

                            String month_in_String = DateParser.getMonthString(monthOfYear);
                            String monthstring = String.valueOf(monthOfYear + 1);
                            String datestring = String.valueOf(dayOfMonth);
                            if (datestring.length() == 1) {
                                datestring = "0" + datestring;
                            }
                            if (monthstring.length() == 1) {
                                monthstring = "0" + monthstring;
                            }

                            Calendar myCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);

                            int dayOfWeek = myCalendar.get(Calendar.DAY_OF_WEEK);

                            String CurrentDay = DateParser.getDayString(dayOfWeek);
                            //Log.d(Constants.TAG, "dayOfWeek Response: " + dayOfWeek);


                            String CurrentDateString = datestring + monthstring + String.valueOf(year);
                            try {
                                PreviousDateString = DateParser.getDatewithNameofthePreviousDayfromSelectedDayForDatePicker(CurrentDateString);
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            startDateValue.setText(CurrentDay + ", " + dayOfMonth + " " + month_in_String + " " + year);
                            DateString = (CurrentDay + ", " + dayOfMonth + " " + month_in_String + " " + year);

                            selectedStartDate = DateString;
                            selectedEndDate = DateParser.getDatewithNameoftheseventhDayFromSelectedStartDate(DateString);

                            paymentReportCreated_CardView.setVisibility(View.GONE);
                            recordsListinstruction.setText(" Please Select  To Date then click Fetch Data  ");
                            recordsListinstruction.setVisibility(View.VISIBLE);
                            paymentDetailsArrayList.clear();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, year, month, day);






        Calendar c = Calendar.getInstance();



        DatePicker datePicker = datepicker.getDatePicker();

        c.add(Calendar.YEAR, -2);
        // Toast.makeText(getApplicationContext(), Calendar.DATE, Toast.LENGTH_LONG).show();
        Log.d(Constants.TAG, "Calendar.DATE " + String.valueOf(Calendar.DATE));
        long oneMonthAhead = c.getTimeInMillis();
        datePicker.setMaxDate(System.currentTimeMillis() - 1000);
        datePicker.setMinDate(oneMonthAhead);

        datepicker.show();
    }

    private void openEndDatePicker() {


        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        enddatepicker = new DatePickerDialog(TransactionDetails_by_buyer.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        try {




                            String month_in_String =  DateParser.getMonthString(monthOfYear);
                            String monthstring = String.valueOf(monthOfYear + 1);
                            String datestring = String.valueOf(dayOfMonth);
                            if (datestring.length() == 1) {
                                datestring = "0" + datestring;
                            }
                            if (monthstring.length() == 1) {
                                monthstring = "0" + monthstring;
                            }


                            Calendar myCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);

                            int dayOfWeek = myCalendar.get(Calendar.DAY_OF_WEEK);

                            String CurrentDay = DateParser.getDayString(dayOfWeek);
                            //Log.d(Constants.TAG, "dayOfWeek Response: " + dayOfWeek);


                            String CurrentDateString = datestring + monthstring + String.valueOf(year);
                            try {
                                PreviousDateString = DateParser.getDatewithNameofthePreviousDayfromSelectedDayForDatePicker(CurrentDateString);
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            endDateValue.setText(CurrentDay + ", " + dayOfMonth + " " + month_in_String + " " + year);
                            DateString = (CurrentDay + ", " + dayOfMonth + " " + month_in_String + " " + year);

                            selectedEndDate = DateString;
                            Toast.makeText(getApplicationContext(), "After Selecting the data . Please Click on Get Data Button", Toast.LENGTH_LONG).show();

                            paymentReportCreated_CardView.setVisibility(View.GONE);
                            recordsListinstruction.setText(" Please Click Fetch Data  ");
                            recordsListinstruction.setVisibility(View.VISIBLE);
                            paymentDetailsArrayList.clear();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, year, month, day);






        Calendar c = Calendar.getInstance();




        boolean isEndDateisAfterCurrentDate = false;
        Date d2=null,d1 = null;
        long MaxDate = DateParser.getMillisecondsFromDate(selectedEndDate);
        long MinDate = DateParser.getMillisecondsFromDate(selectedStartDate);

        String todayDate = DateParser.getDate_and_time_OLDFormat();
        SimpleDateFormat sdformat = new SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH);
        sdformat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

        try {
            d2 = sdformat.parse(todayDate);

            d1 = sdformat.parse(selectedEndDate);
            if((d1.compareTo(d2) < 0)||(d1.compareTo(d2) == 0)){
                isEndDateisAfterCurrentDate =false;
            }
            else if(d1.compareTo(d2) > 0){
                isEndDateisAfterCurrentDate =true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        DatePicker datePicker = enddatepicker.getDatePicker();
        c.add(Calendar.DATE, -15);
        try {
            if (!isEndDateisAfterCurrentDate) {

                MaxDate = DateParser.getMillisecondsFromDate(selectedEndDate);

            } else {
                MaxDate = DateParser.getMillisecondsFromDate(todayDate);

            }
            MinDate = DateParser.getMillisecondsFromDate(selectedStartDate);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        long oneMonthAhead = c.getTimeInMillis();
        datePicker.setMaxDate(MaxDate);
        datePicker.setMinDate(MinDate);

        enddatepicker.show();



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


                        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(TransactionDetails_by_buyer.this, WRITE_EXTERNAL_STORAGE);
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


                        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(TransactionDetails_by_buyer.this, WRITE_EXTERNAL_STORAGE);
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
            String filename = "Transaction Details of "+ String.valueOf(retailerName)+" for the period( From : "+(selectedStartDate)+" To : "+(selectedEndDate)+" ) "+timeinLong+".pdf";
            String croppedfileName = filename.substring(0,20);
            filename_textview.setText(String.valueOf(croppedfileName+"... "));
            file = new File(folder, filename);
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

            sharePdf();

            // }
        } catch (IOException e) {
            showProgressBar(false);

            Log.i("error", e.getLocalizedMessage());
        } catch (Exception ex) {
            showProgressBar(false);

            ex.printStackTrace();
        }


    }

    private void sharePdf() {

        paymentReportCreated_CardView.setVisibility(View.VISIBLE);

        recordsListinstruction.setVisibility(View.GONE);


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());



        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri outputPdfUri = FileProvider.getUriForFile(this, TransactionDetails_by_buyer.this.getPackageName() + ".provider", file);

        share.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        share.setType("application/pdf");
        share.putExtra(Intent.EXTRA_STREAM,outputPdfUri);
        startActivity(Intent.createChooser(share, "Share"));

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
        Font valueFont_1= new Font(Font.FontFamily.TIMES_ROMAN, 1,
                Font.NORMAL);



        double totalAmount =0 ;
        String closingBalance = "0";
        RoundRectangle roundRectange = new RoundRectangle();
        PdfPTable wholePDFContentOutline_table = new PdfPTable(1);

        PdfPTable wholePDFContentWithImage_and_table = new PdfPTable(1);
        PdfPTable wholePDFContentWithOut_Outline_table = new PdfPTable(1);

        PdfPTable tmcLogoImage_table = new PdfPTable(new float[] { 50, 25 ,25 });


        try {
            PdfPCell table_Cell = new PdfPCell();
            table_Cell.setPaddingTop(5);
            table_Cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table_Cell.setCalculatedHeight(5f);
            table_Cell.setVerticalAlignment(Element.ALIGN_RIGHT);
            table_Cell.setBorder(Rectangle.NO_BORDER);
            tmcLogoImage_table.addCell(table_Cell);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PdfPCell table_Cell = new PdfPCell();
            table_Cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table_Cell.setCalculatedHeight(5f);
            table_Cell.setVerticalAlignment(Element.ALIGN_RIGHT);
            table_Cell.setBorder(Rectangle.NO_BORDER);
            tmcLogoImage_table.addCell(table_Cell);

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
           PdfPCell table_Cell = new PdfPCell(addLogo(layoutDocument));
            table_Cell.setBorder(Rectangle.NO_BORDER);
            table_Cell.setPaddingRight(10);
            table_Cell.setCalculatedHeight(5f);
            table_Cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table_Cell.setVerticalAlignment(Element.ALIGN_RIGHT);
            tmcLogoImage_table.addCell(table_Cell);

        } catch (Exception e) {
            e.printStackTrace();
        }







        PdfPTable billtimeDetails_table = new PdfPTable(1);
        /*try {

            Phrase phrasebilltimeDetails = new Phrase("TRANSACTION DETAILS BY BUYER", valueFont_8);
            PdfPCell phrasebilltimedetailscell = new PdfPCell(phrasebilltimeDetails);
            phrasebilltimedetailscell.setBorder(Rectangle.NO_BORDER);
            phrasebilltimedetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
            phrasebilltimedetailscell.setVerticalAlignment(Element.ALIGN_CENTER);
            phrasebilltimedetailscell.setPaddingLeft(10);
            phrasebilltimedetailscell.setPaddingBottom(10);
            phrasebilltimedetailscell.setPaddingTop(4);
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

         */


        try {
            PdfPCell addBorder_billTimeDetails = new PdfPCell(billtimeDetails_table);
            addBorder_billTimeDetails.setBorder(Rectangle.NO_BORDER);
            addBorder_billTimeDetails.setPaddingTop(5);
            addBorder_billTimeDetails.setBorderWidthBottom(01);
         //   wholePDFContentWithOut_Outline_table.addCell(addBorder_billTimeDetails);

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


                    Phrase phraseRetailerNameTitle = new Phrase(retailerName+"\n", valueFont_10);

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
                    text = retailerMobileNo.replaceAll("[+]91","");
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


                    Phrase phraseRetailerNameTitle = new Phrase(retailerAddress+"\n", valueFont_10);

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
                Whole_WarehouseDetails_table_Cell.setPaddingTop(5);

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
                Whole_Warehouse_and_RetailerDetails_table_Cell.setBorderWidthTop(01);
                wholePDFContentWithOut_Outline_table.addCell(Whole_Warehouse_and_RetailerDetails_table_Cell);

            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                PdfPTable EnptytableLabel_table = new PdfPTable(new float[]{100});


                try {
                    Phrase phraseParticularsLabelTitle = new Phrase("    ", valueFont_10Bold);

                    PdfPCell phraseParticularsLabelTitlecell = new PdfPCell(phraseParticularsLabelTitle);
                    phraseParticularsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseParticularsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phraseParticularsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                    EnptytableLabel_table.addCell(phraseParticularsLabelTitlecell);


                } catch (Exception e) {
                    e.printStackTrace();
                }



                try {
                    Phrase phraseParticularsLabelTitle = new Phrase("Transaction Details of "+ String.valueOf(retailerName)+" for the period ( From : "+(selectedStartDate)+" To : "+(selectedEndDate)+" ) ", valueFont_10);

                    PdfPCell phraseParticularsLabelTitlecell = new PdfPCell(phraseParticularsLabelTitle);
                    phraseParticularsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseParticularsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phraseParticularsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                    EnptytableLabel_table.addCell(phraseParticularsLabelTitlecell);


                } catch (Exception e) {
                    e.printStackTrace();
                }



                try {
                    Phrase phraseParticularsLabelTitle = new Phrase("    ", valueFont_10Bold);

                    PdfPCell phraseParticularsLabelTitlecell = new PdfPCell(phraseParticularsLabelTitle);
                    phraseParticularsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseParticularsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phraseParticularsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                    EnptytableLabel_table.addCell(phraseParticularsLabelTitlecell);



                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
                    PdfPCell itemDetails_table_Cell = new PdfPCell(EnptytableLabel_table);
                    itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                    itemDetails_table_Cell.setBorderWidthTop(1);
                    itemDetails_table_Cell.setBackgroundColor(WHITE);
                    wholePDFContentWithOut_Outline_table.addCell(itemDetails_table_Cell);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            catch (Exception e) {
                e.printStackTrace();
            }








            try{
                PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {19, 18 , 15 ,15, 15 , 18 });



                try {


                    Phrase phraseEarTagDetailsLabelTitle = new Phrase("Trans Date   ", valueFont_10Bold);

                    PdfPCell phraseEarTagDetailsLabelTitlecell = new PdfPCell(phraseEarTagDetailsLabelTitle);
                    phraseEarTagDetailsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseEarTagDetailsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phraseEarTagDetailsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                    phraseEarTagDetailsLabelTitlecell.setBorderWidthRight(01);
                    phraseEarTagDetailsLabelTitlecell.setPaddingLeft(6);
                    phraseEarTagDetailsLabelTitlecell.setPaddingBottom(10);
                    itemDetailsLabel_table.addCell(phraseEarTagDetailsLabelTitlecell);




                    Phrase phrasQuantityLabelTitle = new Phrase(" Particulars    ", valueFont_10Bold);

                    PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                    phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                    phraseQuantityLabelTitlecell.setBorderWidthRight(01);
                    phraseQuantityLabelTitlecell.setPaddingLeft(6);
                    phraseQuantityLabelTitlecell.setPaddingBottom(10);
                    itemDetailsLabel_table.addCell(phraseQuantityLabelTitlecell);




                    Phrase phrasBatchpriceLabelTitle = new Phrase("Amount  ", valueFont_10Bold);

                    PdfPCell phraseBatchPriceLabelTitlecell = new PdfPCell(phrasBatchpriceLabelTitle);
                    phraseBatchPriceLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseBatchPriceLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phraseBatchPriceLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                    phraseBatchPriceLabelTitlecell.setBorderWidthRight(01);
                    phraseBatchPriceLabelTitlecell.setPaddingLeft(6);
                    phraseBatchPriceLabelTitlecell.setPaddingBottom(10);
                    itemDetailsLabel_table.addCell(phraseBatchPriceLabelTitlecell);




                    Phrase phrasDebitCreditLabelTitle = new Phrase("DR / CR  ", valueFont_10Bold);

                    PdfPCell phraseDebitCreditLabelTitlecell = new PdfPCell(phrasDebitCreditLabelTitle);
                    phraseDebitCreditLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseDebitCreditLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phraseDebitCreditLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                    phraseDebitCreditLabelTitlecell.setBorderWidthRight(01);
                    phraseDebitCreditLabelTitlecell.setPaddingLeft(6);
                    phraseDebitCreditLabelTitlecell.setPaddingBottom(10);
                    itemDetailsLabel_table.addCell(phraseDebitCreditLabelTitlecell);


                    Phrase phrasTotalLabelTitle = new Phrase("Balance ", valueFont_10Bold);

                    PdfPCell phraseTotalLabelTitlecell = new PdfPCell(phrasTotalLabelTitle);
                    phraseTotalLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseTotalLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phraseTotalLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                    phraseTotalLabelTitlecell.setPaddingLeft(6);
                    phraseTotalLabelTitlecell.setPaddingBottom(10);
                    phraseTotalLabelTitlecell.setBorderWidthRight(01);

                    itemDetailsLabel_table.addCell(phraseTotalLabelTitlecell);




                    Phrase phrasNotesLabelTitle = new Phrase("Comments ", valueFont_10Bold);

                    PdfPCell phraseNotesLabelTitlecell = new PdfPCell(phrasNotesLabelTitle);
                    phraseNotesLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseNotesLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phraseNotesLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                    phraseNotesLabelTitlecell.setPaddingLeft(6);
                    phraseNotesLabelTitlecell.setPaddingBottom(10);
                    itemDetailsLabel_table.addCell(phraseNotesLabelTitlecell);

                }
                catch (Exception e){
                    e.printStackTrace();
                }




                try {
                    PdfPCell itemDetails_table_Cell = new PdfPCell(itemDetailsLabel_table);
                    itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                    itemDetails_table_Cell.setBorderWidthTop(1);
                    itemDetails_table_Cell.setBackgroundColor(LIGHT_GRAY);
                    wholePDFContentWithOut_Outline_table.addCell(itemDetails_table_Cell);


                }
                catch (Exception e) {
                    e.printStackTrace();
                }






            }
            catch (Exception e){
                e.printStackTrace();
            }

          /*

            try {
                for(int iterator = 0 ; iterator<paymentDetails_OrderDetails_ArrayList.size(); iterator++){
                    Modal__PaymentDetails_withOrderDetails modal__b2BPaymentDetails = paymentDetails_OrderDetails_ArrayList.get(iterator);
                    try{
                        PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {19, 18 , 15 ,15, 15 , 18  });



                        try {

                            String date = (modal__b2BPaymentDetails.getTransactiontime());
                            date = DateParser.convertDateTime_to_DisplayingDateOnlyFormat(date);
                            Phrase phraseEarTagDetailsLabelTitle = new Phrase(String.valueOf(date), valueFont_10);

                            PdfPCell phraseEarTagDetailsLabelTitlecell = new PdfPCell(phraseEarTagDetailsLabelTitle);
                            phraseEarTagDetailsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseEarTagDetailsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseEarTagDetailsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseEarTagDetailsLabelTitlecell.setBorderWidthRight(01);
                            phraseEarTagDetailsLabelTitlecell.setPaddingLeft(6);
                            phraseEarTagDetailsLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseEarTagDetailsLabelTitlecell);

                            Phrase phrasQuantityLabelTitle = null;
                            if(String.valueOf(modal__b2BPaymentDetails.getTransactiontype()).toUpperCase().equals(Constants.creditTransaction)){
                                phrasQuantityLabelTitle = new Phrase("Order Placed", valueFont_10);

                            }
                            else{
                                phrasQuantityLabelTitle = new Phrase("Payment via "+String.valueOf(modal__b2BPaymentDetails.getPaymentmode()), valueFont_10);

                            }


                            PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                            phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseQuantityLabelTitlecell.setBorderWidthRight(01);
                            phraseQuantityLabelTitlecell.setPaddingLeft(6);
                            phraseQuantityLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseQuantityLabelTitlecell);


                            String text = ""; double itemTotal = 0;
                            try{
                                text = String.valueOf(modal__b2BPaymentDetails.getTransactionvalue()).replaceAll("[^\\d.]", "");
                                if(text.equals("")){
                                    text ="0";
                                }

                            }
                            catch (Exception e){
                                text ="0";
                                e.printStackTrace();
                            }

                            try{
                                itemTotal = Double.parseDouble(text);
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }

                            try{
                                totalAmount = totalAmount + itemTotal;
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            if(paymentDetails_OrderDetails_ArrayList.size() - 1 == iterator){
                                closingBalance = (modal__b2BPaymentDetails.getCreditvalue());
                            }
                            try{
                                totalAmount = Double.parseDouble(twoDecimalConverter.format(String.valueOf(Math.round(totalAmount))));
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }

                            Phrase phrasBatchpriceLabelTitle = new Phrase(twoDecimalConverterWithComma.format(Double.parseDouble(String.valueOf(modal__b2BPaymentDetails.getTransactionvalue()))), valueFont_10);

                            PdfPCell phraseBatchPriceLabelTitlecell = new PdfPCell(phrasBatchpriceLabelTitle);
                            phraseBatchPriceLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseBatchPriceLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseBatchPriceLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseBatchPriceLabelTitlecell.setBorderWidthRight(01);
                            phraseBatchPriceLabelTitlecell.setPaddingLeft(6);
                            phraseBatchPriceLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseBatchPriceLabelTitlecell);


                            Phrase phrascreditDebitlabelTitle = new Phrase(String.valueOf(modal__b2BPaymentDetails.getTransactiontype()));
                            PdfPCell phrasecreditDebitLabelTitlecell = new PdfPCell(phrascreditDebitlabelTitle);
                            phrasecreditDebitLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phrasecreditDebitLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phrasecreditDebitLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phrasecreditDebitLabelTitlecell.setBorderWidthRight(01);
                            phrasecreditDebitLabelTitlecell.setPaddingLeft(6);
                            phrasecreditDebitLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phrasecreditDebitLabelTitlecell);


                            Phrase phrasTotalLabelTitle = new Phrase(twoDecimalConverterWithComma.format(Double.parseDouble(String.valueOf(modal__b2BPaymentDetails.getCreditvalue()))), valueFont_10);

                            PdfPCell phraseTotalLabelTitlecell = new PdfPCell(phrasTotalLabelTitle);
                            phraseTotalLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseTotalLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseTotalLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseTotalLabelTitlecell.setPaddingLeft(6);
                            phraseTotalLabelTitlecell.setBorderWidthRight(01);
                            phraseTotalLabelTitlecell.setPaddingBottom(10);


                            itemDetailsLabel_table.addCell(phraseTotalLabelTitlecell);




                            Phrase phrasNotesLabelTitle = new Phrase(String.valueOf(modal__b2BPaymentDetails.getNotes()), valueFont_10);

                            PdfPCell phraseNotesLabelTitlecell = new PdfPCell(phrasNotesLabelTitle);
                            phraseNotesLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseNotesLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseNotesLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseNotesLabelTitlecell.setPaddingLeft(6);
                            phraseNotesLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseNotesLabelTitlecell);

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }




                        try {
                            PdfPCell itemDetails_table_Cell = new PdfPCell(itemDetailsLabel_table);
                            itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                            itemDetails_table_Cell.setBorderWidthBottom(1);
                            itemDetails_table_Cell.setBackgroundColor(WHITE);
                            wholePDFContentWithOut_Outline_table.addCell(itemDetails_table_Cell);


                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }






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
            Toast.makeText(this, "Size : "+String.valueOf(paymentDetails_OrderDetails_ArrayList.size()), Toast.LENGTH_SHORT).show();

            if(paymentDetails_OrderDetails_ArrayList.size()>10){
                try {


                    for (int iterator = 0; iterator < 11; iterator++) {
                        Modal__PaymentDetails_withOrderDetails modal__b2BPaymentDetails = paymentDetails_OrderDetails_ArrayList.get(iterator);
                        try {
                            PdfPTable itemDetailsLabel_table  = new PdfPTable(new float[]{19, 18, 15, 15, 15, 18});


                            try {

                                String date = (modal__b2BPaymentDetails.getTransactiontime());
                                date = DateParser.convertDateTime_to_DisplayingDateOnlyFormat(date);
                                Phrase phraseEarTagDetailsLabelTitle = new Phrase(String.valueOf(date), valueFont_10);

                                PdfPCell phraseEarTagDetailsLabelTitlecell = new PdfPCell(phraseEarTagDetailsLabelTitle);
                                phraseEarTagDetailsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseEarTagDetailsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseEarTagDetailsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseEarTagDetailsLabelTitlecell.setBorderWidthRight(01);
                                phraseEarTagDetailsLabelTitlecell.setPaddingLeft(6);
                                phraseEarTagDetailsLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseEarTagDetailsLabelTitlecell);

                                Phrase phrasQuantityLabelTitle = null;
                                if (String.valueOf(modal__b2BPaymentDetails.getTransactiontype()).toUpperCase().equals(Constants.creditTransaction)) {
                                    phrasQuantityLabelTitle = new Phrase("Order Placed", valueFont_10);

                                } else {
                                    phrasQuantityLabelTitle = new Phrase("Payment via " + String.valueOf(modal__b2BPaymentDetails.getPaymentmode()), valueFont_10);

                                }


                                PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                                phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseQuantityLabelTitlecell.setBorderWidthRight(01);
                                phraseQuantityLabelTitlecell.setPaddingLeft(6);
                                phraseQuantityLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseQuantityLabelTitlecell);


                                String text = "";
                                double itemTotal = 0;
                                try {
                                    text = String.valueOf(modal__b2BPaymentDetails.getTransactionvalue()).replaceAll("[^\\d.]", "");
                                    if (text.equals("")) {
                                        text = "0";
                                    }

                                } catch (Exception e) {
                                    text = "0";
                                    e.printStackTrace();
                                }

                                try {
                                    itemTotal = Double.parseDouble(text);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                try {
                                    totalAmount = totalAmount + itemTotal;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (paymentDetails_OrderDetails_ArrayList.size() - 1 == iterator) {
                                    closingBalance = (modal__b2BPaymentDetails.getCreditvalue());
                                }
                                try {
                                    totalAmount = Double.parseDouble(twoDecimalConverter.format(String.valueOf(Math.round(totalAmount))));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                Phrase phrasBatchpriceLabelTitle = new Phrase(twoDecimalConverterWithComma.format(Double.parseDouble(String.valueOf(modal__b2BPaymentDetails.getTransactionvalue()))), valueFont_10);

                                PdfPCell phraseBatchPriceLabelTitlecell = new PdfPCell(phrasBatchpriceLabelTitle);
                                phraseBatchPriceLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseBatchPriceLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseBatchPriceLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseBatchPriceLabelTitlecell.setBorderWidthRight(01);
                                phraseBatchPriceLabelTitlecell.setPaddingLeft(6);
                                phraseBatchPriceLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseBatchPriceLabelTitlecell);


                                Phrase phrascreditDebitlabelTitle = new Phrase(String.valueOf(modal__b2BPaymentDetails.getTransactiontype()));
                                PdfPCell phrasecreditDebitLabelTitlecell = new PdfPCell(phrascreditDebitlabelTitle);
                                phrasecreditDebitLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phrasecreditDebitLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phrasecreditDebitLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phrasecreditDebitLabelTitlecell.setBorderWidthRight(01);
                                phrasecreditDebitLabelTitlecell.setPaddingLeft(6);
                                phrasecreditDebitLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phrasecreditDebitLabelTitlecell);


                                Phrase phrasTotalLabelTitle = new Phrase(twoDecimalConverterWithComma.format(Double.parseDouble(String.valueOf(modal__b2BPaymentDetails.getCreditvalue()))), valueFont_10);

                                PdfPCell phraseTotalLabelTitlecell = new PdfPCell(phrasTotalLabelTitle);
                                phraseTotalLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseTotalLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseTotalLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseTotalLabelTitlecell.setPaddingLeft(6);
                                phraseTotalLabelTitlecell.setBorderWidthRight(01);
                                phraseTotalLabelTitlecell.setPaddingBottom(10);


                                itemDetailsLabel_table.addCell(phraseTotalLabelTitlecell);


                                Phrase phrasNotesLabelTitle = new Phrase(String.valueOf(modal__b2BPaymentDetails.getNotes()), valueFont_10);

                                PdfPCell phraseNotesLabelTitlecell = new PdfPCell(phrasNotesLabelTitle);
                                phraseNotesLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseNotesLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseNotesLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseNotesLabelTitlecell.setPaddingLeft(6);
                                phraseNotesLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseNotesLabelTitlecell);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try{

                                try {
                                    PdfPCell itemDetails_table_Cell = new PdfPCell(itemDetailsLabel_table);
                                    itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                                    itemDetails_table_Cell.setBorderWidthBottom(1);
                                    itemDetails_table_Cell.setBackgroundColor(WHITE);
                                    wholePDFContentWithOut_Outline_table.addCell(itemDetails_table_Cell);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                tmcLogoImage_table.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tmcLogoImage_table.setTotalWidth(10f);
                layoutDocument.add(tmcLogoImage_table);

                PdfPCell wholePDFWithOutBordercell = new PdfPCell(wholePDFContentWithOut_Outline_table);
                wholePDFWithOutBordercell.setCellEvent(roundRectange);
                wholePDFWithOutBordercell.setPadding(1);
                wholePDFWithOutBordercell.setBorder(Rectangle.NO_BORDER);
                wholePDFContentOutline_table.addCell(wholePDFWithOutBordercell);
                wholePDFContentOutline_table.setWidthPercentage(100);


                layoutDocument.add(wholePDFContentOutline_table);
                PdfPTable wholePDFContentWithOut_Outline_table2 = new PdfPTable(1);
                try{
                    layoutDocument.newPage();



                    try {


                        for (int iterator = 11; iterator < paymentDetails_OrderDetails_ArrayList.size(); iterator++) {
                            Modal__PaymentDetails_withOrderDetails modal__b2BPaymentDetails = paymentDetails_OrderDetails_ArrayList.get(iterator);
                            try {
                                PdfPTable itemDetailsLabel_table  = new PdfPTable(new float[]{19, 18, 15, 15, 15, 18});


                                try {

                                    String date = (modal__b2BPaymentDetails.getTransactiontime());
                                    date = DateParser.convertDateTime_to_DisplayingDateOnlyFormat(date);
                                    Phrase phraseEarTagDetailsLabelTitle = new Phrase(String.valueOf(date), valueFont_10);

                                    PdfPCell phraseEarTagDetailsLabelTitlecell = new PdfPCell(phraseEarTagDetailsLabelTitle);
                                    phraseEarTagDetailsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                    phraseEarTagDetailsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    phraseEarTagDetailsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                    phraseEarTagDetailsLabelTitlecell.setBorderWidthRight(01);
                                    phraseEarTagDetailsLabelTitlecell.setPaddingLeft(6);
                                    phraseEarTagDetailsLabelTitlecell.setPaddingBottom(10);
                                    itemDetailsLabel_table.addCell(phraseEarTagDetailsLabelTitlecell);

                                    Phrase phrasQuantityLabelTitle = null;
                                    if (String.valueOf(modal__b2BPaymentDetails.getTransactiontype()).toUpperCase().equals(Constants.creditTransaction)) {
                                        phrasQuantityLabelTitle = new Phrase("Order Placed", valueFont_10);

                                    } else {
                                        phrasQuantityLabelTitle = new Phrase("Payment via " + String.valueOf(modal__b2BPaymentDetails.getPaymentmode()), valueFont_10);

                                    }


                                    PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                                    phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                    phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                    phraseQuantityLabelTitlecell.setBorderWidthRight(01);
                                    phraseQuantityLabelTitlecell.setPaddingLeft(6);
                                    phraseQuantityLabelTitlecell.setPaddingBottom(10);
                                    itemDetailsLabel_table.addCell(phraseQuantityLabelTitlecell);


                                    String text = "";
                                    double itemTotal = 0;
                                    try {
                                        text = String.valueOf(modal__b2BPaymentDetails.getTransactionvalue()).replaceAll("[^\\d.]", "");
                                        if (text.equals("")) {
                                            text = "0";
                                        }

                                    } catch (Exception e) {
                                        text = "0";
                                        e.printStackTrace();
                                    }

                                    try {
                                        itemTotal = Double.parseDouble(text);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    try {
                                        totalAmount = totalAmount + itemTotal;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (paymentDetails_OrderDetails_ArrayList.size() - 1 == iterator) {
                                        closingBalance = (modal__b2BPaymentDetails.getCreditvalue());
                                    }
                                    try {
                                        totalAmount = Double.parseDouble(twoDecimalConverter.format(String.valueOf(Math.round(totalAmount))));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    Phrase phrasBatchpriceLabelTitle = new Phrase(twoDecimalConverterWithComma.format(Double.parseDouble(String.valueOf(modal__b2BPaymentDetails.getTransactionvalue()))), valueFont_10);

                                    PdfPCell phraseBatchPriceLabelTitlecell = new PdfPCell(phrasBatchpriceLabelTitle);
                                    phraseBatchPriceLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                    phraseBatchPriceLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    phraseBatchPriceLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                    phraseBatchPriceLabelTitlecell.setBorderWidthRight(01);
                                    phraseBatchPriceLabelTitlecell.setPaddingLeft(6);
                                    phraseBatchPriceLabelTitlecell.setPaddingBottom(10);
                                    itemDetailsLabel_table.addCell(phraseBatchPriceLabelTitlecell);


                                    Phrase phrascreditDebitlabelTitle = new Phrase(String.valueOf(modal__b2BPaymentDetails.getTransactiontype()));
                                    PdfPCell phrasecreditDebitLabelTitlecell = new PdfPCell(phrascreditDebitlabelTitle);
                                    phrasecreditDebitLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                    phrasecreditDebitLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    phrasecreditDebitLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                    phrasecreditDebitLabelTitlecell.setBorderWidthRight(01);
                                    phrasecreditDebitLabelTitlecell.setPaddingLeft(6);
                                    phrasecreditDebitLabelTitlecell.setPaddingBottom(10);
                                    itemDetailsLabel_table.addCell(phrasecreditDebitLabelTitlecell);


                                    Phrase phrasTotalLabelTitle = new Phrase(twoDecimalConverterWithComma.format(Double.parseDouble(String.valueOf(modal__b2BPaymentDetails.getCreditvalue()))), valueFont_10);

                                    PdfPCell phraseTotalLabelTitlecell = new PdfPCell(phrasTotalLabelTitle);
                                    phraseTotalLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                    phraseTotalLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    phraseTotalLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                    phraseTotalLabelTitlecell.setPaddingLeft(6);
                                    phraseTotalLabelTitlecell.setBorderWidthRight(01);
                                    phraseTotalLabelTitlecell.setPaddingBottom(10);


                                    itemDetailsLabel_table.addCell(phraseTotalLabelTitlecell);


                                    Phrase phrasNotesLabelTitle = new Phrase(String.valueOf(modal__b2BPaymentDetails.getNotes()), valueFont_10);

                                    PdfPCell phraseNotesLabelTitlecell = new PdfPCell(phrasNotesLabelTitle);
                                    phraseNotesLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                    phraseNotesLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    phraseNotesLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                    phraseNotesLabelTitlecell.setPaddingLeft(6);
                                    phraseNotesLabelTitlecell.setPaddingBottom(10);
                                    itemDetailsLabel_table.addCell(phraseNotesLabelTitlecell);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try{

                                    try {
                                        PdfPCell itemDetails_table_Cell = new PdfPCell(itemDetailsLabel_table);
                                        itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                                        itemDetails_table_Cell.setBorderWidthBottom(1);
                                        itemDetails_table_Cell.setBackgroundColor(WHITE);
                                        wholePDFContentWithOut_Outline_table2.addCell(itemDetails_table_Cell);


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }



                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }



                    //extraas
                    try{




                        try{
                            PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {19, 18 , 15 ,15, 15 , 18 });



                            try {


                                Phrase phraseEarTagDetailsLabelTitle = new Phrase(String.valueOf("."), valueFont_1);

                                PdfPCell phraseEarTagDetailsLabelTitlecell = new PdfPCell(phraseEarTagDetailsLabelTitle);
                                phraseEarTagDetailsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseEarTagDetailsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseEarTagDetailsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseEarTagDetailsLabelTitlecell.setBorderWidthRight(01);
                                phraseEarTagDetailsLabelTitlecell.setPaddingLeft(6);
                                phraseEarTagDetailsLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseEarTagDetailsLabelTitlecell);




                                Phrase phrasQuantityLabelTitle = new Phrase("  ", valueFont_10);

                                PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                                phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseQuantityLabelTitlecell.setBorderWidthRight(01);
                                phraseQuantityLabelTitlecell.setPaddingLeft(6);
                                phraseQuantityLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseQuantityLabelTitlecell);





                                Phrase phrasBatchpriceLabelTitle = new Phrase(String.valueOf("  "), valueFont_10);

                                PdfPCell phraseBatchPriceLabelTitlecell = new PdfPCell(phrasBatchpriceLabelTitle);
                                phraseBatchPriceLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseBatchPriceLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseBatchPriceLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseBatchPriceLabelTitlecell.setBorderWidthRight(01);
                                phraseBatchPriceLabelTitlecell.setPaddingLeft(6);
                                phraseBatchPriceLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseBatchPriceLabelTitlecell);

                                Phrase phrasDebitCreditLabelTitle = new Phrase("  ", valueFont_10);

                                PdfPCell phraseDebitCreditLabelTitlecell = new PdfPCell(phrasDebitCreditLabelTitle);
                                phraseDebitCreditLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseDebitCreditLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseDebitCreditLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseDebitCreditLabelTitlecell.setBorderWidthRight(01);
                                phraseDebitCreditLabelTitlecell.setPaddingLeft(6);
                                phraseDebitCreditLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseDebitCreditLabelTitlecell);




                                Phrase phrasTotalLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                                PdfPCell phraseTotalLabelTitlecell = new PdfPCell(phrasTotalLabelTitle);
                                phraseTotalLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseTotalLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseTotalLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseTotalLabelTitlecell.setPaddingLeft(6);
                                phraseTotalLabelTitlecell.setBorderWidthRight(01);
                                phraseTotalLabelTitlecell.setPaddingBottom(10);


                                itemDetailsLabel_table.addCell(phraseTotalLabelTitlecell);




                                Phrase phrasNotesLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                                PdfPCell phraseNotesLabelTitlecell = new PdfPCell(phrasNotesLabelTitle);
                                phraseNotesLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseNotesLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseNotesLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseNotesLabelTitlecell.setPaddingLeft(6);
                                phraseNotesLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseNotesLabelTitlecell);

                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }




                            try {
                                PdfPCell itemDetails_table_Cell = new PdfPCell(itemDetailsLabel_table);
                                itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                                itemDetails_table_Cell.setBorderWidthBottom(1);
                                itemDetails_table_Cell.setBackgroundColor(WHITE);
                                wholePDFContentWithOut_Outline_table2.addCell(itemDetails_table_Cell);


                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }






                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }



                        try{
                            PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {19, 18 , 15 ,15, 15 , 18 });



                            try {


                                Phrase phraseEarTagDetailsLabelTitle = new Phrase(String.valueOf("."), valueFont_1);

                                PdfPCell phraseEarTagDetailsLabelTitlecell = new PdfPCell(phraseEarTagDetailsLabelTitle);
                                phraseEarTagDetailsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseEarTagDetailsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseEarTagDetailsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseEarTagDetailsLabelTitlecell.setBorderWidthRight(01);
                                phraseEarTagDetailsLabelTitlecell.setPaddingLeft(6);
                                phraseEarTagDetailsLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseEarTagDetailsLabelTitlecell);




                                Phrase phrasQuantityLabelTitle = new Phrase("  ", valueFont_10);

                                PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                                phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseQuantityLabelTitlecell.setBorderWidthRight(01);
                                phraseQuantityLabelTitlecell.setPaddingLeft(6);
                                phraseQuantityLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseQuantityLabelTitlecell);





                                Phrase phrasBatchpriceLabelTitle = new Phrase(String.valueOf("  "), valueFont_10);

                                PdfPCell phraseBatchPriceLabelTitlecell = new PdfPCell(phrasBatchpriceLabelTitle);
                                phraseBatchPriceLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseBatchPriceLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseBatchPriceLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseBatchPriceLabelTitlecell.setBorderWidthRight(01);
                                phraseBatchPriceLabelTitlecell.setPaddingLeft(6);
                                phraseBatchPriceLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseBatchPriceLabelTitlecell);

                                Phrase phrasDebitCreditLabelTitle = new Phrase("  ", valueFont_10);

                                PdfPCell phraseDebitCreditLabelTitlecell = new PdfPCell(phrasDebitCreditLabelTitle);
                                phraseDebitCreditLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseDebitCreditLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseDebitCreditLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseDebitCreditLabelTitlecell.setBorderWidthRight(01);
                                phraseDebitCreditLabelTitlecell.setPaddingLeft(6);
                                phraseDebitCreditLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseDebitCreditLabelTitlecell);




                                Phrase phrasTotalLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                                PdfPCell phraseTotalLabelTitlecell = new PdfPCell(phrasTotalLabelTitle);
                                phraseTotalLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseTotalLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseTotalLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseTotalLabelTitlecell.setPaddingLeft(6);
                                phraseTotalLabelTitlecell.setBorderWidthRight(01);
                                phraseTotalLabelTitlecell.setPaddingBottom(10);


                                itemDetailsLabel_table.addCell(phraseTotalLabelTitlecell);




                                Phrase phrasNotesLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                                PdfPCell phraseNotesLabelTitlecell = new PdfPCell(phrasNotesLabelTitle);
                                phraseNotesLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseNotesLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseNotesLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseNotesLabelTitlecell.setPaddingLeft(6);
                                phraseNotesLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseNotesLabelTitlecell);

                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }




                            try {
                                PdfPCell itemDetails_table_Cell = new PdfPCell(itemDetailsLabel_table);
                                itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                                itemDetails_table_Cell.setBorderWidthBottom(1);
                                itemDetails_table_Cell.setBackgroundColor(WHITE);
                                wholePDFContentWithOut_Outline_table2.addCell(itemDetails_table_Cell);


                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }






                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }



                        try{
                            PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {19, 18 , 15 ,15, 15 , 18 });



                            try {


                                Phrase phraseEarTagDetailsLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                                PdfPCell phraseEarTagDetailsLabelTitlecell = new PdfPCell(phraseEarTagDetailsLabelTitle);
                                phraseEarTagDetailsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseEarTagDetailsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseEarTagDetailsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseEarTagDetailsLabelTitlecell.setBorderWidthRight(01);
                                phraseEarTagDetailsLabelTitlecell.setPaddingLeft(6);
                                phraseEarTagDetailsLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseEarTagDetailsLabelTitlecell);




                                Phrase phrasQuantityLabelTitle = new Phrase("Transaction Total", valueFont_10);

                                PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                                phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseQuantityLabelTitlecell.setBorderWidthRight(01);
                                phraseQuantityLabelTitlecell.setPaddingLeft(6);
                                phraseQuantityLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseQuantityLabelTitlecell);


                                Phrase phrasBatchpriceLabelTitle = new Phrase(String.valueOf(twoDecimalConverterWithComma.format(totalAmount)), valueFont_10);

                                PdfPCell phraseBatchPriceLabelTitlecell = new PdfPCell(phrasBatchpriceLabelTitle);
                                phraseBatchPriceLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseBatchPriceLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseBatchPriceLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseBatchPriceLabelTitlecell.setBorderWidthRight(01);
                                phraseBatchPriceLabelTitlecell.setPaddingLeft(6);
                                phraseBatchPriceLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseBatchPriceLabelTitlecell);




                                Phrase phrasDebitCreditLabelTitle = new Phrase("  ", valueFont_10);

                                PdfPCell phraseDebitCreditLabelTitlecell = new PdfPCell(phrasDebitCreditLabelTitle);
                                phraseDebitCreditLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseDebitCreditLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseDebitCreditLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseDebitCreditLabelTitlecell.setBorderWidthRight(01);
                                phraseDebitCreditLabelTitlecell.setPaddingLeft(6);
                                phraseDebitCreditLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseDebitCreditLabelTitlecell);



                                Phrase phrasTotalLabelTitle = new Phrase("", valueFont_10);

                                PdfPCell phraseTotalLabelTitlecell = new PdfPCell(phrasTotalLabelTitle);
                                phraseTotalLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseTotalLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseTotalLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseTotalLabelTitlecell.setPaddingLeft(6);
                                phraseTotalLabelTitlecell.setBorderWidthRight(01);
                                phraseTotalLabelTitlecell.setPaddingBottom(10);


                                itemDetailsLabel_table.addCell(phraseTotalLabelTitlecell);




                                Phrase phrasNotesLabelTitle = new Phrase(".", valueFont_10);

                                PdfPCell phraseNotesLabelTitlecell = new PdfPCell(phrasNotesLabelTitle);
                                phraseNotesLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseNotesLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseNotesLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseNotesLabelTitlecell.setPaddingLeft(6);
                                phraseNotesLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseNotesLabelTitlecell);

                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }




                            try {
                                PdfPCell itemDetails_table_Cell = new PdfPCell(itemDetailsLabel_table);
                                itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                                itemDetails_table_Cell.setBorderWidthBottom(1);
                                itemDetails_table_Cell.setBackgroundColor(WHITE);
                                wholePDFContentWithOut_Outline_table2.addCell(itemDetails_table_Cell);


                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }






                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }




                        try{
                            PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {19, 18 , 15 ,15, 15 , 18 });



                            try {


                                Phrase phraseEarTagDetailsLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                                PdfPCell phraseEarTagDetailsLabelTitlecell = new PdfPCell(phraseEarTagDetailsLabelTitle);
                                phraseEarTagDetailsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseEarTagDetailsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseEarTagDetailsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseEarTagDetailsLabelTitlecell.setBorderWidthRight(01);
                                phraseEarTagDetailsLabelTitlecell.setPaddingLeft(6);
                                phraseEarTagDetailsLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseEarTagDetailsLabelTitlecell);




                                Phrase phrasQuantityLabelTitle = new Phrase("Closing Balance", valueFont_10);

                                PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                                phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseQuantityLabelTitlecell.setBorderWidthRight(01);
                                phraseQuantityLabelTitlecell.setPaddingLeft(6);
                                phraseQuantityLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseQuantityLabelTitlecell);


                                Phrase phrasBatchpriceLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                                PdfPCell phraseBatchPriceLabelTitlecell = new PdfPCell(phrasBatchpriceLabelTitle);
                                phraseBatchPriceLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseBatchPriceLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseBatchPriceLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseBatchPriceLabelTitlecell.setBorderWidthRight(01);
                                phraseBatchPriceLabelTitlecell.setPaddingLeft(6);
                                phraseBatchPriceLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseBatchPriceLabelTitlecell);




                                Phrase phrasDebitCreditLabelTitle = new Phrase("  ", valueFont_10);

                                PdfPCell phraseDebitCreditLabelTitlecell = new PdfPCell(phrasDebitCreditLabelTitle);
                                phraseDebitCreditLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseDebitCreditLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseDebitCreditLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseDebitCreditLabelTitlecell.setBorderWidthRight(01);
                                phraseDebitCreditLabelTitlecell.setPaddingLeft(6);
                                phraseDebitCreditLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseDebitCreditLabelTitlecell);




                                Phrase phrasTotalLabelTitle = new Phrase(String.valueOf(twoDecimalConverterWithComma.format(Double.parseDouble(closingBalance))), valueFont_10);

                                PdfPCell phraseTotalLabelTitlecell = new PdfPCell(phrasTotalLabelTitle);
                                phraseTotalLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseTotalLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseTotalLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseTotalLabelTitlecell.setPaddingLeft(6);
                                phraseTotalLabelTitlecell.setBorderWidthRight(01);
                                phraseTotalLabelTitlecell.setPaddingBottom(10);


                                itemDetailsLabel_table.addCell(phraseTotalLabelTitlecell);




                                Phrase phrasNotesLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                                PdfPCell phraseNotesLabelTitlecell = new PdfPCell(phrasNotesLabelTitle);
                                phraseNotesLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseNotesLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseNotesLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseNotesLabelTitlecell.setPaddingLeft(6);
                                phraseNotesLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseNotesLabelTitlecell);

                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }




                            try {
                                PdfPCell itemDetails_table_Cell = new PdfPCell(itemDetailsLabel_table);
                                itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                                itemDetails_table_Cell.setBorderWidthBottom(1);
                                itemDetails_table_Cell.setBackgroundColor(WHITE);
                                wholePDFContentWithOut_Outline_table2.addCell(itemDetails_table_Cell);


                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }






                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                        try{
                            PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {19, 18 , 15 ,15, 15 , 18 });



                            try {


                                Phrase phraseEarTagDetailsLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                                PdfPCell phraseEarTagDetailsLabelTitlecell = new PdfPCell(phraseEarTagDetailsLabelTitle);
                                phraseEarTagDetailsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseEarTagDetailsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseEarTagDetailsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseEarTagDetailsLabelTitlecell.setBorderWidthRight(01);
                                phraseEarTagDetailsLabelTitlecell.setPaddingLeft(6);
                                phraseEarTagDetailsLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseEarTagDetailsLabelTitlecell);




                                Phrase phrasQuantityLabelTitle = new Phrase("", valueFont_10);

                                PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                                phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseQuantityLabelTitlecell.setBorderWidthRight(01);
                                phraseQuantityLabelTitlecell.setPaddingLeft(6);
                                phraseQuantityLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseQuantityLabelTitlecell);


                                Phrase phrasBatchpriceLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                                PdfPCell phraseBatchPriceLabelTitlecell = new PdfPCell(phrasBatchpriceLabelTitle);
                                phraseBatchPriceLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseBatchPriceLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseBatchPriceLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseBatchPriceLabelTitlecell.setBorderWidthRight(01);
                                phraseBatchPriceLabelTitlecell.setPaddingLeft(6);
                                phraseBatchPriceLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseBatchPriceLabelTitlecell);




                                Phrase phrasDebitCreditLabelTitle = new Phrase("  ", valueFont_10);

                                PdfPCell phraseDebitCreditLabelTitlecell = new PdfPCell(phrasDebitCreditLabelTitle);
                                phraseDebitCreditLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseDebitCreditLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseDebitCreditLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseDebitCreditLabelTitlecell.setBorderWidthRight(01);
                                phraseDebitCreditLabelTitlecell.setPaddingLeft(6);
                                phraseDebitCreditLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseDebitCreditLabelTitlecell);


                                Phrase phrasTotalLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                                PdfPCell phraseTotalLabelTitlecell = new PdfPCell(phrasTotalLabelTitle);
                                phraseTotalLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseTotalLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseTotalLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseTotalLabelTitlecell.setPaddingLeft(6);
                                phraseTotalLabelTitlecell.setBorderWidthRight(01);
                                phraseTotalLabelTitlecell.setPaddingBottom(10);


                                itemDetailsLabel_table.addCell(phraseTotalLabelTitlecell);




                                Phrase phrasNotesLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                                PdfPCell phraseNotesLabelTitlecell = new PdfPCell(phrasNotesLabelTitle);
                                phraseNotesLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseNotesLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseNotesLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseNotesLabelTitlecell.setPaddingLeft(6);
                                phraseNotesLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseNotesLabelTitlecell);

                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }




                            try {
                                PdfPCell itemDetails_table_Cell = new PdfPCell(itemDetailsLabel_table);
                                itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                                itemDetails_table_Cell.setBorderWidthBottom(1);
                                itemDetails_table_Cell.setBackgroundColor(WHITE);
                                wholePDFContentWithOut_Outline_table2.addCell(itemDetails_table_Cell);


                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }






                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                        try {
                            PdfPTable EnptytableLabel_table = new PdfPTable(new float[]{35, 65});


                            try {
                                Phrase phraseParticularsLabelTitle = new Phrase("    ", valueFont_10Bold);

                                PdfPCell phraseParticularsLabelTitlecell = new PdfPCell(phraseParticularsLabelTitle);
                                phraseParticularsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseParticularsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                                phraseParticularsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseParticularsLabelTitlecell.setPaddingLeft(6);
                                phraseParticularsLabelTitlecell.setPaddingTop(5);
                                phraseParticularsLabelTitlecell.setPaddingBottom(10);
                                EnptytableLabel_table.addCell(phraseParticularsLabelTitlecell);


                                Phrase phrasQuantityLabelTitle = new Phrase("     ", valueFont_10Bold);
                                PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                                phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseQuantityLabelTitlecell.setPaddingTop(5);
                                phraseQuantityLabelTitlecell.setPaddingLeft(6);
                                phraseQuantityLabelTitlecell.setPaddingBottom(10);
                                EnptytableLabel_table.addCell(phraseQuantityLabelTitlecell);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                PdfPCell itemDetails_table_Cell = new PdfPCell(EnptytableLabel_table);
                                itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                                itemDetails_table_Cell.setBackgroundColor(WHITE);
                                wholePDFContentWithOut_Outline_table2.addCell(itemDetails_table_Cell);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            PdfPTable EnptytableLabel_table = new PdfPTable(new float[]{35, 65});


                            try {
                                Phrase phraseParticularsLabelTitle = new Phrase("    ", valueFont_10Bold);

                                PdfPCell phraseParticularsLabelTitlecell = new PdfPCell(phraseParticularsLabelTitle);
                                phraseParticularsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseParticularsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                                phraseParticularsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseParticularsLabelTitlecell.setPaddingLeft(6);
                                phraseParticularsLabelTitlecell.setPaddingTop(5);
                                phraseParticularsLabelTitlecell.setPaddingBottom(10);
                                EnptytableLabel_table.addCell(phraseParticularsLabelTitlecell);


                                Phrase phrasQuantityLabelTitle = new Phrase("     ", valueFont_10Bold);
                                PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                                phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseQuantityLabelTitlecell.setPaddingTop(5);
                                phraseQuantityLabelTitlecell.setPaddingLeft(6);
                                phraseQuantityLabelTitlecell.setPaddingBottom(10);
                                EnptytableLabel_table.addCell(phraseQuantityLabelTitlecell);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                PdfPCell itemDetails_table_Cell = new PdfPCell(EnptytableLabel_table);
                                itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                                itemDetails_table_Cell.setBackgroundColor(WHITE);
                                wholePDFContentWithOut_Outline_table2.addCell(itemDetails_table_Cell);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            PdfPTable EnptytableLabel_table = new PdfPTable(new float[]{35, 65});


                            try {
                                Phrase phraseParticularsLabelTitle = new Phrase("    ", valueFont_10Bold);

                                PdfPCell phraseParticularsLabelTitlecell = new PdfPCell(phraseParticularsLabelTitle);
                                phraseParticularsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseParticularsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                                phraseParticularsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseParticularsLabelTitlecell.setPaddingLeft(6);
                                phraseParticularsLabelTitlecell.setPaddingTop(5);
                                phraseParticularsLabelTitlecell.setPaddingBottom(10);
                                EnptytableLabel_table.addCell(phraseParticularsLabelTitlecell);


                                Phrase phrasQuantityLabelTitle = new Phrase("     ", valueFont_10Bold);
                                PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                                phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                                phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseQuantityLabelTitlecell.setPaddingTop(5);
                                phraseQuantityLabelTitlecell.setPaddingLeft(6);
                                phraseQuantityLabelTitlecell.setPaddingBottom(10);
                                EnptytableLabel_table.addCell(phraseQuantityLabelTitlecell);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                PdfPCell itemDetails_table_Cell = new PdfPCell(EnptytableLabel_table);
                                itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                                itemDetails_table_Cell.setBackgroundColor(WHITE);
                                wholePDFContentWithOut_Outline_table2.addCell(itemDetails_table_Cell);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    catch (Exception e ){
                        e.printStackTrace();
                    }


                    //finaladd
                    try {





        /*    PdfPCell wholePDFContentWithImage_and_tableimagecell = new PdfPCell(tmcLogoImage_table);
            wholePDFContentWithImage_and_tableimagecell.setCellEvent(roundRectange);
            wholePDFContentWithImage_and_tableimagecell.setPadding(1);
            wholePDFContentWithImage_and_tableimagecell.setBorder(Rectangle.NO_BORDER);
            wholePDFContentWithImage_and_table.addCell(wholePDFContentWithImage_and_tableimagecell);
            wholePDFContentWithImage_and_table.setWidthPercentage(100);





            PdfPCell wholePDFContentWithImage_and_tablebordeercell = new PdfPCell(wholePDFContentWithOut_Outline_table);
            wholePDFContentWithImage_and_tablebordeercell.setCellEvent(roundRectange);
            wholePDFContentWithImage_and_tablebordeercell.setPadding(1);
            wholePDFContentWithImage_and_tablebordeercell.setBorder(Rectangle.NO_BORDER);
            wholePDFContentWithImage_and_table.addCell(wholePDFContentWithImage_and_tablebordeercell);
            wholePDFContentWithImage_and_table.setWidthPercentage(100);


         */

                        PdfPTable wholePDFContentOutline_table2 = new PdfPTable(1);


                        PdfPCell wholePDFWithOutBordercell2 = new PdfPCell(wholePDFContentWithOut_Outline_table2);
                        wholePDFWithOutBordercell2.setCellEvent(roundRectange);
                        wholePDFWithOutBordercell2.setPadding(1);
                        wholePDFWithOutBordercell2.setBorder(Rectangle.NO_BORDER);
                        wholePDFContentOutline_table2.addCell(wholePDFWithOutBordercell2);
                        wholePDFContentOutline_table2.setWidthPercentage(100);


                        layoutDocument.add(wholePDFContentOutline_table2);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }
            else {
                try {
                    for (int iterator = 0; iterator < paymentDetails_OrderDetails_ArrayList.size(); iterator++) {
                        Modal__PaymentDetails_withOrderDetails modal__b2BPaymentDetails = paymentDetails_OrderDetails_ArrayList.get(iterator);
                        try {
                            PdfPTable itemDetailsLabel_table = new PdfPTable(new float[]{19, 18, 15, 15, 15, 18});


                            try {

                                String date = (modal__b2BPaymentDetails.getTransactiontime());
                                date = DateParser.convertDateTime_to_DisplayingDateOnlyFormat(date);
                                Phrase phraseEarTagDetailsLabelTitle = new Phrase(String.valueOf(date), valueFont_10);

                                PdfPCell phraseEarTagDetailsLabelTitlecell = new PdfPCell(phraseEarTagDetailsLabelTitle);
                                phraseEarTagDetailsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseEarTagDetailsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseEarTagDetailsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseEarTagDetailsLabelTitlecell.setBorderWidthRight(01);
                                phraseEarTagDetailsLabelTitlecell.setPaddingLeft(6);
                                phraseEarTagDetailsLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseEarTagDetailsLabelTitlecell);

                                Phrase phrasQuantityLabelTitle = null;
                                if (String.valueOf(modal__b2BPaymentDetails.getTransactiontype()).toUpperCase().equals(Constants.creditTransaction)) {
                                    phrasQuantityLabelTitle = new Phrase("Order Placed", valueFont_10);

                                } else {
                                    phrasQuantityLabelTitle = new Phrase("Payment via " + String.valueOf(modal__b2BPaymentDetails.getPaymentmode()), valueFont_10);

                                }


                                PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                                phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseQuantityLabelTitlecell.setBorderWidthRight(01);
                                phraseQuantityLabelTitlecell.setPaddingLeft(6);
                                phraseQuantityLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseQuantityLabelTitlecell);


                                String text = "";
                                double itemTotal = 0;
                                try {
                                    text = String.valueOf(modal__b2BPaymentDetails.getTransactionvalue()).replaceAll("[^\\d.]", "");
                                    if (text.equals("")) {
                                        text = "0";
                                    }

                                } catch (Exception e) {
                                    text = "0";
                                    e.printStackTrace();
                                }

                                try {
                                    itemTotal = Double.parseDouble(text);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                try {
                                    totalAmount = totalAmount + itemTotal;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (paymentDetails_OrderDetails_ArrayList.size() - 1 == iterator) {
                                    closingBalance = (modal__b2BPaymentDetails.getCreditvalue());
                                }
                                double total_amtlocal =totalAmount;
                                try {
                                    totalAmount = Double.parseDouble(twoDecimalConverter.format(String.valueOf(Math.round(totalAmount))));
                                } catch (Exception e) {
                                try{
                                totalAmount = total_amtlocal;

                                }
                                catch (Exception e1){
                                e1.printStackTrace();
                                }
                                e.printStackTrace();
                                }


                                try {
                                    Phrase phrasBatchpriceLabelTitle = null;
                                    if(modal__b2BPaymentDetails.getTransactionvalue().trim().equals("")){
                                        phrasBatchpriceLabelTitle = new Phrase(twoDecimalConverterWithComma.format(Double.parseDouble(String.valueOf("0"))), valueFont_10);


                                    }
                                    else{
                                        phrasBatchpriceLabelTitle = new Phrase(twoDecimalConverterWithComma.format(Double.parseDouble(String.valueOf(modal__b2BPaymentDetails.getTransactionvalue()))), valueFont_10);

                                    }


                                    PdfPCell phraseBatchPriceLabelTitlecell = new PdfPCell(phrasBatchpriceLabelTitle);
                                    phraseBatchPriceLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                    phraseBatchPriceLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    phraseBatchPriceLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                    phraseBatchPriceLabelTitlecell.setBorderWidthRight(01);
                                    phraseBatchPriceLabelTitlecell.setPaddingLeft(6);
                                    phraseBatchPriceLabelTitlecell.setPaddingBottom(10);
                                    itemDetailsLabel_table.addCell(phraseBatchPriceLabelTitlecell);
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }

                                Phrase phrascreditDebitlabelTitle = new Phrase(String.valueOf(modal__b2BPaymentDetails.getTransactiontype()));
                                PdfPCell phrasecreditDebitLabelTitlecell = new PdfPCell(phrascreditDebitlabelTitle);
                                phrasecreditDebitLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phrasecreditDebitLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phrasecreditDebitLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phrasecreditDebitLabelTitlecell.setBorderWidthRight(01);
                                phrasecreditDebitLabelTitlecell.setPaddingLeft(6);
                                phrasecreditDebitLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phrasecreditDebitLabelTitlecell);

                                try {
                                    Phrase phrasTotalLabelTitle = null;
                                    if(modal__b2BPaymentDetails.getCreditvalue().trim().equals("")){
                                        phrasTotalLabelTitle = new Phrase(twoDecimalConverterWithComma.format(Double.parseDouble(String.valueOf("0"))), valueFont_10);

                                    }
                                    else{
                                        phrasTotalLabelTitle = new Phrase(twoDecimalConverterWithComma.format(Double.parseDouble(String.valueOf(modal__b2BPaymentDetails.getCreditvalue()))), valueFont_10);

                                    }

                                    PdfPCell phraseTotalLabelTitlecell = new PdfPCell(phrasTotalLabelTitle);
                                    phraseTotalLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                    phraseTotalLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    phraseTotalLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                    phraseTotalLabelTitlecell.setPaddingLeft(6);
                                    phraseTotalLabelTitlecell.setBorderWidthRight(01);
                                    phraseTotalLabelTitlecell.setPaddingBottom(10);
                                    itemDetailsLabel_table.addCell(phraseTotalLabelTitlecell);
                                }
                                catch (Exception e){

                                    e.printStackTrace();
                                }

                                Phrase phrasNotesLabelTitle = new Phrase(String.valueOf(modal__b2BPaymentDetails.getNotes()), valueFont_10);

                                PdfPCell phraseNotesLabelTitlecell = new PdfPCell(phrasNotesLabelTitle);
                                phraseNotesLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phraseNotesLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phraseNotesLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phraseNotesLabelTitlecell.setPaddingLeft(6);
                                phraseNotesLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phraseNotesLabelTitlecell);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                PdfPCell itemDetails_table_Cell = new PdfPCell(itemDetailsLabel_table);
                                itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                                itemDetails_table_Cell.setBorderWidthBottom(1);
                                itemDetails_table_Cell.setBackgroundColor(WHITE);
                                wholePDFContentWithOut_Outline_table.addCell(itemDetails_table_Cell);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }



                //extraas
                try{




                    try{
                        PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {19, 18 , 15 ,15, 15 , 18 });



                        try {


                            Phrase phraseEarTagDetailsLabelTitle = new Phrase(String.valueOf("."), valueFont_1);

                            PdfPCell phraseEarTagDetailsLabelTitlecell = new PdfPCell(phraseEarTagDetailsLabelTitle);
                            phraseEarTagDetailsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseEarTagDetailsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseEarTagDetailsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseEarTagDetailsLabelTitlecell.setBorderWidthRight(01);
                            phraseEarTagDetailsLabelTitlecell.setPaddingLeft(6);
                            phraseEarTagDetailsLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseEarTagDetailsLabelTitlecell);




                            Phrase phrasQuantityLabelTitle = new Phrase("  ", valueFont_10);

                            PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                            phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseQuantityLabelTitlecell.setBorderWidthRight(01);
                            phraseQuantityLabelTitlecell.setPaddingLeft(6);
                            phraseQuantityLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseQuantityLabelTitlecell);





                            Phrase phrasBatchpriceLabelTitle = new Phrase(String.valueOf("  "), valueFont_10);

                            PdfPCell phraseBatchPriceLabelTitlecell = new PdfPCell(phrasBatchpriceLabelTitle);
                            phraseBatchPriceLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseBatchPriceLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseBatchPriceLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseBatchPriceLabelTitlecell.setBorderWidthRight(01);
                            phraseBatchPriceLabelTitlecell.setPaddingLeft(6);
                            phraseBatchPriceLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseBatchPriceLabelTitlecell);

                            Phrase phrasDebitCreditLabelTitle = new Phrase("  ", valueFont_10);

                            PdfPCell phraseDebitCreditLabelTitlecell = new PdfPCell(phrasDebitCreditLabelTitle);
                            phraseDebitCreditLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseDebitCreditLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseDebitCreditLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseDebitCreditLabelTitlecell.setBorderWidthRight(01);
                            phraseDebitCreditLabelTitlecell.setPaddingLeft(6);
                            phraseDebitCreditLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseDebitCreditLabelTitlecell);




                            Phrase phrasTotalLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                            PdfPCell phraseTotalLabelTitlecell = new PdfPCell(phrasTotalLabelTitle);
                            phraseTotalLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseTotalLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseTotalLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseTotalLabelTitlecell.setPaddingLeft(6);
                            phraseTotalLabelTitlecell.setBorderWidthRight(01);
                            phraseTotalLabelTitlecell.setPaddingBottom(10);


                            itemDetailsLabel_table.addCell(phraseTotalLabelTitlecell);




                            Phrase phrasNotesLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                            PdfPCell phraseNotesLabelTitlecell = new PdfPCell(phrasNotesLabelTitle);
                            phraseNotesLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseNotesLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseNotesLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseNotesLabelTitlecell.setPaddingLeft(6);
                            phraseNotesLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseNotesLabelTitlecell);

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }




                        try {
                            PdfPCell itemDetails_table_Cell = new PdfPCell(itemDetailsLabel_table);
                            itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                            itemDetails_table_Cell.setBorderWidthBottom(1);
                            itemDetails_table_Cell.setBackgroundColor(WHITE);
                            wholePDFContentWithOut_Outline_table.addCell(itemDetails_table_Cell);


                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }






                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }



                    try{
                        PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {19, 18 , 15 ,15, 15 , 18 });



                        try {


                            Phrase phraseEarTagDetailsLabelTitle = new Phrase(String.valueOf("."), valueFont_1);

                            PdfPCell phraseEarTagDetailsLabelTitlecell = new PdfPCell(phraseEarTagDetailsLabelTitle);
                            phraseEarTagDetailsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseEarTagDetailsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseEarTagDetailsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseEarTagDetailsLabelTitlecell.setBorderWidthRight(01);
                            phraseEarTagDetailsLabelTitlecell.setPaddingLeft(6);
                            phraseEarTagDetailsLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseEarTagDetailsLabelTitlecell);




                            Phrase phrasQuantityLabelTitle = new Phrase("  ", valueFont_10);

                            PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                            phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseQuantityLabelTitlecell.setBorderWidthRight(01);
                            phraseQuantityLabelTitlecell.setPaddingLeft(6);
                            phraseQuantityLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseQuantityLabelTitlecell);





                            Phrase phrasBatchpriceLabelTitle = new Phrase(String.valueOf("  "), valueFont_10);

                            PdfPCell phraseBatchPriceLabelTitlecell = new PdfPCell(phrasBatchpriceLabelTitle);
                            phraseBatchPriceLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseBatchPriceLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseBatchPriceLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseBatchPriceLabelTitlecell.setBorderWidthRight(01);
                            phraseBatchPriceLabelTitlecell.setPaddingLeft(6);
                            phraseBatchPriceLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseBatchPriceLabelTitlecell);

                            Phrase phrasDebitCreditLabelTitle = new Phrase("  ", valueFont_10);

                            PdfPCell phraseDebitCreditLabelTitlecell = new PdfPCell(phrasDebitCreditLabelTitle);
                            phraseDebitCreditLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseDebitCreditLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseDebitCreditLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseDebitCreditLabelTitlecell.setBorderWidthRight(01);
                            phraseDebitCreditLabelTitlecell.setPaddingLeft(6);
                            phraseDebitCreditLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseDebitCreditLabelTitlecell);




                            Phrase phrasTotalLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                            PdfPCell phraseTotalLabelTitlecell = new PdfPCell(phrasTotalLabelTitle);
                            phraseTotalLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseTotalLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseTotalLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseTotalLabelTitlecell.setPaddingLeft(6);
                            phraseTotalLabelTitlecell.setBorderWidthRight(01);
                            phraseTotalLabelTitlecell.setPaddingBottom(10);


                            itemDetailsLabel_table.addCell(phraseTotalLabelTitlecell);




                            Phrase phrasNotesLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                            PdfPCell phraseNotesLabelTitlecell = new PdfPCell(phrasNotesLabelTitle);
                            phraseNotesLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseNotesLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseNotesLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseNotesLabelTitlecell.setPaddingLeft(6);
                            phraseNotesLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseNotesLabelTitlecell);

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }




                        try {
                            PdfPCell itemDetails_table_Cell = new PdfPCell(itemDetailsLabel_table);
                            itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                            itemDetails_table_Cell.setBorderWidthBottom(1);
                            itemDetails_table_Cell.setBackgroundColor(WHITE);
                            wholePDFContentWithOut_Outline_table.addCell(itemDetails_table_Cell);


                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }






                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }



                    try{
                        PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {19, 18 , 15 ,15, 15 , 18 });



                        try {


                            Phrase phraseEarTagDetailsLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                            PdfPCell phraseEarTagDetailsLabelTitlecell = new PdfPCell(phraseEarTagDetailsLabelTitle);
                            phraseEarTagDetailsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseEarTagDetailsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseEarTagDetailsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseEarTagDetailsLabelTitlecell.setBorderWidthRight(01);
                            phraseEarTagDetailsLabelTitlecell.setPaddingLeft(6);
                            phraseEarTagDetailsLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseEarTagDetailsLabelTitlecell);




                            Phrase phrasQuantityLabelTitle = new Phrase("Transaction Total", valueFont_10);

                            PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                            phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseQuantityLabelTitlecell.setBorderWidthRight(01);
                            phraseQuantityLabelTitlecell.setPaddingLeft(6);
                            phraseQuantityLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseQuantityLabelTitlecell);


                            Phrase phrasBatchpriceLabelTitle = new Phrase(String.valueOf(twoDecimalConverterWithComma.format(totalAmount)), valueFont_10);

                            PdfPCell phraseBatchPriceLabelTitlecell = new PdfPCell(phrasBatchpriceLabelTitle);
                            phraseBatchPriceLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseBatchPriceLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseBatchPriceLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseBatchPriceLabelTitlecell.setBorderWidthRight(01);
                            phraseBatchPriceLabelTitlecell.setPaddingLeft(6);
                            phraseBatchPriceLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseBatchPriceLabelTitlecell);




                            Phrase phrasDebitCreditLabelTitle = new Phrase("  ", valueFont_10);

                            PdfPCell phraseDebitCreditLabelTitlecell = new PdfPCell(phrasDebitCreditLabelTitle);
                            phraseDebitCreditLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseDebitCreditLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseDebitCreditLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseDebitCreditLabelTitlecell.setBorderWidthRight(01);
                            phraseDebitCreditLabelTitlecell.setPaddingLeft(6);
                            phraseDebitCreditLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseDebitCreditLabelTitlecell);



                            Phrase phrasTotalLabelTitle = new Phrase("", valueFont_10);

                            PdfPCell phraseTotalLabelTitlecell = new PdfPCell(phrasTotalLabelTitle);
                            phraseTotalLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseTotalLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseTotalLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseTotalLabelTitlecell.setPaddingLeft(6);
                            phraseTotalLabelTitlecell.setBorderWidthRight(01);
                            phraseTotalLabelTitlecell.setPaddingBottom(10);


                            itemDetailsLabel_table.addCell(phraseTotalLabelTitlecell);




                            Phrase phrasNotesLabelTitle = new Phrase(".", valueFont_10);

                            PdfPCell phraseNotesLabelTitlecell = new PdfPCell(phrasNotesLabelTitle);
                            phraseNotesLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseNotesLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseNotesLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseNotesLabelTitlecell.setPaddingLeft(6);
                            phraseNotesLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseNotesLabelTitlecell);

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }




                        try {
                            PdfPCell itemDetails_table_Cell = new PdfPCell(itemDetailsLabel_table);
                            itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                            itemDetails_table_Cell.setBorderWidthBottom(1);
                            itemDetails_table_Cell.setBackgroundColor(WHITE);
                            wholePDFContentWithOut_Outline_table.addCell(itemDetails_table_Cell);


                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }






                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }




                    try{
                        PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {19, 18 , 15 ,15, 15 , 18 });



                        try {


                            Phrase phraseEarTagDetailsLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                            PdfPCell phraseEarTagDetailsLabelTitlecell = new PdfPCell(phraseEarTagDetailsLabelTitle);
                            phraseEarTagDetailsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseEarTagDetailsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseEarTagDetailsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseEarTagDetailsLabelTitlecell.setBorderWidthRight(01);
                            phraseEarTagDetailsLabelTitlecell.setPaddingLeft(6);
                            phraseEarTagDetailsLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseEarTagDetailsLabelTitlecell);




                            Phrase phrasQuantityLabelTitle = new Phrase("Closing Balance", valueFont_10);

                            PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                            phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseQuantityLabelTitlecell.setBorderWidthRight(01);
                            phraseQuantityLabelTitlecell.setPaddingLeft(6);
                            phraseQuantityLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseQuantityLabelTitlecell);


                            Phrase phrasBatchpriceLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                            PdfPCell phraseBatchPriceLabelTitlecell = new PdfPCell(phrasBatchpriceLabelTitle);
                            phraseBatchPriceLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseBatchPriceLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseBatchPriceLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseBatchPriceLabelTitlecell.setBorderWidthRight(01);
                            phraseBatchPriceLabelTitlecell.setPaddingLeft(6);
                            phraseBatchPriceLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseBatchPriceLabelTitlecell);




                            Phrase phrasDebitCreditLabelTitle = new Phrase("  ", valueFont_10);

                            PdfPCell phraseDebitCreditLabelTitlecell = new PdfPCell(phrasDebitCreditLabelTitle);
                            phraseDebitCreditLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseDebitCreditLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseDebitCreditLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseDebitCreditLabelTitlecell.setBorderWidthRight(01);
                            phraseDebitCreditLabelTitlecell.setPaddingLeft(6);
                            phraseDebitCreditLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseDebitCreditLabelTitlecell);




                            Phrase phrasTotalLabelTitle = new Phrase(String.valueOf(twoDecimalConverterWithComma.format(Double.parseDouble(closingBalance))), valueFont_10);

                            PdfPCell phraseTotalLabelTitlecell = new PdfPCell(phrasTotalLabelTitle);
                            phraseTotalLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseTotalLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseTotalLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseTotalLabelTitlecell.setPaddingLeft(6);
                            phraseTotalLabelTitlecell.setBorderWidthRight(01);
                            phraseTotalLabelTitlecell.setPaddingBottom(10);


                            itemDetailsLabel_table.addCell(phraseTotalLabelTitlecell);




                            Phrase phrasNotesLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                            PdfPCell phraseNotesLabelTitlecell = new PdfPCell(phrasNotesLabelTitle);
                            phraseNotesLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseNotesLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseNotesLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseNotesLabelTitlecell.setPaddingLeft(6);
                            phraseNotesLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseNotesLabelTitlecell);

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }




                        try {
                            PdfPCell itemDetails_table_Cell = new PdfPCell(itemDetailsLabel_table);
                            itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                            itemDetails_table_Cell.setBorderWidthBottom(1);
                            itemDetails_table_Cell.setBackgroundColor(WHITE);
                            wholePDFContentWithOut_Outline_table.addCell(itemDetails_table_Cell);


                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }






                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    try{
                        PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {19, 18 , 15 ,15, 15 , 18 });



                        try {


                            Phrase phraseEarTagDetailsLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                            PdfPCell phraseEarTagDetailsLabelTitlecell = new PdfPCell(phraseEarTagDetailsLabelTitle);
                            phraseEarTagDetailsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseEarTagDetailsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseEarTagDetailsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseEarTagDetailsLabelTitlecell.setBorderWidthRight(01);
                            phraseEarTagDetailsLabelTitlecell.setPaddingLeft(6);
                            phraseEarTagDetailsLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseEarTagDetailsLabelTitlecell);




                            Phrase phrasQuantityLabelTitle = new Phrase("", valueFont_10);

                            PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                            phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseQuantityLabelTitlecell.setBorderWidthRight(01);
                            phraseQuantityLabelTitlecell.setPaddingLeft(6);
                            phraseQuantityLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseQuantityLabelTitlecell);


                            Phrase phrasBatchpriceLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                            PdfPCell phraseBatchPriceLabelTitlecell = new PdfPCell(phrasBatchpriceLabelTitle);
                            phraseBatchPriceLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseBatchPriceLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseBatchPriceLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseBatchPriceLabelTitlecell.setBorderWidthRight(01);
                            phraseBatchPriceLabelTitlecell.setPaddingLeft(6);
                            phraseBatchPriceLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseBatchPriceLabelTitlecell);




                            Phrase phrasDebitCreditLabelTitle = new Phrase("  ", valueFont_10);

                            PdfPCell phraseDebitCreditLabelTitlecell = new PdfPCell(phrasDebitCreditLabelTitle);
                            phraseDebitCreditLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseDebitCreditLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseDebitCreditLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseDebitCreditLabelTitlecell.setBorderWidthRight(01);
                            phraseDebitCreditLabelTitlecell.setPaddingLeft(6);
                            phraseDebitCreditLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseDebitCreditLabelTitlecell);


                            Phrase phrasTotalLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                            PdfPCell phraseTotalLabelTitlecell = new PdfPCell(phrasTotalLabelTitle);
                            phraseTotalLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseTotalLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseTotalLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseTotalLabelTitlecell.setPaddingLeft(6);
                            phraseTotalLabelTitlecell.setBorderWidthRight(01);
                            phraseTotalLabelTitlecell.setPaddingBottom(10);


                            itemDetailsLabel_table.addCell(phraseTotalLabelTitlecell);




                            Phrase phrasNotesLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                            PdfPCell phraseNotesLabelTitlecell = new PdfPCell(phrasNotesLabelTitle);
                            phraseNotesLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseNotesLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phraseNotesLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseNotesLabelTitlecell.setPaddingLeft(6);
                            phraseNotesLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phraseNotesLabelTitlecell);

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }




                        try {
                            PdfPCell itemDetails_table_Cell = new PdfPCell(itemDetailsLabel_table);
                            itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                            itemDetails_table_Cell.setBorderWidthBottom(1);
                            itemDetails_table_Cell.setBackgroundColor(WHITE);
                            wholePDFContentWithOut_Outline_table.addCell(itemDetails_table_Cell);


                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }






                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                    try {
                        PdfPTable EnptytableLabel_table = new PdfPTable(new float[]{35, 65});


                        try {
                            Phrase phraseParticularsLabelTitle = new Phrase("    ", valueFont_10Bold);

                            PdfPCell phraseParticularsLabelTitlecell = new PdfPCell(phraseParticularsLabelTitle);
                            phraseParticularsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseParticularsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            phraseParticularsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseParticularsLabelTitlecell.setPaddingLeft(6);
                            phraseParticularsLabelTitlecell.setPaddingTop(5);
                            phraseParticularsLabelTitlecell.setPaddingBottom(10);
                            EnptytableLabel_table.addCell(phraseParticularsLabelTitlecell);


                            Phrase phrasQuantityLabelTitle = new Phrase("     ", valueFont_10Bold);
                            PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                            phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseQuantityLabelTitlecell.setPaddingTop(5);
                            phraseQuantityLabelTitlecell.setPaddingLeft(6);
                            phraseQuantityLabelTitlecell.setPaddingBottom(10);
                            EnptytableLabel_table.addCell(phraseQuantityLabelTitlecell);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        try {
                            PdfPCell itemDetails_table_Cell = new PdfPCell(EnptytableLabel_table);
                            itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                            itemDetails_table_Cell.setBackgroundColor(WHITE);
                            wholePDFContentWithOut_Outline_table.addCell(itemDetails_table_Cell);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        PdfPTable EnptytableLabel_table = new PdfPTable(new float[]{35, 65});


                        try {
                            Phrase phraseParticularsLabelTitle = new Phrase("    ", valueFont_10Bold);

                            PdfPCell phraseParticularsLabelTitlecell = new PdfPCell(phraseParticularsLabelTitle);
                            phraseParticularsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseParticularsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            phraseParticularsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseParticularsLabelTitlecell.setPaddingLeft(6);
                            phraseParticularsLabelTitlecell.setPaddingTop(5);
                            phraseParticularsLabelTitlecell.setPaddingBottom(10);
                            EnptytableLabel_table.addCell(phraseParticularsLabelTitlecell);


                            Phrase phrasQuantityLabelTitle = new Phrase("     ", valueFont_10Bold);
                            PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                            phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseQuantityLabelTitlecell.setPaddingTop(5);
                            phraseQuantityLabelTitlecell.setPaddingLeft(6);
                            phraseQuantityLabelTitlecell.setPaddingBottom(10);
                            EnptytableLabel_table.addCell(phraseQuantityLabelTitlecell);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        try {
                            PdfPCell itemDetails_table_Cell = new PdfPCell(EnptytableLabel_table);
                            itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                            itemDetails_table_Cell.setBackgroundColor(WHITE);
                            wholePDFContentWithOut_Outline_table.addCell(itemDetails_table_Cell);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        PdfPTable EnptytableLabel_table = new PdfPTable(new float[]{35, 65});


                        try {
                            Phrase phraseParticularsLabelTitle = new Phrase("    ", valueFont_10Bold);

                            PdfPCell phraseParticularsLabelTitlecell = new PdfPCell(phraseParticularsLabelTitle);
                            phraseParticularsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseParticularsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            phraseParticularsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseParticularsLabelTitlecell.setPaddingLeft(6);
                            phraseParticularsLabelTitlecell.setPaddingTop(5);
                            phraseParticularsLabelTitlecell.setPaddingBottom(10);
                            EnptytableLabel_table.addCell(phraseParticularsLabelTitlecell);


                            Phrase phrasQuantityLabelTitle = new Phrase("     ", valueFont_10Bold);
                            PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                            phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phraseQuantityLabelTitlecell.setPaddingTop(5);
                            phraseQuantityLabelTitlecell.setPaddingLeft(6);
                            phraseQuantityLabelTitlecell.setPaddingBottom(10);
                            EnptytableLabel_table.addCell(phraseQuantityLabelTitlecell);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        try {
                            PdfPCell itemDetails_table_Cell = new PdfPCell(EnptytableLabel_table);
                            itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                            itemDetails_table_Cell.setBackgroundColor(WHITE);
                            wholePDFContentWithOut_Outline_table.addCell(itemDetails_table_Cell);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                catch (Exception e ){
                    e.printStackTrace();
                }


                //finaladd
                try {

                    tmcLogoImage_table.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    tmcLogoImage_table.setTotalWidth(10f);
                    layoutDocument.add(tmcLogoImage_table);




        /*    PdfPCell wholePDFContentWithImage_and_tableimagecell = new PdfPCell(tmcLogoImage_table);
            wholePDFContentWithImage_and_tableimagecell.setCellEvent(roundRectange);
            wholePDFContentWithImage_and_tableimagecell.setPadding(1);
            wholePDFContentWithImage_and_tableimagecell.setBorder(Rectangle.NO_BORDER);
            wholePDFContentWithImage_and_table.addCell(wholePDFContentWithImage_and_tableimagecell);
            wholePDFContentWithImage_and_table.setWidthPercentage(100);





            PdfPCell wholePDFContentWithImage_and_tablebordeercell = new PdfPCell(wholePDFContentWithOut_Outline_table);
            wholePDFContentWithImage_and_tablebordeercell.setCellEvent(roundRectange);
            wholePDFContentWithImage_and_tablebordeercell.setPadding(1);
            wholePDFContentWithImage_and_tablebordeercell.setBorder(Rectangle.NO_BORDER);
            wholePDFContentWithImage_and_table.addCell(wholePDFContentWithImage_and_tablebordeercell);
            wholePDFContentWithImage_and_table.setWidthPercentage(100);


         */




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
















        }
        catch (Exception e){
            e.printStackTrace();
        }

/*
        try {
            PdfPCell itemDetails_table_Cell = new PdfPCell(tmcLogoImage_table);
            itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
            itemDetails_table_Cell.setBackgroundColor(WHITE);
            wholePDFContentWithImage_and_table.addCell(itemDetails_table_Cell);


        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            PdfPCell itemDetails_table_Cell = new PdfPCell(wholePDFContentWithOut_Outline_table);
            itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
            itemDetails_table_Cell.setBackgroundColor(LIGHT_GRAY);
            itemDetails_table_Cell.setCellEvent(roundRectange);
            itemDetails_table_Cell.setPadding(1);
            wholePDFContentWithImage_and_table.addCell(itemDetails_table_Cell);



        } catch (Exception e) {
            e.printStackTrace();
        }
*/




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







}