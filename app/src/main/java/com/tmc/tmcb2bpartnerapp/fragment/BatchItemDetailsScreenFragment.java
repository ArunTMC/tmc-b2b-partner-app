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
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.utils.BarcodeScannerScreen;
import com.tmc.tmcb2bpartnerapp.activity.View_or_Edit_BatchItem_Supplier;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BItemCtgy;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagTransaction;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BItemCtgyInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.BarcodeScannerInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagTransactionInterface;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BBatchDetailsStatic;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BItemCtgy;
import com.tmc.tmcb2bpartnerapp.model.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_Static_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_GoatEarTagTransaction;
import com.tmc.tmcb2bpartnerapp.model.Modal_SupplierDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_UpdatedGoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.tmc.tmcb2bpartnerapp.activity.View_or_Edit_BatchItem_Supplier.showProgressBar;
//import static com.tmc.tmcb2bpartnerapp.activity.CreateNew_Or_EditOldBatchScreen.showProgressBar;

public class BatchItemDetailsScreenFragment extends Fragment {

    TextView toolBarHeader_TextView,barcodeNo_textView,weightDetails_textview,breedType_textView,
            description_textiew,selectedGender_textview,selectedGoatStatus_textview;
    ImageView backButton_icon;
    Context mContext;
    Button save_button;
    String calledFrom,scannedBarcode="",selectedCategoryItem="",selectedGender="",selectedBreed=""
            ,userType ="",userMobileNo ="",selectedGoatStatus="",lastlySelectedSupplierKey="" ,lastlySelectedBreedType ="";
    CardView scanBarcode_view;
    public static BarcodeScannerInterface barcodeScannerInterface = null;
    Spinner chooseItem_spinner,breedType_spinner;
    ArrayList<String> itemCategory_arrayList = new ArrayList<>();
    ArrayList<String> breedType_arrayList_string = new ArrayList<>();
    EditText description_edittext,weightDetails_edittext;
    private RadioGroup genderRadioGroup;
    GoatEarTagDetailsInterface callback_GoatEarTagDetails = null;
    boolean  isGoatEarTagDetailsTableServiceCalled = false;

    GoatEarTagTransactionInterface callback_GoatEarTagTransactionInterface = null;
    boolean  isGoatEarTagTransactionTableServiceCalled = false;

    B2BItemCtgyInterface callback_B2BItemCtgyInterface = null;
    boolean  isB2BItemCtgyTableServiceCalled = false;

    RadioButton male_radioButton,female_radioButton,female_WithBaby_radioButton;
    RadioGroup goatstatusradiogrp;
    double entered_Weight_double = 0 ;
    String previous_WeightInGrams  = "0";
    String previousSelectedGender = "";
    String batchStatus ="" , batchNo ="";
    DecimalFormat df = new DecimalFormat(Constants.threeDecimalPattern);
    LinearLayout back_IconLayout,goatstatus_layout,noneditable_weight_breed_layout,editable_weight_breed_layout;

    RadioButton dead_goat_radio, sick_goat_radio,normal_goat_radio;



    public static BatchItemDetailsScreenFragment newInstance(String key, String value) {
        BatchItemDetailsScreenFragment fragment = new BatchItemDetailsScreenFragment();
        Bundle args = new Bundle();
        args.putString(key, value);
        fragment.setArguments(args);
        return fragment;
    }

    public BatchItemDetailsScreenFragment() {
        // Required empty public constructor
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
                return inflater.inflate(R.layout.fragment_batch_item_details_screen, container, false);            } else {

                // Inflate the layout for this fragment
                return inflater.inflate(R.layout.pos_fragment_batch_item_details_screen, container, false);            }
        }
        catch (Exception e){
            e.printStackTrace();
            return inflater.inflate(R.layout.fragment_batch_item_details_screen, container, false);
        }




    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        toolBarHeader_TextView = view.findViewById(R.id.toolBarHeader_TextView);
        backButton_icon = view.findViewById(R.id.backButton_icon);
        save_button = view.findViewById(R.id.save_button);
        scanBarcode_view  = view.findViewById(R.id.scanBarcode_view);
        barcodeNo_textView  = view.findViewById(R.id.barcodeNo_textView);
        weightDetails_edittext  = view.findViewById(R.id.weightDetails_edittext);
        chooseItem_spinner = view.findViewById(R.id.chooseItem_spinner);
        genderRadioGroup =  view.findViewById(R.id.genderRadioGroup);
        breedType_spinner =  view.findViewById(R.id.breedType_spinner);
        description_edittext =  view.findViewById(R.id.description_edittext);
        female_radioButton =  view.findViewById(R.id.female_radioButton);
        male_radioButton =  view.findViewById(R.id.male_radioButton);
        female_WithBaby_radioButton =  view.findViewById(R.id.female_WithBaby_radioButton);
        back_IconLayout  =  view.findViewById(R.id.back_IconLayout);
        goatstatus_layout  =  view.findViewById(R.id.goatstatus_layout);
        normal_goat_radio = view.findViewById(R.id.normal_goat_radio);
        dead_goat_radio = view.findViewById(R.id.dead_goat_radio);
        sick_goat_radio = view.findViewById(R.id.sick_goat_radio);
        weightDetails_textview = view.findViewById(R.id.weightDetails_textview);
        breedType_textView = view.findViewById(R.id.breedType_textView);
        description_textiew  = view.findViewById(R.id.description_textiew);

        selectedGoatStatus_textview = view.findViewById(R.id.selectedGoatStatus_textview);
        editable_weight_breed_layout = view.findViewById(R.id.editable_weight_breed_layout);
        noneditable_weight_breed_layout = view.findViewById(R.id.noneditable_weight_breed_layout);
        goatstatusradiogrp  = view.findViewById(R.id.goatstatusradiogrp);
        selectedGender_textview = view.findViewById(R.id.selectedGender_textview);



        SharedPreferences sh
                = mContext.getSharedPreferences("LoginData",
                MODE_PRIVATE);
        userType = sh.getString("UserType", "");
        userMobileNo = sh.getString("UserMobileNumber", "");

        SharedPreferences sh1 = mContext.getSharedPreferences("LastlyCachedData",MODE_PRIVATE);
        lastlySelectedBreedType = sh1.getString("LastlySelectedBreedType","");

        batchNo = String.valueOf( Modal_B2BBatchDetailsStatic.getBatchno());

        batchStatus = String.valueOf( Modal_B2BBatchDetailsStatic.getStatus().toUpperCase());



        if(DatabaseArrayList_PojoClass.breedType_arrayList_string.size()>0){
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
        else{
            Initialize_and_ExecuteB2BCtgyItem();
        }


        //breedType_arrayList.add("GURIJI");
       // breedType_arrayList.add("SIROHI");
       // breedType_arrayList.add("KOTA");
      //  ArrayAdapter breedType_aAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, breedType_arrayList_string);
     //  breedType_spinner.setAdapter(breedType_aAdapter);



        itemCategory_arrayList.add("Goat");
        ArrayAdapter aAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, itemCategory_arrayList);
        chooseItem_spinner.setAdapter(aAdapter);
       // toolBarHeader_TextView.setText(calledFrom);
            if(calledFrom.equals(getString(R.string.stock_batch_item_withoutScan_allowedScan))){
                scannedBarcode = View_or_Edit_BatchItem_Supplier.scannedBarcode;
                barcodeNo_textView.setText(scannedBarcode);
                if(scannedBarcode.equals("")){
                    scanBarcode_view.callOnClick();
                }
            }


            if (batchStatus.equals(Constants.batchDetailsStatus_Loading)) {
                showEditabeLayouts(true);

            } else {
                showEditabeLayouts(false);


            }




        back_IconLayout.setOnClickListener(view1 -> ((View_or_Edit_BatchItem_Supplier)getActivity()).closeFragment());



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
                if(entered_Weight_double>0){
                    if(scannedBarcode.length()>0){
                        if(selectedCategoryItem.length()>0){
                            if(selectedBreed.length()>0){
                                if(selectedGender.length()>0){


                                    ((View_or_Edit_BatchItem_Supplier) requireActivity()).closeFragment();

                                    if(calledFrom.equals(getString(R.string.add_new_item))){


                                        Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallADDMethod);
                                    //    Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod,Constants.CallADDMethod);
                                    }
                                    else if(calledFrom.equals(getString(R.string.add_new_item_existing_batch))){
                                        Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallADDMethod);
                                      //  Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod,Constants.CallADDMethod);
                                    }
                                    else if(calledFrom.equals(getString(R.string.stock_batch_item_withoutScan_allowedScan))){
                                        Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallADDMethod);
                                        //  Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod,Constants.CallADDMethod);
                                    }
                                    else if(calledFrom.equals(getString(R.string.view_edit_existing_item))){
                                        previous_WeightInGrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();
                                        previousSelectedGender = Modal_Static_GoatEarTagDetails.getGender();
                                        Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);
                                      //  Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod,Constants.CallUPDATEMethod);
                                    }


                                }
                                else{
                                    AlertDialogClass.showDialog(getActivity(),R.string.CannotSaveWithOutGenderAlert);

                                }
                            }
                            else{
                                AlertDialogClass.showDialog(getActivity(),R.string.CannotSaveWithOutBreedTypeAlert);

                            }
                        }
                        else{
                            AlertDialogClass.showDialog(getActivity(),R.string.CannotSaveWithOutItemnameAlert);

                        }
                    }
                    else{
                        AlertDialogClass.showDialog(getActivity(),R.string.CannotSaveWithOutBarcodeAlert);

                    }
                }
                else{
                    AlertDialogClass.showDialog(getActivity(),R.string.CannotSaveWithOutWeightDetailsAlert);

                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

        });

        scanBarcode_view.setOnClickListener(view13 -> {

            if(calledFrom.equals(getString(R.string.add_new_item))){
                Initialize_and_StartBarcodeScanner(getString(R.string.scannerCalled_Not_to_FetchData));

            }
            else if(calledFrom.equals(getString(R.string.add_new_item_existing_batch))){
                Initialize_and_StartBarcodeScanner(getString(R.string.scannerCalled_Not_to_FetchData));

            }
            else if(calledFrom.equals(getString(R.string.view_edit_existing_item))){
                Initialize_and_StartBarcodeScanner(getString(R.string.scannerCalled_to_FetchData));

            }
            else if(calledFrom.equals(getString(R.string.stock_batch_item_withoutScan_allowedScan))){
                Initialize_and_StartBarcodeScanner(getString(R.string.scannerCalled_Not_to_FetchData));
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

                selectedBreed = String.valueOf(breedType_arrayList_string.get(position));


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
            //Toast.makeText(mContext, "Selected Radio Button is : " + radioButton.getText(), Toast.LENGTH_SHORT).show();
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

    private void showEditabeLayouts(boolean isEditable) {

        if(isEditable){
            save_button.setVisibility(View.VISIBLE);
            editable_weight_breed_layout.setVisibility(View.VISIBLE);
            goatstatusradiogrp.setVisibility(View.VISIBLE);
            genderRadioGroup .setVisibility(View.VISIBLE);
            description_edittext.setVisibility(View.VISIBLE);
            goatstatus_layout.setVisibility(View.GONE);


            selectedGoatStatus_textview.setVisibility(View.GONE);
            description_textiew.setVisibility(View.GONE);
            selectedGender_textview.setVisibility(View.GONE);
            noneditable_weight_breed_layout.setVisibility(View.GONE);
        }
        else{
            save_button.setVisibility(View.GONE);

            editable_weight_breed_layout.setVisibility(View.GONE);
            goatstatusradiogrp.setVisibility(View.GONE);
            genderRadioGroup .setVisibility(View.GONE);
            description_edittext.setVisibility(View.GONE);
            goatstatus_layout.setVisibility(View.GONE);

            selectedGoatStatus_textview.setVisibility(View.VISIBLE);
            description_textiew.setVisibility(View.VISIBLE);
            selectedGender_textview.setVisibility(View.VISIBLE);
            noneditable_weight_breed_layout.setVisibility(View.VISIBLE);
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
                breedType_arrayList_string = DatabaseArrayList_PojoClass.getBreedType_arrayList_string();
                isB2BItemCtgyTableServiceCalled = false;
                showProgressBar(false);
                ArrayAdapter breedType_aAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, breedType_arrayList_string);
                breedType_spinner.setAdapter(breedType_aAdapter);
                for(int iterator = 0 ;iterator<breedType_arrayList_string.size();iterator++){
                    String breedTypeFromarray =  breedType_arrayList_string.get(iterator);
                    if(breedTypeFromarray.equals(lastlySelectedBreedType)){
                        breedType_spinner.setSelection(iterator);
                    }
                }

            }

            @Override
            public void notifyError(VolleyError error) {

            }
        };
        String addApiToCall = API_Manager.getB2BItemCtgy ;
        B2BItemCtgy asyncTask = new B2BItemCtgy(callback_B2BItemCtgyInterface,  addApiToCall );
        asyncTask.execute();




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
                previousSelectedGender = "";
                batchStatus ="";
                batchNo = "";
                showProgressBar(false);
                isGoatEarTagTransactionTableServiceCalled = false;
                ((View_or_Edit_BatchItem_Supplier)getActivity()).closeFragment();

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                new Modal_UpdatedGoatEarTagDetails();
                new Modal_Static_GoatEarTagDetails();  entered_Weight_double =0;
                previous_WeightInGrams ="";
                Toast.makeText(mContext, "There is an volley error while updating Ear Tag Transaction", Toast.LENGTH_SHORT).show();
                showProgressBar(false);
                ((View_or_Edit_BatchItem_Supplier)getActivity()).closeFragment();

                isGoatEarTagTransactionTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                new Modal_UpdatedGoatEarTagDetails();
                new Modal_Static_GoatEarTagDetails();
                entered_Weight_double =0;
                previous_WeightInGrams ="";
                Toast.makeText(mContext, "There is an Process error while updating Ear Tag Transaction", Toast.LENGTH_SHORT).show();

                showProgressBar(false);
                isGoatEarTagTransactionTableServiceCalled = false;
                ((View_or_Edit_BatchItem_Supplier)getActivity()).closeFragment();


            }




        };

        if(callMethod.equals(Constants.CallADDMethod)){
            if(goatEarTagAdd_OR_Updated.equals(Constants.CallADDMethod)) {
                try {
                    new Modal_UpdatedGoatEarTagDetails();
                    Modal_GoatEarTagTransaction.barcodeno = scannedBarcode;
                    Modal_GoatEarTagTransaction.batchno = batchNo;
                    Modal_GoatEarTagTransaction.updateddate = DateParser.getDate_and_time_newFormat();
                    Modal_GoatEarTagTransaction.previousweightingrams = previous_WeightInGrams;
                    Modal_GoatEarTagTransaction.newweightingrams = String.valueOf(df.format(entered_Weight_double));
                    Modal_GoatEarTagTransaction.weighingpurpose = Constants.goatEarTagWeighingPurpose_RegularAudit;
                    Modal_GoatEarTagTransaction.status = Modal_Static_GoatEarTagDetails.getStatus();
                    Modal_GoatEarTagTransaction.gender = Modal_Static_GoatEarTagDetails.getGender();
                    Modal_GoatEarTagTransaction.breedtype = Modal_Static_GoatEarTagDetails.getBreedtype();
                    Modal_GoatEarTagTransaction.mobileno = userMobileNo;
                    Modal_GoatEarTagTransaction.description = description_edittext.getText().toString();

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
                    Modal_GoatEarTagTransaction.batchno = batchNo;
                    Modal_GoatEarTagTransaction.updateddate = DateParser.getDate_and_time_newFormat();
                    Modal_GoatEarTagTransaction.mobileno = userMobileNo;
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_currentweightingrams_boolean()){
                        Modal_GoatEarTagTransaction.previousweightingrams = previous_WeightInGrams;
                        Modal_GoatEarTagTransaction.newweightingrams =(String.valueOf(df.format(entered_Weight_double)));
                        Modal_GoatEarTagTransaction.weighingpurpose = Constants.goatEarTagWeighingPurpose_RegularAudit;

                    }
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_status_boolean()) {
                        Modal_GoatEarTagTransaction.status = Modal_Static_GoatEarTagDetails.getStatus();
                    }
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_gender_boolean()) {
                        Modal_GoatEarTagTransaction.gender = selectedGender;
                    }
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_breedtype_boolean()) {
                        Modal_GoatEarTagTransaction.breedtype = selectedBreed;
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

              /*  if (callMethod.equals(Constants.CallGETMethod)){
                    isGoatEarTagDetailsTableServiceCalled = false;
                    barcodeNo_textView.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getBarcodeno()));
                    weightDetails_edittext.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getCurrentweightingrams()));
                    description_edittext.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getDescription()));
                    try{
                       for(int iterator = 0; iterator<breedType_arrayList.size(); iterator++){
                           if(String.valueOf(Modal_Static_GoatEarTagDetails.getBreedtype()).equals(breedType_arrayList.get(iterator))){
                               breedType_spinner.setSelection(iterator);
                           }
                       }
                        }
                    catch (Exception e){
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
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    showProgressBar(false);

                }

               */
                if (callMethod.equals(Constants.CallGETMethod)){
                    isGoatEarTagDetailsTableServiceCalled = false;
                    if(result.equals(Constants.emptyResult_volley)){
                        //barcodeNo_textView.setText(scannedBarcode);
                        showProgressBar(false);
                        AlertDialogClass.showDialog(getActivity(), R.string.BatchDetailsnotFound_Instruction);
                        return;
                    }




                    if (String.valueOf(Modal_Static_GoatEarTagDetails.getStatus()).equals(Constants.goatEarTagStatus_GoatLost)) {

                        AlertDialogClass.showDialog(getActivity(), R.string.GoatLost_Instruction);
                        save_button.setVisibility(View.GONE);

                    }
                    if (String.valueOf(Modal_Static_GoatEarTagDetails.getStatus()).equals(Constants.goatEarTagStatus_EarTagLost)) {
                        save_button.setVisibility(View.GONE);
                        showProgressBar(false);
                        AlertDialogClass.showDialog(getActivity(), R.string.EarTagLost_Instruction);
                    }

                    if (String.valueOf(Modal_Static_GoatEarTagDetails.getStatus()).equals(Constants.goatEarTagStatus_Goatsick)) {
                        save_button.setVisibility(View.GONE);
                        showProgressBar(false);
                        AlertDialogClass.showDialog(getActivity(), R.string.EarTagSick_Instruction);
                    }
                    barcodeNo_textView.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getBarcodeno()));
                    weightDetails_edittext.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getCurrentweightingrams()));
                    weightDetails_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getCurrentweightingrams()));
                    breedType_textView.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getBreedtype()));
                    description_textiew.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getDescription()));
                    description_edittext.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getDescription()));
                    selectedGoatStatus_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getStatus()));
                    selectedGender_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getGender()));



                    selectedBreed = String.valueOf(Modal_Static_GoatEarTagDetails.getBreedtype());
                    selectedGender = String.valueOf(Modal_Static_GoatEarTagDetails.getGender());
                    selectedGoatStatus = String.valueOf(Modal_Static_GoatEarTagDetails.getStatus());

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
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }


                    try {

                        if (String.valueOf(selectedGoatStatus).equals(Constants.goatEarTagStatus_GoatLost)) {
                            dead_goat_radio.setChecked(false);
                            sick_goat_radio.setChecked(false);
                            normal_goat_radio.setChecked(false);
                        } else if (String.valueOf(selectedGoatStatus).equals(Constants.goatEarTagStatus_EarTagLost)) {
                            dead_goat_radio.setChecked(false);
                            sick_goat_radio.setChecked(false);
                            normal_goat_radio.setChecked(false);
                        } else if (String.valueOf(selectedGoatStatus).equals(Constants.goatEarTagStatus_Goatdead)) {
                            dead_goat_radio.setChecked(true);
                            sick_goat_radio.setChecked(false);
                            normal_goat_radio.setChecked(false);
                        }
                        else if (String.valueOf(selectedGoatStatus).equals(Constants.goatEarTagStatus_Goatsick)) {
                            dead_goat_radio.setChecked(false);
                            sick_goat_radio.setChecked(true);
                            normal_goat_radio.setChecked(false);
                        }

                        else {
                            selectedGoatStatus = getString(R.string.good);
                            dead_goat_radio.setChecked(false);
                            sick_goat_radio.setChecked(false);
                            normal_goat_radio.setChecked(true);
                        }



                    } catch (Exception e) {
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

                    //    Toast.makeText(mContext, Constants.unknown_API_Result_volley, Toast.LENGTH_SHORT).show();
                    }
                   // isGoatEarTagDetailsTableServiceCalled = false;
                   // showProgressBar(false);
                }
                else if (callMethod.equals(Constants.CallADDMethod)){


                    if(result.equals(Constants.item_Already_Added_volley)){
                        AlertDialogClass.showDialog(getActivity(), R.string.GoatEarTagAlreadyCreated_Instruction);
                        showProgressBar(false);
                        isGoatEarTagDetailsTableServiceCalled = false;

                    }
                    else if(result.equals(Constants.successResult_volley)){
                        showProgressBar(false);
                        UpdateCalculationDataINSharedPref(callMethod);
                        Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod,callMethod);

                        isGoatEarTagDetailsTableServiceCalled = false;

                    }
                    else{
                        //Toast.makeText(mContext, Constants.unknown_API_Result_volley, Toast.LENGTH_SHORT).show();
                        showProgressBar(false);
                        isGoatEarTagDetailsTableServiceCalled = false;
                    }



                }

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

             //   Modal_Static_GoatEarTagDetails.deliverycenterkey=View_or_Edit_BatchItem_Supplier.selectDeliveryCenterKey;
             //  Modal_Static_GoatEarTagDetails.deliverycentrename=View_or_Edit_BatchItem_Supplier.selectDeliveryCenterName;

                Modal_Static_GoatEarTagDetails.batchno= batchNo;
                Modal_Static_GoatEarTagDetails.itemaddeddate = DateParser.getDate_and_time_newFormat();
                Modal_Static_GoatEarTagDetails.status = Constants.batchDetailsStatus_Loading;
                Modal_Static_GoatEarTagDetails.supplierkey = Modal_SupplierDetails.getSupplierkey_static();
                Modal_Static_GoatEarTagDetails.suppliername = Modal_SupplierDetails.getSuppliername_static();

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
                Modal_UpdatedGoatEarTagDetails.setUpdated_selectedItem(selectedCategoryItem);

                Modal_UpdatedGoatEarTagDetails.setUpdated_currentweightingrams(String.valueOf(df.format(entered_Weight_double)));
                Modal_UpdatedGoatEarTagDetails.setUpdated_description(description_edittext.getText().toString());
                Modal_UpdatedGoatEarTagDetails.setUpdated_gender(selectedGender);
                Modal_UpdatedGoatEarTagDetails.setUpdated_breedtype(selectedBreed);
                String addApiToCall = API_Manager.updateGoatEarTag ;
                GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails,  addApiToCall , callMethod);
                asyncTask.execute();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (callMethod.equals(Constants.CallGETMethod)){

            String addApiToCall = API_Manager.getGoatEarTagDetails_forBarcodeWithBatchno +"?barcodeno="+scannedBarcode+"&batchno="+ batchNo ;
            GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails,  addApiToCall , callMethod);
            asyncTask.execute();

        }




    }

    private void UpdateCalculationDataINSharedPref(String callMethod) {
        String batchNo_fromPreference ="";
    int totalCount_int = 0 ,maleCount_int =0,femaleCount_int =0, femaleWithBabyCount_int =0 ;
    double total_loadedweight_double =0,minimum_weight_double =0,maximum_weight_double =0, average_weight_double =0;
        SharedPreferences sharedPreferences_forAdd  = mContext.getSharedPreferences(Constants.earTagCalculationDeta_SupplierCenter,MODE_PRIVATE);


        batchNo_fromPreference =    sharedPreferences_forAdd.getString(
                "BatchNo", "0"
        );
        if(batchNo_fromPreference.toUpperCase().equals(batchNo)) {
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


            SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constants.earTagCalculationDeta_SupplierCenter, MODE_PRIVATE);

            SharedPreferences.Editor myEdit = sharedPreferences.edit();

            if (callMethod.toUpperCase().equals(Constants.CallADDMethod)) {
                totalCount_int = totalCount_int + 1;
                myEdit.putInt(
                        "TotalCount", totalCount_int
                );
                if (selectedGender.toUpperCase().equals(getString(R.string.MALE))) {
                    maleCount_int = maleCount_int + 1;
                    myEdit.putInt(
                            "MaleCount", maleCount_int
                    );
                }
                if (selectedGender.toUpperCase().equals(getString(R.string.FEMALE))) {
                    femaleCount_int = femaleCount_int + 1;
                    myEdit.putInt(
                            "FemaleCount", femaleCount_int
                    );

                }
                if (selectedGender.toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {
                    femaleWithBabyCount_int = femaleWithBabyCount_int + 1;
                    myEdit.putInt(
                            "FemaleWithBabyCount", femaleWithBabyCount_int
                    );


                }

                total_loadedweight_double = total_loadedweight_double + entered_Weight_double;

                if (entered_Weight_double < minimum_weight_double) {
                    minimum_weight_double = entered_Weight_double;
                    myEdit.putFloat(
                            "MinimumWeight", (float) Double.parseDouble(df.format( minimum_weight_double))
                    );
                }
                if (entered_Weight_double > maximum_weight_double) {
                    maximum_weight_double = entered_Weight_double;
                    myEdit.putFloat(
                            "MaximumWeight", (float) Double.parseDouble(df.format(maximum_weight_double))
                    );
                }
                average_weight_double = total_loadedweight_double / totalCount_int;


            }
            if (callMethod.equals(Constants.CallUPDATEMethod)) {
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

                if (!previousSelectedGender.toUpperCase().equals(selectedGender.toUpperCase())) {

                    if (previousSelectedGender.toUpperCase().equals(getString(R.string.MALE))) {
                        maleCount_int = maleCount_int - 1;
                    }

                    if (previousSelectedGender.toUpperCase().equals(getString(R.string.FEMALE))) {
                        femaleCount_int = femaleCount_int - 1;
                    }

                    if (previousSelectedGender.toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {
                        femaleWithBabyCount_int = femaleWithBabyCount_int - 1;
                    }


                    if (selectedGender.toUpperCase().equals(getString(R.string.MALE))) {
                        maleCount_int = maleCount_int + 1;
                    }

                    if (selectedGender.toUpperCase().equals(getString(R.string.FEMALE))) {
                        femaleCount_int = femaleCount_int + 1;
                    }

                    if (selectedGender.toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {
                        femaleWithBabyCount_int = femaleWithBabyCount_int + 1;

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

            }


            myEdit.putFloat(
                    "AverageWeight", (float) Double.parseDouble(df.format(average_weight_double))

            );


            myEdit.putFloat(
                    "TotalWeight", (float) Double.parseDouble(df.format(total_loadedweight_double))
            );


            myEdit.apply();

        }



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
        intent.putExtra(getString(R.string.called_from), getString(R.string.supplier));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        mContext.startActivity(intent);



    }


    //Doing the same with this method as we did with getName()
    private String getBreedTypeList(int position,String fieldName){
        String data="";
        try {
            for(int i =0 ;i<DatabaseArrayList_PojoClass.breedType_arrayList.size() ; i++){
                Modal_B2BItemCtgy itemCtgy = DatabaseArrayList_PojoClass.breedType_arrayList.get(position);




            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }



}