package com.tmc.tmcb2bpartnerapp.apiRequestServices;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BRetailerDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
import com.tmc.tmcb2bpartnerapp.utils.volleyrequestqueuehelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class B2BRetailerDetails extends AsyncTask<String, String, List<Modal_B2BRetailerDetails>> {
    B2BRetailerDetailsInterface callback_retailerDetailsInterface;
    String ApitoCall , callMethod;
    JSONObject jsonToADD_Or_Update = new JSONObject();

   


    public B2BRetailerDetails(B2BRetailerDetailsInterface callback_retailerDetailsInterfacee, String getApiToCall,String apiMethodtoCall) {
        this.callback_retailerDetailsInterface = callback_retailerDetailsInterfacee;
        this.ApitoCall = getApiToCall;
        this.callMethod = apiMethodtoCall;
        if(callMethod.equals(Constants.CallADDMethod) ){
            jsonToADD_Or_Update = getJSONforPOJOClass();

        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){
            jsonToADD_Or_Update = getJSONforPOJOClass();
        
        }
        else if(callMethod.equals(Constants.CallGETMethod)){
            new Modal_B2BRetailerDetails();
        }
        else if(callMethod.equals(Constants.CallGETListMethod) || callMethod.equals(Constants.CallGETLastEntryMethod)){
            new Modal_B2BRetailerDetails();
        }
    }




    @Override
    protected ArrayList<Modal_B2BRetailerDetails> doInBackground(String... strings) {


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

    private void updateEntryInBatchDetails() {

        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,jsonToADD_Or_Update,
                    response -> {
                        if (callback_retailerDetailsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callback_retailerDetailsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){
                                   new Modal_B2BRetailerDetails();


                                    callback_retailerDetailsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callback_retailerDetailsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callback_retailerDetailsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback_retailerDetailsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callback_retailerDetailsInterface.notifyVolleyError( error);

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
            callback_retailerDetailsInterface.notifyProcessingError(e);
        }
    }

    private void getDataFromBatchDetails() {


        ArrayList<Modal_B2BRetailerDetails>retailerDetailsArrayList = new ArrayList<>();

        new Modal_B2BRetailerDetails();
        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApitoCall,null,
                    response -> {
                        if (callback_retailerDetailsInterface != null) {
                            try {
                                //converting jsonSTRING into array
                                JSONArray JArray = response.getJSONArray("content");
                                //Log.d(Constants.TAG, "convertingJsonStringintoArray Response: " + JArray);
                                int i1 = 0;
                                int arrayLength = JArray.length();
                                //Log.d("Constants.TAG", "convertingJsonStringintoArray Response: " + arrayLength);
                                if (JArray.length() == 0) {
                                    callback_retailerDetailsInterface.notifySuccess(Constants.emptyResult_volley);

                                }
                                if(arrayLength>0) {
                                    for (; i1 < (arrayLength); i1++) {
                                        JSONObject json = JArray.getJSONObject(i1);
                                        Modal_B2BRetailerDetails modal_B2BRetailerDetails = new Modal_B2BRetailerDetails();


                                        try {
                                            if (json.has("retailerkey")) {
                                                modal_B2BRetailerDetails.retailerkey = String.valueOf(json.get("retailerkey"));
                                            } else {
                                                modal_B2BRetailerDetails.retailerkey = "";
                                            }
                                        } catch (Exception e) {
                                            modal_B2BRetailerDetails.retailerkey = "";
                                            e.printStackTrace();
                                        }
                                        try {
                                            if (json.has("retailername")) {
                                                modal_B2BRetailerDetails.retailername = String.valueOf(json.get("retailername"));
                                            } else {
                                                modal_B2BRetailerDetails.retailername = "";
                                            }
                                        } catch (Exception e) {
                                            modal_B2BRetailerDetails.retailername = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("deliverycentrekey")) {
                                                modal_B2BRetailerDetails.deliveryCenterKey = String.valueOf(json.get("deliverycentrekey"));
                                            } else {
                                                modal_B2BRetailerDetails.deliveryCenterKey = "";
                                            }
                                        } catch (Exception e) {
                                            modal_B2BRetailerDetails.deliveryCenterKey = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("address")) {
                                                modal_B2BRetailerDetails.address = String.valueOf(json.get("address"));
                                            } else {
                                                modal_B2BRetailerDetails.address = "";
                                            }
                                        } catch (Exception e) {
                                            modal_B2BRetailerDetails.address = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("GSTIN")) {
                                                modal_B2BRetailerDetails.gstin = String.valueOf(json.get("GSTIN"));
                                            } else {
                                                modal_B2BRetailerDetails.gstin = "";
                                            }
                                        } catch (Exception e) {
                                            modal_B2BRetailerDetails.gstin = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("deliverycentrename")) {
                                                modal_B2BRetailerDetails.deliveryCenterName = String.valueOf(json.get("deliverycentrename"));
                                            } else {
                                                modal_B2BRetailerDetails.deliveryCenterName = "";
                                            }
                                        } catch (Exception e) {
                                            modal_B2BRetailerDetails.deliveryCenterName = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("mobileno")) {
                                                modal_B2BRetailerDetails.mobileno = String.valueOf(json.get("mobileno"));
                                            } else {
                                                modal_B2BRetailerDetails.mobileno = "";
                                            }
                                        } catch (Exception e) {
                                            modal_B2BRetailerDetails.mobileno = "";
                                            e.printStackTrace();
                                        }



                                        if(callMethod.equals(Constants.CallGETListMethod)){
                                            retailerDetailsArrayList.add(modal_B2BRetailerDetails);
                                        }
                                        else if(callMethod.equals(Constants.CallGETMethod)){
                                            Modal_B2BRetailerDetails.deliveryCenterKey_static = String.valueOf(json.get("deliverycentrekey"));
                                            Modal_B2BRetailerDetails.mobileno_static = String.valueOf(json.get("mobileno"));
                                            Modal_B2BRetailerDetails.deliveryCenterName_static = String.valueOf(json.get("deliverycentrename"));
                                            Modal_B2BRetailerDetails.retailerkey_static = String.valueOf(json.get("retailerkey"));
                                            Modal_B2BRetailerDetails.retailername_static = String.valueOf(json.get("retailername"));
                                            Modal_B2BRetailerDetails.address_static = String.valueOf(json.get("address"));
                                            Modal_B2BRetailerDetails.gstin_static = String.valueOf(json.get("GSTIN"));

                                            callback_retailerDetailsInterface.notifySuccess("success");

                                        }


                                    }
                                }

                                if(callMethod.equals(Constants.CallGETListMethod)){
                                    DatabaseArrayList_PojoClass.setRetailerDetailsArrayList(retailerDetailsArrayList);
                                    callback_retailerDetailsInterface.notifySuccessForGettingListItem(retailerDetailsArrayList);
                                }
                                else if(callMethod.equals(Constants.CallGETMethod)){
                                    callback_retailerDetailsInterface.notifySuccess("success");
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, error -> {

                //Log.d(Constants.TAG, "Error: " + error.getLocalizedMessage());
                //Log.d(Constants.TAG, "Error: " + error.getMessage());
                //Log.d(Constants.TAG, "Error: " + error.toString());
                callback_retailerDetailsInterface.notifyProcessingError( error);

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
            callback_retailerDetailsInterface.notifyVolleyError((VolleyError) e);
        }



    }

    private void addEntryInBatchDetails() {


        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,jsonToADD_Or_Update,
                    response -> {
                        if (callback_retailerDetailsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callback_retailerDetailsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){

                                    JSONArray JArray = response.getJSONArray("content");
                                    //Log.d(Constants.TAG, "convertingJsonStringintoArray Response: " + JArray);
                                    int i1 = 0;
                                    int arrayLength = JArray.length();
                                    //Log.d("Constants.TAG", "convertingJsonStringintoArray Response: " + arrayLength);
                                    if (JArray.length() == 0) {
                                        callback_retailerDetailsInterface.notifySuccess(Constants.emptyResult_volley);

                                    }
                                    if(arrayLength>0) {
                                        for (; i1 < (arrayLength); i1++) {
                                            JSONObject json = JArray.getJSONObject(i1);

                                            Modal_B2BRetailerDetails modal_B2BRetailerDetails = new Modal_B2BRetailerDetails();

                                            try {
                                                if (json.has("retailerkey")) {
                                                    modal_B2BRetailerDetails.retailerkey = String.valueOf(json.get("retailerkey"));
                                                } else {
                                                    modal_B2BRetailerDetails.retailerkey = "";
                                                }
                                            } catch (Exception e) {
                                                modal_B2BRetailerDetails.retailerkey = "";
                                                e.printStackTrace();
                                            }
                                            try {
                                                if (json.has("retailername")) {
                                                    modal_B2BRetailerDetails.retailername = String.valueOf(json.get("retailername"));
                                                } else {
                                                    modal_B2BRetailerDetails.retailername = "";
                                                }
                                            } catch (Exception e) {
                                                modal_B2BRetailerDetails.retailername = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("deliverycentrekey")) {
                                                    modal_B2BRetailerDetails.deliveryCenterKey = String.valueOf(json.get("deliverycentrekey"));
                                                } else {
                                                    modal_B2BRetailerDetails.deliveryCenterKey = "";
                                                }
                                            } catch (Exception e) {
                                                modal_B2BRetailerDetails.deliveryCenterKey = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("address")) {
                                                    modal_B2BRetailerDetails.address = String.valueOf(json.get("address"));
                                                } else {
                                                    modal_B2BRetailerDetails.address = "";
                                                }
                                            } catch (Exception e) {
                                                modal_B2BRetailerDetails.address = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("GSTIN")) {
                                                    modal_B2BRetailerDetails.gstin = String.valueOf(json.get("GSTIN"));
                                                } else {
                                                    modal_B2BRetailerDetails.gstin = "";
                                                }
                                            } catch (Exception e) {
                                                modal_B2BRetailerDetails.gstin = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("deliverycentrename")) {
                                                    modal_B2BRetailerDetails.deliveryCenterName = String.valueOf(json.get("deliverycentrename"));
                                                } else {
                                                    modal_B2BRetailerDetails.deliveryCenterName = "";
                                                }
                                            } catch (Exception e) {
                                                modal_B2BRetailerDetails.deliveryCenterName = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("mobileno")) {
                                                    modal_B2BRetailerDetails.mobileno = String.valueOf(json.get("mobileno"));
                                                } else {
                                                    modal_B2BRetailerDetails.mobileno = "";
                                                }
                                            } catch (Exception e) {
                                                modal_B2BRetailerDetails.mobileno = "";
                                                e.printStackTrace();
                                            }


                                            DatabaseArrayList_PojoClass.retailerDetailsArrayList.add(modal_B2BRetailerDetails);

                                            Modal_B2BRetailerDetails.deliveryCenterKey_static = String.valueOf(json.get("deliverycentrekey"));
                                            Modal_B2BRetailerDetails.mobileno_static = String.valueOf(json.get("mobileno"));
                                            Modal_B2BRetailerDetails.deliveryCenterName_static = String.valueOf(json.get("deliverycentrename"));
                                            Modal_B2BRetailerDetails.retailerkey_static = String.valueOf(json.get("retailerkey"));
                                            Modal_B2BRetailerDetails.retailername_static = String.valueOf(json.get("retailername"));
                                            Modal_B2BRetailerDetails.address_static = String.valueOf(json.get("address"));
                                            Modal_B2BRetailerDetails.gstin_static = String.valueOf(json.get("GSTIN"));

                                        }
                                    }


                                            callback_retailerDetailsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callback_retailerDetailsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callback_retailerDetailsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback_retailerDetailsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callback_retailerDetailsInterface.notifyVolleyError( error);

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
            callback_retailerDetailsInterface.notifyProcessingError(e);
        }

    }





    private JSONObject getJSONforPOJOClass() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (!Modal_B2BRetailerDetails.getRetailerkey_static().toString().equals("") && !Modal_B2BRetailerDetails.getRetailerkey_static().toString().equals("null")) {
                jsonObject.put("retailerkey", Modal_B2BRetailerDetails.getRetailerkey_static());
            }
            if (!Modal_B2BRetailerDetails.getRetailername_static().toString().equals("") && !Modal_B2BRetailerDetails.getRetailername_static().toString().equals("null")) {
                jsonObject.put("retailername", Modal_B2BRetailerDetails.getRetailername_static());
            }
            if (!Modal_B2BRetailerDetails.getMobileno_static().toString().equals("") && !Modal_B2BRetailerDetails.getMobileno_static().toString().equals("null")) {
                jsonObject.put("mobileno", Modal_B2BRetailerDetails.getMobileno_static());
            }
            if (!Modal_B2BRetailerDetails.getDeliveryCenterKey_static().toString().equals("") && !Modal_B2BRetailerDetails.getDeliveryCenterKey_static().toString().equals("null")) {
                jsonObject.put("deliverycentrekey", Modal_B2BRetailerDetails.getDeliveryCenterKey_static());
            }
            if (!Modal_B2BRetailerDetails.getDeliveryCenterName_static().toString().equals("") && !Modal_B2BRetailerDetails.getDeliveryCenterName_static().toString().equals("null")) {
                jsonObject.put("deliverycentrename", Modal_B2BRetailerDetails.getDeliveryCenterName_static());
            }
            if (!Modal_B2BRetailerDetails.getAddress_static().toString().equals("") && !Modal_B2BRetailerDetails.getAddress_static().toString().equals("null")) {
                jsonObject.put("address", Modal_B2BRetailerDetails.getAddress_static());
            }

            if (!Modal_B2BRetailerDetails.getGstin_static().toString().equals("") && !Modal_B2BRetailerDetails.getGstin_static().toString().equals("null")) {
                jsonObject.put("GSTIN", Modal_B2BRetailerDetails.getGstin_static());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


    @Override
    protected void onPostExecute( List<Modal_B2BRetailerDetails> modal ) {
        super.onPostExecute(modal);

    }



}
