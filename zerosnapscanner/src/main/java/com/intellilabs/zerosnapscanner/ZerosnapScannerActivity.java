package com.intellilabs.zerosnapscanner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.scansolutions.mrzscannerlib.MRZResultModel;
import com.scansolutions.mrzscannerlib.MRZScanner;
import com.scansolutions.mrzscannerlib.MRZScannerListener;
import com.scansolutions.mrzscannerlib.ScannerType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.intellilabs.zerosnapscanner.ZerosnapScannerUtils.DOCUMENT_DATE_FORMAT;
import static com.intellilabs.zerosnapscanner.ZerosnapScannerUtils.EXTRA_DOCUMENT_TYPE;
import static com.intellilabs.zerosnapscanner.ZerosnapScannerUtils.EXTRA_LICENCE_KEY;
import static com.intellilabs.zerosnapscanner.ZerosnapScannerUtils.MRZ_PASSPORT;
import static com.intellilabs.zerosnapscanner.ZerosnapScannerUtils.MRZ_VISA;

public class ZerosnapScannerActivity extends AppCompatActivity implements MRZScannerListener {

    private final String TAG = ZerosnapScannerActivity.class.getName();

    private MRZScanner mrzScanner;
    private String documentType;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DOCUMENT_DATE_FORMAT);
    private static ZerosnapScannerCallback mZerosnapScannerCallback;
    private String licence;

    public static Intent newIntent(Context context, ZerosnapScannerCallback zerosnapScannerCallback){
        mZerosnapScannerCallback = zerosnapScannerCallback;
        return new Intent(context,ZerosnapScannerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zerosnap_scanner);

        documentType = getIntent().getStringExtra(EXTRA_DOCUMENT_TYPE);

        licence = getIntent().getStringExtra(EXTRA_LICENCE_KEY);

        ZerosnapScannerType zerosnapScannerType = ZerosnapScannerType.valueOf(documentType);
        mrzScanner = (MRZScanner) getSupportFragmentManager().findFragmentById(R.id.scannerFragment);
        if (zerosnapScannerType.equals(ZerosnapScannerType.DOCUMENT_IMAGE)){
            mrzScanner.setScannerType(ScannerType.SCANNER_TYPE_DOC_IMAGE_ID); // Options: [SCANNER_TYPE_MRZ, SCANNER_TYPE_DOC_IMAGE_ID, SCANNER_TYPE_DOC_IMAGE_PASSPORT]
        }else {
            mrzScanner.setScannerType(ScannerType.SCANNER_TYPE_MRZ); // Options: [SCANNER_TYPE_MRZ, SCANNER_TYPE_DOC_IMAGE_ID, SCANNER_TYPE_DOC_IMAGE_PASSPORT]
        }

        MRZScanner.setIDActive(true);          // Enable/disable the ID document type

        MRZScanner.setPassportActive(true);    // Enable/disable the Passport document type

        MRZScanner.setVisaActive(true);        // Enable/disable the Visa document type

        MRZScanner.setMaxThreads(2);           // Set the max CPU threads that the scanner can use

        MRZScanner.registerWithLicenseKey(this,licence);
//        licence = "F1F1889B599CCF807EE9673EEBE908A5D4BA634C7808EB861B899C4F28EFFA1FB554045EF21D2B93F18BFFAD5ECBCADA2168BE8020FE242C623A9D6E06BF2F15";
    }

    @Override
    public void successfulScanWithResult(MRZResultModel mrzResultModel) {
        ZerosnapScannerType zerosnapScannerType = ZerosnapScannerType.valueOf(documentType);
        switch (zerosnapScannerType) {
            case PASSPORT:
                if (mrzResultModel.document_type_readable.equalsIgnoreCase(MRZ_PASSPORT)) {
                    PassportModel  passportModel = getPassportData(mrzResultModel);
                    mZerosnapScannerCallback.onPassportScanSuccess(passportModel);
                    finish();
                } else {
                    mZerosnapScannerCallback.onPassportScanFailed();
                    finish();
                }
                break;
            case VISA:
                if (mrzResultModel.document_type_readable.equalsIgnoreCase(MRZ_VISA)) {
                    VisaModel visaModel = getVisaData(mrzResultModel);
                    mZerosnapScannerCallback.onVisaScanSuccess(visaModel);
                    finish();
                } else {
                    mZerosnapScannerCallback.onPassportScanFailed();
                    finish();
                }
                break;
        }

    }

    private PassportModel getPassportData(MRZResultModel mrzResultModel) {
        PassportModel passportModel = new PassportModel();

        Bitmap bitmap = mrzResultModel.portrait;
        passportModel.setImage(bitmap);
        passportModel.setDocumentType(mrzResultModel.document_type_readable);
        Date dobDate = null;
        try {
            dobDate = simpleDateFormat.parse(mrzResultModel.dob_readable);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        passportModel.setDob(dobDate);
        Date expiry = null;
        try {
            expiry = simpleDateFormat.parse(mrzResultModel.expiration_date_readable);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        passportModel.setExpiry(expiry);
        passportModel.setIssuingCountry(mrzResultModel.issuing_country);

        //First Name
        if (mrzResultModel.given_names!=null) {
            if (mrzResultModel.given_names.length != 0)
                passportModel.setFirstName(mrzResultModel.given_names[0]);
            else
                passportModel.setFirstName("");
        }else {
            passportModel.setFirstName("");
        }

        //Surname
        if (mrzResultModel.surnames!=null) {
            if (mrzResultModel.surnames.length != 0)
                passportModel.setLastName(mrzResultModel.surnames[0]);
            else
                passportModel.setLastName("");
        }else {
            passportModel.setLastName("");
        }
        passportModel.setGender(mrzResultModel.sex);
        passportModel.setNationality(mrzResultModel.nationality);
        passportModel.setPassportNumber(mrzResultModel.document_number);

        return passportModel;
    }

    @Override
    public void successfulScanWithDocumentImage(Bitmap bitmap) {
        mZerosnapScannerCallback.onDocumentImageCaptured(bitmap);
        finish();
    }

    @Override
    public void scanImageFailed() {
        ZerosnapScannerType zerosnapScannerType = ZerosnapScannerType.valueOf(documentType);
        switch (zerosnapScannerType){
            case PASSPORT:
                mZerosnapScannerCallback.onPassportScanFailed();
                break;
            case VISA:
                mZerosnapScannerCallback.onVisaScanFailed();
                break;
             default:
                 break;
        }

    }

    @Override
    public void permissionsWereDenied() {
        ZerosnapScannerType zerosnapScannerType = ZerosnapScannerType.valueOf(documentType);
        switch (zerosnapScannerType){
            case PASSPORT:
                mZerosnapScannerCallback.onPassportScanFailed();
                break;
            case VISA:
                mZerosnapScannerCallback.onVisaScanFailed();
                break;
            default:
                break;
        }
    }

    public VisaModel getVisaData(MRZResultModel mrzResultModel) {
        VisaModel visaModel = new VisaModel();
        visaModel.setDocumentType(mrzResultModel.document_type_readable);
        Date expiry = null;
        try {
            expiry = simpleDateFormat.parse(mrzResultModel.expiration_date_readable);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        visaModel.setExpiry(expiry);
        //First Name
        if (mrzResultModel.given_names!=null) {
            if (mrzResultModel.given_names.length != 0)
                visaModel.setFirstName(mrzResultModel.given_names[0]);
            else
                visaModel.setFirstName("");
        }else {
            visaModel.setFirstName("");
        }

        //Surname
        if (mrzResultModel.surnames!=null) {
            if (mrzResultModel.surnames.length != 0)
                visaModel.setLastName(mrzResultModel.surnames[0]);
            else
                visaModel.setLastName("");
        }else {
            visaModel.setLastName("");
        }
        visaModel.setVisaNumber(mrzResultModel.document_number);
        visaModel.setIssuingCountry(mrzResultModel.issuing_country);
        Date dobDate = null;
        try {
            dobDate = simpleDateFormat.parse(mrzResultModel.dob_readable);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        visaModel.setDob(dobDate);
        return visaModel;
    }
}
