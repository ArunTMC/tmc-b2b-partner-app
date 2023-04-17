package com.tmc.tmcb2bpartnerapp.activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserState;
import com.amazonaws.mobile.client.UserStateDetails;
import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.AppUserAccess;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BItemCtgy;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.DeliveryCenterDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.SupplierDetails;
import com.tmc.tmcb2bpartnerapp.interfaces.AppUserAccessInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BGoatGradeDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BItemCtgyInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BRetailerDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.DeliveryCenterDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.SupplierDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_AppUserAccess;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_SupplierDetails;
import com.tmc.tmcb2bpartnerapp.second_version.activity.Home_Screen;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.TMCAlertDialogClass;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import static com.tmc.tmcb2bpartnerapp.activity.View_or_Edit_BatchItem_deliveryCenter.showProgressBar;
import static com.tmc.tmcb2bpartnerapp.utils.Constants.TAG;

public class SplashScreen extends BaseActivity {
ImageView splash_imageView;
boolean loginStatusBoolean;
String mobileNo_String , userType = "" ,passCode ,deliveryCenterKey ="";
private static final int REQUEST_CAMERA_PERMISSION = 201;

    AppUserAccessInterface callback_appUserInterface = null;
    boolean  isAppUserAccessTableServiceCalled = false ;
    boolean  isDeliveryCentereTableServiceCalled = false;
    B2BBatchDetailsInterface callback_B2BBatchDetailsInterface = null;
    boolean  isBatchDetailsTableServiceCalled = false;
    DeliveryCenterDetailsInterface callback_DeliveryCenterDetailsInterface = null;
    SupplierDetailsInterface callback_supplierDetailsInterface = null;
    boolean  isSupplierDetailsTableServiceCalled = false;
    B2BRetailerDetailsInterface callback_retailerDetailsInterface = null;
    boolean  isRetailerDetailsServiceCalled = false ;

    boolean isB2BItemCtgyTableServiceCalled = false;
    B2BItemCtgyInterface callback_B2BItemCtgyInterface;


    B2BGoatGradeDetailsInterface callback_goatGradeDetailsInterface = null;
    boolean isGoatGradeDetailsServiceCalled = false;
    boolean isSupplierDetailsApiCalled = false;


    boolean isRetailerDetailsApiCalled = false;


    boolean isDeliveryCenterDetailsApiCalled = false;


    boolean isCtgyItemDetailsApiCalled = false;


    Intent intent_to_MainActivity  = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDeviceName();
        if(isDeviceIsMobilePhone){
            setContentView(R.layout.activity_splash_screen);

        }
        else{
            setContentView(R.layout.pos_activity_splash_screen);
        }


        splash_imageView = findViewById(R.id.splash_imageView);

        intent_to_MainActivity = new Intent();

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splashscreenanimation);
        splash_imageView.startAnimation(animation);
        Log.d(Constants.TAG, "App user access async onCreate   " + DateParser.getDate_and_time_newFormat());


        this.getWindow().setFlags
                (WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager
                        .LayoutParams.FLAG_FULLSCREEN);


        /*Thread thread=new Thread(){
            @Override
            public void run()
            {
                try
                {
                    sleep(2000);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    Intent mainIntent=new Intent(SplashScreen.
                            this,LoginActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    overridePendingTransition(0,0);
                    startActivity(mainIntent);

                }
            }
        };
        thread.start();

         */




    }









    @Override
    protected void onStart() {
        super.onStart();
        //checkPermissionForCamera();
        Log.d(Constants.TAG, "App user access async onStart   " + DateParser.getDate_and_time_newFormat());

        checkNetworkAccess();

    }

    private void checkNetworkAccess() {
        Log.d(Constants.TAG, "App user access async check network access   " + DateParser.getDate_and_time_newFormat());

        if(!isOnline()) {
          //  AlertDialogClass.showDialog(SplashScreen.this,R.string.Please_Connect_To_Internet);
            new TMCAlertDialogClass(this, R.string.app_name, R.string.Please_Connect_To_Internet,
                    R.string.OK_Text, R.string.Empty_Text,
                    new TMCAlertDialogClass.AlertListener() {
                        @Override
                        public void onYes() {
                            checkNetworkAccess();
                        }

                        @Override
                        public void onNo() {

                        }
                    });
        }
        else{
            AWSMobileIntialization();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Constants.TAG, "App user access async onRestart   " + DateParser.getDate_and_time_newFormat());

        //    checkPermissionForCamera();
        checkNetworkAccess();
    }

    private void AWSMobileIntialization() {
        try {
            Log.d(Constants.TAG, "App user access async AWSMobileIntialization   " + DateParser.getDate_and_time_newFormat());

            AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

                        @Override
                        public void onResult(UserStateDetails userStateDetails) {
                            //Log.i("INIT", "onResult: " + userStateDetails.getUserState());
                            if (userStateDetails.getUserState() == UserState.SIGNED_IN) {

                                SharedPreferences sh
                                        = getSharedPreferences("LoginData",
                                        MODE_PRIVATE);
                                mobileNo_String = sh.getString("UserMobileNumber", "");
                                loginStatusBoolean = sh.getBoolean("LoginStatus", false);
                                userType = sh.getString("UserType", "");





                                runOnUiThread(() -> {

                                    Intent i;

                                    if (loginStatusBoolean && mobileNo_String.length()==13 && userType.length()>1) {


                                        Thread thread=new Thread(){
                                            @Override
                                            public void run()
                                            {
                                                try
                                                {
                                                    sleep(20);
                                                }
                                                catch(Exception e)
                                                {
                                                    e.printStackTrace();
                                                }
                                                finally
                                                {
                                                    try {
                                                        call_and_init_AppUserAccessService();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }
                                        };
                                        thread.start();








                                    } else {

                                        Intent mainIntent = new Intent(SplashScreen.
                                                this, LoginActivity.class);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                                        startActivity(mainIntent);
                                        overridePendingTransition(0, 0);
                                        finish();
                                    }

                                });
                            } else {
                                Intent mainIntent = new Intent(SplashScreen.
                                        this, LoginActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                                startActivity(mainIntent);
                                overridePendingTransition(0, 0);
                                finish();
                                Log.i("INIT", "onResult: " + userStateDetails.getUserState());

                            }
                        }

                        @Override
                        public void onError(Exception e) {

                            Log.e("INIT", "Initialization error.", e);
                        }
                    }
            );
        }catch (Exception e){

            e.printStackTrace();

            Intent mainIntent=new Intent(SplashScreen.
                    this,LoginActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0,0);
            startActivity(mainIntent);
            finish();


        }
    }

    private void call_and_init_AppUserAccessService() {


        if (isAppUserAccessTableServiceCalled) {
            return;
        }
        isAppUserAccessTableServiceCalled = true;
        callback_appUserInterface = new AppUserAccessInterface() {


            @Override
            public void notifySuccess(String result) {
                //     Toast.makeText(mContext, "response : "+result, Toast.LENGTH_SHORT).show();


                Log.d(Constants.TAG, "App user access async task respond result   " + DateParser.getDate_and_time_newFormat());

                if(result.equals(Constants.emptyResult_volley)){
                    userType ="";
                    Toast.makeText(SplashScreen.this, "Sorry you Don't have Access !!" + Modal_AppUserAccess.getName(), Toast.LENGTH_SHORT).show();
                    intent_to_MainActivity = new Intent(SplashScreen.this, LoginActivity.class);

                }
                if (userType.toUpperCase().equals(Modal_AppUserAccess.getUsertype().toUpperCase())) {

                    if (userType.toUpperCase().equals(Constants.userType_DeliveryCenter)) {
                        try {
                            call_and_init_B2BRetailerDetailsService();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                          //  call_and_init_SupplierDetailsService();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            Initialize_and_ExecuteB2BCtgyItem();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        try {
                         //   Call_and_Initialize_GoatGradeDetails(Constants.CallGETListMethod);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        intent_to_MainActivity = new Intent(SplashScreen.this, Home_Screen.class);

                    }
                    else if (userType.toUpperCase().equals(Constants.userType_SupplierCenter)) {

                        try {
                            call_and_init_DeliveryCenterDetailsService();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            Initialize_and_ExecuteB2BCtgyItem();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        intent_to_MainActivity = new Intent(SplashScreen.this, SupplierDashboardScreen.class);

                    }
                    else {
                        //Toast.makeText(SplashScreen.this, "Your Login Access have Changed !!" + Modal_AppUserAccess.getMobileno(), Toast.LENGTH_SHORT).show();

                        signOutfromAWSandClearSharedPref();
                        intent_to_MainActivity = new Intent(SplashScreen.this, LoginActivity.class);

                    }
                }
                else{
                    Toast.makeText(SplashScreen.this, "Your Login Access have Changed !!" + Modal_AppUserAccess.getMobileno(), Toast.LENGTH_SHORT).show();

                    signOutfromAWSandClearSharedPref();
                   // i = new Intent(SplashScreen.this, LoginActivity.class);
                    return;
                }
                Log.i("INIT", "Intent Called : 1 " + DateParser.getDate_and_time_newFormat());
                runThread_to_StartIntent();

             //   Toast.makeText(SplashScreen.this, "response namee : "+Modal_AppUserAccess.getName(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void notifyError(VolleyError error) {
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
                userType = "";

                isAppUserAccessTableServiceCalled = false;
            }


        };
        if (mobileNo_String.length() == 13) {
            String UserMobileNumberEncoded = mobileNo_String;
            try {
                UserMobileNumberEncoded = URLEncoder.encode(mobileNo_String, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.d(Constants.TAG, "App user access async task executed   " + DateParser.getDate_and_time_newFormat());

            String appUserAccessApi = API_Manager.getAppUserAccessWithMobileNo + "?mobileno=" + UserMobileNumberEncoded ;
            AppUserAccess asyncTask = new AppUserAccess(callback_appUserInterface,  appUserAccessApi);
            asyncTask.execute();

        }
    }



    private void runThread_to_StartIntent() {

        new Thread() {
            public void run() {
               if(isCtgyItemDetailsApiCalled || isSupplierDetailsApiCalled || isDeliveryCenterDetailsApiCalled || isRetailerDetailsApiCalled){

                   try {
                       runOnUiThread(new Runnable() {

                           @Override
                           public void run() {
                               runThread_to_StartIntent();
                           }
                       });
                       Thread.sleep(500);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
               else{
                   Log.d(Constants.TAG, "App user access async intent   " + DateParser.getDate_and_time_newFormat());

                   intent_to_MainActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                   startActivity(intent_to_MainActivity);
                   overridePendingTransition(0, 0);
                   finish();
               }

            }
        }.start();
    }


    private void Initialize_and_ExecuteB2BCtgyItem() {
        isCtgyItemDetailsApiCalled = true;
       // showProgressBar(true);
        if (isB2BItemCtgyTableServiceCalled) {
            showProgressBar(false);

            return;
        }
        isB2BItemCtgyTableServiceCalled = true;
        callback_B2BItemCtgyInterface = new B2BItemCtgyInterface() {
            @Override
            public void notifySuccess(String result) {
              //  showProgressBar(false);
                isCtgyItemDetailsApiCalled = false;

                isB2BItemCtgyTableServiceCalled = false;
            }

            @Override
            public void notifyError(VolleyError error) {
             //   showProgressBar(false);
                isCtgyItemDetailsApiCalled = false;

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

            }

            @Override
            public void notifySuccess(String key) {

                isGoatGradeDetailsServiceCalled = false;

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isGoatGradeDetailsServiceCalled = false;
                //    Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 3 " + DateParser.getDate_and_time_newFormat());

            }

            @Override
            public void notifyProcessingError(Exception error) {
                isGoatGradeDetailsServiceCalled = false;
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());

            }


        };


        if(ApiMethod.equals(Constants.CallGETListMethod)){
            SharedPreferences sharedPreferences_Data
                    = getSharedPreferences("DeliveryCenterData",
                    MODE_PRIVATE);

            deliveryCenterKey = sharedPreferences_Data.getString("DeliveryCenterKey", "");
            String getApiToCall = API_Manager.getgoatGradeForDeliveryCentreKey +deliveryCenterKey;

            B2BGoatGradeDetails asyncTask = new B2BGoatGradeDetails(callback_goatGradeDetailsInterface,  getApiToCall, Constants.CallGETListMethod);
            asyncTask.execute();



        }



    }

    private void call_and_init_SupplierDetailsService() {

        isSupplierDetailsApiCalled = true;
        if (isSupplierDetailsTableServiceCalled) {
            return;
        }
        isSupplierDetailsTableServiceCalled = true;
        callback_supplierDetailsInterface = new SupplierDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_SupplierDetails> supplierDetailsArrayListt) {
                Log.i("INIT", "call_and_init_SupplierDetailsService: 1 " + DateParser.getDate_and_time_newFormat());
                isSupplierDetailsApiCalled = false;
            }

            @Override
            public void notifySuccess(String result) {
                Log.i("INIT", "call_and_init_SupplierDetailsService: 2 " + DateParser.getDate_and_time_newFormat());
                isSupplierDetailsApiCalled = false;
                isSupplierDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyVolleyError(VolleyError error) {

                Log.i("INIT", "call_and_init_SupplierDetailsService: 3 " + DateParser.getDate_and_time_newFormat());
                isSupplierDetailsApiCalled = false;
                isSupplierDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                Log.i("INIT", "call_and_init_SupplierDetailsService: 4 " + DateParser.getDate_and_time_newFormat());
                isSupplierDetailsApiCalled = false;
                isSupplierDetailsTableServiceCalled = false;
            }


        };

        String supplierDetailsApi = API_Manager.getsupplierDetailsList;

        SupplierDetails asyncTask = new SupplierDetails(callback_supplierDetailsInterface,  supplierDetailsApi,Constants.CallGETListMethod);
        asyncTask.execute();



    }

    private void call_and_init_B2BRetailerDetailsService() {
        isRetailerDetailsApiCalled =true;
        if (isRetailerDetailsServiceCalled) {
            //  showProgressBar(false);
            return;
        }
        isRetailerDetailsServiceCalled = true;
        callback_retailerDetailsInterface = new B2BRetailerDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList) {
                isRetailerDetailsApiCalled =false;
                isRetailerDetailsServiceCalled = false;
                Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 1 " + DateParser.getDate_and_time_newFormat());

            }

            @Override
            public void notifySuccess(String result) {
                Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 2 " + DateParser.getDate_and_time_newFormat());
                isRetailerDetailsApiCalled =false;
                isRetailerDetailsServiceCalled = false;
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isRetailerDetailsServiceCalled = false;
                isRetailerDetailsApiCalled =false;
                Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 3 " + DateParser.getDate_and_time_newFormat());

            }

            @Override
            public void notifyProcessingError(Exception error) {
                isRetailerDetailsServiceCalled = false;
                isRetailerDetailsApiCalled =false;
                Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());

            }


        };

        String getApiToCall = API_Manager.getretailerDetailsListWithDeliveryCentreKey+deliveryCenterKey  ;

        B2BRetailerDetails asyncTask = new B2BRetailerDetails(callback_retailerDetailsInterface,  getApiToCall, Constants.CallGETListMethod);
        asyncTask.execute();




    }

    private void call_and_init_DeliveryCenterDetailsService() {
        isDeliveryCenterDetailsApiCalled = true;


        if (isDeliveryCentereTableServiceCalled) {
          //  showProgressBar(false);
            return;
        }
        isDeliveryCentereTableServiceCalled = true;
        callback_DeliveryCenterDetailsInterface = new DeliveryCenterDetailsInterface() {


            @Override
            public void notifySuccess(String result) {
                Log.i("INIT", "call_and_init_DeliveryCenterDetailsService: 1 " + DateParser.getDate_and_time_newFormat());
                isDeliveryCenterDetailsApiCalled = false;
                isDeliveryCentereTableServiceCalled = false;
            }

            @Override
            public void notifyError(VolleyError error) {

                Log.i("INIT", "call_and_init_DeliveryCenterDetailsService: 2 " + DateParser.getDate_and_time_newFormat());
                isDeliveryCenterDetailsApiCalled = false;
                isDeliveryCentereTableServiceCalled = false;
            }


        };

        String getApiToCall = API_Manager.getDeliveryCenterList ;

        DeliveryCenterDetails asyncTask = new DeliveryCenterDetails(callback_DeliveryCenterDetailsInterface,  getApiToCall, Constants.CallGETListMethod);
        asyncTask.execute();


    }

    private void checkPermissionForCamera() {
        try {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {


            } else {
                ActivityCompat.requestPermissions(SplashScreen.this, new
                        String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);


            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (!isOnline()) {
                    AlertDialogClass.showDialog(SplashScreen.this, R.string.Please_Connect_To_Internet);

                } else {
                    //   AWSMobileIntialization();
                }

                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();

            } else {
                //  checkPermissionForCamera();
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == 101) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Toast.makeText(this, "Name " + Build.DEVICE, Toast.LENGTH_SHORT).show();
        } else {
            //not granted
        }
    }

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
                = getSharedPreferences("SupplierData",
                MODE_PRIVATE);

        SharedPreferences.Editor edit
                = sharedPreferences_SupplierData.edit();

        edit.putString(
                "SupplierKey", "");
        edit.putString(
                "SupplierName", "");
        edit.putString(
                "AadharCardNo", "");
        edit.putString(
                "GstNo", "");
        edit.putString(
                "Password", "");
        edit.putString(
                "PrimaryMobileNo", "");
        edit.putString(
                "SecondaryMobileNo", "");

        edit.apply();

        SharedPreferences sharedPreferences_DeliveryCentreData
                = getSharedPreferences("DeliveryCenterData",
                MODE_PRIVATE);

        SharedPreferences.Editor edit1
                = sharedPreferences_DeliveryCentreData.edit();

        edit1.putString(
                "DeliveryCenterKey", "");
        edit1.putString(
                "DeliveryCenterName", "");
        edit1.putString(
                "DeliveryCenterPassword", "");


        edit1.apply();

        Intent i = new Intent(SplashScreen.this, LoginActivity.class);
        startActivity(i);
        finish();



    }




    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}