package com.tmc.tmcb2bpartnerapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.utils.BarcodeScannerScreen;
import com.tmc.tmcb2bpartnerapp.activity.View_or_Edit_BatchItem_deliveryCenter;
import com.tmc.tmcb2bpartnerapp.adapter.Adapter_goatGradeDetailsSpinnerItems;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BItemCtgy;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagTransaction;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BGoatGradeDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BItemCtgyInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.BarcodeScannerInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagTransactionInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_AppUserAccess;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetailsStatic;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagTransaction;
import com.tmc.tmcb2bpartnerapp.modal.Modal_Static_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_SupplierDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_UpdatedGoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.TMCAlertDialogClass;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;
import static com.tmc.tmcb2bpartnerapp.activity.View_or_Edit_BatchItem_deliveryCenter.showProgressBar;


public class BatchItemDetailsDeliveryCenterScreenfragment extends Fragment {
   public BatchItemDetailsDeliveryCenterScreenfragment() { }

    TextView selectedGrade_textview,chooseGrade_label,toolBarHeader_TextView,barcodeNo_textView,loaded_weight_ingrams_textview,gender_textview,breedType_textView;
    ImageView backButton_icon;
    Context mContext;
    Button save_button;
    String calledFrom,scannedBarcode="",selectedCategoryItem="",selectedGender="",selectedGoatStatus ="" ,selectedBreed="" , selectedGradeName ="" , selectedGradePrice = "",
            selectedGradeKey = "" , userType ="" ,batchNo ="" , batchStatus ="",userMobileNo ="", addedStatusInDB = "",previousSelectedGender="",deliveryCenterKey="" , deliveryCenterName ="";
    String selectedgoatGrade_inarrayList = "";

    CardView scanBarcode_view;
    public static BarcodeScannerInterface barcodeScannerInterface = null;
    Spinner chooseItem_spinner,breedType_spinner,chooseGrade_spinner;
    ArrayList<String> itemCategory_arrayList = new ArrayList<>();
    ArrayList<String> breedType_arrayList_string = new ArrayList<>();
    EditText description_edittext,weightDetails_edittext;
    private RadioGroup genderRadioGroup , goatStatus_radio_group;
    GoatEarTagDetailsInterface callback_GoatEarTagDetails = null;
    boolean  isGoatEarTagDetailsTableServiceCalled = false;
    LinearLayout UneditableFields_Layout,back_IconLayout;

    boolean isStatusUpdatedFromDB = false;
    GoatEarTagTransactionInterface callback_GoatEarTagTransactionInterface = null;
    boolean  isGoatEarTagTransactionTableServiceCalled = false;
    RadioButton dead_goat_radio, sick_goat_radio,normal_goat_radio,sold_goat_radio;
    RadioButton male_radioButton,female_radioButton,female_WithBaby_radioButton;
    double entered_Weight_double = 0 ;
    String previous_WeightInGrams  = "0" , lastlySelectedBreedType ="";
    DecimalFormat df = new DecimalFormat(Constants.threeDecimalPattern);

    B2BItemCtgyInterface callback_B2BItemCtgyInterface = null;
    boolean  isB2BItemCtgyTableServiceCalled = false;
    HashMap<String,Double> earTagWeightListWithBarcodeAsKey = new HashMap<>();
    B2BGoatGradeDetailsInterface callback_goatGradeDetailsInterface = null;
    boolean isGoatGradeDetailsServiceCalled = false;
    ArrayList<Modal_B2BGoatGradeDetails> goatGrade_arrayLsit = new ArrayList<>();



    public static BatchItemDetailsDeliveryCenterScreenfragment newInstance(String key, String value) {
        BatchItemDetailsDeliveryCenterScreenfragment fragment = new BatchItemDetailsDeliveryCenterScreenfragment();
        Bundle args = new Bundle();
        args.putString(key, value);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity().getWindow().getContext();


        if (getArguments() != null) {
            calledFrom = getArguments().getString(getString(R.string.called_from));
        }
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        try {
            BaseActivity.baseActivity.getDeviceName();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            if (BaseActivity.isDeviceIsMobilePhone) {
                // Inflate the layout for this fragment
                return inflater.inflate(R.layout.fragment_batch_item_details_delivery_center_screenfragment, container, false);
            } else {

                // Inflate the layout for this fragment
                return inflater.inflate(R.layout.pos_fragment_batch_item_details_delivery_center_screenfragment, container, false);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return inflater.inflate(R.layout.fragment_batch_item_details_delivery_center_screenfragment, container, false);

        }





    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        toolBarHeader_TextView = view.findViewById(R.id.toolBarHeader_TextView);
        backButton_icon = view.findViewById(R.id.backButton_icon);
        save_button = view.findViewById(R.id.save_button);
        scanBarcode_view = view.findViewById(R.id.scanBarcode_view);
        barcodeNo_textView = view.findViewById(R.id.barcodeNo_textView);
        weightDetails_edittext = view.findViewById(R.id.weightDetails_edittext);
        chooseItem_spinner = view.findViewById(R.id.chooseItem_spinner);
        genderRadioGroup = view.findViewById(R.id.genderRadioGroup);
        breedType_spinner = view.findViewById(R.id.breedType_spinner);
        description_edittext = view.findViewById(R.id.description_edittext);
        female_radioButton = view.findViewById(R.id.female_radioButton);
        male_radioButton = view.findViewById(R.id.male_radioButton);
        female_WithBaby_radioButton = view.findViewById(R.id.female_WithBaby_radioButton);
        normal_goat_radio = view.findViewById(R.id.normal_goat_radio);
        dead_goat_radio = view.findViewById(R.id.dead_goat_radio);
        sick_goat_radio = view.findViewById(R.id.sick_goat_radio);
        goatStatus_radio_group = view.findViewById(R.id.goatstatusradiogrp);
        UneditableFields_Layout = view.findViewById(R.id.UneditableFields_Layout);
        back_IconLayout = view.findViewById(R.id.back_IconLayout);
        loaded_weight_ingrams_textview  = view.findViewById(R.id.loaded_weight_ingrams_textview);
        gender_textview = view.findViewById(R.id.gender_textview);
        breedType_textView = view.findViewById(R.id.breedType_textView);
        sold_goat_radio = view.findViewById(R.id.sold_goat_radio);
        chooseGrade_spinner = view.findViewById(R.id.chooseGrade_spinner);
        chooseGrade_label = view.findViewById(R.id.chooseGrade_label);
        selectedGrade_textview = view.findViewById(R.id.selectedGrade_textview);


        breedType_arrayList_string.clear();



        showProgressBar(false);


        SharedPreferences sh = mContext.getSharedPreferences("LoginData", MODE_PRIVATE);
        userType = sh.getString("UserType", "");
        userMobileNo = sh.getString("UserMobileNumber", "");


        SharedPreferences sh1 = mContext.getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);
        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");

        SharedPreferences sh2 = mContext.getSharedPreferences("LastlyCachedData",MODE_PRIVATE);
        lastlySelectedBreedType = sh2.getString("LastlySelectedBreedType","");

        batchNo = String.valueOf( Modal_B2BBatchDetailsStatic.getBatchno());

        batchStatus = String.valueOf( Modal_B2BBatchDetailsStatic.getStatus().toUpperCase());
        if(calledFrom.equals(getString(R.string.stock_batch_item_withoutScan)) || calledFrom.equals(getString(R.string.stock_batch_item_withoutScan_allowedScan))){

            if (DatabaseArrayList_PojoClass.goatGradeDetailsArrayList.size() == 0) {
                try {
                    Call_and_Initialize_GoatGradeDetails(Constants.CallGETListMethod);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                goatGrade_arrayLsit = DatabaseArrayList_PojoClass.getGoatGradeDetailsArrayList();
                setAdapterForgoatGradeArrayList();
            }


            GetDataFromPOJO_and_updateUI();
        }

        if (calledFrom.equals(getString(R.string.stock_batch_item_withoutScan))) {
            scanBarcode_view.setVisibility(View.GONE);
        }
        else{
            scanBarcode_view.setVisibility(View.VISIBLE);

        }

            if (batchStatus.toUpperCase().equals(Constants.batchDetailsStatus_Fully_Loaded) || batchStatus.toUpperCase().equals(Constants.batchDetailsStatus_Reviewing)) {
            sold_goat_radio.setVisibility(View.GONE);

        }
        else
        {
            sold_goat_radio.setVisibility(View.GONE);
        }


        if(DatabaseArrayList_PojoClass.breedType_arrayList_string.size()>0){
            setAdapterForBreedTypeSpinner();

        }
        else{
            Initialize_and_ExecuteB2BCtgyItem();
        }



        itemCategory_arrayList.add("Goat");
        ArrayAdapter aAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, itemCategory_arrayList);
        chooseItem_spinner.setAdapter(aAdapter);

    //    toolBarHeader_TextView.setText(calledFrom);




    /*    if (Modal_B2BBatchDetailsStatic.getStatus().equals(Constants.batchDetailsStatus_UnLoading) || Modal_B2BBatchDetailsStatic.getStatus().equals(Constants.batchDetailsStatus_InTransit)) {
            save_button.setVisibility(View.VISIBLE);
        } else {
            save_button.setVisibility(View.GONE);

        }
        if(calledFrom.equals(getString(R.string.view_stockedBatch_item))){
            save_button.setVisibility(View.GONE);
        }
        else{
            save_button.setVisibility(View.VISIBLE);
        }
        */


        back_IconLayout.setOnClickListener(view1 ->
                ((View_or_Edit_BatchItem_deliveryCenter)getActivity()).closeFragment());


        save_button.setOnClickListener(view1 ->{
            entered_Weight_double = 0;
            try {
                String  entered_Weight = weightDetails_edittext.getText().toString();
                entered_Weight = entered_Weight.replaceAll("[^\\d.]", "");
                if(entered_Weight.equals("") || entered_Weight.equals(null)){
                    entered_Weight = "0";
                }

                entered_Weight_double = Double.parseDouble(entered_Weight);



            }
            catch (Exception e){
                e.printStackTrace();
            }

            try{
                if(selectedGoatStatus.toUpperCase().equals(getString(R.string.good)) || selectedGoatStatus.toUpperCase().equals(getString(R.string.sold))){
                    if (entered_Weight_double > 0) {
                        if (scannedBarcode.length() > 0) {
                            if (selectedCategoryItem.length() > 0) {
                                if (selectedBreed.length() > 0) {
                                    if (selectedGender.length() > 0) {

                                       // Toast.makeText(mContext, "Item Saved Successfully", Toast.LENGTH_SHORT).show();
                                       // ((View_or_Edit_BatchItem_deliveryCenter) requireActivity()).closeFragment();

                                        if (calledFrom.equals(getString(R.string.stock_batch_item))) {
                                            previous_WeightInGrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();

                                            Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);
                                           // Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod, Constants.CallUPDATEMethod);
                                        }
                                        else  if (calledFrom.equals(getString(R.string.stock_batch_item_withoutScan))) {
                                            previous_WeightInGrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();

                                            Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);
                                            // Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod, Constants.CallUPDATEMethod);
                                        }
                                        else  if (calledFrom.equals(getString(R.string.stock_batch_item_withoutScan_allowedScan))) {
                                            previous_WeightInGrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();

                                            Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);
                                            // Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod, Constants.CallUPDATEMethod);
                                        }
                                        else if (calledFrom.equals(getString(R.string.view_stockedBatch_item))) {
                                            previous_WeightInGrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();

                                            Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);
                                            // Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallUPDATEMethod,Constants.CallUPDATEMethod);
                                        }

                                    } else {
                                        AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWithOutGenderAlert);

                                    }
                                } else {
                                    AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWithOutBreedTypeAlert);

                                }
                            } else {
                                AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWithOutItemnameAlert);

                            }
                        } else {
                            AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWithOutBarcodeAlert);

                        }
                    } else {
                        AlertDialogClass.showDialog(getActivity(), R.string.please_enter_correctweight);

                    }
                }
                else {


                    new TMCAlertDialogClass(mContext, R.string.app_name, R.string.Goat_Status_is_Not_good,
                            R.string.Yes_Text, R.string.No_Text,
                            new TMCAlertDialogClass.AlertListener() {
                                @Override
                                public void onYes() {
                                    if (entered_Weight_double > 0) {
                                        if (scannedBarcode.length() > 0) {
                                            if (selectedCategoryItem.length() > 0) {
                                                if (selectedBreed.length() > 0) {
                                                    if (selectedGender.length() > 0) {

                                                       // Toast.makeText(mContext, "Item Saved Successfully", Toast.LENGTH_SHORT).show();
                                                        //((View_or_Edit_BatchItem_deliveryCenter) requireActivity()).closeFragment();

                                                        if (calledFrom.equals(getString(R.string.stock_batch_item))) {
                                                            previous_WeightInGrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();

                                                            Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);
                                                            // Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod, Constants.CallUPDATEMethod);
                                                        }
                                                        else  if (calledFrom.equals(getString(R.string.stock_batch_item_withoutScan))) {
                                                            previous_WeightInGrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();

                                                            Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);
                                                            // Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod, Constants.CallUPDATEMethod);
                                                        }
                                                        else  if (calledFrom.equals(getString(R.string.stock_batch_item_withoutScan_allowedScan))) {
                                                            previous_WeightInGrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();

                                                            Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);
                                                            // Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod, Constants.CallUPDATEMethod);
                                                        }
                                                        else if (calledFrom.equals(getString(R.string.view_stockedBatch_item))) {
                                                            previous_WeightInGrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();

                                                            Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);
                                                            // Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallUPDATEMethod,Constants.CallUPDATEMethod);
                                                        }


                                                    } else {
                                                        AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWithOutGenderAlert);

                                                    }
                                                } else {
                                                    AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWithOutBreedTypeAlert);

                                                }
                                            } else {
                                                AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWithOutItemnameAlert);

                                            }
                                        } else {
                                            AlertDialogClass.showDialog(getActivity(), R.string.CannotSaveWithOutBarcodeAlert);

                                        }
                                    } else {
                                        AlertDialogClass.showDialog(getActivity(), R.string.please_enter_correctweight);

                                    }
                                }

                                @Override
                                public void onNo() {


                                }
                            });



                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

        });

        scanBarcode_view.setOnClickListener(view13 -> {

                Initialize_and_StartBarcodeScanner(getString(R.string.scannerCalled_to_FetchData));




        });

        chooseGrade_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedGradeName = goatGrade_arrayLsit.get(position).getName();
                selectedGradePrice = goatGrade_arrayLsit.get(position).getPrice();
                selectedGradeKey =  goatGrade_arrayLsit.get(position).getKey();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        chooseItem_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedCategoryItem = itemCategory_arrayList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        breedType_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedBreed= String.valueOf(breedType_arrayList_string.get(position));

                SharedPreferences sharedPreferences
                        = mContext.getSharedPreferences("LastlyCachedData",MODE_PRIVATE);

                SharedPreferences.Editor myEdit
                        = sharedPreferences.edit();

                myEdit.putString(
                        "LastlySelectedBreedType", selectedBreed
                );
                myEdit.apply();



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        genderRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            // on below line we are getting radio button from our group.
            RadioButton radioButton = view.findViewById(checkedId);
            selectedGender =  String.valueOf(radioButton.getText());
            // on below line we are displaying a toast message.
          //  Toast.makeText(mContext, "Selected Gender is : " + radioButton.getText(), Toast.LENGTH_SHORT).show();
        });

      /*
        goatStatus_radio_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                isStatusUpdatedFromDB = false;
            }
        });
        goatStatus_radio_group.setOnCheckedChangeListener((group, checkedId) -> {

            // on below line we are getting radio button from our group.
            RadioButton radioButton = view.findViewById(checkedId);
            selectedGoatStatus =  String.valueOf(radioButton.getText());
            if(!isStatusUpdatedFromDB) {
                if (!selectedGoatStatus.toUpperCase().equals(getString(R.string.good))) {
                    new TMCAlertDialogClass(mContext, R.string.app_name, R.string.ChangeGoatStatus_Instruction,
                            R.string.Yes_Text, R.string.No_Text,
                            new TMCAlertDialogClass.AlertListener() {
                                @Override
                                public void onYes() {
                               //     Toast.makeText(mContext, "Selected Goat Status : " + radioButton.getText(), Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onNo() {
                                    isStatusUpdatedFromDB = true;
                                    RadioButton radioButton = view.findViewById(R.id.normal_goat_radio);
                                    radioButton.setChecked(true);
                                }
                            });
                }
                // on below line we are displaying a toast message.
            }
        });


       */

        normal_goat_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStatusUpdatedFromDB = false;

            }
        });

        sick_goat_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStatusUpdatedFromDB = false;

            }
        });

        dead_goat_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStatusUpdatedFromDB = false;

            }
        });
        goatStatus_radio_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStatusUpdatedFromDB = false;
            }
        });
        goatStatus_radio_group.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = view.findViewById(checkedId);
            selectedGoatStatus = String.valueOf(radioButton.getText());
            if(!isStatusUpdatedFromDB) {
                if (!selectedGoatStatus.toUpperCase().equals(getString(R.string.good)) && (!selectedGoatStatus.toUpperCase().equals(Constants.goatEarTagStatus_Loading))) {
                    new TMCAlertDialogClass(mContext, R.string.app_name, R.string.ChangeGoatStatus_Instruction,
                            R.string.Yes_Text, R.string.No_Text,
                            new TMCAlertDialogClass.AlertListener() {
                                @Override
                                public void onYes() {
                                    //     Toast.makeText(mContext, "Selected Goat Status : " + radioButton.getText(), Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onNo() {
                                    //   isStatusUpdatedFromDB = true;
                                    RadioButton radioButton = view.findViewById(R.id.normal_goat_radio);
                                    radioButton.setChecked(true);
                                }
                            });
                }
                // on below line we are displaying a toast message.
            }
            else{
                isStatusUpdatedFromDB = false;
            }




        });





        weightDetails_edittext.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String weight = (editable.toString());


                int count = 0;
                char c = '.';
                for (int i = 0; i < weight.length(); i++) {
                    if (weight.charAt(i) == c)
                        count++;
                }
                if (count > 1) {
                    AlertDialogClass.showDialog(getActivity(), R.string.cant_enter_more_thanOne_ponit_weight);
                    String result = weight.substring(0, weight.indexOf("."));
                    weightDetails_edittext.setText(result);
                    weightDetails_edittext.setSelection(weightDetails_edittext.getText().length());
                } else{
                    try {
                        weight = weight.replaceAll("[^\\d.]", "");
                        if(weight.equals("") || weight.equals(null)){
                            weight = "0";
                        }


                        double weight_double = Double.parseDouble(weight);
                        if (weight_double > 50) {

                            AlertDialogClass.showDialog(getActivity(), R.string.EnteredWeightShouldbelessthan50Instruction);
                            weightDetails_edittext.setText("0");
                            weightDetails_edittext.setSelection(weightDetails_edittext.getText().length());
                        } else if (weight_double < 0) {
                            AlertDialogClass.showDialog(getActivity(), R.string.EnteredWeightShouldbegreaterthan0Instruction);
                            weightDetails_edittext.setText("0");
                            weightDetails_edittext.setSelection(weightDetails_edittext.getText().length());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });






    }

    private void setAdapterForBreedTypeSpinner() {

        breedType_arrayList_string = DatabaseArrayList_PojoClass.breedType_arrayList_string;
        ArrayAdapter breedType_aAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, breedType_arrayList_string);
        breedType_spinner.setAdapter(breedType_aAdapter);
        for(int iterator = 0 ;iterator<breedType_arrayList_string.size();iterator++){
            String breedTypeFromarray =  breedType_arrayList_string.get(iterator);
            if(breedTypeFromarray.equals(lastlySelectedBreedType)){
                breedType_spinner.setSelection(iterator);
            }
        }
    }



    private void setAdapterForgoatGradeArrayList() {
       /*
        if(!selectedgoatGrade_inarrayList.equals(selectedGradeKey) || selectedgoatGrade_inarrayList.equals("")) {
            try {
                for (int iterator = 0; iterator < goatGrade_arrayLsit.size(); iterator++) {
                    if (String.valueOf(Modal_Static_GoatEarTagDetails.getGradekey()).equals(goatGrade_arrayLsit.get(iterator).getKey())) {
                        if (String.valueOf(Modal_Static_GoatEarTagDetails.getGradeprice()).equals(goatGrade_arrayLsit.get(iterator).getPrice())) {
                            selectedgoatGrade_inarrayList = (Modal_Static_GoatEarTagDetails.getGradekey());

                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                for (int iterator = 0; iterator < goatGrade_arrayLsit.size(); iterator++) {
                    if (!selectedgoatGrade_inarrayList.equals(Modal_Static_GoatEarTagDetails.getGradekey())) {
                        if (String.valueOf(Modal_Static_GoatEarTagDetails.getGradekey()).equals(goatGrade_arrayLsit.get(iterator).getKey())) {


                            if (iterator - (goatGrade_arrayLsit.size() - 1) == 0) {

                                Modal_B2BGoatGradeDetails modal_b2BGoatGradeDetails = new Modal_B2BGoatGradeDetails();
                                modal_b2BGoatGradeDetails.key = Modal_Static_GoatEarTagDetails.getGradekey();
                                modal_b2BGoatGradeDetails.name = selectedGradeName + " ( Price Edited )";
                                modal_b2BGoatGradeDetails.description = "";
                                modal_b2BGoatGradeDetails.price = Modal_Static_GoatEarTagDetails.getGradeprice();
                                modal_b2BGoatGradeDetails.deliverycentername = deliveryCenterName;
                                modal_b2BGoatGradeDetails.deliverycenterkey = deliveryCenterKey;
                                goatGrade_arrayLsit.add(modal_b2BGoatGradeDetails);
                                selectedgoatGrade_inarrayList = (Modal_Static_GoatEarTagDetails.getGradekey());


                            }
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                Modal_B2BGoatGradeDetails modal_b2BGoatGradeDetails = new Modal_B2BGoatGradeDetails();
                modal_b2BGoatGradeDetails.key = Modal_Static_GoatEarTagDetails.getGradekey();
                modal_b2BGoatGradeDetails.name = "Deleted Entry";
                modal_b2BGoatGradeDetails.price = Modal_Static_GoatEarTagDetails.getGradeprice();
                modal_b2BGoatGradeDetails.deliverycenterkey = deliveryCenterKey;
                modal_b2BGoatGradeDetails.deliverycentername = deliveryCenterName;
                modal_b2BGoatGradeDetails.description = "";
                goatGrade_arrayLsit.add(modal_b2BGoatGradeDetails);
                selectedgoatGrade_inarrayList = (Modal_Static_GoatEarTagDetails.getGradekey());

            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                for (int iterator = 0; iterator < goatGrade_arrayLsit.size(); iterator++) {
                    if (selectedgoatGrade_inarrayList.equals(goatGrade_arrayLsit.get(iterator).getKey())) {
                        selectedGradeKey = goatGrade_arrayLsit.get(iterator).getKey();
                        selectedGradePrice = goatGrade_arrayLsit.get(iterator).getName();
                        selectedGradeName = goatGrade_arrayLsit.get(iterator).getName();
                        chooseGrade_spinner.setSelection(iterator);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


         */

        chooseGrade_spinner.setVisibility(View.VISIBLE);
        selectedGrade_textview.setVisibility(View.GONE);
        chooseGrade_label.setText("Choose Grade Type");
        Adapter_goatGradeDetailsSpinnerItems adapter_goatGradeDetailsSpinnerItems = new Adapter_goatGradeDetailsSpinnerItems(mContext,goatGrade_arrayLsit,"");

        chooseGrade_spinner.setAdapter(adapter_goatGradeDetailsSpinnerItems);
        try {
            for (int iterator = 0; iterator < goatGrade_arrayLsit.size(); iterator++) {
                if (String.valueOf(Modal_Static_GoatEarTagDetails.getGradekey()).equals(goatGrade_arrayLsit.get(iterator).getKey())) {
                    selectedGradeKey = goatGrade_arrayLsit.get(iterator).getKey();
                    selectedGradePrice = goatGrade_arrayLsit.get(iterator).getName();
                    selectedGradeName = goatGrade_arrayLsit.get(iterator).getName();
                    chooseGrade_spinner.setSelection(iterator);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        showProgressBar(false);

    }


    private void GetDataFromPOJO_and_updateUI() {
        try {
            loaded_weight_ingrams_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getLoadedweightingrams()));
            barcodeNo_textView.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getBarcodeno()));
            weightDetails_edittext.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getCurrentweightingrams()));
            description_edittext.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getDescription()));
            breedType_textView.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getBreedtype()));
            gender_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getGender()));
            selectedGrade_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getGradename().toUpperCase() + " - ( Rs. " + Modal_Static_GoatEarTagDetails.getGradeprice()));


            selectedBreed = String.valueOf(Modal_Static_GoatEarTagDetails.getBreedtype());
            selectedGender = String.valueOf(Modal_Static_GoatEarTagDetails.getGender());
            previousSelectedGender = String.valueOf(Modal_Static_GoatEarTagDetails.getGender());
            selectedGoatStatus = String.valueOf(Modal_Static_GoatEarTagDetails.getStatus());
            addedStatusInDB = String.valueOf(Modal_Static_GoatEarTagDetails.getStatus());
            scannedBarcode = String.valueOf(Modal_Static_GoatEarTagDetails.getBarcodeno());
            selectedGradeName = String.valueOf(Modal_Static_GoatEarTagDetails.getGradename());
            selectedGradePrice = String.valueOf(Modal_Static_GoatEarTagDetails.getGradeprice());
            selectedGradeKey = String.valueOf(Modal_Static_GoatEarTagDetails.getGradekey());

            try {
                for (int iterator = 0; iterator < breedType_arrayList_string.size(); iterator++) {
                    if (String.valueOf(Modal_Static_GoatEarTagDetails.getBreedtype()).equals(breedType_arrayList_string.get(iterator))) {
                        breedType_spinner.setSelection(iterator);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }




                try {
                    if (String.valueOf(Modal_Static_GoatEarTagDetails.getGender()).equals(getString(R.string.MALE))) {
                        male_radioButton.setChecked(true);
                        female_radioButton.setChecked(false);
                        female_WithBaby_radioButton.setChecked(false);
                    } else if (String.valueOf(Modal_Static_GoatEarTagDetails.getGender()).equals(getString(R.string.FEMALE))) {
                        male_radioButton.setChecked(false);
                        female_radioButton.setChecked(true);
                        female_WithBaby_radioButton.setChecked(false);
                    } else if (String.valueOf(Modal_Static_GoatEarTagDetails.getGender()).equals(getString(R.string.FEMALE_WITH_BABY))) {
                        male_radioButton.setChecked(false);
                        female_radioButton.setChecked(false);
                        female_WithBaby_radioButton.setChecked(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                //  isStatusUpdatedFromDB = true;

                try {

                    if (String.valueOf(selectedGoatStatus).equals(Constants.goatEarTagStatus_GoatLost)) {
                        dead_goat_radio.setChecked(false);
                        sick_goat_radio.setChecked(false);
                        normal_goat_radio.setChecked(false);
                        sold_goat_radio.setChecked(false);
                    } else if (String.valueOf(selectedGoatStatus).equals(Constants.goatEarTagStatus_EarTagLost)) {
                        dead_goat_radio.setChecked(false);
                        sick_goat_radio.setChecked(false);
                        normal_goat_radio.setChecked(false);
                        sold_goat_radio.setChecked(false);
                    } else if (String.valueOf(selectedGoatStatus).equals(Constants.goatEarTagStatus_Goatdead)) {
                        dead_goat_radio.setChecked(true);
                        sick_goat_radio.setChecked(false);
                        normal_goat_radio.setChecked(false);
                        sold_goat_radio.setChecked(false);
                    } else if (String.valueOf(selectedGoatStatus).equals(Constants.goatEarTagStatus_Goatsick)) {
                        dead_goat_radio.setChecked(false);
                        sick_goat_radio.setChecked(true);
                        normal_goat_radio.setChecked(false);
                        sold_goat_radio.setChecked(false);
                    } else if (String.valueOf(selectedGoatStatus).equals(Constants.goatEarTagStatus_Sold)) {
                        dead_goat_radio.setChecked(false);
                        sick_goat_radio.setChecked(false);
                        normal_goat_radio.setChecked(false);
                        sold_goat_radio.setChecked(true);
                    } else {
                        selectedGoatStatus = getString(R.string.good);
                        dead_goat_radio.setChecked(false);
                        sick_goat_radio.setChecked(false);
                        normal_goat_radio.setChecked(true);
                        sold_goat_radio.setChecked(false);
                    }

                    if (calledFrom.equals(getString(R.string.stock_batch_item_withoutScan_allowedScan))) {
                        if (!Modal_Static_GoatEarTagDetails.getStatus().equals(Constants.goatEarTagStatus_Loading)) {
                            if (Modal_Static_GoatEarTagDetails.getStatus().equals(Constants.goatEarTagStatus_Goatdead)) {
                                try {
                                    AlertDialogClass.showDialog(getActivity(), R.string.GoatDead_Instruction);

                                } catch (Exception e) {
                                    Toast.makeText(mContext, getString(R.string.GoatDead_Instruction), Toast.LENGTH_SHORT).show();

                                    e.printStackTrace();
                                }
                            } else if (Modal_Static_GoatEarTagDetails.getStatus().equals(Constants.goatEarTagStatus_GoatLost)) {
                                try {
                                    AlertDialogClass.showDialog(getActivity(), R.string.GoatLost_Instruction);

                                } catch (Exception e) {
                                    Toast.makeText(mContext, getString(R.string.GoatLost_Instruction), Toast.LENGTH_SHORT).show();

                                    e.printStackTrace();
                                }
                            } else if (Modal_Static_GoatEarTagDetails.getStatus().equals(Constants.goatEarTagStatus_Goatsick)) {
                                try {
                                    AlertDialogClass.showDialog(getActivity(), R.string.GoatSick_Instruction);

                                } catch (Exception e) {
                                    Toast.makeText(mContext, getString(R.string.GoatSick_Instruction), Toast.LENGTH_SHORT).show();

                                    e.printStackTrace();
                                }
                            } else if (Modal_Static_GoatEarTagDetails.getStatus().equals(Constants.goatEarTagStatus_EarTagLost)) {
                                try {
                                    AlertDialogClass.showDialog(getActivity(), R.string.EarTagLost_Instruction);

                                } catch (Exception e) {
                                    Toast.makeText(mContext, getString(R.string.EarTagLost_Instruction), Toast.LENGTH_SHORT).show();

                                    e.printStackTrace();
                                }
                            } else if (Modal_Static_GoatEarTagDetails.getStatus().equals(Constants.goatEarTagStatus_Cancelled)) {
                                try {
                                    AlertDialogClass.showDialog(getActivity(), R.string.EarTagCancelled_Instruction);

                                } catch (Exception e) {
                                    Toast.makeText(mContext, getString(R.string.EarTagCancelled_Instruction), Toast.LENGTH_SHORT).show();

                                    e.printStackTrace();
                                }
                            } else if (Modal_Static_GoatEarTagDetails.getStatus().equals(Constants.goatEarTagStatus_Sold)) {
                                try {
                                    AlertDialogClass.showDialog(getActivity(), R.string.EarTagSold_Instruction);

                                } catch (Exception e) {
                                    Toast.makeText(mContext, getString(R.string.EarTagSold_Instruction), Toast.LENGTH_SHORT).show();

                                    e.printStackTrace();
                                }
                            } else if (Modal_Static_GoatEarTagDetails.getStatus().equals(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE)) {
                                try {
                                    AlertDialogClass.showDialog(getActivity(), R.string.EarTag_Is_AlreadyReviewedInstruction);

                                } catch (Exception e) {
                                    Toast.makeText(mContext, getString(R.string.EarTag_Is_AlreadyReviewedInstruction), Toast.LENGTH_SHORT).show();

                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    AlertDialogClass.showDialog(getActivity(), R.string.EarTagDetailsNotFound_Instruction);

                                } catch (Exception e) {
                                    Toast.makeText(mContext, getString(R.string.EarTagDetailsNotFound_Instruction), Toast.LENGTH_SHORT).show();

                                    e.printStackTrace();
                                }


                            }
                            save_button.setVisibility(View.GONE);
                        } else {
                            save_button.setVisibility(View.VISIBLE);
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
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
                goatGrade_arrayLsit = arrayListt;
                setAdapterForgoatGradeArrayList();
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
            goatGrade_arrayLsit.clear();selectedgoatGrade_inarrayList ="";
            String getApiToCall = API_Manager.getgoatGradeForDeliveryCentreKey +deliveryCenterKey;

            B2BGoatGradeDetails asyncTask = new B2BGoatGradeDetails(callback_goatGradeDetailsInterface,  getApiToCall, Constants.CallGETListMethod);
            asyncTask.execute();



        }



    }



    private void Initialize_and_ExecuteB2BCtgyItem() {

        showProgressBar(true);
        if (isB2BItemCtgyTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isB2BItemCtgyTableServiceCalled = true;
        callback_B2BItemCtgyInterface = new B2BItemCtgyInterface() {
            @Override
            public void notifySuccess(String result) {
                showProgressBar(false);
                isB2BItemCtgyTableServiceCalled = false;
            }

            @Override
            public void notifyError(VolleyError error) {
                showProgressBar(false);
                isB2BItemCtgyTableServiceCalled = false;

            }
        };
        String addApiToCall = API_Manager.getB2BItemCtgy ;
        B2BItemCtgy asyncTask = new B2BItemCtgy(callback_B2BItemCtgyInterface,  addApiToCall );
        asyncTask.execute();




    }





    private void Initialize_and_StartBarcodeScanner(String processtoDOAfterScan) {
        barcodeScannerInterface = new BarcodeScannerInterface() {


            @Override
            public void notifySuccess(String Barcode) {
                scannedBarcode = Barcode;
                barcodeNo_textView.setText(Barcode);
                // Toast.makeText(mContext, "Only Scan", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void notifySuccessAndFetchData(String Barcode) {
                scannedBarcode = Barcode;

                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod);
                // Toast.makeText(mContext, "Scan And Fetch", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void notifyProcessingError(Exception error) {
                // Toast.makeText(mContext, "Error in scanning ", Toast.LENGTH_SHORT).show();


            }
        };


        Intent intent = new Intent(mContext, BarcodeScannerScreen.class);
        intent.putExtra(getString(R.string.scanner_called_to_do), processtoDOAfterScan);
        intent.putExtra(getString(R.string.called_from), getString(R.string.delivery_center));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        mContext.startActivity(intent);



    }




    private void Initialize_and_ExecuteInGoatEarTagDetails(String callMethod) {

        showProgressBar(true);
        if (isGoatEarTagDetailsTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isGoatEarTagDetailsTableServiceCalled = true;
        callback_GoatEarTagDetails = new GoatEarTagDetailsInterface() {


            @Override
            public void notifySuccess(String result) {

                if (callMethod.equals(Constants.CallGETMethod)){
                    isGoatEarTagDetailsTableServiceCalled = false;
                    if(result.equals(Constants.emptyResult_volley)){
                        barcodeNo_textView.setText(scannedBarcode);
                        showProgressBar(false);
                        AlertDialogClass.showDialog(getActivity(), R.string.BatchDetailsnotFound_Instruction);
                        return;
                    }

                    if (String.valueOf(Modal_Static_GoatEarTagDetails.getStatus()).equals(Constants.goatEarTagStatus_GoatLost)) {

                        AlertDialogClass.showDialog(getActivity(), R.string.GoatLost_Instruction);
                        save_button.setVisibility(View.GONE);

                    }
                    else if (String.valueOf(Modal_Static_GoatEarTagDetails.getStatus()).equals(Constants.goatEarTagStatus_EarTagLost)) {
                        save_button.setVisibility(View.GONE);
                        showProgressBar(false);
                        AlertDialogClass.showDialog(getActivity(), R.string.EarTagLost_Instruction);
                    }

                    else if (calledFrom.equals(getString(R.string.view_stockedBatch_item))) {
                        if (String.valueOf(Modal_Static_GoatEarTagDetails.getStatus()).equals(Constants.goatEarTagStatus_Loading)) {
                            showProgressBar(false);
                            AlertDialogClass.showDialog(getActivity(), R.string.notStockedYet_Instruction);
                            return;
                        }
                    }
                     else  if(calledFrom.equals(getString(R.string.stock_batch_item_withoutScan_allowedScan)))
                     {
                        if(!Modal_Static_GoatEarTagDetails.getStatus().equals(Constants.goatEarTagStatus_Loading)){
                            try{
                                AlertDialogClass.showDialog(getActivity(), R.string.GoatEarTagAlreadyCreated_Instruction);

                            }
                            catch (Exception e){
                                Toast.makeText(mContext, getString(R.string.GoatEarTagAlreadyCreated_Instruction), Toast.LENGTH_SHORT).show();

                                e.printStackTrace();
                            }
                            save_button.setVisibility(View.GONE);
                        }
                        else{
                            showProgressBar(false);
                            save_button.setVisibility(View.VISIBLE);
                        }
                    }
                    else{
                        showProgressBar(false);
                        save_button.setVisibility(View.VISIBLE);
                    }






                        loaded_weight_ingrams_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getLoadedweightingrams()));
                        barcodeNo_textView.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getBarcodeno()));
                        weightDetails_edittext.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getCurrentweightingrams()));
                        description_edittext.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getDescription()));
                        breedType_textView.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getBreedtype()));
                        gender_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getGender()));
                        selectedBreed = String.valueOf(Modal_Static_GoatEarTagDetails.getBreedtype());
                        selectedGender = String.valueOf(Modal_Static_GoatEarTagDetails.getGender());
                        previousSelectedGender = String.valueOf(Modal_Static_GoatEarTagDetails.getGender());
                        selectedGoatStatus = String.valueOf(Modal_Static_GoatEarTagDetails.getStatus());
                        selectedGradePrice = String.valueOf(Modal_Static_GoatEarTagDetails.getGradeprice());
                        selectedGradeName = String.valueOf(Modal_Static_GoatEarTagDetails.getGradename());
                        selectedGradeKey = String.valueOf(Modal_Static_GoatEarTagDetails.getGradekey());

                        try {
                            for (int iterator = 0; iterator < breedType_arrayList_string.size(); iterator++) {
                                if (String.valueOf(Modal_Static_GoatEarTagDetails.getBreedtype()).equals(breedType_arrayList_string.get(iterator))) {
                                    breedType_spinner.setSelection(iterator);
                                }
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            if (String.valueOf(Modal_Static_GoatEarTagDetails.getGender()).equals(getString(R.string.MALE))) {
                                male_radioButton.setChecked(true);
                                female_radioButton.setChecked(false);
                                female_WithBaby_radioButton.setChecked(false);
                            } else if (String.valueOf(Modal_Static_GoatEarTagDetails.getGender()).equals(getString(R.string.FEMALE))) {
                                male_radioButton.setChecked(false);
                                female_radioButton.setChecked(true);
                                female_WithBaby_radioButton.setChecked(false);
                            } else if (String.valueOf(Modal_Static_GoatEarTagDetails.getGender()).equals(getString(R.string.FEMALE_WITH_BABY))) {
                                male_radioButton.setChecked(false);
                                female_radioButton.setChecked(false);
                                female_WithBaby_radioButton.setChecked(true);
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                         isStatusUpdatedFromDB = true;

                        try {

                            if (String.valueOf(selectedGoatStatus).equals(Constants.goatEarTagStatus_GoatLost)) {
                                dead_goat_radio.setChecked(false);
                                sick_goat_radio.setChecked(false);
                                normal_goat_radio.setChecked(false);
                                sold_goat_radio.setChecked(false);
                            } else if (String.valueOf(selectedGoatStatus).equals(Constants.goatEarTagStatus_EarTagLost)) {
                                dead_goat_radio.setChecked(false);
                                sick_goat_radio.setChecked(false);
                                normal_goat_radio.setChecked(false);
                                sold_goat_radio.setChecked(false);
                            } else if (String.valueOf(selectedGoatStatus).equals(Constants.goatEarTagStatus_Goatdead)) {
                                dead_goat_radio.setChecked(true);
                                sick_goat_radio.setChecked(false);
                                normal_goat_radio.setChecked(false);
                                sold_goat_radio.setChecked(false);
                            }
                            else if (String.valueOf(selectedGoatStatus).equals(Constants.goatEarTagStatus_Goatsick)) {
                                dead_goat_radio.setChecked(false);
                                sick_goat_radio.setChecked(true);
                                normal_goat_radio.setChecked(false);
                                sold_goat_radio.setChecked(false);
                            }
                            else if (String.valueOf(selectedGoatStatus).equals(Constants.goatEarTagStatus_Sold)) {
                                dead_goat_radio.setChecked(false);
                                sick_goat_radio.setChecked(false);
                                normal_goat_radio.setChecked(false);
                                sold_goat_radio.setChecked(true);
                            }


                            else {
                                selectedGoatStatus = getString(R.string.good);
                                dead_goat_radio.setChecked(false);
                                sick_goat_radio.setChecked(false);
                                normal_goat_radio.setChecked(true);
                                sold_goat_radio.setChecked(false);
                            }



                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        showProgressBar(false);

                }
                else if (callMethod.equals(Constants.CallUPDATEMethod)){



                    if(result.equals(Constants.item_not_Found_volley)){
                        AlertDialogClass.showDialog(getActivity(), R.string.GoatEatTagsCannotUpdated_Instruction);
                        // Toast.makeText(mContext, "This barcode is already added for this batch please add new one", Toast.LENGTH_SHORT).show();
                        showProgressBar(false);
                        isGoatEarTagDetailsTableServiceCalled = false;

                    }
                    else  if(result.equals(Constants.expressionAttribute_is_empty_volley_response)){
                        AlertDialogClass.showDialog(getActivity(), R.string.GoatEatTagsCannotUpdated_PleaseChangeTOUpdate_Instruction);
                        // Toast.makeText(mContext, "This barcode is already added for this batch please add new one", Toast.LENGTH_SHORT).show();
                        showProgressBar(false);
                        isGoatEarTagDetailsTableServiceCalled = false;

                    }
                    else if(result.equals(Constants.successResult_volley)){
                        //showProgressBar(false);
                        isGoatEarTagDetailsTableServiceCalled = false;
                        UpdateCalculationDataINSharedPref(callMethod);
                        Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod, callMethod);


                    }
                    else{

                        isGoatEarTagDetailsTableServiceCalled = false;
                        Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod, callMethod);

                     //   Toast.makeText(mContext, Constants.unknown_API_Result_volley, Toast.LENGTH_SHORT).show();
                    }
                    //isGoatEarTagDetailsTableServiceCalled = false;
                    //showProgressBar(false);
                   // Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod, callMethod);

                }
              /*
                else if (callMethod.equals(Constants.CallADDMethod)){



                    if(result.equals(Constants.item_Already_Added_volley)){
                        AlertDialogClass.showDialog(getActivity(), R.string.BatchDetailsAlreadyCreated_Instruction);
                       // Toast.makeText(mContext, "This barcode is already added for this batch please add new one", Toast.LENGTH_SHORT).show();
                        showProgressBar(false);
                        isGoatEarTagDetailsTableServiceCalled = false;

                    }
                    else if(result.equals(Constants.successResult_volley)){
                        //showProgressBar(false);
                        isGoatEarTagDetailsTableServiceCalled = false;
                        UpdateCalculationDataINSharedPref(callMethod);
                        Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod, callMethod);


                    }
                    else{
                        isGoatEarTagDetailsTableServiceCalled = false;
                        Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod, callMethod);

                     //   Toast.makeText(mContext, Constants.unknown_API_Result_volley, Toast.LENGTH_SHORT).show();
                    }




                }
            */
                //showProgressBar(false);

            }

            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch) {

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
                Toast.makeText(mContext, "There is an volley error while updating Ear Tag", Toast.LENGTH_SHORT).show();

                isGoatEarTagDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                Toast.makeText(mContext, "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();

                showProgressBar(false);
                isGoatEarTagDetailsTableServiceCalled = false;


            }




        };

        if(callMethod.equals(Constants.CallADDMethod)){


            try{


                Modal_Static_GoatEarTagDetails.loadedweightingrams  = (String.valueOf(df.format(entered_Weight_double)));
                Modal_Static_GoatEarTagDetails.currentweightingrams  = (String.valueOf(df.format(entered_Weight_double)));
                Modal_GoatEarTagTransaction.newweightingrams  = (String.valueOf(df.format(entered_Weight_double)));
                Modal_Static_GoatEarTagDetails.description = description_edittext.getText().toString();
                Modal_Static_GoatEarTagDetails.gender=selectedGender;
                Modal_Static_GoatEarTagDetails.breedtype=selectedBreed;
                Modal_Static_GoatEarTagDetails.barcodeno=scannedBarcode;
                Modal_Static_GoatEarTagDetails.batchno= Modal_B2BBatchDetailsStatic.getBatchno();
                Modal_Static_GoatEarTagDetails.itemaddeddate = DateParser.getDate_and_time_newFormat();
                Modal_Static_GoatEarTagDetails.status = Constants.batchDetailsStatus_Loading;
                Modal_Static_GoatEarTagDetails.supplierkey = Modal_SupplierDetails.getSupplierkey_static();
                Modal_Static_GoatEarTagDetails.suppliername = Modal_SupplierDetails.getSuppliername_static();
                Modal_Static_GoatEarTagDetails.gradename = selectedGradeName;
                Modal_Static_GoatEarTagDetails.gradeprice = selectedGradePrice;
                Modal_Static_GoatEarTagDetails.gradekey = selectedGradeKey;
                String addApiToCall = API_Manager.addGoatEarTagDetails ;
                GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails,  addApiToCall , callMethod);
                asyncTask.execute();
            }
            catch (Exception e){
                e.printStackTrace();
            }



        }
        else if (callMethod.equals(Constants.CallUPDATEMethod)){
            try{
                Modal_UpdatedGoatEarTagDetails.setUpdated_barcodeno(scannedBarcode);
                Modal_UpdatedGoatEarTagDetails.setUpdated_batchno(Modal_B2BBatchDetailsStatic.getBatchno());
                Modal_UpdatedGoatEarTagDetails.setUpdated_selectedItem(selectedCategoryItem);

                Modal_UpdatedGoatEarTagDetails.setUpdated_selectedItem(selectedCategoryItem);
                Modal_UpdatedGoatEarTagDetails.setUpdated_stockedweightingrams(String.valueOf(df.format(entered_Weight_double)));
                Modal_UpdatedGoatEarTagDetails.setUpdated_currentweightingrams(String.valueOf(df.format(entered_Weight_double)));
                Modal_UpdatedGoatEarTagDetails.setUpdated_description(description_edittext.getText().toString());
                Modal_UpdatedGoatEarTagDetails.setUpdated_gender(selectedGender);
                Modal_UpdatedGoatEarTagDetails.setUpdated_breedtype(selectedBreed);
                Modal_UpdatedGoatEarTagDetails.setUpdated_gradename(selectedGradeName);
                Modal_UpdatedGoatEarTagDetails.setUpdated_gradeprice(selectedGradePrice);
                Modal_UpdatedGoatEarTagDetails.setUpdated_gradeKey(selectedGradeKey);
                Modal_UpdatedGoatEarTagDetails.setUpdated_deliverycenterkey(deliveryCenterKey);
                Modal_UpdatedGoatEarTagDetails.setUpdated_deliverycentername(deliveryCenterName);

                if(selectedGoatStatus.toUpperCase().equals(getString(R.string.good))){
                    Modal_UpdatedGoatEarTagDetails.setUpdated_status(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE);

                }
                else if(selectedGoatStatus.toUpperCase().equals(getString(R.string.goatdead))){
                    Modal_UpdatedGoatEarTagDetails.setUpdated_status(Constants.goatEarTagStatus_Goatdead);

                }
                else if(selectedGoatStatus.toUpperCase().equals(getString(R.string.goatsick))){
                    Modal_UpdatedGoatEarTagDetails.setUpdated_status(Constants.goatEarTagStatus_Goatsick);

                }
                else if(selectedGoatStatus.toUpperCase().equals(getString(R.string.sold))){
                    Modal_UpdatedGoatEarTagDetails.setUpdated_status(Constants.goatEarTagStatus_Sold);

                }


                String addApiToCall = API_Manager.updateGoatEarTag;
                GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails,  addApiToCall , callMethod);
                asyncTask.execute();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (callMethod.equals(Constants.CallGETMethod)){

            String addApiToCall = API_Manager.getGoatEarTagDetails_forBarcodeWithBatchno +"?barcodeno="+scannedBarcode+"&batchno="+ Modal_B2BBatchDetailsStatic.getBatchno() ;
            GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails,  addApiToCall , callMethod);
            asyncTask.execute();

        }




    }





    private void Initialize_and_ExecuteInGoatEarTagTransaction(String callMethod, String goatEarTagAdd_OR_Updated) {

        showProgressBar(true);
        if (isGoatEarTagTransactionTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isGoatEarTagTransactionTableServiceCalled = true;
        callback_GoatEarTagTransactionInterface = new GoatEarTagTransactionInterface() {


            @Override
            public void notifySuccess(String result) {
                new Modal_UpdatedGoatEarTagDetails();
                new Modal_Static_GoatEarTagDetails();
                entered_Weight_double =0;
                previous_WeightInGrams ="";

                showProgressBar(false);
                isGoatEarTagTransactionTableServiceCalled = false;
                 Toast.makeText(mContext, "Item Saved Successfully", Toast.LENGTH_SHORT).show();
                ((View_or_Edit_BatchItem_deliveryCenter) requireActivity()).closeFragment();

                //((View_or_Edit_BatchItem_deliveryCenter)getActivity()).closeFragment();

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                Modal_UpdatedGoatEarTagDetails modal_updatedGoatEarTagDetails = new Modal_UpdatedGoatEarTagDetails();
                Modal_Static_GoatEarTagDetails modal_goatEarTagDetails = new Modal_Static_GoatEarTagDetails();
                entered_Weight_double =0;
                previous_WeightInGrams ="";
                Toast.makeText(mContext, "There is an volley error while updating Ear Tag Transaction", Toast.LENGTH_SHORT).show();
                showProgressBar(false);
                ((View_or_Edit_BatchItem_deliveryCenter)getActivity()).closeFragment();

                isGoatEarTagTransactionTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                Modal_UpdatedGoatEarTagDetails modal_updatedGoatEarTagDetails = new Modal_UpdatedGoatEarTagDetails();
                Modal_Static_GoatEarTagDetails modal_goatEarTagDetails = new Modal_Static_GoatEarTagDetails();
                entered_Weight_double =0;
                previous_WeightInGrams ="";
                Toast.makeText(mContext, "There is an Process error while updating Ear Tag Transaction", Toast.LENGTH_SHORT).show();

                showProgressBar(false);
                isGoatEarTagTransactionTableServiceCalled = false;
                ((View_or_Edit_BatchItem_deliveryCenter)getActivity()).closeFragment();


            }




        };

        if(callMethod.equals(Constants.CallADDMethod)){
            if(goatEarTagAdd_OR_Updated.equals(Constants.CallADDMethod)) {
                try {
                    new Modal_UpdatedGoatEarTagDetails();
                    Modal_GoatEarTagTransaction.barcodeno = scannedBarcode;
                    Modal_GoatEarTagTransaction.batchno = Modal_B2BBatchDetailsStatic.getBatchno();
                    Modal_GoatEarTagTransaction.updateddate = DateParser.getDate_and_time_newFormat();
                    Modal_GoatEarTagTransaction.previousweightingrams = previous_WeightInGrams;
                    Modal_GoatEarTagTransaction.newweightingrams = String.valueOf(df.format(entered_Weight_double));
                    Modal_GoatEarTagTransaction.weighingpurpose = Constants.goatEarTagWeighingPurpose_Reviewed_and_READYFORSALE;
                    Modal_GoatEarTagTransaction.status = Modal_Static_GoatEarTagDetails.getStatus();
                    Modal_GoatEarTagTransaction.gender = Modal_Static_GoatEarTagDetails.getGender();
                    Modal_GoatEarTagTransaction.breedtype = Modal_Static_GoatEarTagDetails.getBreedtype();
                    Modal_GoatEarTagTransaction.mobileno = Modal_AppUserAccess.getMobileno();
                    Modal_GoatEarTagTransaction.description = description_edittext.getText().toString();
                    Modal_GoatEarTagTransaction.gradename = Modal_Static_GoatEarTagDetails.getGradename();
                    Modal_GoatEarTagTransaction.gradeprice = Modal_Static_GoatEarTagDetails.getGradeprice();
                    Modal_GoatEarTagTransaction.gradekey = Modal_Static_GoatEarTagDetails.getGradekey();
                    Modal_GoatEarTagTransaction.deliverycenterkey = deliveryCenterKey;
                    Modal_GoatEarTagTransaction.deliverycentername = deliveryCenterName;

                    String addApiToCall = API_Manager.addGoatEarTagTransactions;
                    GoatEarTagTransaction asyncTask = new GoatEarTagTransaction(callback_GoatEarTagTransactionInterface, addApiToCall, callMethod);
                    asyncTask.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(goatEarTagAdd_OR_Updated.equals(Constants.CallUPDATEMethod)){
                try{

                    Modal_GoatEarTagTransaction.barcodeno = scannedBarcode;
                    Modal_GoatEarTagTransaction.batchno = Modal_B2BBatchDetailsStatic.getBatchno();
                    Modal_GoatEarTagTransaction.updateddate = DateParser.getDate_and_time_newFormat();
                    Modal_GoatEarTagTransaction.mobileno = Modal_AppUserAccess.getMobileno();
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_currentweightingrams_boolean()){
                        Modal_GoatEarTagTransaction.previousweightingrams = previous_WeightInGrams;
                        Modal_GoatEarTagTransaction.newweightingrams =(String.valueOf(df.format(entered_Weight_double)));
                        Modal_GoatEarTagTransaction.weighingpurpose = Constants.goatEarTagWeighingPurpose_RegularAudit;

                    }

                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_deliverycenterkey_boolean()) {
                        Modal_GoatEarTagTransaction.deliverycenterkey = deliveryCenterKey;
                    }
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_deliverycentername_boolean()) {
                        Modal_GoatEarTagTransaction.deliverycentername = deliveryCenterName;
                    }


                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_status_boolean()) {
                        Modal_GoatEarTagTransaction.status = Modal_UpdatedGoatEarTagDetails.getUpdated_status();
                    }
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_gender_boolean()) {
                        Modal_GoatEarTagTransaction.gender = selectedGender;
                    }
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_breedtype_boolean()) {
                        Modal_GoatEarTagTransaction.breedtype = selectedBreed;
                    }
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_gradeprice_boolean()) {
                        Modal_GoatEarTagTransaction.gradeprice = selectedGradePrice;
                    }
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_gradename_boolean()) {
                        Modal_GoatEarTagTransaction.gradename = selectedGradeName;
                    }
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_gradekey_boolean()) {
                        Modal_GoatEarTagTransaction.gradekey = selectedGradeKey;
                    }

                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_description_boolean()) {
                        Modal_GoatEarTagTransaction.description = description_edittext.getText().toString();
                    }

                    String addApiToCall = API_Manager.addGoatEarTagTransactions;
                    GoatEarTagTransaction asyncTask = new GoatEarTagTransaction(callback_GoatEarTagTransactionInterface, addApiToCall, callMethod);
                    asyncTask.execute();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
        else if (callMethod.equals(Constants.CallUPDATEMethod)){

        }
        else if (callMethod.equals(Constants.CallGETMethod)){

        }



    }

    private void UpdateCalculationDataINSharedPref(String callMethod) {


        try {
            String batchNo_fromPreference = "";
            int totalCount_int = 0, maleCount_int = 0, femaleCount_int = 0, femaleWithBabyCount_int = 0, totalReviewedItemCount = 0,
                    reviewed_maleCount_int = 0, reviewed_femaleCount_int = 0, reviewed_femaleWithbabyCount_int = 0;
            double total_loadedweight_double = 0, minimum_weight_double = 0, maximum_weight_double = 0, average_weight_double = 0 , totalReviewedItemWeight =0;
            SharedPreferences sharedPreferences_forAdd = mContext.getSharedPreferences(Constants.earTagCalculationDeta_DeliveryCenter, MODE_PRIVATE);


            batchNo_fromPreference = sharedPreferences_forAdd.getString(
                    "BatchNo", "0"
            );
            if (batchNo_fromPreference.toUpperCase().equals(batchNo)) {


                totalCount_int = sharedPreferences_forAdd.getInt(
                        "TotalCount", 0
                );

                maleCount_int = sharedPreferences_forAdd.getInt(
                        "MaleCount", 0
                );
                femaleCount_int = sharedPreferences_forAdd.getInt(
                        "FemaleCount", 0
                );
                femaleWithBabyCount_int = sharedPreferences_forAdd.getInt(
                        "FemaleWithBabyCount", 0
                );


                total_loadedweight_double = (double) sharedPreferences_forAdd.getFloat(
                        "TotalWeight", 0);

                minimum_weight_double = (double) sharedPreferences_forAdd.getFloat(
                        "MinimumWeight", 0
                );

                maximum_weight_double = (double) sharedPreferences_forAdd.getFloat(
                        "MaximumWeight", 0
                );

                average_weight_double = (double) sharedPreferences_forAdd.getFloat(
                        "AverageWeight", 0
                );

                totalReviewedItemCount = sharedPreferences_forAdd.getInt(
                        "ReviewedTotalCount", 0
                );
                reviewed_maleCount_int = sharedPreferences_forAdd.getInt(
                        "ReviewedMaleCount", 0
                );

                reviewed_femaleCount_int = sharedPreferences_forAdd.getInt(
                        "ReviewedFemaleCount", 0
                );
                reviewed_femaleWithbabyCount_int = sharedPreferences_forAdd.getInt(
                        "ReviewedFemaleWithBabyCount", 0
                );
                totalReviewedItemWeight = sharedPreferences_forAdd.getFloat(
                        "ReviewedTotalWeight", 0
                );

                String defValue = new Gson().toJson(new HashMap<String, Double>());
                String json = sharedPreferences_forAdd.getString("EarTagWeightWithBarcodeAsKey", defValue);
                TypeToken<HashMap<String, Double>> token = new TypeToken<HashMap<String, Double>>() {
                };
                earTagWeightListWithBarcodeAsKey.putAll( new Gson().fromJson(json, token.getType()));


                SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constants.earTagCalculationDeta_DeliveryCenter, MODE_PRIVATE);

                SharedPreferences.Editor myEdit = sharedPreferences.edit();


                if(calledFrom.equals(getString(R.string.stock_batch_item)) || calledFrom.equals(getString(R.string.stock_batch_item_withoutScan)) || calledFrom.equals(getString(R.string.stock_batch_item_withoutScan_allowedScan))){
                   // totalCount_int = totalCount_int - 1;
                   // totalReviewedItemCount = totalReviewedItemCount + 1;

                    try {
                        earTagWeightListWithBarcodeAsKey.remove(scannedBarcode);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    previous_WeightInGrams = previous_WeightInGrams.replaceAll("[^\\d.]", "");
                    if(previous_WeightInGrams.equals("") || previous_WeightInGrams.equals(null)){
                        previous_WeightInGrams = "0";
                    }


                    double previous_WeightInGrams_double = Double.parseDouble(previous_WeightInGrams);
                    /*
                    if (selectedGender.toUpperCase().equals(getString(R.string.MALE))) {
                        maleCount_int = maleCount_int - 1;
                        reviewed_maleCount_int = reviewed_maleCount_int + 1;
                    }
                    if (selectedGender.toUpperCase().equals(getString(R.string.FEMALE))) {

                        femaleCount_int = femaleCount_int - 1;
                        reviewed_femaleCount_int = reviewed_femaleCount_int + 1;

                    }
                    if (selectedGender.toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {
                        femaleWithBabyCount_int = femaleWithBabyCount_int - 1;
                        reviewed_femaleWithbabyCount_int = reviewed_femaleWithbabyCount_int + 1;
                    }



                    previous_WeightInGrams = previous_WeightInGrams.replaceAll("[^\\d.]", "");
                    double previous_WeightInGrams_double = Double.parseDouble(previous_WeightInGrams);
                    total_loadedweight_double = total_loadedweight_double - previous_WeightInGrams_double;
                    try{
                        total_loadedweight_double = Double.parseDouble(df.format(total_loadedweight_double));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    try{
                        totalReviewedItemWeight = totalReviewedItemWeight + entered_Weight_double;
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    if (previous_WeightInGrams_double == minimum_weight_double) {
                        // minimum_weight_double = entered_Weight_double;
                        double firstKeysEntry = 0;
                        for (String key : earTagWeightListWithBarcodeAsKey.keySet()) {
                            double secondKeysEntry = 0;
                            secondKeysEntry = earTagWeightListWithBarcodeAsKey.get(key);
                            if (firstKeysEntry == 0) {
                                firstKeysEntry = earTagWeightListWithBarcodeAsKey.get(key);
                            }
                            if (firstKeysEntry > secondKeysEntry) {
                                firstKeysEntry = secondKeysEntry;
                            }


                        }
                        minimum_weight_double = firstKeysEntry;
                        myEdit.putFloat("MinimumWeight", (float) Double.parseDouble(df.format(minimum_weight_double)));

                    }
                    if (previous_WeightInGrams_double == maximum_weight_double) {
                        //    maximum_weight_double = entered_Weight_double;
                        double firstKeysEntry = 0;
                        for (String key : earTagWeightListWithBarcodeAsKey.keySet()) {
                            double secondKeysEntry = 0;
                            secondKeysEntry = earTagWeightListWithBarcodeAsKey.get(key);
                            if (firstKeysEntry == 0) {
                                firstKeysEntry = earTagWeightListWithBarcodeAsKey.get(key);
                            }
                            if (firstKeysEntry < secondKeysEntry) {
                                firstKeysEntry = secondKeysEntry;
                            }


                        }
                        maximum_weight_double = firstKeysEntry;
                        myEdit.putFloat("MaximumWeight", (float) Double.parseDouble(df.format(maximum_weight_double)));

                    }

                    average_weight_double = total_loadedweight_double / totalCount_int;

                    try{
                        average_weight_double = Double.parseDouble(df.format(average_weight_double));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    myEdit.putInt("FemaleWithBabyCount", femaleWithBabyCount_int);
                    myEdit.putInt(
                            "FemaleCount", femaleCount_int
                    );
                    myEdit.putInt(
                            "MaleCount", maleCount_int
                    );
                    myEdit.putInt(
                            "TotalCount", totalCount_int
                    );


///
                    myEdit.putInt(
                            "ReviewedFemaleCount", reviewed_femaleWithbabyCount_int
                    );
                    myEdit.putInt(
                            "ReviewedFemaleCount", reviewed_femaleCount_int
                    );
                    myEdit.putInt(
                            "ReviewedMaleCount", reviewed_maleCount_int
                    );
                    myEdit.putInt(
                            "ReviewedTotalCount", totalReviewedItemCount
                    );

                    myEdit.putFloat(
                            "ReviewedTotalWeight", (float) Double.parseDouble(df.format(totalReviewedItemWeight))
                    );



                     */
                    // if selected item status is good then remove the item details  from unreviewed and add it in reviewed details
                    // if status is other than that then remove the item from the Unreviewed  details
                    if(selectedGoatStatus.equals(getString(R.string.good)) || selectedGoatStatus.equals(getString(R.string.goatsick))) {


                        try {
                            total_loadedweight_double = total_loadedweight_double - previous_WeightInGrams_double;
                        }
                        catch (Exception e){
                            total_loadedweight_double =0;
                            e.printStackTrace();
                        }


                        if(total_loadedweight_double<0){
                            total_loadedweight_double = 0;
                        }


                        totalReviewedItemWeight = totalReviewedItemWeight + entered_Weight_double;
                        totalCount_int = totalCount_int - 1;
                        totalReviewedItemCount = totalReviewedItemCount + 1;



                        if (previousSelectedGender.toUpperCase().equals(getString(R.string.MALE))) {
                            maleCount_int = maleCount_int - 1;
                        }

                        if (previousSelectedGender.toUpperCase().equals(getString(R.string.FEMALE))) {
                            femaleCount_int = femaleCount_int - 1;
                        }

                        if (previousSelectedGender.toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {
                            femaleWithBabyCount_int = femaleWithBabyCount_int - 1;
                        }




                        myEdit.putInt(
                                "FemaleWithBabyCount", femaleWithBabyCount_int
                        );
                        myEdit.putInt(
                                "FemaleCount", femaleCount_int
                        );
                        myEdit.putInt(
                                "MaleCount", maleCount_int
                        );
                        myEdit.putInt(
                                "TotalCount", totalCount_int
                        );

                        if (entered_Weight_double < minimum_weight_double) {
                            minimum_weight_double = entered_Weight_double;
                            myEdit.putFloat(
                                    "MinimumWeight", (float) Double.parseDouble(df.format(minimum_weight_double))
                            );
                        }
                        if (entered_Weight_double > maximum_weight_double) {
                            maximum_weight_double = entered_Weight_double;
                            myEdit.putFloat(
                                    "MaximumWeight", (float) Double.parseDouble(df.format(maximum_weight_double))
                            );
                        }

                        average_weight_double = total_loadedweight_double / totalCount_int;

                        myEdit.putFloat(
                                "AverageWeight", (float) Double.parseDouble(df.format(average_weight_double))

                        );


                        myEdit.putFloat(
                                "TotalWeight", (float) Double.parseDouble(df.format(total_loadedweight_double))
                        );


                        myEdit.putInt(
                                "ReviewedTotalCount", totalReviewedItemCount
                        );
                        if (selectedGender.toUpperCase().equals(getString(R.string.MALE))) {
                            reviewed_maleCount_int = reviewed_maleCount_int + 1;
                            myEdit.putInt(
                                    "ReviewedMaleCount", reviewed_maleCount_int
                            );
                        }
                        if (selectedGender.toUpperCase().equals(getString(R.string.FEMALE))) {
                            reviewed_femaleCount_int = reviewed_femaleCount_int + 1;
                            myEdit.putInt(
                                    "ReviewedFemaleCount", reviewed_femaleCount_int
                            );

                        }
                        if (selectedGender.toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {
                            reviewed_femaleWithbabyCount_int = reviewed_femaleWithbabyCount_int + 1;
                            myEdit.putInt(
                                    "ReviewedFemaleWithBabyCount", reviewed_femaleWithbabyCount_int
                            );


                        }


                        myEdit.putFloat(
                                "ReviewedTotalWeight", (float) Double.parseDouble(df.format(totalReviewedItemWeight))
                        );

                    }
                    else{
                        total_loadedweight_double = total_loadedweight_double - previous_WeightInGrams_double;
                        totalCount_int = totalCount_int - 1;
                        if (previousSelectedGender.toUpperCase().equals(getString(R.string.MALE))) {
                            maleCount_int = maleCount_int - 1;
                        }

                        if (previousSelectedGender.toUpperCase().equals(getString(R.string.FEMALE))) {
                            femaleCount_int = femaleCount_int - 1;
                        }

                        if (previousSelectedGender.toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {
                            femaleWithBabyCount_int = femaleWithBabyCount_int - 1;
                        }

                        if (entered_Weight_double < minimum_weight_double) {
                            minimum_weight_double = entered_Weight_double;
                            myEdit.putFloat(
                                    "MinimumWeight", (float) Double.parseDouble(df.format(minimum_weight_double))
                            );
                        }
                        if (entered_Weight_double > maximum_weight_double) {
                            maximum_weight_double = entered_Weight_double;
                            myEdit.putFloat(
                                    "MaximumWeight", (float) Double.parseDouble(df.format(maximum_weight_double))
                            );
                        }
                        average_weight_double = total_loadedweight_double / totalCount_int;


                        myEdit.putInt(
                                "FemaleWithBabyCount", femaleWithBabyCount_int
                        );
                        myEdit.putInt(
                                "FemaleCount", femaleCount_int
                        );
                        myEdit.putInt(
                                "MaleCount", maleCount_int
                        );
                        myEdit.putInt(
                                "TotalCount", totalCount_int
                        );
                        myEdit.putFloat(
                                "AverageWeight", (float) Double.parseDouble(df.format(average_weight_double))

                        );


                        myEdit.putFloat(
                                "TotalWeight", (float) Double.parseDouble(df.format(total_loadedweight_double))
                        );

                    }

                }
                else if (calledFrom.equals(getString(R.string.view_stockedBatch_item))) {


                    previous_WeightInGrams = previous_WeightInGrams.replaceAll("[^\\d.]", "");
                    if(previous_WeightInGrams.equals("") || previous_WeightInGrams.equals(null)){
                        previous_WeightInGrams = "0";
                    }


                    double previous_WeightInGrams_double = Double.parseDouble(previous_WeightInGrams);

                    try {
                        total_loadedweight_double = total_loadedweight_double - previous_WeightInGrams_double;
                    }
                    catch (Exception e){
                        total_loadedweight_double =0;
                        e.printStackTrace();
                    }


                    if(total_loadedweight_double<0){
                        total_loadedweight_double = 0;
                    }
                    total_loadedweight_double = total_loadedweight_double + entered_Weight_double;



                    try{
                        total_loadedweight_double = Double.parseDouble(df.format(total_loadedweight_double));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }



                    if (entered_Weight_double < minimum_weight_double) {
                        minimum_weight_double = entered_Weight_double;
                        myEdit.putFloat(
                                "MinimumWeight", (float) Double.parseDouble(df.format(minimum_weight_double))
                        );
                    }
                    if (entered_Weight_double > maximum_weight_double) {
                        maximum_weight_double = entered_Weight_double;
                        myEdit.putFloat(
                                "MaximumWeight", (float) Double.parseDouble(df.format(maximum_weight_double))
                        );
                    }

                    average_weight_double = total_loadedweight_double / totalCount_int;
                    try{
                        average_weight_double = Double.parseDouble(df.format(average_weight_double));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
              String jsonString = new Gson().toJson(earTagWeightListWithBarcodeAsKey);
                myEdit.putString("EarTagWeightWithBarcodeAsKey", jsonString);
                myEdit.putFloat(
                        "AverageWeight", (float) Double.parseDouble(df.format(average_weight_double))
                );


                myEdit.putFloat(
                        "TotalWeight", (float) Double.parseDouble(df.format(total_loadedweight_double))
                );


                myEdit.apply();
                ((View_or_Edit_BatchItem_deliveryCenter) requireActivity()).getCalculationValueFromSharedPreference();

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }



}