package com.tmc.tmcb2bpartnerapp.modal;

import com.tmc.tmcb2bpartnerapp.utils.Constants;

import java.text.DecimalFormat;

public class Modal_B2BCartItemDetails {

    public String barcodeno = "", batchno = "", itemaddeddate = "", weightingrams = "", gender = "", breedtype = "", status = "",weightbeforechanging = "",
            gradekey ="",oldgradekey ="",orderid = "",b2bctgykey ="" , b2bsubctgykey ="" , oldweightingrams="" , gradename ="" , gradeprice ="",
            oldgradeprice ="" ,supplierkey ="", suppliername="",totalPrice_ofItem ="", meatyieldweight ="", discount ="" , partsweight =""  , approxliveweight =""
            ,itemprice= "",totalItemWeight = "";


    String dicountweightageAmount = "0" , dicountweightageAmountPercentage = "0" ;

    DecimalFormat threeDecimalConverter = new DecimalFormat(Constants.threeDecimalPattern);
    DecimalFormat twoDecimalConverter = new DecimalFormat(Constants.twoDecimalPattern);


    public void setDicountweightageAmount(String dicountweightageAmount) {
        try {
            dicountweightageAmount = String.valueOf(Double.parseDouble(twoDecimalConverter .format(Double.parseDouble(dicountweightageAmount))));
        }
        catch (Exception e ){
            dicountweightageAmount  ="0";
            e.printStackTrace();
        }

        this.dicountweightageAmount = dicountweightageAmount;
    }

    public void setDicountweightageAmountPercentage(String dicountweightageAmountPercentage) {

        try {
            dicountweightageAmountPercentage = String.valueOf(Double.parseDouble(twoDecimalConverter .format(Double.parseDouble(dicountweightageAmountPercentage))));
        }
        catch (Exception e ){
            dicountweightageAmount  ="0";
            e.printStackTrace();
        }


        this.dicountweightageAmountPercentage = dicountweightageAmountPercentage;
    }

    public String getDicountweightageAmount() {
        return dicountweightageAmount;
    }

    public String getDicountweightageAmountPercentage() {
        return dicountweightageAmountPercentage;
    }


    public String getTotalItemWeight() {
        return totalItemWeight;
    }

    public void setTotalItemWeight(String totalItemWeight) {
        try {
            totalItemWeight = String.valueOf(Double.parseDouble(threeDecimalConverter .format(Double.parseDouble(totalItemWeight))));
        }
        catch (Exception e ){
            totalItemWeight  ="0";
            e.printStackTrace();
        }
        this.totalItemWeight = totalItemWeight;
    }

    public void setTotalPrice_ofItem(String totalPrice_ofItem) {
        try {
            totalPrice_ofItem = String.valueOf(Double.parseDouble(twoDecimalConverter .format(Double.parseDouble(totalPrice_ofItem))));
        }
        catch (Exception e ){
            totalPrice_ofItem  ="0";
            e.printStackTrace();
        }
        this.totalPrice_ofItem = totalPrice_ofItem;
    }

    public void setMeatyieldweight(String meatyieldweight) {
        try {
            meatyieldweight = String.valueOf(Double.parseDouble(threeDecimalConverter .format(Double.parseDouble(meatyieldweight))));
        }
        catch (Exception e ){
            meatyieldweight  ="0";
            e.printStackTrace();
        }

        this.meatyieldweight = meatyieldweight;
    }

    public void setDiscount(String discount) {
        try {
            discount = String.valueOf(Double.parseDouble(twoDecimalConverter .format(Double.parseDouble(discount))));
        }
        catch (Exception e ){
            discount  ="0";
            e.printStackTrace();
        }
        this.discount = discount;
    }

    public void setPartsweight(String partsweight) {
        try {
            partsweight = String.valueOf(Double.parseDouble(threeDecimalConverter .format(Double.parseDouble(partsweight))));
        }
        catch (Exception e ){
            partsweight  ="0";
            e.printStackTrace();
        }

        this.partsweight = partsweight;
    }

    public void setApproxliveweight(String approxliveweight) {

        try {
            approxliveweight = String.valueOf(Double.parseDouble(threeDecimalConverter .format(Double.parseDouble(approxliveweight))));
        }
        catch (Exception e ){
            approxliveweight  ="0";
            e.printStackTrace();
        }

        this.approxliveweight = approxliveweight;
    }

    public void setItemprice(String itemprice) {
        try {
            itemprice = String.valueOf(Double.parseDouble(twoDecimalConverter .format(Double.parseDouble(itemprice))));
        }
        catch (Exception e ){
            itemprice  ="0";
            e.printStackTrace();
        }
        this.itemprice = itemprice;
    }

    public String getItemprice() {
        return itemprice;
    }

    public String getTotalPrice_ofItem() {
        return totalPrice_ofItem;
    }


    public String getMeatyieldweight() {
        return meatyieldweight;
    }


    public String getDiscount() {
        return discount;
    }


    public String getPartsweight() {
        return partsweight;
    }


    public String getApproxliveweight() {
        return approxliveweight;
    }


    public String getSupplierkey() {
        return supplierkey;
    }

    public void setSupplierkey(String supplierkey) {
        this.supplierkey = supplierkey;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername;
    }

    public String getWeightbeforechanging() {
        return weightbeforechanging;
    }

    public void setWeightbeforechanging(String weightbeforechanging) {
        this.weightbeforechanging = weightbeforechanging;
    }

    public String getOldgradekey() {
        return oldgradekey;
    }

    public void setOldgradekey(String oldgradekey) {
        this.oldgradekey = oldgradekey;
    }

    public String getOldgradeprice() {
        return oldgradeprice;
    }

    public void setOldgradeprice(String oldgradeprice) {
        this.oldgradeprice = oldgradeprice;
    }

    public String getGradekey() {
        return gradekey;
    }

    public void setGradekey(String gradekey) {
        this.gradekey = gradekey;
    }

    public String getOldweightingrams() {
        return oldweightingrams;
    }

    public void setOldweightingrams(String oldweightingrams) {
        this.oldweightingrams = oldweightingrams;
    }

    public String getB2bctgykey() {
        return b2bctgykey;
    }

    public void setB2bctgykey(String b2bctgykey) {
        this.b2bctgykey = b2bctgykey;
    }

    public String getB2bsubctgykey() {
        return b2bsubctgykey;
    }

    public void setB2bsubctgykey(String b2bsubctgykey) {
        this.b2bsubctgykey = b2bsubctgykey;
    }

    public String getBarcodeno() {
        return barcodeno;
    }

    public void setBarcodeno(String barcodeno) {
        this.barcodeno = barcodeno;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getItemaddeddate() {
        return itemaddeddate;
    }

    public void setItemaddeddate(String itemaddeddate) {
        this.itemaddeddate = itemaddeddate;
    }

    public String getWeightingrams() {
        return weightingrams;
    }

    public void setWeightingrams(String weightingrams) {
        this.weightingrams = weightingrams;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBreedtype() {
        return breedtype;
    }

    public void setBreedtype(String breedtype) {
        this.breedtype = breedtype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getGradename() {
        return gradename;
    }

    public void setGradename(String gradename) {
        this.gradename = gradename;
    }

    public String getGradeprice() {
        return gradeprice;
    }

    public void setGradeprice(String gradeprice) {
        this.gradeprice = gradeprice;
    }
}
