package com.example.vehidocs.activities;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vehidocs.R;
import com.example.vehidocs.features.models.ImageUploadInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class AddBluebookImageActivity<Url> extends AppCompatActivity {

    public static final String mobileNoParameter = "mobileNo";
    public static final String fullNameParameter = "fullName";
    public static final String vehicleNoParameter = "vehicleNo";
    public static final String zoneParameter = "zone";
    public static final String vehicleTypeParameter = "vehicleType";
    public static final String symbolParameter = "symbol";
    public static final String vehicleLotNoParameter = "vehicleLotNo";
    Button btnbrowseOne, btnbrowseTwo, btnupload;
    ImageView imgviewOne, imgviewTwo;
    Uri FilePathUrl, FilePathUri1;
    ProgressDialog progressDialog;
    private FirebaseDatabase rootNode;
    DatabaseReference dbReference;
    StorageReference stoRef;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bluebook_image);

        // Assign FirebaseStorage instance to storageReference.
        stoRef = FirebaseStorage.getInstance().getReference("Bluebook_Images/");
        // Assign FirebaseDatabase instance with root database name.
        dbReference = FirebaseDatabase.getInstance().getReference("bluebooks");

        imgviewOne = findViewById(R.id.imgVehicle);
        imgviewTwo = findViewById(R.id.imgVehicle1);
        btnupload = findViewById(R.id.btnRequestBluebook);
        btnbrowseOne = findViewById(R.id.btnBrowse);
        btnbrowseTwo = findViewById(R.id.btnBrowse1);

        // Assigning Id to ProgressDialog.
        progressDialog = new ProgressDialog(getApplicationContext());
        stoRef = FirebaseStorage.getInstance().getReference("bluebooks_Image");
        dbReference = FirebaseDatabase.getInstance().getReference("bluebooks");


        btnbrowseOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "Please select Image"), 1);
            }
        });

        btnbrowseTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "Please select Image"), 2);
            }
        });

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadToFirebase1();
                UploadToFirebase();
            }
        });
    }


    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FilePathUrl = data.getData();
            try {
                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUrl);
                // Setting up bitmap selected image into ImageView.
                imgviewOne.setImageBitmap(bitmap);
                // After selecting image change choose button above text.
                btnbrowseOne.setText("Uploaded");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FilePathUri1 = data.getData();
            try {
                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri1);
                // Setting up bitmap selected image into ImageView.
                imgviewTwo.setImageBitmap(bitmap);
                // After selecting image change choose button above text.
                btnbrowseTwo.setText("Uploaded");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    private void UploadToFirebase1() {
        String mobileNumber = getIntent().getStringExtra(mobileNoParameter);
        String fullName = getIntent().getStringExtra(fullNameParameter);
        String vehicleNo = getIntent().getStringExtra(vehicleNoParameter);
        String zone = getIntent().getStringExtra(zoneParameter);
        String vehicleType = getIntent().getStringExtra(vehicleTypeParameter);
        String symbol = getIntent().getStringExtra(symbolParameter);
        String vehicleLotNo = getIntent().getStringExtra(vehicleLotNoParameter);
        String LendTo = " ";
        String LendToMobileNo = " ";
        String LendToLicenseNumber = " ";
        String LendStatus = "";
        String TheftStatus = "";
        String imageUrl = "";
        String TaxStatus = "";
        String TaxRenewedDate = "";
        String TaxExpireDate = "";
        String RequestStatus = "Pending";
        String fullVehicleNo = zone + " " + vehicleLotNo + " " + symbol + " " + vehicleNo;
        if (fullVehicleNo == null) {
            Intent in = new Intent(getApplicationContext(), RequestBluebookActivity.class);
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            startActivity(in);
        } else if (FilePathUri1 != null) {
            progressDialog.setTitle("Image is uploading...");
            //progressDialog.show();
            StorageReference storageReference4th = stoRef.child(UUID.randomUUID().toString() + "." + GetFileExtension(FilePathUrl));
            // Adding addOnSuccessListener to second StorageReference.
            storageReference4th.putFile(FilePathUri1)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    progressDialog.dismiss();
                                    ImageUploadInfo imageUploadInfo = new ImageUploadInfo(fullVehicleNo, mobileNumber, vehicleType, imageUrl, uri.toString(), LendTo, LendToMobileNo, LendToLicenseNumber, LendStatus, TheftStatus, TaxStatus, TaxExpireDate, TaxRenewedDate, RequestStatus);
                                    dbReference.child(mobileNumber).setValue(imageUploadInfo);
                                    Toast.makeText(getApplicationContext(), "Requested Successfully ", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getApplicationContext(), MainPageActivity.class);
                                    i.putExtra(MainPageActivity.fullNameParameter, fullName);
                                    i.putExtra(MainPageActivity.mobileNoParameter, mobileNumber);
                                    startActivity(i);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            progressDialog.setTitle("Image is Uploading...");

                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();
        }
    }


    private void UploadToFirebase() {
        if (FilePathUrl != null) {
            String mobileNumber = getIntent().getStringExtra(mobileNoParameter);
            progressDialog.setTitle("Image is uploading...");
            //progressDialog.show();

            // Adding addOnSuccessListener to second StorageReference.
            StorageReference storageReference3rd = stoRef.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUrl));
            storageReference3rd.putFile(FilePathUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    dbReference.child(mobileNumber).child("imageURL").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(AddBluebookImageActivity.this, "Uploaded", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });
                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Hiding the progressDialog.
                            progressDialog.dismiss();
                            // Showing exception erro message.
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            // Setting progressDialog Title.
                            progressDialog.setTitle("Image is Uploading...");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back Pressed", Toast.LENGTH_SHORT).show();
        Intent i = getIntent();
        String fullName = i.getStringExtra(fullNameParameter);
        String mobileNumber = i.getStringExtra(mobileNoParameter);
        AlertDialog.Builder alert = new AlertDialog.Builder(AddBluebookImageActivity.this);
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




