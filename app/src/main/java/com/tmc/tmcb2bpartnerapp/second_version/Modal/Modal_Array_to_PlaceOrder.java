package com.tmc.tmcb2bpartnerapp.second_version.Modal;

import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BCartItemDetails;

import java.util.ArrayList;
import java.util.HashMap;

public class Modal_Array_to_PlaceOrder {

    public static HashMap<String, Modal_B2BCartItemDetails> cartItemDetails_Hashmap = new HashMap<>();
    public static ArrayList<String> cartItemDetailsList_String = new ArrayList<>();
    public static HashMap<String,ArrayList<String>> batchwiseEartagDetails_Hashmap = new HashMap<>();

    public static String paymentMode ="",retailerAddress = "",retailerMobileNo ="" , retailerName = "" , retailerKey = "" , feedweight = "" , feedPricePerKg = "" , feedPrice ="" , discountValue = "";


    public Modal_Array_to_PlaceOrder(String clearArray) {
        if(clearArray.equals("clearArray")){
            cartItemDetailsList_String.clear();
            cartItemDetails_Hashmap.clear();
            batchwiseEartagDetails_Hashmap.clear();
            retailerAddress = "";retailerMobileNo ="" ; retailerName = "" ; retailerKey = "" ; feedweight = "" ; feedPricePerKg = "" ;feedPrice ="" ;
            discountValue = "";paymentMode ="";

        }
    }


    public static HashMap<String, ArrayList<String>> getBatchwiseEartagDetails_Hashmap() {
        return batchwiseEartagDetails_Hashmap;
    }

    public static String getPaymentMode() {
        return paymentMode;
    }

    public static void setPaymentMode(String paymentMode) {
        Modal_Array_to_PlaceOrder.paymentMode = paymentMode;
    }

    public static String getRetailerAddress() {
        return retailerAddress;
    }

    public static String getRetailerMobileNo() {
        return retailerMobileNo;
    }

    public static String getRetailerName() {
        return retailerName;
    }

    public static String getRetailerKey() {
        return retailerKey;
    }

    public static String getFeedweight() {
        return feedweight;
    }

    public static String getFeedPricePerKg() {
        return feedPricePerKg;
    }

    public static String getFeedPrice() {
        return feedPrice;
    }

    public static String getDiscountValue() {
        return discountValue;
    }

    public static HashMap<String, Modal_B2BCartItemDetails> getCartItemDetails_Hashmap() {
        return cartItemDetails_Hashmap;
    }

    public static ArrayList<String> getCartItemDetailsList_String() {
        return cartItemDetailsList_String;
    }


    public static void setCartItemDetails_Hashmap(HashMap<String, Modal_B2BCartItemDetails> cartItemDetails_Hashmap) {
        Modal_Array_to_PlaceOrder.cartItemDetails_Hashmap = cartItemDetails_Hashmap;
    }

    public static void setCartItemDetailsList_String(ArrayList<String> cartItemDetailsList_String) {
        Modal_Array_to_PlaceOrder.cartItemDetailsList_String = cartItemDetailsList_String;
    }

    public static void setRetailerMobileNo(String retailerMobileNo) {
        Modal_Array_to_PlaceOrder.retailerMobileNo = retailerMobileNo;
    }

    public static void setRetailerName(String retailerName) {
        Modal_Array_to_PlaceOrder.retailerName = retailerName;
    }

    public static void setRetailerKey(String retailerKey) {
        Modal_Array_to_PlaceOrder.retailerKey = retailerKey;
    }

    public static void setFeedweight(String feedweight) {
        Modal_Array_to_PlaceOrder.feedweight = feedweight;
    }

    public static void setFeedPricePerKg(String feedPricePerKg) {
        Modal_Array_to_PlaceOrder.feedPricePerKg = feedPricePerKg;
    }

    public static void setFeedPrice(String feedPrice) {
        Modal_Array_to_PlaceOrder.feedPrice = feedPrice;
    }

    public static void setDiscountValue(String discountValue) {
        Modal_Array_to_PlaceOrder.discountValue = discountValue;
    }

    public static void setRetailerAddress(String retailerAddress) {
        Modal_Array_to_PlaceOrder.retailerAddress = retailerAddress;
    }


    public static void setBatchwiseEartagDetails_Hashmap(HashMap<String, ArrayList<String>> batchwiseEartagDetails_Hashmap) {
        Modal_Array_to_PlaceOrder.batchwiseEartagDetails_Hashmap = batchwiseEartagDetails_Hashmap;
    }
}
