package com.tmc.tmcb2bpartnerapp.apiRequestServices;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.fragment.DeliveryCenter_PlaceOrderScreen_SecondVersn;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderItemDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.second_version.activity.CheckOut_activity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.volleyrequestqueuehelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class B2BOrderItemDetails  extends AsyncTask<String, String, Modal_B2BOrderItemDetails> {

    HashMap<String,Modal_GoatEarTagDetails> earTagDetailsHashMap = new HashMap<>();
    ArrayList<String> earTagDetailsArrayList_String = new ArrayList<>();
    public static B2BOrderItemDetailsInterface callback_OrderItemDetailsInterface = null;
    String ApitoCall ="",callMethod ="" , orderid ="";
    JSONObject jsonToADD_Or_Update;
    Modal_B2BOrderItemDetails modal_b2BOrderItemDetails ;
    ArrayList<Modal_B2BOrderItemDetails> orderItemDetailsArrayList = new ArrayList<>();

    HashMap<String, Modal_B2BCartItemDetails> earTagDetails_jsonFinalSalesHashMap  = new HashMap<>();
    boolean isCalledFromPlaceOrderSecondVersn = false , isCalledFromCheckOutActivity = false;
    DeliveryCenter_PlaceOrderScreen_SecondVersn deliveryCenter_placeOrderScreen_secondVersn;
    CheckOut_activity checkOut_activity;


    public B2BOrderItemDetails(B2BOrderItemDetailsInterface callback_OrderItemDetailsInterfacee, String getApiToCall, String apiMethodtoCall ,Modal_B2BOrderItemDetails modal_b2BOrderItemDetailss ) {
        this.callback_OrderItemDetailsInterface = callback_OrderItemDetailsInterfacee;
        this.ApitoCall = getApiToCall;
        this.callMethod = apiMethodtoCall;
        this.modal_b2BOrderItemDetails = modal_b2BOrderItemDetailss;
        this.earTagDetailsHashMap = modal_b2BOrderItemDetailss.getEarTagDetailsHashMap();
        this.earTagDetailsArrayList_String = modal_b2BOrderItemDetailss.getEarTagDetailsArrayList_String();
        isCalledFromPlaceOrderSecondVersn = false;
        isCalledFromCheckOutActivity = false;
        if(callMethod.equals(Constants.CallADDMethod) ){
            jsonToADD_Or_Update = getJSONforPOJOClass();

        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){
            jsonToADD_Or_Update = getJSONforPOJOClass();

        }
        else if(callMethod.equals(Constants.CallGETMethod)){
            new Modal_B2BOrderItemDetails();
        }
        else if(callMethod.equals(Constants.CallGETListMethod) || callMethod.equals(Constants.CallGETLastEntryMethod)){
            new Modal_B2BOrderItemDetails();
        }
    }

    public B2BOrderItemDetails(B2BOrderItemDetailsInterface callback_OrderItemDetailsInterfacee, String getApiToCall, String apiMethodtoCall) {
        this.callback_OrderItemDetailsInterface = callback_OrderItemDetailsInterfacee;
        this.ApitoCall = getApiToCall;
        this.callMethod = apiMethodtoCall;
        isCalledFromPlaceOrderSecondVersn = false;
        isCalledFromCheckOutActivity = false;
        if(callMethod.equals(Constants.CallADDMethod) ){
            jsonToADD_Or_Update = getJSONforPOJOClass();

        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){
            jsonToADD_Or_Update = getJSONforPOJOClass();

        }
        else if(callMethod.equals(Constants.CallGETMethod)){
            new Modal_B2BOrderItemDetails();
        }
        else if(callMethod.equals(Constants.CallGETListMethod) || callMethod.equals(Constants.CallGETLastEntryMethod)){
            new Modal_B2BOrderItemDetails();
        }
   
    }



    public B2BOrderItemDetails(B2BOrderItemDetailsInterface callback_b2BOrderItemDetailsInterface, String getApiToCall, String callMethod, HashMap<String, Modal_B2BCartItemDetails> earTagDetails_jsonFinalSalesHashMapp, ArrayList<String> earTagDetailsArrayList_String, String orderid, DeliveryCenter_PlaceOrderScreen_SecondVersn deliveryCenter_placeOrderScreen_secondVersnn) {

        isCalledFromCheckOutActivity = false;
        this.callback_OrderItemDetailsInterface = callback_b2BOrderItemDetailsInterface;
        this.ApitoCall = getApiToCall;
        this.callMethod = callMethod;
        this.orderid = orderid ;
        this.earTagDetails_jsonFinalSalesHashMap = earTagDetails_jsonFinalSalesHashMapp;
        this.earTagDetailsArrayList_String = earTagDetailsArrayList_String;
        isCalledFromPlaceOrderSecondVersn = true;
        this.jsonToADD_Or_Update = new JSONObject();
        this.deliveryCenter_placeOrderScreen_secondVersn = deliveryCenter_placeOrderScreen_secondVersnn;


    }

    public B2BOrderItemDetails(B2BOrderItemDetailsInterface callback_b2BOrderItemDetailsInterface, String getApiToCall, String callMethod, HashMap<String, Modal_B2BCartItemDetails> cartItemDetails_hashmap, ArrayList<String> cartItemDetailsList_string, String orderid, CheckOut_activity checkOut_activity) {

        this.callback_OrderItemDetailsInterface = callback_b2BOrderItemDetailsInterface;
        this.ApitoCall = getApiToCall;
        this.callMethod = callMethod;
        this.orderid = orderid ;
        this.earTagDetails_jsonFinalSalesHashMap = cartItemDetails_hashmap;
        //this.earTagDetailsHashMap = cartItemDetails_hashmap;

        this.earTagDetailsArrayList_String = cartItemDetailsList_string;
        isCalledFromPlaceOrderSecondVersn = false;
        isCalledFromCheckOutActivity = true;
        this.jsonToADD_Or_Update = new JSONObject();
        this.checkOut_activity = checkOut_activity;



    }


    private JSONObject getJSONforPOJOClass() {
        JSONObject jsonObject = new JSONObject();

        try {
            if (!modal_b2BOrderItemDetails.getBatchno_static().toString().equals("") && !modal_b2BOrderItemDetails.getBatchno_static().toString().equals("null")) {
                jsonObject.put("batchno", modal_b2BOrderItemDetails.getBatchno_static());
            }

            if (!modal_b2BOrderItemDetails.getOrderid_static().toString().equals("") && !modal_b2BOrderItemDetails.getOrderid_static().toString().equals("null")) {
                jsonObject.put("orderid", modal_b2BOrderItemDetails.getOrderid_static());
            }
            if (!modal_b2BOrderItemDetails.getOrderplaceddate_static().toString().equals("") && !modal_b2BOrderItemDetails.getOrderplaceddate_static().toString().equals("null")) {
                jsonObject.put("orderplaceddate", modal_b2BOrderItemDetails.getOrderplaceddate_static());
            }
            if (!modal_b2BOrderItemDetails.getRetailerkey_static().toString().equals("") && !modal_b2BOrderItemDetails.getRetailerkey_static().toString().equals("null")) {
                jsonObject.put("retailerkey", modal_b2BOrderItemDetails.getRetailerkey_static());
            }
            if (!modal_b2BOrderItemDetails.getRetailermobileno_static().toString().equals("") && !modal_b2BOrderItemDetails.getRetailermobileno_static().toString().equals("null")) {
                jsonObject.put("retailermobileno", modal_b2BOrderItemDetails.getRetailermobileno_static());
            }
            if (!modal_b2BOrderItemDetails.getStatus_static().toString().equals("") && !modal_b2BOrderItemDetails.getStatus_static().toString().equals("null")) {
                jsonObject.put("status",modal_b2BOrderItemDetails.getStatus());
            }
            if (!modal_b2BOrderItemDetails.getDeliverycentrekey_static().toString().equals("") && !modal_b2BOrderItemDetails.getDeliverycentrekey_static().toString().equals("null")) {
                jsonObject.put("deliverycentrekey", modal_b2BOrderItemDetails.getDeliverycentrekey_static());
            }

            if (!modal_b2BOrderItemDetails.getGradename_static().toString().equals("") && !modal_b2BOrderItemDetails.getGradename_static().toString().equals("null")) {
                jsonObject.put("gradename", modal_b2BOrderItemDetails.getGradename_static());
            }
            if (!modal_b2BOrderItemDetails.getGradeprice_static().toString().equals("") && !modal_b2BOrderItemDetails.getGradeprice_static().toString().equals("null")) {
                jsonObject.put("gradeprice", modal_b2BOrderItemDetails.getGradeprice_static());
            }
            if (!modal_b2BOrderItemDetails.getGradekey_static().toString().equals("") && !modal_b2BOrderItemDetails.getGradekey_static().toString().equals("null")) {
                jsonObject.put("gradekey", modal_b2BOrderItemDetails.getGradekey_static());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        
        
        
        return jsonObject;
    }
    
    
    


    @Override
    protected Modal_B2BOrderItemDetails doInBackground(String... strings) {



        if(callMethod.equals(Constants.CallADDMethod)){
            if(isCalledFromPlaceOrderSecondVersn || isCalledFromCheckOutActivity){
                addEntryInOrderItemDetailsUsingHashmap();
            }
            else{
                addEntryInBatchDetails();
            }

        }
        else if(callMethod.equals(Constants.CallGETMethod) || callMethod.equals(Constants.CallGETListMethod) || callMethod.equals(Constants.CallGETLastEntryMethod)){
            getDataFromBatchDetails();
        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){
            updateEntryInBatchDetails();
        }



        return null;
    }

    private void addEntryInOrderItemDetailsUsingHashmap() {
        try{
            for(int i = 0 ; i < earTagDetailsArrayList_String.size(); i++){
                String barcode = earTagDetailsArrayList_String .get(i);
                if(earTagDetails_jsonFinalSalesHashMap.containsKey(barcode)){
                    Modal_B2BCartItemDetails modal_b2BCartItemDetails = earTagDetails_jsonFinalSalesHashMap.get(barcode);
                    try {
                        try {
                            if(jsonToADD_Or_Update.has("meatyieldweight")) {
                                jsonToADD_Or_Update.remove("meatyieldweight");
                            }
                            try {
                                String weightinKiloGrams = modal_b2BCartItemDetails.getMeatyieldweight();
                                weightinKiloGrams = weightinKiloGrams.replaceAll("[^\\d.]", "");
                                if(weightinKiloGrams.equals("") || weightinKiloGrams.equals(null)){
                                    weightinKiloGrams = "0";
                                }


                                String weightinGrams = ConvertKilogramstoGrams(weightinKiloGrams);
                                double weightinGrams_double = Double.parseDouble(weightinGrams);
                                jsonToADD_Or_Update.put("meatyieldweight", weightinGrams_double);
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("meatyieldweight","");
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("meatyieldweight","");

                            e.printStackTrace();
                        }

                        try {
                            if(jsonToADD_Or_Update.has("gender")) {
                                jsonToADD_Or_Update.remove("gender");
                            }
                            try {
                                jsonToADD_Or_Update.put("gender", modal_b2BCartItemDetails.getGender());
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("gender","");

                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("gender","");
                            e.printStackTrace();
                        }

                        try {
                            if(jsonToADD_Or_Update.has("orderid")) {
                                jsonToADD_Or_Update.remove("orderid");
                            }
                            try {
                                jsonToADD_Or_Update.put("orderid", orderid );
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("orderid","");

                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("orderid","");
                            e.printStackTrace();
                        }

                        try {
                            if(jsonToADD_Or_Update.has("orderplaceddate")) {
                                jsonToADD_Or_Update.remove("orderplaceddate");
                            }
                            try {
                                jsonToADD_Or_Update.put("orderplaceddate", checkOut_activity.orderplaceddate );
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("orderplaceddate","");

                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("orderplaceddate","");
                            e.printStackTrace();
                        }


                        try {
                            if(jsonToADD_Or_Update.has("deliverycentrekey")) {
                                jsonToADD_Or_Update.remove("deliverycentrekey");
                            }
                            try {
                                jsonToADD_Or_Update.put("deliverycentrekey", checkOut_activity.deliveryCentreKey );
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("deliverycentrekey","");

                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("deliverycentrekey","");
                            e.printStackTrace();
                        }



                        try {
                            if(jsonToADD_Or_Update.has("retailerkey")) {
                                jsonToADD_Or_Update.remove("retailerkey");
                            }
                            try {
                                jsonToADD_Or_Update.put("retailerkey", checkOut_activity.retailerKey );
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("retailerkey","");

                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("retailerkey","");
                            e.printStackTrace();
                        }

                        try {
                            if(jsonToADD_Or_Update.has("retailermobileno")) {
                                jsonToADD_Or_Update.remove("retailermobileno");
                            }
                            try {
                                jsonToADD_Or_Update.put("retailermobileno", checkOut_activity.retailermobileno );
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("retailermobileno","");

                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("retailermobileno","");
                            e.printStackTrace();
                        }

                        try {
                            if(jsonToADD_Or_Update.has("retaileraddress")) {
                                jsonToADD_Or_Update.remove("retaileraddress");
                            }
                            try {
                                jsonToADD_Or_Update.put("retaileraddress", checkOut_activity.retaileraddress );
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("retaileraddress","");

                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("retaileraddress","");
                            e.printStackTrace();
                        }




                        try {
                            if(jsonToADD_Or_Update.has("gradename")) {
                                jsonToADD_Or_Update.remove("gradename");
                            }
                            try {
                                jsonToADD_Or_Update.put("gradename", modal_b2BCartItemDetails.getGradename());
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("gradename","");

                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("gradename","");
                            e.printStackTrace();
                        }




                        try {
                            if(jsonToADD_Or_Update.has("b2bctgykey")) {
                                jsonToADD_Or_Update.remove("b2bctgykey");
                            }
                            try {
                                jsonToADD_Or_Update.put("b2bctgykey", modal_b2BCartItemDetails.getB2bctgykey());
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("b2bctgykey","");
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("b2bctgykey","");
                            e.printStackTrace();
                        }


                        try {
                            if(jsonToADD_Or_Update.has("b2bsubctgykey")) {
                                jsonToADD_Or_Update.remove("b2bsubctgykey");
                            }
                            try {
                                jsonToADD_Or_Update.put("b2bsubctgykey", modal_b2BCartItemDetails.getB2bsubctgykey());
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("b2bsubctgykey","");
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("b2bsubctgykey","");
                            e.printStackTrace();
                        }

                        try {
                            if(jsonToADD_Or_Update.has("barcodeno")) {
                                jsonToADD_Or_Update.remove("barcodeno");
                            }
                            try {
                                jsonToADD_Or_Update.put("barcodeno", modal_b2BCartItemDetails.getBarcodeno().toUpperCase());
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("barcodeno","");
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("barcodeno","");
                            e.printStackTrace();
                        }

                        try {
                            if(jsonToADD_Or_Update.has("batchno")) {
                                jsonToADD_Or_Update.remove("batchno");
                            }
                            try {
                                jsonToADD_Or_Update.put("batchno", modal_b2BCartItemDetails.getBatchno());
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("batchno","");
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("batchno","");
                            e.printStackTrace();
                        }



                        try {
                            if(jsonToADD_Or_Update.has("partsweight")) {
                                jsonToADD_Or_Update.remove("partsweight");
                            }
                            try {
                                String weightinKiloGrams = modal_b2BCartItemDetails.getPartsweight();
                                weightinKiloGrams = weightinKiloGrams.replaceAll("[^\\d.]", "");
                                if(weightinKiloGrams.equals("") || weightinKiloGrams.equals(null)){
                                    weightinKiloGrams = "0";
                                }


                                String weightinGrams = ConvertKilogramstoGrams(weightinKiloGrams);
                                double weightinGrams_double = Double.parseDouble(weightinGrams);
                                jsonToADD_Or_Update.put("partsweight", weightinGrams_double);
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("partsweight","");
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("partsweight","");

                            e.printStackTrace();
                        }


                        try {
                            if(jsonToADD_Or_Update.has("approxliveweight")) {
                                jsonToADD_Or_Update.remove("approxliveweight");
                            }
                            try {
                                String weightinKiloGrams = modal_b2BCartItemDetails.getApproxliveweight();
                                weightinKiloGrams = weightinKiloGrams.replaceAll("[^\\d.]", "");
                                if(weightinKiloGrams.equals("") || weightinKiloGrams.equals(null)){
                                    weightinKiloGrams = "0";
                                }


                                String weightinGrams = ConvertKilogramstoGrams(weightinKiloGrams);
                                double weightinGrams_double = Double.parseDouble(weightinGrams);
                                jsonToADD_Or_Update.put("approxliveweight", weightinGrams_double);
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("approxliveweight","");
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("approxliveweight","");

                            e.printStackTrace();
                        }

                        try {
                            if(jsonToADD_Or_Update.has("totalweight")) {
                                jsonToADD_Or_Update.remove("totalweight");
                            }
                            try {
                                String weightinKiloGrams = modal_b2BCartItemDetails.getTotalItemWeight();
                                weightinKiloGrams = weightinKiloGrams.replaceAll("[^\\d.]", "");
                                if(weightinKiloGrams.equals("") || weightinKiloGrams.equals(null)){
                                    weightinKiloGrams = "0";
                                }


                                String weightinGrams = ConvertKilogramstoGrams(weightinKiloGrams);
                                double weightinGrams_double = Double.parseDouble(weightinGrams);
                                jsonToADD_Or_Update.put("totalweight", weightinGrams_double);
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("totalweight","");
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("totalweight","");

                            e.printStackTrace();
                        }

                        try {
                            if(jsonToADD_Or_Update.has("price")) {
                                jsonToADD_Or_Update.remove("price");
                            }
                            try {
                                jsonToADD_Or_Update.put("price", modal_b2BCartItemDetails.getItemprice());
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("price","");
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("price","");
                            e.printStackTrace();
                        }



                        try {
                            if(jsonToADD_Or_Update.has("totalprice")) {
                                jsonToADD_Or_Update.remove("totalprice");
                            }
                            try {
                                jsonToADD_Or_Update.put("totalprice", modal_b2BCartItemDetails.getTotalPrice_ofItem());
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("totalprice","");
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("totalprice","");
                            e.printStackTrace();
                        }



                        try {
                            if(jsonToADD_Or_Update.has("discount")) {
                                jsonToADD_Or_Update.remove("discount");
                            }
                            try {
                                jsonToADD_Or_Update.put("discount", modal_b2BCartItemDetails.getDiscount());
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("discount","");
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("discount","");
                            e.printStackTrace();
                        }




                        try {
                            if(jsonToADD_Or_Update.has("discountweightageamount")) {
                                jsonToADD_Or_Update.remove("discountweightageamount");
                            }
                            try {
                                jsonToADD_Or_Update.put("discountweightageamount", modal_b2BCartItemDetails.getDicountweightageAmount());
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("discountweightageamount","");
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("discountweightageamount","");
                            e.printStackTrace();
                        }


                        try {
                            if(jsonToADD_Or_Update.has("status")) {
                                jsonToADD_Or_Update.remove("status");
                            }
                            try {
                                jsonToADD_Or_Update.put("status", Constants.orderItemDetailsStatus_NOTDelivered);
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("status","");

                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("status","");
                            e.printStackTrace();
                        }



                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                    try{


                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,jsonToADD_Or_Update,
                                response -> {
                                    if (callback_OrderItemDetailsInterface != null) {
                                        try {
                                            String statusCode = response.getString("statusCode");
                                            if(statusCode.equals("400")){
                                                callback_OrderItemDetailsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                            }
                                            else if(statusCode.equals("200")){


                                            }
                                            else{

                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            callback_OrderItemDetailsInterface.notifyProcessingError(e);
                                        }
                                    }
                                }, error -> {


                            callback_OrderItemDetailsInterface.notifyVolleyError( error);

                            error.printStackTrace();
                        })
                        {
                            @Override
                            public Map<String, String> getParams() {
                                final Map<String, String> params = new HashMap<>();
                                params.put("modulename", "Store");
                                return params;
                            }


                            @NonNull
                            @Override
                            public Map<String, String> getHeaders() {
                                final Map<String, String> header = new HashMap<>();
                                header.put("Content-Type", "application/json");

                                return header;
                            }
                        };
                        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(40000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        // Make the request
                        volleyrequestqueuehelper.getInstance().addToRequestQueue(jsonObjectRequest);

                        if(i == (earTagDetailsArrayList_String.size()-1)){
                            callback_OrderItemDetailsInterface.notifySuccess(Constants.successResult_volley);

                        }



                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }




                }


            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addEntryInBatchDetails() {

        try{
            for(int iterator =0 ; iterator < earTagDetailsArrayList_String.size();iterator++){
                String barcodeString = earTagDetailsArrayList_String.get(iterator);
                if(earTagDetailsHashMap.containsKey(barcodeString)){

                    Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagDetailsHashMap.get(barcodeString);
                    try {
                        try {
                            if(jsonToADD_Or_Update.has("weightingrams")) {
                                jsonToADD_Or_Update.remove("weightingrams");
                            }
                            try {
                                String weightinKiloGrams = modal_goatEarTagDetails.getNewWeight_forBillingScreen();
                                weightinKiloGrams = weightinKiloGrams.replaceAll("[^\\d.]", "");
                                if(weightinKiloGrams.equals("") || weightinKiloGrams.equals(null)){
                                    weightinKiloGrams = "0";
                                }


                                String weightinGrams = ConvertKilogramstoGrams(weightinKiloGrams);
                                double weightinGrams_double = Double.parseDouble(weightinGrams);
                                jsonToADD_Or_Update.put("weightingrams", weightinGrams_double);
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("weightingrams","");
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("weightingrams","");

                            e.printStackTrace();
                        }

                        try {
                            if(jsonToADD_Or_Update.has("gender")) {
                                jsonToADD_Or_Update.remove("gender");
                            }
                            try {
                                 jsonToADD_Or_Update.put("gender", modal_goatEarTagDetails.getGender());
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("gender","");

                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("gender","");
                            e.printStackTrace();
                        }


                        try {
                            if(jsonToADD_Or_Update.has("gradename")) {
                                jsonToADD_Or_Update.remove("gradename");
                            }
                            try {
                                jsonToADD_Or_Update.put("gradename", modal_goatEarTagDetails.getGradename());
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("gradename","");

                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("gradename","");
                            e.printStackTrace();
                        }

                        try {
                            if(jsonToADD_Or_Update.has("gradeprice")) {
                                jsonToADD_Or_Update.remove("gradeprice");
                            }
                            try {
                                jsonToADD_Or_Update.put("gradeprice", modal_goatEarTagDetails.getGradeprice());
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("gradeprice","");

                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("gradeprice","");
                            e.printStackTrace();
                        }
                        try {
                            if(jsonToADD_Or_Update.has("gradekey")) {
                                jsonToADD_Or_Update.remove("gradekey");
                            }
                            try {
                                jsonToADD_Or_Update.put("gradekey", modal_goatEarTagDetails.getGradekey());
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("gradekey","");

                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("gradekey","");
                            e.printStackTrace();
                        }


                        try {
                            if(jsonToADD_Or_Update.has("b2bctgykey")) {
                                jsonToADD_Or_Update.remove("b2bctgykey");
                            }
                            try {
                                jsonToADD_Or_Update.put("b2bctgykey", modal_goatEarTagDetails.getB2bctgykey());
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("b2bctgykey","");
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("b2bctgykey","");
                            e.printStackTrace();
                        }


                        try {
                            if(jsonToADD_Or_Update.has("b2bsubctgykey")) {
                                jsonToADD_Or_Update.remove("b2bsubctgykey");
                            }
                            try {
                                jsonToADD_Or_Update.put("b2bsubctgykey", modal_goatEarTagDetails.getB2bsubctgykey());
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("b2bsubctgykey","");
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("b2bsubctgykey","");
                            e.printStackTrace();
                        }

                        try {
                            if(jsonToADD_Or_Update.has("barcodeno")) {
                                jsonToADD_Or_Update.remove("barcodeno");
                            }
                            try {
                                jsonToADD_Or_Update.put("barcodeno", modal_goatEarTagDetails.getBarcodeno().toUpperCase());
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("barcodeno","");
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("barcodeno","");
                            e.printStackTrace();
                        }

                        try {
                            if(jsonToADD_Or_Update.has("batchno")) {
                                jsonToADD_Or_Update.remove("batchno");
                            }
                            try {
                                jsonToADD_Or_Update.put("batchno", modal_goatEarTagDetails.getBatchno());
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("batchno","");
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("batchno","");
                            e.printStackTrace();
                        }



                        try {
                            if(jsonToADD_Or_Update.has("discountweightageamount")) {

                                String discountweightageamount = modal_goatEarTagDetails.getDicountweightageAmount();
                                discountweightageamount = discountweightageamount.replaceAll("[^\\d.]", "");
                                if (discountweightageamount.equals("") || discountweightageamount.equals(null)) {
                                    discountweightageamount = "0";
                                }


                                double discountweightageamount_double = Double.parseDouble(discountweightageamount);
                                jsonToADD_Or_Update.put("discountweightageamount", discountweightageamount_double);
                            }
                            else{
                                jsonToADD_Or_Update.put("discountweightageamount","");
                            }
                        }
                        catch (Exception e){
                            jsonToADD_Or_Update.put("discountweightageamount","");
                            e.printStackTrace();
                        }

                    /*    try {
                            if(jsonToADD_Or_Update.has("discountweightageamount")) {
                                jsonToADD_Or_Update.remove("discountweightageamount");
                            }
                            try {
                                jsonToADD_Or_Update.put("discountweightageamount", modal_goatEarTagDetails.getDicountweightageAmount());
                            }
                            catch (Exception e){
                                jsonToADD_Or_Update.put("discountweightageamount","");
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            jsonToADD_Or_Update.put("discountweightageamount","");
                            e.printStackTrace();
                        }

                     */






                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }




            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,jsonToADD_Or_Update,
                    response -> {
                        if (callback_OrderItemDetailsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callback_OrderItemDetailsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){



                                   // callback_OrderItemDetailsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                 //   callback_OrderItemDetailsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callback_OrderItemDetailsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback_OrderItemDetailsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callback_OrderItemDetailsInterface.notifyVolleyError( error);

                error.printStackTrace();
            })
            {
                @Override
                public Map<String, String> getParams() {
                    final Map<String, String> params = new HashMap<>();
                    params.put("modulename", "Store");
                    return params;
                }


                @NonNull
                @Override
                public Map<String, String> getHeaders() {
                    final Map<String, String> header = new HashMap<>();
                    header.put("Content-Type", "application/json");

                    return header;
                }
            };
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(40000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Make the request
            volleyrequestqueuehelper.getInstance().addToRequestQueue(jsonObjectRequest);

                    if(iterator == (earTagDetailsArrayList_String.size()-1)){
                        callback_OrderItemDetailsInterface.notifySuccess(Constants.successResult_volley);

                    }

                }
                else{
                    if(iterator == (earTagDetailsArrayList_String.size()-1)){
                        callback_OrderItemDetailsInterface.notifySuccess(Constants.successResult_volley);

                    }

                }
            }




        }
        catch (Exception e){
            e.printStackTrace();
            callback_OrderItemDetailsInterface.notifyProcessingError(e);
        }

    }


    private void getDataFromBatchDetails() {
        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApitoCall,null,
                    response -> {
                        if (callback_OrderItemDetailsInterface != null) {
                            try {
                                JSONArray JArray = response.getJSONArray("content");


                                if (JArray.length() == 0) {
                                    callback_OrderItemDetailsInterface.notifySuccess(Constants.emptyResult_volley);

                                }
                                else {


                                    int i1 = 0;
                                    int arrayLength = JArray.length();

                                    if (arrayLength > 0) {
                                        for (; i1 < (arrayLength); i1++) {
                                            JSONObject json = JArray.getJSONObject(i1);
                                            Modal_B2BOrderItemDetails modal_b2BOrderItemDetails = new Modal_B2BOrderItemDetails();
                                            try {
                                                if (json.has("weightingrams")) {
                                                    modal_b2BOrderItemDetails.weightingrams_static = ConvertGramsToKilograms( String.valueOf(json.get("weightingrams")));
                                                    modal_b2BOrderItemDetails.weightingrams = ConvertGramsToKilograms( String.valueOf(json.get("weightingrams")));

                                                } else {
                                                    modal_b2BOrderItemDetails.weightingrams_static = "";
                                                    modal_b2BOrderItemDetails.weightingrams  = "";

                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.weightingrams_static = "";
                                                modal_b2BOrderItemDetails.weightingrams  = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("gradename")) {
                                                    //     modal_b2BOrderItemDetails.gradename = ( String.valueOf(json.get("gradename")));
                                                    modal_b2BOrderItemDetails.gradename_static = ConvertFirstLetter_to_Capital( String.valueOf(json.get("gradename")));
                                                    modal_b2BOrderItemDetails.gradename  = ConvertFirstLetter_to_Capital( String.valueOf(json.get("gradename")));

                                                } else {
                                                    modal_b2BOrderItemDetails.gradename_static = "";
                                                    modal_b2BOrderItemDetails.gradename  = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.gradename_static = "";
                                                modal_b2BOrderItemDetails.gradename  = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("gradeprice")) {
                                                    modal_b2BOrderItemDetails.gradeprice_static = ( String.valueOf(json.get("gradeprice")));
                                                    modal_b2BOrderItemDetails.gradeprice  = ( String.valueOf(json.get("gradeprice")));

                                                } else {
                                                    modal_b2BOrderItemDetails.gradeprice_static = "";
                                                    modal_b2BOrderItemDetails.gradeprice  = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.gradeprice  = "";

                                                modal_b2BOrderItemDetails.gradeprice_static = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("gradekey")) {
                                                    modal_b2BOrderItemDetails.gradekey_static = ( String.valueOf(json.get("gradekey")));
                                                    modal_b2BOrderItemDetails.gradekey = ( String.valueOf(json.get("gradekey")));

                                                } else {
                                                    modal_b2BOrderItemDetails.gradekey_static = "";
                                                    modal_b2BOrderItemDetails.gradekey  = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.gradekey_static = "";
                                                modal_b2BOrderItemDetails.gradekey  = "";
                                                e.printStackTrace();
                                            }
                                            try {
                                                if (json.has("supplierkey")) {
                                                    modal_b2BOrderItemDetails.supplierkey_static = ( String.valueOf(json.get("supplierkey")));
                                                    modal_b2BOrderItemDetails.supplierkey  = ( String.valueOf(json.get("supplierkey")));

                                                } else {
                                                    modal_b2BOrderItemDetails.supplierkey_static = "";
                                                    modal_b2BOrderItemDetails.supplierkey  = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.supplierkey_static = "";
                                                modal_b2BOrderItemDetails.supplierkey  = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("suppliername")) {
                                                    modal_b2BOrderItemDetails.suppliername_static = ( String.valueOf(json.get("suppliername")));
                                                    modal_b2BOrderItemDetails.suppliername  = ( String.valueOf(json.get("suppliername")));

                                                } else {
                                                    modal_b2BOrderItemDetails.suppliername_static = "";
                                                    modal_b2BOrderItemDetails.suppliername = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.suppliername_static = "";
                                                modal_b2BOrderItemDetails.suppliername = "";
                                                e.printStackTrace();
                                            }




                                            try {
                                                if (json.has("batchno")) {
                                                    modal_b2BOrderItemDetails.batchno_static = String.valueOf(json.get("batchno"));
                                                    modal_b2BOrderItemDetails.batchno = String.valueOf(json.get("batchno"));

                                                } else {
                                                    modal_b2BOrderItemDetails.batchno_static = "";
                                                    modal_b2BOrderItemDetails.batchno  = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.batchno_static = "";
                                                modal_b2BOrderItemDetails.batchno  = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("barcodeno")) {
                                                    modal_b2BOrderItemDetails.barcodeno_static = String.valueOf(json.get("barcodeno")).toUpperCase();
                                                    modal_b2BOrderItemDetails.barcodeno  = String.valueOf(json.get("barcodeno")).toUpperCase();

                                                } else {
                                                    modal_b2BOrderItemDetails.barcodeno_static = "";
                                                    modal_b2BOrderItemDetails.barcodeno  = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.barcodeno  = "";
                                                modal_b2BOrderItemDetails.barcodeno_static = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("breedtype")) {
                                                    modal_b2BOrderItemDetails.breedtype_static = String.valueOf(json.get("breedtype"));
                                                    modal_b2BOrderItemDetails.breedtype  = String.valueOf(json.get("breedtype"));

                                                } else {
                                                    modal_b2BOrderItemDetails.breedtype_static = "";
                                                    modal_b2BOrderItemDetails.breedtype  = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.breedtype_static = "";
                                                modal_b2BOrderItemDetails.breedtype  = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("gender")) {
                                                    if(String.valueOf(json.get("gender")).toUpperCase().contains("BABY")){
                                                        modal_b2BOrderItemDetails.gender_static = "FEMALE";
                                                        modal_b2BOrderItemDetails.gender = "FEMALE";
                                                    }
                                                    else{
                                                        modal_b2BOrderItemDetails.gender_static = String.valueOf(json.get("gender"));
                                                        modal_b2BOrderItemDetails.gender  = String.valueOf(json.get("gender"));


                                                    }


                                                } else {
                                                    modal_b2BOrderItemDetails.gender_static = "";
                                                    modal_b2BOrderItemDetails.gender  = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.gender_static = "";
                                                modal_b2BOrderItemDetails.gender  = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("status")) {
                                                    modal_b2BOrderItemDetails.status_static = String.valueOf(json.get("status"));
                                                    modal_b2BOrderItemDetails.status  = String.valueOf(json.get("status"));

                                                } else {
                                                    modal_b2BOrderItemDetails.status_static = "";
                                                    modal_b2BOrderItemDetails.status  = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.status_static = "";
                                                modal_b2BOrderItemDetails.status  = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("orderid")) {
                                                    modal_b2BOrderItemDetails.orderid_static = String.valueOf(json.get("orderid"));
                                                    modal_b2BOrderItemDetails.orderid = String.valueOf(json.get("orderid"));

                                                } else {
                                                    modal_b2BOrderItemDetails.orderid_static = "";
                                                    modal_b2BOrderItemDetails.orderid = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.orderid = "";
                                                modal_b2BOrderItemDetails.orderid_static = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("itemaddeddate")) {
                                                    modal_b2BOrderItemDetails.itemaddeddate_static = String.valueOf(json.get("itemaddeddate"));
                                                    modal_b2BOrderItemDetails.itemaddeddate  = String.valueOf(json.get("itemaddeddate"));

                                                } else {
                                                    modal_b2BOrderItemDetails.itemaddeddate_static = "";
                                                    modal_b2BOrderItemDetails.itemaddeddate  = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.itemaddeddate_static = "";
                                                modal_b2BOrderItemDetails.itemaddeddate = "";
                                                e.printStackTrace();
                                            }



                                            try {
                                                if (json.has("b2bsubctgykey")) {
                                                    modal_b2BOrderItemDetails.b2bsubctgykey_static = String.valueOf(json.get("b2bsubctgykey"));
                                                    modal_b2BOrderItemDetails.b2bsubctgykey = String.valueOf(json.get("b2bsubctgykey"));

                                                } else {
                                                    modal_b2BOrderItemDetails.b2bsubctgykey_static = "";
                                                    modal_b2BOrderItemDetails.b2bsubctgykey  = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.b2bsubctgykey_static = "";
                                                modal_b2BOrderItemDetails.b2bsubctgykey  = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("b2bctgykey")) {
                                                    modal_b2BOrderItemDetails.b2bctgykey_static = String.valueOf(json.get("b2bctgykey"));
                                                    modal_b2BOrderItemDetails.b2bctgykey  = String.valueOf(json.get("b2bctgykey"));

                                                } else {
                                                    modal_b2BOrderItemDetails.b2bctgykey_static = "";
                                                    modal_b2BOrderItemDetails.b2bctgykey = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.b2bctgykey_static = "";
                                                modal_b2BOrderItemDetails.b2bctgykey  = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("oldweightingrams")) {
                                                    modal_b2BOrderItemDetails.oldweightingrams_static = ConvertGramsToKilograms(String.valueOf(json.get("oldweightingrams")));
                                                    modal_b2BOrderItemDetails.oldweightingrams  = ConvertGramsToKilograms(String.valueOf(json.get("oldweightingrams")));

                                                } else {
                                                    modal_b2BOrderItemDetails.oldweightingrams_static = "";
                                                    modal_b2BOrderItemDetails.oldweightingrams  = "";

                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.oldweightingrams  = "";
                                                modal_b2BOrderItemDetails.oldweightingrams_static = "";
                                                e.printStackTrace();
                                            }




                                            try {
                                                if (json.has("partsweight")) {
                                                    modal_b2BOrderItemDetails.partsweight = ConvertGramsToKilograms(String.valueOf(json.get("partsweight")));
                                                    Modal_B2BOrderItemDetails.partsweight_static = ConvertGramsToKilograms(String.valueOf(json.get("partsweight")));

                                                } else {
                                                    modal_b2BOrderItemDetails.partsweight = "";
                                                    Modal_B2BOrderItemDetails.partsweight_static = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.partsweight = "";
                                                Modal_B2BOrderItemDetails.partsweight_static = "";

                                                e.printStackTrace();
                                            }



                                            try {
                                                if (json.has("meatyieldweight")) {
                                                    modal_b2BOrderItemDetails.meatyieldweight = ConvertGramsToKilograms(String.valueOf(json.get("meatyieldweight")));
                                                    Modal_B2BOrderItemDetails.meatyieldweight_static = ConvertGramsToKilograms(String.valueOf(json.get("meatyieldweight")));
                                                } else {
                                                    modal_b2BOrderItemDetails.meatyieldweight = "";
                                                    Modal_B2BOrderItemDetails.meatyieldweight_static = "";

                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.meatyieldweight = "";
                                                Modal_B2BOrderItemDetails.meatyieldweight_static = "";
                                                e.printStackTrace();
                                            }





                                            try {
                                                if (json.has("approxliveweight")) {
                                                    modal_b2BOrderItemDetails.approxliveweight = ConvertGramsToKilograms(String.valueOf(json.get("approxliveweight")));
                                                    Modal_B2BOrderItemDetails.approxliveweight_static  = ConvertGramsToKilograms(String.valueOf(json.get("approxliveweight")));
                                                } else {
                                                    modal_b2BOrderItemDetails.approxliveweight = "";
                                                    Modal_B2BOrderItemDetails.approxliveweight_static  = "";

                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.approxliveweight = "";
                                                Modal_B2BOrderItemDetails.approxliveweight_static  = "";

                                                e.printStackTrace();
                                            }



                                            try {
                                                if (json.has("totalprice")) {
                                                    modal_b2BOrderItemDetails.totalPrice_ofItem = String.valueOf(json.get("totalprice"));
                                                    Modal_B2BOrderItemDetails.totalPrice_ofItem_static  = String.valueOf(json.get("totalprice"));
                                                } else {
                                                    modal_b2BOrderItemDetails.totalPrice_ofItem = "";
                                                    Modal_B2BOrderItemDetails.totalPrice_ofItem_static  = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BOrderItemDetails.totalPrice_ofItem_static  = "";
                                                modal_b2BOrderItemDetails.totalPrice_ofItem = "";
                                                e.printStackTrace();
                                            }



                                            try {
                                                if (json.has("price")) {
                                                    modal_b2BOrderItemDetails.itemPrice = String.valueOf(json.get("price"));
                                                    Modal_B2BOrderItemDetails.itemPrice_static = String.valueOf(json.get("price"));

                                                } else {
                                                    modal_b2BOrderItemDetails.itemPrice = "";
                                                    Modal_B2BOrderItemDetails.itemPrice_static = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.itemPrice = "";
                                                Modal_B2BOrderItemDetails.itemPrice_static = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("discount")) {
                                                    modal_b2BOrderItemDetails.discount = String.valueOf(json.get("discount"));
                                                    Modal_B2BOrderItemDetails.discount_static = String.valueOf(json.get("discount"));
                                                } else {
                                                    modal_b2BOrderItemDetails.discount = "0";
                                                    Modal_B2BOrderItemDetails.discount_static = "0";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.discount = "0";
                                                Modal_B2BOrderItemDetails.discount_static = "0";
                                                e.printStackTrace();
                                            }



                                            try {
                                                if (json.has("discountweightageamount")) {
                                                    modal_b2BOrderItemDetails.dicountweightageAmount = String.valueOf(json.get("discountweightageamount"));
                                                    Modal_B2BOrderItemDetails.dicountweightageAmount_static = String.valueOf(json.get("discountweightageamount"));
                                                    modal_b2BOrderItemDetails.discount = String.valueOf(json.get("discountweightageamount"));
                                                    Modal_B2BOrderItemDetails.discount_static = String.valueOf(json.get("discountweightageamount"));
                                                } else {
                                                    modal_b2BOrderItemDetails.dicountweightageAmount = "0";
                                                    Modal_B2BOrderItemDetails.dicountweightageAmount_static = "0";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderItemDetails.dicountweightageAmount = "0";
                                                Modal_B2BOrderItemDetails.dicountweightageAmount_static = "0";
                                                e.printStackTrace();
                                            }



                                            double  meatYield_double =0 , parts_double = 0 , totalWeight_double =0 ;
                                            try{
                                                String text = String.valueOf(modal_b2BOrderItemDetails.getMeatyieldweight());
                                                text = text.replaceAll("[^\\d.]", "");
                                                if(text.equals("")){
                                                    text = "0";
                                                }
                                                meatYield_double = Double.parseDouble(text);
                                            }
                                            catch (Exception e){
                                                meatYield_double = 0;
                                                e.printStackTrace();
                                            }


                                            try{
                                                String text = String.valueOf(modal_b2BOrderItemDetails.getPartsweight());
                                                text = text.replaceAll("[^\\d.]", "");
                                                if(text.equals("")){
                                                    text = "0";
                                                }
                                                parts_double = Double.parseDouble(text);
                                            }
                                            catch (Exception e){
                                                parts_double = 0;
                                                e.printStackTrace();
                                            }

                                            try{
                                                totalWeight_double = meatYield_double+parts_double;
                                            }
                                            catch (Exception e){
                                                totalWeight_double = 0;
                                                e.printStackTrace();
                                            }
                                            modal_b2BOrderItemDetails.setTotalItemWeight(String.valueOf(totalWeight_double));
                                            Modal_B2BOrderItemDetails.totalItemWeight_static = (String.valueOf(totalWeight_double));






                                            orderItemDetailsArrayList.add(modal_b2BOrderItemDetails);
                                            if(arrayLength -1 == i1)
                                            {

                                                if(callMethod.equals(Constants.CallGETMethod)){
                                                    callback_OrderItemDetailsInterface.notifySuccess(Constants.successResult_volley);

                                                }
                                                else  if(callMethod.equals(Constants.CallGETListMethod)){
                                                    callback_OrderItemDetailsInterface.notifySuccessForGettingListItem(orderItemDetailsArrayList);

                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback_OrderItemDetailsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callback_OrderItemDetailsInterface.notifyVolleyError( error);

                error.printStackTrace();
            })
            {
                @Override
                public Map<String, String> getParams() {
                    final Map<String, String> params = new HashMap<>();
                    params.put("modulename", "Store");
                    return params;
                }


                @NonNull
                @Override
                public Map<String, String> getHeaders() {
                    final Map<String, String> header = new HashMap<>();
                    header.put("Content-Type", "application/json");

                    return header;
                }
            };
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(40000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Make the request
            volleyrequestqueuehelper.getInstance().addToRequestQueue(jsonObjectRequest);







        }
        catch (Exception e){
            e.printStackTrace();
            callback_OrderItemDetailsInterface.notifyProcessingError(e);
        }



    }




    private void updateEntryInBatchDetails() {
    }




    private String ConvertFirstLetter_to_Capital(String String_to_convert) {
        try {
            // get First letter of the string
            String firstLetStr = String_to_convert.substring(0, 1);
            // Get remaining letter using substring
            String remLetStr = String_to_convert.substring(1);

            // convert the first letter of String to uppercase
            firstLetStr = firstLetStr.toUpperCase();

            // concantenate the first letter and remaining string
            String firstLetterCapitalizedName = firstLetStr + remLetStr;
            return firstLetterCapitalizedName;
        }
        catch (Exception e){

            e.printStackTrace();
            return String_to_convert;
        }


    }



    private String ConvertGramsToKilograms(String grossWeightingramsString) {
        String weightinKGString = "";
        DecimalFormat df = new DecimalFormat(Constants.threeDecimalPattern);

        try {
            grossWeightingramsString = grossWeightingramsString.replaceAll("[^\\d.]", "");

            if(grossWeightingramsString.equals("") || grossWeightingramsString.equals(null)){
                grossWeightingramsString = "0";
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        float grossweightInGramDouble = 0;
        try{
            grossweightInGramDouble = Float.parseFloat(grossWeightingramsString);
        }
        catch (Exception e){
            grossweightInGramDouble = 0;
            //   e.printStackTrace();
        }
        if(grossweightInGramDouble >0 ) {
            try {
                float temp = grossweightInGramDouble / 1000;
                // double rf = Math.round((temp * 10.0) / 10.0);
                weightinKGString = String.valueOf(temp);
            }
            catch (Exception e){
                weightinKGString = grossWeightingramsString;

                e.printStackTrace();
            }

        }
        else{
            weightinKGString = grossWeightingramsString;
        }
        weightinKGString = df.format(Double.parseDouble(weightinKGString));
        return  weightinKGString;
    }

    private String ConvertKilogramstoGrams(String weightInGrams) {
        String weightinGramsString = "";

        try {
            weightInGrams = weightInGrams.replaceAll("[^\\d.]", "");

            if(weightInGrams.equals("") || weightInGrams.equals(null)){
                weightInGrams = "0";
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        double grossweightInKiloGramDouble = 0;
        try{
            grossweightInKiloGramDouble = Double.parseDouble(weightInGrams);
        }
        catch (Exception e){
            grossweightInKiloGramDouble = 0;
            e.printStackTrace();
        }
        if(grossweightInKiloGramDouble >0 ) {
            try {
                double temp = grossweightInKiloGramDouble * 1000;
                // double rf = Math.round((temp * 10.0) / 10.0);
                weightinGramsString = String.valueOf(temp);
            }
            catch (Exception e){
                weightinGramsString = weightInGrams;

                e.printStackTrace();
            }

        }
        else{
            weightinGramsString = weightInGrams;
        }
        return  weightinGramsString;


    }



}
