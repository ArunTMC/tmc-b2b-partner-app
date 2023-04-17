 package com.tmc.tmcb2bpartnerapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.Audit_UnstockedBatch_item;
import com.tmc.tmcb2bpartnerapp.activity.GoatEarTagItemDetailsList;
import com.tmc.tmcb2bpartnerapp.fragment.BatchItemDetailsFragment_withoutScanBarcode;
import com.tmc.tmcb2bpartnerapp.fragment.DeliveryCenter_PlaceOrderScreen_SecondVersn;
import com.tmc.tmcb2bpartnerapp.fragment.DeliveryCentre_PlaceOrderScreen_Fragment;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_Static_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.TMCAlertDialogClass;

import java.text.DecimalFormat;
import java.util.List;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

public class Adapter_EarTagItemDetails_List extends ArrayAdapter<Modal_GoatEarTagDetails> {

    Context mContext;
    List<Modal_GoatEarTagDetails> earTagDetailsList;
    GoatEarTagItemDetailsList goatEarTagItemDetailsList;
    String CalledFrom = "" , TaskToDo = "" ,batchStatus;
    DecimalFormat df3 = new DecimalFormat(Constants.threeDecimalPattern);

    DecimalFormat df2 = new DecimalFormat(Constants.twoDecimalPattern);
    public Adapter_EarTagItemDetails_List(Context mContext, List<Modal_GoatEarTagDetails> earTagDetailsListt, GoatEarTagItemDetailsList goatEarTagItemDetailsListt,String CalledFromm , String TaskToDoo , String batchStatuss) {
        super(mContext, R.layout.adapter_goat_eartag_details_item_list, earTagDetailsListt);
        this.mContext = mContext;
        this.earTagDetailsList = earTagDetailsListt;
        this.goatEarTagItemDetailsList = goatEarTagItemDetailsListt;
        this.CalledFrom = CalledFromm;
        this.TaskToDo = TaskToDoo;
        this.batchStatus = batchStatuss;
    }
        @Nullable
        @Override
        public Modal_GoatEarTagDetails getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public int getPosition(@Nullable Modal_GoatEarTagDetails item) {
            return super.getPosition(item);
        }

        public View getView(final int pos, View view, ViewGroup v) {
            @SuppressLint("ViewHolder")  View listViewItem = null;
            try{
                BaseActivity.baseActivity.getDeviceName();
                for(int i =0 ; i<500; i++){
                    if(i==499){
                        if(BaseActivity.isDeviceIsMobilePhone){


                            listViewItem = LayoutInflater.from(mContext).inflate(R.layout.adapter_goat_eartag_details_item_list, (ViewGroup) view, false);


                        }
                        else{
                            listViewItem = LayoutInflater.from(mContext).inflate(R.layout.pos_adapter_goat_eartag_details_item_list, (ViewGroup) view, false);

                        }
                    }

                }
            }
            catch (Exception e){
                listViewItem = LayoutInflater.from(mContext).inflate(R.layout.adapter_goat_eartag_details_item_list, (ViewGroup) view, false);

                e.printStackTrace();
            }
            final TextView barcodeno_textview = listViewItem.findViewById(R.id.barcodeno_textview);
            final TextView total_Weight_genderName_textview = listViewItem.findViewById(R.id.genderName_textview);
            final TextView total_Price_breedType_textview = listViewItem.findViewById(R.id.breedType_textview);
            final TextView currentweight_textview = listViewItem.findViewById(R.id.currentweight_textview);
            final TextView currentweight_KG_textview = listViewItem.findViewById(R.id.kg_label);


            final TextView total_Weight_genderName_label = listViewItem.findViewById(R.id.gender_label);
            final TextView total_Price_breedType_label = listViewItem.findViewById(R.id.breedType_label);
            final TextView currentweight_label = listViewItem.findViewById(R.id.currentweight_label);




            final Button auditItem_button = listViewItem.findViewById(R.id.auditItem_button);
            final Button reviewItem_button = listViewItem.findViewById(R.id.reviewItem_button);
            final RelativeLayout parentRelativeLayout = listViewItem.findViewById(R.id.parentRelativeLayout);
            final TextView sickGoatInstruction_textview = listViewItem.findViewById(R.id.sickGoatInstruction_textview);
            final Button deletebatchLayout = listViewItem.findViewById(R.id.deletebatchLayout);
            final TextView deadGoatInstruction_textview = listViewItem.findViewById(R.id.deadGoatInstruction_textview);



            if(TaskToDo.equals(String.valueOf("ItemInCart_SecondVersn")) ||TaskToDo.equals(mContext.getString(R.string.edit_unsold_items_in_batch)) ||TaskToDo.equals(mContext.getString(R.string.view_sold_items_in_batch))  ||  TaskToDo.equals(mContext.getString(R.string.view_unsold_items_in_batch)) || TaskToDo.equals("PlacedOrderItemDetails") ) {
                currentweight_label.setVisibility(View.INVISIBLE);
                currentweight_textview.setVisibility(View.INVISIBLE);
                currentweight_KG_textview.setVisibility(View.INVISIBLE);
                total_Weight_genderName_label.setText(String.valueOf("Weight"));
                total_Price_breedType_label .setText(String.valueOf("Price"));


            }
            else{
                total_Weight_genderName_label.setText(String.valueOf("Gender"));
                total_Price_breedType_label .setText(String.valueOf("Breed Type"));

                currentweight_KG_textview.setVisibility(View.VISIBLE);
                currentweight_label.setVisibility(View.VISIBLE);
                currentweight_textview.setVisibility(View.VISIBLE);
            }





            Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagDetailsList.get(pos);
            barcodeno_textview.setText(earTagDetailsList.get(pos).getBarcodeno());

            if(TaskToDo.equals(String.valueOf("ItemInCart_SecondVersn")) ||TaskToDo.equals(mContext.getString(R.string.edit_unsold_items_in_batch)) ||TaskToDo.equals(mContext.getString(R.string.view_sold_items_in_batch))  ||  TaskToDo.equals(mContext.getString(R.string.view_unsold_items_in_batch)) || TaskToDo.equals("PlacedOrderItemDetails")) {
                double meatyeild_weight_double =0 ,parts_Weight_double = 0 , total_Weight_double =0 , price_double =0;

                try{
                    String text =  String.valueOf(earTagDetailsList.get(pos).getMeatyieldweight());
                    text = text.replaceAll("[^\\d.]", "");
                    if(text.equals("")){
                        text = "0";
                    }
                    meatyeild_weight_double  = Double.parseDouble(text);


                }
                catch (Exception e){
                    e.printStackTrace();
                }

                try{
                    String text =  String.valueOf(earTagDetailsList.get(pos).getPartsweight());
                    text = text.replaceAll("[^\\d.]", "");
                    if(text.equals("")){
                        text = "0";
                    }
                    parts_Weight_double  = Double.parseDouble(text);

                }
                catch (Exception e){
                    e.printStackTrace();
                }

                try{
                    total_Weight_double = parts_Weight_double + meatyeild_weight_double;
                }
                catch (Exception e){
                    e.printStackTrace();
                }



                try{
                    String text = String.valueOf(earTagDetailsList.get(pos).getItemPrice());
                    text = text.replaceAll("[^\\d.]", "");
                    if(text.equals("")){
                        text = "0";
                    }
                    price_double = Double.parseDouble(text);

                    if(price_double==0){
                        try{
                            text = String.valueOf(earTagDetailsList.get(pos).getTotalPrice_ofItem());
                            text = text.replaceAll("[^\\d.]", "");
                            if(text.equals("")){
                                text = "0";
                            }
                            price_double = Double.parseDouble(text);

                        }
                        catch (Exception e){
                            price_double = 0;
                            e.printStackTrace();
                        }
                    }
                }
                catch (Exception e){
                    price_double = 0;
                    if(price_double==0){
                        try{
                            String text = String.valueOf(earTagDetailsList.get(pos).getTotalPrice_ofItem());
                            text = text.replaceAll("[^\\d.]", "");
                            if(text.equals("")){
                                text = "0";
                            }
                            price_double = Double.parseDouble(text);

                        }
                        catch (Exception e1){
                            price_double = 0;
                            e1.printStackTrace();
                        }
                    }
                    e.printStackTrace();
                }


                double temp_totalWeight = 0;
                try{
                    temp_totalWeight = total_Weight_double;
                    total_Weight_double = Double.parseDouble(df3.format(total_Weight_double));
                }
                catch (Exception e){
                    total_Weight_double = temp_totalWeight;
                    e.printStackTrace();
                }


                double temp_price = 0;
                try{
                    temp_price = price_double;
                    price_double = Double.parseDouble(df2.format(price_double));
                }
                catch (Exception e){
                    price_double = temp_price;
                    e.printStackTrace();
                }


                total_Weight_genderName_textview.setText(String.valueOf(total_Weight_double) + " kg");
                total_Price_breedType_textview.setText(String.valueOf(price_double)+" ₹");
            }
            else{
                total_Weight_genderName_textview.setText(earTagDetailsList.get(pos).getGender());
                total_Price_breedType_textview.setText(earTagDetailsList.get(pos).getBreedtype());
            }


            currentweight_textview.setText(earTagDetailsList.get(pos).getCurrentweightingrams());

            if(CalledFrom.equals(Constants.userType_SupplierCenter)) {
                reviewItem_button.setVisibility(View.GONE);
                if (TaskToDo.equals("NewAddedItem")) {
                    auditItem_button.setText("Edit");
                }
                else  if (TaskToDo.equals("Oldtem")) {
                    auditItem_button.setText("View");
                }
            }
            else if(CalledFrom.equals(Constants.userType_DeliveryCenter) || CalledFrom.equals(Constants.viewStockBalance)) {
                reviewItem_button.setVisibility(View.GONE);
                if (TaskToDo.equals("ViewUnReviewedItem")) {
                    auditItem_button.setText("Edit");
                    reviewItem_button.setVisibility(View.VISIBLE);

                } else if (TaskToDo.equals("ViewReviewedItem")) {
                    auditItem_button.setText("Edit");

                }
                else if(TaskToDo.equals("ViewSoldItem")){
                    auditItem_button.setText("View");

                }
                else if(TaskToDo.equals("ViewUnSoldItem")){
                    if(batchStatus.equals(Constants.batchDetailsStatus_Sold)){
                        auditItem_button.setText("View");

                    }
                    else{
                        auditItem_button.setText("Edit");

                    }

                }
                else if(TaskToDo.equals("ItemInCart")){
                    auditItem_button.setText("Edit");

                }

                else if(TaskToDo.equals(mContext.getString(R.string.edit_unsold_items_in_batch))  ) {
                    reviewItem_button.setVisibility(View.GONE);
                    if(batchStatus.equals(Constants.batchDetailsStatus_Sold)){
                        auditItem_button.setText("View");

                    }
                    else{
                        auditItem_button.setText("Edit");

                    }
                    deletebatchLayout.setVisibility(View.GONE);
                }
                else if(TaskToDo.equals(mContext.getString(R.string.view_sold_items_in_batch)) || TaskToDo.equals(mContext.getString(R.string.view_unsold_items_in_batch))  || TaskToDo.equals("PlacedOrderItemDetails")) {
                    reviewItem_button.setVisibility(View.GONE);
                    auditItem_button.setText("View");
                    deletebatchLayout.setVisibility(View.GONE);
                }

            }
            else if(CalledFrom.equals(mContext.getString(R.string.billing_Screen))) {
                reviewItem_button.setVisibility(View.GONE);
                if(TaskToDo.equals("ItemInCart")) {
                    auditItem_button.setText("Edit");
                    currentweight_textview.setText(earTagDetailsList.get(pos).getNewWeight_forBillingScreen());
                    deletebatchLayout.setVisibility(View.VISIBLE);
                }
            }

            else if(CalledFrom.equals(mContext.getString(R.string.placedOrder_Details_Screen_SecondVersion))) {
                reviewItem_button.setVisibility(View.GONE);
                if(TaskToDo.equals("ItemInCart_SecondVersn")) {
                    auditItem_button.setText("Edit");
                    currentweight_textview.setText(earTagDetailsList.get(pos).getNewWeight_forBillingScreen());
                    deletebatchLayout.setVisibility(View.VISIBLE);
                }
            }
            else if(CalledFrom.equals(mContext.getString(R.string.placedOrder_Details_Screen))) {
                reviewItem_button.setVisibility(View.GONE);
                if(TaskToDo.equals("ItemInOrderDetails")) {
                    auditItem_button.setText("View");
                    currentweight_textview.setText(earTagDetailsList.get(pos).getNewWeight_forBillingScreen());

                }
            }
            else if(CalledFrom.equals(mContext.getString(R.string.datewise_placedOrder_Details_Screen_SecondVersion))) {
                reviewItem_button.setVisibility(View.GONE);
                if(TaskToDo.equals("PlacedOrderItemDetails")) {
                    auditItem_button.setText("View");
                    deletebatchLayout.setVisibility(View.GONE);
                }
            }
            else if(CalledFrom.equals(mContext.getString(R.string.markdeliveredOrders_detailsScreen))) {
                reviewItem_button.setVisibility(View.GONE);
                if(TaskToDo.equals("PlacedOrderItemDetails")) {
                    auditItem_button.setText("View");
                    deletebatchLayout.setVisibility(View.GONE);
                }
            }

       /*     if(earTagDetailsList.get(pos).getStatus().equals(Constants.goatEarTagStatus_Loading)){
                if(goatEarTagItemDetailsList.CalledFrom.equals(Constants.userType_SupplierCenter)){
                    reviewItem_button.setVisibility(View.GONE);
                if(Modal_B2BBatchDetailsStatic.getStatus().equals(Constants.batchDetailsStatus_Fully_Loaded) || Modal_B2BBatchDetailsStatic.getStatus().equals(Constants.batchDetailsStatus_Cancelled)){
                    auditItem_button.setText("View");
                }

            }
            else{
                reviewItem_button.setVisibility(View.VISIBLE);
            }


            }
            else{
                reviewItem_button.setVisibility(View.GONE);
            }


        */
            if(earTagDetailsList.get(pos).getStatus().equals(Constants.goatEarTagStatus_Goatsick)){
                parentRelativeLayout.setBackground(getDrawable(mContext,R.color.TMCPalePurple));
                sickGoatInstruction_textview.setVisibility(View.VISIBLE);
                deadGoatInstruction_textview.setVisibility(View.GONE);

            }
            else if(earTagDetailsList.get(pos).getStatus().equals(Constants.goatEarTagStatus_Goatdead)){
                parentRelativeLayout.setBackground(getDrawable(mContext,R.color.TMCPalePurple));
                sickGoatInstruction_textview.setVisibility(View.GONE);
                deadGoatInstruction_textview.setVisibility(View.VISIBLE);

            }
            else{
                parentRelativeLayout.setBackground(getDrawable(mContext,R.color.white));
                sickGoatInstruction_textview.setVisibility(View.GONE);
                deadGoatInstruction_textview.setVisibility(View.GONE);
            }

            deletebatchLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new TMCAlertDialogClass(mContext, R.string.app_name, R.string.DeleteItemFromCart,
                            R.string.Yes_Text, R.string.No_Text,
                            new TMCAlertDialogClass.AlertListener() {
                                @Override
                                public void onYes() {


                                    if(CalledFrom.equals(mContext.getString(R.string.placedOrder_Details_Screen_SecondVersion))) {
                                        try{

                                            goatEarTagItemDetailsList.Intialize_and_ExecuteInB2BCartItemDetails(Constants.CallDELETEMethod,earTagDetailsList.get(pos).getBarcodeno(), goatEarTagItemDetailsList.orderid);


                                        }
                                        catch (Exception e){
                                            e.printStackTrace();
                                        }

                                        try{
                                            DeliveryCenter_PlaceOrderScreen_SecondVersn.earTagDetails_JSONFinalSalesHashMap.remove(earTagDetailsList.get(pos).getBarcodeno());
                                            DeliveryCenter_PlaceOrderScreen_SecondVersn.earTagDetailsArrayList_String.remove(earTagDetailsList.get(pos).getBarcodeno());
                                            DeliveryCenter_PlaceOrderScreen_SecondVersn.CalculateAndSetTotal_Quantity_Price_Values();


                                        }
                                        catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                    else {
                                        if(TaskToDo.equals(mContext.getString(R.string.edit_unsold_items_in_batch)) ||TaskToDo.equals(mContext.getString(R.string.view_sold_items_in_batch)) ) {
                                            goatEarTagItemDetailsList.deleteItemFromGoatEarTagItemList(earTagDetailsList.get(pos).getBatchno(), earTagDetailsList.get(pos).getBarcodeno(), earTagDetailsList.get(pos).getOrderid_forBillingScreen());

                                        }
                                        else{
                                            goatEarTagItemDetailsList.Intialize_and_ExecuteInB2BCartItemDetails(Constants.CallDELETEMethod, earTagDetailsList.get(pos).getBarcodeno(), earTagDetailsList.get(pos).getOrderid_forBillingScreen());

                                            Modal_B2BCartItemDetails modal_b2BCartItemDetails = new Modal_B2BCartItemDetails();
                                            
                                            modal_b2BCartItemDetails.batchno = earTagDetailsList.get(pos).getBatchno();
                                            modal_b2BCartItemDetails.barcodeno = earTagDetailsList.get(pos).getBarcodeno();
                                            modal_b2BCartItemDetails.weightingrams = earTagDetailsList.get(pos).getCurrentweightingrams();
                                            modal_b2BCartItemDetails.oldweightingrams = earTagDetailsList.get(pos).getCurrentweightingrams();
                                            modal_b2BCartItemDetails.itemaddeddate = DateParser.getDate_and_time_newFormat();
                                            modal_b2BCartItemDetails.status = earTagDetailsList.get(pos).getStatus();
                                            modal_b2BCartItemDetails.gender = earTagDetailsList.get(pos).getGender();
                                            modal_b2BCartItemDetails.breedtype = earTagDetailsList.get(pos).getBreedtype();
                                            // modal_b2BCartItemDetails.orderid= BillingScreen.orderid;
                                            modal_b2BCartItemDetails.orderid = DeliveryCentre_PlaceOrderScreen_Fragment.orderid;
                                            modal_b2BCartItemDetails.b2bctgykey = earTagDetailsList.get(pos).getB2bctgykey();
                                            modal_b2BCartItemDetails.b2bsubctgykey = earTagDetailsList.get(pos).getB2bsubctgykey();
                                            modal_b2BCartItemDetails.gradekey = earTagDetailsList.get(pos).getGradekey();
                                            modal_b2BCartItemDetails.gradename = earTagDetailsList.get(pos).getGradename();
                                            modal_b2BCartItemDetails.gradeprice = earTagDetailsList.get(pos).getGradeprice();
                                            modal_b2BCartItemDetails.oldgradekey = earTagDetailsList.get(pos).getGradekey();
                                            modal_b2BCartItemDetails.oldgradeprice = earTagDetailsList.get(pos).getGradeprice();
                                            modal_b2BCartItemDetails.supplierkey = earTagDetailsList.get(pos).getSupplierkey();
                                            modal_b2BCartItemDetails.suppliername = earTagDetailsList.get(pos).getSuppliername();

                                            deleteItemFromBillingScreen(earTagDetailsList.get(pos).getBatchno(), earTagDetailsList.get(pos).getBarcodeno(), earTagDetailsList.get(pos).getOrderid_forBillingScreen(), modal_b2BCartItemDetails);


                                            goatEarTagItemDetailsList.deleteItemFromGoatEarTagItemList(earTagDetailsList.get(pos).getBatchno(), earTagDetailsList.get(pos).getBarcodeno(), earTagDetailsList.get(pos).getOrderid_forBillingScreen());

                                        }

                                    }
                                }

                                @Override
                                public void onNo() {

                                }
                            });
                }
            });
            reviewItem_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagDetailsList.get(pos);

                    Modal_Static_GoatEarTagDetails.batchno = earTagDetailsList.get(pos).getBatchno();
                    Modal_Static_GoatEarTagDetails.barcodeno = earTagDetailsList.get(pos).getBarcodeno();
                    Modal_Static_GoatEarTagDetails.currentweightingrams = earTagDetailsList.get(pos).getCurrentweightingrams();
                    Modal_Static_GoatEarTagDetails.loadedweightingrams = earTagDetailsList.get(pos).getLoadedweightingrams();
                    Modal_Static_GoatEarTagDetails.stockedweightingrams = earTagDetailsList.get(pos).getStockedweightingrams();
                    Modal_Static_GoatEarTagDetails.itemaddeddate = DateParser.getDate_and_time_newFormat();
                    Modal_Static_GoatEarTagDetails.status = earTagDetailsList.get(pos).getStatus();
                    Modal_Static_GoatEarTagDetails.gender = earTagDetailsList.get(pos).getGender();
                    Modal_Static_GoatEarTagDetails.breedtype = earTagDetailsList.get(pos).getBreedtype();
                    Modal_Static_GoatEarTagDetails.supplierkey = earTagDetailsList.get(pos).getSupplierkey();
                    Modal_Static_GoatEarTagDetails.suppliername = earTagDetailsList.get(pos).getSuppliername();
                    Modal_Static_GoatEarTagDetails.description = earTagDetailsList.get(pos).getDescription();
                    Modal_Static_GoatEarTagDetails.newWeight_forBillingScreen = earTagDetailsList.get(pos).getNewWeight_forBillingScreen();
                    Modal_Static_GoatEarTagDetails.gradekey = earTagDetailsList.get(pos).getGradekey();
                    Modal_Static_GoatEarTagDetails.gradename = earTagDetailsList.get(pos).getGradename();
                    Modal_Static_GoatEarTagDetails.gradeprice = earTagDetailsList.get(pos).getGradeprice();
                    goatEarTagItemDetailsList. mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                    goatEarTagItemDetailsList.value_forFragment = mContext.getString(R.string.stock_batch_item_withoutScan);
                    goatEarTagItemDetailsList.loadMyFragment();
                }
            });



            auditItem_button.setOnClickListener(view1 -> {


                Modal_Static_GoatEarTagDetails.batchno = earTagDetailsList.get(pos).getBatchno();
                Modal_Static_GoatEarTagDetails.barcodeno = earTagDetailsList.get(pos).getBarcodeno();
                Modal_Static_GoatEarTagDetails.currentweightingrams = earTagDetailsList.get(pos).getCurrentweightingrams();
                Modal_Static_GoatEarTagDetails.loadedweightingrams = earTagDetailsList.get(pos).getLoadedweightingrams();
                Modal_Static_GoatEarTagDetails.stockedweightingrams = earTagDetailsList.get(pos).getStockedweightingrams();
                Modal_Static_GoatEarTagDetails.itemaddeddate = DateParser.getDate_and_time_newFormat();
                Modal_Static_GoatEarTagDetails.status = earTagDetailsList.get(pos).getStatus();
                Modal_Static_GoatEarTagDetails.gender = earTagDetailsList.get(pos).getGender();
                Modal_Static_GoatEarTagDetails.breedtype = earTagDetailsList.get(pos).getBreedtype();
                Modal_Static_GoatEarTagDetails.supplierkey = earTagDetailsList.get(pos).getSupplierkey();
                Modal_Static_GoatEarTagDetails.suppliername = earTagDetailsList.get(pos).getSuppliername();
                Modal_Static_GoatEarTagDetails.description = earTagDetailsList.get(pos).getDescription();
                Modal_Static_GoatEarTagDetails.newWeight_forBillingScreen = earTagDetailsList.get(pos).getNewWeight_forBillingScreen();
                Modal_Static_GoatEarTagDetails.gradekey = earTagDetailsList.get(pos).getGradekey();
                Modal_Static_GoatEarTagDetails.gradename = earTagDetailsList.get(pos).getGradename();
                Modal_Static_GoatEarTagDetails.gradeprice = earTagDetailsList.get(pos).getGradeprice();
                Modal_Static_GoatEarTagDetails.batchWiseStatus = batchStatus;

                if(CalledFrom.equals(Constants.userType_SupplierCenter)) {
                    if (TaskToDo.equals("NewAddedItem")) {
                        goatEarTagItemDetailsList. mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                        goatEarTagItemDetailsList.value_forFragment = mContext.getString(R.string.supplier_goatItemList);

                        goatEarTagItemDetailsList.loadMyFragment();

                    }
                    else  if (TaskToDo.equals("Oldtem")) {
                        goatEarTagItemDetailsList. mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                        goatEarTagItemDetailsList.value_forFragment = mContext.getString(R.string.supplier_goatItemList);

                        goatEarTagItemDetailsList.loadMyFragment();
                    }
                }
                else if(CalledFrom.equals(Constants.userType_DeliveryCenter)) {
                    if (TaskToDo.equals("ViewUnReviewedItem")) {
                        Intent intent = new Intent(mContext, Audit_UnstockedBatch_item.class);
                        intent.putExtra("batchno",earTagDetailsList.get(pos).getBatchno());
                        intent.putExtra("barcodeno",earTagDetailsList.get(pos).getBarcodeno());
                        intent.putExtra("supplierkey",earTagDetailsList.get(pos).getSupplierkey());
                        intent.putExtra("itemsposition",String.valueOf(pos));
                        mContext.startActivity(intent);




                    }
                    else if (TaskToDo.equals("ViewReviewedItem")) {
                        goatEarTagItemDetailsList. mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                        goatEarTagItemDetailsList.value_forFragment = mContext.getString(R.string.deliverycenter_ReviewedgoatItemList);

                        goatEarTagItemDetailsList.loadMyFragment();

                    }
                    else if(TaskToDo.equals("ViewSoldItem")){
                        goatEarTagItemDetailsList. mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                        goatEarTagItemDetailsList.value_forFragment = mContext.getString(R.string.deliverycenter_SoldgoatItemList);

                        goatEarTagItemDetailsList.loadMyFragment();
                    }
                    else if(TaskToDo.equals("ViewUnSoldItem")){
                        goatEarTagItemDetailsList. mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                        goatEarTagItemDetailsList.value_forFragment = mContext.getString(R.string.deliverycenter_UnsoldgoatItemList);

                        goatEarTagItemDetailsList.loadMyFragment();
                    }
                    else if(TaskToDo.equals("ItemInCart")){
                        Modal_Static_GoatEarTagDetails.currentweightingrams = earTagDetailsList.get(pos).getNewWeight_forBillingScreen();

                        goatEarTagItemDetailsList. mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                        goatEarTagItemDetailsList.value_forFragment = mContext.getString(R.string.billing_Screen_editOrder);

                        goatEarTagItemDetailsList.loadMyFragment();
                    }
                    else if(TaskToDo.equals(mContext.getString(R.string.edit_unsold_items_in_batch)) ||TaskToDo.equals(mContext.getString(R.string.view_sold_items_in_batch))  || TaskToDo.equals("PlacedOrderItemDetails")) {

                        goatEarTagItemDetailsList. selectedItemsStatus = "";
                        goatEarTagItemDetailsList. selectedGenderName = "";
                        Modal_Static_GoatEarTagDetails.approxliveweight = earTagDetailsList.get(pos).getApproxliveweight();
                        Modal_Static_GoatEarTagDetails.meatyieldweight = earTagDetailsList.get(pos).getMeatyieldweight();
                        Modal_Static_GoatEarTagDetails.partsweight = earTagDetailsList.get(pos).getPartsweight();
                        Modal_Static_GoatEarTagDetails.itemPrice = earTagDetailsList.get(pos).getItemPrice();
                        Modal_Static_GoatEarTagDetails.itemWeight = earTagDetailsList.get(pos).getTotalItemWeight();

                        Modal_Static_GoatEarTagDetails.totalPrice_ofItem = earTagDetailsList.get(pos).getTotalPrice_ofItem();
                        Modal_Static_GoatEarTagDetails.discount = earTagDetailsList.get(pos).getDiscount();

                        Modal_Static_GoatEarTagDetails.gender=earTagDetailsList.get(pos).getGender();
                        Modal_Static_GoatEarTagDetails.gradename=earTagDetailsList.get(pos).getGradename();
                        Modal_Static_GoatEarTagDetails.status=earTagDetailsList.get(pos).getStatus();

                        goatEarTagItemDetailsList. selectedItemsStatus = earTagDetailsList.get(pos).getStatus();
                        goatEarTagItemDetailsList. selectedGenderName = earTagDetailsList.get(pos).getGender();
                        goatEarTagItemDetailsList. ShowGoatItemDetailsDialog();
                    }
                    }
                else if(CalledFrom.equals(mContext.getString(R.string.billing_Screen))) {
                    if(TaskToDo.equals("ItemInCart")){
                        Modal_Static_GoatEarTagDetails.currentweightingrams = earTagDetailsList.get(pos).getNewWeight_forBillingScreen();
                        Modal_Static_GoatEarTagDetails.supplierkey = earTagDetailsList.get(pos).getSupplierkey();
                        Modal_Static_GoatEarTagDetails.suppliername = earTagDetailsList.get(pos).getSuppliername();
                        goatEarTagItemDetailsList. mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                        goatEarTagItemDetailsList.value_forFragment = mContext.getString(R.string.billing_Screen_editOrder);
                        goatEarTagItemDetailsList.loadMyFragment();
                    }
                }
                else if(CalledFrom.equals(mContext.getString(R.string.placedOrder_Details_Screen))) {
                    if(TaskToDo.equals("ItemInOrderDetails")){
                        Modal_Static_GoatEarTagDetails.currentweightingrams = earTagDetailsList.get(pos).getNewWeight_forBillingScreen();
                        Modal_Static_GoatEarTagDetails.supplierkey = earTagDetailsList.get(pos).getSupplierkey();
                        Modal_Static_GoatEarTagDetails.suppliername = earTagDetailsList.get(pos).getSuppliername();
                        goatEarTagItemDetailsList. mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                        goatEarTagItemDetailsList.value_forFragment = mContext.getString(R.string.placedOrder_Details_Screen);
                        goatEarTagItemDetailsList.loadMyFragment();
                    }
                }

                else if(CalledFrom.equals(mContext.getString(R.string.placedOrder_Details_Screen_SecondVersion))) {
                    if(TaskToDo.equals("ItemInCart_SecondVersn")){
                    /*    Modal_Static_GoatEarTagDetails.currentweightingrams = earTagDetailsList.get(pos).getNewWeight_forBillingScreen();
                        Modal_Static_GoatEarTagDetails.supplierkey = earTagDetailsList.get(pos).getSupplierkey();
                        Modal_Static_GoatEarTagDetails.suppliername = earTagDetailsList.get(pos).getSuppliername();
                        goatEarTagItemDetailsList. mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                        goatEarTagItemDetailsList.value_forFragment = mContext.getString(R.string.placedOrder_Details_Screen);
                        goatEarTagItemDetailsList.loadMyFragment();

                     */
                        goatEarTagItemDetailsList. selectedItemsStatus = "";
                        goatEarTagItemDetailsList. selectedGenderName =  "";
                        Modal_Static_GoatEarTagDetails.approxliveweight = earTagDetailsList.get(pos).getApproxliveweight();
                        Modal_Static_GoatEarTagDetails.meatyieldweight = earTagDetailsList.get(pos).getMeatyieldweight();
                        Modal_Static_GoatEarTagDetails.partsweight = earTagDetailsList.get(pos).getPartsweight();
                        Modal_Static_GoatEarTagDetails.itemPrice = earTagDetailsList.get(pos).getItemPrice();
                        Modal_Static_GoatEarTagDetails.itemWeight = earTagDetailsList.get(pos).getTotalItemWeight();

                        Modal_Static_GoatEarTagDetails.totalPrice_ofItem = earTagDetailsList.get(pos).getTotalPrice_ofItem();
                        Modal_Static_GoatEarTagDetails.discount = earTagDetailsList.get(pos).getDiscount();
                        Modal_Static_GoatEarTagDetails.gender=earTagDetailsList.get(pos).getGender();
                        Modal_Static_GoatEarTagDetails.gradename=earTagDetailsList.get(pos).getGradename();
                        Modal_Static_GoatEarTagDetails.status=earTagDetailsList.get(pos).getStatus();
                        goatEarTagItemDetailsList. selectedItemsStatus = earTagDetailsList.get(pos).getStatus();
                        goatEarTagItemDetailsList. selectedGenderName = earTagDetailsList.get(pos).getGender();
                       goatEarTagItemDetailsList. ShowGoatItemDetailsDialog();



                    }
                }
                else if(CalledFrom.equals(mContext.getString(R.string.datewise_placedOrder_Details_Screen_SecondVersion))) {
                    goatEarTagItemDetailsList. selectedItemsStatus = "";
                    goatEarTagItemDetailsList. selectedGenderName = "";
                    Modal_Static_GoatEarTagDetails.approxliveweight = earTagDetailsList.get(pos).getApproxliveweight();
                    Modal_Static_GoatEarTagDetails.meatyieldweight = earTagDetailsList.get(pos).getMeatyieldweight();
                    Modal_Static_GoatEarTagDetails.partsweight = earTagDetailsList.get(pos).getPartsweight();
                    Modal_Static_GoatEarTagDetails.itemPrice = earTagDetailsList.get(pos).getItemPrice();
                    Modal_Static_GoatEarTagDetails.itemWeight = earTagDetailsList.get(pos).getTotalItemWeight();

                    Modal_Static_GoatEarTagDetails.totalPrice_ofItem = earTagDetailsList.get(pos).getTotalPrice_ofItem();
                    Modal_Static_GoatEarTagDetails.discount = earTagDetailsList.get(pos).getDiscount();

                    Modal_Static_GoatEarTagDetails.gender=earTagDetailsList.get(pos).getGender();
                    Modal_Static_GoatEarTagDetails.gradename=earTagDetailsList.get(pos).getGradename();
                    Modal_Static_GoatEarTagDetails.status=earTagDetailsList.get(pos).getStatus();

                    goatEarTagItemDetailsList. selectedItemsStatus = earTagDetailsList.get(pos).getStatus();
                    goatEarTagItemDetailsList. selectedGenderName = earTagDetailsList.get(pos).getGender();
                    goatEarTagItemDetailsList. ShowGoatItemDetailsDialog();

                }
                else if(CalledFrom.equals(mContext.getString(R.string.markdeliveredOrders_detailsScreen))) {
                    goatEarTagItemDetailsList. selectedItemsStatus = "";
                    goatEarTagItemDetailsList. selectedGenderName = "";
                    Modal_Static_GoatEarTagDetails.approxliveweight = earTagDetailsList.get(pos).getApproxliveweight();
                    Modal_Static_GoatEarTagDetails.meatyieldweight = earTagDetailsList.get(pos).getMeatyieldweight();
                    Modal_Static_GoatEarTagDetails.partsweight = earTagDetailsList.get(pos).getPartsweight();
                    Modal_Static_GoatEarTagDetails.itemPrice = earTagDetailsList.get(pos).getItemPrice();
                    Modal_Static_GoatEarTagDetails.itemWeight = earTagDetailsList.get(pos).getTotalItemWeight();

                    Modal_Static_GoatEarTagDetails.totalPrice_ofItem = earTagDetailsList.get(pos).getTotalPrice_ofItem();
                    Modal_Static_GoatEarTagDetails.discount = earTagDetailsList.get(pos).getDiscount();

                    Modal_Static_GoatEarTagDetails.gender=earTagDetailsList.get(pos).getGender();
                    Modal_Static_GoatEarTagDetails.gradename=earTagDetailsList.get(pos).getGradename();
                    Modal_Static_GoatEarTagDetails.status=earTagDetailsList.get(pos).getStatus();

                    goatEarTagItemDetailsList. selectedItemsStatus = earTagDetailsList.get(pos).getStatus();
                    goatEarTagItemDetailsList. selectedGenderName = earTagDetailsList.get(pos).getGender();
                    goatEarTagItemDetailsList. ShowGoatItemDetailsDialog();

                }

                else if(CalledFrom.equals(Constants.viewStockBalance)) {
                    goatEarTagItemDetailsList. selectedItemsStatus = "";
                    goatEarTagItemDetailsList. selectedGenderName = "";
                    Modal_Static_GoatEarTagDetails.approxliveweight = earTagDetailsList.get(pos).getApproxliveweight();
                    Modal_Static_GoatEarTagDetails.meatyieldweight = earTagDetailsList.get(pos).getMeatyieldweight();
                    Modal_Static_GoatEarTagDetails.partsweight = earTagDetailsList.get(pos).getPartsweight();
                    Modal_Static_GoatEarTagDetails.itemPrice = earTagDetailsList.get(pos).getItemPrice();
                    Modal_Static_GoatEarTagDetails.itemWeight = earTagDetailsList.get(pos).getTotalItemWeight();

                    Modal_Static_GoatEarTagDetails.totalPrice_ofItem = earTagDetailsList.get(pos).getTotalPrice_ofItem();
                    Modal_Static_GoatEarTagDetails.discount = earTagDetailsList.get(pos).getDiscount();

                    Modal_Static_GoatEarTagDetails.gender=earTagDetailsList.get(pos).getGender();
                    Modal_Static_GoatEarTagDetails.gradename=earTagDetailsList.get(pos).getGradename();
                    Modal_Static_GoatEarTagDetails.status=earTagDetailsList.get(pos).getStatus();

                    goatEarTagItemDetailsList. selectedItemsStatus = earTagDetailsList.get(pos).getStatus();
                    goatEarTagItemDetailsList. selectedGenderName = earTagDetailsList.get(pos).getGender();
                    goatEarTagItemDetailsList. ShowGoatItemDetailsDialog();

                }







               /* Intent intent = new Intent(mContext, Audit_UnstockedBatch_item.class);
                intent.putExtra("batchno",earTagDetailsList.get(pos).getBatchno());
                intent.putExtra("barcodeno",earTagDetailsList.get(pos).getBarcodeno());
                intent.putExtra("supplierkey",earTagDetailsList.get(pos).getSupplierkey());
                intent.putExtra("itemsposition",String.valueOf(pos));
                                mContext.startActivity(intent);

                */

            });


            return listViewItem;
        }


    private void deleteItemFromBillingScreen(String batchno, String barcodeno, String orderid_forBillingScreen , Modal_B2BCartItemDetails modal_b2BCartItemDetails ) {
       /* try {
            if (BillingScreen.earTagDetailsArrayList_String.contains(barcodeno)) {



                try{
                         if(CalledFrom.equals(mContext.getString(R.string.billing_Screen))) {
                        if(TaskToDo.equals("ItemInCart")) {

                            BillingScreen.removeEntryFromGradewiseQuantity_and_Weight(modal_b2BCartItemDetails,false);



                        }

                    }


                }
                catch (Exception e){
                    e.printStackTrace();
                }

                if (BillingScreen.earTagDetails_weightStringHashMap.containsKey(barcodeno)) {
                    BillingScreen.earTagDetails_weightStringHashMap.remove(barcodeno);
                }


                BillingScreen.earTagDetailsArrayList_String.remove(barcodeno);
                try {
                    BillingScreen.adapter_billingScreen_cartList.notifyDataSetChanged();
                }
                catch (Exception e){
                  //  e.printStackTrace();
                }
                BillingScreen.calculateTotalweight_Quantity_Price();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        */


        try {
            if(CalledFrom.equals(mContext.getString(R.string.placedOrder_Details_Screen_SecondVersion))) {
                if (DeliveryCenter_PlaceOrderScreen_SecondVersn.earTagDetailsArrayList_String.contains(barcodeno)) {
                /*if (DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetailsHashMap.containsKey(barcodeno)) {
                    DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetailsHashMap.remove(barcodeno);
                }

                if (BillingScreen.earTagDetails_weightStringHashMap.containsKey(barcodeno)) {
                    BillingScreen.earTagDetails_weightStringHashMap.remove(barcodeno);
                }


                BillingScreen.earTagDetailsArrayList_String.remove(barcodeno);
                try {
                    BillingScreen.adapter_billingScreen_cartList.notifyDataSetChanged();
                }
                catch (Exception e){
                    //  e.printStackTrace();
                }
                BillingScreen.calculateTotalweight_Quantity_Price();
                 */


                    try{
                            if(TaskToDo.equals("ItemInCart_SecondVersn")) {
                                try{
                                    if(DeliveryCenter_PlaceOrderScreen_SecondVersn.earTagDetailsArrayList_String.contains(barcodeno)){
                                        DeliveryCenter_PlaceOrderScreen_SecondVersn.earTagDetailsArrayList_String.remove(barcodeno);
                                    }
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                                try{
                                    if( DeliveryCenter_PlaceOrderScreen_SecondVersn.earTagDetails_JSONFinalSalesHashMap.containsKey(barcodeno)){
                                        DeliveryCenter_PlaceOrderScreen_SecondVersn.earTagDetails_JSONFinalSalesHashMap.remove(barcodeno);

                                    }

                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }

                                try{
                                    DeliveryCenter_PlaceOrderScreen_SecondVersn.CalculateAndSetTotal_Quantity_Price_Values();

                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }

                            }




                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                }
            }
            else{
                if (DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetailsArrayList_String.contains(barcodeno)) {
                /*if (DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetailsHashMap.containsKey(barcodeno)) {
                    DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetailsHashMap.remove(barcodeno);
                }

                if (BillingScreen.earTagDetails_weightStringHashMap.containsKey(barcodeno)) {
                    BillingScreen.earTagDetails_weightStringHashMap.remove(barcodeno);
                }


                BillingScreen.earTagDetailsArrayList_String.remove(barcodeno);
                try {
                    BillingScreen.adapter_billingScreen_cartList.notifyDataSetChanged();
                }
                catch (Exception e){
                    //  e.printStackTrace();
                }
                BillingScreen.calculateTotalweight_Quantity_Price();
                 */


                    try{
                        if(CalledFrom.equals(mContext.getString(R.string.billing_Screen))) {
                            if(TaskToDo.equals("ItemInCart")) {

                                DeliveryCentre_PlaceOrderScreen_Fragment.removeEntryFromGradewiseQuantity_and_Weight(modal_b2BCartItemDetails,false);



                            }

                        }


                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    if (DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetails_weightStringHashMap.containsKey(barcodeno)) {
                        DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetails_weightStringHashMap.remove(barcodeno);
                    }


                    DeliveryCentre_PlaceOrderScreen_Fragment.earTagDetailsArrayList_String.remove(barcodeno);
                    try {
                        DeliveryCentre_PlaceOrderScreen_Fragment.adapter_billingScreen_cartList.notifyDataSetChanged();
                    }
                    catch (Exception e){
                        //  e.printStackTrace();
                    }
                    DeliveryCentre_PlaceOrderScreen_Fragment.calculateTotalweight_Quantity_Price();
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
