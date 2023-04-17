package com.tmc.tmcb2bpartnerapp.apiRequestServices;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.interfaces.SupplierDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_SupplierDetails;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
import com.tmc.tmcb2bpartnerapp.utils.volleyrequestqueuehelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierDetails extends AsyncTask<String, String, List<Modal_SupplierDetails>> {


    SupplierDetailsInterface callback_supplierDetailsInterface;
    String supplierDetailsApi ="", callMethod ="";
    boolean isGetApiRequestStarted = false;
    ArrayList<Modal_SupplierDetails> supplierDetailsList_local = new ArrayList<>();



    public SupplierDetails(SupplierDetailsInterface callback_supplierDetailsInterfacee, String supplierDetailsApi, String callMethod) {
        this.callback_supplierDetailsInterface = callback_supplierDetailsInterfacee;
        this.supplierDetailsApi = supplierDetailsApi;
        this.callMethod  = callMethod;
    }

    @Override
    protected List<Modal_SupplierDetails> doInBackground(String... strings) {

        if(callMethod.equals(Constants.CallADDMethod)){
            addEntryInBatchDetails();
        }
        else if(callMethod.equals(Constants.CallGETMethod) || callMethod.equals(Constants.CallGETListMethod)){
            if(!isGetApiRequestStarted) {
                isGetApiRequestStarted = true;
                new Modal_SupplierDetails();
                supplierDetailsList_local.clear();
                DatabaseArrayList_PojoClass.supplierDetailsArrayList.clear();
                getDataFromBatchDetails();
            }
        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){
            updateEntryInBatchDetails();
        }


        return null;
    }

    private void updateEntryInBatchDetails() {
    }

    private void getDataFromBatchDetails() {

        try{
            if(callMethod.equals(Constants.CallGETListMethod)){
            DatabaseArrayList_PojoClass.supplierDetailsArrayList.clear();
                supplierDetailsList_local.clear();
        }


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, supplierDetailsApi,null,
                    response -> {
                        if (callback_supplierDetailsInterface != null) {
                            try {
                                //converting jsonSTRING into array
                                JSONArray JArray = response.getJSONArray("content");
                                //Log.d(Constants.TAG, "convertingJsonStringintoArray Response: " + JArray);
                                int i1 = 0;
                                int arrayLength = JArray.length();
                                //Log.d("Constants.TAG", "convertingJsonStringintoArray Response: " + arrayLength);
                                if(arrayLength == 0){
                                    callback_supplierDetailsInterface.notifySuccess(Constants.emptyResult_volley);

                                }
                                if(arrayLength>0) {
                                    for (; i1 < (arrayLength); i1++) {
                                        JSONObject json = JArray.getJSONObject(i1);
                                        Modal_SupplierDetails modal_supplierDetails = new Modal_SupplierDetails();

                                        try {
                                            if (json.has("supplierkey")) {
                                                Modal_SupplierDetails.supplierkey_static = String.valueOf(json.get("supplierkey"));
                                                modal_supplierDetails.supplierkey = String.valueOf(json.get("supplierkey"));

                                            } else {
                                                Modal_SupplierDetails.supplierkey_static = "";
                                                modal_supplierDetails.supplierkey = "";
                                            }
                                        } catch (Exception e) {
                                            Modal_SupplierDetails.supplierkey_static = "";
                                            modal_supplierDetails.supplierkey  = "";
                                            e.printStackTrace();
                                        }
                                        try {
                                            if (json.has("aadhaarcardno")) {
                                                Modal_SupplierDetails.aadhaarcardno_static = String.valueOf(json.get("aadhaarcardno"));
                                                modal_supplierDetails.aadhaarcardno = String.valueOf(json.get("aadhaarcardno"));

                                            } else {
                                                Modal_SupplierDetails.aadhaarcardno_static = "";
                                                modal_supplierDetails.aadhaarcardno = "";
                                            }
                                        } catch (Exception e) {
                                            Modal_SupplierDetails.aadhaarcardno_static = "";
                                            modal_supplierDetails.aadhaarcardno  = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("gstno")) {
                                                Modal_SupplierDetails.gstno_static = String.valueOf(json.get("gstno"));
                                                modal_supplierDetails.gstno = String.valueOf(json.get("gstno"));

                                            } else {
                                                Modal_SupplierDetails.gstno_static = "";
                                                modal_supplierDetails.gstno = "";
                                            }
                                        } catch (Exception e) {
                                            Modal_SupplierDetails.gstno_static = "";
                                            modal_supplierDetails.gstno = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("name")) {
                                                Modal_SupplierDetails.name_static = String.valueOf(json.get("name"));
                                                modal_supplierDetails.name = String.valueOf(json.get("name"));
                                            } else {
                                                Modal_SupplierDetails.name_static = "";
                                                modal_supplierDetails.name = "";
                                            }
                                        } catch (Exception e) {
                                            Modal_SupplierDetails.name_static = "";
                                            modal_supplierDetails.name  = "";
                                            e.printStackTrace();
                                        }

                                        try {
                                            if (json.has("password")) {
                                                Modal_SupplierDetails.password_static = String.valueOf(json.get("password"));
                                                modal_supplierDetails.password = String.valueOf(json.get("password"));
                                            } else {
                                                Modal_SupplierDetails.password_static = "";
                                                modal_supplierDetails.password = "";
                                            }
                                        } catch (Exception e) {
                                            Modal_SupplierDetails.password_static = "";
                                            modal_supplierDetails.password = "";
                                            e.printStackTrace();
                                        }

                                        try {
                                            if (json.has("primarymobileno")) {
                                                Modal_SupplierDetails.primarymobileno_static = String.valueOf(json.get("primarymobileno"));
                                                modal_supplierDetails.primarymobileno = String.valueOf(json.get("primarymobileno"));
                                            } else {
                                                Modal_SupplierDetails.primarymobileno_static = "";
                                                modal_supplierDetails.primarymobileno = "";
                                            }
                                        } catch (Exception e) {
                                            Modal_SupplierDetails.primarymobileno_static = "";
                                            modal_supplierDetails.primarymobileno  = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("name")) {
                                                Modal_SupplierDetails.suppliername_static = String.valueOf(json.get("name"));
                                                modal_supplierDetails.suppliername = String.valueOf(json.get("name"));
                                            } else {
                                                Modal_SupplierDetails.suppliername_static = "";
                                                modal_supplierDetails.suppliername = "";
                                            }
                                        } catch (Exception e) {
                                            Modal_SupplierDetails.suppliername_static = "";
                                            modal_supplierDetails.suppliername= "";
                                            e.printStackTrace();
                                        }

                                        try {
                                            if (json.has("secondarymobileno")) {
                                                Modal_SupplierDetails.secondarymobileno_static = String.valueOf(json.get("secondarymobileno"));
                                                modal_supplierDetails.secondarymobileno = String.valueOf(json.get("secondarymobileno"));

                                            } else {
                                                Modal_SupplierDetails.secondarymobileno_static = "";
                                                modal_supplierDetails.secondarymobileno = "";
                                            }
                                        } catch (Exception e) {
                                            Modal_SupplierDetails.secondarymobileno_static = "";
                                            modal_supplierDetails.secondarymobileno = "";
                                            e.printStackTrace();
                                        }

                                    if(callMethod.equals(Constants.CallGETListMethod)){
                                        supplierDetailsList_local.add(modal_supplierDetails);
                                    }


                                    }
                                }
                                if(callMethod.equals(Constants.CallGETListMethod)) {
                                    isGetApiRequestStarted = false;
                                    DatabaseArrayList_PojoClass.setSupplierDetailsArrayList(supplierDetailsList_local);
                                    callback_supplierDetailsInterface.notifySuccessForGettingListItem(DatabaseArrayList_PojoClass.supplierDetailsArrayList);

                                }
                                else{isGetApiRequestStarted = false;
                                    callback_supplierDetailsInterface.notifySuccess("success" + Modal_SupplierDetails.name_static);

                                }
                            } catch (Exception e) {
                                isGetApiRequestStarted = false;
                                callback_supplierDetailsInterface.notifyProcessingError(e);

                                e.printStackTrace();
                            }
                        }

                    }, error -> {

                //Log.d(Constants.TAG, "Error: " + error.getLocalizedMessage());
                //Log.d(Constants.TAG, "Error: " + error.getMessage());
                //Log.d(Constants.TAG, "Error: " + error.toString());
                isGetApiRequestStarted = false;
                callback_supplierDetailsInterface.notifyVolleyError( error);

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
            isGetApiRequestStarted = false;
            e.printStackTrace();
            callback_supplierDetailsInterface.notifyVolleyError((VolleyError) e);
        }



    }

    private void addEntryInBatchDetails() {
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


    @Override
    protected void onPostExecute( List<Modal_SupplierDetails> modal ) {
        super.onPostExecute(modal);

    }
    
    
    
}
