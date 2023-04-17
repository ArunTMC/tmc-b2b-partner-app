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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.adapter.Adapter_B2BBatchItemsList;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
* A simple {@link Fragment} subclass.
* Use the {@link DeliveryCenter_StockList_Fragment#newInstance} factory method to
* create an instance of this fragment.
*/
public class DeliveryCenter_StockList_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //widgets
    ListView batches_listview;
    LinearLayout loadingpanelmask,loadingPanel;


    //String
    public String deliveryCenterKey="";
    public String deliveryCenterName ="";
    public String supplierkey="";


    //ArrayList
    public static ArrayList<Modal_B2BBatchDetails> batchDetailsArrayList = new ArrayList<>();


    //boolean
    boolean  isBatchDetailsTableServiceCalled = false;




    //interface
    B2BBatchDetailsInterface callback_B2BBatchDetailsInterface = null;


    //general
    Context mContext;
    public static Adapter_B2BBatchItemsList adapter_b2BBatchItemsList = null;



    public DeliveryCenter_StockList_Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DeliveryCenter_StockList_Fragment newInstance(String param1, String param2) {
        DeliveryCenter_StockList_Fragment fragment = new DeliveryCenter_StockList_Fragment();
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
        return inflater.inflate(R.layout.fragment_delivery_center_stock_list, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        batches_listview = view.findViewById(R.id.batches_listview);
        loadingPanel = view.findViewById(R.id.loadingPanel);
        loadingpanelmask = view.findViewById(R.id.loadingpanelmask);



        SharedPreferences sh = mContext.getSharedPreferences("DeliveryCenterData",MODE_PRIVATE);
        deliveryCenterKey = sh.getString("DeliveryCenterKey","");
        deliveryCenterName = sh.getString("DeliveryCenterName","");



        callandInitBatchDetails("testsupplier_1");



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

            ApiToCall =  API_Manager.getBatchDetailsWithDeliveryCenterAndStatusFromToDate +"?deliverycentrekey="+ deliveryCenterKey+"&supplierkey="+selectedSupplierKey + "&status1="+Constants.batchDetailsStatus_Fully_Loaded +"&status2="+Constants.batchDetailsStatus_Sold +"&status3="+Constants.batchDetailsStatus_Reviewed_and_READYFORSALE +"&fromdate="+fromDate+"&todate="+toDate ;


        B2BBatchDetails asyncTask = new B2BBatchDetails(callback_B2BBatchDetailsInterface,  ApiToCall , Constants.CallGETListMethod);
        asyncTask.execute();






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


    private void setAdapter() {

        adapter_b2BBatchItemsList = new Adapter_B2BBatchItemsList(mContext, batchDetailsArrayList, DeliveryCenter_StockList_Fragment.this);
        batches_listview.setAdapter(adapter_b2BBatchItemsList);

    }



}