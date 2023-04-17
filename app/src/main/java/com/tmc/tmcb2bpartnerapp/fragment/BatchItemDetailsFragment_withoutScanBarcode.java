package com.tmc.tmcb2bpartnerapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.activity.GoatEarTagItemDetailsList;
import com.tmc.tmcb2bpartnerapp.adapter.Adapter_goatGradeDetailsSpinnerItems;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BItemCtgy;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagTransaction;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartItemDetaillsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BGoatGradeDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BItemCtgyInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.BarcodeScannerInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagTransactionInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetailsStatic;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BItemCtgy;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagTransaction;
import com.tmc.tmcb2bpartnerapp.modal.Modal_Static_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_UpdatedGoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.TMCAlertDialogClass;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;
import static android.os.Build.VERSION.SDK_INT;
import static com.tmc.tmcb2bpartnerapp.activity.GoatEarTagItemDetailsList.earTagItemsForBatch;
import static com.tmc.tmcb2bpartnerapp.activity.GoatEarTagItemDetailsList.showProgressBar;
import static com.tmc.tmcb2bpartnerapp.utils.Constants.CallGETMethod;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BatchItemDetailsFragment_withoutScanBarcode#newInstance} factory method to
 * create an instance of this fragment.
 */

public class BatchItemDetailsFragment_withoutScanBarcode extends Fragment {

    TextView loadedWeightLabel,chooseGrade_label,selectedGrade_textview,chooseitemSpinnerLabel,toolBarHeader_TextView,barcodeNo_textView,weightDetails_textview,breedType_textView,gender_label_textview,
            description_textiew,selectedGender_textview,selectedGoatStatus_textview,loaded_weight_ingrams_textview,gender_textview;
    ImageView backButton_icon;
    Context mContext;
    Button save_button;
    String calledFrom,scannedBarcode="",selectedCategoryItem="",selectedGender="",selectedBreed="", selectedGradeName ="" , selectedGradePrice ="",selectedGradeKey =""
            ,userType ="",userMobileNo ="",selectedGoatStatus="",earTaglastStatusFromDB = "",lastlySelectedSupplierKey="" , batchStatus ="" ;
    public static BarcodeScannerInterface barcodeScannerInterface = null;
    Spinner chooseItem_spinner,chooseGrade_spinner,breedType_spinner;
    ArrayList<String> itemCategory_arrayList = new ArrayList<>();
    ArrayList<Modal_B2BGoatGradeDetails> goatGrade_arrayLsit = new ArrayList<>();
    String selectedgoatGrade_inarrayList = "";
    ArrayList<String> breedType_arrayList_string = new ArrayList<>();
    EditText description_edittext,weightDetails_edittext,current_weight_edittext,barcodeNo_EdittextView;
    private RadioGroup genderRadioGroup;
    GoatEarTagDetailsInterface callback_GoatEarTagDetails = null;
    boolean  isGoatEarTagDetailsTableServiceCalled = false;

    GoatEarTagTransactionInterface callback_GoatEarTagTransactionInterface = null;
    boolean  isGoatEarTagTransactionTableServiceCalled = false;

    B2BItemCtgyInterface callback_B2BItemCtgyInterface = null;
    boolean  isB2BItemCtgyTableServiceCalled = false;

    B2BCartItemDetaillsInterface callback_b2BCartItemDetaillsInterface = null;
    B2BGoatGradeDetailsInterface callback_goatGradeDetailsInterface = null;
    boolean isGoatGradeDetailsServiceCalled = false;

    B2BCartOrderDetailsInterface callback_b2bOrderDetails =null ;
    boolean isB2BCartOrderTableServiceCalled = false;

    RadioButton male_radioButton,female_radioButton,female_WithBaby_radioButton;
    RadioGroup goatstatusradiogrp;
    double entered_Weight_double = 0 ;
    String previous_WeightInGrams  = "0";
    String previousSelectedGender = "",previouslySelectedGradekey ="" , previouslySelectedGradePrice ="";
    String  batchNo ="" , deliveryCenterKey ="", deliveryCenterName ="" , orderid ="";
    DecimalFormat df = new DecimalFormat(Constants.threeDecimalPattern);
    LinearLayout back_IconLayout,goatstatus_layout,noneditable_weight_breed_layout,editable_weight_breed_layout
            ,editable_weight_breed_Label_layout;

    RadioButton dead_goat_radio, sick_goat_radio,normal_goat_radio,sold_goat_radio;
    boolean getWeight_current_weight_edittext = false;
    boolean isStatusUpdatedFromDB =  false;
    boolean isB2BCartDetailsCalled = false;
    boolean isBatchDetailsTableServiceCalled = false;
    public static ArrayList<Modal_B2BItemCtgy> ctgy_subCtgy_DetailsArrayList = new ArrayList<>();
    B2BBatchDetailsInterface callback_B2BBatchDetailsInterface;
    EditTextListener EditTextListener = null;

    public BatchItemDetailsFragment_withoutScanBarcode() {
        // Required empty public constructor
    }

    public static BatchItemDetailsFragment_withoutScanBarcode newInstance(String key, String value) {
        BatchItemDetailsFragment_withoutScanBarcode fragment = new BatchItemDetailsFragment_withoutScanBarcode();
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
                return inflater.inflate(R.layout.fragment_batch_item_details_without_scan_barcode, container, false);
            } else {

                // Inflate the layout for this fragment
                return inflater.inflate(R.layout.pos_fragment_batch_item_details_without_scan_barcode, container, false);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return inflater.inflate(R.layout.fragment_batch_item_details_without_scan_barcode, container, false);

        }



    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        toolBarHeader_TextView = view.findViewById(R.id.toolBarHeader_TextView);
        backButton_icon = view.findViewById(R.id.backButton_icon);
        save_button = view.findViewById(R.id.save_button);
        barcodeNo_textView  = view.findViewById(R.id.barcodeNo_textView);
        weightDetails_edittext  = view.findViewById(R.id.weightDetails_edittext);
        chooseItem_spinner = view.findViewById(R.id.chooseItem_spinner);
        chooseGrade_spinner = view .findViewById(R.id.chooseGrade_spinner);
        chooseitemSpinnerLabel = view.findViewById(R.id.chooseitemSpinnerLabel);
        genderRadioGroup =  view.findViewById(R.id.genderRadioGroup);
        breedType_spinner =  view.findViewById(R.id.breedType_spinner);
        current_weight_edittext  =  view.findViewById(R.id.current_weight_edittext);
        description_edittext =  view.findViewById(R.id.description_edittext);
        female_radioButton =  view.findViewById(R.id.female_radioButton);
        male_radioButton =  view.findViewById(R.id.male_radioButton);
        female_WithBaby_radioButton =  view.findViewById(R.id.female_WithBaby_radioButton);
        back_IconLayout  =  view.findViewById(R.id.back_IconLayout);
        goatstatus_layout  =  view.findViewById(R.id.goatstatus_layout);
        normal_goat_radio = view.findViewById(R.id.normal_goat_radio);
        dead_goat_radio = view.findViewById(R.id.dead_goat_radio);
        sick_goat_radio = view.findViewById(R.id.sick_goat_radio);
        sold_goat_radio = view.findViewById(R.id.sold_goat_radio);
      //  weightDetails_textview = view.findViewById(R.id.weightDetails_textview);
        breedType_textView = view.findViewById(R.id.breedType_textView);
        description_textiew  = view.findViewById(R.id.description_textiew);
        loaded_weight_ingrams_textview = view.findViewById(R.id.loaded_weight_ingrams_textview);
        gender_textview  = view.findViewById(R.id.gender_textview);
        selectedGoatStatus_textview = view.findViewById(R.id.selectedGoatStatus_textview);
        editable_weight_breed_layout = view.findViewById(R.id.editable_weight_breed_layout);
        noneditable_weight_breed_layout = view.findViewById(R.id.noneditable_weight_breed_layout);
        goatstatusradiogrp  = view.findViewById(R.id.goatstatusradiogrp);
        selectedGender_textview = view.findViewById(R.id.selectedGender_textview);
        editable_weight_breed_Label_layout = view.findViewById(R.id.editable_weight_breed_Label_layout);
        gender_label_textview = view.findViewById(R.id.gender_label_textview);
        selectedGrade_textview = view.findViewById(R.id.selectedGrade_textview);
        chooseGrade_label =  view.findViewById(R.id.chooseGrade_label);
        loadedWeightLabel =  view.findViewById(R.id.loadedWeightLabel);
        barcodeNo_EdittextView =  view.findViewById(R.id.barcodeNo_EdittextView);
        EditTextListener = new EditTextListener();
        barcodeNo_EdittextView .addTextChangedListener(EditTextListener);

        SharedPreferences sh
                = mContext.getSharedPreferences("LoginData",
                MODE_PRIVATE);
        userType = sh.getString("UserType", "");
        userMobileNo = sh.getString("UserMobileNumber", "");


        batchNo = String.valueOf( Modal_B2BBatchDetailsStatic.getBatchno());

        batchStatus = String.valueOf( Modal_Static_GoatEarTagDetails.getBatchWiseStatus().toUpperCase());

        SharedPreferences sh1 = mContext.getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);

        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");


            itemCategory_arrayList.add("Goat");
            ArrayAdapter aAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, itemCategory_arrayList);
            chooseItem_spinner.setAdapter(aAdapter);

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



        updateUIBasedOnCalledFrom();
        toolBarHeader_TextView.setText(calledFrom);
        getDataFrom_POJO_AND_setData_inUI();







        if(calledFrom.equals(getString(R.string.billing_Screen_placeOrder)) || calledFrom.equals(getString(R.string.billing_Screen_editOrder)) || calledFrom.equals(getString(R.string.pos_billing_Screen_placeOrder)) ||  calledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion)) ||  calledFrom.equals(getString(R.string.pos_placedOrder_Details_Screen_SecondVersion))  )
        {

           // chooseGrade_label.setText("Grade Details");
           // selectedGrade_textview.setVisibility(View.VISIBLE);
           // setAdapterForgoatGradeArrayList();

           // Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallGETMethod, "");
            batchNo = String.valueOf( Modal_Static_GoatEarTagDetails.getBatchno());
            Intialize_And_Process_BatchDetails(batchNo,CallGETMethod);

        }


        else if(calledFrom.equals(getString(R.string.deliverycenter_UnsoldgoatItemList))){
            batchNo  = Modal_Static_GoatEarTagDetails.getBatchno();
            Initialize_and_ExecuteB2BCartOrderDetails(Constants.CallGETMethod, "");

        }

        else if(calledFrom.equals(getString(R.string.pos_billing_Screen_placeOrder))){
             //new Modal_Static_GoatEarTagDetails();
            updateUIBasedOnCalledFrom();
        }

        else{
            updateUIBasedOnCalledFrom();

        }




        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(calledFrom.equals(getString(R.string.billing_Screen_placeOrder)) ){
               //     ((BillingScreen)getActivity()).closeFragment();
                    DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.closeFragment();
                }
                else if(calledFrom.equals(getString(R.string.pos_billing_Screen_placeOrder)) ){
                    //     ((BillingScreen)getActivity()).closeFragment();
                    DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.closeFragment();
                }
                else if(calledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion)) ){
                    //     ((BillingScreen)getActivity()).closeFragment();
                    DeliveryCenter_PlaceOrderScreen_SecondVersn.deliveryCenter_placeOrderScreen_secondVersn.closeFragment();
                }
                else if(calledFrom.equals(getString(R.string.pos_placedOrder_Details_Screen_SecondVersion)) ){
                    //     ((BillingScreen)getActivity()).closeFragment();
                    DeliveryCenter_PlaceOrderScreen_SecondVersn.deliveryCenter_placeOrderScreen_secondVersn.closeFragment();
                }
                else{
                    ((GoatEarTagItemDetailsList)getActivity()).closeFragment();
                }

            }
        });

        chooseGrade_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {


                    selectedGradeName = goatGrade_arrayLsit.get(position).getName();
                    selectedGradePrice = goatGrade_arrayLsit.get(position).getPrice();
                    selectedGradeKey = goatGrade_arrayLsit.get(position).getKey();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        save_button.setOnClickListener(view1 ->{
            entered_Weight_double = 0;
            String  entered_Weight = "";
            try {
                if(getWeight_current_weight_edittext){
                    entered_Weight = current_weight_edittext.getText().toString();

                }
                else{
                    entered_Weight = weightDetails_edittext.getText().toString();

                }
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


                                    if(calledFrom.equals(getString(R.string.add_new_item))){


                                       // Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallADDMethod);
                                        //    Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod,Constants.CallADDMethod);
                                    }
                                    else if(calledFrom.equals(getString(R.string.add_new_item_existing_batch))){
                                     //   Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallADDMethod);
                                        //  Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod,Constants.CallADDMethod);
                                    }
                                    else if(calledFrom.equals(getString(R.string.supplier_goatItemList))){
                                        previous_WeightInGrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();
                                        previousSelectedGender = Modal_Static_GoatEarTagDetails.getGender();
                                        Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);
                                        //  Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod,Constants.CallUPDATEMethod);
                                    }
                                    else if(calledFrom.equals(getString(R.string.deliverycenter_SoldgoatItemList))){
                                        previous_WeightInGrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();
                                        previousSelectedGender = Modal_Static_GoatEarTagDetails.getGender();
                                        Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);
                                        //  Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod,Constants.CallUPDATEMethod);
                                    }
                                    else if(calledFrom.equals(getString(R.string.deliverycenter_UnsoldgoatItemList))){
                                        previous_WeightInGrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();
                                        previousSelectedGender = Modal_Static_GoatEarTagDetails.getGender();
                                        Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);
                                        //  Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod,Constants.CallUPDATEMethod);
                                    }
                                    else if(calledFrom.equals(getString(R.string.deliverycenter_ReviewedgoatItemList))){
                                        previous_WeightInGrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();
                                        previousSelectedGender = Modal_Static_GoatEarTagDetails.getGender();
                                        Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);
                                        //  Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod,Constants.CallUPDATEMethod);
                                    }
                                    else if(calledFrom.equals(getString(R.string.stock_batch_item_withoutScan))){
                                        previous_WeightInGrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();
                                        previousSelectedGender = Modal_Static_GoatEarTagDetails.getGender();
                                        Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);
                                        //  Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod,Constants.CallUPDATEMethod);
                                    }
                                    else if(calledFrom.equals(getString(R.string.billing_Screen_placeOrder))){
                                        previous_WeightInGrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();
                                        previousSelectedGender = Modal_Static_GoatEarTagDetails.getGender();
                                        Initialize_and_ExecuteB2BOrderCartDetails(Constants.CallADDMethod);

                                          //  Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);


                                        //addItemInTheBillingScreenArrayList();

                                        //  Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod,Constants.CallUPDATEMethod);
                                    }
                                    else if(calledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion))){
                                        previous_WeightInGrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();
                                        previousSelectedGender = Modal_Static_GoatEarTagDetails.getGender();
                                        Initialize_and_ExecuteB2BOrderCartDetails(Constants.CallADDMethod);

                                        //  Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);


                                        //addItemInTheBillingScreenArrayList();

                                        //  Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod,Constants.CallUPDATEMethod);
                                    }
                                    else if(calledFrom.equals(getString(R.string.pos_placedOrder_Details_Screen_SecondVersion))){
                                        previous_WeightInGrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();
                                        previousSelectedGender = Modal_Static_GoatEarTagDetails.getGender();
                                        Initialize_and_ExecuteB2BOrderCartDetails(Constants.CallADDMethod);

                                        //  Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);


                                        //addItemInTheBillingScreenArrayList();

                                        //  Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod,Constants.CallUPDATEMethod);
                                    }
                                    else if(calledFrom.equals(getString(R.string.pos_billing_Screen_placeOrder))){
                                        previous_WeightInGrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();
                                        previousSelectedGender = Modal_Static_GoatEarTagDetails.getGender();
                                        Initialize_and_ExecuteB2BOrderCartDetails(Constants.CallADDMethod);

                                        //  Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);


                                        //addItemInTheBillingScreenArrayList();

                                        //  Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod,Constants.CallUPDATEMethod);
                                    }
                                    else if(calledFrom.equals(getString(R.string.billing_Screen_editOrder))){
                                        previous_WeightInGrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();
                                        previousSelectedGender = Modal_Static_GoatEarTagDetails.getGender();
                                        previouslySelectedGradekey = Modal_Static_GoatEarTagDetails.getGradekey();
                                        previouslySelectedGradePrice = Modal_Static_GoatEarTagDetails.getGradeprice();
                                         Initialize_and_ExecuteB2BOrderCartDetails(Constants.CallUPDATEMethod);
                                        //addItemInTheBillingScreenArrayList();

                                       // Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);
                                    }

                                    else {
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
        goatstatusradiogrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStatusUpdatedFromDB = false;
            }
        });
        goatstatusradiogrp.setOnCheckedChangeListener((group, checkedId) -> {
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




        genderRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            // on below line we are getting radio button from our group.
            RadioButton radioButton = view.findViewById(checkedId);
            selectedGender =  String.valueOf(radioButton.getText());
            // on below line we are displaying a toast message.
            //Toast.makeText(mContext, "Selected Radio Button is : " + radioButton.getText(), Toast.LENGTH_SHORT).show();
        });

        current_weight_edittext.addTextChangedListener(new TextWatcher(){

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
                    current_weight_edittext.setText(result);
                    current_weight_edittext.setSelection(current_weight_edittext.getText().length());
                } else{
                    try {
                        weight = weight.replaceAll("[^\\d.]", "");

                        if(weight.equals("") || weight.equals(null)){
                            weight = "0";
                        }

                        if(weight.equals("")){
                            weight ="0";
                        }
                        double weight_double = Double.parseDouble(weight);
                        if (weight_double > 50) {

                            AlertDialogClass.showDialog(getActivity(), R.string.EnteredWeightShouldbelessthan50Instruction);
                            current_weight_edittext.setText("0");
                            current_weight_edittext.setSelection(current_weight_edittext.getText().length());
                        } else if (weight_double < 0) {
                            AlertDialogClass.showDialog(getActivity(), R.string.EnteredWeightShouldbegreaterthan0Instruction);
                            current_weight_edittext.setText("0");
                            current_weight_edittext.setSelection(current_weight_edittext.getText().length());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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

    private void getDataFrom_POJO_AND_setData_inUI() {

        batchStatus = String.valueOf(Modal_Static_GoatEarTagDetails.getBatchWiseStatus());
        scannedBarcode = String.valueOf(Modal_Static_GoatEarTagDetails.getBarcodeno());
        previous_WeightInGrams = String.valueOf(Modal_Static_GoatEarTagDetails.getCurrentweightingrams());
        selectedBreed = String.valueOf(Modal_Static_GoatEarTagDetails.getBreedtype());
        selectedGender = String.valueOf(Modal_Static_GoatEarTagDetails.getGender());
        selectedGoatStatus = String.valueOf(Modal_Static_GoatEarTagDetails.getStatus());
        selectedGradePrice = String.valueOf(Modal_Static_GoatEarTagDetails.getGradeprice());
        selectedGradeName = String.valueOf(Modal_Static_GoatEarTagDetails.getGradename());
        selectedGradeKey = String.valueOf(Modal_Static_GoatEarTagDetails.getGradekey());
        earTaglastStatusFromDB = String.valueOf(Modal_Static_GoatEarTagDetails.getStatus());
        if(selectedGoatStatus.toUpperCase().equals(Constants.goatEarTagStatus_Loading) ||selectedGoatStatus.toUpperCase().equals(Constants.goatEarTagStatus_Sold) || selectedGoatStatus.toUpperCase().equals(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE) ){
            selectedGoatStatus = getString(R.string.good);

        }

        barcodeNo_textView.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getBarcodeno()));
        weightDetails_edittext.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getCurrentweightingrams()));
        //weightDetails_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getCurrentweightingrams()));
        breedType_textView.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getBreedtype()));
        description_textiew.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getDescription()));
        description_edittext.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getDescription()));
        selectedGoatStatus_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getStatus()));
        selectedGender_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getGender()));
        current_weight_edittext.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getCurrentweightingrams()));
        gender_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getGender()));


        if(Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Loading)){
            loadedWeightLabel.setText(getString(R.string.loaded_weightInGrams));
            loaded_weight_ingrams_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getLoadedweightingrams()));

        }
        else   if(Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE) || Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatsick) || Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Sold)){
            loadedWeightLabel.setText(getString(R.string.stocked_weightInGrams));
            loaded_weight_ingrams_textview.setText(String.valueOf(Modal_Static_GoatEarTagDetails.getStockedweightingrams()));

        }




        if(DatabaseArrayList_PojoClass.breedType_arrayList_string.size()>0){
            breedType_arrayList_string = DatabaseArrayList_PojoClass.breedType_arrayList_string;
            ArrayAdapter breedType_aAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, breedType_arrayList_string);
            breedType_spinner.setAdapter(breedType_aAdapter);
            for(int iterator = 0 ;iterator<breedType_arrayList_string.size();iterator++){
                String breedTypeFromarray =  breedType_arrayList_string.get(iterator);
                if(breedTypeFromarray.equals(selectedBreed)){
                    breedType_spinner.setSelection(iterator);
                }
            }
            ctgy_subCtgy_DetailsArrayList = DatabaseArrayList_PojoClass.breedType_arrayList;

        }
        else{
            Initialize_and_ExecuteB2BCtgyItem();
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



        try {
            //  isStatusUpdatedFromDB = true;
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
                sick_goat_radio.setChecked(true);
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
        showProgressBar_in_theActivity(false);

    }

    private void updateUIBasedOnCalledFrom() {

        if(userType.equals(Constants.userType_SupplierCenter)) {
            goatstatus_layout.setVisibility(View.GONE);
            getWeight_current_weight_edittext = false;
            if (calledFrom.equals(getString(R.string.supplier_goatItemList))) {
                chooseGrade_spinner.setVisibility(View.GONE);
                chooseGrade_label.setVisibility(View.GONE);
                if (batchStatus.equals(Constants.batchDetailsStatus_Loading)   ) {
                    if(Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Loading)){
                        showEditabeLayouts(true,false);
                    }
                    else{
                        showEditabeLayouts(false, false);
                    }


                } else {
                    showEditabeLayouts(false, false);

                }
            }
        }
        else if(userType.equals(Constants.userType_DeliveryCenter)) {
            getWeight_current_weight_edittext = true;
            if (calledFrom.equals("ViewUnReviewedItem")) {

                if (batchStatus.equals(Constants.batchDetailsStatus_Sold) || batchStatus.equals(Constants.batchDetailsStatus_Cancelled)) {

                    showEditabeLayouts(false, true);
                } else {
                    showEditabeLayouts(true, true);
                    goatstatus_layout.setVisibility(View.VISIBLE);


                }


            }
            else if (calledFrom.equals(getString(R.string.deliverycenter_ReviewedgoatItemList))) {
                if (batchStatus.equals(Constants.batchDetailsStatus_Sold) || batchStatus.equals(Constants.batchDetailsStatus_Cancelled)) {

                    showEditabeLayouts(false, true);
                } else {
                    showEditabeLayouts(true, true);
                    goatstatus_layout.setVisibility(View.VISIBLE);


                }
            }
            else if(calledFrom.equals(getString(R.string.deliverycenter_SoldgoatItemList))){

                sold_goat_radio.setVisibility(View.VISIBLE);
                showEditabeLayouts(false, false);

                goatstatus_layout.setVisibility(View.GONE);



            }
            else if(calledFrom.equals(getString(R.string.deliverycenter_UnsoldgoatItemList))){
                if (batchStatus.equals(Constants.batchDetailsStatus_Sold) || batchStatus.equals(Constants.batchDetailsStatus_Cancelled)) {

                    showEditabeLayouts(false, false);
                } else {
                    sold_goat_radio.setVisibility(View.GONE);
                    showEditabeLayouts(true, true);

                    goatstatus_layout.setVisibility(View.VISIBLE);

                }


            }
            else if(calledFrom.equals(getString(R.string.stock_batch_item_withoutScan))){

                if (batchStatus.equals(Constants.batchDetailsStatus_Sold) || batchStatus.equals(Constants.batchDetailsStatus_Cancelled)) {

                    showEditabeLayouts(false, true);
                } else {
                    showEditabeLayouts(true, true);
                    goatstatus_layout.setVisibility(View.VISIBLE);


                }


            }
            else if(calledFrom.equals(getString(R.string.billing_Screen_placeOrder)) || calledFrom.equals(getString(R.string.billing_Screen_editOrder))){


                showEditabeLayouts(false, true);

                goatstatus_layout.setVisibility(View.GONE);

            }
            else if(calledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion)) ){
                showEditabeLayouts(false, true);

                goatstatus_layout.setVisibility(View.GONE);


            }
            else if(calledFrom.equals(getString(R.string.pos_placedOrder_Details_Screen_SecondVersion))) {
                barcodeNo_textView.setVisibility(View.VISIBLE);
                barcodeNo_EdittextView.setVisibility(View.GONE);
                showEditabeLayouts(false, true);
                goatstatus_layout.setVisibility(View.GONE);
            }


            else if(calledFrom.equals(getString(R.string.pos_billing_Screen_placeOrder))){
                barcodeNo_textView.setVisibility(View.VISIBLE);
                barcodeNo_EdittextView.setVisibility(View.GONE);
                showEditabeLayouts(false, true);

                goatstatus_layout.setVisibility(View.GONE);

            }
            else if(calledFrom.equals(getString(R.string.placedOrder_Details_Screen))){

                showEditabeLayouts(false, false);

                goatstatus_layout.setVisibility(View.GONE);


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
                    selectedGradePrice = goatGrade_arrayLsit.get(iterator).getPrice();
                    selectedGradeName = goatGrade_arrayLsit.get(iterator).getName();
                    chooseGrade_spinner.setSelection(iterator);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        showProgressBar_in_theActivity(false);

    }



    private void Call_and_Initialize_GoatGradeDetails(String ApiMethod) {
        showProgressBar_in_theActivity(true);
        if (isGoatGradeDetailsServiceCalled) {
            showProgressBar_in_theActivity(false);

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
                showProgressBar_in_theActivity(false);
                isGoatGradeDetailsServiceCalled = false;

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isGoatGradeDetailsServiceCalled = false;showProgressBar_in_theActivity(false);
                //    Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 3 " + DateParser.getDate_and_time_newFormat());

            }

            @Override
            public void notifyProcessingError(Exception error) {
                isGoatGradeDetailsServiceCalled = false;showProgressBar_in_theActivity(false);
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());

            }


        };


        if(ApiMethod.equals(Constants.CallGETListMethod)){
            goatGrade_arrayLsit.clear();selectedgoatGrade_inarrayList = "";
            String getApiToCall = API_Manager.getgoatGradeForDeliveryCentreKey +deliveryCenterKey;

            B2BGoatGradeDetails asyncTask = new B2BGoatGradeDetails(callback_goatGradeDetailsInterface,  getApiToCall, Constants.CallGETListMethod);
            asyncTask.execute();



        }



    }


    private void addItemInTheBillingScreenArrayList() {

    /* if(BillingScreen.earTagDetailsArrayList_String.contains(Modal_Static_GoatEarTagDetails.getBarcodeno())){


         if(BillingScreen.earTagDetails_weightStringHashMap.containsKey(Modal_Static_GoatEarTagDetails.getBarcodeno())) {

             JSONObject jsonObject = new JSONObject();
             try{
                 jsonObject.put("weight",String.valueOf(entered_Weight_double));
                 jsonObject.put("gradekey",selectedGradeKey);
                 jsonObject.put("gradeprice",selectedGradePrice);
             }
             catch (Exception e){
                 e.printStackTrace();
             }

             if (SDK_INT >= Build.VERSION_CODES.N) {

                 Objects.requireNonNull(BillingScreen.earTagDetails_weightStringHashMap.replace(Modal_Static_GoatEarTagDetails.getBarcodeno(),jsonObject ));
             }
             else{
                 Objects.requireNonNull(BillingScreen.earTagDetails_weightStringHashMap.put(Modal_Static_GoatEarTagDetails.getBarcodeno() , jsonObject));
             }
             try{
                 Modal_B2BCartItemDetails modal_b2BCartItemDetails = new Modal_B2BCartItemDetails();
                 modal_b2BCartItemDetails.barcodeno= Modal_Static_GoatEarTagDetails.getBarcodeno();
                 modal_b2BCartItemDetails.batchno= Modal_Static_GoatEarTagDetails.getBatchno();
                 modal_b2BCartItemDetails.itemaddeddate= Modal_Static_GoatEarTagDetails.getItemaddeddate();
                 modal_b2BCartItemDetails.weightingrams= String.valueOf(entered_Weight_double);
                 modal_b2BCartItemDetails.gender= Modal_Static_GoatEarTagDetails.getGender();
                 modal_b2BCartItemDetails.status= Modal_Static_GoatEarTagDetails.getStatus();
                 modal_b2BCartItemDetails.orderid= BillingScreen.orderid;
                 modal_b2BCartItemDetails.b2bctgykey= Modal_Static_GoatEarTagDetails.getB2bctgykey();
                 modal_b2BCartItemDetails.b2bsubctgykey= Modal_Static_GoatEarTagDetails.getB2bsubctgykey();
                 modal_b2BCartItemDetails.oldweightingrams= previous_WeightInGrams;
                 modal_b2BCartItemDetails.gradename= selectedGradeName;
                 modal_b2BCartItemDetails.gradeprice= selectedGradePrice;
                 modal_b2BCartItemDetails.gradekey= selectedGradeKey;
                 modal_b2BCartItemDetails.oldgradekey =previouslySelectedGradekey;
                 modal_b2BCartItemDetails.oldgradeprice = previouslySelectedGradePrice;
                 if(calledFrom.equals(getString(R.string.billing_Screen_placeOrder))) {
                     BillingScreen.calculateGradewiseQuantity_and_Weight(modal_b2BCartItemDetails );
                 }
                 else  if(calledFrom.equals(getString(R.string.billing_Screen_editOrder))){
                     BillingScreen.removeEntryFromGradewiseQuantity_and_Weight(modal_b2BCartItemDetails,true);
                 }
             }
             catch (Exception e){
                 e.printStackTrace();
             }
             BillingScreen.calculateTotalweight_Quantity_Price();
         }
         else{
             Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();

             modal_goatEarTagDetails.barcodeno = Modal_Static_GoatEarTagDetails.getBarcodeno();
             modal_goatEarTagDetails.batchno = Modal_Static_GoatEarTagDetails.getBatchno();
             modal_goatEarTagDetails.status = Modal_Static_GoatEarTagDetails.getStatus();
             modal_goatEarTagDetails.suppliername = Modal_Static_GoatEarTagDetails.getSuppliername();
             modal_goatEarTagDetails.supplierkey = Modal_Static_GoatEarTagDetails.getSupplierkey();
             modal_goatEarTagDetails.description = Modal_Static_GoatEarTagDetails.getDescription();
             modal_goatEarTagDetails.itemaddeddate = Modal_Static_GoatEarTagDetails.getItemaddeddate();
             modal_goatEarTagDetails.stockedweightingrams = Modal_Static_GoatEarTagDetails.getStockedweightingrams();
             modal_goatEarTagDetails.selecteditem = Modal_Static_GoatEarTagDetails.getSelecteditem();
             modal_goatEarTagDetails.gender = Modal_Static_GoatEarTagDetails.getGender();
             modal_goatEarTagDetails.breedtype = Modal_Static_GoatEarTagDetails.getBreedtype();
             modal_goatEarTagDetails.loadedweightingrams = Modal_Static_GoatEarTagDetails.getLoadedweightingrams();
             modal_goatEarTagDetails.currentweightingrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();
             modal_goatEarTagDetails.newWeight_forBillingScreen = String.valueOf(entered_Weight_double);
             modal_goatEarTagDetails.gradename= selectedGradeName;
             modal_goatEarTagDetails.gradeprice= selectedGradePrice;
             modal_goatEarTagDetails.gradekey= selectedGradeKey;



             JSONObject jsonObject = new JSONObject();
             try{
                 jsonObject.put("weight",String.valueOf(entered_Weight_double));
                 jsonObject.put("gradekey",selectedGradeKey);
                 jsonObject.put("gradeprice",selectedGradePrice);
             }
             catch (Exception e){
                 e.printStackTrace();
             }
            // BillingScreen.earTagDetailsHashMap.put(Modal_Static_GoatEarTagDetails.getBarcodeno(),modal_goatEarTagDetails);
             Objects.requireNonNull(BillingScreen.earTagDetails_weightStringHashMap.put(Modal_Static_GoatEarTagDetails.getBarcodeno() , jsonObject));

             try{
                 Modal_B2BCartItemDetails modal_b2BCartItemDetails = new Modal_B2BCartItemDetails();
                 modal_b2BCartItemDetails.barcodeno= Modal_Static_GoatEarTagDetails.getBarcodeno();
                 modal_b2BCartItemDetails.batchno= Modal_Static_GoatEarTagDetails.getBatchno();
                 modal_b2BCartItemDetails.itemaddeddate= Modal_Static_GoatEarTagDetails.getItemaddeddate();
                 modal_b2BCartItemDetails.weightingrams= String.valueOf(entered_Weight_double);
                 modal_b2BCartItemDetails.gender= Modal_Static_GoatEarTagDetails.getGender();
                 modal_b2BCartItemDetails.status= Modal_Static_GoatEarTagDetails.getStatus();
                 modal_b2BCartItemDetails.orderid= BillingScreen.orderid;
                 modal_b2BCartItemDetails.b2bctgykey= Modal_Static_GoatEarTagDetails.getB2bctgykey();
                 modal_b2BCartItemDetails.b2bsubctgykey= Modal_Static_GoatEarTagDetails.getB2bsubctgykey();
                 modal_b2BCartItemDetails.oldweightingrams= previous_WeightInGrams;
                 modal_b2BCartItemDetails.gradename= selectedGradeName;
                 modal_b2BCartItemDetails.gradeprice= selectedGradePrice;
                 modal_b2BCartItemDetails.gradekey= selectedGradeKey;
                 modal_b2BCartItemDetails.oldgradekey =previouslySelectedGradekey;
                 modal_b2BCartItemDetails.oldgradeprice = previouslySelectedGradePrice;
                 if(calledFrom.equals(getString(R.string.billing_Screen_placeOrder))) {
                     BillingScreen.calculateGradewiseQuantity_and_Weight(modal_b2BCartItemDetails );
                 }
                 else  if(calledFrom.equals(getString(R.string.billing_Screen_editOrder))){
                     BillingScreen.removeEntryFromGradewiseQuantity_and_Weight(modal_b2BCartItemDetails,true);
                 }
             }
             catch (Exception e){
                 e.printStackTrace();
             }
             BillingScreen.calculateTotalweight_Quantity_Price();

         }


     }
     else{


         if(BillingScreen.earTagDetails_weightStringHashMap.containsKey(Modal_Static_GoatEarTagDetails.getBarcodeno())) {
             JSONObject jsonObject = new JSONObject();
             try{
                 jsonObject.put("weight",String.valueOf(entered_Weight_double));
                 jsonObject.put("gradekey",selectedGradeKey);
                 jsonObject.put("gradeprice",selectedGradePrice);
             }
             catch (Exception e){
                 e.printStackTrace();
             }
             if (SDK_INT >= Build.VERSION_CODES.N) {
                 Objects.requireNonNull(BillingScreen.earTagDetails_weightStringHashMap.replace(Modal_Static_GoatEarTagDetails.getBarcodeno(), jsonObject));
             }
             else{
                 Objects.requireNonNull(BillingScreen.earTagDetails_weightStringHashMap.put(Modal_Static_GoatEarTagDetails.getBarcodeno() , jsonObject));
             }

             BillingScreen.earTagDetailsArrayList_String.add(Modal_Static_GoatEarTagDetails.getBarcodeno());
             try{
                 Modal_B2BCartItemDetails modal_b2BCartItemDetails = new Modal_B2BCartItemDetails();
                 modal_b2BCartItemDetails.barcodeno= Modal_Static_GoatEarTagDetails.getBarcodeno();
                 modal_b2BCartItemDetails.batchno= Modal_Static_GoatEarTagDetails.getBatchno();
                 modal_b2BCartItemDetails.itemaddeddate= Modal_Static_GoatEarTagDetails.getItemaddeddate();
                 modal_b2BCartItemDetails.weightingrams= String.valueOf(entered_Weight_double);
                 modal_b2BCartItemDetails.gender= Modal_Static_GoatEarTagDetails.getGender();
                 modal_b2BCartItemDetails.status= Modal_Static_GoatEarTagDetails.getStatus();
                 modal_b2BCartItemDetails.orderid= BillingScreen.orderid;
                 modal_b2BCartItemDetails.b2bctgykey= Modal_Static_GoatEarTagDetails.getB2bctgykey();
                 modal_b2BCartItemDetails.b2bsubctgykey= Modal_Static_GoatEarTagDetails.getB2bsubctgykey();
                 modal_b2BCartItemDetails.oldweightingrams= previous_WeightInGrams;
                 modal_b2BCartItemDetails.gradename= selectedGradeName;
                 modal_b2BCartItemDetails.gradeprice= selectedGradePrice;
                 modal_b2BCartItemDetails.gradekey= selectedGradeKey;
                 modal_b2BCartItemDetails.oldgradekey =previouslySelectedGradekey;
                 modal_b2BCartItemDetails.oldgradeprice = previouslySelectedGradePrice;
                 if(calledFrom.equals(getString(R.string.billing_Screen_placeOrder))) {
                     BillingScreen.calculateGradewiseQuantity_and_Weight(modal_b2BCartItemDetails );
                 }
                 else  if(calledFrom.equals(getString(R.string.billing_Screen_editOrder))){
                     BillingScreen.removeEntryFromGradewiseQuantity_and_Weight(modal_b2BCartItemDetails,true);
                 }

             }
             catch (Exception e){
                 e.printStackTrace();
             }

             BillingScreen.calculateTotalweight_Quantity_Price();
         }
         else{
             Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();

             modal_goatEarTagDetails.barcodeno = Modal_Static_GoatEarTagDetails.getBarcodeno();
             modal_goatEarTagDetails.batchno = Modal_Static_GoatEarTagDetails.getBatchno();
             modal_goatEarTagDetails.status = Modal_Static_GoatEarTagDetails.getStatus();
             modal_goatEarTagDetails.suppliername = Modal_Static_GoatEarTagDetails.getSuppliername();
             modal_goatEarTagDetails.supplierkey = Modal_Static_GoatEarTagDetails.getSupplierkey();
             modal_goatEarTagDetails.description = Modal_Static_GoatEarTagDetails.getDescription();
             modal_goatEarTagDetails.itemaddeddate = Modal_Static_GoatEarTagDetails.getItemaddeddate();
             modal_goatEarTagDetails.stockedweightingrams = Modal_Static_GoatEarTagDetails.getStockedweightingrams();
             modal_goatEarTagDetails.selecteditem = Modal_Static_GoatEarTagDetails.getSelecteditem();
             modal_goatEarTagDetails.gender = Modal_Static_GoatEarTagDetails.getGender();
             modal_goatEarTagDetails.breedtype = Modal_Static_GoatEarTagDetails.getBreedtype();
             modal_goatEarTagDetails.loadedweightingrams = Modal_Static_GoatEarTagDetails.getLoadedweightingrams();
             modal_goatEarTagDetails.currentweightingrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();
             modal_goatEarTagDetails.newWeight_forBillingScreen = String.valueOf(entered_Weight_double);
             modal_goatEarTagDetails.gradename= selectedGradeName;
             modal_goatEarTagDetails.gradeprice= selectedGradePrice;
             modal_goatEarTagDetails.gradekey= selectedGradeKey;
             BillingScreen.earTagDetailsArrayList_String.add(Modal_Static_GoatEarTagDetails.getBarcodeno());
             //BillingScreen.earTagDetailsHashMap.put(Modal_Static_GoatEarTagDetails.getBarcodeno(),modal_goatEarTagDetails);

             JSONObject jsonObject = new JSONObject();
             try{
                 jsonObject.put("weight",String.valueOf(entered_Weight_double));
                 jsonObject.put("gradekey",selectedGradeKey);
                 jsonObject.put("gradeprice",selectedGradePrice);
             }
             catch (Exception e){
                 e.printStackTrace();
             }

            try {
                BillingScreen.earTagDetails_weightStringHashMap.put(Modal_Static_GoatEarTagDetails.getBarcodeno(), jsonObject);
            }
            catch (Exception e){
                e.printStackTrace();
            }


            try{
                Modal_B2BCartItemDetails modal_b2BCartItemDetails = new Modal_B2BCartItemDetails();
                modal_b2BCartItemDetails.barcodeno= Modal_Static_GoatEarTagDetails.getBarcodeno();
                modal_b2BCartItemDetails.batchno= Modal_Static_GoatEarTagDetails.getBatchno();
                modal_b2BCartItemDetails.itemaddeddate= Modal_Static_GoatEarTagDetails.getItemaddeddate();
                modal_b2BCartItemDetails.weightingrams= String.valueOf(entered_Weight_double);
                modal_b2BCartItemDetails.gender= Modal_Static_GoatEarTagDetails.getGender();
                modal_b2BCartItemDetails.status= Modal_Static_GoatEarTagDetails.getStatus();
                modal_b2BCartItemDetails.orderid= BillingScreen.orderid;
                modal_b2BCartItemDetails.b2bctgykey= Modal_Static_GoatEarTagDetails.getB2bctgykey();
                modal_b2BCartItemDetails.b2bsubctgykey= Modal_Static_GoatEarTagDetails.getB2bsubctgykey();
                modal_b2BCartItemDetails.oldweightingrams= previous_WeightInGrams;
                modal_b2BCartItemDetails.gradename= selectedGradeName;
                modal_b2BCartItemDetails.gradeprice= selectedGradePrice;
                modal_b2BCartItemDetails.gradekey= selectedGradeKey;
                modal_b2BCartItemDetails.oldgradekey =previouslySelectedGradekey;
                modal_b2BCartItemDetails.oldgradeprice = previouslySelectedGradePrice;
                if(calledFrom.equals(getString(R.string.billing_Screen_placeOrder))) {
                    BillingScreen.calculateGradewiseQuantity_and_Weight(modal_b2BCartItemDetails );
                }
                else  if(calledFrom.equals(getString(R.string.billing_Screen_editOrder))){
                    BillingScreen.removeEntryFromGradewiseQuantity_and_Weight(modal_b2BCartItemDetails,true);
                }            }
            catch (Exception e){
                e.printStackTrace();
            }
             BillingScreen.calculateTotalweight_Quantity_Price();

         }


         }


     try{

         if(calledFrom.equals(getString(R.string.billing_Screen_placeOrder)) ){
             ((BillingScreen) requireActivity()).closeFragment();
             try{
                 BillingScreen.adapter_gradeWiseTotal_billingScreen.notifyDataSetChanged();

             }
             catch (Exception e){
                 try{
                     ((BillingScreen)getActivity()).setAdapterForGradewiseTotal();
                 }
                 catch (Exception e1){
                     e1.printStackTrace();
                 }
                 e.printStackTrace();
             }
         }
         else{
             ((GoatEarTagItemDetailsList)getActivity()).closeFragment();


         }

     }
     catch ( Exception e){
         e.printStackTrace();
     }


     */

        if(DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetailsArrayList_String.contains(Modal_Static_GoatEarTagDetails.getBarcodeno())){
        /* if(BillingScreen.earTagDetailsHashMap.containsKey(Modal_Static_GoatEarTagDetails.getBarcodeno())) {
             Objects.requireNonNull(BillingScreen.earTagDetailsHashMap.get(Modal_Static_GoatEarTagDetails.getBarcodeno())).setDescription(String.valueOf(description_edittext.getText()));
             Objects.requireNonNull(BillingScreen.earTagDetailsHashMap.get(Modal_Static_GoatEarTagDetails.getBarcodeno())).setNewWeight_forBillingScreen(String.valueOf(entered_Weight_double));

             BillingScreen.calculateTotalweight_Quantity_Price();
         }

         */

            if(DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetails_weightStringHashMap.containsKey(Modal_Static_GoatEarTagDetails.getBarcodeno())) {

                JSONObject jsonObject = new JSONObject();
                try{
                    jsonObject.put("weight",String.valueOf(entered_Weight_double));
                    jsonObject.put("gradekey",selectedGradeKey);
                    jsonObject.put("gradeprice",selectedGradePrice);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                if (SDK_INT >= Build.VERSION_CODES.N) {

                    Objects.requireNonNull(DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetails_weightStringHashMap.replace(Modal_Static_GoatEarTagDetails.getBarcodeno(),jsonObject ));
                }
                else{
                    Objects.requireNonNull(DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetails_weightStringHashMap.put(Modal_Static_GoatEarTagDetails.getBarcodeno() , jsonObject));
                }
                try{
                    Modal_B2BCartItemDetails modal_b2BCartItemDetails = new Modal_B2BCartItemDetails();
                    modal_b2BCartItemDetails.barcodeno= Modal_Static_GoatEarTagDetails.getBarcodeno();
                    modal_b2BCartItemDetails.batchno= Modal_Static_GoatEarTagDetails.getBatchno();
                    modal_b2BCartItemDetails.itemaddeddate= Modal_Static_GoatEarTagDetails.getItemaddeddate();
                    modal_b2BCartItemDetails.weightingrams= String.valueOf(entered_Weight_double);
                    modal_b2BCartItemDetails.gender= Modal_Static_GoatEarTagDetails.getGender();
                    modal_b2BCartItemDetails.status= Modal_Static_GoatEarTagDetails.getStatus();
                    modal_b2BCartItemDetails.orderid= DeliveryCentre_PlaceOrderScreen_Fragment.orderid;
                    modal_b2BCartItemDetails.b2bctgykey= Modal_Static_GoatEarTagDetails.getB2bctgykey();
                    modal_b2BCartItemDetails.b2bsubctgykey= Modal_Static_GoatEarTagDetails.getB2bsubctgykey();
                    modal_b2BCartItemDetails.oldweightingrams= previous_WeightInGrams;
                    modal_b2BCartItemDetails.gradename= selectedGradeName;
                    modal_b2BCartItemDetails.gradeprice= selectedGradePrice;
                    modal_b2BCartItemDetails.gradekey= selectedGradeKey;
                    modal_b2BCartItemDetails.oldgradekey =previouslySelectedGradekey;
                    modal_b2BCartItemDetails.oldgradeprice = previouslySelectedGradePrice;
                    if(calledFrom.equals(getString(R.string.billing_Screen_placeOrder))) {
                        DeliveryCentre_PlaceOrderScreen_Fragment.calculateGradewiseQuantity_and_Weight(modal_b2BCartItemDetails );
                    }
                    else if(calledFrom.equals(getString(R.string.pos_billing_Screen_placeOrder))) {
                        DeliveryCentre_PlaceOrderScreen_Fragment.calculateGradewiseQuantity_and_Weight(modal_b2BCartItemDetails );
                    }
                    else if(calledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion))) {
                       // DeliveryCenter_PlaceOrderScreen_SecondVersn.calculateGradewiseQuantity_and_Weight(modal_b2BCartItemDetails );
                    }
                    else if(calledFrom.equals(getString(R.string.pos_placedOrder_Details_Screen_SecondVersion))) {
                      //  DeliveryCenter_PlaceOrderScreen_SecondVersn.calculateGradewiseQuantity_and_Weight(modal_b2BCartItemDetails );
                    }
                    else  if(calledFrom.equals(getString(R.string.billing_Screen_editOrder))){
                        DeliveryCentre_PlaceOrderScreen_Fragment.removeEntryFromGradewiseQuantity_and_Weight(modal_b2BCartItemDetails,true);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                DeliveryCentre_PlaceOrderScreen_Fragment.calculateTotalweight_Quantity_Price();
            }
            else{
                Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();

                modal_goatEarTagDetails.barcodeno = Modal_Static_GoatEarTagDetails.getBarcodeno();
                modal_goatEarTagDetails.batchno = Modal_Static_GoatEarTagDetails.getBatchno();
                modal_goatEarTagDetails.status = Modal_Static_GoatEarTagDetails.getStatus();
                modal_goatEarTagDetails.suppliername = Modal_Static_GoatEarTagDetails.getSuppliername();
                modal_goatEarTagDetails.supplierkey = Modal_Static_GoatEarTagDetails.getSupplierkey();
                modal_goatEarTagDetails.description = Modal_Static_GoatEarTagDetails.getDescription();
                modal_goatEarTagDetails.itemaddeddate = Modal_Static_GoatEarTagDetails.getItemaddeddate();
                modal_goatEarTagDetails.stockedweightingrams = Modal_Static_GoatEarTagDetails.getStockedweightingrams();
                modal_goatEarTagDetails.selecteditem = Modal_Static_GoatEarTagDetails.getSelecteditem();
                modal_goatEarTagDetails.gender = Modal_Static_GoatEarTagDetails.getGender();
                modal_goatEarTagDetails.breedtype = Modal_Static_GoatEarTagDetails.getBreedtype();
                modal_goatEarTagDetails.loadedweightingrams = Modal_Static_GoatEarTagDetails.getLoadedweightingrams();
                modal_goatEarTagDetails.currentweightingrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();
                modal_goatEarTagDetails.newWeight_forBillingScreen = String.valueOf(entered_Weight_double);
                modal_goatEarTagDetails.gradename= selectedGradeName;
                modal_goatEarTagDetails.gradeprice= selectedGradePrice;
                modal_goatEarTagDetails.gradekey= selectedGradeKey;



                JSONObject jsonObject = new JSONObject();
                try{
                    jsonObject.put("weight",String.valueOf(entered_Weight_double));
                    jsonObject.put("gradekey",selectedGradeKey);
                    jsonObject.put("gradeprice",selectedGradePrice);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                // BillingScreen.earTagDetailsHashMap.put(Modal_Static_GoatEarTagDetails.getBarcodeno(),modal_goatEarTagDetails);
                Objects.requireNonNull(DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetails_weightStringHashMap.put(Modal_Static_GoatEarTagDetails.getBarcodeno() , jsonObject));

                try{
                    Modal_B2BCartItemDetails modal_b2BCartItemDetails = new Modal_B2BCartItemDetails();
                    modal_b2BCartItemDetails.barcodeno= Modal_Static_GoatEarTagDetails.getBarcodeno();
                    modal_b2BCartItemDetails.batchno= Modal_Static_GoatEarTagDetails.getBatchno();
                    modal_b2BCartItemDetails.itemaddeddate= Modal_Static_GoatEarTagDetails.getItemaddeddate();
                    modal_b2BCartItemDetails.weightingrams= String.valueOf(entered_Weight_double);
                    modal_b2BCartItemDetails.gender= Modal_Static_GoatEarTagDetails.getGender();
                    modal_b2BCartItemDetails.status= Modal_Static_GoatEarTagDetails.getStatus();
                    modal_b2BCartItemDetails.orderid= DeliveryCentre_PlaceOrderScreen_Fragment.orderid;
                    modal_b2BCartItemDetails.b2bctgykey= Modal_Static_GoatEarTagDetails.getB2bctgykey();
                    modal_b2BCartItemDetails.b2bsubctgykey= Modal_Static_GoatEarTagDetails.getB2bsubctgykey();
                    modal_b2BCartItemDetails.oldweightingrams= previous_WeightInGrams;
                    modal_b2BCartItemDetails.gradename= selectedGradeName;
                    modal_b2BCartItemDetails.gradeprice= selectedGradePrice;
                    modal_b2BCartItemDetails.gradekey= selectedGradeKey;
                    modal_b2BCartItemDetails.oldgradekey =previouslySelectedGradekey;
                    modal_b2BCartItemDetails.oldgradeprice = previouslySelectedGradePrice;
                    if(calledFrom.equals(getString(R.string.billing_Screen_placeOrder))) {
                        DeliveryCentre_PlaceOrderScreen_Fragment.calculateGradewiseQuantity_and_Weight(modal_b2BCartItemDetails );
                    }
                    else if(calledFrom.equals(getString(R.string.pos_billing_Screen_placeOrder))) {
                        DeliveryCentre_PlaceOrderScreen_Fragment.calculateGradewiseQuantity_and_Weight(modal_b2BCartItemDetails );
                    }
                    else if(calledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion))) {
                        // DeliveryCenter_PlaceOrderScreen_SecondVersn.calculateGradewiseQuantity_and_Weight(modal_b2BCartItemDetails );
                    }
                    else if(calledFrom.equals(getString(R.string.pos_placedOrder_Details_Screen_SecondVersion))) {
                        //  DeliveryCenter_PlaceOrderScreen_SecondVersn.calculateGradewiseQuantity_and_Weight(modal_b2BCartItemDetails );
                    }
                    else  if(calledFrom.equals(getString(R.string.billing_Screen_editOrder))){
                        DeliveryCentre_PlaceOrderScreen_Fragment.removeEntryFromGradewiseQuantity_and_Weight(modal_b2BCartItemDetails,true);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                DeliveryCentre_PlaceOrderScreen_Fragment.calculateTotalweight_Quantity_Price();

            }


        }
        else{
         /*if(BillingScreen.earTagDetailsHashMap.containsKey(Modal_Static_GoatEarTagDetails.getBarcodeno())) {
             Objects.requireNonNull(BillingScreen.earTagDetailsHashMap.get(Modal_Static_GoatEarTagDetails.getBarcodeno())).setDescription(String.valueOf(description_edittext.getText()));
             Objects.requireNonNull(BillingScreen.earTagDetailsHashMap.get(Modal_Static_GoatEarTagDetails.getBarcodeno())).setNewWeight_forBillingScreen(String.valueOf(entered_Weight_double));
             BillingScreen.earTagDetailsArrayList_String.add(Modal_Static_GoatEarTagDetails.getBarcodeno());

             BillingScreen.calculateTotalweight_Quantity_Price();
         }

          */

            if(DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetails_weightStringHashMap.containsKey(Modal_Static_GoatEarTagDetails.getBarcodeno())) {
                JSONObject jsonObject = new JSONObject();
                try{
                    jsonObject.put("weight",String.valueOf(entered_Weight_double));
                    jsonObject.put("gradekey",selectedGradeKey);
                    jsonObject.put("gradeprice",selectedGradePrice);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                if (SDK_INT >= Build.VERSION_CODES.N) {
                    Objects.requireNonNull(DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetails_weightStringHashMap.replace(Modal_Static_GoatEarTagDetails.getBarcodeno(), jsonObject));
                }
                else{
                    Objects.requireNonNull(DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetails_weightStringHashMap.put(Modal_Static_GoatEarTagDetails.getBarcodeno() , jsonObject));
                }

                DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetailsArrayList_String.add(Modal_Static_GoatEarTagDetails.getBarcodeno());
                try{
                    Modal_B2BCartItemDetails modal_b2BCartItemDetails = new Modal_B2BCartItemDetails();
                    modal_b2BCartItemDetails.barcodeno= Modal_Static_GoatEarTagDetails.getBarcodeno();
                    modal_b2BCartItemDetails.batchno= Modal_Static_GoatEarTagDetails.getBatchno();
                    modal_b2BCartItemDetails.itemaddeddate= Modal_Static_GoatEarTagDetails.getItemaddeddate();
                    modal_b2BCartItemDetails.weightingrams= String.valueOf(entered_Weight_double);
                    modal_b2BCartItemDetails.gender= Modal_Static_GoatEarTagDetails.getGender();
                    modal_b2BCartItemDetails.status= Modal_Static_GoatEarTagDetails.getStatus();
                    modal_b2BCartItemDetails.orderid= DeliveryCentre_PlaceOrderScreen_Fragment.orderid;
                    modal_b2BCartItemDetails.b2bctgykey= Modal_Static_GoatEarTagDetails.getB2bctgykey();
                    modal_b2BCartItemDetails.b2bsubctgykey= Modal_Static_GoatEarTagDetails.getB2bsubctgykey();
                    modal_b2BCartItemDetails.oldweightingrams= previous_WeightInGrams;
                    modal_b2BCartItemDetails.gradename= selectedGradeName;
                    modal_b2BCartItemDetails.gradeprice= selectedGradePrice;
                    modal_b2BCartItemDetails.gradekey= selectedGradeKey;
                    modal_b2BCartItemDetails.oldgradekey =previouslySelectedGradekey;
                    modal_b2BCartItemDetails.oldgradeprice = previouslySelectedGradePrice;
                    if(calledFrom.equals(getString(R.string.billing_Screen_placeOrder))) {
                        DeliveryCentre_PlaceOrderScreen_Fragment.calculateGradewiseQuantity_and_Weight(modal_b2BCartItemDetails );
                    }
                    else if(calledFrom.equals(getString(R.string.pos_billing_Screen_placeOrder))) {
                        DeliveryCentre_PlaceOrderScreen_Fragment.calculateGradewiseQuantity_and_Weight(modal_b2BCartItemDetails );
                    }
                    else if(calledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion))) {
                        // DeliveryCenter_PlaceOrderScreen_SecondVersn.calculateGradewiseQuantity_and_Weight(modal_b2BCartItemDetails );
                    }
                    else if(calledFrom.equals(getString(R.string.pos_placedOrder_Details_Screen_SecondVersion))) {
                        //  DeliveryCenter_PlaceOrderScreen_SecondVersn.calculateGradewiseQuantity_and_Weight(modal_b2BCartItemDetails );
                    }


                    else  if(calledFrom.equals(getString(R.string.billing_Screen_editOrder))){
                        DeliveryCentre_PlaceOrderScreen_Fragment.removeEntryFromGradewiseQuantity_and_Weight(modal_b2BCartItemDetails,true);
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }

                DeliveryCentre_PlaceOrderScreen_Fragment.calculateTotalweight_Quantity_Price();
            }
            else{
                Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();

                modal_goatEarTagDetails.barcodeno = Modal_Static_GoatEarTagDetails.getBarcodeno();
                modal_goatEarTagDetails.batchno = Modal_Static_GoatEarTagDetails.getBatchno();
                modal_goatEarTagDetails.status = Modal_Static_GoatEarTagDetails.getStatus();
                modal_goatEarTagDetails.suppliername = Modal_Static_GoatEarTagDetails.getSuppliername();
                modal_goatEarTagDetails.supplierkey = Modal_Static_GoatEarTagDetails.getSupplierkey();
                modal_goatEarTagDetails.description = Modal_Static_GoatEarTagDetails.getDescription();
                modal_goatEarTagDetails.itemaddeddate = Modal_Static_GoatEarTagDetails.getItemaddeddate();
                modal_goatEarTagDetails.stockedweightingrams = Modal_Static_GoatEarTagDetails.getStockedweightingrams();
                modal_goatEarTagDetails.selecteditem = Modal_Static_GoatEarTagDetails.getSelecteditem();
                modal_goatEarTagDetails.gender = Modal_Static_GoatEarTagDetails.getGender();
                modal_goatEarTagDetails.breedtype = Modal_Static_GoatEarTagDetails.getBreedtype();
                modal_goatEarTagDetails.loadedweightingrams = Modal_Static_GoatEarTagDetails.getLoadedweightingrams();
                modal_goatEarTagDetails.currentweightingrams = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();
                modal_goatEarTagDetails.newWeight_forBillingScreen = String.valueOf(entered_Weight_double);
                modal_goatEarTagDetails.gradename= selectedGradeName;
                modal_goatEarTagDetails.gradeprice= selectedGradePrice;
                modal_goatEarTagDetails.gradekey= selectedGradeKey;
                DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetailsArrayList_String.add(Modal_Static_GoatEarTagDetails.getBarcodeno());
                //BillingScreen.earTagDetailsHashMap.put(Modal_Static_GoatEarTagDetails.getBarcodeno(),modal_goatEarTagDetails);

                JSONObject jsonObject = new JSONObject();
                try{
                    jsonObject.put("weight",String.valueOf(entered_Weight_double));
                    jsonObject.put("gradekey",selectedGradeKey);
                    jsonObject.put("gradeprice",selectedGradePrice);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetails_weightStringHashMap.put(Modal_Static_GoatEarTagDetails.getBarcodeno(), jsonObject);
                }
                catch (Exception e){
                    e.printStackTrace();
                }


                try{
                    Modal_B2BCartItemDetails modal_b2BCartItemDetails = new Modal_B2BCartItemDetails();
                    modal_b2BCartItemDetails.barcodeno= Modal_Static_GoatEarTagDetails.getBarcodeno();
                    modal_b2BCartItemDetails.batchno= Modal_Static_GoatEarTagDetails.getBatchno();
                    modal_b2BCartItemDetails.itemaddeddate= Modal_Static_GoatEarTagDetails.getItemaddeddate();
                    modal_b2BCartItemDetails.weightingrams= String.valueOf(entered_Weight_double);
                    modal_b2BCartItemDetails.gender= Modal_Static_GoatEarTagDetails.getGender();
                    modal_b2BCartItemDetails.status= Modal_Static_GoatEarTagDetails.getStatus();
                    modal_b2BCartItemDetails.supplierkey= Modal_Static_GoatEarTagDetails.getSupplierkey();
                    modal_b2BCartItemDetails.orderid= DeliveryCentre_PlaceOrderScreen_Fragment.orderid;
                    modal_b2BCartItemDetails.b2bctgykey= Modal_Static_GoatEarTagDetails.getB2bctgykey();
                    modal_b2BCartItemDetails.b2bsubctgykey= Modal_Static_GoatEarTagDetails.getB2bsubctgykey();
                    modal_b2BCartItemDetails.oldweightingrams= previous_WeightInGrams;
                    modal_b2BCartItemDetails.gradename= selectedGradeName;
                    modal_b2BCartItemDetails.gradeprice= selectedGradePrice;
                    modal_b2BCartItemDetails.gradekey= selectedGradeKey;
                    modal_b2BCartItemDetails.oldgradekey =previouslySelectedGradekey;
                    modal_b2BCartItemDetails.oldgradeprice = previouslySelectedGradePrice;
                    if(calledFrom.equals(getString(R.string.billing_Screen_placeOrder))) {
                        DeliveryCentre_PlaceOrderScreen_Fragment.calculateGradewiseQuantity_and_Weight(modal_b2BCartItemDetails );
                    }
                    else if(calledFrom.equals(getString(R.string.pos_billing_Screen_placeOrder))) {
                        DeliveryCentre_PlaceOrderScreen_Fragment.calculateGradewiseQuantity_and_Weight(modal_b2BCartItemDetails );
                    }
                    else if(calledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion))) {
                        // DeliveryCenter_PlaceOrderScreen_SecondVersn.calculateGradewiseQuantity_and_Weight(modal_b2BCartItemDetails );
                    }
                    else if(calledFrom.equals(getString(R.string.pos_placedOrder_Details_Screen_SecondVersion))) {
                        //  DeliveryCenter_PlaceOrderScreen_SecondVersn.calculateGradewiseQuantity_and_Weight(modal_b2BCartItemDetails );
                    }
                    else  if(calledFrom.equals(getString(R.string.billing_Screen_editOrder))){
                        DeliveryCentre_PlaceOrderScreen_Fragment.removeEntryFromGradewiseQuantity_and_Weight(modal_b2BCartItemDetails,true);
                    }            }
                catch (Exception e){
                    e.printStackTrace();
                }
                DeliveryCentre_PlaceOrderScreen_Fragment.calculateTotalweight_Quantity_Price();

            }


        }


        try{

            if(calledFrom.equals(getString(R.string.billing_Screen_placeOrder)) || calledFrom.equals(getString(R.string.pos_billing_Screen_placeOrder))){
                DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.closeFragment();
                try{
                    DeliveryCentre_PlaceOrderScreen_Fragment.adapter_gradeWiseTotal_billingScreen.notifyDataSetChanged();

                }
                catch (Exception e){
                    try{
                        DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.setAdapterForGradewiseTotal();
                    }
                    catch (Exception e1){
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            else if(calledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion)) || calledFrom.equals(getString(R.string.pos_placedOrder_Details_Screen_SecondVersion))){
                        DeliveryCenter_PlaceOrderScreen_SecondVersn.deliveryCenter_placeOrderScreen_secondVersn.closeFragment();
                try{
                 //   DeliveryCenter_PlaceOrderScreen_SecondVersn.adapter_gradeWiseTotal_billingScreen.notifyDataSetChanged();

                }
                catch (Exception e){
                    try{
                        DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.setAdapterForGradewiseTotal();
                    }
                    catch (Exception e1){
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            else{
                ((GoatEarTagItemDetailsList)getActivity()).closeFragment();


            }

        }
        catch ( Exception e){
            e.printStackTrace();
        }




    }



    private void Intialize_And_Process_BatchDetails(String batchId, String callMethod) {
        showProgressBar_in_theActivity(true);
        if (isBatchDetailsTableServiceCalled) {
            showProgressBar_in_theActivity(false);
            return;
        }
        isBatchDetailsTableServiceCalled = true;
        callback_B2BBatchDetailsInterface = new B2BBatchDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BBatchDetails> batchDetailsArrayList) {

            }

            @Override
            public void notifySuccess(String result) {


                if (callMethod.equals(Constants.CallGETMethod)) {
                    if(result.equals(Constants.emptyResult_volley)){
                        //barcodeNo_textView.setText(scannedBarcode);
                        showProgressBar_in_theActivity(false);
                        AlertDialogClass.showDialog(getActivity(), R.string.BatchDetailsnotFound_Instruction);
                        return;
                    }
                    else{
                        String batchStatus = Modal_B2BBatchDetailsStatic.getStatus().toUpperCase();
                        if(!batchStatus.equals(Constants.batchDetailsStatus_Reviewed_and_READYFORSALE)){
                            showEditabeLayouts(false, false);
                            showProgressBar_in_theActivity(false);
                            AlertDialogClass.showDialog(getActivity(), R.string.BatchDetailsnotReviewedYet_Instruction);
                            return;
                        }

                    }


                }
                showProgressBar_in_theActivity(false);
                isBatchDetailsTableServiceCalled = false;

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar_in_theActivity(false);
                Toast.makeText(requireActivity(), Constants.volleyError_Result_volley, Toast.LENGTH_SHORT).show();

                isBatchDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                showProgressBar_in_theActivity(false);
                Toast.makeText(requireActivity(), Constants.processingErrorResult_volley, Toast.LENGTH_SHORT).show();

                isBatchDetailsTableServiceCalled = false;
            }


        };

        if (callMethod.equals(Constants.CallGETMethod)) {


            String addApiToCall = API_Manager.getBatchDetailsWithDeliveryCenterKeyBatchNo + "?supplierkey=" + Modal_Static_GoatEarTagDetails.getSupplierkey() + "&batchno=" + batchId;

            B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface, addApiToCall, Constants.CallGETMethod);
            asyncTask.execute();
        }

    }



    public void Initialize_and_ExecuteB2BCartOrderDetails(String callMethod, String valuetoUpdate) {


        showProgressBar_in_theActivity(true);
        if (isB2BCartOrderTableServiceCalled) {
            showProgressBar_in_theActivity(false);
            return;
        }
        isB2BCartOrderTableServiceCalled = true;
        callback_b2bOrderDetails = new B2BCartOrderDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BCartOrderDetails> arrayList) {

            }

            @Override
            public void notifySuccess(String result) {
                if (result.equals(Constants.emptyResult_volley)) {
                    showProgressBar_in_theActivity(false);


                } else {
                    orderid  = Modal_B2BCartOrderDetails.orderid;


                    Initialize_and_ExecuteB2BOrderCartDetails(Constants.CallGETMethod);
                    showProgressBar_in_theActivity(true);
                }
            }
            @Override
            public void notifyVolleyError(VolleyError error) {

                showProgressBar_in_theActivity(false);
                isB2BCartOrderTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {


                showProgressBar_in_theActivity(false);
                isB2BCartOrderTableServiceCalled = false;


            }




        };



        //String getApiToCall = API_Manager.getCartOrderDetailsForBatchno+"?batchno="+batchno ;
        String getApiToCall = API_Manager.getCartOrderDetailsForDeliveryCentrekey+"?deliverycentrekey="+deliveryCenterKey ;

        B2BCartOrderDetails asyncTask = new B2BCartOrderDetails(callback_b2bOrderDetails,  getApiToCall, Constants.CallGETMethod);
            asyncTask.execute();




    }



    private void Initialize_and_ExecuteB2BOrderCartDetails(String callADDMethod) {
        if(calledFrom.equals(getString(R.string.billing_Screen_placeOrder)) || calledFrom.equals(getString(R.string.pos_billing_Screen_placeOrder))) {
           // ((BillingScreen)getActivity()).showProgressBar(true);
            DeliveryCentre_PlaceOrderScreen_Fragment.showProgressBar(true);
        }
        else if(calledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion)) || calledFrom.equals(getString(R.string.pos_placedOrder_Details_Screen_SecondVersion))) {
            DeliveryCenter_PlaceOrderScreen_SecondVersn.showProgressBar(true);
        }
        else  if(calledFrom.equals(getString(R.string.billing_Screen_editOrder))){
            showProgressBar(true);
        }
        else if(calledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion)) || calledFrom.equals(getString(R.string.pos_placedOrder_Details_Screen_SecondVersion))) {
            // ((BillingScreen)getActivity()).showProgressBar(false);
            DeliveryCenter_PlaceOrderScreen_SecondVersn.showProgressBar(false);
        }
        if (isB2BCartDetailsCalled) {
            //  showProgressBar(false);
            return;
        }
        isB2BCartDetailsCalled = true;
        callback_b2BCartItemDetaillsInterface = new B2BCartItemDetaillsInterface()
        {

            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BCartItemDetails> arrayList) {
                if(calledFrom.equals(getString(R.string.billing_Screen_placeOrder)) || calledFrom.equals(getString(R.string.pos_billing_Screen_placeOrder))) {
                   // ((BillingScreen)getActivity()).showProgressBar(false);
                    DeliveryCentre_PlaceOrderScreen_Fragment.showProgressBar(false);
                }
                else if(calledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion)) || calledFrom.equals(getString(R.string.pos_placedOrder_Details_Screen_SecondVersion))) {
                    DeliveryCenter_PlaceOrderScreen_SecondVersn.showProgressBar(false);
                }
                else  if(calledFrom.equals(getString(R.string.billing_Screen_editOrder))){
                    showProgressBar(false);
                }
                else if(calledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion)) || calledFrom.equals(getString(R.string.pos_placedOrder_Details_Screen_SecondVersion))) {
                    // ((BillingScreen)getActivity()).showProgressBar(false);
                    DeliveryCenter_PlaceOrderScreen_SecondVersn.showProgressBar(false);
                }


            }

            @Override
            public void notifySuccess(String result) {
                Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 2 " + DateParser.getDate_and_time_newFormat());
                isB2BCartDetailsCalled = false;

                showProgressBar_in_theActivity(false);
                if(callADDMethod.equals(Constants.CallGETMethod)){
                    if(!result.equals(Constants.emptyResult_volley)){
                        showEditabeLayouts(false, false);
                        AlertDialogClass.showDialog(getActivity(), R.string.CantEditItem_Which_isInCart_Instruction);

                    }
                }
                else {

                    if (result.equals(Constants.item_Already_Added_volley)) {
                        AlertDialogClass.showDialog(getActivity(), R.string.GoatEarTagAlreadyCreated_Instruction);

                    } else {
                        addItemInTheBillingScreenArrayList();
                        Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallUPDATEMethod);

                        if (callADDMethod.equals(Constants.CallUPDATEMethod)) {
                            // UpdateValueInLocalArray();

                        }
                    }

                }
                showProgressBar_in_theActivity(false);
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isB2BCartDetailsCalled = false;
                Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 3 " + DateParser.getDate_and_time_newFormat());
                showProgressBar_in_theActivity(false);
            }

            @Override
            public void notifyProcessingError(Exception error) {
                isB2BCartDetailsCalled = false;showProgressBar_in_theActivity(false);
                Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());

            }


        };
        if(callADDMethod.equals(Constants.CallADDMethod)){
            String getApiToCall = API_Manager.addCartItemDetails;

            String ctgykey = "", ctgyname = "", subctgykey = "", suctgyname = "";

            for (int iterator2 = 0; iterator2 < ctgy_subCtgy_DetailsArrayList.size(); iterator2++) {
                String subctgyname = String.valueOf(ctgy_subCtgy_DetailsArrayList.get(iterator2).getSubctgy_name().toUpperCase());
                if (subctgyname.equals(Modal_Static_GoatEarTagDetails.getBreedtype().toUpperCase())) {

                    ctgykey = ctgy_subCtgy_DetailsArrayList.get(iterator2).getKey();
                    ctgyname = ctgy_subCtgy_DetailsArrayList.get(iterator2).getName();
                    subctgykey = ctgy_subCtgy_DetailsArrayList.get(iterator2).getSubctgy_key();
                    suctgyname = ctgy_subCtgy_DetailsArrayList.get(iterator2).getSubctgy_name();




                }
            }




            Modal_B2BCartItemDetails modal_b2BCartDetails = new Modal_B2BCartItemDetails();
            try {
                modal_b2BCartDetails.barcodeno = Modal_Static_GoatEarTagDetails.getBarcodeno();
                modal_b2BCartDetails.batchno = Modal_Static_GoatEarTagDetails.getBatchno();
                modal_b2BCartDetails.status = Modal_Static_GoatEarTagDetails.getStatus();
                modal_b2BCartDetails.itemaddeddate = DateParser.getDate_and_time_newFormat();

                modal_b2BCartDetails.oldweightingrams = previous_WeightInGrams;
                modal_b2BCartDetails.weightingrams = (String.valueOf(df.format(entered_Weight_double)));
                modal_b2BCartDetails.gender = Modal_Static_GoatEarTagDetails.getGender();
                modal_b2BCartDetails.b2bctgykey = ctgykey;
                modal_b2BCartDetails.b2bsubctgykey = subctgykey;
                modal_b2BCartDetails.breedtype = Modal_Static_GoatEarTagDetails.getBreedtype();
                modal_b2BCartDetails.orderid = String.valueOf(DeliveryCentre_PlaceOrderScreen_Fragment.orderid);
                modal_b2BCartDetails.supplierkey = Modal_Static_GoatEarTagDetails.getSupplierkey();
                modal_b2BCartDetails.suppliername = Modal_Static_GoatEarTagDetails.getSuppliername();
                modal_b2BCartDetails.gradeprice = selectedGradePrice;
                modal_b2BCartDetails.gradename = selectedGradeName;
                modal_b2BCartDetails.gradekey = selectedGradeKey;

            }
            catch (Exception e){
                e.printStackTrace();
            }


            B2BCartItemDetails asyncTask = new B2BCartItemDetails(callback_b2BCartItemDetaillsInterface,  getApiToCall, callADDMethod,modal_b2BCartDetails);
            asyncTask.execute();

        }
        else if(callADDMethod.equals(Constants.CallUPDATEMethod)){
            String getApiToCall = API_Manager.updateCartItemDetails;
            Modal_B2BCartItemDetails modal_b2BCartDetails = new Modal_B2BCartItemDetails();
            try {
                modal_b2BCartDetails.barcodeno = Modal_Static_GoatEarTagDetails.getBarcodeno();
                modal_b2BCartDetails.orderid = String.valueOf(DeliveryCentre_PlaceOrderScreen_Fragment.orderid);
                modal_b2BCartDetails.weightingrams =  (String.valueOf(df.format(entered_Weight_double)));
                modal_b2BCartDetails.gradename = String.valueOf(selectedGradeName);
                modal_b2BCartDetails.gradeprice = String.valueOf(selectedGradePrice);
                modal_b2BCartDetails.gradekey = selectedGradeKey;
            }
            catch (Exception e){
                e.printStackTrace();
            }


            B2BCartItemDetails asyncTask = new B2BCartItemDetails(callback_b2BCartItemDetaillsInterface,  getApiToCall, callADDMethod,modal_b2BCartDetails);
            asyncTask.execute();

        }
        else if(callADDMethod.equals(Constants.CallGETMethod)) {
            String getApiToCall = API_Manager.getCartDetailsForOrderidWithBarcodeNo+"?orderid="+orderid+"&barcodeno="+scannedBarcode;

            B2BCartItemDetails asyncTask = new B2BCartItemDetails(callback_b2BCartItemDetaillsInterface,  getApiToCall, callADDMethod);
            asyncTask.execute();

        }



    }

/*
    public void onGoatStatusRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        RadioButton radioButton = view.findViewById(view.getId());
        selectedGoatStatus =  String.valueOf(radioButton.getText());
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.normal_goat_radio:
                if (checked) {
                    if (!isStatusUpdatedFromDB) {




                    }
                }


                    break;
            case R.id.dead_goat_radio:
            case R.id.sick_goat_radio:
                if (checked){

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

                    break;

        }
    }
*/


    private void showEditabeLayouts(boolean isEditable, boolean isPartiallyEditable) {

        if(isEditable){
            save_button.setVisibility(View.VISIBLE);

            goatstatusradiogrp.setVisibility(View.VISIBLE);
            description_edittext.setVisibility(View.VISIBLE);

            selectedGender_textview.setVisibility(View.GONE);
            selectedGoatStatus_textview.setVisibility(View.GONE);
            description_textiew.setVisibility(View.GONE);
            if(isPartiallyEditable){
                editable_weight_breed_Label_layout.setVisibility(View.GONE);
                editable_weight_breed_layout.setVisibility(View.GONE);
                noneditable_weight_breed_layout.setVisibility(View.VISIBLE);
                genderRadioGroup .setVisibility(View.GONE);

                gender_label_textview.setVisibility(View.GONE);
            }
            else{
                chooseGrade_label.setVisibility(View.GONE);
                chooseGrade_spinner.setVisibility(View.GONE);
                editable_weight_breed_Label_layout.setVisibility(View.VISIBLE);
                editable_weight_breed_layout.setVisibility(View.VISIBLE);
                noneditable_weight_breed_layout.setVisibility(View.GONE);
                genderRadioGroup .setVisibility(View.VISIBLE);
                gender_label_textview.setVisibility(View.VISIBLE);
            }

        }
        else{

            goatstatusradiogrp.setVisibility(View.GONE);
            description_edittext.setVisibility(View.GONE);
            genderRadioGroup .setVisibility(View.GONE);


            gender_label_textview.setVisibility(View.GONE);
            selectedGender_textview.setVisibility(View.GONE);
            selectedGoatStatus_textview.setVisibility(View.VISIBLE);
            description_textiew.setVisibility(View.VISIBLE);
            if(isPartiallyEditable){
                genderRadioGroup.setVisibility(View.GONE);

                editable_weight_breed_Label_layout.setVisibility(View.GONE);
                editable_weight_breed_layout.setVisibility(View.GONE);
                noneditable_weight_breed_layout.setVisibility(View.VISIBLE);
                save_button.setVisibility(View.VISIBLE);
            }
            else{
                editable_weight_breed_Label_layout.setVisibility(View.GONE);
                editable_weight_breed_layout.setVisibility(View.GONE);
                noneditable_weight_breed_layout.setVisibility(View.VISIBLE);
                save_button.setVisibility(View.GONE);

            }
        }


    }

    private void Initialize_and_ExecuteB2BCtgyItem() {

        showProgressBar_in_theActivity(true);
        if (isB2BItemCtgyTableServiceCalled) {
            showProgressBar_in_theActivity(false);
            return;
        }
        isB2BItemCtgyTableServiceCalled = true;
        callback_B2BItemCtgyInterface = new B2BItemCtgyInterface() {
            @Override
            public void notifySuccess(String result) {
                breedType_arrayList_string = DatabaseArrayList_PojoClass.getBreedType_arrayList_string();
                isB2BItemCtgyTableServiceCalled = false;

                if (calledFrom.equals(getString(R.string.billing_Screen_placeOrder)) || calledFrom.equals(getString(R.string.billing_Screen_editOrder)) || calledFrom.equals(getString(R.string.pos_billing_Screen_placeOrder))){
                    showProgressBar_in_theActivity(false);
                }
                else if(calledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion)) || calledFrom.equals(getString(R.string.pos_placedOrder_Details_Screen_SecondVersion))) {
                    DeliveryCenter_PlaceOrderScreen_SecondVersn.showProgressBar(false);
                }



                ArrayAdapter breedType_aAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, breedType_arrayList_string);
                breedType_spinner.setAdapter(breedType_aAdapter);
                for(int iterator = 0 ;iterator<breedType_arrayList_string.size();iterator++){
                    String breedTypeFromarray =  breedType_arrayList_string.get(iterator);
                    if(breedTypeFromarray.equals(selectedBreed)){
                        breedType_spinner.setSelection(iterator);
                    }
                }
                ctgy_subCtgy_DetailsArrayList = DatabaseArrayList_PojoClass.breedType_arrayList;


            }

            @Override
            public void notifyError(VolleyError error) {

            }
        };
        String addApiToCall = API_Manager.getB2BItemCtgy ;
        B2BItemCtgy asyncTask = new B2BItemCtgy(callback_B2BItemCtgyInterface,  addApiToCall );
        asyncTask.execute();




    }

    private void showProgressBar_in_theActivity(boolean show) {
        try {
            if (calledFrom.equals(getString(R.string.billing_Screen_placeOrder)) || calledFrom.equals(getString(R.string.pos_billing_Screen_placeOrder))) {
              //  ((BillingScreen) getActivity()).showProgressBar(show);
                DeliveryCentre_PlaceOrderScreen_Fragment.showProgressBar(show);


            }
            else if (calledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion)) || calledFrom.equals(getString(R.string.pos_placedOrder_Details_Screen_SecondVersion))) {
                //  ((BillingScreen) getActivity()).showProgressBar(show);
                DeliveryCenter_PlaceOrderScreen_SecondVersn.showProgressBar(show);

            }
            else if (calledFrom.equals(getString(R.string.billing_Screen_editOrder))) {
                showProgressBar(show);
            } else {
                showProgressBar(show);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    private void Initialize_and_ExecuteInGoatEarTagTransaction(String callMethod, String goatEarTagAdd_OR_Updated) {

        showProgressBar_in_theActivity(true);
        if (isGoatEarTagTransactionTableServiceCalled) {
            showProgressBar_in_theActivity(false);
            return;
        }
        isGoatEarTagTransactionTableServiceCalled = true;
        callback_GoatEarTagTransactionInterface = new GoatEarTagTransactionInterface() {


            @Override
            public void notifySuccess(String result) {
                UpdateCalculationDataINSharedPref(goatEarTagAdd_OR_Updated);


                isGoatEarTagTransactionTableServiceCalled = false;
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
              //  UpdateCalculationDataINSharedPref(goatEarTagAdd_OR_Updated);
             //   Modal_UpdatedGoatEarTagDetails modal_updatedGoatEarTagDetails = new Modal_UpdatedGoatEarTagDetails();
             //   Modal_Static_GoatEarTagDetails modal_goatEarTagDetails = new Modal_Static_GoatEarTagDetails();
             //   entered_Weight_double =0;
              //  previous_WeightInGrams ="";
                Toast.makeText(mContext, "There is an volley error while updating Ear Tag Transaction", Toast.LENGTH_SHORT).show();
             //   showProgressBar_in_theActivity(false);
               // ((GoatEarTagItemDetailsList)getActivity()).closeFragment();
                BaseActivity. isAdding_Or_UpdatingEntriesInDB_Service = false;
                isGoatEarTagTransactionTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
         //       UpdateCalculationDataINSharedPref(goatEarTagAdd_OR_Updated);
               // Modal_UpdatedGoatEarTagDetails modal_updatedGoatEarTagDetails = new Modal_UpdatedGoatEarTagDetails();
              //  Modal_Static_GoatEarTagDetails modal_goatEarTagDetails = new Modal_Static_GoatEarTagDetails();
              //  entered_Weight_double =0;
             //   previous_WeightInGrams ="";
                Toast.makeText(mContext, "There is an Process error while updating Ear Tag Transaction", Toast.LENGTH_SHORT).show();
                BaseActivity. isAdding_Or_UpdatingEntriesInDB_Service = false;
                showProgressBar_in_theActivity(false);
               isGoatEarTagTransactionTableServiceCalled = false;
              //  ((GoatEarTagItemDetailsList)getActivity()).closeFragment();


            }




        };

        if(callMethod.equals(Constants.CallADDMethod)){
            if(goatEarTagAdd_OR_Updated.equals(Constants.CallADDMethod)) {
                try {
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
                    Modal_GoatEarTagTransaction.gradeprice=(selectedGradePrice);
                    Modal_GoatEarTagTransaction.gradename=(selectedGradeName);
                    Modal_GoatEarTagTransaction.gradekey=(selectedGradeKey);
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
                    Modal_GoatEarTagTransaction.batchno = batchNo;
                    Modal_GoatEarTagTransaction.updateddate = DateParser.getDate_and_time_newFormat();
                    Modal_GoatEarTagTransaction.mobileno = userMobileNo;
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_gradekey_boolean()){

                        Modal_GoatEarTagTransaction.gradeprice=(selectedGradePrice);
                        Modal_GoatEarTagTransaction.gradename=(selectedGradeName);
                        Modal_GoatEarTagTransaction.gradekey=(selectedGradeKey);
                    }
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_deliverycenterkey_boolean()) {
                        Modal_GoatEarTagTransaction.deliverycenterkey = deliveryCenterKey;
                    }
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_deliverycentername_boolean()) {
                        Modal_GoatEarTagTransaction.deliverycentername = deliveryCenterName;
                    }
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_currentweightingrams_boolean()){
                        try {
                            Modal_GoatEarTagTransaction.previousweightingrams = previous_WeightInGrams;
                            Modal_GoatEarTagTransaction.newweightingrams = (String.valueOf(df.format(entered_Weight_double)));
                            Modal_GoatEarTagTransaction.weighingpurpose = Constants.goatEarTagWeighingPurpose_RegularAudit;
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
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

        showProgressBar_in_theActivity(true);
        if (isGoatEarTagDetailsTableServiceCalled) {
            showProgressBar_in_theActivity(false);
            return;
        }
        isGoatEarTagDetailsTableServiceCalled = true;
        callback_GoatEarTagDetails = new GoatEarTagDetailsInterface() {


            @Override
            public void notifySuccess(String result) {
                if (callMethod.equals(Constants.CallUPDATEMethod)){
                    if(result.equals(Constants.item_not_Found_volley)){
                        AlertDialogClass.showDialog(getActivity(), R.string.GoatEatTagsCannotUpdated_Instruction);
                        // Toast.makeText(mContext, "This barcode is already added for this batch please add new one", Toast.LENGTH_SHORT).show();
                        showProgressBar_in_theActivity(false);
                        isGoatEarTagDetailsTableServiceCalled = false;

                    }
                    else  if(result.equals(Constants.expressionAttribute_is_empty_volley_response)){
                        AlertDialogClass.showDialog(getActivity(), R.string.GoatEatTagsCannotUpdated_PleaseChangeTOUpdate_Instruction);
                        // Toast.makeText(mContext, "This barcode is already added for this batch please add new one", Toast.LENGTH_SHORT).show();
                        showProgressBar_in_theActivity(false);
                        isGoatEarTagDetailsTableServiceCalled = false;

                    }
                    else if(result.equals(Constants.successResult_volley)){
                        // Toast.makeText(mContext, "Item Saved Successfully", Toast.LENGTH_SHORT).show();
                        //((GoatEarTagItemDetailsList) requireActivity()).closeFragment();

                        //showProgressBar_in_theActivity(false);
                        isGoatEarTagDetailsTableServiceCalled = false;
                        UpdateValueInLocalArray();

                        Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod, callMethod);


                    }
                    else{
                        isGoatEarTagDetailsTableServiceCalled = false;
                        Initialize_and_ExecuteInGoatEarTagTransaction(Constants.CallADDMethod, callMethod);

                        Toast.makeText(mContext, Constants.unknown_API_Result_volley, Toast.LENGTH_SHORT).show();
                    }
                    // isGoatEarTagDetailsTableServiceCalled = false;
                    // showProgressBar_in_theActivity(false);
                }
                else if(callMethod.equals(CallGETMethod)) {
                    isGoatEarTagDetailsTableServiceCalled = false;

                    if (result.equals(Constants.emptyResult_volley)) {
                        showProgressBar_in_theActivity(false);
                        try {
                            AlertDialogClass.showDialog(requireActivity(), R.string.EarTagDetailsNotFound_Instruction);

                        } catch (Exception e) {
                            Toast.makeText(requireActivity(), getString(R.string.EarTagDetailsNotFound_Instruction), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }

                    } else {
                        if (Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_EarTagLost)) {
                            showProgressBar_in_theActivity(false);
                            try{
                                AlertDialogClass.showDialog(requireActivity(),R.string.EarTagLost_Instruction);

                            }
                            catch (Exception e){
                                Toast.makeText(requireActivity(), getString(R.string.EarTagLost_Instruction), Toast.LENGTH_LONG).show();

                                e.printStackTrace();
                            }

                            return;
                        }
                        else if (Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_GoatLost)) {

                            showProgressBar_in_theActivity(false);
                            try{
                                AlertDialogClass.showDialog(requireActivity(),R.string.GoatLost_Instruction);

                            }
                            catch (Exception e){
                                Toast.makeText(mContext, getString(R.string.GoatLost_Instruction), Toast.LENGTH_LONG).show();

                                e.printStackTrace();
                            }

                            return;

                        }
                        else if (Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatdead)) {
                            showProgressBar_in_theActivity(false);
                            try{
                                AlertDialogClass.showDialog(requireActivity(),R.string.GoatDead_Instruction);

                            }
                            catch (Exception e){
                                Toast.makeText(mContext, getString(R.string.GoatDead_Instruction), Toast.LENGTH_LONG).show();

                                e.printStackTrace();
                            }

                            return;

                        }
                        else if (Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Sold)) {
                            showProgressBar_in_theActivity(false);
                            try{
                                AlertDialogClass.showDialog(requireActivity(),R.string.EarTagSold_Instruction);

                            }
                            catch (Exception e){
                                Toast.makeText(mContext, getString(R.string.EarTagSold_Instruction), Toast.LENGTH_LONG).show();

                                e.printStackTrace();
                            }
                            return;

                        }
                        else if (Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatsick)) {
                            showProgressBar_in_theActivity(false);
                            try{
                                AlertDialogClass.showDialog(requireActivity(),R.string.GoatSick_Instruction);

                            }
                            catch (Exception e){
                                Toast.makeText(mContext, getString(R.string.GoatSick_Instruction), Toast.LENGTH_LONG).show();

                                e.printStackTrace();
                            }


                        }
                        else{
                            if (!DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetails_weightStringHashMap.containsKey(Modal_Static_GoatEarTagDetails.getBarcodeno())){

                                barcodeNo_EdittextView.setVisibility(View.GONE);
                                barcodeNo_textView.setVisibility(View.VISIBLE);
                                getDataFrom_POJO_AND_setData_inUI();
                            }
                            else{
                                Toast.makeText(mContext, "Please Scan the New Item", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                }


            }

            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch) {

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar_in_theActivity(false);
                Toast.makeText(mContext, "There is an volley error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                BaseActivity. isAdding_Or_UpdatingEntriesInDB_Service = false;
                isGoatEarTagDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                Toast.makeText(mContext, "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                BaseActivity. isAdding_Or_UpdatingEntriesInDB_Service = false;
                showProgressBar_in_theActivity(false);
                isGoatEarTagDetailsTableServiceCalled = false;


            }




        };
/*
        if(callMethod.equals(Constants.CallADDMethod)){


            try{


                Modal_Static_GoatEarTagDetails.loadedweightingrams  = (String.valueOf(df.format(entered_Weight_double)));
                Modal_Static_GoatEarTagDetails.currentweightingrams  = (String.valueOf(df.format(entered_Weight_double)));
                Modal_GoatEarTagTransaction.newweightingrams  = (String.valueOf(df.format(entered_Weight_double)));
                Modal_Static_GoatEarTagDetails.description = description_edittext.getText().toString();
                Modal_Static_GoatEarTagDetails.gender=selectedGender;
                Modal_Static_GoatEarTagDetails.breedtype=selectedBreed;
                Modal_Static_GoatEarTagDetails.barcodeno=scannedBarcode;
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


 */
        Log.i("tag1 callMethod",callMethod);
            BaseActivity. isAdding_Or_UpdatingEntriesInDB_Service = true;
         if (callMethod.equals(Constants.CallUPDATEMethod)){
            try{
               // Modal_UpdatedGoatEarTagDetails.setUpdated_selectedItem(selectedCategoryItem);
                Modal_Static_GoatEarTagDetails.batchno = batchNo;
                Modal_Static_GoatEarTagDetails.barcodeno = scannedBarcode;
                Modal_UpdatedGoatEarTagDetails.setUpdated_gender(selectedGender);
                Modal_UpdatedGoatEarTagDetails.setUpdated_breedtype(selectedBreed);
                Modal_UpdatedGoatEarTagDetails.setUpdated_currentweightingrams(String.valueOf(df.format(entered_Weight_double)));
                Modal_UpdatedGoatEarTagDetails.setUpdated_description(description_edittext.getText().toString());
                Modal_UpdatedGoatEarTagDetails.setUpdated_gradeKey(selectedGradeKey);
                Modal_UpdatedGoatEarTagDetails.setUpdated_gradeprice(selectedGradePrice);
                Modal_UpdatedGoatEarTagDetails.setUpdated_gradename(selectedGradeName);
                Modal_UpdatedGoatEarTagDetails.setUpdated_deliverycenterkey(deliveryCenterKey);
                Modal_UpdatedGoatEarTagDetails.setUpdated_deliverycentername(deliveryCenterName);

                if(!calledFrom.equals(getString(R.string.billing_Screen_placeOrder)) && !calledFrom.equals(getString(R.string.billing_Screen_editOrder)) && !calledFrom.equals(getString(R.string.pos_billing_Screen_placeOrder)) && !calledFrom.equals(getString(R.string.pos_placedOrder_Details_Screen_SecondVersion)) && !calledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion))) {
                    if(userType.equals(Constants.userType_DeliveryCenter)) {
                        if (selectedGoatStatus.toUpperCase().equals(getString(R.string.good)) || selectedGoatStatus.toUpperCase().equals(Constants.goatEarTagStatus_Loading)) {
                            Modal_UpdatedGoatEarTagDetails.setUpdated_status(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE);

                        } else if (selectedGoatStatus.toUpperCase().equals(getString(R.string.goatdead))) {
                            Modal_UpdatedGoatEarTagDetails.setUpdated_status(Constants.goatEarTagStatus_Goatdead);

                        } else if (selectedGoatStatus.toUpperCase().equals(getString(R.string.goatsick))) {
                            Modal_UpdatedGoatEarTagDetails.setUpdated_status(Constants.goatEarTagStatus_Goatsick);

                        } else if (selectedGoatStatus.toUpperCase().equals(getString(R.string.sold))) {
                            Modal_UpdatedGoatEarTagDetails.setUpdated_status(Constants.goatEarTagStatus_Sold);

                        }
                        Modal_UpdatedGoatEarTagDetails.setUpdated_stockedweightingrams(String.valueOf(df.format(entered_Weight_double)));
                    }


                }




                if(calledFrom.equals(getString(R.string.billing_Screen_placeOrder)) || calledFrom.equals(getString(R.string.pos_billing_Screen_placeOrder)) || calledFrom.equals(getString(R.string.placedOrder_Details_Screen_SecondVersion)) || calledFrom.equals(getString(R.string.pos_placedOrder_Details_Screen_SecondVersion))){
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_currentweightingrams_boolean() || Modal_UpdatedGoatEarTagDetails.isUpdated_gradekey_boolean()){
                        String addApiToCall = API_Manager.updateGoatEarTag ;
                        GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails,  addApiToCall , callMethod);
                        asyncTask.execute();
                    }

                }
                else   if(calledFrom.equals(getString(R.string.billing_Screen_editOrder))){
                    if(Modal_UpdatedGoatEarTagDetails.isUpdated_currentweightingrams_boolean() || Modal_UpdatedGoatEarTagDetails.isUpdated_gradekey_boolean()){
                        String addApiToCall = API_Manager.updateGoatEarTag ;
                        GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails,  addApiToCall , callMethod);
                        asyncTask.execute();
                    }

                }
                else{
                    String addApiToCall = API_Manager.updateGoatEarTag ;
                    GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails,  addApiToCall , callMethod);
                    asyncTask.execute();
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }


         else if (callMethod.equals(Constants.CallGETMethod)){
             Log.i("tag1 scannedBarcode",scannedBarcode);
             Log.i("tag1 deliveryCenterKey",deliveryCenterKey);
             String addApiToCall = API_Manager.getGetGoatEarTagDetails_forBarcodeNoWithDeliveryCentreKey + "?barcodeno=" + scannedBarcode + "&deliverycentrekey="+deliveryCenterKey;
            GoatEarTagDetails asyncTask = new GoatEarTagDetails(callback_GoatEarTagDetails,  addApiToCall , callMethod);
            asyncTask.execute();

        }






    }

    private void UpdateValueInLocalArray() {

        for(int iterator = 0 ; iterator<GoatEarTagItemDetailsList.earTagItemsForBatch.size(); iterator++){
            try{

                if(earTagItemsForBatch.get(iterator).getBarcodeno().equals(scannedBarcode)) {

                        if(calledFrom.equals(getString(R.string.stock_batch_item_withoutScan))) {
                        earTagItemsForBatch.get(iterator).setStatus(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE);
                        earTagItemsForBatch.remove(iterator);
                    }
                    else {
                        if(calledFrom.equals(getString(R.string.deliverycenter_UnsoldgoatItemList))) {
                            if(selectedGoatStatus.equals(getString(R.string.good)) || selectedGoatStatus.equals(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE)) {
                                earTagItemsForBatch.get(iterator).setStatus(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE);

                            }
                            if(selectedGoatStatus.equals(getString(R.string.sold)) || selectedGoatStatus.equals(getString(R.string.goatdead))){
                                earTagItemsForBatch.remove(iterator);
                            }
                            else  if(selectedGoatStatus.equals(getString(R.string.goatsick))){
                                earTagItemsForBatch.get(iterator).setStatus(Constants.goatEarTagStatus_Goatsick);
                                earTagItemsForBatch.get(iterator).setBarcodeno(scannedBarcode);
                                earTagItemsForBatch.get(iterator).setBatchno(batchNo);
                                earTagItemsForBatch.get(iterator).setBreedtype(selectedBreed);
                                earTagItemsForBatch.get(iterator).setCurrentweightingrams(String.valueOf(df.format(entered_Weight_double)));
                                earTagItemsForBatch.get(iterator).setGender(selectedGender);
                                earTagItemsForBatch.get(iterator).setDescription(description_edittext.getText().toString());
                                earTagItemsForBatch.get(iterator).setGradeprice(selectedGradePrice);
                                earTagItemsForBatch.get(iterator).setGradename(selectedGradeName);
                                earTagItemsForBatch.get(iterator).setGradekey(selectedGradeKey);


                            }
                            else {
                                earTagItemsForBatch.get(iterator).setBarcodeno(scannedBarcode);
                                earTagItemsForBatch.get(iterator).setBatchno(batchNo);
                                earTagItemsForBatch.get(iterator).setBreedtype(selectedBreed);
                                earTagItemsForBatch.get(iterator).setCurrentweightingrams(String.valueOf(df.format(entered_Weight_double)));
                                earTagItemsForBatch.get(iterator).setGender(selectedGender);
                                earTagItemsForBatch.get(iterator).setDescription(description_edittext.getText().toString());
                                earTagItemsForBatch.get(iterator).setGradeprice(selectedGradePrice);
                                earTagItemsForBatch.get(iterator).setGradename(selectedGradeName);
                                earTagItemsForBatch.get(iterator).setGradekey(selectedGradeKey);
                           }

                        }
                        else if(calledFrom.equals(getString(R.string.deliverycenter_ReviewedgoatItemList))) {
                            if(selectedGoatStatus.equals(getString(R.string.good)) || selectedGoatStatus.equals(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE)) {
                                earTagItemsForBatch.get(iterator).setStatus(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE);

                            }
                            if(selectedGoatStatus.equals(getString(R.string.sold)) || selectedGoatStatus.equals(getString(R.string.goatdead))){
                                earTagItemsForBatch.remove(iterator);
                            }
                            else  if(selectedGoatStatus.equals(getString(R.string.goatsick))){
                                earTagItemsForBatch.get(iterator).setStatus(Constants.goatEarTagStatus_Goatsick);
                                earTagItemsForBatch.get(iterator).setBarcodeno(scannedBarcode);
                                earTagItemsForBatch.get(iterator).setBatchno(batchNo);
                                earTagItemsForBatch.get(iterator).setBreedtype(selectedBreed);
                                earTagItemsForBatch.get(iterator).setCurrentweightingrams(String.valueOf(df.format(entered_Weight_double)));
                                earTagItemsForBatch.get(iterator).setGender(selectedGender);
                                earTagItemsForBatch.get(iterator).setDescription(description_edittext.getText().toString());
                                earTagItemsForBatch.get(iterator).setGradeprice(selectedGradePrice);
                                earTagItemsForBatch.get(iterator).setGradename(selectedGradeName);
                                earTagItemsForBatch.get(iterator).setGradekey(selectedGradeKey);
                            }
                            else {
                                earTagItemsForBatch.get(iterator).setBarcodeno(scannedBarcode);
                                earTagItemsForBatch.get(iterator).setBatchno(batchNo);
                                earTagItemsForBatch.get(iterator).setBreedtype(selectedBreed);
                                earTagItemsForBatch.get(iterator).setCurrentweightingrams(String.valueOf(df.format(entered_Weight_double)));
                                earTagItemsForBatch.get(iterator).setGender(selectedGender);
                                earTagItemsForBatch.get(iterator).setDescription(description_edittext.getText().toString());
                                earTagItemsForBatch.get(iterator).setGradeprice(selectedGradePrice);
                                earTagItemsForBatch.get(iterator).setGradename(selectedGradeName);
                                earTagItemsForBatch.get(iterator).setGradekey(selectedGradeKey);
                            }

                        }

                        else if(calledFrom.equals(getString(R.string.billing_Screen_placeOrder)) || calledFrom.equals(getString(R.string.billing_Screen_editOrder)) || calledFrom.equals(getString(R.string.pos_billing_Screen_placeOrder))) {
                            earTagItemsForBatch.get(iterator).setNewWeight_forBillingScreen(String.valueOf(df.format(entered_Weight_double)));
                            earTagItemsForBatch.get(iterator).setGradename(selectedGradeName);
                            earTagItemsForBatch.get(iterator).setGradeprice(selectedGradePrice);
                            earTagItemsForBatch.get(iterator).setGradeprice(selectedGradePrice);
                            earTagItemsForBatch.get(iterator).setGradename(selectedGradeName);
                            earTagItemsForBatch.get(iterator).setGradekey(selectedGradeKey);
                            earTagItemsForBatch.get(iterator).setCurrentweightingrams(String.valueOf(df.format(entered_Weight_double)));

                        }

                        else{
                            earTagItemsForBatch.get(iterator).setBarcodeno(scannedBarcode);
                            earTagItemsForBatch.get(iterator).setBatchno(batchNo);
                            earTagItemsForBatch.get(iterator).setBreedtype(selectedBreed);
                            earTagItemsForBatch.get(iterator).setCurrentweightingrams(String.valueOf(df.format(entered_Weight_double)));
                            earTagItemsForBatch.get(iterator).setGender(selectedGender);
                            earTagItemsForBatch.get(iterator).setDescription(description_edittext.getText().toString());
                            earTagItemsForBatch.get(iterator).setGradeprice(selectedGradePrice);
                            earTagItemsForBatch.get(iterator).setGradename(selectedGradeName);
                            earTagItemsForBatch.get(iterator).setGradekey(selectedGradeKey);
                        }


                    }
                }
            try{
                GoatEarTagItemDetailsList.adapter_earTagItemDetails_list.notifyDataSetChanged();


            }
            catch (Exception e){
                e.printStackTrace();
            }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            try{
                if(earTagItemsForBatch.size()==0){
                    GoatEarTagItemDetailsList.earTagItems_listview.setVisibility(View.GONE);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void UpdateCalculationDataINSharedPref(String callMethod) {
        try {
            String batchNo_fromPreference ="";
            SharedPreferences sharedPreferences_forAdd  = null;
            if(userType.toUpperCase().equals(Constants.userType_DeliveryCenter)){
                sharedPreferences_forAdd =     mContext.getSharedPreferences(Constants.earTagCalculationDeta_DeliveryCenter,MODE_PRIVATE);
            }
            else{
                sharedPreferences_forAdd =     mContext.getSharedPreferences(Constants.earTagCalculationDeta_SupplierCenter,MODE_PRIVATE);

            }
            batchNo_fromPreference =    sharedPreferences_forAdd.getString(
                    "BatchNo", "0"
            );


            if (calledFrom.equals(getString(R.string.deliverycenter_UnsoldgoatItemList))) {

                int soldtotalCount_int = 0 ,soldmaleCount_int =0,soldfemaleCount_int =0, soldfemaleWithBabyCount_int =0 ;
                int unsoldtotalCount_int =0 ,unsoldmaleCount_int =0 ,unsoldfemaleCount_int =0 , unsoldfemaleWithBabyCount_int =0;

                double soldtotalItemWeight =0 ;
                double unsoldtotalItemWeight =0 ;
                try{
                    if (batchNo_fromPreference.toUpperCase().equals(batchNo)) {

                        soldtotalCount_int = sharedPreferences_forAdd.getInt(
                                "TotalSoldCount", 0
                        );

                        soldmaleCount_int = sharedPreferences_forAdd.getInt(
                                "SoldMaleCount", 0
                        );
                        soldfemaleCount_int = sharedPreferences_forAdd.getInt(
                                "SoldFemaleCount", 0
                        );
                        soldfemaleWithBabyCount_int = sharedPreferences_forAdd.getInt(
                                "SoldFemaleWithBabyCount", 0
                        );


                        soldtotalItemWeight = (double) sharedPreferences_forAdd.getFloat(
                                "TotalSoldWeight", 0
                        );

                        unsoldtotalCount_int = sharedPreferences_forAdd.getInt(
                                "TotalUnsoldCount", 0
                        );

                        unsoldmaleCount_int = sharedPreferences_forAdd.getInt(
                                "UnsoldMaleCount", 0
                        );
                        unsoldfemaleCount_int = sharedPreferences_forAdd.getInt(
                                "UnsoldFemaleCount", 0
                        );
                        unsoldfemaleWithBabyCount_int = sharedPreferences_forAdd.getInt(
                                "UnsoldFemaleWithBabyCount", 0
                        );


                        unsoldtotalItemWeight = (double) sharedPreferences_forAdd.getFloat(
                                "TotalUnsoldWeight", 0
                        );


                        SharedPreferences sharedPreferences = null;
                            sharedPreferences = mContext.getSharedPreferences(Constants.earTagCalculationDeta_DeliveryCenter, MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        if (callMethod.equals(Constants.CallUPDATEMethod)) {

                            previous_WeightInGrams = previous_WeightInGrams.replaceAll("[^\\d.]", "");

                            if(previous_WeightInGrams.equals("") || previous_WeightInGrams.equals(null)){
                                previous_WeightInGrams = "0";
                            }
                            double previous_WeightInGrams_double = Double.parseDouble(previous_WeightInGrams);

                            if (selectedGoatStatus.equals(getString(R.string.sold))) {
                                try {
                                    unsoldtotalItemWeight = unsoldtotalItemWeight - previous_WeightInGrams_double;
                                }
                                catch (Exception e){
                                    unsoldtotalItemWeight =0;

                                    e.printStackTrace();
                                }
                                if(unsoldtotalItemWeight <0){
                                    unsoldtotalItemWeight =0;
                                }

                                soldtotalItemWeight = soldtotalItemWeight + entered_Weight_double;
                                unsoldtotalCount_int = unsoldtotalCount_int - 1;
                                soldtotalCount_int = soldtotalCount_int + 1;


                                if (previousSelectedGender.toUpperCase().equals(getString(R.string.MALE))) {
                                    unsoldmaleCount_int = unsoldmaleCount_int - 1;
                                }

                                if (previousSelectedGender.toUpperCase().equals(getString(R.string.FEMALE))) {
                                    unsoldfemaleCount_int = unsoldfemaleCount_int - 1;
                                }

                                if (previousSelectedGender.toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {
                                    unsoldfemaleWithBabyCount_int = unsoldfemaleWithBabyCount_int - 1;
                                }


                                if (selectedGender.toUpperCase().equals(getString(R.string.MALE))) {
                                    soldmaleCount_int = soldmaleCount_int + 1;
                                }

                                if (selectedGender.toUpperCase().equals(getString(R.string.FEMALE))) {
                                    soldfemaleCount_int = soldfemaleCount_int + 1;
                                }

                                if (selectedGender.toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {
                                    soldfemaleWithBabyCount_int = soldfemaleWithBabyCount_int + 1;

                                }
                                if(unsoldtotalCount_int <= 0){
                                    unsoldfemaleWithBabyCount_int =0;
                                    unsoldfemaleCount_int =0;
                                    unsoldmaleCount_int =0;
                                    unsoldtotalItemWeight =0;
                                    unsoldtotalCount_int =0;
                                }
                                myEdit.putInt(
                                        "TotalSoldCount", soldtotalCount_int
                                );
                                myEdit.putInt(
                                        "SoldMaleCount", soldmaleCount_int
                                );
                                myEdit.putInt(
                                        "SoldFemaleCount", soldfemaleCount_int
                                );
                                myEdit.putInt(
                                        "SoldFemaleWithBabyCount", soldfemaleWithBabyCount_int
                                );


                                myEdit.putFloat(
                                        "TotalSoldWeight", (float) Double.parseDouble(df.format(soldtotalItemWeight))
                                );

                                myEdit.putInt(
                                        "TotalUnsoldCount", unsoldtotalCount_int
                                );

                                myEdit.putInt(
                                        "UnsoldMaleCount", unsoldmaleCount_int
                                );


                                myEdit.putInt(
                                        "UnsoldFemaleCount", unsoldfemaleCount_int
                                );

                                myEdit.putInt(
                                        "UnsoldFemaleWithBabyCount", unsoldfemaleWithBabyCount_int
                                );


                                myEdit.putFloat(
                                        "TotalUnsoldWeight", (float) Double.parseDouble(df.format(unsoldtotalItemWeight))
                                );

                            }
                            else if (selectedGoatStatus.equals(getString(R.string.goatdead)))
                             {

                                 try{
                                     unsoldtotalItemWeight = unsoldtotalItemWeight - previous_WeightInGrams_double;

                                 }
                                 catch (Exception e){
                                     unsoldtotalItemWeight =0;
                                     e.printStackTrace();
                                 }

                                 if(unsoldtotalItemWeight < 0){
                                     unsoldtotalItemWeight =0;
                                 }


                                unsoldtotalCount_int = unsoldtotalCount_int - 1;
                                if (previousSelectedGender.toUpperCase().equals(getString(R.string.MALE))) {
                                    unsoldmaleCount_int = unsoldmaleCount_int - 1;
                                }

                                if (previousSelectedGender.toUpperCase().equals(getString(R.string.FEMALE))) {
                                    unsoldfemaleCount_int = unsoldfemaleCount_int - 1;
                                }

                                if (previousSelectedGender.toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {
                                    unsoldfemaleWithBabyCount_int = unsoldfemaleWithBabyCount_int - 1;
                                }

                                 if(unsoldtotalCount_int <= 0){
                                     unsoldfemaleWithBabyCount_int =0;
                                     unsoldfemaleCount_int =0;
                                     unsoldmaleCount_int =0;
                                     unsoldtotalItemWeight =0;
                                     unsoldtotalCount_int =0;
                                 }
                                 myEdit.putInt(
                                         "TotalUnsoldCount", unsoldtotalCount_int
                                 );

                                 myEdit.putInt(
                                         "UnsoldMaleCount", unsoldmaleCount_int
                                 );


                                 myEdit.putInt(
                                         "UnsoldFemaleCount", unsoldfemaleCount_int
                                 );

                                 myEdit.putInt(
                                         "UnsoldFemaleWithBabyCount", unsoldfemaleWithBabyCount_int
                                 );


                                 myEdit.putFloat(
                                         "TotalUnsoldWeight", (float) Double.parseDouble(df.format(unsoldtotalItemWeight))
                                 );


                             }
                            else{
                                try {
                                    unsoldtotalItemWeight = unsoldtotalItemWeight - previous_WeightInGrams_double;
                                }
                                catch (Exception e){
                                    unsoldtotalItemWeight =0;
                                    e.printStackTrace();
                                }

                                if(unsoldtotalItemWeight <0){
                                    unsoldtotalItemWeight =0;
                                }
                                unsoldtotalItemWeight = unsoldtotalItemWeight + entered_Weight_double;
                                myEdit.putFloat(
                                        "TotalUnsoldWeight", (float) Double.parseDouble(df.format(unsoldtotalItemWeight))
                                );
                            }
                            myEdit.apply();
                        }



                    }

                    }
                catch (Exception e){
                    e.printStackTrace();
                }


            }
            else if (calledFrom.equals(getString(R.string.deliverycenter_SoldgoatItemList))) {

            }
            else{
                try{
                    int totalCount_int = 0 ,maleCount_int =0,femaleCount_int =0, femaleWithBabyCount_int =0 ;
                    int totalReviewedItemCount =0 ,reviewed_maleCount_int =0 , reviewed_femaleCount_int =0 , reviewed_femaleWithbabyCount_int =0;

                    double totalReviewedItemWeight =0 , total_loadedweight_double =0,minimum_weight_double =0,maximum_weight_double =0, average_weight_double =0;



                    try {
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
                            totalReviewedItemWeight = sharedPreferences_forAdd.getFloat(
                                    "ReviewedTotalWeight", 0
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

                            SharedPreferences sharedPreferences = null;
                            if (userType.toUpperCase().equals(Constants.userType_DeliveryCenter)) {
                                sharedPreferences = mContext.getSharedPreferences(Constants.earTagCalculationDeta_DeliveryCenter, MODE_PRIVATE);

                            } else {
                                sharedPreferences = mContext.getSharedPreferences(Constants.earTagCalculationDeta_SupplierCenter, MODE_PRIVATE);

                            }

                            // SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constants.earTagCalculationDeta_SupplierCenter, MODE_PRIVATE);

                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            /*
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
            */
                            try {
                                if (callMethod.equals(Constants.CallUPDATEMethod)) {


                                    previous_WeightInGrams = previous_WeightInGrams.replaceAll("[^\\d.]", "");
                                    if(previous_WeightInGrams.equals("") || previous_WeightInGrams.equals(null)){
                                        previous_WeightInGrams = "0";
                                    }


                                    double previous_WeightInGrams_double = Double.parseDouble(previous_WeightInGrams);
                                    if (calledFrom.equals(getString(R.string.supplier_goatItemList))) {
                                        //updateChanges in new added item weight details
                                        if (earTaglastStatusFromDB.equals(Constants.goatEarTagStatus_Loading)) {
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
                                    else if (calledFrom.equals(getString(R.string.deliverycenter_ReviewedgoatItemList))) {
                                        //update weight changes only in reviewed item if status is good
                                        //unless remove it from the count

                                        if (selectedGoatStatus.equals(getString(R.string.good)) || selectedGoatStatus.equals(getString(R.string.goatsick)) || selectedGoatStatus.toUpperCase().equals(Constants.goatEarTagStatus_Loading))
                                        {
                                            try {
                                                totalReviewedItemWeight = totalReviewedItemWeight - previous_WeightInGrams_double;
                                            }
                                            catch (Exception e){
                                                totalReviewedItemWeight =0;
                                                e.printStackTrace();
                                            }


                                            if(totalReviewedItemWeight<0){
                                                totalReviewedItemWeight =0;
                                            }


                                            totalReviewedItemWeight = totalReviewedItemWeight + entered_Weight_double;




                                            myEdit.putFloat(
                                                    "ReviewedTotalWeight", (float) Double.parseDouble(df.format(totalReviewedItemWeight))
                                            );

                                        }
                                        else {
                                            try {
                                                totalReviewedItemWeight = totalReviewedItemWeight - previous_WeightInGrams_double;
                                            }
                                            catch (Exception e){
                                                totalReviewedItemWeight =0;
                                                e.printStackTrace();
                                            }


                                            if(totalReviewedItemWeight<0){
                                                totalReviewedItemWeight =0;
                                            }

                                            totalReviewedItemCount = totalReviewedItemCount - 1;
                                            if (previousSelectedGender.toUpperCase().equals(getString(R.string.MALE))) {
                                                reviewed_maleCount_int = reviewed_maleCount_int - 1;
                                            }

                                            if (previousSelectedGender.toUpperCase().equals(getString(R.string.FEMALE))) {
                                                reviewed_femaleCount_int = reviewed_femaleCount_int - 1;
                                            }

                                            if (previousSelectedGender.toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {
                                                reviewed_femaleWithbabyCount_int = reviewed_femaleWithbabyCount_int - 1;
                                            }

                                            if(totalReviewedItemCount == 0){
                                                reviewed_maleCount_int =0;
                                                reviewed_femaleCount_int =0;
                                                reviewed_femaleWithbabyCount_int =0;
                                                totalReviewedItemWeight =0;
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
                                                    "ReviewedFemaleWithBabyCount", reviewed_femaleWithbabyCount_int
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

                                        }

                                    }
                                    else if (calledFrom.equals(getString(R.string.stock_batch_item_withoutScan))) {
                                        // if selected item status is good then remove the item details  from unreviewed and add it in reviewed details
                                        // if status is other than that then remove the item from the Unreviewed  details
                                        if (selectedGoatStatus.equals(getString(R.string.good)) || selectedGoatStatus.equals(getString(R.string.goatsick)) || selectedGoatStatus.toUpperCase().equals(Constants.goatEarTagStatus_Loading)) {


                                            try {
                                                total_loadedweight_double = total_loadedweight_double - previous_WeightInGrams_double;
                                            }
                                            catch (Exception e){
                                                total_loadedweight_double =0;
                                                e.printStackTrace();
                                            }


                                            if(total_loadedweight_double<0){
                                                total_loadedweight_double =0;
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

                                            if(totalCount_int <= 0){
                                                femaleWithBabyCount_int =0;
                                                femaleCount_int =0;
                                                maleCount_int =0;
                                                total_loadedweight_double =0;
                                                totalCount_int =0;
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
                                        else {



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
                                            if(totalCount_int <= 0){
                                                femaleWithBabyCount_int =0;
                                                femaleCount_int =0;
                                                maleCount_int =0;
                                                total_loadedweight_double =0;
                                                totalCount_int=0;
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
                                            myEdit.putFloat(
                                                    "AverageWeight", (float) Double.parseDouble(df.format(average_weight_double))

                                            );


                                            myEdit.putFloat(
                                                    "TotalWeight", (float) Double.parseDouble(df.format(total_loadedweight_double))
                                            );

                                        }

                                    }

                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            myEdit.apply();



                        }

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }





        new Modal_UpdatedGoatEarTagDetails();
        new Modal_Static_GoatEarTagDetails();
        entered_Weight_double =0;
        previous_WeightInGrams ="";
        previousSelectedGender = "";
        batchStatus ="";
        batchNo = "";
        showProgressBar_in_theActivity(false);
        isGoatEarTagTransactionTableServiceCalled = false;
        BaseActivity. isAdding_Or_UpdatingEntriesInDB_Service = false;
        try {
            if (calledFrom.equals(getString(R.string.billing_Screen_placeOrder)) || calledFrom.equals(getString(R.string.pos_billing_Screen_placeOrder))) {
                //((BillingScreen) getActivity()).closeFragment();
                DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.closeFragment();
            } else if (calledFrom.equals(getString(R.string.billing_Screen_editOrder))) {
                ((GoatEarTagItemDetailsList) getActivity()).closeFragment();
            } else {
                ((GoatEarTagItemDetailsList) getActivity()).closeFragment();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
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
            String barcodeData = (editable.toString());
            Log.i("tag1 barcodeData",barcodeData);
            if(barcodeData.length() > 3) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {

                                requireActivity().runOnUiThread(new Runnable() {

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
                                                                scannedBarcode = barcodeData;
                                                                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod);

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

                                                                scannedBarcode = barcodeData;
                                                                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod);
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

                                                                scannedBarcode = barcodeData;
                                                                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod);
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
                                                                scannedBarcode = barcodeData;
                                                                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod);
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

                                                                scannedBarcode = barcodeData;
                                                                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod);
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
                                                                scannedBarcode = barcodeData;
                                                                Initialize_and_ExecuteInGoatEarTagDetails(Constants.CallGETMethod);
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

    private void throwAlertMsg() {

        new TMCAlertDialogClass( mContext, R.string.app_name, R.string.ScanAgain_Instruction,
                R.string.OK_Text, R.string.Empty_Text,
                new TMCAlertDialogClass.AlertListener() {
                    @Override
                    public void onYes() {
                    //    onRestart();

                    }

                    @Override
                    public void onNo() {

                    }
                });


    }

}