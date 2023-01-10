package com.tmc.tmcb2bpartnerapp.activity;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchNoManager;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.DeliveryCenterDetails;
import com.tmc.tmcb2bpartnerapp.fragment.BatchItemDetailsScreenFragment;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchNoManagerInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.DeliveryCenterDetailsInterface;
import com.tmc.tmcb2bpartnerapp.model.Modal_AppUserAccess;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BBatchDetailsStatic;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BBatchDetailsUpdate;
import com.tmc.tmcb2bpartnerapp.model.Modal_DeliveryCenterDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_SupplierDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.TMCAlertDialogClass;

import java.util.ArrayList;
import java.util.List;

import static com.tmc.tmcb2bpartnerapp.utils.Constants.CallADDMethod;
import static com.tmc.tmcb2bpartnerapp.utils.Constants.CallUPDATEMethod;
import static com.tmc.tmcb2bpartnerapp.utils.Constants.TAG;

public class CreateNew_Or_EditOldBatchScreen extends BaseActivity {
String activityCalledFrom = "", selectDeliveryCenterKey ="", batchno = "" , selectDeliveryCenterName ="";
LinearLayout createNewItem_layout,editExistingItem_layout;
TextView itemCount_textview, itemWeight_textview,toolBarHeader_TextView,batchid_textview,deliveryCenterNameLabel_textview,deliveryCenterName_textview;
Button addNewItem_Button,addNewItem_ButtonExistingBatch,editOldItem_ButtonExistingBatch,finishBatch_Button;
Fragment mfragment;
FrameLayout batchItemDetailsFrame;
ImageView backButton_icon;
DeliveryCenterDetailsInterface callback_DeliveryCenterDetailsInterface = null;
B2BBatchNoManagerInterface callback_b2BBatchIdManagerInterface =null;
boolean  isDeliveryCentereTableServiceCalled = false;
B2BBatchDetailsInterface callback_B2BBatchDetailsInterface = null;
boolean  isBatchDetailsTableServiceCalled = false;

Spinner deliveryCenterName_spinner;
ImageView delete_batchIcon;
static LinearLayout loadingPanel , loadingpanelmask ,back_IconLayout;
LinearLayout delieryCenterName_spinnerLayout,itemCount_layout,itemWeight_layout,deletebatchLayout;
private static final int REQUEST_CAMERA_PERMISSION = 201;
String supplierKey ="", supplierName ="",userType ="";
public static List<Modal_DeliveryCenterDetails> deliveryCenterDetailsList = new ArrayList<>();
boolean iscalledFromCreateNewbatch =false;




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
                setContentView(R.layout.activity_create_new_batch_screen);
            } else {

                // Inflate the layout for this fragment
                setContentView(R.layout.pos_activity_create_new_batch_screen);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            setContentView(R.layout.activity_create_new_batch_screen);

        }








        editExistingItem_layout = findViewById(R.id.editExistingItem_layout);
        createNewItem_layout = findViewById(R.id.createNewItem_layout);
        toolBarHeader_TextView = findViewById(R.id.toolBarHeader_TextView);
        addNewItem_Button = findViewById(R.id.addNewItem_Button);
        batchItemDetailsFrame  = findViewById(R.id.batchItemDetailsFrame);
        addNewItem_ButtonExistingBatch = findViewById(R.id.addNewItem_ButtonExistingBatch);
        editOldItem_ButtonExistingBatch = findViewById(R.id.editOldItem_ButtonExistingBatch);
        backButton_icon = findViewById(R.id.backButton_icon);
        finishBatch_Button = findViewById(R.id.finishBatch_Button);
        deliveryCenterName_spinner = findViewById(R.id.delieryCenterName_spinner);
        delete_batchIcon = findViewById(R.id.delete_batchIcon);
        deletebatchLayout = findViewById(R.id.deletebatchLayout);
        batchid_textview = findViewById(R.id.batchid_textview);
        deliveryCenterNameLabel_textview = findViewById(R.id.deliveryCenterNameLabel_textview);
        deliveryCenterName_textview = findViewById(R.id.deliveryCenterName_textview);
        delieryCenterName_spinnerLayout  =  findViewById(R.id.delieryCenterName_spinnerLayout);
        loadingpanelmask =  findViewById(R.id.loadingpanelmask);
        loadingPanel =  findViewById(R.id.loadingPanel);
        back_IconLayout =  findViewById(R.id.back_IconLayout);
        itemCount_layout =  findViewById(R.id.itemCount_layout);
        itemWeight_layout  =  findViewById(R.id.itemWeight_layout);
        itemCount_textview = findViewById(R.id.itemCount_textview);
        itemWeight_textview = findViewById(R.id.itemWeight_textview);
        iscalledFromCreateNewbatch = false;

        SharedPreferences sh
                = getSharedPreferences("LoginData",
                MODE_PRIVATE);
        userType = sh.getString("UserType", "");



        Intent intent = getIntent();
        activityCalledFrom = intent.getStringExtra(String.valueOf(getString(R.string.called_from)));
        iscalledFromCreateNewbatch = intent.getBooleanExtra("iscalledFromCreateNewBatch",false);
        checkPermissionForCamera();




        if(activityCalledFrom.equals(String.valueOf(getString(R.string.create_new_batch)))){
            showProgressBar(true);
            Intialize_And_getDataFrom_DeliveryCenterDetails();
            Initialize_And_Process_BatchIDManager("GET");
            deliveryCenterNameLabel_textview.setText(getString(R.string.select_delivery_center));
            deliveryCenterName_textview.setVisibility(View.GONE);
            delieryCenterName_spinnerLayout.setVisibility(View.VISIBLE);
            itemWeight_layout.setVisibility(View.GONE);
            itemCount_layout.setVisibility(View.GONE);
            deletebatchLayout.setVisibility(View.GONE);
            createNewItem_layout.setVisibility(View.VISIBLE);
            editExistingItem_layout.setVisibility(View.GONE);
            toolBarHeader_TextView.setText(String.valueOf(getString(R.string.create_new_batch)));
        }
        else if(activityCalledFrom.equals(String.valueOf(getString(R.string.edit_existing_batch)))) {
            createNewItem_layout.setVisibility(View.GONE);
            editExistingItem_layout.setVisibility(View.VISIBLE);
            delieryCenterName_spinnerLayout.setVisibility(View.GONE);
            deliveryCenterName_textview.setVisibility(View.VISIBLE);
            batchid_textview.setText(String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno()));
            deliveryCenterName_textview.setText(String.valueOf(Modal_B2BBatchDetailsStatic.getDeliverycentername()));
            toolBarHeader_TextView.setText(String.valueOf(getString(R.string.edit_existing_batch)));
            deliveryCenterNameLabel_textview.setText(getString(R.string.selected_delivery_center));
            batchno = String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno());
            selectDeliveryCenterKey = String.valueOf(Modal_B2BBatchDetailsStatic.getDeliverycenterkey());
            selectDeliveryCenterName = String.valueOf(Modal_B2BBatchDetailsStatic.getDeliverycentername());

            if(!Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Loading)){
                itemCount_layout.setVisibility(View.VISIBLE);
                finishBatch_Button.setVisibility(View.VISIBLE);
                finishBatch_Button.setText(R.string.Generate_Report);
                addNewItem_ButtonExistingBatch.setVisibility(View.GONE);
                itemWeight_layout.setVisibility(View.VISIBLE);
                deletebatchLayout.setVisibility(View.GONE);
                itemWeight_textview.setText(Modal_B2BBatchDetailsStatic.getLoadedweightingrams());
                itemCount_textview.setText(Modal_B2BBatchDetailsStatic.getItemcount());
            }
            else{

                if(iscalledFromCreateNewbatch){
                    deletebatchLayout.setVisibility(View.GONE);

                }
                else{
                    deletebatchLayout.setVisibility(View.VISIBLE);

                }
                addNewItem_ButtonExistingBatch.setVisibility(View.VISIBLE);
                itemCount_layout.setVisibility(View.GONE);
                finishBatch_Button.setText(R.string.finish_batch);
                finishBatch_Button.setVisibility(View.VISIBLE);
                itemWeight_layout.setVisibility(View.GONE);
            }
        }




        addNewItem_Button.setOnClickListener(view -> runOnUiThread(() -> {

            if(selectDeliveryCenterName.equals("")){
                AlertDialogClass.showDialog(CreateNew_Or_EditOldBatchScreen.this, R.string.Select_DeliveryCenter_Instruction);

            }
            else {
                try {
                    mfragment = new BatchItemDetailsScreenFragment();
                    loadMyFragment(mfragment, getString(R.string.add_new_item));


                    showProgressBar(false);

                } catch (WindowManager.BadTokenException e) {
                    showProgressBar(false);

                    e.printStackTrace();
                }
            }
        }));


        addNewItem_ButtonExistingBatch.setOnClickListener(view -> runOnUiThread(() -> {

            if((!String.valueOf(Modal_B2BBatchDetailsStatic.getStatus()).toUpperCase().equals(Constants.batchDetailsStatus_Loading)) && (!String.valueOf(Modal_B2BBatchDetailsStatic.getStatus()).toUpperCase().equals(Constants.batchDetailsStatus_Fully_Loaded))){
                Toast.makeText(this, "Can't Add new Item while status is "+ Modal_B2BBatchDetailsStatic.getStatus(), Toast.LENGTH_SHORT).show();
            }
            try {
                mfragment = new BatchItemDetailsScreenFragment();
                loadMyFragment(mfragment,getString(R.string.add_new_item_existing_batch));


                showProgressBar(false);

            } catch (WindowManager.BadTokenException e) {
                showProgressBar(false);

                e.printStackTrace();
            }
        }));


        editOldItem_ButtonExistingBatch.setOnClickListener(view -> runOnUiThread(() -> {
            try {
                mfragment = new BatchItemDetailsScreenFragment();
                loadMyFragment(mfragment,getString(R.string.view_edit_existing_item));


                showProgressBar(false);

            } catch (WindowManager.BadTokenException e) {
                showProgressBar(false);

                e.printStackTrace();
            }
        }));


        finishBatch_Button.setOnClickListener(view -> runOnUiThread(() -> {
            try {
                Intent intent1 = new Intent(CreateNew_Or_EditOldBatchScreen.this,FinishBatch_ConsolidatedReport.class);
                intent1.putExtra("batchno", batchno) ;
                intent1.putExtra("deliveryCenterKey", selectDeliveryCenterKey) ;
                intent1.putExtra("deliveryCenterName", selectDeliveryCenterName) ;
                intent1.putExtra(getString(R.string.called_from), getString(R.string.supplier)) ;
                startActivity(intent1);
                showProgressBar(false);

            } catch (WindowManager.BadTokenException e) {
                showProgressBar(false);

                e.printStackTrace();
            }
        }));



        back_IconLayout.setOnClickListener(view -> {
            onBackPressed();
        });

        deletebatchLayout.setOnClickListener(view -> {
            new TMCAlertDialogClass(this, R.string.app_name, R.string.DeleteBatch_Instruction,
                    R.string.Yes_Text, R.string.No_Text,
                    new TMCAlertDialogClass.AlertListener() {
                        @Override
                        public void onYes() {
                            Intialize_And_Process_BatchDetails(batchno,CallUPDATEMethod);

                        }

                        @Override
                        public void onNo() {

                        }
                    });
        });

        deliveryCenterName_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                    selectDeliveryCenterKey = getDeliveryCenterData(position, "deliverycenterkey");
                    selectDeliveryCenterName = getDeliveryCenterData(position, "name");
                    // Toast.makeText(CreateNew_Or_EditOldBatchScreen.this, selectDeliveryCenterKey, Toast.LENGTH_SHORT).show();

                    Initialize_And_Process_BatchIDManager("GENERATE");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void checkPermissionForCamera() {
        try {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {


            } else {
                ActivityCompat.requestPermissions(CreateNew_Or_EditOldBatchScreen.this, new
                        String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);


            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        }

    private void Initialize_And_Process_BatchIDManager(String methodToCall) {
        showProgressBar(true);
        callback_b2BBatchIdManagerInterface = new B2BBatchNoManagerInterface() {


            @Override
            public void notifySuccess(String result) {
                batchno = result;
                if(methodToCall.equals("GET")){
                    int batchid_Int = Integer.parseInt(batchno);
                    batchid_Int = batchid_Int+1;
                    batchno = String.valueOf(batchid_Int);
                }
                else if(methodToCall.equals("GENERATE")){

                    Intialize_And_Process_BatchDetails(batchno, CallADDMethod);
                }
                batchid_textview.setText(batchno);

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
            }

            @Override
            public void notifyProcessingError(Exception error) {
                showProgressBar(false);
            }



        };
        if(methodToCall.equals("GENERATE"))
            B2BBatchNoManager.generateNewBatchNo(callback_b2BBatchIdManagerInterface);
        else if(methodToCall.equals("GET"))
            B2BBatchNoManager.getBatchNo(callback_b2BBatchIdManagerInterface);


    }

    private void Intialize_And_Process_BatchDetails(String batchId, String callADDMethod) {
        showProgressBar(true);
        if (isBatchDetailsTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isBatchDetailsTableServiceCalled = true;
        callback_B2BBatchDetailsInterface = new B2BBatchDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BBatchDetails> batchDetailsArrayList) {

            }

            @Override
            public void notifySuccess(String result) {

                if(callADDMethod.equals(CallADDMethod)){
                if(result.equals(Constants.item_Already_Added_volley)){
                    AlertDialogClass.showDialog(CreateNew_Or_EditOldBatchScreen.this, R.string.BatchDetailsAlreadyCreated_Instruction);

                }
                else if(result.equals(Constants.successResult_volley)){
                    deliveryCenterNameLabel_textview.setText(getString(R.string.selected_delivery_center));
                    delieryCenterName_spinnerLayout.setVisibility(View.GONE);
                    deliveryCenterName_textview.setVisibility(View.VISIBLE);
                    deliveryCenterName_textview.setText(selectDeliveryCenterName);
                }
                else{
                    //Toast.makeText(CreateNew_Or_EditOldBatchScreen.this, Constants.unknown_API_Result_volley, Toast.LENGTH_SHORT).show();
                }
                }
                else if(callADDMethod.equals(CallUPDATEMethod)){
                    onBackPressed();
                }



                showProgressBar(false);
                isBatchDetailsTableServiceCalled = false;

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);

                isBatchDetailsTableServiceCalled = false;
                Toast.makeText(CreateNew_Or_EditOldBatchScreen.this, Constants.volleyError_Result_volley, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void notifyProcessingError(Exception error) {
                showProgressBar(false);
                Toast.makeText(CreateNew_Or_EditOldBatchScreen.this, Constants.processingErrorResult_volley, Toast.LENGTH_SHORT).show();

                isBatchDetailsTableServiceCalled = false;
            }




        };
        if(callADDMethod.equals(CallADDMethod)) {
            Modal_B2BBatchDetailsStatic.setBatchno(batchId);
            Modal_B2BBatchDetailsStatic.setDeliverycentername(selectDeliveryCenterName);
            Modal_B2BBatchDetailsStatic.setDeliverycenterkey(selectDeliveryCenterKey);
            Modal_B2BBatchDetailsStatic.setSupplierkey(Modal_SupplierDetails.getSupplierkey_static());
            Modal_B2BBatchDetailsStatic.setSuppliername(Modal_SupplierDetails.getName_static());
            Modal_B2BBatchDetailsStatic.setStatus(Constants.batchDetailsStatus_Loading);
            Modal_B2BBatchDetailsStatic.setCreateddate(DateParser.getDate_and_time_newFormat());
            Modal_B2BBatchDetailsStatic.setSuppliermobileno(Modal_AppUserAccess.getMobileno());


            String addApiToCall = API_Manager.addBatchDetails;

            B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface, addApiToCall, callADDMethod);
            asyncTask.execute();
        }
        else  if(callADDMethod.equals(CallUPDATEMethod)) {
            Modal_B2BBatchDetailsUpdate modal_b2BBatchDetailsUpdate = new Modal_B2BBatchDetailsUpdate();
            modal_b2BBatchDetailsUpdate.setBatchno(batchno);
            modal_b2BBatchDetailsUpdate.setSupplierkey(supplierKey);
            modal_b2BBatchDetailsUpdate.setStatus(Constants.batchDetailsStatus_Cancelled);

            String addApiToCall = API_Manager.updateBatchDetailsWithSupplierkeyBatchNo;

            B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface, addApiToCall, CallUPDATEMethod);
            asyncTask.execute();

        }






    }

    private void Intialize_And_getDataFrom_DeliveryCenterDetails() {

        if (isDeliveryCentereTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isDeliveryCentereTableServiceCalled = true;
        callback_DeliveryCenterDetailsInterface = new DeliveryCenterDetailsInterface() {


            @Override
            public void notifySuccess(String result) {
                addDataToDeliveryCenterSpinner();
            }

            @Override
            public void notifyError(VolleyError error) {
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
                showProgressBar(false);

                isDeliveryCentereTableServiceCalled = false;
            }


        };

        String getApiToCall = API_Manager.getDeliveryCenterList ;

        DeliveryCenterDetails asyncTask = new DeliveryCenterDetails(callback_DeliveryCenterDetailsInterface,  getApiToCall,Constants.CallGETListMethod);
        asyncTask.execute();




    }

    private void addDataToDeliveryCenterSpinner() {
        deliveryCenterDetailsList =DatabaseArrayList_PojoClass.deliveryCenterDetailsList ;
         ArrayList<String> deliveryCenter_arrayList = new ArrayList<>();
      //  deliveryCenter_arrayList.add(getString(R.string.selectItem_SpinnerInstruction));
        try{
            for(int i=0; i< deliveryCenterDetailsList.size();i++){
                deliveryCenter_arrayList.add(deliveryCenterDetailsList.get(i).getName());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        ArrayAdapter aAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, deliveryCenter_arrayList);
        deliveryCenterName_spinner.setAdapter(aAdapter);
        showProgressBar(false);

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
    public void onBackPressed() {
        if(batchItemDetailsFrame.getVisibility() == View.VISIBLE){
            batchItemDetailsFrame.setVisibility(View.GONE);
            return;
        }
    //    Intent intent = new Intent(CreateNew_Or_EditOldBatchScreen.this, SupplierDashboardScreen.class);
      //  intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
       // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

      //  startActivity(intent);
        finish();
        overridePendingTransition(0,0);


    }


    public  void closeFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(batchItemDetailsFrame.getId());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();



        batchItemDetailsFrame.setVisibility(View.GONE);
    }



    private void loadMyFragment(Fragment fm, String Value) {
        batchItemDetailsFrame.setVisibility(View.VISIBLE);
        if (fm != null) {

            try {

                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null);
                transaction2 .replace(batchItemDetailsFrame.getId(),  BatchItemDetailsScreenFragment.newInstance(getString(R.string.called_from),Value));

                transaction2.commit();


            } catch (Exception e) {
                onResume();
                e.printStackTrace();
            }


        }
    }

/*
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Tag","On Restart Createnew");

        Intent i = getBaseContext().getPackageManager().
                getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }

 */

    @Override
    protected void onResume() {
        Intent intent = getIntent();
        activityCalledFrom = intent.getStringExtra(String.valueOf(getString(R.string.called_from)));


        if(String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno()).equals("") ){
            if(!activityCalledFrom.equals(String.valueOf(getString(R.string.create_new_batch)))) {

                Log.d("Tag", "On Resume Createnew if");
                Intent i = new Intent(this, SupplierDashboardScreen.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
            else{
                super.onResume();
            }
        }
        else{
            Log.d("Tag","On Resume Createnew else");

            super.onResume();
            SharedPreferences sh = getSharedPreferences("SupplierData",MODE_PRIVATE);
            supplierKey = sh.getString("SupplierKey","");
            supplierName = sh.getString("SupplierName","");


            SharedPreferences sh1
                    = getSharedPreferences("LoginData",
                    MODE_PRIVATE);
            userType = sh1.getString("UserType", "");
        }


    }



    //Doing the same with this method as we did with getName()
    private String getDeliveryCenterData(int position,String fieldName){
        String data="";
        try {
            Modal_DeliveryCenterDetails vendor = DatabaseArrayList_PojoClass.deliveryCenterDetailsList.get(position);
            data = vendor.getGet(fieldName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}