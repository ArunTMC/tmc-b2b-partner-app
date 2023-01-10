package com.tmc.tmcb2bpartnerapp.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.SplashScreen;

public class BaseActivity extends AppCompatActivity {
    private boolean activityfinished = false;
    private BaseUtil settingsUtil;
    public static boolean  isAdding_Or_UpdatingEntriesInDB_Service = false ;
    public static boolean  isDeviceIsMobilePhone = false ;
    public static BaseActivity baseActivity ;
    private boolean isDeviceOnLandscape = false;
    private double DeviceResolutionLevel = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        baseActivity = this;
        settingsUtil = new BaseUtil(this);
        getDeviceName();

    }


    @SuppressLint("ShowToast")
    public  String getDeviceName() {
        int height = 0 ;int width =0;
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            if (showNavigationBar(Resources.getSystem())) {
                height = displayMetrics.heightPixels + getNavigationBarHeight();

            } else {
                height = displayMetrics.heightPixels;
            }

             width = displayMetrics.widthPixels;

            if(width>height){
                isDeviceOnLandscape = true;
            }
            else{
                isDeviceOnLandscape = false;
            }

            float densityValue =  displayMetrics.density;

            if(0<=densityValue && densityValue <0.5){
                DeviceResolutionLevel = 0.5;

            }
            else if(0.5<=densityValue && densityValue <1){
                DeviceResolutionLevel = 1;
            }
            else if(1<=densityValue && densityValue <1.5){
                DeviceResolutionLevel = 1.5;
            }
            else if(1.5<=densityValue && densityValue <2){
                DeviceResolutionLevel = 2;
            }
            else if(2<=densityValue && densityValue <2.5){
                DeviceResolutionLevel = 2.5;
            }
            else if(2.5<=densityValue && densityValue <3){
                DeviceResolutionLevel = 3;
            }
            else if(3<=densityValue && densityValue <3.5){
                DeviceResolutionLevel = 3.5;
            }
            else if(3.5<=densityValue && densityValue <4){
                DeviceResolutionLevel = 4;
            }
            else if(0>densityValue){
                DeviceResolutionLevel = -1;
            }
            else{
                DeviceResolutionLevel = 10;
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

          //  Toast.makeText(this, "Name " + android.os.Build.MODEL, Toast.LENGTH_SHORT).show();
            Log.d(Constants.TAG, "Device Name " + android.os.Build.MODEL);

            if(String.valueOf(android.os.Build.MODEL).equals(getString(R.string.pos_device_emulator_name))){
                if(isDeviceOnLandscape){
                    isDeviceIsMobilePhone = false;
                }
                else{
                    isDeviceIsMobilePhone = true;
                }

            }
            else if(String.valueOf(android.os.Build.MODEL).equals(getString(R.string.pos_device_name))){
                if(isDeviceOnLandscape){
                    isDeviceIsMobilePhone = false;
                }
                else{
                    isDeviceIsMobilePhone = true;
                }
            }
            else{

                if(isDeviceOnLandscape){
                    if(DeviceResolutionLevel == 0 || DeviceResolutionLevel == 0.5){
                        if(width>=800){
                            isDeviceIsMobilePhone = false;
                        }
                        else{
                            isDeviceIsMobilePhone = true;
                        }
                    }
                    else if(DeviceResolutionLevel == 1 ){
                        if(width>=900){
                            isDeviceIsMobilePhone = false;
                        }
                        else{
                            isDeviceIsMobilePhone = true;
                        }
                    }
                    else if(DeviceResolutionLevel == 1.5 ){
                        if(width>=1100){
                            isDeviceIsMobilePhone = false;
                        }
                        else{
                            isDeviceIsMobilePhone = true;
                        }
                    }
                    else if(DeviceResolutionLevel == 2 ){
                        if(width>=1600){
                            isDeviceIsMobilePhone = false;
                        }
                        else{
                            isDeviceIsMobilePhone = true;
                        }
                    }
                    else if(DeviceResolutionLevel == 2.5 ){
                        if(width>=2200){
                            isDeviceIsMobilePhone = false;
                        }
                        else{
                            isDeviceIsMobilePhone = true;
                        }
                    }
                    else if(DeviceResolutionLevel == 3 ){
                        if(width>=2800){
                            isDeviceIsMobilePhone = false;
                        }
                        else{
                            isDeviceIsMobilePhone = true;
                        }
                    }
                    else if(DeviceResolutionLevel == 3.5 ){
                        if(width>=3300){
                            isDeviceIsMobilePhone = false;
                        }
                        else{
                            isDeviceIsMobilePhone = true;
                        }
                    }
                    else if(DeviceResolutionLevel == 4 ){
                        if(width>=4000){
                            isDeviceIsMobilePhone = false;
                        }
                        else{
                            isDeviceIsMobilePhone = true;
                        }
                    }
                    else if(DeviceResolutionLevel == -1){
                        isDeviceIsMobilePhone = true;
                    }
                }
                else{
                    isDeviceIsMobilePhone = true;
                }
            }







        return "";
    }

    public boolean showNavigationBar(Resources resources)
    {
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        return id > 0 && resources.getBoolean(id);
    }


    @SuppressLint("ObsoleteSdkInt")
    private int getNavigationBarHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        }
        return 0;
    }



    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (settingsUtil == null) {
            settingsUtil = new BaseUtil(this);
        }
        if (!activityfinished) {
            settingsUtil.setLastAccessedTime(System.currentTimeMillis());
        } else {
            settingsUtil.setLastAccessedTime(0);
        }
    }

    @Override
    public void finish() {
        super.finish();
        activityfinished = true;
        overridePendingTransition(0, 0);
    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, SplashScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private static final int APP_IDLE_TIME = 150000;

    @Override
    protected void onResume() {
        super.onResume();
        if (settingsUtil == null) {
            settingsUtil = new BaseUtil(this);
        }
        long lastAccessedTime = settingsUtil.getLastAccessedTime();
        long idletime = System.currentTimeMillis() - lastAccessedTime;
        long appidletime = APP_IDLE_TIME;


        if ((lastAccessedTime > 0) && (idletime > appidletime)) {
            //Log.d("BaseActivity", "cart and menu items cleared");
            // TMCMenuItemCatalog.getInstance().clearCartTMCMenuItems();
            //TMCMenuItemCatalog.getInstance().clear();
            startHomeActivity();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

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





}