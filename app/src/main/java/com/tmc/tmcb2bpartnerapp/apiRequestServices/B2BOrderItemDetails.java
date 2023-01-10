package com.tmc.tmcb2bpartnerapp.apiRequestServices;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderItemDetailsInterface;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_GoatEarTagDetails;
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
    String ApitoCall ="",callMethod =""; 
    JSONObject jsonToADD_Or_Update;
    Modal_B2BOrderItemDetails modal_b2BOrderItemDetails ;
    ArrayList<Modal_B2BOrderItemDetails> orderItemDetailsArrayList = new ArrayList<>();



    public B2BOrderItemDetails(B2BOrderItemDetailsInterface callback_OrderItemDetailsInterfacee, String getApiToCall, String apiMethodtoCall ,Modal_B2BOrderItemDetails modal_b2BOrderItemDetailss ) {
        this.callback_OrderItemDetailsInterface = callback_OrderItemDetailsInterfacee;
        this.ApitoCall = getApiToCall;
        this.callMethod = apiMethodtoCall;
        this.modal_b2BOrderItemDetails = modal_b2BOrderItemDetailss;
        this.earTagDetailsHashMap = modal_b2BOrderItemDetailss.getEarTagDetailsHashMap();
        this.earTagDetailsArrayList_String = modal_b2BOrderItemDetailss.getEarTagDetailsArrayList_String();

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
                jsonObject.put("status",Constants.orderDetailsStatus_Delivered);
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
            addEntryInBatchDetails();
        }
        else if(callMethod.equals(Constants.CallGETMethod) || callMethod.equals(Constants.CallGETListMethod) || callMethod.equals(Constants.CallGETLastEntryMethod)){
            getDataFromBatchDetails();
        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){
            updateEntryInBatchDetails();
        }



        return null;
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
                                jsonToADD_Or_Update.put("barcodeno", modal_goatEarTagDetails.getBarcodeno());
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
                                                    modal_b2BOrderItemDetails.barcodeno_static = String.valueOf(json.get("barcodeno"));
                                                    modal_b2BOrderItemDetails.barcodeno  = String.valueOf(json.get("barcodeno"));

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
