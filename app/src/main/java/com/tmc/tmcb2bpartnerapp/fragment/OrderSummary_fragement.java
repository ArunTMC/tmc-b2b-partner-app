package com.tmc.tmcb2bpartnerapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.adapter.Adapter_GradeWiseTotal_BillingScreen;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BItemCtgy;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.apiRequestServices.GoatEarTagDetails_BulkUpdate;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartItemDetaillsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BGoatGradeDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BItemCtgyInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderItemDetailsInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagDetails_BulkUpdateInterface;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagTransactionInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BItemCtgy;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_POJOClassForFinalSalesHashmap;
import com.tmc.tmcb2bpartnerapp.modal.Modal_Static_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.AlertDialogClass;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.ListItemSizeSetter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static android.os.Build.VERSION.SDK_INT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderSummary_fragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderSummary_fragement extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    LinearLayout back_IconLayout;
    static LinearLayout discountlayout,totalAmountlayout;
    Button proceedCheckOut_button;

    B2BOrderDetailsInterface callback_b2BOrderDetailsInterface ;
    boolean isOrderDetailsServiceCalled = false;

    B2BOrderItemDetailsInterface callback_b2BOrderItemDetailsInterface ;
    boolean isOrderItemDetailsServiceCalled = false;
    
    GoatEarTagDetails_BulkUpdateInterface goatEarTagDetailsBulkUpdateInterface;
    boolean isGoatEarTagDetailsTableServiceCalled = false;

    GoatEarTagTransactionInterface callback_GoatEarTagTransactionInterface = null;
    boolean  isGoatEarTagTransactionTableServiceCalled = false;


    B2BCartOrderDetailsInterface b2BCartOrderDetailsInterface;
    boolean isCartOrderTableServiceCalled = false;



    TextView total_Amount_textview,total_Wt_textview,orderid_textview , invoiceno_textview ,totalItem_CountTextview , totalWeight_textview;
    TextView final_pricePerKg_textview ,total_Price_textview ,total_Discount_textview,finalAmount_textview;
    TextView male_Qty_textview ,female_Qty_textview ,femaleWithBaby_Qty_textview,total_Qty_textview;
    TextView male_Wt_textview ,female_Wt_textview ,femaleWithBaby_Wt_textview ,retailername_textview;

    Context mContext ;
    static DecimalFormat threeDecimalConverter = new DecimalFormat(Constants.threeDecimalPattern);
    static DecimalFormat twoDecimalConverter = new DecimalFormat(Constants.twoDecimalPattern);




    String totalCount = "" , batchno ="" ,deliveryCenterName ="" , deliveryCenterKey ="",invoiceno ="" ,orderid ="" ,usermobileno_string ="";
    String totalWeight ="" , pricePerKg  = "" , discountAmount = "", totalPrice =  "" , itemCtgyKey ="" , orderplaceddate ="";

    static double  weight_double =0 ,totalWeight_double = 0 , pricePerKg_double = 0 , discountAmount_double = 0 , totalPrice_double = 0  ,gradeprice_double =0;
    static double totalPrice_doubleWithoutDiscount = 0;

    static int male_Count =0;
    static int femaleCount =0 , total_Count =0;
    static int femaleWithBabyCount = 0;

    static  double male_weight =0 , female_weight =0 , femaleWithBabyWeight =0;


    boolean isB2BItemCtgyTableServiceCalled = false;
    B2BItemCtgyInterface callback_B2BItemCtgyInterface;

    boolean isB2BCartDetailsCalled = false;
    B2BCartItemDetaillsInterface callback_b2BCartItemDetaillsInterface = null;
    B2BGoatGradeDetailsInterface callback_goatGradeDetailsInterface = null;
    boolean isGoatGradeDetailsServiceCalled = false;


    public static ArrayList<String> earTagDetailsArrayList_String = new ArrayList<>();
    public static HashMap<String,Modal_GoatEarTagDetails> earTagDetailsHashMap = new HashMap<>();

    public static ArrayList<Modal_B2BItemCtgy> ctgy_subCtgy_DetailsArrayList = new ArrayList<>();
    public static HashMap<String, JSONObject> earTagDetails_weightStringHashMap = new HashMap<>();

    public static ArrayList<Modal_B2BGoatGradeDetails> selected_gradeDetailss_arrayList = new ArrayList<>();
    //static JSONObject gradeWise_count_weightJSONOBJECT = new JSONObject();

    public static HashMap<String, Modal_POJOClassForFinalSalesHashmap> earTagDetails_JSONFinalSalesHashMap = new HashMap<>();



    static ArrayList<Modal_B2BGoatGradeDetails> goatGrade_arrayLsit = new ArrayList<>();

    public static Adapter_GradeWiseTotal_BillingScreen adapter_gradeWiseTotal_billingScreen ;

    public static ListView gradewisetotalCount_listview;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderSummary_fragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderSummary_fragement.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderSummary_fragement newInstance(String param1, String param2) {
        OrderSummary_fragement fragment = new OrderSummary_fragement();
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
                return inflater.inflate(R.layout.fragment_order_summary_fragement, container, false);
            } else {

                // Inflate the layout for this fragment
                return inflater.inflate(R.layout.pos_fragment_order_summary_fragement, container, false);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return inflater.inflate(R.layout.fragment_order_summary_fragement, container, false);

        }


        // Inflate the layout for this fragment

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        back_IconLayout = view.findViewById(R.id.back_IconLayout);

        proceedCheckOut_button = view.findViewById(R.id.proceedCheckOut_button);

        final_pricePerKg_textview = view.findViewById(R.id.final_pricePerKg_textview);
        total_Price_textview  = view.findViewById(R.id.total_Price_textview);
        total_Discount_textview  = view.findViewById(R.id.total_Discount_textview);
        finalAmount_textview  = view.findViewById(R.id.finalAmount_textview);

        male_Qty_textview = view.findViewById(R.id.male_Qty_textview);
        female_Qty_textview = view.findViewById(R.id.female_Qty_textview);
        femaleWithBaby_Qty_textview = view.findViewById(R.id.femaleWithBaby_Qty_textview);
        total_Qty_textview = view.findViewById(R.id.total_Qty_textview);

        male_Wt_textview = view.findViewById(R.id.male_Wt_textview);
        female_Wt_textview = view.findViewById(R.id.female_Wt_textview);
        femaleWithBaby_Wt_textview = view.findViewById(R.id.femaleWithBaby_Wt_textview);
        total_Wt_textview  = view.findViewById(R.id.total_Wt_textview);
        orderid_textview = view.findViewById(R.id.orderid_textview);
        invoiceno_textview  = view.findViewById(R.id.invoiceno_textview);
        totalItem_CountTextview  = view.findViewById(R.id.totalItem_CountTextview);
        totalWeight_textview  = view.findViewById(R.id.totalWeight_textview);
        discountlayout = view.findViewById(R.id.discountlayout);
        retailername_textview = view.findViewById(R.id.retailername_textview);
        total_Amount_textview  = view.findViewById(R.id.total_Amount_textview);
        totalAmountlayout  = view.findViewById(R.id.totalAmountlayout);



        gradewisetotalCount_listview  = view.findViewById(R.id.gradewisetotalCount_listview);
        SharedPreferences sh1 = getActivity().getSharedPreferences("DeliveryCenterData", MODE_PRIVATE);

        deliveryCenterKey = sh1.getString("DeliveryCenterKey", "");
        deliveryCenterName = sh1.getString("DeliveryCenterName", "");

        SharedPreferences sh = getActivity().getSharedPreferences("LoginData",MODE_PRIVATE);
        usermobileno_string = sh.getString("UserMobileNumber","");
      //  gradeWise_count_weightJSONOBJECT = new JSONObject();
        earTagDetails_JSONFinalSalesHashMap = new HashMap<>();
        //Initialize_and_ExecuteB2BCtgyItem();
      /*  orderid_textview.setText( String.valueOf(  BillingScreen. orderid ));
        retailername_textview.setText(String.valueOf(BillingScreen.retailername));
        invoiceno_textview.setText("INV - "+ String.valueOf(  BillingScreen. invoiceno ));
        BillingScreen.isorderSummary_checkoutClicked = false;
        BillingScreen.showProgressBar(true);

       */
        orderid_textview.setText( String.valueOf(  DeliveryCentre_PlaceOrderScreen_Fragment. orderid ));
        retailername_textview.setText(String.valueOf(DeliveryCentre_PlaceOrderScreen_Fragment.retailername));
        invoiceno_textview.setText("INV - "+ String.valueOf(  DeliveryCentre_PlaceOrderScreen_Fragment. invoiceno ));
        DeliveryCentre_PlaceOrderScreen_Fragment.isorderSummary_checkoutClicked = false;
        DeliveryCentre_PlaceOrderScreen_Fragment.showProgressBar(true);
        if (DatabaseArrayList_PojoClass.goatGradeDetailsArrayList.size() == 0) {
            try {
                //Log.i("INIT", "call_and_init_B2BCartItemDetailsService in Call_and_Initialize_GoatGradeDetails" );

                Call_and_Initialize_GoatGradeDetails(Constants.CallGETListMethod);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            goatGrade_arrayLsit = DatabaseArrayList_PojoClass.getGoatGradeDetailsArrayList();
            call_and_init_B2BCartItemDetailsService(Constants.CallGETListMethod);

            //Log.i("INIT", "call_and_init_B2BCartItemDetailsService in Call_and_Initialize_GoatGradeDetails goatGrade_arrayLsit" +String.valueOf(goatGrade_arrayLsit.size()) );

        }
        if(DatabaseArrayList_PojoClass.breedType_arrayList.size() == 0){
            try {
                Initialize_and_ExecuteB2BCtgyItem();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            ctgy_subCtgy_DetailsArrayList = DatabaseArrayList_PojoClass.breedType_arrayList;
        }








        back_IconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.closeFragment();
            }
        });

        proceedCheckOut_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    DeliveryCentre_PlaceOrderScreen_Fragment.showProgressBar(true);
                    orderplaceddate  = DateParser.getDate_and_time_newFormat() ;
                    orderid = String.valueOf(  DeliveryCentre_PlaceOrderScreen_Fragment. orderid );
                    batchno = String.valueOf(  DeliveryCentre_PlaceOrderScreen_Fragment. batchno );
                    invoiceno = String.valueOf(  DeliveryCentre_PlaceOrderScreen_Fragment. invoiceno );
                      call_and_init_B2BOrderItemDetailsService(Constants.CallADDMethod);
                    call_and_init_B2BOrderDetailsService(Constants.CallADDMethod);
                    call_and_init_GoatEarTagDetails_BulkUpdate(Constants.CallUPDATEMethod);
                    call_and_init_B2BCartOrderDetailsService(Constants.CallDELETEMethod);



                    DeliveryCentre_PlaceOrderScreen_Fragment.isorderSummary_checkoutClicked = true;
                  //  DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.Create_and_SharePdf();
                    UpdateDataInSharedPreference();

                 //   ((BillingScreen) getActivity()).Create_and_SharePdf();

                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });



       }

    private void call_and_init_B2BCartOrderDetailsService(String callUPDATEMethod) {

        try {

            if (isCartOrderTableServiceCalled) {

                return;
            }
            isCartOrderTableServiceCalled = true;
            b2BCartOrderDetailsInterface = new B2BCartOrderDetailsInterface() {


                @Override
                public void notifySuccess(String result) {
                    isCartOrderTableServiceCalled = false;
                    //((BillingScreen) getActivity()).Create_and_SharePdf();

                }

                @Override
                public void notifySuccessForGettingListItem(ArrayList<Modal_B2BCartOrderDetails> earTagItemsForBatch) {
                    isCartOrderTableServiceCalled = false;
                   // ((BillingScreen) getActivity()).neutralizeArray_and_OtherValues();
                    //((BillingScreen) getActivity()).closeFragment();
                }

                @Override
                public void notifyVolleyError(VolleyError error) {
                    //((BillingScreen) getActivity()).neutralizeArray_and_OtherValues();
                    // Toast.makeText(mContext, "There is an volley error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                   // ((BillingScreen) getActivity()).closeFragment();

                    isCartOrderTableServiceCalled = false;
                }

                @Override
                public void notifyProcessingError(Exception error) {
                    Toast.makeText(mContext, "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                   // ((BillingScreen) getActivity()).neutralizeArray_and_OtherValues();
                   // ((BillingScreen) getActivity()).closeFragment();
                    isCartOrderTableServiceCalled = false;


                }

            };



            try {

                String addApiToCall = API_Manager.deleteCartOrderDetails+"?orderid="+orderid+"&deliverycentrekey="+deliveryCenterKey;
                B2BCartOrderDetails asyncTask = new B2BCartOrderDetails(b2BCartOrderDetailsInterface, addApiToCall, Constants.CallDELETEMethod);
                asyncTask.execute();
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
                //Log.i("INIT", "call_and_init_B2BCartItemDetailsService in goatGrade_arrayLsit " + String.valueOf(goatGrade_arrayLsit.size()));
                call_and_init_B2BCartItemDetailsService(Constants.CallGETListMethod);

            }

            @Override
            public void notifySuccess(String key) {

                isGoatGradeDetailsServiceCalled = false;
                //Log.i("INIT", "call_and_init_B2BCartItemDetailsService in goatGrade_arrayLsit " + String.valueOf("success"));

            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isGoatGradeDetailsServiceCalled = false;
                //    //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 3 " + DateParser.getDate_and_time_newFormat());
                //Log.i("INIT", "call_and_init_B2BCartItemDetailsService in goatGrade_arrayLsit error" + String.valueOf(error));
            }

            @Override
            public void notifyProcessingError(Exception error) {
                isGoatGradeDetailsServiceCalled = false;
                ////Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());
                //Log.i("INIT", "call_and_init_B2BCartItemDetailsService in goatGrade_arrayLsit pr error" + String.valueOf(error));

            }


        };


        if(ApiMethod.equals(Constants.CallGETListMethod)){
            goatGrade_arrayLsit.clear();
            //Log.i("INIT", "call_and_init_B2BCartItemDetailsService in goatGrade_arrayLsit deliveryCenterKey  " + String.valueOf(deliveryCenterKey));

            String getApiToCall = API_Manager.getgoatGradeForDeliveryCentreKey +deliveryCenterKey;

            B2BGoatGradeDetails asyncTask = new B2BGoatGradeDetails(callback_goatGradeDetailsInterface,  getApiToCall, Constants.CallGETListMethod);
            asyncTask.execute();



        }



    }




    private void call_and_init_B2BCartItemDetailsService(String callMethod) {
        DeliveryCentre_PlaceOrderScreen_Fragment.showProgressBar(true);

        //Log.i("INIT", "call_and_init_B2BCartItemDetailsService : called " + DateParser.getDate_and_time_newFormat());



        if (isB2BCartDetailsCalled) {
            Toast.makeText(mContext, "B2BCartItemDetail called again ", Toast.LENGTH_SHORT).show();
            return;
        }
        isB2BCartDetailsCalled = true;
        earTagDetailsArrayList_String.clear();
        earTagDetailsHashMap.clear();
        earTagDetails_weightStringHashMap.clear();
        earTagDetails_JSONFinalSalesHashMap.clear();
       // gradeWise_count_weightJSONOBJECT = new JSONObject();
        selected_gradeDetailss_arrayList.clear();
        callback_b2BCartItemDetaillsInterface = new B2BCartItemDetaillsInterface()
        {

            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BCartItemDetails> arrayList) {
                try {
                    //Log.i("INIT", "call_and_init_B2BCartItemDetailsService : on success " + DateParser.getDate_and_time_newFormat());
                    //Log.i("INIT", "call_and_init_B2BCartItemDetailsService : on success array   " + String.valueOf(arrayList.size()));

                    if (arrayList.size() <= 0) {
                        AlertDialogClass.showDialog(getActivity(), R.string.CartItemListNotFound_Instruction);

                        return;
                    }
                    DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.   add_And_UpdateChangesInBarcodeItemsList(arrayList);

                    for (int iterator = 0; iterator < arrayList.size(); iterator++) {



                        Modal_B2BCartItemDetails modal_b2BCartDetails = arrayList.get(iterator);
                        //Log.i("INIT", "call_and_init_B2BCartItemDetailsService : on iteartor " + modal_b2BCartDetails.getBarcodeno());

                 /*   Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();
                    modal_goatEarTagDetails.barcodeno = modal_b2BCartDetails.getBarcodeno();
                    modal_goatEarTagDetails.batchno = modal_b2BCartDetails.getBatchno();
                    modal_goatEarTagDetails.status = modal_b2BCartDetails.getStatus();
                    modal_goatEarTagDetails.itemaddeddate = modal_b2BCartDetails.getItemaddeddate();
                    modal_goatEarTagDetails.currentweightingrams = modal_b2BCartDetails.getWeightingrams();
                    modal_goatEarTagDetails.gender = modal_b2BCartDetails.getGender();
                    modal_goatEarTagDetails.breedtype = modal_b2BCartDetails.getBreedtype();
                    modal_goatEarTagDetails.status = Constants.goatEarTagStatus_Reviewed_and_READYFORSALE;
                    modal_goatEarTagDetails.stockedweightingrams = modal_b2BCartDetails.getWeightingrams();
                    modal_goatEarTagDetails.loadedweightingrams = modal_b2BCartDetails.getWeightingrams();
                    modal_goatEarTagDetails.newWeight_forBillingScreen = modal_b2BCartDetails.getWeightingrams();
                    modal_goatEarTagDetails.b2bctgykey = modal_b2BCartDetails.getB2bctgykey();
                    modal_goatEarTagDetails.b2bsubctgykey = modal_b2BCartDetails.getB2bsubctgykey();
                    modal_goatEarTagDetails.orderid_forBillingScreen = modal_b2BCartDetails.getOrderid();
                    earTagDetailsHashMap.put(modal_b2BCartDetails.getBarcodeno(),modal_goatEarTagDetails);
                    earTagDetailsArrayList_String.add(modal_b2BCartDetails.getBarcodeno());


                  */
                        String ctgykey = "", ctgyname = "", subctgykey = "", suctgyname = "";
                        //gradeWise_count_weightJSONOBJECT = new JSONObject();

                        for(int iterator2 = 0; iterator2 < ctgy_subCtgy_DetailsArrayList.size(); iterator2++){
                            if(ctgy_subCtgy_DetailsArrayList.get(iterator2).getSubctgy_name().toUpperCase() .equals(modal_b2BCartDetails.getBreedtype().toUpperCase())){

                                ctgykey = ctgy_subCtgy_DetailsArrayList.get(iterator2).getKey();
                                ctgyname = ctgy_subCtgy_DetailsArrayList.get(iterator2).getName();
                                subctgykey = ctgy_subCtgy_DetailsArrayList.get(iterator2).getSubctgy_key();
                                suctgyname = ctgy_subCtgy_DetailsArrayList.get(iterator2).getSubctgy_name();

                            }
                        }

                        try {
                            if (earTagDetailsArrayList_String.contains(modal_b2BCartDetails.getBarcodeno())) {

                                calculateGradewiseQuantity_and_Weight(modal_b2BCartDetails);
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("weight", modal_b2BCartDetails.getWeightingrams());
                                    jsonObject.put("gradekey", modal_b2BCartDetails.getGradekey());
                                    jsonObject.put("gender", modal_b2BCartDetails.getGender());
                                    jsonObject.put("gradeprice", modal_b2BCartDetails.getGradeprice());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                if (earTagDetailsHashMap.containsKey(modal_b2BCartDetails.getBarcodeno())) {
                                    Objects.requireNonNull(earTagDetailsHashMap.get(modal_b2BCartDetails.getBarcodeno())).setB2bctgykey(ctgykey);
                                    Objects.requireNonNull(earTagDetailsHashMap.get(modal_b2BCartDetails.getBarcodeno())).setB2bsubctgykey(subctgykey);
                                    Objects.requireNonNull(earTagDetailsHashMap.get(modal_b2BCartDetails.getBarcodeno())).setNewWeight_forBillingScreen(modal_b2BCartDetails.getWeightingrams());

                                    Objects.requireNonNull(earTagDetailsHashMap.get(modal_b2BCartDetails.getBarcodeno())).setNewWeight_forBillingScreen(modal_b2BCartDetails.getWeightingrams());
                                    // adapter_billingScreen_cartList.notifyDataSetChanged();
                                    calculateTotalweight_Quantity_Price();
                                }


                                if (earTagDetails_weightStringHashMap.containsKey(modal_b2BCartDetails.getBarcodeno())) {
                                    if (SDK_INT >= Build.VERSION_CODES.N) {
                                        Objects.requireNonNull(earTagDetails_weightStringHashMap.replace(modal_b2BCartDetails.getBarcodeno(), jsonObject));
                                    } else {
                                        Objects.requireNonNull(earTagDetails_weightStringHashMap.put(modal_b2BCartDetails.getBarcodeno(), jsonObject));
                                    }

                                    // adapter_billingScreen_cartList.notifyDataSetChanged();
                                    calculateTotalweight_Quantity_Price();
                                } else {
                                    Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();

                                    modal_goatEarTagDetails.barcodeno = modal_b2BCartDetails.getBarcodeno();
                                    modal_goatEarTagDetails.batchno = modal_b2BCartDetails.getBatchno();
                                    modal_goatEarTagDetails.status = modal_b2BCartDetails.getStatus();
                                    modal_goatEarTagDetails.itemaddeddate = modal_b2BCartDetails.getItemaddeddate();
                                    modal_goatEarTagDetails.stockedweightingrams = modal_b2BCartDetails.getWeightingrams();
                                    modal_goatEarTagDetails.selecteditem = Modal_Static_GoatEarTagDetails.getSelecteditem();
                                    modal_goatEarTagDetails.gender = modal_b2BCartDetails.getGender();
                                    modal_goatEarTagDetails.breedtype = modal_b2BCartDetails.getBreedtype();
                                    modal_goatEarTagDetails.loadedweightingrams = modal_b2BCartDetails.getWeightingrams();
                                    modal_goatEarTagDetails.currentweightingrams = modal_b2BCartDetails.getOldweightingrams();
                                    modal_goatEarTagDetails.newWeight_forBillingScreen = modal_b2BCartDetails.getWeightingrams();
                                    modal_goatEarTagDetails.b2bsubctgykey = subctgykey;
                                    modal_goatEarTagDetails.b2bctgykey = ctgykey;
                                    try {
                                        for (int iterator1 = 0; iterator1 < goatGrade_arrayLsit.size(); iterator1++) {
                                            if (String.valueOf(modal_b2BCartDetails.getGradekey()).equals(goatGrade_arrayLsit.get(iterator1).getKey())) {
                                                modal_goatEarTagDetails.gradeprice = goatGrade_arrayLsit.get(iterator1).getPrice();
                                                modal_goatEarTagDetails.gradename  = goatGrade_arrayLsit.get(iterator1).getName();
                                                modal_goatEarTagDetails.gradekey   = goatGrade_arrayLsit.get(iterator1).getKey();

                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    earTagDetails_weightStringHashMap.put(modal_b2BCartDetails.getBarcodeno(), jsonObject);
                                    earTagDetailsHashMap.put(modal_b2BCartDetails.getBarcodeno(), modal_goatEarTagDetails);
                                    //adapter_billingScreen_cartList.notifyDataSetChanged();
                                    calculateTotalweight_Quantity_Price();

                                }
                            } else {
                                calculateGradewiseQuantity_and_Weight(modal_b2BCartDetails);
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("weight", modal_b2BCartDetails.getWeightingrams());
                                    jsonObject.put("gradekey", modal_b2BCartDetails.getGradekey());
                                    jsonObject.put("gender", modal_b2BCartDetails.getGender());
                                    jsonObject.put("gradeprice", modal_b2BCartDetails.getGradeprice());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                if (earTagDetails_weightStringHashMap.containsKey(modal_b2BCartDetails.getBarcodeno())) {
                                    if (SDK_INT >= Build.VERSION_CODES.N) {
                                        Objects.requireNonNull(earTagDetails_weightStringHashMap.replace(modal_b2BCartDetails.getBarcodeno(), jsonObject));
                                    } else {
                                        Objects.requireNonNull(earTagDetails_weightStringHashMap.put(modal_b2BCartDetails.getBarcodeno(), jsonObject));
                                    }
                                    earTagDetailsArrayList_String.add(modal_b2BCartDetails.getBarcodeno());
                                    //  adapter_billingScreen_cartList.notifyDataSetChanged();
                                    calculateTotalweight_Quantity_Price();
                                }


                                if (earTagDetailsHashMap.containsKey(modal_b2BCartDetails.getBarcodeno())) {
                                    Objects.requireNonNull(earTagDetailsHashMap.get(modal_b2BCartDetails.getBarcodeno())).setNewWeight_forBillingScreen(modal_b2BCartDetails.getWeightingrams());
                                    earTagDetailsArrayList_String.add(modal_b2BCartDetails.getBarcodeno());
                                    //  adapter_billingScreen_cartList.notifyDataSetChanged();
                                    calculateTotalweight_Quantity_Price();
                                } else {
                                    Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();

                                    modal_goatEarTagDetails.barcodeno = modal_b2BCartDetails.getBarcodeno();
                                    modal_goatEarTagDetails.batchno = modal_b2BCartDetails.getBatchno();
                                    modal_goatEarTagDetails.status = modal_b2BCartDetails.getStatus();
                                    modal_goatEarTagDetails.itemaddeddate = modal_b2BCartDetails.getItemaddeddate();
                                    modal_goatEarTagDetails.stockedweightingrams = modal_b2BCartDetails.getWeightingrams();
                                    modal_goatEarTagDetails.selecteditem = Modal_Static_GoatEarTagDetails.getSelecteditem();
                                    modal_goatEarTagDetails.gender = modal_b2BCartDetails.getGender();
                                    modal_goatEarTagDetails.breedtype = modal_b2BCartDetails.getBreedtype();
                                    modal_goatEarTagDetails.loadedweightingrams = String.valueOf(modal_b2BCartDetails.getWeightingrams());
                                    modal_goatEarTagDetails.currentweightingrams = String.valueOf(modal_b2BCartDetails.getOldweightingrams());
                                    modal_goatEarTagDetails.newWeight_forBillingScreen = modal_b2BCartDetails.getWeightingrams();
                                    modal_goatEarTagDetails.b2bsubctgykey = subctgykey;
                                    modal_goatEarTagDetails.b2bctgykey = ctgykey;
                                    try {
                                        for (int iterator1 = 0; iterator1 < goatGrade_arrayLsit.size(); iterator1++) {
                                            if (String.valueOf(modal_b2BCartDetails.getGradekey()).equals(goatGrade_arrayLsit.get(iterator1).getKey())) {
                                                modal_goatEarTagDetails.gradeprice = goatGrade_arrayLsit.get(iterator1).getPrice();
                                                modal_goatEarTagDetails.gradename  = goatGrade_arrayLsit.get(iterator1).getName();
                                                modal_goatEarTagDetails.gradekey   = goatGrade_arrayLsit.get(iterator1).getKey();

                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    earTagDetailsArrayList_String.add(modal_b2BCartDetails.getBarcodeno());
                                    earTagDetails_weightStringHashMap.put(modal_b2BCartDetails.getBarcodeno(), jsonObject);

                                    earTagDetailsHashMap.put(modal_b2BCartDetails.getBarcodeno(), modal_goatEarTagDetails);
                                    // adapter_billingScreen_cartList.notifyDataSetChanged();
                                    calculateTotalweight_Quantity_Price();

                                }
                            }
                        }
                        catch (Exception e){
                            //Log.i("INIT", "call_and_init_B2BCartItemDetailsService :error before calling set adapter " + String.valueOf(e));

                            e.printStackTrace();
                        }

                        if (iterator == (arrayList.size() - 1)) {

                            //Log.i("INIT", "call_and_init_B2BCartItemDetailsService : calling set adapter " + DateParser.getDate_and_time_newFormat());
                            //Log.i("INIT", "call_and_init_B2BCartItemDetailsService : calling set adapter " + DateParser.getDate_and_time_newFormat());

                            setAdapterForGradewiseTotal();
                        }
                    }
                }
                catch (Exception e){
                    Toast.makeText(mContext, "B2BCartItemDetail Exception  :   " +String.valueOf(e), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                /*
                without grade wise price report
                for (int iterator = 0; iterator < arrayList.size(); iterator++) {
                    Modal_B2BCartItemDetails modal_b2BCartDetails = arrayList.get(iterator);




                    if(earTagDetailsArrayList_String.contains(modal_b2BCartDetails.getBarcodeno())){

                        if(earTagDetailsHashMap.containsKey(modal_b2BCartDetails.getBarcodeno())) {
                            Objects.requireNonNull(earTagDetailsHashMap.get(modal_b2BCartDetails.getBarcodeno())).setNewWeight_forBillingScreen( modal_b2BCartDetails.getWeightingrams());

                            Objects.requireNonNull(earTagDetailsHashMap.get(modal_b2BCartDetails.getBarcodeno())).setNewWeight_forBillingScreen( modal_b2BCartDetails.getWeightingrams());



                        }
                        else{
                            Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();

                            modal_goatEarTagDetails.barcodeno = modal_b2BCartDetails.getBarcodeno();
                            modal_goatEarTagDetails.batchno = modal_b2BCartDetails.getBatchno();
                            modal_goatEarTagDetails.status = modal_b2BCartDetails.getStatus();
                            modal_goatEarTagDetails.itemaddeddate = modal_b2BCartDetails.getItemaddeddate();
                            modal_goatEarTagDetails.stockedweightingrams = modal_b2BCartDetails.getWeightingrams();
                            modal_goatEarTagDetails.selecteditem = Modal_Static_GoatEarTagDetails.getSelecteditem();
                            modal_goatEarTagDetails.gender = modal_b2BCartDetails.getGender();
                            modal_goatEarTagDetails.breedtype = modal_b2BCartDetails.getBreedtype();
                            modal_goatEarTagDetails.loadedweightingrams = modal_b2BCartDetails.getWeightingrams();
                            modal_goatEarTagDetails.currentweightingrams = modal_b2BCartDetails.getOldweightingrams();
                            modal_goatEarTagDetails.newWeight_forBillingScreen = modal_b2BCartDetails.getWeightingrams();
                            modal_goatEarTagDetails.b2bsubctgykey = modal_b2BCartDetails.getB2bsubctgykey();
                            modal_goatEarTagDetails.b2bctgykey = modal_b2BCartDetails.getB2bctgykey();
                            modal_goatEarTagDetails.gradekey = modal_b2BCartDetails.getGradekey();
                            modal_goatEarTagDetails.gradename = modal_b2BCartDetails.getGradename();
                            modal_b2BCartDetails.gradeprice = modal_b2BCartDetails.getGradeprice();
                            earTagDetailsHashMap.put(modal_b2BCartDetails.getBarcodeno(),modal_goatEarTagDetails);
                            //adapter_billingScreen_cartList.notifyDataSetChanged();


                        }
                    }
                    else{

                        if(earTagDetailsHashMap.containsKey(modal_b2BCartDetails.getBarcodeno())) {
                            Objects.requireNonNull(earTagDetailsHashMap.get(modal_b2BCartDetails.getBarcodeno())).setNewWeight_forBillingScreen(modal_b2BCartDetails.getWeightingrams());
                            earTagDetailsArrayList_String.add(modal_b2BCartDetails.getBarcodeno());
                          //  adapter_billingScreen_cartList.notifyDataSetChanged();

                        }


                        else{
                            Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();

                            modal_goatEarTagDetails.barcodeno = modal_b2BCartDetails.getBarcodeno();
                            modal_goatEarTagDetails.batchno = modal_b2BCartDetails.getBatchno();
                            modal_goatEarTagDetails.status = modal_b2BCartDetails.getStatus();
                            modal_goatEarTagDetails.itemaddeddate = modal_b2BCartDetails.getItemaddeddate();
                            modal_goatEarTagDetails.stockedweightingrams = modal_b2BCartDetails.getWeightingrams();
                            modal_goatEarTagDetails.selecteditem = Modal_Static_GoatEarTagDetails.getSelecteditem();
                            modal_goatEarTagDetails.gender = modal_b2BCartDetails.getGender();
                            modal_goatEarTagDetails.breedtype = modal_b2BCartDetails.getBreedtype();
                            modal_goatEarTagDetails.loadedweightingrams =  String.valueOf(modal_b2BCartDetails.getWeightingrams());
                            modal_goatEarTagDetails.currentweightingrams = String.valueOf(modal_b2BCartDetails.getOldweightingrams());
                            modal_goatEarTagDetails.newWeight_forBillingScreen = modal_b2BCartDetails.getWeightingrams();
                            modal_goatEarTagDetails.b2bsubctgykey = modal_b2BCartDetails.getB2bsubctgykey();
                            modal_goatEarTagDetails.b2bctgykey = modal_b2BCartDetails.getB2bctgykey();
                            modal_goatEarTagDetails.gradekey = modal_b2BCartDetails.getGradekey();
                            modal_goatEarTagDetails.gradename = modal_b2BCartDetails.getGradename();
                            modal_b2BCartDetails.gradeprice = modal_b2BCartDetails.getGradeprice();
                            earTagDetailsArrayList_String.add(modal_b2BCartDetails.getBarcodeno());

                            earTagDetailsHashMap.put(modal_b2BCartDetails.getBarcodeno(),modal_goatEarTagDetails);
                            // adapter_billingScreen_cartList.notifyDataSetChanged();


                        }
                    }





                    if(iterator == (arrayList.size() -1)){


                        calculateTotalweight_Quantity_Price();
                    }
                }

                */
            }

            @Override
            public void notifySuccess(String result) {
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 2 " + DateParser.getDate_and_time_newFormat());
                DeliveryCentre_PlaceOrderScreen_Fragment.showProgressBar(false);
                isB2BCartDetailsCalled = false;
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isB2BCartDetailsCalled = false;
                DeliveryCentre_PlaceOrderScreen_Fragment.showProgressBar(false);
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 3 " + DateParser.getDate_and_time_newFormat());

            }

            @Override
            public void notifyProcessingError(Exception error) {
                isB2BCartDetailsCalled = false;
                DeliveryCentre_PlaceOrderScreen_Fragment.showProgressBar(false);
                //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());

            }


        };

        String getApiToCall = API_Manager.getCartItemDetailsForOrderid + DeliveryCentre_PlaceOrderScreen_Fragment. orderid  ;
        B2BCartItemDetails asyncTask = new B2BCartItemDetails(callback_b2BCartItemDetaillsInterface,  getApiToCall, callMethod);
        asyncTask.execute();



    }
    public  void setAdapterForGradewiseTotal() {
        try {
            //Log.i("INIT", "call_and_init_B2BCartItemDetailsService : on set Addapter " + DateParser.getDate_and_time_newFormat());

            adapter_gradeWiseTotal_billingScreen = new Adapter_GradeWiseTotal_BillingScreen(mContext, selected_gradeDetailss_arrayList, earTagDetails_JSONFinalSalesHashMap, "",this);
            gradewisetotalCount_listview.setAdapter(adapter_gradeWiseTotal_billingScreen);

            ListItemSizeSetter.getListViewSize(gradewisetotalCount_listview);
            DeliveryCentre_PlaceOrderScreen_Fragment.showProgressBar(false);
        }
        catch (Exception e){
            //Log.i("INIT", "call_and_init_B2BCartItemDetailsService : error on set adapter " + DateParser.getDate_and_time_newFormat());

            e.printStackTrace();
        }
   //     Toast.makeText(mContext, " json size  : " +String.valueOf(gradeWise_count_weightJSONOBJECT.length()), Toast.LENGTH_SHORT).show();
     //   Toast.makeText(mContext, " array size  : " +String.valueOf(selected_gradeDetailss_arrayList.size()), Toast.LENGTH_SHORT).show();
    }




    public static void calculateGradewiseQuantity_and_Weight(Modal_B2BCartItemDetails modal_b2BCartDetails )
    {


        //Log.i("INIT", "call_and_init_B2BCartItemDetailsService in calculateGradewiseQuantity_and_Weight " + String.valueOf(modal_b2BCartDetails.getBarcodeno()));
        //Log.i("INIT", "call_and_init_B2BCartItemDetailsService in calculateGradewiseQua goatGrade_arrayLsit" + String.valueOf(goatGrade_arrayLsit.size()));



        for(int iterator = 0 ; iterator< goatGrade_arrayLsit.size(); iterator ++ ){
            Modal_B2BGoatGradeDetails modal_b2BGoatGradeDetails = goatGrade_arrayLsit.get(iterator);

            String maleQty = "0" , femaleQty ="0" , maleWeight = "0" , femaleWeight ="0" , totalWeight ="0" , totalCount ="0" ,malePrice ="0" , femalePrice ="0" , toalPrice = "";
            double maleQtydouble = 0 , femaleQtydouble = 0 , maleWeightdouble = 0 , femaleWeightdouble = 0 , totalWeightdouble = 0 , totalCountdouble = 0 ,malePricedouble = 0 ,
                    femalePricedouble = 0 , toalPricedouble = 0,oldWeight_inGramsdouble = 0, oldPrice_inGramsdouble =0;
            int totalCountint =0;
            //Log.i("INIT", "call_and_init_B2BCartItemDetailsService in loop calculateGradewiseQua modal_b2BGoatGradeDetails" + String.valueOf(modal_b2BGoatGradeDetails.getKey()));
            //Log.i("INIT", "call_and_init_B2BCartItemDetailsService in loop calculateGradewiseQua modal_b2BCartDetails" + String.valueOf(modal_b2BCartDetails.getGradekey()));

            if(modal_b2BGoatGradeDetails.getKey().equals(modal_b2BCartDetails.getGradekey())) {
                String weightString = "0"; double weightDouble = 0; double price_for_this_entry =0;
                weightString = modal_b2BCartDetails.getWeightingrams();
                if(weightString.equals("") || weightString.toString().toUpperCase().equals("NULL")){
                    weightString = "0";

                }
                try{
                    weightString = weightString.replaceAll("[^\\d.]", "");
                    weightDouble = Double.parseDouble(weightString);
                }
                catch (Exception e){
                    e.printStackTrace();
                }




                String gradePriceString = "0";
                double gradePriceDouble = 0;
                gradePriceString = modal_b2BGoatGradeDetails.getPrice();
                if (gradePriceString.equals("") || gradePriceString.toString().toUpperCase().equals("NULL")) {
                    gradePriceString = "0";

                }
                try {
                    gradePriceString = gradePriceString.replaceAll("[^\\d.]", "");
                    gradePriceDouble = Double.parseDouble(gradePriceString);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    toalPricedouble = gradePriceDouble * weightDouble;
                    price_for_this_entry = gradePriceDouble * weightDouble;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                /*
                if (gradeWise_count_weightJSONOBJECT.has(modal_b2BCartDetails.gradekey)) {

                    try {
                        JSONObject jsonObject = gradeWise_count_weightJSONOBJECT.getJSONObject(modal_b2BCartDetails.gradekey);
                        double weight = 0,  price = 0, maleweight = 0, femaleweight = 0, maleprice = 0, femaleprice = 0;
                        int maleQtyy = 0, femaleQtyy = 0 ,quantity =0;
                        if (jsonObject.has("totalprice")) {
                            price = jsonObject.getDouble("totalprice");
                            toalPricedouble = toalPricedouble + price;
                        }

                        if (jsonObject.has("totalQty")) {
                            quantity = jsonObject.getInt("totalQty");
                            quantity = quantity + 1;
                        }

                        if (jsonObject.has("totalweight")) {
                            weight = jsonObject.getDouble("totalweight");
                            weight = weightDouble + weight;
                        }


                        jsonObject.put("totalQty", quantity);
                        jsonObject.put("totalweight", threeDecimalConverter.format(weight));
                        jsonObject.put("totalprice", twoDecimalConverter.format(toalPricedouble));
                        totalCount = String.valueOf(quantity);



                        if (modal_b2BCartDetails.getGender().equals("MALE")) {

                            if (jsonObject.has("maleQty")) {
                                maleQtyy = jsonObject.getInt("maleQty");
                                maleQtyy = maleQtyy + 1;
                            }
                            else{
                                maleQtyy =  1;
                            }


                            if (jsonObject.has("maleWeight")) {
                                maleweight = jsonObject.getDouble("maleWeight");
                                maleweight = maleweight + weightDouble;
                            }
                            else{
                                maleweight =  weightDouble;
                            }

                            if (jsonObject.has("maleprice")) {
                                maleprice = jsonObject.getDouble("maleprice");
                                maleprice = maleprice + price_for_this_entry;
                            }
                            else{
                                maleprice =  price_for_this_entry;

                            }

                            jsonObject.put("maleprice", twoDecimalConverter.format(maleprice));
                            jsonObject.put("maleQty", maleQtyy);
                            jsonObject.put("maleWeight", threeDecimalConverter.format(maleweight));
                            male_Count =1 + male_Count ;
                            male_weight =  male_weight + weightDouble;



                        } else if (modal_b2BCartDetails.getGender().equals("FEMALE")) {
                            if (jsonObject.has("femaleQty")) {
                                femaleQtyy = jsonObject.getInt("femaleQty");
                                femaleQtyy = femaleQtyy + 1;
                            }
                            else{
                                femaleQtyy =  1;

                            }


                            if (jsonObject.has("femaleWeight")) {
                                femaleweight = jsonObject.getDouble("femaleWeight");
                                femaleweight = femaleweight + weightDouble;
                            }
                            else{
                                femaleweight = weightDouble;
                            }

                            if (jsonObject.has("femaleprice")) {
                                femaleprice = jsonObject.getDouble("femaleprice");
                                femaleprice = femaleprice + price_for_this_entry;
                            }
                            else{
                                femaleprice = price_for_this_entry;
                            }

                            jsonObject.put("femaleprice",twoDecimalConverter.format( femaleprice));
                            jsonObject.put("femaleQty", femaleQtyy);
                            jsonObject.put("femaleWeight", threeDecimalConverter.format(femaleweight));
                            femaleCount =femaleCount + 1;
                            female_weight =  weightDouble + female_weight;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                else {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        if (modal_b2BCartDetails.getGender().equals("MALE")) {
                            jsonObject.put("maleQty", 1);
                            jsonObject.put("maleWeight", threeDecimalConverter.format(weightDouble));
                            jsonObject.put("maleprice", twoDecimalConverter.format(toalPricedouble));
                            male_Count = male_Count + 1;
                            male_weight =male_weight +  weightDouble;

                        } else if (modal_b2BCartDetails.getGender().equals("FEMALE")) {
                            jsonObject.put("femaleWeight", threeDecimalConverter.format(weightDouble));
                            jsonObject.put("femaleQty", 1);
                            jsonObject.put("femaleprice", twoDecimalConverter.format(toalPricedouble));
                            femaleCount = femaleCount + 1 ;
                            female_weight =female_weight +  weightDouble;
                        }
                        jsonObject.put("totalQty", 1);
                        jsonObject.put("totalweight", threeDecimalConverter.format(weightDouble));
                        jsonObject.put("totalprice", twoDecimalConverter.format(toalPricedouble));
                        totalCount = totalCount +1;


                        gradeWise_count_weightJSONOBJECT.put(modal_b2BCartDetails.gradekey, jsonObject);
                        selected_gradeDetailss_arrayList.add(modal_b2BGoatGradeDetails);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                 */



                if(earTagDetails_JSONFinalSalesHashMap.containsKey(modal_b2BCartDetails.gradekey)){
                    Modal_POJOClassForFinalSalesHashmap modal_pojoClassForFinalSalesHashmap = earTagDetails_JSONFinalSalesHashMap.get(modal_b2BCartDetails.gradekey);
                    double weight = 0,  price = 0, maleweight = 0, femaleweight = 0, maleprice = 0, femaleprice = 0;
                    int maleQtyy = 0, femaleQtyy = 0 ,quantity =0;
                    int femaleCount = 0 , maleCount =0  ;





                    weight = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalweight();
                    weight = weight +weightDouble;

                    quantity = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalqty();
                    quantity = quantity + 1;

                    price  = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalprice();
                    price = toalPricedouble +price;


                    modal_pojoClassForFinalSalesHashmap.setTotalprice(price);
                    modal_pojoClassForFinalSalesHashmap.setTotalqty(quantity);
                    modal_pojoClassForFinalSalesHashmap.setTotalweight(weight);



                    if(modal_b2BCartDetails.getGender().toUpperCase().equals("MALE")){
                        maleprice = modal_pojoClassForFinalSalesHashmap.getMaleprice();
                        maleprice = maleprice + toalPricedouble;
                        maleCount  = modal_pojoClassForFinalSalesHashmap.getMaleqty();
                        maleCount = maleCount + 1;
                        maleweight =   modal_pojoClassForFinalSalesHashmap.getTotalmaleweight();
                        maleweight = maleweight + weightDouble;


                        modal_pojoClassForFinalSalesHashmap.setTotalmaleweight(maleweight);
                        modal_pojoClassForFinalSalesHashmap.setMaleqty(maleCount);
                        modal_pojoClassForFinalSalesHashmap.setMaleprice(maleprice);


                    }
                    if(modal_b2BCartDetails.getGender().toUpperCase().equals("FEMALE")){
                        femaleprice = modal_pojoClassForFinalSalesHashmap.getFemaleprice();
                        femaleprice = femaleprice + toalPricedouble;
                        femaleCount  = modal_pojoClassForFinalSalesHashmap.getFemaleqty();
                        femaleCount = femaleCount + 1;
                        femaleweight =   modal_pojoClassForFinalSalesHashmap.getTotalfemaleweight();
                        femaleweight = femaleweight + weightDouble;

                        modal_pojoClassForFinalSalesHashmap.setTotalfemaleweight(femaleweight);
                        modal_pojoClassForFinalSalesHashmap.setFemaleqty(femaleCount);
                        modal_pojoClassForFinalSalesHashmap.setFemaleprice(femaleprice);


                    }
                    earTagDetails_JSONFinalSalesHashMap.put(modal_b2BCartDetails.gradekey, modal_pojoClassForFinalSalesHashmap);



                }
                else{

                    Modal_POJOClassForFinalSalesHashmap modal_pojoClassForFinalSalesHashmap = new Modal_POJOClassForFinalSalesHashmap();



                    modal_pojoClassForFinalSalesHashmap.setTotalprice(toalPricedouble);
                    modal_pojoClassForFinalSalesHashmap.setTotalqty(1);
                    modal_pojoClassForFinalSalesHashmap.setTotalweight(weightDouble);




                    if(modal_b2BCartDetails.getGender().toUpperCase().equals("MALE")){



                        modal_pojoClassForFinalSalesHashmap.setTotalmaleweight(weightDouble);
                        modal_pojoClassForFinalSalesHashmap.setMaleqty(1);
                        modal_pojoClassForFinalSalesHashmap.setMaleprice(toalPricedouble);


                    }
                    if(modal_b2BCartDetails.getGender().toUpperCase().equals("FEMALE")){

                        modal_pojoClassForFinalSalesHashmap.setTotalfemaleweight(weightDouble);
                        modal_pojoClassForFinalSalesHashmap.setFemaleqty(1);
                        modal_pojoClassForFinalSalesHashmap.setFemaleprice(toalPricedouble);


                    }
                    earTagDetails_JSONFinalSalesHashMap.put(modal_b2BCartDetails.gradekey,modal_pojoClassForFinalSalesHashmap);
                    selected_gradeDetailss_arrayList.add(modal_b2BGoatGradeDetails);


                }

            }





            try {
                if (iterator - (goatGrade_arrayLsit.size() - 1) == 0) {
                    if (adapter_gradeWiseTotal_billingScreen != null) {
                        adapter_gradeWiseTotal_billingScreen.notifyDataSetChanged();
                        ListItemSizeSetter.getListViewSize(gradewisetotalCount_listview);

                    }


                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }





    private void UpdateDataInSharedPreference() {

        String batchNo_fromPreference ="";
        SharedPreferences sharedPreferences_forAdd  = null;

            sharedPreferences_forAdd =     mContext.getSharedPreferences(Constants.earTagCalculationDeta_DeliveryCenter,MODE_PRIVATE);

        batchNo_fromPreference =    sharedPreferences_forAdd.getString(
                "BatchNo", "0"
        );
        int soldtotalCount_int = 0 ,soldmaleCount_int =0,soldfemaleCount_int =0, soldfemaleWithBabyCount_int =0 ;
        int unsoldtotalCount_int =0 ,unsoldmaleCount_int =0 ,unsoldfemaleCount_int =0 , unsoldfemaleWithBabyCount_int =0;

        double soldtotalItemWeight =0 ;
        double unsoldtotalItemWeight =0 ;
        try{
            if (batchNo_fromPreference.toUpperCase().equals(batchno)) {

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
                String previous_WeightInGrams = "" ,entered_Weight_string ="" ,previousSelectedGender = "" , selectedGender ="";
                for(int iterator = 0 ; iterator<earTagDetailsArrayList_String.size(); iterator++) {
                    String barcode = earTagDetailsArrayList_String.get(iterator);
                    Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagDetailsHashMap.get(barcode);
                    previous_WeightInGrams = "" ; entered_Weight_string ="" ; previousSelectedGender ="";selectedGender  ="";
                    try {
                    previous_WeightInGrams = modal_goatEarTagDetails.getCurrentweightingrams();
                    previous_WeightInGrams = previous_WeightInGrams.replaceAll("[^\\d.]", "");
                        if(previous_WeightInGrams.equals("") || previous_WeightInGrams.equals(null)){
                            previous_WeightInGrams = "0";
                        }

                    double previous_WeightInGrams_double = Double.parseDouble(previous_WeightInGrams);
                    unsoldtotalItemWeight = unsoldtotalItemWeight - previous_WeightInGrams_double;

                    }
                      catch (Exception e){
                        e.printStackTrace();
                    }




                  try {
                      entered_Weight_string = modal_goatEarTagDetails.getNewWeight_forBillingScreen();
                      entered_Weight_string = entered_Weight_string.replaceAll("[^\\d.]", "");

                      if(entered_Weight_string.equals("") || entered_Weight_string.equals(null)){
                          entered_Weight_string = "0";
                      }

                      double entered_Weight_double = Double.parseDouble(entered_Weight_string);
                      soldtotalItemWeight = soldtotalItemWeight + entered_Weight_double;
                      unsoldtotalCount_int = unsoldtotalCount_int - 1;
                      soldtotalCount_int = soldtotalCount_int + 1;
                  }
                  catch (Exception e){
                      e.printStackTrace();
                  }


                    previousSelectedGender = String.valueOf(modal_goatEarTagDetails.getGender());

                        if (previousSelectedGender.toUpperCase().equals(getString(R.string.MALE))) {
                            unsoldmaleCount_int = unsoldmaleCount_int - 1;
                        }

                        if (previousSelectedGender.toUpperCase().equals(getString(R.string.FEMALE))) {
                            unsoldfemaleCount_int = unsoldfemaleCount_int - 1;
                        }

                        if (previousSelectedGender.toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {
                            unsoldfemaleWithBabyCount_int = unsoldfemaleWithBabyCount_int - 1;
                        }


                        if (previousSelectedGender.toUpperCase().equals(getString(R.string.MALE))) {
                            soldmaleCount_int = soldmaleCount_int + 1;
                        }

                        if (previousSelectedGender.toUpperCase().equals(getString(R.string.FEMALE))) {
                            soldfemaleCount_int = soldfemaleCount_int + 1;
                        }

                        if (previousSelectedGender.toUpperCase().equals(getString(R.string.FEMALE_WITH_BABY))) {
                            soldfemaleWithBabyCount_int = soldfemaleWithBabyCount_int + 1;

                        }


                }
                try{



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
                            "TotalSoldWeight", (float) Double.parseDouble(threeDecimalConverter.format(soldtotalItemWeight))
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
                            "TotalUnsoldWeight", (float) Double.parseDouble(threeDecimalConverter.format(unsoldtotalItemWeight))
                    );

                    myEdit.apply();

                }
                catch (Exception e){
                    e.printStackTrace();
                }





            }

        }
        catch (Exception e){
            e.printStackTrace();
        }



    }

    private void call_and_init_GoatEarTagDetails_BulkUpdate(String callMethod) {

        try {

            if (isGoatEarTagDetailsTableServiceCalled) {

                return;
            }
            isGoatEarTagDetailsTableServiceCalled = true;
            goatEarTagDetailsBulkUpdateInterface = new GoatEarTagDetails_BulkUpdateInterface() {


                @Override
                public void notifySuccess(String result) {
                    isGoatEarTagDetailsTableServiceCalled = false;
                    proceedCheckOut_button.setVisibility(View.GONE);
                   // ((BillingScreen) getActivity()).Create_and_SharePdf();
                    DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.Create_and_SharePdf();
                    //((BillingScreen) getActivity()).neutralizeArray_and_OtherValues();
                }

                @Override
                public void notifySuccessForGettingListItem(ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch) {
                    isGoatEarTagDetailsTableServiceCalled = false;
                  //  ((BillingScreen) getActivity()).neutralizeArray_and_OtherValues();
                   // ((BillingScreen) getActivity()).closeFragment();
                }

                @Override
                public void notifyVolleyError(VolleyError error) {
                   // ((BillingScreen) getActivity()).neutralizeArray_and_OtherValues();
                    Toast.makeText(mContext, "There is an volley error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                   // ((BillingScreen) getActivity()).closeFragment();

                    isGoatEarTagDetailsTableServiceCalled = false;
                }

                @Override
                public void notifyProcessingError(Exception error) {
                    Toast.makeText(mContext, "There is an process error while updating Ear Tag", Toast.LENGTH_SHORT).show();
                   // ((BillingScreen) getActivity()).neutralizeArray_and_OtherValues();
                   // ((BillingScreen) getActivity()).closeFragment();
                    isGoatEarTagDetailsTableServiceCalled = false;


                }

            };


            Modal_B2BOrderItemDetails modal_b2BOrderItemDetails = new Modal_B2BOrderItemDetails();

            modal_b2BOrderItemDetails.setBatchno_static(String.valueOf(batchno));
            modal_b2BOrderItemDetails.setDeliverycentrekey_static(String.valueOf(deliveryCenterKey));
            modal_b2BOrderItemDetails.setOrderplaceddate_static(String.valueOf(orderplaceddate));
            modal_b2BOrderItemDetails.setStatus(String.valueOf(Constants.goatEarTagStatus_Sold));
            modal_b2BOrderItemDetails.setOrderid_static(String.valueOf(orderid));
            modal_b2BOrderItemDetails.setRetailerkey_static(String.valueOf(DeliveryCentre_PlaceOrderScreen_Fragment.retailerKey));
            modal_b2BOrderItemDetails.setRetailermobileno_static(String.valueOf(DeliveryCentre_PlaceOrderScreen_Fragment.retailermobileno));
            modal_b2BOrderItemDetails.setEarTagDetailsArrayList_String(earTagDetailsArrayList_String);
            modal_b2BOrderItemDetails.setEarTagDetailsHashMap(earTagDetailsHashMap);


            try {

                String addApiToCall = API_Manager.updateGoatEarTag;
                GoatEarTagDetails_BulkUpdate asyncTask = new GoatEarTagDetails_BulkUpdate(goatEarTagDetailsBulkUpdateInterface, addApiToCall, callMethod, modal_b2BOrderItemDetails, orderplaceddate, usermobileno_string);
                asyncTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }




    }




    private void call_and_init_B2BOrderItemDetailsService(String callADDMethod) {

        if (isOrderItemDetailsServiceCalled) {
            // showProgressBar(false);
            return;
        }
        isOrderItemDetailsServiceCalled = true;
        callback_b2BOrderItemDetailsInterface = new B2BOrderItemDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BOrderItemDetails> arrayList) {

            }

            @Override
            public void notifySuccess(String result) {

                isOrderItemDetailsServiceCalled = false;



               // showProgressBar(false);


            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isOrderItemDetailsServiceCalled = false;
                //    //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 3 " + DateParser.getDate_and_time_newFormat());

            }

            @Override
            public void notifyProcessingError(Exception error) {
                isOrderItemDetailsServiceCalled = false;
                ////Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());

            }


        };

        Modal_B2BOrderItemDetails modal_b2BOrderItemDetails = new Modal_B2BOrderItemDetails();

        modal_b2BOrderItemDetails.setBatchno_static(String.valueOf(batchno));
        modal_b2BOrderItemDetails.setDeliverycentrekey_static(String.valueOf(deliveryCenterKey));
        modal_b2BOrderItemDetails.setOrderplaceddate_static(String.valueOf(orderplaceddate));
        modal_b2BOrderItemDetails.setStatus_static(String.valueOf(Constants.orderDetailsStatus_Delivered));
        modal_b2BOrderItemDetails.setOrderid_static(String.valueOf( orderid));
        modal_b2BOrderItemDetails.setRetailerkey_static( String.valueOf(DeliveryCentre_PlaceOrderScreen_Fragment.retailerKey));
        modal_b2BOrderItemDetails.setRetailermobileno_static(String.valueOf(DeliveryCentre_PlaceOrderScreen_Fragment.retailermobileno));
        modal_b2BOrderItemDetails.setEarTagDetailsArrayList_String(earTagDetailsArrayList_String);
        modal_b2BOrderItemDetails.setEarTagDetailsHashMap(earTagDetailsHashMap);
        modal_b2BOrderItemDetails.setEarTagDetails_weightStringHashMap(earTagDetails_weightStringHashMap);



        String getApiToCall = API_Manager.addOrderItemDetails ;

        B2BOrderItemDetails asyncTask = new B2BOrderItemDetails(callback_b2BOrderItemDetailsInterface,  getApiToCall, Constants.CallADDMethod,modal_b2BOrderItemDetails);
        asyncTask.execute();







    }

    private void call_and_init_B2BOrderDetailsService(String callADDMethod) {

        if (isOrderDetailsServiceCalled) {
           // showProgressBar(false);
            return;
        }
        isOrderDetailsServiceCalled = true;
        callback_b2BOrderDetailsInterface = new B2BOrderDetailsInterface() {


            @Override
            public void notifySuccessForGettingListItem(ArrayList<Modal_B2BOrderDetails> retailerDetailsArrayListt) {
                isOrderDetailsServiceCalled = false;


            }

            @Override
            public void notifySuccess(String result) {

                isOrderDetailsServiceCalled = false;


                //((BillingScreen)getActivity()).closeFragment();
            }

            @Override
            public void notifyVolleyError(VolleyError error) {
                isOrderDetailsServiceCalled = false;
            //    //Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 3 " + DateParser.getDate_and_time_newFormat());

            }

            @Override
            public void notifyProcessingError(Exception error) {
                isOrderDetailsServiceCalled = false;
                ////Log.i("INIT", "call_and_init_B2BReceiverDetailsService: 4 " + DateParser.getDate_and_time_newFormat());

            }


        };



        Modal_B2BOrderDetails.setTotalquantity_Static(String.valueOf(totalCount));
        Modal_B2BOrderDetails.setBatchno_Static(String.valueOf(batchno));
        Modal_B2BOrderDetails.setTotalweight_Static(String.valueOf(threeDecimalConverter.format(totalWeight_double)));
        Modal_B2BOrderDetails.setTotalmaleweight_Static(String.valueOf(threeDecimalConverter.format(male_weight)));
        Modal_B2BOrderDetails.setDeliverycentrekey_Static(String.valueOf(deliveryCenterKey));
        Modal_B2BOrderDetails.setDeliverycentrename_Static(String.valueOf(deliveryCenterName));
        Modal_B2BOrderDetails.setDiscountamount_Static(String.valueOf(twoDecimalConverter.format(discountAmount_double)));
        Modal_B2BOrderDetails.setOrderplaceddate_Static(String.valueOf(orderplaceddate));
        Modal_B2BOrderDetails.setPayableAmount_Static(String.valueOf(twoDecimalConverter.format(totalPrice_double)));
        Modal_B2BOrderDetails.setStatus_Static(String.valueOf(Constants.orderDetailsStatus_Delivered));
        Modal_B2BOrderDetails.setPriceperkg_Static(String.valueOf(twoDecimalConverter.format(pricePerKg_double)));
        Modal_B2BOrderDetails.setTotalfemalequantity_Static(String.valueOf(femaleCount));
        Modal_B2BOrderDetails.setTotalfemaleweight_Static(String.valueOf(threeDecimalConverter.format(female_weight)));
        Modal_B2BOrderDetails.setTotalfemalewithbabyquantity_Static(String.valueOf(femaleWithBabyCount));
        Modal_B2BOrderDetails.setTotalfemalewithbabyweight_Static(String.valueOf(threeDecimalConverter.format(femaleWithBabyWeight)));
        Modal_B2BOrderDetails.setTotalmalequantity_Static(String.valueOf(male_Count));
        Modal_B2BOrderDetails.setTotalquantity_Static(String.valueOf(totalCount));

        Modal_B2BOrderDetails.setOrderid_Static(String.valueOf(orderid));
        Modal_B2BOrderDetails.setInvoiceno_Static(String.valueOf(invoiceno));
        Modal_B2BOrderDetails.setPaymentMode_Static(String.valueOf(DeliveryCentre_PlaceOrderScreen_Fragment.paymentMode));
        Modal_B2BOrderDetails.setRetailerkey_Static( String.valueOf(DeliveryCentre_PlaceOrderScreen_Fragment.retailerKey));
        Modal_B2BOrderDetails.setRetailername_Static(String.valueOf(DeliveryCentre_PlaceOrderScreen_Fragment.retailername));
        Modal_B2BOrderDetails.setRetailermobileno_Static(String.valueOf(DeliveryCentre_PlaceOrderScreen_Fragment.retailermobileno));


        String getApiToCall = API_Manager.addOrderDetailsList ;

        B2BOrderDetails asyncTask = new B2BOrderDetails(callback_b2BOrderDetailsInterface,  getApiToCall, Constants.CallADDMethod);
        asyncTask.execute();







    }

/*
    public void calculateTotalweight_Quantity_Price() {

        batchno = String.valueOf(BillingScreen.batchno);
        totalCount = String.valueOf(earTagDetailsArrayList_String.size());
        for(int iterator =0 ; iterator < earTagDetailsArrayList_String .size(); iterator ++){

            if(earTagDetailsHashMap.containsKey(earTagDetailsArrayList_String .get(iterator))) {
                try {
                    totalWeight = earTagDetailsHashMap.get(earTagDetailsArrayList_String.get(iterator)).getNewWeight_forBillingScreen();
                    totalWeight = totalWeight.replaceAll("[^\\d.]", "");
                    if (totalWeight.equals("") || totalWeight.equals(null)) {
                        totalWeight = "0";
                    }
                    totalWeight_double = totalWeight_double + Double.parseDouble(totalWeight);


                    if(earTagDetailsHashMap.get(earTagDetailsArrayList_String.get(iterator)).getGender().equals(mContext.getString(R.string.MALE))){
                        male_Count = male_Count +1;
                        male_weight = male_weight + Double.parseDouble(totalWeight);
                    }
                    else if(earTagDetailsHashMap.get(earTagDetailsArrayList_String.get(iterator)).getGender().equals(mContext.getString(R.string.FEMALE))){
                        femaleCount = femaleCount + 1;
                        female_weight = female_weight + Double.parseDouble(totalWeight);
                    }
                    else if(earTagDetailsHashMap.get(earTagDetailsArrayList_String.get(iterator)).getGender().equals(mContext.getString(R.string.FEMALE_WITH_BABY))){
                        femaleWithBabyCount = femaleWithBabyCount +1 ;
                        femaleWithBabyWeight = femaleWithBabyWeight + Double.parseDouble(totalWeight);
                    }





                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        try{

            pricePerKg = ((BillingScreen)getActivity()).pricePerKg_editText.getText().toString();
            pricePerKg = pricePerKg.replaceAll("[^\\d.]", "");




            if(pricePerKg.equals("") ||pricePerKg.equals(null) ){
                pricePerKg = "0";
                Toast.makeText(mContext, "Please set the value of price per kg", Toast.LENGTH_SHORT).show();
            }
            pricePerKg_double = Double.parseDouble(pricePerKg);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{

            discountAmount = ((BillingScreen)getActivity()).discount_editText.getText().toString();
            discountAmount = discountAmount.replaceAll("[^\\d.]", "");
            if(discountAmount.equals("") ||discountAmount.equals(null) ){
                discountAmount = "0";
             //   Toast.makeText(mContext, "Please set the value of dicount Amount", Toast.LENGTH_SHORT).show();
            }
            discountAmount_double = Double.parseDouble(discountAmount);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{
            //  int weight = Integer.parseInt(String.valueOf(Math.round(totalWeight_double)))
            totalPrice_double= pricePerKg_double *totalWeight_double ;

            total_Price_textview.setText(" Rs. "+String.valueOf(twoDecimalConverter.format(totalPrice_double)));

            totalPrice_double= totalPrice_double - discountAmount_double;
            totalPrice_double = Double.parseDouble(twoDecimalConverter.format((totalPrice_double)));


        }
        catch (Exception e){
            e.printStackTrace();
        }




        final_pricePerKg_textview.setText(" Rs. "+String.valueOf(twoDecimalConverter.format(pricePerKg_double)) +"");
        total_Discount_textview.setText(" Rs. "+String.valueOf(twoDecimalConverter.format(discountAmount_double)) + "");
        finalAmount_textview.setText(" Rs. "+String.valueOf(twoDecimalConverter.format(totalPrice_double)) +"");
        if(discountAmount_double>0){
            discountlayout.setVisibility(View.VISIBLE);
        }
        else{
            discountlayout.setVisibility(View.GONE);
        }

        male_Qty_textview.setText(String.valueOf(male_Count));
        female_Qty_textview.setText(String.valueOf(femaleCount));
        femaleWithBaby_Qty_textview.setText(String.valueOf(femaleWithBabyCount));
        total_Qty_textview.setText(String.valueOf(totalCount));



        male_Wt_textview.setText(""+String.valueOf(threeDecimalConverter.format(male_weight)) +" Kg");
        female_Wt_textview.setText(""+String.valueOf(threeDecimalConverter.format(female_weight))+" Kg");
        femaleWithBaby_Wt_textview.setText(""+String.valueOf(threeDecimalConverter.format(femaleWithBabyWeight))+" Kg");
        total_Wt_textview.setText(String.valueOf(threeDecimalConverter.format(totalWeight_double))+" Kg");
        totalWeight_textview.setText(String.valueOf(threeDecimalConverter.format(totalWeight_double))+" Kg");
        totalItem_CountTextview .setText(String.valueOf(totalCount));

        BillingScreen.showProgressBar(false);

    }
*/



    public void calculateTotalweight_Quantity_Price() {
         totalCount = "";
        if(earTagDetails_weightStringHashMap.containsKey("")){
            totalCount = String.valueOf(earTagDetails_weightStringHashMap.size() - 1);
        }
        else{
            totalCount = String.valueOf(earTagDetails_weightStringHashMap.size());
        }






        /*if(earTagDetailsHashMap.containsKey("")){
            totalCount = String.valueOf(earTagDetailsHashMap.size() - 1);
        }
        else{
            totalCount = String.valueOf(earTagDetailsHashMap.size());
        }

         */

        String Weight ="" , pricePerKg  = "" , discountAmount = "",gradeprice_string ="", totalPrice =  "" ,gradename ="" , gradeprice ="";
        JSONObject jsonObject = new JSONObject();
        totalWeight_double =0 ; weight_double = 0 ; pricePerKg_double = 0 ; discountAmount_double = 0 ; totalPrice_double = 0 ; totalPrice_doubleWithoutDiscount =0 ; gradeprice_double =0;
        male_Count = 0 ; female_weight = 0; male_weight =0 ; femaleCount = 0;
        for(int iterator =0 ; iterator < earTagDetailsArrayList_String .size(); iterator ++){
/*
            if(earTagDetailsHashMap.containsKey(earTagDetailsArrayList_String .get(iterator))) {
                try {
                    totalWeight = earTagDetailsHashMap.get(earTagDetailsArrayList_String.get(iterator)).getNewWeight_forBillingScreen();
                    totalWeight = totalWeight.replaceAll("[^\\d.]", "");
                    if (totalWeight.equals("") || totalWeight.equals(null)) {
                        totalWeight = "0";
                    }
                    totalWeight_double = totalWeight_double + Double.parseDouble(totalWeight);
                    totalWeight_double = Double.parseDouble(threeDecimalConverter.format(totalWeight_double));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

 */


            if(earTagDetails_weightStringHashMap.containsKey(earTagDetailsArrayList_String .get(iterator))) {

                try{
                    jsonObject = earTagDetails_weightStringHashMap.get(earTagDetailsArrayList_String.get(iterator));

                }
                catch (Exception e){
                    e.printStackTrace();
                }


                try {

                    if(jsonObject.has("weight")){
                        Weight = jsonObject.getString("weight");
                        Weight = Weight.replaceAll("[^\\d.]", "");
                        if (Weight.equals("") || Weight.equals(null)) {
                            Weight = "0";
                        }
                    }
                    else{
                        Weight ="0";
                    }


                    weight_double = Double.parseDouble(Weight);
                    weight_double = Double.parseDouble(threeDecimalConverter.format(weight_double));




                    try {
                        if(jsonObject.has("gender")){

                            if(String.valueOf(jsonObject.getString("gender")).toUpperCase().equals("MALE")){
                                male_Count = male_Count +1;
                                male_weight = weight_double +male_weight;
                            }
                            if(String.valueOf(jsonObject.getString("gender")).toUpperCase().equals("FEMALE")){
                                femaleCount = femaleCount+1;
                                female_weight = female_weight + weight_double;

                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                    totalWeight_double = weight_double + totalWeight_double;
                    totalWeight_double = Double.parseDouble(threeDecimalConverter.format(totalWeight_double));

                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
                    if(jsonObject.has("gradeprice")){
                        gradeprice_string = jsonObject.getString("gradeprice");
                        gradeprice_string = gradeprice_string.replaceAll("[^\\d.]", "");
                        if (gradeprice_string.equals("") || gradeprice_string.equals(null)) {
                            gradeprice_string = "0";
                        }
                    }
                    else{
                        gradeprice_string ="0";
                    }

                    gradeprice_double =  Double.parseDouble(gradeprice_string);
                    gradeprice_double = Double.parseDouble(threeDecimalConverter.format(gradeprice_double));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try{
                    totalPrice_doubleWithoutDiscount = totalPrice_doubleWithoutDiscount + (weight_double * gradeprice_double);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                try{
                    totalPrice_doubleWithoutDiscount = Double.parseDouble(threeDecimalConverter.format( totalPrice_doubleWithoutDiscount));
                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }
        }


        try{

            pricePerKg = DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.pricePerKg_editText.getText().toString();
            pricePerKg = pricePerKg.replaceAll("[^\\d.]", "");
            if(pricePerKg.equals("") ||pricePerKg.equals(null) ){
                pricePerKg = "0";
                //     Toast.makeText(this, "Please set the value of price per kg", Toast.LENGTH_SHORT).show();
            }
            pricePerKg_double = Double.parseDouble(pricePerKg);
            pricePerKg_double = Double.parseDouble(twoDecimalConverter.format(pricePerKg_double));
        }
        catch (Exception e){
            e.printStackTrace();
        }





        try{
            discountAmount = DeliveryCentre_PlaceOrderScreen_Fragment.deliveryCentre_placeOrderScreen_fragment.discount_editText.getText().toString();

            discountAmount = discountAmount.replaceAll("[^\\d.]", "");
            if(discountAmount.equals("") ||discountAmount.equals(null) ){
                discountAmount = "0";
                // Toast.makeText(this, "Please set the value of dicount Amount", Toast.LENGTH_SHORT).show();
            }
            discountAmount_double = Double.parseDouble(discountAmount);
            discountAmount_double = Double.parseDouble(twoDecimalConverter.format(discountAmount_double));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{
            //  int weight = Integer.parseInt(String.valueOf(Math.round(totalWeight_double)));
                   /* if (weight < 1000) {
                        totalPrice_double = (pricePerKg_double * weight);


                        totalPrice_double = totalPrice_double / 1000;

                        totalPrice_double = Double.parseDouble(df.format(totalPrice_double));


                    }

                    if (weight == 1000) {
                        totalPrice_double =  Double.parseDouble(df.format(totalPrice_double));


                    }

                    if (weight > 1000) {


                        int itemquantity = weight - 1000;
                        //Log.e("TAg", "weight itemquantity" + itemquantity);

                        totalPrice_double = (pricePerKg_double * itemquantity) / 1000;
                        totalPrice_double = Double.parseDouble(df.format(totalPrice_double));


                        //Log.e("TAg", "weight item_total" + item_total);

                        totalPrice_double= pricePerKg_double + totalPrice_double;
                        totalPrice_double = Double.parseDouble(df.format((totalPrice_double)));




                    }


                    */
            //  totalPrice_doubleWithoutDiscount= pricePerKg_double *totalWeight_double ;
            totalPrice_double= totalPrice_doubleWithoutDiscount - discountAmount_double;
            totalPrice_double = Double.parseDouble(twoDecimalConverter.format((totalPrice_double)));


        }
        catch (Exception e){
            e.printStackTrace();
        }




        try {
            if (discountAmount_double > 0) {
                discountlayout.setVisibility(View.VISIBLE);
                totalAmountlayout.setVisibility(View.VISIBLE);
            } else {
                discountlayout.setVisibility(View.GONE);
                totalAmountlayout.setVisibility(View.GONE);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        total_Discount_textview.setText("Rs . " +String.valueOf(twoDecimalConverter.format(discountAmount_double)));
        totalItem_CountTextview .setText(String.valueOf(totalCount));
        finalAmount_textview.setText("Rs . " +String.valueOf(twoDecimalConverter.format(totalPrice_double)));
        totalWeight_textview.setText(String.valueOf(threeDecimalConverter.format(totalWeight_double)) +" Kg");
        total_Amount_textview.setText("Rs . " +String.valueOf(twoDecimalConverter.format(totalPrice_doubleWithoutDiscount)));


    }



    private void Initialize_and_ExecuteB2BCtgyItem() {

        // showProgressBar(true);
        if (isB2BItemCtgyTableServiceCalled) {
           // BillingScreen.showProgressBar(false);
            return;
        }
        isB2BItemCtgyTableServiceCalled = true;
        callback_B2BItemCtgyInterface = new B2BItemCtgyInterface() {
            @Override
            public void notifySuccess(String result) {
                //  showProgressBar(false);
                isB2BItemCtgyTableServiceCalled = false;
            }

            @Override
            public void notifyError(VolleyError error) {
                //   showProgressBar(false);
                isB2BItemCtgyTableServiceCalled = false;

            }
        };
        String addApiToCall = API_Manager.getB2BItemCtgy ;
        B2BItemCtgy asyncTask = new B2BItemCtgy(callback_B2BItemCtgyInterface,  addApiToCall );
        asyncTask.execute();




    }


    }