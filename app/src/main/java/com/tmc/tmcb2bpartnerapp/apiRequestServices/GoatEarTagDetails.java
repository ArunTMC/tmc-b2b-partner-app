package com.tmc.tmcb2bpartnerapp.apiRequestServices;

import android.os.AsyncTask;


import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tmc.tmcb2bpartnerapp.interfaces.GoatEarTagDetailsInterface;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_Static_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_UpdatedGoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.volleyrequestqueuehelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("InstantiationOfUtilityClass")
public class GoatEarTagDetails extends AsyncTask<String, String, List<Modal_Static_GoatEarTagDetails>> {

    String ApitoCall ="", callMethod ="";
    GoatEarTagDetailsInterface callback_GoatEarTagDetailsInterface = null;
    JSONObject jsonToADD_Or_Update = new JSONObject();
    ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch = new ArrayList<>();
    double meatyeild_weight_double = 0 , parts_Weight_double =0 , total_Weight_double = 0;



    public GoatEarTagDetails(GoatEarTagDetailsInterface callback_GoatEarTagDetailsInterfacee, String addApiToCall, String callADDMethod) {
        this.ApitoCall = addApiToCall;
        this.callMethod = callADDMethod;
        this.callback_GoatEarTagDetailsInterface = callback_GoatEarTagDetailsInterfacee;
        if(callMethod.equals(Constants.CallADDMethod)){
            jsonToADD_Or_Update = getJSON_to_ADD_FromPOJOClass();

        }
         else if(callMethod.equals(Constants.CallUPDATEMethod)){
            jsonToADD_Or_Update = getJSON_to_Update_FromPOJOClass();

        }
         else if(callMethod.equals(Constants.CallGETMethod)){
            new Modal_Static_GoatEarTagDetails();
            new Modal_UpdatedGoatEarTagDetails();
            new Modal_GoatEarTagDetails();
        }

    }



    @Override
    protected List<Modal_Static_GoatEarTagDetails> doInBackground(String... strings) {
        if(callMethod.equals(Constants.CallADDMethod)){
            addEntryInBatchDetails();
        }
        else if(callMethod.equals(Constants.CallGETMethod) ||callMethod.equals(Constants.CallGETListMethod) ){
            new Modal_UpdatedGoatEarTagDetails();
            new Modal_Static_GoatEarTagDetails();
            getDataFromBatchDetails();
        }
        else if(callMethod.equals(Constants.CallUPDATEMethod)){
            updateEntryInBatchDetails();
        }
        return null;
    }

    private void getDataFromBatchDetails() {


        try{
            earTagItemsForBatch.clear();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ApitoCall,null,
                    response -> {
                        if (callback_GoatEarTagDetailsInterface != null) {
                            try {
                                //converting jsonSTRING into array
                                JSONArray JArray = response.getJSONArray("content");
                                //Log.d(Constants.TAG, "convertingJsonStringintoArray Response: " + JArray);
                                if (JArray.length() == 0) {
                                    if (callMethod.equals(Constants.CallGETMethod)) {


                                        callback_GoatEarTagDetailsInterface.notifySuccess(Constants.emptyResult_volley);
                                    } else if (callMethod.equals(Constants.CallGETListMethod)) {
                                        earTagItemsForBatch.clear();
                                        callback_GoatEarTagDetailsInterface.notifySuccessForGettingListItem(earTagItemsForBatch);

                                    }
                                }

                                else{
                                int i1 = 0;
                                int arrayLength = JArray.length();
                                //Log.d("Constants.TAG", "convertingJsonStringintoArray Response: " + arrayLength);

                                if (arrayLength > 0) {
                                    for (; i1 < (arrayLength); i1++) {
                                        JSONObject json = JArray.getJSONObject(i1);
                                        Modal_GoatEarTagDetails modal_goatEarTagDetails = new Modal_GoatEarTagDetails();

                                        try {
                                            if (json.has("batchno")) {
                                                modal_goatEarTagDetails.batchno = String.valueOf(json.get("batchno"));
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.batchno = String.valueOf(json.get("batchno"));
                                                }
                                            } else {
                                                modal_goatEarTagDetails.batchno = "";
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.batchno = "";
                                                }
                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.batchno = "";
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.batchno = "";
                                            }
                                            e.printStackTrace();
                                        }
                                        try {
                                            if (json.has("barcodeno")) {
                                                modal_goatEarTagDetails.barcodeno = String.valueOf(json.get("barcodeno")).toUpperCase();
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.barcodeno = String.valueOf(json.get("barcodeno")).toUpperCase();
                                                }
                                            } else {
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    modal_goatEarTagDetails.barcodeno = "";
                                                }
                                                Modal_Static_GoatEarTagDetails.barcodeno = "";

                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.barcodeno = "";
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.barcodeno = "";
                                            }
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("suppliername")) {
                                                modal_goatEarTagDetails.suppliername = String.valueOf(json.get("suppliername"));
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.suppliername = String.valueOf(json.get("suppliername"));
                                                }
                                            } else {
                                                modal_goatEarTagDetails.suppliername = "";
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.suppliername = "";
                                                }
                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.suppliername = "";
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.suppliername = "";
                                            }
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("loadedweightingrams")) {
                                                modal_goatEarTagDetails.loadedweightingrams = ConvertGramsToKilograms(String.valueOf(json.get("loadedweightingrams")));
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.loadedweightingrams = ConvertGramsToKilograms(String.valueOf(json.get("loadedweightingrams")));
                                                }
                                            } else {
                                                modal_goatEarTagDetails.loadedweightingrams = "";
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.loadedweightingrams = "";
                                                }

                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.loadedweightingrams = "";
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.loadedweightingrams = "";
                                            }
                                            e.printStackTrace();
                                        }
                                        try {
                                            if (json.has("stockedweightingrams")) {
                                                try {
                                                    modal_goatEarTagDetails.stockedweightingrams = ConvertGramsToKilograms(String.valueOf(json.get("stockedweightingrams")));
                                                }
                                                catch (Exception e){
                                                    e.printStackTrace();
                                                }
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.stockedweightingrams = ConvertGramsToKilograms(String.valueOf(json.get("stockedweightingrams")));
                                                }
                                            } else {
                                                modal_goatEarTagDetails.stockedweightingrams = "";
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.stockedweightingrams = "";
                                                }
                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.stockedweightingrams = "";
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.stockedweightingrams = "";
                                            }
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("itemaddeddate")) {
                                                modal_goatEarTagDetails.itemaddeddate = DateParser.convertTime_to_DisplayingFormat(String.valueOf(json.get("itemaddeddate")));
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.itemaddeddate = DateParser.convertTime_to_DisplayingFormat(String.valueOf(json.get("itemaddeddate")));
                                                }
                                                }
                                                else {
                                                modal_goatEarTagDetails.itemaddeddate = "";
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.itemaddeddate = "";
                                                }
                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.itemaddeddate = "";
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.itemaddeddate = "";
                                            }
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("status")) {
                                                modal_goatEarTagDetails.status = String.valueOf(json.get("status"));
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.status = String.valueOf(json.get("status"));
                                                }
                                            } else {
                                                modal_goatEarTagDetails.status = "";
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.status = "";
                                                }
                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.status = "";
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.status = "";
                                            }
                                            e.printStackTrace();
                                        }



                                        try {
                                            if (json.has("gradename")) {
                                               // modal_goatEarTagDetails.gradename = String.valueOf(json.get("gradename"));
                                                modal_goatEarTagDetails.gradename =ConvertFirstLetter_to_Capital( String.valueOf(json.get("gradename")));
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    //Modal_Static_GoatEarTagDetails.gradename = String.valueOf(json.get("gradename"));
                                                    Modal_Static_GoatEarTagDetails.gradename =ConvertFirstLetter_to_Capital(String.valueOf(json.get("gradename")));
                                                }
                                            } else {
                                                modal_goatEarTagDetails.gradename = "";
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.gradename = "";
                                                }
                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.gradename = "";
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.gradename = "";
                                            }
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("gradekey")) {
                                                modal_goatEarTagDetails.gradekey = String.valueOf(json.get("gradekey"));
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.gradekey = String.valueOf(json.get("gradekey"));
                                                }
                                            } else {
                                                modal_goatEarTagDetails.gradekey = "";
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.gradekey = "";
                                                }
                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.gradekey = "";
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.gradekey = "";
                                            }
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("gradeprice")) {
                                                modal_goatEarTagDetails.gradeprice = String.valueOf(json.get("gradeprice"));
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.gradeprice = String.valueOf(json.get("gradeprice"));
                                                }
                                            } else {
                                                modal_goatEarTagDetails.gradeprice = "";
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.gradeprice = "";
                                                }
                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.gradeprice = "";
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.gradeprice = "";
                                            }
                                            e.printStackTrace();
                                        }





                                        try {
                                            if (json.has("currentweightingrams")) {
                                                modal_goatEarTagDetails.currentweightingrams = ConvertGramsToKilograms(String.valueOf(json.get("currentweightingrams")));
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.currentweightingrams = ConvertGramsToKilograms(String.valueOf(json.get("currentweightingrams")));
                                                }
                                            } else {
                                                modal_goatEarTagDetails.currentweightingrams = "";
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.currentweightingrams = "";
                                                }
                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.currentweightingrams = "";
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.currentweightingrams = "";
                                            }
                                            e.printStackTrace();
                                        }

                                        try {
                                            if (json.has("gender")) {
                                                if(String.valueOf(json.get("gender")).toUpperCase().contains("BABY")){
                                                    modal_goatEarTagDetails.gender = "FEMALE";
                                                }
                                                else{
                                                    modal_goatEarTagDetails.gender = String.valueOf(json.get("gender"));
                                                }
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    if(String.valueOf(json.get("gender")).toUpperCase().contains("BABY")){
                                                        Modal_Static_GoatEarTagDetails.gender = "FEMALE";
                                                    }
                                                    else{
                                                        Modal_Static_GoatEarTagDetails.gender = String.valueOf(json.get("gender"));

                                                    }
                                                }
                                            } else {
                                                modal_goatEarTagDetails.gender = "";
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.gender = "";
                                                }

                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.gender = "";
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.gender = "";
                                            }
                                            e.printStackTrace();
                                        }
                                        try {
                                            if (json.has("breedtype")) {
                                                modal_goatEarTagDetails.breedtype = String.valueOf(json.get("breedtype"));
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.breedtype = String.valueOf(json.get("breedtype"));
                                                }
                                            } else {
                                                modal_goatEarTagDetails.breedtype = "";

                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.breedtype = "";
                                                }

                                            }
                                        } catch (Exception e) {
                                            if (callMethod.equals(Constants.CallGETMethod)) {

                                                Modal_Static_GoatEarTagDetails.breedtype = "";
                                            }
                                            modal_goatEarTagDetails.breedtype = "";


                                            e.printStackTrace();
                                        }

                                        try {
                                            if (json.has("description")) {
                                                modal_goatEarTagDetails.description = String.valueOf(json.get("description"));
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.description = String.valueOf(json.get("description"));
                                                }
                                            } else {
                                                modal_goatEarTagDetails.description = "";
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.description = "";
                                                }
                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.description = "";
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.description = "";
                                            }
                                            e.printStackTrace();
                                        }
                                        try {
                                            if (json.has("supplierkey")) {
                                                modal_goatEarTagDetails.supplierkey = String.valueOf(json.get("supplierkey"));
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.supplierkey = String.valueOf(json.get("supplierkey"));
                                                }
                                            } else {
                                                modal_goatEarTagDetails.supplierkey = "";
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.supplierkey = "";
                                                }
                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.supplierkey = "";
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.supplierkey = "";
                                            }
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("deliverycentrename")) {
                                                modal_goatEarTagDetails.deliverycentername = String.valueOf(json.get("deliverycentrename"));
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.deliverycentername = String.valueOf(json.get("deliverycentrename"));
                                                }
                                            } else {
                                                modal_goatEarTagDetails.deliverycentername = "";
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.deliverycentername = "";
                                                }
                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.deliverycentername = "";
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.deliverycentername = "";
                                            }
                                            e.printStackTrace();
                                        }


                                        try {
                                            if (json.has("deliverycentrekey")) {
                                                modal_goatEarTagDetails.deliverycenterkey = String.valueOf(json.get("deliverycentrekey"));
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.deliverycenterkey = String.valueOf(json.get("deliverycentrekey"));
                                                }
                                            } else {
                                                modal_goatEarTagDetails.deliverycenterkey = "";
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.deliverycenterkey = "";
                                                }
                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.deliverycenterkey = "";
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.deliverycenterkey = "";
                                            }
                                            e.printStackTrace();
                                        }



                                        try {
                                            if (json.has("partsweight")) {
                                                modal_goatEarTagDetails.partsweight = ConvertGramsToKilograms(String.valueOf(json.get("partsweight")));
                                                try{

                                                    parts_Weight_double  = Double.parseDouble(ConvertGramsToKilograms(String.valueOf(json.get("partsweight"))));;


                                                }
                                                catch (Exception e){
                                                    e.printStackTrace();
                                                }
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.partsweight = ConvertGramsToKilograms(String.valueOf(json.get("partsweight")));
                                                }
                                            } else {
                                                modal_goatEarTagDetails.partsweight = "";
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.partsweight = "";
                                                    try{

                                                        parts_Weight_double  = 0;


                                                    }
                                                    catch (Exception e){
                                                        e.printStackTrace();
                                                    }
                                                }

                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.partsweight = "";
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.partsweight = "";
                                            }
                                            try{

                                                parts_Weight_double  = 0;


                                            }
                                            catch (Exception e1){
                                                e1.printStackTrace();
                                            }
                                            e.printStackTrace();
                                        }





                                        try {
                                            if (json.has("meatyieldweight")) {
                                                modal_goatEarTagDetails.meatyieldweight = ConvertGramsToKilograms(String.valueOf(json.get("meatyieldweight")));
                                                try{

                                                    meatyeild_weight_double  = Double.parseDouble(ConvertGramsToKilograms(String.valueOf(json.get("meatyieldweight"))));;


                                                }
                                                catch (Exception e){
                                                    e.printStackTrace();
                                                }
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.meatyieldweight = ConvertGramsToKilograms(String.valueOf(json.get("meatyieldweight")));
                                                }
                                            } else {
                                                modal_goatEarTagDetails.meatyieldweight = "";
                                                try{

                                                    meatyeild_weight_double  = 0;


                                                }
                                                catch (Exception e){
                                                    e.printStackTrace();
                                                }
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.meatyieldweight = "";
                                                }

                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.meatyieldweight = "";
                                            try{

                                                meatyeild_weight_double  = 0;


                                            }
                                            catch (Exception e1){
                                                e1.printStackTrace();
                                            }
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.meatyieldweight = "";
                                            }
                                            e.printStackTrace();
                                        }


                                        try{
                                            total_Weight_double = meatyeild_weight_double  + parts_Weight_double;
                                        }
                                        catch (Exception e){
                                            e.printStackTrace();
                                        }
                                        double temp_totalWeight =0;
                                        try{
                                            temp_totalWeight  = total_Weight_double ;
                                            DecimalFormat df = new DecimalFormat(Constants.threeDecimalPattern);

                                            total_Weight_double = Double.parseDouble(df.format(total_Weight_double));
                                        }
                                        catch (Exception e){
                                            total_Weight_double = temp_totalWeight ;
                                            e.printStackTrace();
                                        }
                                        try {
                                                modal_goatEarTagDetails.totalItemWeight = String.valueOf(total_Weight_double);
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.itemWeight =String.valueOf(total_Weight_double); ;
                                                }

                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.totalItemWeight = String.valueOf(total_Weight_double);;
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.itemWeight =String.valueOf(total_Weight_double); ;
                                            }
                                            e.printStackTrace();
                                        }



                                        try {
                                            if (json.has("approxliveweight")) {
                                                modal_goatEarTagDetails.approxliveweight = ConvertGramsToKilograms(String.valueOf(json.get("approxliveweight")));
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.approxliveweight = ConvertGramsToKilograms(String.valueOf(json.get("approxliveweight")));
                                                }
                                            } else {
                                                modal_goatEarTagDetails.approxliveweight = "";
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.approxliveweight = "";
                                                }

                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.approxliveweight = "";
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.approxliveweight = "";
                                            }
                                            e.printStackTrace();
                                        }



                                        try {
                                            if (json.has("totalprice")) {
                                                modal_goatEarTagDetails.totalPrice_ofItem = String.valueOf(json.get("totalprice"));
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.totalPrice_ofItem = String.valueOf(json.get("totalprice"));
                                                }
                                            } else {
                                                modal_goatEarTagDetails.totalPrice_ofItem = "";
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.totalPrice_ofItem = "";
                                                }
                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.totalPrice_ofItem = "";
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.totalPrice_ofItem = "";
                                            }
                                            e.printStackTrace();
                                        }



                                        try {
                                            if (json.has("price")) {
                                                modal_goatEarTagDetails.itemPrice = String.valueOf(json.get("price"));
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.itemPrice = String.valueOf(json.get("price"));
                                                }
                                            } else {
                                                modal_goatEarTagDetails.itemPrice = "";
                                                if (callMethod.equals(Constants.CallGETMethod)) {
                                                    Modal_Static_GoatEarTagDetails.itemPrice = "";
                                                }
                                            }
                                        } catch (Exception e) {
                                            modal_goatEarTagDetails.itemPrice = "";
                                            if (callMethod.equals(Constants.CallGETMethod)) {
                                                Modal_Static_GoatEarTagDetails.itemPrice = "";
                                            }
                                            e.printStackTrace();
                                        }


                                        double  meatYield_double =0 , parts_double = 0 , totalWeight_double =0 ;
                                        try{
                                            String text = String.valueOf(Modal_Static_GoatEarTagDetails.getMeatyieldweight());
                                            text = text.replaceAll("[^\\d.]", "");
                                            if(text.equals("")){
                                                text = "0";
                                            }
                                            meatYield_double = Double.parseDouble(text);
                                        }
                                        catch (Exception e){
                                            meatYield_double = 0;
                                            e.printStackTrace();
                                        }


                                        try{
                                            String text = String.valueOf(Modal_Static_GoatEarTagDetails.getPartsweight());
                                            text = text.replaceAll("[^\\d.]", "");
                                            if(text.equals("")){
                                                text = "0";
                                            }
                                            parts_double = Double.parseDouble(text);
                                        }
                                        catch (Exception e){
                                            parts_double = 0;
                                            e.printStackTrace();
                                        }

                                        try{
                                            totalWeight_double = meatYield_double+parts_double;
                                        }
                                        catch (Exception e){
                                            totalWeight_double = 0;
                                            e.printStackTrace();
                                        }
                                        Modal_Static_GoatEarTagDetails.setItemWeight(String.valueOf(totalWeight_double));



                                        if (callMethod.equals(Constants.CallGETListMethod)) {
                                            earTagItemsForBatch.add(modal_goatEarTagDetails);
                                        }


                                    }
                                    if (callMethod.equals(Constants.CallGETMethod)) {
                                        callback_GoatEarTagDetailsInterface.notifySuccess(callMethod);
                                    } else if (callMethod.equals(Constants.CallGETListMethod)) {
                                        callback_GoatEarTagDetailsInterface.notifySuccessForGettingListItem(earTagItemsForBatch);

                                    }
                                }
                            }

                            } catch (Exception e) {
                                e.printStackTrace();
                                callback_GoatEarTagDetailsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callback_GoatEarTagDetailsInterface.notifyVolleyError( error);

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
            callback_GoatEarTagDetailsInterface.notifyProcessingError(e);
        }
        
        
        
    }



    private void updateEntryInBatchDetails() {


        try{


            if(jsonToADD_Or_Update.length() <= 2){
                callback_GoatEarTagDetailsInterface.notifySuccess(Constants.expressionAttribute_is_empty_volley_response);
            }
            else {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall, jsonToADD_Or_Update,
                        response -> {

                            String statusCode = null;
                            try {
                                statusCode = response.getString("statusCode");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                            if (statusCode.equals("400")) {
                                JSONObject Json = null;
                                try {
                                    Json = response.getJSONObject("content");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //Log.d(Constants.TAG, "convertingJsonStringintoArray Response: " + JArray);

                                String message = null;
                                try {
                                    message = Json.getString("message");
                                    //message = response.getString("message");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if (message.toUpperCase().equals(Constants.expressionAttribute_is_empty_volley_syntax)) {
                                    callback_GoatEarTagDetailsInterface.notifySuccess(Constants.expressionAttribute_is_empty_volley_response);

                                } else {
                                    callback_GoatEarTagDetailsInterface.notifySuccess(Constants.item_not_Found_volley);

                                }

                            } else if (statusCode.equals("200")) {
                                if (callback_GoatEarTagDetailsInterface != null) {
                                    try {
                                        Update_Local_POJOClass();
                                        //   callback_GoatEarTagDetailsInterface.notifySuccess("success");

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        callback_GoatEarTagDetailsInterface.notifyProcessingError(e);
                                    }
                                }
                            } else {
                                callback_GoatEarTagDetailsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                            }


                        }, error -> {


                    callback_GoatEarTagDetailsInterface.notifyVolleyError(error);

                    error.printStackTrace();
                }) {

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


       }
        catch (Exception e){
            e.printStackTrace();
            callback_GoatEarTagDetailsInterface.notifyProcessingError(e);
        }


    }

    private void Update_Local_POJOClass() {
        try {

        if(jsonToADD_Or_Update.has("loadedweightingrams")){
            if(!jsonToADD_Or_Update.getString("loadedweightingrams").equals("")){
                String weightinGrams_str =jsonToADD_Or_Update.getString("loadedweightingrams");
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                    weightinGrams_str = "0";
                }
                String convertedValue =  ConvertGramsToKilograms(weightinGrams_str);
                if(convertedValue.equals(null) || convertedValue.equals("")){
                    convertedValue = "0";
                }
                Modal_Static_GoatEarTagDetails.loadedweightingrams = String.valueOf(convertedValue);
            }
        }
        if(jsonToADD_Or_Update.has("stockedweightingrams")){
            if(!jsonToADD_Or_Update.getString("stockedweightingrams").equals("")){
                String weightinGrams_str = jsonToADD_Or_Update.getString("stockedweightingrams");
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                    weightinGrams_str = "0";
                }
                String convertedValue =  ConvertGramsToKilograms(weightinGrams_str);
                if(convertedValue.equals(null) || convertedValue.equals("")){
                    convertedValue = "0";
                }
                Modal_Static_GoatEarTagDetails.stockedweightingrams = String.valueOf(convertedValue);
            }
        }
        if(jsonToADD_Or_Update.has("status")){
            if(!jsonToADD_Or_Update.getString("status").equals("")){
                Modal_Static_GoatEarTagDetails.status =  jsonToADD_Or_Update.getString("status");
            }
        }
        if(jsonToADD_Or_Update.has("currentweightingrams")){
            if(!jsonToADD_Or_Update.getString("currentweightingrams").equals("")){

                String weightinGrams_str = jsonToADD_Or_Update.getString("currentweightingrams");
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                    weightinGrams_str = "0";
                }
                String convertedValue =  ConvertGramsToKilograms(weightinGrams_str);
                if(convertedValue.equals(null) || convertedValue.equals("")){
                    convertedValue = "0";
                }
                Modal_Static_GoatEarTagDetails.currentweightingrams = String.valueOf(convertedValue);


            }
        }
        if(jsonToADD_Or_Update.has("gender")){
            if(!jsonToADD_Or_Update.getString("gender").equals("")){
                Modal_Static_GoatEarTagDetails.gender =  jsonToADD_Or_Update.getString("gender");
            }
        }
        if(jsonToADD_Or_Update.has("breedtype")){
            if(!jsonToADD_Or_Update.getString("breedtype").equals("")){
                Modal_Static_GoatEarTagDetails.breedtype =  jsonToADD_Or_Update.getString("breedtype");
            }
        }
        if(jsonToADD_Or_Update.has("suppliername")){
            if(!jsonToADD_Or_Update.getString("suppliername").equals("")){
                Modal_Static_GoatEarTagDetails.suppliername =  jsonToADD_Or_Update.getString("suppliername");
            }
        }
        if(jsonToADD_Or_Update.has("supplierkey")){
            if(!jsonToADD_Or_Update.getString("supplierkey").equals("")){
                Modal_Static_GoatEarTagDetails.supplierkey =  jsonToADD_Or_Update.getString("supplierkey");
            }
        }
        if(jsonToADD_Or_Update.has("description")){
            if(!jsonToADD_Or_Update.getString("description").equals("")){
                Modal_Static_GoatEarTagDetails.description =  jsonToADD_Or_Update.getString("description");
            }
        }
            if(jsonToADD_Or_Update.has("gradename")){
                if(!jsonToADD_Or_Update.getString("gradename").equals("")){
                    Modal_Static_GoatEarTagDetails.gradename =  jsonToADD_Or_Update.getString("gradename");
                }
            }

            if(jsonToADD_Or_Update.has("gradeprice")){
                if(!jsonToADD_Or_Update.getString("gradeprice").equals("")){
                    Modal_Static_GoatEarTagDetails.gradeprice =  jsonToADD_Or_Update.getString("gradeprice");
                }
            }

            if(jsonToADD_Or_Update.has("gradekey")){
                if(!jsonToADD_Or_Update.getString("gradekey").equals("")){
                    Modal_Static_GoatEarTagDetails.gradekey =  jsonToADD_Or_Update.getString("gradekey");
                }
            }


            if(jsonToADD_Or_Update.has("meatyieldweight")){
                if(!jsonToADD_Or_Update.getString("meatyieldweight").equals("")){

                    String weightinGrams_str = jsonToADD_Or_Update.getString("meatyieldweight");
                    weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                    if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                        weightinGrams_str = "0";
                    }
                    String convertedValue =  ConvertGramsToKilograms(weightinGrams_str);
                    if(convertedValue.equals(null) || convertedValue.equals("")){
                        convertedValue = "0";
                    }
                    Modal_Static_GoatEarTagDetails.meatyieldweight = String.valueOf(convertedValue);


                }
            }



            if(jsonToADD_Or_Update.has("partsweight")){
                if(!jsonToADD_Or_Update.getString("partsweight").equals("")){

                    String weightinGrams_str = jsonToADD_Or_Update.getString("partsweight");
                    weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                    if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                        weightinGrams_str = "0";
                    }
                    String convertedValue =  ConvertGramsToKilograms(weightinGrams_str);
                    if(convertedValue.equals(null) || convertedValue.equals("")){
                        convertedValue = "0";
                    }
                    Modal_Static_GoatEarTagDetails.partsweight = String.valueOf(convertedValue);


                }
            }


            if(jsonToADD_Or_Update.has("approxliveweight")){
                if(!jsonToADD_Or_Update.getString("approxliveweight").equals("")){

                    String weightinGrams_str = jsonToADD_Or_Update.getString("approxliveweight");
                    weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                    if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                        weightinGrams_str = "0";
                    }
                    String convertedValue =  ConvertGramsToKilograms(weightinGrams_str);
                    if(convertedValue.equals(null) || convertedValue.equals("")){
                        convertedValue = "0";
                    }
                    Modal_Static_GoatEarTagDetails.approxliveweight = String.valueOf(convertedValue);


                }
            }

            if(jsonToADD_Or_Update.has("price")){
                if(!jsonToADD_Or_Update.getString("price").equals("")){
                    Modal_Static_GoatEarTagDetails.itemPrice =  jsonToADD_Or_Update.getString("price");
                }
            }


          /*  if(jsonToADD_Or_Update.has("totalprice")){
                if(!jsonToADD_Or_Update.getString("totalprice").equals("")){
                    Modal_Static_GoatEarTagDetails.totalPrice_ofItem =  jsonToADD_Or_Update.getString("totalprice");
                }
            }

           */


            callback_GoatEarTagDetailsInterface.notifySuccess(Constants.successResult_volley);

        } catch (JSONException e) {
            callback_GoatEarTagDetailsInterface.notifyProcessingError(e);

            e.printStackTrace();
        }


    }

    private void addEntryInBatchDetails() {


        try{

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApitoCall,jsonToADD_Or_Update,
                    response -> {
                        if (callback_GoatEarTagDetailsInterface != null) {
                            try {
                                //converting jsonSTRING into array
                                //JSONArray JArray = response.getJSONArray("content");
                                String statusCode = response.getString("statusCode");
                                if(statusCode.equals("400")){
                                    callback_GoatEarTagDetailsInterface.notifySuccess(Constants.item_Already_Added_volley);

                                }
                                else if(statusCode.equals("200")){
                                    callback_GoatEarTagDetailsInterface.notifySuccess(Constants.successResult_volley);

                                }
                                else{
                                    callback_GoatEarTagDetailsInterface.notifySuccess(Constants.unknown_API_Result_volley);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback_GoatEarTagDetailsInterface.notifyProcessingError(e);
                            }
                        }
                    }, error -> {


                callback_GoatEarTagDetailsInterface.notifyVolleyError( error);

                error.printStackTrace();
            })
            {

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
            callback_GoatEarTagDetailsInterface.notifyProcessingError(e);
        }


    }

    private JSONObject getJSON_to_Update_FromPOJOClass() {
        JSONObject jsonObject = new JSONObject();
        try {
            if(!Modal_Static_GoatEarTagDetails.getBatchno().toString().equals("") && !Modal_Static_GoatEarTagDetails.getBatchno().toString().equals("null")){
                jsonObject.put("batchno",String.valueOf(Modal_Static_GoatEarTagDetails.getBatchno()));
            }
            if(!Modal_Static_GoatEarTagDetails.getBarcodeno().toString().equals("") && !Modal_Static_GoatEarTagDetails.getBarcodeno().toString().equals("null")){
                jsonObject.put("barcodeno",String.valueOf(Modal_Static_GoatEarTagDetails.getBarcodeno()));
            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_stockedweightingrams().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_stockedweightingrams().toString().equals("null")){
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_stockedweightingrams_boolean()){
                    String weightinGrams_str = Modal_UpdatedGoatEarTagDetails.getUpdated_stockedweightingrams();
                    weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");

                    if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                        weightinGrams_str = "0";
                    }
                    weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                    double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                    jsonObject.put("stockedweightingrams",weightinGrams_double);
                }

            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_status().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_status().toString().equals("null")){
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_status_boolean()){

                    jsonObject.put("status", String.valueOf(Modal_UpdatedGoatEarTagDetails.getUpdated_status()));
                }
            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_supplierkey().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_supplierkey().toString().equals("null")){
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_supplierkey_boolean()){
                    jsonObject.put("supplierkey", String.valueOf(Modal_UpdatedGoatEarTagDetails.getUpdated_supplierkey()));
                }
            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_suppliername().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_suppliername().toString().equals("null")){
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_suppliername_boolean()){

                    jsonObject.put("suppliername", String.valueOf(Modal_UpdatedGoatEarTagDetails.getUpdated_suppliername()));
                }
            }

            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_currentweightingrams().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_currentweightingrams().toString().equals("null")){
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_currentweightingrams_boolean()){
                    String weightinGrams_str = Modal_UpdatedGoatEarTagDetails.getUpdated_currentweightingrams();
                    weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                    if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                        weightinGrams_str = "0";
                    }


                    weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);

                    double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                    jsonObject.put("currentweightingrams", weightinGrams_double);
                }
            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_gender().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_gender().toString().equals("null")){
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_gender_boolean()){
                    jsonObject.put("gender", String.valueOf(Modal_UpdatedGoatEarTagDetails.getUpdated_gender()));
                }
                }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_breedtype().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_breedtype().toString().equals("null")){
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_breedtype_boolean()){

                    jsonObject.put("breedtype", String.valueOf(Modal_UpdatedGoatEarTagDetails.getUpdated_breedtype()));
                }
            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_loadedweightingrams().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_loadedweightingrams().toString().equals("null")){
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_loadedweightingrams_boolean()){

                    String weightinGrams_str = Modal_UpdatedGoatEarTagDetails.getUpdated_loadedweightingrams();
                    weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                    if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                        weightinGrams_str = "0";
                    }


                    weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);

                    double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                    jsonObject.put("loadedweightingrams", weightinGrams_double);
                }
            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_description().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_description().toString().equals("null")) {
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_description_boolean()){

                    jsonObject.put("description", String.valueOf(Modal_UpdatedGoatEarTagDetails.getUpdated_description()));
                }
            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_gradename().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_gradename().toString().equals("null")) {
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_gradename_boolean()){

                    jsonObject.put("gradename", ConvertFirstLetter_to_Capital(String.valueOf(Modal_UpdatedGoatEarTagDetails.getUpdated_gradename())));
                }
            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_gradeprice().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_gradeprice().toString().equals("null")) {
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_gradeprice_boolean()){

                    jsonObject.put("gradeprice", String.valueOf(Modal_UpdatedGoatEarTagDetails.getUpdated_gradeprice()));
                }
            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_gradeKey().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_gradeKey().toString().equals("null")) {
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_gradekey_boolean()){

                    jsonObject.put("gradekey", String.valueOf(Modal_UpdatedGoatEarTagDetails.getUpdated_gradeKey()));
                }
            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_deliverycenterkey().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_deliverycenterkey().toString().equals("null")) {
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_deliverycenterkey_boolean()){

                    jsonObject.put("deliverycentrekey", String.valueOf(Modal_UpdatedGoatEarTagDetails.getUpdated_deliverycenterkey()));
                }
            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_deliverycentername().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_deliverycentername().toString().equals("null")) {
                if(Modal_UpdatedGoatEarTagDetails.isUpdated_deliverycentername_boolean()){

                    jsonObject.put("deliverycentrename", String.valueOf(Modal_UpdatedGoatEarTagDetails.getUpdated_deliverycentername()));
                }
            }

            /*if(!Modal_UpdatedGoatEarTagDetails.getUpdated_totalPrice_ofItem().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_totalPrice_ofItem().toString().equals("null")){
                jsonObject.put("totalprice",String.valueOf(Modal_UpdatedGoatEarTagDetails.getUpdated_totalPrice_ofItem()));
            }

             */

            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_Price().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_Price().toString().equals("null")){
                jsonObject.put("price",String.valueOf(Modal_UpdatedGoatEarTagDetails.getUpdated_Price()));
            }
            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_discount().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_discount().toString().equals("null")){
                jsonObject.put("discount",String.valueOf(Modal_UpdatedGoatEarTagDetails.getUpdated_discount()));
            }


            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_meatyieldweight().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_meatyieldweight().toString().equals("null")){
                String weightinGrams_str = Modal_UpdatedGoatEarTagDetails.getUpdated_meatyieldweight();
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                    weightinGrams_str = "0";
                }


                weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                jsonObject.put("meatyieldweight",weightinGrams_double);






            }


            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_partsweight().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_partsweight().toString().equals("null")){
                String weightinGrams_str = Modal_UpdatedGoatEarTagDetails.getUpdated_partsweight();
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                    weightinGrams_str = "0";
                }


                weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                jsonObject.put("partsweight",weightinGrams_double);



            }





            if(!Modal_UpdatedGoatEarTagDetails.getUpdated_approxliveweight().toString().equals("") && !Modal_UpdatedGoatEarTagDetails.getUpdated_approxliveweight().toString().equals("null")){
                String weightinGrams_str = Modal_UpdatedGoatEarTagDetails.getUpdated_approxliveweight();
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                    weightinGrams_str = "0";
                }


                weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                jsonObject.put("approxliveweight",weightinGrams_double);
            }





        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private JSONObject getJSON_to_ADD_FromPOJOClass() {
        JSONObject jsonObject = new JSONObject();
        try {
            if(!Modal_Static_GoatEarTagDetails.getBatchno().toString().equals("") && !Modal_Static_GoatEarTagDetails.getBatchno().toString().equals("null")){
                jsonObject.put("batchno",String.valueOf(Modal_Static_GoatEarTagDetails.getBatchno()));
            }
            if(!Modal_Static_GoatEarTagDetails.getBarcodeno().toString().equals("") && !Modal_Static_GoatEarTagDetails.getBarcodeno().toString().equals("null")){
                jsonObject.put("barcodeno",String.valueOf(Modal_Static_GoatEarTagDetails.getBarcodeno()));
            }
            if(!Modal_Static_GoatEarTagDetails.getStockedweightingrams().toString().equals("") && !Modal_Static_GoatEarTagDetails.getStockedweightingrams().toString().equals("null")){
                String weightinGrams_str = Modal_Static_GoatEarTagDetails.getStockedweightingrams();
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                    weightinGrams_str = "0";
                }


                weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                jsonObject.put("stockedweightingrams",weightinGrams_double);
            }
            if(!Modal_Static_GoatEarTagDetails.getStatus().toString().equals("") && !Modal_Static_GoatEarTagDetails.getStatus().toString().equals("null")){
                jsonObject.put("status",String.valueOf(Modal_Static_GoatEarTagDetails.getStatus()));
            }
            if(!Modal_Static_GoatEarTagDetails.getSupplierkey().toString().equals("") && !Modal_Static_GoatEarTagDetails.getSupplierkey().toString().equals("null")){
                jsonObject.put("supplierkey",String.valueOf(Modal_Static_GoatEarTagDetails.getSupplierkey()));
            }
            if(!Modal_Static_GoatEarTagDetails.getSuppliername().toString().equals("") && !Modal_Static_GoatEarTagDetails.getSuppliername().toString().equals("null")){
                jsonObject.put("suppliername",String.valueOf(Modal_Static_GoatEarTagDetails.getSuppliername()));
            }
            if(!Modal_Static_GoatEarTagDetails.getItemaddeddate().toString().equals("") && !Modal_Static_GoatEarTagDetails.getItemaddeddate().toString().equals("null")){
                jsonObject.put("itemaddeddate",String.valueOf(Modal_Static_GoatEarTagDetails.getItemaddeddate()));
            }
            if(!Modal_Static_GoatEarTagDetails.getCurrentweightingrams().toString().equals("") && !Modal_Static_GoatEarTagDetails.getCurrentweightingrams().toString().equals("null")){
                String weightinGrams_str = Modal_Static_GoatEarTagDetails.getCurrentweightingrams();
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                    weightinGrams_str = "0";
                }

                weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                double weightinGrams_double = Double.parseDouble(weightinGrams_str);


                jsonObject.put("currentweightingrams",weightinGrams_double);
            }
            if(!Modal_Static_GoatEarTagDetails.getGender().toString().equals("") && !Modal_Static_GoatEarTagDetails.getGender().toString().equals("null")){
                jsonObject.put("gender",String.valueOf(Modal_Static_GoatEarTagDetails.getGender()));
            }
            if(!Modal_Static_GoatEarTagDetails.getBreedtype().toString().equals("") && !Modal_Static_GoatEarTagDetails.getBreedtype().toString().equals("null")){
                jsonObject.put("breedtype",String.valueOf(Modal_Static_GoatEarTagDetails.getBreedtype()));
            }
            if(!Modal_Static_GoatEarTagDetails.getLoadedweightingrams().toString().equals("") && !Modal_Static_GoatEarTagDetails.getLoadedweightingrams().toString().equals("null")){
                String weightinGrams_str = Modal_Static_GoatEarTagDetails.getLoadedweightingrams();
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                    weightinGrams_str = "0";
                }


                weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                jsonObject.put("loadedweightingrams",weightinGrams_double);
            }
            if(!Modal_Static_GoatEarTagDetails.getDescription().toString().equals("") && !Modal_Static_GoatEarTagDetails.getDescription().toString().equals("null")){
                jsonObject.put("description",String.valueOf(Modal_Static_GoatEarTagDetails.getDescription()));
            }

            if(!Modal_Static_GoatEarTagDetails.getDeliverycenterkey().toString().equals("") && !Modal_Static_GoatEarTagDetails.getDeliverycenterkey().toString().equals("null")){
                jsonObject.put("deliverycentrekey",String.valueOf(Modal_Static_GoatEarTagDetails.getDeliverycenterkey()));
            }
            if(!Modal_Static_GoatEarTagDetails.getDeliverycentername().toString().equals("") && !Modal_Static_GoatEarTagDetails.getDeliverycentername().toString().equals("null")){
                jsonObject.put("deliverycentrename",String.valueOf(Modal_Static_GoatEarTagDetails.getDeliverycentername()));
            }

            if(!Modal_Static_GoatEarTagDetails.getGradekey().toString().equals("") && !Modal_Static_GoatEarTagDetails.getGradekey().toString().equals("null")){
                jsonObject.put("gradekey",String.valueOf(Modal_Static_GoatEarTagDetails.getGradekey()));
            }
            if(!Modal_Static_GoatEarTagDetails.getGradeprice().toString().equals("") && !Modal_Static_GoatEarTagDetails.getGradeprice().toString().equals("null")){
                jsonObject.put("gradeprice",String.valueOf(Modal_Static_GoatEarTagDetails.getGradeprice()));
            }
            if(!Modal_Static_GoatEarTagDetails.getGradename().toString().equals("") && !Modal_Static_GoatEarTagDetails.getGradename().toString().equals("null")){
                jsonObject.put("gradename",String.valueOf(Modal_Static_GoatEarTagDetails.getGradename()));
            }

          /*  if(!Modal_Static_GoatEarTagDetails.getTotalPrice_ofItem().toString().equals("") && !Modal_Static_GoatEarTagDetails.getTotalPrice_ofItem().toString().equals("null")){
                jsonObject.put("totalprice",String.valueOf(Modal_Static_GoatEarTagDetails.getTotalPrice_ofItem()));
            }

           */


            if(!Modal_Static_GoatEarTagDetails.getItemPrice().toString().equals("") && !Modal_Static_GoatEarTagDetails.getItemPrice().toString().equals("null")){
                jsonObject.put("price",String.valueOf(Modal_Static_GoatEarTagDetails.getItemPrice()));
            }


            if(!Modal_Static_GoatEarTagDetails.getDiscount().toString().equals("") && !Modal_Static_GoatEarTagDetails.getDiscount().toString().equals("null")){
                jsonObject.put("discount",String.valueOf(Modal_Static_GoatEarTagDetails.getDiscount()));
            }


            if(!Modal_Static_GoatEarTagDetails.getMeatyieldweight().toString().equals("") && !Modal_Static_GoatEarTagDetails.getMeatyieldweight().toString().equals("null")){
                String weightinGrams_str = Modal_Static_GoatEarTagDetails.getMeatyieldweight();
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                    weightinGrams_str = "0";
                }


                weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                jsonObject.put("meatyieldweight",weightinGrams_double);
            }


            if(!Modal_Static_GoatEarTagDetails.getPartsweight().toString().equals("") && !Modal_Static_GoatEarTagDetails.getPartsweight().toString().equals("null")){
                String weightinGrams_str = Modal_Static_GoatEarTagDetails.getPartsweight();
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                    weightinGrams_str = "0";
                }


                weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                jsonObject.put("partsweight",weightinGrams_double);
            }




            if(!Modal_Static_GoatEarTagDetails.getApproxliveweight().toString().equals("") && !Modal_Static_GoatEarTagDetails.getApproxliveweight().toString().equals("null")){
                String weightinGrams_str = Modal_Static_GoatEarTagDetails.getApproxliveweight();
                weightinGrams_str = weightinGrams_str.replaceAll("[^\\d.]", "");
                if(weightinGrams_str.equals("") || weightinGrams_str.equals(null)){
                    weightinGrams_str = "0";
                }


                weightinGrams_str = ConvertKilogramstoGrams(weightinGrams_str);
                double weightinGrams_double = Double.parseDouble(weightinGrams_str);

                jsonObject.put("approxliveweight",weightinGrams_double);
            }





        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
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
