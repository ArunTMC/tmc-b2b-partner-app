package com.tmc.tmcb2bpartnerapp.model;

public class Modal_AppUserAccess {
    public Modal_AppUserAccess() {
        appname="";name ="";role="";vendorname ="";usertype="";mobileno ="";supplierkey ="";suppliername=""
        ;deliverycenterkey = "";deliverycentername ="";

    }

    public  static String appname="",name ="",role="",vendorname ="",usertype="",mobileno ="",supplierkey ="",suppliername="",deliverycenterkey="",deliverycentername="";


    public static String getDeliverycenterkey() {
        return deliverycenterkey;
    }

    public static void setDeliverycenterkey(String deliverycenterkey) {
        Modal_AppUserAccess.deliverycenterkey = deliverycenterkey;
    }

    public static String getDeliverycentername() {
        return deliverycentername;
    }

    public static void setDeliverycentername(String deliverycentername) {
        Modal_AppUserAccess.deliverycentername = deliverycentername;
    }

    public static String getSuppliername() {
        return suppliername;
    }

    public static void setSuppliername(String suppliername) {
        Modal_AppUserAccess.suppliername = suppliername;
    }

    public static String getSupplierkey() {
        return supplierkey;
    }

    public static void setSupplierkey(String supplierkey) {
        Modal_AppUserAccess.supplierkey = supplierkey;
    }

    public static String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public static String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static String getVendorname() {
        return vendorname;
    }

    public void setVendorname(String vendorname) {
        this.vendorname = vendorname;
    }

    public static String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}
