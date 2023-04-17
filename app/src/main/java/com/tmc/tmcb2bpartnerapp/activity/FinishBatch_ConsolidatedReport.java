package com.tmc.tmcb2bpartnerapp.activity;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.adapter.Adapter_BatchWise_ConsolidatedReport;
import com.tmc.tmcb2bpartnerapp.adapter.adapterHelpers.ListData;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.fragment.DeliveryCenterHomeScreenFragment;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetailsStatic;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetailsUpdate;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.ListItemSizeSetter;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;
import static org.apache.poi.hssf.record.ExtendedFormatRecord.CENTER;
import static org.apache.poi.hssf.record.ExtendedFormatRecord.LEFT;


public class FinishBatch_ConsolidatedReport extends BaseActivity {
    GoatEarTagDetailsInterface callback_GoatEarTagDetails = null;
    LinearLayout loadingPanel , loadingpanelmask,count_loadingAnim_layout,back_IconLayout,goatSoldtotal_layout,
            goatDeadtotal_layout,goatLosttotal_layout,earTagtotalwise_layout , goatSicktotal_Layout ,generate_reportlayout;
    TextView deliveryCenterName_textview ,batchno_textview,itemCount_textview,itemWeight_textview,supplierName_textview,
            goatDead_weight_textview,goatDead_Qty_textview,goatLost_Weight_textview,goatLost_Qty_textview,earTag_Qty_textview,
            batchno_textview_deliveryCenter,itemCount_textview_deliveryCenter,itemunscannedCount_textview_deliveryCenter,
            scannedItems_textview_deliveryCenter,goatSick_weight_textview,goatsickQty_textview,goatSold_weight_textview,
            goatSold_Qty_textview , male_Qty_textview ,male_weight_textview,female_Qty_textview,female_weight_textview,unscanneditem_label,scanneditem_label
            ,femalewithbaby_Qty_textview,femalewithbaby_weight_textview,report_genderwise_report_instru_textview, mark_batch_as_sold_button;
    ScrollView scrollView;
    ListView batchwiseConsolidatedReport_listview;
    Button finishBatch_Button,share_excelSheet_report,share_reportButton;
    B2BBatchDetailsInterface callback_B2BBatchDetailsInterface = null;
    CardView deliveryCenterLogin_Cardview,supplierLogin_Cardview,final_statuswise_totalValueCardView;


    Workbook wb;
    Sheet sheet = null;
    DecimalFormat decimalFormat = new DecimalFormat(Constants.threeDecimalPattern);
    private static String[] columnsHeading_Supplier = {"S.No", "Breed Type", "Gender", " Quantity", "Weight"};
    private static int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;


    boolean isGoatEarTagDetailsTableServiceCalled;
    boolean  isBatchDetailsTableServiceCalled = false;
    ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch = new ArrayList<>();
    ArrayList<Modal_GoatEarTagDetails> goatDead_Status_earTagItem = new ArrayList<>();
    ArrayList<Modal_GoatEarTagDetails> goatLost_Status_earTagItem = new ArrayList<>();
    ArrayList<Modal_GoatEarTagDetails> goatSick_Status_earTagItem = new ArrayList<>();
    ArrayList<Modal_GoatEarTagDetails> goatSold_Status_earTagItem = new ArrayList<>();
    ArrayList<Modal_GoatEarTagDetails> goatearTagLost_Status_earTagItem = new ArrayList<>();
    HashMap<String, JSONObject> breedWise_ItemHashmap = new HashMap<>();
    ArrayList<String> breedType_arrayList = new ArrayList<>();
    List<ListData> dataList = new ArrayList<>();
    HashMap<String, JSONObject> breedwise_total_weight_Hashmap = new HashMap<>();
    double goatDead_Weight =0, goatLost_Weight =0,earTagLost_Weight =0, goatSick_Weight =0,
            total_WeightDouble=0 , goatSold_Weight =0 , totalmale_weight=0, totalFemale_weight = 0 , totalFemaleWithBaby_weight = 0;
    int goatDead_Qty = 0,  goatLost_Qty =0 ,  earTagLost_Qty =0 ,goatSick_Qty =0 , goatSold_Qty =0   ;
    int totalScannedItem =0 , totalUnscannedItem =0 ,totalSoldItem =0 , maleCount =0 , femaleCount =0, femaleWithBabyCount = 0  ;
    String processedDate ="",batchno="" ,usermobileno ="", supplierKey ="" , supplierName ="" ,
            selectedDeliveryCenterKey ="",selectedDeliveryCenterName="",activityCalledFrom ="";

    DecimalFormat df = new DecimalFormat(Constants.threeDecimalPattern);



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
                setContentView(R.layout.activity_finish_batch__consolidated_sales_report);
            } else {

                // Inflate the layout for this fragment
                setContentView(R.layout.pos_activity_finish_batch__consolidated_sales_report);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            setContentView(R.layout.activity_finish_batch__consolidated_sales_report);

        }






        loadingpanelmask =  findViewById(R.id.loadingpanelmask);
        loadingPanel =  findViewById(R.id.loadingPanel);
        count_loadingAnim_layout  =  findViewById(R.id.count_loadingAnim_layout);
        deliveryCenterName_textview =  findViewById(R.id.deliveryCenterName_textview);
        batchno_textview =  findViewById(R.id.batchno_textview);
        itemCount_textview =  findViewById(R.id.itemCount_textview);
        batchwiseConsolidatedReport_listview  =  findViewById(R.id.batchwiseConsolidatedReport_listview);
        scrollView = findViewById(R.id.scrollView);
        itemWeight_textview = findViewById(R.id.itemWeight_textview);
        earTag_Qty_textview = findViewById(R.id.earTag_Qty_textview);
        goatDead_weight_textview = findViewById(R.id.goatDead_weight_textview);
        goatDead_Qty_textview = findViewById(R.id.goatDead_Qty_textview);
        goatLost_Weight_textview = findViewById(R.id.goatLost_Weight_textview);
        goatLost_Qty_textview = findViewById(R.id.goatLost_Qty_textview);
        back_IconLayout = findViewById(R.id.back_IconLayout);
        share_reportButton = findViewById(R.id.share_reportButton);
        goatDeadtotal_layout = findViewById(R.id.goatDeadtotal_layout);
        final_statuswise_totalValueCardView = findViewById(R.id.final_statuswise_totalValueCardView);
        goatLosttotal_layout = findViewById(R.id.goatLosttotal_layout);
        earTagtotalwise_layout = findViewById(R.id.earTagtotalwise_layout);
        goatSicktotal_Layout = findViewById(R.id.goatSicktotal_layout);
        goatsickQty_textview = findViewById(R.id.goatsickQty_textview);
        goatSick_weight_textview = findViewById(R.id.goatSick_weight_textview);
        goatSold_Qty_textview = findViewById(R.id.goatSold_Qty_textview);
        goatSold_weight_textview = findViewById(R.id.goatSold_weight_textview);
        goatSoldtotal_layout = findViewById(R.id.goatSoldtotal_layout);
        generate_reportlayout = findViewById(R.id.generate_reportlayout);

        finishBatch_Button =   findViewById(R.id.finishBatch_Button);
        supplierLogin_Cardview =findViewById(R.id.supplierLogin_Cardview);
        deliveryCenterLogin_Cardview  = findViewById(R.id.deliveryCenterLogin_Cardview);
        supplierName_textview =   findViewById(R.id.supplierName_textview);
        batchno_textview_deliveryCenter =findViewById(R.id.batchno_textview_deliveryCenter);
        itemCount_textview_deliveryCenter  = findViewById(R.id.itemCount_textview_deliveryCenter);
        itemunscannedCount_textview_deliveryCenter  = findViewById(R.id.itemunscannedCount_textview_deliveryCenter);
        scannedItems_textview_deliveryCenter = findViewById(R.id.scannedItems_textview_deliveryCenter);
        report_genderwise_report_instru_textview = findViewById(R.id.report_genderwise_report_instru_textview);
        unscanneditem_label = findViewById(R.id.unscanneditem_label);
        scanneditem_label = findViewById(R.id.scanneditem_label);

        male_weight_textview = findViewById(R.id.male_weight_textview);
        male_Qty_textview = findViewById(R.id.male_Qty_textview);
        female_weight_textview = findViewById(R.id.female_weight_textview);
        female_Qty_textview = findViewById(R.id.female_Qty_textview);
        femalewithbaby_weight_textview  = findViewById(R.id.femalewithbaby_weight_textview);
        femalewithbaby_Qty_textview  = findViewById(R.id.femalewithbaby_Qty_textview);
        mark_batch_as_sold_button = findViewById(R.id.markBatch_as_sold_button);



        AskPermissionToGenerateSheet();

        neturalizeAllArraysAndValues();

        IntializeData_And_UI();





      /*  closeBatch_Button.setOnClickListener(view -> {

            if(activityCalledFrom.equals(getString(R.string.delivery_center))|| activityCalledFrom.equals(getString(R.string.delivery_center_batchDetails))) {
                if(totalUnscannedItem==0){
                    updateBatchStatus_as_Finish_Or_Sold();
                }
                else{
                    Toast.makeText(FinishBatch_ConsolidatedReport.this, "Can't Finish batch without Adding Item", Toast.LENGTH_SHORT).show();
                }
            }
            else if(activityCalledFrom.equals(getString(R.string.supplier))) {
                updateBatchStatus_as_Finish_Or_Sold();
            }

        });



       */
        finishBatch_Button.setOnClickListener(view -> {
            if(activityCalledFrom.equals(getString(R.string.delivery_center))|| activityCalledFrom.equals(getString(R.string.delivery_center_batchDetails))) {
                if(totalUnscannedItem==0){
                    updateBatchStatus_as_Finish_Or_Sold();
                }
                else{
                    Toast.makeText(FinishBatch_ConsolidatedReport.this, "Can't Finish batch without scanning every item", Toast.LENGTH_SHORT).show();
                }
            }
            else if(activityCalledFrom.equals(getString(R.string.supplier))) {
                if(breedWise_ItemHashmap.size() >0) {
                    updateBatchStatus_as_Finish_Or_Sold();
                }
                else{
                    AlertDialogClass.showDialog(FinishBatch_ConsolidatedReport.this, R.string.Should_add_one_item);

                }
            }



        });


        mark_batch_as_sold_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalUnscannedItem==0){
                    updateBatchStatus_as_Finish_Or_Sold();
                }
                else{
                    Toast.makeText(FinishBatch_ConsolidatedReport.this, "Can't Finish batch without Sale Every Item", Toast.LENGTH_SHORT).show();
                }




            }
        });

        share_reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenerateReport();

            }
        });

        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



    }



    private void AskPermissionToGenerateSheet() {
        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(FinishBatch_ConsolidatedReport.this, WRITE_EXTERNAL_STORAGE);
        //Log.d("ExportInvoiceActivity", "writeExternalStoragePermission "+writeExternalStoragePermission);
        // If do not grant write external storage permission.
        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            // Request user to grant write external storage permission.
            ActivityCompat.requestPermissions(FinishBatch_ConsolidatedReport.this, new String[]{WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
        } else {


        }
    }
    private void GenerateReport() {


        try {
           wb = new HSSFWorkbook();
           //Now we are creating sheet

        } catch (Exception e) {
            e.printStackTrace();
        }



        if (SDK_INT >= Build.VERSION_CODES.R) {

            if(Environment.isExternalStorageManager()){
                try {
                    showProgressBar(true);

                    try {

                            AddDatatoExcelSheet();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    ;
                }
            }
            else{
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s",FinishBatch_ConsolidatedReport.this.getApplicationContext().getPackageName())));
                    startActivityForResult(intent, 2296);
                } catch (Exception e) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivityForResult(intent, 2296);
                }
            }

        } else {


            int writeExternalStoragePermission = ContextCompat.checkSelfPermission(FinishBatch_ConsolidatedReport.this, WRITE_EXTERNAL_STORAGE);
            //Log.d("ExportInvoiceActivity", "writeExternalStoragePermission "+writeExternalStoragePermission);
            // If do not grant write external storage permission.
            if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                // Request user to grant write external storage permission.
                ActivityCompat.requestPermissions(FinishBatch_ConsolidatedReport.this, new String[]{WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
            } else {

                showProgressBar(true);

                try {

                        AddDatatoExcelSheet();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

/*

    int writeExternalStoragePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // If do not grant write external storage permission.
        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "Click Allow and then Generate Again", Toast.LENGTH_SHORT).show();

            // Request user to grant write external storage permission.
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);


        } else {

            Adjusting_Widgets_Visibility(true);

            try {
                if(whichsheetoGenerate.equals("UserDetailsSheet")){
                    FilterUserAndAddressArray() ;
                }
                else if(whichsheetoGenerate.equals("OrderItemDetailsSheet")){
                    AddDatatoOrderItemDetailsExcelSheet();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

 */
    }

    private void AddDatatoExcelSheet() {

        sheet = wb.createSheet(String.valueOf(System.currentTimeMillis()));
        int rowNum = 7;
        Cell topDetailscell = null, headerCell =null;
        try {
            if (breedWise_ItemHashmap.size() > 0) {
                org.apache.poi.ss.usermodel.Font topDetails = wb.createFont();
                topDetails.setBoldweight((short) 1);
                topDetails.setFontHeightInPoints((short) 12);
                topDetails.setColor(HSSFColor.BLACK.index);
                org.apache.poi.ss.usermodel.Font heasercontent = wb.createFont();
                heasercontent.setFontHeightInPoints((short) 12);
                heasercontent.setColor(HSSFColor.RED.index);


                org.apache.poi.ss.usermodel.Font contentFont = wb.createFont();
                contentFont.setFontHeightInPoints((short) 10);
                contentFont.setColor(HSSFColor.BLACK.index);

                //topdetails
                CellStyle topDetailsStyle = wb.createCellStyle();
                topDetailsStyle.setFillForegroundColor(HSSFColor.BLUE.index);
                topDetailsStyle.setFont(topDetails);
                topDetailsStyle.setAlignment(LEFT);

                //header details
                CellStyle headerStyle = wb.createCellStyle();
                headerStyle.setFont(heasercontent);
                headerStyle.setAlignment(CENTER);

                //cell

                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setFont(contentFont);
                cellStyle.setAlignment(CENTER);


                CellStyle breedtype_cellStyle = wb.createCellStyle();
                breedtype_cellStyle.setFont(contentFont);
                breedtype_cellStyle.setAlignment(LEFT);


                Row topDetailsRow1 = sheet.createRow(1);
                topDetailscell = topDetailsRow1.createCell(1);
                topDetailscell.setCellValue("Batch No : ");
                topDetailscell.setCellStyle(topDetailsStyle);
                topDetailscell = topDetailsRow1.createCell(2);
                topDetailscell.setCellValue(batchno);
                topDetailscell.setCellStyle(topDetailsStyle);


                if (processedDate.equals("")) {
                    processedDate = DateParser.getDate_and_time_newFormat();
                    if (activityCalledFrom.equals(getString(R.string.delivery_center))) {
                        Row topDetailsRow11 = sheet.createRow(2);
                        topDetailscell = topDetailsRow11.createCell(1);
                        topDetailscell.setCellValue("Date : ");
                        topDetailscell.setCellStyle(topDetailsStyle);
                        topDetailscell = topDetailsRow11.createCell(2);
                        topDetailscell.setCellValue(processedDate);
                        topDetailscell.setCellStyle(topDetailsStyle);
                    } else if (activityCalledFrom.equals(getString(R.string.delivery_center_batchDetails))) {
                        Row topDetailsRow11 = sheet.createRow(2);
                        topDetailscell = topDetailsRow11.createCell(1);
                        topDetailscell.setCellValue("Date : ");
                        topDetailscell.setCellStyle(topDetailsStyle);
                        topDetailscell = topDetailsRow11.createCell(2);
                        topDetailscell.setCellValue(processedDate);
                        topDetailscell.setCellStyle(topDetailsStyle);
                    } else {
                        Row topDetailsRow11 = sheet.createRow(2);
                        topDetailscell = topDetailsRow11.createCell(1);
                        topDetailscell.setCellValue("Date : ");
                        topDetailscell.setCellStyle(topDetailsStyle);
                        topDetailscell = topDetailsRow11.createCell(2);
                        topDetailscell.setCellValue(processedDate);
                        topDetailscell.setCellStyle(topDetailsStyle);
                    }
                } else {
                    if (activityCalledFrom.equals(getString(R.string.delivery_center))) {
                        Row topDetailsRow11 = sheet.createRow(2);
                        topDetailscell = topDetailsRow11.createCell(1);
                        topDetailscell.setCellValue("Received Date : ");
                        topDetailscell.setCellStyle(topDetailsStyle);
                        topDetailscell = topDetailsRow11.createCell(2);
                        topDetailscell.setCellValue(processedDate);
                        topDetailscell.setCellStyle(topDetailsStyle);
                    } else if (activityCalledFrom.equals(getString(R.string.delivery_center_batchDetails))) {
                        Row topDetailsRow11 = sheet.createRow(2);
                        topDetailscell = topDetailsRow11.createCell(1);
                        topDetailscell.setCellValue("Received Date : ");
                        topDetailscell.setCellStyle(topDetailsStyle);
                        topDetailscell = topDetailsRow11.createCell(2);
                        topDetailscell.setCellValue(processedDate);
                        topDetailscell.setCellStyle(topDetailsStyle);
                    } else {
                        Row topDetailsRow11 = sheet.createRow(2);
                        topDetailscell = topDetailsRow11.createCell(1);
                        topDetailscell.setCellValue("Sent Date : ");
                        topDetailscell.setCellStyle(topDetailsStyle);
                        topDetailscell = topDetailsRow11.createCell(2);
                        topDetailscell.setCellValue(processedDate);
                        topDetailscell.setCellStyle(topDetailsStyle);
                    }
                }


                Row topDetailsRow2 = sheet.createRow(3);
                topDetailscell = topDetailsRow2.createCell(1);
                topDetailscell.setCellValue("Supplier Name : ");
                topDetailscell.setCellStyle(topDetailsStyle);
                topDetailscell = topDetailsRow2.createCell(2);
                topDetailscell.setCellValue(supplierName);
                topDetailscell.setCellStyle(topDetailsStyle);
                Row topDetailsRow3 = sheet.createRow(4);
                topDetailscell = topDetailsRow3.createCell(1);
                topDetailscell.setCellValue("Delivery Center Name : ");
                topDetailscell.setCellStyle(topDetailsStyle);
                topDetailscell = topDetailsRow3.createCell(2);
                topDetailscell.setCellValue(selectedDeliveryCenterName);
                topDetailscell.setCellStyle(topDetailsStyle);


                //Now column and row
                Row headerRow = sheet.createRow(8);

                for (int i = 0; i < columnsHeading_Supplier.length; i++) {
                    headerCell = headerRow.createCell(i);
                    headerCell.setCellValue(columnsHeading_Supplier[i]);
                    headerCell.setCellStyle(headerStyle);
                }
                if (breedType_arrayList.size() > 0) {
                    for (int iterator = 0; iterator < breedType_arrayList.size(); iterator++) {
                        rowNum++;
                        Row row = sheet.createRow(rowNum++);
                        JSONObject jsonObject = breedWise_ItemHashmap.get(breedType_arrayList.get(iterator));
                        JSONObject totalWeight_Json = breedwise_total_weight_Hashmap.get(breedType_arrayList.get(iterator));
                        String total_weight = "";
                        String total_Qty = "";

                        try {
                            total_weight = totalWeight_Json.getString("WEIGHT");

                            try {
                                total_weight = ((decimalFormat.format(Double.parseDouble(total_weight))));
                            } catch (Exception e) {
                                total_weight = totalWeight_Json.getString("WEIGHT");
                                e.printStackTrace();
                            }


                            total_weight = " - " + total_weight + " Kg";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            total_Qty = totalWeight_Json.getString("QUANTITY");
                            total_Qty = " ( " + total_Qty + " ) ";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        row.createCell(0).setCellValue(iterator + 1);
                        row.getCell(0).setCellStyle(cellStyle);

                        row.createCell(1).setCellValue(breedType_arrayList.get(iterator) + total_Qty + String.valueOf(total_weight));
                        row.getCell(1).setCellStyle(cellStyle);
                        if (jsonObject.has(getString(R.string.MALE))) {


                            row.createCell(2).setCellValue(getString(R.string.MALE));
                            row.getCell(2).setCellStyle(breedtype_cellStyle);

                            try {
                                try {
                                    row.createCell(4).setCellValue((decimalFormat.format(Double.parseDouble(jsonObject.getString("MALE_WEIGHT")))) + "Kg");

                                } catch (Exception e) {
                                    row.createCell(4).setCellValue(jsonObject.getString("MALE_WEIGHT") + "Kg");
                                    e.printStackTrace();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            row.getCell(4).setCellStyle(cellStyle);

                            try {
                                row.createCell(3).setCellValue(jsonObject.getString("MALE_QTY"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            row.getCell(3).setCellStyle(cellStyle);

                        }

                        if (jsonObject.has(getString(R.string.FEMALE))) {
                            Row row1 = sheet.createRow(rowNum++);

                            row1.createCell(2).setCellValue(getString(R.string.FEMALE));
                            row1.getCell(2).setCellStyle(breedtype_cellStyle);

                            try {

                                try {
                                    row1.createCell(4).setCellValue((decimalFormat.format(Double.parseDouble(jsonObject.getString("FEMALE_WEIGHT")))) + "Kg");

                                } catch (Exception e) {
                                    row1.createCell(4).setCellValue(jsonObject.getString("FEMALE_WEIGHT") + "Kg");
                                    e.printStackTrace();
                                }


                                //  row1.createCell(4).setCellValue(jsonObject.getString("FEMALE_WEIGHT") +"Kg");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            row1.getCell(4).setCellStyle(cellStyle);

                            try {
                                row1.createCell(3).setCellValue(jsonObject.getString("FEMALE_QTY"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            row1.getCell(3).setCellStyle(cellStyle);

                        }


                        if (jsonObject.has(getString(R.string.FEMALE_WITH_BABY))) {
                            Row row1 = sheet.createRow(rowNum++);

                            row1.createCell(2).setCellValue(getString(R.string.FEMALE_WITH_BABY));
                            row1.getCell(2).setCellStyle(breedtype_cellStyle);

                            try {


                                try {
                                    row1.createCell(4).setCellValue((decimalFormat.format(Double.parseDouble(jsonObject.getString("FEMALE WITH BABY_WEIGHT")))) + "Kg");

                                } catch (Exception e) {
                                    row1.createCell(4).setCellValue(jsonObject.getString("FEMALE WITH BABY_WEIGHT") + "Kg");
                                    e.printStackTrace();
                                }


                                // row1.createCell(4).setCellValue(jsonObject.getString("FEMALE WITH BABY_WEIGHT") +"Kg");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            row1.getCell(4).setCellStyle(cellStyle);

                            try {
                                row1.createCell(3).setCellValue(jsonObject.getString("FEMALE WITH BABY_QTY"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            row1.getCell(3).setCellStyle(cellStyle);

                        }

                        if (jsonObject.has(("OTHERS"))) {
                            Row row1 = sheet.createRow(rowNum++);

                            row1.createCell(2).setCellValue("OTHERS");
                            row1.getCell(2).setCellStyle(breedtype_cellStyle);

                            try {
                                try {
                                    row1.createCell(4).setCellValue((decimalFormat.format(Double.parseDouble(jsonObject.getString("OTHERS_WEIGHT")))) + "Kg");

                                } catch (Exception e) {
                                    row1.createCell(4).setCellValue(jsonObject.getString("OTHERS_WEIGHT") + "Kg");
                                    e.printStackTrace();
                                }


                                // row1.createCell(4).setCellValue(jsonObject.getString("OTHERS_WEIGHT") +"Kg");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            row1.getCell(4).setCellStyle(cellStyle);

                            try {
                                row1.createCell(3).setCellValue(jsonObject.getString("OTHERS_QTY"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            row1.getCell(3).setCellStyle(cellStyle);

                        }


                    }
                }

                rowNum++;
                rowNum++;

                if (goatLost_Qty > 0 || goatDead_Qty > 0 || earTagLost_Qty > 0 || goatSick_Qty > 0 || goatSold_Qty > 0) {
                    Row row4 = sheet.createRow(rowNum++);
                    row4.createCell(2).setCellValue("Others");
                    row4.getCell(2).setCellStyle(headerStyle);


                    rowNum++;


                    if (goatDead_Qty > 0) {


                        Row row1 = sheet.createRow(rowNum++);

                        row1.createCell(2).setCellValue(Constants.goatEarTagStatus_Goatdead);
                        row1.getCell(2).setCellStyle(topDetailsStyle);
                        row1.createCell(3).setCellValue(goatDead_Qty);
                        row1.getCell(3).setCellStyle(cellStyle);
                        row1.createCell(4).setCellValue(df.format(goatDead_Weight) + " Kg");
                        row1.getCell(4).setCellStyle(cellStyle);

                    } else {

                    }

                    if (goatLost_Qty > 0) {

                        Row row2 = sheet.createRow(rowNum++);
                        row2.createCell(2).setCellValue(Constants.goatEarTagStatus_GoatLost);
                        row2.getCell(2).setCellStyle(topDetailsStyle);
                        row2.createCell(3).setCellValue(goatLost_Qty);
                        row2.getCell(3).setCellStyle(cellStyle);
                        row2.createCell(4).setCellValue(df.format(goatLost_Weight) + " Kg");
                        row2.getCell(4).setCellStyle(cellStyle);


                    } else {

                    }

                    if (earTagLost_Qty > 0) {

                        Row row3 = sheet.createRow(rowNum++);
                        row3.createCell(2).setCellValue(Constants.goatEarTagStatus_EarTagLost);
                        row3.getCell(2).setCellStyle(topDetailsStyle);
                        row3.createCell(3).setCellValue(earTagLost_Qty);
                        row3.getCell(3).setCellStyle(cellStyle);
                        row3.createCell(4).setCellValue(df.format(earTagLost_Weight) + " Kg");
                        row3.getCell(4).setCellStyle(cellStyle);


                    } else {

                    }

                    if (goatSick_Qty > 0) {

                        Row row5 = sheet.createRow(rowNum++);
                        row5.createCell(2).setCellValue(Constants.goatEarTagStatus_Goatsick);
                        row5.getCell(2).setCellStyle(topDetailsStyle);
                        row5.createCell(3).setCellValue(goatSick_Qty);
                        row5.getCell(3).setCellStyle(cellStyle);
                        row5.createCell(4).setCellValue(df.format(goatSick_Weight) + " Kg");
                        row5.getCell(4).setCellStyle(cellStyle);

                    } else {

                    }

                /*if (goatSold_Qty > 0) {

                    Row row5 = sheet.createRow(rowNum++);
                    row5.createCell(2).setCellValue(Constants.goatEarTagStatus_Sold);
                    row5.getCell(2).setCellStyle(topDetailsStyle);
                    row5.createCell(3).setCellValue(goatSold_Qty);
                    row5.getCell(3).setCellStyle(cellStyle);
                    row5.createCell(4).setCellValue(goatSold_Weight+" Kg");
                    row5.getCell(4).setCellStyle(cellStyle);

                }
                else{

                }

                 */


                }


                //  row1.createCell(4).setCellValue(goatDead_Weight+" Kg");
                // row1.getCell(4).setCellStyle(cellStyle);

                sheet.setColumnWidth(1, (10 * 600));
                sheet.setColumnWidth(2, (10 * 600));
                sheet.setColumnWidth(3, (10 * 250));
                sheet.setColumnWidth(4, (10 * 500));


                GenerateExcelSheet("Consolidated Report");


            } else {
                showProgressBar(false);
                Toast.makeText(FinishBatch_ConsolidatedReport.this, "There is no data to create sheet", Toast.LENGTH_LONG).show();

            }
        }
        catch (Exception e){
            e.printStackTrace();
            showProgressBar(false);
            Toast.makeText(FinishBatch_ConsolidatedReport.this, "There is processing error while create sheet", Toast.LENGTH_LONG).show();


        }

    }





    private void GenerateExcelSheet(String FolderName) {


        String  path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/TMCB2BPartner/"+FolderName+"/";
        File dir = new File(path);
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e("Failed", "Storage not available or read only");

        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir,  System.currentTimeMillis()  +".xls");


        //   File file = new File(getExternalFilesDir(null), "Onlineorderdetails.xls");
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(file);
            wb.write(outputStream);
            showProgressBar(false);
            //  Toast.makeText(getApplicationContext(), "File can't be  Created", Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Toast.makeText(FinishBatch_ConsolidatedReport.this, "File Created", Toast.LENGTH_LONG).show();
        } catch (java.io.IOException e) {
            Toast.makeText(FinishBatch_ConsolidatedReport.this, "File can't Created Permission Denied", Toast.LENGTH_LONG).show();

            e.printStackTrace();
            showProgressBar(false);


        }
        Uri pdfUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pdfUri = FileProvider.getUriForFile(FinishBatch_ConsolidatedReport.this, getPackageName() + ".provider", file);
        } else {
            pdfUri = Uri.fromFile(file);
        }
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.setType("application/xls");
        share.putExtra(Intent.EXTRA_STREAM, pdfUri);
        startActivity(Intent.createChooser(share, "Share"));


    }
    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }


    private void IntializeData_And_UI() {
        finishBatch_Button.setText("Complete Review");
        SharedPreferences sh1  = getSharedPreferences("LoginData",   MODE_PRIVATE);
        usermobileno = sh1.getString("UserMobileNumber","");


        Intent i = getIntent();
        batchno = i.getStringExtra("batchno");
        activityCalledFrom = i.getStringExtra(getString(R.string.called_from));

        if(activityCalledFrom.equals(getString(R.string.supplier))){
            if(!Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Loading)){
                generate_reportlayout.setVisibility(View.VISIBLE);
                report_genderwise_report_instru_textview.setVisibility(View.GONE);
            }
            else{

                generate_reportlayout.setVisibility(View.GONE);
            }
        }

        else{
            if(Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Fully_Loaded)){
                generate_reportlayout.setVisibility(View.GONE);
                report_genderwise_report_instru_textview.setText(getString(R.string.reviewed_item_details_instruction));

            }
            else  if(Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Reviewed_and_READYFORSALE)){
                generate_reportlayout.setVisibility(View.VISIBLE);
                report_genderwise_report_instru_textview.setText(getString(R.string.readyForsale_Sold_item_details_instruction));

            }
            else  if(Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Sold)){
                generate_reportlayout.setVisibility(View.VISIBLE);
                report_genderwise_report_instru_textview.setText(getString(R.string.sold_item_details_instruction));

            }
            else{
                generate_reportlayout.setVisibility(View.VISIBLE);
            }
        }
        SharedPreferences sharedPreferences_forAdd  = getSharedPreferences(Constants.earTagCalculationDeta_SupplierCenter,MODE_PRIVATE);

        String batchNo_sharedPreference  =    sharedPreferences_forAdd.getString(
                "BatchNo", "0"
        );
        double   total_loadedweight_double = (double) sharedPreferences_forAdd.getFloat(
                "TotalWeight", 0);





        if(!batchno .equals("")) {
            if(batchNo_sharedPreference.equals(batchno)){
                itemWeight_textview.setText(String.valueOf(decimalFormat.format(total_loadedweight_double))+" Kg");

            }


            if (activityCalledFrom.equals(getString(R.string.delivery_center)) || activityCalledFrom.equals(getString(R.string.delivery_center_batchDetails))) {
                supplierLogin_Cardview.setVisibility(View.GONE);
                deliveryCenterLogin_Cardview.setVisibility(View.VISIBLE);
                final_statuswise_totalValueCardView.setVisibility(View.VISIBLE);

                supplierKey = i.getStringExtra("supplierkey");
                supplierName = i.getStringExtra("suppliername");
                selectedDeliveryCenterKey = i.getStringExtra("deliveryCenterKey");
                selectedDeliveryCenterName = i.getStringExtra("deliveryCenterName");

                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETListMethod);
                supplierName_textview.setText(supplierName);
                batchno_textview_deliveryCenter.setText(batchno);

                 if (Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Reviewing) || Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Fully_Loaded)) {
                    finishBatch_Button.setVisibility(View.VISIBLE);


                    mark_batch_as_sold_button.setVisibility(View.GONE);
                }
               else if ( Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Reviewed_and_READYFORSALE)) {
                    finishBatch_Button.setVisibility(View.GONE);

                    mark_batch_as_sold_button.setVisibility(View.VISIBLE);
                }
                else{

                    finishBatch_Button.setVisibility(View.GONE);
                    mark_batch_as_sold_button.setVisibility(View.GONE);
                }




            } else if (activityCalledFrom.equals(getString(R.string.supplier))) {
                supplierKey = i.getStringExtra("supplierkey");
                supplierName = i.getStringExtra("suppliername");
                if (Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Loading)) {
                    finishBatch_Button.setVisibility(View.VISIBLE);

                    mark_batch_as_sold_button.setVisibility(View.GONE);

                }
                else {
                    finishBatch_Button.setVisibility(View.GONE);
                    mark_batch_as_sold_button.setVisibility(View.GONE);
                }


                final_statuswise_totalValueCardView.setVisibility(View.GONE);
                supplierLogin_Cardview.setVisibility(View.VISIBLE);

                deliveryCenterLogin_Cardview.setVisibility(View.GONE);

                SharedPreferences sh = getSharedPreferences("SupplierData", MODE_PRIVATE);
                supplierKey = sh.getString("SupplierKey", "");
                selectedDeliveryCenterKey = i.getStringExtra("deliveryCenterKey");
                selectedDeliveryCenterName = i.getStringExtra("deliveryCenterName");
                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETListMethod);
                deliveryCenterName_textview.setText(selectedDeliveryCenterName);
                batchno_textview.setText(batchno);
            }
        }
        else {
            Intent i1 = null;
            if (activityCalledFrom.equals(getString(R.string.delivery_center)) || activityCalledFrom.equals(getString(R.string.delivery_center_batchDetails))) {
                i1 = new Intent(FinishBatch_ConsolidatedReport.this, DeliveryCenterDashboardScreen.class);

            }

            else if (activityCalledFrom.equals(getString(R.string.supplier))) {
                 i1 = new Intent(FinishBatch_ConsolidatedReport.this, SupplierDashboardScreen.class);
            }
            i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i1);
        }

    }

    private void updateBatchStatus_as_Finish_Or_Sold() {
        {
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



                    if(result.equals(Constants.item_Already_Added_volley)){
                        AlertDialogClass.showDialog(FinishBatch_ConsolidatedReport.this, R.string.BatchDetailsAlreadyCreated_Instruction);

                    }
                    else if(result.equals(Constants.successResult_volley)){
                        new Modal_B2BBatchDetailsUpdate();
                        showProgressBar(false);

                        if(activityCalledFrom.equals(getString(R.string.delivery_center)) || activityCalledFrom.equals(getString(R.string.delivery_center_batchDetails))) {
                            finishBatch_Button.setVisibility(View.GONE);
                            mark_batch_as_sold_button.setVisibility(View.GONE);

                            generate_reportlayout.setVisibility(View.VISIBLE);
                            String Status_to_update = String.valueOf(Modal_B2BBatchDetailsStatic.getStatus());
                         /*   if(Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Fully_Loaded)) {
                                    Status_to_update =Constants.batchDetailsStatus_Reviewed_and_READYFORSALE;
                            }
                            else{
                                Status_to_update =Constants.batchDetailsStatus_Sold;

                            }
                            Modal_B2BBatchDetailsStatic.setStatus(Status_to_update);


                          */

                            try{
                                for(int iterator = 0 ; iterator<DeliveryCenterHomeScreenFragment.batchDetailsArrayList.size(); iterator++){
                                    String batchnoFromArray  = DeliveryCenterHomeScreenFragment.batchDetailsArrayList.get(iterator).getBatchno();
                                    if(batchnoFromArray.equals(batchno)){
                                        DeliveryCenterHomeScreenFragment.batchDetailsArrayList.get(iterator).setStatus(Status_to_update);
                                        try{
                                            DeliveryCenterHomeScreenFragment.adapter_b2BBatchItemsList.notifyDataSetChanged();
                                        }
                                        catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            /*
                            Intent mainIntent = new Intent(FinishBatch_ConsolidatedReport.
                                    this, DeliveryCenterDashboardScreen.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            overridePendingTransition(0, 0);
                            startActivity(mainIntent);
                            finish();

                             */
                            GenerateReport();
                            showProgressBar(false);
                            isBatchDetailsTableServiceCalled = false;

                        }
                        else  if(activityCalledFrom.equals(getString(R.string.supplier))) {
                            finishBatch_Button.setVisibility(View.GONE);
                            generate_reportlayout.setVisibility(View.VISIBLE);
                            Modal_B2BBatchDetailsStatic.setStatus(Constants.batchDetailsStatus_Fully_Loaded);

                            /*
                            Intent mainIntent = new Intent(FinishBatch_ConsolidatedReport.
                                    this, SupplierDashboardScreen.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            overridePendingTransition(0, 0);
                            startActivity(mainIntent);
                            finish();

                             */
                            GenerateReport();
                            showProgressBar(false);
                            isBatchDetailsTableServiceCalled = false;
                        }

                    }
                    else{
                        Toast.makeText(FinishBatch_ConsolidatedReport.this, Constants.unknown_API_Result_volley, Toast.LENGTH_SHORT).show();
                    }




                   /*new Modal_B2BBatchDetailsUpdate();

                    if(activityCalledFrom.equals(getString(R.string.delivery_center))) {
                        Modal_B2BBatchDetailsStatic.setStatus(Constants.batchDetailsStatus_Stocked);

                        Intent mainIntent = new Intent(FinishBatch_ConsolidatedReport.
                                this, DeliveryCenterDashboardScreen.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        overridePendingTransition(0, 0);
                        startActivity(mainIntent);
                        finish();
                        showProgressBar(false);
                        isBatchDetailsTableServiceCalled = false;

                    }
                    else  if(activityCalledFrom.equals(getString(R.string.supplier))) {

                        Modal_B2BBatchDetailsStatic.setStatus(Constants.batchDetailsStatus_InTransit);

                        Intent mainIntent = new Intent(FinishBatch_ConsolidatedReport.
                                this, SupplierDashboardScreen.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        overridePendingTransition(0, 0);
                        startActivity(mainIntent);
                        finish();
                        showProgressBar(false);
                        isBatchDetailsTableServiceCalled = false;
                    }
                    */


                }

                @Override
                public void notifyVolleyError(VolleyError error) {
                    showProgressBar(false);
                    Toast.makeText(FinishBatch_ConsolidatedReport.this, Constants.volleyError_Result_volley, Toast.LENGTH_SHORT).show();

                    isBatchDetailsTableServiceCalled = false;
                }

                @Override
                public void notifyProcessingError(Exception error) {
                    showProgressBar(false);
                    Toast.makeText(FinishBatch_ConsolidatedReport.this, Constants.processingErrorResult_volley, Toast.LENGTH_SHORT).show();

                    isBatchDetailsTableServiceCalled = false;
                }


            };


            Modal_B2BBatchDetailsUpdate modal_b2BBatchDetailsUpdate = new Modal_B2BBatchDetailsUpdate();
            modal_b2BBatchDetailsUpdate.setBatchno(batchno);
            modal_b2BBatchDetailsUpdate.setSupplierkey(supplierKey);
            modal_b2BBatchDetailsUpdate.setDeliverycenterkey(selectedDeliveryCenterKey);

            processedDate = DateParser.getDate_and_time_newFormat();
            if(activityCalledFrom.equals(getString(R.string.delivery_center))|| activityCalledFrom.equals(getString(R.string.delivery_center_batchDetails))) {
                if(Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Fully_Loaded)){
                    modal_b2BBatchDetailsUpdate.setStatus(Constants.batchDetailsStatus_Reviewed_and_READYFORSALE);
                    modal_b2BBatchDetailsUpdate.setStockedweightingrams(String.valueOf(decimalFormat.format(total_WeightDouble)));

                }
                else{
                    modal_b2BBatchDetailsUpdate.setStatus(Constants.batchDetailsStatus_Sold);

                }


            }
            else if(activityCalledFrom.equals(getString(R.string.supplier))) {
                modal_b2BBatchDetailsUpdate.setStatus(Constants.batchDetailsStatus_Fully_Loaded);
                modal_b2BBatchDetailsUpdate.setLoadedweightingrams(String.valueOf(decimalFormat.format(total_WeightDouble)));
                modal_b2BBatchDetailsUpdate.setItemcount(String.valueOf(earTagItemsForBatch.size()));
                modal_b2BBatchDetailsUpdate.setSentdate(processedDate);

            }

            String addApiToCall = API_Manager.updateBatchDetailsWithSupplierkeyBatchNo;

            B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface, addApiToCall, Constants.CallUPDATEMethod);
            asyncTask.execute();


        }



    }

    private void Initialize_and_ExecuteInGoatEarTagDetails(String callMethod) {
        breedwise_total_weight_Hashmap.clear();
        dataList.clear();
        breedType_arrayList.clear();
        breedWise_ItemHashmap.clear();
        earTagItemsForBatch.clear();
        goatearTagLost_Status_earTagItem.clear();
        goatLost_Status_earTagItem.clear();
        goatDead_Status_earTagItem.clear();
        goatSold_Status_earTagItem.clear();
        goatSick_Status_earTagItem.clear();


        total_WeightDouble =0 ;
        totalScannedItem =0;
        totalUnscannedItem =0;
         goatDead_Weight =0; goatLost_Weight =0;earTagLost_Weight =0; total_WeightDouble=0; goatSold_Weight =0 ; goatSick_Weight =0;
         goatDead_Qty = 0;  goatLost_Qty =0 ;  earTagLost_Qty =0 ; goatSold_Qty =0 ;goatSick_Qty = 0;

        showProgressBar(true);
        if (isGoatEarTagDetailsTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isGoatEarTagDetailsTableServiceCalled = true;
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


                            if(activityCalledFrom.equals(getString(R.string.delivery_center))|| activityCalledFrom.equals(getString(R.string.delivery_center_batchDetails))){

                                Filter_Array_basedOn_Status();

                            }
                            else  if(activityCalledFrom.equals(getString(R.string.supplier))){

                                processArray_And_FormatIt();
                            }

                        }
                        else{
                            showProgressBar(false);
                            isGoatEarTagDetailsTableServiceCalled = false;

                            Toast.makeText(FinishBatch_ConsolidatedReport.this, Constants.there_is_noData_volley, Toast.LENGTH_SHORT).show();

                        }
                    }
                    catch (Exception e){
                        showProgressBar(false);
                        isGoatEarTagDetailsTableServiceCalled = false;

                        Toast.makeText(FinishBatch_ConsolidatedReport.this, Constants.processingErrorResult_volley, Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    }



                }



            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
                Toast.makeText(FinishBatch_ConsolidatedReport.this, Constants.volleyError_Result_volley, Toast.LENGTH_SHORT).show();

                isGoatEarTagDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                Toast.makeText(FinishBatch_ConsolidatedReport.this, Constants.processingErrorResult_volley, Toast.LENGTH_SHORT).show();

                showProgressBar(false);
                isGoatEarTagDetailsTableServiceCalled = false;


            }




        };

             String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchnoWithVariousStatus +"?batchno="+batchno +"&status1="+Constants.goatEarTagStatus_Cancelled+"&status2="+Constants.goatEarTagStatus_EarTagLost+"&filtertype="+Constants.api_filtertype_notequals;

            //String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBatchno +batchno ;
            GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails,  addApiToCall , callMethod);
            asyncTask.execute();





    }

    private void Filter_Array_basedOn_Status() {
        try {
            for (int iterator = 0; iterator < earTagItemsForBatch.size(); iterator++) {

                Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagItemsForBatch.get(iterator);

                if (modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_EarTagLost)) {
                    goatearTagLost_Status_earTagItem.add(modal_goatEarTagDetails);

                }
                if (modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatdead)) {
                    goatDead_Status_earTagItem.add(modal_goatEarTagDetails);

                }
                if (modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_GoatLost)) {
                    goatLost_Status_earTagItem.add(modal_goatEarTagDetails);

                }

                if (modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatsick)) {
                    goatSick_Status_earTagItem.add(modal_goatEarTagDetails);

                }


                if (modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Sold)) {
                    goatSold_Status_earTagItem.add(modal_goatEarTagDetails);

                }




            }

            processArray_And_FormatIt();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        }


    private void processArray_And_FormatIt() {
        try {
    for(int iterator = 0 ;iterator<earTagItemsForBatch.size(); iterator++){

        Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagItemsForBatch.get(iterator);
        if(Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Reviewed_and_READYFORSALE)) {



            if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE)){
                totalUnscannedItem = totalUnscannedItem + 1 ;

            }

        }
        else if(Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Fully_Loaded)) {



            if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Loading)){
                totalUnscannedItem = totalUnscannedItem + 1 ;

            }

        }


        if(activityCalledFrom.equals(getString(R.string.delivery_center))|| activityCalledFrom.equals(getString(R.string.delivery_center_batchDetails))) {

           // if((modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE) || (modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Sold))) ) {



                if(Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Reviewed_and_READYFORSALE)) {



                    if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Sold)){
                        totalScannedItem = totalScannedItem +1;
                        unscanneditem_label .setText("UnSold Item");
                        scanneditem_label .setText("Sold Item");

                        String weight_in_grams = "0";
                        weight_in_grams = modal_goatEarTagDetails.getCurrentweightingrams();


                        if(weight_in_grams.equals("")){
                            weight_in_grams = "0";
                        }
                        try {
                            weight_in_grams = ((decimalFormat.format(Double.parseDouble(weight_in_grams))));
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                        String weightinString_fromDB = weight_in_grams;
                        weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");
                        if(weightinString_fromDB.equals("") || weightinString_fromDB.equals(null)){
                            weightinString_fromDB = "0";
                        }

                        double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);

                        total_WeightDouble = total_WeightDouble + weightinDouble_fromDB;

                        if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))) {
                            maleCount = maleCount + 1;

                            totalmale_weight = totalmale_weight + weightinDouble_fromDB;

                        }

                        if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))) {

                            femaleCount = femaleCount + 1;

                            totalFemale_weight = totalFemale_weight + weightinDouble_fromDB;
                        }


                        if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {

                            femaleWithBabyCount = femaleWithBabyCount + 1;

                            totalFemaleWithBaby_weight = totalFemaleWithBaby_weight + weightinDouble_fromDB;


                        }


                        if (!breedType_arrayList.contains(modal_goatEarTagDetails.getBreedtype())) {
                            breedType_arrayList.add(modal_goatEarTagDetails.getBreedtype());


                            JSONObject jsonObject = new JSONObject();
                            JSONObject breedwise_weighttotal_Json = new JSONObject();
                            try {

                                breedwise_weighttotal_Json.put("WEIGHT", weight_in_grams);
                                breedwise_weighttotal_Json.put("QUANTITY", 1);

                                breedwise_total_weight_Hashmap.put(modal_goatEarTagDetails.getBreedtype(), breedwise_weighttotal_Json);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {

                                if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))) {


                                    jsonObject.put("MALE", getString(R.string.MALE));
                                    jsonObject.put("MALE_WEIGHT", weight_in_grams);
                                    jsonObject.put("MALE_QTY", 1);
                                } else if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))) {

                                    jsonObject.put("FEMALE", getString(R.string.FEMALE));
                                    jsonObject.put("FEMALE_WEIGHT", weight_in_grams);
                                    jsonObject.put("FEMALE_QTY", 1);

                                } else if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {


                                    jsonObject.put("FEMALE WITH BABY", getString(R.string.FEMALE_WITH_BABY));
                                    jsonObject.put("FEMALE WITH BABY_WEIGHT", weight_in_grams);
                                    jsonObject.put("FEMALE WITH BABY_QTY", 1);

                                } else {
                                    jsonObject.put("OTHERS", "OTHERS");
                                    jsonObject.put("OTHERS_WEIGHT", weight_in_grams);
                                    jsonObject.put("OTHERS_QTY", 1);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            breedWise_ItemHashmap.put(modal_goatEarTagDetails.getBreedtype(), jsonObject);
                        }
                        else {
                            JSONObject jsonObject = breedWise_ItemHashmap.get(modal_goatEarTagDetails.getBreedtype());
                            JSONObject breedwise_weighttotal_Json = breedwise_total_weight_Hashmap.get(modal_goatEarTagDetails.getBreedtype());
                            try {
                                if (breedwise_weighttotal_Json.has("WEIGHT")) {
                                    //Total Breedwise Weight
                                    String weightinString_fromJSON = breedwise_weighttotal_Json.getString("WEIGHT");
                                    weightinString_fromJSON = weightinString_fromJSON.replaceAll("[^\\d.]", "");

                                    if(weightinString_fromJSON.equals("") || weightinString_fromJSON.equals(null)){
                                        weightinString_fromJSON = "0";
                                    }


                                    double weightinDouble_fromJSON = Double.parseDouble(weightinString_fromJSON);

                                    double totalWeight = weightinDouble_fromJSON + weightinDouble_fromDB;


                                    breedwise_weighttotal_Json.put("WEIGHT", String.valueOf(decimalFormat.format(totalWeight)));


                                    //Total Breedwise Quantity

                                    int quantityinInt_fromJSON = breedwise_weighttotal_Json.getInt("QUANTITY");
                                    int totalQuantity = quantityinInt_fromJSON + 1;
                                    breedwise_weighttotal_Json.put("QUANTITY", totalQuantity);
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                if (Objects.requireNonNull(jsonObject).has(modal_goatEarTagDetails.getGender().toUpperCase())) {


                                    if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))) {

                            /*    maleCount = maleCount+1;
                                if (jsonObject.has("MALE_WEIGHT")) {
                                    String weightinString_fromJSON = jsonObject.getString("MALE_WEIGHT");
                                    weightinString_fromJSON = weightinString_fromJSON.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromJSON = Double.parseDouble(weightinString_fromJSON);
                                    totalmale_weight = totalmale_weight + weightinDouble_fromJSON;


                                }
                                else{
                                    String weightinString_fromDB = weight_in_grams;
                                    weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);
                                    totalmale_weight = totalmale_weight + weightinDouble_fromDB;

                                }

                             */

                                        if (jsonObject.has("MALE_WEIGHT")) {
                                            try {
                                                //Weight
                                                String weightinString_fromJSON = jsonObject.getString("MALE_WEIGHT");
                                                weightinString_fromJSON = weightinString_fromJSON.replaceAll("[^\\d.]", "");


                                                if(weightinString_fromJSON.equals("") || weightinString_fromJSON.equals(null)){
                                                    weightinString_fromJSON = "0";
                                                }

                                                double weightinDouble_fromJSON = Double.parseDouble(weightinString_fromJSON);

                                                double totalWeight = weightinDouble_fromJSON + weightinDouble_fromDB;

                                                jsonObject.put("MALE_WEIGHT", String.valueOf(decimalFormat.format(totalWeight)));

                                                //Quantity
                                                int quantityinInt_fromJSON = jsonObject.getInt("MALE_QTY");
                                                int totalQuantity = quantityinInt_fromJSON + 1;
                                                jsonObject.put("MALE_QTY", totalQuantity);
                                            }
                                            catch (Exception e)
                                            {
                                                e.printStackTrace();
                                            }
                                        } else {
                                      /*  maleCount = maleCount+1;

                                        String weightinString_fromDB = weight_in_grams;
                                        weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");
                                        double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);
                                        totalmale_weight = totalmale_weight + weightinDouble_fromDB;


                                       */

                                            jsonObject.put("MALE", getString(R.string.MALE));
                                            jsonObject.put("MALE_WEIGHT", weight_in_grams);
                                            jsonObject.put("MALE_QTY", 1);
                                        }
                                    } else if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))) {


                                 /*   femaleCount = femaleCount+1;

                                if (jsonObject.has("FEMALE_WEIGHT")) {
                                    String weightinString_fromJSON = jsonObject.getString("FEMALE_WEIGHT");
                                    weightinString_fromJSON = weightinString_fromJSON.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromJSON = Double.parseDouble(weightinString_fromJSON);
                                    totalFemale_weight = totalFemale_weight + weightinDouble_fromJSON;


                                }
                                else{
                                    String weightinString_fromDB = weight_in_grams;
                                    weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);
                                    totalFemale_weight = totalFemale_weight + weightinDouble_fromDB;

                                }

                                  */




                                        if (jsonObject.has("FEMALE_WEIGHT")) {


                                            //Weight
                                            try {
                                                String weightinString_fromJSON = jsonObject.getString("FEMALE_WEIGHT");
                                                weightinString_fromJSON = weightinString_fromJSON.replaceAll("[^\\d.]", "");


                                                if(weightinString_fromJSON.equals("") || weightinString_fromJSON.equals(null)){
                                                    weightinString_fromJSON = "0";
                                                }

                                                double weightinDouble_fromJSON = Double.parseDouble(weightinString_fromJSON);

                                                double totalWeight = weightinDouble_fromJSON + weightinDouble_fromDB;

                                                jsonObject.put("FEMALE_WEIGHT",String.valueOf(decimalFormat.format(totalWeight)));

                                                //Quantity
                                                int quantityinInt_fromJSON = jsonObject.getInt("FEMALE_QTY");
                                                int totalQuantity = quantityinInt_fromJSON + 1;
                                                jsonObject.put("FEMALE_QTY", totalQuantity);
                                            }
                                            catch (Exception e ){
                                                e.printStackTrace();
                                            }

                                        } else {

                                /*    femaleCount = femaleCount+1;

                                    String weightinString_fromDB = weight_in_grams;
                                    weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);
                                    totalFemale_weight = totalFemale_weight + weightinDouble_fromDB;


                                 */
                                            jsonObject.put("FEMALE", getString(R.string.FEMALE));
                                            jsonObject.put("FEMALE_WEIGHT", weight_in_grams);
                                            jsonObject.put("FEMALE_QTY", 1);
                                        }


                                    } else if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {

                             /*   femaleWithBabyCount = femaleWithBabyCount +1;
                                if (jsonObject.has("FEMALE WITH BABY_WEIGHT")) {
                                    String weightinString_fromJSON = jsonObject.getString("FEMALE WITH BABY_WEIGHT");
                                    weightinString_fromJSON = weightinString_fromJSON.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromJSON = Double.parseDouble(weightinString_fromJSON);
                                    totalFemaleWithBaby_weight = totalFemaleWithBaby_weight + weightinDouble_fromJSON;


                                }
                                else{
                                    String weightinString_fromDB = weight_in_grams;
                                    weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);
                                    totalFemaleWithBaby_weight = totalFemaleWithBaby_weight + weightinDouble_fromDB;

                                }


                              */

                                        if (jsonObject.has("FEMALE WITH BABY_WEIGHT")) {
                                            try {
                                                //Weight
                                                String weightinString_fromJSON = jsonObject.getString("FEMALE WITH BABY_WEIGHT");
                                                weightinString_fromJSON = weightinString_fromJSON.replaceAll("[^\\d.]", "");

                                                if(weightinString_fromJSON.equals("") || weightinString_fromJSON.equals(null)){
                                                    weightinString_fromJSON = "0";
                                                }

                                                double weightinDouble_fromJSON = Double.parseDouble(weightinString_fromJSON);

                                                double totalWeight = weightinDouble_fromJSON + weightinDouble_fromDB;

                                                jsonObject.put("FEMALE WITH BABY_WEIGHT", String.valueOf(decimalFormat.format(totalWeight)));

                                                //Quantity
                                                int quantityinInt_fromJSON = jsonObject.getInt("FEMALE WITH BABY_QTY");
                                                int totalQuantity = quantityinInt_fromJSON + 1;
                                                jsonObject.put("FEMALE WITH BABY_QTY", totalQuantity);
                                            }
                                            catch (Exception e){
                                                e.printStackTrace();
                                            }

                                        } else {

                                  /*  femaleWithBabyCount = femaleWithBabyCount +1;

                                    String weightinString_fromDB = weight_in_grams;
                                    weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);
                                    totalFemaleWithBaby_weight = totalFemaleWithBaby_weight + weightinDouble_fromDB;


                                   */
                                            jsonObject.put("FEMALE WITH BABY", getString(R.string.FEMALE_WITH_BABY));
                                            jsonObject.put("FEMALE WITH BABY_WEIGHT", weight_in_grams);
                                            jsonObject.put("FEMALE WITH BABY_QTY", 1);
                                        }

                                    }
                                }
                                else {
                                    if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))) {

                              /*  maleCount = maleCount+1;

                                    String weightinString_fromDB = weight_in_grams;
                                    weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);
                                    totalmale_weight = totalmale_weight + weightinDouble_fromDB;



                               */
                                        jsonObject.put("MALE", getString(R.string.MALE));
                                        jsonObject.put("MALE_WEIGHT", weight_in_grams);
                                        jsonObject.put("MALE_QTY", 1);
                                    } else if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))) {


                                  /*   femaleCount = femaleCount+1;

                                    String weightinString_fromDB = weight_in_grams;
                                    weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);
                                    totalFemale_weight = totalFemale_weight + weightinDouble_fromDB;


                                   */

                                        jsonObject.put("FEMALE", getString(R.string.FEMALE));
                                        jsonObject.put("FEMALE_WEIGHT", weight_in_grams);
                                        jsonObject.put("FEMALE_QTY", 1);
                                    } else if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {

                                  /*  femaleWithBabyCount = femaleWithBabyCount +1;

                                    String weightinString_fromDB = weight_in_grams;
                                    weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);
                                    totalFemaleWithBaby_weight = totalFemaleWithBaby_weight + weightinDouble_fromDB;



                                   */


                                        jsonObject.put("FEMALE WITH BABY", getString(R.string.FEMALE_WITH_BABY));
                                        jsonObject.put("FEMALE WITH BABY_WEIGHT", weight_in_grams);
                                        jsonObject.put("FEMALE WITH BABY_QTY", 1);
                                    }
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }







                    }

                }
                else if(Modal_B2BBatchDetailsStatic.getStatus().toUpperCase().equals(Constants.batchDetailsStatus_Fully_Loaded)) {



                    if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE)){
                        totalScannedItem = totalScannedItem +1;




                        String weight_in_grams = "0";
                        weight_in_grams = modal_goatEarTagDetails.getCurrentweightingrams();


                        if(weight_in_grams.equals("")){
                            weight_in_grams = "0";
                        }
                        try {
                            weight_in_grams = ((decimalFormat.format(Double.parseDouble(weight_in_grams))));
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }


                        String weightinString_fromDB = weight_in_grams;
                        weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");
                        if(weightinString_fromDB.equals("") || weightinString_fromDB.equals(null)){
                            weightinString_fromDB = "0";
                        }

                        double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);
                        weightinDouble_fromDB = Double.parseDouble((decimalFormat.format(weightinDouble_fromDB)));
                        total_WeightDouble = total_WeightDouble + weightinDouble_fromDB;
                        total_WeightDouble = Double.parseDouble((decimalFormat.format(total_WeightDouble)));
                        if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))) {
                            maleCount = maleCount + 1;

                            totalmale_weight = totalmale_weight + weightinDouble_fromDB;

                        }

                        if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))) {

                            femaleCount = femaleCount + 1;

                            totalFemale_weight = totalFemale_weight + weightinDouble_fromDB;
                        }


                        if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {

                            femaleWithBabyCount = femaleWithBabyCount + 1;

                            totalFemaleWithBaby_weight = totalFemaleWithBaby_weight + weightinDouble_fromDB;


                        }


                        if (!breedType_arrayList.contains(modal_goatEarTagDetails.getBreedtype())) {
                            breedType_arrayList.add(modal_goatEarTagDetails.getBreedtype());


                            JSONObject jsonObject = new JSONObject();
                            JSONObject breedwise_weighttotal_Json = new JSONObject();
                            try {

                                breedwise_weighttotal_Json.put("WEIGHT", weight_in_grams);
                                breedwise_weighttotal_Json.put("QUANTITY", 1);

                                breedwise_total_weight_Hashmap.put(modal_goatEarTagDetails.getBreedtype(), breedwise_weighttotal_Json);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {

                                if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))) {


                                    jsonObject.put("MALE", getString(R.string.MALE));
                                    jsonObject.put("MALE_WEIGHT", weight_in_grams);
                                    jsonObject.put("MALE_QTY", 1);
                                } else if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))) {

                                    jsonObject.put("FEMALE", getString(R.string.FEMALE));
                                    jsonObject.put("FEMALE_WEIGHT", weight_in_grams);
                                    jsonObject.put("FEMALE_QTY", 1);

                                } else if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {


                                    jsonObject.put("FEMALE WITH BABY", getString(R.string.FEMALE_WITH_BABY));
                                    jsonObject.put("FEMALE WITH BABY_WEIGHT", weight_in_grams);
                                    jsonObject.put("FEMALE WITH BABY_QTY", 1);

                                } else {
                                    jsonObject.put("OTHERS", "OTHERS");
                                    jsonObject.put("OTHERS_WEIGHT", weight_in_grams);
                                    jsonObject.put("OTHERS_QTY", 1);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            breedWise_ItemHashmap.put(modal_goatEarTagDetails.getBreedtype(), jsonObject);
                        }
                        else {
                            JSONObject jsonObject = breedWise_ItemHashmap.get(modal_goatEarTagDetails.getBreedtype());
                            JSONObject breedwise_weighttotal_Json = breedwise_total_weight_Hashmap.get(modal_goatEarTagDetails.getBreedtype());
                            try {
                                if (breedwise_weighttotal_Json.has("WEIGHT")) {
                                    //Total Breedwise Weight
                                    String weightinString_fromJSON = breedwise_weighttotal_Json.getString("WEIGHT");
                                    weightinString_fromJSON = weightinString_fromJSON.replaceAll("[^\\d.]", "");

                                    if(weightinString_fromJSON.equals("") || weightinString_fromJSON.equals(null)){
                                        weightinString_fromJSON = "0";
                                    }


                                    double weightinDouble_fromJSON = Double.parseDouble(weightinString_fromJSON);
                                    double totalWeight = weightinDouble_fromJSON + weightinDouble_fromDB;

                                    totalWeight = Double.parseDouble((decimalFormat.format(totalWeight)));

                                    breedwise_weighttotal_Json.put("WEIGHT", String.valueOf(totalWeight));


                                    //Total Breedwise Quantity

                                    int quantityinInt_fromJSON = breedwise_weighttotal_Json.getInt("QUANTITY");
                                    int totalQuantity = quantityinInt_fromJSON + 1;
                                    breedwise_weighttotal_Json.put("QUANTITY", totalQuantity);
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                if (Objects.requireNonNull(jsonObject).has(modal_goatEarTagDetails.getGender().toUpperCase())) {


                                    if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))) {

                            /*    maleCount = maleCount+1;
                                if (jsonObject.has("MALE_WEIGHT")) {
                                    String weightinString_fromJSON = jsonObject.getString("MALE_WEIGHT");
                                    weightinString_fromJSON = weightinString_fromJSON.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromJSON = Double.parseDouble(weightinString_fromJSON);
                                    totalmale_weight = totalmale_weight + weightinDouble_fromJSON;


                                }
                                else{
                                    String weightinString_fromDB = weight_in_grams;
                                    weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);
                                    totalmale_weight = totalmale_weight + weightinDouble_fromDB;

                                }

                             */

                                        if (jsonObject.has("MALE_WEIGHT")) {
                                            try {
                                                //Weight
                                                String weightinString_fromJSON = jsonObject.getString("MALE_WEIGHT");
                                                weightinString_fromJSON = weightinString_fromJSON.replaceAll("[^\\d.]", "");


                                                if(weightinString_fromJSON.equals("") || weightinString_fromJSON.equals(null)){
                                                    weightinString_fromJSON = "0";
                                                }

                                                double weightinDouble_fromJSON = Double.parseDouble(weightinString_fromJSON);

                                                double totalWeight = weightinDouble_fromJSON + weightinDouble_fromDB;
                                                totalWeight = Double.parseDouble((decimalFormat.format(totalWeight)));

                                                jsonObject.put("MALE_WEIGHT", String.valueOf(totalWeight));

                                                //Quantity
                                                int quantityinInt_fromJSON = jsonObject.getInt("MALE_QTY");
                                                int totalQuantity = quantityinInt_fromJSON + 1;
                                                jsonObject.put("MALE_QTY", totalQuantity);
                                            }
                                            catch (Exception e)
                                            {
                                                e.printStackTrace();
                                            }
                                        } else {
                                      /*  maleCount = maleCount+1;

                                        String weightinString_fromDB = weight_in_grams;
                                        weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");
                                        double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);
                                        totalmale_weight = totalmale_weight + weightinDouble_fromDB;


                                       */

                                            jsonObject.put("MALE", getString(R.string.MALE));
                                            jsonObject.put("MALE_WEIGHT", weight_in_grams);
                                            jsonObject.put("MALE_QTY", 1);
                                        }
                                    } else if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))) {


                                 /*   femaleCount = femaleCount+1;

                                if (jsonObject.has("FEMALE_WEIGHT")) {
                                    String weightinString_fromJSON = jsonObject.getString("FEMALE_WEIGHT");
                                    weightinString_fromJSON = weightinString_fromJSON.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromJSON = Double.parseDouble(weightinString_fromJSON);
                                    totalFemale_weight = totalFemale_weight + weightinDouble_fromJSON;


                                }
                                else{
                                    String weightinString_fromDB = weight_in_grams;
                                    weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);
                                    totalFemale_weight = totalFemale_weight + weightinDouble_fromDB;

                                }

                                  */




                                        if (jsonObject.has("FEMALE_WEIGHT")) {


                                            //Weight
                                            try {
                                                String weightinString_fromJSON = jsonObject.getString("FEMALE_WEIGHT");
                                                weightinString_fromJSON = weightinString_fromJSON.replaceAll("[^\\d.]", "");


                                                if(weightinString_fromJSON.equals("") || weightinString_fromJSON.equals(null)){
                                                    weightinString_fromJSON = "0";
                                                }

                                                double weightinDouble_fromJSON = Double.parseDouble(weightinString_fromJSON);

                                                double totalWeight = weightinDouble_fromJSON + weightinDouble_fromDB;
                                                totalWeight = Double.parseDouble((decimalFormat.format(totalWeight)));
                                                jsonObject.put("FEMALE_WEIGHT", String.valueOf(totalWeight));

                                                //Quantity
                                                int quantityinInt_fromJSON = jsonObject.getInt("FEMALE_QTY");
                                                int totalQuantity = quantityinInt_fromJSON + 1;
                                                jsonObject.put("FEMALE_QTY", totalQuantity);
                                            }
                                            catch (Exception e ){
                                                e.printStackTrace();
                                            }

                                        } else {

                                /*    femaleCount = femaleCount+1;

                                    String weightinString_fromDB = weight_in_grams;
                                    weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);
                                    totalFemale_weight = totalFemale_weight + weightinDouble_fromDB;


                                 */
                                            jsonObject.put("FEMALE", getString(R.string.FEMALE));
                                            jsonObject.put("FEMALE_WEIGHT", weight_in_grams);
                                            jsonObject.put("FEMALE_QTY", 1);
                                        }


                                    } else if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {

                             /*   femaleWithBabyCount = femaleWithBabyCount +1;
                                if (jsonObject.has("FEMALE WITH BABY_WEIGHT")) {
                                    String weightinString_fromJSON = jsonObject.getString("FEMALE WITH BABY_WEIGHT");
                                    weightinString_fromJSON = weightinString_fromJSON.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromJSON = Double.parseDouble(weightinString_fromJSON);
                                    totalFemaleWithBaby_weight = totalFemaleWithBaby_weight + weightinDouble_fromJSON;


                                }
                                else{
                                    String weightinString_fromDB = weight_in_grams;
                                    weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);
                                    totalFemaleWithBaby_weight = totalFemaleWithBaby_weight + weightinDouble_fromDB;

                                }


                              */

                                        if (jsonObject.has("FEMALE WITH BABY_WEIGHT")) {
                                            try {
                                                //Weight
                                                String weightinString_fromJSON = jsonObject.getString("FEMALE WITH BABY_WEIGHT");
                                                weightinString_fromJSON = weightinString_fromJSON.replaceAll("[^\\d.]", "");

                                                if(weightinString_fromJSON.equals("") || weightinString_fromJSON.equals(null)){
                                                    weightinString_fromJSON = "0";
                                                }

                                                double weightinDouble_fromJSON = Double.parseDouble(weightinString_fromJSON);

                                                double totalWeight = weightinDouble_fromJSON + weightinDouble_fromDB;
                                                totalWeight = Double.parseDouble((decimalFormat.format(totalWeight)));
                                                jsonObject.put("FEMALE WITH BABY_WEIGHT", String.valueOf(totalWeight));

                                                //Quantity
                                                int quantityinInt_fromJSON = jsonObject.getInt("FEMALE WITH BABY_QTY");
                                                int totalQuantity = quantityinInt_fromJSON + 1;
                                                jsonObject.put("FEMALE WITH BABY_QTY", totalQuantity);
                                            }
                                            catch (Exception e){
                                                e.printStackTrace();
                                            }

                                        } else {

                                  /*  femaleWithBabyCount = femaleWithBabyCount +1;

                                    String weightinString_fromDB = weight_in_grams;
                                    weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);
                                    totalFemaleWithBaby_weight = totalFemaleWithBaby_weight + weightinDouble_fromDB;


                                   */
                                            jsonObject.put("FEMALE WITH BABY", getString(R.string.FEMALE_WITH_BABY));
                                            jsonObject.put("FEMALE WITH BABY_WEIGHT", weight_in_grams);
                                            jsonObject.put("FEMALE WITH BABY_QTY", 1);
                                        }

                                    }
                                }
                                else {
                                    if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))) {

                              /*  maleCount = maleCount+1;

                                    String weightinString_fromDB = weight_in_grams;
                                    weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);
                                    totalmale_weight = totalmale_weight + weightinDouble_fromDB;



                               */
                                        jsonObject.put("MALE", getString(R.string.MALE));
                                        jsonObject.put("MALE_WEIGHT", weight_in_grams);
                                        jsonObject.put("MALE_QTY", 1);
                                    } else if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))) {


                                  /*   femaleCount = femaleCount+1;

                                    String weightinString_fromDB = weight_in_grams;
                                    weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);
                                    totalFemale_weight = totalFemale_weight + weightinDouble_fromDB;


                                   */

                                        jsonObject.put("FEMALE", getString(R.string.FEMALE));
                                        jsonObject.put("FEMALE_WEIGHT", weight_in_grams);
                                        jsonObject.put("FEMALE_QTY", 1);
                                    } else if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {

                                  /*  femaleWithBabyCount = femaleWithBabyCount +1;

                                    String weightinString_fromDB = weight_in_grams;
                                    weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");
                                    double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);
                                    totalFemaleWithBaby_weight = totalFemaleWithBaby_weight + weightinDouble_fromDB;



                                   */


                                        jsonObject.put("FEMALE WITH BABY", getString(R.string.FEMALE_WITH_BABY));
                                        jsonObject.put("FEMALE WITH BABY_WEIGHT", weight_in_grams);
                                        jsonObject.put("FEMALE WITH BABY_QTY", 1);
                                    }
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }







                    }

                }




           //second if }

        }
        else if(activityCalledFrom.equals(getString(R.string.supplier))) {

            if(!modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_EarTagLost) ) {


                totalScannedItem = totalScannedItem + 1;


                String weight_in_grams = "0";
                weight_in_grams = modal_goatEarTagDetails.getCurrentweightingrams();

                if (weight_in_grams.equals("")) {
                    weight_in_grams = "0";
                }
                String weightinString_fromDB = weight_in_grams;
                weightinString_fromDB = weightinString_fromDB.replaceAll("[^\\d.]", "");

                if(weightinString_fromDB.equals("") || weightinString_fromDB.equals(null)){
                    weightinString_fromDB = "0";
                }


                double weightinDouble_fromDB = Double.parseDouble(weightinString_fromDB);
                total_WeightDouble = total_WeightDouble + weightinDouble_fromDB;
                if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))) {
                    maleCount = maleCount + 1;

                    totalmale_weight = totalmale_weight + weightinDouble_fromDB;

                }

                if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))) {

                    femaleCount = femaleCount + 1;

                    totalFemale_weight = totalFemale_weight + weightinDouble_fromDB;
                }


                if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {

                    femaleWithBabyCount = femaleWithBabyCount + 1;

                    totalFemaleWithBaby_weight = totalFemaleWithBaby_weight + weightinDouble_fromDB;


                }


                if (!breedType_arrayList.contains(modal_goatEarTagDetails.getBreedtype())) {
                    breedType_arrayList.add(modal_goatEarTagDetails.getBreedtype());


                    JSONObject jsonObject = new JSONObject();
                    JSONObject breedwise_weighttotal_Json = new JSONObject();
                    try {

                        breedwise_weighttotal_Json.put("WEIGHT", weight_in_grams);
                        breedwise_weighttotal_Json.put("QUANTITY", 1);

                        breedwise_total_weight_Hashmap.put(modal_goatEarTagDetails.getBreedtype(), breedwise_weighttotal_Json);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {

                        if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))) {
                            jsonObject.put("MALE", getString(R.string.MALE));
                            jsonObject.put("MALE_WEIGHT", weight_in_grams);
                            jsonObject.put("MALE_QTY", 1);
                        } else if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))) {


                            jsonObject.put("FEMALE", getString(R.string.FEMALE));
                            jsonObject.put("FEMALE_WEIGHT", weight_in_grams);
                            jsonObject.put("FEMALE_QTY", 1);

                        } else if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {


                            jsonObject.put("FEMALE WITH BABY", getString(R.string.FEMALE_WITH_BABY));
                            jsonObject.put("FEMALE WITH BABY_WEIGHT", weight_in_grams);
                            jsonObject.put("FEMALE WITH BABY_QTY", 1);

                        } else {
                            jsonObject.put("OTHERS", "OTHERS");
                            jsonObject.put("OTHERS_WEIGHT", weight_in_grams);
                            jsonObject.put("OTHERS_QTY", 1);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    breedWise_ItemHashmap.put(modal_goatEarTagDetails.getBreedtype(), jsonObject);
                } else {
                    JSONObject jsonObject = breedWise_ItemHashmap.get(modal_goatEarTagDetails.getBreedtype());
                    JSONObject breedwise_weighttotal_Json = breedwise_total_weight_Hashmap.get(modal_goatEarTagDetails.getBreedtype());
                    try {
                        if (breedwise_weighttotal_Json.has("WEIGHT")) {
                            //Total Breedwise Weight
                            String weightinString_fromJSON = breedwise_weighttotal_Json.getString("WEIGHT");
                            weightinString_fromJSON = weightinString_fromJSON.replaceAll("[^\\d.]", "");

                            if(weightinString_fromJSON.equals("") || weightinString_fromJSON.equals(null)){
                                weightinString_fromJSON = "0";
                            }

                            double weightinDouble_fromJSON = Double.parseDouble(weightinString_fromJSON);

                            double totalWeight = weightinDouble_fromJSON + weightinDouble_fromDB;
                            breedwise_weighttotal_Json.put("WEIGHT", String.valueOf(totalWeight));


                            //Total Breedwise Quantity

                            int quantityinInt_fromJSON = breedwise_weighttotal_Json.getInt("QUANTITY");
                            int totalQuantity = quantityinInt_fromJSON + 1;
                            breedwise_weighttotal_Json.put("QUANTITY", totalQuantity);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (Objects.requireNonNull(jsonObject).has(modal_goatEarTagDetails.getGender().toUpperCase())) {


                            if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))) {


                                if (jsonObject.has("MALE_WEIGHT")) {
                                    try {
                                        //Weight
                                        String weightinString_fromJSON = jsonObject.getString("MALE_WEIGHT");
                                        weightinString_fromJSON = weightinString_fromJSON.replaceAll("[^\\d.]", "");
                                        if(weightinString_fromJSON.equals("") || weightinString_fromJSON.equals(null)){
                                            weightinString_fromJSON = "0";
                                        }

                                        double weightinDouble_fromJSON = Double.parseDouble(weightinString_fromJSON);

                                        double totalWeight = weightinDouble_fromJSON + weightinDouble_fromDB;

                                        jsonObject.put("MALE_WEIGHT", String.valueOf(totalWeight));

                                        //Quantity
                                        int quantityinInt_fromJSON = jsonObject.getInt("MALE_QTY");
                                        int totalQuantity = quantityinInt_fromJSON + 1;
                                        jsonObject.put("MALE_QTY", totalQuantity);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    jsonObject.put("MALE", getString(R.string.MALE));
                                    jsonObject.put("MALE_WEIGHT", weight_in_grams);
                                    jsonObject.put("MALE_QTY", 1);
                                }
                            } else if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))) {


                                if (jsonObject.has("FEMALE_WEIGHT")) {
                                    //Weight
                                    try {
                                        String weightinString_fromJSON = jsonObject.getString("FEMALE_WEIGHT");
                                        weightinString_fromJSON = weightinString_fromJSON.replaceAll("[^\\d.]", "");
                                        if(weightinString_fromJSON.equals("") || weightinString_fromJSON.equals(null)){
                                            weightinString_fromJSON = "0";
                                        }

                                        double weightinDouble_fromJSON = Double.parseDouble(weightinString_fromJSON);

                                        double totalWeight = weightinDouble_fromJSON + weightinDouble_fromDB;

                                        jsonObject.put("FEMALE_WEIGHT", String.valueOf(totalWeight));

                                        //Quantity
                                        int quantityinInt_fromJSON = jsonObject.getInt("FEMALE_QTY");
                                        int totalQuantity = quantityinInt_fromJSON + 1;
                                        jsonObject.put("FEMALE_QTY", totalQuantity);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                } else {


                                    jsonObject.put("FEMALE", getString(R.string.FEMALE));
                                    jsonObject.put("FEMALE_WEIGHT", weight_in_grams);
                                    jsonObject.put("FEMALE_QTY", 1);
                                }


                            } else if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {


                                if (jsonObject.has("FEMALE WITH BABY_WEIGHT")) {
                                    try {
                                        //Weight
                                        String weightinString_fromJSON = jsonObject.getString("FEMALE WITH BABY_WEIGHT");
                                        weightinString_fromJSON = weightinString_fromJSON.replaceAll("[^\\d.]", "");

                                        if(weightinString_fromJSON.equals("") || weightinString_fromJSON.equals(null)){
                                            weightinString_fromJSON = "0";
                                        }

                                        double weightinDouble_fromJSON = Double.parseDouble(weightinString_fromJSON);

                                        double totalWeight = weightinDouble_fromJSON + weightinDouble_fromDB;

                                        jsonObject.put("FEMALE WITH BABY_WEIGHT", String.valueOf(totalWeight));

                                        //Quantity
                                        int quantityinInt_fromJSON = jsonObject.getInt("FEMALE WITH BABY_QTY");
                                        int totalQuantity = quantityinInt_fromJSON + 1;
                                        jsonObject.put("FEMALE WITH BABY_QTY", totalQuantity);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                } else {

                                    jsonObject.put("FEMALE WITH BABY", getString(R.string.FEMALE_WITH_BABY));
                                    jsonObject.put("FEMALE WITH BABY_WEIGHT", weight_in_grams);
                                    jsonObject.put("FEMALE WITH BABY_QTY", 1);
                                }

                            }
                        } else {
                            if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.MALE))) {


                                jsonObject.put("MALE", getString(R.string.MALE));
                                jsonObject.put("MALE_WEIGHT", weight_in_grams);
                                jsonObject.put("MALE_QTY", 1);
                            } else if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE))) {

                                jsonObject.put("FEMALE", getString(R.string.FEMALE));
                                jsonObject.put("FEMALE_WEIGHT", weight_in_grams);
                                jsonObject.put("FEMALE_QTY", 1);
                            } else if (modal_goatEarTagDetails.getGender().toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {


                                jsonObject.put("FEMALE WITH BABY", getString(R.string.FEMALE_WITH_BABY));
                                jsonObject.put("FEMALE WITH BABY_WEIGHT", weight_in_grams);
                                jsonObject.put("FEMALE WITH BABY_QTY", 1);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }





        if (iterator == (earTagItemsForBatch.size() - 1)) {
            if(activityCalledFrom.equals(getString(R.string.delivery_center)) || activityCalledFrom.equals(getString(R.string.delivery_center_batchDetails))) {
                for (int iterator1 = 0; iterator1 < goatDead_Status_earTagItem.size(); iterator1++) {
                    Modal_GoatEarTagDetails modal_goatEarTagDetails1 = goatDead_Status_earTagItem.get(iterator1);

                    goatDead_Qty = goatDead_Qty + 1;
                    String goatWeight = (modal_goatEarTagDetails1.getCurrentweightingrams());
                    if(goatWeight.equals("") || goatWeight.equals(null)){
                        goatWeight = "0";
                    }

                    goatWeight = goatWeight.replaceAll("[^\\d.]", "");
                    double weightinDouble_fromDB = Double.parseDouble(goatWeight);
                    goatDead_Weight = goatDead_Weight + weightinDouble_fromDB;

                }

                for (int iterator2 = 0; iterator2 < goatearTagLost_Status_earTagItem.size(); iterator2++) {
                    Modal_GoatEarTagDetails modal_goatEarTagDetails2 = goatearTagLost_Status_earTagItem.get(iterator2);

                    earTagLost_Qty = earTagLost_Qty + 1;
                    String goatWeight = (modal_goatEarTagDetails2.getCurrentweightingrams());

                    if(goatWeight.equals("") || goatWeight.equals(null)){
                        goatWeight = "0";
                    }
                    goatWeight = goatWeight.replaceAll("[^\\d.]", "");
                    double weightinDouble_fromDB = Double.parseDouble(goatWeight);
                    earTagLost_Weight = earTagLost_Weight + weightinDouble_fromDB;


                }

                for (int iterator3 = 0; iterator3 < goatLost_Status_earTagItem.size(); iterator3++) {
                    Modal_GoatEarTagDetails modal_goatEarTagDetails3 = goatLost_Status_earTagItem.get(iterator3);

                    goatLost_Qty = goatLost_Qty + 1;
                    String goatWeight = (modal_goatEarTagDetails3.getCurrentweightingrams());
                    if(goatWeight.equals("") || goatWeight.equals(null)){
                        goatWeight = "0";
                    }
                    goatWeight = goatWeight.replaceAll("[^\\d.]", "");
                    double weightinDouble_fromDB = Double.parseDouble(goatWeight);
                    goatLost_Weight = goatLost_Weight + weightinDouble_fromDB;
                }

                for (int iterator3 = 0; iterator3 < goatSick_Status_earTagItem.size(); iterator3++) {
                    Modal_GoatEarTagDetails modal_goatEarTagDetails3 = goatSick_Status_earTagItem.get(iterator3);

                    goatSick_Qty = goatSick_Qty + 1;
                    String goatWeight = (modal_goatEarTagDetails3.getCurrentweightingrams());
                    if(goatWeight.equals("") || goatWeight.equals(null)){
                        goatWeight = "0";
                    }
                    goatWeight = goatWeight.replaceAll("[^\\d.]", "");
                    double weightinDouble_fromDB = Double.parseDouble(goatWeight);
                    goatSick_Weight = goatSick_Weight + weightinDouble_fromDB;
                }

                for (int iterator3 = 0; iterator3 < goatSold_Status_earTagItem.size(); iterator3++) {
                    Modal_GoatEarTagDetails modal_goatEarTagDetails3 = goatSold_Status_earTagItem.get(iterator3);

                    goatSold_Qty = goatSold_Qty + 1;
                    String goatWeight = (modal_goatEarTagDetails3.getCurrentweightingrams());
                    if(goatWeight.equals("") || goatWeight.equals(null)){
                        goatWeight = "0";
                    }

                    goatWeight = goatWeight.replaceAll("[^\\d.]", "");
                    double weightinDouble_fromDB = Double.parseDouble(goatWeight);
                    goatSold_Weight = goatSold_Weight + weightinDouble_fromDB;
                }

                prepareData_AND_DisplayIt();

            }
            else {
                prepareData_AND_DisplayIt();
            }
        }
        }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void prepareData_AND_DisplayIt() {
        try {
            dataList.clear();
            try {
                Collections.sort(breedType_arrayList, new Comparator<String>() {
                    public int compare(final String object1, final String object2) {
                        return object1.compareTo(object2);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            try{
                itemCount_textview.setText(String.valueOf(earTagItemsForBatch.size()));
            }
            catch (Exception e){
                e.printStackTrace();
            }
            if(activityCalledFrom.equals(getString(R.string.delivery_center)) || activityCalledFrom.equals(getString(R.string.delivery_center_batchDetails))) {
                try{
                    itemCount_textview_deliveryCenter.setText(String.valueOf(earTagItemsForBatch.size()));
                    itemunscannedCount_textview_deliveryCenter.setText(String.valueOf(totalUnscannedItem));
                    scannedItems_textview_deliveryCenter.setText(String.valueOf(totalScannedItem));
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                if(goatLost_Qty > 0 || goatDead_Qty > 0 ||  goatSick_Qty >0 || goatSold_Qty > 0){
                    final_statuswise_totalValueCardView.setVisibility(View.VISIBLE);
                }
                else{
                    final_statuswise_totalValueCardView.setVisibility(View.GONE);
                }



                if(earTagLost_Qty >0 ) {
                    earTagtotalwise_layout.setVisibility(View.GONE);
                    try {
                        earTag_Qty_textview.setText(String.valueOf(earTagLost_Qty));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    earTagtotalwise_layout.setVisibility(View.GONE);
                }



                if(goatDead_Qty>0){
                try {
                    goatDeadtotal_layout.setVisibility(View.VISIBLE);
                    goatDead_weight_textview.setText(String.valueOf(df.format(goatDead_Weight)) + "Kg");
                    goatDead_Qty_textview.setText(String.valueOf(goatDead_Qty));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                }
                else{
                    goatDeadtotal_layout.setVisibility(View.GONE);
                }



                if(goatLost_Qty>0) {
                    goatLosttotal_layout.setVisibility(View.VISIBLE);
                    try {
                        goatLost_Qty_textview.setText(String.valueOf(goatLost_Qty));
                        goatLost_Weight_textview.setText(String.valueOf(df.format(goatLost_Weight)) + "Kg");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    goatLosttotal_layout.setVisibility(View.GONE);

                }
            }


            if(goatSick_Qty>0) {
                goatSicktotal_Layout.setVisibility(View.VISIBLE);
                try {
                    goatsickQty_textview.setText(String.valueOf(goatSick_Qty));
                    goatSick_weight_textview.setText(String.valueOf(df.format(goatSick_Weight)) + "Kg");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                goatSicktotal_Layout.setVisibility(View.GONE);

            }

            if(goatSold_Qty>0) {
                goatSoldtotal_layout.setVisibility(View.GONE);
                try {
                    goatSold_Qty_textview.setText(String.valueOf(goatSold_Qty));
                    goatSold_weight_textview.setText(String.valueOf(df.format(goatSold_Weight)) + "Kg");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                goatSoldtotal_layout.setVisibility(View.GONE);

            }

        try{
            male_weight_textview.setText(String.valueOf(df.format(totalmale_weight)));
            female_weight_textview.setText(String.valueOf(df.format(totalFemale_weight)));
            femalewithbaby_weight_textview.setText(String.valueOf(df.format(totalFemaleWithBaby_weight)));
        }
        catch (Exception e){
            e.printStackTrace();
        }


            try{
                male_Qty_textview.setText(String.valueOf(maleCount));
                female_Qty_textview.setText(String.valueOf(femaleCount));
                femalewithbaby_Qty_textview.setText(String.valueOf(femaleWithBabyCount));
            }
            catch (Exception e){
                e.printStackTrace();
            }
            showProgressBar(false);

       /*     if(breedType_arrayList.size()>0){
                for(int iterator = 0; iterator < breedType_arrayList.size(); iterator++){

                    JSONObject jsonObject = breedWise_ItemHashmap.get(breedType_arrayList.get(iterator));
                    JSONObject totalWeight_Json = breedwise_total_weight_Hashmap.get(breedType_arrayList.get(iterator));


                   /* try {
                        if (Objects.requireNonNull(jsonObject).length() > 0) {
                            //title
                            ListSection listSection = new ListSection();
                            try {
                                if (!listSection.getTitle().equals(breedType_arrayList.get(iterator))) {
                                    listSection.setTitle(breedType_arrayList.get(iterator) + " ( " + String.valueOf(totalWeight_Json.get("QUANTITY")) + " ) ");
                                    listSection.setTotalAmount(String.valueOf(decimalFormat.format(Double.parseDouble(String.valueOf(totalWeight_Json.get("WEIGHT"))))) + " Kg");
                                    dataList.add(listSection);


                                }
                            } catch (Exception e) {
                                listSection.setTitle(breedType_arrayList.get(iterator) + " ( " + String.valueOf(totalWeight_Json.get("QUANTITY")) + " ) ");
                                listSection.setTotalAmount(String.valueOf(decimalFormat.format(Double.parseDouble(String.valueOf(totalWeight_Json.get("WEIGHT"))))) + " Kg");
                                dataList.add(listSection);
                                try {
                                    total_WeightDouble = total_WeightDouble + Double.parseDouble(String.valueOf(totalWeight_Json.get("WEIGHT")));
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                            }
                            try {
                                if (jsonObject.has(getString(R.string.MALE))) {
                                    //item
                                    ListItem listItem = new ListItem();
                                    listItem.setMessage(jsonObject.getString(getString(R.string.MALE)) + " - " + String.valueOf(jsonObject.getInt("MALE_QTY")));
                                    listItem.setMessageLine2(String.valueOf(decimalFormat.format(Double.parseDouble(jsonObject.getString("MALE_WEIGHT")))) + " Kg");
                                    dataList.add(listItem);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                if (jsonObject.has(getString(R.string.FEMALE))) {
                                    //item
                                    ListItem listItem = new ListItem();
                                    listItem.setMessage(jsonObject.getString(getString(R.string.FEMALE)) + " - " + String.valueOf(jsonObject.getInt("FEMALE_QTY")));
                                    listItem.setMessageLine2(String.valueOf(decimalFormat.format(Double.parseDouble(jsonObject.getString("FEMALE_WEIGHT")))) + " Kg");
                                    dataList.add(listItem);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                if (jsonObject.has(getString(R.string.FEMALE_WITH_BABY))) {
                                    //item
                                    ListItem listItem = new ListItem();
                                    listItem.setMessage(jsonObject.getString(getString(R.string.FEMALE_WITH_BABY)) + " - " + String.valueOf(jsonObject.getInt("FEMALE WITH BABY_QTY")));
                                    listItem.setMessageLine2(String.valueOf(decimalFormat.format(Double.parseDouble(jsonObject.getString("FEMALE WITH BABY_WEIGHT")))) + " Kg");
                                    dataList.add(listItem);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {

                                if (jsonObject.has("OTHERS")) {
                                    //item
                                    ListItem listItem = new ListItem();
                                    listItem.setMessage(jsonObject.getString("OTHERS") + " - " + String.valueOf(jsonObject.getInt("OTHERS_QTY")));
                                    listItem.setMessageLine2(String.valueOf(decimalFormat.format(Double.parseDouble(jsonObject.getString("OTHERS_WEIGHT")))) + " Kg");
                                    dataList.add(listItem);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    if(iterator == (breedType_arrayList.size()-1)){
                        setAdapter();
                    }


                }
            }
            else{
                setAdapter();
            }


        */



        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {
        try {
            itemWeight_textview.setText(String.valueOf(decimalFormat.format(total_WeightDouble))+" Kg");
            Adapter_BatchWise_ConsolidatedReport adapter = new Adapter_BatchWise_ConsolidatedReport(FinishBatch_ConsolidatedReport.this, dataList);
            batchwiseConsolidatedReport_listview.setAdapter(adapter);

        }
        catch(Exception e){
            e.printStackTrace();
        }

        try {
            showProgressBar(false);
            ListItemSizeSetter.getListViewSize(batchwiseConsolidatedReport_listview);
            scrollView.fullScroll(View.FOCUS_UP);
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }

    public  void showProgressBar(boolean show) {

        if(show){
            loadingPanel.setVisibility(View.VISIBLE);
            loadingpanelmask.setVisibility(View.VISIBLE);
            count_loadingAnim_layout.setVisibility(View.VISIBLE);
        }
        else{
            loadingPanel.setVisibility(View.GONE);
            loadingpanelmask.setVisibility(View.GONE);
            count_loadingAnim_layout.setVisibility(View.GONE);

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    private void neturalizeAllArraysAndValues() {
        batchno="" ; supplierKey ="" ;
        selectedDeliveryCenterKey ="";selectedDeliveryCenterName="";
        breedwise_total_weight_Hashmap.clear();
        dataList.clear();
        breedType_arrayList.clear();
        breedWise_ItemHashmap.clear();
        earTagItemsForBatch.clear();
        goatDead_Status_earTagItem.clear();
        goatLost_Status_earTagItem.clear();
        goatearTagLost_Status_earTagItem.clear();
        goatSick_Status_earTagItem.clear();
        goatSold_Status_earTagItem.clear();
        totalScannedItem =0;
        totalUnscannedItem =0;
        total_WeightDouble =0 ;
        goatDead_Weight =0; goatDead_Qty = 0;goatLost_Weight =0; goatSick_Qty=0; goatSick_Weight =0 ; goatSold_Qty =0 ; goatSold_Weight =0;
        goatLost_Qty =0 ; earTagLost_Weight =0; earTagLost_Qty =0 ;
        isGoatEarTagDetailsTableServiceCalled = false;
        isBatchDetailsTableServiceCalled = false;

    }

}