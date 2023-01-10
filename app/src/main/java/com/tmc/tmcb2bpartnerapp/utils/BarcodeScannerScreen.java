package com.tmc.tmcb2bpartnerapp.utils;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.GoatEarTagItemDetailsList;
import com.tmc.tmcb2bpartnerapp.activity.View_or_Edit_BatchItem_Supplier;
import com.tmc.tmcb2bpartnerapp.activity.View_or_Edit_BatchItem_deliveryCenter;
import com.tmc.tmcb2bpartnerapp.fragment.BatchItemDetailsDeliveryCenterScreenfragment;
import com.tmc.tmcb2bpartnerapp.fragment.BatchItemDetailsScreenFragment;
import com.tmc.tmcb2bpartnerapp.fragment.DeliveryCentre_PlaceOrderScreen_Fragment;
import com.tmc.tmcb2bpartnerapp.fragment.SwitchEarTag_Fragment_UnscannedItems;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class BarcodeScannerScreen extends BaseActivity {
    private SurfaceView surfaceView;
    private CameraSource cameraSource;

    private static final int REQUEST_CAMERA_PERMISSION = 201;
    //This class provides methods to play DTMF tones
    private ToneGenerator toneGen1;
    private TextView barcodeText;
    private String barcodeData;
    ImageView barcodescannerIcon;
    boolean isItemAdded = false;
    SparseArray<Barcode> barcodes = new SparseArray<>();
    String scannercalledToDo ="",calledFrom="";
    boolean isMobileDevice = false;
    ConstraintLayout parentBarcodeScanner;
    Dialog show_scan_barcode_dialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner_screenn);
        parentBarcodeScanner = findViewById(R.id.parentBarcodeScanner);

        Intent intent = getIntent();
        scannercalledToDo = intent.getStringExtra(String.valueOf(getString(R.string.scanner_called_to_do)));
        calledFrom = intent.getStringExtra(String.valueOf(getString(R.string.called_from)));

        try {
            BaseActivity.baseActivity.getDeviceName();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            if (BaseActivity.isDeviceIsMobilePhone) {
                // Inflate the layout for this fragment
                isMobileDevice = true;
                parentBarcodeScanner.setVisibility(View.VISIBLE);
                setupEnvironment_and_initialiseDetectorsAndSources();
            } else {
                isMobileDevice = false;
                parentBarcodeScanner.setVisibility(View.GONE);
                showScanBarcodeDialog();
                // Inflate the layout for this fragment
            }
        }
        catch (Exception e){
            e.printStackTrace();
            parentBarcodeScanner.setVisibility(View.GONE);
            showScanBarcodeDialog();
            isMobileDevice = false;

        }



    }
    private void showScanBarcodeDialog() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {

                    show_scan_barcode_dialog = new Dialog(BarcodeScannerScreen.this);
                    show_scan_barcode_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    //  dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    show_scan_barcode_dialog.setContentView(R.layout.show_scan_barcode_activity);

                    
                    show_scan_barcode_dialog.show();
                    EditText scannedbarcode_EditText = show_scan_barcode_dialog.findViewById(R.id.scannedbarcode_EditText);
                    EditTextListener editTextListener = new EditTextListener();
                    scannedbarcode_EditText.addTextChangedListener(editTextListener);

                } catch (WindowManager.BadTokenException e) {
                    

                    e.printStackTrace();
                }
            }
        });





    }







    public class EditTextListener implements TextWatcher {
        Timer timer = new Timer();
        final long DELAY = 1000;
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
             barcodeData = (editable.toString());
            if(barcodeData.contains("\n")){
                barcodeData.replace("\n","");
            }
            Log.i("tag1 barcodeData",barcodeData);
            if(barcodeData.length() > 3) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                        if (barcodeData.length() <= 6) {

                                            if (barcodeData.length() == 4) {


                                                if (Character.isLetter(barcodeData.charAt(0)) && Character.isLetter(barcodeData.charAt(1))) {
                                                    String barcodeSubString = barcodeData.substring(2, 4);
                                                    for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                        if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                            throwAlertMsg();
                                                            //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                            //onRestart();

                                                            return;
                                                        } else {
                                                            if (iterator - (barcodeSubString.length() - 1) == 0) {

                                                                sendResponsebackToActivity(barcodeData);

                                                                return;
                                                            }
                                                        }
                                                    }

                                                } else if (Character.isLetter(barcodeData.charAt(0))) {
                                                    String barcodeSubString = barcodeData.substring(1, 4);
                                                    for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                        if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                            // Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                            //onRestart();
                                                            throwAlertMsg();
                                                            return;
                                                        } else {
                                                            if (iterator - (barcodeSubString.length() - 1) == 0) {


                                                                sendResponsebackToActivity(barcodeData);
                                                                return;
                                                            }
                                                        }
                                                    }

                                                } else {
                                                    //  Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();

                                                    //onRestart();
                                                    throwAlertMsg();
                                                }
                                            } else if (barcodeData.length() == 5) {
                                                if (Character.isLetter(barcodeData.charAt(0)) && Character.isLetter(barcodeData.charAt(1))) {
                                                    String barcodeSubString = barcodeData.substring(2, 5);
                                                    for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                        if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                            throwAlertMsg();
                                                            //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                            //onRestart();

                                                            return;
                                                        } else {
                                                            if (iterator - (barcodeSubString.length() - 1) == 0) {


                                                                sendResponsebackToActivity(barcodeData);
                                                                return;
                                                            }
                                                        }
                                                    }

                                                } else if (Character.isLetter(barcodeData.charAt(0))) {
                                                    String barcodeSubString = barcodeData.substring(1, 5);
                                                    for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                        if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                            //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                            // onRestart();
                                                            throwAlertMsg();
                                                            return;
                                                        } else {
                                                            if (iterator - (barcodeSubString.length() - 1) == 0) {

                                                                sendResponsebackToActivity(barcodeData);
                                                                return;
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    // Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();

                                                    // onRestart();
                                                    throwAlertMsg();
                                                }
                                            } else if (barcodeData.length() == 6) {
                                                if (Character.isLetter(barcodeData.charAt(0)) && Character.isLetter(barcodeData.charAt(1))) {
                                                    String barcodeSubString = barcodeData.substring(2, 6);
                                                    for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                        if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                            throwAlertMsg();
                                                            //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                            //onRestart();

                                                            return;
                                                        } else {
                                                            if (iterator - (barcodeSubString.length() - 1) == 0) {

                                                                sendResponsebackToActivity(barcodeData);
                                                                return;
                                                            }
                                                        }
                                                    }

                                                } else if (Character.isLetter(barcodeData.charAt(0))) {
                                                    String barcodeSubString = barcodeData.substring(1, 6);
                                                    for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                        if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                            //  Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                            // onRestart();
                                                            throwAlertMsg();
                                                            return;
                                                        } else {
                                                            if (iterator - (barcodeSubString.length() - 1) == 0) {
                                                                sendResponsebackToActivity(barcodeData);
                                                                return;
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                    //onRestart();
                                                    throwAlertMsg();
                                                }
                                            } else {
                                                //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                //onRestart();
                                                throwAlertMsg();
                                            }
                                        }
                                        else {
                                            throwAlertMsg();
                                        }

                                    }
                                });


                            }
                        },
                        DELAY
                );


            }
        }
    }


    private void setupEnvironment_and_initialiseDetectorsAndSources() {


        isItemAdded = false;
        barcodes = new SparseArray<>();
        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC,     100);
        final ImageView imageView1 = (ImageView) findViewById(R.id.barcodescannerIcon);



        surfaceView = findViewById(R.id.surface_view);
        barcodeText = findViewById(R.id.barcode_text);

        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        surfaceView = findViewById(R.id.surface_view);
        barcodeText = findViewById(R.id.barcode_text);

        try {
            imageView1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scanner_bar_animation));

        }
        catch (Exception e){
            e.printStackTrace();
        }



        initialiseDetectorsAndSources();


    }


    private void initialiseDetectorsAndSources() {

        //Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
        try{
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();


        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                try {
                    if (ActivityCompat.checkSelfPermission(BarcodeScannerScreen.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(BarcodeScannerScreen.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                // Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                if (barcodes.size() == 0) {
                    barcodes = detections.getDetectedItems();
                    if (barcodes.size() != 0) {
                        Log.e("TAG", "receiveDetections  1    " + barcodes);


                        barcodeText.post(() -> {
                            try {
                                if (barcodes.valueAt(0).email != null) {
                                    barcodeText.removeCallbacks(null);
                                    barcodeData = barcodes.valueAt(0).email.address;
                                    //barcodeText.setText(barcodeData);
                                    toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                                    // Toast.makeText(BarcodeScannerScreen.this, barcodeData, Toast.LENGTH_SHORT).show();

                                    if(barcodeData.length() <= 6) {
                                        if (barcodeData.length() == 4) {
                                            if (Character.isLetter(barcodeData.charAt(0)) && Character.isLetter(barcodeData.charAt(1))) {
                                                String barcodeSubString = barcodeData.substring(2, 4);
                                                for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                    if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                        throwAlertMsg();
                                                        //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                        //onRestart();

                                                        return;
                                                    } else {
                                                        if (iterator - (barcodeSubString.length() - 1) == 0) {

                                                            sendResponsebackToActivity(barcodeData);
                                                            return;
                                                        }
                                                    }
                                                }

                                            } else if (Character.isLetter(barcodeData.charAt(0))) {
                                                String barcodeSubString = barcodeData.substring(1, 4);
                                                for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                    if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                        throwAlertMsg();
                                                        //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                        //onRestart();

                                                        return;
                                                    } else {
                                                        if (iterator - (barcodeSubString.length() - 1) == 0) {

                                                            sendResponsebackToActivity(barcodeData);
                                                            return;
                                                        }
                                                    }
                                                }

                                            } else {
                                                //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                throwAlertMsg();
                                                //onRestart();
                                            }
                                        } else if (barcodeData.length() == 5) {
                                            if (Character.isLetter(barcodeData.charAt(0)) && Character.isLetter(barcodeData.charAt(1))) {
                                                String barcodeSubString = barcodeData.substring(2, 5);
                                                for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                    if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                        throwAlertMsg();
                                                        //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                        //onRestart();

                                                        return;
                                                    } else {
                                                        if (iterator - (barcodeSubString.length() - 1) == 0) {

                                                            sendResponsebackToActivity(barcodeData);
                                                            return;
                                                        }
                                                    }
                                                }

                                            } else if (Character.isLetter(barcodeData.charAt(0))) {
                                                String barcodeSubString = barcodeData.substring(1, 5);
                                                for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                    if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                        // Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                        //  onRestart();
                                                        throwAlertMsg();
                                                        return;
                                                    } else {
                                                        if (iterator - (barcodeSubString.length() - 1) == 0) {
                                                            sendResponsebackToActivity(barcodeData);
                                                            return;
                                                        }
                                                    }
                                                }
                                            } else {
                                                //  Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();

                                                // onRestart();
                                                throwAlertMsg();
                                            }
                                        } else if (barcodeData.length() == 6) {

                                            if (Character.isLetter(barcodeData.charAt(0)) && Character.isLetter(barcodeData.charAt(1))) {
                                                String barcodeSubString = barcodeData.substring(2, 6);
                                                for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                    if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                        throwAlertMsg();
                                                        //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                        //onRestart();

                                                        return;
                                                    } else {
                                                        if (iterator - (barcodeSubString.length() - 1) == 0) {

                                                            sendResponsebackToActivity(barcodeData);
                                                            return;
                                                        }
                                                    }
                                                }

                                            } else if (Character.isLetter(barcodeData.charAt(0))) {
                                                String barcodeSubString = barcodeData.substring(1, 6);
                                                for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                    if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                        // Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                        //onRestart();
                                                        throwAlertMsg();
                                                        return;
                                                    } else {
                                                        if (iterator - (barcodeSubString.length() - 1) == 0) {
                                                            sendResponsebackToActivity(barcodeData);
                                                            return;
                                                        }
                                                    }
                                                }
                                            } else {
                                                //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                //onRestart();
                                                throwAlertMsg();
                                            }
                                        } else {
                                            //   Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                            //  onRestart();
                                            throwAlertMsg();
                                        }
                                    }
                                    else{
                                        throwAlertMsg();
                                    }

                                  /*  if(calledFrom.equals(getString(R.string.supplier))) {
                                        if (scannercalledToDo.equals(getString(R.string.scannerCalled_to_FetchData))) {
                                            BatchItemDetailsScreenFragment.barcodeScannerInterface.notifySuccessAndFetchData(barcodeData);
                                            finish();
                                        } else {
                                            BatchItemDetailsScreenFragment.barcodeScannerInterface.notifySuccess(barcodeData);
                                            finish();
                                        }
                                    }
                                    else  if(calledFrom.equals(getString(R.string.delivery_center))) {
                                        BatchItemDetailsDeliveryCenterScreenfragment.barcodeScannerInterface.notifySuccessAndFetchData(barcodeData);
                                        finish();
                                    }
                                    else  if(calledFrom.equals(getString(R.string.audit_unstocked_batch_item))) {
                                        SwitchEarTag_Fragment_UnscannedItems.barcodeScannerInterface.notifySuccessAndFetchData(barcodeData);
                                        finish();
                                    }
                                    else  if(calledFrom.equals(getString(R.string.supplier_goatItemList))) {
                                        GoatEarTagItemDetailsList.barcodeScannerInterface.notifySuccess(barcodeData);
                                        finish();
                                    }
                                    else  if(calledFrom.equals(getString(R.string.view_edit_batch_item_deliveryCenter))) {
                                        View_or_Edit_BatchItem_deliveryCenter.barcodeScannerInterface.notifySuccessAndFetchData(barcodeData);
                                        finish();
                                    }
                                    else  if(calledFrom.equals(getString(R.string.view_edit_batch_item_supplier))) {
                                            View_or_Edit_BatchItem_Supplier.barcodeScannerInterface.notifySuccessAndFetchData(barcodeData);

                                        finish();
                                    }
                                    finish();

                                   */
                                } else {


                                    barcodeData = barcodes.valueAt(0).displayValue;
                                    barcodeText.setText(barcodeData);
                                    toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                                    // Toast.makeText(BarcodeScannerScreen.this, barcodeData, Toast.LENGTH_SHORT).show();
                                   /* if(calledFrom.equals(getString(R.string.supplier))) {

                                        if (scannercalledToDo.equals(getString(R.string.scannerCalled_to_FetchData))) {
                                            BatchItemDetailsScreenFragment.barcodeScannerInterface.notifySuccessAndFetchData(barcodeData);
                                            finish();
                                        } else {
                                            BatchItemDetailsScreenFragment.barcodeScannerInterface.notifySuccess(barcodeData);
                                            finish();
                                        }
                                    }
                                    else  if(calledFrom.equals(getString(R.string.delivery_center))) {
                                        BatchItemDetailsDeliveryCenterScreenfragment.barcodeScannerInterface.notifySuccessAndFetchData(barcodeData);
                                        finish();
                                    }
                                    else  if(calledFrom.equals(getString(R.string.audit_unstocked_batch_item))) {
                                        SwitchEarTag_Fragment_UnscannedItems.barcodeScannerInterface.notifySuccessAndFetchData(barcodeData);
                                        finish();
                                    }
                                    else  if(calledFrom.equals(getString(R.string.supplier_goatItemList))) {
                                        GoatEarTagItemDetailsList.barcodeScannerInterface.notifySuccess(barcodeData);
                                        finish();
                                    }
                                    else  if(calledFrom.equals(getString(R.string.view_edit_batch_item_deliveryCenter))) {
                                        View_or_Edit_BatchItem_deliveryCenter.barcodeScannerInterface.notifySuccessAndFetchData(barcodeData);
                                        finish();
                                    }
                                    else  if(calledFrom.equals(getString(R.string.view_edit_batch_item_supplier))) {
                                        View_or_Edit_BatchItem_Supplier.barcodeScannerInterface.notifySuccessAndFetchData(barcodeData);

                                        finish();
                                    }

                                    */


                                    if(barcodeData.length() <= 6) {

                                        if (barcodeData.length() == 4) {


                                        if (Character.isLetter(barcodeData.charAt(0)) && Character.isLetter(barcodeData.charAt(1))) {
                                            String barcodeSubString = barcodeData.substring(2, 4);
                                            for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                    throwAlertMsg();
                                                    //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                    //onRestart();

                                                    return;
                                                } else {
                                                    if (iterator - (barcodeSubString.length() - 1) == 0) {

                                                        sendResponsebackToActivity(barcodeData);
                                                        return;
                                                    }
                                                }
                                            }

                                        }
                                        else if (Character.isLetter(barcodeData.charAt(0))) {
                                            String barcodeSubString = barcodeData.substring(1, 4);
                                            for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                   // Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                    //onRestart();
                                                    throwAlertMsg();
                                                    return;
                                                } else {
                                                    if (iterator - (barcodeSubString.length() - 1) == 0) {

                                                        sendResponsebackToActivity(barcodeData);
                                                        return;
                                                    }
                                                }
                                            }

                                        } else {
                                          //  Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();

                                            //onRestart();
                                            throwAlertMsg();
                                        }
                                    }
                                    else if (barcodeData.length() == 5) {
                                        if (Character.isLetter(barcodeData.charAt(0)) && Character.isLetter(barcodeData.charAt(1))) {
                                            String barcodeSubString = barcodeData.substring(2, 5);
                                            for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                    throwAlertMsg();
                                                    //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                    //onRestart();

                                                    return;
                                                } else {
                                                    if (iterator - (barcodeSubString.length() - 1) == 0) {

                                                        sendResponsebackToActivity(barcodeData);
                                                        return;
                                                    }
                                                }
                                            }

                                        }
                                        else if (Character.isLetter(barcodeData.charAt(0))) {
                                            String barcodeSubString = barcodeData.substring(1, 5);
                                            for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                    //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                   // onRestart();
                                                    throwAlertMsg();
                                                    return;
                                                } else {
                                                    if (iterator - (barcodeSubString.length() - 1) == 0) {
                                                        sendResponsebackToActivity(barcodeData);
                                                        return;
                                                    }
                                                }
                                            }
                                        } else {
                                           // Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();

                                           // onRestart();
                                            throwAlertMsg();
                                        }
                                    }
                                    else if (barcodeData.length() == 6) {
                                        if (Character.isLetter(barcodeData.charAt(0)) && Character.isLetter(barcodeData.charAt(1))) {
                                            String barcodeSubString = barcodeData.substring(2, 6);
                                            for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                    throwAlertMsg();
                                                    //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                    //onRestart();

                                                    return;
                                                } else {
                                                    if (iterator - (barcodeSubString.length() - 1) == 0) {

                                                        sendResponsebackToActivity(barcodeData);
                                                        return;
                                                    }
                                                }
                                            }

                                        }
                                        else if (Character.isLetter(barcodeData.charAt(0))) {
                                            String barcodeSubString = barcodeData.substring(1, 6);
                                            for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                                if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                  //  Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                   // onRestart();
                                                    throwAlertMsg();
                                                    return;
                                                } else {
                                                    if (iterator - (barcodeSubString.length() - 1) == 0) {
                                                        sendResponsebackToActivity(barcodeData);
                                                        return;
                                                    }
                                                }
                                            }
                                        } else {
                                            //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                            //onRestart();
                                            throwAlertMsg();
                                        }
                                    }
                                    else {
                                        //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                        //onRestart();
                                        throwAlertMsg();
                                    }
                                    }
                                    else{
                                        throwAlertMsg();
                                    }
                                    //finish();

                                }
                            } catch (Exception error) {
                               /*if(calledFrom.equals(getString(R.string.supplier))) {

                                    BatchItemDetailsScreenFragment.barcodeScannerInterface.notifyProcessingError(error);
                                    finish();
                                }
                                else  if(calledFrom.equals(getString(R.string.delivery_center))) {
                                    BatchItemDetailsDeliveryCenterScreenfragment.barcodeScannerInterface.notifyProcessingError(error);
                                    finish();
                                }
                                else  if(calledFrom.equals(getString(R.string.audit_unstocked_batch_item))) {
                                    SwitchEarTag_Fragment_UnscannedItems.barcodeScannerInterface.notifySuccessAndFetchData(barcodeData);
                                    finish();
                                }
                                else  if(calledFrom.equals(getString(R.string.supplier_goatItemList))) {
                                    GoatEarTagItemDetailsList.barcodeScannerInterface.notifySuccess(barcodeData);
                                    finish();
                                }
                                else  if(calledFrom.equals(getString(R.string.view_edit_batch_item_deliveryCenter))) {
                                    View_or_Edit_BatchItem_deliveryCenter.barcodeScannerInterface.notifySuccessAndFetchData(barcodeData);
                                    finish();
                                }
                                else  if(calledFrom.equals(getString(R.string.view_edit_batch_item_supplier))) {
                                    View_or_Edit_BatchItem_Supplier.barcodeScannerInterface.notifySuccessAndFetchData(barcodeData);

                                    finish();
                                }

                                */
                                if(barcodeData.length() <= 6) {

                                if (barcodeData.length() == 4) {
                                    if (Character.isLetter(barcodeData.charAt(0)) && Character.isLetter(barcodeData.charAt(1))) {
                                        String barcodeSubString = barcodeData.substring(2, 4);
                                        for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                            if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                throwAlertMsg();
                                                //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                //onRestart();

                                                return;
                                            } else {
                                                if (iterator - (barcodeSubString.length() - 1) == 0) {

                                                    sendResponsebackToActivity(barcodeData);
                                                    return;
                                                }
                                            }
                                        }

                                    }
                                    else if (Character.isLetter(barcodeData.charAt(0))) {
                                        String barcodeSubString = barcodeData.substring(1, 4);
                                        for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                            if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                               // Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                             //   onRestart();
                                                throwAlertMsg();
                                                return;
                                            } else {
                                                if (iterator - (barcodeSubString.length() - 1) == 0) {

                                                    sendResponsebackToActivity(barcodeData);
                                                    return;
                                                }
                                            }
                                        }

                                    }
                                    else {
                                        //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                       // onRestart();
                                        throwAlertMsg();
                                    }
                                }
                                else if (barcodeData.length() == 5) {
                                    if (Character.isLetter(barcodeData.charAt(0)) && Character.isLetter(barcodeData.charAt(1))) {
                                        String barcodeSubString = barcodeData.substring(2, 5);
                                        for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                            if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                throwAlertMsg();
                                                //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                //onRestart();

                                                return;
                                            } else {
                                                if (iterator - (barcodeSubString.length() - 1) == 0) {

                                                    sendResponsebackToActivity(barcodeData);
                                                    return;
                                                }
                                            }
                                        }

                                    }
                                    else if (Character.isLetter(barcodeData.charAt(0))) {
                                        String barcodeSubString = barcodeData.substring(1, 5);
                                        for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                            if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                               // onRestart();
                                                throwAlertMsg();
                                                return;
                                            } else {
                                                if (iterator - (barcodeSubString.length() - 1) == 0) {
                                                    sendResponsebackToActivity(barcodeData);
                                                    return;
                                                }
                                            }
                                        }
                                    } else {
                                        //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                       // onRestart();
                                        throwAlertMsg();
                                    }
                                }
                                else if (barcodeData.length() == 6) {
                                    if (Character.isLetter(barcodeData.charAt(0)) && Character.isLetter(barcodeData.charAt(1))) {
                                        String barcodeSubString = barcodeData.substring(2, 6);
                                        for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                            if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                throwAlertMsg();
                                                //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                //onRestart();

                                                return;
                                            } else {
                                                if (iterator - (barcodeSubString.length() - 1) == 0) {

                                                    sendResponsebackToActivity(barcodeData);
                                                    return;
                                                }
                                            }
                                        }

                                    }
                                    else if (Character.isLetter(barcodeData.charAt(0))) {
                                        String barcodeSubString = barcodeData.substring(1, 6);
                                        for (int iterator = 0; iterator < barcodeSubString.length(); iterator++) {
                                            if (barcodeSubString.charAt(iterator) < '0' || barcodeSubString.charAt(iterator) > '9') {
                                                //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                                //onRestart();
                                                throwAlertMsg();
                                                return;
                                            } else {
                                                if (iterator - (barcodeSubString.length() - 1) == 0) {
                                                    sendResponsebackToActivity(barcodeData);
                                                    return;
                                                }
                                            }
                                        }
                                    } else {
                                        //Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                        //onRestart();
                                        throwAlertMsg();
                                    }
                                }
                                else {
                                   // Toast.makeText(BarcodeScannerScreen.this, "Please Scan Again", Toast.LENGTH_SHORT).show();
                                   // onRestart();
                                    throwAlertMsg();
                                }
                                }
                                else{
                                    throwAlertMsg();
                                }
                                error.printStackTrace();
                            }

                        });
                    }
                }
            }
        });
    }
    catch(Exception e){
        throwAlertMsg();
        e.printStackTrace();
    }
}

    private void throwAlertMsg() {

        new TMCAlertDialogClass(BarcodeScannerScreen.this, R.string.app_name, R.string.ScanAgain_Instruction,
                R.string.OK_Text, R.string.Empty_Text,
                new TMCAlertDialogClass.AlertListener() {
                    @Override
                    public void onYes() {
                        onRestart();

                    }

                    @Override
                    public void onNo() {

                    }
                });


    }

    private void sendResponsebackToActivity(String barcodeData) {


        if(calledFrom.equals(getString(R.string.supplier))) {
            if (scannercalledToDo.equals(getString(R.string.scannerCalled_to_FetchData))) {
                BatchItemDetailsScreenFragment.barcodeScannerInterface.notifySuccessAndFetchData(barcodeData);
                finish();
            } else {
                BatchItemDetailsScreenFragment.barcodeScannerInterface.notifySuccess(barcodeData);
                finish();
            }
        }
        else  if(calledFrom.equals(getString(R.string.delivery_center))) {
            BatchItemDetailsDeliveryCenterScreenfragment.barcodeScannerInterface.notifySuccessAndFetchData(barcodeData);
            finish();
        }
        else  if(calledFrom.equals(getString(R.string.audit_unstocked_batch_item))) {
            SwitchEarTag_Fragment_UnscannedItems.barcodeScannerInterface.notifySuccessAndFetchData(barcodeData);
            finish();
        }
        else  if(calledFrom.equals(getString(R.string.supplier_goatItemList))) {
            GoatEarTagItemDetailsList.barcodeScannerInterface.notifySuccess(barcodeData);
            finish();
        }
        else  if(calledFrom.equals(getString(R.string.view_edit_batch_item_deliveryCenter))) {
            View_or_Edit_BatchItem_deliveryCenter.barcodeScannerInterface.notifySuccessAndFetchData(barcodeData);
            finish();
        }
        else  if(calledFrom.equals(getString(R.string.view_edit_batch_item_supplier))) {
            View_or_Edit_BatchItem_Supplier.barcodeScannerInterface.notifySuccessAndFetchData(barcodeData);

            finish();
        }
        else  if(calledFrom.equals(getString(R.string.billing_Screen))) {
           // BillingScreen.barcodeScannerInterface.notifySuccessAndFetchData(barcodeData);
            try {
                DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.barcodeScannerInterface.notifySuccessAndFetchData(barcodeData);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finish();
        }
        finish();


    }

    @Override
    protected void onPause() {
        super.onPause();
//        getSupportActionBar().hide();
        try {
            BaseActivity.baseActivity.getDeviceName();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            if (BaseActivity.isDeviceIsMobilePhone) {
                // Inflate the layout for this fragment
                isMobileDevice = true;
                cameraSource.release();

            } else {
                isMobileDevice = false;


                // Inflate the layout for this fragment
            }
        }
        catch (Exception e){
            e.printStackTrace();


            isMobileDevice = false;

        }

    }

    @Override
    protected void onResume() {
        super. onResume();
//        getSupportActionBar().hide();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            BaseActivity.baseActivity.getDeviceName();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            if (BaseActivity.isDeviceIsMobilePhone) {
                // Inflate the layout for this fragment
                isMobileDevice = true;
                parentBarcodeScanner.setVisibility(View.VISIBLE);
                setupEnvironment_and_initialiseDetectorsAndSources();
            } else {
                isMobileDevice = false;
                parentBarcodeScanner.setVisibility(View.GONE);
                showScanBarcodeDialog();
                // Inflate the layout for this fragment
            }
        }
        catch (Exception e){
            e.printStackTrace();
            parentBarcodeScanner.setVisibility(View.GONE);
            showScanBarcodeDialog();
            isMobileDevice = false;

        }
    }

}