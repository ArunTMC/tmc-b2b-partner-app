package com.tmc.tmcb2bpartnerapp.utils;

import com.tmc.tmcb2bpartnerapp.model.Modal_B2BGoatGradeDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BItemCtgy;
import com.tmc.tmcb2bpartnerapp.model.Modal_B2BRetailerDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_DeliveryCenterDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_GoatEarTagDetails;
import com.tmc.tmcb2bpartnerapp.model.Modal_SupplierDetails;

import java.util.ArrayList;
import java.util.List;

public class DatabaseArrayList_PojoClass {

    public DatabaseArrayList_PojoClass() {
    }

    public static List<Modal_DeliveryCenterDetails> deliveryCenterDetailsList = new ArrayList<>();
    public static ArrayList<Modal_SupplierDetails> supplierDetailsArrayList = new ArrayList<>();
    public static  ArrayList<String> breedType_arrayList_string = new ArrayList<>();
    public static  ArrayList<Modal_B2BItemCtgy> breedType_arrayList = new ArrayList<>();
    public static  ArrayList<Modal_GoatEarTagDetails> eartagDetails_arrayList = new ArrayList<>();
    public static ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList = new ArrayList<>();
    public static ArrayList<Modal_B2BGoatGradeDetails> goatGradeDetailsArrayList = new ArrayList<>();


    public static ArrayList<Modal_B2BGoatGradeDetails> getGoatGradeDetailsArrayList() {
        return goatGradeDetailsArrayList;
    }

    public static void setGoatGradeDetailsArrayList(ArrayList<Modal_B2BGoatGradeDetails> goatGradeDetailsArrayList) {
        DatabaseArrayList_PojoClass.goatGradeDetailsArrayList.clear();
        DatabaseArrayList_PojoClass.goatGradeDetailsArrayList .addAll(goatGradeDetailsArrayList);
    }

    public static List<Modal_B2BRetailerDetails> getRetailerDetailsArrayList() {
        return retailerDetailsArrayList;
    }

    public static void setRetailerDetailsArrayList(ArrayList<Modal_B2BRetailerDetails> retailerDetailsArrayList) {
        DatabaseArrayList_PojoClass.retailerDetailsArrayList.clear();
        DatabaseArrayList_PojoClass.retailerDetailsArrayList .addAll(retailerDetailsArrayList);
    }

    public static ArrayList<Modal_GoatEarTagDetails> getEartagDetails_arrayList() {
        return eartagDetails_arrayList;
    }

    public static void setEartagDetails_arrayList(ArrayList<Modal_GoatEarTagDetails> eartagDetails_arrayList) {
        DatabaseArrayList_PojoClass.eartagDetails_arrayList = eartagDetails_arrayList;
    }

    public static ArrayList<Modal_B2BItemCtgy> getBreedType_arrayList() {
        return breedType_arrayList;
    }

    public static void setBreedType_arrayList(ArrayList<Modal_B2BItemCtgy> breedType_arrayList) {
        DatabaseArrayList_PojoClass.breedType_arrayList.clear();
        DatabaseArrayList_PojoClass.breedType_arrayList .addAll(breedType_arrayList);
    }

    public static ArrayList<String> getBreedType_arrayList_string() {
        return breedType_arrayList_string;
    }

    public static void setBreedType_arrayList_string(ArrayList<String> breedType_arrayList_string) {
        DatabaseArrayList_PojoClass.breedType_arrayList_string = breedType_arrayList_string;
    }

    public static List<Modal_SupplierDetails> getSupplierDetailsArrayList() {
        return supplierDetailsArrayList;
    }

    public static void setSupplierDetailsArrayList(ArrayList<Modal_SupplierDetails> supplierDetailsArrayListt) {
        DatabaseArrayList_PojoClass.supplierDetailsArrayList.clear();
        DatabaseArrayList_PojoClass.supplierDetailsArrayList .addAll(supplierDetailsArrayListt);
    }

    public static List<Modal_DeliveryCenterDetails> getDeliveryCenterDetailsList() {
        return deliveryCenterDetailsList;
    }

    public static void setDeliveryCenterDetailsList(List<Modal_DeliveryCenterDetails> deliveryCenterDetailsListt) {
        DatabaseArrayList_PojoClass.deliveryCenterDetailsList.clear();
        DatabaseArrayList_PojoClass.deliveryCenterDetailsList = deliveryCenterDetailsListt;
    }
}
