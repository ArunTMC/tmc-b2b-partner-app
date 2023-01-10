package com.tmc.tmcb2bpartnerapp.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Modal_B2BOrderItemDetails {
    public HashMap<String,Modal_GoatEarTagDetails> earTagDetailsHashMap = new HashMap<>();
    public HashMap<String, JSONObject> earTagDetails_weightStringHashMap = new HashMap<>();

    public  ArrayList<String> earTagDetailsArrayList_String = new ArrayList<>();
    public  String orderid ="",retailermobileno ="",retailerkey = "" , b2bctgykey ="" , b2bsubctgykey ="",orderplaceddate ="",b2bctgyName="",b2bSubctgyName ="",
            weightingrams ="" , deliverycentrekey ="" , gender  ="" , status ="" , batchno ="" , gradeprice ="" , gradename ="" ,totalPrice ="",
            gradekey ="",supplierkey ="",suppliername ="",barcodeno ="",breedtype ="",itemaddeddate="",oldweightingrams ="";


    public static String orderid_static ="", retailermobileno_static ="", retailerkey_static = "" , b2bctgykey_static ="" , b2bsubctgykey_static ="", orderplaceddate_static ="",b2bctgyName_static ="",b2bSubctgyName_static ="",
    weightingrams_static ="" , deliverycentrekey_static ="" , gender_static ="" , status_static ="" , batchno_static ="" , gradeprice_static ="" , gradename_static ="" ,totalPrice_static ="",
            gradekey_static ="", supplierkey_static ="", suppliername_static ="", barcodeno_static ="", breedtype_static ="", itemaddeddate_static ="", oldweightingrams_static ="";


    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getB2bctgyName() {
        return b2bctgyName;
    }

    public void setB2bctgyName(String b2bctgyName) {
        this.b2bctgyName = b2bctgyName;
    }

    public String getB2bSubctgyName() {
        return b2bSubctgyName;
    }

    public void setB2bSubctgyName(String b2bSubctgyName) {
        this.b2bSubctgyName = b2bSubctgyName;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getRetailermobileno() {
        return retailermobileno;
    }

    public void setRetailermobileno(String retailermobileno) {
        this.retailermobileno = retailermobileno;
    }

    public String getRetailerkey() {
        return retailerkey;
    }

    public void setRetailerkey(String retailerkey) {
        this.retailerkey = retailerkey;
    }

    public String getB2bctgykey() {
        return b2bctgykey;
    }

    public void setB2bctgykey(String b2bctgykey) {
        this.b2bctgykey = b2bctgykey;
    }

    public String getB2bsubctgykey() {
        return b2bsubctgykey;
    }

    public void setB2bsubctgykey(String b2bsubctgykey) {
        this.b2bsubctgykey = b2bsubctgykey;
    }

    public String getOrderplaceddate() {
        return orderplaceddate;
    }

    public void setOrderplaceddate(String orderplaceddate) {
        this.orderplaceddate = orderplaceddate;
    }

    public String getWeightingrams() {
        return weightingrams;
    }

    public void setWeightingrams(String weightingrams) {
        this.weightingrams = weightingrams;
    }

    public String getDeliverycentrekey() {
        return deliverycentrekey;
    }

    public void setDeliverycentrekey(String deliverycentrekey) {
        this.deliverycentrekey = deliverycentrekey;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getGradeprice() {
        return gradeprice;
    }

    public void setGradeprice(String gradeprice) {
        this.gradeprice = gradeprice;
    }

    public String getGradename() {
        return gradename;
    }

    public void setGradename(String gradename) {
        this.gradename = gradename;
    }

    public String getGradekey() {
        return gradekey;
    }

    public void setGradekey(String gradekey) {
        this.gradekey = gradekey;
    }

    public String getSupplierkey() {
        return supplierkey;
    }

    public void setSupplierkey(String supplierkey) {
        this.supplierkey = supplierkey;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername;
    }

    public String getBarcodeno() {
        return barcodeno;
    }

    public void setBarcodeno(String barcodeno) {
        this.barcodeno = barcodeno;
    }

    public String getBreedtype() {
        return breedtype;
    }

    public void setBreedtype(String breedtype) {
        this.breedtype = breedtype;
    }

    public String getItemaddeddate() {
        return itemaddeddate;
    }

    public void setItemaddeddate(String itemaddeddate) {
        this.itemaddeddate = itemaddeddate;
    }

    public String getOldweightingrams() {
        return oldweightingrams;
    }

    public void setOldweightingrams(String oldweightingrams) {
        this.oldweightingrams = oldweightingrams;
    }

    public HashMap<String, JSONObject> getEarTagDetails_weightStringHashMap() {
        return earTagDetails_weightStringHashMap;
    }

    public void setEarTagDetails_weightStringHashMap(HashMap<String, JSONObject> earTagDetails_weightStringHashMap) {
        this.earTagDetails_weightStringHashMap = earTagDetails_weightStringHashMap;
    }

    public static String getB2bctgyName_static() {
        return b2bctgyName_static;
    }

    public static void setB2bctgyName_static(String b2bctgyName_static) {
        Modal_B2BOrderItemDetails.b2bctgyName_static = b2bctgyName_static;
    }

    public static String getB2bSubctgyName_static() {
        return b2bSubctgyName_static;
    }

    public static void setB2bSubctgyName_static(String b2bSubctgyName_static) {
        Modal_B2BOrderItemDetails.b2bSubctgyName_static = b2bSubctgyName_static;
    }

    public static String getTotalPrice_static() {
        return totalPrice_static;
    }

    public static void setTotalPrice_static(String totalPrice_static) {
        Modal_B2BOrderItemDetails.totalPrice_static = totalPrice_static;
    }

    public static String getSupplierkey_static() {
        return supplierkey_static;
    }

    public static void setSupplierkey_static(String supplierkey_static) {
        Modal_B2BOrderItemDetails.supplierkey_static = supplierkey_static;
    }

    public static String getSuppliername_static() {
        return suppliername_static;
    }

    public static void setSuppliername_static(String suppliername_static) {
        Modal_B2BOrderItemDetails.suppliername_static = suppliername_static;
    }

    public static String getBarcodeno_static() {
        return barcodeno_static;
    }

    public static void setBarcodeno_static(String barcodeno_static) {
        Modal_B2BOrderItemDetails.barcodeno_static = barcodeno_static;
    }

    public static String getBreedtype_static() {
        return breedtype_static;
    }

    public static void setBreedtype_static(String breedtype_static) {
        Modal_B2BOrderItemDetails.breedtype_static = breedtype_static;
    }

    public static String getItemaddeddate_static() {
        return itemaddeddate_static;
    }

    public static void setItemaddeddate_static(String itemaddeddate_static) {
        Modal_B2BOrderItemDetails.itemaddeddate_static = itemaddeddate_static;
    }

    public static String getOldweightingrams_static() {
        return oldweightingrams_static;
    }

    public static void setOldweightingrams_static(String oldweightingrams_static) {
        Modal_B2BOrderItemDetails.oldweightingrams_static = oldweightingrams_static;
    }

    public static String getGradekey_static() {
        return gradekey_static;
    }

    public static void setGradekey_static(String gradekey_static) {
        Modal_B2BOrderItemDetails.gradekey_static = gradekey_static;
    }

    public static String getGradeprice_static() {
        return gradeprice_static;
    }

    public static void setGradeprice_static(String gradeprice_static) {
        Modal_B2BOrderItemDetails.gradeprice_static = gradeprice_static;
    }

    public static String getGradename_static() {
        return gradename_static;
    }

    public static void setGradename_static(String gradename_static) {
        Modal_B2BOrderItemDetails.gradename_static = gradename_static;
    }

    public static String getBatchno_static() {
        return batchno_static;
    }

    public static void setBatchno_static(String batchno_static) {
        Modal_B2BOrderItemDetails.batchno_static = batchno_static;
    }

    public HashMap<String, Modal_GoatEarTagDetails> getEarTagDetailsHashMap() {
        return earTagDetailsHashMap;
    }

    public void setEarTagDetailsHashMap(HashMap<String, Modal_GoatEarTagDetails> earTagDetailsHashMap) {
        this.earTagDetailsHashMap = earTagDetailsHashMap;
    }

    public ArrayList<String> getEarTagDetailsArrayList_String() {
        return earTagDetailsArrayList_String;
    }

    public void setEarTagDetailsArrayList_String(ArrayList<String> earTagDetailsArrayList_String) {
        this.earTagDetailsArrayList_String = earTagDetailsArrayList_String;
    }

    public static String getOrderid_static() {
        return orderid_static;
    }

    public static void setOrderid_static(String orderid_static) {
        Modal_B2BOrderItemDetails.orderid_static = orderid_static;
    }

    public static String getRetailermobileno_static() {
        return retailermobileno_static;
    }

    public static void setRetailermobileno_static(String retailermobileno_static) {
        Modal_B2BOrderItemDetails.retailermobileno_static = retailermobileno_static;
    }

    public static String getRetailerkey_static() {
        return retailerkey_static;
    }

    public static void setRetailerkey_static(String retailerkey_static) {
        Modal_B2BOrderItemDetails.retailerkey_static = retailerkey_static;
    }

    public static String getB2bctgykey_static() {
        return b2bctgykey_static;
    }

    public static void setB2bctgykey_static(String b2bctgykey_static) {
        Modal_B2BOrderItemDetails.b2bctgykey_static = b2bctgykey_static;
    }

    public static String getB2bsubctgykey_static() {
        return b2bsubctgykey_static;
    }

    public static void setB2bsubctgykey_static(String b2bsubctgykey_static) {
        Modal_B2BOrderItemDetails.b2bsubctgykey_static = b2bsubctgykey_static;
    }

    public static String getOrderplaceddate_static() {
        return orderplaceddate_static;
    }

    public static void setOrderplaceddate_static(String orderplaceddate_static) {
        Modal_B2BOrderItemDetails.orderplaceddate_static = orderplaceddate_static;
    }

    public static String getWeightingrams_static() {
        return weightingrams_static;
    }

    public static void setWeightingrams_static(String weightingrams_static) {
        Modal_B2BOrderItemDetails.weightingrams_static = weightingrams_static;
    }

    public static String getDeliverycentrekey_static() {
        return deliverycentrekey_static;
    }

    public static void setDeliverycentrekey_static(String deliverycentrekey_static) {
        Modal_B2BOrderItemDetails.deliverycentrekey_static = deliverycentrekey_static;
    }

    public static String getGender_static() {
        return gender_static;
    }

    public static void setGender_static(String gender_static) {
        Modal_B2BOrderItemDetails.gender_static = gender_static;
    }

    public static String getStatus_static() {
        return status_static;
    }

    public static void setStatus_static(String status_static) {
        Modal_B2BOrderItemDetails.status_static = status_static;
    }
}
