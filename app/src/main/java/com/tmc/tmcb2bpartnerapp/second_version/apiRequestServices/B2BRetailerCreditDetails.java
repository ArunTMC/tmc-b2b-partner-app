package com.tmc.tmcb2bpartnerapp.second_version.apiRequestServices;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.second_version.interfaces.B2BRetailerCreditDetailsInterface;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal_B2BRetailerCreditDetails;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.volleyrequestqueuehelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class B2BRetailerCreditDetails extends AsyncTask<String, String, List<Modal_B2BRetailerCreditDetails>> {


    B2BRetailerCreditDetailsInterface callB2BRetailerCreditDetailsInterface;
    String ApitoCall , callMethod;
    JSONObject jsonToADD_Or_Update = new JSONObject();



    public B2BRetailerCreditDetails(B2BRetailerCreditDetailsInterface callB2BRetailerCreditDetailsInterfacee, String getApiToCall,String apiMethodtoCall) {
        this.callB2BRetailerCreditDetailsInterface = callB2BRetailerCreditDetailsInterfacee;
        this.ApitoCall = getApiToCall;
        this.callMethod = apiMethodtoCall;
        if(callMethod.equals(Constants.CallADDMethod) ){
            jsonToADD_Or_Update = getJSONforPOJOClass();

        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){
            jsonToADD_Or_Update = getJSONforPOJOClass();

        }
        else if(callMethod.equals(Constants.CallGETMethod)){
            new Modal_B2BRetailerCreditDetails();
        }
        else if(callMethod.equals(Constants.CallGETListMethod) || callMethod.equals(Constants.CallGETLastEntryMethod)){
            new Modal_B2BRetailerCreditDetails();
        }
    }

    @Override
    protected ArrayList<Modal_B2BRetailerCreditDetails> doInBackground(String... strings) {


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
                        if (callB2BRetailerCreditDetailsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callB2BRetailerCreditDetailsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){
                                    new Modal_B2BRetailerCreditDetails();


                                    callB2BRetailerCreditDetailsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callB2BRetailerCreditDetailsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callB2BRetailerCreditDetailsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callB2BRetailerCreditDetailsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callB2BRetailerCreditDetailsInterface.notifyVolleyError( error);

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
            callB2BRetailerCreditDetailsInterface.notifyProcessingError(e);
        }
    }

    private void getDataFromBatchDetails() {


        ArrayList<Modal_B2BRetailerCreditDetails>retailerDetailsArrayList = new ArrayList<>();

        new Modal_B2BRetailerCreditDetails();
        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApitoCall,null,
                    response -> {
                        if (callB2BRetailerCreditDetailsInterface != null) {
                            try {
                                //converting jsonSTRING into array
                                JSONArray JArray = response.getJSONArray("content");
                                //Log.d(Constants.TAG, "convertingJsonStringintoArray Response: " + JArray);
                                int i1 = 0;
                                int arrayLength = JArray.length();
                                //Log.d("Constants.TAG", "convertingJsonStringintoArray Response: " + arrayLength);
                                if (JArray.length() == 0) {
                                    callB2BRetailerCreditDetailsInterface.notifySuccess(Constants.emptyResult_volley);

                                }
                                if(arrayLength>0) {
                                    for (; i1 < (arrayLength); i1++) {
                                        JSONObject json = JArray.getJSONObject(i1);
                                        Modal_B2BRetailerCreditDetails modal_B2BRetailerDetails = new Modal_B2BRetailerCreditDetails();


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
                                                modal_B2BRetailerDetails.deliverycentrekey = String.valueOf(json.get("deliverycentrekey"));
                                            } else {
                                                modal_B2BRetailerDetails.deliverycentrekey = "";
                                            }
                                        } catch (Exception e) {
                                            modal_B2BRetailerDetails.deliverycentrekey = "";
                                            e.printStackTrace();
                                        }



                                        try {
                                            if (json.has("retailermobileno")) {
                                                modal_B2BRetailerDetails.retailermobileno = String.valueOf(json.get("retailermobileno"));
                                            } else {
                                                modal_B2BRetailerDetails.retailermobileno = "";
                                            }
                                        } catch (Exception e) {
                                            modal_B2BRetailerDetails.retailermobileno = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("lastupdatedtime")) {
                                                modal_B2BRetailerDetails.lastupdatedtime = DateParser.convertTime_to_DisplayingFormat(String.valueOf(json.get("lastupdatedtime")));
                                            } else {
                                                modal_B2BRetailerDetails.lastupdatedtime = "";
                                            }
                                        } catch (Exception e) {
                                            modal_B2BRetailerDetails.lastupdatedtime = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("totalamountincredit")) {
                                                modal_B2BRetailerDetails.totalamountincredit = String.valueOf(json.get("totalamountincredit"));
                                            } else {
                                                modal_B2BRetailerDetails.totalamountincredit = "";
                                            }
                                        } catch (Exception e) {
                                            modal_B2BRetailerDetails.totalamountincredit = "";
                                            e.printStackTrace();
                                        }



                                        if(callMethod.equals(Constants.CallGETListMethod)){
                                            retailerDetailsArrayList.add(modal_B2BRetailerDetails);
                                        }
                                        else if(callMethod.equals(Constants.CallGETMethod)){
                                            Modal_B2BRetailerCreditDetails.deliverycentrekey_static = String.valueOf(json.get("deliverycentrekey"));
                                            Modal_B2BRetailerCreditDetails.retailermobileno_static = String.valueOf(json.get("retailermobileno"));
                                            Modal_B2BRetailerCreditDetails.retailerkey_static = String.valueOf(json.get("retailerkey"));
                                            Modal_B2BRetailerCreditDetails.retailername_static = String.valueOf(json.get("retailername"));
                                            Modal_B2BRetailerCreditDetails.totalamountincredit_static = String.valueOf(json.get("totalamountincredit"));
                                            Modal_B2BRetailerCreditDetails.lastupdatedtime_static = String.valueOf(json.get("lastupdatedtime"));

                                            callB2BRetailerCreditDetailsInterface.notifySuccess("success");

                                        }


                                    }
                                }
                                if (JArray.length() > 0) {
                                    if (callMethod.equals(Constants.CallGETListMethod)) {
                                        callB2BRetailerCreditDetailsInterface.notifySuccessForGettingListItem(retailerDetailsArrayList);
                                    } else if (callMethod.equals(Constants.CallGETMethod)) {
                                        callB2BRetailerCreditDetailsInterface.notifySuccess("success");
                                    }
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, error -> {

                //Log.d(Constants.TAG, "Error: " + error.getLocalizedMessage());
                //Log.d(Constants.TAG, "Error: " + error.getMessage());
                //Log.d(Constants.TAG, "Error: " + error.toString());
                callB2BRetailerCreditDetailsInterface.notifyProcessingError( error);

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
            callB2BRetailerCreditDetailsInterface.notifyVolleyError((VolleyError) e);
        }



    }

    private void addEntryInBatchDetails() {


        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,jsonToADD_Or_Update,
                    response -> {
                        if (callB2BRetailerCreditDetailsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callB2BRetailerCreditDetailsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){

                                    JSONArray JArray = response.getJSONArray("content");
                                    //Log.d(Constants.TAG, "convertingJsonStringintoArray Response: " + JArray);
                                    int i1 = 0;
                                    int arrayLength = JArray.length();
                                    //Log.d("Constants.TAG", "convertingJsonStringintoArray Response: " + arrayLength);
                                    if (JArray.length() == 0) {
                                        callB2BRetailerCreditDetailsInterface.notifySuccess(Constants.emptyResult_volley);

                                    }
                                    if(arrayLength>0) {
                                        for (; i1 < (arrayLength); i1++) {
                                            JSONObject json = JArray.getJSONObject(i1);

                                            Modal_B2BRetailerCreditDetails modal_B2BRetailerDetails = new Modal_B2BRetailerCreditDetails();


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
                                                    modal_B2BRetailerDetails.deliverycentrekey = String.valueOf(json.get("deliverycentrekey"));
                                                } else {
                                                    modal_B2BRetailerDetails.deliverycentrekey = "";
                                                }
                                            } catch (Exception e) {
                                                modal_B2BRetailerDetails.deliverycentrekey = "";
                                                e.printStackTrace();
                                            }



                                            try {
                                                if (json.has("retailermobileno")) {
                                                    modal_B2BRetailerDetails.retailermobileno = String.valueOf(json.get("retailermobileno"));
                                                } else {
                                                    modal_B2BRetailerDetails.retailermobileno = "";
                                                }
                                            } catch (Exception e) {
                                                modal_B2BRetailerDetails.retailermobileno = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("lastupdatedtime")) {
                                                    modal_B2BRetailerDetails.lastupdatedtime = String.valueOf(json.get("lastupdatedtime"));
                                                } else {
                                                    modal_B2BRetailerDetails.lastupdatedtime = "";
                                                }
                                            } catch (Exception e) {
                                                modal_B2BRetailerDetails.lastupdatedtime = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("totalamountincredit")) {
                                                    modal_B2BRetailerDetails.totalamountincredit = String.valueOf(json.get("totalamountincredit"));
                                                } else {
                                                    modal_B2BRetailerDetails.totalamountincredit = "";
                                                }
                                            } catch (Exception e) {
                                                modal_B2BRetailerDetails.totalamountincredit = "";
                                                e.printStackTrace();
                                            }



                                            Modal_B2BRetailerCreditDetails.deliverycentrekey_static = String.valueOf(json.get("deliverycentrekey"));
                                            Modal_B2BRetailerCreditDetails.retailermobileno_static = String.valueOf(json.get("retailermobileno"));
                                            Modal_B2BRetailerCreditDetails.retailerkey_static = String.valueOf(json.get("retailerkey"));
                                            Modal_B2BRetailerCreditDetails.retailername_static = String.valueOf(json.get("retailername"));
                                            Modal_B2BRetailerCreditDetails.totalamountincredit_static = String.valueOf(json.get("totalamountincredit"));
                                            Modal_B2BRetailerCreditDetails.lastupdatedtime_static = String.valueOf(json.get("lastupdatedtime"));

                                            callB2BRetailerCreditDetailsInterface.notifySuccess("success");

                                        }
                                    }


                                    callB2BRetailerCreditDetailsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callB2BRetailerCreditDetailsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callB2BRetailerCreditDetailsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callB2BRetailerCreditDetailsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callB2BRetailerCreditDetailsInterface.notifyVolleyError( error);

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
            callB2BRetailerCreditDetailsInterface.notifyProcessingError(e);
        }

    }




    private JSONObject getJSONforPOJOClass() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("retailerkey", Modal_B2BRetailerCreditDetails.getRetailerkey_static());

            if (!Modal_B2BRetailerCreditDetails.getRetailername_static().toString().equals("") && !Modal_B2BRetailerCreditDetails.getRetailername_static().toString().equals("null")) {
                jsonObject.put("retailername", Modal_B2BRetailerCreditDetails.getRetailername_static());
            }
            if (!Modal_B2BRetailerCreditDetails.getRetailermobileno_static().toString().equals("") && !Modal_B2BRetailerCreditDetails.getRetailermobileno_static().toString().equals("null")) {
                jsonObject.put("retailermobileno", Modal_B2BRetailerCreditDetails.getRetailermobileno_static());
            }
            if (!Modal_B2BRetailerCreditDetails.getDeliverycentrekey_static().toString().equals("") && !Modal_B2BRetailerCreditDetails.getDeliverycentrekey_static().toString().equals("null")) {
                jsonObject.put("deliverycentrekey", Modal_B2BRetailerCreditDetails.getDeliverycentrekey_static());
            }
            if (!Modal_B2BRetailerCreditDetails.getTotalamountincredit_static().toString().equals("") && !Modal_B2BRetailerCreditDetails.getTotalamountincredit_static().toString().equals("null")) {
                jsonObject.put("totalamountincredit", Modal_B2BRetailerCreditDetails.getTotalamountincredit_static());
            }
            if (!Modal_B2BRetailerCreditDetails.getLastupdatedtime_static().toString().equals("") && !Modal_B2BRetailerCreditDetails.getLastupdatedtime_static().toString().equals("null")) {
                jsonObject.put("lastupdatedtime", Modal_B2BRetailerCreditDetails.getLastupdatedtime_static());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }





}
