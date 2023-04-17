package com.tmc.tmcb2bpartnerapp.second_version.apiRequestServices;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal__B2BPaymentDetails;
import com.tmc.tmcb2bpartnerapp.second_version.interfaces.B2BPaymentDetailsInterface;
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

public class B2BPaymentDetails  extends AsyncTask<String, String, List<Modal__B2BPaymentDetails>> {




    B2BPaymentDetailsInterface callB2BPaymemtDetailsInterface;
    String ApitoCall , callMethod;
    JSONObject jsonToADD_Or_Update = new JSONObject();




    public B2BPaymentDetails(B2BPaymentDetailsInterface callB2BPaymemtDetailsInterfacee, String getApiToCall,String apiMethodtoCall) {
        this.callB2BPaymemtDetailsInterface = callB2BPaymemtDetailsInterfacee;
        this.ApitoCall = getApiToCall;
        this.callMethod = apiMethodtoCall;
        if(callMethod.equals(Constants.CallADDMethod) ){
            jsonToADD_Or_Update = getJSONforPOJOClass();

        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){
            jsonToADD_Or_Update = getJSONforPOJOClass();

        }
        else if(callMethod.equals(Constants.CallGETMethod)){
            new Modal__B2BPaymentDetails();
        }
        else if(callMethod.equals(Constants.CallGETListMethod) || callMethod.equals(Constants.CallGETLastEntryMethod)){
            new Modal__B2BPaymentDetails();
        }
    }

    @Override
    protected ArrayList<Modal__B2BPaymentDetails> doInBackground(String... strings) {


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
                        if (callB2BPaymemtDetailsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callB2BPaymemtDetailsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){
                                    new Modal__B2BPaymentDetails();


                                    callB2BPaymemtDetailsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callB2BPaymemtDetailsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callB2BPaymemtDetailsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callB2BPaymemtDetailsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callB2BPaymemtDetailsInterface.notifyVolleyError( error);

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
            callB2BPaymemtDetailsInterface.notifyProcessingError(e);
        }
    }

    private void getDataFromBatchDetails() {


        ArrayList<Modal__B2BPaymentDetails>retailerDetailsArrayList = new ArrayList<>();

        new Modal__B2BPaymentDetails();
        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApitoCall,null,
                    response -> {
                        if (callB2BPaymemtDetailsInterface != null) {
                            try {
                                //converting jsonSTRING into array



                                JSONArray JArray = response.getJSONArray("content");
                                //Log.d(Constants.TAG, "convertingJsonStringintoArray Response: " + JArray);
                                int i1 = 0;
                                int arrayLength = JArray.length();
                                //Log.d("Constants.TAG", "convertingJsonStringintoArray Response: " + arrayLength);
                                if (JArray.length() == 0) {
                                    callB2BPaymemtDetailsInterface.notifySuccess(Constants.emptyResult_volley);
                                    return;
                                }
                                if(arrayLength>0) {
                                    for (; i1 < (arrayLength); i1++) {
                                        JSONObject json = JArray.getJSONObject(i1);
                                        Modal__B2BPaymentDetails modal__b2BPaymentDetails = new Modal__B2BPaymentDetails();


                                        try {
                                            if (json.has("retailerkey")) {
                                                modal__b2BPaymentDetails.retailerkey = String.valueOf(json.get("retailerkey"));
                                            } else {
                                                modal__b2BPaymentDetails.retailerkey = "";
                                            }
                                        } catch (Exception e) {
                                            modal__b2BPaymentDetails.retailerkey = "";
                                            e.printStackTrace();
                                        }
                                        try {
                                            if (json.has("retailername")) {
                                                modal__b2BPaymentDetails.retailername = String.valueOf(json.get("retailername"));
                                            } else {
                                                modal__b2BPaymentDetails.retailername = "";
                                            }
                                        } catch (Exception e) {
                                            modal__b2BPaymentDetails.retailername = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("deliverycentrekey")) {
                                                modal__b2BPaymentDetails.deliverycentrekey = String.valueOf(json.get("deliverycentrekey"));
                                            } else {
                                                modal__b2BPaymentDetails.deliverycentrekey = "";
                                            }
                                        } catch (Exception e) {
                                            modal__b2BPaymentDetails.deliverycentrekey = "";
                                            e.printStackTrace();
                                        }

                                        try {
                                            if (json.has("orderid")) {
                                                modal__b2BPaymentDetails.orderid = String.valueOf(json.get("orderid"));
                                            } else {
                                                modal__b2BPaymentDetails.orderid = "";
                                            }
                                        } catch (Exception e) {
                                            modal__b2BPaymentDetails.orderid = "";
                                            e.printStackTrace();
                                        }

                                        try {
                                            if (json.has("paymentid")) {
                                                modal__b2BPaymentDetails.paymentid = String.valueOf(json.get("paymentid"));
                                            } else {
                                                modal__b2BPaymentDetails.paymentid = "";
                                            }
                                        } catch (Exception e) {
                                            modal__b2BPaymentDetails.paymentid = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("retailermobileno")) {
                                                modal__b2BPaymentDetails.retailermobileno = String.valueOf(json.get("retailermobileno"));
                                            } else {
                                                modal__b2BPaymentDetails.retailermobileno = "";
                                            }
                                        } catch (Exception e) {
                                            modal__b2BPaymentDetails.retailermobileno = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("notes")) {
                                                modal__b2BPaymentDetails.notes = String.valueOf(json.get("notes"));
                                            } else {
                                                modal__b2BPaymentDetails.notes = "";
                                            }
                                        } catch (Exception e) {
                                            modal__b2BPaymentDetails.notes = "";
                                            e.printStackTrace();
                                        }
                                        try {
                                            if (json.has("transactiontime")) {
                                                modal__b2BPaymentDetails.transactiontime = String.valueOf(json.get("transactiontime"));
                                                modal__b2BPaymentDetails.transactiontime_long = DateParser.getLongValuefortheDate(String.valueOf(json.get("transactiontime")));
                                            } else {
                                                modal__b2BPaymentDetails.transactiontime = "";
                                            }
                                        } catch (Exception e) {
                                            modal__b2BPaymentDetails.transactiontime = "";
                                            e.printStackTrace();
                                        }

                                        try {
                                            if (json.has("transactiontype")) {
                                                modal__b2BPaymentDetails.transactiontype = String.valueOf(json.get("transactiontype"));
                                            } else {
                                                modal__b2BPaymentDetails.transactiontype = "";
                                            }
                                        } catch (Exception e) {
                                            modal__b2BPaymentDetails.transactiontype = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("paymentmode")) {
                                                modal__b2BPaymentDetails.paymentmode = String.valueOf(json.get("paymentmode"));
                                            } else {
                                                modal__b2BPaymentDetails.paymentmode = "";
                                            }
                                        } catch (Exception e) {
                                            modal__b2BPaymentDetails.paymentmode = "";
                                            e.printStackTrace();
                                        }




                                        try {
                                            if (json.has("uniquekey")) {
                                                modal__b2BPaymentDetails.uniquekey = String.valueOf(json.get("uniquekey"));
                                            } else {
                                                modal__b2BPaymentDetails.uniquekey = "";
                                            }
                                        } catch (Exception e) {
                                            modal__b2BPaymentDetails.uniquekey = "";
                                            e.printStackTrace();
                                        }




                                        try {
                                            if (json.has("transactionvalue")) {
                                                modal__b2BPaymentDetails.transactionvalue = String.valueOf(json.get("transactionvalue"));
                                            } else {
                                                modal__b2BPaymentDetails.transactionvalue = "";
                                            }
                                        } catch (Exception e) {
                                            modal__b2BPaymentDetails.transactionvalue = "";
                                            e.printStackTrace();
                                        }



                                        if(callMethod.equals(Constants.CallGETListMethod)){
                                            retailerDetailsArrayList.add(modal__b2BPaymentDetails);
                                        }
                                        else if(callMethod.equals(Constants.CallGETMethod)){
                                           /*
                                            Modal__B2BPaymentDetails.deliverycentrekey_static = String.valueOf(json.get("deliverycentrekey"));

                                            Modal__B2BPaymentDetails.retailermobileno_static = String.valueOf(json.get("retailermobileno"));
                                            Modal__B2BPaymentDetails.retailerkey_static = String.valueOf(json.get("retailerkey"));
                                            Modal__B2BPaymentDetails.retailername_static = String.valueOf(json.get("retailername"));
                                            Modal__B2BPaymentDetails.notes_static = String.valueOf(json.get("notes"));
                                            Modal__B2BPaymentDetails.uniquekey_static = String.valueOf(json.get("uniquekey"));
                                            Modal__B2BPaymentDetails.paymentmode_static = String.valueOf(json.get("paymentmode"));
                                            Modal__B2BPaymentDetails.transactiontime_static = String.valueOf(json.get("transactiontime"));
                                            Modal__B2BPaymentDetails.transactionvalue_static = String.valueOf(json.get("transactionvalue"));
                                            Modal__B2BPaymentDetails.transactiontype_static = String.valueOf(json.get("transactiontype"));
                                            Modal__B2BPaymentDetails.orderid_static = String.valueOf(json.get("orderid"));
                                         //   callB2BPaymemtDetailsInterface.notifySuccess("success");


                                            */

                                            Modal__B2BPaymentDetails.retailermobileno_static = String.valueOf(modal__b2BPaymentDetails.getRetailermobileno());
                                            Modal__B2BPaymentDetails.deliverycentrekey_static = String.valueOf(modal__b2BPaymentDetails.getDeliverycentrekey());
                                            Modal__B2BPaymentDetails.retailerkey_static =        String.valueOf(modal__b2BPaymentDetails.getRetailerkey());
                                            Modal__B2BPaymentDetails.retailername_static =       String.valueOf(modal__b2BPaymentDetails.getRetailername());
                                            Modal__B2BPaymentDetails.notes_static =              String.valueOf(modal__b2BPaymentDetails.getNotes());
                                            Modal__B2BPaymentDetails.uniquekey_static =          String.valueOf(modal__b2BPaymentDetails.getUniquekey());
                                            Modal__B2BPaymentDetails.paymentmode_static =        String.valueOf(modal__b2BPaymentDetails.getPaymentmode());
                                            Modal__B2BPaymentDetails.transactiontime_static =    String.valueOf(modal__b2BPaymentDetails.getTransactiontime());
                                            Modal__B2BPaymentDetails.transactionvalue_static =   String.valueOf(modal__b2BPaymentDetails.getTransactionvalue());
                                            Modal__B2BPaymentDetails.transactiontype_static =    String.valueOf(modal__b2BPaymentDetails.getTransactiontype());
                                            Modal__B2BPaymentDetails.orderid_static =            String.valueOf(modal__b2BPaymentDetails.getOrderid());
                                            Modal__B2BPaymentDetails.paymentmode_static =            String.valueOf(modal__b2BPaymentDetails.getPaymentid());

                                        }


                                    }
                                }

                                if(callMethod.equals(Constants.CallGETListMethod)){
                                    callB2BPaymemtDetailsInterface.notifySuccessForGettingListItem(retailerDetailsArrayList);
                                }
                                else if(callMethod.equals(Constants.CallGETMethod)){
                                    callB2BPaymemtDetailsInterface.notifySuccess("success");
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, error -> {

                //Log.d(Constants.TAG, "Error: " + error.getLocalizedMessage());
                //Log.d(Constants.TAG, "Error: " + error.getMessage());
                //Log.d(Constants.TAG, "Error: " + error.toString());
                callB2BPaymemtDetailsInterface.notifyProcessingError( error);

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
            callB2BPaymemtDetailsInterface.notifyVolleyError((VolleyError) e);
        }



    }

    private void addEntryInBatchDetails() {


        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,jsonToADD_Or_Update,
                    response -> {
                        if (callB2BPaymemtDetailsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callB2BPaymemtDetailsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){

                                    JSONArray JArray = response.getJSONArray("content");
                                    //Log.d(Constants.TAG, "convertingJsonStringintoArray Response: " + JArray);
                                    int i1 = 0;
                                    int arrayLength = JArray.length();
                                    //Log.d("Constants.TAG", "convertingJsonStringintoArray Response: " + arrayLength);
                                    if (JArray.length() == 0) {
                                        callB2BPaymemtDetailsInterface.notifySuccess(Constants.emptyResult_volley);

                                    }
                                    if(arrayLength>0) {
                                        for (; i1 < (arrayLength); i1++) {
                                            JSONObject json = JArray.getJSONObject(i1);

                                            Modal__B2BPaymentDetails modal__b2BPaymentDetails = new Modal__B2BPaymentDetails();


                                            try {
                                                if (json.has("retailerkey")) {
                                                    modal__b2BPaymentDetails.retailerkey = String.valueOf(json.get("retailerkey"));
                                                } else {
                                                    modal__b2BPaymentDetails.retailerkey = "";
                                                }
                                            } catch (Exception e) {
                                                modal__b2BPaymentDetails.retailerkey = "";
                                                e.printStackTrace();
                                            }
                                            try {
                                                if (json.has("retailername")) {
                                                    modal__b2BPaymentDetails.retailername = String.valueOf(json.get("retailername"));
                                                } else {
                                                    modal__b2BPaymentDetails.retailername = "";
                                                }
                                            } catch (Exception e) {
                                                modal__b2BPaymentDetails.retailername = "";
                                                e.printStackTrace();
                                            }
                                            try {
                                                if (json.has("orderid")) {
                                                    modal__b2BPaymentDetails.orderid = String.valueOf(json.get("orderid"));
                                                } else {
                                                    modal__b2BPaymentDetails.orderid = "";
                                                }
                                            } catch (Exception e) {
                                                modal__b2BPaymentDetails.orderid = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("paymentid")) {
                                                    modal__b2BPaymentDetails.paymentid = String.valueOf(json.get("paymentid"));
                                                } else {
                                                    modal__b2BPaymentDetails.paymentid = "";
                                                }
                                            } catch (Exception e) {
                                                modal__b2BPaymentDetails.paymentid = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("deliverycentrekey")) {
                                                    modal__b2BPaymentDetails.deliverycentrekey = String.valueOf(json.get("deliverycentrekey"));
                                                } else {
                                                    modal__b2BPaymentDetails.deliverycentrekey = "";
                                                }
                                            } catch (Exception e) {
                                                modal__b2BPaymentDetails.deliverycentrekey = "";
                                                e.printStackTrace();
                                            }



                                            try {
                                                if (json.has("retailermobileno")) {
                                                    modal__b2BPaymentDetails.retailermobileno = String.valueOf(json.get("retailermobileno"));
                                                } else {
                                                    modal__b2BPaymentDetails.retailermobileno = "";
                                                }
                                            } catch (Exception e) {
                                                modal__b2BPaymentDetails.retailermobileno = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("notes")) {
                                                    modal__b2BPaymentDetails.notes = String.valueOf(json.get("notes"));
                                                } else {
                                                    modal__b2BPaymentDetails.notes = "";
                                                }
                                            } catch (Exception e) {
                                                modal__b2BPaymentDetails.notes = "";
                                                e.printStackTrace();
                                            }
                                            try {
                                                if (json.has("transactiontime")) {
                                                    modal__b2BPaymentDetails.transactiontime = String.valueOf(json.get("transactiontime"));
                                                } else {
                                                    modal__b2BPaymentDetails.transactiontime = "";
                                                }
                                            } catch (Exception e) {
                                                modal__b2BPaymentDetails.transactiontime = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("transactiontype")) {
                                                    modal__b2BPaymentDetails.transactiontype = String.valueOf(json.get("transactiontype"));
                                                } else {
                                                    modal__b2BPaymentDetails.transactiontype = "";
                                                }
                                            } catch (Exception e) {
                                                modal__b2BPaymentDetails.transactiontype = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("paymentmode")) {
                                                    modal__b2BPaymentDetails.paymentmode = String.valueOf(json.get("paymentmode"));
                                                } else {
                                                    modal__b2BPaymentDetails.paymentmode = "";
                                                }
                                            } catch (Exception e) {
                                                modal__b2BPaymentDetails.paymentmode = "";
                                                e.printStackTrace();
                                            }




                                            try {
                                                if (json.has("uniquekey")) {
                                                    modal__b2BPaymentDetails.uniquekey = String.valueOf(json.get("uniquekey"));
                                                } else {
                                                    modal__b2BPaymentDetails.uniquekey = "";
                                                }
                                            } catch (Exception e) {
                                                modal__b2BPaymentDetails.uniquekey = "";
                                                e.printStackTrace();
                                            }




                                            try {
                                                if (json.has("transactionvalue")) {
                                                    modal__b2BPaymentDetails.transactionvalue = String.valueOf(json.get("transactionvalue"));
                                                } else {
                                                    modal__b2BPaymentDetails.transactionvalue = "";
                                                }
                                            } catch (Exception e) {
                                                modal__b2BPaymentDetails.transactionvalue = "";
                                                e.printStackTrace();
                                            }





                                            Modal__B2BPaymentDetails.deliverycentrekey_static = String.valueOf(modal__b2BPaymentDetails.getDeliverycentrekey());
                                            Modal__B2BPaymentDetails.retailerkey_static =        String.valueOf(modal__b2BPaymentDetails.getRetailerkey());
                                            Modal__B2BPaymentDetails.retailername_static =       String.valueOf(modal__b2BPaymentDetails.getRetailername());
                                            Modal__B2BPaymentDetails.notes_static =              String.valueOf(modal__b2BPaymentDetails.getNotes());
                                            Modal__B2BPaymentDetails.uniquekey_static =          String.valueOf(modal__b2BPaymentDetails.getUniquekey());
                                            Modal__B2BPaymentDetails.paymentmode_static =        String.valueOf(modal__b2BPaymentDetails.getPaymentmode());
                                            Modal__B2BPaymentDetails.transactiontime_static =    String.valueOf(modal__b2BPaymentDetails.getTransactiontime());
                                            Modal__B2BPaymentDetails.transactionvalue_static =   String.valueOf(modal__b2BPaymentDetails.getTransactionvalue());
                                            Modal__B2BPaymentDetails.transactiontype_static =    String.valueOf(modal__b2BPaymentDetails.getTransactiontype());
                                            Modal__B2BPaymentDetails.orderid_static =            String.valueOf(modal__b2BPaymentDetails.getOrderid());
                                            Modal__B2BPaymentDetails.paymentmode_static =            String.valueOf(modal__b2BPaymentDetails.getPaymentid());

                                            //callB2BPaymemtDetailsInterface.notifySuccess("success");

                                        }
                                    }


                                    callB2BPaymemtDetailsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callB2BPaymemtDetailsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callB2BPaymemtDetailsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callB2BPaymemtDetailsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callB2BPaymemtDetailsInterface.notifyVolleyError( error);

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
            callB2BPaymemtDetailsInterface.notifyProcessingError(e);
        }

    }





    private JSONObject getJSONforPOJOClass() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("retailerkey", Modal__B2BPaymentDetails.getRetailerkey_static());

            if (!Modal__B2BPaymentDetails.getRetailername_static().toString().equals("") && !Modal__B2BPaymentDetails.getRetailername_static().toString().equals("null")) {
                jsonObject.put("retailername", Modal__B2BPaymentDetails.getRetailername_static());
            }
            if (!Modal__B2BPaymentDetails.getRetailermobileno_static().toString().equals("") && !Modal__B2BPaymentDetails.getRetailermobileno_static().toString().equals("null")) {
                jsonObject.put("retailermobileno", Modal__B2BPaymentDetails.getRetailermobileno_static());
            }
            if (!Modal__B2BPaymentDetails.getDeliverycentrekey_static().toString().equals("") && !Modal__B2BPaymentDetails.getDeliverycentrekey_static().toString().equals("null")) {
                jsonObject.put("deliverycentrekey", Modal__B2BPaymentDetails.getDeliverycentrekey_static());
            }
            if (!Modal__B2BPaymentDetails.getNotes_static().toString().equals("") && !Modal__B2BPaymentDetails.getNotes_static().toString().equals("null")) {
                jsonObject.put("notes", Modal__B2BPaymentDetails.getNotes_static());
            }
            if (!Modal__B2BPaymentDetails.getUniquekey_static().toString().equals("") && !Modal__B2BPaymentDetails.getUniquekey_static().toString().equals("null")) {
                jsonObject.put("uniquekey", Modal__B2BPaymentDetails.getUniquekey_static());
            }
            if (!Modal__B2BPaymentDetails.getPaymentmode_static().toString().equals("") && !Modal__B2BPaymentDetails.getPaymentmode_static().toString().equals("null")) {
                jsonObject.put("paymentmode", Modal__B2BPaymentDetails.getPaymentmode_static());
            }
            if (!Modal__B2BPaymentDetails.getTransactiontime_static().toString().equals("") && !Modal__B2BPaymentDetails.getTransactiontime_static().toString().equals("null")) {
                jsonObject.put("transactiontime", Modal__B2BPaymentDetails.getTransactiontime_static());
            }
            if (!Modal__B2BPaymentDetails.getTransactionvalue_static().toString().equals("") && !Modal__B2BPaymentDetails.getTransactionvalue_static().toString().equals("null")) {
                jsonObject.put("transactionvalue", Modal__B2BPaymentDetails.getTransactionvalue_static());
            }
            if (!Modal__B2BPaymentDetails.getTransactiontype_static().toString().equals("") && !Modal__B2BPaymentDetails.getTransactiontype_static().toString().equals("null")) {
                jsonObject.put("transactiontype", Modal__B2BPaymentDetails.getTransactiontype_static());
            }

            if (!Modal__B2BPaymentDetails.getOrderid_static().toString().equals("") && !Modal__B2BPaymentDetails.getOrderid_static().toString().equals("null")) {
                jsonObject.put("orderid", Modal__B2BPaymentDetails.getOrderid_static());
            }

            if (!Modal__B2BPaymentDetails.getPaymentid_static().toString().equals("") && !Modal__B2BPaymentDetails.getPaymentid_static().toString().equals("null")) {
                jsonObject.put("paymentid", Modal__B2BPaymentDetails.getPaymentid_static());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
