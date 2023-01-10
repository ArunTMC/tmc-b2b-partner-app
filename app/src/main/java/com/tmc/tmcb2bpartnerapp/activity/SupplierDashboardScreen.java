package com.tmc.tmcb2bpartnerapp.activity;

import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;

import android.content.SharedPreferences;
import android.os.Bundle;

//import android.widget.Button;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.SupplierDetails;
import com.tmc.tmcb2bpartnerapp.fragment.SupplierHomeScreenFragment;
import com.tmc.tmcb2bpartnerapp.fragment.SupplierSettingsFragment;
import com.tmc.tmcb2bpartnerapp.interfaces.SupplierDetailsInterface;
import com.tmc.tmcb2bpartnerapp.model.Modal_AppUserAccess;

import com.tmc.tmcb2bpartnerapp.model.Modal_SupplierDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;

import java.util.ArrayList;

import static com.tmc.tmcb2bpartnerapp.utils.Constants.TAG;


public class SupplierDashboardScreen extends BaseActivity {
    Fragment mfragment;
    TextView supplierHome_navigationButton ,supplierSettings_navigationButton,toolBarHeader_TextView;
    private long pressedTime;
    ImageView backButton_icon;
    SupplierDetailsInterface callback_supplierDetailsInterface = null;
    boolean  isSupplierDetailsTableServiceCalled = false;
    static LinearLayout loadingPanel;
    public static LinearLayout loadingpanelmask,back_IconLayout;
    String supplierKey ="",supplierName;

    FrameLayout supplierFrame;

    @SuppressLint("UseCompatLoadingForDrawables")
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
                setContentView(R.layout.activity_supplier_dashboard_screen);
            } else {

                // Inflate the layout for this fragment
                setContentView(R.layout.pos_activity_supplier_dashboard_screen);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            setContentView(R.layout.activity_supplier_dashboard_screen);

        }


        supplierHome_navigationButton = findViewById(R.id.supplierHome_navigationButton);
        supplierSettings_navigationButton = findViewById(R.id.supplierSettings_navigationButton);
        backButton_icon = findViewById(R.id.backButton_icon);
        loadingpanelmask =  findViewById(R.id.loadingpanelmask);
        loadingPanel =  findViewById(R.id.loadingPanel);
        back_IconLayout = findViewById(R.id.back_IconLayout);
        toolBarHeader_TextView  =  findViewById(R.id.toolBarHeader_TextView);
        supplierFrame = findViewById(R.id.supplierFrame);



        SharedPreferences sh = getSharedPreferences("SupplierData",MODE_PRIVATE);
        supplierKey = sh.getString("SupplierKey","");
        supplierName = sh.getString("SupplierName","");
        toolBarHeader_TextView.setText(supplierName);
        Log.d("Tag","onCreate Dashboard");





        if(!Modal_AppUserAccess.getSupplierkey().equals("")){
            showProgressBar(true);
            String supplierKeyFromAppAccess = Modal_AppUserAccess.getSupplierkey();
            call_and_init_SupplierDetailsService(supplierKeyFromAppAccess);

        }
        else{
            showProgressBar(true);
            call_and_init_SupplierDetailsService(supplierKey);
        }



        supplierSettings_navigationButton.setOnClickListener(view -> {
            try{
                if(SupplierHomeScreenFragment.createNewBatchFrame.getVisibility() == View.VISIBLE) {
                    Toast.makeText(SupplierDashboardScreen.this , "Please close the 'Create New Batch Dialog Box' " , Toast.LENGTH_SHORT).show();

                }
                else{
                    supplierSettings_navigationButton.setBackground(getDrawable(R.drawable.grey_navigation_bar_button));
                    supplierHome_navigationButton.setBackground(getDrawable(R.drawable.white_navigation_bar_button));

                    mfragment = new SupplierSettingsFragment();
                    loadMyFragment(mfragment);
                }
                }
            catch (Exception e) {
                e.printStackTrace();

                supplierSettings_navigationButton.setBackground(getDrawable(R.drawable.grey_navigation_bar_button));
                supplierHome_navigationButton.setBackground(getDrawable(R.drawable.white_navigation_bar_button));

                mfragment = new SupplierSettingsFragment();
                loadMyFragment(mfragment);
            }
        });


        supplierHome_navigationButton.setOnClickListener(view -> {
            try{
                if(SupplierHomeScreenFragment.createNewBatchFrame.getVisibility() == View.VISIBLE) {
                    Toast.makeText(SupplierDashboardScreen.this , "Please close the 'Create New Batch Dialog Box' " , Toast.LENGTH_SHORT).show();

                }
                else{
                    supplierSettings_navigationButton.setBackground(getDrawable(R.drawable.white_navigation_bar_button));
                    supplierHome_navigationButton.setBackground(getDrawable(R.drawable.grey_navigation_bar_button));

                    mfragment = new SupplierHomeScreenFragment();
                    loadMyFragment(mfragment);
                }
            }
            catch (Exception e) {
                e.printStackTrace();

                supplierSettings_navigationButton.setBackground(getDrawable(R.drawable.white_navigation_bar_button));
                supplierHome_navigationButton.setBackground(getDrawable(R.drawable.grey_navigation_bar_button));

                mfragment = new SupplierHomeScreenFragment();
                loadMyFragment(mfragment);
            }




        });



        back_IconLayout.setOnClickListener(view -> {
            onBackPressed();
        });


    }

    /*  @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Tag","On Start Dashboard");

        supplierSettings_navigationButton.setBackground(getDrawable(R.drawable.white_navigation_bar_button));
        supplierHome_navigationButton.setBackground(getDrawable(R.drawable.grey_navigation_bar_button));
        mfragment = new SupplierHomeScreenFragment();
        loadMyFragment(mfragment);
    }

   */

    /*
     @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onRestart() {
        Log.d("Tag","On Restart Dashboard");
        super.onRestart();
        supplierSettings_navigationButton.setBackground(getDrawable(R.drawable.white_navigation_bar_button));
        supplierHome_navigationButton.setBackground(getDrawable(R.drawable.grey_navigation_bar_button));
        mfragment = new SupplierHomeScreenFragment();
        loadMyFragment(mfragment);
    }

     */

    private void call_and_init_SupplierDetailsService(String supplierKey) {


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
                //showProgressBar(false);
                saveSupplierDataInSharedPreference();

                supplierSettings_navigationButton.setBackground(getDrawable(R.drawable.white_navigation_bar_button));
                supplierHome_navigationButton.setBackground(getDrawable(R.drawable.grey_navigation_bar_button));
                mfragment = new SupplierHomeScreenFragment();
                loadMyFragment(mfragment);
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
              //  showProgressBar(false);

                isSupplierDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                Log.d(TAG, "Volley JSON post" + error);
              //  showProgressBar(false);
                isSupplierDetailsTableServiceCalled = false;
            }


        };

        String supplierDetailsApi = API_Manager.getsupplierDetailsWithSupplierKey + supplierKey;

        SupplierDetails asyncTask = new SupplierDetails(callback_supplierDetailsInterface,  supplierDetailsApi, Constants.CallGETMethod);
        asyncTask.execute();


    }

    private void saveSupplierDataInSharedPreference() {

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


    private void loadMyFragment(Fragment fm) {
        if (fm != null) {
            try {
              /*  FragmentTransaction transaction2 = getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null);
                transaction2 .replace(R.id.supplierFrame,  fm);

                transaction2.commit();


               */



                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.supplierFrame, fm)

                        .addToBackStack(null)
                        .commit();

            } catch (Exception e) {
                onResume();
                e.printStackTrace();
            }

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
    public void onBackPressed() {

        try{
            if(SupplierHomeScreenFragment.createNewBatchFrame.getVisibility() == View.VISIBLE){
                SupplierHomeScreenFragment.createNewBatchFrame.setVisibility(View.GONE);
                SupplierHomeScreenFragment.lastBatchLayout.setVisibility(View.VISIBLE);
            }
            else{
                if (pressedTime + 2000 > System.currentTimeMillis()) {
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
                }
                pressedTime = System.currentTimeMillis();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            if (pressedTime + 2000 > System.currentTimeMillis()) {
                finish();
            } else {
                Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            pressedTime = System.currentTimeMillis();
        }


        }




      /*new TMCAlertDialogClass(this, R.string.app_name, R.string.Exit_Instruction,
                R.string.Yes_Text, R.string.No_Text,
                new TMCAlertDialogClass.AlertListener() {
                    @Override
                    public void onYes() {
                        //Intent intent = new Intent(Intent.ACTION_MAIN);
                       // intent.addCategory(Intent.CATEGORY_HOME);
                       // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      //  startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onNo() {

                    }
                });
                }
*/






}