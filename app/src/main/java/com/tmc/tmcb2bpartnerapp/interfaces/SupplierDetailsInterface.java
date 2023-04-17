package com.tmc.tmcb2bpartnerapp.interfaces;

import com.android.volley.VolleyError;

import com.tmc.tmcb2bpartnerapp.modal.Modal_SupplierDetails;

import java.util.ArrayList;

public interface SupplierDetailsInterface {

    void notifySuccessForGettingListItem( ArrayList<Modal_SupplierDetails> supplierDetailsArrayList);

     void notifySuccess( String result);
     void notifyVolleyError(VolleyError error);
     void notifyProcessingError(Exception error);


}
