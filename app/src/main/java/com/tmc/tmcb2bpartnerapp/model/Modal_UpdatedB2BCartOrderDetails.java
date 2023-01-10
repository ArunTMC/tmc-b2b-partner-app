package com.tmc.tmcb2bpartnerapp.model;

public class Modal_UpdatedB2BCartOrderDetails {


    static public  String orderid = "" , batchno ="" , deliverycenterkey ="" , deliverycentername = "" ,priceperkg ="" , retailerkey ="",
            retailermobileno ="" , retailername ="",itemaddeddate = "" , invoiceno ="",paymentMode = "";


    static public  boolean orderid_boolean = false , batchno_boolean = false , deliverycenterkey_boolean = false, deliverycentername_boolean = false ,priceperkg_boolean = false, retailerkey_boolean = false,
            retailermobileno_boolean = false, retailername_boolean = false,itemaddeddate_boolean = false ,invoice_boolean = false,paymentmode_boolean = false;


    public Modal_UpdatedB2BCartOrderDetails() {
        orderid = "" ; batchno ="" ; deliverycenterkey ="" ; deliverycentername = "" ;priceperkg ="" ; retailerkey ="";
        retailermobileno ="" ; retailername ="" ; itemaddeddate ="";invoiceno ="";paymentMode = "";


        orderid_boolean = false ; batchno_boolean = false ; deliverycenterkey_boolean = false ; deliverycentername_boolean = false ; priceperkg_boolean = false ; retailerkey_boolean = false ;
                retailermobileno_boolean = false; retailername_boolean = false ; itemaddeddate_boolean = false;invoice_boolean = false;paymentmode_boolean = false;



    }


    public static String getPaymentMode() {
        return paymentMode;
    }

    public static void setPaymentMode(String paymentMode) {
        if (!Modal_B2BCartOrderDetails.getPaymentMode().equals(paymentMode)){
            Modal_UpdatedB2BCartOrderDetails.paymentMode = paymentMode;

            Modal_UpdatedB2BCartOrderDetails.paymentmode_boolean = true;
        }
        else{
            Modal_UpdatedB2BCartOrderDetails.paymentmode_boolean = false;
        }

    }

    public static String getInvoiceno() {
        return invoiceno;
    }

    public static void setInvoiceno(String invoiceno) {
        Modal_UpdatedB2BCartOrderDetails.invoiceno = invoiceno;
    }

    public static String getOrderid() {
        return orderid;
    }

    public static void setOrderid(String orderid) {
        Modal_UpdatedB2BCartOrderDetails.orderid = orderid;


    }

    public static String getBatchno() {
        return batchno;
    }

    public static void setBatchno(String batchno) {
        Modal_UpdatedB2BCartOrderDetails.batchno = batchno;


    }

    public static String getDeliverycenterkey() {
        return deliverycenterkey;
    }

    public static void setDeliverycenterkey(String deliverycenterkey) {

        if (!Modal_B2BCartOrderDetails.getDeliverycenterkey().equals(deliverycenterkey)){
            Modal_UpdatedB2BCartOrderDetails.deliverycenterkey = deliverycenterkey;

            Modal_UpdatedB2BCartOrderDetails.deliverycenterkey_boolean = true;
        }
        else{
            Modal_UpdatedB2BCartOrderDetails.deliverycenterkey_boolean = false;
        }


    }

    public static String getDeliverycentername() {
        return deliverycentername;
    }

    public static void setDeliverycentername(String deliverycentername) {

        if (!Modal_B2BCartOrderDetails.getDeliverycentername().equals(deliverycentername)){
            Modal_UpdatedB2BCartOrderDetails.deliverycentername = deliverycentername;

            Modal_UpdatedB2BCartOrderDetails.deliverycentername_boolean = true;
        }
        else{
            Modal_UpdatedB2BCartOrderDetails.deliverycentername_boolean = false;
        }

    }

    public static String getPriceperkg() {
        return priceperkg;
    }

    public static void setPriceperkg(String priceperkg) {
        if (!Modal_B2BCartOrderDetails.getPriceperkg().equals(priceperkg)){
            Modal_UpdatedB2BCartOrderDetails.priceperkg = priceperkg;


            Modal_UpdatedB2BCartOrderDetails.priceperkg_boolean = true;
        }
        else{
            Modal_UpdatedB2BCartOrderDetails.priceperkg_boolean = false;
        }

    }

    public static String getRetailerkey() {
        return retailerkey;
    }

    public static void setRetailerkey(String retailerkey) {
        if (!Modal_B2BCartOrderDetails.getRetailerkey().equals(retailerkey)){
            Modal_UpdatedB2BCartOrderDetails.retailerkey = retailerkey;


            Modal_UpdatedB2BCartOrderDetails.retailerkey_boolean = true;
        }
        else{
            Modal_UpdatedB2BCartOrderDetails.retailerkey_boolean = false;
        }

    }

    public static String getRetailermobileno() {
        return retailermobileno;
    }

    public static void setRetailermobileno(String retailermobileno) {

        if (!Modal_B2BCartOrderDetails.getRetailermobileno().equals(retailermobileno)){
            Modal_UpdatedB2BCartOrderDetails.retailermobileno = retailermobileno;


            Modal_UpdatedB2BCartOrderDetails.retailermobileno_boolean = true;
        }
        else{
            Modal_UpdatedB2BCartOrderDetails.retailermobileno_boolean = false;
        }


    }

    public static String getRetailername() {
        return retailername;
    }

    public static void setRetailername(String retailername) {
        if (!Modal_B2BCartOrderDetails.getRetailername().equals(retailername)){
            Modal_UpdatedB2BCartOrderDetails.retailername = retailername;


            Modal_UpdatedB2BCartOrderDetails.retailername_boolean = true;
        }
        else{
            Modal_UpdatedB2BCartOrderDetails.retailername_boolean = false;
        }

    }

    public static String getItemaddeddate() {
        return itemaddeddate;
    }

    public static void setItemaddeddate(String itemaddeddate) {

        if (!Modal_B2BCartOrderDetails.getItemaddeddate().equals(itemaddeddate)){
            Modal_UpdatedB2BCartOrderDetails.itemaddeddate = itemaddeddate;


            Modal_UpdatedB2BCartOrderDetails.itemaddeddate_boolean = true;
        }
        else{
            Modal_UpdatedB2BCartOrderDetails.itemaddeddate_boolean = false;
        }
    }
}
