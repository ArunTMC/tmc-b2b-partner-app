package com.tmc.tmcb2bpartnerapp.model;

public class Modal_GoatEarTagTransaction {
    public static  String batchno ="", uniquekey = "", barcodeno ="",previousweightingrams ="", newweightingrams="", weighingpurpose ="",
    updateddate="",status="",gender="",breedtype="",mobileno,description ="" , gradename ="" , gradeprice = "" , gradekey ="",deliverycenterkey ="",deliverycentername ="";

    public Modal_GoatEarTagTransaction() {
        batchno ="";uniquekey = "";barcodeno ="";previousweightingrams =""; newweightingrams=""; weighingpurpose ="";deliverycenterkey ="";deliverycentername ="";
                updateddate="";status="";gender="";breedtype="";mobileno="";description ="";gradename ="" ; gradeprice = ""  ; gradekey ="";
    }


    public static String getDeliverycenterkey() {
        return deliverycenterkey;
    }

    public static String getDeliverycentername() {
        return deliverycentername;
    }

    public static String getGradekey() {
        return gradekey;
    }

    public static String getGradename() {
        return gradename;
    }

    public static String getGradeprice() {
        return gradeprice;
    }

    public static String getDescription() {
        return description;
    }

    public static String getStatus() {
        return status;
    }

    public static String getGender() {
        return gender;
    }

    public static String getBreedtype() {
        return breedtype;
    }

    public static String getMobileno() {
        return mobileno;
    }

    public static String getBatchno() {
        return batchno;
    }

    public static String getUniquekey() {
        return uniquekey;
    }

    public static String getBarcodeno() {
        return barcodeno;
    }

    public static String getPreviousweightingrams() {
        return previousweightingrams;
    }

    public static String getNewweightingrams() {
        return newweightingrams;
    }

    public static String getWeighingpurpose() {
        return weighingpurpose;
    }

    public static String getUpdateddate() {
        return updateddate;
    }
}
