package com.tmc.tmcb2bpartnerapp.apiRequestServices;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchDetailsInterface;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BBatchDetailsStatic;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BBatchDetailsUpdate;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.volleyrequestqueuehelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class B2BBatchDetails extends AsyncTask<String, String, List<Modal_B2BBatchDetailsStatic>> {
    String ApitoCall ="", callMethod ="";
    B2BBatchDetailsInterface callback_b2BBatchDetailsInterface = null;
    JSONObject jsonToADD_Or_Update = new JSONObject();
    ArrayList<Modal_B2BBatchDetails> batchDetailsArrayList = new ArrayList();

    public B2BBatchDetails(B2BBatchDetailsInterface callback_b2BBatchDetailsInterfacee, String addApiToCall, String callADDMethod, Modal_B2BBatchDetailsStatic modal_b2BBatchDetailssStatic) {
    this.ApitoCall = addApiToCall;
    this.callMethod = callADDMethod;
    this.callback_b2BBatchDetailsInterface = callback_b2BBatchDetailsInterfacee;
   // this.Modal_B2BBatchDetailsStatic = modal_b2BBatchDetailssStatic;
    jsonToADD_Or_Update = getJSONforPOJOClass();

    }

    public B2BBatchDetails(B2BBatchDetailsInterface callback_b2BBatchDetailsInterface, String addApiToCall, String callGetMethod) {
        this.ApitoCall = addApiToCall;
        this.callMethod = callGetMethod;
        this.callback_b2BBatchDetailsInterface = callback_b2BBatchDetailsInterface;
        if(callMethod.equals(Constants.CallADDMethod) ){
            jsonToADD_Or_Update = getJSONforPOJOClass();

        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){
            jsonToADD_Or_Update = getJSON_to_Update_FromPOJOClass();
        }
        else if(callMethod.equals(Constants.CallGETMethod)){
            new Modal_B2BBatchDetailsStatic();
            new Modal_B2BBatchDetailsUpdate();
        }
        else if(callMethod.equals(Constants.CallGETListMethod) || callMethod.equals(Constants.CallGETLastEntryMethod)){
            new Modal_B2BBatchDetailsStatic();
            new Modal_B2BBatchDetailsUpdate();
        }
    }




    @Override
    protected List<Modal_B2BBatchDetailsStatic> doInBackground(String... strings) {

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
                        if (callback_b2BBatchDetailsInterface != null) {
                            try {
                                //converting jsonSTRING into array
                              //  JSONArray JArray = response.getJSONArray("content");
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callback_b2BBatchDetailsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){
                                    Update_Local_POJOClass();
                                 //   callback_b2BBatchDetailsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callback_b2BBatchDetailsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                               // callback_b2BBatchDetailsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback_b2BBatchDetailsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callback_b2BBatchDetailsInterface.notifyVolleyError( error);

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
            callback_b2BBatchDetailsInterface.notifyProcessingError(e);
        }


    }

    private void getDataFromBatchDetails() {


        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApitoCall,null,
                    response -> {
                        if (callback_b2BBatchDetailsInterface != null) {
                            try {
                                //converting jsonSTRING into array
                                JSONArray JArray = response.getJSONArray("content");


                                if (JArray.length() == 0) {
                                    callback_b2BBatchDetailsInterface.notifySuccess(Constants.emptyResult_volley);

                                }
                                else{


                                    int i1 = 0;
                                    int arrayLength = JArray.length();
                                    //Log.d("Constants.TAG", "convertingJsonStringintoArray Response: " + arrayLength);

                                    if(arrayLength>0) {
                                        for (; i1 < (arrayLength); i1++) {
                                            JSONObject json = JArray.getJSONObject(i1);
                                            Modal_B2BBatchDetails modal_b2BBatchDetails = new Modal_B2BBatchDetails();
                                            try {
                                                if (json.has("supplierkey")) {
                                                    Modal_B2BBatchDetailsStatic.supplierkey = String.valueOf(json.get("supplierkey"));
                                                    modal_b2BBatchDetails.supplierkey = String.valueOf(json.get("supplierkey"));

                                                } else {
                                                    Modal_B2BBatchDetailsStatic.supplierkey = "";
                                                    modal_b2BBatchDetails.supplierkey = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BBatchDetailsStatic.supplierkey = "";
                                                modal_b2BBatchDetails.supplierkey = "";
                                                e.printStackTrace();
                                            }
                                            try {
                                                if (json.has("batchno")) {
                                                    Modal_B2BBatchDetailsStatic.batchno = String.valueOf(json.get("batchno"));
                                                    modal_b2BBatchDetails.batchno = String.valueOf(json.get("batchno"));

                                                } else {
                                                    Modal_B2BBatchDetailsStatic.batchno = "";
                                                    modal_b2BBatchDetails.batchno = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BBatchDetailsStatic.batchno = "";
                                                modal_b2BBatchDetails.batchno = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("suppliername")) {
                                                    Modal_B2BBatchDetailsStatic.suppliername = String.valueOf(json.get("suppliername"));
                                                    modal_b2BBatchDetails.suppliername = String.valueOf(json.get("suppliername"));
                                                } else {
                                                    Modal_B2BBatchDetailsStatic.suppliername = "";
                                                    modal_b2BBatchDetails.suppliername = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BBatchDetailsStatic.suppliername = "";
                                                modal_b2BBatchDetails.suppliername = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("suppliermobileno")) {
                                                    Modal_B2BBatchDetailsStatic.suppliermobileno = String.valueOf(json.get("suppliermobileno"));
                                                    modal_b2BBatchDetails.suppliermobileno = String.valueOf(json.get("suppliermobileno"));
                                                } else {
                                                    Modal_B2BBatchDetailsStatic.suppliermobileno = "";
                                                    modal_b2BBatchDetails.suppliermobileno = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BBatchDetailsStatic.suppliermobileno = "";
                                                modal_b2BBatchDetails.suppliermobileno = "";
                                                e.printStackTrace();
                                            }
                                            try {
                                                if (json.has("deliverycentrename")) {
                                                    Modal_B2BBatchDetailsStatic.deliverycentername = String.valueOf(json.get("deliverycentrename"));
                                                    modal_b2BBatchDetails.deliverycentername = String.valueOf(json.get("deliverycentrename"));
                                                } else {
                                                    Modal_B2BBatchDetailsStatic.deliverycentername = "";
                                                    modal_b2BBatchDetails.deliverycentername = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BBatchDetailsStatic.deliverycentername = "";
                                                modal_b2BBatchDetails.deliverycentername = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("deliverycentrekey")) {
                                                    Modal_B2BBatchDetailsStatic.deliverycenterkey = String.valueOf(json.get("deliverycentrekey"));
                                                    modal_b2BBatchDetails.deliverycenterkey = String.valueOf(json.get("deliverycentrekey"));
                                                } else {
                                                    Modal_B2BBatchDetailsStatic.deliverycenterkey = "";
                                                    modal_b2BBatchDetails.deliverycenterkey = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BBatchDetailsStatic.deliverycenterkey = "";
                                                modal_b2BBatchDetails.deliverycenterkey = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("createddate")) {
                                                    Modal_B2BBatchDetailsStatic.createddate = DateParser.convertTime_to_DisplayingFormat(String.valueOf(json.get("createddate")));
                                                    modal_b2BBatchDetails.createddate = DateParser.convertTime_to_DisplayingFormat(String.valueOf(json.get("createddate")));
                                                    modal_b2BBatchDetails.createdDate_long = DateParser.getLongValuefortheDate(String.valueOf(json.get("createddate")));

                                                } else {
                                                    Modal_B2BBatchDetailsStatic.createddate = "";
                                                    modal_b2BBatchDetails.createddate = "";
                                                    modal_b2BBatchDetails.createdDate_long = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BBatchDetailsStatic.createddate = "";
                                                modal_b2BBatchDetails.createddate = "";
                                                modal_b2BBatchDetails.createdDate_long = "";
                                                e.printStackTrace();
                                            }
                                            try {
                                                if (json.has("status")) {
                                                  //  Modal_B2BBatchDetailsStatic.status = Constants.batchDetailsStatus_InTransit;
                                                    //modal_b2BBatchDetails.status = Constants.batchDetailsStatus_InTransit;
                                                    Modal_B2BBatchDetailsStatic.status = String.valueOf(json.get("status"));
                                                    modal_b2BBatchDetails.status = String.valueOf(json.get("status"));
                                                }
                                                else {
                                                    Modal_B2BBatchDetailsStatic.status = "";
                                                    modal_b2BBatchDetails.status = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BBatchDetailsStatic.status = "";
                                                modal_b2BBatchDetails.status = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("sentdate")) {
                                                    Modal_B2BBatchDetailsStatic.sentdate = DateParser.convertTime_to_DisplayingFormat(String.valueOf(json.get("sentdate")));
                                                    modal_b2BBatchDetails.sentdate = DateParser.convertTime_to_DisplayingFormat(String.valueOf(json.get("sentdate")));
                                                    modal_b2BBatchDetails.sentDate_Long = DateParser.getLongValuefortheDate(String.valueOf(json.get("sentdate")));

                                                } else {
                                                    Modal_B2BBatchDetailsStatic.sentdate = "";
                                                    modal_b2BBatchDetails.sentdate = "";
                                                    modal_b2BBatchDetails.sentDate_Long = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BBatchDetailsStatic.sentdate = "";
                                                modal_b2BBatchDetails.sentdate = "";
                                                modal_b2BBatchDetails.sentDate_Long = "";
                                                e.printStackTrace();
                                            }
                                            try {
                                                if (json.has("itemcount")) {
                                                    Modal_B2BBatchDetailsStatic.itemcount = (String.valueOf(json.get("itemcount")));
                                                    modal_b2BBatchDetails.itemcount = (String.valueOf(json.get("itemcount")));
                                                } else {
                                                    Modal_B2BBatchDetailsStatic.itemcount = "";
                                                    modal_b2BBatchDetails.itemcount = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BBatchDetailsStatic.itemcount = "";
                                                modal_b2BBatchDetails.itemcount = "";
                                                e.printStackTrace();
                                            }
                                            try {
                                                if (json.has("receiveddate")) {
                                                    Modal_B2BBatchDetailsStatic.receiveddate = DateParser.convertTime_to_DisplayingFormat(String.valueOf(json.get("receiveddate")));
                                                    modal_b2BBatchDetails.receiveddate = DateParser.convertTime_to_DisplayingFormat(String.valueOf(json.get("receiveddate")));
                                                    modal_b2BBatchDetails.receivedDate_long = DateParser.getLongValuefortheDate(String.valueOf(json.get("receiveddate")));

                                                } else {
                                                    Modal_B2BBatchDetailsStatic.receiveddate = "";
                                                    modal_b2BBatchDetails.receiveddate = "";
                                                    modal_b2BBatchDetails.receivedDate_long = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BBatchDetailsStatic.receiveddate = "";
                                                modal_b2BBatchDetails.receiveddate = "";
                                                modal_b2BBatchDetails.receivedDate_long = "";
                                                e.printStackTrace();
                                            }
                                            try {
                                                if (json.has("receivermobileno")) {
                                                    Modal_B2BBatchDetailsStatic.receivermobileno = String.valueOf(json.get("receivermobileno"));
                                                    modal_b2BBatchDetails.receivermobileno = String.valueOf(json.get("receivermobileno"));
                                                } else {
                                                    Modal_B2BBatchDetailsStatic.receivermobileno = "";
                                                    modal_b2BBatchDetails.receivermobileno = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BBatchDetailsStatic.receivermobileno = "";
                                                modal_b2BBatchDetails.receivermobileno = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("loadedweightingrams")) {

                                                    Modal_B2BBatchDetailsStatic.loadedweightingrams = ConvertGramsToKilograms(String.valueOf(json.get("loadedweightingrams")));
                                                    modal_b2BBatchDetails.loadedweightingrams = ConvertGramsToKilograms(String.valueOf(json.get("loadedweightingrams")));
                                                } else {
                                                    Modal_B2BBatchDetailsStatic.loadedweightingrams = "";
                                                    modal_b2BBatchDetails.loadedweightingrams = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BBatchDetailsStatic.loadedweightingrams = "";
                                                modal_b2BBatchDetails.loadedweightingrams = "";
                                                e.printStackTrace();
                                            }
                                            try {
                                                if (json.has("stockedweightingrams")) {
                                                    Modal_B2BBatchDetailsStatic.stockedweightingrams = ConvertGramsToKilograms(String.valueOf(json.get("stockedweightingrams")));
                                                    modal_b2BBatchDetails.stockedweightingrams = ConvertGramsToKilograms(String.valueOf(json.get("stockedweightingrams")));
                                                } else {
                                                    Modal_B2BBatchDetailsStatic.stockedweightingrams = "";
                                                    modal_b2BBatchDetails.stockedweightingrams = "";
                                                }
                                            } catch (Exception e) {
                                                Modal_B2BBatchDetailsStatic.stockedweightingrams = "";
                                                modal_b2BBatchDetails.stockedweightingrams = "";
                                                e.printStackTrace();
                                            }
                                            if (callMethod.equals(Constants.CallGETListMethod) || callMethod.equals(Constants.CallGETLastEntryMethod) ) {
                                                batchDetailsArrayList.add(modal_b2BBatchDetails);
                                            }


                                        }
                                        if (callMethod.equals(Constants.CallGETMethod)) {
                                            callback_b2BBatchDetailsInterface.notifySuccess("success");
                                        } else if (callMethod.equals(Constants.CallGETListMethod)) {
                                            callback_b2BBatchDetailsInterface.notifySuccessForGettingListItem(batchDetailsArrayList);

                                        }
                                        else if (callMethod.equals(Constants.CallGETLastEntryMethod)) {
                                            getLastEntryFromTheArray();
                                        }
                                        else {
                                            callback_b2BBatchDetailsInterface.notifySuccess("success");
                                        }
                                    }
                            }
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback_b2BBatchDetailsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callback_b2BBatchDetailsInterface.notifyVolleyError( error);

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
            callback_b2BBatchDetailsInterface.notifyProcessingError(e);
        }





    }



    private void addEntryInBatchDetails() {


        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,jsonToADD_Or_Update,
                    response -> {
                        if (callback_b2BBatchDetailsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callback_b2BBatchDetailsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){
                                    callback_b2BBatchDetailsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callback_b2BBatchDetailsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                              //  callback_b2BBatchDetailsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback_b2BBatchDetailsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callback_b2BBatchDetailsInterface.notifyVolleyError( error);

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
            callback_b2BBatchDetailsInterface.notifyProcessingError(e);
        }


    }

    private void getLastEntryFromTheArray() {

        Collections.sort(batchDetailsArrayList, new Comparator<Modal_B2BBatchDetails>() {
            public int compare(final Modal_B2BBatchDetails object1, final Modal_B2BBatchDetails object2) {
                String createdDate_long1 = object1.getCreatedDate_long();
                String createdDate_long2 = object2.getCreatedDate_long();

                if ((createdDate_long1.equals("")) || (createdDate_long1.equals("null")) || (createdDate_long1.equals(null))) {
                    createdDate_long1 = String.valueOf(0);
                }
                if ((createdDate_long2.equals("")) || (createdDate_long2.equals("null")) || (createdDate_long2.equals(null))) {
                    createdDate_long2 = String.valueOf(0);
                }

                Long i2 = Long.valueOf(createdDate_long2);
                Long i1 = Long.valueOf(createdDate_long1);

                return i2.compareTo(i1);
            }
        });

        try{
            Modal_B2BBatchDetails modal_b2BBatchDetails = batchDetailsArrayList.get(0);
            Modal_B2BBatchDetailsStatic . batchno = modal_b2BBatchDetails.getBatchno();
            Modal_B2BBatchDetailsStatic . sentdate = modal_b2BBatchDetails.getSentdate();
            Modal_B2BBatchDetailsStatic . createddate = modal_b2BBatchDetails.getCreateddate();
            Modal_B2BBatchDetailsStatic . receiveddate = modal_b2BBatchDetails.getReceiveddate();
            Modal_B2BBatchDetailsStatic . deliverycenterkey = modal_b2BBatchDetails.getDeliverycenterkey();
            Modal_B2BBatchDetailsStatic . deliverycentername = modal_b2BBatchDetails.getDeliverycentername();
            Modal_B2BBatchDetailsStatic . itemcount = modal_b2BBatchDetails.getItemcount();
            Modal_B2BBatchDetailsStatic . loadedweightingrams = modal_b2BBatchDetails.getLoadedweightingrams();
            Modal_B2BBatchDetailsStatic . receivermobileno = modal_b2BBatchDetails.getReceivermobileno();
            Modal_B2BBatchDetailsStatic . status = modal_b2BBatchDetails.getStatus();
            Modal_B2BBatchDetailsStatic . stockedweightingrams = modal_b2BBatchDetails.getStockedweightingrams();
            Modal_B2BBatchDetailsStatic . supplierkey = modal_b2BBatchDetails.getSupplierkey();
            Modal_B2BBatchDetailsStatic . suppliermobileno = modal_b2BBatchDetails.getSuppliermobileno();
            Modal_B2BBatchDetailsStatic . suppliername = modal_b2BBatchDetails.getSuppliername();


            callback_b2BBatchDetailsInterface.notifySuccess("success");

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }



    private JSONObject getJSON_to_Update_FromPOJOClass() {
        JSONObject jsonObject = new JSONObject();
        try {
            if(!Modal_B2BBatchDetailsUpdate.getBatchno().toString().equals("") && !Modal_B2BBatchDetailsUpdate.getBatchno().toString().equals("null")){
                jsonObject.put("batchno",Modal_B2BBatchDetailsUpdate.getBatchno());
            }
            if(!Modal_B2BBatchDetailsUpdate.getReceiveddate().toString().equals("") && !Modal_B2BBatchDetailsUpdate.getReceiveddate().toString().equals("null")){
                jsonObject.put("receiveddate",Modal_B2BBatchDetailsUpdate.getReceiveddate());
            }
            if(!Modal_B2BBatchDetailsUpdate.getReceivermobileno().toString().equals("") && !Modal_B2BBatchDetailsUpdate.getReceivermobileno().toString().equals("null")){
                jsonObject.put("receivermobileno",Modal_B2BBatchDetailsUpdate.getReceivermobileno());
            }
            if(!Modal_B2BBatchDetailsUpdate.getStatus().toString().equals("") && !Modal_B2BBatchDetailsUpdate.getStatus().toString().equals("null")){
                jsonObject.put("status",Modal_B2BBatchDetailsUpdate.getStatus());
            }
            if(!Modal_B2BBatchDetailsUpdate.getSupplierkey().toString().equals("") && !Modal_B2BBatchDetailsUpdate.getSupplierkey().toString().equals("null")){
                jsonObject.put("supplierkey",Modal_B2BBatchDetailsUpdate.getSupplierkey());
            }
            if(!Modal_B2BBatchDetailsUpdate.getSuppliername().toString().equals("") && !Modal_B2BBatchDetailsUpdate.getSuppliername().toString().equals("null")){
                jsonObject.put("suppliername",Modal_B2BBatchDetailsUpdate.getSuppliername());
            }
            if(!Modal_B2BBatchDetailsUpdate.getDeliverycentername().toString().equals("") && !Modal_B2BBatchDetailsUpdate.getDeliverycentername().toString().equals("null")){
                jsonObject.put("deliverycentrename",Modal_B2BBatchDetailsUpdate.getDeliverycentername());
            }
            if(!Modal_B2BBatchDetailsUpdate.getDeliverycenterkey().toString().equals("") && !Modal_B2BBatchDetailsUpdate.getDeliverycenterkey().toString().equals("null")){
                jsonObject.put("deliverycentrekey",Modal_B2BBatchDetailsUpdate.getDeliverycenterkey());
            }
            if(!Modal_B2BBatchDetailsUpdate.getSentdate().toString().equals("") && !Modal_B2BBatchDetailsUpdate.getSentdate().toString().equals("null")){
                jsonObject.put("sentdate",Modal_B2BBatchDetailsUpdate.getSentdate());
            }
            if(!Modal_B2BBatchDetailsUpdate.getCreateddate().toString().equals("") && !Modal_B2BBatchDetailsUpdate.getCreateddate().toString().equals("null")){
                jsonObject.put("createddate",Modal_B2BBatchDetailsUpdate.getCreateddate());
            }
            if(!Modal_B2BBatchDetailsUpdate.getSuppliermobileno().toString().equals("") && !Modal_B2BBatchDetailsUpdate.getSuppliermobileno().toString().equals("null")){
                jsonObject.put("suppliermobileno",Modal_B2BBatchDetailsUpdate.getSuppliermobileno());
            }
            if(!Modal_B2BBatchDetailsUpdate.getItemcount().toString().equals("") && !Modal_B2BBatchDetailsUpdate.getItemcount().toString().equals("null")){
                String qty_str = Modal_B2BBatchDetailsUpdate.getItemcount();
                qty_str = qty_str.replaceAll("[^\\d.]", "");
                if(qty_str.equals("") || qty_str.equals(null)){
                    qty_str = "0";
                }

                int qty_int = Integer.parseInt(qty_str);
                jsonObject.put("itemcount",qty_int);
            }
            if(!Modal_B2BBatchDetailsUpdate.getLoadedweightingrams().toString().equals("") && !Modal_B2BBatchDetailsUpdate.getLoadedweightingrams().toString().equals("null")){
                String weightinGrams_str = Modal_B2BBatchDetailsUpdate.getLoadedweightingrams();
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                    weightinGrams_str = "0";
                }


                weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                jsonObject.put("loadedweightingrams",weightinGrams_double);
            }
            if(!Modal_B2BBatchDetailsUpdate.getStockedweightingrams().toString().equals("") && !Modal_B2BBatchDetailsUpdate.getStockedweightingrams().toString().equals("null")){
                String weightinGrams_str = Modal_B2BBatchDetailsUpdate.getStockedweightingrams();
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                    weightinGrams_str = "0";
                }

                weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                jsonObject.put("stockedweightingrams",weightinGrams_double);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;


    }

    private JSONObject getJSONforPOJOClass() {
        JSONObject jsonObject = new JSONObject();
        try {
            if(!Modal_B2BBatchDetailsStatic.getBatchno().toString().equals("") && !Modal_B2BBatchDetailsStatic.getBatchno().toString().equals("null")){
                jsonObject.put("batchno", Modal_B2BBatchDetailsStatic.getBatchno());
            }
            if(!Modal_B2BBatchDetailsStatic.getReceiveddate().toString().equals("") && !Modal_B2BBatchDetailsStatic.getReceiveddate().toString().equals("null")){
                jsonObject.put("receiveddate", Modal_B2BBatchDetailsStatic.getReceiveddate());
            }
            if(!Modal_B2BBatchDetailsStatic.getReceivermobileno().toString().equals("") && !Modal_B2BBatchDetailsStatic.getReceivermobileno().toString().equals("null")){
                jsonObject.put("receivermobileno", Modal_B2BBatchDetailsStatic.getReceivermobileno());
            }
            if(!Modal_B2BBatchDetailsStatic.getStatus().toString().equals("") && !Modal_B2BBatchDetailsStatic.getStatus().toString().equals("null")){
                jsonObject.put("status", Modal_B2BBatchDetailsStatic.getStatus());
            }
            if(!Modal_B2BBatchDetailsStatic.getSupplierkey().toString().equals("") && !Modal_B2BBatchDetailsStatic.getSupplierkey().toString().equals("null")){
                jsonObject.put("supplierkey", Modal_B2BBatchDetailsStatic.getSupplierkey());
            }
            if(!Modal_B2BBatchDetailsStatic.getSuppliername().toString().equals("") && !Modal_B2BBatchDetailsStatic.getSuppliername().toString().equals("null")){
                jsonObject.put("suppliername", Modal_B2BBatchDetailsStatic.getSuppliername());
            }
            if(!Modal_B2BBatchDetailsStatic.getDeliverycentername().toString().equals("") && !Modal_B2BBatchDetailsStatic.getDeliverycentername().toString().equals("null")){
                jsonObject.put("deliverycentrename", Modal_B2BBatchDetailsStatic.getDeliverycentername());
            }
            if(!Modal_B2BBatchDetailsStatic.getDeliverycenterkey().toString().equals("") && !Modal_B2BBatchDetailsStatic.getDeliverycenterkey().toString().equals("null")){
                jsonObject.put("deliverycentrekey", Modal_B2BBatchDetailsStatic.getDeliverycenterkey());
            }
            if(!Modal_B2BBatchDetailsStatic.getSentdate().toString().equals("") && !Modal_B2BBatchDetailsStatic.getSentdate().toString().equals("null")){
                jsonObject.put("sentdate", Modal_B2BBatchDetailsStatic.getSentdate());
            }
            if(!Modal_B2BBatchDetailsStatic.getCreateddate().toString().equals("") && !Modal_B2BBatchDetailsStatic.getCreateddate().toString().equals("null")){
                jsonObject.put("createddate", Modal_B2BBatchDetailsStatic.getCreateddate());
            }
            if(!Modal_B2BBatchDetailsStatic.getSuppliermobileno().toString().equals("") && !Modal_B2BBatchDetailsStatic.getSuppliermobileno().toString().equals("null")){
                jsonObject.put("suppliermobileno", Modal_B2BBatchDetailsStatic.getSuppliermobileno());
            }
            if(!Modal_B2BBatchDetailsStatic.getItemcount().toString().equals("") && !Modal_B2BBatchDetailsStatic.getItemcount().toString().equals("null")){
                String qty_str = Modal_B2BBatchDetailsStatic.getItemcount();
                qty_str = qty_str.replaceAll("[^\\d.]", "");

                if(qty_str.equals("") || qty_str.equals(null)){
                    qty_str = "0";
                }
                int qty_int = Integer.parseInt(qty_str);
                jsonObject.put("itemcount",qty_int);
            }
            if(!Modal_B2BBatchDetailsStatic.getLoadedweightingrams().toString().equals("") && !Modal_B2BBatchDetailsStatic.getLoadedweightingrams().toString().equals("null")){
                String weightinGrams_str = Modal_B2BBatchDetailsStatic.getLoadedweightingrams();
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                    weightinGrams_str = "0";
                }


                weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                jsonObject.put("loadedweightingrams",weightinGrams_double);
            }
            if(!Modal_B2BBatchDetailsStatic.getStockedweightingrams().toString().equals("") && !Modal_B2BBatchDetailsStatic.getStockedweightingrams().toString().equals("null")){
                String weightinGrams_str = Modal_B2BBatchDetailsStatic.getStockedweightingrams();
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");

                if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                    weightinGrams_str = "0";
                }
                weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                jsonObject.put("stockedweightingrams",weightinGrams_double);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void Update_Local_POJOClass() {
        try {

            if(jsonToADD_Or_Update.has("status")){
                if(!jsonToADD_Or_Update.getString("status").equals("")){
                    Modal_B2BBatchDetailsStatic.status =  jsonToADD_Or_Update.getString("status");
                }
            }
            if(jsonToADD_Or_Update.has("stockedweightingrams")){
                if(!jsonToADD_Or_Update.getString("stockedweightingrams").equals("")){
                    Modal_B2BBatchDetailsStatic.stockedweightingrams =  jsonToADD_Or_Update.getString("stockedweightingrams");
                }
            }
            if(jsonToADD_Or_Update.has("sentdate")){
                if(!jsonToADD_Or_Update.getString("sentdate").equals("")){
                    Modal_B2BBatchDetailsStatic.sentdate =  jsonToADD_Or_Update.getString("sentdate");
                }
            }
            if(jsonToADD_Or_Update.has("loadedweightingrams")){
                if(!jsonToADD_Or_Update.getString("loadedweightingrams").equals("")){
                    Modal_B2BBatchDetailsStatic.loadedweightingrams =  jsonToADD_Or_Update.getString("loadedweightingrams");
                }
            }
            if(jsonToADD_Or_Update.has("itemcount")){
                if(!jsonToADD_Or_Update.getString("itemcount").equals("")){
                    Modal_B2BBatchDetailsStatic.itemcount =  jsonToADD_Or_Update.getString("itemcount");
                }
            }


            callback_b2BBatchDetailsInterface.notifySuccess(Constants.successResult_volley);

        } catch (JSONException e) {
            callback_b2BBatchDetailsInterface.notifyProcessingError(e);

            e.printStackTrace();
        }


    }




    private String ConvertGramsToKilograms(String grossWeightingramsString) {
        String weightinKGString = "";

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
            e.printStackTrace();
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
