package com.tmc.tmcb2bpartnerapp.modal;

public class Modal_UpdatedB2BCartOrderDetails {


    static public  String orderid = "" , batchno ="" , deliverycenterkey ="" , deliverycentername = "" ,priceperkg ="" , retailerkey ="",
            retailermobileno ="" , retailername ="",itemaddeddate = "" , invoiceno ="",paymentMode = "", retaileraddress ="",discount ="",
            feedPrice ="" , feedPriceperkg ="" ,  feedWeight ="";


    static public  boolean orderid_boolean = false , isPaymentmode_boolean = false, batchno_boolean = false , deliverycenterkey_boolean = false, deliverycentername_boolean = false ,priceperkg_boolean = false, retailerkey_boolean = false,
            isretailerAddressUpdated_boolean = false,retailermobileno_boolean = false, retailername_boolean = false,itemaddeddate_boolean = false ,invoice_boolean = false,
            discount_boolean = false , feedPrice_boolean = false ,feedWeight_boolean = false , feedPricePerKG_boolean = false;


    public Modal_UpdatedB2BCartOrderDetails() {
        orderid = "" ; batchno ="" ; deliverycenterkey ="" ; deliverycentername = "" ;priceperkg ="" ; retailerkey ="";
        retailermobileno ="" ; retailername ="" ; itemaddeddate ="";invoiceno ="";paymentMode = ""; retaileraddress ="";discount ="";
        feedPrice ="" ; feedPriceperkg ="" ;  feedWeight ="";


        orderid_boolean = false ; batchno_boolean = false ; deliverycenterkey_boolean = false ; deliverycentername_boolean = false ; priceperkg_boolean = false ; retailerkey_boolean = false ;
        isretailerAddressUpdated_boolean  = false;retailermobileno_boolean = false; retailername_boolean = false ; itemaddeddate_boolean = false;invoice_boolean = false;
        discount_boolean = false;feedPrice_boolean = false ;feedWeight_boolean = false ; feedPricePerKG_boolean = false;isPaymentmode_boolean = false;



    }


    public static String getFeedPrice() {
        return feedPrice;
    }

    public static void setFeedPrice(String feedPrice) {
     //   Modal_UpdatedB2BCartOrderDetails.feedPrice = feedPrice;


        if (!Modal_B2BCartOrderDetails.getFeedPrice().equals(feedPrice)){
            Modal_UpdatedB2BCartOrderDetails.feedPrice = feedPrice;

            Modal_UpdatedB2BCartOrderDetails.feedPrice_boolean = true;
        }
        else{
            Modal_UpdatedB2BCartOrderDetails.feedPrice_boolean = false;
        }




    }

    public static String getFeedPriceperkg() {
        return feedPriceperkg;
    }

    public static void setFeedPriceperkg(String feedPriceperkg) {
        //Modal_UpdatedB2BCartOrderDetails.feedPriceperkg = feedPriceperkg;


        if (!Modal_B2BCartOrderDetails.getFeedPriceperkg().equals(feedPriceperkg)){
            Modal_UpdatedB2BCartOrderDetails.feedPriceperkg = feedPriceperkg;

            Modal_UpdatedB2BCartOrderDetails.feedPricePerKG_boolean = true;
        }
        else{
            Modal_UpdatedB2BCartOrderDetails.feedPricePerKG_boolean = false;
        }



    }

    public static String getFeedWeight() {
        return feedWeight;
    }

    public static void setFeedWeight(String feedWeight) {
      //  Modal_UpdatedB2BCartOrderDetails.feedWeight = feedWeight;



        if (!Modal_B2BCartOrderDetails.getFeedWeight().equals(feedWeight)){
            Modal_UpdatedB2BCartOrderDetails.feedWeight = feedWeight;

            Modal_UpdatedB2BCartOrderDetails.feedWeight_boolean = true;
        }
        else{
            Modal_UpdatedB2BCartOrderDetails.feedWeight_boolean = false;
        }


    }

    public static String getDiscount() {
        return discount;
    }

    public static void setDiscount(String discount) {

        if (!Modal_B2BCartOrderDetails.getDiscountAmount().equals(discount)){
            Modal_UpdatedB2BCartOrderDetails.discount = discount;

            Modal_UpdatedB2BCartOrderDetails.discount_boolean = true;
        }
        else{
            Modal_UpdatedB2BCartOrderDetails.discount_boolean = false;
        }




      //  Modal_UpdatedB2BCartOrderDetails.discount = discount;
    }

    public static String getRetaileraddress() {
        return retaileraddress;
    }

    public static void setRetaileraddress(String retaileraddress) {
        if (!Modal_B2BCartOrderDetails.getRetaileraddress().equals(retaileraddress)){
            Modal_UpdatedB2BCartOrderDetails.retaileraddress = retaileraddress;

            Modal_UpdatedB2BCartOrderDetails.isretailerAddressUpdated_boolean = true;
        }
        else{
            Modal_UpdatedB2BCartOrderDetails.isretailerAddressUpdated_boolean = false;
        }


    }

    public static String getPaymentMode() {
        return paymentMode;
    }

    public static void setPaymentMode(String paymentMode) {
        if (!Modal_B2BCartOrderDetails.getPaymentMode().equals(paymentMode)){
            Modal_UpdatedB2BCartOrderDetails.paymentMode = paymentMode;

            Modal_UpdatedB2BCartOrderDetails.isPaymentmode_boolean = true;
        }
        else{
            Modal_UpdatedB2BCartOrderDetails.isPaymentmode_boolean = false;
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
