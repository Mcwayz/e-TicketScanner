package de.markusfisch.android.barcodescannerviewdemo.activity.model;

import java.util.List;

public class ScannerResponse {
    private List<ScannerRequest> scanner;
    public List<ScannerRequest> getScannerResult() {
        return scanner;
    }
    public void setResult(List<ScannerRequest> scanner) {
        this.scanner = scanner;
    }
}