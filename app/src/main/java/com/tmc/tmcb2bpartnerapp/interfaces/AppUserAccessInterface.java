package com.tmc.tmcb2bpartnerapp.interfaces;

import com.android.volley.VolleyError;
import com.tmc.tmcb2bpartnerapp.model.Modal_AppUserAccess;

import java.util.List;

public interface AppUserAccessInterface {



    public void notifySuccess( String result);
    public void notifyError( VolleyError error);


}
