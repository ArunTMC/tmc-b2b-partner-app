package com.tmc.tmcb2bpartnerapp.apiRequestServices;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BCartDetaillsInterface;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.volleyrequestqueuehelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class B2BCartItemDetails extends AsyncTask<String, String, ArrayList<Modal_B2BCartItemDetails>> {
    B2BCartDetaillsInterface callbackB2BCartDetaillsInterface;
    String ApitoCall ="" , callMethod ="";
    JSONObject jsonToADD_Or_Update;
    Modal_B2BCartItemDetails modal_b2BCartDetails;
    ArrayList<Modal_B2BCartItemDetails> arrayList  = new ArrayList<>();

    public B2BCartItemDetails(B2BCartDetaillsInterface callbackB2BCartDetaillsInterfacee, String getApiToCall, String apiMethodtoCall, Modal_B2BCartItemDetails modal_b2BCartDetailss) {
        this.callbackB2BCartDetaillsInterface = callbackB2BCartDetaillsInterfacee;
        this.ApitoCall = getApiToCall;
        this.callMethod = apiMethodtoCall;
        this.modal_b2BCartDetails = modal_b2BCartDetailss;
        if(callMethod.equals(Constants.CallADDMethod) ){
            jsonToADD_Or_Update = getJSONforPOJOClass();

        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){
            jsonToADD_Or_Update = getJSONforPOJOClass();

        }

    }

    public B2BCartItemDetails(B2BCartDetaillsInterface callback_b2BCartDetaillsInterfacee, String getApiToCall, String apiMethodtoCall) {

        this.callbackB2BCartDetaillsInterface = callback_b2BCartDetaillsInterfacee;
        this.ApitoCall = getApiToCall;
        this.callMethod = apiMethodtoCall;
        if(callMethod.equals(Constants.CallGETMethod)){
            new Modal_B2BCartItemDetails();
        }
        else if(callMethod.equals(Constants.CallGETListMethod) || callMethod.equals(Constants.CallGETLastEntryMethod)){
            new Modal_B2BCartItemDetails();
        }
        else  if(callMethod.equals(Constants.CallDELETEMethod)){

        }

    }




    @Override
    protected ArrayList<Modal_B2BCartItemDetails> doInBackground(String... strings) {

        if(callMethod.equals(Constants.CallADDMethod)){
            addEntryInBatchDetails();
        }
        else if(callMethod.equals(Constants.CallGETMethod) || callMethod.equals(Constants.CallGETListMethod) || callMethod.equals(Constants.CallGETLastEntryMethod)){
            getDataFromBatchDetails();
        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){
            updateEntryInBatchDetails();
        }
        else  if(callMethod.equals(Constants.CallDELETEMethod)){
            deleteEntryInBatchDetails();
        }






        return null;
    }

    private void deleteEntryInBatchDetails() {
        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,jsonToADD_Or_Update,
                    response -> {
                        if (callbackB2BCartDetaillsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callbackB2BCartDetaillsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){



                                    callbackB2BCartDetaillsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callbackB2BCartDetaillsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callbackB2BCartDetaillsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callbackB2BCartDetaillsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callbackB2BCartDetaillsInterface.notifyVolleyError( error);

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
            callbackB2BCartDetaillsInterface.notifyProcessingError(e);
        }
    }

    private void addEntryInBatchDetails() {


        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,jsonToADD_Or_Update,
                    response -> {
                        if (callbackB2BCartDetaillsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callbackB2BCartDetaillsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){



                                    callbackB2BCartDetaillsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callbackB2BCartDetaillsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callbackB2BCartDetaillsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callbackB2BCartDetaillsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callbackB2BCartDetaillsInterface.notifyVolleyError( error);

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
            callbackB2BCartDetaillsInterface.notifyProcessingError(e);
        }
        
        
        
    }


    private void getDataFromBatchDetails() {



        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApitoCall,null,
                    response -> {
                        if (callbackB2BCartDetaillsInterface != null) {
                            try {
                                JSONArray JArray = response.getJSONArray("content");


                                if (JArray.length() == 0) {
                                    callbackB2BCartDetaillsInterface.notifySuccess(Constants.emptyResult_volley);

                                }
                                else {


                                    int i1 = 0;
                                    int arrayLength = JArray.length();

                                    if (arrayLength > 0) {
                                        for (; i1 < (arrayLength); i1++) {
                                            JSONObject json = JArray.getJSONObject(i1);
                                            Modal_B2BCartItemDetails modal_b2BCartDetails = new Modal_B2BCartItemDetails();
                                            try {
                                                if (json.has("weightingrams")) {
                                                    modal_b2BCartDetails.weightingrams = ConvertGramsToKilograms( String.valueOf(json.get("weightingrams")));

                                                } else {
                                                    modal_b2BCartDetails.weightingrams = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BCartDetails.weightingrams = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("gradename")) {
                                               //     modal_b2BCartDetails.gradename = ( String.valueOf(json.get("gradename")));
                                                    modal_b2BCartDetails.gradename = ConvertFirstLetter_to_Capital( String.valueOf(json.get("gradename")));

                                                } else {
                                                    modal_b2BCartDetails.gradename = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BCartDetails.gradename = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("gradeprice")) {
                                                    modal_b2BCartDetails.gradeprice = ( String.valueOf(json.get("gradeprice")));

                                                } else {
                                                    modal_b2BCartDetails.gradeprice = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BCartDetails.gradeprice = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("gradekey")) {
                                                    modal_b2BCartDetails.gradekey = ( String.valueOf(json.get("gradekey")));

                                                } else {
                                                    modal_b2BCartDetails.gradekey = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BCartDetails.gradekey = "";
                                                e.printStackTrace();
                                            }
                                            try {
                                                if (json.has("supplierkey")) {
                                                    modal_b2BCartDetails.supplierkey = ( String.valueOf(json.get("supplierkey")));

                                                } else {
                                                    modal_b2BCartDetails.supplierkey = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BCartDetails.supplierkey = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("suppliername")) {
                                                    modal_b2BCartDetails.suppliername = ( String.valueOf(json.get("suppliername")));

                                                } else {
                                                    modal_b2BCartDetails.suppliername = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BCartDetails.suppliername = "";
                                                e.printStackTrace();
                                            }




                                            try {
                                                if (json.has("batchno")) {
                                                    modal_b2BCartDetails.batchno = String.valueOf(json.get("batchno"));

                                                } else {
                                                    modal_b2BCartDetails.batchno = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BCartDetails.batchno = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("barcodeno")) {
                                                    modal_b2BCartDetails.barcodeno = String.valueOf(json.get("barcodeno"));

                                                } else {
                                                    modal_b2BCartDetails.barcodeno = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BCartDetails.barcodeno = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("breedtype")) {
                                                    modal_b2BCartDetails.breedtype = String.valueOf(json.get("breedtype"));

                                                } else {
                                                    modal_b2BCartDetails.breedtype = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BCartDetails.breedtype = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("gender")) {
                                                    if(String.valueOf(json.get("gender")).toUpperCase().contains("BABY")){
                                                        modal_b2BCartDetails.gender = "FEMALE";
                                                    }
                                                    else{
                                                        modal_b2BCartDetails.gender = String.valueOf(json.get("gender"));
                                                    }


                                                } else {
                                                    modal_b2BCartDetails.gender = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BCartDetails.gender = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("status")) {
                                                    modal_b2BCartDetails.status = String.valueOf(json.get("status"));

                                                } else {
                                                    modal_b2BCartDetails.status = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BCartDetails.status = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("orderid")) {
                                                    modal_b2BCartDetails.orderid = String.valueOf(json.get("orderid"));

                                                } else {
                                                    modal_b2BCartDetails.orderid = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BCartDetails.orderid = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("itemaddeddate")) {
                                                    modal_b2BCartDetails.itemaddeddate = String.valueOf(json.get("itemaddeddate"));

                                                } else {
                                                    modal_b2BCartDetails.itemaddeddate = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BCartDetails.itemaddeddate = "";
                                                e.printStackTrace();
                                            }



                                            try {
                                                if (json.has("b2bsubctgykey")) {
                                                    modal_b2BCartDetails.b2bsubctgykey = String.valueOf(json.get("b2bsubctgykey"));

                                                } else {
                                                    modal_b2BCartDetails.b2bsubctgykey = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BCartDetails.b2bsubctgykey = "";
                                                e.printStackTrace();
                                            }

                                            try {
                                                if (json.has("b2bctgykey")) {
                                                    modal_b2BCartDetails.b2bctgykey = String.valueOf(json.get("b2bctgykey"));

                                                } else {
                                                    modal_b2BCartDetails.b2bctgykey = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BCartDetails.b2bctgykey = "";
                                                e.printStackTrace();
                                            }


                                            try {
                                                if (json.has("oldweightingrams")) {
                                                    modal_b2BCartDetails.oldweightingrams = ConvertGramsToKilograms(String.valueOf(json.get("oldweightingrams")));

                                                } else {
                                                    modal_b2BCartDetails.oldweightingrams = "";
                                                }
                                            } catch (Exception e) {
                                                modal_b2BCartDetails.oldweightingrams = "";
                                                e.printStackTrace();
                                            }



                                            arrayList.add(modal_b2BCartDetails);
                                            if(arrayLength -1 == i1)
                                            {

                                                if(callMethod.equals(Constants.CallGETMethod)){
                                                    callbackB2BCartDetaillsInterface.notifySuccess(Constants.successResult_volley);

                                                }
                                                else  if(callMethod.equals(Constants.CallGETListMethod)){
                                                    callbackB2BCartDetaillsInterface.notifySuccessForGettingListItem(arrayList);

                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                callbackB2BCartDetaillsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callbackB2BCartDetaillsInterface.notifyVolleyError( error);

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
            callbackB2BCartDetaillsInterface.notifyProcessingError(e);
        }



    }

    private void updateEntryInBatchDetails() {

        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,jsonToADD_Or_Update,
                    response -> {
                        if (callbackB2BCartDetaillsInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callbackB2BCartDetaillsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){



                                    callbackB2BCartDetaillsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callbackB2BCartDetaillsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callbackB2BCartDetaillsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callbackB2BCartDetaillsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callbackB2BCartDetaillsInterface.notifyVolleyError( error);

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
            callbackB2BCartDetaillsInterface.notifyProcessingError(e);
        }




    }


    private JSONObject getJSONforPOJOClass() {
        JSONObject jsonObject = new JSONObject();
        try{
            if(!modal_b2BCartDetails.getBatchno().toString().equals("") && !modal_b2BCartDetails.getBatchno().toString().equals("null")){
                jsonObject.put("batchno",String.valueOf(modal_b2BCartDetails.getBatchno()));
            }
            if(!modal_b2BCartDetails.getBarcodeno().toString().equals("") && !modal_b2BCartDetails.getBarcodeno().toString().equals("null")){
                jsonObject.put("barcodeno",String.valueOf(modal_b2BCartDetails.getBarcodeno()));
            }
            if(!modal_b2BCartDetails.getWeightingrams().toString().equals("") && !modal_b2BCartDetails.getWeightingrams().toString().equals("null")){
                String weightinGrams_str = modal_b2BCartDetails.getWeightingrams();
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                    weightinGrams_str = "0";
                }

                weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                jsonObject.put("weightingrams",weightinGrams_double);
            }
            if(!modal_b2BCartDetails.getOldweightingrams().toString().equals("") && !modal_b2BCartDetails.getOldweightingrams().toString().equals("null")){
                String weightinGrams_str = modal_b2BCartDetails.getOldweightingrams();
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                    weightinGrams_str = "0";
                }


                weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                jsonObject.put("oldweightingrams",weightinGrams_double);
            }
            if(!modal_b2BCartDetails.getStatus().toString().equals("") && !modal_b2BCartDetails.getStatus().toString().equals("null")){
                jsonObject.put("status",String.valueOf(modal_b2BCartDetails.getStatus()));
            }
            if(!modal_b2BCartDetails.getItemaddeddate().toString().equals("") && !modal_b2BCartDetails.getItemaddeddate().toString().equals("null")){
                jsonObject.put("itemaddeddate",String.valueOf(modal_b2BCartDetails.getItemaddeddate()));
            }

            if(!modal_b2BCartDetails.getGender().toString().equals("") && !modal_b2BCartDetails.getGender().toString().equals("null")){
                jsonObject.put("gender",String.valueOf(modal_b2BCartDetails.getGender()));
            }
            if(!modal_b2BCartDetails.getBreedtype().toString().equals("") && !modal_b2BCartDetails.getBreedtype().toString().equals("null")){
                jsonObject.put("breedtype",String.valueOf(modal_b2BCartDetails.getBreedtype()));
            }

            if(!modal_b2BCartDetails.getOrderid().toString().equals("") && !modal_b2BCartDetails.getOrderid().toString().equals("null")){
                jsonObject.put("orderid",String.valueOf(modal_b2BCartDetails.getOrderid()));
            }

            if(!modal_b2BCartDetails.getB2bctgykey().toString().equals("") && !modal_b2BCartDetails.getB2bctgykey().toString().equals("null")){
                jsonObject.put("b2bctgykey",String.valueOf(modal_b2BCartDetails.getB2bctgykey()));
            }


            if(!modal_b2BCartDetails.getB2bsubctgykey().toString().equals("") && !modal_b2BCartDetails.getB2bsubctgykey().toString().equals("null")){
                jsonObject.put("b2bsubctgykey",String.valueOf(modal_b2BCartDetails.getB2bsubctgykey()));
            }

            if(!modal_b2BCartDetails.getGradename().toString().equals("") && !modal_b2BCartDetails.getGradename().toString().equals("null")){
                jsonObject.put("gradename",String.valueOf(modal_b2BCartDetails.getGradename()));
            }

            if(!modal_b2BCartDetails.getGradeprice().toString().equals("") && !modal_b2BCartDetails.getGradeprice().toString().equals("null")){
                jsonObject.put("gradeprice",String.valueOf(modal_b2BCartDetails.getGradeprice()));
            }
            if(!modal_b2BCartDetails.getGradekey().toString().equals("") && !modal_b2BCartDetails.getGradekey().toString().equals("null")){
                jsonObject.put("gradekey",String.valueOf(modal_b2BCartDetails.getGradekey()));
            }


            if(!modal_b2BCartDetails.getSupplierkey().toString().equals("") && !modal_b2BCartDetails.getSupplierkey().toString().equals("null")){
                jsonObject.put("supplierkey",String.valueOf(modal_b2BCartDetails.getSupplierkey()));
            }
            if(!modal_b2BCartDetails.getSuppliername().toString().equals("") && !modal_b2BCartDetails.getSuppliername().toString().equals("null")){
                jsonObject.put("suppliername",String.valueOf(modal_b2BCartDetails.getSuppliername()));
            }



        /*    if(!modal_b2BCartDetails.getRetailerkey().toString().equals("") && !modal_b2BCartDetails.getRetailerkey().toString().equals("null")){
                jsonObject.put("retailerkey",String.valueOf(modal_b2BCartDetails.getRetailerkey()));
            }

            if(!modal_b2BCartDetails.getRetailername().toString().equals("") && !modal_b2BCartDetails.getRetailername().toString().equals("null")){
                jsonObject.put("retailername",String.valueOf(modal_b2BCartDetails.getRetailername()));
            }


            if(!modal_b2BCartDetails.getDeliverycenterkey ().toString().equals("") && !modal_b2BCartDetails.getDeliverycenterkey().toString().equals("null")){
                jsonObject.put("deliverycentrekey",String.valueOf(modal_b2BCartDetails.getDeliverycenterkey()));
            }

            if(!modal_b2BCartDetails.getDeliverycentername().toString().equals("") && !modal_b2BCartDetails.getDeliverycentername().toString().equals("null")){
                jsonObject.put("deliverycentrename",String.valueOf(modal_b2BCartDetails.getDeliverycentername()));
            }


            if(!modal_b2BCartDetails.getRetailermobileno().toString().equals("") && !modal_b2BCartDetails.getRetailermobileno().toString().equals("null")){
                jsonObject.put("retailermobileno",String.valueOf(modal_b2BCartDetails.getRetailermobileno()));
            }

            if(!modal_b2BCartDetails.getSupplierkey().toString().equals("") && !modal_b2BCartDetails.getSupplierkey().toString().equals("null")){
                jsonObject.put("supplierkey",String.valueOf(modal_b2BCartDetails.getSupplierkey()));
            }

            if(!modal_b2BCartDetails.getSuppliername().toString().equals("") && !modal_b2BCartDetails.getSuppliername().toString().equals("null")){
                jsonObject.put("suppliername",String.valueOf(modal_b2BCartDetails.getSuppliername()));
            }

            if(!modal_b2BCartDetails.getPriceperkg().toString().equals("") && !modal_b2BCartDetails.getPriceperkg().toString().equals("null")){
                jsonObject.put("priceperkg",String.valueOf(modal_b2BCartDetails.getPriceperkg()));
            }

         */

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



}
