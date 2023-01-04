package de.markusfisch.android.barcodescannerviewdemo.activity.model;

import java.util.List;

public class ScannerRequest {
    //sending
    private  String barcode;
    private String status_code;

    public ScannerRequest() {

    }

    public ScannerRequest(String barcode, String status_code) {
        this.barcode = barcode;
        this.status_code = status_code;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    @Override
    public String toString() {
        return "ScannerRequest{" +
                "barcode='" + barcode + '\'' +
                ", status_code='" + status_code + '\'' +
                '}';
    }
}
