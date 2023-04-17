package com.tmc.tmcb2bpartnerapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchNoManager;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.DeliveryCenterDetails;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchNoManagerInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.DeliveryCenterDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_AppUserAccess;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetailsStatic;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetailsUpdate;
import com.tmc.tmcb2bpartnerapp.modal.Modal_DeliveryCenterDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_SupplierDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.tmc.tmcb2bpartnerapp.utils.Constants.CallADDMethod;
import static com.tmc.tmcb2bpartnerapp.utils.Constants.CallUPDATEMethod;
import static com.tmc.tmcb2bpartnerapp.utils.Constants.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateNewBatch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateNewBatch extends Fragment {

    public static List<Modal_DeliveryCenterDetails> deliveryCenterDetailsList = new ArrayList<>();
    DeliveryCenterDetailsInterface callback_DeliveryCenterDetailsInterface = null;
    B2BBatchNoManagerInterface callback_b2BBatchIdManagerInterface =null;
    boolean  isDeliveryCentereTableServiceCalled = false;
    B2BBatchDetailsInterface callback_B2BBatchDetailsInterface = null;
    boolean  isBatchDetailsTableServiceCalled = false;
    Spinner deliveryCenterName_spinner;
    LinearLayout loadingPanel , loadingpanelmask,backButton_layout;
    Context mContext;
    TextView batchid_textview;
    Button createBatch_button;

    String activityCalledFrom = "", selectDeliveryCenterKey ="", batchno = "" , selectDeliveryCenterName ="", supplierKey ="";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateNewBatch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateNewBatch.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateNewBatch newInstance(String param1, String param2) {
        CreateNewBatch fragment = new CreateNewBatch();
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
                return inflater.inflate(R.layout.fragment_create_new_batch, container, false);
            } else {

                // Inflate the layout for this fragment
                return inflater.inflate(R.layout.pos_fragment_create_new_batch, container, false);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return inflater.inflate(R.layout.fragment_create_new_batch, container, false);
        }





    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        batchid_textview = view.findViewById(R.id.batchid_textview);
        deliveryCenterName_spinner = view.findViewById(R.id.delieryCenterName_spinner);
        loadingPanel = view.findViewById(R.id.loadingPanel);
        loadingpanelmask = view.findViewById(R.id.loadingpanelmask);
        createBatch_button =  view.findViewById(R.id.createBatch_button);
        backButton_layout  =  view.findViewById(R.id.backButton_layout);

        Intialize_And_getDataFrom_DeliveryCenterDetails();
        Initialize_And_Process_BatchIDManager("GET");
        createBatch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Initialize_And_Process_BatchIDManager("GENERATE");
            }
        });


        deliveryCenterName_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                selectDeliveryCenterKey = getDeliveryCenterData(position, "deliverycenterkey");
                selectDeliveryCenterName = getDeliveryCenterData(position, "name");


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        backButton_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFragment();
            }
        });

    }

    private void Intialize_And_getDataFrom_DeliveryCenterDetails() {

        if (isDeliveryCentereTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isDeliveryCentereTableServiceCalled = true;
        callback_DeliveryCenterDetailsInterface = new DeliveryCenterDetailsInterface() {


            @Override
            public void notifySuccess(String result) {
                addDataToDeliveryCenterSpinner();
            }

            @Override
            public void notifyError(VolleyError error) {
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
                showProgressBar(false);

                isDeliveryCentereTableServiceCalled = false;
            }


        };

        String getApiToCall = API_Manager.getDeliveryCenterList ;

        DeliveryCenterDetails asyncTask = new DeliveryCenterDetails(callback_DeliveryCenterDetailsInterface,  getApiToCall, Constants.CallGETListMethod);
        asyncTask.execute();




    }

    private void addDataToDeliveryCenterSpinner() {
        deliveryCenterDetailsList = DatabaseArrayList_PojoClass.deliveryCenterDetailsList ;
        ArrayList<String> deliveryCenter_arrayList = new ArrayList<>();
        //  deliveryCenter_arrayList.add(getString(R.string.selectItem_SpinnerInstruction));
        try{
            for(int i=0; i< deliveryCenterDetailsList.size();i++){
                deliveryCenter_arrayList.add(deliveryCenterDetailsList.get(i).getName());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        ArrayAdapter aAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, deliveryCenter_arrayList);
        deliveryCenterName_spinner.setAdapter(aAdapter);
        showProgressBar(false);

    }


    private void Initialize_And_Process_BatchIDManager(String methodToCall) {
        showProgressBar(true);
        callback_b2BBatchIdManagerInterface = new B2BBatchNoManagerInterface() {


            @Override
            public void notifySuccess(String result) {
                batchno = result;
                if(methodToCall.equals("GET")){
                    int batchid_Int = Integer.parseInt(batchno);
                    batchid_Int = batchid_Int+1;
                    batchno = String.valueOf(batchid_Int);
                }
                else if(methodToCall.equals("GENERATE")){

                    Intialize_And_Process_BatchDetails(batchno, CallADDMethod);
                }
                batchid_textview.setText(batchno);

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
            }

            @Override
            public void notifyProcessingError(Exception error) {
                showProgressBar(false);
            }



        };
        if(methodToCall.equals("GENERATE"))
            B2BBatchNoManager.generateNewBatchNo(callback_b2BBatchIdManagerInterface);
        else if(methodToCall.equals("GET"))
            B2BBatchNoManager.getBatchNo(callback_b2BBatchIdManagerInterface);


    }

    private void Intialize_And_Process_BatchDetails(String batchId, String callADDMethod) {
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
                    AlertDialogClass.showDialog(getActivity(), R.string.BatchDetailsAlreadyCreated_Instruction);

                }
                else if(result.equals(Constants.successResult_volley)){
                    try {
                        SaveCalculationDatasInSharedPref();

                        SupplierHomeScreenFragment.iscalledFromCreateNewbatch =true;
                        SupplierHomeScreenFragment.viewBatchDetails_Button.callOnClick();
                        SupplierHomeScreenFragment.batchNo_textview.setText(Modal_B2BBatchDetailsStatic.getBatchno());
                        SupplierHomeScreenFragment.batch_createdDate.setText(Modal_B2BBatchDetailsStatic.getCreateddate());
                        SupplierHomeScreenFragment.batchStatus_textview.setText(Modal_B2BBatchDetailsStatic.getStatus());
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    closeFragment();

                }
                else{
                    Toast.makeText(mContext, Constants.unknown_API_Result_volley, Toast.LENGTH_SHORT).show();
                }




                showProgressBar(false);
                isBatchDetailsTableServiceCalled = false;

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);

                isBatchDetailsTableServiceCalled = false;

                Toast.makeText(mContext, Constants.volleyError_Result_volley, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void notifyProcessingError(Exception error) {
                showProgressBar(false);

                isBatchDetailsTableServiceCalled = false;
                Toast.makeText(mContext, Constants.processingErrorResult_volley, Toast.LENGTH_SHORT).show();

            }




        };
        if(callADDMethod.equals(CallADDMethod)) {
            Modal_B2BBatchDetailsStatic.setBatchno(batchId);
            Modal_B2BBatchDetailsStatic.setDeliverycentername(selectDeliveryCenterName);
            Modal_B2BBatchDetailsStatic.setDeliverycenterkey(selectDeliveryCenterKey);
            Modal_B2BBatchDetailsStatic.setSupplierkey(Modal_SupplierDetails.getSupplierkey_static());
            Modal_B2BBatchDetailsStatic.setSuppliername(Modal_SupplierDetails.getName_static());
            Modal_B2BBatchDetailsStatic.setStatus(Constants.batchDetailsStatus_Loading);
            Modal_B2BBatchDetailsStatic.setLoadedweightingrams("0");
            Modal_B2BBatchDetailsStatic.setStockedweightingrams("0");
            Modal_B2BBatchDetailsStatic.setItemcount("0");

            Modal_B2BBatchDetailsStatic.setCreateddate(DateParser.getDate_and_time_newFormat());
            Modal_B2BBatchDetailsStatic.setSuppliermobileno(Modal_AppUserAccess.getMobileno());


            String addApiToCall = API_Manager.addBatchDetails;

            B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface, addApiToCall, callADDMethod);
            asyncTask.execute();
        }
        else  if(callADDMethod.equals(CallUPDATEMethod)) {
            Modal_B2BBatchDetailsUpdate modal_b2BBatchDetailsUpdate = new Modal_B2BBatchDetailsUpdate();
            modal_b2BBatchDetailsUpdate.setBatchno(batchno);
            modal_b2BBatchDetailsUpdate.setSupplierkey(supplierKey);
            modal_b2BBatchDetailsUpdate.setStatus(Constants.batchDetailsStatus_Cancelled);
            modal_b2BBatchDetailsUpdate.setSupplierkey(selectDeliveryCenterKey);
            String addApiToCall = API_Manager.updateBatchDetailsWithSupplierkeyBatchNo;

            B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface, addApiToCall, CallUPDATEMethod);
            asyncTask.execute();

        }






    }

    public  void closeFragment() {


        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(SupplierHomeScreenFragment.createNewBatchFrame.getId());
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();


        SupplierHomeScreenFragment.lastBatchLayout.setVisibility(View.VISIBLE);
        SupplierHomeScreenFragment.createNewBatchFrame.setVisibility(View.GONE);
    }

    private void SaveCalculationDatasInSharedPref() {


        SharedPreferences sharedPreferences
                = mContext.getSharedPreferences(Constants.earTagCalculationDeta_SupplierCenter,
                MODE_PRIVATE);

        SharedPreferences.Editor myEdit
                = sharedPreferences.edit();

        myEdit.putInt(
                "TotalCount", 0
        );

        myEdit.putInt(
                "MaleCount", 0
        );
        myEdit.putInt(
                "FemaleCount", 0
        );
        myEdit.putInt(
                "FemaleWithBabyCount", 0
        );

        myEdit.putFloat(
                "TotalWeight", (float) 0
        );

        myEdit.putFloat(
                "MinimumWeight", (float)  0
        );


        myEdit.putFloat(
                "MaximumWeight", (float) (0)
        );


        myEdit.putFloat(
                "AverageWeight",(float)  (0)
        );

        myEdit.putString(
                "UpdatedTime", String.valueOf(DateParser.getDate_and_time_newFormat())
        );
        myEdit.putString(
                "BatchNo", String.valueOf(batchno)
        );



        myEdit.apply();





    }


    public  void showProgressBar(boolean show) {

        if(show){
            loadingPanel.setVisibility(View.VISIBLE);
            loadingpanelmask.setVisibility(View.VISIBLE);

        }
        else{
            loadingPanel.setVisibility(View.GONE);
            loadingpanelmask.setVisibility(View.GONE);

        }


    }



    private String getDeliveryCenterData(int position,String fieldName){
        String data="";
        try {
            Modal_DeliveryCenterDetails vendor = DatabaseArrayList_PojoClass.deliveryCenterDetailsList.get(position);
            data = vendor.getGet(fieldName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}