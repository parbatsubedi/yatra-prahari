package com.example.vehidocs.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vehidocs.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class generateCodeActivity extends AppCompatActivity {

    public static final String fullNameParameter = "fullName";
    public static final String mobileNoParameter = "mobileNumber";
    public static final String licenseNoParameter = "licenseNumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);

        //ImageView for generated QR code
        ImageView imageCode = findViewById(R.id.imageCode);
        Intent i = getIntent();
        String mobileNo = i.getStringExtra(mobileNoParameter);
        String licenseNo = i.getStringExtra(licenseNoParameter);
        MultiFormatWriter mWriter = new MultiFormatWriter();
        try {
            //BitMatrix class to encode entered text and set Width & Height
            BitMatrix mMatrix = mWriter.encode(mobileNo + "by" + licenseNo, BarcodeFormat.QR_CODE, 600, 600);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(mMatrix);//creating bitmap of code
            imageCode.setImageBitmap(mBitmap);//Setting generated QR code to imageView
//            // to hide the keyboard
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}