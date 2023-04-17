package com.tmc.tmcb2bpartnerapp.fragment;

import android.content.Context;
import android.content.Intent;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.SupplierDashboardScreen;
import com.tmc.tmcb2bpartnerapp.activity.View_or_Edit_BatchItem_Supplier;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchNoManager;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchNoManagerInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetailsStatic;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.NukeSSLCerts;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.tmc.tmcb2bpartnerapp.utils.Constants.CallGETLastEntryMethod;
import static com.tmc.tmcb2bpartnerapp.utils.Constants.CallGETMethod;

public class SupplierHomeScreenFragment extends Fragment {

    Button createNewBatch_Button;
    static Button viewBatchDetails_Button;
    Context mContext;
    B2BBatchNoManagerInterface callback_b2BBatchIdManagerInterface =null;
    B2BBatchDetailsInterface callback_B2BBatchDetailsInterface = null;
    boolean  isBatchDetailsTableServiceCalled = false;
    static TextView batchNo_textview;
    static TextView batchStatus_textview;
    static TextView batch_createdDate;
    public static LinearLayout lastBatchLayout,loadingpanelmask,loadingPanel;
    String supplierKey ="",supplierName ="" , batchno ="" , createdDate ="", batchStatus ="";
    public static FrameLayout createNewBatchFrame;Fragment mfragment;
    static boolean iscalledFromCreateNewbatch =false;

    public SupplierHomeScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity().getWindow().getContext();

        new NukeSSLCerts();
        NukeSSLCerts.nuke();

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
                return inflater.inflate(R.layout.fragment_supplier_home_screen, container, false);
            } else {

                // Inflate the layout for this fragment
                return inflater.inflate(R.layout.pos_fragment_supplier_home_screen, container, false);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return inflater.inflate(R.layout.fragment_supplier_home_screen, container, false);

        }





    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        createNewBatch_Button = view.findViewById(R.id.createNewBatch_Button);
        viewBatchDetails_Button  = view.findViewById(R.id.viewBatchDetails_Button);
        batchNo_textview = view.findViewById(R.id.batchNo_textview);
        batchStatus_textview  = view.findViewById(R.id.batchStatus_textview);
        batch_createdDate = view.findViewById(R.id.batch_createdDate);
        lastBatchLayout= view.findViewById(R.id.lastBatchLayout);
        loadingpanelmask = view.findViewById(R.id.loadingpanelmask);
        loadingPanel = view.findViewById(R.id.loadingPanel);
        createNewBatchFrame = view.findViewById(R.id.createNewBatchFrame);

        iscalledFromCreateNewbatch = false;

        SharedPreferences sh = mContext.getSharedPreferences("SupplierData",MODE_PRIVATE);
        supplierKey = sh.getString("SupplierKey","");
        supplierName = sh.getString("SupplierName","");

        Log.d("Tag","On Create Fragment");
       /* if(batchno.equals("") || batchno.equals(null) || createdDate.equals("") || createdDate.equals(null) || batchStatus.equals("") || batchStatus.equals(null)){
            showProgressBar(true);
            callandInitBatchIDManager();
        }
        else{
            if(supplierKey.equals("") || supplierKey.equals(null) ){
                showProgressBar(true);
                callandInitBatchDetails(batchno ,CallGETMethod);
            }
        }

        */
        if(!supplierKey.equals("") && supplierKey != null){
            showProgressBar(true);
            callandInitBatchDetails(batchno ,CallGETLastEntryMethod);
        }






        createNewBatch_Button.setOnClickListener(view1 -> {

            if(!String.valueOf(Modal_B2BBatchDetailsStatic.getStatus().toUpperCase()).equals(Constants.batchDetailsStatus_Loading)) {
                try{
                mfragment = new CreateNewBatch();
                loadMyFragment(mfragment, getString(R.string.add_new_item));
                    loadingpanelmask.setVisibility(View.VISIBLE);

                showProgressBar(false);

            } catch (WindowManager.BadTokenException e) {
                showProgressBar(false);

                e.printStackTrace();
            }

                /*Intent intent = new Intent(getActivity(), CreateNew_Or_EditOldBatchScreen.class);
                intent.putExtra(getString(R.string.called_from), getString(R.string.create_new_batch));
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


                startActivity(intent);

                 */
            }
            else {
                AlertDialogClass.showDialog(getActivity(),R.string.CannotCreate_NewBatchAlert);

            }


        });


        viewBatchDetails_Button.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), View_or_Edit_BatchItem_Supplier.class);
            intent.putExtra(getString(R.string.called_from), getString(R.string.edit_existing_batch));
            intent.putExtra("iscalledFromCreateNewBatch",iscalledFromCreateNewbatch);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


            startActivity(intent);

        });


    }





    private void loadMyFragment(Fragment fm, String Value) {
        createNewBatchFrame.setVisibility(View.VISIBLE);
        if (fm != null) {

            try {

                FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null);
                transaction2 .replace(createNewBatchFrame.getId(),  CreateNewBatch.newInstance(getString(R.string.called_from),Value));

                transaction2.commit();


            } catch (Exception e) {
                onResume();
                e.printStackTrace();
            }

            try{

                lastBatchLayout.setVisibility(View.INVISIBLE);

            }
            catch (Exception e){
                e.printStackTrace();
            }


        }
    }





    private void showProgressBar(boolean show) {
        if(show){
            loadingPanel.setVisibility(View.VISIBLE);
            loadingpanelmask.setVisibility(View.VISIBLE);

        }
        else{
            loadingPanel.setVisibility(View.GONE);
            loadingpanelmask.setVisibility(View.GONE);

        }


        try{
            SupplierDashboardScreen.showProgressBar(show);
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }


    private void callandInitBatchDetails(String batchId, String callGetMethod) {

        if (isBatchDetailsTableServiceCalled) {
           showProgressBar(false);
            return;
        }
        isBatchDetailsTableServiceCalled = true;
        callback_B2BBatchDetailsInterface = new B2BBatchDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BBatchDetails> batchDetailsArrayList) {
                showProgressBar(false);
                isBatchDetailsTableServiceCalled = false;
            }

            @Override
            public void notifySuccess(String result) {



                if(result.equals(Constants.emptyResult_volley)){
                    lastBatchLayout.setVisibility(View.INVISIBLE);
                  //  Toast.makeText(mContext, Constants.there_is_noData_volley, Toast.LENGTH_SHORT).show();

                }
                else {
                    lastBatchLayout.setVisibility(View.VISIBLE);
                    batchno = String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno());
                    createdDate = String.valueOf(Modal_B2BBatchDetailsStatic.getCreateddate());
                    batchStatus = String.valueOf(Modal_B2BBatchDetailsStatic.getStatus());

                    batch_createdDate.setText(String.valueOf(Modal_B2BBatchDetailsStatic.getCreateddate()));
                    batchStatus_textview.setText(String.valueOf(Modal_B2BBatchDetailsStatic.getStatus()));
                    batchNo_textview.setText(String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno()));
                    showProgressBar(false);

                }



                showProgressBar(false);
                isBatchDetailsTableServiceCalled = false;

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);

                isBatchDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                showProgressBar(false);

                isBatchDetailsTableServiceCalled = false;
            }




        };

        String toDate = DateParser.getDate_and_time_newFormat();
        String fromDate = DateParser.getDateTextFor_OldDays(30);

      //  String addApiToCall = API_Manager.getBatchDetailsWithSupplierkeyBatchNo+"?supplierkey="+ supplierKey+"&batchno="+batchId ;
         String addApiToCall = API_Manager.getBatchDetailsWithSupplierkeyFromToDate +"?supplierkey="+ supplierKey+"&todate="+toDate+"&fromdate="+fromDate ;

        B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface,  addApiToCall , callGetMethod);
        asyncTask.execute();






    }



    private void callandInitBatchIDManager() {

        callback_b2BBatchIdManagerInterface = new B2BBatchNoManagerInterface() {


            @Override
            public void notifySuccess(String result) {

                batchno = result;
                callandInitBatchDetails(result, CallGETMethod);


            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
                Toast.makeText(mContext, "Volley Error in get BatchNo", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void notifyProcessingError(Exception error) {
                showProgressBar(false);
                Toast.makeText(mContext, "Processing Error in get Batch No", Toast.LENGTH_SHORT).show();

            }



        };


        B2BBatchNoManager.getBatchNo(callback_b2BBatchIdManagerInterface);


    }





    @Override
    public void onResume() {
        super.onResume();
        Log.d("Tag","On Resume Fragment");
        /*
        if(batchno.equals("") || batchno.equals(null) || createdDate.equals("") || createdDate.equals(null) || batchStatus.equals("") || batchStatus.equals(null)){
            showProgressBar(true);
            callandInitBatchIDManager();
        }
        */
        try {
            if (lastBatchLayout.getVisibility() != View.VISIBLE) {
                lastBatchLayout.setVisibility(View.VISIBLE);

            }
                if (!Modal_B2BBatchDetailsStatic.getBatchno().equals("") && !Modal_B2BBatchDetailsStatic.getBatchno().equals(null)) {
                    batchno = String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno());
                    createdDate = String.valueOf(Modal_B2BBatchDetailsStatic.getCreateddate());
                    batchStatus = String.valueOf(Modal_B2BBatchDetailsStatic.getStatus());

                    batch_createdDate.setText(String.valueOf(Modal_B2BBatchDetailsStatic.getCreateddate()));
                    batchStatus_textview.setText(String.valueOf(Modal_B2BBatchDetailsStatic.getStatus()));
                    batchNo_textview.setText(String.valueOf(Modal_B2BBatchDetailsStatic.getBatchno()));
                }

        }
        catch (Exception e){
            e.printStackTrace();
        }


        // showProgressBar(true);
        //callandInitBatchIDManager();
    }
}