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
import com.tmc.tmcb2bpartnerapp.fragment.DeliveryCentre_PlaceOrderScreen_Fragment;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BCartItemDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_Static_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.utils.BaseActivity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;
import com.tmc.tmcb2bpartnerapp.utils.TMCAlertDialogClass;

import java.util.List;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

public class Adapter_EarTagItemDetails_List extends ArrayAdapter<Modal_GoatEarTagDetails> {

    Context mContext;
    List<Modal_GoatEarTagDetails> earTagDetailsList;
    GoatEarTagItemDetailsList goatEarTagItemDetailsList;
    String CalledFrom = "" , TaskToDo = "";


    public Adapter_EarTagItemDetails_List(Context mContext, List<Modal_GoatEarTagDetails> earTagDetailsListt, GoatEarTagItemDetailsList goatEarTagItemDetailsListt,String CalledFromm , String TaskToDoo) {
        super(mContext, R.layout.adapter_goat_eartag_details_item_list, earTagDetailsListt);
        this.mContext = mContext;
        this.earTagDetailsList = earTagDetailsListt;
        this.goatEarTagItemDetailsList = goatEarTagItemDetailsListt;
        this.CalledFrom = CalledFromm;
        this.TaskToDo = TaskToDoo;
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
            final TextView genderName_textview = listViewItem.findViewById(R.id.genderName_textview);
            final TextView breedType_textview = listViewItem.findViewById(R.id.breedType_textview);
            final TextView currentweight_textview = listViewItem.findViewById(R.id.currentweight_textview);
            final Button auditItem_button = listViewItem.findViewById(R.id.auditItem_button);
            final Button reviewItem_button = listViewItem.findViewById(R.id.reviewItem_button);
            final RelativeLayout parentRelativeLayout = listViewItem.findViewById(R.id.parentRelativeLayout);
            final TextView sickGoatInstruction_textview = listViewItem.findViewById(R.id.sickGoatInstruction_textview);
            final Button deletebatchLayout = listViewItem.findViewById(R.id.deletebatchLayout);



            Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagDetailsList.get(pos);
            barcodeno_textview.setText(modal_goatEarTagDetails.getBarcodeno());
            genderName_textview.setText(modal_goatEarTagDetails.getGender());
            breedType_textview.setText(modal_goatEarTagDetails.getBreedtype());
            currentweight_textview.setText(modal_goatEarTagDetails.getCurrentweightingrams());

            if(CalledFrom.equals(Constants.userType_SupplierCenter)) {
                reviewItem_button.setVisibility(View.GONE);
                if (TaskToDo.equals("NewAddedItem")) {
                    auditItem_button.setText("Edit");
                }
                else  if (TaskToDo.equals("Oldtem")) {
                    auditItem_button.setText("View");
                }
            }
            else if(CalledFrom.equals(Constants.userType_DeliveryCenter)) {
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
                    auditItem_button.setText("Edit");

                }
                else if(TaskToDo.equals("ItemInCart")){
                    auditItem_button.setText("Edit");

                }
            }
            else if(CalledFrom.equals(mContext.getString(R.string.billing_Screen))) {
                reviewItem_button.setVisibility(View.GONE);
                if(TaskToDo.equals("ItemInCart")) {
                    auditItem_button.setText("Edit");
                    currentweight_textview.setText(modal_goatEarTagDetails.getNewWeight_forBillingScreen());
                    deletebatchLayout.setVisibility(View.VISIBLE);
                }
            }
            else if(CalledFrom.equals(mContext.getString(R.string.placedOrder_Details_Screen))) {
                reviewItem_button.setVisibility(View.GONE);
                if(TaskToDo.equals("ItemInOrderDetails")) {
                    auditItem_button.setText("View");
                    currentweight_textview.setText(modal_goatEarTagDetails.getNewWeight_forBillingScreen());

                }
            }

       /*     if(modal_goatEarTagDetails.getStatus().equals(Constants.goatEarTagStatus_Loading)){
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
            if(modal_goatEarTagDetails.getStatus().equals(Constants.goatEarTagStatus_Goatsick)){
                parentRelativeLayout.setBackground(getDrawable(mContext,R.color.TMCPalePurple));
                sickGoatInstruction_textview.setVisibility(View.VISIBLE);

            }
            else{
                parentRelativeLayout.setBackground(getDrawable(mContext,R.color.white));
                sickGoatInstruction_textview.setVisibility(View.GONE);
            }

            deletebatchLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new TMCAlertDialogClass(mContext, R.string.app_name, R.string.DeleteItemFromCart,
                            R.string.Yes_Text, R.string.No_Text,
                            new TMCAlertDialogClass.AlertListener() {
                                @Override
                                public void onYes() {
                                    goatEarTagItemDetailsList.Intialize_and_ExecuteInB2BCartItemDetails(Constants.CallDELETEMethod , modal_goatEarTagDetails.getBarcodeno(),modal_goatEarTagDetails.getOrderid_forBillingScreen());

                                    Modal_B2BCartItemDetails modal_b2BCartItemDetails = new Modal_B2BCartItemDetails();

                                    modal_b2BCartItemDetails.batchno = modal_goatEarTagDetails.getBatchno();
                                    modal_b2BCartItemDetails.barcodeno = modal_goatEarTagDetails.getBarcodeno();
                                    modal_b2BCartItemDetails.weightingrams = modal_goatEarTagDetails.getCurrentweightingrams();
                                    modal_b2BCartItemDetails.oldweightingrams = modal_goatEarTagDetails.getCurrentweightingrams();
                                    modal_b2BCartItemDetails.itemaddeddate = DateParser.getDate_and_time_newFormat();
                                    modal_b2BCartItemDetails.status = modal_goatEarTagDetails.getStatus();
                                    modal_b2BCartItemDetails.gender = modal_goatEarTagDetails.getGender();
                                    modal_b2BCartItemDetails.breedtype = modal_goatEarTagDetails.getBreedtype();
                                   // modal_b2BCartItemDetails.orderid= BillingScreen.orderid;
                                    modal_b2BCartItemDetails.orderid= DeliveryCentre_PlaceOrderScreen_Fragment.orderid;
                                    modal_b2BCartItemDetails.b2bctgykey = modal_goatEarTagDetails.getB2bctgykey();
                                    modal_b2BCartItemDetails.b2bsubctgykey = modal_goatEarTagDetails.getB2bsubctgykey();
                                    modal_b2BCartItemDetails.gradekey = modal_goatEarTagDetails.getGradekey();
                                    modal_b2BCartItemDetails.gradename = modal_goatEarTagDetails.getGradename();
                                    modal_b2BCartItemDetails.gradeprice = modal_goatEarTagDetails.getGradeprice();
                                    modal_b2BCartItemDetails.oldgradekey = modal_goatEarTagDetails.getGradekey();
                                    modal_b2BCartItemDetails.oldgradeprice = modal_goatEarTagDetails.getGradeprice();
                                    modal_b2BCartItemDetails.supplierkey = modal_goatEarTagDetails.getSupplierkey();
                                    modal_b2BCartItemDetails.suppliername = modal_goatEarTagDetails.getSuppliername();

                                    deleteItemFromBillingScreen(modal_goatEarTagDetails.getBatchno(),modal_goatEarTagDetails.getBarcodeno(),modal_goatEarTagDetails.getOrderid_forBillingScreen(),modal_b2BCartItemDetails);


                                    goatEarTagItemDetailsList. deleteItemFromGoatEarTagItemList(modal_goatEarTagDetails.getBatchno(),modal_goatEarTagDetails.getBarcodeno(),modal_goatEarTagDetails.getOrderid_forBillingScreen() );

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

                    Modal_Static_GoatEarTagDetails.batchno = modal_goatEarTagDetails.getBatchno();
                    Modal_Static_GoatEarTagDetails.barcodeno = modal_goatEarTagDetails.getBarcodeno();
                    Modal_Static_GoatEarTagDetails.currentweightingrams = modal_goatEarTagDetails.getCurrentweightingrams();
                    Modal_Static_GoatEarTagDetails.loadedweightingrams = modal_goatEarTagDetails.getLoadedweightingrams();
                    Modal_Static_GoatEarTagDetails.stockedweightingrams = modal_goatEarTagDetails.getStockedweightingrams();
                    Modal_Static_GoatEarTagDetails.itemaddeddate = DateParser.getDate_and_time_newFormat();
                    Modal_Static_GoatEarTagDetails.status = modal_goatEarTagDetails.getStatus();
                    Modal_Static_GoatEarTagDetails.gender = modal_goatEarTagDetails.getGender();
                    Modal_Static_GoatEarTagDetails.breedtype = modal_goatEarTagDetails.getBreedtype();
                    Modal_Static_GoatEarTagDetails.supplierkey = modal_goatEarTagDetails.getSupplierkey();
                    Modal_Static_GoatEarTagDetails.suppliername = modal_goatEarTagDetails.getSuppliername();
                    Modal_Static_GoatEarTagDetails.description = modal_goatEarTagDetails.getDescription();
                    Modal_Static_GoatEarTagDetails.newWeight_forBillingScreen = modal_goatEarTagDetails.getNewWeight_forBillingScreen();
                    Modal_Static_GoatEarTagDetails.gradekey = modal_goatEarTagDetails.getGradekey();
                    Modal_Static_GoatEarTagDetails.gradename = modal_goatEarTagDetails.getGradename();
                    Modal_Static_GoatEarTagDetails.gradeprice = modal_goatEarTagDetails.getGradeprice();
                    goatEarTagItemDetailsList. mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                    goatEarTagItemDetailsList.value_forFragment = mContext.getString(R.string.stock_batch_item_withoutScan);
                    goatEarTagItemDetailsList.loadMyFragment();
                }
            });



            auditItem_button.setOnClickListener(view1 -> {


                Modal_Static_GoatEarTagDetails.batchno = modal_goatEarTagDetails.getBatchno();
                Modal_Static_GoatEarTagDetails.barcodeno = modal_goatEarTagDetails.getBarcodeno();
                Modal_Static_GoatEarTagDetails.currentweightingrams = modal_goatEarTagDetails.getCurrentweightingrams();
                Modal_Static_GoatEarTagDetails.loadedweightingrams = modal_goatEarTagDetails.getLoadedweightingrams();
                Modal_Static_GoatEarTagDetails.stockedweightingrams = modal_goatEarTagDetails.getStockedweightingrams();
                Modal_Static_GoatEarTagDetails.itemaddeddate = DateParser.getDate_and_time_newFormat();
                Modal_Static_GoatEarTagDetails.status = modal_goatEarTagDetails.getStatus();
                Modal_Static_GoatEarTagDetails.gender = modal_goatEarTagDetails.getGender();
                Modal_Static_GoatEarTagDetails.breedtype = modal_goatEarTagDetails.getBreedtype();
                Modal_Static_GoatEarTagDetails.supplierkey = modal_goatEarTagDetails.getSupplierkey();
                Modal_Static_GoatEarTagDetails.suppliername = modal_goatEarTagDetails.getSuppliername();
                Modal_Static_GoatEarTagDetails.description = modal_goatEarTagDetails.getDescription();
                Modal_Static_GoatEarTagDetails.newWeight_forBillingScreen = modal_goatEarTagDetails.getNewWeight_forBillingScreen();
                Modal_Static_GoatEarTagDetails.gradekey = modal_goatEarTagDetails.getGradekey();
                Modal_Static_GoatEarTagDetails.gradename = modal_goatEarTagDetails.getGradename();
                Modal_Static_GoatEarTagDetails.gradeprice = modal_goatEarTagDetails.getGradeprice();

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
                        intent.putExtra("batchno",modal_goatEarTagDetails.getBatchno());
                        intent.putExtra("barcodeno",modal_goatEarTagDetails.getBarcodeno());
                        intent.putExtra("supplierkey",modal_goatEarTagDetails.getSupplierkey());
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
                        Modal_Static_GoatEarTagDetails.currentweightingrams = modal_goatEarTagDetails.getNewWeight_forBillingScreen();

                        goatEarTagItemDetailsList. mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                        goatEarTagItemDetailsList.value_forFragment = mContext.getString(R.string.billing_Screen_editOrder);

                        goatEarTagItemDetailsList.loadMyFragment();
                    }
                }
                else if(CalledFrom.equals(mContext.getString(R.string.billing_Screen))) {
                    if(TaskToDo.equals("ItemInCart")){
                        Modal_Static_GoatEarTagDetails.currentweightingrams = modal_goatEarTagDetails.getNewWeight_forBillingScreen();
                        Modal_Static_GoatEarTagDetails.supplierkey = modal_goatEarTagDetails.getSupplierkey();
                        Modal_Static_GoatEarTagDetails.suppliername = modal_goatEarTagDetails.getSuppliername();
                        goatEarTagItemDetailsList. mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                        goatEarTagItemDetailsList.value_forFragment = mContext.getString(R.string.billing_Screen_editOrder);
                        goatEarTagItemDetailsList.loadMyFragment();
                    }
                }
                else if(CalledFrom.equals(mContext.getString(R.string.placedOrder_Details_Screen))) {
                    if(TaskToDo.equals("ItemInOrderDetails")){
                        Modal_Static_GoatEarTagDetails.currentweightingrams = modal_goatEarTagDetails.getNewWeight_forBillingScreen();
                        Modal_Static_GoatEarTagDetails.supplierkey = modal_goatEarTagDetails.getSupplierkey();
                        Modal_Static_GoatEarTagDetails.suppliername = modal_goatEarTagDetails.getSuppliername();
                        goatEarTagItemDetailsList. mfragment = new BatchItemDetailsFragment_withoutScanBarcode();
                        goatEarTagItemDetailsList.value_forFragment = mContext.getString(R.string.placedOrder_Details_Screen);
                        goatEarTagItemDetailsList.loadMyFragment();
                    }
                }









               /* Intent intent = new Intent(mContext, Audit_UnstockedBatch_item.class);
                intent.putExtra("batchno",modal_goatEarTagDetails.getBatchno());
                intent.putExtra("barcodeno",modal_goatEarTagDetails.getBarcodeno());
                intent.putExtra("supplierkey",modal_goatEarTagDetails.getSupplierkey());
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
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
