package com.tmc.tmcb2bpartnerapp.interfaces;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BOrderDetails;

import java.util.ArrayList;

public interface B2BOrderDetailsInterface {

    void notifySuccessForGettingListItem(ArrayList<Modal_B2BOrderDetails> retailerDetailsArrayList);
    void notifySuccess( String result);
    void notifyVolleyError(VolleyError error);
    void notifyProcessingError(Exception error);

}
