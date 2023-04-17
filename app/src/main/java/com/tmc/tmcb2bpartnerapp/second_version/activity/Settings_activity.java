package com.tmc.tmcb2bpartnerapp.second_version.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.Add_Or_Edit_Retailer_Activity;
import com.tmc.tmcb2bpartnerapp.activity.ChangeGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.activity.DatewisePlacedOrdersList;
import com.tmc.tmcb2bpartnerapp.activity.LoginActivity;
import com.tmc.tmcb2bpartnerapp.activity.PasswordVerificationScreen;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.TMCAlertDialogClass;

public class Settings_activity extends BaseActivity {
    TextView userMobileNo,supplier_name ,toolbar_textview;
    String usermobileno_string ="",supplierName ="";
    Context mContext;
    Button logout_button ,goatgradeDetails_button;
    LinearLayout add_or_edit_retailers_button , datewisePlacedOrdersList_button,back_IconLayout,Login_as_another_vendor_button;
    LinearLayout ManageGoatDetailsConfiguration_linearLayout;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        toolbar_textview = findViewById(R.id.toolBarHeader_TextView);
        ManageGoatDetailsConfiguration_linearLayout = findViewById(R.id.ManageGoatDetailsConfiguration_linearLayout);
        supplier_name = findViewById(R.id.deliveryCenter_name);
        userMobileNo = findViewById(R.id.userMobileNo);
        goatgradeDetails_button = findViewById(R.id.goatgradeDetails_button);
        back_IconLayout = findViewById(R.id.back_IconLayout);
        datewisePlacedOrdersList_button  = findViewById(R.id.datewisePlacedOrdersList_button);
        add_or_edit_retailers_button = findViewById(R.id.add_or_edit_retailers_button);
        SharedPreferences sh = getSharedPreferences("LoginData",MODE_PRIVATE);
        usermobileno_string = sh.getString("UserMobileNumber","");

        SharedPreferences sh1 = getSharedPreferences("DeliveryCenterData",MODE_PRIVATE);
        supplierName = sh1.getString("DeliveryCenterName","");

        supplier_name.setText(supplierName);
        userMobileNo.setText(usermobileno_string);
        toolbar_textview.setText("Settings ");
        logout_button = findViewById(R.id.logout_button);
        Login_as_another_vendor_button = findViewById(R.id.Login_as_another_vendor_button);
        // datewisePlacedOrdersList_button.setVisibility(View.GONE);



        if(usermobileno_string.contains("9597580128") || usermobileno_string.contains("7010779096") || usermobileno_string.contains("9994026550")){
            Login_as_another_vendor_button.setVisibility(View.VISIBLE);
            ManageGoatDetailsConfiguration_linearLayout.setVisibility(View.VISIBLE);
        }
        else{
            ManageGoatDetailsConfiguration_linearLayout.setVisibility(View.GONE);
            Login_as_another_vendor_button.setVisibility(View.GONE);
        }



        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        Login_as_another_vendor_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TMCAlertDialogClass(Settings_activity.this, R.string.app_name, R.string.LogoutDeliveryCentre_Instruction,
                        R.string.Yes_Text, R.string.No_Text,
                        new TMCAlertDialogClass.AlertListener() {
                            @Override
                            public void onYes() {
                                logOutFromCurrentDeliveryCentre();

                            }

                            @Override
                            public void onNo() {

                            }
                        });

            }
        });




        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TMCAlertDialogClass(Settings_activity.this, R.string.app_name, R.string.Logout_Instruction,
                        R.string.Yes_Text, R.string.No_Text,
                        new TMCAlertDialogClass.AlertListener() {
                            @Override
                            public void onYes() {
                                signOutfromAWSandClearSharedPref();

                            }

                            @Override
                            public void onNo() {

                            }
                        });

            }
        });




        datewisePlacedOrdersList_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings_activity.this, DatewisePlacedOrdersList.class);
                intent.putExtra("CalledFrom","datewiseorderslistscreen");
                startActivity(intent);
            }
        });


        add_or_edit_retailers_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings_activity.this, Add_Or_Edit_Retailer_Activity.class);
                startActivity(intent);
            }
        });



    }

    private void logOutFromCurrentDeliveryCentre() {



        SharedPreferences sharedPreferences
                = getSharedPreferences("LoginData",
                MODE_PRIVATE);

        SharedPreferences.Editor myEdit
                = sharedPreferences.edit();

        myEdit.putBoolean(
                "LoginStatus", false
        );

        myEdit.putString(
                "SupplierKey", ""        );


        myEdit.apply();


        SharedPreferences sharedPreferences_SupplierData
                = getSharedPreferences("DeliveryCenterData",
                MODE_PRIVATE);

        SharedPreferences.Editor edit
                = sharedPreferences_SupplierData.edit();

        edit.putString(
                "DeliveryCenterKey", "");
        edit.putString(
                "DeliveryCenterName", "");
        edit.putString(
                "DeliveryCenterPassword", "");


        edit.apply();

        Intent i = new Intent(Settings_activity.this, PasswordVerificationScreen.class);
        startActivity(i);
        finish();




    }


    private void signOutfromAWSandClearSharedPref() {
        try {
            AWSMobileClient.getInstance().signOut();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        SharedPreferences sharedPreferences
                = getSharedPreferences("LoginData",
                MODE_PRIVATE);

        SharedPreferences.Editor myEdit
                = sharedPreferences.edit();

        myEdit.putBoolean(
                "LoginStatus", false
        );
        myEdit.putString("UserMobileNumber","");
        myEdit.putString(
                "UserType", ""
        );
        myEdit.putString(
                "SupplierKey", ""        );


        myEdit.apply();


        SharedPreferences sharedPreferences_SupplierData
                = getSharedPreferences("DeliveryCenterData",
                MODE_PRIVATE);

        SharedPreferences.Editor edit
                = sharedPreferences_SupplierData.edit();

        edit.putString(
                "DeliveryCenterKey", "");
        edit.putString(
                "DeliveryCenterName", "");
        edit.putString(
                "DeliveryCenterPassword", "");


        edit.apply();

        Intent i = new Intent(Settings_activity.this, LoginActivity.class);
        startActivity(i);
        finish();



    }

}