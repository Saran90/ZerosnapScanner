package com.intellilabs.zerosnapscanner;

import android.graphics.Bitmap;

/**
 * Created by Saran M S on 11/20/2019.
 */
public interface ZerosnapScannerCallback {
    void onPassportScanSuccess(PassportModel passportModel);
    void onPassportScanFailed();
    void onVisaScanSuccess(VisaModel visaModel);
    void onVisaScanFailed();
    void onDocumentImageCaptured(Bitmap bitmap);
}
