package com.tmc.tmcb2bpartnerapp.apiRequestServices;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.interfaces.DeliveryCenterDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_DeliveryCenterDetails;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
import com.tmc.tmcb2bpartnerapp.utils.volleyrequestqueuehelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryCenterDetails extends AsyncTask<String, String, List<Modal_DeliveryCenterDetails>> {


    DeliveryCenterDetailsInterface callback_deliveryCenterDetailsInterface;
    String getApiToCall , apiMethodToCall;


    public DeliveryCenterDetails(DeliveryCenterDetailsInterface callback_deliveryCenterInterfacee, String getApiToCall,String apiMethodtoCall) {
        this.callback_deliveryCenterDetailsInterface = callback_deliveryCenterInterfacee;
        this.getApiToCall = getApiToCall;
        this.apiMethodToCall = apiMethodtoCall;
    }

    @Override
    protected List<Modal_DeliveryCenterDetails> doInBackground(String... strings) {

           List<Modal_DeliveryCenterDetails>deliveryCenterDetailsList = new ArrayList<>();
           DatabaseArrayList_PojoClass.deliveryCenterDetailsList.clear();
          new Modal_DeliveryCenterDetails();
        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getApiToCall,null,
                    response -> {
                        if (callback_deliveryCenterDetailsInterface != null) {
                            try {
                                //converting jsonSTRING into array
                                JSONArray JArray = response.getJSONArray("content");
                                //Log.d(Constants.TAG, "convertingJsonStringintoArray Response: " + JArray);
                                int i1 = 0;
                                int arrayLength = JArray.length();
                                //Log.d("Constants.TAG", "convertingJsonStringintoArray Response: " + arrayLength);
                                if (JArray.length() == 0) {
                                    callback_deliveryCenterDetailsInterface.notifySuccess(Constants.emptyResult_volley);

                                }
                                if(arrayLength>0) {
                                    for (; i1 < (arrayLength); i1++) {
                                        JSONObject json = JArray.getJSONObject(i1);
                                        Modal_DeliveryCenterDetails modal_DeliveryCenterDetails = new Modal_DeliveryCenterDetails();


                                        try {
                                            if (json.has("deliverycentrekey")) {
                                                modal_DeliveryCenterDetails.deliverycenterkey = String.valueOf(json.get("deliverycentrekey"));
                                            } else {
                                                modal_DeliveryCenterDetails.deliverycenterkey = "";
                                            }
                                        } catch (Exception e) {
                                            modal_DeliveryCenterDetails.deliverycenterkey = "";
                                            e.printStackTrace();
                                        }
                                        try {
                                            if (json.has("mobileno")) {
                                                modal_DeliveryCenterDetails.mobileno = String.valueOf(json.get("mobileno"));
                                            } else {
                                                modal_DeliveryCenterDetails.mobileno = "";
                                            }
                                        } catch (Exception e) {
                                            modal_DeliveryCenterDetails.mobileno = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("name")) {
                                                modal_DeliveryCenterDetails.name = String.valueOf(json.get("name"));
                                            } else {
                                                modal_DeliveryCenterDetails.name = "";
                                            }
                                        } catch (Exception e) {
                                            modal_DeliveryCenterDetails.name = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("password")) {
                                                modal_DeliveryCenterDetails.password = String.valueOf(json.get("password"));
                                            } else {
                                                modal_DeliveryCenterDetails.password = "";
                                            }
                                        } catch (Exception e) {
                                            modal_DeliveryCenterDetails.password = "";
                                            e.printStackTrace();
                                        }
                                        if(apiMethodToCall.equals(Constants.CallGETListMethod)){
                                            deliveryCenterDetailsList.add(modal_DeliveryCenterDetails);
                                        }
                                        else if(apiMethodToCall.equals(Constants.CallGETMethod)){
                                            Modal_DeliveryCenterDetails.deliverycenterkey_static = String.valueOf(json.get("deliverycentrekey"));
                                            Modal_DeliveryCenterDetails.mobileno_static = String.valueOf(json.get("mobileno"));
                                            Modal_DeliveryCenterDetails.password_static = String.valueOf(json.get("password"));
                                            Modal_DeliveryCenterDetails.name_static = String.valueOf(json.get("name"));
                                            callback_deliveryCenterDetailsInterface.notifySuccess("success");

                                        }


                                    }
                                }

                                if(apiMethodToCall.equals(Constants.CallGETListMethod)){
                                    DatabaseArrayList_PojoClass.setDeliveryCenterDetailsList(deliveryCenterDetailsList);
                                }
                                else if(apiMethodToCall.equals(Constants.CallGETMethod)){

                                }

                                callback_deliveryCenterDetailsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, error -> {

                //Log.d(Constants.TAG, "Error: " + error.getLocalizedMessage());
                //Log.d(Constants.TAG, "Error: " + error.getMessage());
                //Log.d(Constants.TAG, "Error: " + error.toString());
                callback_deliveryCenterDetailsInterface.notifyError( error);

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
            callback_deliveryCenterDetailsInterface.notifyError((VolleyError) e);
        }



        return null;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


    @Override
    protected void onPostExecute( List<Modal_DeliveryCenterDetails> modal ) {
        super.onPostExecute(modal);

    }



}
