package com.tmc.tmcb2bpartnerapp.apiRequestServices;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartOrderDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_UpdatedB2BCartOrderDetails;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.volleyrequestqueuehelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class B2BCartOrderDetails extends AsyncTask<String, String, ArrayList<Modal_B2BCartOrderDetails>> {
    B2BCartOrderDetailsInterface callbackB2BCartOrderDetaillsInterface;
    String ApitoCall ="" , callMethod ="";
    JSONObject jsonToADD_Or_Update;
    ArrayList<Modal_B2BCartOrderDetails> arrayList  = new ArrayList<>();



    public B2BCartOrderDetails(B2BCartOrderDetailsInterface callback_b2BCartDetaillsInterfacee, String getApiToCall, String apiMethodtoCall) {

        this.callbackB2BCartOrderDetaillsInterface = callback_b2BCartDetaillsInterfacee;
        this.ApitoCall = getApiToCall;
        this.callMethod = apiMethodtoCall;
        if(callMethod.equals(Constants.CallADDMethod) ){
            jsonToADD_Or_Update = getJSONforPOJOClass();

        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){
            jsonToADD_Or_Update = getUpdateJSONforPOJOClass();

        }
        else if(callMethod.equals(Constants.CallGETMethod)){
            new Modal_B2BCartOrderDetails();
            new Modal_UpdatedB2BCartOrderDetails();
        }
        else if(callMethod.equals(Constants.CallDELETEMethod)){
            new Modal_B2BCartOrderDetails();
            new Modal_UpdatedB2BCartOrderDetails();
        }
        else if(callMethod.equals(Constants.CallGETListMethod) || callMethod.equals(Constants.CallGETLastEntryMethod)){
            new Modal_B2BCartOrderDetails();
            new Modal_UpdatedB2BCartOrderDetails();
        }

    }

    private JSONObject getUpdateJSONforPOJOClass() {
        JSONObject jsonObject = new JSONObject();


        try{
            if(Modal_UpdatedB2BCartOrderDetails.itemaddeddate_boolean){
                jsonObject.put("itemaddeddate",Modal_UpdatedB2BCartOrderDetails.getItemaddeddate());

            }
            if(Modal_UpdatedB2BCartOrderDetails.retailername_boolean){
                jsonObject.put("retailername",Modal_UpdatedB2BCartOrderDetails.getRetailername());

            }
            if(Modal_UpdatedB2BCartOrderDetails.retailerkey_boolean){
                jsonObject.put("retailerkey",Modal_UpdatedB2BCartOrderDetails.getRetailerkey());

            }


            if(Modal_UpdatedB2BCartOrderDetails.isretailerAddressUpdated_boolean){
                jsonObject.put("retaileraddress",Modal_UpdatedB2BCartOrderDetails.getRetaileraddress());

            }


            if(Modal_UpdatedB2BCartOrderDetails.retailermobileno_boolean){
                jsonObject.put("retailermobileno",Modal_UpdatedB2BCartOrderDetails.getRetailermobileno());

            }
            if(Modal_UpdatedB2BCartOrderDetails.deliverycentername_boolean){
                jsonObject.put("deliverycentrename",Modal_UpdatedB2BCartOrderDetails.getDeliverycentername());

            }

            if(Modal_UpdatedB2BCartOrderDetails.priceperkg_boolean){
                jsonObject.put("priceperkg",Modal_UpdatedB2BCartOrderDetails.getPriceperkg());

            }
            if(Modal_UpdatedB2BCartOrderDetails.isPaymentmode_boolean){
                jsonObject.put("paymentmode",Modal_UpdatedB2BCartOrderDetails.getPaymentMode());

            }

            if(Modal_UpdatedB2BCartOrderDetails.discount_boolean){




                try {
                    String value = Modal_UpdatedB2BCartOrderDetails.getDiscount();
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
                    jsonObject.put("discount",Modal_UpdatedB2BCartOrderDetails.getDiscount());

                    e.printStackTrace();
                }



            }



            if (Modal_UpdatedB2BCartOrderDetails.feedPrice_boolean) {





                try {
                    String value = Modal_UpdatedB2BCartOrderDetails.getFeedPrice();
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
                    jsonObject.put("feedprice",Modal_UpdatedB2BCartOrderDetails.getFeedPrice());

                    e.printStackTrace();
                }




            }




            if(Modal_UpdatedB2BCartOrderDetails.feedWeight_boolean){

                try {
                    String weightinGrams_str = Modal_UpdatedB2BCartOrderDetails.getFeedWeight();
                    weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");

                    if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                        weightinGrams_str = "0";
                    }

                    if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                        weightinGrams_str = "0";
                    }


                    weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                    double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                    jsonObject.put("feedweight", weightinGrams_double);
                }catch (Exception e){
                    e.printStackTrace();
                }

                //jsonObject.put("feedweight",Modal_UpdatedB2BCartOrderDetails.getFeedWeight());

            }


            if(Modal_UpdatedB2BCartOrderDetails.feedPricePerKG_boolean){
                //    jsonObject.put("price", Modal_B2BOrderDetails.getTotalPrice_Static());


                try {
                    String value = Modal_UpdatedB2BCartOrderDetails.getFeedPriceperkg();
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
                    jsonObject.put("feedpriceperkg",Modal_UpdatedB2BCartOrderDetails.getFeedPriceperkg());

                    e.printStackTrace();
                }


            }


            jsonObject.put("orderid",Modal_UpdatedB2BCartOrderDetails.getOrderid());

            jsonObject.put("deliverycentrekey",Modal_UpdatedB2BCartOrderDetails.getDeliverycenterkey());

            jsonObject.put("batchno",Modal_UpdatedB2BCartOrderDetails.getBatchno());



        }
        catch (Exception e){
            e.printStackTrace();
        }

        return jsonObject;



    }


    @Override
    protected ArrayList<Modal_B2BCartOrderDetails> doInBackground(String... strings) {

        if(callMethod.equals(Constants.CallADDMethod)){
            addEntryInBatchDetails();
        }
        else if(callMethod.equals(Constants.CallGETMethod) || callMethod.equals(Constants.CallGETListMethod) || callMethod.equals(Constants.CallGETLastEntryMethod)){
            getDataFromBatchDetails();
        }
        else if(callMethod.equals(Constants.CallDELETEMethod)){

            deleteEntryFromDB();

        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){
            updateEntryInBatchDetails();
        }






        return null;
    }

    private void deleteEntryFromDB() {

        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,null,
                    response -> {
                        if (callbackB2BCartOrderDetaillsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callbackB2BCartOrderDetaillsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){



                                    callbackB2BCartOrderDetaillsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callbackB2BCartOrderDetaillsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callbackB2BCartOrderDetaillsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callbackB2BCartOrderDetaillsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callbackB2BCartOrderDetaillsInterface.notifyVolleyError( error);

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
            callbackB2BCartOrderDetaillsInterface.notifyProcessingError(e);
        }




    }

    private void addEntryInBatchDetails() {


        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,jsonToADD_Or_Update,
                    response -> {
                        if (callbackB2BCartOrderDetaillsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callbackB2BCartOrderDetaillsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){



                                    callbackB2BCartOrderDetaillsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callbackB2BCartOrderDetaillsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callbackB2BCartOrderDetaillsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callbackB2BCartOrderDetaillsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callbackB2BCartOrderDetaillsInterface.notifyVolleyError( error);

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
            callbackB2BCartOrderDetaillsInterface.notifyProcessingError(e);
        }



    }


    private void getDataFromBatchDetails() {



        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApitoCall,null,
                    response -> {
                        if (callbackB2BCartOrderDetaillsInterface != null) {
                            try {
                                JSONArray JArray = response.getJSONArray("content");


                                if (JArray.length() == 0) {
                                    callbackB2BCartOrderDetaillsInterface.notifySuccess(Constants.emptyResult_volley);

                                }
                                else {


                                    int i1 = 0;
                                    int arrayLength = JArray.length();

                                    if (arrayLength > 0) {
                                        for (; i1 < (arrayLength); i1++) {
                                            JSONObject json = JArray.getJSONObject(i1);
                                            try {
                                                if (json.has("orderid")) {
                                                    Modal_B2BCartOrderDetails.orderid = String.valueOf(json.get("orderid"));

                                                } else {
                                                    Modal_B2BCartOrderDetails.orderid = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BCartOrderDetails.orderid = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("totalcount")) {
                                                    Modal_B2BCartOrderDetails.totalCount = String.valueOf(json.get("totalcount"));

                                                } else {
                                                    Modal_B2BCartOrderDetails.totalCount = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BCartOrderDetails.totalCount = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("batchno")) {
                                                    Modal_B2BCartOrderDetails.batchno = String.valueOf(json.get("batchno"));

                                                } else {
                                                    Modal_B2BCartOrderDetails.batchno = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BCartOrderDetails.batchno = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("paymentmode")) {
                                                    Modal_B2BCartOrderDetails.paymentMode = String.valueOf(json.get("paymentmode"));
                                                } else {
                                                    Modal_B2BCartOrderDetails.paymentMode = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BCartOrderDetails.paymentMode = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("invoiceno")) {
                                                    Modal_B2BCartOrderDetails.invoiceno = String.valueOf(json.get("invoiceno"));

                                                } else {
                                                    Modal_B2BCartOrderDetails.invoiceno = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BCartOrderDetails.invoiceno = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("deliverycentrekey")) {
                                                    Modal_B2BCartOrderDetails.deliverycenterkey = String.valueOf(json.get("deliverycentrekey"));

                                                } else {
                                                    Modal_B2BCartOrderDetails.deliverycenterkey = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BCartOrderDetails.deliverycenterkey = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("deliverycentrename")) {
                                                    Modal_B2BCartOrderDetails.deliverycentername = String.valueOf(json.get("deliverycentrename"));

                                                } else {
                                                    Modal_B2BCartOrderDetails.deliverycentername = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BCartOrderDetails.deliverycentername = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("priceperkg")) {
                                                    Modal_B2BCartOrderDetails.priceperkg = String.valueOf(json.get("priceperkg"));

                                                } else {
                                                    Modal_B2BCartOrderDetails.priceperkg = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BCartOrderDetails.priceperkg = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("retailerkey")) {
                                                    Modal_B2BCartOrderDetails.retailerkey = String.valueOf(json.get("retailerkey"));

                                                } else {
                                                    Modal_B2BCartOrderDetails.retailerkey = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BCartOrderDetails.retailerkey = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("retaileraddress")) {
                                                    Modal_B2BCartOrderDetails.retaileraddress = String.valueOf(json.get("retaileraddress"));

                                                } else {
                                                    Modal_B2BCartOrderDetails.retaileraddress = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BCartOrderDetails.retaileraddress = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("retailermobileno")) {
                                                    Modal_B2BCartOrderDetails.retailermobileno = String.valueOf(json.get("retailermobileno"));

                                                } else {
                                                    Modal_B2BCartOrderDetails.retailermobileno = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BCartOrderDetails.retailermobileno = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("retailername")) {
                                                    Modal_B2BCartOrderDetails.retailername = String.valueOf(json.get("retailername"));

                                                } else {
                                                    Modal_B2BCartOrderDetails.retailername = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BCartOrderDetails.retailername = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("discount")) {
                                                    Modal_B2BCartOrderDetails.discountAmount = String.valueOf(json.get("discount"));

                                                } else {
                                                    Modal_B2BCartOrderDetails.discountAmount = "0";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BCartOrderDetails.discountAmount = "0";
                                                e.printStackTrace();
                                            }



                                            try {
                                                if (json.has("feedprice")) {
                                                    Modal_B2BCartOrderDetails.feedPrice = String.valueOf(json.get("feedprice"));

                                                } else {
                                                    Modal_B2BCartOrderDetails.feedPrice = "0";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BCartOrderDetails.feedPrice = "0";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("feedweight")) {
                                                    Modal_B2BCartOrderDetails.feedWeight = ConvertGramsToKilograms(String.valueOf(json.get("feedweight")));

                                                } else {
                                                    Modal_B2BCartOrderDetails.feedWeight = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BCartOrderDetails.feedWeight = "";
                                                e.printStackTrace();
                                            }



                                            try {
                                                if (json.has("feedpriceperkg")) {
                                                    Modal_B2BCartOrderDetails.feedPriceperkg = String.valueOf(json.get("feedpriceperkg"));

                                                } else {
                                                    Modal_B2BCartOrderDetails.feedPriceperkg = "0";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BCartOrderDetails.feedPriceperkg = "0";
                                                e.printStackTrace();
                                            }


                                            callbackB2BCartOrderDetaillsInterface.notifySuccess(Constants.successResult_volley);


                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                callbackB2BCartOrderDetaillsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callbackB2BCartOrderDetaillsInterface.notifyVolleyError( error);

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
            callbackB2BCartOrderDetaillsInterface.notifyProcessingError(e);
        }



    }

    private void updateEntryInBatchDetails() {

        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,jsonToADD_Or_Update,
                    response -> {
                        if (callbackB2BCartOrderDetaillsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callbackB2BCartOrderDetaillsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){



                                    callbackB2BCartOrderDetaillsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callbackB2BCartOrderDetaillsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callbackB2BCartOrderDetaillsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callbackB2BCartOrderDetaillsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callbackB2BCartOrderDetaillsInterface.notifyVolleyError( error);

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
            callbackB2BCartOrderDetaillsInterface.notifyProcessingError(e);
        }



    }


    private JSONObject getJSONforPOJOClass() {
        JSONObject jsonObject = new JSONObject();
        try{
            if(!Modal_B2BCartOrderDetails.getBatchno().toString().equals("") && !Modal_B2BCartOrderDetails.getBatchno().toString().equals("null")){
                jsonObject.put("batchno",String.valueOf(Modal_B2BCartOrderDetails.getBatchno()));
            }

            if(!Modal_B2BCartOrderDetails.getOrderid().toString().equals("") && !Modal_B2BCartOrderDetails.getOrderid().toString().equals("null")){
                jsonObject.put("orderid",String.valueOf(Modal_B2BCartOrderDetails.getOrderid()));
            }

            if(!Modal_B2BCartOrderDetails.getInvoiceno().toString().equals("") && !Modal_B2BCartOrderDetails.getInvoiceno().toString().equals("null")){
                jsonObject.put("invoiceno",String.valueOf(Modal_B2BCartOrderDetails.getInvoiceno()));
            }

           if(!Modal_B2BCartOrderDetails.getRetailerkey().toString().equals("") && !Modal_B2BCartOrderDetails.getRetailerkey().toString().equals("null")){
                jsonObject.put("retailerkey",String.valueOf(Modal_B2BCartOrderDetails.getRetailerkey()));
            }

            if(!Modal_B2BCartOrderDetails.getRetailername().toString().equals("") && !Modal_B2BCartOrderDetails.getRetailername().toString().equals("null")){
                jsonObject.put("retailername",String.valueOf(Modal_B2BCartOrderDetails.getRetailername()));
            }

            if(!Modal_B2BCartOrderDetails.getRetaileraddress().toString().equals("") && !Modal_B2BCartOrderDetails.getRetaileraddress().toString().equals("null")){
                jsonObject.put("retaileraddress",String.valueOf(Modal_B2BCartOrderDetails.getRetaileraddress()));
            }


            if(!Modal_B2BCartOrderDetails.getDeliverycenterkey ().toString().equals("") && !Modal_B2BCartOrderDetails.getDeliverycenterkey().toString().equals("null")){
                jsonObject.put("deliverycentrekey",String.valueOf(Modal_B2BCartOrderDetails.getDeliverycenterkey()));
            }

            if(!Modal_B2BCartOrderDetails.getDeliverycentername().toString().equals("") && !Modal_B2BCartOrderDetails.getDeliverycentername().toString().equals("null")){
                jsonObject.put("deliverycentrename",String.valueOf(Modal_B2BCartOrderDetails.getDeliverycentername()));
            }


            if(!Modal_B2BCartOrderDetails.getRetailermobileno().toString().equals("") && !Modal_B2BCartOrderDetails.getRetailermobileno().toString().equals("null")){
                jsonObject.put("retailermobileno",String.valueOf(Modal_B2BCartOrderDetails.getRetailermobileno()));
            }




            if(!Modal_B2BCartOrderDetails.getPaymentMode().toString().equals("") && !Modal_B2BCartOrderDetails.getPaymentMode().toString().equals("null")){
                jsonObject.put("paymentmode",String.valueOf(Modal_B2BCartOrderDetails.getPaymentMode()));
            }

            if(!Modal_B2BCartOrderDetails.getItemaddeddate().toString().equals("") && !Modal_B2BCartOrderDetails.getItemaddeddate().toString().equals("null")){
                jsonObject.put("itemaddeddate",String.valueOf(Modal_B2BCartOrderDetails.getItemaddeddate()));
            }


            if(!Modal_B2BCartOrderDetails.getDiscountAmount().toString().equals("") && !Modal_B2BCartOrderDetails.getDiscountAmount().toString().equals("null")){


                try {
                    String value = Modal_B2BCartOrderDetails.getDiscountAmount();
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
                    jsonObject.put("discount",String.valueOf(Modal_B2BCartOrderDetails.getDiscountAmount()));

                    e.printStackTrace();
                }







            }

            if(!Modal_B2BCartOrderDetails.getFeedPrice().toString().equals("") && !Modal_B2BCartOrderDetails.getFeedPrice().toString().equals("null")){


                try {
                    String value = Modal_B2BCartOrderDetails.getFeedPrice();
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
                    jsonObject.put("feedprice",String.valueOf(Modal_B2BCartOrderDetails.getFeedPrice()));

                    e.printStackTrace();
                }







            }

            if(!Modal_B2BCartOrderDetails.getFeedWeight().toString().equals("") && !Modal_B2BCartOrderDetails.getFeedWeight().toString().equals("null")){



                try {
                    String weightinGrams_str = Modal_B2BCartOrderDetails.getFeedWeight();
                    weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");

                    if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                        weightinGrams_str = "0";
                    }

                    if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                        weightinGrams_str = "0";
                    }


                    weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                    double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                    jsonObject.put("feedweight", weightinGrams_double);
                }catch (Exception e){
                    e.printStackTrace();
                }




               // jsonObject.put("feedweight",String.valueOf(Modal_B2BCartOrderDetails.getFeedWeight()));
            }

            if(!Modal_B2BCartOrderDetails.getFeedPriceperkg().toString().equals("") && !Modal_B2BCartOrderDetails.getFeedPriceperkg().toString().equals("null")){



                try {
                    String value = Modal_B2BCartOrderDetails.getFeedPriceperkg();
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
                    jsonObject.put("feedpriceperkg",String.valueOf(Modal_B2BCartOrderDetails.getFeedPriceperkg()));

                    e.printStackTrace();
                }






            }


        }
        catch (Exception e){
            e.printStackTrace();
        }

        return  jsonObject;
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
