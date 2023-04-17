package com.tmc.tmcb2bpartnerapp.second_version.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.util.Pair;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.os.Parcel;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
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
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal__B2BCreditTransactionHistory;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal__B2BPaymentDetails;
import com.tmc.tmcb2bpartnerapp.second_version.adapter.Adapter_AutoComplete_RetailerMobileNoForCreditClearance;
import com.tmc.tmcb2bpartnerapp.second_version.adapter.Adapter_PamentReceivedReport_recyclerView;
import com.tmc.tmcb2bpartnerapp.second_version.adapter.Adapter_PlaceOrder_recyclerView;
import com.tmc.tmcb2bpartnerapp.second_version.apiRequestServices.B2BCreditTransactionHistory;
import com.tmc.tmcb2bpartnerapp.second_version.apiRequestServices.B2BPaymentDetails;
import com.tmc.tmcb2bpartnerapp.second_version.interfaces.B2BCreditTransactionHistoryInterface;
import com.tmc.tmcb2bpartnerapp.second_version.interfaces.B2BPaymentDetailsInterface;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.RangeDateValidator;

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
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;
import static com.itextpdf.text.BaseColor.BLACK;
import static com.itextpdf.text.BaseColor.GRAY;
import static com.itextpdf.text.BaseColor.LIGHT_GRAY;
import static com.itextpdf.text.BaseColor.WHITE;

public class PaymentReceivedReport extends AppCompatActivity {

    //general
    DatePickerDialog datepicker,enddatepicker;




    //Wifdgets
    public static LinearLayout back_IconLayout , loadingpanelmask ,loadingPanel , fetchData_layout;
    RecyclerView paymentItems_recyclerview;
    NestedScrollView idNestedSV;
    static LinearLayout search_IconLayout , pdf_IconLayout;
    static LinearLayout close_IconLayout;
    EditText retailerName_editText;
    TextView retailerName_textView ,recordsListinstruction ,startDateValue , endDateValue;




    //String
    String deliveryCenterKey ="" , deliveryCenterName ="" , PreviousDateString ="" , DateString ="" ,
            selectedStartDate ="",selectedEndDate ="" , supervisorMobileno ="", supervisorName ="" ;


    boolean isPaymentDetailsCalled = false;
    boolean isSearchButtonClicked  = false;
    boolean isRetailerCreditTransactionHistoryCalled = false;



    public static ArrayList<Modal__B2BPaymentDetails> paymentDetailsArrayList = new ArrayList<>();
    public static ArrayList<Modal__B2BPaymentDetails> sorted_paymentDetailsArrayList = new ArrayList<>();
    public static ArrayList<Modal__B2BCreditTransactionHistory> creditTransactionHistoryArrayList = new ArrayList<>();


    //interface
    B2BCreditTransactionHistoryInterface callB2BCreditTransactionHistoryInterface = null;
    B2BPaymentDetailsInterface callB2BPaymentDetailsInterface = null;

    static DecimalFormat twoDecimalConverter = new DecimalFormat(Constants.twoDecimalPattern);

    static DecimalFormat twoDecimalConverterWithComma = new DecimalFormat(Constants.twoDecimalWithCommaPattern);

    //int
    private static int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;

    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_received_report);
        search_IconLayout = findViewById(R.id.search_IconLayout);
        close_IconLayout = findViewById(R.id.close_IconLayout);
        retailerName_editText = findViewById(R.id.retailerName_editText);
        retailerName_textView = findViewById(R.id.retailerName_textView);
        recordsListinstruction = findViewById(R.id.recordsListinstruction);
        idNestedSV  = findViewById(R.id.idNestedSV);
        pdf_IconLayout = findViewById(R.id.pdf_IconLayout);
        fetchData_layout = findViewById(R.id.fetchData_layout);
        startDateValue = findViewById(R.id.startDateValue);
        endDateValue = findViewById(R.id.endDateValue);

        back_IconLayout  = findViewById(R.id.back_IconLayout);
        loadingpanelmask = findViewById(R.id.loadingpanelmask);
        loadingPanel = findViewById(R.id.loadingPanel);
        paymentItems_recyclerview= findViewById(R.id.paymentItems_recyclerview);

        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);
        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");


        SharedPreferences sh = getSharedPreferences("LoginData",MODE_PRIVATE);
        supervisorName = sh.getString("UserName","");
        supervisorMobileno = sh.getString("UserMobileNumber","");
/*

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();





        long today = MaterialDatePicker.todayInUtcMilliseconds();

        calendar.setTimeInMillis(today);


        calendar.set(Calendar.MONTH , Calendar.JANUARY);
        long january = calendar.getTimeInMillis();
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        long december = calendar.getTimeInMillis();

        Calendar calendarr = Calendar.getInstance();
        calendarr.add(Calendar.DAY_OF_YEAR, 7);
        long maxDate = calendarr.getTimeInMillis();

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(new RangeDateValidator(System.currentTimeMillis(), maxDate));

        // calendarConstraints.setStart(january);
       // calendarConstraints.setEnd(december);

     //   MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select Date Range");
        builder.setCalendarConstraints(constraintsBuilder.build());


        final MaterialDatePicker materialDatePicker = builder.build();

        fromdateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKKER");
                }

        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                fromdateValue.setText(String.valueOf(materialDatePicker.getHeaderText()));
            }
        });


 */

        Create_and_SharePdf(true);

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


        pdf_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Create_and_SharePdf(false);
            }
        });


        fetchData_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    if(!startDateValue.getText().toString().contains("/--/")){
                        if(!endDateValue.getText().toString().contains("/--/")){
                            showProgressBar(true);
                            Call_and_Execute_PaymentDetails(Constants.CallGETListMethod);
                        }
                        else{
                            AlertDialogClass.showDialog(PaymentReceivedReport.this, R.string.CannotFetchWhenendDateNotSelected);

                        }
                    }
                    else{
                        AlertDialogClass.showDialog(PaymentReceivedReport.this, R.string.CannotFetchWhenstartdateNotSelected);

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

        retailerName_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_IconLayout.performClick();
            }

        });



        close_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(retailerName_editText);
                closeSearchBarEditText();
                retailerName_editText.setText("");
                isSearchButtonClicked =false;
                setAdapterForRecyclerView(paymentDetailsArrayList);

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

        retailerName_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sorted_paymentDetailsArrayList.clear();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                sorted_paymentDetailsArrayList.clear();
                isSearchButtonClicked =true;
                String nameEntered = (editable.toString().toUpperCase() );
                if(!nameEntered.equals("")) {
                    for(int i = 0 ; i< paymentDetailsArrayList.size() ; i ++){
                        Modal__B2BPaymentDetails modal__b2BPaymentDetails = paymentDetailsArrayList.get(i);

                        if((modal__b2BPaymentDetails.getRetailername().toUpperCase()).contains(nameEntered.toUpperCase())) {
                            sorted_paymentDetailsArrayList.add(modal__b2BPaymentDetails);
                        }
                            if (i == (paymentDetailsArrayList.size() - 1)) {
                                if (sorted_paymentDetailsArrayList.size() > 0) {
                                    setAdapterForRecyclerView(sorted_paymentDetailsArrayList);
                                }
                                else{
                                    showProgressBar(false);
                                    recordsListinstruction.setText(String.valueOf("There is No Payment Record for this retailer "));
                                    recordsListinstruction.setVisibility(View.VISIBLE);
                                    idNestedSV.setVisibility(View.GONE);
                                }
                            }

                    }
                }
                else{
                    setAdapterForRecyclerView(paymentDetailsArrayList);
                }
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

                paymentDetailsArrayList = arrayList;
                Call_and_Execute_CreditTransactionHistory(Constants.CallGETListMethod);
                //setAdapterForRecyclerView(paymentDetailsArrayList);
                isPaymentDetailsCalled = false;
            }

            @Override
            public void notifySuccess(String result) {
                isPaymentDetailsCalled = false;
                if(result.equals(Constants.emptyResult_volley)){
                    showProgressBar(false);
                    Toast.makeText(PaymentReceivedReport.this, "There is no payment details for this date", Toast.LENGTH_SHORT).show();
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

            String getApiToCall = API_Manager.getPaymentDetailsUsingDeliveryCentrekeyFromToDate +"?deliverycentrekey=" +deliveryCenterKey+"&fromdate="+String.valueOf(DateParser.convertOldFormatDateintoNewFormat(selectedStartDate))+" 00:00:00" + "&todate="+String.valueOf(DateParser.convertOldFormatDateintoNewFormat(selectedEndDate))+" 23:59:59";
            B2BPaymentDetails asyncTask = new B2BPaymentDetails(callB2BPaymentDetailsInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }
    }

    private void setAdapterForRecyclerView(ArrayList<Modal__B2BPaymentDetails> paymentDetailsArrayList) {








        try {
            if(paymentDetailsArrayList.size()>0) {
                showProgressBar(false);
                recordsListinstruction.setVisibility(View.GONE);
                idNestedSV.setVisibility(View.VISIBLE);
                Adapter_PamentReceivedReport_recyclerView mAdapter = new Adapter_PamentReceivedReport_recyclerView(paymentDetailsArrayList, PaymentReceivedReport.this, PaymentReceivedReport.this);
                LinearLayoutManager manager = new LinearLayoutManager(PaymentReceivedReport.this);
                paymentItems_recyclerview.setLayoutManager(manager);
                paymentItems_recyclerview.setHasFixedSize(true);
                paymentItems_recyclerview.setLayoutManager(manager);
                paymentItems_recyclerview.setAdapter(mAdapter);

            }
            else{
                showProgressBar(false);
                recordsListinstruction.setText(String.valueOf("There is No Payment Record for now "));
                recordsListinstruction.setVisibility(View.VISIBLE);
                idNestedSV.setVisibility(View.GONE);
            }

        }
        catch (Exception e){
            e.printStackTrace();
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

                creditTransactionHistoryArrayList = creditTransactionHistoryArrayListt;
                for(int i =0 ; i<creditTransactionHistoryArrayList.size(); i++ ){
                    Modal__B2BCreditTransactionHistory modal__b2BCreditTransactionHistory = creditTransactionHistoryArrayList.get(i);

                    for(int j =0 ; j<paymentDetailsArrayList.size(); j++ ) {
                        Modal__B2BPaymentDetails modal__b2BPaymentDetails = paymentDetailsArrayList.get(j);
                        try{
                            if(modal__b2BCreditTransactionHistory.getPaymentid().equals(modal__b2BPaymentDetails.getPaymentid())){
                                modal__b2BPaymentDetails.setCreditvalue(String.valueOf(modal__b2BCreditTransactionHistory.getNewamountincredit()));
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    if((creditTransactionHistoryArrayList.size() -1 ) == i){
                        showProgressBar(false);

                        Collections.sort(paymentDetailsArrayList, new Comparator<Modal__B2BPaymentDetails>() {
                            public int compare(final Modal__B2BPaymentDetails object1, final Modal__B2BPaymentDetails object2) {
                                return object1.getTransactiontime_long().compareTo(object2.getTransactiontime_long());
                            }
                        });


                    }

                    setAdapterForRecyclerView(paymentDetailsArrayList);

                }


            }

            @Override
            public void notifySuccess(String result) {
                if(result.equals(Constants.emptyResult_volley)){
                    showProgressBar(false);
                    Toast.makeText(PaymentReceivedReport.this, "There is no Credit transaction details for this date", Toast.LENGTH_SHORT).show();
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

            String getApiToCall = API_Manager.getCreditTransactionHistoryUsingDeliveryCentrekeyFromToDate +"?deliverycentrekey=" +deliveryCenterKey+"&fromdate="+String.valueOf(DateParser.convertOldFormatDateintoNewFormat(selectedStartDate))+" 00:00:00" + "&todate="+String.valueOf(DateParser.convertOldFormatDateintoNewFormat(selectedEndDate))+" 23:59:59";
            B2BCreditTransactionHistory asyncTask = new B2BCreditTransactionHistory(callB2BCreditTransactionHistoryInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }
    }
    private ArrayList<Modal__B2BPaymentDetails> sortThisArrayUsingDate(ArrayList<Modal__B2BPaymentDetails> paymentDetailsArrayList) {

/*
        final Pattern p = Pattern.compile("^\\d+");



        Comparator<Modal__B2BPaymentDetails> c = new Comparator<Modal__B2BPaymentDetails>() {
            @Override
            public int compare(Modal__B2BPaymentDetails object1, Modal__B2BPaymentDetails object2) {
                Matcher m = p.matcher(object1.getTransactiontime_long());
                Integer number1 = null;
                if (!m.find()) {
                    Matcher m1 = p.matcher(object2.getTransactiontime_long());
                    if (m1.find()) {
                        return object1.getTransactiontime_long().compareTo(object2.getTransactiontime_long());
                    } else {
                        return object2.getTransactiontime_long().compareTo(object1.getTransactiontime_long());
                    }
                } else {
                    Integer number2 = null;
                    number1 = Integer.parseInt(m.group());
                    m = p.matcher(object2.getTransactiontime_long());
                    if (!m.find()) {
                        // return object1.compareTo(object2);
                        Matcher m1 = p.matcher(object1.getTransactiontime_long());
                        if (m1.find()) {
                            return object1.getTransactiontime_long().compareTo(object2.getTransactiontime_long());
                        } else {
                            return object2.getTransactiontime_long().compareTo(object1.getTransactiontime_long());
                        }
                    } else {
                        number2 = Integer.parseInt(m.group());
                        int comparison = number1.compareTo(number2);
                        if (comparison != 0) {
                            return comparison;
                        } else {
                            return object2.getTransactiontime_long().compareTo(object1.getTransactiontime_long());
                        }
                    }
                }
            }
        };

        Collections.sort(paymentDetailsArrayList, c);


 */
        Collections.sort(paymentDetailsArrayList, new Comparator<Modal__B2BPaymentDetails>() {
            public int compare(final Modal__B2BPaymentDetails object1, final Modal__B2BPaymentDetails object2) {
                return object2.getTransactiontime_long().compareTo(object1.getTransactiontime_long());
            }
        });

        return  paymentDetailsArrayList;

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


    private void openDatePicker() {



        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        datepicker = new DatePickerDialog(PaymentReceivedReport.this,
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
        enddatepicker = new DatePickerDialog(PaymentReceivedReport.this,
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
        SimpleDateFormat sdformat = new SimpleDateFormat("EEE, d MMM yyyy",Locale.ENGLISH);
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
        c.add(Calendar.DATE, -7);
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


                        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(PaymentReceivedReport.this, WRITE_EXTERNAL_STORAGE);
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


                        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(PaymentReceivedReport.this, WRITE_EXTERNAL_STORAGE);
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
            String filename = "Payment Details "+" for the period( From : "+(selectedStartDate)+" To : "+(selectedEndDate)+" ) "+timeinLong+".pdf";

            //    String filename = "PaymentReport  on "+date+" "+timeinLong+".pdf";
            String croppedfileName = filename.substring(0,20);

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



        recordsListinstruction.setVisibility(View.GONE);


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());



        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri outputPdfUri = FileProvider.getUriForFile(this, PaymentReceivedReport.this.getPackageName() + ".provider", file);

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
        PaymentReceivedReport.RoundRectangle roundRectange = new PaymentReceivedReport.RoundRectangle();
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
        PdfPTable billtimeDetails_table = new PdfPTable(1);
       /* try {

            Phrase phrasebilltimeDetails = new Phrase("PAYMENT REPORTS DATEWISE ", valueFont_8Bold);
            PdfPCell phrasebilltimedetailscell = new PdfPCell(phrasebilltimeDetails);
            phrasebilltimedetailscell.setBorder(Rectangle.NO_BORDER);
            phrasebilltimedetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
            phrasebilltimedetailscell.setVerticalAlignment(Element.ALIGN_CENTER);
            phrasebilltimedetailscell.setPaddingLeft(10);
            phrasebilltimedetailscell.setPaddingBottom(10);
            phrasebilltimedetailscell.setPaddingTop(5);
            billtimeDetails_table.addCell(phrasebilltimedetailscell);
        } catch (Exception e) {
            e.printStackTrace();
        }

       try {

            PdfPTable supervisorNameDetails_table = new PdfPTable(1);

            Phrase phraseSupervisorNameLabelTitle = new Phrase("" , valueFont_8Bold);

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
            addBorder_billTimeDetails.setBorderColor(GRAY);


            //wholePDFContentWithOut_Outline_table.addCell(addBorder_billTimeDetails);

        } catch (Exception e) {
            e.printStackTrace();
        }

        PdfPTable Whole_Warehouse_and_RetailerDetails_table = new PdfPTable(new float[] { 100});

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

                Phrase phrasecompanyAddressDetails = new Phrase("(Unit of Culinary Triangle Private Ltd)\n \n Old No 4, New No 50, \n Kumaraswamy Street, Lakshmipuram, Chromepet,\n Chennai – 44 , India. \n GSTIN 33AAJCC0055D1Z9", valueFont_10);

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







            try {
                PdfPCell Whole_WarehouseDetails_table_Cell = new PdfPCell(Whole_WarehouseDetails_table);
                Whole_WarehouseDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                Whole_Warehouse_and_RetailerDetails_table.addCell(Whole_WarehouseDetails_table_Cell);



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


            try {
                PdfPTable EnptytableLabel_table = new PdfPTable(new float[]{100});

                try {
                    Phrase phraseParticularsLabelTitle = new Phrase("  " , valueFont_10Bold);

                    PdfPCell phraseParticularsLabelTitlecell = new PdfPCell(phraseParticularsLabelTitle);
                    phraseParticularsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseParticularsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phraseParticularsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                    EnptytableLabel_table.addCell(phraseParticularsLabelTitlecell);




                } catch (Exception e) {
                    e.printStackTrace();
                }



                try {
                    Phrase phraseParticularsLabelTitle = new Phrase("Payment Details  "+" for the period ( From : "+(selectedStartDate)+" To : "+(selectedEndDate)+" ) ", valueFont_10);

                    PdfPCell phraseParticularsLabelTitlecell = new PdfPCell(phraseParticularsLabelTitle);
                    phraseParticularsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseParticularsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    phraseParticularsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                    EnptytableLabel_table.addCell(phraseParticularsLabelTitlecell);




                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
                    Phrase phraseParticularsLabelTitle = new Phrase("  " , valueFont_10Bold);

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
                PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] { 18, 20 , 22 , 12 , 12 , 16  });



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


                    Phrase phraseretailernameLabelTitle = new Phrase("Buyer Name", valueFont_10Bold);

                    PdfPCell phrasretailernameLabelTitlecell = new PdfPCell(phraseretailernameLabelTitle);
                   phrasretailernameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                   phrasretailernameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                   phrasretailernameLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                   phrasretailernameLabelTitlecell.setBorderWidthRight(01);
                   phrasretailernameLabelTitlecell.setPaddingLeft(6);
                   phrasretailernameLabelTitlecell.setPaddingBottom(10);
                    itemDetailsLabel_table.addCell(phrasretailernameLabelTitlecell);




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




            Toast.makeText(this, "Size : "+String.valueOf(paymentDetailsArrayList.size()), Toast.LENGTH_SHORT).show();

            if(paymentDetailsArrayList.size()>10){
                try {


                    for (int iterator = 0; iterator < 11; iterator++) {
                        Modal__B2BPaymentDetails modal__b2BPaymentDetails = paymentDetailsArrayList.get(iterator);
                        try {
                            PdfPTable itemDetailsLabel_table  = new PdfPTable(new float[]{18, 20 , 22 , 12 , 12 , 16});


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




                                Phrase phraseretailernameLabelTitle = new Phrase(String.valueOf(modal__b2BPaymentDetails.getRetailername()), valueFont_10);

                                PdfPCell phrasretailernameLabelTitlecell = new PdfPCell(phraseretailernameLabelTitle);
                                phrasretailernameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phrasretailernameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phrasretailernameLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phrasretailernameLabelTitlecell.setBorderWidthRight(01);
                                phrasretailernameLabelTitlecell.setPaddingLeft(6);
                                phrasretailernameLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phrasretailernameLabelTitlecell);



                                phrasQuantityLabelTitle = new Phrase("Payment via " + String.valueOf(modal__b2BPaymentDetails.getPaymentmode()), valueFont_10);
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
                                if (paymentDetailsArrayList.size() - 1 == iterator) {
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


                        for (int iterator = 11; iterator < paymentDetailsArrayList.size(); iterator++) {
                            Modal__B2BPaymentDetails modal__b2BPaymentDetails = paymentDetailsArrayList.get(iterator);
                            try {
                                PdfPTable itemDetailsLabel_table  = new PdfPTable(new float[]{18, 20 , 22 , 12 , 12 , 16});


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





                                    Phrase phraseretailernameLabelTitle = new Phrase(String.valueOf(modal__b2BPaymentDetails.getRetailername()), valueFont_10);

                                    PdfPCell phrasretailernameLabelTitlecell = new PdfPCell(phraseretailernameLabelTitle);
                                    phrasretailernameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                    phrasretailernameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                    phrasretailernameLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                    phrasretailernameLabelTitlecell.setBorderWidthRight(01);
                                    phrasretailernameLabelTitlecell.setPaddingLeft(6);
                                    phrasretailernameLabelTitlecell.setPaddingBottom(10);
                                    itemDetailsLabel_table.addCell(phrasretailernameLabelTitlecell);




                                    phrasQuantityLabelTitle = new Phrase("Payment via " + String.valueOf(modal__b2BPaymentDetails.getPaymentmode()), valueFont_10);
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
                                    if (paymentDetailsArrayList.size() - 1 == iterator) {
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
                            PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {18, 20 , 22 , 12 , 12 , 16 });



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





                                Phrase phraseretailernameLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                                PdfPCell phrasretailernameLabelTitlecell = new PdfPCell(phraseretailernameLabelTitle);
                                phrasretailernameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phrasretailernameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phrasretailernameLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phrasretailernameLabelTitlecell.setBorderWidthRight(01);
                                phrasretailernameLabelTitlecell.setPaddingLeft(6);
                                phrasretailernameLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phrasretailernameLabelTitlecell);






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
                            PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {18, 20 , 22 , 12 , 12 , 16 });



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




                                Phrase phraseretailernameLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                                PdfPCell phrasretailernameLabelTitlecell = new PdfPCell(phraseretailernameLabelTitle);
                                phrasretailernameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phrasretailernameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phrasretailernameLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phrasretailernameLabelTitlecell.setBorderWidthRight(01);
                                phrasretailernameLabelTitlecell.setPaddingLeft(6);
                                phrasretailernameLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phrasretailernameLabelTitlecell);




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
                            PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {18, 20 , 22 , 12 , 12 , 16 });



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




                                Phrase phraseretailernameLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                                PdfPCell phrasretailernameLabelTitlecell = new PdfPCell(phraseretailernameLabelTitle);
                                phrasretailernameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phrasretailernameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phrasretailernameLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phrasretailernameLabelTitlecell.setBorderWidthRight(01);
                                phrasretailernameLabelTitlecell.setPaddingLeft(6);
                                phrasretailernameLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phrasretailernameLabelTitlecell);



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
                            PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {18, 20 , 22 , 12 , 12 , 16 });



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




                                Phrase phraseretailernameLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                                PdfPCell phrasretailernameLabelTitlecell = new PdfPCell(phraseretailernameLabelTitle);
                                phrasretailernameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phrasretailernameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phrasretailernameLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phrasretailernameLabelTitlecell.setBorderWidthRight(01);
                                phrasretailernameLabelTitlecell.setPaddingLeft(6);
                                phrasretailernameLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phrasretailernameLabelTitlecell);



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
                            PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {18, 20 , 22 , 12 , 12 , 16 });



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




                                Phrase phraseretailernameLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                                PdfPCell phrasretailernameLabelTitlecell = new PdfPCell(phraseretailernameLabelTitle);
                                phrasretailernameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phrasretailernameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phrasretailernameLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phrasretailernameLabelTitlecell.setBorderWidthRight(01);
                                phrasretailernameLabelTitlecell.setPaddingLeft(6);
                                phrasretailernameLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phrasretailernameLabelTitlecell);



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
                    for (int iterator = 0; iterator < paymentDetailsArrayList.size(); iterator++) {
                        Modal__B2BPaymentDetails modal__b2BPaymentDetails = paymentDetailsArrayList.get(iterator);
                        try {
                            PdfPTable itemDetailsLabel_table = new PdfPTable(new float[]{18, 20 , 22 , 12 , 12 , 16});


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




                                Phrase phraseretailernameLabelTitle = new Phrase(String.valueOf(modal__b2BPaymentDetails.getRetailername()), valueFont_10);

                                PdfPCell phrasretailernameLabelTitlecell = new PdfPCell(phraseretailernameLabelTitle);
                                phrasretailernameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                                phrasretailernameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                phrasretailernameLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                                phrasretailernameLabelTitlecell.setBorderWidthRight(01);
                                phrasretailernameLabelTitlecell.setPaddingLeft(6);
                                phrasretailernameLabelTitlecell.setPaddingBottom(10);
                                itemDetailsLabel_table.addCell(phrasretailernameLabelTitlecell);






                                phrasQuantityLabelTitle = new Phrase("Payment via " + String.valueOf(modal__b2BPaymentDetails.getPaymentmode()), valueFont_10);


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
                                if (paymentDetailsArrayList.size() - 1 == iterator) {
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
                }
                catch (Exception e) {
                    e.printStackTrace();
                }



                //extraas
                try{




                    try{
                        PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {18, 20 , 22 , 12 , 12 , 16 });



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




                            Phrase phraseretailernameLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                            PdfPCell phrasretailernameLabelTitlecell = new PdfPCell(phraseretailernameLabelTitle);
                            phrasretailernameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phrasretailernameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phrasretailernameLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phrasretailernameLabelTitlecell.setBorderWidthRight(01);
                            phrasretailernameLabelTitlecell.setPaddingLeft(6);
                            phrasretailernameLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phrasretailernameLabelTitlecell);


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
                        PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {18, 20 , 22 , 12 , 12 , 16 });



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





                            Phrase phraseretailernameLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                            PdfPCell phrasretailernameLabelTitlecell = new PdfPCell(phraseretailernameLabelTitle);
                            phrasretailernameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phrasretailernameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phrasretailernameLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phrasretailernameLabelTitlecell.setBorderWidthRight(01);
                            phrasretailernameLabelTitlecell.setPaddingLeft(6);
                            phrasretailernameLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phrasretailernameLabelTitlecell);




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
                        PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {18, 20 , 22 , 12 , 12 , 16 });



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




                            Phrase phraseretailernameLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                            PdfPCell phrasretailernameLabelTitlecell = new PdfPCell(phraseretailernameLabelTitle);
                            phrasretailernameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phrasretailernameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phrasretailernameLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phrasretailernameLabelTitlecell.setBorderWidthRight(01);
                            phrasretailernameLabelTitlecell.setPaddingLeft(6);
                            phrasretailernameLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phrasretailernameLabelTitlecell);




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
                        PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {18, 20 , 22 , 12 , 12 , 16 });



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





                            Phrase phraseretailernameLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                            PdfPCell phrasretailernameLabelTitlecell = new PdfPCell(phraseretailernameLabelTitle);
                            phrasretailernameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phrasretailernameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phrasretailernameLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phrasretailernameLabelTitlecell.setBorderWidthRight(01);
                            phrasretailernameLabelTitlecell.setPaddingLeft(6);
                            phrasretailernameLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phrasretailernameLabelTitlecell);





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
                        PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {18, 20 , 22 , 12 , 12 , 16 });



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




                            Phrase phraseretailernameLabelTitle = new Phrase(String.valueOf(""), valueFont_10);

                            PdfPCell phrasretailernameLabelTitlecell = new PdfPCell(phraseretailernameLabelTitle);
                            phrasretailernameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phrasretailernameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phrasretailernameLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phrasretailernameLabelTitlecell.setBorderWidthRight(01);
                            phrasretailernameLabelTitlecell.setPaddingLeft(6);
                            phrasretailernameLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phrasretailernameLabelTitlecell);





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


            
            
            
            
            
            
            
            
            
/*
            try{
                for(int iterator = 0 ; iterator<paymentDetailsArrayList.size(); iterator++){
                    Modal__B2BPaymentDetails modal__b2BPaymentDetails = paymentDetailsArrayList.get(iterator);
                    try{
                        PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] {18, 20 , 22 , 12 , 12 , 16  });



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




                            Phrase phraseretailernameLabelTitle = new Phrase(String.valueOf(modal__b2BPaymentDetails.getRetailername()), valueFont_10);

                            PdfPCell phrasretailernameLabelTitlecell = new PdfPCell(phraseretailernameLabelTitle);
                            phrasretailernameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                            phrasretailernameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            phrasretailernameLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                            phrasretailernameLabelTitlecell.setBorderWidthRight(01);
                            phrasretailernameLabelTitlecell.setPaddingLeft(6);
                            phrasretailernameLabelTitlecell.setPaddingBottom(10);
                            itemDetailsLabel_table.addCell(phrasretailernameLabelTitlecell);


                            Phrase phrasQuantityLabelTitle = new Phrase("Payment via "+String.valueOf(modal__b2BPaymentDetails.getPaymentmode()), valueFont_10);

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
                            if(paymentDetailsArrayList.size() - 1 == iterator){
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








            try{




                try{
                    PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] { 18, 20 , 22 , 12 , 12 , 16 });



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



                        Phrase phraseretailernameLabelTitle = new Phrase(String.valueOf(""), valueFont_10Bold);

                        PdfPCell phrasretailernameLabelTitlecell = new PdfPCell(phraseretailernameLabelTitle);
                        phrasretailernameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                        phrasretailernameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phrasretailernameLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phrasretailernameLabelTitlecell.setBorderWidthRight(01);
                        phrasretailernameLabelTitlecell.setPaddingLeft(6);
                        phrasretailernameLabelTitlecell.setPaddingBottom(10);
                        itemDetailsLabel_table.addCell(phrasretailernameLabelTitlecell);

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
                    PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] { 18, 20 , 22 , 12 , 12 , 16 });



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



                        Phrase phraseretailernameLabelTitle = new Phrase(String.valueOf(""), valueFont_10Bold);

                        PdfPCell phrasretailernameLabelTitlecell = new PdfPCell(phraseretailernameLabelTitle);
                        phrasretailernameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                        phrasretailernameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phrasretailernameLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phrasretailernameLabelTitlecell.setBorderWidthRight(01);
                        phrasretailernameLabelTitlecell.setPaddingLeft(6);
                        phrasretailernameLabelTitlecell.setPaddingBottom(10);
                        itemDetailsLabel_table.addCell(phrasretailernameLabelTitlecell);

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
                    PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] { 18, 20 , 22 , 12 , 12 , 16 });



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



                        Phrase phraseretailernameLabelTitle = new Phrase(String.valueOf(""), valueFont_10Bold);

                        PdfPCell phrasretailernameLabelTitlecell = new PdfPCell(phraseretailernameLabelTitle);
                        phrasretailernameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                        phrasretailernameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phrasretailernameLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phrasretailernameLabelTitlecell.setBorderWidthRight(01);
                        phrasretailernameLabelTitlecell.setPaddingLeft(6);
                        phrasretailernameLabelTitlecell.setPaddingBottom(10);
                        itemDetailsLabel_table.addCell(phrasretailernameLabelTitlecell);


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
                    PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] { 18, 20 , 22 , 12 , 12 , 16 });



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




                        Phrase phraseretailernameLabelTitle = new Phrase(String.valueOf(""), valueFont_10Bold);

                        PdfPCell phrasretailernameLabelTitlecell = new PdfPCell(phraseretailernameLabelTitle);
                        phrasretailernameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                        phrasretailernameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phrasretailernameLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phrasretailernameLabelTitlecell.setBorderWidthRight(01);
                        phrasretailernameLabelTitlecell.setPaddingLeft(6);
                        phrasretailernameLabelTitlecell.setPaddingBottom(10);
                        itemDetailsLabel_table.addCell(phrasretailernameLabelTitlecell);

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


                        Phrase phrasTotalLabelTitle = new Phrase(String.valueOf(twoDecimalConverterWithComma.format(closingBalance)), valueFont_10);

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
                    PdfPTable itemDetailsLabel_table = new PdfPTable(new float[] { 18, 20 , 22 , 12 , 12 , 16 });



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



                        Phrase phraseretailernameLabelTitle = new Phrase(String.valueOf(""), valueFont_10Bold);

                        PdfPCell phrasretailernameLabelTitlecell = new PdfPCell(phraseretailernameLabelTitle);
                        phrasretailernameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                        phrasretailernameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phrasretailernameLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phrasretailernameLabelTitlecell.setBorderWidthRight(01);
                        phrasretailernameLabelTitlecell.setPaddingLeft(6);
                        phrasretailernameLabelTitlecell.setPaddingBottom(10);
                        itemDetailsLabel_table.addCell(phrasretailernameLabelTitlecell);


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


 */










        }
        catch (Exception e){
            e.printStackTrace();
        }





/*
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






    private void closeSearchBarEditText() {
        search_IconLayout.setVisibility(View.VISIBLE);
        retailerName_textView.setVisibility(View.VISIBLE);
        retailerName_editText.setVisibility(View.GONE);
        close_IconLayout.setVisibility(View.GONE);
    }

    private void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
    private void showSearchBarEditText() {
        retailerName_editText.setVisibility(View.VISIBLE);
        retailerName_textView.setVisibility(View.GONE);
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


}