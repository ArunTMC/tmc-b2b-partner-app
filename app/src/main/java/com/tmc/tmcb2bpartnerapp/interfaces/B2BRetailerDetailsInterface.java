package com.tmc.tmcb2bpartnerapp.interfaces;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_SupplierDetails;

import java.util.ArrayList;

public interface B2BRetailerDetailsInterface {

    void notifySuccessForGettingListItem( ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList);

    void notifySuccess( String result);
    void notifyVolleyError(VolleyError error);
    void notifyProcessingError(Exception error);




}
