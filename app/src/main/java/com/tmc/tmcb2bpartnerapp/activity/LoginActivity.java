package com.tmc.tmcb2bpartnerapp.activity;

import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserState;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.results.SignInResult;
import com.amazonaws.mobile.client.results.SignUpResult;
import com.amazonaws.mobile.client.results.UserCodeDeliveryDetails;
import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.AppUserAccess;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BPartnerAppUser;
import com.tmc.tmcb2bpartnerapp.interfaces.AppUserAccessInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BPartnerAppUserInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_AppUserAccess;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BPartnerAppUser;
import com.tmc.tmcb2bpartnerapp.second_version.activity.Home_Screen;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.NukeSSLCerts;
import com.tmc.tmcb2bpartnerapp.utils.TMCAlertDialogClass;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.tmc.tmcb2bpartnerapp.utils.Constants.TAG;

public class LoginActivity extends BaseActivity {
CardView otpCardView,mobileNoCardView;
Button sendOtpbutton,verifyOtpButton;
EditText mobileNumber_editText;
String mobileNo_String , userType =Constants.userType_SupplierCenter ,userName ="",passCode;
boolean loginStatusBoolean;
LinearLayout loadingPanel , loadingpanelmask,back_IconLayout;
EditText edOtp1, edOtp2, edOtp3, edOtp4, edOtp5, edOtp6;
AppUserAccessInterface callback_appUserInterface = null;
B2BPartnerAppUserInterface callback_partnerAppUsersInterface = null ;
boolean  isAppUserAccessTableServiceCalled = false ,isDeveloperMode = false , isB2BTMCUserTableSericeCalled =false;
TextView mobileNumber_textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeviceName();

        new NukeSSLCerts();
        NukeSSLCerts.nuke();
        if(isDeviceIsMobilePhone){
            setContentView(R.layout.activity_login);
        }
        else{

            setContentView(R.layout.pos_activity_login);
        }



        verifyOtpButton = findViewById(R.id.verifyOtpButton);
        sendOtpbutton = findViewById(R.id.sendOtpbutton);
        mobileNoCardView = findViewById(R.id.mobileNoCardView);
        otpCardView = findViewById(R.id.otpCardView);
        mobileNumber_editText = findViewById(R.id.mobileNumber_editText);
        loadingpanelmask =  findViewById(R.id.loadingpanelmask);
        loadingPanel =  findViewById(R.id.loadingPanel);
        mobileNumber_textView =  findViewById(R.id.mobileNumber_textView);
        back_IconLayout =  findViewById(R.id.back_IconLayout);


        edOtp1 = findViewById(R.id.otp_first_et);
        edOtp2 = findViewById(R.id.otp_second_et);
        edOtp3 = findViewById(R.id.otp_third_et);
        edOtp4 = findViewById(R.id.otp_fourth_et);
        edOtp6 = findViewById(R.id.otp_sixth_et);
        edOtp5 = findViewById(R.id.otp_fifth_et);

        edOtp1.addTextChangedListener(new GenericTextWatcher(edOtp1));
        edOtp2.addTextChangedListener(new GenericTextWatcher(edOtp2));
        edOtp3.addTextChangedListener(new GenericTextWatcher(edOtp3));
        edOtp4.addTextChangedListener(new GenericTextWatcher(edOtp4));
        edOtp5.addTextChangedListener(new GenericTextWatcher(edOtp5));
        edOtp6.addTextChangedListener(new GenericTextWatcher(edOtp6));
        back_IconLayout.setVisibility(View.GONE);





        sendOtpbutton.setOnClickListener(view -> {


            mobileNo_String = mobileNumber_editText.getText().toString().trim();
            if(mobileNo_String .length() == 10) {
                mobileNo_String = "+91" + mobileNo_String;
                showProgressBar(true);


                try{
                    call_and_init_AppUserAccessService();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
               // mobileNoCardView.setVisibility(View.GONE);
              //  otpCardView.setVisibility(View.VISIBLE);
              //  loadingPanel.setVisibility(View.INVISIBLE);
             //   loadingpanelmask.setVisibility(View.INVISIBLE);

            }
            else{

                showProgressBar(false);
                AlertDialogClass.showDialog(LoginActivity.this,R.string.Enter_Mobileno_Instruction);
            }




        });

        verifyOtpButton.setOnClickListener(view -> {
            if(isDeveloperMode){
                if(mobileNo_String.equals("+919597580128") || mobileNo_String.equals("+917010779096")) {
                    saveUserDetails();
                    Intent intent = new Intent(LoginActivity.this, PasswordVerificationScreen.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();

                }
                else{
                    AlertDialogClass.showDialog(LoginActivity.this, R.string.LimitExceed_Please_Try_again_afterSometime);
                }
            }
            else{
                if ((edOtp1.getText().toString().length() != 0) && (edOtp2.getText().toString().length() != 0) && (edOtp3.getText().toString().length() != 0) && (edOtp4.getText().toString().length() != 0) && (edOtp5.getText().toString().length() != 0) && (edOtp6.getText().toString().length() != 0)) {
                    passCode = edOtp1.getText().toString().trim() + edOtp2.getText().toString().trim() + edOtp3.getText().toString().trim() + edOtp4.getText().toString().trim() + edOtp5.getText().toString().trim() + edOtp6.getText().toString().trim();
                    showProgressBar(true);

                    verifyotp();
                }
                else{
                    AlertDialogClass.showDialog(LoginActivity.this, R.string.Enter_Otp_Instruction);

                }
            }


            /*

            Intent intent = new Intent(LoginActivity.this,PasswordVerificationScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
            overridePendingTransition(0,0);
        */

        });


        back_IconLayout.setOnClickListener(view -> {
            onBackPressed();
        });

        loadingpanelmask.setOnClickListener(view -> {

        });





    }




    @Override
    protected void onStart() {
        super.onStart();
        AWSMobileIntialization();
    }

    private void AWSMobileIntialization() {
        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

                    @Override
                    public void onResult(UserStateDetails userStateDetails) {
                        //Log.i("INIT", "onResult: " + userStateDetails.getUserState());
                        if(userStateDetails.getUserState() == UserState.SIGNED_IN){

                            SharedPreferences sh
                                    = getSharedPreferences("LoginData",
                                    MODE_PRIVATE);

                            loginStatusBoolean = sh.getBoolean("LoginStatus",false);
                            userType = sh.getString("UserType","");

                            //Log.i("Tag","VendorLoginStatus"+vendorLoginStatusBoolean);
                            runOnUiThread(() -> {

                                Intent i = null;
                                if (loginStatusBoolean) {
                                    showProgressBar(true);
                                    Modal_AppUserAccess.usertype = userType;
                                    if(userType.toUpperCase().equals(Constants.userType_DeliveryCenter) ){
                                        i = new Intent(LoginActivity.this, Home_Screen.class);

                                    }
                                    else if(userType.toUpperCase().equals(Constants.userType_SupplierCenter)) {


                                        i = new Intent(LoginActivity.this, SupplierDashboardScreen.class);

                                    }
                                    else{
                                        return;
                                    }
                                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    overridePendingTransition(0,0);
                                    startActivity(i);
                                    finish();

                                }

                            });}
                        else{
                            Log.i("INIT", "onResult: " + userStateDetails.getUserState());

                        }
                    }

                    @Override
                    public void onError(Exception e) {

                        Log.e("INIT", "Initialization error.", e);
                    }
                }
        );
    }



    private void StartSignUp(final String mobileNo_String) {

        final Map<String, String> attributes = new HashMap<>();
        attributes.put("phone_number", mobileNo_String);
        attributes.put("name", "");
        attributes.put("email", "");

        AWSMobileClient.getInstance().signUp(mobileNo_String, "password", attributes, null, new Callback<SignUpResult>() {
            @Override
            public void onResult(final SignUpResult signUpResult) {
                runOnUiThread(() -> {
                    //Log.d(TAG, "Sign-up callback state: " + signUpResult.getConfirmationState());
                    if (!signUpResult.getConfirmationState()) {
                       final UserCodeDeliveryDetails details = signUpResult.getUserCodeDeliveryDetails();
                        Log.d(TAG, "Sign-up callback state: " + details.getDestination());
                    } else {

                        StartSignIN(mobileNo_String);
                        //Log.d(TAG, "Sign-up  " );
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    //AlertDialogClass.showDialog(Login.this, R.string.Enter_Correct_No);
                    StartSignIN(mobileNo_String);
                });
                Log.e(TAG, "Sign-up error", e);
            }
        });

    }



    private void StartSignIN(final String mobileNo_String) {
        try{
          //  call_and_init_AppUserAccessService();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        AWSMobileClient.getInstance().signIn(mobileNo_String, "password", null, new Callback<SignInResult>() {

            @Override
            public void onResult(final SignInResult signInResult) {
                runOnUiThread(() -> {
                    //Log.d(TAG, "UserName"+mobileNo_String);

                    //Log.d(TAG, "Log-in callback state: " + signInResult.getSignInState());
                    switch (signInResult.getSignInState()) {
                        case DONE:
                            Log.d(TAG,"Log-in done.");
                           /* saveUserDetails();
                            Intent intent = new Intent(LoginActivity.this,PasswordVerificationScreen.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(0,0);


                            */
                            back_IconLayout.setVisibility(View.VISIBLE);
                            mobileNoCardView.setVisibility(View.GONE);
                            otpCardView.setVisibility(View.VISIBLE);
                            mobileNumber_textView.setText("Developer Mode");
                            isDeveloperMode =true;
                            showProgressBar(false);

                            //Log.d(TAG,"Email: " + signInResult.getCodeDetails());
                            break;
                        case SMS_MFA:
                            //Log.d(TAG,"Please confirm sign-in with SMS.");
                            break;
                        case NEW_PASSWORD_REQUIRED:
                            //Log.d(TAG,"Please confirm sign-in with new password.");
                            break;
                        case CUSTOM_CHALLENGE:
                            isDeveloperMode =false;
                            back_IconLayout.setVisibility(View.VISIBLE);
                            mobileNoCardView.setVisibility(View.GONE);
                            otpCardView.setVisibility(View.VISIBLE);
                            mobileNumber_textView.setText(mobileNo_String);
                            showProgressBar(false);



                            break;
                        default:

                            //Log.d(TAG,"Unsupported Log-in confirmation: " + signInResult.getSignInState());
                            break;
                    }
                });
            }

            @Override
            public void onError(final Exception e) {
                //Log.e(TAG, "Log-in error", e);
            }
        });


    }

    private void call_and_init_AppUserAccessService() {


        if (isAppUserAccessTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isAppUserAccessTableServiceCalled = true;
        callback_appUserInterface = new AppUserAccessInterface() {


            @Override
            public void notifySuccess(String result) {
                isAppUserAccessTableServiceCalled = false;
           //     Toast.makeText(mContext, "response : "+result, Toast.LENGTH_SHORT).show();
                if(result.equals(Constants.emptyResult_volley)){
                    showProgressBar(false);
                    userType =""; userName ="";
                    Toast.makeText(LoginActivity.this, "Sorry you Don't have Access !!" , Toast.LENGTH_SHORT).show();

                }
                else {
                    if(Modal_AppUserAccess.getUsertype().equals(Constants.userType_SupplierCenter) || Modal_AppUserAccess.getUsertype().equals(Constants.userType_DeliveryCenter)){
                        userType = Modal_AppUserAccess.getUsertype();
                        userName = Modal_AppUserAccess.getName();
                        StartSignUp(mobileNo_String);

                    }
                    else{
                        showProgressBar(false);
                        userType =""; userName ="";
                        Toast.makeText(LoginActivity.this, "Sorry you Don't have Access !!" , Toast.LENGTH_SHORT).show();

                    }

                    //  Toast.makeText(LoginActivity.this, "response namee : " + Modal_AppUserAccess.getName(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void notifyError(VolleyError error) {
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
                showProgressBar(false);
                userType ="";
                userName ="";
                Toast.makeText(LoginActivity.this, "Sorry you Don't have Access !!" + Modal_AppUserAccess.getName(), Toast.LENGTH_SHORT).show();

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
            String appUserAccessApi = API_Manager.getAppUserAccessWithMobileNo + "?mobileno=" + UserMobileNumberEncoded ;

            AppUserAccess asyncTask = new AppUserAccess(callback_appUserInterface,  appUserAccessApi);
            asyncTask.execute();

        }
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


    private void verifyotp() {
        final Map<String, String> attributes = new HashMap<>();
        attributes.put("ANSWER", passCode);
        //Log.d(TAG, "Si " + passCode);

        AWSMobileClient.getInstance().confirmSignIn(attributes, new Callback<SignInResult>() {
            @Override
            public void onResult(SignInResult signInResult) {
                //Log.d(TAG, "UserName " + userMobileString + "  password " + passCode);

                //Log.d(TAG, "Log-in  state: " + signInResult.getSignInState());
                switch (signInResult.getSignInState()) {
                    case DONE:
                        //Log.d(TAG, "Log-in done");




                        Add_or_updateB2BUserDetails();
                        saveUserDetails();
                        Intent intent = new Intent(LoginActivity.this,PasswordVerificationScreen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0,0);


                        break;
                    case SMS_MFA:
                        //Log.d(TAG, "Please confirm sign-in with SMS.");
                        break;
                    case NEW_PASSWORD_REQUIRED:
                        //Log.d(TAG, "Please confirm sign-in with new password.");
                        break;
                    case CUSTOM_CHALLENGE:
                        runOnUiThread(new Runnable() {
                            public void run() {
                                showProgressBar(false);
                                AlertDialogClass.showDialog(LoginActivity.this, R.string.Enter_Otp_Instruction);

                            }
                        });
                         //Log.d(TAG, " Custom challenge.");
                        break;
                    default:
                        //Log.d(TAG, "Unsupported Log-in confirmation: " + signInResult.getSignInState().toString());
                        break;
                }
            }

            @Override
            public void onError(final Exception e) {
                showProgressBar(false);

                AlertDialogClass.showDialog(LoginActivity.this, R.string.NetworkErrorPlease_Try_again_afterSometime);

                //Log.d(TAG, "UserNamexx" + userMobileString + "  passwordvv" + passCode);

                //Log.e(TAG, "Log-in error", e);
            }
        });
    }

    private void Add_or_updateB2BUserDetails() {



        if (isB2BTMCUserTableSericeCalled) {
            showProgressBar(false);
            return;
        }
        isB2BTMCUserTableSericeCalled = true;
        callback_partnerAppUsersInterface = new B2BPartnerAppUserInterface() {


            @Override
            public void notifySuccess(String result) {
                //     Toast.makeText(mContext, "response : "+result, Toast.LENGTH_SHORT).show();
                if(result.equals(Constants.emptyResult_volley)){
                    showProgressBar(false);
                    userType ="";
                    userName ="";
                    Toast.makeText(LoginActivity.this, "Sorry you Don't have Access !!" + Modal_AppUserAccess.getName(), Toast.LENGTH_SHORT).show();

                }
                else {
                    userType = Modal_AppUserAccess.getUsertype();
                    userName = Modal_AppUserAccess.getName();
                    //  Toast.makeText(LoginActivity.this, "response namee : " + Modal_AppUserAccess.getName(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void notifyVolleyError(VolleyError error) {

            }

            @Override
            public void notifyProcessingError(Exception error) {
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
                showProgressBar(false);
                userType =Constants.userType_SupplierCenter;
                userName ="";
                isAppUserAccessTableServiceCalled = false;
            }




        };
            Modal_B2BPartnerAppUser.setMobileno(mobileNo_String);
             Modal_B2BPartnerAppUser.setUsertype(userType);
             Modal_B2BPartnerAppUser.setUpdatedtime(DateParser.getDate_and_time_newFormat());
             Modal_B2BPartnerAppUser.setCreatedtime(DateParser.getDate_and_time_newFormat());
            Modal_B2BPartnerAppUser.setAppversion(getString(R.string.Version_No));

        String userTableApi = API_Manager.check_or_Update_B2BUserDetails;

            B2BPartnerAppUser asyncTask = new B2BPartnerAppUser(callback_partnerAppUsersInterface,  userTableApi, Constants.CallUPDATEMethod);
            asyncTask.execute();



        
    }


    class GenericTextWatcher implements TextWatcher {
        private final View view;

        private GenericTextWatcher(View view) {
            this.view = view;
            //Log.i("Tag", "ONGeneric Text Watcher");
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();

            //Log.i("Tag", "ONAfterTextChanged");
            switch (view.getId()) {

                case R.id.otp_first_et:

                    if (text.length() == 2) {
                        edOtp1.setText(String.valueOf(text.charAt(0)));
                        edOtp2.setText(String.valueOf(text.charAt(1)));
                        edOtp2.requestFocus();
                        edOtp2.setSelection(edOtp2.getText().length());
                    } else if (text.length() == 6) {
                        edOtp1.setText(String.valueOf(text.charAt(0)));
                        edOtp2.setText(String.valueOf(text.charAt(1)));
                        edOtp3.setText(String.valueOf(text.charAt(2)));
                        edOtp4.setText(String.valueOf(text.charAt(3)));
                        edOtp5.setText(String.valueOf(text.charAt(4)));
                        edOtp6.setText(String.valueOf(text.charAt(5)));

                        edOtp6.requestFocus();
                        edOtp6.setSelection(edOtp3.getText().length());
                    }

                    break;
                case R.id.otp_second_et:

                    if (text.length() > 1) {
                        edOtp2.setText(String.valueOf(text.charAt(0)));
                        edOtp3.setText(String.valueOf(text.charAt(1)));
                        edOtp3.requestFocus();
                        edOtp3.setSelection(edOtp3.getText().length());
                    }
                    if (text.length() == 0) {
                        edOtp1.requestFocus();
                        edOtp1.setSelection(edOtp1.getText().length());
                    }
                    break;
                case R.id.otp_third_et:

                    if (text.length() > 1) {
                        edOtp3.setText(String.valueOf(text.charAt(0)));
                        edOtp4.setText(String.valueOf(text.charAt(1)));
                        edOtp4.requestFocus();
                        edOtp4.setSelection(edOtp4.getText().length());
                    }
                    if (text.length() == 0) {
                        edOtp2.requestFocus();
                        edOtp2.setSelection(edOtp2.getText().length());
                    }
                    break;

                case R.id.otp_fourth_et:

                    if (text.length() > 1) {
                        edOtp4.setText(String.valueOf(text.charAt(0)));
                        edOtp5.setText(String.valueOf(text.charAt(1)));
                        edOtp5.requestFocus();
                        edOtp5.setSelection(edOtp5.getText().length());
                    }
                    if (text.length() == 0) {
                        edOtp3.requestFocus();
                        edOtp3.setSelection(edOtp3.getText().length());
                    }
                    break;

                case R.id.otp_fifth_et:

                    if (text.length() > 1) {
                        edOtp5.setText(String.valueOf(text.charAt(0)));
                        edOtp6.setText(String.valueOf(text.charAt(1)));
                        edOtp6.requestFocus();
                        edOtp6.setSelection(edOtp6.getText().length());
                    }
                    if (text.length() == 0) {
                        edOtp4.requestFocus();
                        edOtp4.setSelection(edOtp4.getText().length());
                    }
                    break;

                case R.id.otp_sixth_et:
                    if (text.length() == 0) {
                        edOtp5.requestFocus();
                        edOtp5.setSelection(edOtp5.getText().length());
                    }
                    break;

            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            //Log.i("Tag", "ONbeforeTextChanged");
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            //Log.i("Tag", "ONTextChanged");
        }
    }

    private void saveUserDetails() {


        SharedPreferences sharedPreferences
                = getSharedPreferences("LoginData",
                MODE_PRIVATE);

        SharedPreferences.Editor myEdit
                = sharedPreferences.edit();

        myEdit.putString(
                "UserMobileNumber", mobileNo_String
        );

        myEdit.putString(
                "UserName", userName
        );


        myEdit.putString(
                "UserType", userType
        );
        myEdit.putString(
                "SupplierKey", Modal_AppUserAccess.supplierkey
        );


        myEdit.apply();

    }

    @Override
    public void onBackPressed() {
        if (otpCardView.getVisibility() == View.VISIBLE) {
            otpCardView.setVisibility(View.GONE);
            mobileNoCardView.setVisibility(View.VISIBLE);
            back_IconLayout.setVisibility(View.GONE);
        }
        else {
            new TMCAlertDialogClass(this, R.string.app_name, R.string.Exit_Instruction,
                    R.string.Yes_Text, R.string.No_Text,
                    new TMCAlertDialogClass.AlertListener() {
                        @Override
                        public void onYes() {
                            finish();
                        }

                        @Override
                        public void onNo() {

                        }
                    });
        }

    }




}