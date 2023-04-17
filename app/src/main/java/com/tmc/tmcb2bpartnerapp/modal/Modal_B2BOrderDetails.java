package com.tmc.tmcb2bpartnerapp.modal;

import java.io.Serializable;

public class Modal_B2BOrderDetails  implements Serializable {

   public String deliverycentrekey ="" , deliverycentrename ="" , orderid ="" , orderplaceddate = "" , status = "", payableAmount ="",orderdeliveredtime="",
    priceperkg ="" , discountamount ="", batchno ="", b2bctgykey="" , totalweight= "" , totalquantity ="", totalmalequantity ="",invoiceno ="", paymentmode ="",ordercanceledtime ="",totalPrice="",
    totalmaleweight ="",totalfemaleweight = "" , totalfemalewithbabyquantity = "" , totalfemalewithbabyweight ="" ,totalfemalequantity ="",retailerkey ="" , retailermobileno = "",
        notes="",   billno ="",retailername = "" , retaileraddress ="",feedPrice ="" , feedPriceperkg ="" ,  feedWeight ="", supervisorname ="", supervisormobileno ="";
    public  String itemDespjsonArray = "" , batchnoList = "" ,creditvalue= "";

   String orderplacedlong ="0";

    public static String deliverycentrekey_Static ="" ,orderdeliveredtime_static="", deliverycentrename_Static ="" , orderid_Static ="" , orderplaceddate_Static = "" , status_Static = "", payableAmount_Static ="",
            priceperkg_Static ="" , discountamount_Static ="", batchno_Static ="", b2bctgykey_Static="" , totalweight_Static= "" , totalquantity_Static ="", totalmalequantity_Static ="",
            totalmaleweight_Static ="",totalfemaleweight_Static = "" , totalfemalewithbabyquantity_Static = "" , totalfemalewithbabyweight_Static ="",totalfemalequantity_Static ="" ,ordercanceledtime_Static ="",
            retailerkey_Static ="" , retailermobileno_Static = "", retailername_Static = "",invoiceno_Static ="",paymentMode_Static="",totalPrice_Static ="" , retaileraddress_static =""
           ,notes_static="",batchnoList_static = "" , billno_static ="",feedPrice_Static ="" ,feedWeight_Static ="" , feedPricePerKG_Static ="", supervisorname_Static ="", supervisormobileno_Static ="";
    public static   String itemDespBatchListjsonArray_static = "";

    public String getCreditvalue() {
        return creditvalue;
    }

    public void setCreditvalue(String creditvalue) {
        this.creditvalue = creditvalue;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public static String getNotes_static() {
        return notes_static;
    }

    public static void setNotes_static(String notes_static) {
        Modal_B2BOrderDetails.notes_static = notes_static;
    }

    public String getOrderdeliveredtime() {
        return orderdeliveredtime;
    }

    public void setOrderdeliveredtime(String orderdeliveredtime) {
        this.orderdeliveredtime = orderdeliveredtime;
    }

    public static String getOrderdeliveredtime_static() {
        return orderdeliveredtime_static;
    }

    public static void setOrderdeliveredtime_static(String orderdeliveredtime_static) {
        Modal_B2BOrderDetails.orderdeliveredtime_static = orderdeliveredtime_static;
    }

    public String getBatchnoList() {
        return batchnoList;
    }

    public void setBatchnoList(String batchnoList) {
        this.batchnoList = batchnoList;
    }

    public String getOrderplacedlong() {
        return orderplacedlong;
    }

    public void setOrderplacedlong(String orderplacedlong) {
        this.orderplacedlong = orderplacedlong;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getSupervisorname() {
        return supervisorname;
    }

    public void setSupervisorname(String supervisorname) {
        this.supervisorname = supervisorname;
    }

    public String getSupervisormobileno() {
        return supervisormobileno;
    }

    public void setSupervisormobileno(String supervisormobileno) {
        this.supervisormobileno = supervisormobileno;
    }

    public String getFeedPrice() {
        return feedPrice;
    }

    public void setFeedPrice(String feedPrice) {
        this.feedPrice = feedPrice;
    }

    public String getFeedPriceperkg() {
        return feedPriceperkg;
    }

    public void setFeedPriceperkg(String feedPriceperkg) {
        this.feedPriceperkg = feedPriceperkg;
    }

    public String getFeedWeight() {
        return feedWeight;
    }

    public void setFeedWeight(String feedWeight) {
        this.feedWeight = feedWeight;
    }

    public static String getFeedPrice_Static() {
        return feedPrice_Static;
    }

    public static void setFeedPrice_Static(String feedPrice_Static) {
        Modal_B2BOrderDetails.feedPrice_Static = feedPrice_Static;
    }

    public static String getFeedWeight_Static() {
        return feedWeight_Static;
    }

    public static void setFeedWeight_Static(String feedWeight_Static) {
        Modal_B2BOrderDetails.feedWeight_Static = feedWeight_Static;
    }

    public static String getFeedPricePerKG_Static() {
        return feedPricePerKG_Static;
    }

    public static void setFeedPricePerKG_Static(String feedPricePerKG_Static) {
        Modal_B2BOrderDetails.feedPricePerKG_Static = feedPricePerKG_Static;
    }

    public String getRetaileraddress() {
        return retaileraddress;
    }

    public void setRetaileraddress(String retaileraddress) {
        this.retaileraddress = retaileraddress;
    }

    public static String getRetaileraddress_static() {
        return retaileraddress_static;
    }

    public static void setRetaileraddress_static(String retaileraddress_static) {
        Modal_B2BOrderDetails.retaileraddress_static = retaileraddress_static;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public static String getTotalPrice_Static() {
        return totalPrice_Static;
    }

    public static void setTotalPrice_Static(String totalPrice_Static) {
        Modal_B2BOrderDetails.totalPrice_Static = totalPrice_Static;
    }

    public String getOrdercanceledtime() {
        return ordercanceledtime;
    }

    public void setOrdercanceledtime(String ordercanceledtime) {
        this.ordercanceledtime = ordercanceledtime;
    }

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;
    }








    public static String getSupervisorname_Static() {
        return supervisorname_Static;
    }

    public static void setSupervisorname_Static(String supervisorname_Static) {
        Modal_B2BOrderDetails.supervisorname_Static = supervisorname_Static;
    }

    public static String getSupervisormobileno_Static() {
        return supervisormobileno_Static;
    }

    public static void setSupervisormobileno_Static(String supervisormobileno_Static) {
        Modal_B2BOrderDetails.supervisormobileno_Static = supervisormobileno_Static;
    }

    public static String getPaymentMode_Static() {
        return paymentMode_Static;
    }

    public static void setPaymentMode_Static(String paymentMode_Static) {
        Modal_B2BOrderDetails.paymentMode_Static = paymentMode_Static;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public String getRetailerkey() {
        return retailerkey;
    }

    public String getRetailermobileno() {
        return retailermobileno;
    }

    public String getRetailername() {
        return retailername;
    }

    public String getTotalfemalequantity() {
        return totalfemalequantity;
    }

    public String getDeliverycentrekey() {
        return deliverycentrekey;
    }

    public String getDeliverycentrename() {
        return deliverycentrename;
    }

    public String getOrderid() {
        return orderid;
    }

    public String getOrderplaceddate() {
        return orderplaceddate;
    }

    public String getStatus() {
        return status;
    }

    public String getPayableAmount() {
        return payableAmount;
    }

    public String getPriceperkg() {
        return priceperkg;
    }

    public String getDiscountamount() {
        return discountamount;
    }

    public String getBatchno() {
        return batchno;
    }

    public String getB2bctgykey() {
        return b2bctgykey;
    }

    public String getTotalweight() {
        return totalweight;
    }

    public String getTotalquantity() {
        return totalquantity;
    }

    public String getTotalmalequantity() {
        return totalmalequantity;
    }

    public String getTotalmaleweight() {
        return totalmaleweight;
    }

    public String getTotalfemaleweight() {
        return totalfemaleweight;
    }

    public String getTotalfemalewithbabyquantity() {
        return totalfemalewithbabyquantity;
    }

    public String getTotalfemalewithbabyweight() {
        return totalfemalewithbabyweight;
    }


    public void setRetailerkey(String retailerkey) {
        this.retailerkey = retailerkey;
    }

    public void setRetailermobileno(String retailermobileno) {
        this.retailermobileno = retailermobileno;
    }

    public void setRetailername(String retailername) {
        this.retailername = retailername;
    }

    public void setTotalfemalequantity(String totalfemalequantity) {
        this.totalfemalequantity = totalfemalequantity;
    }

    public void setDeliverycentrekey(String deliverycentrekey) {
        this.deliverycentrekey = deliverycentrekey;
    }

    public void setDeliverycentrename(String deliverycentrename) {
        this.deliverycentrename = deliverycentrename;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public void setOrderplaceddate(String orderplaceddate) {
        this.orderplaceddate = orderplaceddate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPayableAmount(String payableAmount) {
        this.payableAmount = payableAmount;
    }

    public void setPriceperkg(String priceperkg) {
        this.priceperkg = priceperkg;
    }

    public void setDiscountamount(String discountamount) {
        this.discountamount = discountamount;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public void setB2bctgykey(String b2bctgykey) {
        this.b2bctgykey = b2bctgykey;
    }

    public void setTotalweight(String totalweight) {
        this.totalweight = totalweight;
    }

    public void setTotalquantity(String totalquantity) {
        this.totalquantity = totalquantity;
    }

    public void setTotalmalequantity(String totalmalequantity) {
        this.totalmalequantity = totalmalequantity;
    }

    public void setTotalmaleweight(String totalmaleweight) {
        this.totalmaleweight = totalmaleweight;
    }

    public void setTotalfemaleweight(String totalfemaleweight) {
        this.totalfemaleweight = totalfemaleweight;
    }

    public void setTotalfemalewithbabyquantity(String totalfemalewithbabyquantity) {
        this.totalfemalewithbabyquantity = totalfemalewithbabyquantity;
    }

    public void setTotalfemalewithbabyweight(String totalfemalewithbabyweight) {
        this.totalfemalewithbabyweight = totalfemalewithbabyweight;
    }

    public static String getOrdercanceledtime_Static() {
        return ordercanceledtime_Static;
    }

    public static String getTotalfemalequantity_Static() {
        return totalfemalequantity_Static;
    }

    public static String getDeliverycentrekey_Static() {
        return deliverycentrekey_Static;
    }

    public static String getDeliverycentrename_Static() {
        return deliverycentrename_Static;
    }

    public static String getOrderid_Static() {
        return orderid_Static;
    }

    public static String getOrderplaceddate_Static() {
        return orderplaceddate_Static;
    }

    public static String getStatus_Static() {
        return status_Static;
    }

    public static String getPayableAmount_Static() {
        return payableAmount_Static;
    }

    public static String getPriceperkg_Static() {
        return priceperkg_Static;
    }

    public static String getDiscountamount_Static() {
        return discountamount_Static;
    }

    public static String getBatchno_Static() {
        return batchno_Static;
    }

    public static String getB2bctgykey_Static() {
        return b2bctgykey_Static;
    }

    public static String getTotalweight_Static() {
        return totalweight_Static;
    }

    public static String getTotalquantity_Static() {
        return totalquantity_Static;
    }

    public static String getTotalmalequantity_Static() {
        return totalmalequantity_Static;
    }

    public static String getTotalmaleweight_Static() {
        return totalmaleweight_Static;
    }

    public static String getTotalfemaleweight_Static() {
        return totalfemaleweight_Static;
    }

    public static String getTotalfemalewithbabyquantity_Static() {
        return totalfemalewithbabyquantity_Static;
    }

    public static String getTotalfemalewithbabyweight_Static() {
        return totalfemalewithbabyweight_Static;
    }

    public static String getInvoiceno_Static() {
        return invoiceno_Static;
    }

    public static void setInvoiceno_Static(String invoiceno_Static) {
        Modal_B2BOrderDetails.invoiceno_Static = invoiceno_Static;
    }

    public static String getRetailerkey_Static() {
        return retailerkey_Static;
    }

    public static void setRetailerkey_Static(String retailerkey_Static) {
        Modal_B2BOrderDetails.retailerkey_Static = retailerkey_Static;
    }

    public static String getRetailermobileno_Static() {
        return retailermobileno_Static;
    }

    public static void setRetailermobileno_Static(String retailermobileno_Static) {
        Modal_B2BOrderDetails.retailermobileno_Static = retailermobileno_Static;
    }

    public static String getRetailername_Static() {
        return retailername_Static;
    }

    public static void setRetailername_Static(String retailername_Static) {
        Modal_B2BOrderDetails.retailername_Static = retailername_Static;
    }

    public static void setTotalfemalequantity_Static(String totalfemalequantity_Static) {
        Modal_B2BOrderDetails.totalfemalequantity_Static = totalfemalequantity_Static;
    }

    public static void setDeliverycentrekey_Static(String deliverycentrekey_Static) {
        Modal_B2BOrderDetails.deliverycentrekey_Static = deliverycentrekey_Static;
    }

    public static void setDeliverycentrename_Static(String deliverycentrename_Static) {
        Modal_B2BOrderDetails.deliverycentrename_Static = deliverycentrename_Static;
    }

    public static void setOrdercanceledtime_Static(String ordercanceledtime_Static) {
        Modal_B2BOrderDetails.ordercanceledtime_Static = ordercanceledtime_Static;
    }

    public static void setOrderid_Static(String orderid_Static) {
        Modal_B2BOrderDetails.orderid_Static = orderid_Static;
    }

    public static void setOrderplaceddate_Static(String orderplaceddate_Static) {
        Modal_B2BOrderDetails.orderplaceddate_Static = orderplaceddate_Static;
    }

    public static void setStatus_Static(String status_Static) {
        Modal_B2BOrderDetails.status_Static = status_Static;
    }

    public static void setPayableAmount_Static(String payableAmount_Static) {
        Modal_B2BOrderDetails.payableAmount_Static = payableAmount_Static;
    }

    public static void setPriceperkg_Static(String priceperkg_Static) {
        Modal_B2BOrderDetails.priceperkg_Static = priceperkg_Static;
    }

    public static void setDiscountamount_Static(String discountamount_Static) {
        Modal_B2BOrderDetails.discountamount_Static = discountamount_Static;
    }

    public static void setBatchno_Static(String batchno_Static) {
        Modal_B2BOrderDetails.batchno_Static = batchno_Static;
    }

    public static void setB2bctgykey_Static(String b2bctgykey_Static) {
        Modal_B2BOrderDetails.b2bctgykey_Static = b2bctgykey_Static;
    }

    public static void setTotalweight_Static(String totalweight_Static) {
        Modal_B2BOrderDetails.totalweight_Static = totalweight_Static;
    }

    public static void setTotalquantity_Static(String totalquantity_Static) {
        Modal_B2BOrderDetails.totalquantity_Static = totalquantity_Static;
    }

    public static void setTotalmalequantity_Static(String totalmalequantity_Static) {
        Modal_B2BOrderDetails.totalmalequantity_Static = totalmalequantity_Static;
    }

    public static void setTotalmaleweight_Static(String totalmaleweight_Static) {
        Modal_B2BOrderDetails.totalmaleweight_Static = totalmaleweight_Static;
    }

    public static void setTotalfemaleweight_Static(String totalfemaleweight_Static) {
        Modal_B2BOrderDetails.totalfemaleweight_Static = totalfemaleweight_Static;
    }

    public static void setTotalfemalewithbabyquantity_Static(String totalfemalewithbabyquantity_Static) {
        Modal_B2BOrderDetails.totalfemalewithbabyquantity_Static = totalfemalewithbabyquantity_Static;
    }

    public static void setTotalfemalewithbabyweight_Static(String totalfemalewithbabyweight_Static) {
        Modal_B2BOrderDetails.totalfemalewithbabyweight_Static = totalfemalewithbabyweight_Static;
    }

    public static String getBillno_static() {
        return billno_static;
    }

    public static void setBillno_static(String billno_static) {
        Modal_B2BOrderDetails.billno_static = billno_static;
    }


    public static String getBatchnoList_static() {
        return batchnoList_static;
    }

    public static void setBatchnoList_static(String batchnoList_static) {
        Modal_B2BOrderDetails.batchnoList_static = batchnoList_static;
    }

    public String getItemDespjsonArray() {
        return itemDespjsonArray;
    }

    public void setItemDespjsonArray(String itemDespjsonArray) {
        this.itemDespjsonArray = itemDespjsonArray;
    }

    public static String getItemDespBatchListjsonArray_static() {
        return itemDespBatchListjsonArray_static;
    }

    public static void setItemDespBatchListjsonArray_static(String itemDespBatchListjsonArray_static) {
        Modal_B2BOrderDetails.itemDespBatchListjsonArray_static = itemDespBatchListjsonArray_static;
    }
}
