package com.tmc.tmcb2bpartnerapp.second_version.interfaces;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal__B2BCreditTransactionHistory;

import java.util.ArrayList;

public interface B2BCreditTransactionHistoryInterface {



    void notifySuccessForGettingListItem( ArrayList<Modal__B2BCreditTransactionHistory> retailerDetailsArrayList);

    void notifySuccess( String result);
    void notifyVolleyError(VolleyError error);
    void notifyProcessingError(Exception error);

}
