package com.tmc.tmcb2bpartnerapp.activity;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.DeliveryCenterDetails;
import com.tmc.tmcb2bpartnerapp.fragment.DeliveryCenterHomeScreenFragment;
import com.tmc.tmcb2bpartnerapp.fragment.DeliveryCenterSettingsFragment;
import com.tmc.tmcb2bpartnerapp.fragment.DeliveryCentre_PlaceOrderScreen_Fragment;
import com.tmc.tmcb2bpartnerapp.interfaces.DeliveryCenterDetailsInterface;
import com.tmc.tmcb2bpartnerapp.model.Modal_DeliveryCenterDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;

import static com.tmc.tmcb2bpartnerapp.utils.Constants.TAG;

public class DeliveryCenterDashboardScreen extends BaseActivity {
TextView toolBarHeader_TextView;
TextView deliveryCenterHome_navigationButton;
TextView deliveryCenterSettings_navigationButton;
TextView deliveryCenterPlaceOrder_navigationButton;
FrameLayout deliveryCenterFrame;
ImageView backButton_icon;
LinearLayout loadingpanelmask,loadingPanel,back_IconLayout;
Fragment mfragment;
public  static LinearLayout deliveryCenterFrame_backgroundMask;
    //Boolean variable to mark if there is any transaction pending
    private boolean isTransactionPending;
    private boolean isTransactionSafe;
    private long pressedTime;
    String deliveryCenterKey="",deliveryCenterName="";
    DeliveryCenterDetailsInterface callback_DeliveryCenterDetailsInterface = null;
    boolean  isDeliveryCentereTableServiceCalled = false;
    String value_forFragment = "";
    public static CardView navigationBar_cardView;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDeviceName();

        if(isDeviceIsMobilePhone){
            setContentView(R.layout.activity_delivery_center_dashboard_screen);
        }
        else{

            setContentView(R.layout.pos_activity_delivery_center_dashboard_screen);
        }


        toolBarHeader_TextView =findViewById(R.id.toolBarHeader_TextView);
        deliveryCenterHome_navigationButton =findViewById(R.id.deliveryCenterHome_navigationButton);
        deliveryCenterSettings_navigationButton =findViewById(R.id.deliveryCenterSettings_navigationButton);
        deliveryCenterPlaceOrder_navigationButton = findViewById(R.id.deliveryCenterPlaceOrder_navigationButton);
        deliveryCenterFrame = findViewById(R.id.deliveryCenterFrame);
        backButton_icon = findViewById(R.id.backButton_icon);
        loadingpanelmask =  findViewById(R.id.loadingpanelmask);
        loadingPanel =  findViewById(R.id.loadingPanel);
        back_IconLayout =  findViewById(R.id.back_IconLayout);
        navigationBar_cardView   = findViewById(R.id.navigationBar_cardView);
        deliveryCenterFrame_backgroundMask = findViewById(R.id.deliveryCenterFrame_backgroundMask);
        back_IconLayout.clearFocus();
        SharedPreferences sh = getSharedPreferences("DeliveryCenterData",MODE_PRIVATE);
        deliveryCenterKey = sh.getString("DeliveryCenterKey","");
        deliveryCenterName = sh.getString("DeliveryCenterName","");
        toolBarHeader_TextView.setText(deliveryCenterName);
        mfragment = new DeliveryCenterHomeScreenFragment();
        value_forFragment = getString(R.string.home);
        loadMyFragment();

       /* if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
        }
        else{
            Toast.makeText(this, "Name " + Build.MODEL, Toast.LENGTH_SHORT).show();

            toolBarHeader_TextView.setText(" Device " + Build.DEVICE + " Model " + Build.MODEL+ " BRAND " + Build.BRAND + " DISPLAY " + Build.DISPLAY+ " MANUFACTURER " + Build.MANUFACTURER+ " PRODUCT " + Build.PRODUCT+ " TYPE " + Build.TYPE+ " TYPE " + Build.TYPE);

        }

        */


        //showProgressBar(true);
       // call_and_init_DeliveryCenterService(deliveryCenterKey);


        deliveryCenterPlaceOrder_navigationButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                deliveryCenterSettings_navigationButton.setBackground(DeliveryCenterDashboardScreen.this.getDrawable(R.drawable.white_navigation_bar_button));
                deliveryCenterHome_navigationButton.setBackground(DeliveryCenterDashboardScreen.this.getDrawable(R.drawable.white_navigation_bar_button));
                deliveryCenterPlaceOrder_navigationButton.setBackground(DeliveryCenterDashboardScreen.this.getDrawable(R.drawable.grey_navigation_bar_button));
                mfragment = new DeliveryCentre_PlaceOrderScreen_Fragment();
                value_forFragment = DeliveryCenterDashboardScreen.this.getString(R.string.placeOrder);
                DeliveryCenterDashboardScreen.this.loadMyFragment();

                //    Intent intent = new Intent(getApplicationContext(), BillingScreen.class);
                //   intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                //    overridePendingTransition(0,0);
                //   startActivity(intent);

                return false;
            }
        });


        deliveryCenterSettings_navigationButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                deliveryCenterSettings_navigationButton.setBackground(DeliveryCenterDashboardScreen.this.getDrawable(R.drawable.grey_navigation_bar_button));
                deliveryCenterHome_navigationButton.setBackground(DeliveryCenterDashboardScreen.this.getDrawable(R.drawable.white_navigation_bar_button));
                deliveryCenterPlaceOrder_navigationButton.setBackground(DeliveryCenterDashboardScreen.this.getDrawable(R.drawable.white_navigation_bar_button));

                mfragment = new DeliveryCenterSettingsFragment();
                value_forFragment = DeliveryCenterDashboardScreen.this.getString(R.string.settings);
                DeliveryCenterDashboardScreen.this.loadMyFragment();
                return false;
            }
        });


        deliveryCenterHome_navigationButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                deliveryCenterSettings_navigationButton.setBackground(DeliveryCenterDashboardScreen.this.getDrawable(R.drawable.white_navigation_bar_button));
                deliveryCenterHome_navigationButton.setBackground(DeliveryCenterDashboardScreen.this.getDrawable(R.drawable.grey_navigation_bar_button));
                deliveryCenterPlaceOrder_navigationButton.setBackground(DeliveryCenterDashboardScreen.this.getDrawable(R.drawable.white_navigation_bar_button));

                mfragment = new DeliveryCenterHomeScreenFragment();
                value_forFragment = DeliveryCenterDashboardScreen.this.getString(R.string.home);
                DeliveryCenterDashboardScreen.this.loadMyFragment();
                return false;
            }
        });



        back_IconLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                DeliveryCenterDashboardScreen.this.onBackPressed();
                return false;
            }
        });

    }


    private void call_and_init_DeliveryCenterService(String selectDeliveryCenterKey) {


        if (isDeliveryCentereTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isDeliveryCentereTableServiceCalled = true;
        callback_DeliveryCenterDetailsInterface = new DeliveryCenterDetailsInterface() {


            @Override
            public void notifySuccess(String result) {

                deliveryCenterKey = Modal_DeliveryCenterDetails.deliverycenterkey_static;
                deliveryCenterName = Modal_DeliveryCenterDetails.name_static;
                saveDatainSharedPrefrences();
            }

            @Override
            public void notifyError(VolleyError error) {
                Log.d(TAG, "Volley JSON post" + "That didn't work!");

                showProgressBar(false);
                isDeliveryCentereTableServiceCalled = false;


            }


        };

        String getApiToCall = API_Manager.getDeliveryCenterWithKey+"?deliverycentrekey="+deliveryCenterKey ;

        DeliveryCenterDetails asyncTask = new DeliveryCenterDetails(callback_DeliveryCenterDetailsInterface,  getApiToCall, Constants.CallGETMethod);
        asyncTask.execute();





    }

    private void saveDatainSharedPrefrences() {


        SharedPreferences sharedPreferences_SupplierData
                = getSharedPreferences("DeliveryCenterData",
                MODE_PRIVATE);

        SharedPreferences.Editor edit
                = sharedPreferences_SupplierData.edit();

        edit.putString(
                "DeliveryCenterKey", deliveryCenterKey);
        edit.putString(
                "DeliveryCenterName", deliveryCenterName);

        edit.apply();
    }


    private void loadMyFragment() {

            try {
              /*  FragmentTransaction transaction2 = getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null);
                transaction2 .replace(R.id.supplierFrame,  fm);

                transaction2.commit();


               */

               if(isTransactionSafe) {
                    isTransactionPending=false;
                    if (mfragment != null) {

                        try {

                            FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction()
                                    .addToBackStack(null);
                            if(value_forFragment.equals(getString(R.string.placeOrder))){
                                transaction2.replace(deliveryCenterFrame.getId(), DeliveryCentre_PlaceOrderScreen_Fragment.newInstance(getString(R.string.called_from), value_forFragment));

                            }
                            else if(value_forFragment.equals(getString(R.string.home))){
                                transaction2.replace(deliveryCenterFrame.getId(), DeliveryCenterHomeScreenFragment.newInstance(getString(R.string.called_from), value_forFragment));

                            }
                        else{
                            transaction2.replace(deliveryCenterFrame.getId(), DeliveryCenterSettingsFragment.newInstance(getString(R.string.called_from), value_forFragment));

                            }

                            transaction2.remove(mfragment).commit();




                        } catch (Exception e) {
                            onResume();
                            loadMyFragment();
                            e.printStackTrace();
                        }


                    }
                }else {

                    isTransactionPending=true;
                }
/*
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.deliveryCenterFrame, fm)

                        .addToBackStack(null)
                        .commit();


 */
            } catch (Exception e) {
                onResume();
                e.printStackTrace();
            }


    }


    public  void showProgressBar(boolean show) {

        if(show){
            loadingPanel.setVisibility(View.GONE);
            loadingpanelmask.setVisibility(View.VISIBLE);

        }
        else{
            loadingPanel.setVisibility(View.GONE);
            loadingpanelmask.setVisibility(View.GONE);

        }


    }



    public void onPause(){
        super.onPause();
        isTransactionSafe=false;
        try{
            DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.isTransactionSafe = false;

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public void onPostResume(){
        super.onPostResume();
        isTransactionSafe=true;
        try{
            if(value_forFragment.equals(getString(R.string.placeOrder))) {
                DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.isTransactionSafe = true;
                if(DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.isTransactionPending)
                {
                    DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.loadMyFragment();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


            /* Here after the activity is restored we check if there is any transaction pending from
            the last restoration
            */
        if (isTransactionPending) {
            loadMyFragment();
        }
    }


    @Override
    public void onBackPressed() {

        if(value_forFragment.equals(getString(R.string.placeOrder))) {


            if (DeliveryCentre_PlaceOrderScreen_Fragment.frameLayout_for_Fragment.getVisibility() == View.VISIBLE) {
                DeliveryCentre_PlaceOrderScreen_Fragment.frameLayout_for_Fragment.setVisibility(View.GONE);
                deliveryCenterFrame_backgroundMask.setVisibility(View.GONE);
                if (DeliveryCentre_PlaceOrderScreen_Fragment.isorderSummary_checkoutClicked) {

                    DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.neutralizeArray_and_OtherValues();
                    DeliveryCentre_PlaceOrderScreen_Fragment.isorderSummary_checkoutClicked = false;
                    return;


                } else {
                    return;
                }

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
        else {


            if (pressedTime + 2000 > System.currentTimeMillis()) {
                finish();
            } else {
                Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            pressedTime = System.currentTimeMillis();

        }
    }




}