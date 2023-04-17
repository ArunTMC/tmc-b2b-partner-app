package com.tmc.tmcb2bpartnerapp.interfaces;

import com.android.volley.VolleyError;

public interface  B2BTokenNoManagerInterface {

    void notifySuccess( String result);
    void notifyVolleyError(VolleyError error);
    void notifyProcessingError(Exception error);
}
