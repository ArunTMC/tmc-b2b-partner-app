package com.tmc.tmcb2bpartnerapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.adapter.Adapter_B2BBatchItemsList;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.SupplierDetails;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.SupplierDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_SupplierDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.NukeSSLCerts;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.tmc.tmcb2bpartnerapp.utils.Constants.TAG;


public class DeliveryCenterHomeScreenFragment extends Fragment {

    ListView batches_listview;
    Context mContext;
    LinearLayout loadingpanelmask,loadingPanel;
    Spinner supplierName_spinner;
    TextView batchDetailslabel;
    SupplierDetailsInterface callback_supplierDetailsInterface = null;
    boolean  isSupplierDetailsTableServiceCalled = false;


    ArrayList<Modal_SupplierDetails> supplierDetailsArrayList = new ArrayList<>();
     public static ArrayList<Modal_B2BBatchDetails> batchDetailsArrayList = new ArrayList<>();
    public static Adapter_B2BBatchItemsList adapter_b2BBatchItemsList = null;
    public String deliveryCenterKey="";
    public String deliveryCenterName ="";
    String selectedSupplierKey ="";
    B2BBatchDetailsInterface callback_B2BBatchDetailsInterface = null;
    boolean  isBatchDetailsTableServiceCalled = false;
    String lastlySelectedSupplierKey ="" ,calledFrom ="";



    public DeliveryCenterHomeScreenFragment() {
        // Required empty public constructor
    }


    public static DeliveryCenterHomeScreenFragment newInstance(String key, String value) {
        DeliveryCenterHomeScreenFragment fragment = new DeliveryCenterHomeScreenFragment();
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



        new NukeSSLCerts();
        NukeSSLCerts.nuke();

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
                return inflater.inflate(R.layout.fragment_delivery_center_home_screen, container, false);
            } else {

                // Inflate the layout for this fragment
                return inflater.inflate(R.layout.pos_fragment_delivery_center_home_screen, container, false);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return inflater.inflate(R.layout.fragment_delivery_center_home_screen, container, false);

        }


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            batches_listview = view.findViewById(R.id.batches_listview);
            loadingpanelmask = view.findViewById(R.id.loadingpanelmask);
            loadingPanel = view.findViewById(R.id.loadingPanel);
            supplierName_spinner  = view.findViewById(R.id.supplierName_spinner);
            batchDetailslabel = view.findViewById(R.id.batchDetailslabel);



            SharedPreferences sh = mContext.getSharedPreferences("LastlyCachedData",MODE_PRIVATE);
            lastlySelectedSupplierKey = sh.getString("LastlySelectedSupplierKey","");
            Intialize_and_neutralizeValue();

            if(calledFrom.equals(getString(R.string.home))){
                batchDetailslabel.setText(getString(R.string.batches_on_the_way));
            }
            else  if(calledFrom.equals(getString(R.string.placeOrder))){
                batchDetailslabel.setText(getString(R.string.batches_readyForSale));
            }




            supplierName_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    selectedSupplierKey = getSupplierData(position, "supplierkey");




                    SharedPreferences sharedPreferences
                            = mContext.getSharedPreferences("LastlyCachedData",MODE_PRIVATE);

                    SharedPreferences.Editor myEdit
                            = sharedPreferences.edit();

                    myEdit.putString(
                            "LastlySelectedSupplierKey", selectedSupplierKey
                    );
                    myEdit.apply();


                    callandInitBatchDetails(selectedSupplierKey);
                    isSupplierDetailsTableServiceCalled = false;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });






    }






    private void Intialize_and_neutralizeValue() {
        isSupplierDetailsTableServiceCalled = false;
        supplierDetailsArrayList.clear();
        batchDetailsArrayList.clear();
        deliveryCenterKey="";deliveryCenterName ="" ; selectedSupplierKey ="";
        isBatchDetailsTableServiceCalled = false;
        SharedPreferences sh = mContext.getSharedPreferences("DeliveryCenterData",MODE_PRIVATE);
        deliveryCenterKey = sh.getString("DeliveryCenterKey","");
        deliveryCenterName = sh.getString("DeliveryCenterName","");
        supplierDetailsArrayList.clear();
        if(DatabaseArrayList_PojoClass.supplierDetailsArrayList.size()==0) {
            call_and_init_SupplierDetailsService();
        }
        else{
            supplierDetailsArrayList =DatabaseArrayList_PojoClass.supplierDetailsArrayList ;

            addDataToSupplierDataSpinner();
        }
    }





    private void callandInitBatchDetails(String selectedSupplierKey) {
        showProgressBar(true);
        if (isBatchDetailsTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isBatchDetailsTableServiceCalled = true;
        batchDetailsArrayList.clear();

        callback_B2BBatchDetailsInterface = new B2BBatchDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BBatchDetails> batchDetailsArrayListt) {
                if(batchDetailsArrayListt.size()==0){
                    batches_listview.setVisibility(View.INVISIBLE);
                    Toast.makeText(mContext, Constants.there_is_noData_volley, Toast.LENGTH_SHORT).show();

                }
                else {
                    batches_listview.setVisibility(View.VISIBLE);
                    batchDetailsArrayList  = batchDetailsArrayListt;
                    setAdapter();

                }
                setAdapter();
                showProgressBar(false);
                isBatchDetailsTableServiceCalled = false;
            }

            @Override
            public void notifySuccess(String result) {

                showProgressBar(false);
                isBatchDetailsTableServiceCalled = false;

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                showProgressBar(false);
                setAdapter();
                isBatchDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                showProgressBar(false);
                setAdapter();
                isBatchDetailsTableServiceCalled = false;
            }




        };

        String fromDate = DateParser.getDateTextFor_OldDays(30 );
        String toDate = DateParser.getDate_newFormat();
        fromDate = fromDate + Constants.defaultStartTime;
        toDate = toDate + Constants.defaultEndTime;

        String ApiToCall = "";
        if(calledFrom.equals(getString(R.string.placeOrder))){
            ApiToCall =  API_Manager.getBatchDetailsWithDeliveryCenterAndStatusFromToDate +"?deliverycentrekey="+ deliveryCenterKey+"&supplierkey="+selectedSupplierKey +"&status1="+Constants.batchDetailsStatus_Reviewed_and_READYFORSALE +"&fromdate="+fromDate+"&todate="+toDate ;

        }
        else if(calledFrom.equals(getString(R.string.home))){
            ApiToCall =  API_Manager.getBatchDetailsWithDeliveryCenterAndStatusFromToDate +"?deliverycentrekey="+ deliveryCenterKey+"&supplierkey="+selectedSupplierKey + "&status1="+Constants.batchDetailsStatus_Fully_Loaded +"&status2="+Constants.batchDetailsStatus_Sold +"&status3="+Constants.batchDetailsStatus_Reviewed_and_READYFORSALE +"&fromdate="+fromDate+"&todate="+toDate ;

        }

        B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface,  ApiToCall , Constants.CallGETListMethod);
        asyncTask.execute();






    }

    private void setAdapter() {

         adapter_b2BBatchItemsList = new Adapter_B2BBatchItemsList(mContext, batchDetailsArrayList, DeliveryCenterHomeScreenFragment.this,calledFrom);
        batches_listview.setAdapter(adapter_b2BBatchItemsList);

    }

    private void call_and_init_SupplierDetailsService() {
        showProgressBar(true);


        if (isSupplierDetailsTableServiceCalled) {
            showProgressBar(false);
            return;
        }
        isSupplierDetailsTableServiceCalled = true;
        callback_supplierDetailsInterface = new SupplierDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_SupplierDetails> supplierDetailsArrayListt) {
                supplierDetailsArrayList.clear();
                supplierDetailsArrayList .addAll(supplierDetailsArrayListt);
                showProgressBar(false);
                addDataToSupplierDataSpinner();

            }

            @Override
            public void notifySuccess(String result) {
                showProgressBar(false);

                isSupplierDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
                showProgressBar(false);

                isSupplierDetailsTableServiceCalled = false;
            }

            @Override
            public void notifyProcessingError(Exception error) {
                Log.d(TAG, "Volley JSON post" + error);
                showProgressBar(false);
                isSupplierDetailsTableServiceCalled = false;
            }


        };

        String supplierDetailsApi = API_Manager.getsupplierDetailsList;

        SupplierDetails asyncTask = new SupplierDetails(callback_supplierDetailsInterface,  supplierDetailsApi,Constants.CallGETListMethod);
        asyncTask.execute();


    }

    private void addDataToSupplierDataSpinner() {
        ArrayList<String> supplierCenter_arrayList = new ArrayList<>();
        supplierCenter_arrayList.clear();
      //  supplierCenter_arrayList.add(getString(R.string.selectItem_SpinnerInstruction));
        try{
            for(int i=0; i< supplierDetailsArrayList.size();i++){
                supplierCenter_arrayList.add(supplierDetailsArrayList.get(i).getName());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try {
            BaseActivity.baseActivity.getDeviceName();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            if (BaseActivity.isDeviceIsMobilePhone) {
                // Inflate the layout for this fragment
                ArrayAdapter aAdapter = new ArrayAdapter<String>(mContext, R.layout.simple_spinner_dropdown_item, supplierCenter_arrayList);
                supplierName_spinner.setAdapter(aAdapter);            } else {

                // Inflate the layout for this fragment
                ArrayAdapter aAdapter = new ArrayAdapter<String>(mContext, R.layout.pos_simple_spinner_dropdown_item, supplierCenter_arrayList);
                supplierName_spinner.setAdapter(aAdapter);            }
        }
        catch (Exception e){
            e.printStackTrace();
            ArrayAdapter aAdapter = new ArrayAdapter<String>(mContext, R.layout.simple_spinner_dropdown_item, supplierCenter_arrayList);
            supplierName_spinner.setAdapter(aAdapter);
        }





        for(int iterator = 0 ;iterator<supplierDetailsArrayList.size();iterator++){
           String supplierkeyFromarray =  getSupplierData(iterator, "supplierkey");
           if(supplierkeyFromarray.equals(lastlySelectedSupplierKey)){
               supplierName_spinner.setSelection(iterator);
           }
        }

    }

    private String getSupplierData(int position,String fieldName){
        String data="";
        try {
            Modal_SupplierDetails supplierDetails = DatabaseArrayList_PojoClass.supplierDetailsArrayList.get(position);
            data = supplierDetails.getGet(fieldName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
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


    }
    }