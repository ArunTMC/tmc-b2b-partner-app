package com.tmc.tmcb2bpartnerapp.activity;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.adapter.Adapter_EarTagItemDetails_List;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BItemCtgy;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.fragment.BatchItemDetailsFragment_withoutScanBarcode;
import com.tmc.tmcb2bpartnerapp.fragment.DeliveryCentre_PlaceOrderScreen_Fragment;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartDetaillsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BGoatGradeDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BItemCtgyInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderItemDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.BarcodeScannerInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagDetailsInterface;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BBatchDetailsStatic;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BItemCtgy;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_Static_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BarcodeScannerScreen;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static android.os.Build.VERSION.SDK_INT;

public class GoatEarTagItemDetailsList extends BaseActivity {
    public static ListView earTagItems_listview;
    CardView scanBarcode_view;
    public static LinearLayout loadingPanel , loadingpanelmask,back_IconLayout;
    TextView toolBarHeader_TextView,scanBarcodeLayout_textview;
    FrameLayout batchItemDetailsFrame;
    public Fragment mfragment;


    public static BarcodeScannerInterface barcodeScannerInterface = null;


    public static Adapter_EarTagItemDetails_List adapter_earTagItemDetails_list =null;
    boolean isGoatEarTagDetailsTableServiceCalled = false;
    boolean isB2BCartDetailsCalled = false;
    B2BCartDetaillsInterface callback_b2BCartDetaillsInterface = null;

    B2BCartOrderDetailsInterface callback_b2bOrderDetails =null ;
    boolean isB2BCartOrderTableServiceCalled = false;

    String batchno ="";
    public String CalledFrom ="";
    String TaskToDo ="";
    String scannedBarcode="";
    public String supplierKey ="" , deliveryCenterKey ="" , deliveryCenterName ="",value_forFragment ="";
    String statusToFilter ="";String orderid ="";
    public static ArrayList<String> earTagItemsBarcodeList = new ArrayList<>();

    static ArrayList<Modal_B2BGoatGradeDetails> goatGrade_arrayLsit = new ArrayList<>();

    B2BGoatGradeDetailsInterface callback_goatGradeDetailsInterface = null;
    boolean isGoatGradeDetailsServiceCalled = false;
    private boolean isTransactionSafe;
    private boolean isTransactionPending;

    public static ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch = new ArrayList<>();
    GoatEarTagDetailsInterface callback_GoatEarTagDetails = null;
    boolean isB2BOrderItemDetailsTableServiceCalled = false;
    B2BOrderItemDetailsInterface callback_b2bOrderItemDetails;
    boolean isB2BItemCtgyTableServiceCalled = false;
    B2BItemCtgyInterface callback_B2BItemCtgyInterface;
    public static ArrayList<Modal_B2BItemCtgy> ctgy_subCtgy_DetailsArrayList = new ArrayList<>();

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
        toolBarHeader_TextView = findViewById(R.id.toolBarHeader_TextView);
        batchItemDetailsFrame  = findViewById(R.id.batchItemDetailsFrame);
        scanBarcodeLayout_textview = findViewById(R.id.scanBarcodeLayout_textview);


        Intent intent = getIntent();
        batchno = intent.getStringExtra(String.valueOf("batchno"));
        CalledFrom = intent.getStringExtra(String.valueOf("CalledFrom"));

        TaskToDo  = intent.getStringExtra(String.valueOf("TaskToDo"));


        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);

        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");

        if(CalledFrom.equals(Constants.userType_DeliveryCenter)){
            supplierKey  = intent.getStringExtra(String.valueOf("supplierkey"));

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
            if(Modal_B2BBatchDetailsStatic.getStatus().equals(Constants.batchDetailsStatus_Sold) || Modal_B2BBatchDetailsStatic.getStatus().equals(Constants.batchDetailsStatus_Cancelled)){
                scanBarcodeLayout_textview.setText(getString(R.string.search_view_barcode));

            }
            else{
                scanBarcodeLayout_textview.setText(getString(R.string.search_edit_barcode));

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

        if(TaskToDo.equals("NewAddedItem")){
            if(Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Loading)){
                toolBarHeader_TextView.setText(getString(R.string.edit_batch_item_details));

            }
            else{
                toolBarHeader_TextView.setText(getString(R.string.view_batch_item_details));

            }

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
        else{
            Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETListMethod);
        }



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
                        earTagItemsForBatch.add(modal_goatEarTagDetails);

                        if (iterator == (arrayList.size() - 1)) {

                            setAdapter();
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

            if (callMethod.equals(Constants.CallGETListMethod)) {
                //String getApiToCall = API_Manager.getOrderDetailsForBatchno+"?batchno="+batchno ;
                String getApiToCall = API_Manager.getOrderItemDetailsForOrderid + orderid;

                B2BOrderItemDetails asyncTask = new B2BOrderItemDetails(callback_b2bOrderItemDetails, getApiToCall, callMethod);
                asyncTask.execute();

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
                if (result.equals(Constants.emptyResult_volley)) {
                    showProgressBar(false);
                    isB2BCartOrderTableServiceCalled = false;


                } else {

                 //   batchno = Modal_B2BCartOrderDetails.getBatchno();
                    orderid = Modal_B2BCartOrderDetails.getOrderid();


                    Initialize_and_ExecuteB2BOrderCartDetails(Constants.CallGETMethod);







                    isB2BCartOrderTableServiceCalled = false;
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


    }

    private void Initialize_and_ExecuteB2BOrderCartDetails(String callADDMethod) {

            showProgressBar(true);

        if (isB2BCartDetailsCalled) {
            //  showProgressBar(false);
            return;
        }
        isB2BCartDetailsCalled = true;
        callback_b2BCartDetaillsInterface = new B2BCartDetaillsInterface()
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
                    if(!result.equals(Constants.emptyResult_volley)){
                       // showEditabeLayouts(false, false);
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

            B2BCartItemDetails asyncTask = new B2BCartItemDetails(callback_b2BCartDetaillsInterface,  getApiToCall, callADDMethod);
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
        callback_b2BCartDetaillsInterface = new B2BCartDetaillsInterface()
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

                            earTagItemsForBatch.add(modal_goatEarTagDetails);
                            if (iterator == (arrayList.size() - 1)) {
                                setAdapter();
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
            B2BCartItemDetails asyncTask = new B2BCartItemDetails(callback_b2BCartDetaillsInterface, getApiToCall, callMethod);
            asyncTask.execute();
        }
        else {

            String getApiToCall = API_Manager.getCartDetailsForOrderid + orderid;
            B2BCartItemDetails asyncTask = new B2BCartItemDetails(callback_b2BCartDetaillsInterface, getApiToCall, callMethod);
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
        setAdapter();



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

                    if (CalledFrom.equals(Constants.userType_DeliveryCenter)){

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
                    else if (CalledFrom.equals(getString(R.string.placedOrder_Details_Screen)))
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


        }


    }


    private void Initialize_and_ExecuteInGoatEarTagDetails(String callMethod) {
        earTagItemsForBatch.clear();
        earTagItemsBarcodeList.clear();

        showProgressBar(true);
        if (isGoatEarTagDetailsTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isGoatEarTagDetailsTableServiceCalled = true;
        earTagItemsForBatch.clear();
        callback_GoatEarTagDetails = new GoatEarTagDetailsInterface() {


            @Override
            public void notifySuccess(String result) {

                //showProgressBar(false);

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
                    setAdapter();
                }
                catch (Exception e){
                    setAdapter();
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
                setAdapter();
                isGoatEarTagDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                Toast.makeText(GoatEarTagItemDetailsList.this, "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                setAdapter();
                showProgressBar(false);
                isGoatEarTagDetailsTableServiceCalled = false;


            }




        };
        if(CalledFrom.equals(Constants.userType_SupplierCenter)){
            if(TaskToDo.equals("NewAddedItem")){
                String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithVariousStatus +"?batchno="+batchno +"&status1="+Constants.goatEarTagStatus_EarTagLost+"&filtertype="+Constants.api_filtertype_notequals;
                GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails,  addApiToCall , callMethod);
                asyncTask.execute();

            }
            else  if (TaskToDo.equals("Oldtem")) {
                String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithVariousStatus +"?batchno="+batchno +"&status1="+Constants.goatEarTagStatus_EarTagLost+"&filtertype="+Constants.api_filtertype_notequals;
                GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails,  addApiToCall , callMethod);
                asyncTask.execute();
            }

        }
        else if(CalledFrom.equals(Constants.userType_DeliveryCenter)) {
            if(TaskToDo.equals("ViewUnReviewedItem")){
                String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithStatus +"?batchno="+batchno +"&status="+Constants.goatEarTagStatus_Loading;
                GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails,  addApiToCall , callMethod);
                asyncTask.execute();

            }
            else if(TaskToDo.equals("ViewReviewedItem")){
                String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithVariousStatus +"?batchno="+batchno +"&status1="+Constants.goatEarTagStatus_Reviewed_and_READYFORSALE+"&status2="+Constants.goatEarTagStatus_Goatsick+"&filtertype="+Constants.api_filtertype_equals;
                GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails,  addApiToCall , callMethod);
                asyncTask.execute();

            }
            else if(TaskToDo.equals("ViewSoldItem")){
                String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithStatus +"?batchno="+batchno +"&status="+Constants.goatEarTagStatus_Sold;
                GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails,  addApiToCall , callMethod);
                asyncTask.execute();

            }
            else if(TaskToDo.equals("ViewUnSoldItem")){
                String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithVariousStatus +"?batchno="+batchno +"&status1="+Constants.goatEarTagStatus_Reviewed_and_READYFORSALE+"&status2="+Constants.goatEarTagStatus_Goatsick+"&filtertype="+Constants.api_filtertype_equals;
                GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails,  addApiToCall , callMethod);
                asyncTask.execute();

            }
        }






        }

    private void setAdapter() {
        if(earTagItemsForBatch.size()>0){
            earTagItems_listview.setVisibility(View.VISIBLE);
        }
        else{
            earTagItems_listview.setVisibility(View.INVISIBLE);
        }


        adapter_earTagItemDetails_list = new Adapter_EarTagItemDetails_List(GoatEarTagItemDetailsList.this,earTagItemsForBatch, GoatEarTagItemDetailsList.this,CalledFrom,TaskToDo);
        earTagItems_listview.setAdapter(adapter_earTagItemDetails_list);
        showProgressBar(false);
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


}