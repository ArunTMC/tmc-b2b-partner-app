package com.tmc.tmcb2bpartnerapp.interfaces;

import com.android.volley.VolleyError;

public interface AppUserAccessInterface {



    public void notifySuccess( String result);
    public void notifyError( VolleyError error);


}
