package com.tmc.tmcb2bpartnerapp.second_version.interfaces;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal__B2BPaymentDetails;

import java.util.ArrayList;

public interface B2BPaymentDetailsInterface {




    void notifySuccessForGettingListItem( ArrayList<Modal__B2BPaymentDetails> retailerDetailsArrayList);

    void notifySuccess( String result);
    void notifyVolleyError(VolleyError error);
    void notifyProcessingError(Exception error);
}
