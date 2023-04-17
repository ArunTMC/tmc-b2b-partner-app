package com.tmc.tmcb2bpartnerapp.adapter;

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
import com.tmc.tmcb2bpartnerapp.modal.Model_Test;

import java.util.List;

public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestRecyclerViewAdapter.MyViewHolder> {

    private List<Model_Test> mModelList;

    public TestRecyclerViewAdapter(List<Model_Test> modelList) {
        mModelList = modelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Model_Test model = mModelList.get(position);
        holder.textView.setText(model.getText());
        holder.constraintlayout.setBackgroundColor(model.isSelected() ? Color.parseColor("#FFBB86FC")  : Color.WHITE);



        holder.constraintlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setSelected(!model.isSelected());
                holder.constraintlayout.setBackgroundColor(model.isSelected() ? Color.parseColor("#FFBB86FC") : Color.WHITE);
                if(model.isSelected()){
                    holder.textView.setPadding(0,0,0,0);
                }
                else{
                    holder.textView.setPadding(0,0,0,0);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
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