package com.tmc.tmcb2bpartnerapp.second_version.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderItemDetails;
import com.tmc.tmcb2bpartnerapp.second_version.activity.MarkDeliveredGoats_in_an_Order;

import java.util.ArrayList;

public class Adapter_Mark_item_as_delivered_recyclerView extends RecyclerView.Adapter<Adapter_Mark_item_as_delivered_recyclerView.MyViewHolder> {

    private ArrayList<Modal_B2BOrderItemDetails> earTagDetailsList;
    MarkDeliveredGoats_in_an_Order markDeliveredGoats_in_an_order;
    Context mContext;
    boolean isViewLongClicked = false;
    String calledFrom = "";


    

    public Adapter_Mark_item_as_delivered_recyclerView(ArrayList<Modal_B2BOrderItemDetails> orderItemDetailsArrayList, MarkDeliveredGoats_in_an_Order markDeliveredGoats_in_an_order, MarkDeliveredGoats_in_an_Order context) {
        this.calledFrom = "markitemasdeliveredscreen";
        earTagDetailsList = orderItemDetailsArrayList;
        this.mContext = context;
        this.markDeliveredGoats_in_an_order = markDeliveredGoats_in_an_order;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Modal_B2BOrderItemDetails model = earTagDetailsList.get(position);

        holder.textView.setVisibility(model.isItemAlreadyMarkedDelivered() ? View.GONE  : View.VISIBLE);
        holder.overlaptextview.setVisibility(model.isItemAlreadyMarkedDelivered() ? View.VISIBLE  : View.GONE);

        holder.overlaptextview.setText(model.getBarcodeno());
        holder.textView.setText(model.getBarcodeno());
        holder.constraintlayout.setBackgroundColor(model.isItemAlreadyMarkedDelivered() ? Color.parseColor("#F1A1A1A1")  : Color.WHITE);

        if(!model.isItemAlreadyMarkedDelivered()){
            holder.constraintlayout.setBackgroundColor(model.isItemNowMarkedforDelivered() ? Color.parseColor("#FFBB86FC")  : Color.WHITE);

        }


        holder.constraintlayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                isViewLongClicked = true;
                // Toast.makeText(mContext, "LongClick", Toast.LENGTH_LONG).show();
                markDeliveredGoats_in_an_order.ShowGoatItemDetailsDialog(earTagDetailsList.get(position));
                isViewLongClicked = false;
                return true;
            }
        });



        holder.constraintlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modal_B2BOrderItemDetails model = earTagDetailsList.get(position);
                if(!earTagDetailsList.get(position).isItemAlreadyMarkedDelivered) {
                    if(markDeliveredGoats_in_an_order.checkBox_selectAllitems.isChecked()){
                        markDeliveredGoats_in_an_order.isSelectAllCheckBoxListenerEnabled = false;
                        markDeliveredGoats_in_an_order.checkBox_selectAllitems.setChecked(false);
                    }


                    if(model.isItemNowMarkedforDelivered()){
                        if(markDeliveredGoats_in_an_order.currentlymarkedItemCountForCheckbox>0) {
                            markDeliveredGoats_in_an_order.currentlymarkedItemCountForCheckbox = markDeliveredGoats_in_an_order.currentlymarkedItemCountForCheckbox - 1;
                        }
                        }
                    else{
                        markDeliveredGoats_in_an_order.currentlymarkedItemCountForCheckbox =  markDeliveredGoats_in_an_order.currentlymarkedItemCountForCheckbox  + 1;

                    }

                    if( markDeliveredGoats_in_an_order.currentlymarkedItemCountForCheckbox == markDeliveredGoats_in_an_order.orderwise_notdeliveredItemDetailsArrayList.size()){
                        markDeliveredGoats_in_an_order.isSelectAllCheckBoxListenerEnabled = false;
                        markDeliveredGoats_in_an_order.checkBox_selectAllitems.setChecked(true);
                    }

                model.setItemNowMarkedforDelivered(!model.isItemNowMarkedforDelivered());
                holder.constraintlayout.setBackgroundColor(model.isItemNowMarkedforDelivered() ? Color.parseColor("#FFBB86FC") : Color.WHITE);

                }


               


            }
        });
    }

    @Override
    public int getItemCount() {
        return earTagDetailsList == null ? 0 : earTagDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView textView , overlaptextview;
        CardView cardview;
        ConstraintLayout constraintlayout;
        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            constraintlayout = itemView.findViewById(R.id.constraintlayout);
            //  cardview = (CardView) itemView.findViewById(R.id.cardview);
            textView = (TextView) itemView.findViewById(R.id.text_view);
            overlaptextview = (TextView) itemView.findViewById(R.id.overlaptextview);
        }
    }
}