package com.tmc.tmcb2bpartnerapp.apiRequestServices;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BTokenNoManagerInterface;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.volleyrequestqueuehelper;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class B2BTokenNoManager extends AsyncTask<String, String, String> {
    B2BTokenNoManagerInterface  callback_B2BTokenNoManagerInterface ;
    String apiToCall = "" ,callMethod ="";
    
    
    
    
    public B2BTokenNoManager(B2BTokenNoManagerInterface callback_B2BTokenNoManagerInterfacee, String getApiToCall, String apiMethodtoCall) {
        this.callback_B2BTokenNoManagerInterface = callback_B2BTokenNoManagerInterfacee;
        this.apiToCall = getApiToCall;
        this.callMethod = apiMethodtoCall;
       
        
    }


    public  void generateNewTokenNo() {


        if(callback_B2BTokenNoManagerInterface != null) {



            try {

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, apiToCall, null,
                        response -> {

                            //callback_appUserInterface != null) {
                            try {
                                //converting jsonSTRING into array
                                Log.d("Constants.TAG", "GenerateBatchId Response: " + response);
                                String invoiceno = response.getString("tokenno");

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

                                callback_B2BTokenNoManagerInterface.notifySuccess(invoiceno);


                                //callback_appUserInterface.notifySuccess("success"+ Modal_AppUserAccess.name);
                            } catch (Exception e) {
                                Log.d("Constants.TAG", "GenerateBatchId Response: Exception " + e);
                                callback_B2BTokenNoManagerInterface.notifyProcessingError(e);

                                e.printStackTrace();

                            }
                        }, error -> {

                    //Log.d(Constants.TAG, "Error: " + error.getLocalizedMessage());
                    //Log.d(Constants.TAG, "Error: " + error.getMessage());
                    //Log.d(Constants.TAG, "Error: " + error.toString());
                    //callback_appUserInterface.notifyError( error);
                    Log.d("Constants.TAG", "GenerateBatchId Response: error " + error);
                    callback_B2BTokenNoManagerInterface.notifyVolleyError(error);

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

    public  void getInvoiceNo() {
        if(callback_B2BTokenNoManagerInterface != null) {



            try {

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiToCall, null,
                        response -> {


                            try {
                                Log.d("Constants.TAG", "GenerateBatchId Response: " + response);
                                JSONArray batchId_JSONArray = response.getJSONArray("tokenno");
                                Log.d("Constants.TAG", "GenerateBatchId batchId_JSONArray: " + batchId_JSONArray);

                                String invoiceno = String.valueOf(batchId_JSONArray.get(0));




                                    int invoice_int = Integer.parseInt(invoiceno);
                                    invoice_int = invoice_int+1;
                                    invoiceno = String.valueOf(invoice_int);





                                Log.d("Constants.TAG", "GenerateBatchId batchId: " + invoiceno);

                                callback_B2BTokenNoManagerInterface.notifySuccess(invoiceno);

                            } catch (Exception e) {
                                Log.d("Constants.TAG", "GenerateBatchId Response: Exception " + e);
                                callback_B2BTokenNoManagerInterface.notifyProcessingError(e);

                                e.printStackTrace();

                            }
                        }, error -> {


                    Log.d("Constants.TAG", "GenerateBatchId Response: error " + error);
                    callback_B2BTokenNoManagerInterface.notifyVolleyError(error);

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


    @Override
    protected String doInBackground(String... strings) {


        if(callMethod.equals(Constants.CallGENERATEMethod) ){
            generateNewTokenNo();
        }

        else if(callMethod.equals(Constants.CallGETMethod)){
            getInvoiceNo();

        }
        
        
        
        
        
        
        
        return null;
    }
}
