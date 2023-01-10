package com.tmc.tmcb2bpartnerapp.apiRequestServices;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BPartnerAppUserInterface;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BPartnerAppUser;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.volleyrequestqueuehelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class B2BPartnerAppUser extends AsyncTask<String, String, List<Modal_B2BPartnerAppUser>> {



    B2BPartnerAppUserInterface callback_B2BPartnerAppUserInterface;
    String ApiToCall ="", callMethod ="";
    JSONObject jsonToADD_Or_Update = new JSONObject();



    public B2BPartnerAppUser(B2BPartnerAppUserInterface callback_B2BPartnerAppUserInterfacee, String ApiToCall, String callMethod) {
        this.callback_B2BPartnerAppUserInterface = callback_B2BPartnerAppUserInterfacee;
        this.ApiToCall = ApiToCall;
        this.callMethod  = callMethod;
        this.jsonToADD_Or_Update = getJSONforPOJOClass();
    }



    @Override
    protected List<Modal_B2BPartnerAppUser> doInBackground(String... strings) {


        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiToCall,jsonToADD_Or_Update,
                    response -> {
                        if (callback_B2BPartnerAppUserInterface != null) {
                            try {
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callback_B2BPartnerAppUserInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){
                                    callback_B2BPartnerAppUserInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callback_B2BPartnerAppUserInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                                //  callback_b2BBatchDetailsInterface.notifySuccess("success");
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback_B2BPartnerAppUserInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callback_B2BPartnerAppUserInterface.notifyVolleyError( error);

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
            callback_B2BPartnerAppUserInterface.notifyProcessingError(e);
        }


        return null;
    }




    private JSONObject getJSONforPOJOClass() {
        JSONObject jsonObject = new JSONObject();
        try {
            if(!Modal_B2BPartnerAppUser.getAppversion().toString().equals("") && !Modal_B2BPartnerAppUser.getAppversion().toString().equals("null")){
                jsonObject.put("appversion", Modal_B2BPartnerAppUser.getAppversion());
            }
            if(!Modal_B2BPartnerAppUser.getCreatedtime().toString().equals("") && !Modal_B2BPartnerAppUser.getCreatedtime().toString().equals("null")){
                jsonObject.put("createdtime", Modal_B2BPartnerAppUser.getCreatedtime());
            }
            if(!Modal_B2BPartnerAppUser.getFcmtoken().toString().equals("") && !Modal_B2BPartnerAppUser.getFcmtoken().toString().equals("null")){
                jsonObject.put("fcmtoken", Modal_B2BPartnerAppUser.getFcmtoken());
            }
            if(!Modal_B2BPartnerAppUser.getMobileno().toString().equals("") && !Modal_B2BPartnerAppUser.getMobileno().toString().equals("null")){
                jsonObject.put("mobileno", Modal_B2BPartnerAppUser.getMobileno());
            }
            if(!Modal_B2BPartnerAppUser.getName().toString().equals("") && !Modal_B2BPartnerAppUser.getName().toString().equals("null")){
                jsonObject.put("name", Modal_B2BPartnerAppUser.getName());
            }
            if(!Modal_B2BPartnerAppUser.getPreviousversion().toString().equals("") && !Modal_B2BPartnerAppUser.getPreviousversion().toString().equals("null")){
                jsonObject.put("previousversion", Modal_B2BPartnerAppUser.getPreviousversion());
            }
            if(!Modal_B2BPartnerAppUser.getUsertype().toString().equals("") && !Modal_B2BPartnerAppUser.getUsertype().toString().equals("null")){
                jsonObject.put("usertype", Modal_B2BPartnerAppUser.getUsertype());
            }
            if(!Modal_B2BPartnerAppUser.getUpdatedtime().toString().equals("") && !Modal_B2BPartnerAppUser.getUpdatedtime().toString().equals("null")){
                jsonObject.put("updatedtime", Modal_B2BPartnerAppUser.getUpdatedtime());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }



}
