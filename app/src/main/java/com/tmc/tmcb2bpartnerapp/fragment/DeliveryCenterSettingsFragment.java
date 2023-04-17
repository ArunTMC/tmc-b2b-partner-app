package com.tmc.tmcb2bpartnerapp.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.Add_Or_Edit_Retailer_Activity;
import com.tmc.tmcb2bpartnerapp.activity.DatewisePlacedOrdersList;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.activity.ChangeGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.activity.LoginActivity;
import com.tmc.tmcb2bpartnerapp.utils.TMCAlertDialogClass;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeliveryCenterSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeliveryCenterSettingsFragment extends Fragment {
    TextView userMobileNo,supplier_name;
    String usermobileno_string ="",supplierName ="";
    Context mContext;
    Button logout_button ,goatgradeDetails_button;
    LinearLayout add_or_edit_retailers_button , datewisePlacedOrdersList_button;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DeliveryCenterSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeliveryCenterSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeliveryCenterSettingsFragment newInstance(String param1, String param2) {
        DeliveryCenterSettingsFragment fragment = new DeliveryCenterSettingsFragment();
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

        try {
            BaseActivity.baseActivity.getDeviceName();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            if (BaseActivity.isDeviceIsMobilePhone) {
                // Inflate the layout for this fragment
                return inflater.inflate(R.layout.fragment_delivery_center_settings, container, false);
            } else {

                // Inflate the layout for this fragment
                return inflater.inflate(R.layout.pos_fragment_delivery_center_settings, container, false);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return inflater.inflate(R.layout.fragment_delivery_center_settings, container, false);

        }
        // Inflate the layout for this fragment

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        supplier_name = view.findViewById(R.id.deliveryCenter_name);
        userMobileNo = view.findViewById(R.id.userMobileNo);
        goatgradeDetails_button = view.findViewById(R.id.goatgradeDetails_button);
        datewisePlacedOrdersList_button  = view.findViewById(R.id.datewisePlacedOrdersList_button);
        add_or_edit_retailers_button = view.findViewById(R.id.add_or_edit_retailers_button);
        SharedPreferences sh = mContext.getSharedPreferences("LoginData",MODE_PRIVATE);
        usermobileno_string = sh.getString("UserMobileNumber","");

        SharedPreferences sh1 = mContext.getSharedPreferences("DeliveryCenterData",MODE_PRIVATE);
        supplierName = sh1.getString("DeliveryCenterName","");

        supplier_name.setText(supplierName);
        userMobileNo.setText(usermobileno_string);

        logout_button = view.findViewById(R.id.logout_button);
       // datewisePlacedOrdersList_button.setVisibility(View.GONE);



        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TMCAlertDialogClass(mContext, R.string.app_name, R.string.Logout_Instruction,
                        R.string.Yes_Text, R.string.No_Text,
                        new TMCAlertDialogClass.AlertListener() {
                            @Override
                            public void onYes() {
                                signOutfromAWSandClearSharedPref();

                            }

                            @Override
                            public void onNo() {

                            }
                        });

            }
        });


        goatgradeDetails_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ChangeGoatGradeDetails.class);
                startActivity(intent);
            }
        });


        datewisePlacedOrdersList_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DatewisePlacedOrdersList.class);
                startActivity(intent);
            }
        });


        add_or_edit_retailers_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Add_Or_Edit_Retailer_Activity.class);
                startActivity(intent);
            }
        });



    }




    private void signOutfromAWSandClearSharedPref() {
        try {
            AWSMobileClient.getInstance().signOut();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        SharedPreferences sharedPreferences
                = mContext.getSharedPreferences("LoginData",
                MODE_PRIVATE);

        SharedPreferences.Editor myEdit
                = sharedPreferences.edit();

        myEdit.putBoolean(
                "LoginStatus", false
        );
        myEdit.putString("UserMobileNumber","");
        myEdit.putString(
                "UserType", ""
        );
        myEdit.putString(
                "SupplierKey", ""        );


        myEdit.apply();


        SharedPreferences sharedPreferences_SupplierData
                = mContext.getSharedPreferences("DeliveryCenterData",
                MODE_PRIVATE);

        SharedPreferences.Editor edit
                = sharedPreferences_SupplierData.edit();

        edit.putString(
                "DeliveryCenterKey", "");
        edit.putString(
                "DeliveryCenterName", "");
        edit.putString(
                "DeliveryCenterPassword", "");


        edit.apply();

        Intent i = new Intent(mContext, LoginActivity.class);
        startActivity(i);
        getActivity().finish();



    }






}