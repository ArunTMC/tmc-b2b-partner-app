package com.tmc.tmcb2bpartnerapp.interfaces;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.model.Modal_GoatEarTagDetails;

import java.util.ArrayList;

public interface B2BOrderItemDetails_BulkUpdateInterface {


    void notifySuccess( String result);
    void notifySuccessForGettingListItem( ArrayList<Modal_GoatEarTagDetails> earTagItemsForBatch);
    void notifyVolleyError(VolleyError error);
    void notifyProcessingError(Exception error);





}
