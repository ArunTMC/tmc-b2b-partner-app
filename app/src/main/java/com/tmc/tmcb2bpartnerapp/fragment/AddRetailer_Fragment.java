package com.tmc.tmcb2bpartnerapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BRetailerDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRetailer_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRetailer_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private String mParam1;
    private String mParam2;
    String retailers_name = "";
    String retailers_mobileno = "";
    String retailers_address = "" ,gstin ="";
    String  deliveryCenterKey ="" , deliveryCenterName ="";



    EditText retailerDetailsFrame,mobileNo_textView,address_edittext,retailerName_textView ,gstin_editText ;
    Button save_button;
    static LinearLayout loadingPanel;
    static LinearLayout loadingpanelmask;
    LinearLayout back_IconLayout;
    Context mContext;


    B2BRetailerDetailsInterface callback_retailerDetailsInterface = null;
    boolean  isRetailerDetailsServiceCalled = false ;

    public AddRetailer_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRetailer_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRetailer_Fragment newInstance(String param1, String param2) {
        AddRetailer_Fragment fragment = new AddRetailer_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity().getWindow().getContext();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
                return inflater.inflate(R.layout.fragment_add_retailer_, container, false);
            } else {

                // Inflate the layout for this fragment
                return inflater.inflate(R.layout.pos_fragment_add_retailer, container, false);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return inflater.inflate(R.layout.fragment_add_retailer_, container, false);

        }


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retailerName_textView = view.findViewById(R.id.retailerName_textView);
        address_edittext = view.findViewById(R.id.address_edittext);
        mobileNo_textView = view.findViewById(R.id.mobileNo_textView);
        retailerDetailsFrame = view.findViewById(R.id.retailerDetailsFrame);
        loadingpanelmask =  view.findViewById(R.id.loadingpanelmask);
        gstin_editText =  view.findViewById(R.id.gstin_editText);

        loadingPanel =  view.findViewById(R.id.loadingPanel);
        back_IconLayout =  view.findViewById(R.id.back_IconLayout);
        SharedPreferences sh1 = mContext.getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);

        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");


        save_button  = view.findViewById(R.id.save_button);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            retailers_mobileno = mobileNo_textView.getText().toString();
            retailers_address = address_edittext.getText().toString();
            retailers_name = retailerName_textView.getText().toString();
            gstin = gstin_editText.getText().toString();

            if(retailers_mobileno.length()==10){
                retailers_mobileno = "+91"+retailers_mobileno;
             if(retailers_name.length()>0){
                 call_and_init_B2BRetailerDetailsService();
             }
             else{
                 AlertDialogClass.showDialog(getActivity(), R.string.retailers_name_cant_be_empty);

             }
            }
            else{
                AlertDialogClass.showDialog(getActivity(), R.string.retailers_mobileno_should_be_10digits);

            }


            }
        });

        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // ((BillingScreen)getActivity()).closeFragment();
                DeliveryCenter_PlaceOrderScreen_SecondVersn.deliveryCenter_placeOrderScreen_secondVersn.closeFragment();

            }
        });

    }

    private void call_and_init_B2BRetailerDetailsService() {

        showProgressBar(true);
        if (isRetailerDetailsServiceCalled) {
             showProgressBar(false);
            return;
        }
        isRetailerDetailsServiceCalled = true;
        callback_retailerDetailsInterface = new B2BRetailerDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayListt) {


            }

            @Override
            public void notifySuccess(String result) {



                if(result.equals(Constants.item_Already_Added_volley)){
                    AlertDialogClass.showDialog(getActivity(), R.string.retailersAlreadyCreated_Instruction);
                    isRetailerDetailsServiceCalled = false;
                    showProgressBar(false);

                }
                else if(result.equals(Constants.successResult_volley)){
                    try {
                        DeliveryCentre_PlaceOrderScreen_Fragment.retailerDetailsArrayList = DatabaseArrayList_PojoClass.retailerDetailsArrayList;

                        //  BillingScreen.retailerDetailsArrayList = DatabaseArrayList_PojoClass.retailerDetailsArrayList;
                        isRetailerDetailsServiceCalled = false;
                        showProgressBar(false);
                        // ((BillingScreen)getActivity()).closeFragment();
                        DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.closeFragment();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                    try{




                              //  DeliveryCenter_PlaceOrderScreen_SecondVersn.deliveryCenter_placeOrderScreen_secondVersn. call_and_init_B2BRetailerDetailsService(Constants.CallGETListMethod , true,retailers_mobileno);

                        isRetailerDetailsServiceCalled = false;
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                }
                else{
                    isRetailerDetailsServiceCalled = false;
                    showProgressBar(false);
                }



            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isRetailerDetailsServiceCalled = false;
                showProgressBar(false);
            }

            @Override
            public void notifyProcessingError(Exception error) {
                isRetailerDetailsServiceCalled = false;
                showProgressBar(false);
            }


        };

        Modal_B2BRetailerDetails.setAddress_static(retailers_address);
        Modal_B2BRetailerDetails.setMobileno_static(retailers_mobileno);
        Modal_B2BRetailerDetails.setRetailername_static(retailers_name);
        Modal_B2BRetailerDetails.setDeliveryCenterKey_static(deliveryCenterKey);
        Modal_B2BRetailerDetails.setDeliveryCenterName_static(deliveryCenterName);
        Modal_B2BRetailerDetails.setGstin_static(gstin);
        String getApiToCall = API_Manager.addRetailerDetailsList ;

        B2BRetailerDetails asyncTask = new B2BRetailerDetails(callback_retailerDetailsInterface,  getApiToCall, Constants.CallADDMethod);
        asyncTask.execute();



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
}