package com.tmc.tmcb2bpartnerapp.modal;

public class Modal_UpdatedGoatEarTagDetails {

    public static String updated_selectedItem="",updated_batchno ="",updated_barcodeno ="",updated_loadedweightingrams="",updated_stockedweightingrams="",updated_itemaddeddate="", updated_deliverycenterkey ="", updated_deliverycentername =""
            ,updated_status="",updated_currentweightingrams="",updated_gender="",updated_breedtype="",updated_suppliername="",updated_supplierkey="",updated_description="" , updated_gradeprice="", updated_gradename ="" , updated_gradeKey ="",
            updated_totalWeight="",updated_totalPrice_ofItem ="",updated_Price ="", updated_meatyieldweight ="", updated_discount ="" , updated_partsweight =""  , updated_approxliveweight ="";

    public static boolean updated_selectedItem_boolean= false,updated_batchno_boolean =false,updated_barcodeno_boolean =false,updated_loadedweightingrams_boolean =false,updated_stockedweightingrams_boolean =false,updated_itemaddeddate_boolean =false,updated_deliverycentername_boolean = false , updated_deliverycenterkey_boolean = false
            ,updated_status_boolean =false,updated_currentweightingrams_boolean =false,updated_gender_boolean =false,updated_breedtype_boolean =false,updated_suppliername_boolean =false,updated_supplierkey_boolean =false,updated_description_boolean =false,
            updated_gradename_boolean = false , updated_gradeprice_boolean = false , updated_gradekey_boolean = false ,
            updated_totalPrice_ofItem_boolean = false , updated_Price_boolean = false ,  updated_meatyieldweight_boolean = false ,  updated_discount_boolean = false ,  updated_partsweight_boolean = false ,
            updated_approxliveweight_boolean = false , updated_totalWeight_boolean = false;



    public Modal_UpdatedGoatEarTagDetails() {
        updated_selectedItem="";updated_batchno ="";updated_barcodeno ="";updated_loadedweightingrams="";updated_stockedweightingrams="";updated_itemaddeddate="";
        updated_deliverycenterkey =""; updated_deliverycentername ="";
                updated_status="";updated_currentweightingrams="";updated_gender="";updated_breedtype="";updated_suppliername="";updated_supplierkey="";updated_description="";
                updated_gradeprice=""; updated_gradename =""; updated_totalWeight="";
        updated_totalPrice_ofItem ="";updated_Price =""; updated_meatyieldweight =""; updated_discount ="" ; updated_partsweight ="" ; updated_approxliveweight ="";
        updated_selectedItem_boolean= false ; updated_batchno_boolean =false ; updated_barcodeno_boolean =false;updated_loadedweightingrams_boolean =false; updated_stockedweightingrams_boolean =false ; updated_itemaddeddate_boolean =false ;
                updated_status_boolean =false ; updated_currentweightingrams_boolean =false ;updated_gender_boolean =false;updated_breedtype_boolean =false;updated_suppliername_boolean =false ; updated_supplierkey_boolean =false ;updated_description_boolean =false;
                updated_gradename_boolean = false ; updated_gradeprice_boolean = false ;updated_gradekey_boolean = false;updated_deliverycentername_boolean = false ; updated_deliverycenterkey_boolean = false;
        updated_totalPrice_ofItem_boolean = false ; updated_Price_boolean = false ; updated_meatyieldweight_boolean = false ;  updated_discount_boolean = false ;  updated_partsweight_boolean = false ;
                updated_approxliveweight_boolean = false ; updated_totalWeight_boolean = false;
    }

    public static boolean isUpdated_totalPrice_ofItem_boolean() {
        return updated_totalPrice_ofItem_boolean;
    }

    public static boolean isUpdated_Price_boolean() {
        return updated_Price_boolean;
    }

    public static boolean isUpdated_meatyieldweight_boolean() {
        return updated_meatyieldweight_boolean;
    }

    public static boolean isUpdated_discount_boolean() {
        return updated_discount_boolean;
    }

    public static boolean isUpdated_partsweight_boolean() {
        return updated_partsweight_boolean;
    }

    public static boolean isUpdated_approxliveweight_boolean() {
        return updated_approxliveweight_boolean;
    }

    public static boolean isUpdated_totalWeight_boolean() {
        return updated_totalWeight_boolean;
    }

    public static String getUpdated_totalWeight() {
        return updated_totalWeight;
    }

    public static String getUpdated_Price() {
        return updated_Price;
    }

    public static String getUpdated_totalPrice_ofItem() {
        return updated_totalPrice_ofItem;
    }

    public static String getUpdated_meatyieldweight() {
        return updated_meatyieldweight;
    }

    public static String getUpdated_discount() {
        return updated_discount;
    }

    public static String getUpdated_partsweight() {
        return updated_partsweight;
    }

    public static String getUpdated_approxliveweight() {
        return updated_approxliveweight;
    }

    public static String getUpdated_deliverycenterkey() {
        return updated_deliverycenterkey;
    }

    public static String getUpdated_deliverycentername() {
        return updated_deliverycentername;
    }

    public static String getUpdated_selectedItem() {
        return updated_selectedItem;
    }


    public static String getUpdated_gradeKey() {
        return updated_gradeKey;
    }

    public static String getUpdated_batchno() {
        return updated_batchno;
    }

    public static String getUpdated_barcodeno() {
        return updated_barcodeno;
    }

    public static String getUpdated_loadedweightingrams() {
        return updated_loadedweightingrams;
    }

    public static String getUpdated_stockedweightingrams() {
        return updated_stockedweightingrams;
    }

    public static String getUpdated_itemaddeddate() {
        return updated_itemaddeddate;
    }

    public static String getUpdated_status() {
        return updated_status;
    }

    public static String getUpdated_currentweightingrams() {
        return updated_currentweightingrams;
    }

    public static String getUpdated_gender() {
        return updated_gender;
    }

    public static String getUpdated_breedtype() {
        return updated_breedtype;
    }

    public static String getUpdated_suppliername() {
        return updated_suppliername;
    }

    public static String getUpdated_supplierkey() {
        return updated_supplierkey;
    }

    public static String getUpdated_description() {
        return updated_description;
    }

    public static String getUpdated_gradeprice() {
        return updated_gradeprice;
    }

    public static String getUpdated_gradename() {
        return updated_gradename;
    }


    public static void setUpdated_totalWeight(String updated_totalWeight) {

        double valueFromStaticClass = 0 , valueFromActivity = 0;
        try{
            String text =  String.valueOf(updated_totalWeight.toUpperCase());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }

            valueFromActivity  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }


        try{
            String text =  String.valueOf(Modal_Static_GoatEarTagDetails.getItemWeight());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }

            valueFromStaticClass  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{


            if(valueFromStaticClass!=valueFromActivity){
                Modal_UpdatedGoatEarTagDetails.updated_totalWeight = updated_totalWeight;
                Modal_UpdatedGoatEarTagDetails.updated_totalWeight_boolean = true;
            }
            else{
                Modal_UpdatedGoatEarTagDetails.updated_totalWeight_boolean = false;

            }


        }
        catch (Exception e){
            Modal_UpdatedGoatEarTagDetails.updated_totalWeight_boolean = false;

            e.printStackTrace();
        }




    }

    public static void setUpdated_deliverycenterkey(String updated_deliverycenterkey) {

        if(!Modal_Static_GoatEarTagDetails.getDeliverycenterkey().toUpperCase().equals(updated_deliverycenterkey.toUpperCase())){
            Modal_UpdatedGoatEarTagDetails.updated_deliverycenterkey = updated_deliverycenterkey;
            Modal_UpdatedGoatEarTagDetails.updated_deliverycenterkey_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_deliverycenterkey_boolean = false;

        }




    }

    public static void setUpdated_deliverycentername(String updated_deliverycentername) {
        if(!Modal_Static_GoatEarTagDetails.getDeliverycentername().toUpperCase().equals(updated_deliverycentername.toUpperCase())){
            Modal_UpdatedGoatEarTagDetails.updated_deliverycentername = updated_deliverycentername;
            Modal_UpdatedGoatEarTagDetails.updated_deliverycentername_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_deliverycentername_boolean = false;

        }

    }

    //set
    public static void setUpdated_selectedItem(String updated_selectedItem) {
        if(!Modal_Static_GoatEarTagDetails.getSelecteditem().toUpperCase().equals(updated_selectedItem.toUpperCase())){
            Modal_UpdatedGoatEarTagDetails.updated_selectedItem = updated_selectedItem;
            Modal_UpdatedGoatEarTagDetails.updated_selectedItem_boolean = true;
        }
      else{
            Modal_UpdatedGoatEarTagDetails.updated_selectedItem_boolean = false;

        }

    }


    public static void setUpdated_batchno(String updated_batchno) {
        Modal_UpdatedGoatEarTagDetails.updated_batchno = updated_batchno;
        Modal_UpdatedGoatEarTagDetails.updated_batchno_boolean = true;
    }

    public static void setUpdated_barcodeno(String updated_barcodeno) {
        Modal_UpdatedGoatEarTagDetails.updated_barcodeno = updated_barcodeno;
        Modal_UpdatedGoatEarTagDetails.updated_barcodeno_boolean = true;
    }

    public static void setUpdated_loadedweightingrams(String updated_loadedweightingrams) {
        if(!Modal_Static_GoatEarTagDetails.getLoadedweightingrams().toUpperCase().equals(updated_loadedweightingrams.toUpperCase())){
            Modal_UpdatedGoatEarTagDetails.updated_loadedweightingrams = updated_loadedweightingrams;
            Modal_UpdatedGoatEarTagDetails.updated_loadedweightingrams_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_loadedweightingrams_boolean = false;

        }
    }

    public static void setUpdated_stockedweightingrams(String updated_stockedweightingrams) {

        double valueFromStaticClass = 0 , valueFromActivity = 0;
        try{
            String text =  String.valueOf(updated_totalWeight.toUpperCase());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }

            valueFromActivity  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }


        try{
            String text =  String.valueOf(Modal_Static_GoatEarTagDetails.getItemWeight());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }

            valueFromStaticClass  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{


            if(valueFromStaticClass!=valueFromActivity){


            Modal_UpdatedGoatEarTagDetails.updated_stockedweightingrams = updated_stockedweightingrams;
            Modal_UpdatedGoatEarTagDetails.updated_stockedweightingrams_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_stockedweightingrams_boolean = false;

        }
        }
        catch (Exception e){
            Modal_UpdatedGoatEarTagDetails.updated_stockedweightingrams_boolean = false;

            e.printStackTrace();
        }
    }

    public static void setUpdated_itemaddeddate(String updated_itemaddeddate) {
        if(!Modal_Static_GoatEarTagDetails.getItemaddeddate().toUpperCase().equals(updated_itemaddeddate.toUpperCase())){
            Modal_UpdatedGoatEarTagDetails.updated_itemaddeddate = updated_itemaddeddate;
            Modal_UpdatedGoatEarTagDetails.updated_itemaddeddate_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_itemaddeddate_boolean = false;

        }

    }

    public static void setUpdated_status(String updated_status) {
        if(!Modal_Static_GoatEarTagDetails.getStatus().toUpperCase().equals(updated_status.toUpperCase())){
            Modal_UpdatedGoatEarTagDetails.updated_status = updated_status;
            Modal_UpdatedGoatEarTagDetails.updated_status_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_status_boolean = false;

        }
    }

    public static void setUpdated_currentweightingrams(String updated_currentweightingrams) {


        double valueFromStaticClass = 0 , valueFromActivity = 0;
        try{
            String text =  String.valueOf(updated_currentweightingrams.toUpperCase());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }

            valueFromActivity  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }


        try{
            String text =  String.valueOf(Modal_Static_GoatEarTagDetails.getCurrentweightingrams());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }

            valueFromStaticClass  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{


            if(valueFromStaticClass!=valueFromActivity){

            Modal_UpdatedGoatEarTagDetails.updated_currentweightingrams = updated_currentweightingrams;
            Modal_UpdatedGoatEarTagDetails.updated_currentweightingrams_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_currentweightingrams_boolean = false;

        }
        }
        catch (Exception e){
            Modal_UpdatedGoatEarTagDetails.updated_currentweightingrams_boolean = false;
            e.printStackTrace();
        }

        }

    public static void setUpdated_gender(String updated_gender) {
        if(!Modal_Static_GoatEarTagDetails.getGender().toUpperCase().equals(updated_gender.toUpperCase())){
            Modal_UpdatedGoatEarTagDetails.updated_gender = updated_gender;
            Modal_UpdatedGoatEarTagDetails.updated_gender_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_gender_boolean = false;

        }
    }

    public static void setUpdated_breedtype(String updated_breedtype) {
        if(!Modal_Static_GoatEarTagDetails.getBreedtype().toUpperCase().equals(updated_breedtype.toUpperCase())){
            Modal_UpdatedGoatEarTagDetails.updated_breedtype = updated_breedtype;
            Modal_UpdatedGoatEarTagDetails.updated_breedtype_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_breedtype_boolean = false;

        }
    }

    public static void setUpdated_suppliername(String updated_suppliername) {
        if(!Modal_Static_GoatEarTagDetails.getSuppliername().toUpperCase().equals(updated_suppliername.toUpperCase())){
            Modal_UpdatedGoatEarTagDetails.updated_suppliername = updated_suppliername;
            Modal_UpdatedGoatEarTagDetails.updated_suppliername_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_suppliername_boolean = false;

        }
    }

    public static void setUpdated_supplierkey(String updated_supplierkey) {
        if(!Modal_Static_GoatEarTagDetails.getSupplierkey().toUpperCase().equals(updated_supplierkey.toUpperCase())){
            Modal_UpdatedGoatEarTagDetails.updated_supplierkey = updated_supplierkey;
            Modal_UpdatedGoatEarTagDetails.updated_supplierkey_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_supplierkey_boolean = false;

        }
    }

    public static void setUpdated_gradeprice(String updated_gradeprice) {
        if(!Modal_Static_GoatEarTagDetails.getGradeprice().toUpperCase().equals(updated_gradeprice.toUpperCase())){
            Modal_UpdatedGoatEarTagDetails.updated_gradeprice = updated_gradeprice;
            Modal_UpdatedGoatEarTagDetails.updated_gradeprice_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_gradeprice_boolean = false;

        }

    }

    public static void setUpdated_gradename(String updated_gradename) {
        if(!Modal_Static_GoatEarTagDetails.getGradename().toUpperCase().equals(updated_gradename.toUpperCase())){
            Modal_UpdatedGoatEarTagDetails.updated_gradename = updated_gradename;
            Modal_UpdatedGoatEarTagDetails.updated_gradename_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_gradename_boolean = false;

        }
    }

    public static void setUpdated_gradeKey(String updated_gradeKey) {
        if(!Modal_Static_GoatEarTagDetails.getGradekey().toUpperCase().equals(updated_gradeKey.toUpperCase())){
            Modal_UpdatedGoatEarTagDetails.updated_gradeKey = updated_gradeKey;
            Modal_UpdatedGoatEarTagDetails.updated_gradekey_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_gradekey_boolean = false;

        }

    }

    public static void setUpdated_description(String updated_description) {
        if(!Modal_Static_GoatEarTagDetails.getDescription().toUpperCase().equals(updated_description.toUpperCase())){
            Modal_UpdatedGoatEarTagDetails.updated_description = updated_description;
            Modal_UpdatedGoatEarTagDetails.updated_description_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_description_boolean = false;

        }
        Modal_UpdatedGoatEarTagDetails.updated_description = updated_description;
    }



    public static void setUpdated_meatyieldweight(String updated_meatyieldweight) {


        double valueFromStaticClass = 0 , valueFromActivity = 0;
        try{
            String text =  String.valueOf(updated_meatyieldweight.toUpperCase());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }

            valueFromActivity  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }


        try{
            String text =  String.valueOf(Modal_Static_GoatEarTagDetails.getMeatyieldweight());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }

            valueFromStaticClass  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{


            if(valueFromStaticClass!=valueFromActivity){

            Modal_UpdatedGoatEarTagDetails.updated_meatyieldweight = updated_meatyieldweight;
            Modal_UpdatedGoatEarTagDetails.updated_meatyieldweight_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_meatyieldweight_boolean = false;

        }
        }
        catch (Exception e){
            Modal_UpdatedGoatEarTagDetails.updated_meatyieldweight_boolean = false;
            e.printStackTrace();
        }
    }

    public static void setUpdated_totalPrice_ofItem(String updated_totalPrice_ofItem) {



        double valueFromStaticClass = 0 , valueFromActivity = 0;
        try{
            String text =  String.valueOf(updated_totalPrice_ofItem.toUpperCase());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }

            valueFromActivity  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }


        try{
            String text =  String.valueOf(Modal_Static_GoatEarTagDetails.getTotalPrice_ofItem());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }

            valueFromStaticClass  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{


            if(valueFromStaticClass!=valueFromActivity){

            Modal_UpdatedGoatEarTagDetails.updated_totalPrice_ofItem = updated_totalPrice_ofItem;
            Modal_UpdatedGoatEarTagDetails.updated_totalPrice_ofItem_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_totalPrice_ofItem_boolean = false;

        }


        }
        catch (Exception e){
            e.printStackTrace();
            Modal_UpdatedGoatEarTagDetails.updated_totalPrice_ofItem_boolean = false;
        }


    }

    public static void setUpdated_Price(String updated_Price) {

        double valueFromStaticClass = 0 , valueFromActivity = 0;
        try{
            String text =  String.valueOf(updated_Price.toUpperCase());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }

            valueFromActivity  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }


        try{
            String text =  String.valueOf(Modal_Static_GoatEarTagDetails.getItemPrice());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }

            valueFromStaticClass  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{


            if(valueFromStaticClass!=valueFromActivity){
            Modal_UpdatedGoatEarTagDetails.updated_Price = updated_Price;
            Modal_UpdatedGoatEarTagDetails.updated_Price_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_Price_boolean = false;

        }
        }
        catch (Exception e){
            Modal_UpdatedGoatEarTagDetails.updated_Price_boolean = false;
            e.printStackTrace();
        }

    }

    public static void setUpdated_discount(String updated_discount) {



        double valueFromStaticClass = 0 , valueFromActivity = 0;
        try{
            String text =  String.valueOf(updated_discount.toUpperCase());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }

            valueFromActivity  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }


        try{
            String text =  String.valueOf(Modal_Static_GoatEarTagDetails.getDiscount());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }

            valueFromStaticClass  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{


            if(valueFromStaticClass!=valueFromActivity){

            Modal_UpdatedGoatEarTagDetails.updated_discount = updated_discount;
            Modal_UpdatedGoatEarTagDetails.updated_discount_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_discount_boolean = false;

        }
        }
        catch (Exception e){
            Modal_UpdatedGoatEarTagDetails.updated_discount_boolean = false;

            e.printStackTrace();
        }

    }

    public static void setUpdated_partsweight(String updated_partsweight) {



        double valueFromStaticClass = 0 , valueFromActivity = 0;
        try{
            String text =  String.valueOf(updated_partsweight.toUpperCase());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }

            valueFromActivity  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }


        try{
            String text =  String.valueOf(Modal_Static_GoatEarTagDetails.getPartsweight());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }

            valueFromStaticClass  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{


            if(valueFromStaticClass!=valueFromActivity){


            Modal_UpdatedGoatEarTagDetails.updated_partsweight = updated_partsweight;
            Modal_UpdatedGoatEarTagDetails.updated_partsweight_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_partsweight_boolean = false;

        }
        }
        catch (Exception e){
            Modal_UpdatedGoatEarTagDetails.updated_partsweight_boolean = false;
            e.printStackTrace();
        }
    }

    public static void setUpdated_approxliveweight(String updated_approxliveweight) {




        double valueFromStaticClass = 0 , valueFromActivity = 0;
        try{
            String text =  String.valueOf(updated_approxliveweight.toUpperCase());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }

            valueFromActivity  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }


        try{
            String text =  String.valueOf(Modal_Static_GoatEarTagDetails.getApproxliveweight());
            text = text.replaceAll("[^\\d.]", "");
            if(text.equals("")){
                text = "0";
            }

            valueFromStaticClass  = Double.parseDouble(text);


        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{


            if(valueFromStaticClass!=valueFromActivity){



            Modal_UpdatedGoatEarTagDetails.updated_approxliveweight = updated_approxliveweight;
            Modal_UpdatedGoatEarTagDetails.updated_approxliveweight_boolean = true;
        }
        else{
            Modal_UpdatedGoatEarTagDetails.updated_approxliveweight_boolean = false;

        }
        }
        catch (Exception e){
            Modal_UpdatedGoatEarTagDetails.updated_approxliveweight_boolean = false;

            e.printStackTrace();
        }
    }

    //get boolean

    public static boolean isUpdated_selectedItem_boolean() {
        return updated_selectedItem_boolean;
    }



    public static boolean isUpdated_batchno_boolean() {
        return updated_batchno_boolean;
    }

    public static boolean isUpdated_barcodeno_boolean() {
        return updated_barcodeno_boolean;
    }

    public static boolean isUpdated_loadedweightingrams_boolean() {
        return updated_loadedweightingrams_boolean;
    }

    public static boolean isUpdated_stockedweightingrams_boolean() {
        return updated_stockedweightingrams_boolean;
    }

    public static boolean isUpdated_itemaddeddate_boolean() {
        return updated_itemaddeddate_boolean;
    }

    public static boolean isUpdated_status_boolean() {
        return updated_status_boolean;
    }

    public static boolean isUpdated_currentweightingrams_boolean() {
        return updated_currentweightingrams_boolean;
    }

    public static boolean isUpdated_gender_boolean() {
        return updated_gender_boolean;
    }

    public static boolean isUpdated_breedtype_boolean() {
        return updated_breedtype_boolean;
    }

    public static boolean isUpdated_suppliername_boolean() {
        return updated_suppliername_boolean;
    }

    public static boolean isUpdated_supplierkey_boolean() {
        return updated_supplierkey_boolean;
    }

    public static boolean isUpdated_description_boolean() {
        return updated_description_boolean;
    }

    public static boolean isUpdated_gradename_boolean() {
        return updated_gradename_boolean;
    }

    public static boolean isUpdated_gradeprice_boolean() {
        return updated_gradeprice_boolean;
    }

    public static boolean isUpdated_gradekey_boolean() {
        return updated_gradekey_boolean;
    }

    public static boolean isUpdated_deliverycentername_boolean() {
        return updated_deliverycentername_boolean;
    }

    public static boolean isUpdated_deliverycenterkey_boolean() {
        return updated_deliverycenterkey_boolean;
    }
}
