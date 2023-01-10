package com.tmc.tmcb2bpartnerapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.BillingScreen;
import com.tmc.tmcb2bpartnerapp.activity.ChangeGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.activity.PlacedOrderDetailsScreen;
import com.tmc.tmcb2bpartnerapp.fragment.DeliveryCentre_PlaceOrderScreen_Fragment;
import com.tmc.tmcb2bpartnerapp.fragment.OrderSummary_fragement;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_POJOClassForFinalSalesHashmap;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;

public class Adapter_GradeWiseTotal_BillingScreen extends ArrayAdapter<Modal_B2BGoatGradeDetails> {

    Context mContext;
    ArrayList<Modal_B2BGoatGradeDetails> goatgradelist;
    BillingScreen billingScreen;
    String calledFrom = "";
    JSONObject gradeWiseJSONObject = new JSONObject();
    boolean isConstructerCalled = false;
    boolean isnotifyDataSetCalled = false;
    DeliveryCentre_PlaceOrderScreen_Fragment deliveryCentre_placeOrderScreen_fragment;
    PlacedOrderDetailsScreen placedOrderDetailsScreen;
    OrderSummary_fragement orderSummary_fragement;

    HashMap<String, Modal_POJOClassForFinalSalesHashmap> earTagDetails_jsonFinalSalesHashMap = new HashMap<>();
    public Adapter_GradeWiseTotal_BillingScreen(Context mContext, ArrayList<Modal_B2BGoatGradeDetails> goatgradelistt, JSONObject gradeWiseJSONObjectt, BillingScreen billingScreenn, String calledFrom) {
        super(mContext, R.layout.adapter_batch_item_list, goatgradelistt);
        this.mContext=mContext;
        this.goatgradelist =goatgradelistt;
        this.billingScreen = billingScreenn;
        this.calledFrom = calledFrom;
        this.gradeWiseJSONObject = gradeWiseJSONObjectt;
        isnotifyDataSetCalled = false;

        isConstructerCalled = true;
    }


    /*public Adapter_GradeWiseTotal_BillingScreen(Context mContext, ArrayList<Modal_B2BGoatGradeDetails> selected_gradeDetailss_arrayList, JSONObject gradeWise_count_weightJSONOBJECT, String s) {
        super(mContext, R.layout.adapter_batch_item_list, selected_gradeDetailss_arrayList);
        this.mContext=mContext;
        this.goatgradelist =selected_gradeDetailss_arrayList;
        this.calledFrom = calledFrom;
        this.gradeWiseJSONObject = gradeWise_count_weightJSONOBJECT;

    }

     */

    public Adapter_GradeWiseTotal_BillingScreen(BillingScreen mContext, ArrayList<Modal_B2BGoatGradeDetails> selected_gradeDetailss_arrayList, HashMap<String, Modal_POJOClassForFinalSalesHashmap> earTagDetails_jsonFinalSalesHashMapp, BillingScreen billingScreenn, String calledFrom) {
        super(mContext, R.layout.adapter_batch_item_list, selected_gradeDetailss_arrayList);
        this.mContext=mContext;
        this.goatgradelist =selected_gradeDetailss_arrayList;
        this.calledFrom = calledFrom;
        this.earTagDetails_jsonFinalSalesHashMap = earTagDetails_jsonFinalSalesHashMapp;
        isConstructerCalled = true;
        isnotifyDataSetCalled = false;

        try {
            if(goatgradelist.size()>0) {
                Collections.sort(goatgradelist, new Comparator<Modal_B2BGoatGradeDetails>() {
                    public int compare(final Modal_B2BGoatGradeDetails object1, final Modal_B2BGoatGradeDetails object2) {
                        return object2.getName().compareTo(object1.getName());
                    }
                });
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        }

    public Adapter_GradeWiseTotal_BillingScreen(Context mContext, ArrayList<Modal_B2BGoatGradeDetails> selected_gradeDetailss_arrayList, HashMap<String, Modal_POJOClassForFinalSalesHashmap> earTagDetails_jsonFinalSalesHashMapp, String calledFrom,OrderSummary_fragement orderSummary_fragementt) {
        super(mContext, R.layout.adapter_batch_item_list, selected_gradeDetailss_arrayList);
        this.mContext=mContext;
        this.goatgradelist =selected_gradeDetailss_arrayList;
        this.calledFrom = calledFrom;
        this.earTagDetails_jsonFinalSalesHashMap = earTagDetails_jsonFinalSalesHashMapp;
        this.orderSummary_fragement =  orderSummary_fragementt;
        isConstructerCalled = true;
        isnotifyDataSetCalled = false;

        try {
            if(goatgradelist.size()>0) {
                Collections.sort(goatgradelist, new Comparator<Modal_B2BGoatGradeDetails>() {
                    public int compare(final Modal_B2BGoatGradeDetails object1, final Modal_B2BGoatGradeDetails object2) {
                        return object2.getName().compareTo(object1.getName());
                    }
                });
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public Adapter_GradeWiseTotal_BillingScreen(Context mContext, ArrayList<Modal_B2BGoatGradeDetails> selected_gradeDetailss_arrayList, HashMap<String, Modal_POJOClassForFinalSalesHashmap> earTagDetails_jsonFinalSalesHashMapp, DeliveryCentre_PlaceOrderScreen_Fragment deliveryCentre_placeOrderScreen_fragmentt, String s) {
        super(mContext, R.layout.adapter_batch_item_list, selected_gradeDetailss_arrayList);

        this.mContext=mContext;
        this.goatgradelist =selected_gradeDetailss_arrayList;
        this.calledFrom = calledFrom;
        this.deliveryCentre_placeOrderScreen_fragment = deliveryCentre_placeOrderScreen_fragmentt;
        this.earTagDetails_jsonFinalSalesHashMap = earTagDetails_jsonFinalSalesHashMapp;
        isConstructerCalled = true;
        isnotifyDataSetCalled = false;

        try {
            if(goatgradelist.size()>0) {
                Collections.sort(goatgradelist, new Comparator<Modal_B2BGoatGradeDetails>() {
                    public int compare(final Modal_B2BGoatGradeDetails object1, final Modal_B2BGoatGradeDetails object2) {
                        return object2.getName().compareTo(object1.getName());
                    }
                });
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public Adapter_GradeWiseTotal_BillingScreen(Context mContext, ArrayList<Modal_B2BGoatGradeDetails> selected_gradeDetailss_arrayList, HashMap<String, Modal_POJOClassForFinalSalesHashmap> earTagDetails_jsonFinalSalesHashMapp, PlacedOrderDetailsScreen placedOrderDetailsScreenn) {
        super(mContext, R.layout.adapter_batch_item_list, selected_gradeDetailss_arrayList);
        this.mContext=mContext;
        this.goatgradelist =selected_gradeDetailss_arrayList;
        this.calledFrom = calledFrom;
        this.placedOrderDetailsScreen = placedOrderDetailsScreenn;
        this.earTagDetails_jsonFinalSalesHashMap = earTagDetails_jsonFinalSalesHashMapp;
        isConstructerCalled = true;
        isnotifyDataSetCalled = false;

        try {
            if(goatgradelist.size()>0) {
                Collections.sort(goatgradelist, new Comparator<Modal_B2BGoatGradeDetails>() {
                    public int compare(final Modal_B2BGoatGradeDetails object1, final Modal_B2BGoatGradeDetails object2) {
                        return object2.getName().compareTo(object1.getName());
                    }
                });
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public Modal_B2BGoatGradeDetails getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable Modal_B2BGoatGradeDetails item) {
        return super.getPosition(item);
    }

    public View getView(final int pos, View view, ViewGroup v) {
        @SuppressLint("ViewHolder")  View listViewItem = null;
        try{
            BaseActivity.baseActivity.getDeviceName();
            for(int i =0 ; i<500; i++){
                if(i==499){
                    if(BaseActivity.isDeviceIsMobilePhone){
                        listViewItem = LayoutInflater.from(mContext).inflate(R.layout.gradewise_total_billingscreen_listitem, (ViewGroup) view, false);

                    }
                    else{
                        listViewItem = LayoutInflater.from(mContext).inflate(R.layout.pos_gradewise_total_billingscreen_listitem, (ViewGroup) view, false);

                    }
                }

            }
        }
        catch (Exception e){
            listViewItem = LayoutInflater.from(mContext).inflate(R.layout.gradewise_total_billingscreen_listitem, (ViewGroup) view, false);


            e.printStackTrace();
        }


        final TextView gradename_textview = listViewItem.findViewById(R.id.gradename_textview);
        final TextView male_Qty_textview = listViewItem.findViewById(R.id.male_Qty_textview);
        final TextView male_Weight_textview = listViewItem.findViewById(R.id.male_Weight_textview);
        final TextView maleprice_textview = listViewItem.findViewById(R.id.maleprice_textview);
        final TextView female_Qty_textview = listViewItem.findViewById(R.id.female_Qty_textview);
        final TextView female_Weight_textview = listViewItem.findViewById(R.id.female_Weight_textview);
        final TextView female_price_textview = listViewItem.findViewById(R.id.female_price_textview);
        final TextView gradeprice_textview = listViewItem.findViewById(R.id.gradeprice_textview);
        final TextView femalewithBaby_Price_textview = listViewItem.findViewById(R.id.femalewithBaby_Price_textview);
        final TextView femalewithBaby_Weight_textview = listViewItem.findViewById(R.id.femalewithBaby_Weight_textview);


        LinearLayout parentLayout = listViewItem.findViewById(R.id.parentLayout);

         LinearLayout male_layout = listViewItem.findViewById(R.id.male_layout);
         LinearLayout female_layout = listViewItem.findViewById(R.id.female_layout);
         LinearLayout femaleBaby_layout = listViewItem.findViewById(R.id.femaleBaby_layout);
         if(!isConstructerCalled){
             if(!isnotifyDataSetCalled) {
                 if (pos == 0) {
                     isnotifyDataSetCalled = true;
                     notifyDataSetChanged();
                 }
             }
             else{
                 if(pos !=0){
                     isnotifyDataSetCalled = false;
                 }
             }
         }
         else{
             if(pos !=0){
                 isConstructerCalled = false;
                 isnotifyDataSetCalled = false;
             }
             else{
                 isConstructerCalled = false;
             }
         }




            if(billingScreen!=null) {
                try {

                    if (earTagDetails_jsonFinalSalesHashMap.size() > 0) {
                        try {
                            billingScreen.gradewisetotalCount_listview.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(mContext, "Error from Adapter o: " + String.valueOf(e), Toast.LENGTH_SHORT).show();

                        }
                        try {
                            Modal_B2BGoatGradeDetails modal_b2BGoatGradeDetails = goatgradelist.get(pos);

                            if (earTagDetails_jsonFinalSalesHashMap.containsKey(modal_b2BGoatGradeDetails.getKey())) {
                                gradename_textview.setText(modal_b2BGoatGradeDetails.getName());
                                gradeprice_textview.setText("Rs. " + modal_b2BGoatGradeDetails.getPrice() + " / per Kg");


                                Modal_POJOClassForFinalSalesHashmap modal_pojoClassForFinalSalesHashmap = earTagDetails_jsonFinalSalesHashMap.get(modal_b2BGoatGradeDetails.getKey());
                                double weight = 0, price = 0, maleweight = 0, femaleweight = 0, maleprice = 0, femaleprice = 0;
                                int maleCount = 0, femaleCount = 0, totalquantity = 0;


                                weight = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalweight();

                                totalquantity = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalqty();
                                price = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalprice();

                                maleprice = modal_pojoClassForFinalSalesHashmap.getMaleprice();

                                maleCount = modal_pojoClassForFinalSalesHashmap.getMaleqty();

                                maleweight = modal_pojoClassForFinalSalesHashmap.getTotalmaleweight();
                                femaleprice = modal_pojoClassForFinalSalesHashmap.getFemaleprice();
                                femaleCount = modal_pojoClassForFinalSalesHashmap.getFemaleqty();
                                femaleweight = modal_pojoClassForFinalSalesHashmap.getTotalfemaleweight();


                                try {
                                    if (maleCount > 0) {
                                        male_layout.setVisibility(View.VISIBLE);
                                    } else {
                                        male_layout.setVisibility(View.GONE);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    if (femaleCount > 0) {
                                        female_layout.setVisibility(View.VISIBLE);
                                    } else {
                                        female_layout.setVisibility(View.GONE);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                male_Qty_textview.setText(String.valueOf(String.valueOf(maleCount)));
                                male_Weight_textview.setText(String.valueOf(maleweight) + " Kg");
                                maleprice_textview.setText("Rs . " + String.valueOf(maleprice));

                                female_Qty_textview.setText(String.valueOf(String.valueOf(femaleCount)));
                                female_Weight_textview.setText(String.valueOf(femaleweight) + " Kg");
                                female_price_textview.setText("Rs . " + String.valueOf(femaleprice));


                            }



             /*
                 if (gradeWiseJSONObject.has(modal_b2BGoatGradeDetails.getKey())) {
                     gradename_textview.setText(modal_b2BGoatGradeDetails.getName());
                     gradeprice_textview.setText("Rs. " + modal_b2BGoatGradeDetails.getPrice() + " / per Kg");
                     try {
                         JSONObject jsonObject = gradeWiseJSONObject.getJSONObject(modal_b2BGoatGradeDetails.getKey());


                         String maleQty = "", femaleQty = "", femaleWithBabyQty = "";
                         int maleQty_double = 0, femaleQty_double = 0, femaleWithBabyQty_double = 0;
                         if (jsonObject.has("maleQty")) {

                             maleQty = String.valueOf(jsonObject.getString("maleQty"));
                             try {
                                 maleQty = maleQty.replaceAll("[^\\d.]", "");
                                 if (maleQty.equals("") || String.valueOf(maleQty).toUpperCase().equals("NULL")) {
                                     maleQty = "0";
                                 }
                                 maleQty_double = Integer.parseInt((maleQty));


                             } catch (Exception e) {
                                 e.printStackTrace();
                             }

                             try {
                                 if (maleQty_double > 0) {
                                     male_layout.setVisibility(View.VISIBLE);
                                 } else {
                                     male_layout.setVisibility(View.GONE);
                                 }
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }
                         } else {
                             male_layout.setVisibility(View.GONE);
                         }
                         if (jsonObject.has("femaleQty")) {
                             femaleQty = String.valueOf(jsonObject.getString("femaleQty"));
                             try {
                                 femaleQty = femaleQty.replaceAll("[^\\d.]", "");
                                 if (femaleQty.equals("") || String.valueOf(femaleQty).toUpperCase().equals("NULL")) {
                                     femaleQty = "0";
                                 }
                                 femaleQty_double = Integer.parseInt(femaleQty);

                             } catch (Exception e) {
                                 e.printStackTrace();
                             }

                             try {
                                 if (femaleQty_double > 0) {
                                     female_layout.setVisibility(View.VISIBLE);
                                 } else {
                                     female_layout.setVisibility(View.GONE);
                                 }
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }
                         } else {
                             female_layout.setVisibility(View.GONE);
                         }
                         if (jsonObject.has("femalewithbabyQty")) {
                             femaleWithBabyQty = String.valueOf(jsonObject.getString("femalewithbabyQty"));
                             try {
                                 femaleWithBabyQty = femaleWithBabyQty.replaceAll("[^\\d.]", "");
                                 if (femaleWithBabyQty.equals("") || String.valueOf(femaleWithBabyQty).toUpperCase().equals("NULL")) {
                                     femaleWithBabyQty = "0";
                                 }
                                 femaleWithBabyQty_double = Integer.parseInt(femaleWithBabyQty);

                             } catch (Exception e) {
                                 e.printStackTrace();
                             }

                             try {
                                 if (femaleWithBabyQty_double > 0) {
                                     femaleBaby_layout.setVisibility(View.VISIBLE);
                                 } else {
                                     femaleBaby_layout.setVisibility(View.GONE);
                                 }
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }


                         } else {
                             femaleBaby_layout.setVisibility(View.GONE);
                         }


                         if (jsonObject.has("maleQty")) {
                             male_Qty_textview.setText(String.valueOf(String.valueOf(maleQty_double)));
                             male_Weight_textview.setText(String.valueOf(jsonObject.get("maleWeight")) + " Kg");
                             maleprice_textview.setText("Rs . " + String.valueOf(jsonObject.get("maleprice")));
                         }

                         if (jsonObject.has("femaleQty")) {
                             female_Qty_textview.setText(String.valueOf(String.valueOf(femaleQty_double)));
                             female_Weight_textview.setText(String.valueOf(jsonObject.get("femaleWeight")) + " Kg");
                             female_price_textview.setText("Rs . " + String.valueOf(jsonObject.get("femaleprice")));
                         }


                     } catch (Exception e) {
                         Log.i("INIT", "call_and_init_B2BCartItemDetailsService : error on  adapter " + String.valueOf(e));

                         e.printStackTrace();
                     }
                 }


              */
                        } catch (Exception ee) {
                            Toast.makeText(mContext, "Error from Adapter 1: " + String.valueOf(ee), Toast.LENGTH_SHORT).show();

                            Log.i("INIT", "call_and_init_B2BCartItemDetailsService : 2nd error on  adapter " + String.valueOf(ee));

                            ee.printStackTrace();
                        }
                    } else {
                        try {
                            billingScreen.gradewisetotalCount_listview.setVisibility(View.GONE);
                        } catch (Exception e) {
                            Toast.makeText(mContext, "Error from Adapter 2: " + String.valueOf(e), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                        parentLayout.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    Toast.makeText(mContext, "Error from Adapter : " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }

            }
            else if(deliveryCentre_placeOrderScreen_fragment!=null){
                try {

                    if (earTagDetails_jsonFinalSalesHashMap.size() > 0) {
                        try {
                            deliveryCentre_placeOrderScreen_fragment.gradewisetotalCount_listview.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(mContext, "Error from Adapter o: " + String.valueOf(e), Toast.LENGTH_SHORT).show();

                        }
                        try {
                            Modal_B2BGoatGradeDetails modal_b2BGoatGradeDetails = goatgradelist.get(pos);

                            if (earTagDetails_jsonFinalSalesHashMap.containsKey(modal_b2BGoatGradeDetails.getKey())) {
                                gradename_textview.setText(modal_b2BGoatGradeDetails.getName());
                                gradeprice_textview.setText("Rs. " + modal_b2BGoatGradeDetails.getPrice() + " / per Kg");


                                Modal_POJOClassForFinalSalesHashmap modal_pojoClassForFinalSalesHashmap = earTagDetails_jsonFinalSalesHashMap.get(modal_b2BGoatGradeDetails.getKey());
                                double weight = 0, price = 0, maleweight = 0, femaleweight = 0, maleprice = 0, femaleprice = 0;
                                int maleCount = 0, femaleCount = 0, totalquantity = 0;


                                weight = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalweight();

                                totalquantity = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalqty();
                                price = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalprice();

                                maleprice = modal_pojoClassForFinalSalesHashmap.getMaleprice();

                                maleCount = modal_pojoClassForFinalSalesHashmap.getMaleqty();

                                maleweight = modal_pojoClassForFinalSalesHashmap.getTotalmaleweight();
                                femaleprice = modal_pojoClassForFinalSalesHashmap.getFemaleprice();
                                femaleCount = modal_pojoClassForFinalSalesHashmap.getFemaleqty();
                                femaleweight = modal_pojoClassForFinalSalesHashmap.getTotalfemaleweight();


                                try {
                                    if (maleCount > 0) {
                                        male_layout.setVisibility(View.VISIBLE);
                                    } else {
                                        male_layout.setVisibility(View.GONE);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    if (femaleCount > 0) {
                                        female_layout.setVisibility(View.VISIBLE);
                                    } else {
                                        female_layout.setVisibility(View.GONE);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                male_Qty_textview.setText(String.valueOf(String.valueOf(maleCount)));
                                male_Weight_textview.setText(String.valueOf(maleweight) + " Kg");
                                maleprice_textview.setText("Rs . " + String.valueOf(maleprice));

                                female_Qty_textview.setText(String.valueOf(String.valueOf(femaleCount)));
                                female_Weight_textview.setText(String.valueOf(femaleweight) + " Kg");
                                female_price_textview.setText("Rs . " + String.valueOf(femaleprice));


                            }



                        } catch (Exception ee) {
                            Toast.makeText(mContext, "Error from Adapter 1: " + String.valueOf(ee), Toast.LENGTH_SHORT).show();

                            Log.i("INIT", "call_and_init_B2BCartItemDetailsService : 2nd error on  adapter " + String.valueOf(ee));

                            ee.printStackTrace();
                        }
                    } else {
                        try {
                            deliveryCentre_placeOrderScreen_fragment.gradewisetotalCount_listview.setVisibility(View.GONE);
                        } catch (Exception e) {
                            Toast.makeText(mContext, "Error from Adapter 2: " + String.valueOf(e), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                        parentLayout.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    Toast.makeText(mContext, "Error from Adapter : " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }
            }
            else if(placedOrderDetailsScreen!=null){
                try {

                    if (earTagDetails_jsonFinalSalesHashMap.size() > 0) {
                        try {
                            placedOrderDetailsScreen.gradewisetotalCount_listview.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(mContext, "Error from Adapter o: " + String.valueOf(e), Toast.LENGTH_SHORT).show();

                        }
                        try {
                            Modal_B2BGoatGradeDetails modal_b2BGoatGradeDetails = goatgradelist.get(pos);

                            if (earTagDetails_jsonFinalSalesHashMap.containsKey(modal_b2BGoatGradeDetails.getKey())) {
                                gradename_textview.setText(modal_b2BGoatGradeDetails.getName());
                                gradeprice_textview.setText("Rs. " + modal_b2BGoatGradeDetails.getPrice() + " / per Kg");


                                Modal_POJOClassForFinalSalesHashmap modal_pojoClassForFinalSalesHashmap = earTagDetails_jsonFinalSalesHashMap.get(modal_b2BGoatGradeDetails.getKey());
                                double weight = 0, price = 0, maleweight = 0, femaleweight = 0, maleprice = 0, femaleprice = 0;
                                int maleCount = 0, femaleCount = 0, totalquantity = 0;


                                weight = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalweight();

                                totalquantity = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalqty();
                                price = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalprice();

                                maleprice = modal_pojoClassForFinalSalesHashmap.getMaleprice();

                                maleCount = modal_pojoClassForFinalSalesHashmap.getMaleqty();

                                maleweight = modal_pojoClassForFinalSalesHashmap.getTotalmaleweight();
                                femaleprice = modal_pojoClassForFinalSalesHashmap.getFemaleprice();
                                femaleCount = modal_pojoClassForFinalSalesHashmap.getFemaleqty();
                                femaleweight = modal_pojoClassForFinalSalesHashmap.getTotalfemaleweight();


                                try {
                                    if (maleCount > 0) {
                                        male_layout.setVisibility(View.VISIBLE);
                                    } else {
                                        male_layout.setVisibility(View.GONE);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    if (femaleCount > 0) {
                                        female_layout.setVisibility(View.VISIBLE);
                                    } else {
                                        female_layout.setVisibility(View.GONE);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                male_Qty_textview.setText(String.valueOf(String.valueOf(maleCount)));
                                male_Weight_textview.setText(String.valueOf(maleweight) + " Kg");
                                maleprice_textview.setText("Rs . " + String.valueOf(maleprice));

                                female_Qty_textview.setText(String.valueOf(String.valueOf(femaleCount)));
                                female_Weight_textview.setText(String.valueOf(femaleweight) + " Kg");
                                female_price_textview.setText("Rs . " + String.valueOf(femaleprice));


                            }



                        } catch (Exception ee) {
                            Toast.makeText(mContext, "Error from Adapter 1: " + String.valueOf(ee), Toast.LENGTH_SHORT).show();

                            Log.i("INIT", "call_and_init_B2BCartItemDetailsService : 2nd error on  adapter " + String.valueOf(ee));

                            ee.printStackTrace();
                        }
                    } else {
                        try {
                            placedOrderDetailsScreen.gradewisetotalCount_listview.setVisibility(View.GONE);
                        } catch (Exception e) {
                            Toast.makeText(mContext, "Error from Adapter 2: " + String.valueOf(e), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                        parentLayout.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    Toast.makeText(mContext, "Error from Adapter : " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }
            }

            else {
            try {

                if (earTagDetails_jsonFinalSalesHashMap.size() > 0) {
                    try {
                        orderSummary_fragement.gradewisetotalCount_listview.setVisibility(View.VISIBLE);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, "Error from Adapter o: " + String.valueOf(e), Toast.LENGTH_SHORT).show();

                    }
                    try {
                        Modal_B2BGoatGradeDetails modal_b2BGoatGradeDetails = goatgradelist.get(pos);

                        if (earTagDetails_jsonFinalSalesHashMap.containsKey(modal_b2BGoatGradeDetails.getKey())) {
                            gradename_textview.setText(modal_b2BGoatGradeDetails.getName());
                            gradeprice_textview.setText("Rs. " + modal_b2BGoatGradeDetails.getPrice() + " / per Kg");


                            Modal_POJOClassForFinalSalesHashmap modal_pojoClassForFinalSalesHashmap = earTagDetails_jsonFinalSalesHashMap.get(modal_b2BGoatGradeDetails.getKey());
                            double weight = 0, price = 0, maleweight = 0, femaleweight = 0, maleprice = 0, femaleprice = 0;
                            int maleCount = 0, femaleCount = 0, totalquantity = 0;


                            weight = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalweight();

                            totalquantity = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalqty();
                            price = Objects.requireNonNull(modal_pojoClassForFinalSalesHashmap).getTotalprice();

                            maleprice = modal_pojoClassForFinalSalesHashmap.getMaleprice();

                            maleCount = modal_pojoClassForFinalSalesHashmap.getMaleqty();

                            maleweight = modal_pojoClassForFinalSalesHashmap.getTotalmaleweight();
                            femaleprice = modal_pojoClassForFinalSalesHashmap.getFemaleprice();
                            femaleCount = modal_pojoClassForFinalSalesHashmap.getFemaleqty();
                            femaleweight = modal_pojoClassForFinalSalesHashmap.getTotalfemaleweight();


                            try {
                                if (maleCount > 0) {
                                    male_layout.setVisibility(View.VISIBLE);
                                } else {
                                    male_layout.setVisibility(View.GONE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                if (femaleCount > 0) {
                                    female_layout.setVisibility(View.VISIBLE);
                                } else {
                                    female_layout.setVisibility(View.GONE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            male_Qty_textview.setText(String.valueOf(String.valueOf(maleCount)));
                            male_Weight_textview.setText(String.valueOf(maleweight) + " Kg");
                            maleprice_textview.setText("Rs . " + String.valueOf(maleprice));

                            female_Qty_textview.setText(String.valueOf(String.valueOf(femaleCount)));
                            female_Weight_textview.setText(String.valueOf(femaleweight) + " Kg");
                            female_price_textview.setText("Rs . " + String.valueOf(femaleprice));


                        }



             /*
                 if (gradeWiseJSONObject.has(modal_b2BGoatGradeDetails.getKey())) {
                     gradename_textview.setText(modal_b2BGoatGradeDetails.getName());
                     gradeprice_textview.setText("Rs. " + modal_b2BGoatGradeDetails.getPrice() + " / per Kg");
                     try {
                         JSONObject jsonObject = gradeWiseJSONObject.getJSONObject(modal_b2BGoatGradeDetails.getKey());


                         String maleQty = "", femaleQty = "", femaleWithBabyQty = "";
                         int maleQty_double = 0, femaleQty_double = 0, femaleWithBabyQty_double = 0;
                         if (jsonObject.has("maleQty")) {

                             maleQty = String.valueOf(jsonObject.getString("maleQty"));
                             try {
                                 maleQty = maleQty.replaceAll("[^\\d.]", "");
                                 if (maleQty.equals("") || String.valueOf(maleQty).toUpperCase().equals("NULL")) {
                                     maleQty = "0";
                                 }
                                 maleQty_double = Integer.parseInt((maleQty));


                             } catch (Exception e) {
                                 e.printStackTrace();
                             }

                             try {
                                 if (maleQty_double > 0) {
                                     male_layout.setVisibility(View.VISIBLE);
                                 } else {
                                     male_layout.setVisibility(View.GONE);
                                 }
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }
                         } else {
                             male_layout.setVisibility(View.GONE);
                         }
                         if (jsonObject.has("femaleQty")) {
                             femaleQty = String.valueOf(jsonObject.getString("femaleQty"));
                             try {
                                 femaleQty = femaleQty.replaceAll("[^\\d.]", "");
                                 if (femaleQty.equals("") || String.valueOf(femaleQty).toUpperCase().equals("NULL")) {
                                     femaleQty = "0";
                                 }
                                 femaleQty_double = Integer.parseInt(femaleQty);

                             } catch (Exception e) {
                                 e.printStackTrace();
                             }

                             try {
                                 if (femaleQty_double > 0) {
                                     female_layout.setVisibility(View.VISIBLE);
                                 } else {
                                     female_layout.setVisibility(View.GONE);
                                 }
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }
                         } else {
                             female_layout.setVisibility(View.GONE);
                         }
                         if (jsonObject.has("femalewithbabyQty")) {
                             femaleWithBabyQty = String.valueOf(jsonObject.getString("femalewithbabyQty"));
                             try {
                                 femaleWithBabyQty = femaleWithBabyQty.replaceAll("[^\\d.]", "");
                                 if (femaleWithBabyQty.equals("") || String.valueOf(femaleWithBabyQty).toUpperCase().equals("NULL")) {
                                     femaleWithBabyQty = "0";
                                 }
                                 femaleWithBabyQty_double = Integer.parseInt(femaleWithBabyQty);

                             } catch (Exception e) {
                                 e.printStackTrace();
                             }

                             try {
                                 if (femaleWithBabyQty_double > 0) {
                                     femaleBaby_layout.setVisibility(View.VISIBLE);
                                 } else {
                                     femaleBaby_layout.setVisibility(View.GONE);
                                 }
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }


                         } else {
                             femaleBaby_layout.setVisibility(View.GONE);
                         }


                         if (jsonObject.has("maleQty")) {
                             male_Qty_textview.setText(String.valueOf(String.valueOf(maleQty_double)));
                             male_Weight_textview.setText(String.valueOf(jsonObject.get("maleWeight")) + " Kg");
                             maleprice_textview.setText("Rs . " + String.valueOf(jsonObject.get("maleprice")));
                         }

                         if (jsonObject.has("femaleQty")) {
                             female_Qty_textview.setText(String.valueOf(String.valueOf(femaleQty_double)));
                             female_Weight_textview.setText(String.valueOf(jsonObject.get("femaleWeight")) + " Kg");
                             female_price_textview.setText("Rs . " + String.valueOf(jsonObject.get("femaleprice")));
                         }


                     } catch (Exception e) {
                         Log.i("INIT", "call_and_init_B2BCartItemDetailsService : error on  adapter " + String.valueOf(e));

                         e.printStackTrace();
                     }
                 }


              */
                    } catch (Exception ee) {
                        Toast.makeText(mContext, "Error from Adapter 1: " + String.valueOf(ee), Toast.LENGTH_SHORT).show();

                        Log.i("INIT", "call_and_init_B2BCartItemDetailsService : 2nd error on  adapter " + String.valueOf(ee));

                        ee.printStackTrace();
                    }
                } else {
                    try {
                        orderSummary_fragement.gradewisetotalCount_listview.setVisibility(View.GONE);
                    } catch (Exception e) {
                        Toast.makeText(mContext, "Error from Adapter 2: " + String.valueOf(e), Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    }
                    parentLayout.setVisibility(View.GONE);
                }

            } catch (Exception e) {
                Toast.makeText(mContext, "Error from Adapter : " + String.valueOf(e), Toast.LENGTH_SHORT).show();
                e.printStackTrace();

            }

        }



        return listViewItem;
    }




    }
