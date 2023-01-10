package com.tmc.tmcb2bpartnerapp.interfaces;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BCartOrderDetails;

import java.util.ArrayList;

public interface B2BCartOrderDetailsInterface {


    void notifySuccessForGettingListItem(ArrayList<Modal_B2BCartOrderDetails> arrayList);
    void notifySuccess( String result);
    void notifyVolleyError(VolleyError error);
    void notifyProcessingError(Exception error);




}
