package com.tmc.tmcb2bpartnerapp.interfaces;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BBatchDetails;


import java.util.ArrayList;

public interface B2BBatchDetailsInterface {
    void notifySuccessForGettingListItem( ArrayList<Modal_B2BBatchDetails> batchDetailsArrayList);

    void notifySuccess( String result);
    void notifyVolleyError(VolleyError error);
    void notifyProcessingError(Exception error);
}
