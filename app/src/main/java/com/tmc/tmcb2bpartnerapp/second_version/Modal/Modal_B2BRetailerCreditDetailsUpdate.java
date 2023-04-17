package com.tmc.tmcb2bpartnerapp.second_version.Modal;

import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BBatchDetailsStatic;

public class Modal_B2BRetailerCreditDetailsUpdate {


    public static String retailerkey ="",retailername ="",deliverycentrekey ="",retailermobileno ="",lastupdatedtime ="",totalamountincredit ="";


    public static void setRetailerkey(String retailerkey) {
        if(!Modal_B2BRetailerCreditDetailsUpdate.retailerkey.toUpperCase().equals(retailerkey.toUpperCase())){
            Modal_B2BRetailerCreditDetailsUpdate.retailerkey = retailerkey;
        }
        else{
            Modal_B2BRetailerCreditDetailsUpdate.retailerkey = "";
        }


    }

    public static void setRetailername(String retailername) {
        if(!Modal_B2BRetailerCreditDetailsUpdate.retailername.toUpperCase().equals(retailername.toUpperCase())){
            Modal_B2BRetailerCreditDetailsUpdate.retailername = retailername;
        }
        else{
            Modal_B2BRetailerCreditDetailsUpdate.retailername = "";
        }


    }

    public static void setDeliverycentrekey(String deliverycentrekey) {
        if(!Modal_B2BRetailerCreditDetailsUpdate.deliverycentrekey.toUpperCase().equals(deliverycentrekey.toUpperCase())){
            Modal_B2BRetailerCreditDetailsUpdate.deliverycentrekey = deliverycentrekey;
        }
        else{
            Modal_B2BRetailerCreditDetailsUpdate.deliverycentrekey = "";
        }

    }

    public static void setRetailermobileno(String retailermobileno) {
        if(!Modal_B2BRetailerCreditDetailsUpdate.retailermobileno.toUpperCase().equals(retailermobileno.toUpperCase())){
            Modal_B2BRetailerCreditDetailsUpdate.retailermobileno = retailermobileno;
        }
        else{
            Modal_B2BRetailerCreditDetailsUpdate.retailermobileno = "";
        }

    }

    public static void setLastupdatedtime(String lastupdatedtime) {
        if(!Modal_B2BRetailerCreditDetailsUpdate.lastupdatedtime.toUpperCase().equals(lastupdatedtime.toUpperCase())){
            Modal_B2BRetailerCreditDetailsUpdate.lastupdatedtime = lastupdatedtime;
        }
        else{
            Modal_B2BRetailerCreditDetailsUpdate.lastupdatedtime = "";
        }

    }

    public static void setTotalamountincredit(String totalamountincredit) {
        if(!Modal_B2BRetailerCreditDetailsUpdate.totalamountincredit.equals(totalamountincredit)){
            Modal_B2BRetailerCreditDetailsUpdate.totalamountincredit = totalamountincredit;
        }
        else{
            Modal_B2BRetailerCreditDetailsUpdate.totalamountincredit = "";
        }

    }

    public static String getRetailerkey() {
        return retailerkey;
    }

    public static String getRetailername() {
        return retailername;
    }

    public static String getDeliverycentrekey() {
        return deliverycentrekey;
    }

    public static String getRetailermobileno() {
        return retailermobileno;
    }

    public static String getLastupdatedtime() {
        return lastupdatedtime;
    }

    public static String getTotalamountincredit() {
        return totalamountincredit;
    }
}
