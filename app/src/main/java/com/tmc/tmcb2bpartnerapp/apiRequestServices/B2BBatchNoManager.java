package com.tmc.tmcb2bpartnerapp.apiRequestServices;

import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BBatchNoManagerInterface;
import com.tmc.tmcb2bpartnerapp.utils.volleyrequestqueuehelper;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import static com.tmc.tmcb2bpartnerapp.utils.API_Manager.generateBatchId;
import static com.tmc.tmcb2bpartnerapp.utils.API_Manager.getBatchId;

public class B2BBatchNoManager {

    public static void generateNewBatchNo(B2BBatchNoManagerInterface callback_b2BBatchIdManagerInterface) {


        if(callback_b2BBatchIdManagerInterface != null) {



            try {

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, generateBatchId, null,
                        response -> {

                            //callback_appUserInterface != null) {
                            try {
                                //converting jsonSTRING into array
                                Log.d("Constants.TAG", "GenerateBatchId Response: " + response);
                                String batchId = response.getString("batchno");
                                callback_b2BBatchIdManagerInterface.notifySuccess(batchId);


                                //callback_appUserInterface.notifySuccess("success"+ Modal_AppUserAccess.name);
                            } catch (Exception e) {
                                Log.d("Constants.TAG", "GenerateBatchId Response: Exception " + e);
                                callback_b2BBatchIdManagerInterface.notifyProcessingError(e);

                                e.printStackTrace();

                            }
                        }, error -> {

                    //Log.d(Constants.TAG, "Error: " + error.getLocalizedMessage());
                    //Log.d(Constants.TAG, "Error: " + error.getMessage());
                    //Log.d(Constants.TAG, "Error: " + error.toString());
                    //callback_appUserInterface.notifyError( error);
                    Log.d("Constants.TAG", "GenerateBatchId Response: error " + error);
                    callback_b2BBatchIdManagerInterface.notifyVolleyError(error);

                    error.printStackTrace();
                }) {
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


            } catch (Exception e) {
                e.printStackTrace();
                //callback_appUserInterface.notifyError((VolleyError) e);
            }
        }
    
    }

    public static void getBatchNo(B2BBatchNoManagerInterface callback_b2BBatchIdManagerInterface) {
        if(callback_b2BBatchIdManagerInterface != null) {



            try {

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getBatchId , null,
                        response -> {


                            try {
                                Log.d("Constants.TAG", "GenerateBatchId Response: " + response);
                                JSONArray batchId_JSONArray = response.getJSONArray("batchno");
                                Log.d("Constants.TAG", "GenerateBatchId batchId_JSONArray: " + batchId_JSONArray);

                                String batchId = String.valueOf(batchId_JSONArray.get(0));
                                Log.d("Constants.TAG", "GenerateBatchId batchId: " + batchId);

                                callback_b2BBatchIdManagerInterface.notifySuccess(batchId);

                            } catch (Exception e) {
                                Log.d("Constants.TAG", "GenerateBatchId Response: Exception " + e);
                                callback_b2BBatchIdManagerInterface.notifyProcessingError(e);

                                e.printStackTrace();

                            }
                        }, error -> {


                    Log.d("Constants.TAG", "GenerateBatchId Response: error " + error);
                    callback_b2BBatchIdManagerInterface.notifyVolleyError(error);

                    error.printStackTrace();
                }) {
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


            } catch (Exception e) {
                e.printStackTrace();
                //callback_appUserInterface.notifyError((VolleyError) e);
            }
        }
    }
}
