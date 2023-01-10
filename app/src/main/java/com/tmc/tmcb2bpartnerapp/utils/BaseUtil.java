package com.tmc.tmcb2bpartnerapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class BaseUtil {
    private Context context;
    private SharedPreferences pref;

    public BaseUtil(Context context) {
        this.context = context;
        pref = context.getSharedPreferences("SettingsPrefs", 0); //NO I18N
    }

    public void setLastAccessedTime(long time) {
        SharedPreferences.Editor edit = pref.edit();
        edit.putLong("lastaccessedtime", time);
        edit.commit();
    }

    public long getLastAccessedTime() {
        return pref.getLong("lastaccessedtime", 0);
    }

}
