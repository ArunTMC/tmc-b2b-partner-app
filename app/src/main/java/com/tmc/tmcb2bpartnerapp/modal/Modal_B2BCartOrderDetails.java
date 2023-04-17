package com.tmc.tmcb2bpartnerapp.modal;

public class Modal_B2BCartOrderDetails {
    static public  String orderid = "" , batchno ="" , deliverycenterkey ="" , deliverycentername = "" ,priceperkg ="" , retailerkey ="",
    retailermobileno ="" , retailername ="",itemaddeddate = "" , invoiceno = "", paymentMode ="" , retaileraddress ="" , discountAmount = "",
            feedPrice ="" , feedPriceperkg ="" ,  feedWeight ="", totalCount = "";;
    static public boolean ispriceperkgUpdated = false , isRetailerNameUpdated = false , isretailerAddressUpdated = false ,feedPrice_boolean = false ,feedWeight_boolean = false , feedPricePerKG_boolean = false;;

    public Modal_B2BCartOrderDetails() {
        totalCount =""; orderid = "" ; batchno ="" ; deliverycenterkey ="" ; deliverycentername = "" ;priceperkg ="" ; retailerkey ="";   feedPrice ="" ; feedPriceperkg ="" ;  feedWeight ="";
                retailermobileno ="" ; retailername ="" ; itemaddeddate ="" ;invoiceno  =""; paymentMode ="";retaileraddress ="";discountAmount = "";
        ispriceperkgUpdated = false ; isRetailerNameUpdated = false ; isretailerAddressUpdated = false;feedPrice_boolean = false ;feedWeight_boolean = false ; feedPricePerKG_boolean = false;
    }


    public static String getTotalCount() {
        return totalCount;
    }

    public static void setTotalCount(String totalCount) {
        Modal_B2BCartOrderDetails.totalCount = totalCount;
    }

    public static String getFeedPrice() {
        return feedPrice;
    }

    public static void setFeedPrice(String feedPrice) {
        Modal_B2BCartOrderDetails.feedPrice = feedPrice;
    }

    public static String getFeedPriceperkg() {
        return feedPriceperkg;
    }

    public static void setFeedPriceperkg(String feedPriceperkg) {
        Modal_B2BCartOrderDetails.feedPriceperkg = feedPriceperkg;
    }

    public static String getFeedWeight() {
        return feedWeight;
    }

    public static void setFeedWeight(String feedWeight) {
        Modal_B2BCartOrderDetails.feedWeight = feedWeight;
    }

    public static boolean isFeedPrice_boolean() {
        return feedPrice_boolean;
    }

    public static void setFeedPrice_boolean(boolean feedPrice_boolean) {
        Modal_B2BCartOrderDetails.feedPrice_boolean = feedPrice_boolean;
    }

    public static boolean isFeedWeight_boolean() {
        return feedWeight_boolean;
    }

    public static void setFeedWeight_boolean(boolean feedWeight_boolean) {
        Modal_B2BCartOrderDetails.feedWeight_boolean = feedWeight_boolean;
    }

    public static boolean isFeedPricePerKG_boolean() {
        return feedPricePerKG_boolean;
    }

    public static void setFeedPricePerKG_boolean(boolean feedPricePerKG_boolean) {
        Modal_B2BCartOrderDetails.feedPricePerKG_boolean = feedPricePerKG_boolean;
    }

    public static String getDiscountAmount() {
        return discountAmount;
    }

    public static void setDiscountAmount(String discountAmount) {
        Modal_B2BCartOrderDetails.discountAmount = discountAmount;
    }

    public static boolean isIsretailerAddressUpdated() {
        return isretailerAddressUpdated;
    }

    public static void setIsretailerAddressUpdated(boolean isretailerAddressUpdated) {
        Modal_B2BCartOrderDetails.isretailerAddressUpdated = isretailerAddressUpdated;
    }

    public static String getRetaileraddress() {
        return retaileraddress;
    }

    public static void setRetaileraddress(String retaileraddress) {
        Modal_B2BCartOrderDetails.retaileraddress = retaileraddress;
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
