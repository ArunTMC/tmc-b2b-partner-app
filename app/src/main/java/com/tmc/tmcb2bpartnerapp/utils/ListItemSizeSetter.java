package com.tmc.tmcb2bpartnerapp.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListItemSizeSetter  {
    public static void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter=myListView.getAdapter();

        if (myListAdapter==null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight=0;
        for (int size=0; size < myListAdapter.getCount(); size++) {
            View listItem=myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            //   //Log.i("height of listItem :", String.valueOf(totalHeight));

                if (totalHeight < 500) {
                    totalHeight += listItem.getMeasuredHeight() ;
                    //          //Log.i("height of listItem: 1  ", String.valueOf(totalHeight));
                }
                else {
                    totalHeight += listItem.getMeasuredHeight() ;
                    //      //Log.i("height of listItem: 1  ", String.valueOf(totalHeight));
                }




        }
        //setting listview item in adapter
        ViewGroup.LayoutParams params=myListView.getLayoutParams();
        params.height=totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        // print height of adapter on log
    }

}
