package com.tmc.tmcb2bpartnerapp.second_version.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.GoatEarTagItemDetailsList;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.second_version.activity.ViewStockBalance;
import com.tmc.tmcb2bpartnerapp.utils.Constants;
import com.tmc.tmcb2bpartnerapp.utils.DateParser;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Viewstockbalance_batchwise_Recyclerview extends RecyclerView.Adapter<Adapter_Viewstockbalance_batchwise_Recyclerview.MyViewHolder> {

    Context mContext;
    List<Modal_B2BBatchDetails> batchDetailsList;
    String calledFrom = "";
    ViewStockBalance viewStockBalance;
    ArrayList<Modal_GoatEarTagDetails> earTagDetailsList = new ArrayList<>();
    int totalItemCount = 0 ,  totalSoldItemCount = 0  ,totalUnsoldItemCount = 0;



    public Adapter_Viewstockbalance_batchwise_Recyclerview(Context mContext, ArrayList<Modal_B2BBatchDetails> batchDetailsListt, ViewStockBalance viewStockBalance1, String calledFrom, ArrayList<Modal_GoatEarTagDetails> earTagDetailsListt) {
        this.mContext=mContext;
        this.batchDetailsList=batchDetailsListt;
        this.viewStockBalance = viewStockBalance1;
        this.calledFrom = calledFrom;
        this.earTagDetailsList = earTagDetailsListt;

    }










    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_stockbalance_batchwise_listitem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Modal_B2BBatchDetails modal_b2BBatchDetails = batchDetailsList.get(position);

        holder. batchNo_textview_table.setText(String.valueOf(modal_b2BBatchDetails.getBatchno()));
        String sentDate = String.valueOf(modal_b2BBatchDetails.getSentdate());

       String sentDate_withoutSeconds = DateParser.convertDateTimeFormatWithoutSeconds(sentDate);
        //holder. sentdate_textview_table.setText(String.valueOf(modal_b2BBatchDetails.getSentdate()));
        holder. batchNo_textview.setText(String.valueOf(modal_b2BBatchDetails.getBatchno()));
        holder.  sentdate_textview_table.setText(String.valueOf(sentDate_withoutSeconds));
        holder. sentdate_textview.setText(String.valueOf(modal_b2BBatchDetails.getSentdate()));



        holder. tableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, GoatEarTagItemDetailsList.class);
                i.putExtra("TaskToDo", mContext.getString(R.string.view_unsold_items_in_batch));
                i.putExtra("batchno",  batchDetailsList.get(position).getBatchno());
                i.putExtra("CalledFrom", Constants.viewStockBalance);
                mContext.startActivity(i);
            }
        });

        totalItemCount = 0 ;  totalSoldItemCount = 0  ;totalUnsoldItemCount = 0;


        for(int iteratorEarTagList = 0 ; iteratorEarTagList < earTagDetailsList.size(); iteratorEarTagList++){
            Modal_GoatEarTagDetails modal_goatEarTagDetails = earTagDetailsList.get(iteratorEarTagList);

            if(modal_goatEarTagDetails.getBatchno().toUpperCase().equals(modal_b2BBatchDetails.getBatchno().toUpperCase())){


                try{
                    totalItemCount   =  totalItemCount +1;

                    if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Reviewed_and_READYFORSALE ) || modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatsick )){
                        totalUnsoldItemCount = totalUnsoldItemCount +1;
                    }
                    else  if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Sold)){
                        totalSoldItemCount = totalSoldItemCount+1;
                    }
                    else  if(modal_goatEarTagDetails.getStatus().toUpperCase().equals(Constants.goatEarTagStatus_Goatdead)){
                        totalSoldItemCount = totalSoldItemCount+1;
                    }
                    else{

                    }


                }
                catch (Exception e){
                    e.printStackTrace();
                }





            }

            if(iteratorEarTagList == (earTagDetailsList.size() - 1)){

                holder.    totalCount_textview_table.setText(String.valueOf(totalItemCount));
                holder.   soldCount_textview_table.setText(String.valueOf(totalSoldItemCount));
                holder. unsoldCount_textview_table.setText(String.valueOf(totalUnsoldItemCount));

                holder.  totalCount_textview.setText(String.valueOf(totalItemCount));
                holder. soldCount_textview.setText(String.valueOf(totalSoldItemCount));
                holder. unsoldCount_textview.setText(String.valueOf(totalUnsoldItemCount));

            }
        }



    }


    @Override
    public int getItemCount() {
        return batchDetailsList == null ? 0 : batchDetailsList.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView batchNo_textview    ;
        TextView sentdate_textview   ;
        TextView totalCount_textview ;
        TextView soldCount_textview   ;
        TextView unsoldCount_textview ;

        TableLayout tableLayout             ;
        TextView batchNo_textview_table     ;
        TextView sentdate_textview_table    ;
        TextView totalCount_textview_table  ;
        TextView soldCount_textview_table   ;
        TextView unsoldCount_textview_table ;

        Button viewsoldItem_Button ;
        Button viewUnSoldItem_Button ;



        private MyViewHolder(View itemView) {
            super(itemView);



    batchNo_textview    = itemView.findViewById(R.id.batchNo_textview);
    sentdate_textview   = itemView.findViewById(R.id.sentdate_textview);
    totalCount_textview = itemView.findViewById(R.id.totalCount_textview);
    soldCount_textview  = itemView.findViewById(R.id.soldCount_textview);
    unsoldCount_textview = itemView.findViewById(R.id.unsoldCount_textview);
     tableLayout             = itemView.findViewById(R.id.tableLayout);
     batchNo_textview_table     = itemView.findViewById(R.id.batchNo_textview_table);
     sentdate_textview_table    = itemView.findViewById(R.id.sentDate_textview_table);
     totalCount_textview_table  = itemView.findViewById(R.id.totalCount_textview_table);
     soldCount_textview_table   = itemView.findViewById(R.id.soldCount_textview_table);
     unsoldCount_textview_table = itemView.findViewById(R.id.unsoldCount_textview_table);

            viewsoldItem_Button =   itemView.findViewById(R.id.viewsoldItem_Button);
            viewUnSoldItem_Button  = itemView.findViewById(R.id.viewUnSoldItem_Button);












        }
    }






}
