package com.tmc.tmcb2bpartnerapp.modal;

import org.json.JSONArray;

public class Modal_B2BItemCtgy {
    public  String key ="" , name =""  ,subctgy_key ="" , subctgy_name = "" , gender_key = "" , gender_name = "";
    public static JSONArray subctgydetails = new JSONArray();
    public static JSONArray genderdetails = new JSONArray();


    public String getGender_key() {
        return gender_key;
    }

    public void setGender_key(String gender_key) {
        this.gender_key = gender_key;
    }

    public String getGender_name() {
        return gender_name;
    }

    public void setGender_name(String gender_name) {
        this.gender_name = gender_name;
    }

    public static JSONArray getGenderdetails() {
        return genderdetails;
    }

    public static void setGenderdetails(JSONArray genderdetails) {
        Modal_B2BItemCtgy.genderdetails = new JSONArray();
        Modal_B2BItemCtgy.genderdetails = genderdetails;
    }

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
