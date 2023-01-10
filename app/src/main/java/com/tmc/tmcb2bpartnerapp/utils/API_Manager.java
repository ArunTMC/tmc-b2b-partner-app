package com.tmc.tmcb2bpartnerapp.utils;

public class API_Manager {
    //General
    public static final  String getAppUserAccessWithMobileNo = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/b2bappuseraccessusingmobileno";
    public static final  String generateBatchId  = "https://tym073r18h.execute-api.ap-south-1.amazonaws.com/dev/generatebatchid?batchnokey=key";
    public static final  String getBatchId  = "https://tym073r18h.execute-api.ap-south-1.amazonaws.com/dev/getbatchid?batchnokey=key";
    public static final  String check_or_Update_B2BUserDetails  = "https://w430rhwyyc.execute-api.ap-south-1.amazonaws.com/dev/b2bpartnerappuser";
    public static final  String getB2BItemCtgy = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/list?modulename=B2BCtgyModule";
    public static final  String getInvoiceNo  = "https://9iozauklk3.execute-api.ap-south-1.amazonaws.com/dev/getinvoiceno?invoicekey=key";
    public static final  String generateInvoiceNo  = "https://9iozauklk3.execute-api.ap-south-1.amazonaws.com/dev/generateinvoiceno?invoicekey=key";




    //Supplier
    public static final  String getsupplierDetailsWithSupplierKey  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/b2bsupplierdetailsforsupplierid?supplierkey=";
    public static final  String getsupplierDetailsList  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/list?modulename=B2BSupplierModule";

   //DeliveryCenter
    public static final  String getDeliveryCenterList  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/list?modulename=B2BDeliveryModule";
    public static final  String getDeliveryCenterWithKey  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/deliverycenterfordeliverycenterkey";


    //BatchDetails
    public static final  String addBatchDetails  = "https://w430rhwyyc.execute-api.ap-south-1.amazonaws.com/dev/batchdetails";
    public static final  String getBatchDetailsWithSupplierkeyBatchNo  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/supplierdetailsforsupplierkeywithbatchno";
    public static final  String updateBatchDetailsWithSupplierkeyBatchNo  = "https://69wey38qj4.execute-api.ap-south-1.amazonaws.com/dev/b2bbatchdetails";
    public static final  String getBatchDetailsWithSupplierkeyDeliveryCenterAndStatus  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/b2bbatchdetailsforsupplierkeydeliverycenterkeystatus";
    public static final  String getBatchDetailsWithSupplierkeyDeliveryCenterAndStatusFromToDate  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/b2bbatchdetailssupplierdelvrycentrstatusfromtodate";
    public static final  String updateBatchDetailsWithSupplierkeyFromToDate  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/b2bbatchdetailssupplierkeyfromtodate";



    //GoatEarTagDetails
    public static final  String addGoatEarTagDetails  = "https://w430rhwyyc.execute-api.ap-south-1.amazonaws.com/dev/goateartagdetails";
    public static final  String getGoatEarTagDetails_forBarcodeWithBatchno = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/goateartagdetailsforbatchnowithbarcodeno";
    public static final  String getGetGoatEarTagDetails_forBatchno  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/eartagdetailslistforbatchno?batchno=";
    public static final  String getGetGoatEarTagDetails_forBatchnoWithStatus  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/goateartagdetailsforbatchnowithstatus";
    public static final  String updateGoatEarTag  = "https://69wey38qj4.execute-api.ap-south-1.amazonaws.com/dev/goateartagdetails";
    public static final  String getGetGoatEarTagDetails_forBatchnoWithVariousStatus  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/goateartagdetailsforbatchnowithvariousstatus";
    public static final  String getgoatEarTagDetailsForbarcodenowithVariousStatus_deliveryCenter = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/goateartagdetailsforbarcodenowithstatus";
    public static final  String getGetGoatEarTagDetails_forBarcodeNoWithDeliveryCentreKey = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/goateartagdetailsforbarcodeno_deliverycentrekey";


    //GoatEarTagTransaction
    public static final  String addGoatEarTagTransactions  = "https://w430rhwyyc.execute-api.ap-south-1.amazonaws.com/dev/goateartagtransactions";



    //RetailerDetails
    public static final  String getretailerDetailsList  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/list?modulename=B2BRetailersModule";
    public static final  String addRetailerDetailsList  = "https://w430rhwyyc.execute-api.ap-south-1.amazonaws.com/dev/retailerdetails";




    //B2BOrderDetails
    public static final  String addOrderDetailsList  = "https://w430rhwyyc.execute-api.ap-south-1.amazonaws.com/dev/b2borderdetails";
    public static final  String getOrderDetailsForDeliveryCentrekeySlotdateWithStatus  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/b2borderdetailsfordeliverycenterkeyslotdateandstatus";
    public static final  String updateOrderDetails  = "https://69wey38qj4.execute-api.ap-south-1.amazonaws.com/dev/b2borderdetails";



    //B2BOrderItemDetails
    public static final  String addOrderItemDetails  = "https://w430rhwyyc.execute-api.ap-south-1.amazonaws.com/dev/b2borderitemdetails";
    public static final  String getOrderItemDetailsForOrderid  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/b2borderitemdetailsfororderid?orderid=";



    //B2BCartItemDetails
    public static final  String addCartItemDetails = "https://w430rhwyyc.execute-api.ap-south-1.amazonaws.com/dev/b2bcartdetails";
    public static final  String getCartDetailsForOrderid  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/b2bcartdetails?orderid=";
    public static final  String updateCartItemDetails = "https://69wey38qj4.execute-api.ap-south-1.amazonaws.com/dev/b2bcartitemdetails";
    public static final  String deleteCartItemDetails = "https://s5my3jk0y6.execute-api.ap-south-1.amazonaws.com/dev/b2bcartitemdetails";
    public static final  String getCartDetailsForOrderidWithBarcodeNo  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/b2bcartitemdetailsorderidwithbarcodeno";



    //B2BCartOrderDetails
    public static final  String addCartOrderDetails = "https://w430rhwyyc.execute-api.ap-south-1.amazonaws.com/dev/b2bcartorderdetails";
    public static final  String getCartOrderDetailsForOrderidWithBatchno  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/b2bcartorderdetailsfororderidwithbatchno";
    public static final  String getCartOrderDetailsForBatchno  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/b2bcartorderdetailsforbatchno";
    public static final  String updateCartOrderDetails = "https://69wey38qj4.execute-api.ap-south-1.amazonaws.com/dev/b2bcartorderdetails";
    public static final  String deleteCartOrderDetails = "https://s5my3jk0y6.execute-api.ap-south-1.amazonaws.com/dev/b2bcartorderdetails";
    public static final  String getCartOrderDetailsForDeliveryCentrekey  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/b2bcartorderdetailsfordeliverycentrekey";




    //B2BGoatGradeDetails
    public static final  String addgoatGradeDetails = "https://w430rhwyyc.execute-api.ap-south-1.amazonaws.com/dev/b2bgoatgradedetails";
    public static final  String getgoatGradeList  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/list?modulename=GoatGradeModule";
    public static final  String updategoatGradeDetails = "https://69wey38qj4.execute-api.ap-south-1.amazonaws.com/dev/b2bgoatgradedetails";
    public static final  String deletegoatGradeDetails = "https://s5my3jk0y6.execute-api.ap-south-1.amazonaws.com/dev/b2bgoatgradedetails+?key=";
    public static final  String getgoatGradeForDeliveryCentreKey  = "https://49snsmggcf.execute-api.ap-south-1.amazonaws.com/dev/b2bgoatgradedetailsfordeliverycentrekey?deliverycentrekey=";


}
