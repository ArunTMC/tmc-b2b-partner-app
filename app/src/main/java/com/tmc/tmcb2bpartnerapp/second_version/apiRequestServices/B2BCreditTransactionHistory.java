package com.tmc.tmcb2bpartnerapp.second_version.apiRequestServices;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal__B2BCreditTransactionHistory;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal__B2BCreditTransactionHistory;
import com.tmc.tmcb2bpartnerapp.second_version.interfaces.B2BCreditTransactionHistoryInterface;
import com.tmc.tmcb2bpartnerapp.second_version.interfaces.B2BPaymentDetailsInterface;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.volleyrequestqueuehelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class B2BCreditTransactionHistory extends AsyncTask<String, String, List<Modal__B2BCreditTransactionHistory>> {



    B2BCreditTransactionHistoryInterface callbackB2bCreditTransactionHistoryInterface;
    String ApitoCall , callMethod;
    JSONObject jsonToADD_Or_Update = new JSONObject();

    public B2BCreditTransactionHistory(B2BCreditTransactionHistoryInterface callbackB2bCreditTransactionHistoryInterfacee, String getApiToCall,String apiMethodtoCall) {
        this.callbackB2bCreditTransactionHistoryInterface = callbackB2bCreditTransactionHistoryInterfacee;
        this.ApitoCall = getApiToCall;
        this.callMethod = apiMethodtoCall;
        if(callMethod.equals(Constants.CallADDMethod) ){
            jsonToADD_Or_Update = getJSONforPOJOClass();

        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){
            jsonToADD_Or_Update = getJSONforPOJOClass();

        }
        else if(callMethod.equals(Constants.CallGETMethod)){
            new Modal__B2BCreditTransactionHistory("resetall");
        }
        else if(callMethod.equals(Constants.CallGETListMethod) || callMethod.equals(Constants.CallGETLastEntryMethod)){
            new Modal__B2BCreditTransactionHistory("resetall");
        }
    }

    @Override
    protected ArrayList<Modal__B2BCreditTransactionHistory> doInBackground(String... strings) {


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
                        if (callbackB2bCreditTransactionHistoryInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callbackB2bCreditTransactionHistoryInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){
                                    new Modal__B2BCreditTransactionHistory("resetall");


                                    callbackB2bCreditTransactionHistoryInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callbackB2bCreditTransactionHistoryInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callbackB2bCreditTransactionHistoryInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callbackB2bCreditTransactionHistoryInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callbackB2bCreditTransactionHistoryInterface.notifyVolleyError( error);

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
            callbackB2bCreditTransactionHistoryInterface.notifyProcessingError(e);
        }
    }

    private void getDataFromBatchDetails() {


        ArrayList<Modal__B2BCreditTransactionHistory>retailerDetailsArrayList = new ArrayList<>();

        new Modal__B2BCreditTransactionHistory("resetall");

        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApitoCall,null,
                    response -> {
                        if (callbackB2bCreditTransactionHistoryInterface != null) {
                            try {
                                //converting jsonSTRING into array
                                JSONArray JArray = response.getJSONArray("content");
                                //Log.d(Constants.TAG, "convertingJsonStringintoArray Response: " + JArray);
                                int i1 = 0;
                                int arrayLength = JArray.length();
                                //Log.d("Constants.TAG", "convertingJsonStringintoArray Response: " + arrayLength);
                                if (JArray.length() == 0) {
                                    callbackB2bCreditTransactionHistoryInterface.notifySuccess(Constants.emptyResult_volley);
                                    return;
                                }
                                if(arrayLength>0) {
                                    for (; i1 < (arrayLength); i1++) {
                                        JSONObject json = JArray.getJSONObject(i1);
                                        Modal__B2BCreditTransactionHistory modal__b2BPaymentDetails = new Modal__B2BCreditTransactionHistory("resetall");



                                        try {
                                            if (json.has("retailerkey")) {
                                                Modal__B2BCreditTransactionHistory.retailerkey_static = String.valueOf(json.get("retailerkey"));

                                                modal__b2BPaymentDetails.retailerkey = String.valueOf(json.get("retailerkey"));
                                            } else {
                                                modal__b2BPaymentDetails.retailerkey = "";
                                            }
                                        } catch (Exception e) {
                                            modal__b2BPaymentDetails.retailerkey = "";
                                            e.printStackTrace();
                                        }



                                        try {
                                            if (json.has("deliverycentrekey")) {
                                                Modal__B2BCreditTransactionHistory.deliverycentrekey_static = String.valueOf(json.get("deliverycentrekey"));

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
                                                Modal__B2BCreditTransactionHistory.orderid_static = String.valueOf(json.get("orderid"));

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
                                                Modal__B2BCreditTransactionHistory.paymentid_static = String.valueOf(json.get("paymentid"));

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
                                                Modal__B2BCreditTransactionHistory.retailermobileno_static = String.valueOf(json.get("retailermobileno"));

                                                modal__b2BPaymentDetails.retailermobileno = String.valueOf(json.get("retailermobileno"));
                                            } else {
                                                modal__b2BPaymentDetails.retailermobileno = "";
                                            }
                                        } catch (Exception e) {
                                            modal__b2BPaymentDetails.retailermobileno = "";
                                            e.printStackTrace();
                                        }

                                        try {
                                            if (json.has("retailername")) {
                                                Modal__B2BCreditTransactionHistory.retailername_static = String.valueOf(json.get("retailername"));

                                                modal__b2BPaymentDetails.retailername = String.valueOf(json.get("retailername"));
                                            } else {
                                                modal__b2BPaymentDetails.retailername = "";
                                            }
                                        } catch (Exception e) {
                                            modal__b2BPaymentDetails.retailername = "";
                                            e.printStackTrace();
                                        }

                                        try {
                                            if (json.has("transactiontime")) {
                                                Modal__B2BCreditTransactionHistory.transactiontime_static = String.valueOf(json.get("transactiontime"));

                                                modal__b2BPaymentDetails.transactiontime = String.valueOf(json.get("transactiontime"));
                                            } else {
                                                modal__b2BPaymentDetails.transactiontime = "";
                                            }
                                        } catch (Exception e) {
                                            modal__b2BPaymentDetails.transactiontime = "";
                                            e.printStackTrace();
                                        }







                                        try {
                                            if (json.has("uniquekey")) {
                                                Modal__B2BCreditTransactionHistory.uniquekey_static = String.valueOf(json.get("uniquekey"));

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
                                                Modal__B2BCreditTransactionHistory.transactionvalue_static = String.valueOf(json.get("transactionvalue"));

                                                modal__b2BPaymentDetails.transactionvalue = String.valueOf(json.get("transactionvalue"));
                                            } else {
                                                modal__b2BPaymentDetails.transactionvalue = "";
                                            }
                                        } catch (Exception e) {
                                            modal__b2BPaymentDetails.transactionvalue = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("supervisormobileno")) {
                                                Modal__B2BCreditTransactionHistory.supervisormobileno_static = String.valueOf(json.get("supervisormobileno"));

                                                modal__b2BPaymentDetails.supervisormobileno = String.valueOf(json.get("supervisormobileno"));
                                            } else {
                                                modal__b2BPaymentDetails.supervisormobileno = "";
                                            }
                                        } catch (Exception e) {
                                            modal__b2BPaymentDetails.supervisormobileno = "";
                                            e.printStackTrace();
                                        }

                                        try {
                                            if (json.has("oldamountincredit")) {
                                                Modal__B2BCreditTransactionHistory.oldamountincredit_static = String.valueOf(json.get("oldamountincredit"));

                                                modal__b2BPaymentDetails.oldamountincredit = String.valueOf(json.get("oldamountincredit"));
                                            } else {
                                                modal__b2BPaymentDetails.oldamountincredit = "";
                                            }
                                        } catch (Exception e) {
                                            modal__b2BPaymentDetails.oldamountincredit = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("newamountincredit")) {
                                                Modal__B2BCreditTransactionHistory.newamountincredit_static = String.valueOf(json.get("newamountincredit"));

                                                modal__b2BPaymentDetails.newamountincredit = String.valueOf(json.get("newamountincredit"));
                                            } else {
                                                modal__b2BPaymentDetails.newamountincredit = "";
                                            }
                                        } catch (Exception e) {
                                            modal__b2BPaymentDetails.newamountincredit = "";
                                            e.printStackTrace();
                                        }


                                        if(callMethod.equals(Constants.CallGETListMethod)){
                                            retailerDetailsArrayList.add(modal__b2BPaymentDetails);
                                        }



                                    }
                                }

                                if(callMethod.equals(Constants.CallGETListMethod)){
                                    callbackB2bCreditTransactionHistoryInterface.notifySuccessForGettingListItem(retailerDetailsArrayList);
                                }
                                else if(callMethod.equals(Constants.CallGETMethod)){
                                    callbackB2bCreditTransactionHistoryInterface.notifySuccess("success");
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, error -> {

                //Log.d(Constants.TAG, "Error: " + error.getLocalizedMessage());
                //Log.d(Constants.TAG, "Error: " + error.getMessage());
                //Log.d(Constants.TAG, "Error: " + error.toString());
                callbackB2bCreditTransactionHistoryInterface.notifyProcessingError( error);

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
            callbackB2bCreditTransactionHistoryInterface.notifyVolleyError((VolleyError) e);
        }



    }

    private void addEntryInBatchDetails() {


        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,jsonToADD_Or_Update,
                    response -> {
                        if (callbackB2bCreditTransactionHistoryInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callbackB2bCreditTransactionHistoryInterface.notifySuccess(Constants.item_Already_Added_volley);
                                    new Modal__B2BCreditTransactionHistory("resetall");

                                }
                                else if(statusCode.equals("200")){
                                    new Modal__B2BCreditTransactionHistory("resetall");

                                    JSONArray JArray = response.getJSONArray("content");
                                    //Log.d(Constants.TAG, "convertingJsonStringintoArray Response: " + JArray);
                                    int i1 = 0;
                                    int arrayLength = JArray.length();
                                    //Log.d("Constants.TAG", "convertingJsonStringintoArray Response: " + arrayLength);
                                    if (JArray.length() == 0) {
                                        callbackB2bCreditTransactionHistoryInterface.notifySuccess(Constants.emptyResult_volley);

                                    }
                                    /*
                                    if(arrayLength>0) {
                                        for (; i1 < (arrayLength); i1++) {
                                            JSONObject json = JArray.getJSONObject(i1);

                                            Modal__B2BCreditTransactionHistory modal__b2BPaymentDetails = new Modal__B2BCreditTransactionHistory();
                                            try {
                                                if (json.has("retailerkey")) {
                                                    Modal__B2BCreditTransactionHistory.retailerkey_static = String.valueOf(json.get("retailerkey"));
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
                                                    Modal__B2BCreditTransactionHistory.retailername_static = String.valueOf(json.get("retailername"));
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
                                                    Modal__B2BCreditTransactionHistory.orderid_static = String.valueOf(json.get("orderid"));
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
                                                    Modal__B2BCreditTransactionHistory.paymentid_static = String.valueOf(json.get("paymentid"));
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
                                                    Modal__B2BCreditTransactionHistory.deliverycentrekey_static = String.valueOf(json.get("deliverycentrekey"));
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
                                                    Modal__B2BCreditTransactionHistory.retailermobileno_static = String.valueOf(json.get("retailermobileno"));
                                                } else {
                                                    modal__b2BPaymentDetails.retailermobileno = "";
                                                }
                                            } catch (Exception e) {
                                                modal__b2BPaymentDetails.retailermobileno = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("transactiontime")) {
                                                    modal__b2BPaymentDetails.transactiontime = String.valueOf(json.get("transactiontime"));
                                                    Modal__B2BCreditTransactionHistory.transactiontime_static = String.valueOf(json.get("transactiontime"));
                                                } else {
                                                    modal__b2BPaymentDetails.transactiontime = "";
                                                }
                                            } catch (Exception e) {
                                                modal__b2BPaymentDetails.transactiontime = "";
                                                e.printStackTrace();
                                            }







                                            try {
                                                if (json.has("uniquekey")) {
                                                    modal__b2BPaymentDetails.uniquekey = String.valueOf(json.get("uniquekey"));
                                                    Modal__B2BCreditTransactionHistory.uniquekey_static = String.valueOf(json.get("uniquekey"));
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
                                                    Modal__B2BCreditTransactionHistory.transactionvalue_static = String.valueOf(json.get("transactionvalue"));
                                                } else {
                                                    modal__b2BPaymentDetails.transactionvalue = "";
                                                }
                                            } catch (Exception e) {
                                                modal__b2BPaymentDetails.transactionvalue = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("supervisormobileno")) {
                                                    modal__b2BPaymentDetails.supervisormobileno = String.valueOf(json.get("supervisormobileno"));
                                                    Modal__B2BCreditTransactionHistory.supervisormobileno_static = String.valueOf(json.get("supervisormobileno"));
                                                } else {
                                                    modal__b2BPaymentDetails.supervisormobileno = "";
                                                }
                                            } catch (Exception e) {
                                                modal__b2BPaymentDetails.supervisormobileno = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("oldamountincredit")) {
                                                    modal__b2BPaymentDetails.oldamountincredit = String.valueOf(json.get("oldamountincredit"));
                                                    Modal__B2BCreditTransactionHistory.oldamountincredit_static = String.valueOf(json.get("oldamountincredit"));
                                                } else {
                                                    modal__b2BPaymentDetails.oldamountincredit = "";
                                                }
                                            } catch (Exception e) {
                                                modal__b2BPaymentDetails.oldamountincredit = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("newamountincredit")) {
                                                    modal__b2BPaymentDetails.newamountincredit = String.valueOf(json.get("newamountincredit"));
                                                    Modal__B2BCreditTransactionHistory.newamountincredit_static = String.valueOf(json.get("newamountincredit"));
                                                } else {
                                                    modal__b2BPaymentDetails.newamountincredit = "";
                                                }
                                            } catch (Exception e) {
                                                modal__b2BPaymentDetails.newamountincredit = "";
                                                e.printStackTrace();
                                            }


















                                            callbackB2bCreditTransactionHistoryInterface.notifySuccess("success");

                                        }
                                    }

                                     */

                                    new Modal__B2BCreditTransactionHistory("resetall");

                                    callbackB2bCreditTransactionHistoryInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    new Modal__B2BCreditTransactionHistory("resetall");

                                    callbackB2bCreditTransactionHistoryInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callbackB2bCreditTransactionHistoryInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                new Modal__B2BCreditTransactionHistory("resetall");

                                callbackB2bCreditTransactionHistoryInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {

                new Modal__B2BCreditTransactionHistory("resetall");

                callbackB2bCreditTransactionHistoryInterface.notifyVolleyError( error);

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
            new Modal__B2BCreditTransactionHistory("resetall");

            callbackB2bCreditTransactionHistoryInterface.notifyProcessingError(e);
        }

    }



    private JSONObject getJSONforPOJOClass() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("retailerkey", Modal__B2BCreditTransactionHistory.getRetailerkey_static());

            if (!Modal__B2BCreditTransactionHistory.getDeliverycentrekey_static().toString().equals("") && !Modal__B2BCreditTransactionHistory.getDeliverycentrekey_static().toString().equals("null")) {
                jsonObject.put("deliverycentrekey", Modal__B2BCreditTransactionHistory.getDeliverycentrekey_static());
            }
            if (!Modal__B2BCreditTransactionHistory.getRetailermobileno_static().toString().equals("") && !Modal__B2BCreditTransactionHistory.getRetailermobileno_static().toString().equals("null")) {
                jsonObject.put("retailermobileno", Modal__B2BCreditTransactionHistory.getRetailermobileno_static());
            }
            if (!Modal__B2BCreditTransactionHistory.getRetailerkey_static().toString().equals("") && !Modal__B2BCreditTransactionHistory.getRetailerkey_static().toString().equals("null")) {
                jsonObject.put("retailerkey", Modal__B2BCreditTransactionHistory.getRetailerkey_static());
            }
            if (!Modal__B2BCreditTransactionHistory.getRetailername_static().toString().equals("") && !Modal__B2BCreditTransactionHistory.getRetailername_static().toString().equals("null")) {
                jsonObject.put("retailername", Modal__B2BCreditTransactionHistory.getRetailername_static());
            }
            if (!Modal__B2BCreditTransactionHistory.getNewamountincredit_static().toString().equals("") && !Modal__B2BCreditTransactionHistory.getNewamountincredit_static().toString().equals("null")) {
                jsonObject.put("newamountincredit", Modal__B2BCreditTransactionHistory.getNewamountincredit_static());
            }
            if (!Modal__B2BCreditTransactionHistory.getOldamountincredit_static().toString().equals("") && !Modal__B2BCreditTransactionHistory.getOldamountincredit_static().toString().equals("null")) {
                jsonObject.put("oldamountincredit", Modal__B2BCreditTransactionHistory.getOldamountincredit_static());
            }
            if (!Modal__B2BCreditTransactionHistory.getUniquekey_static().toString().equals("") && !Modal__B2BCreditTransactionHistory.getUniquekey_static().toString().equals("null")) {
                jsonObject.put("uniquekey", Modal__B2BCreditTransactionHistory.getUniquekey_static());
            }
            if (!Modal__B2BCreditTransactionHistory.getSupervisormobileno_static().toString().equals("") && !Modal__B2BCreditTransactionHistory.getSupervisormobileno_static().toString().equals("null")) {
                jsonObject.put("supervisormobileno", Modal__B2BCreditTransactionHistory.getSupervisormobileno_static());
            }
            if (!Modal__B2BCreditTransactionHistory.getTransactiontime_static().toString().equals("") && !Modal__B2BCreditTransactionHistory.getTransactiontime_static().toString().equals("null")) {
                jsonObject.put("transactiontime", Modal__B2BCreditTransactionHistory.getTransactiontime_static());
            }
            if (!Modal__B2BCreditTransactionHistory.getTransactionvalue_static().toString().equals("") && !Modal__B2BCreditTransactionHistory.getTransactionvalue_static().toString().equals("null")) {
                jsonObject.put("transactionvalue", Modal__B2BCreditTransactionHistory.getTransactionvalue_static());
            }
            if (!Modal__B2BCreditTransactionHistory.getOrderid_static().toString().equals("") && !Modal__B2BCreditTransactionHistory.getOrderid_static().toString().equals("null")) {
                jsonObject.put("orderid", Modal__B2BCreditTransactionHistory.getOrderid_static());
            }
            if (!Modal__B2BCreditTransactionHistory.getPaymentid_static().toString().equals("") && !Modal__B2BCreditTransactionHistory.getPaymentid_static().toString().equals("null")) {
                jsonObject.put("paymentid", Modal__B2BCreditTransactionHistory.getPaymentid_static());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    
    

}
