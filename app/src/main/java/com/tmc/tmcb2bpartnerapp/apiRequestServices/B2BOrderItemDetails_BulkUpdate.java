package com.tmc.tmcb2bpartnerapp.apiRequestServices;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderItemDetails_BulkUpdateInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_Static_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_UpdatedGoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.utils.API_Manager;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.volleyrequestqueuehelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

public class B2BOrderItemDetails_BulkUpdate extends AsyncTask<String, String, List<Modal_Static_GoatEarTagDetails>> {



    String ApitoCall ="", callMethod ="" , orderplaceddate ="" ,usermobileno_string ="" , statustoUpdate  ="" ,orderid ="";
    B2BOrderItemDetails_BulkUpdateInterface callbackB2BOrderItemDetails_BulkUpdateInterface = null;
    
    JSONObject jsontoUpdateEarTagDetails = new JSONObject();
    HashMap<String, Modal_GoatEarTagDetails> earTagDetailsHashMap = new HashMap<>();
    ArrayList<String> earTagDetailsArrayList_String = new ArrayList<>();
    Modal_B2BOrderItemDetails modal_b2BOrderItemDetails ;




    public B2BOrderItemDetails_BulkUpdate(B2BOrderItemDetails_BulkUpdateInterface callBackB2BOrderItemDetails_bulkUpdateInterfacee, String callADDMethod , String orderid , String statusToUpdate , ArrayList<String> earTagDetailsArrayList_Stringg) {
        this.callMethod = callADDMethod;
        this.callbackB2BOrderItemDetails_BulkUpdateInterface = callBackB2BOrderItemDetails_bulkUpdateInterfacee;
        this.orderid = orderid;
        this.earTagDetailsArrayList_String = earTagDetailsArrayList_Stringg;
        this.statustoUpdate = statusToUpdate;

    }




    @Override
    protected List<Modal_Static_GoatEarTagDetails> doInBackground(String... strings) {


        for(int iterator =0 ; iterator < earTagDetailsArrayList_String.size();iterator++) {
            String barcodeString = earTagDetailsArrayList_String.get(iterator);
           // if (earTagDetailsHashMap.containsKey(barcodeString)) {

             //   Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagDetailsHashMap.get(barcodeString);

                try{
                    jsontoUpdateEarTagDetails.put("barcodeno" , barcodeString);
                    jsontoUpdateEarTagDetails.put("orderid",orderid);
                    jsontoUpdateEarTagDetails.put("status" ,statustoUpdate);

                }
                catch (Exception e){
                    e.printStackTrace();
                }



                updateEntryInEarTagDetails();


                if(iterator == (earTagDetailsArrayList_String.size()-1)){


                    try {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                callbackB2BOrderItemDetails_BulkUpdateInterface.notifySuccess(Constants.successResult_volley);

                            }
                        });
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }

          /*  }
            else{
                if(iterator == (earTagDetailsArrayList_String.size()-1)){

                    try {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                callbackB2BOrderItemDetails_BulkUpdateInterface.notifySuccess(Constants.successResult_volley);

                            }
                        });
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

           */
        }




        return null;
    }




    private void updateEntryInEarTagDetails() {

        ApitoCall = API_Manager.updateOrderItemDetails;
        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall, jsontoUpdateEarTagDetails,
                    response -> {

                        String statusCode = null;
                        try {
                            statusCode = response.getString("statusCode");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                        if (statusCode.equals("400")) {
                            JSONObject Json = null;
                            try {
                                Json = response.getJSONObject("content");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Log.d(Constants.TAG, "convertingJsonStringintoArray Response: " + JArray);

                            String message = null;
                            try {
                                message = Json.getString("message");
                                //message = response.getString("message");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (message.toUpperCase().equals(Constants.expressionAttribute_is_empty_volley_syntax)) {
                                try {

                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            callbackB2BOrderItemDetails_BulkUpdateInterface.notifySuccess(Constants.expressionAttribute_is_empty_volley_response);

                                        }
                                    });

                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }

                            } else {

                                try {
                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            callbackB2BOrderItemDetails_BulkUpdateInterface.notifySuccess(Constants.item_not_Found_volley);

                                        }
                                    });
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }

                            }

                        } else if (statusCode.equals("200")) {
                            if (callbackB2BOrderItemDetails_BulkUpdateInterface != null) {

                            }
                        } else {
                            //          callbackB2BOrderItemDetails_BulkUpdateInterface.notifySuccess(Constants.unknown_API_Result_volley);

                        }


                    }, error -> {

                try {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            callbackB2BOrderItemDetails_BulkUpdateInterface.notifyVolleyError(error);
                        }
                    });

                }
                catch (Exception e){
                    e.printStackTrace();
                }


                error.printStackTrace();
            }) {

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
            try {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        callbackB2BOrderItemDetails_BulkUpdateInterface.notifyProcessingError(e);
                    }
                });
            }
            catch (Exception e1){
                e1.printStackTrace();
            }
        }


    }



    private JSONObject getJSON_to_Update_FromPOJOClass() {
        JSONObject jsonObject = new JSONObject();
        try {
            if(!Modal_Static_GoatEarTagDetails.getBatchno().toString().equals("") && !Modal_Static_GoatEarTagDetails.getBatchno().toString().equals("null")){
                jsonObject.put("batchno",String.valueOf(Modal_Static_GoatEarTagDetails.getBatchno()));
            }
            if(!Modal_Static_GoatEarTagDetails.getBarcodeno().toString().equals("") && !Modal_Static_GoatEarTagDetails.getBarcodeno().toString().equals("null")){
                jsonObject.put("barcodeno",String.valueOf(Modal_Static_GoatEarTagDetails.getBarcodeno()));
            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_stockedweightingrams().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_stockedweightingrams().toString().equals("null")){
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_stockedweightingrams_boolean()){
                    String weightinGrams_str = Modal_UpdatedGoatEarTagDetails.getUpdated_stockedweightingrams();
                    weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                    if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                        weightinGrams_str = "0";
                    }

                    weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                    double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                    jsonObject.put("stockedweightingrams",weightinGrams_double);
                }

            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_status().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_status().toString().equals("null")){
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_status_boolean()){

                    jsonObject.put("status", String.valueOf(Modal_UpdatedGoatEarTagDetails.getUpdated_status()));
                }
            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_supplierkey().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_supplierkey().toString().equals("null")){
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_supplierkey_boolean()){
                    jsonObject.put("supplierkey", String.valueOf(Modal_UpdatedGoatEarTagDetails.getUpdated_supplierkey()));
                }
            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_suppliername().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_suppliername().toString().equals("null")){
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_suppliername_boolean()){

                    jsonObject.put("suppliername", String.valueOf(Modal_UpdatedGoatEarTagDetails.getUpdated_suppliername()));
                }
            }

            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_currentweightingrams().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_currentweightingrams().toString().equals("null")){
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_currentweightingrams_boolean()){
                    String weightinGrams_str = Modal_UpdatedGoatEarTagDetails.getUpdated_currentweightingrams();
                    weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                    if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                        weightinGrams_str = "0";
                    }


                    weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);

                    double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                    jsonObject.put("currentweightingrams", weightinGrams_double);
                }
            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_gender().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_gender().toString().equals("null")){
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_gender_boolean()){
                    jsonObject.put("gender", String.valueOf(Modal_UpdatedGoatEarTagDetails.getUpdated_gender()));
                }
            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_breedtype().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_breedtype().toString().equals("null")){
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_breedtype_boolean()){

                    jsonObject.put("breedtype", String.valueOf(Modal_UpdatedGoatEarTagDetails.getUpdated_breedtype()));
                }
            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_loadedweightingrams().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_loadedweightingrams().toString().equals("null")){
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_loadedweightingrams_boolean()){

                    String weightinGrams_str = Modal_UpdatedGoatEarTagDetails.getUpdated_loadedweightingrams();
                    weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                    if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                        weightinGrams_str = "0";
                    }


                    weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);

                    double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                    jsonObject.put("loadedweightingrams", weightinGrams_double);
                }
            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_description().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_description().toString().equals("null")) {
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_description_boolean()){

                    jsonObject.put("description", String.valueOf(Modal_UpdatedGoatEarTagDetails.getUpdated_description()));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
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
