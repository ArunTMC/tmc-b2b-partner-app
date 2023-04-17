package com.tmc.tmcb2bpartnerapp.second_version.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.second_version.activity.MarkDeliveredGoats_in_an_Order;
import com.tmc.tmcb2bpartnerapp.second_version.activity.PlaceNewOrder_activity;
import com.tmc.tmcb2bpartnerapp.utils.Constants;

import java.util.ArrayList;

public class Adapter_PlaceOrder_recyclerView extends RecyclerView.Adapter<Adapter_PlaceOrder_recyclerView.MyViewHolder> {

    private ArrayList<Modal_GoatEarTagDetails> earTagDetailsList;
    PlaceNewOrder_activity placeNewOrder_activity;
    Context mContext;
    boolean isViewLongClicked = false;
    String calledFrom = "";


    public Adapter_PlaceOrder_recyclerView(ArrayList<Modal_GoatEarTagDetails> modelList, PlaceNewOrder_activity placeNewOrder_activity ,Context context) {
        earTagDetailsList = modelList;
        this.mContext = context;
        this.placeNewOrder_activity = placeNewOrder_activity;
        this.calledFrom = "placeorderscreen";
    }

    public Adapter_PlaceOrder_recyclerView(ArrayList<Modal_B2BOrderItemDetails> orderItemDetailsArrayList, MarkDeliveredGoats_in_an_Order markDeliveredGoats_in_an_order, MarkDeliveredGoats_in_an_Order context) {
        this.calledFrom = "markitemasdeliveredscreen";


    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Modal_GoatEarTagDetails model = earTagDetailsList.get(position);



        holder.textView.setText(model.getBarcodeno());
        holder.constraintlayout.setBackgroundColor(model.isSelected() ? Color.parseColor("#FFBB86FC")  : Color.WHITE);

        if (model.isSelected()) {

            placeNewOrder_activity.UpdateValueInBatchWiseArray(model.getBatchno() ,model.getBarcodeno() , false);

        }


        holder.constraintlayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                isViewLongClicked = true;
               // Toast.makeText(mContext, "LongClick", Toast.LENGTH_LONG).show();
                placeNewOrder_activity.ShowGoatItemDetailsDialog(earTagDetailsList.get(position));
                isViewLongClicked = false;
                return true;
            }
        });



        holder.constraintlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

           //     if(!isViewLongClicked) {
                    model.setSelected(!model.isSelected());
                    holder.constraintlayout.setBackgroundColor(model.isSelected() ? Color.parseColor("#FFBB86FC") : Color.WHITE);
                    if (model.isSelected()) {
                        if (placeNewOrder_activity.cartItemDetails_Hashmap.size() == 0) {

                            if (!placeNewOrder_activity.isshowCartItemDetails) {
                                placeNewOrder_activity.Call_And_Execute_B2BCartOrder_Details(Constants.CallADDMethod, false);
                            }
                            placeNewOrder_activity. itemCountDetails2_LinearLayout.setVisibility(View.GONE);

                        }
                        placeNewOrder_activity.UpdateValueInBatchWiseArray(model.getBatchno() ,model.getBarcodeno() , false);
                        placeNewOrder_activity.Call_And_Execute_B2BCartOrderItem_Details(Constants.CallADDMethod, model.getBarcodeno(), model);

                    }
                    else {
                        if (placeNewOrder_activity.cartItemDetails_Hashmap.size() == 1) {

                            placeNewOrder_activity.isshowCartItemDetails = false;
                            placeNewOrder_activity. itemCountDetails2_LinearLayout.setVisibility(View.VISIBLE);

                            placeNewOrder_activity.Call_And_Execute_B2BCartOrder_Details(Constants.CallDELETEMethod, false);
                        }


                        placeNewOrder_activity.Call_And_Execute_B2BCartOrderItem_Details(Constants.CallDELETEMethod, model.getBarcodeno(), model);
                        placeNewOrder_activity.UpdateValueInBatchWiseArray(model.getBatchno() ,model.getBarcodeno() , true);
                    }
              /*  }
                else{
                    isViewLongClicked = false;
                }

               */



            }
        });
    }

    @Override
    public int getItemCount() {
        return earTagDetailsList == null ? 0 : earTagDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView textView;
        CardView cardview;
        ConstraintLayout constraintlayout;
        ImageView closeButton_icon;
        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            constraintlayout = itemView.findViewById(R.id.constraintlayout);
            //  cardview = (CardView) itemView.findViewById(R.id.cardview);
            textView = (TextView) itemView.findViewById(R.id.text_view);
            ///closeButton_icon = itemView.findViewById(R.id.closeButton_icon);
        }
    }
}