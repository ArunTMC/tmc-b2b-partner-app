package com.tmc.tmcb2bpartnerapp.second_version.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.activity.GoatEarTagItemDetailsList;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetails;
import com.tmc.tmcb2bpartnerapp.modal.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.second_version.activity.ViewStockBalance;
import com.tmc.tmcb2bpartnerapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Viewstockbalance_batchwise extends ArrayAdapter<Modal_B2BBatchDetails> {



    Context mContext;
    List<Modal_B2BBatchDetails> batchDetailsList;
    String calledFrom = "";
    ViewStockBalance viewStockBalance;
     ArrayList<Modal_GoatEarTagDetails> earTagDetailsList = new ArrayList<>();
    int totalItemCount = 0 ,  totalSoldItemCount = 0  ,totalUnsoldItemCount = 0;


    public Adapter_Viewstockbalance_batchwise(Context mContext, List<Modal_B2BBatchDetails> batchDetailsListt, ViewStockBalance viewStockBalance, String calledFrom) {
        super(mContext, R.layout.adapter_batch_item_list, batchDetailsListt);
        this.mContext=mContext;
        this.batchDetailsList=batchDetailsListt;
        this.viewStockBalance = viewStockBalance;
        this.calledFrom = calledFrom;
    }

    public Adapter_Viewstockbalance_batchwise(Context mContext, ArrayList<Modal_B2BBatchDetails> batchDetailsListt, ViewStockBalance viewStockBalance1, String calledFrom, ArrayList<Modal_GoatEarTagDetails> earTagDetailsListt) {
        super(mContext, R.layout.adapter_batch_item_list, batchDetailsListt);
        this.mContext=mContext;
        this.batchDetailsList=batchDetailsListt;
        this.viewStockBalance = viewStockBalance1;
        this.calledFrom = calledFrom;
        this.earTagDetailsList = earTagDetailsListt;

    }

    @Nullable
    @Override
    public Modal_B2BBatchDetails getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable Modal_B2BBatchDetails item) {
        return super.getPosition(item);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder") View listViewItem = null;
        listViewItem = LayoutInflater.from(mContext).inflate(R.layout.view_stockbalance_batchwise_listitem, (ViewGroup) view, false);


        TextView batchNo_textview = listViewItem.findViewById(R.id.batchNo_textview);
        TextView sentdate_textview = listViewItem.findViewById(R.id.sentdate_textview);
        TextView totalCount_textview = listViewItem.findViewById(R.id.totalCount_textview);
        TextView soldCount_textview = listViewItem.findViewById(R.id.soldCount_textview);
        TextView unsoldCount_textview = listViewItem.findViewById(R.id.unsoldCount_textview);

        TableLayout tableLayout = listViewItem.findViewById(R.id.tableLayout);
        TextView batchNo_textview_table = listViewItem.findViewById(R.id.batchNo_textview_table);
        TextView sentdate_textview_table = listViewItem.findViewById(R.id.sentDate_textview_table);
        TextView totalCount_textview_table = listViewItem.findViewById(R.id.totalCount_textview_table);
        TextView soldCount_textview_table = listViewItem.findViewById(R.id.soldCount_textview_table);
        TextView unsoldCount_textview_table = listViewItem.findViewById(R.id.unsoldCount_textview_table);




        Button viewsoldItem_Button = listViewItem.findViewById(R.id.viewsoldItem_Button);
        Button viewUnSoldItem_Button = listViewItem.findViewById(R.id.viewUnSoldItem_Button);



        Modal_B2BBatchDetails modal_b2BBatchDetails = batchDetailsList.get(position);

        batchNo_textview_table.setText(String.valueOf(modal_b2BBatchDetails.getBatchno()));
        sentdate_textview_table.setText(String.valueOf(modal_b2BBatchDetails.getSentdate()));
        batchNo_textview.setText(String.valueOf(modal_b2BBatchDetails.getBatchno()));
        sentdate_textview.setText(String.valueOf(modal_b2BBatchDetails.getSentdate()));


        viewsoldItem_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, GoatEarTagItemDetailsList.class);
                i.putExtra("TaskToDo", mContext.getString(R.string.view_sold_items_in_batch));
                i.putExtra("batchno",  batchDetailsList.get(position).getBatchno());
                i.putExtra("CalledFrom",Constants.viewStockBalance);
                mContext.startActivity(i);
            }
        });

        tableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, GoatEarTagItemDetailsList.class);
                i.putExtra("TaskToDo", mContext.getString(R.string.view_unsold_items_in_batch));
                i.putExtra("batchno",  batchDetailsList.get(position).getBatchno());
                i.putExtra("CalledFrom",Constants.viewStockBalance);
                mContext.startActivity(i);
            }
        });

        viewUnSoldItem_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, GoatEarTagItemDetailsList.class);
                i.putExtra("TaskToDo", mContext.getString(R.string.view_unsold_items_in_batch));
                i.putExtra("batchno",  batchDetailsList.get(position).getBatchno());
                i.putExtra("CalledFrom",Constants.viewStockBalance);
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

                totalCount_textview_table.setText(String.valueOf(totalItemCount));
                soldCount_textview_table.setText(String.valueOf(totalSoldItemCount));
                unsoldCount_textview_table.setText(String.valueOf(totalUnsoldItemCount));

                totalCount_textview.setText(String.valueOf(totalItemCount));
                soldCount_textview.setText(String.valueOf(totalSoldItemCount));
                unsoldCount_textview.setText(String.valueOf(totalUnsoldItemCount));

            }
            }







        return listViewItem;
    }
}
