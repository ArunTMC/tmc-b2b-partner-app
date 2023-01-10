package com.tmc.tmcb2bpartnerapp.apiRequestServices;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.interfaces.B2BItemCtgyInterface;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BItemCtgy;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DatabaseArrayList_PojoClass;
import com.tmc.tmcb2bpartnerapp.utils.volleyrequestqueuehelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class B2BItemCtgy extends AsyncTask<String, String, List<Modal_B2BItemCtgy>> {
        B2BItemCtgyInterface callback_ItemCtgyInterface;
        String Apitocall;




public B2BItemCtgy(B2BItemCtgyInterface callback_ItemCtgyInterfacee, String apii) {
        this.callback_ItemCtgyInterface = callback_ItemCtgyInterfacee;
        this.Apitocall = apii;
        new Modal_B2BItemCtgy();
        }

@Override
protected List<Modal_B2BItemCtgy> doInBackground(String... strings) {

    DatabaseArrayList_PojoClass.breedType_arrayList_string.clear();
    DatabaseArrayList_PojoClass.breedType_arrayList.clear();



    try{

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Apitocall,null,
        response -> {
        if (callback_ItemCtgyInterface != null) {
        try {
        //converting jsonSTRING into array
        JSONArray JArray = response.getJSONArray("content");
        if (JArray.length() == 0) {
        callback_ItemCtgyInterface.notifySuccess(Constants.emptyResult_volley);
        return;
        }
        //Log.d(Constants.TAG, "convertingJsonStringintoArray Response: " + JArray);
        int i1 = 0;
        int arrayLength = JArray.length();
        //Log.d("Constants.TAG", "convertingJsonStringintoArray Response: " + arrayLength);

        if(arrayLength>0) {
        for (; i1 < (arrayLength); i1++) {
        JSONObject json = JArray.getJSONObject(i1);


     /*   try {
        if (json.has("name")) {
        Modal_B2BItemCtgy.name = String.valueOf(json.get("name"));
        } else {
        Modal_B2BItemCtgy.name = "";
        }
        } catch (Exception e) {
        Modal_B2BItemCtgy.name = "";
        e.printStackTrace();
        }



        try {
        if (json.has("key")) {
        Modal_B2BItemCtgy.key = String.valueOf(json.get("key"));
        } else {
        Modal_B2BItemCtgy.key = "";
        }
        } catch (Exception e) {
        Modal_B2BItemCtgy.key = "";
        e.printStackTrace();
        }

      */


        try {
        if (json.has("subctgydetails")) {
        Modal_B2BItemCtgy.setSubctgydetails( json.getJSONArray("subctgydetails"));
            JSONArray subctgydetails = json.getJSONArray("subctgydetails");


            try{
                for(int  iterator =0; iterator< subctgydetails.length(); iterator++){
                    JSONObject jsonObject = subctgydetails.getJSONObject(iterator);

                    Modal_B2BItemCtgy b2BItemCtgy = new Modal_B2BItemCtgy();
                    b2BItemCtgy.setSubctgy_name(jsonObject.getString("name"));
                    b2BItemCtgy.setSubctgy_key(jsonObject.getString("key"));
                    b2BItemCtgy.setKey(String.valueOf(json.get("key")));
                    b2BItemCtgy.setName(String.valueOf(json.get("name")));
                    DatabaseArrayList_PojoClass.breedType_arrayList.add(b2BItemCtgy);
                    DatabaseArrayList_PojoClass.breedType_arrayList_string.add(jsonObject.getString("name"));
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }



        } else {
        Modal_B2BItemCtgy.subctgydetails = new JSONArray();

        }
        } catch (Exception e) {
        Modal_B2BItemCtgy.subctgydetails = new JSONArray();

        e.printStackTrace();
        }



        }
        }
        callback_ItemCtgyInterface.notifySuccess("success");
        } catch (Exception e) {
        e.printStackTrace();
        }
        }
        }, error -> {

        //Log.d(Constants.TAG, "Error: " + error.getLocalizedMessage());
        //Log.d(Constants.TAG, "Error: " + error.getMessage());
        //Log.d(Constants.TAG, "Error: " + error.toString());
        callback_ItemCtgyInterface.notifyError( error);

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
        callback_ItemCtgyInterface.notifyError((VolleyError) e);
        }



        return null;
        }



@Override
protected void onPreExecute() {
        super.onPreExecute();

        }


@Override
protected void onPostExecute( List<Modal_B2BItemCtgy> modal ) {
        super.onPostExecute(modal);

        }
        }
