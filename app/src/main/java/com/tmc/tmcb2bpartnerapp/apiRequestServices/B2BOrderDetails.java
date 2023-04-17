package com.tmc.tmcb2bpartnerapp.apiRequestServices;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.volleyrequestqueuehelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class B2BOrderDetails extends AsyncTask<String, String, ArrayList<Modal_B2BOrderDetails>> {
    B2BOrderDetailsInterface callback_OrderDetailsInterface;
    String ApitoCall ="" , callMethod ="" , invoiceno = "";
    JSONObject jsonToADD_Or_Update;
    ArrayList <Modal_B2BOrderDetails> orderDetailsArrayList  = new ArrayList<>();

    public B2BOrderDetails(B2BOrderDetailsInterface callback_OrderDetailsInterfacee, String getApiToCall, String apiMethodtoCall) {
        this.callback_OrderDetailsInterface = callback_OrderDetailsInterfacee;
        this.ApitoCall = getApiToCall;
        this.callMethod = apiMethodtoCall;
        if(callMethod.equals(Constants.CallADDMethod) ){
            jsonToADD_Or_Update = getJSONforPOJOClass();

        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){
            jsonToADD_Or_Update = getJSONforPOJOClass();

        }
        else if(callMethod.equals(Constants.CallGETMethod)){
            new Modal_B2BOrderDetails();
        }
        else if(callMethod.equals(Constants.CallGETListMethod) || callMethod.equals(Constants.CallGETLastEntryMethod)){
            new Modal_B2BOrderDetails();
        }
    }




    @Override
    protected ArrayList<Modal_B2BOrderDetails> doInBackground(String... strings) {


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

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,jsonToADD_Or_Update,
                    response -> {
                        if (callback_OrderDetailsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callback_OrderDetailsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){



                                    callback_OrderDetailsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callback_OrderDetailsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callback_OrderDetailsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback_OrderDetailsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callback_OrderDetailsInterface.notifyVolleyError( error);

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
            callback_OrderDetailsInterface.notifyProcessingError(e);
        }
        
        
        
        
    }
    private void getDataFromBatchDetails() {



        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApitoCall,null,
                    response -> {
                        if (callback_OrderDetailsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callback_OrderDetailsInterface.notifySuccess(Constants.item_not_Found_volley);
                                    return;
                                }
                                else if(statusCode.equals("200")){

                                    JSONArray JArray = response.getJSONArray("content");
                                    //Log.d(Constants.TAG, "convertingJsonStringintoArray Response: " + JArray);
                                    int i1 = 0;
                                    int arrayLength = JArray.length();
                                    //Log.d("Constants.TAG", "convertingJsonStringintoArray Response: " + arrayLength);

                                    if(arrayLength>0) {
                                        for (; i1 < (arrayLength); i1++) {
                                            JSONObject json = JArray.getJSONObject(i1);

                                            Modal_B2BOrderDetails modal_b2BOrderDetails = new Modal_B2BOrderDetails();

                                            try {
                                                if (json.has("b2bctgykey")) {
                                                    modal_b2BOrderDetails.b2bctgykey = String.valueOf(json.get("b2bctgykey"));
                                                } else {
                                                    modal_b2BOrderDetails.b2bctgykey = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.b2bctgykey = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("tokenno")) {
                                                    modal_b2BOrderDetails.billno = String.valueOf(json.get("tokenno"));
                                                } else {
                                                    modal_b2BOrderDetails.billno = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.billno = "";
                                                e.printStackTrace();
                                            }
                                            try {
                                                if (json.has("notes")) {
                                                    modal_b2BOrderDetails.notes = String.valueOf(json.get("notes"));
                                                } else {
                                                    modal_b2BOrderDetails.notes = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.notes = "";
                                                e.printStackTrace();
                                            }


                                            if(modal_b2BOrderDetails.getBillno().equals("")|| modal_b2BOrderDetails.getBillno().equals(null) || String.valueOf(modal_b2BOrderDetails.getBillno().toString()).toUpperCase().equals(String.valueOf("NULL"))){
                                                try {
                                                    if (json.has("billno")) {
                                                        modal_b2BOrderDetails.billno = String.valueOf(json.get("billno"));
                                                    } else {
                                                        modal_b2BOrderDetails.billno = "";
                                                    }
                                                } catch (Exception e) {
                                                    modal_b2BOrderDetails.billno = "";
                                                    e.printStackTrace();
                                                }
                                            }

                                            try {
                                                if (json.has("batchno")) {
                                                    modal_b2BOrderDetails.batchno = String.valueOf(json.get("batchno"));
                                                } else {
                                                    modal_b2BOrderDetails.batchno = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.batchno = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("supervisorname")) {
                                                    modal_b2BOrderDetails.supervisorname = String.valueOf(json.get("supervisorname"));
                                                } else {
                                                    modal_b2BOrderDetails.supervisorname = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.supervisorname = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("supervisormobileno")) {
                                                    modal_b2BOrderDetails.supervisormobileno = String.valueOf(json.get("supervisormobileno"));
                                                } else {
                                                    modal_b2BOrderDetails.supervisormobileno = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.supervisormobileno = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("deliverycentrekey")) {
                                                    modal_b2BOrderDetails.deliverycentrekey = String.valueOf(json.get("deliverycentrekey"));
                                                } else {
                                                    modal_b2BOrderDetails.deliverycentrekey = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.deliverycentrekey = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("deliverycentrename")) {
                                                    modal_b2BOrderDetails.deliverycentrename = String.valueOf(json.get("deliverycentrename"));
                                                } else {
                                                    modal_b2BOrderDetails.deliverycentrename = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.deliverycentrename = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("discount")) {
                                                    modal_b2BOrderDetails.discountamount = String.valueOf(json.get("discount"));
                                                } else {
                                                    modal_b2BOrderDetails.discountamount = "0";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.discountamount = "0";
                                                e.printStackTrace();
                                            }



                                            try {
                                                if (json.has("orderid")) {
                                                    modal_b2BOrderDetails.orderid = String.valueOf(json.get("orderid"));
                                                } else {
                                                    modal_b2BOrderDetails.orderid = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.orderid = "";
                                                e.printStackTrace();
                                            }
                                            try {
                                                if (json.has("orderplaceddate")) {
                                                    modal_b2BOrderDetails.orderplaceddate = DateParser.convertTime_to_DisplayingFormat(String.valueOf(json.get("orderplaceddate")));
                                                } else {
                                                    modal_b2BOrderDetails.orderplaceddate = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.orderplaceddate = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("payableAmount")) {
                                                    modal_b2BOrderDetails.payableAmount = String.valueOf(json.get("payableAmount"));
                                                } else {
                                                    modal_b2BOrderDetails.payableAmount = "0";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.payableAmount = "0";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("paymentmode")) {
                                                    modal_b2BOrderDetails.paymentmode = String.valueOf(json.get("paymentmode"));
                                                } else {
                                                    modal_b2BOrderDetails.paymentmode = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.paymentmode = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("priceperkg")) {
                                                    modal_b2BOrderDetails.priceperkg = String.valueOf(json.get("priceperkg"));
                                                } else {
                                                    modal_b2BOrderDetails.priceperkg = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.priceperkg = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("status")) {
                                                    modal_b2BOrderDetails.status = String.valueOf(json.get("status"));
                                                } else {
                                                    modal_b2BOrderDetails.status = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.status = "";
                                                e.printStackTrace();
                                            }



                                            try {
                                                if (json.has("femalequantity")) {
                                                    modal_b2BOrderDetails.totalfemalequantity = String.valueOf(json.get("femalequantity"));
                                                } else {
                                                    modal_b2BOrderDetails.totalfemalequantity = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.totalfemalequantity = "";
                                                e.printStackTrace();
                                            }
                                            try {
                                                if (json.has("femaleweight")) {
                                                    modal_b2BOrderDetails.totalfemaleweight = ConvertGramsToKilograms(String.valueOf(json.get("femaleweight")));
                                                } else {
                                                    modal_b2BOrderDetails.totalfemaleweight = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.totalfemaleweight = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("femalewithbabyquantity")) {
                                                    modal_b2BOrderDetails.totalfemalewithbabyquantity = String.valueOf(json.get("femalewithbabyquantity"));
                                                } else {
                                                    modal_b2BOrderDetails.totalfemalewithbabyquantity = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.totalfemalewithbabyquantity = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("femalewithbabyweight")) {
                                                    modal_b2BOrderDetails.totalfemalewithbabyweight = ConvertGramsToKilograms(String.valueOf(json.get("femalewithbabyweight")));
                                                } else {
                                                    modal_b2BOrderDetails.totalfemalewithbabyweight = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.totalfemalewithbabyweight = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("malequantity")) {
                                                    modal_b2BOrderDetails.totalmalequantity = String.valueOf(json.get("malequantity"));
                                                } else {
                                                    modal_b2BOrderDetails.totalmalequantity = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.totalmalequantity = "";
                                                e.printStackTrace();
                                            }

                                            ////

                                            try {
                                                if (json.has("maleweight")) {
                                                    modal_b2BOrderDetails.totalmaleweight = ConvertGramsToKilograms(String.valueOf(json.get("maleweight")));
                                                } else {
                                                    modal_b2BOrderDetails.totalmaleweight = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.totalmaleweight = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("totalquantity")) {
                                                    modal_b2BOrderDetails.totalquantity = String.valueOf(json.get("totalquantity"));
                                                } else {
                                                    modal_b2BOrderDetails.totalquantity = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.totalquantity = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("totalweight")) {
                                                    modal_b2BOrderDetails.totalweight = ConvertGramsToKilograms(String.valueOf(json.get("totalweight")));
                                                } else {
                                                    modal_b2BOrderDetails.totalweight = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.totalweight = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("retailermobileno")) {
                                                    modal_b2BOrderDetails.retailermobileno = String.valueOf(json.get("retailermobileno"));
                                                } else {
                                                    modal_b2BOrderDetails.retailermobileno = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.retailermobileno = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("retailername")) {
                                                    modal_b2BOrderDetails.retailername = String.valueOf(json.get("retailername"));
                                                } else {
                                                    modal_b2BOrderDetails.retailername = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.retailername = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("retailerkey")) {
                                                    modal_b2BOrderDetails.retailerkey = String.valueOf(json.get("retailerkey"));
                                                } else {
                                                    modal_b2BOrderDetails.retailerkey = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.retailerkey = "";
                                                e.printStackTrace();
                                            }



                                            try {
                                                if (json.has("retaileraddress")) {
                                                    modal_b2BOrderDetails.retaileraddress = String.valueOf(json.get("retaileraddress"));
                                                } else {
                                                    modal_b2BOrderDetails.retaileraddress = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.retaileraddress = "";
                                                e.printStackTrace();
                                            }





                                            try {
                                                if (json.has("totalprice")) {
                                                    modal_b2BOrderDetails.totalPrice = String.valueOf(json.get("totalprice"));
                                                } else {
                                                    modal_b2BOrderDetails.totalPrice = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.totalPrice = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("payableamount")) {
                                                    modal_b2BOrderDetails.payableAmount = String.valueOf(json.get("payableamount"));
                                                } else {
                                                    modal_b2BOrderDetails.payableAmount = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.payableAmount = "";
                                                e.printStackTrace();
                                            }



                                            try {
                                                if (json.has("totalprice")) {
                                                    modal_b2BOrderDetails.totalPrice = String.valueOf(json.get("totalprice"));
                                                } else {
                                                    modal_b2BOrderDetails.totalPrice = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.totalPrice = "";
                                                e.printStackTrace();
                                            }




                                            try {
                                                if (json.has("feedpriceperkg")) {
                                                    modal_b2BOrderDetails.feedPriceperkg = String.valueOf(json.get("feedpriceperkg"));
                                                } else {
                                                    modal_b2BOrderDetails.feedPriceperkg = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.feedPriceperkg = "";
                                                e.printStackTrace();
                                            }




                                            try {
                                                if (json.has("feedweight")) {
                                                    modal_b2BOrderDetails.feedWeight = ConvertGramsToKilograms(String.valueOf(json.get("feedweight")));

                                                } else {
                                                    modal_b2BOrderDetails.feedWeight = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.feedWeight = "";
                                                e.printStackTrace();
                                            }



                                            try {
                                                if (json.has("feedprice")) {
                                                    modal_b2BOrderDetails.feedPrice = String.valueOf(json.get("feedprice"));
                                                } else {
                                                    modal_b2BOrderDetails.feedPrice = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.feedPrice = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("batchwiseitemdesp")) {
                                                    modal_b2BOrderDetails.itemDespjsonArray = String.valueOf(json.get("batchwiseitemdesp"));
                                                } else {
                                                    modal_b2BOrderDetails.itemDespjsonArray = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BOrderDetails.itemDespjsonArray = "";
                                                e.printStackTrace();
                                            }


                                            orderDetailsArrayList.add(modal_b2BOrderDetails);


                                        }
                                    }

                                    if(callMethod.equals(Constants.CallGETListMethod)){
                                        if (JArray.length() == 0) {
                                            callback_OrderDetailsInterface.notifySuccess(Constants.emptyResult_volley);

                                        }else {
                                            callback_OrderDetailsInterface.notifySuccessForGettingListItem(orderDetailsArrayList);
                                        }
                                    }
                                    else{
                                        if (JArray.length() == 0) {
                                            callback_OrderDetailsInterface.notifySuccess(Constants.emptyResult_volley);

                                        }
                                        else {
                                            callback_OrderDetailsInterface.notifySuccess(Constants.successResult_volley);
                                        }
                                    }


                                }
                                else{
                                    callback_OrderDetailsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callback_OrderDetailsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback_OrderDetailsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callback_OrderDetailsInterface.notifyVolleyError( error);

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
            callback_OrderDetailsInterface.notifyProcessingError(e);
        }



    }
    private void updateEntryInBatchDetails() {



        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,jsonToADD_Or_Update,
                    response -> {
                        if (callback_OrderDetailsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callback_OrderDetailsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){



                                    callback_OrderDetailsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callback_OrderDetailsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callback_OrderDetailsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback_OrderDetailsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callback_OrderDetailsInterface.notifyVolleyError( error);

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
            callback_OrderDetailsInterface.notifyProcessingError(e);
        }

    }




    private JSONObject getJSONforPOJOClass() {
        JSONObject jsonObject = new JSONObject();




        try {


            jsonObject.put("deliverycentrekey", Modal_B2BOrderDetails.getDeliverycentrekey_Static());
            jsonObject.put("orderid", Modal_B2BOrderDetails.getOrderid_Static());

            if (!Modal_B2BOrderDetails.getOrdercanceledtime_Static().toString().equals("") && !Modal_B2BOrderDetails.getOrdercanceledtime_Static().toString().equals("null")) {
                jsonObject.put("ordercancelledtime", Modal_B2BOrderDetails.getOrdercanceledtime_Static());
            }
            if (!Modal_B2BOrderDetails.getOrderdeliveredtime_static().toString().equals("") && !Modal_B2BOrderDetails.getOrderdeliveredtime_static().toString().equals("null")) {
                jsonObject.put("orderdeliveredtime", Modal_B2BOrderDetails.getOrderdeliveredtime_static());
            }
            if (!Modal_B2BOrderDetails.getNotes_static().toString().equals("") && !Modal_B2BOrderDetails.getNotes_static().toString().equals("null")) {
                jsonObject.put("notes", Modal_B2BOrderDetails.getNotes_static());
            }
            if (!Modal_B2BOrderDetails.getB2bctgykey_Static().toString().equals("") && !Modal_B2BOrderDetails.getB2bctgykey_Static().toString().equals("null")) {
                jsonObject.put("b2bctgykey", Modal_B2BOrderDetails.getB2bctgykey_Static());
            }
            if (!Modal_B2BOrderDetails.getBatchno_Static().toString().equals("") && !Modal_B2BOrderDetails.getBatchno_Static().toString().equals("null")) {
                jsonObject.put("batchno", Modal_B2BOrderDetails.getBatchno_Static());
            }

            if (!Modal_B2BOrderDetails.getBillno_static().toString().equals("") && !Modal_B2BOrderDetails.getBillno_static().toString().equals("null")) {
                jsonObject.put("billno", Modal_B2BOrderDetails.getBillno_static());
            }


            if (!Modal_B2BOrderDetails.getInvoiceno_Static().toString().equals("") && !Modal_B2BOrderDetails.getInvoiceno_Static().toString().equals("null")) {
                jsonObject.put("invoiceno", Modal_B2BOrderDetails.getInvoiceno_Static());
            }
           /* if (!Modal_B2BOrderDetails.getDeliverycentrekey_Static().toString().equals("") && !Modal_B2BOrderDetails.getDeliverycentrekey_Static().toString().equals("null")) {
                jsonObject.put("deliverycentrekey", Modal_B2BOrderDetails.getDeliverycentrekey_Static());
            }
            if (!Modal_B2BOrderDetails.getOrderid_Static().toString().equals("") && !Modal_B2BOrderDetails.getOrderid_Static().toString().equals("null")) {
                jsonObject.put("orderid", Modal_B2BOrderDetails.getOrderid_Static());
            }
            */


            if (!Modal_B2BOrderDetails.getSupervisorname_Static().toString().equals("") && !Modal_B2BOrderDetails.getSupervisorname_Static().toString().equals("null")) {
                jsonObject.put("supervisorname", Modal_B2BOrderDetails.getSupervisorname_Static());
            }



            if (!Modal_B2BOrderDetails.getSupervisormobileno_Static().toString().equals("") && !Modal_B2BOrderDetails.getSupervisormobileno_Static().toString().equals("null")) {
                jsonObject.put("supervisormobileno", Modal_B2BOrderDetails.getSupervisormobileno_Static());
            }


            if (!Modal_B2BOrderDetails.getDeliverycentrename_Static().toString().equals("") && !Modal_B2BOrderDetails.getDeliverycentrename_Static().toString().equals("null")) {
                jsonObject.put("deliverycentrename", Modal_B2BOrderDetails.getDeliverycentrename_Static());
            }
            if (!Modal_B2BOrderDetails.getDiscountamount_Static().toString().equals("") && !Modal_B2BOrderDetails.getDiscountamount_Static().toString().equals("null")) {




                try {
                    String value = Modal_B2BOrderDetails.getDiscountamount_Static();
                    value = value.replaceAll("[^\\d.]", "");

                    if(value.equals("") || value.equals(null)){
                        value = "0";
                    }

                    if(value.equals("") || value.equals(null)){
                        value = "0";
                    }


                    double value_double = Double.parseDouble(value);

                    jsonObject.put("discount", value_double);
                }catch (Exception e){
                    jsonObject.put("discount", Modal_B2BOrderDetails.getDiscountamount_Static());

                    e.printStackTrace();
                }





/*

                try{
                    jsonObject.put("discount", Double.parseDouble(String.valueOf(Modal_B2BOrderDetails.getDiscountamount_Static())));

                }
                catch (Exception e){
                    jsonObject.put("discount", Modal_B2BOrderDetails.getDiscountamount_Static());
                    e.printStackTrace();
                }

 */
              //  jsonObject.put("discountamount", Modal_B2BOrderDetails.getDiscountamount_Static());
            }

            if (!Modal_B2BOrderDetails.getOrderplaceddate_Static().toString().equals("") && !Modal_B2BOrderDetails.getOrderplaceddate_Static().toString().equals("null")) {
                jsonObject.put("orderplaceddate", Modal_B2BOrderDetails.getOrderplaceddate_Static());
            }
            if (!Modal_B2BOrderDetails.getPayableAmount_Static().toString().equals("") && !Modal_B2BOrderDetails.getPayableAmount_Static().toString().equals("null")) {


                try {
                    String value = Modal_B2BOrderDetails.getPayableAmount_Static();
                    value = value.replaceAll("[^\\d.]", "");

                    if(value.equals("") || value.equals(null)){
                        value = "0";
                    }

                    if(value.equals("") || value.equals(null)){
                        value = "0";
                    }


                    double value_double = Double.parseDouble(value);

                    jsonObject.put("totalprice", value_double);
                }catch (Exception e){
                    jsonObject.put("totalprice", Modal_B2BOrderDetails.getPayableAmount_Static());

                    e.printStackTrace();
                }




/*
                try{
                    jsonObject.put("totalprice", Double.parseDouble(String.valueOf(Modal_B2BOrderDetails.getPayableAmount_Static())));

                }
                catch (Exception e){
                    jsonObject.put("totalprice", Modal_B2BOrderDetails.getPayableAmount_Static());
                    e.printStackTrace();
                }

 */
                //jsonObject.put("payableamount", Modal_B2BOrderDetails.getPayableAmount_Static());
            }
            if (!Modal_B2BOrderDetails.getPaymentMode_Static().toString().equals("") && !Modal_B2BOrderDetails.getPaymentMode_Static().toString().equals("null")) {
                jsonObject.put("paymentmode", Modal_B2BOrderDetails.getPaymentMode_Static());
            }
         /*   if (!Modal_B2BOrderDetails.getPriceperkg_Static().toString().equals("") && !Modal_B2BOrderDetails.getPriceperkg_Static().toString().equals("null")) {
                jsonObject.put("priceperkg", Modal_B2BOrderDetails.getPriceperkg_Static());
            }

          */
            if (!Modal_B2BOrderDetails.getStatus_Static().toString().equals("") && !Modal_B2BOrderDetails.getStatus_Static().toString().equals("null")) {
                jsonObject.put("status", Modal_B2BOrderDetails.getStatus_Static());
            }
            if (!Modal_B2BOrderDetails.getTotalfemaleweight_Static().toString().equals("") && !Modal_B2BOrderDetails.getTotalfemaleweight_Static().toString().equals("null")) {

                try {
                    String weightinGrams_str = Modal_B2BOrderDetails.getTotalfemaleweight_Static();
                    weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");

                    if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                        weightinGrams_str = "0";
                    }

                    if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                        weightinGrams_str = "0";
                    }


                    weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                    double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                    jsonObject.put("femaleweight", weightinGrams_double);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (!Modal_B2BOrderDetails.getTotalfemalequantity_Static().toString().equals("") && !Modal_B2BOrderDetails.getTotalfemalequantity_Static().toString().equals("null")) {
                jsonObject.put("femalequantity", Modal_B2BOrderDetails.getTotalfemalequantity_Static());
            }
            if (!Modal_B2BOrderDetails.getTotalfemalewithbabyquantity_Static().toString().equals("") && !Modal_B2BOrderDetails.getTotalfemalewithbabyquantity_Static().toString().equals("null")) {
                jsonObject.put("femalewithbabyquantity", Modal_B2BOrderDetails.getTotalfemalewithbabyquantity_Static());
            }
            if (!Modal_B2BOrderDetails.getTotalfemalewithbabyweight_Static().toString().equals("") && !Modal_B2BOrderDetails.getTotalfemalewithbabyweight_Static().toString().equals("null")) {
                try{
                String weightinGrams_str = Modal_B2BOrderDetails.getTotalfemalewithbabyweight_Static();
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                    if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                        weightinGrams_str = "0";
                    }

                weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                jsonObject.put("femalewithbabyweight", weightinGrams_double);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (!Modal_B2BOrderDetails.getTotalmalequantity_Static().toString().equals("") && !Modal_B2BOrderDetails.getTotalmalequantity_Static().toString().equals("null")) {
                jsonObject.put("malequantity", Modal_B2BOrderDetails.getTotalmalequantity_Static());
            }
            if (!Modal_B2BOrderDetails.getTotalmaleweight_Static().toString().equals("") && !Modal_B2BOrderDetails.getTotalmaleweight_Static().toString().equals("null")) {
                try {
                    String weightinGrams_str = Modal_B2BOrderDetails.getTotalmaleweight_Static();
                    weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");

                    if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                        weightinGrams_str = "0";
                    }

                    weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                    double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                    jsonObject.put("maleweight", weightinGrams_double);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (!Modal_B2BOrderDetails.getTotalweight_Static().toString().equals("") && !Modal_B2BOrderDetails.getTotalweight_Static().toString().equals("null")) {
                try{
                String weightinGrams_str = Modal_B2BOrderDetails.getTotalweight_Static();
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                    if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                        weightinGrams_str = "0";
                    }

                weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                double weightinGrams_double = Double.parseDouble(weightinGrams_str);


                jsonObject.put("totalweight", weightinGrams_double);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            if (!Modal_B2BOrderDetails.getTotalquantity_Static().toString().equals("") && !Modal_B2BOrderDetails.getTotalquantity_Static().toString().equals("null")) {
                try{
                    jsonObject.put("totalquantity", Integer.parseInt(String.valueOf(Modal_B2BOrderDetails.getTotalquantity_Static())));

                }
                catch (Exception e){
                    jsonObject.put("totalquantity", Modal_B2BOrderDetails.getTotalquantity_Static());
                    e.printStackTrace();
                }
            }
            if (!Modal_B2BOrderDetails.getRetailerkey_Static().toString().equals("") && !Modal_B2BOrderDetails.getRetailerkey_Static().toString().equals("null")) {
                jsonObject.put("retailerkey", Modal_B2BOrderDetails.getRetailerkey_Static());
            }
            if (!Modal_B2BOrderDetails.getRetailermobileno_Static().toString().equals("") && !Modal_B2BOrderDetails.getRetailermobileno_Static().toString().equals("null")) {
                jsonObject.put("retailermobileno", Modal_B2BOrderDetails.getRetailermobileno_Static());
            }
            if (!Modal_B2BOrderDetails.getRetailername_Static().toString().equals("") && !Modal_B2BOrderDetails.getRetailername_Static().toString().equals("null")) {
                jsonObject.put("retailername", Modal_B2BOrderDetails.getRetailername_Static());
            }

            if (!Modal_B2BOrderDetails.getRetaileraddress_static().toString().equals("") && !Modal_B2BOrderDetails.getRetaileraddress_static().toString().equals("null")) {
                jsonObject.put("retaileraddress", Modal_B2BOrderDetails.getRetaileraddress_static());
            }
            if (!Modal_B2BOrderDetails.getTotalPrice_Static().toString().equals("") && !Modal_B2BOrderDetails.getTotalPrice_Static().toString().equals("null")) {
            //    jsonObject.put("price", Modal_B2BOrderDetails.getTotalPrice_Static());


                try {
                    String value = Modal_B2BOrderDetails.getTotalPrice_Static();
                    value = value.replaceAll("[^\\d.]", "");

                    if(value.equals("") || value.equals(null)){
                        value = "0";
                    }

                    if(value.equals("") || value.equals(null)){
                        value = "0";
                    }


                    double value_double = Double.parseDouble(value);

                    jsonObject.put("price", value_double);
                }catch (Exception e){
                    jsonObject.put("price", Modal_B2BOrderDetails.getTotalPrice_Static());

                    e.printStackTrace();
                }





/*
                try{
                    jsonObject.put("price", Integer.parseInt(String.valueOf(Modal_B2BOrderDetails.getTotalPrice_Static())));

                }
                catch (Exception e){
                    jsonObject.put("price", Modal_B2BOrderDetails.getTotalPrice_Static());
                    e.printStackTrace();
                }

 */
            }



            if (!Modal_B2BOrderDetails.getFeedPrice_Static().toString().equals("") && !Modal_B2BOrderDetails.getFeedPrice_Static().toString().equals("null")) {
                //    jsonObject.put("price", Modal_B2BOrderDetails.getTotalPrice_Static());


                try {
                    String value = Modal_B2BOrderDetails.getFeedPrice_Static();
                    value = value.replaceAll("[^\\d.]", "");

                    if(value.equals("") || value.equals(null)){
                        value = "0";
                    }

                    if(value.equals("") || value.equals(null)){
                        value = "0";
                    }


                    double value_double = Double.parseDouble(value);

                    jsonObject.put("feedprice", value_double);
                }catch (Exception e){
                    jsonObject.put("feedprice", Modal_B2BOrderDetails.getFeedPrice_Static());

                    e.printStackTrace();
                }


               /* try{
                    jsonObject.put("feedprice", Integer.parseInt(String.valueOf(Modal_B2BOrderDetails.getFeedPrice_Static())));

                }
                catch (Exception e){
                    jsonObject.put("feedprice", Modal_B2BOrderDetails.getFeedPrice_Static());
                    e.printStackTrace();
                }

                */
            }


            if (!Modal_B2BOrderDetails.getFeedPricePerKG_Static().toString().equals("") && !Modal_B2BOrderDetails.getFeedPricePerKG_Static().toString().equals("null")) {
                //    jsonObject.put("price", Modal_B2BOrderDetails.getTotalPrice_Static());

                try {
                    String value = Modal_B2BOrderDetails.getFeedPricePerKG_Static();
                    value = value.replaceAll("[^\\d.]", "");

                    if(value.equals("") || value.equals(null)){
                        value = "0";
                    }

                    if(value.equals("") || value.equals(null)){
                        value = "0";
                    }


                    double value_double = Double.parseDouble(value);

                    jsonObject.put("feedpriceperkg", value_double);
                }catch (Exception e){
                    jsonObject.put("feedpriceperkg", Modal_B2BOrderDetails.getFeedPricePerKG_Static());

                    e.printStackTrace();
                }



/*
                try{
                    jsonObject.put("feedpriceperkg", Integer.parseInt(String.valueOf(Modal_B2BOrderDetails.getFeedPricePerKG_Static())));

                }
                catch (Exception e){
                    jsonObject.put("feedpriceperkg", Modal_B2BOrderDetails.getFeedPricePerKG_Static());
                    e.printStackTrace();
                }

 */
            }


            if (!Modal_B2BOrderDetails.getFeedWeight_Static().toString().equals("") && !Modal_B2BOrderDetails.getFeedWeight_Static().toString().equals("null")) {
                try {
                    String weightinGrams_str = Modal_B2BOrderDetails.getFeedWeight_Static();
                    weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                    if (weightinGrams_str.equals("") || weightinGrams_str.equals(null)) {
                        weightinGrams_str = "0";
                    }

                    weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                    double weightinGrams_double = Double.parseDouble(weightinGrams_str);


                    jsonObject.put("feedweight", weightinGrams_double);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

                if (!Modal_B2BOrderDetails.getItemDespBatchListjsonArray_static().toString().equals("") && !Modal_B2BOrderDetails.getItemDespBatchListjsonArray_static().toString().equals("null")) {
                    jsonObject.put("batchwiseitemdesp", new JSONObject( Modal_B2BOrderDetails.getItemDespBatchListjsonArray_static()));
                }

                if (!Modal_B2BOrderDetails.getBatchnoList_static().toString().equals("") && !Modal_B2BOrderDetails.getBatchnoList_static().toString().equals("null")) {
                    jsonObject.put("batchnolist", ( Modal_B2BOrderDetails.getBatchnoList_static()));
                }






        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        
        
        
        return jsonObject;
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
