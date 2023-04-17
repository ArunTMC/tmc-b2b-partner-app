package com.tmc.tmcb2bpartnerapp.second_version.interfaces;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.second_version.Modal.Modal_B2BRetailerCreditDetails;

import java.util.ArrayList;

public interface B2BRetailerCreditDetailsInterface {


    void notifySuccessForGettingListItem( ArrayList<Modal_B2BRetailerCreditDetails> retailerDetailsArrayList);

    void notifySuccess( String result);
    void notifyVolleyError(VolleyError error);
    void notifyProcessingError(Exception error);


}
