package com.tmc.tmcb2bpartnerapp.activity;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.DeliveryCenterDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.SupplierDetails;
import com.tmc.tmcb2bpartnerapp.interfaces.DeliveryCenterDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.SupplierDetailsInterface;
import com.tmc.tmcb2bpartnerapp.model.Modal_AppUserAccess;
import com.tmc.tmcb2bpartnerapp.model.Modal_DeliveryCenterDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_SupplierDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;

import java.util.ArrayList;

import static com.tmc.tmcb2bpartnerapp.utils.Constants.TAG;

public class PasswordVerificationScreen extends BaseActivity {

    Button verifyDeliveryCenterPasswordbutton,verifySupplierPasswordbutton;
    CardView supplierCardView,deliveryCenterCardView;
    String userType ="",supplierKey ="",password ="",selectDeliveryCenterKey="",selectDeliveryCenterName="",selectDeliveryCenterPassword ="";
    EditText password_editText,deliveryCenter_password_editText;
    TextView supplierName_textview;
    SupplierDetailsInterface callback_supplierDetailsInterface = null;
    boolean  isSupplierDetailsTableServiceCalled = false;
    LinearLayout loadingPanel , loadingpanelmask ,back_IconLayout;
    DeliveryCenterDetailsInterface callback_DeliveryCenterDetailsInterface = null;
    boolean  isDeliveryCentereTableServiceCalled = false;

    Spinner delieryCenterName_spinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDeviceName();
        if(isDeviceIsMobilePhone){
            setContentView(R.layout.activity_password_verification_screen);
        }
        else{

            setContentView(R.layout.pos_activity_password_verification_screen);
        }



        verifyDeliveryCenterPasswordbutton = findViewById(R.id.verifyDeliveryCenterPasswordbutton);
        verifySupplierPasswordbutton = findViewById(R.id.verifySupplierPasswordbutton);
        supplierCardView = findViewById(R.id.supplierCardView);
        deliveryCenterCardView = findViewById(R.id.deliveryCenterCardView);
        supplierName_textview = findViewById(R.id.supplierName_textview);
        password_editText = findViewById(R.id.password_editText);
        loadingpanelmask =  findViewById(R.id.loadingpanelmask);
        loadingPanel =  findViewById(R.id.loadingPanel);
        back_IconLayout = findViewById(R.id.back_IconLayout);
        delieryCenterName_spinner  =  findViewById(R.id.delieryCenterName_spinner);
        deliveryCenter_password_editText =  findViewById(R.id.deliveryCenter_password_editText);

        userType = Modal_AppUserAccess.getUsertype();

        if(userType.toUpperCase().equals(Constants.userType_SupplierCenter)){
            supplierCardView.setVisibility(View.VISIBLE);
            deliveryCenterCardView.setVisibility(View.GONE);
            supplierKey = String.valueOf(Modal_AppUserAccess.getSupplierkey());
            supplierName_textview.setText(String.valueOf(Modal_AppUserAccess.getSuppliername()));
            isSupplierDetailsTableServiceCalled = false;
            call_and_init_SupplierDetailsService();
        }
        else{
            supplierKey = String.valueOf(Modal_AppUserAccess.getSupplierkey());
            isSupplierDetailsTableServiceCalled = false;
            Intialize_And_getDataFrom_DeliveryCenterDetails();

            supplierCardView.setVisibility(View.GONE);
            deliveryCenterCardView.setVisibility(View.VISIBLE);
        }



        verifyDeliveryCenterPasswordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password_asInput = String.valueOf(deliveryCenter_password_editText.getText());
                if(password_asInput.equals(selectDeliveryCenterPassword))
                {
                    saveDataInSharedPreference();

                    Intent intent = new Intent(PasswordVerificationScreen.this, DeliveryCenterDashboardScreen.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    finish();
                }
                else{
                    AlertDialogClass.showDialog(PasswordVerificationScreen.this,R.string.Enter_DeliveryCenter_Password_Instruction);

                }

            }
        });

        verifySupplierPasswordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String password_asInput = String.valueOf(password_editText.getText());
            String password_fromDb = String.valueOf(Modal_SupplierDetails.getPassword_static());

                if(password_asInput.equals(password_fromDb)){
                    saveDataInSharedPreference();
                    Intent intent = new Intent(PasswordVerificationScreen.this, SupplierDashboardScreen.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    finish();
                }
                else{
                    AlertDialogClass.showDialog(PasswordVerificationScreen.this,R.string.Enter_Supplier_Password_Instruction);

                }

            }
        });


        delieryCenterName_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                    selectDeliveryCenterKey = getDeliveryCenterData(position, "deliverycenterkey");
                    selectDeliveryCenterName = getDeliveryCenterData(position, "name");
                     selectDeliveryCenterPassword = getDeliveryCenterData(position, "password");



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




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


    private void saveDataInSharedPreference() {

        SharedPreferences sharedPreferences
                = getSharedPreferences("LoginData",
                MODE_PRIVATE);

        SharedPreferences.Editor myEdit
                = sharedPreferences.edit();

        myEdit.putBoolean(
                "LoginStatus", true
        );


        myEdit.apply();
        if(userType.toUpperCase().equals(Constants.userType_SupplierCenter)) {


            SharedPreferences sharedPreferences_SupplierData
                    = getSharedPreferences("SupplierData",
                    MODE_PRIVATE);

            SharedPreferences.Editor edit
                    = sharedPreferences_SupplierData.edit();

            edit.putString(
                    "SupplierKey", Modal_SupplierDetails.getSupplierkey_static());
            edit.putString(
                    "SupplierName", Modal_SupplierDetails.getSuppliername_static());
            edit.putString(
                    "AadharCardNo", Modal_SupplierDetails.getAadhaarcardno_static());
            edit.putString(
                    "GstNo", Modal_SupplierDetails.getGstno_static());
            edit.putString(
                    "Password", Modal_SupplierDetails.getPassword_static());
            edit.putString(
                    "PrimaryMobileNo", Modal_SupplierDetails.getPrimarymobileno_static());
            edit.putString(
                    "SecondaryMobileNo", Modal_SupplierDetails.getSecondarymobileno_static());

            edit.apply();
        }
        else if(userType.toUpperCase().equals(Constants.userType_DeliveryCenter)) {


            SharedPreferences sharedPreferences_SupplierData
                    = getSharedPreferences("DeliveryCenterData",
                    MODE_PRIVATE);

            SharedPreferences.Editor edit
                    = sharedPreferences_SupplierData.edit();

            edit.putString(
                    "DeliveryCenterKey", selectDeliveryCenterKey);
            edit.putString(
                    "DeliveryCenterName", selectDeliveryCenterName);
            edit.putString(
                    "DeliveryCenterPassword", selectDeliveryCenterPassword);
            edit.apply();
        }

    }


    private void addDataToDeliveryCenterSpinner() {
        ArrayList<String> deliveryCenter_arrayList = new ArrayList<>();
      //  deliveryCenter_arrayList.add(getString(R.string.selectItem_SpinnerInstruction));
        try{
            for(int i=0; i< DatabaseArrayList_PojoClass.deliveryCenterDetailsList.size();i++){
                deliveryCenter_arrayList.add(DatabaseArrayList_PojoClass.deliveryCenterDetailsList.get(i).getName());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        ArrayAdapter aAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, deliveryCenter_arrayList);
        delieryCenterName_spinner.setAdapter(aAdapter);
        showProgressBar(false);

    }




    private void call_and_init_SupplierDetailsService() {


        if (isSupplierDetailsTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isSupplierDetailsTableServiceCalled = true;
        callback_supplierDetailsInterface = new SupplierDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_SupplierDetails> supplierDetailsArrayList) {

            }

            @Override
            public void notifySuccess(String result) {
             //       Toast.makeText(PasswordVerificationScreen.this, "response : "+result, Toast.LENGTH_SHORT).show();
           //     Toast.makeText(PasswordVerificationScreen.this, "response 2 : "+Modal_SupplierDetails.getPassword_static(), Toast.LENGTH_SHORT).show();

                supplierKey = Modal_SupplierDetails.getSupplierkey_static();
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
                showProgressBar(false);

                isSupplierDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                Log.d(TAG, "Volley JSON post" + error);
                showProgressBar(false);
                isSupplierDetailsTableServiceCalled = false;
            }


        };

            String supplierDetailsApi = API_Manager.getsupplierDetailsWithSupplierKey + supplierKey;

            SupplierDetails asyncTask = new SupplierDetails(callback_supplierDetailsInterface,  supplierDetailsApi,Constants.CallGETMethod);
            asyncTask.execute();


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


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PasswordVerificationScreen.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
        finish();
        overridePendingTransition(0,0);
    }
}