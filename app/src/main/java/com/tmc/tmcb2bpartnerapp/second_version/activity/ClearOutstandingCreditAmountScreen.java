package com.tmc.tmcb2bpartnerapp.second_version.activity;

import androidx.appcompat.app.AppCompatActivity;
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
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BRetailerDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal_B2BRetailerCreditDetails;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal__B2BCreditTransactionHistory;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal__B2BPaymentDetails;
import com.tmc.tmcb2bpartnerapp.second_version.adapter.Adapter_AutoComplete_RetailerMobileNoForCreditClearance;
import com.tmc.tmcb2bpartnerapp.second_version.apiRequestServices.B2BCreditTransactionHistory;
import com.tmc.tmcb2bpartnerapp.second_version.apiRequestServices.B2BPaymentDetails;
import com.tmc.tmcb2bpartnerapp.second_version.apiRequestServices.B2BRetailerCreditDetails;
import com.tmc.tmcb2bpartnerapp.second_version.interfaces.B2BCreditTransactionHistoryInterface;
import com.tmc.tmcb2bpartnerapp.second_version.interfaces.B2BPaymentDetailsInterface;
import com.tmc.tmcb2bpartnerapp.second_version.interfaces.B2BRetailerCreditDetailsInterface;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
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
import static com.itextpdf.text.BaseColor.BLACK;
import static com.itextpdf.text.BaseColor.GRAY;
import static com.itextpdf.text.BaseColor.LIGHT_GRAY;
import static com.itextpdf.text.BaseColor.WHITE;

public class ClearOutstandingCreditAmountScreen extends AppCompatActivity {
    //widgets
    AutoCompleteTextView buyerName_autoComplete_textview ;
    public static LinearLayout loadingpanelmask ,loadingPanel;
    LinearLayout search_IconLayout,back_IconLayout,close_IconLayout;
    //buyerName_textview
    //buyerNamerightarrow
    //buyerMobileNorightarrow
    //amountPaid_textview
    //amountpaidrightarrow
    TextView amountPaidDate_textview , receivedByText ,buyerMobileNo_textview , buyersAdddress_textview ,
            totalOutStandingAmount_textview;
    EditText amountPaid_editText,notes_edittext;
    Spinner paymentMode_spinner ;
    Button save_button;
    DatePickerDialog datepicker;
    //ArrayList
    public static ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList = new ArrayList<>();
    
    //boolean
    boolean  isRetailerDetailsServiceCalled = false ;
    boolean isSearchButtonClicked = false;
    boolean isRetailerCreditDetailsCalled = false;
    boolean isRetailerCreditDetailsIsNotCreated = false;
    boolean isRetailerCreditTransactionHistoryCalled = false;
    boolean isPaymentDetailsCalled = false;
    boolean isPaymentNotSelectedManually = false;
    boolean isPDF_FIle = false;
    boolean isSaveButtonClicked = false;



    //interface
    B2BRetailerDetailsInterface callback_retailerDetailsInterface = null;
    B2BRetailerCreditDetailsInterface callBackb2BRetailerCreditDetailsInterface = null;
    B2BCreditTransactionHistoryInterface callB2BCreditTransactionHistoryInterface = null;
    B2BPaymentDetailsInterface callB2BPaymentDetailsInterface = null;


    //int
    private static int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;


    //double 
    double oldCreditAmountOfUser= 0 , newCreditAmountOfUser = 0 , amount_paid_double = 0;


    //String
    String amount_paid_String ="0" , deliveryCenterName = "", supervisorName ="",deliveryCenterKey ="" , updatedTime ="",
            supervisorMobileno = "",paymentid="",
            retailerAddress ="" , retailerMobileNo ="" , retailerKey = "" , retailerName ="",selectedPaymentMode ="";





    static DecimalFormat threeDecimalConverter = new DecimalFormat(Constants.threeDecimalPattern);
    static DecimalFormat twoDecimalConverter = new DecimalFormat(Constants.twoDecimalPattern);
    static DecimalFormat twoDecimalConverterWithComma = new DecimalFormat(Constants.twoDecimalWithCommaPattern);





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_outstanding_credit_amount_screen);

        loadingPanel = findViewById(R.id.loadingPanel);
        loadingpanelmask = findViewById(R.id.loadingpanelmask);
        back_IconLayout = findViewById(R.id.back_IconLayout);
        search_IconLayout = findViewById(R.id.search_IconLayout);
        close_IconLayout = findViewById(R.id.close_IconLayout);
        buyerName_autoComplete_textview = findViewById(R.id.buyerName_autoComplete_textview);
       // buyerName_textview  = findViewById(R.id.buyerName_textview);
        buyerMobileNo_textview  = findViewById(R.id.buyerMobileNo_textview);
        buyersAdddress_textview  = findViewById(R.id.buyersAdddress_textview);
        totalOutStandingAmount_textview  = findViewById(R.id.totalOutStandingAmount_textview);
     //   buyerNamerightarrow = findViewById(R.id.buyerNamerightarrow);
        paymentMode_spinner = findViewById(R.id.paymentMode_spinner);
       // buyerMobileNorightarrow = findViewById(R.id.buyerMobileNorightarrow);
        amountPaid_editText = findViewById(R.id.amountPaid_editText);
      //  amountPaid_textview = findViewById(R.id.amountPaid_textview);
      //  amountpaidrightarrow = findViewById(R.id.amountpaidrightarrow);
        save_button = findViewById(R.id.save_button);
        notes_edittext = findViewById(R.id.notes_edittext);





        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);
        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");



        SharedPreferences sh = getSharedPreferences("LoginData",MODE_PRIVATE);
        supervisorName = sh.getString("UserName","");
        supervisorMobileno = sh.getString("UserMobileNumber","");
        
        
        
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        addDatatoPaymentTypeSpinner();


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
      /*
        layoutDocument.addDocListener(new DocListener() {
          @Override
          public void open() {
            //  Toast.makeText(ClearOutstandingCreditAmountScreen.this, "opened", Toast.LENGTH_SHORT).show();

          }

          @Override
          public void close() {
             // Toast.makeText(ClearOutstandingCreditAmountScreen.this, "closed", Toast.LENGTH_SHORT).show();
              try {
                  buyerName_autoComplete_textview.setText("");
                  buyerName_textview.setText("");
                  buyerName_autoComplete_textview.clearFocus();

                  buyerName_autoComplete_textview.dismissDropDown();
                  hideKeyboard(buyerName_autoComplete_textview);


                  buyerMobileNo_textview.setText("");
                  buyersAdddress_textview.setText("");
                  totalOutStandingAmount_textview.setText("0.00");
                  addDatatoPaymentTypeSpinner();
                  amountPaid_textview.setText("0");
                  amountPaid_editText.setText("0");
                  notes_edittext.setText("");

                  isRetailerDetailsServiceCalled = false;
                  isSearchButtonClicked = false;
                  isRetailerCreditDetailsCalled = false;
                  isRetailerCreditDetailsIsNotCreated = false;
                  isRetailerCreditTransactionHistoryCalled = false;
                  isPaymentDetailsCalled = false;
                  isPaymentNotSelectedManually = false;
                  isPDF_FIle = false;
                  oldCreditAmountOfUser = 0;
                  newCreditAmountOfUser = 0;
                  amount_paid_double = 0;
                  amount_paid_String = "0";
                  updatedTime = "";
                  retailerAddress = "";
                  retailerMobileNo = "";
                  retailerKey = "";
                  retailerName = "";
                  selectedPaymentMode = "";

              }
              catch (Exception e){
                  e.printStackTrace();
              }
          }

          @Override
          public boolean newPage() {
              return false;
          }

          @Override
          public boolean setPageSize(Rectangle pageSize) {
              return false;
          }

          @Override
          public boolean setMargins(float marginLeft, float marginRight, float marginTop, float marginBottom) {
              return false;
          }

          @Override
          public boolean setMarginMirroring(boolean marginMirroring) {
              return false;
          }

          @Override
          public boolean setMarginMirroringTopBottom(boolean marginMirroringTopBottom) {
              return false;
          }

          @Override
          public void setPageCount(int pageN) {

          }

          @Override
          public void resetPageCount() {

          }

          @Override
          public boolean add(Element element) throws DocumentException {
              return false;
          }
      });

       */

                save_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if (!isSaveButtonClicked) {
                                isSaveButtonClicked = true;
                                amount_paid_String = amountPaid_editText.getText().toString();
                                try {
                                    amount_paid_String = amount_paid_String.replaceAll("[^\\d.]", "");
                                    if (amount_paid_String.equals("")) {
                                        amount_paid_String = "0";
                                    }
                                } catch (Exception e) {
                                    amount_paid_String = "0";
                                    e.printStackTrace();
                                }


                                try {
                                    amount_paid_double = Double.parseDouble(amount_paid_String);

                                } catch (Exception e) {
                                    if(String.valueOf(e).contains("multiple points")){
                                        AlertDialogClass.showDialog(ClearOutstandingCreditAmountScreen.this, R.string.CannotSaveWhenAmounthaveMultiplePointsAlert);
                                        return;
                                    }
                                    e.printStackTrace();
                                }

                                if (oldCreditAmountOfUser >= amount_paid_double) {


                                    if (amount_paid_double > 0) {


                                        new TMCAlertDialogClass(ClearOutstandingCreditAmountScreen.this, R.string.app_name, R.string.PleaseConfirmrecordpayment,
                                                R.string.Yes_Text, R.string.No_Text,
                                                new TMCAlertDialogClass.AlertListener() {
                                                    @Override
                                                    public void onYes() {

                                                        try {
                                                            showProgressBar(true);
                                                            updatedTime = DateParser.getDate_and_time_newFormat();
                                                            //amountPaid_textview.setText(String.valueOf(amount_paid_String));


                                                            newCreditAmountOfUser = oldCreditAmountOfUser - amount_paid_double;
                                                         //   amountpaidrightarrow.setVisibility(View.VISIBLE);
                                                           // amountPaid_textview.setVisibility(View.VISIBLE);
                                                           // amountPaid_editText.setVisibility(View.GONE);

                                                            paymentid = String.valueOf(System.currentTimeMillis());
                                                            Call_and_Execute_RetailerCreditDetails(Constants.CallUPDATEMethod);
                                                            Call_and_Execute_CreditTransactionHistory(Constants.CallADDMethod);
                                                            Call_and_Execute_PaymentDetails(Constants.CallADDMethod);


                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }


                                                    }

                                                    @Override
                                                    public void onNo() {

                                                        isSaveButtonClicked = false;
                                                    }
                                                });

                                    } else {
                                        isSaveButtonClicked = false;
                                        AlertDialogClass.showDialog(ClearOutstandingCreditAmountScreen.this, R.string.CannotSaveWhenAmountPaidislessthanZeroAlert);

                                    }
                                } else {
                                    AlertDialogClass.showDialog(ClearOutstandingCreditAmountScreen.this, R.string.CannotSaveWhenAmountPaidisgreaterThanOutstandingAmountAlert);
                                    isSaveButtonClicked = false;
                                }
                            } else {
                                Toast.makeText(ClearOutstandingCreditAmountScreen.this, "Please Wait ", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            isSaveButtonClicked = false;
                            e.printStackTrace();
                        }
                    }
                });


        paymentMode_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                selectedPaymentMode = parent.getItemAtPosition(position).toString();
               /* if (isPaymentNotSelectedManually) {
                    isPaymentNotSelectedManually = false;
                    showProgressBar(false);
                } else {
                    selectedPaymentMode = parent.getItemAtPosition(position).toString();
                }

                */
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });
       /* amountPaid_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                amountpaidrightarrow.setVisibility(View.GONE);
                amountPaid_textview.setVisibility(View.GONE);
                amountPaid_editText.setVisibility(View.VISIBLE);
                amountPaid_editText.requestFocus();
                showKeyboard(amountPaid_editText);

            }
        });

        */

        amountPaid_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                amount_paid_String = amountPaid_editText.getText().toString();

                try{
                    amount_paid_String = amount_paid_String.replaceAll("[^\\d.]", "");
                    if (amount_paid_String.equals("")) {
                        amount_paid_String = "0";
                    }
                }
                catch (Exception e){
                    amount_paid_String = "0";
                    e.printStackTrace();
                }


                try{
                    amount_paid_double = Double.parseDouble(amount_paid_String);

                }
                catch (Exception e){
                    if(String.valueOf(e).contains("multiple points")){
                        AlertDialogClass.showDialog(ClearOutstandingCreditAmountScreen.this, R.string.CannotSaveWhenAmounthaveMultiplePointsAlert);

                    }
                    e.printStackTrace();
                }
                if(oldCreditAmountOfUser >=amount_paid_double ) {
                    if (amount_paid_double > 0) {

                      //  amountPaid_textview.setText(String.valueOf(amount_paid_String));


                        newCreditAmountOfUser = oldCreditAmountOfUser - amount_paid_double;
                       // amountpaidrightarrow.setVisibility(View.VISIBLE);
                        //amountPaid_textview.setVisibility(View.VISIBLE);
                     //   amountPaid_editText.setVisibility(View.GONE);


                    } else {
                        AlertDialogClass.showDialog(ClearOutstandingCreditAmountScreen.this, R.string.CannotSaveWhenAmountPaidislessthanZeroAlert);

                    }
                }
                else {
                    AlertDialogClass.showDialog(ClearOutstandingCreditAmountScreen.this, R.string.CannotSaveWhenAmountPaidisgreaterThanOutstandingAmountAlert);

                }


                return false;
            }
        });

        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
   /*     buyerNamerightarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int textlength = buyerName_autoComplete_textview.getText().toString().length();
                isSearchButtonClicked =true;
                showKeyboard(buyerName_autoComplete_textview);
                showSearchBarBuyerNameEditText();
            }
        });
        buyerName_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int textlength = buyerName_autoComplete_textview.getText().toString().length();
                isSearchButtonClicked =true;
                showKeyboard(buyerName_autoComplete_textview);
                showSearchBarBuyerNameEditText();
            }
        });

    */









    }


    private String getMonthString(int value) {
        if (value == 0) {
            return "Jan";
        } else if (value == 1) {
            return "Feb";
        } else if (value == 2) {
            return "Mar";
        } else if (value == 3) {
            return "Apr";
        } else if (value == 4) {
            return "May";
        } else if (value == 5) {
            return "Jun";
        } else if (value == 6) {
            return "Jul";
        } else if (value == 7) {
            return "Aug";
        } else if (value == 8) {
            return "Sep";
        } else if (value == 9) {
            return "Oct";
        } else if (value == 10) {
            return "Nov";
        } else if (value == 11) {
            return "Dec";
        }
        return "";
    }


    private String getDayString(int value) {
        if (value == 1) {
            return "Sun";
        }  else if (value == 2) {
            return "Mon";
        } else if (value == 3) {
            return "Tue";
        } else if (value == 4) {
            return "Wed";
        } else if (value == 5) {
            return "Thu";
        } else if (value == 6) {
            return "Fri";
        }
        else if (value == 7) {
            return "Sat";
        }
        return "";
    }


/*
    private void OpenRecordPaymentdialog() {

        //  show_scan_barcode_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        show_recordPayment_To_ClearOutstanding_Dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //  show_recordPayment_To_ClearOutstanding_Dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        try{
            show_recordPayment_To_ClearOutstanding_Dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

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
                show_recordPayment_To_ClearOutstanding_Dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);


            } else {

                show_recordPayment_To_ClearOutstanding_Dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

            }
        } catch (Exception e) {
            show_recordPayment_To_ClearOutstanding_Dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

            e.printStackTrace();

        }



        try {
            if (BaseActivity.isDeviceIsMobilePhone) {
                show_recordPayment_To_ClearOutstanding_Dialog.setContentView(R.layout.dialog_to_record_outstanding_payment);


            } else {

                show_recordPayment_To_ClearOutstanding_Dialog.setContentView(R.layout.dialog_to_record_outstanding_payment);
            }

        } catch (Exception e) {
            show_recordPayment_To_ClearOutstanding_Dialog.setContentView(R.layout.dialog_to_record_outstanding_payment);
            e.printStackTrace();
        }


        // show_scan_barcode_dialog.setCancelable(false);
        show_recordPayment_To_ClearOutstanding_Dialog.setCanceledOnTouchOutside(false);
        show_recordPayment_To_ClearOutstanding_Dialog.show();
        LinearLayout close_IconLayout = show_recordPayment_To_ClearOutstanding_Dialog.findViewById(R.id.close_IconLayout);

        TextView amountPaidDate_textview = show_recordPayment_To_ClearOutstanding_Dialog.findViewById(R.id.amountPaidDate_textview);
        TextView paymentMode_textview = show_recordPayment_To_ClearOutstanding_Dialog.findViewById(R.id.paymentMode_textview);
        TextView amountPaid_textview = show_recordPayment_To_ClearOutstanding_Dialog.findViewById(R.id.amountPaid_textview);



        amountPaidDate_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        paymentMode_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });





        amountPaid_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    showDialog_to_addAmount_toClearOutStanding_Dialog_dialog();



                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }
        });





        close_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




    }

    private void showDialog_to_addAmount_toClearOutStanding_Dialog_dialog() {

        addAmount_toClearOutstanding_Dialogs_Dialog = new Dialog(ClearOutstandingCreditAmountScreen.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_TranslucentDecor);

        addAmount_toClearOutstanding_Dialogs_Dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        addAmount_toClearOutstanding_Dialogs_Dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        addAmount_toClearOutstanding_Dialogs_Dialog.setContentView(R.layout.addgiven_amount_for_outstanding_credit);

        addAmount_toClearOutstanding_Dialogs_Dialog.show();

    LinearLayout close_IconLayout_addAmountDialog = addAmount_toClearOutstanding_Dialogs_Dialog.findViewById(R.id.close_IconLayout_addAmountDialog);
        close_IconLayout_addAmountDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


 */

    private void addDatatoPaymentTypeSpinner() {
        isPaymentNotSelectedManually = true;
        String[] ordertype=getResources().getStringArray(R.array.paymentmode);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ClearOutstandingCreditAmountScreen.this, android.R.layout.simple_spinner_item, ordertype);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        paymentMode_spinner.setAdapter(arrayAdapter);



    }

    public void call_and_init_B2BRetailerDetailsService(String CallMethod) {
        showProgressBar(true);
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
                if(result.equals(Constants.item_Already_Added_volley)){
                    AlertDialogClass.showDialog(ClearOutstandingCreditAmountScreen.this, R.string.retailersAlreadyCreated_Instruction);
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

    private void setAdapterForRetailerDetails() {


        try{
                retailerDetailsArrayList =  sortThisArrayUsingRetailerName_mobileNo(retailerDetailsArrayList);



        }
        catch (Exception e){
            e.printStackTrace();
        }




        try {
          Adapter_AutoComplete_RetailerMobileNoForCreditClearance adapter_autoComplete_retailerMobileNo = new Adapter_AutoComplete_RetailerMobileNoForCreditClearance(ClearOutstandingCreditAmountScreen.this, retailerDetailsArrayList, ClearOutstandingCreditAmountScreen.this,false);
            adapter_autoComplete_retailerMobileNo.setHandler(newHandler());

            showProgressBar(false);
            buyerName_autoComplete_textview.setAdapter(adapter_autoComplete_retailerMobileNo);
            buyerName_autoComplete_textview.clearFocus();
            buyerName_autoComplete_textview.setThreshold(1);
            buyerName_autoComplete_textview.dismissDropDown();
            buyerName_autoComplete_textview.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_CLASS_TEXT);


           /*


            try {
                Adapter_AutoComplete_RetailerMobileNo   adapter_autoComplete_retailerMobileNo = new Adapter_AutoComplete_RetailerMobileNo(ClearOutstandingCreditAmountScreen.this, retailerDetailsArrayList, ClearOutstandingCreditAmountScreen.this , false);
                adapter_autoComplete_retailerMobileNo.setHandler(newHandler());


                buyerName_autoComplete_textview.setAdapter(adapter_autoComplete_retailerMobileNo);
                buyerName_autoComplete_textview.clearFocus();
                buyerName_autoComplete_textview.setThreshold(1);
                buyerName_autoComplete_textview.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_CLASS_TEXT);



            }
            catch (Exception e){
                e.printStackTrace();
            }

           */

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
                        Call_and_Execute_RetailerCreditDetails(Constants.CallGETMethod);
                        //buyerName_textview.setText(retailerName);
                        buyerMobileNo_textview.setText(retailerMobileNo);

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


                            totalOutStandingAmount_textview.setText("0.00");
                            addDatatoPaymentTypeSpinner();
                         //   amountPaid_textview.setText("0");
                            notes_edittext.setText("");
                            amountPaid_editText.setText("0");

                            isPaymentNotSelectedManually = false;
                            isPDF_FIle = false;
                            oldCreditAmountOfUser = 0;
                            newCreditAmountOfUser = 0;
                            amount_paid_double = 0;
                            amount_paid_String = "0";
                            updatedTime = "";
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


    private void hideKeyboard(AutoCompleteTextView editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
    private void closebuyerNameSearchBarEditText() {
      //  buyerName_textview.setVisibility(View.VISIBLE);
       // buyerNamerightarrow.setVisibility(View.VISIBLE);
       // close_IconLayout.setVisibility(View.GONE);
        search_IconLayout.setVisibility(View.VISIBLE);
        //buyerName_textview.setText(retailerName);
        buyerName_autoComplete_textview.setText(retailerName);
        buyerName_autoComplete_textview.clearFocus();
        buyerName_autoComplete_textview.setThreshold(1);
        buyerName_autoComplete_textview.dismissDropDown();
    }



    private void showSearchBarBuyerNameEditText() {
        //buyerNamerightarrow.setVisibility(View.GONE);
       // buyerName_textview.setVisibility(View.GONE);
        close_IconLayout.setVisibility(View.VISIBLE);
       // search_IconLayout.setVisibility(View.GONE);
        buyerName_autoComplete_textview.setVisibility(View.VISIBLE);
        if(buyerName_autoComplete_textview.getText().toString().equals(" ") || buyerName_autoComplete_textview.getText().length()==0){
            buyerName_autoComplete_textview.setText("" );

        }
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
                Create_and_sharePDF(false);
                isSaveButtonClicked = false;
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
            Modal__B2BPaymentDetails.retailername_static = retailerName;
            Modal__B2BPaymentDetails.retailermobileno_static = retailerMobileNo;
            Modal__B2BPaymentDetails.transactiontime_static = updatedTime;
            Modal__B2BPaymentDetails.paymentid_static = paymentid;

            Modal__B2BPaymentDetails.deliverycentrekey_static = deliveryCenterKey;
            Modal__B2BPaymentDetails.transactionvalue_static = (String.valueOf(twoDecimalConverter.format(amount_paid_double)));
            Modal__B2BPaymentDetails.notes_static = String.valueOf(notes_edittext.getText().toString()) ;
            Modal__B2BPaymentDetails.paymentmode_static = selectedPaymentMode;
            Modal__B2BPaymentDetails.transactiontype_static = Constants.transactiontype_OUTSTANDINGPAYMENT;

            String getApiToCall = API_Manager.addPaymentDetails ;
            B2BPaymentDetails asyncTask = new B2BPaymentDetails(callB2BPaymentDetailsInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }
    }

    private void Create_and_sharePDF(boolean isJustNeedTOAskPermission) {
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


                        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(ClearOutstandingCreditAmountScreen.this, WRITE_EXTERNAL_STORAGE);
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


                        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(ClearOutstandingCreditAmountScreen.this, WRITE_EXTERNAL_STORAGE);
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
            Modal__B2BCreditTransactionHistory.orderid_static="";

            Modal__B2BCreditTransactionHistory.retailerkey_static = retailerKey;
            Modal__B2BCreditTransactionHistory.retailermobileno_static = retailerMobileNo;
            Modal__B2BCreditTransactionHistory.transactiontime_static = updatedTime;
            Modal__B2BCreditTransactionHistory.retailername_static = retailerName;
            Modal__B2BCreditTransactionHistory.paymentid_static = paymentid;

            Modal__B2BCreditTransactionHistory.deliverycentrekey_static = deliveryCenterKey;
            Modal__B2BCreditTransactionHistory.transactionvalue_static = (String.valueOf(twoDecimalConverter.format(amount_paid_double)));
            Modal__B2BCreditTransactionHistory.supervisormobileno_static = supervisorMobileno;
            Modal__B2BCreditTransactionHistory.oldamountincredit_static = String.valueOf(twoDecimalConverter.format(oldCreditAmountOfUser));
            Modal__B2BCreditTransactionHistory.newamountincredit_static = String.valueOf(twoDecimalConverter.format(newCreditAmountOfUser));

            String getApiToCall = API_Manager.addCreditTransactionHistory ;
            B2BCreditTransactionHistory asyncTask = new B2BCreditTransactionHistory(callB2BCreditTransactionHistoryInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }
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
                    newCreditAmountOfUser =0;

                    try{
                        totalOutStandingAmount_textview.setText(String.valueOf(twoDecimalConverterWithComma.format(oldCreditAmountOfUser)));
                     //   buyerName_textview.setText(String.valueOf(retailerName));
                        buyerName_autoComplete_textview.setText(String.valueOf(retailerName));
                        buyerName_autoComplete_textview.clearFocus();
                        buyerName_autoComplete_textview.setThreshold(1);
                        buyerName_autoComplete_textview.dismissDropDown();
                        buyerMobileNo_textview.setText(String.valueOf(retailerMobileNo));
                        buyersAdddress_textview.setText(String.valueOf(retailerAddress));

                        showProgressBar(false);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
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
                            buyerName_autoComplete_textview.setText(String.valueOf(retailerName));
                            buyerName_autoComplete_textview.clearFocus();
                            buyerName_autoComplete_textview.setThreshold(1);
                            buyerName_autoComplete_textview.dismissDropDown();
                            totalOutStandingAmount_textview.setText(String.valueOf(twoDecimalConverterWithComma.format(oldCreditAmountOfUser)));
                          //  buyerName_textview.setText(String.valueOf(retailerName));
                            buyerMobileNo_textview.setText(String.valueOf(retailerMobileNo));
                            buyersAdddress_textview.setText(String.valueOf(retailerAddress));

                            showProgressBar(false);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }




                       
                    }
                    else if(callMethod.equals(Constants.CallUPDATEMethod)){

                    }
                }

             

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isRetailerCreditDetailsCalled = false;
                Toast.makeText(ClearOutstandingCreditAmountScreen.this, "Volley error in Executing RetailerCredit Details", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void notifyProcessingError(Exception error) {
                isRetailerCreditDetailsCalled = false;
                Toast.makeText(ClearOutstandingCreditAmountScreen.this, "Processing error in Executing RetailerCredit Details", Toast.LENGTH_SHORT).show();

            }
        };

        if(callMethod.equals(Constants.CallGETMethod)) {
            oldCreditAmountOfUser = 0;
            newCreditAmountOfUser = 0;
            String getApiToCall = API_Manager.getRetailerCreditDetailsUsingRetailerKeyDeliveryCentrekey +"?deliverycentrekey="+ deliveryCenterKey+"&retailerkey="+retailerKey;
            B2BRetailerCreditDetails asyncTask = new B2BRetailerCreditDetails(callBackb2BRetailerCreditDetailsInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }

        else   if(callMethod.equals(Constants.CallUPDATEMethod)) {
            Modal_B2BRetailerCreditDetails.setDeliverycentrekey_static(deliveryCenterKey);
            Modal_B2BRetailerCreditDetails.setLastupdatedtime_static(updatedTime);
            Modal_B2BRetailerCreditDetails.setRetailerkey_static(retailerKey);
            Modal_B2BRetailerCreditDetails.setTotalamountincredit_static(String.valueOf(twoDecimalConverter.format(newCreditAmountOfUser)));
            String getApiToCall = API_Manager.updateRetailerCreditDetails;
            B2BRetailerCreditDetails asyncTask = new B2BRetailerCreditDetails(callBackb2BRetailerCreditDetailsInterface, getApiToCall, callMethod);
            asyncTask.execute();
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
            String filename = "Record Payment Receipt "+retailerName+" on "+date+" "+timeinLong+".pdf";
            final File file = new File(folder, filename);
            file.createNewFile();
            try {
                Document    layoutDocument = new Document();
                FileOutputStream fOut = new FileOutputStream(file);
                PdfWriter.getInstance(layoutDocument, fOut);
                layoutDocument.open();

                //  addItemRows(layoutDocument);
                // addItemRowsInOldPDFFormat(layoutDocument);
                addItemRowsInNewPDFFormat(layoutDocument);

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
            Uri outputPdfUri = FileProvider.getUriForFile(this, ClearOutstandingCreditAmountScreen.this.getPackageName() + ".provider", file);

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

        Font valueFont_10Bold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                Font.BOLD);
        Font valueFont_8Bold = new Font(Font.FontFamily.TIMES_ROMAN, 10,
                Font.BOLD);

        Font valueFont_10 = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                Font.NORMAL);
        Font valueFont_8 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
                Font.NORMAL);


        RoundRectangle roundRectange = new RoundRectangle();
        PdfPTable wholePDFContentOutline_table = new PdfPTable(1);

        PdfPTable wholePDFContentWithOut_Outline_table = new PdfPTable(1);


        PdfPTable tmcLogoImage_table = new PdfPTable(new float[]{50, 25, 25});

        try {
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


            try {

                tmcLogoImage_table.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tmcLogoImage_table.setTotalWidth(100f);

                layoutDocument.add(tmcLogoImage_table);

            } catch (Exception e) {
                e.printStackTrace();
            }


            PdfPTable billtimeDetails_table = new PdfPTable(2);
            try {

                Phrase phrasebilltimeDetails = new Phrase("DATE : " + DateParser.getDate_newFormat() + "      TIME : " + DateParser.getTime_newFormat(), valueFont_8);
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

                Phrase phraseSupervisorNameLabelTitle = new Phrase("Supervisor Name : " + String.valueOf(supervisorName) + "  ", valueFont_8Bold);

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
            PdfPTable Whole_Warehouse_and_RetailerDetails_table = new PdfPTable(new float[]{40, 60});
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


            PdfPTable Whole_SupplerDetails_table = new PdfPTable(new float[]{40, 60});
            try {

                try {

                    Phrase phraseretailerNameLabelTitle = new Phrase("Store Name :  ", valueFont_10Bold);

                    PdfPCell phraseretailerNameLabelTitlecell = new PdfPCell(phraseretailerNameLabelTitle);
                    phraseretailerNameLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                    phraseretailerNameLabelTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    phraseretailerNameLabelTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                    phraseretailerNameLabelTitlecell.setPaddingLeft(6);
                    phraseretailerNameLabelTitlecell.setPaddingBottom(10);
                    Whole_SupplerDetails_table.addCell(phraseretailerNameLabelTitlecell);


                    Phrase phraseRetailerNameTitle = new Phrase(retailerName + "\n", valueFont_10);

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
                    text = retailerMobileNo.replaceAll("[+]91", "");
                    Phrase phraseRetailerNameTitle = new Phrase(text + "\n", valueFont_10);

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


                    Phrase phraseRetailerNameTitle = new Phrase(retailerAddress + "\n", valueFont_10);

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
                Whole_Warehouse_and_RetailerDetails_table_Cell.setBorderWidthBottom(1);
                wholePDFContentWithOut_Outline_table.addCell(Whole_Warehouse_and_RetailerDetails_table_Cell);

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
                PdfPTable paymentDetailsLabel_table = new PdfPTable(new float[]{40, 25, 35});


                Phrase phraseParticularsLabelTitle = new Phrase(" Particulars    ", valueFont_10Bold);

                PdfPCell phraseParticularsLabelTitlecell = new PdfPCell(phraseParticularsLabelTitle);
                phraseParticularsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                phraseParticularsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phraseParticularsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                phraseParticularsLabelTitlecell.setBorderWidthRight(01);
                phraseParticularsLabelTitlecell.setPaddingLeft(6);
                phraseParticularsLabelTitlecell.setPaddingTop(5);
                phraseParticularsLabelTitlecell.setPaddingBottom(10);
                paymentDetailsLabel_table.addCell(phraseParticularsLabelTitlecell);


                Phrase phrasEmptyLabelTitle = new Phrase("     ", valueFont_10Bold);
                PdfPCell phraseEmptyLabelTitlecell = new PdfPCell(phrasEmptyLabelTitle);
                phraseEmptyLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                phraseEmptyLabelTitlecell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                phraseEmptyLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                phraseEmptyLabelTitlecell.setPaddingTop(5);
                phraseEmptyLabelTitlecell.setPaddingLeft(6);
                phraseEmptyLabelTitlecell.setPaddingBottom(10);
                paymentDetailsLabel_table.addCell(phraseEmptyLabelTitlecell);


                Phrase phrasQuantityLabelTitle = new Phrase(" Amount    ", valueFont_10Bold);

                PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                phraseQuantityLabelTitlecell.setBorderWidthRight(01);
                phraseQuantityLabelTitlecell.setPaddingTop(5);
                phraseQuantityLabelTitlecell.setPaddingLeft(6);
                phraseQuantityLabelTitlecell.setPaddingBottom(10);
                paymentDetailsLabel_table.addCell(phraseQuantityLabelTitlecell);


                try {
                    PdfPCell itemDetails_table_Cell = new PdfPCell(paymentDetailsLabel_table);
                    itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                    itemDetails_table_Cell.setBorderWidthTop(1);
                    itemDetails_table_Cell.setBorderWidthBottom(1);
                    itemDetails_table_Cell.setBackgroundColor(WHITE);
                    wholePDFContentWithOut_Outline_table.addCell(itemDetails_table_Cell);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                PdfPTable paymentDetails_table = new PdfPTable(new float[]{40, 25, 35});


                Phrase phraseParticularsLabelTitle = new Phrase(" Payment via " + selectedPaymentMode, valueFont_10);

                PdfPCell phraseParticularsLabelTitlecell = new PdfPCell(phraseParticularsLabelTitle);
                phraseParticularsLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                phraseParticularsLabelTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                phraseParticularsLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                phraseParticularsLabelTitlecell.setBorderWidthRight(01);
                phraseParticularsLabelTitlecell.setPaddingLeft(6);
                phraseParticularsLabelTitlecell.setPaddingTop(5);
                phraseParticularsLabelTitlecell.setPaddingBottom(10);
                paymentDetails_table.addCell(phraseParticularsLabelTitlecell);


                Phrase phrasEmptyLabelTitle = new Phrase("     ", valueFont_10Bold);
                PdfPCell phraseEmptyLabelTitlecell = new PdfPCell(phrasEmptyLabelTitle);
                phraseEmptyLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                phraseEmptyLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                phraseEmptyLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                phraseEmptyLabelTitlecell.setPaddingTop(5);
                phraseEmptyLabelTitlecell.setPaddingLeft(6);
                phraseEmptyLabelTitlecell.setPaddingBottom(10);
                paymentDetails_table.addCell(phraseEmptyLabelTitlecell);


                Phrase phrasQuantityLabelTitle = new Phrase("  " + String.valueOf(twoDecimalConverterWithComma.format(amount_paid_double)), valueFont_10);

                PdfPCell phraseQuantityLabelTitlecell = new PdfPCell(phrasQuantityLabelTitle);
                phraseQuantityLabelTitlecell.setBorder(Rectangle.NO_BORDER);
                phraseQuantityLabelTitlecell.setHorizontalAlignment(Element.ALIGN_CENTER);
                phraseQuantityLabelTitlecell.setVerticalAlignment(Element.ALIGN_CENTER);
                phraseQuantityLabelTitlecell.setBorderWidthRight(01);
                phraseQuantityLabelTitlecell.setPaddingTop(5);
                phraseQuantityLabelTitlecell.setPaddingLeft(6);
                phraseQuantityLabelTitlecell.setPaddingBottom(10);
                paymentDetails_table.addCell(phraseQuantityLabelTitlecell);


                try {
                    PdfPCell itemDetails_table_Cell = new PdfPCell(paymentDetails_table);
                    itemDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                    itemDetails_table_Cell.setBorderWidthBottom(1);

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
                    itemDetails_table_Cell.setBorderWidthBottom(1);
                    itemDetails_table_Cell.setBackgroundColor(WHITE);
                    wholePDFContentWithOut_Outline_table.addCell(itemDetails_table_Cell);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            catch (Exception e) {
                e.printStackTrace();
            }


            try {

                PdfPTable openingAmountDetails_table = new PdfPTable(new float[]{40, 25, 35});

                try {

                    try {


                        Phrase phrasetotalDetailsTitle = new Phrase("Opening Balance ( Rs ) ", valueFont_10);

                        PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                        phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                        phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                        phrasetotalDetailsTitlecell.setPaddingBottom(4);
                        phrasetotalDetailsTitlecell.setBorderWidthRight(1);
                        phrasetotalDetailsTitlecell.setPaddingLeft(6);
                        openingAmountDetails_table.addCell(phrasetotalDetailsTitlecell);

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
                        openingAmountDetails_table.addCell(phrasetotalDetailsTitlecell);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {

                        Phrase phrasetotalDetailsTitle = new Phrase(String.valueOf(twoDecimalConverterWithComma.format(oldCreditAmountOfUser)), valueFont_10);

                        PdfPCell phraseTotalDetailscell = new PdfPCell(phrasetotalDetailsTitle);
                        phraseTotalDetailscell.setBorder(Rectangle.NO_BORDER);
                        phraseTotalDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phraseTotalDetailscell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phraseTotalDetailscell.setPaddingBottom(10);
                        phraseTotalDetailscell.setPaddingLeft(6);

                        openingAmountDetails_table.addCell(phraseTotalDetailscell);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
                    PdfPCell totalAmountDetails_table_Cell = new PdfPCell(openingAmountDetails_table);
                    totalAmountDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                    totalAmountDetails_table_Cell.setBorderWidthBottom(1);
                    totalAmountDetails_table_Cell.setBackgroundColor(LIGHT_GRAY);
                    wholePDFContentWithOut_Outline_table.addCell(totalAmountDetails_table_Cell);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {

                PdfPTable paidAmountDetails_table = new PdfPTable(new float[]{40, 25, 35});

                try {

                    try {


                        Phrase phrasetotalDetailsTitle = new Phrase("Amount Paid ( Rs ) ", valueFont_10);

                        PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                        phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                        phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                        phrasetotalDetailsTitlecell.setPaddingBottom(4);
                        phrasetotalDetailsTitlecell.setBorderWidthRight(1);
                        phrasetotalDetailsTitlecell.setPaddingLeft(6);
                        paidAmountDetails_table.addCell(phrasetotalDetailsTitlecell);

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
                        paidAmountDetails_table.addCell(phrasetotalDetailsTitlecell);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {

                        Phrase phrasetotalDetailsTitle = new Phrase(String.valueOf(twoDecimalConverterWithComma.format(amount_paid_double)), valueFont_10);

                        PdfPCell phraseTotalDetailscell = new PdfPCell(phrasetotalDetailsTitle);
                        phraseTotalDetailscell.setBorder(Rectangle.NO_BORDER);
                        phraseTotalDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phraseTotalDetailscell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phraseTotalDetailscell.setPaddingBottom(10);
                        phraseTotalDetailscell.setPaddingLeft(6);

                        paidAmountDetails_table.addCell(phraseTotalDetailscell);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
                    PdfPCell totalAmountDetails_table_Cell = new PdfPCell(paidAmountDetails_table);
                    totalAmountDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                    totalAmountDetails_table_Cell.setBorderWidthBottom(1);
                    totalAmountDetails_table_Cell.setBackgroundColor(WHITE);
                    wholePDFContentWithOut_Outline_table.addCell(totalAmountDetails_table_Cell);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {

                PdfPTable closingBalanceDetails_table = new PdfPTable(new float[]{40, 25, 35});

                try {

                    try {


                        Phrase phrasetotalDetailsTitle = new Phrase("Closing Balance ( Rs ) ", valueFont_10);

                        PdfPCell phrasetotalDetailsTitlecell = new PdfPCell(phrasetotalDetailsTitle);
                        phrasetotalDetailsTitlecell.setBorder(Rectangle.NO_BORDER);
                        phrasetotalDetailsTitlecell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        phrasetotalDetailsTitlecell.setVerticalAlignment(Element.ALIGN_LEFT);
                        phrasetotalDetailsTitlecell.setPaddingBottom(4);
                        phrasetotalDetailsTitlecell.setBorderWidthRight(1);
                        phrasetotalDetailsTitlecell.setPaddingLeft(6);
                        closingBalanceDetails_table.addCell(phrasetotalDetailsTitlecell);

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
                        closingBalanceDetails_table.addCell(phrasetotalDetailsTitlecell);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {

                        Phrase phrasetotalDetailsTitle = new Phrase(String.valueOf(twoDecimalConverterWithComma.format(newCreditAmountOfUser)), valueFont_10);

                        PdfPCell phraseTotalDetailscell = new PdfPCell(phrasetotalDetailsTitle);
                        phraseTotalDetailscell.setBorder(Rectangle.NO_BORDER);
                        phraseTotalDetailscell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        phraseTotalDetailscell.setVerticalAlignment(Element.ALIGN_CENTER);
                        phraseTotalDetailscell.setPaddingBottom(10);
                        phraseTotalDetailscell.setPaddingLeft(6);

                        closingBalanceDetails_table.addCell(phraseTotalDetailscell);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
                    PdfPCell totalAmountDetails_table_Cell = new PdfPCell(closingBalanceDetails_table);
                    totalAmountDetails_table_Cell.setBorder(Rectangle.NO_BORDER);
                    totalAmountDetails_table_Cell.setBackgroundColor(LIGHT_GRAY);
                    wholePDFContentWithOut_Outline_table.addCell(totalAmountDetails_table_Cell);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
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


            layoutDocument.close();




            try {
                buyerName_autoComplete_textview.setText("");
                //buyerName_textview.setText("");
                buyerName_autoComplete_textview.clearFocus();

                buyerName_autoComplete_textview.dismissDropDown();
                hideKeyboard(buyerName_autoComplete_textview);


                buyerMobileNo_textview.setText("");
                buyersAdddress_textview.setText("");
                totalOutStandingAmount_textview.setText("0.00");
                addDatatoPaymentTypeSpinner();
               // amountPaid_textview.setText("0");
                amountPaid_editText.setText("0");
                notes_edittext.setText("");

                isRetailerDetailsServiceCalled = false;
                isSearchButtonClicked = false;
                isRetailerCreditDetailsCalled = false;
                isRetailerCreditDetailsIsNotCreated = false;
                isRetailerCreditTransactionHistoryCalled = false;
                isPaymentDetailsCalled = false;
                isPaymentNotSelectedManually = false;
                isPDF_FIle = false;
                oldCreditAmountOfUser = 0;
                newCreditAmountOfUser = 0;
                amount_paid_double = 0;
                amount_paid_String = "0";
                updatedTime = "";
                retailerAddress = "";
                retailerMobileNo = "";
                retailerKey = "";
                retailerName = "";
                selectedPaymentMode = "";

            }
            catch (Exception e){
                e.printStackTrace();
            }


        }
        catch (Exception e){
            e.printStackTrace();
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

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();




    }
       /* try {
            if (show_recordPayment_To_ClearOutstanding_Dialog.isShowing()) {
                if(addAmount_toClearOutstanding_Dialogs_Dialog.isShowing()){
                    addAmount_toClearOutstanding_Dialogs_Dialog.cancel();
                    addAmount_toClearOutstanding_Dialogs_Dialog.dismiss();
                }
                else{
                    show_recordPayment_To_ClearOutstanding_Dialog.cancel();
                    show_recordPayment_To_ClearOutstanding_Dialog.dismiss();
                }

            } else {
                super.onBackPressed();
            }
        }
        catch (Exception e){
            super.onBackPressed();
            e.printStackTrace();
        }
    }

        */
}