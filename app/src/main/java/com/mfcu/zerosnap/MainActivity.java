package com.mfcu.zerosnap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.intellilabs.zerosnapscanner.PassportModel;
import com.intellilabs.zerosnapscanner.VisaModel;
import com.intellilabs.zerosnapscanner.ZerosnapScanner;
import com.intellilabs.zerosnapscanner.ZerosnapScannerCallback;
import com.intellilabs.zerosnapscanner.ZerosnapScannerType;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private String userId = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView mContentTextView = findViewById(R.id.tv_content);
        final ImageView mProfileImageView = findViewById(R.id.iv_image);

        /*
        * Initializing Zerosnap Scanner with Document type and callback
        * */
        ZerosnapScanner.init(this, userId,new ZerosnapScannerCallback() {
            @Override
            public void onPassportScanSuccess(PassportModel passportModel) {
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
        });
        ZerosnapScanner.scan(ZerosnapScannerType.PASSPORT);
    }
}
