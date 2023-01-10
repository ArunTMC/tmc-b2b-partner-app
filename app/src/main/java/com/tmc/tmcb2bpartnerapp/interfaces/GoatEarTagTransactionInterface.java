package com.tmc.tmcb2bpartnerapp.interfaces;

import com.android.volley.VolleyError;

public interface GoatEarTagTransactionInterface {
    void notifySuccess( String result);
    void notifyVolleyError(VolleyError error);
    void notifyProcessingError(Exception error);
}
