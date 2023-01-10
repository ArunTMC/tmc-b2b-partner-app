package com.tmc.tmcb2bpartnerapp.model;

import java.util.ArrayList;
import java.util.List;

public  class Modal_DeliveryCenterDetails {
    public   String deliverycenterkey ="", name ="",password ="",mobileno ="";
    public static   String deliverycenterkey_static ="", name_static ="",password_static ="",mobileno_static ="";






    public String getGet(String name) {
        String value="";
        switch (name) {
            case "deliverycenterkey":
                value = this.deliverycenterkey;
                break;
            case "name":
                value = this.name;
                break;
            case "password":
                value = this.password;
                break;
            case "mobileno":
                value = this.mobileno;
                break;
        }

        return value;
    }






    public String getDeliverycenterkey() {
        return deliverycenterkey;
    }

    public void setDeliverycenterkey(String deliverycenterkey) {
        this.deliverycenterkey = deliverycenterkey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }
}
