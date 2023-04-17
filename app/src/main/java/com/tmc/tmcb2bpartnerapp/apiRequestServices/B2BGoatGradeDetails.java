package com.tmc.tmcb2bpartnerapp.apiRequestServices;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BGoatGradeDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
import com.tmc.tmcb2bpartnerapp.utils.volleyrequestqueuehelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class B2BGoatGradeDetails extends AsyncTask<String, String, ArrayList<Modal_B2BGoatGradeDetails>> {
        B2BGoatGradeDetailsInterface callback_goatGradeDetailsInterface;
        String ApitoCall ="" , callMethod ="";
        JSONObject jsonToADD_Or_Update;
        ArrayList<Modal_B2BGoatGradeDetails> goatGradeDetailsArrayList = new ArrayList<>();


    public B2BGoatGradeDetails(B2BGoatGradeDetailsInterface callback_goatGradeDetailsInterfacee, String getApiToCall, String apiMethodtoCall) {
        this.callback_goatGradeDetailsInterface = callback_goatGradeDetailsInterfacee;
        this.ApitoCall = getApiToCall;
        this.callMethod = apiMethodtoCall;
        if(callMethod.equals(Constants.CallADDMethod) ){
            jsonToADD_Or_Update = getJSONforPOJOClass();

        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){
            jsonToADD_Or_Update = getJSONforPOJOClass();

        }
        else if(callMethod.equals(Constants.CallGETMethod)){
            new Modal_B2BGoatGradeDetails();
        }
        else if(callMethod.equals(Constants.CallDELETEMethod)){
        }
        else if(callMethod.equals(Constants.CallGETListMethod) || callMethod.equals(Constants.CallGETLastEntryMethod)){
            new Modal_B2BGoatGradeDetails();
        }
    }




    @Override
    protected ArrayList<Modal_B2BGoatGradeDetails> doInBackground(String... strings) {

        if(callMethod.equals(Constants.CallADDMethod)){
            addEntryInDatabase();
        }
        else if(callMethod.equals(Constants.CallGETMethod) || callMethod.equals(Constants.CallGETListMethod) || callMethod.equals(Constants.CallGETLastEntryMethod)){
            getDataFromDatabase();
        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){
            updateEntryInDatabase();
        }
        else if(callMethod.equals(Constants.CallDELETEMethod)){
            deleteEntryInDatabase();

        }

        return null;
    }

    private void addEntryInDatabase() {

        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,jsonToADD_Or_Update,
                    response -> {
                        if (callback_goatGradeDetailsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                JSONObject jsonObject = response.getJSONObject("content");
                                if(statusCode.equals("400")){
                                    callback_goatGradeDetailsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){
                                    try{
                                        if(jsonObject.has("key")){
                                            Modal_B2BGoatGradeDetails.setKey_static(jsonObject.getString("key"));
                                        }
                                        else{
                                            Modal_B2BGoatGradeDetails.setKey_static("");
                                        }


                                        if(jsonObject.has("name")){
                                            Modal_B2BGoatGradeDetails.setName_static(jsonObject.getString("name"));
                                        }
                                        else{
                                            Modal_B2BGoatGradeDetails.setName_static("");
                                        }




                                        if(jsonObject.has("description")){
                                            Modal_B2BGoatGradeDetails.setDescription_static(jsonObject.getString("description"));
                                        }
                                        else{
                                            Modal_B2BGoatGradeDetails.setDescription_static("");
                                        }


                                        if(jsonObject.has("price")){
                                            Modal_B2BGoatGradeDetails.setPrice_static(jsonObject.getString("price"));
                                        }
                                        else{
                                            Modal_B2BGoatGradeDetails.setPrice_static("");
                                        }
                                        if(jsonObject.has("deliverycentrekey")){
                                            Modal_B2BGoatGradeDetails.setDeliverycenterkey_static(jsonObject.getString("deliverycentrekey"));
                                        }
                                        else{
                                            Modal_B2BGoatGradeDetails.setDeliverycenterkey_static("");
                                        }

                                        if(jsonObject.has("deliverycentrename")){
                                            Modal_B2BGoatGradeDetails.setDeliverycentername_static(jsonObject.getString("deliverycentrename"));
                                        }
                                        else{
                                            Modal_B2BGoatGradeDetails.setDeliverycentername_static("");
                                        }





                                    }
                                    catch (Exception e){
                                        e.printStackTrace();
                                    }


                                    callback_goatGradeDetailsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callback_goatGradeDetailsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callback_goatGradeDetailsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback_goatGradeDetailsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callback_goatGradeDetailsInterface.notifyVolleyError( error);

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
            callback_goatGradeDetailsInterface.notifyProcessingError(e);
        }




    }
    private void getDataFromDatabase() {
        goatGradeDetailsArrayList.clear();
            DatabaseArrayList_PojoClass.goatGradeDetailsArrayList.clear();
        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApitoCall,null,
                    response -> {
                        if (callback_goatGradeDetailsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callback_goatGradeDetailsInterface.notifySuccess(Constants.item_not_Found_volley);

                                }
                                else if(statusCode.equals("200")){

                                    JSONArray JArray = response.getJSONArray("content");
                                    //Log.d(Constants.TAG, "convertingJsonStringintoArray Response: " + JArray);
                                    int i1 = 0;
                                    int arrayLength = JArray.length();
                                    //Log.d("Constants.TAG", "convertingJsonStringintoArray Response: " + arrayLength);
                                    if (JArray.length() == 0) {
                                        callback_goatGradeDetailsInterface.notifySuccess(Constants.emptyResult_volley);

                                    }
                                    if(arrayLength>0) {
                                        for (; i1 < (arrayLength); i1++) {
                                            JSONObject json = JArray.getJSONObject(i1);

                                            Modal_B2BGoatGradeDetails modal_b2BGoatGradeDetails = new Modal_B2BGoatGradeDetails();

                                            try {
                                                if (json.has("name")) {
                                                  //  modal_b2BGoatGradeDetails.name = String.valueOf(json.get("name"));
                                                    modal_b2BGoatGradeDetails.name = ConvertFirstLetter_to_Capital(String.valueOf(json.get("name")));

                                                } else {
                                                    modal_b2BGoatGradeDetails.name = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BGoatGradeDetails.name = "";
                                                e.printStackTrace();
                                            }



                                            try {
                                                if (json.has("description")) {
                                                    modal_b2BGoatGradeDetails.description = String.valueOf(json.get("description"));
                                                } else {
                                                    modal_b2BGoatGradeDetails.description = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BGoatGradeDetails.description = "";
                                                e.printStackTrace();
                                            }
                                            try {
                                                if (json.has("key")) {
                                                    modal_b2BGoatGradeDetails.key = String.valueOf(json.get("key"));
                                                } else {
                                                    modal_b2BGoatGradeDetails.key = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BGoatGradeDetails.key = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("deliverycentrekey")) {
                                                    modal_b2BGoatGradeDetails.deliverycenterkey = String.valueOf(json.get("deliverycentrekey"));
                                                } else {
                                                    modal_b2BGoatGradeDetails.deliverycenterkey = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BGoatGradeDetails.deliverycenterkey = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("deliverycentrename")) {
                                                    modal_b2BGoatGradeDetails.deliverycentername = String.valueOf(json.get("deliverycentrename"));
                                                } else {
                                                    modal_b2BGoatGradeDetails.deliverycentername = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BGoatGradeDetails.deliverycentername = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("price")) {
                                                    modal_b2BGoatGradeDetails.price = String.valueOf(json.get("price"));
                                                } else {
                                                    modal_b2BGoatGradeDetails.price = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BGoatGradeDetails.price = "";
                                                e.printStackTrace();
                                            }

                                             if(callMethod.equals(Constants.CallGETListMethod)){
                                                 goatGradeDetailsArrayList.add(modal_b2BGoatGradeDetails);
                                                 if(i1 - (arrayLength-1) ==0){

                                                     DatabaseArrayList_PojoClass.setGoatGradeDetailsArrayList(goatGradeDetailsArrayList);
                                                     callback_goatGradeDetailsInterface.notifySuccessForGettingListItem(goatGradeDetailsArrayList);

                                                 }



                                             }
                                             else{
                                                 callback_goatGradeDetailsInterface.notifySuccess(Constants.successResult_volley);

                                             }



                                        }
                                    }



                                }
                                else{
                                    callback_goatGradeDetailsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callback_goatGradeDetailsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback_goatGradeDetailsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callback_goatGradeDetailsInterface.notifyVolleyError( error);

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
            callback_goatGradeDetailsInterface.notifyProcessingError(e);
        }


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

    private void updateEntryInDatabase() {
        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,jsonToADD_Or_Update,
                    response -> {
                        if (callback_goatGradeDetailsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callback_goatGradeDetailsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){



                                    callback_goatGradeDetailsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callback_goatGradeDetailsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callback_goatGradeDetailsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback_goatGradeDetailsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callback_goatGradeDetailsInterface.notifyVolleyError( error);

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
            callback_goatGradeDetailsInterface.notifyProcessingError(e);
        }
    }
    private void deleteEntryInDatabase() {


        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,null,
                    response -> {
                        if (callback_goatGradeDetailsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callback_goatGradeDetailsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){



                                    callback_goatGradeDetailsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callback_goatGradeDetailsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callback_goatGradeDetailsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback_goatGradeDetailsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callback_goatGradeDetailsInterface.notifyVolleyError( error);

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
            callback_goatGradeDetailsInterface.notifyProcessingError(e);
        }

    }

    private JSONObject getJSONforPOJOClass() {
        JSONObject jsonObject = new JSONObject();

        try{
                jsonObject.put("key",Modal_B2BGoatGradeDetails.key_static);

            if(!Modal_B2BGoatGradeDetails.name_static.equals("") && !String.valueOf(Modal_B2BGoatGradeDetails.name_static).toUpperCase().equals("NULL") ){
                jsonObject.put("name",Modal_B2BGoatGradeDetails.name_static);
            }


            if(!Modal_B2BGoatGradeDetails.description_static.equals("") && !String.valueOf(Modal_B2BGoatGradeDetails.description_static).toUpperCase().equals("NULL") ){
                jsonObject.put("description",Modal_B2BGoatGradeDetails.description_static);
            }
            if(!Modal_B2BGoatGradeDetails.price_static.equals("") && !String.valueOf(Modal_B2BGoatGradeDetails.price_static).toUpperCase().equals("NULL") ){
                jsonObject.put("price",Modal_B2BGoatGradeDetails.price_static);
            }
            if(!Modal_B2BGoatGradeDetails.deliverycenterkey_static.equals("") && !String.valueOf(Modal_B2BGoatGradeDetails.deliverycenterkey_static).toUpperCase().equals("NULL") ){
                jsonObject.put("deliverycentrekey",Modal_B2BGoatGradeDetails.deliverycenterkey_static);
            }
            if(!Modal_B2BGoatGradeDetails.deliverycentername_static.equals("") && !String.valueOf(Modal_B2BGoatGradeDetails.deliverycentername_static).toUpperCase().equals("NULL") ){
                jsonObject.put("deliverycentrename",Modal_B2BGoatGradeDetails.deliverycentername_static);
            }
            
        }
        catch (Exception e){
            e.printStackTrace();
        }
        
        return  jsonObject;
        
    }


}
