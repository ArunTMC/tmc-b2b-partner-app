package com.tmc.tmcb2bpartnerapp.apiRequestServices;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagTransactionInterface;
import com.tmc.tmcb2bpartnerapp.model.Modal_GoatEarTagTransaction;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.volleyrequestqueuehelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoatEarTagTransaction extends AsyncTask<String, String, List<Modal_GoatEarTagTransaction>>{

    String ApitoCall ="", callMethod ="";
    GoatEarTagTransactionInterface callback_GoatEarTagTransactionInterface = null;
    JSONObject jsonToADD_Or_Update = new JSONObject();


    public GoatEarTagTransaction(GoatEarTagTransactionInterface callback_GoatEarTagTransactionInterfacee, String addApiToCall, String callMethod) {
        this.ApitoCall = addApiToCall;
        this.callMethod = callMethod;
        this.callback_GoatEarTagTransactionInterface = callback_GoatEarTagTransactionInterfacee;
        jsonToADD_Or_Update = getJSONforPOJOClass();

    }

        @Override
        protected List<Modal_GoatEarTagTransaction> doInBackground(String... strings) {
            if(callMethod.equals(Constants.CallADDMethod)){
                addEntryInBatchDetails();
            }
            else if(callMethod.equals(Constants.CallGETMethod)){
                getDataFromBatchDetails();
            }
            else if(callMethod.equals(Constants.CallUPDATEMethod)){
                updateEntryInBatchDetails();
            }
        
        
            return null;
        }




    private void getDataFromBatchDetails() {
    }

    private void updateEntryInBatchDetails() {
    }

    private void addEntryInBatchDetails() {


        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,jsonToADD_Or_Update,
                    response -> {
                        if (callback_GoatEarTagTransactionInterface != null) {
                            try {
                                //converting jsonSTRING into array
                                //JSONArray JArray = response.getJSONArray("content");
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    new Modal_GoatEarTagTransaction();
                                    callback_GoatEarTagTransactionInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }

                                else if(statusCode.equals("200")){
                                    new Modal_GoatEarTagTransaction();
                                    callback_GoatEarTagTransactionInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    new Modal_GoatEarTagTransaction();
                                    callback_GoatEarTagTransactionInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                              //  callback_GoatEarTagTransactionInterface.notifySuccess("success");
                            } catch (Exception e) {
                                new Modal_GoatEarTagTransaction();
                                e.printStackTrace();
                                callback_GoatEarTagTransactionInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {

                new Modal_GoatEarTagTransaction();
                callback_GoatEarTagTransactionInterface.notifyVolleyError( error);

                error.printStackTrace();
            })
            {

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
            new Modal_GoatEarTagTransaction();
            callback_GoatEarTagTransactionInterface.notifyProcessingError(e);
        }


    }





    private JSONObject getJSONforPOJOClass() {
        JSONObject jsonObject = new JSONObject();
        try {
            if(!Modal_GoatEarTagTransaction.getBatchno().toString().equals("") && !Modal_GoatEarTagTransaction.getBatchno().toString().equals("null")){
                jsonObject.put("batchno",String.valueOf(Modal_GoatEarTagTransaction.getBatchno()));
            }
            if(!Modal_GoatEarTagTransaction.getBarcodeno().toString().equals("") && !Modal_GoatEarTagTransaction.getBarcodeno().toString().equals("null")){
                jsonObject.put("barcodeno",String.valueOf(Modal_GoatEarTagTransaction.getBarcodeno()));
            }
            if(!Modal_GoatEarTagTransaction.getPreviousweightingrams().toString().equals("") && !Modal_GoatEarTagTransaction.getPreviousweightingrams().toString().equals("null")){
                String weightinGrams_str = Modal_GoatEarTagTransaction.getPreviousweightingrams();
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                    weightinGrams_str = "0";
                }

                weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);

                double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                jsonObject.put("previousweightingrams",weightinGrams_double);
            }
            if(!Modal_GoatEarTagTransaction.getNewweightingrams().toString().equals("") && !Modal_GoatEarTagTransaction.getNewweightingrams().toString().equals("null")){
                String weightinGrams_str = Modal_GoatEarTagTransaction.getNewweightingrams();
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");

                if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                    weightinGrams_str = "0";
                }
                weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);

                double weightinGrams_double = Double.parseDouble(weightinGrams_str);
                jsonObject.put("newweightingrams",weightinGrams_double);
            }
            if(!Modal_GoatEarTagTransaction.getWeighingpurpose().toString().equals("") && !Modal_GoatEarTagTransaction.getWeighingpurpose().toString().equals("null")){
                jsonObject.put("weighingpurpose",String.valueOf(Modal_GoatEarTagTransaction.getWeighingpurpose()));
            }
            if(!Modal_GoatEarTagTransaction.getUpdateddate().toString().equals("") && !Modal_GoatEarTagTransaction.getUpdateddate().toString().equals("null")){
                jsonObject.put("updateddate",String.valueOf(Modal_GoatEarTagTransaction.getUpdateddate()));
            }
            if(!Modal_GoatEarTagTransaction.getBreedtype().toString().equals("") && !Modal_GoatEarTagTransaction.getBreedtype().toString().equals("null")){
                jsonObject.put("breedtype",String.valueOf(Modal_GoatEarTagTransaction.getBreedtype()));
            }
            if(!Modal_GoatEarTagTransaction.getStatus().toString().equals("") && !Modal_GoatEarTagTransaction.getStatus().toString().equals("null")){
                jsonObject.put("status",String.valueOf(Modal_GoatEarTagTransaction.getStatus()));
            }
            if(!Modal_GoatEarTagTransaction.getGender().toString().equals("") && !Modal_GoatEarTagTransaction.getGender().toString().equals("null")){
                jsonObject.put("gender",String.valueOf(Modal_GoatEarTagTransaction.getGender()));
            }
            if(!Modal_GoatEarTagTransaction.getDescription().toString().equals("") && !Modal_GoatEarTagTransaction.getDescription().toString().equals("null")){
                jsonObject.put("description",String.valueOf(Modal_GoatEarTagTransaction.getDescription()));
            }
            if(!Modal_GoatEarTagTransaction.getMobileno().toString().equals("") && !Modal_GoatEarTagTransaction.getMobileno().toString().equals("null")){
                jsonObject.put("mobileno",String.valueOf(Modal_GoatEarTagTransaction.getMobileno()));
            }
            if(!Modal_GoatEarTagTransaction.getGradename().toString().equals("") && !Modal_GoatEarTagTransaction.getGradename().toString().equals("null")){
                jsonObject.put("gradename",ConvertFirstLetter_to_Capital(String.valueOf(Modal_GoatEarTagTransaction.getGradename())));
            }
            if(!Modal_GoatEarTagTransaction.getGradeprice().toString().equals("") && !Modal_GoatEarTagTransaction.getGradeprice().toString().equals("null")){
                jsonObject.put("gradeprice",String.valueOf(Modal_GoatEarTagTransaction.getGradeprice()));
            }
            if(!Modal_GoatEarTagTransaction.getGradekey().toString().equals("") && !Modal_GoatEarTagTransaction.getGradekey().toString().equals("null")){
                jsonObject.put("gradekey",String.valueOf(Modal_GoatEarTagTransaction.getGradekey()));
            }
            if(!Modal_GoatEarTagTransaction.getDeliverycenterkey().toString().equals("") && !Modal_GoatEarTagTransaction.getDeliverycenterkey().toString().equals("null")){
                jsonObject.put("deliverycentrekey",String.valueOf(Modal_GoatEarTagTransaction.getDeliverycenterkey()));
            }
            if(!Modal_GoatEarTagTransaction.getDeliverycentername().toString().equals("") && !Modal_GoatEarTagTransaction.getDeliverycentername().toString().equals("null")){
                jsonObject.put("deliverycentrename",String.valueOf(Modal_GoatEarTagTransaction.getDeliverycentername()));
            }
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
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
