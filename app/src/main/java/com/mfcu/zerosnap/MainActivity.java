package com.mfcu.zerosnap;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.intellilabs.zerosnapscanner.PassportModel;
import com.intellilabs.zerosnapscanner.VisaModel;
import com.intellilabs.zerosnapscanner.ZerosnapScanner;
import com.intellilabs.zerosnapscanner.ZerosnapScannerCallback;
import com.intellilabs.zerosnapscanner.ZerosnapScannerType;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        ZerosnapScannerCallback{

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private String userId = "1";
    private Button mScanImageButton,mScanPassportButton,mScanVisaButton;
    private TextView mContentTextView;
    private ImageView mProfileImageView;
    private EditText mUserIdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContentTextView = findViewById(R.id.tv_content);
        mProfileImageView = findViewById(R.id.iv_image);
        mScanImageButton = findViewById(R.id.btn_scan_image);
        mScanPassportButton = findViewById(R.id.btn_scan_passport);
        mScanVisaButton = findViewById(R.id.btn_scan_visa);
        mUserIdEditText = findViewById(R.id.et_userid);

        mScanPassportButton.setOnClickListener(this);
        mScanVisaButton.setOnClickListener(this);
        mScanImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (TextUtils.isEmpty(mUserIdEditText.getText())){
            Toast.makeText(this, "Please enter a userid", Toast.LENGTH_SHORT).show();
        }else {
            userId = mUserIdEditText.getText().toString();
            /*
             * Initializing Zerosnap Scanner with Document type and callback
             * */
            ZerosnapScanner.init(this, userId,this);
            if (view.getId()==R.id.btn_scan_passport){
                ZerosnapScanner.scan(ZerosnapScannerType.PASSPORT);
            }else if (view.getId()==R.id.btn_scan_visa){
                ZerosnapScanner.scan(ZerosnapScannerType.VISA);
            }else {
                ZerosnapScanner.scan(ZerosnapScannerType.DOCUMENT_IMAGE);
            }
        }
    }

    @Override
    public void onPassportScanSuccess(PassportModel passportModel) {
        mContentTextView.setVisibility(View.VISIBLE);
        String content = "First Name: "+passportModel.getFirstName();
        content = content + "\n" + "Last Name: "+passportModel.getLastName();
        content = content + "\n" + "Document Type: "+passportModel.getDocumentType();
        content = content + "\n" + "Gender: "+passportModel.getGender();
        content = content + "\n" + "Issuing Country: "+passportModel.getIssuingCountry();
        content = content + "\n" + "Nationality: "+passportModel.getNationality();
        content = content + "\n" + "Passport Number: "+passportModel.getPassportNumber();
        if (passportModel.getDob()!=null)
            content = content + "\n" + "DOB: "+simpleDateFormat.format(passportModel.getDob());
        else
            content = content + "\n" + "DOB: "+ "";
        if (passportModel.getExpiry()!=null)
            content = content + "\n" + "Expiry: "+simpleDateFormat.format(passportModel.getExpiry());
        else
            content = content + "\n" + "Expiry: "+"";
        mContentTextView.setText(content);
        if (passportModel.getImage()!=null)
            mProfileImageView.setImageBitmap(passportModel.getImage());
    }

    @Override
    public void onPassportScanFailed() {
        Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVisaScanSuccess(VisaModel visaModel) {
        mContentTextView.setVisibility(View.VISIBLE);
        String content = "First Name: "+visaModel.getFirstName();
        content = content + "\n" + "Last Name: "+visaModel.getLastName();
        content = content + "\n" + "Document Type: "+visaModel.getDocumentType();
        content = content + "\n" + "Issuing Country: "+visaModel.getIssuingCountry();
        content = content + "\n" + "Passport Number: "+visaModel.getVisaNumber();
        if (visaModel.getDob()!=null)
            content = content + "\n" + "DOB: "+simpleDateFormat.format(visaModel.getDob());
        else
            content = content + "\n" + "DOB: "+ "";
        if (visaModel.getExpiry()!=null)
            content = content + "\n" + "Expiry: "+simpleDateFormat.format(visaModel.getExpiry());
        else
            content = content + "\n" + "Expiry: "+"";
        mContentTextView.setText(content);
    }

    @Override
    public void onVisaScanFailed() {
        Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDocumentImageCaptured(Bitmap bitmap) {
        mProfileImageView.setImageBitmap(bitmap);
        mContentTextView.setVisibility(View.GONE);
    }
}
