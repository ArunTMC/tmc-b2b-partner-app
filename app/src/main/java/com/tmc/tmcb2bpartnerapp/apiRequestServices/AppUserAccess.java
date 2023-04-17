package com.tmc.tmcb2bpartnerapp.apiRequestServices;

import android.os.AsyncTask;
import android.util.Log;


import androidx.annotation.NonNull;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.interfaces.AppUserAccessInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_AppUserAccess;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.volleyrequestqueuehelper;

import org.json.JSONArray;

import org.json.JSONObject;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppUserAccess extends AsyncTask<String, String, List<Modal_AppUserAccess>> {
    AppUserAccessInterface callback_appUserInterface;
    String appUserAccessApi;
    boolean isbackgroundTaskCompleted = false;

    public AppUserAccess(AppUserAccessInterface callback_appuserInterface, String appUserAccessApi) {
        this.callback_appUserInterface = callback_appuserInterface;
        this.appUserAccessApi = appUserAccessApi;
        new Modal_AppUserAccess();
    }

    @Override
    protected List<Modal_AppUserAccess> doInBackground(String... strings) {
        isbackgroundTaskCompleted = false;
        Log.d(Constants.TAG, "App user access async onbackground started  "+ DateParser.getDate_and_time_newFormat());

        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, appUserAccessApi,null,
                    response -> {
                        if (callback_appUserInterface != null) {
                            try {
                                //converting jsonSTRING into array
                                JSONArray JArray = response.getJSONArray("content");
                                if (JArray.length() == 0) {
                                    isbackgroundTaskCompleted = true;
                                    callback_appUserInterface.notifySuccess(Constants.emptyResult_volley);
                                    return;
                                }
                                //Log.d(Constants.TAG, "convertingJsonStringintoArray Response: " + JArray);
                                int i1 = 0;
                                int arrayLength = JArray.length();
                                //Log.d("Constants.TAG", "convertingJsonStringintoArray Response: " + arrayLength);

                                if(arrayLength>0) {
                                    for (; i1 < (arrayLength); i1++) {
                                        JSONObject json = JArray.getJSONObject(i1);


                                        try {
                                            if (json.has("appname")) {
                                                Modal_AppUserAccess.appname = String.valueOf(json.get("appname"));
                                            } else {
                                                Modal_AppUserAccess.appname = "";
                                            }
                                        } catch (Exception e) {
                                            Modal_AppUserAccess.appname = "";
                                            e.printStackTrace();
                                        }
                                        try {
                                            if (json.has("supplierkey")) {
                                                Modal_AppUserAccess.supplierkey = String.valueOf(json.get("supplierkey"));
                                            } else {
                                                Modal_AppUserAccess.supplierkey = "";
                                            }
                                        } catch (Exception e) {
                                            Modal_AppUserAccess.supplierkey = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("suppliername")) {
                                                Modal_AppUserAccess.suppliername = String.valueOf(json.get("suppliername"));
                                            } else {
                                                Modal_AppUserAccess.suppliername = "";
                                            }
                                        } catch (Exception e) {
                                            Modal_AppUserAccess.suppliername = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("mobileno")) {
                                                Modal_AppUserAccess.mobileno = String.valueOf(json.get("mobileno"));
                                            } else {
                                                Modal_AppUserAccess.mobileno = "";
                                            }
                                        } catch (Exception e) {
                                            Modal_AppUserAccess.mobileno = "";
                                            e.printStackTrace();
                                        }

                                        try {
                                            if (json.has("name")) {
                                                Modal_AppUserAccess.name = String.valueOf(json.get("name"));
                                            } else {
                                                Modal_AppUserAccess.name = "";
                                            }
                                        } catch (Exception e) {
                                            Modal_AppUserAccess.name = "";
                                            e.printStackTrace();
                                        }

                                        try {
                                            if (json.has("role")) {
                                                Modal_AppUserAccess.role = String.valueOf(json.get("role"));
                                            } else {
                                                Modal_AppUserAccess.role = "";
                                            }
                                        } catch (Exception e) {
                                            Modal_AppUserAccess.role = "";
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("usertype")) {
                                                Modal_AppUserAccess.usertype = String.valueOf(json.get("usertype"));
                                            } else {
                                                Modal_AppUserAccess.usertype = "";
                                            }
                                        } catch (Exception e) {
                                            Modal_AppUserAccess.usertype = "";
                                            e.printStackTrace();
                                        }




                                    }
                                    }
                                isbackgroundTaskCompleted = true;
                                Log.d(Constants.TAG, "App user access async responded to user  "+ DateParser.getDate_and_time_newFormat());

                                callback_appUserInterface.notifySuccess("success"+ Modal_AppUserAccess.name);
                                    } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, error -> {

                        //Log.d(Constants.TAG, "Error: " + error.getLocalizedMessage());
                        //Log.d(Constants.TAG, "Error: " + error.getMessage());
                        //Log.d(Constants.TAG, "Error: " + error.toString());
                    isbackgroundTaskCompleted = true;

                    callback_appUserInterface.notifyError( error);

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
            isbackgroundTaskCompleted = true;

            callback_appUserInterface.notifyError((VolleyError) e);
        }



        return null;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.d(Constants.TAG, "App user access async onCancelled  "+ DateParser.getDate_and_time_newFormat());

    }

    @Override
    protected void onPostExecute( List<Modal_AppUserAccess> modal ) {
        super.onPostExecute(modal);
        Log.d(Constants.TAG, "App user access async before backgroundTaskCompleted on post execute   " + DateParser.getDate_and_time_newFormat());

        if(isbackgroundTaskCompleted) {
            Log.d(Constants.TAG, "App user access async backgroundTaskCompleted on post execute   " + DateParser.getDate_and_time_newFormat());

            cancel(true);
        }
        else{
            Log.d(Constants.TAG, "App user access async else backgroundTaskCompleted on post execute   " + DateParser.getDate_and_time_newFormat());

        }

    }
}
