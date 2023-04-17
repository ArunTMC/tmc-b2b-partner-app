package com.tmc.tmcb2bpartnerapp.second_version.activity;

import androidx.cardview.widget.CardView;
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.GoatEarTagItemDetailsList;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BOrderItemDetails_BulkUpdate;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderItemDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderItemDetails_BulkUpdateInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_Static_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_UpdatedGoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.second_version.adapter.Adapter_Mark_item_as_delivered_recyclerView;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;

import java.util.ArrayList;
import java.util.Objects;

public class MarkDeliveredGoats_in_an_Order extends BaseActivity {

    RecyclerView mRecylerView;
    LinearLayout loadingpanelmask ,loadingPanel ,goatInfo_IconLayout , back_IconLayout ,greyDisplaymask ;
    static LinearLayout search_IconLayout;
    static LinearLayout close_IconLayout;
    EditText baarcodeNo_search_editText;
    TextView searchbarcodeNo_textView , listwiseInstruction_textview;
    public CheckBox checkBox_selectAllitems;
    public static Dialog show_total_value_info_Dialog = null;
    TextView total_itemCount ,noItem_inthis_order_instructionTextview;
    Button markselectedOrders_as_delivered , showDelivered_item_list_button;
    RelativeLayout checkBox_selectAllitems_parentLayout ;
    View checkBox_selectAllitems_View;



    Modal_B2BOrderDetails modal_b2BOrderDetails = new Modal_B2BOrderDetails();
    String orderId = "",deliveryCenterKey="",deliveryCenterName ="",usermobileno_string ="",supervisorname ="";


    Adapter_Mark_item_as_delivered_recyclerView mAdapter;

    public ArrayList<String> orderItemDetailsList_String = new ArrayList<>();
    public ArrayList<Modal_B2BOrderItemDetails> orderItemDetailsArrayList = new ArrayList<>();



    public ArrayList<String> orderwise_notdeliveredItemDetailsList_String = new ArrayList<>();
    public ArrayList<Modal_B2BOrderItemDetails> orderwise_notdeliveredItemDetailsArrayList = new ArrayList<>();
    public ArrayList<Modal_B2BOrderItemDetails> sorted_orderwise_notdeliveredItemDetailsArrayList = new ArrayList<>();

    int notDeliveredItemCount = 0;
   public int currentlymarkedItemCount = 0 ,currentlymarkedItemCountForCheckbox = 0;
    int alreadyDeliveredItemCount = 0 ;


   public boolean isSelectAllCheckBoxListenerEnabled = false ,isSearchButtonClicked = false;



    B2BOrderDetailsInterface callback_b2BOrderDetailsInterface ;
    boolean isOrderDetailsServiceCalled = false;
    B2BOrderItemDetailsInterface callback_b2bOrderItemDetails;
    boolean isB2BOrderItemDetailsTableServiceCalled = false;


    boolean isOrderItemDetailsBulkUpdateServiceCalled = false;
    B2BOrderItemDetails_BulkUpdateInterface b2BOrderItemDetails_bulkUpdateInterface;







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









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_delivered_goats_in_an__order);
        greyDisplaymask = findViewById(R.id.greyDisplaymask);
        greyDisplaymask.setVisibility(View.VISIBLE);
        mRecylerView = findViewById(R.id.recycler_view);
        loadingpanelmask  = findViewById(R.id.loadingpanelmask);
        loadingPanel  = findViewById(R.id.loadingPanel);
        total_itemCount  = findViewById(R.id.total_itemCount);
        markselectedOrders_as_delivered = findViewById(R.id.markselectedOrders_as_deliveredbutton);
        noItem_inthis_order_instructionTextview = findViewById(R.id.noItem_inthis_order_instructionTextview);
        search_IconLayout = findViewById(R.id.search_IconLayout);
        close_IconLayout = findViewById(R.id.close_IconLayout);
        checkBox_selectAllitems = findViewById(R.id.checkBox_selectAllitems);
        goatInfo_IconLayout = findViewById(R.id.goatInfo_IconLayout);
        checkBox_selectAllitems_parentLayout =  findViewById(R.id.checkBox_selectAllitems_parentLayout);
        showDelivered_item_list_button =  findViewById(R.id.showDelivered_item_list_button);
        checkBox_selectAllitems_View =  findViewById(R.id.checkBox_selectAllitems_View);
        baarcodeNo_search_editText =  findViewById(R.id.baarcodeNo_search_editText);
        searchbarcodeNo_textView  =  findViewById(R.id.searchbarcodeNo_textView);
        listwiseInstruction_textview =  findViewById(R.id.listwiseInstruction_textview);
        back_IconLayout=  findViewById(R.id.back_IconLayout);
        SharedPreferences sh1 =  getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);
        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");


        SharedPreferences sh = getSharedPreferences("LoginData",MODE_PRIVATE);
        usermobileno_string = sh.getString("UserMobileNumber","");
        supervisorname = sh.getString("UserName","");
        modal_b2BOrderDetails = new Modal_B2BOrderDetails();
        modal_b2BOrderDetails = (Modal_B2BOrderDetails) getIntent().getSerializableExtra("orderdetailsPojoClass");
        orderId = modal_b2BOrderDetails.getOrderid().toString();
        show_total_value_info_Dialog = new Dialog(MarkDeliveredGoats_in_an_Order.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_TranslucentDecor);





        isSelectAllCheckBoxListenerEnabled =false;
        orderItemDetailsArrayList.clear();
        orderItemDetailsList_String.clear();
        orderwise_notdeliveredItemDetailsArrayList.clear();
        orderwise_notdeliveredItemDetailsList_String.clear();
        show_goatEarTagItemDetails_Dialog = new Dialog(MarkDeliveredGoats_in_an_Order.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_TranslucentDecor);



        Call_and_IntializeOrderItemDetails(Constants.CallGETListMethod);


        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });





        showDelivered_item_list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MarkDeliveredGoats_in_an_Order.this, GoatEarTagItemDetailsList.class);
                i.putExtra("TaskToDo", "PlacedOrderItemDetails");
                i.putExtra("batchno", "batchno");
                i.putExtra("orderid", orderId);
                i.putExtra("CalledFrom", getString(R.string.markdeliveredOrders_detailsScreen));


                // i.putExtra("CalledFrom", getString(R.string.placedOrder_Details_Screen));
                startActivity(i);
            }
        });

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
                setAdpterForRecyclerView(orderwise_notdeliveredItemDetailsArrayList,false);

            }
        });



        baarcodeNo_search_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sorted_orderwise_notdeliveredItemDetailsArrayList.clear();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                sorted_orderwise_notdeliveredItemDetailsArrayList.clear();
                isSearchButtonClicked =true;
                String barcodeEntered = (editable.toString().toUpperCase() );

                if(!barcodeEntered.equals("")) {

                    for (int i = 0; i < orderwise_notdeliveredItemDetailsArrayList.size(); i++) {
                        try{
                            final Modal_B2BOrderItemDetails modal_b2BRetailerDetails = orderwise_notdeliveredItemDetailsArrayList.get(i);
                            String barcodeno = modal_b2BRetailerDetails.getBarcodeno();
                            if (barcodeno.contains(barcodeEntered)) {
                                sorted_orderwise_notdeliveredItemDetailsArrayList.add(modal_b2BRetailerDetails);
                            }
                            if (i == (orderwise_notdeliveredItemDetailsArrayList.size() - 1)) {
                                if (sorted_orderwise_notdeliveredItemDetailsArrayList.size() > 0) {
                                    setAdpterForRecyclerView(sorted_orderwise_notdeliveredItemDetailsArrayList,false);
                                }
                                else{
                                    noItem_inthis_order_instructionTextview.setText("This Barcode is not in the Not delivered items list");
                                    noItem_inthis_order_instructionTextview.setVisibility(View.VISIBLE);
                                    mRecylerView.setVisibility(View.GONE);
                                    checkBox_selectAllitems_parentLayout.setVisibility(View.GONE);
                                    markselectedOrders_as_delivered.setVisibility(View.GONE);
                                    showDelivered_item_list_button.setVisibility(View.GONE);
                                }
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    setAdpterForRecyclerView(orderwise_notdeliveredItemDetailsArrayList,false);


                }
            }
        });











        markselectedOrders_as_delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentlymarkedItemCountForCheckbox>0) {

                    showProgressBar(true);
                    call_and_init_OrderItemDetails_BulkUpdate(Constants.CallUPDATEMethod);
                }
                else{
                    Toast.makeText(MarkDeliveredGoats_in_an_Order.this, "First yo u need to mark atleast one Item", Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkBox_selectAllitems.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean markItem) {
                if(isSelectAllCheckBoxListenerEnabled) {
                    mark_or_unmark_AlltheItems_in_the_List(markItem);
                 //   Toast.makeText(MarkDeliveredGoats_in_an_Order.this, "if in check box called", Toast.LENGTH_SHORT).show();
                }
                else{
                    isSelectAllCheckBoxListenerEnabled = true;
                  //  Toast.makeText(MarkDeliveredGoats_in_an_Order.this, "else in check box called", Toast.LENGTH_SHORT).show();

                }

            }
        });
        checkBox_selectAllitems_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSelectAllCheckBoxListenerEnabled = false;
                if(checkBox_selectAllitems.isChecked()){
                    currentlymarkedItemCountForCheckbox = 0;
                }
                else{
                    currentlymarkedItemCountForCheckbox = orderwise_notdeliveredItemDetailsArrayList.size();

                }

                mark_or_unmark_AlltheItems_in_the_List(!checkBox_selectAllitems.isChecked());
                checkBox_selectAllitems.setChecked(!checkBox_selectAllitems.isChecked());


            }
        });

        goatInfo_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGoatinfoDialogLayout();

            }
        });





    }

    private void openGoatinfoDialogLayout() {


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
             try{
                 try{
                     show_total_value_info_Dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                     try{
                         show_total_value_info_Dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

                     }
                     catch (Exception e){
                         e.printStackTrace();
                     }


                     try{
                         show_total_value_info_Dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                         show_total_value_info_Dialog.setContentView(R.layout.total_value_details_of_goat_dialog);

                     }
                     catch (Exception e){
                         e.printStackTrace();
                     }





                     CardView thirdCardView = show_total_value_info_Dialog.findViewById(R.id.thirdCardView);
                     CardView secondCardView = show_total_value_info_Dialog.findViewById(R.id.secondCardView);
                     CardView itemCountDetails2_CardView = show_total_value_info_Dialog.findViewById(R.id.itemCountDetails2_CardView);



                     TextView markItem_totalGoats_textView = show_total_value_info_Dialog.findViewById(R.id.markItemScreen_totalGoats_textView);
                     TextView alreadydelivereditem_textView = show_total_value_info_Dialog.findViewById(R.id.alreadydelivereditem_textView);
                     TextView notdeliveredyet_textView = show_total_value_info_Dialog.findViewById(R.id.notdeliveredyet_textView);
                     TextView markeditems_textView = show_total_value_info_Dialog.findViewById(R.id.markeditems_textView);


                     Button showDelivered_item_list_infoDialog_button = show_total_value_info_Dialog.findViewById(R.id.showDelivered_item_list_infoDialog);


                     LinearLayout close_IconLayout_goatsInfo_dialog =  show_total_value_info_Dialog.findViewById(R.id.close_IconLayout_goatsInfo_dialog);



                     showDelivered_item_list_infoDialog_button.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             Intent i = new Intent(MarkDeliveredGoats_in_an_Order.this, GoatEarTagItemDetailsList.class);
                             i.putExtra("TaskToDo", "PlacedOrderItemDetails");
                             i.putExtra("batchno", "batchno");
                             i.putExtra("orderid", orderId);
                             i.putExtra("CalledFrom", getString(R.string.markdeliveredOrders_detailsScreen));


                             // i.putExtra("CalledFrom", getString(R.string.placedOrder_Details_Screen));
                             startActivity(i);
                         }
                     });





                     close_IconLayout_goatsInfo_dialog.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             try{
                                 show_total_value_info_Dialog.dismiss();
                                 show_total_value_info_Dialog.cancel();
                             }
                             catch (Exception e){
                                 e.printStackTrace();
                             }
                         }
                     });




                     alreadyDeliveredItemCount = 0; notDeliveredItemCount = 0;currentlymarkedItemCount =0;
                     for(int iterator = 0 ; iterator < orderItemDetailsArrayList.size(); iterator++){
                         Modal_B2BOrderItemDetails modal_b2BOrderItemDetails  = orderItemDetailsArrayList.get(iterator);

                         if(modal_b2BOrderItemDetails.isItemAlreadyMarkedDelivered()){
                             alreadyDeliveredItemCount = alreadyDeliveredItemCount +1;
                         }

                         else if(modal_b2BOrderItemDetails.isItemNowMarkedforDelivered()){
                             currentlymarkedItemCount = currentlymarkedItemCount +1;
                         }
                         else{
                             notDeliveredItemCount = notDeliveredItemCount +1;
                         }




                     }

                     if(alreadyDeliveredItemCount>0){
                         if(orderwise_notdeliveredItemDetailsArrayList.size() > 0) {
                             showDelivered_item_list_infoDialog_button.setVisibility(View.VISIBLE);
                         }
                         else{
                             showDelivered_item_list_infoDialog_button.setVisibility(View.GONE);
                         }
                     }
                     else{
                         showDelivered_item_list_infoDialog_button.setVisibility(View.GONE);
                     }
                     markItem_totalGoats_textView.setText(String.valueOf(orderItemDetailsArrayList.size()));
                     alreadydelivereditem_textView.setText(String.valueOf(alreadyDeliveredItemCount));
                     notdeliveredyet_textView.setText(String.valueOf( notDeliveredItemCount));
                     markeditems_textView.setText(String.valueOf( currentlymarkedItemCount));



                     itemCountDetails2_CardView.setVisibility(View.GONE);

                     secondCardView.setVisibility(View.GONE);

                     thirdCardView.setVisibility(View.VISIBLE);



                     show_total_value_info_Dialog.setCanceledOnTouchOutside(false);
                     show_total_value_info_Dialog.show();




                 }
                     catch (Exception e){
                         e.printStackTrace();
                     }

                }
             catch (Exception e){
                 e.printStackTrace();
             }
            }

        });




    }

    private void mark_or_unmark_AlltheItems_in_the_List(boolean markItem) {
        showProgressBar(true);

        for(int iterator = 0 ; iterator < orderItemDetailsArrayList.size(); iterator++){
            Modal_B2BOrderItemDetails modal_b2BOrderItemDetails  = orderItemDetailsArrayList.get(iterator);

            if(!modal_b2BOrderItemDetails.isItemAlreadyMarkedDelivered()){
                modal_b2BOrderItemDetails.setItemNowMarkedforDelivered(markItem);
            }





        }

        if(orderwise_notdeliveredItemDetailsArrayList.size()>0) {
            for (int iterator = 0; iterator < orderwise_notdeliveredItemDetailsArrayList.size(); iterator++) {
                Modal_B2BOrderItemDetails modal_b2BOrderItemDetails = orderwise_notdeliveredItemDetailsArrayList.get(iterator);

                if (!modal_b2BOrderItemDetails.isItemAlreadyMarkedDelivered()) {
                    modal_b2BOrderItemDetails.setItemNowMarkedforDelivered(markItem);
                }


                if (iterator == (orderwise_notdeliveredItemDetailsArrayList.size() - 1)) {
                    setAdpterForRecyclerView(orderwise_notdeliveredItemDetailsArrayList, false);
                }


            }
        }
        else{
            showProgressBar(false);
        }

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
                showProgressBar(false);



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
        Modal_B2BOrderDetails.setStatus_Static(String.valueOf(Constants.orderDetailsStatus_Delivered));
        Modal_B2BOrderDetails.setOrderid_Static(String.valueOf(orderId));
        Modal_B2BOrderDetails.setOrderdeliveredtime_static(String.valueOf(DateParser.getDate_and_time_newFormat()));
        String getApiToCall = API_Manager.updateOrderDetails ;

        B2BOrderDetails asyncTask = new B2BOrderDetails(callback_b2BOrderDetailsInterface,  getApiToCall, callMethod);
        asyncTask.execute();







    }


    private void Call_and_IntializeOrderItemDetails(String callMethod) {

        try {
            showProgressBar(true);
            if (isB2BOrderItemDetailsTableServiceCalled) {
                // showProgressBar(false);
                return;
            }
            isB2BOrderItemDetailsTableServiceCalled = true;
            callback_b2bOrderItemDetails = new B2BOrderItemDetailsInterface() {


                @Override
                public void notifySuccessForGettingListItem(ArrayList<Modal_B2BOrderItemDetails> arrayList) {
                    for (int iterator = 0; iterator < arrayList.size(); iterator++) {
                        Modal_B2BOrderItemDetails modal_b2BOrderItemDetails = arrayList.get(iterator);
                        Modal_B2BOrderItemDetails modal_goatEarTagDetails = new Modal_B2BOrderItemDetails();
                        modal_goatEarTagDetails.barcodeno = modal_b2BOrderItemDetails.getBarcodeno();
                        modal_goatEarTagDetails.batchno = modal_b2BOrderItemDetails.getBatchno();
                        modal_goatEarTagDetails.status = modal_b2BOrderItemDetails.getStatus();
                        modal_goatEarTagDetails.itemaddeddate = modal_b2BOrderItemDetails.getItemaddeddate();
                        modal_goatEarTagDetails.gender = modal_b2BOrderItemDetails.getGender();
                        modal_goatEarTagDetails.breedtype = modal_b2BOrderItemDetails.getBreedtype();
                        modal_goatEarTagDetails.status = modal_b2BOrderItemDetails.getStatus();
                        modal_goatEarTagDetails.b2bctgykey = modal_b2BOrderItemDetails.getB2bctgykey();
                        modal_goatEarTagDetails.b2bsubctgykey = modal_b2BOrderItemDetails.getB2bsubctgykey();
                        modal_goatEarTagDetails.approxliveweight = modal_b2BOrderItemDetails.getApproxliveweight();
                        modal_goatEarTagDetails.meatyieldweight = modal_b2BOrderItemDetails.getMeatyieldweight();
                        modal_goatEarTagDetails.partsweight = modal_b2BOrderItemDetails.getPartsweight();
                        modal_goatEarTagDetails.totalPrice_ofItem = modal_b2BOrderItemDetails.getTotalPrice_ofItem();
                        modal_goatEarTagDetails.discount = modal_b2BOrderItemDetails.getDiscount();
                        modal_goatEarTagDetails.itemPrice = modal_b2BOrderItemDetails.getItemPrice();
                        modal_goatEarTagDetails.totalItemWeight = modal_b2BOrderItemDetails.getTotalItemWeight();
                        modal_goatEarTagDetails.gradename = modal_b2BOrderItemDetails.getGradename();

                        double meatYield_double =0 , parts_double = 0 , totalWeight_double  = 0;
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



                    }
                    isB2BOrderItemDetailsTableServiceCalled = false;

                }

                @Override
                public void notifySuccess(String result) {
                    isB2BOrderItemDetailsTableServiceCalled = false;
                }

                @Override
                public void notifyVolleyError(VolleyError error) {
                    isB2BOrderItemDetailsTableServiceCalled = false;
                    Log.i("INIT", "FetchDataFromOrderItemDetails:  " + String.valueOf(error));
                }

                @Override
                public void notifyProcessingError(Exception error) {

                    Log.i("INIT", "FetchDataFromOrderItemDetails:  " + String.valueOf(error));
                    isB2BOrderItemDetailsTableServiceCalled = false;
                }
            };

            if (callMethod.equals(Constants.CallGETListMethod)) {
                //String getApiToCall = API_Manager.getOrderDetailsForBatchno+"?batchno="+batchno ;
                String getApiToCall = API_Manager.getOrderItemDetailsForOrderid + orderId;

                B2BOrderItemDetails asyncTask = new B2BOrderItemDetails(callback_b2bOrderItemDetails, getApiToCall, callMethod);
                asyncTask.execute();

            }

        }
        catch (Exception e){
            e.printStackTrace();
        }



    }







    private void call_and_init_OrderItemDetails_BulkUpdate(String callUPDATEMethod) {
        ArrayList<String> orderItemDetailsList_String_temp = new ArrayList<>();
        try {

            if (isOrderItemDetailsBulkUpdateServiceCalled) {

                return;
            }
            isOrderItemDetailsBulkUpdateServiceCalled = true;
            b2BOrderItemDetails_bulkUpdateInterface = new B2BOrderItemDetails_BulkUpdateInterface() {


                @Override
                public void notifySuccess(String result) {


                    try{
                        for(int i = 0 ; i< orderItemDetailsList_String_temp.size(); i++){
                            for(int j =0  ; j < orderItemDetailsArrayList.size(); j++){
                                if(orderItemDetailsList_String_temp.get(i).equals(orderItemDetailsArrayList.get(j).getBarcodeno())){
                                    orderItemDetailsArrayList.get(j).setItemNowMarkedforDelivered(false);

                                    orderItemDetailsArrayList.get(j).setItemAlreadyMarkedDelivered(true);

                                }
                            }

                            for(int j =0  ; j < orderwise_notdeliveredItemDetailsArrayList.size(); j++){
                                if(orderItemDetailsList_String_temp.get(i).equals(orderwise_notdeliveredItemDetailsArrayList.get(j).getBarcodeno())){
                                    orderwise_notdeliveredItemDetailsArrayList.remove(j);
                                     notDeliveredItemCount = notDeliveredItemCount-1;
                                      currentlymarkedItemCount = currentlymarkedItemCount -1;
                                      currentlymarkedItemCountForCheckbox = currentlymarkedItemCountForCheckbox -1;
                                     alreadyDeliveredItemCount = alreadyDeliveredItemCount +1 ;



                                }
                            }


                            if(i == (orderItemDetailsList_String_temp.size()-1)){

                                setAdpterForRecyclerView(orderwise_notdeliveredItemDetailsArrayList,false);
                                if(notDeliveredItemCount ==0){
                                    call_and_init_B2BOrderDetailsService(Constants.CallUPDATEMethod);
                                }
                                else{
                                    showProgressBar(false);
                                }
                                isOrderItemDetailsBulkUpdateServiceCalled = false;

                                checkBox_selectAllitems.setChecked(false);

                            }
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
                    isOrderItemDetailsBulkUpdateServiceCalled = false;
                }

                @Override
                public void notifyProcessingError(Exception error) {
                    Toast.makeText(getParent(), "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                    showProgressBar(false);
                    isOrderItemDetailsBulkUpdateServiceCalled = false;


                }

            };





             for(int i =0 ; i< orderwise_notdeliveredItemDetailsArrayList.size(); i++){
                 if(orderwise_notdeliveredItemDetailsArrayList.get(i).isItemNowMarkedforDelivered()){
                     orderItemDetailsList_String_temp.add(String.valueOf(orderwise_notdeliveredItemDetailsArrayList.get(i).getBarcodeno()));
                 }
             }



            Modal_B2BOrderItemDetails modal_b2BOrderItemDetails = new Modal_B2BOrderItemDetails();
            modal_b2BOrderItemDetails.setDeliverycentrekey_static(String.valueOf(deliveryCenterKey));
            modal_b2BOrderItemDetails.setStatus(String.valueOf(Constants.orderItemDetailsStatus_Delivered));
            modal_b2BOrderItemDetails.setOrderid_static(String.valueOf(orderId));
            modal_b2BOrderItemDetails.setEarTagDetailsArrayList_String(orderItemDetailsList_String_temp);
            // modal_b2BOrderItemDetails.setEarTagDetails_weightStringHashMap(earTagDetails_JSONFinalSalesHashMap);
            String orderplaceddate = DateParser.getDate_and_time_newFormat();




            try {

                B2BOrderItemDetails_BulkUpdate asyncTask = new B2BOrderItemDetails_BulkUpdate(b2BOrderItemDetails_bulkUpdateInterface,  Constants.CallUPDATEMethod, orderId , Constants.orderItemDetailsStatus_Delivered , orderItemDetailsList_String_temp);
                asyncTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }



    }





    private void ProcessDataAndAddItIntoArray(Modal_B2BOrderItemDetails modal_b2BOrderItemDetails, int iterator, int size) {
        if (!orderItemDetailsList_String.contains(String.valueOf(modal_b2BOrderItemDetails.getBarcodeno()))) {
            orderItemDetailsList_String.add(String.valueOf(modal_b2BOrderItemDetails.getBarcodeno()));
            orderItemDetailsArrayList.add(modal_b2BOrderItemDetails);
        }

        if(String.valueOf(modal_b2BOrderItemDetails.getStatus()).equals(Constants.orderItemDetailsStatus_Delivered)){
            modal_b2BOrderItemDetails.setItemAlreadyMarkedDelivered(true);
            alreadyDeliveredItemCount = alreadyDeliveredItemCount +1;
        }
        else{
            modal_b2BOrderItemDetails.setItemAlreadyMarkedDelivered(false);
            notDeliveredItemCount = notDeliveredItemCount + 1;
        }
        if(String.valueOf(modal_b2BOrderItemDetails.getStatus()).equals(Constants.orderItemDetailsStatus_NOTDelivered)) {

            if (!orderwise_notdeliveredItemDetailsList_String.contains(String.valueOf(modal_b2BOrderItemDetails.getBarcodeno()))) {
                orderwise_notdeliveredItemDetailsList_String.add(String.valueOf(modal_b2BOrderItemDetails.getBarcodeno()));
                orderwise_notdeliveredItemDetailsArrayList.add(modal_b2BOrderItemDetails);
            }
        }
        

            if((size-1 ) == iterator){
                if(orderwise_notdeliveredItemDetailsArrayList.size()>0) {
                    setAdpterForRecyclerView(orderwise_notdeliveredItemDetailsArrayList,true);
                }
                else{
                    Toast.makeText(this, "All the items in this order has been delivered", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(MarkDeliveredGoats_in_an_Order.this, GoatEarTagItemDetailsList.class);
                    i.putExtra("TaskToDo", "PlacedOrderItemDetails");
                    i.putExtra("batchno", "batchno");
                    i.putExtra("orderid", orderId);
                    i.putExtra("CalledFrom", getString(R.string.markdeliveredOrders_detailsScreen));
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i);
                    overridePendingTransition(0, 0);

                    finish();


                    // noItem_inthis_order_instructionTextview.setText("All the Item's in this Order is Delivered");
                    // listwiseInstruction_textview.setVisibility(View.GONE);
                    // noItem_inthis_order_instructionTextview.setVisibility(View.VISIBLE);
                    // mRecylerView.setVisibility(View.GONE);
                    // checkBox_selectAllitems_parentLayout.setVisibility(View.GONE);
                    // markselectedOrders_as_delivered.setVisibility(View.GONE);
                    // showDelivered_item_list_button.setVisibility(View.VISIBLE);
                }
                try{
                    total_itemCount.setText(String.valueOf(size));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

    }

    private void setAdpterForRecyclerView(ArrayList<Modal_B2BOrderItemDetails> orderItemDetailsArrayList,boolean isNeedtoSendDeliveredListScreen) {
        if(orderItemDetailsArrayList.size()>0) {




            checkBox_selectAllitems_parentLayout.setVisibility(View.VISIBLE);
            markselectedOrders_as_delivered.setVisibility(View.VISIBLE);
            noItem_inthis_order_instructionTextview.setVisibility(View.GONE);
            showDelivered_item_list_button.setVisibility(View.GONE);
            mRecylerView.setVisibility(View.VISIBLE);
            mAdapter = new Adapter_Mark_item_as_delivered_recyclerView(orderItemDetailsArrayList, MarkDeliveredGoats_in_an_Order.this, MarkDeliveredGoats_in_an_Order.this);
            GridLayoutManager manager = new GridLayoutManager(MarkDeliveredGoats_in_an_Order.this, 5, RecyclerView.VERTICAL, false);
            mRecylerView.setLayoutManager(manager);
            mRecylerView.setHasFixedSize(true);
            mRecylerView.setLayoutManager(manager);
            mRecylerView.setAdapter(mAdapter);
            showProgressBar(false);
        }
        else{

            if(isNeedtoSendDeliveredListScreen) {
                Toast.makeText(this, "All the items in this order has been delivered", Toast.LENGTH_SHORT).show();


                Intent i = new Intent(MarkDeliveredGoats_in_an_Order.this, GoatEarTagItemDetailsList.class);
                i.putExtra("TaskToDo", "PlacedOrderItemDetails");
                i.putExtra("batchno", "batchno");
                i.putExtra("orderid", orderId);
                i.putExtra("CalledFrom", getString(R.string.markdeliveredOrders_detailsScreen));
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
                overridePendingTransition(0, 0);

                finish();
            }
            else{
                showProgressBar(false);
             noItem_inthis_order_instructionTextview.setText("All the Item's in this Order is Delivered");
             listwiseInstruction_textview.setVisibility(View.GONE);
             noItem_inthis_order_instructionTextview.setVisibility(View.VISIBLE);
             mRecylerView.setVisibility(View.GONE);
             checkBox_selectAllitems_parentLayout.setVisibility(View.GONE);
             markselectedOrders_as_delivered.setVisibility(View.GONE);
             showDelivered_item_list_button.setVisibility(View.VISIBLE);
                showProgressBar(false);
            }



        }
    }


    private void showProgressBar(boolean show) {
        if(show){
            loadingPanel.setVisibility(View.VISIBLE);
            loadingpanelmask.setVisibility(View.VISIBLE);

        }
        else{
            greyDisplaymask.setVisibility(View.GONE);
            loadingPanel.setVisibility(View.GONE);
            loadingpanelmask.setVisibility(View.GONE);

        }
    }

    public void ShowGoatItemDetailsDialog(Modal_B2BOrderItemDetails modal_goatEarTagDetails) {


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
                    save_button.setVisibility(View.GONE);

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
                        Toast.makeText(MarkDeliveredGoats_in_an_Order.this, "Please Enter new Barcode Again", Toast.LENGTH_SHORT).show();
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

                                                         //   Call_and_ExecuteGoatEarTagDetails(Constants.CallUPDATEMethod);



                                                            try{
                                                                //Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod, Constants.CallUPDATEMethod);
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




/*
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


 */




                                                            try {
                                                                showProgressBar(true);
                                                                show_goatEarTagItemDetails_Dialog.cancel();
                                                                show_goatEarTagItemDetails_Dialog.dismiss();
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                            //  Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod, scannedBarcode, isCalledForPlaceNewOrder);


                                                        } else {
                                                            AlertDialogClass.showDialog(MarkDeliveredGoats_in_an_Order.this, R.string.CannotSaveWithOutBarcodeAlert);

                                                        }
                                                    } else {
                                                        AlertDialogClass.showDialog(MarkDeliveredGoats_in_an_Order.this, R.string.CannotSaveWhenTotalWeightisZeroAlert);

                                                    }
                                                } else {
                                                    AlertDialogClass.showDialog(MarkDeliveredGoats_in_an_Order.this, R.string.CannotSaveWhenTotalPriceisZeroAlert);

                                                }
                                            } else {
                                                AlertDialogClass.showDialog(MarkDeliveredGoats_in_an_Order.this, R.string.CannotSaveWhenTotalPriceWithOutDiscountisZeroAlert);

                                            }
                                        } else {
                                            AlertDialogClass.showDialog(MarkDeliveredGoats_in_an_Order.this, R.string.CannotSaveWhenMeatYieldisZeroAlert);

                                        }

                                    } else {
                                        AlertDialogClass.showDialog(MarkDeliveredGoats_in_an_Order.this, R.string.CannotSaveWhenPartsisZeroAlert);

                                    }
                                } else {
                                    AlertDialogClass.showDialog(MarkDeliveredGoats_in_an_Order.this, R.string.CannotSaveWhenApproxWeightisZeroAlert);

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
                                            AlertDialogClass.showDialog(MarkDeliveredGoats_in_an_Order.this, R.string.CannotSaveWhenPartsisZeroAlert);

                                        }
                                    } else {
                                        AlertDialogClass.showDialog(MarkDeliveredGoats_in_an_Order.this, R.string.CannotSaveWhenMeatYieldisZeroAlert);

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
                                            AlertDialogClass.showDialog(MarkDeliveredGoats_in_an_Order.this, R.string.CannotSaveWhenPartsisZeroAlert);

                                        }
                                    } else {
                                        AlertDialogClass.showDialog(MarkDeliveredGoats_in_an_Order.this, R.string.CannotSaveWhenMeatYieldisZeroAlert);

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
                                                AlertDialogClass.showDialog(MarkDeliveredGoats_in_an_Order.this, R.string.CannotSaveWhenTotalPriceisZeroAlert);

                                            }
                                        } else {
                                            AlertDialogClass.showDialog(MarkDeliveredGoats_in_an_Order.this, R.string.CannotSaveWhenTotalPriceWithOutDiscountisZeroAlert);

                                        }
                                    }
                                    else {
                                        AlertDialogClass.showDialog(MarkDeliveredGoats_in_an_Order.this, R.string.CannotSaveWhendiscountisgreaterthanpriceisZeroAlert);

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
                                                AlertDialogClass.showDialog(MarkDeliveredGoats_in_an_Order.this, R.string.CannotSaveWhenTotalPriceisZeroAlert);

                                            }
                                        } else {
                                            AlertDialogClass.showDialog(MarkDeliveredGoats_in_an_Order.this, R.string.CannotSaveWhenTotalPriceWithOutDiscountisZeroAlert);

                                        }
                                    } else {
                                        AlertDialogClass.showDialog(MarkDeliveredGoats_in_an_Order.this, R.string.CannotSaveWhendiscountisgreaterthanpriceisZeroAlert);

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


}