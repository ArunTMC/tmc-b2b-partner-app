package com.tmc.tmcb2bpartnerapp.model;

public class Modal_SupplierDetails {
   public static String supplierkey_static ="";
    public static String aadhaarcardno_static ="";
    public static String gstno_static ="";
    public static String name_static ="";
    public static String password_static ="";
    public static String primarymobileno_static ="";
    public static String suppliername_static ="";
    public static String secondarymobileno_static ="";


    public  String supplierkey ="";
    public  String aadhaarcardno ="";
    public  String gstno ="";
    public  String name ="";
    public  String password="";
    public  String primarymobileno="";
    public  String suppliername="";
    public  String secondarymobileno ="";









    public String getGet(String supplierkey) {
        String value="";
        switch (supplierkey) {
            case "supplierkey":
                value = this.supplierkey;
                break;
            case "aadhaarcardno":
                value = this.aadhaarcardno;
                break;
            case "gstno":
                value = this.gstno;
                break;
            case "name":
                value = this.name;
                break;
            case "password":
                value = this.password;
                break;
            case "primarymobileno":
                value = this.primarymobileno;
                break;
            case "suppliername":
                value = this.suppliername;
                break;
            case "secondarymobileno":
                value = this.secondarymobileno;
                break;
        }

        return value;
    }










    public static String getSuppliername_static() {
        return suppliername_static;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername_static = suppliername;
    }

    public static String getSupplierkey_static() {
        return supplierkey_static;
    }

    public void setSupplierkey(String supplierkey) {
        this.supplierkey_static = supplierkey;
    }

    public  static String getAadhaarcardno_static() {
        return aadhaarcardno_static;
    }

    public void setAadhaarcardno(String aadhaarcardno) {
        this.aadhaarcardno_static = aadhaarcardno;
    }

    public static String getGstno_static() {
        return gstno_static;
    }

    public void setGstno(String gstno) {
        this.gstno_static = gstno;
    }

    public static String getName_static() {
        return name_static;
    }

    public void setName(String name) {
        this.name_static = name;
    }

    public static String getPassword_static() {
        return password_static;
    }

    public void setPassword(String password) {
        this.password_static = password;
    }

    public static String getPrimarymobileno_static() {
        return primarymobileno_static;
    }

    public void setPrimarymobileno(String primarymobileno) {
        this.primarymobileno_static = primarymobileno;
    }

    public static String getSecondarymobileno_static() {
        return secondarymobileno_static;
    }

    public void setSecondarymobileno(String secondarymobileno) {
        this.secondarymobileno_static = secondarymobileno;
    }


    public String getSupplierkey() {
        return supplierkey;
    }

    public String getAadhaarcardno() {
        return aadhaarcardno;
    }

    public String getGstno() {
        return gstno;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPrimarymobileno() {
        return primarymobileno;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public String getSecondarymobileno() {
        return secondarymobileno;
    }


    public static void setSupplierkey_static(String supplierkey_static) {
        Modal_SupplierDetails.supplierkey_static = supplierkey_static;
    }

    public static void setAadhaarcardno_static(String aadhaarcardno_static) {
        Modal_SupplierDetails.aadhaarcardno_static = aadhaarcardno_static;
    }

    public static void setGstno_static(String gstno_static) {
        Modal_SupplierDetails.gstno_static = gstno_static;
    }

    public static void setName_static(String name_static) {
        Modal_SupplierDetails.name_static = name_static;
    }

    public static void setPassword_static(String password_static) {
        Modal_SupplierDetails.password_static = password_static;
    }

    public static void setPrimarymobileno_static(String primarymobileno_static) {
        Modal_SupplierDetails.primarymobileno_static = primarymobileno_static;
    }

    public static void setSuppliername_static(String suppliername_static) {
        Modal_SupplierDetails.suppliername_static = suppliername_static;
    }

    public static void setSecondarymobileno_static(String secondarymobileno_static) {
        Modal_SupplierDetails.secondarymobileno_static = secondarymobileno_static;
    }
}
