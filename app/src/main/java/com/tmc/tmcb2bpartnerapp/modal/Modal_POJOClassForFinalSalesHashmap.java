package com.tmc.tmcb2bpartnerapp.modal;

import com.tmc.tmcb2bpartnerapp.utils.Constants;

import java.text.DecimalFormat;

public class Modal_POJOClassForFinalSalesHashmap {

    String key ="" ;
    int maleqty =0 , femaleqty = 0 , totalqty =0 ;
    double totalweight = 0 , totalfemaleweight = 0 , totalmaleweight =0 ,maleprice = 0, femaleprice =0 ,totalprice =0 ;

     DecimalFormat threeDecimalConverter = new DecimalFormat(Constants.threeDecimalPattern);
      DecimalFormat twoDecimalConverter = new DecimalFormat(Constants.twoDecimalPattern);

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getMaleqty() {
        return maleqty;
    }

    public void setMaleqty(int maleqty) {
        this.maleqty = maleqty;
    }

    public int getFemaleqty() {
        return femaleqty;
    }

    public void setFemaleqty(int femaleqty) {
        this.femaleqty = femaleqty;
    }

    public int getTotalqty() {
        return totalqty;
    }

    public void setTotalqty(int totalqty) {
        this.totalqty = totalqty;
    }

    public double getTotalweight() {
        return totalweight;
    }

    public void setTotalweight(double totalweight) {
        try {
            totalweight = Double.parseDouble(threeDecimalConverter .format(totalweight));
        }
        catch (Exception e ){
            totalweight  =0;
            e.printStackTrace();
        }


        this.totalweight = totalweight;
    }

    public double getTotalfemaleweight() {
        return totalfemaleweight;
    }

    public void setTotalfemaleweight(double totalfemaleweight) {
        try {
            totalfemaleweight = Double.parseDouble(threeDecimalConverter .format(totalfemaleweight));
        }
        catch (Exception e ){
            totalfemaleweight  =0;
            e.printStackTrace();
        }

        this.totalfemaleweight = totalfemaleweight;
    }

    public double getTotalmaleweight() {
        return totalmaleweight;
    }

    public void setTotalmaleweight(double totalmaleweight) {
        try {
            totalmaleweight = Double.parseDouble(threeDecimalConverter .format(totalmaleweight));
        }
        catch (Exception e ){
            totalmaleweight  =0;
            e.printStackTrace();
        }

        this.totalmaleweight = totalmaleweight;
    }

    public double getMaleprice() {
        return maleprice;
    }

    public void setMaleprice(double maleprice) {
        try {
            maleprice = Double.parseDouble(twoDecimalConverter .format(maleprice));
        }
        catch (Exception e ){
            maleprice  =0;
            e.printStackTrace();
        }

        this.maleprice = maleprice;
    }

    public double getFemaleprice() {
        return femaleprice;
    }

    public void setFemaleprice(double femaleprice) {
        try {
            femaleprice = Double.parseDouble(twoDecimalConverter .format(femaleprice));
        }
        catch (Exception e ){
            femaleprice =0;
            e.printStackTrace();
        }

        this.femaleprice = femaleprice;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        try {
            totalprice = Double.parseDouble(twoDecimalConverter.format(totalprice));
        }
        catch (Exception e ){
            totalprice =0;
            e.printStackTrace();
        }
        this.totalprice = totalprice;
    }
}
