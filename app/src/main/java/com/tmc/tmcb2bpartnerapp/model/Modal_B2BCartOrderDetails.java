package com.tmc.tmcb2bpartnerapp.model;

public class Modal_B2BCartOrderDetails {
    static public  String orderid = "" , batchno ="" , deliverycenterkey ="" , deliverycentername = "" ,priceperkg ="" , retailerkey ="",
    retailermobileno ="" , retailername ="",itemaddeddate = "" , invoiceno = "", paymentMode ="";
    static public boolean ispriceperkgUpdated = false , isRetailerNameUpdated = false;

    public Modal_B2BCartOrderDetails() {
        orderid = "" ; batchno ="" ; deliverycenterkey ="" ; deliverycentername = "" ;priceperkg ="" ; retailerkey ="";
                retailermobileno ="" ; retailername ="" ; itemaddeddate ="" ;invoiceno  =""; paymentMode ="";
        ispriceperkgUpdated = false ; isRetailerNameUpdated = false;
    }

    public static String getPaymentMode() {
        return paymentMode;
    }

    public static void setPaymentMode(String paymentMode) {
        Modal_B2BCartOrderDetails.paymentMode = paymentMode;
    }

    public static String getInvoiceno() {
        return invoiceno;
    }

    public static void setInvoiceno(String invoiceno) {
        Modal_B2BCartOrderDetails.invoiceno = invoiceno;
    }

    public static String getItemaddeddate() {
        return itemaddeddate;
    }

    public static void setItemaddeddate(String itemaddeddate) {
        Modal_B2BCartOrderDetails.itemaddeddate = itemaddeddate;
    }


    public static boolean isIspriceperkgUpdated() {
        return ispriceperkgUpdated;
    }

    public static void setIspriceperkgUpdated(boolean ispriceperkgUpdated) {
        Modal_B2BCartOrderDetails.ispriceperkgUpdated = ispriceperkgUpdated;
    }

    public static boolean isIsRetailerNameUpdated() {
        return isRetailerNameUpdated;
    }

    public static void setIsRetailerNameUpdated(boolean isRetailerNameUpdated) {
        Modal_B2BCartOrderDetails.isRetailerNameUpdated = isRetailerNameUpdated;
    }

    public static String getOrderid() {
        return orderid;
    }

    public static void setOrderid(String orderid) {
        Modal_B2BCartOrderDetails.orderid = orderid;
    }

    public static String getBatchno() {
        return batchno;
    }

    public static void setBatchno(String batchno) {
        Modal_B2BCartOrderDetails.batchno = batchno;
    }

    public static String getDeliverycenterkey() {
        return deliverycenterkey;
    }

    public static void setDeliverycenterkey(String deliverycenterkey) {
        Modal_B2BCartOrderDetails.deliverycenterkey = deliverycenterkey;
    }

    public static String getDeliverycentername() {
        return deliverycentername;
    }

    public static void setDeliverycentername(String deliverycentername) {
        Modal_B2BCartOrderDetails.deliverycentername = deliverycentername;
    }

    public static String getPriceperkg() {
        return priceperkg;
    }

    public static void setPriceperkg(String priceperkg) {
        Modal_B2BCartOrderDetails.priceperkg = priceperkg;
    }

    public static String getRetailerkey() {
        return retailerkey;
    }

    public static void setRetailerkey(String retailerkey) {
        Modal_B2BCartOrderDetails.retailerkey = retailerkey;
    }

    public static String getRetailermobileno() {
        return retailermobileno;
    }

    public static void setRetailermobileno(String retailermobileno) {
        Modal_B2BCartOrderDetails.retailermobileno = retailermobileno;
    }

    public static String getRetailername() {
        return retailername;
    }

    public static void setRetailername(String retailername) {
        Modal_B2BCartOrderDetails.retailername = retailername;
    }
}
