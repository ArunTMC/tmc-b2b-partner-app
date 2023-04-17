package com.tmc.tmcb2bpartnerapp.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.adapter.Adapter_PlacedOrdersList;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.ListItemSizeSetter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public class  DatewisePlacedOrdersList extends BaseActivity {

    boolean isB2BCartOrderTableServiceCalled = false , isSearchButtonClicked = false;
    B2BOrderDetailsInterface callback_b2bOrderDetails;
    String deliveryCenterKey ="", deliveryCenterName ="",startDate ="", EndDate ="";
    static ArrayList<Modal_B2BOrderDetails> orderDetailsArrayList = new ArrayList<>();
    ArrayList<Modal_B2BOrderDetails> sortedOrderDetailsArrayList = new ArrayList<>();
    Adapter_PlacedOrdersList adapter_placedOrdersList ;
    ListView orderdetailsListview;
    TextView ordersCount_textwidget , dateSelector_text ,fetchData_textView,ordersinstruction;
    ImageView search_button , closeSearchView_button;
    DatePickerDialog datepicker;
    LinearLayout dateSelectorLayout , loadingPanel ,loadingpanelmask , back_IconLayout,newOrdersSync_Layout;
    EditText mobile_search_barEditText;
    LinearLayout search_close_btn;

    String calledFrom ="";


   public static  DatewisePlacedOrdersList datewisePlacedOrdersList ;
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
                setContentView(R.layout.activity_datewise_placed_orders_list);
            } else {

                // Inflate the layout for this fragment
                setContentView(R.layout.pos_activity_datewise_placed_orders_list);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            setContentView(R.layout.activity_datewise_placed_orders_list);

        }

        try{
            orderdetailsListview = findViewById(R.id.orderdetailsListview);
            ordersCount_textwidget = findViewById(R.id.ordersCount_textwidget);
            dateSelector_text = findViewById(R.id.dateSelector_text);
            fetchData_textView = findViewById(R.id.fetchData_textView);
            search_button = findViewById(R.id.search_button);
            orderdetailsListview = findViewById(R.id.orderdetailsListview);
            dateSelectorLayout = findViewById(R.id.dateSelectorLayout);
            ordersinstruction =  findViewById(R.id.ordersinstruction);
            loadingpanelmask = findViewById(R.id.loadingpanelmask);
            loadingPanel = findViewById(R.id.loadingPanel);
            back_IconLayout = findViewById(R.id.back_IconLayout);
            mobile_search_barEditText = findViewById(R.id.search_barEdit);
            search_close_btn = findViewById(R.id.search_close_btn);
            newOrdersSync_Layout = findViewById(R.id.newOrdersSync_Layout);
            calledFrom = getIntent().getExtras().getString("CalledFrom");


        }
        catch (Exception e){
            e.printStackTrace();
        }
        datewisePlacedOrdersList = this;

        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);

        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");

        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        dateSelectorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker();
            }
        });

        fetchData_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!startDate.equals("")){
                    Initialize_and_ExecuteB2BOrderDetails(Constants.CallGETListMethod);
                }
                else{
                    Toast.makeText(DatewisePlacedOrdersList.this, "Please select date first", Toast.LENGTH_SHORT).show();
                }


            }
        });


        mobile_search_barEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sortedOrderDetailsArrayList.clear();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                sortedOrderDetailsArrayList.clear();
                isSearchButtonClicked =true;
                String mobileNo = (editable.toString());
                if(!mobileNo.equals("")) {
                    String orderstatus = "";

                    for (int i = 0; i < orderDetailsArrayList.size(); i++) {
                        try {
                            //Log.d(Constants.TAG, "displayorderDetailsinListview ordersList: " + ordersList.get(i));
                            final Modal_B2BOrderDetails modal_b2BOrderDetails = orderDetailsArrayList.get(i);
                            String mobilenumber = modal_b2BOrderDetails.getRetailermobileno();
                            //Log.d(Constants.TAG, "displayorderDetailsinListview orderStatus: " + orderStatus);
                            //Log.d(Constants.TAG, "displayorderDetailsinListview orderidfromOrderList: " + mobilenumber);
                            //Log.d(Constants.TAG, "displayorderDetailsinListview orderidfromOrderList: " + mobileNo);
                            if (mobilenumber.contains("+91" + mobileNo)) {

                                sortedOrderDetailsArrayList.add(modal_b2BOrderDetails);


                            }

                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                    try {
                          callAdapter(sortedOrderDetailsArrayList);

                    } catch (Exception E) {
                        E.printStackTrace();
                    }


                }
                else{
                    orderdetailsListview.setVisibility(View.GONE);
                    ordersinstruction.setVisibility(View.VISIBLE);
                    ordersinstruction.setText("No orders found for this Mobile number");

                }

            }
        });


        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int textlength = mobile_search_barEditText.getText().toString().length();
                isSearchButtonClicked =true;
                showKeyboard(mobile_search_barEditText);
                showSearchBarEditText();
            }
        });

        search_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(mobile_search_barEditText);
                closeSearchBarEditText();
                mobile_search_barEditText.setText("");
                isSearchButtonClicked =false;
                callAdapter(orderDetailsArrayList);
            }
        });


    }

    private void showEditTextToSearch() {
        dateSelectorLayout.setVisibility(View.GONE);
        mobile_search_barEditText.setVisibility(View.VISIBLE);
        search_close_btn.setVisibility(View.VISIBLE);
        newOrdersSync_Layout.setVisibility(View.GONE);
    }


    private void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void closeSearchBarEditText() {
        dateSelectorLayout.setVisibility(View.VISIBLE);
        newOrdersSync_Layout.setVisibility(View.VISIBLE);
        search_close_btn.setVisibility(View.GONE);
        mobile_search_barEditText.setVisibility(View.GONE);
    }

    private void showSearchBarEditText() {
        dateSelectorLayout.setVisibility(View.GONE);
        newOrdersSync_Layout.setVisibility(View.GONE);
        search_close_btn.setVisibility(View.VISIBLE);
        mobile_search_barEditText.setVisibility(View.VISIBLE);
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




    public void Initialize_and_ExecuteB2BOrderDetails(String callMethod) {


        showProgressBar(true);
        if (isB2BCartOrderTableServiceCalled) {
            // showProgressBar(false);
            return;
        }
        orderDetailsArrayList.clear();
        isB2BCartOrderTableServiceCalled = true;
        callback_b2bOrderDetails = new B2BOrderDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BOrderDetails> arrayList) {
                orderDetailsArrayList = arrayList;
                showProgressBar(false);
                isB2BCartOrderTableServiceCalled = false;
                callAdapter(orderDetailsArrayList);
            }

            @Override
            public void notifySuccess(String result) {
                showProgressBar(false);
                isB2BCartOrderTableServiceCalled = false;
            }
            @Override
            public void notifyVolleyError(VolleyError error) {
                Toast.makeText(DatewisePlacedOrdersList.this, "There is an volley error while Fetching Order Details", Toast.LENGTH_SHORT).show();
                showProgressBar(false);
                isB2BCartOrderTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {

                Toast.makeText(DatewisePlacedOrdersList.this, "There is an Process error while Fetching Order Details", Toast.LENGTH_SHORT).show();

                showProgressBar(false);
                isB2BCartOrderTableServiceCalled = false;


            }




        };

        if(callMethod.equals(Constants.CallGETListMethod)){
            //String getApiToCall = API_Manager.getOrderDetailsForBatchno+"?batchno="+batchno ;
            String getApiToCall = API_Manager.getOrderDetailsForDeliveryCentrekeySlotdateWithStatus+"?deliverycentrekey="+deliveryCenterKey+"&orderplaceddate="+startDate+"&status="+Constants.orderDetailsStatus_Delivered ;

            B2BOrderDetails asyncTask = new B2BOrderDetails(callback_b2bOrderDetails,  getApiToCall, callMethod);
            asyncTask.execute();

        }

    }

    public void callAdapter(ArrayList<Modal_B2BOrderDetails> orderDetailsArrayList) {
        if(orderDetailsArrayList.size()>0) {
             adapter_placedOrdersList = new Adapter_PlacedOrdersList(getApplicationContext(), orderDetailsArrayList, DatewisePlacedOrdersList.this, calledFrom);
            orderdetailsListview.setAdapter(adapter_placedOrdersList);
            ListItemSizeSetter.getListViewSize(orderdetailsListview);
            orderdetailsListview.setVisibility(View.VISIBLE);
            ordersinstruction.setVisibility(View.GONE);

        }
        else{
            orderdetailsListview.setVisibility(View.GONE);
            ordersinstruction.setVisibility(View.VISIBLE);
            ordersinstruction.setText("There is no order");
        }
        ordersCount_textwidget.setText(String.valueOf(orderDetailsArrayList.size()));
    }


    private void openDatePicker() {


        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        datepicker = new DatePickerDialog(DatewisePlacedOrdersList.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        try {


                            String month_in_String = getMonthString(monthOfYear);
                            String monthstring = String.valueOf(monthOfYear+1);
                            String datestring =  String.valueOf(dayOfMonth);

                            if(datestring.length()==1){
                                datestring="0"+datestring;
                            }
                            if(monthstring.length()==1){
                                monthstring="0"+monthstring;
                            }



                            Calendar myCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);

                            int dayOfWeek = myCalendar.get(Calendar.DAY_OF_WEEK);

                            String CurrentDay =   getDayString(dayOfWeek);
                            //Log.d(Constants.TAG, "dayOfWeek Response: " + dayOfWeek);

                            String CurrentDateString =(CurrentDay+", "+dayOfMonth + " " + month_in_String + " " + year);
                            startDate = DateParser. convertOldFormatDateintoNewFormat(CurrentDateString);

                            dateSelector_text.setText(CurrentDateString);
                            orderdetailsListview.setVisibility(View.GONE);
                            ordersinstruction.setVisibility(View.VISIBLE);
                            orderDetailsArrayList.clear();
                            ordersinstruction.setText("After Selecting the Date !! Click Fetch Data");
                            ordersCount_textwidget.setText(String.valueOf(orderDetailsArrayList.size()));

                        }
                        catch (Exception e ){
                            e.printStackTrace();
                        }
                    }
                }, year, month, day);
        datepicker.show();

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