package com.tmc.tmcb2bpartnerapp.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tmc.tmcb2bpartnerapp.R;
import com.tmc.tmcb2bpartnerapp.adapter.adapterHelpers.ListData;
import com.tmc.tmcb2bpartnerapp.adapter.adapterHelpers.ListItem;
import com.tmc.tmcb2bpartnerapp.adapter.adapterHelpers.ListSection;

import java.util.List;

public class Adapter_BatchWise_ConsolidatedReport extends BaseAdapter {
    private static final int VIEW_TYPE_NONE = 0;
    private static final int VIEW_TYPE_SECTION = 1;
    private static final int VIEW_TYPE_ITEM = 2;
    private LayoutInflater layoutInflater;
    private List<ListData> dataList;
    int header_count=2;
    Context context;

    public Adapter_BatchWise_ConsolidatedReport(Context context, List<ListData> dataList) {
        this.dataList = dataList;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public int getCount() {
        return dataList != null ? dataList.size() : 0;
    }
    @Override
    public ListData getItem(int position) {
        if (dataList.isEmpty()) {
            return null;
        } else {
            return dataList.get(position);
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == VIEW_TYPE_SECTION) {
            return getSectionView(position, convertView, parent);
        } else if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            return getItemView(position, convertView, parent);
        }
        return null;
    }
    @NonNull
    private View getItemView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder itemViewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_finalbatchwise_consolidatedreport_itemview, parent, false);
            itemViewHolder = new ItemViewHolder(convertView);
            convertView.setTag(itemViewHolder);
        } else {
            itemViewHolder = (ItemViewHolder) convertView.getTag();
        }
        ListItem listItem = (ListItem) getItem(position);
        itemViewHolder.setMessage(listItem.getMessage());
        itemViewHolder.setMessageLine2(listItem.getMessageLine2());

        return convertView;
    }
    @NonNull
    private View getSectionView(int position, View convertView, ViewGroup parent) {
        SectionViewHolder sectionViewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_finalbatchwise_consolidatedreport_headerview, parent, false);
            sectionViewHolder = new SectionViewHolder(convertView);
            convertView.setTag(sectionViewHolder);
        } else {
            sectionViewHolder = (SectionViewHolder) convertView.getTag();
        }
        if(header_count!=position) {
            sectionViewHolder.setTitle(((ListSection) getItem(position)).getTitle());
            sectionViewHolder.setTvtotalAmount(((ListSection) getItem(position)).getTotalAmount());

            header_count = position + 1;
        }
        return convertView;

    }
    @Override
    public int getItemViewType(int position) {
        if (getCount() > 0) {
            ListData listData = getItem(position);

            if (listData instanceof ListSection) {
                return VIEW_TYPE_SECTION;
            } else if (listData instanceof ListItem) {
                return VIEW_TYPE_ITEM;
            } else {
                return VIEW_TYPE_NONE;
            }
        } else {
            return VIEW_TYPE_NONE;
        }
    }
    @Override
    public int getViewTypeCount() {
        return 3;
    }
    class SectionViewHolder {
        TextView tvTitle,tvtotalAmount;
        public SectionViewHolder(View itemView) {
           tvTitle = (TextView) itemView.findViewById(R.id.text_view_title);
           tvtotalAmount = (TextView) itemView.findViewById(R.id.totalWeight);

        }
        public void setTitle(String title) {
            tvTitle.setGravity(Gravity.TOP);

            tvTitle.setText(title);
        }
        public void setTvtotalAmount(String amount) {
            tvtotalAmount.setText(amount);
        }


    }
    class ItemViewHolder {
        TextView tvMessage, tvMessageLine2;
        public ItemViewHolder(View itemView) {
            tvMessage = (TextView) itemView.findViewById(R.id.genderName_textview);
           tvMessageLine2 = (TextView) itemView.findViewById(R.id.weight_textview);




        }

        public void setMessage(String message) {
            tvMessage.setText(message);
        }
        public void setMessageLine2(String messageLine2) {
            tvMessageLine2.setText(messageLine2);
        }
    }
}




