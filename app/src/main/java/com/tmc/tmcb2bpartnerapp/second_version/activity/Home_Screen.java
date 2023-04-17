package com.tmc.tmcb2bpartnerapp.second_version.activity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.Add_Or_Edit_Retailer_Activity;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.TMCAlertDialogClass;

import java.util.ArrayList;

public class Home_Screen extends BaseActivity {


  //widget
    ConstraintLayout placeOrder_ConstraintLayout , itemsInCart_ConstraintLayout , batchList_ConstraintLayout ,
            manageRetailer_ConstraintLayout , settings_ConstraintLayout , markas_delivered_ConstraintLayout,
          viewStockBalance_ConstraintLayout,consolidatedSalesReport_ConstraintLayout,OutStanding_Report_ConstraintLayout,paymentReceivedReportsConstraintLayout,
          clearOutStandingCredit_Retailer_ConstraintLayout , paymentReportsByUserConstraintLayout,transactionDetail_byUser_ConstraintLayout;
    public static LinearLayout loadingpanelmask,loadingPanel,back_IconLayout;
    LinearLayout parent_layout;
    FrameLayout frameLayout;
    Fragment mfragment;
    TextView toolBarHeader_TextView,cartItem_count_textview;
    View iteminCart_dividerView;



    //String
    String value_forFragment ="" , deliveryCenterName ="" , deliveryCenterKey ="" ;
    public static String orderid = "";


    //boolean
    private boolean isTransactionPending;
    private boolean isOnRestartCalled = false;
    boolean isB2BCartOrderTableServiceCalled = false;
    boolean isB2BCartOrderDetailsEntryAlreadyCreated = false;


    //long
    private long pressedTime;



    //interface
    B2BCartOrderDetailsInterface callback_b2bCartOrderDetails =null ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__screen);
        placeOrder_ConstraintLayout = findViewById(R.id.placeOrder_ConstraintLayout);
        itemsInCart_ConstraintLayout = findViewById(R.id.ItemsInCart_ConstraintLayout);
        batchList_ConstraintLayout = findViewById(R.id.batchList_ConstraintLayout);
        manageRetailer_ConstraintLayout = findViewById(R.id.manageRetailer_ConstraintLayout);
        settings_ConstraintLayout = findViewById(R.id.settings_ConstraintLayout);
        markas_delivered_ConstraintLayout = findViewById(R.id.markas_delivered_ConstraintLayout);
        viewStockBalance_ConstraintLayout  = findViewById(R.id.viewStockBalance_ConstraintLayout);
        consolidatedSalesReport_ConstraintLayout = findViewById(R.id.consolidatedSalesReport_ConstraintLayout);
        clearOutStandingCredit_Retailer_ConstraintLayout = findViewById(R.id.clearOutStandingCredit_Retailer_ConstraintLayout);
        OutStanding_Report_ConstraintLayout = findViewById(R.id.OutStanding_Report_ConstraintLayout);
        paymentReceivedReportsConstraintLayout =  findViewById(R.id.paymentReceivedReportsConstraintLayout);
        paymentReportsByUserConstraintLayout =  findViewById(R.id.paymentReportsByUserConstraintLayout);
        transactionDetail_byUser_ConstraintLayout =   findViewById(R.id.transactionDetail_byUser_ConstraintLayout);



      //  parent_layout = findViewById(R.id.parent_layout);
        frameLayout = findViewById(R.id.frameLayout);
        toolBarHeader_TextView = findViewById(R.id.toolBarHeader_TextView);
        loadingpanelmask =  findViewById(R.id.loadingpanelmask);
        loadingPanel =  findViewById(R.id.loadingPanel);
        back_IconLayout =  findViewById(R.id.back_IconLayout);
        iteminCart_dividerView =  findViewById(R.id.iteminCart_dividerView);
        cartItem_count_textview = findViewById(R.id.cartItem_count);

        orderid  ="";

        SharedPreferences sh = getSharedPreferences("DeliveryCenterData",MODE_PRIVATE);
        deliveryCenterKey = sh.getString("DeliveryCenterKey","");
        deliveryCenterName = sh.getString("DeliveryCenterName","");
        toolBarHeader_TextView.setText(deliveryCenterName);



        loadingpanelmask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home_Screen.this, "Please wait until the loading gets finished", Toast.LENGTH_SHORT).show();
            }
        });




        transactionDetail_byUser_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Screen.this, TransactionDetails_by_buyer.class);
                startActivity(intent);
            }
        });




        paymentReportsByUserConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Screen.this, Payment_Reports_by_Buyer.class);
                startActivity(intent);
            }
        });



        paymentReceivedReportsConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Screen.this, PaymentReceivedReport.class);
                startActivity(intent);
            }
        });
        OutStanding_Report_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Screen.this, OutStandingReport.class);
                startActivity(intent);
            }
        });

        clearOutStandingCredit_Retailer_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Screen.this, ClearOutstandingCreditAmountScreen.class);
                startActivity(intent);
            }
        });

        consolidatedSalesReport_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Screen.this, ConsolidatedSalesReport.class);
                startActivity(intent);
            }
        });



        markas_delivered_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Screen.this, ViewOrderList.class);
                intent.putExtra("CalledFrom","markasdeliveredorderscreen");
                startActivity(intent);
            }
        });


        viewStockBalance_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Screen.this, ViewStockBalance.class);
                startActivity(intent);
            }
        });


        placeOrder_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isB2BCartOrderDetailsEntryAlreadyCreated) {
                    new TMCAlertDialogClass(Home_Screen.this, R.string.app_name, R.string.Clear_Cart_Instruction,
                            R.string.Yes_Text, R.string.No_Text,
                            new TMCAlertDialogClass.AlertListener() {
                                @Override
                                public void onYes() {
                                    Intent intent = new Intent(Home_Screen.this, PlaceNewOrder_activity.class);
                                    intent.putExtra("isB2BCartOrderDetailsEntryAlreadyCreated",isB2BCartOrderDetailsEntryAlreadyCreated);
                                    intent.putExtra("showCartItemDetails",false);
                                    intent.putExtra("clearcartnow",true);
                                    intent.putExtra("orderid",orderid);
                                    startActivity(intent);

                                }

                                @Override
                                public void onNo() {

                                }
                            });


                }
                else{

                    Intent intent = new Intent(Home_Screen.this, PlaceNewOrder_activity.class);
                    intent.putExtra("isB2BCartOrderDetailsEntryAlreadyCreated",isB2BCartOrderDetailsEntryAlreadyCreated);
                    intent.putExtra("showCartItemDetails",false);
                    intent.putExtra("clearcartnow",false);
                    intent.putExtra("orderid","");
                    startActivity(intent);
                }


            }
        });



        itemsInCart_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Screen.this, PlaceNewOrder_activity.class);
                intent.putExtra("isB2BCartOrderDetailsEntryAlreadyCreated",isB2BCartOrderDetailsEntryAlreadyCreated);
                intent.putExtra("showCartItemDetails",true);
                intent.putExtra("clearcartnow",false);
                intent.putExtra("orderid",orderid);
                startActivity(intent);
            }
        });

        batchList_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Home_Screen.this, BatchList_activity.class);
                startActivity(intent);
            }
        });

        manageRetailer_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Screen.this, Add_Or_Edit_Retailer_Activity.class);
                startActivity(intent);
            }
        });


        settings_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Screen.this, Settings_activity.class);
                startActivity(intent);
            }
        });


    }

    private void Intitalize_And_Execute_B2BCartOrderDetails(String callMethod) {
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
                showProgressBar(false);
                isB2BCartOrderTableServiceCalled = false;
                if(result.equals(Constants.emptyResult_volley)){
                    orderid = "";
                    isB2BCartOrderDetailsEntryAlreadyCreated = false;
                    iteminCart_dividerView.setVisibility(View.GONE);
                    itemsInCart_ConstraintLayout.setVisibility(View.GONE);
                }
                else{
                    orderid  = Modal_B2BCartOrderDetails.getOrderid().toString();
                    isB2BCartOrderDetailsEntryAlreadyCreated = true;
                    iteminCart_dividerView.setVisibility(View.VISIBLE);
                    itemsInCart_ConstraintLayout.setVisibility(View.VISIBLE);
                  //  cartItem_count_textview.setText(String.valueOf(Modal_B2BCartOrderDetails.getTotalCount()));
                }


            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
                orderid = "";
                isB2BCartOrderDetailsEntryAlreadyCreated = false;
                iteminCart_dividerView.setVisibility(View.GONE);
                itemsInCart_ConstraintLayout.setVisibility(View.GONE);
            }

            @Override
            public void notifyProcessingError(Exception error) {
                showProgressBar(false);
                orderid = "";
                isB2BCartOrderDetailsEntryAlreadyCreated = false;
                iteminCart_dividerView.setVisibility(View.GONE);
                itemsInCart_ConstraintLayout.setVisibility(View.GONE);
            }
        };
        if(callMethod.equals(Constants.CallGETMethod)){
            String getApiToCall = API_Manager.getCartOrderDetailsForDeliveryCentrekey+"?deliverycentrekey="+deliveryCenterKey ;

            B2BCartOrderDetails asyncTask = new B2BCartOrderDetails(callback_b2bCartOrderDetails,  getApiToCall, Constants.CallGETMethod);
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

    @Override
    protected void onResume() {
        super.onResume();
        if(isOnRestartCalled){
            isOnRestartCalled = false;
        }
        else {

            orderid = "";
            isB2BCartOrderDetailsEntryAlreadyCreated = false;
            iteminCart_dividerView.setVisibility(View.GONE);
            itemsInCart_ConstraintLayout.setVisibility(View.GONE);

           // Toast.makeText(this, "on resume called ", Toast.LENGTH_SHORT).show();
            Intitalize_And_Execute_B2BCartOrderDetails(Constants.CallGETMethod);

           /* if (orderid.equals("")) {

                Intitalize_And_Execute_B2BCartOrderDetails(Constants.CallGETMethod);
            }
            else{
                iteminCart_dividerView.setVisibility(View.VISIBLE);
                itemsInCart_ConstraintLayout.setVisibility(View.VISIBLE);
            }

            */
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isOnRestartCalled = true;
     //   Toast.makeText(this, "on restart called ", Toast.LENGTH_SHORT).show();
        orderid = "";
        isB2BCartOrderDetailsEntryAlreadyCreated = false;
        iteminCart_dividerView.setVisibility(View.GONE);
        itemsInCart_ConstraintLayout.setVisibility(View.GONE);
        Intitalize_And_Execute_B2BCartOrderDetails(Constants.CallGETMethod);

       /* if(orderid.equals("")) {

            Intitalize_And_Execute_B2BCartOrderDetails(Constants.CallGETMethod);
        }
        else{
            iteminCart_dividerView.setVisibility(View.VISIBLE);
            itemsInCart_ConstraintLayout.setVisibility(View.VISIBLE);
        }

        */
    }

    @Override
    public void onBackPressed() {
   //     super.onBackPressed();



                if (pressedTime + 2000 > System.currentTimeMillis()) {
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
                }
                pressedTime = System.currentTimeMillis();


        
        
    }
}