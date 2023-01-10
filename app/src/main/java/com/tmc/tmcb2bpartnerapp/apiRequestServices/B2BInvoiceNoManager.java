package com.tmc.tmcb2bpartnerapp.apiRequestServices;

import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BInvoiceNoManagerInterface;
import com.tmc.tmcb2bpartnerapp.utils.volleyrequestqueuehelper;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

 import static com.tmc.tmcb2bpartnerapp.utils.API_Manager.generateInvoiceNo;
 import static com.tmc.tmcb2bpartnerapp.utils.API_Manager.getInvoiceNo;

public class B2BInvoiceNoManager {




    public static void generateNewInvoiceNo(B2BInvoiceNoManagerInterface callback_b2bInvoiceNoManagerInterface) {


        if(callback_b2bInvoiceNoManagerInterface != null) {



            try {

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, generateInvoiceNo, null,
                        response -> {

                            //callback_appUserInterface != null) {
                            try {
                                //converting jsonSTRING into array
                                Log.d("Constants.TAG", "GenerateBatchId Response: " + response);
                                String invoiceno = response.getString("invoiceno");

                             /*   if(invoiceno.length() == 1){
                                    invoiceno = "00000"+invoiceno;

                                }
                                else  if(invoiceno.length() == 2){
                                    invoiceno = "0000"+invoiceno;

                                }
                                else  if(invoiceno.length() == 3){
                                    invoiceno = "000"+invoiceno;

                                }
                                else  if(invoiceno.length() == 4){
                                    invoiceno = "00"+invoiceno;

                                }
                                else  if(invoiceno.length() == 5){
                                    invoiceno = "0"+invoiceno;

                                }
                                else  {
                                    invoiceno = invoiceno;

                                }

                              */

                                callback_b2bInvoiceNoManagerInterface.notifySuccess(invoiceno);


                                //callback_appUserInterface.notifySuccess("success"+ Modal_AppUserAccess.name);
                            } catch (Exception e) {
                                Log.d("Constants.TAG", "GenerateBatchId Response: Exception " + e);
                                callback_b2bInvoiceNoManagerInterface.notifyProcessingError(e);

                                e.printStackTrace();

                            }
                        }, error -> {

                    //Log.d(Constants.TAG, "Error: " + error.getLocalizedMessage());
                    //Log.d(Constants.TAG, "Error: " + error.getMessage());
                    //Log.d(Constants.TAG, "Error: " + error.toString());
                    //callback_appUserInterface.notifyError( error);
                    Log.d("Constants.TAG", "GenerateBatchId Response: error " + error);
                    callback_b2bInvoiceNoManagerInterface.notifyVolleyError(error);

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

    public static void getInvoiceNo(B2BInvoiceNoManagerInterface callback_b2bInvoiceNoManagerInterface, boolean isUpdateNoLocally) {
        if(callback_b2bInvoiceNoManagerInterface != null) {



            try {

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getInvoiceNo , null,
                        response -> {


                            try {
                                Log.d("Constants.TAG", "GenerateBatchId Response: " + response);
                                JSONArray batchId_JSONArray = response.getJSONArray("invoiceno");
                                Log.d("Constants.TAG", "GenerateBatchId batchId_JSONArray: " + batchId_JSONArray);

                                String invoiceno = String.valueOf(batchId_JSONArray.get(0));


                                if(isUpdateNoLocally){


                                    int invoice_int = Integer.parseInt(invoiceno);
                                    invoice_int = invoice_int+1;
                                    invoiceno = String.valueOf(invoice_int);


                                   /* if(invoiceno.length() == 1){
                                        invoiceno = "00000"+invoiceno;

                                    }
                                    else  if(invoiceno.length() == 2){
                                        invoiceno = "0000"+invoiceno;

                                    }
                                    else  if(invoiceno.length() == 3){
                                        invoiceno = "000"+invoiceno;

                                    }
                                    else  if(invoiceno.length() == 4){
                                        invoiceno = "00"+invoiceno;

                                    }
                                    else  if(invoiceno.length() == 5){
                                        invoiceno = "0"+invoiceno;

                                    }
                                    else  {
                                        invoiceno = invoiceno;

                                    }

                                    */

                                }
                                else{
                                    /*if(invoiceno.length() == 1){
                                        invoiceno = "00000"+invoiceno;

                                    }
                                    else  if(invoiceno.length() == 2){
                                        invoiceno = "0000"+invoiceno;

                                    }
                                    else  if(invoiceno.length() == 3){
                                        invoiceno = "000"+invoiceno;

                                    }
                                    else  if(invoiceno.length() == 4){
                                        invoiceno = "00"+invoiceno;

                                    }
                                    else  if(invoiceno.length() == 5){
                                        invoiceno = "0"+invoiceno;

                                    }
                                    else  {
                                        invoiceno = invoiceno;

                                    }

                                     */

                                }


                                Log.d("Constants.TAG", "GenerateBatchId batchId: " + invoiceno);

                                callback_b2bInvoiceNoManagerInterface.notifySuccess(invoiceno);

                            } catch (Exception e) {
                                Log.d("Constants.TAG", "GenerateBatchId Response: Exception " + e);
                                callback_b2bInvoiceNoManagerInterface.notifyProcessingError(e);

                                e.printStackTrace();

                            }
                        }, error -> {


                    Log.d("Constants.TAG", "GenerateBatchId Response: error " + error);
                    callback_b2bInvoiceNoManagerInterface.notifyVolleyError(error);

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
