package com.tmc.tmcb2bpartnerapp.model;

import org.json.JSONArray;

public class Modal_B2BItemCtgy {
    public  String key ="" , name =""  ,subctgy_key ="" , subctgy_name = "";
    public static JSONArray subctgydetails = new JSONArray();


    public  String getSubctgy_key() {
        return subctgy_key;
    }

    public  void setSubctgy_key(String subctgy_key) {
        this.subctgy_key = subctgy_key;
    }

    public  String getSubctgy_name() {
        return subctgy_name;
    }

    public  void setSubctgy_name(String subctgy_name) {
        this.subctgy_name = subctgy_name;
    }

    public static JSONArray getSubctgydetails() {
        return subctgydetails;
    }

    public static void setSubctgydetails(JSONArray subctgy_jsonArray) {
        Modal_B2BItemCtgy.subctgydetails = new JSONArray();
        Modal_B2BItemCtgy.subctgydetails = subctgy_jsonArray;
    }

    public  String getKey() {
        return key;
    }

    public  void setKey(String key) {
        this.key = key;
    }

    public  String getName() {
        return name;
    }

    public  void setName(String name) {
        this.name = name;
    }
}
