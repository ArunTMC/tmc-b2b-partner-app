package com.tmc.tmcb2bpartnerapp.interfaces;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartItemDetails;

import java.util.ArrayList;

public interface B2BCartItemDetaillsInterface {



    void notifySuccessForGettingListItem(ArrayList<Modal_B2BCartItemDetails> arrayList);
    void notifySuccess( String result);
    void notifyVolleyError(VolleyError error);
    void notifyProcessingError(Exception error);


}
