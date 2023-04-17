package com.tmc.tmcb2bpartnerapp.second_version.Modal;

import com.tmc.tmcb2bpartnerapp.modal.Modal_B2BOrderDetails;

public class Modal_ConsolidatedSalesReport  {

    String batchno ="" , retailername ="" , retailerNo ="" , billno ="" , placedtime="";
    double totalWeight = 0 , totalPrice =0 ;
    int male =0 , female =0 , totalCount = 0;
     Modal_B2BOrderDetails modal_b2BOrderDetails = new Modal_B2BOrderDetails();


    public String getPlacedtime() {
        return placedtime;
    }

    public void setPlacedtime(String placedtime) {
        this.placedtime = placedtime;
    }

    public Modal_B2BOrderDetails getModal_b2BOrderDetails() {
        return modal_b2BOrderDetails;
    }

    public void setModal_b2BOrderDetails(Modal_B2BOrderDetails modal_b2BOrderDetails) {
        this.modal_b2BOrderDetails = modal_b2BOrderDetails;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getRetailername() {
        return retailername;
    }

    public void setRetailername(String retailername) {
        this.retailername = retailername;
    }

    public String getRetailerNo() {
        return retailerNo;
    }

    public void setRetailerNo(String retailerNo) {
        this.retailerNo = retailerNo;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getMale() {
        return male;
    }

    public void setMale(int male) {
        this.male = male;
    }

    public int getFemale() {
        return female;
    }

    public void setFemale(int female) {
        this.female = female;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }
}
