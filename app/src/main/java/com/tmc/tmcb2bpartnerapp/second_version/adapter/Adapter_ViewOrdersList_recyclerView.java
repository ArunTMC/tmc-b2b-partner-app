package com.tmc.tmcb2bpartnerapp.second_version.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.PlacedOrderDetailsScreen;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderDetails;
import com.tmc.tmcb2bpartnerapp.second_version.activity.MarkDeliveredGoats_in_an_Order;
import com.tmc.tmcb2bpartnerapp.second_version.activity.ViewOrderList;
import com.tmc.tmcb2bpartnerapp.utils.Constants;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Adapter_ViewOrdersList_recyclerView extends RecyclerView.Adapter<Adapter_ViewOrdersList_recyclerView.MyViewHolder> {

    Context mContext;
    String calledFrom = "";
    ArrayList<Modal_B2BOrderDetails> orderDetailsArrayList = new ArrayList<>();
    ViewOrderList viewOrderList;
    public Adapter_ViewOrdersList_recyclerView(ArrayList<Modal_B2BOrderDetails> modelList, ViewOrderList viewOrderListt , Context context) {
        orderDetailsArrayList = modelList;
        this.mContext = context;
        this.viewOrderList = viewOrderListt;
        this.calledFrom = "markasdeliveredorderscreen";
    }

    public Adapter_ViewOrdersList_recyclerView(ViewOrderList viewOrderList, ArrayList<Modal_B2BOrderDetails> orderDetailsArrayListt, ViewOrderList viewOrderList1, String calledfrom, Context context) {
        this.  orderDetailsArrayList = orderDetailsArrayListt;
        this.mContext = context;
        this.viewOrderList = viewOrderList;
        this.calledFrom = calledfrom;
    }


    @NonNull
    @Override
    public Adapter_ViewOrdersList_recyclerView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_order_details_item_list, parent, false);
        return new Adapter_ViewOrdersList_recyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        Modal_B2BOrderDetails modal_b2BOrderDetails  = orderDetailsArrayList.get(position);

      holder.generatePdfIcon.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(!viewOrderList.isGeneratePDFCalled) {
                  viewOrderList.isGeneratePDFCalled = true;
                  Modal_B2BOrderDetails modal_b2BOrderDetails = orderDetailsArrayList.get(position);
                //  Toast.makeText(mContext, "Orderid " + modal_b2BOrderDetails.getOrderid(), Toast.LENGTH_SHORT).show();
                  viewOrderList.call_and_ExecuteCreditTransactionHistory(Constants.CallGETMethod,modal_b2BOrderDetails);

              }
              else{
                  Toast.makeText(mContext, "Please wait PDF is Generating " + modal_b2BOrderDetails.getOrderid(), Toast.LENGTH_SHORT).show();

              }
              }

      });



       holder. parent_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    double payableAmount = 0 , discountAmount = 0, priceWithDiscount = 0;
                    try{
                        String text =  String.valueOf(modal_b2BOrderDetails.getTotalPrice());
                        text = text.replaceAll("[^\\d.]", "");
                        if(text.equals("")){
                            text = "0";
                        }
                        payableAmount  = Double.parseDouble(text);


                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    try{
                        String text =  String.valueOf(modal_b2BOrderDetails.getDiscountamount());
                        text = text.replaceAll("[^\\d.]", "");
                        if(text.equals("")){
                            text = "0";
                        }
                        discountAmount  = Double.parseDouble(text);


                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }



                    try{

                        priceWithDiscount  = discountAmount + payableAmount ;

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }




                    if(calledFrom.equals("datewiseorderslistscreen")){
                        Intent i = new Intent(mContext, PlacedOrderDetailsScreen.class);
                        i.putExtra("orderdetailsPojoClass", orderDetailsArrayList.get(position));

                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);

                    }
                    else if(calledFrom.equals("markasdeliveredorderscreen")){

                        Intent i = new Intent(mContext, MarkDeliveredGoats_in_an_Order.class);
                        i.putExtra("orderdetailsPojoClass", orderDetailsArrayList.get(position));

                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                    }
                    else{
                        Intent i = new Intent(mContext, PlacedOrderDetailsScreen.class);
                        i.putExtra("orderdetailsPojoClass", orderDetailsArrayList.get(position));

                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);

                    }






                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });






        double payableAmount = 0 , discountAmount = 0, priceWithDiscount = 0;
        try{
            String text =  String.valueOf(modal_b2BOrderDetails.getTotalPrice());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }
            payableAmount  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{
            String text =  String.valueOf(modal_b2BOrderDetails.getDiscountamount());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }
            discountAmount  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }



        try{

            priceWithDiscount  = discountAmount + payableAmount ;

        }
        catch (Exception e){
            e.printStackTrace();
        }






        holder.supervisorMobileNo_textview.setText(modal_b2BOrderDetails.getSupervisormobileno());
       holder.supervisorName_textview.setText(modal_b2BOrderDetails.getSupervisorname());
       holder.total_weight_textview.setText(modal_b2BOrderDetails.getTotalweight()+" Kg");
       holder.female_weight_textview.setText(modal_b2BOrderDetails.getTotalfemaleweight()+" Kg");
       holder.male_weight_textview.setText(modal_b2BOrderDetails.getTotalmaleweight()+" Kg");
       holder.male_quantity_textview.setText(modal_b2BOrderDetails.getTotalmalequantity());
       holder.female_quantity_textview.setText(modal_b2BOrderDetails.getTotalfemalequantity());
       holder.total_qty_textview.setText(modal_b2BOrderDetails.getTotalquantity());


        holder.supervisorName_textview.setText(String.valueOf(position));

        holder.tokenNo_textview.setText(modal_b2BOrderDetails.getBillno());
       holder.orderid_textview.setText(modal_b2BOrderDetails.getOrderid());
       holder.retailername_textview.setText(modal_b2BOrderDetails.getRetailername());
       holder.retailer_mobileno_textview.setText(modal_b2BOrderDetails.getRetailermobileno());
       holder.orderplacedtime_textview.setText(modal_b2BOrderDetails.getOrderplaceddate());
       holder.paymentmode_textview.setText(modal_b2BOrderDetails.getPaymentmode());
       holder.total_qty_textview.setText(modal_b2BOrderDetails.getTotalquantity());
       holder.payableAmount_textview.setText("Rs. "+modal_b2BOrderDetails.getTotalPrice() );
       holder.discount_textview .setText("Rs. "+discountAmount );
       holder.priceWithDiscount_textview .setText("Rs. "+String.valueOf(priceWithDiscount) );




    }

   



    @Override
    public int getItemCount() {
        return orderDetailsArrayList == null ? 0 : orderDetailsArrayList.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView orderid_textview                       ;
        TextView tokenNo_textview                       ;
        TextView retailername_textview                  ;
        TextView retailer_mobileno_textview             ;
        TextView orderplacedtime_textview               ;
        TextView payableAmount_textview                 ;
        TextView paymentmode_textview                   ;
        TextView total_qty_textview                     ;
        TextView female_quantity_textview               ;
        TextView male_quantity_textview                 ;
        TextView male_weight_textview                   ;
        TextView female_weight_textview                 ;
        TextView total_weight_textview                  ;
        TextView priceWithDiscount_textview             ; 
        TextView discount_textview                      ;
        TextView supervisorName_textview                ;
        TextView supervisorMobileNo_textview            ;
        LinearLayout payableAmount_parentLinearLayout   ;
        LinearLayout supervisorNameParentLinearLayout   ;
        LinearLayout totalCountParentLinearLayout       ;
        LinearLayout supervisorMobileNoPaLinearLayout   ;
        LinearLayout generatePdfIcon                    ;
        LinearLayout orderid_parentLayout               ;
        CardView parent_card_view                       ;

        private MyViewHolder(View itemView) {
            super(itemView);
            orderid_parentLayout = itemView.findViewById(R.id.orderid_parentLayout);
            generatePdfIcon = itemView.findViewById(R.id.generatePdfIcon);
           tokenNo_textview = itemView.findViewById(R.id.tokenNo_textview);
           parent_card_view = itemView.findViewById(R.id.parent_card_view);
           orderid_textview = itemView.findViewById(R.id.orderid_textview);
           retailername_textview = itemView.findViewById(R.id.retailername_textview);
           retailer_mobileno_textview = itemView.findViewById(R.id.retailer_mobileno_textview);
           orderplacedtime_textview = itemView.findViewById(R.id.orderplacedtime_textview);
           payableAmount_textview = itemView.findViewById(R.id.payableAmount_textview);
           paymentmode_textview = itemView.findViewById(R.id.paymentmode_textview);
           total_qty_textview = itemView.findViewById(R.id.total_qty_textview);
           female_quantity_textview = itemView.findViewById(R.id.female_quantity_textview);
           male_quantity_textview = itemView.findViewById(R.id.male_quantity_textview);
           male_weight_textview = itemView.findViewById(R.id.male_weight_textview);
           female_weight_textview = itemView.findViewById(R.id.female_weight_textview);
           total_weight_textview = itemView.findViewById(R.id.total_weight_textview);
           priceWithDiscount_textview = itemView.findViewById(R.id.priceWithDiscount_textview);
           discount_textview = itemView.findViewById(R.id.discount_textview);
           supervisorName_textview = itemView.findViewById(R.id.supervisorName_textview);
           supervisorMobileNo_textview = itemView.findViewById(R.id.supervisorMobileNo_textview);


            payableAmount_parentLinearLayout = itemView.findViewById(R.id.payableAmount_parentlayout);
            supervisorNameParentLinearLayout = itemView.findViewById(R.id.supervisorNameParentlayout);
            totalCountParentLinearLayout = itemView.findViewById(R.id.totalCountParentLayout);
            supervisorMobileNoPaLinearLayout = itemView.findViewById(R.id.supervisorMobileNoParentlayot);

            SharedPreferences sh = mContext.getSharedPreferences("LoginData",MODE_PRIVATE);
           String usermobileno_string = sh.getString("UserMobileNumber","");
            generatePdfIcon.setVisibility(View.VISIBLE);
            if(usermobileno_string.equals("+919597580128")){
                orderid_parentLayout.setVisibility(View.GONE );
            }
            else{
                orderid_parentLayout.setVisibility(View.GONE);
            }
            if(calledFrom.equals("datewiseorderslistscreen")){

                payableAmount_parentLinearLayout.setVisibility(View.VISIBLE);
                supervisorNameParentLinearLayout.setVisibility(View.VISIBLE);
                totalCountParentLinearLayout.setVisibility(View.VISIBLE);
                supervisorMobileNoPaLinearLayout.setVisibility(View.VISIBLE);

            }
            else if(calledFrom.equals("markasdeliveredorderscreen")){

                payableAmount_parentLinearLayout.setVisibility(View.GONE);
                supervisorNameParentLinearLayout.setVisibility(View.GONE);
                totalCountParentLinearLayout.setVisibility(View.GONE);
                supervisorMobileNoPaLinearLayout.setVisibility(View.GONE);
            }
            else{

                payableAmount_parentLinearLayout.setVisibility(View.VISIBLE);
                supervisorNameParentLinearLayout.setVisibility(View.VISIBLE);
                totalCountParentLinearLayout.setVisibility(View.VISIBLE);
                supervisorMobileNoPaLinearLayout.setVisibility(View.VISIBLE);
            }










        }
    }



    }
