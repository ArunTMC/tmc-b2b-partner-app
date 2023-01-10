package com.tmc.tmcb2bpartnerapp.interfaces;



public interface BarcodeScannerInterface {

    void notifySuccess( String Barcode);
    void notifySuccessAndFetchData( String Barcode);
    void notifyProcessingError(Exception error);


}
