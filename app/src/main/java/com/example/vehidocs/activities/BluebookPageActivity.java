package com.example.vehidocs.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.vehidocs.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class BluebookPageActivity extends AppCompatActivity {

    public static final String mobileNoParameter = "mobileNumber";
    public static final String fullNameParameter = "fullName";
    public static final String vehicleNoParameter = "vehicleNo";
    public static final String vehicleTypeParameter = "vehicleType";
    public static final String ownerImageParameter = "ownerImage";
    public static final String vehicleImageParameter = "vehicleImage";
    TextView vehicleNo, vehicleTypes, ownerText, vehicleText;
    ImageView Image1, Image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluebook_page);

        Button back_button = findViewById(R.id.back_Btn);
        back_button.setOnClickListener(view -> {
            Intent in = getIntent();
            String fullName = in.getStringExtra(fullNameParameter);
            String mobileNumber = in.getStringExtra(mobileNoParameter);
            Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
            intent.putExtra(MainPageActivity.fullNameParameter, fullName);
            intent.putExtra(MainPageActivity.mobileNoParameter, mobileNumber);
            startActivity(intent);
            finish();
        });

        Intent i = getIntent();
        String ownerImage = i.getStringExtra(ownerImageParameter);
        String vehicleImage = i.getStringExtra(vehicleImageParameter);
        String vehicleNumber = i.getStringExtra(vehicleNoParameter);
        String vehicleType = i.getStringExtra(vehicleTypeParameter);


        Button shareButton = findViewById(R.id.shareButton);
        Image1 = findViewById(R.id.ownerImage);
        Image2 = findViewById(R.id.vehicleImage);
        vehicleNo = findViewById(R.id.vehicleNo);
        vehicleTypes = findViewById(R.id.vehicleType);

        Glide.with(BluebookPageActivity.this)
                .load(ownerImage)
                .apply(new RequestOptions().override(1500, 2000))
                .centerCrop()
                .into(Image1);


        Glide.with(BluebookPageActivity.this)
                .load(vehicleImage)
                .apply(new RequestOptions().override(1500, 2000))
                .centerCrop()
                .into(Image2);

        vehicleNo.setText(vehicleNumber);
        vehicleTypes.setText(vehicleType);


        shareButton.setOnClickListener(view -> {
            Intent in = getIntent();
            String mobileNumber = in.getStringExtra(mobileNoParameter);
            String VehicleNumber = in.getStringExtra(vehicleNoParameter);

            AlertDialog.Builder alert = new AlertDialog.Builder(BluebookPageActivity.this);
            alert.setTitle("QR CODE");
            MultiFormatWriter mWriter = new MultiFormatWriter();
            try {
                BitMatrix mMatrix = mWriter.encode(mobileNumber + "by" + VehicleNumber, BarcodeFormat.QR_CODE, 600, 600);
                BarcodeEncoder mEncoder = new BarcodeEncoder();
                Bitmap mBitmap = mEncoder.createBitmap(mMatrix);//creating bitmap of code
                ImageView imageCode = new ImageView(BluebookPageActivity.this);
                imageCode.setImageBitmap(mBitmap);
                alert.setView(imageCode);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();
        });

        ImageButton ownerImage1 = findViewById(R.id.ownerImage);
        ownerImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), imageOneActivity.class);
                i.putExtra(imageOneActivity.ownerImageParameter, ownerImage);
                startActivity(i);
            }
        });

        ImageButton detailImage = findViewById(R.id.vehicleImage);
        detailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), imageTwoActivity.class);
                i.putExtra(imageTwoActivity.detailImageParameter, vehicleImage);
                startActivity(i);
            }
        });
    }

    public void onBackPressed() {
        Toast.makeText(this, "Back Pressed", Toast.LENGTH_SHORT).show();
        Intent i = getIntent();
        String fullName = i.getStringExtra(fullNameParameter);
        String mobileNumber = i.getStringExtra(mobileNoParameter);
        AlertDialog.Builder alert = new AlertDialog.Builder(BluebookPageActivity.this);
        alert.setTitle("Digital Vehicle");
        alert.setMessage("Are you sure you want to go back?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
                intent.putExtra(MainPageActivity.fullNameParameter, fullName);
                intent.putExtra(MainPageActivity.mobileNoParameter, mobileNumber);
                startActivity(intent);
                finish();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
}