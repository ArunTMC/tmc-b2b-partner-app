package com.tmc.tmcb2bpartnerapp.model;

public class Modal_B2BBatchDetailsUpdate  {
     public static String supplierkey ="", batchno="",suppliername="",suppliermobileno ="",deliverycentername="", deliverycenterkey ="",
            status="", sentdate ="" , receiveddate ="",createddate="",
            receivermobileno ="",loadedweightingrams ="", stockedweightingrams ="",itemcount="";


    public Modal_B2BBatchDetailsUpdate() {
        supplierkey =""; batchno="";suppliername="";suppliermobileno ="";deliverycentername="";deliverycenterkey ="";
                status=""; sentdate ="" ; receiveddate ="";createddate="";
                receivermobileno ="";loadedweightingrams =""; stockedweightingrams ="";itemcount="";
    }

    public static String getSupplierkey() {
        return supplierkey;
    }

    public static String getBatchno() {
        return batchno;
    }

    public static String getSuppliername() {
        return suppliername;
    }

    public static String getSuppliermobileno() {
        return suppliermobileno;
    }

    public static String getDeliverycentername() {
        return deliverycentername;
    }

    public static String getDeliverycenterkey() {
        return deliverycenterkey;
    }

    public static String getStatus() {
        return status;
    }

    public static String getSentdate() {
        return sentdate;
    }

    public static String getReceiveddate() {
        return receiveddate;
    }

    public static String getCreateddate() {
        return createddate;
    }

    public static String getReceivermobileno() {
        return receivermobileno;
    }

    public static String getLoadedweightingrams() {
        return loadedweightingrams;
    }

    public static String getStockedweightingrams() {
        return stockedweightingrams;
    }

    public static String getItemcount() {
        return itemcount;
    }

    public void setSupplierkey(String supplierkey) {

            this.supplierkey = supplierkey;

    }

    public void setBatchno(String batchno) {

            this.batchno = batchno;


    }

    public void setSuppliername(String suppliername) {
        if(!Modal_B2BBatchDetailsStatic.suppliername.toUpperCase().equals(suppliername.toUpperCase())){
            this.suppliername = suppliername;
        }
        else{
            this.suppliername = "";
        }

    }

    public void setSuppliermobileno(String suppliermobileno) {
        if(!Modal_B2BBatchDetailsStatic.suppliermobileno.toUpperCase().equals(suppliermobileno.toUpperCase())){
            this.suppliermobileno = suppliermobileno;
        }
        else{
            this.suppliermobileno = "";
        }

    }

    public void setDeliverycentername(String deliverycentername) {
        if(!Modal_B2BBatchDetailsStatic.deliverycentername.toUpperCase().equals(deliverycentername.toUpperCase())){
            this.deliverycentername = deliverycentername;
        }
        else{
            this.deliverycentername = "";
        }

    }

    public void setDeliverycenterkey(String deliverycenterkey) {
        if(!Modal_B2BBatchDetailsStatic.deliverycenterkey.toUpperCase().equals(deliverycenterkey.toUpperCase())){
            this.deliverycenterkey = deliverycenterkey;
        }
        else{
            this.deliverycenterkey = "";
        }

    }

    public void setStatus(String status) {
        if(!Modal_B2BBatchDetailsStatic.status.toUpperCase().equals(status.toUpperCase())){
            this.status = status;
        }
        else{
            this.status = "";
        }

    }

    public void setSentdate(String sentdate) {
        if(!Modal_B2BBatchDetailsStatic.sentdate.toUpperCase().equals(sentdate.toUpperCase())){
            this.sentdate = sentdate;
        }
        else{
            this.sentdate = "";
        }

    }

    public void setReceiveddate(String receiveddate) {
        if(!Modal_B2BBatchDetailsStatic.receiveddate.toUpperCase().equals(receiveddate.toUpperCase())){
            this.receiveddate = receiveddate;
        }
        else{
            this.receiveddate = "";
        }

    }

    public void setCreateddate(String createddate) {
        if(!Modal_B2BBatchDetailsStatic.createddate.toUpperCase().equals(createddate.toUpperCase())){
            this.createddate = createddate;
        }
        else{
            this.createddate = "";
        }

    }

    public void setReceivermobileno(String receivermobileno) {
        if(!Modal_B2BBatchDetailsStatic.receivermobileno.toUpperCase().equals(receivermobileno.toUpperCase())){
            this.receivermobileno = receivermobileno;
        }
        else{
            this.receivermobileno = "";
        }

    }

    public void setLoadedweightingrams(String loadedweightingrams) {
        if(!Modal_B2BBatchDetailsStatic.loadedweightingrams.toUpperCase().equals(loadedweightingrams.toUpperCase())){
            this.loadedweightingrams = loadedweightingrams;
        }
        else{
            this.loadedweightingrams = "";
        }

    }

    public void setStockedweightingrams(String stockedweightingrams) {
        if(!Modal_B2BBatchDetailsStatic.stockedweightingrams.toUpperCase().equals(stockedweightingrams.toUpperCase())){
            this.stockedweightingrams = stockedweightingrams;
        }
        else{
            this.stockedweightingrams = "";
        }

    }

    public void setItemcount(String itemcount) {
        if(!Modal_B2BBatchDetailsStatic.itemcount.toUpperCase().equals(itemcount.toUpperCase())){
            this.itemcount = itemcount;
        }
        else{
            this.itemcount = "";
        }

    }
}
