package com.example.vehidocs.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.vehidocs.R;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class LicensePageActivity extends AppCompatActivity {

    public static final String mobileNoParameter = "mobileNo";

    public static final String fullNameParameter = "fullName";
    public static final String addressParameter = "address";
    public static final String licenseNoParameter = "licenseNo";
    public static final String bloodGroupParameter = "bloodGroup";
    public static final String dobParameter = "dob";
    public static final String doiParameter = "doi";
    public static final String doeParameter = "doe";
    public static final String fatherNameParameter = "fatherName";
    public static final String licenseTypeParameter = "licenseType";
    public static final String citizenshipNoParameter = "citizenshipNo";
    public static final String imagesParameter = "images";
    FirebaseDatabase rootNode;
    TextView category, bloodGroup, name, licenseNo, address, citizenshipNo, mobileNumber, dob, doi, doe, fatherName;
    ImageView images;
    Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_license_page);
        //Hooks
        images = findViewById(R.id.imageUser);
        category = findViewById(R.id.txtCategory);
        bloodGroup = findViewById(R.id.txtBloodGroup);
        licenseNo = findViewById(R.id.txtLicense);
        name = findViewById(R.id.txtName);
        citizenshipNo = findViewById(R.id.txtCitizenshipNo);
        fatherName = findViewById(R.id.txtFatherName);
        dob = findViewById(R.id.txtDOB);
        doi = findViewById(R.id.txtDOI);
        address = findViewById(R.id.txtAddress);
        doe = findViewById(R.id.txtDOE);
        mobileNumber = findViewById(R.id.txtMobileNo);
        shareButton = findViewById(R.id.shareBtn);

        Intent i = getIntent();

        String fullName = i.getStringExtra(fullNameParameter);
        String licenseType = i.getStringExtra(licenseTypeParameter);
        String bloodgroup = i.getStringExtra(bloodGroupParameter);
        String licenseno = i.getStringExtra(licenseNoParameter);
        String citizenshipno = i.getStringExtra(citizenshipNoParameter);
        String fathername = i.getStringExtra(fatherNameParameter);
        String DOB = i.getStringExtra(dobParameter);
        String DOI = i.getStringExtra(doiParameter);
        String DOE = i.getStringExtra(doeParameter);
        String mobileno = i.getStringExtra(mobileNoParameter);
        String userAddress = i.getStringExtra(addressParameter);
        String link = i.getStringExtra(imagesParameter);


        name.setText(fullName);
        address.setText(userAddress);
        mobileNumber.setText(mobileno);
        licenseNo.setText(licenseno);
        bloodGroup.setText(bloodgroup);
        dob.setText(DOB);
        doe.setText(DOE);
        doi.setText(DOI);
        fatherName.setText(fathername);
        category.setText(licenseType);
        citizenshipNo.setText(citizenshipno);

        Glide.with(LicensePageActivity.this)
                .load(link)
                .apply(new RequestOptions().override(1500, 1500))
                .centerCrop()
                .into(images);


        shareButton.setOnClickListener(view -> {
            Intent in = getIntent();
            String fullName1 = in.getStringExtra(fullNameParameter);
            String mobileNumber = in.getStringExtra(mobileNoParameter);
            String LicenseNumber = in.getStringExtra(licenseNoParameter);

            AlertDialog.Builder alert = new AlertDialog.Builder(LicensePageActivity.this);
            alert.setTitle("QR CODE");
            MultiFormatWriter mWriter = new MultiFormatWriter();
            try {
                //BitMatrix class to encode entered text and set Width & Height
                BitMatrix mMatrix = mWriter.encode(mobileNumber + "by" + LicenseNumber, BarcodeFormat.QR_CODE, 600, 600);
                BarcodeEncoder mEncoder = new BarcodeEncoder();
                Bitmap mBitmap = mEncoder.createBitmap(mMatrix);//creating bitmap of code
                ImageView imageCode = new ImageView(LicensePageActivity.this);
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
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back Pressed", Toast.LENGTH_SHORT).show();
        Intent i = getIntent();
        String fullName = i.getStringExtra(fullNameParameter);
        String mobileNumber = i.getStringExtra(mobileNoParameter);
        AlertDialog.Builder alert = new AlertDialog.Builder(LicensePageActivity.this);
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


