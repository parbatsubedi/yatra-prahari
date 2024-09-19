package com.example.vehidocs.activities;


import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.vehidocs.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MainPageActivity extends AppCompatActivity {

    TextView userName, fullName;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    public static final String mobileNoParameter = "mobileNo";

    public static final String fullNameParameter = "fullName";

    public static final String imagesParameter = "images";

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        userName = findViewById(R.id.userName);
        ImageView images = findViewById(R.id.image);
        Intent i = getIntent();
        String fullName = i.getStringExtra(fullNameParameter);
        userName.setText("Hello" + "\n" + fullName);

        Log.v(TAG, "Fullname" + fullName);


        String mobileNumberForImg = getIntent().getStringExtra(mobileNoParameter);
        //Toast.makeText(getApplicationContext(), mobileNumber, Toast.LENGTH_SHORT).show();
        rootNode = FirebaseDatabase.getInstance("https://digitalvehicle-66b90-default-rtdb.firebaseio.com/");
        DatabaseReference referenceForImage = rootNode.getReference("Licenses");
        Query checkUserForImg = referenceForImage.orderByChild("mobileNo").equalTo(mobileNumberForImg);
        checkUserForImg.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String imageFromDB = snapshot.child(mobileNumberForImg).child("ProfileImage").getValue(String.class);

                    Log.v(TAG,"Img" + imageFromDB);

                    Glide.with(MainPageActivity.this)
                        .load(imageFromDB)
                        .apply(new RequestOptions().override(180, 180))
                        .centerCrop()
                        .into(images);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ImageView btnLicense = findViewById(R.id.btnLicense);
        ImageView btnBluebook = findViewById(R.id.btnBluebook);
        ImageView btnVehicleTax = findViewById(R.id.btnVehicleTax);
        ImageView btnLend = findViewById(R.id.btnLend);
        ImageView btnRequestBluebook = findViewById(R.id.btnRequestBluebook);
        ImageView btnSupport = findViewById(R.id.btnSupport);
        ImageView imageViewLicense = findViewById(R.id.imageViewLicense);
        ImageView imageViewBluebook = findViewById(R.id.imageViewBluebook);

        btnLicense.setOnClickListener(v -> {
            String mobileNumber = getIntent().getStringExtra(mobileNoParameter);
            //Toast.makeText(getApplicationContext(), mobileNumber, Toast.LENGTH_SHORT).show();
            rootNode = FirebaseDatabase.getInstance("https://digitalvehicle-66b90-default-rtdb.firebaseio.com/");
            DatabaseReference reference = rootNode.getReference("Licenses");
            Query checkUser = reference.orderByChild("mobileNo").equalTo(mobileNumber);
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String fullNameFromDB = snapshot.child(mobileNumber).child("name").getValue(String.class);
                        String addressFromDB = snapshot.child(mobileNumber).child("address").getValue(String.class);
                        String mobileNoFromDB = snapshot.child(mobileNumber).child("mobileNo").getValue(String.class);
                        String licenseNoFromDB = snapshot.child(mobileNumber).child("licenseNo").getValue(String.class);
                        String bloodGroupFromDB = snapshot.child(mobileNumber).child("bloodGroup").getValue(String.class);
                        String dobFromDB = snapshot.child(mobileNumber).child("dob").getValue(String.class);
                        String doeFromDB = snapshot.child(mobileNumber).child("doe").getValue(String.class);
                        String doiFromDB = snapshot.child(mobileNumber).child("doi").getValue(String.class);
                        String fatherNameFromDB = snapshot.child(mobileNumber).child("fatherName").getValue(String.class);
                        String licenseTypeFromDB = snapshot.child(mobileNumber).child("licenseType").getValue(String.class);
                        String citizenshipNoFromDB = snapshot.child(mobileNumber).child("citizenshipNo").getValue(String.class);
                        String imageFromDB = snapshot.child(mobileNumber).child("ProfileImage").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), LicensePageActivity.class);

                        intent.putExtra(LicensePageActivity.mobileNoParameter, mobileNoFromDB);
                        intent.putExtra(LicensePageActivity.fullNameParameter, fullNameFromDB);
                        intent.putExtra(LicensePageActivity.addressParameter, addressFromDB);
                        intent.putExtra(LicensePageActivity.licenseNoParameter, licenseNoFromDB);
                        intent.putExtra(LicensePageActivity.bloodGroupParameter, bloodGroupFromDB);
                        intent.putExtra(LicensePageActivity.dobParameter, dobFromDB);
                        intent.putExtra(LicensePageActivity.doiParameter, doiFromDB);
                        intent.putExtra(LicensePageActivity.doeParameter, doeFromDB);
                        intent.putExtra(LicensePageActivity.fatherNameParameter, fatherNameFromDB);
                        intent.putExtra(LicensePageActivity.licenseTypeParameter, licenseTypeFromDB);
                        intent.putExtra(LicensePageActivity.citizenshipNoParameter, citizenshipNoFromDB);
                        intent.putExtra(LicensePageActivity.imagesParameter, imageFromDB);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "No such User", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "No file found", Toast.LENGTH_SHORT).show();
                }
            });
        });
        btnBluebook.setOnClickListener(v -> {
            String mobileNo = getIntent().getStringExtra(mobileNoParameter);
            String fullName1 = getIntent().getStringExtra(fullNameParameter);
            rootNode = FirebaseDatabase.getInstance("https://digitalvehicle-66b90-default-rtdb.firebaseio.com");
            DatabaseReference reference = rootNode.getReference("bluebooks");
            Query checkUser = reference.orderByChild("mobileNumber").equalTo(mobileNo);
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String requestStatusFromDB = snapshot.child(mobileNo).child("requestStatus").getValue(String.class);
                        if (requestStatusFromDB.equals("Pending")) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(MainPageActivity.this);
                            alert.setTitle("Bluebook Detail");
                            alert.setMessage("Your Bluebook Request is pending. Please wait until we finish checking your details.");
                            alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alert.show();
                        } else {
                            String vehicleTypeFromDB = snapshot.child(mobileNo).child("vehicleType").getValue(String.class);
                            String vehicleNoFromDB = snapshot.child(mobileNo).child("vehicleNo").getValue(String.class);
                            String mobileNoFromDB = snapshot.child(mobileNo).child("mobileNumber").getValue(String.class);
                            String ownerImageFromDB = snapshot.child(mobileNo).child("imageURL").getValue(String.class);
                            String vehicleImageFromDB = snapshot.child(mobileNo).child("imageURL1").getValue(String.class);
                            String fullName12 = getIntent().getStringExtra(fullNameParameter);
                            Intent intent = new Intent(getApplicationContext(), BluebookPageActivity.class);

                            intent.putExtra(BluebookPageActivity.mobileNoParameter, mobileNoFromDB);
                            intent.putExtra(BluebookPageActivity.fullNameParameter, fullName12);
                            intent.putExtra(BluebookPageActivity.vehicleNoParameter, vehicleNoFromDB);
                            intent.putExtra(BluebookPageActivity.vehicleTypeParameter, vehicleTypeFromDB);
                            intent.putExtra(BluebookPageActivity.ownerImageParameter, ownerImageFromDB);
                            intent.putExtra(BluebookPageActivity.vehicleImageParameter, vehicleImageFromDB);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Bluebook not Added!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
        btnVehicleTax.setOnClickListener(v -> {
            String mobileNo = getIntent().getStringExtra(mobileNoParameter);
            String fullName100 = getIntent().getStringExtra(fullNameParameter);
            FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://digitalvehicle-66b90-default-rtdb.firebaseio.com/");
            DatabaseReference reference = rootNode.getReference("bluebooks");
            Query checkUser = reference.orderByChild("mobileNumber").equalTo(mobileNo);
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String taxStatusFromDB = snapshot.child(mobileNo).child("taxStatus").getValue(String.class);
                        String taxRenewDateToFromDB = snapshot.child(mobileNo).child("taxRenewedDate").getValue(String.class);
                        String taxExpireDateFromDB = snapshot.child(mobileNo).child("taxExpireDate").getValue(String.class);
                        //String taxStatus = "Clear";
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainPageActivity.this);
                        if (taxStatusFromDB.equals("Status")) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar c = Calendar.getInstance();
                            String TodayDate = sdf.format(c.getTime());
                            alert.setTitle("!Tax Details");
                            alert.setMessage("Vehicle Tax date has been expired..");
                            if (TodayDate.compareTo(taxExpireDateFromDB) < 0) {
                                alert.setMessage("Tax Status : Clear" + "\nTax Expiry Date : " + taxExpireDateFromDB + "\nTax Paid on : " + taxRenewDateToFromDB);
                            } else {
                                alert.setMessage("Tax Status : Expired" + "\nTax Expiry Date : " + taxExpireDateFromDB + "\nTax Paid on : " + taxRenewDateToFromDB);
                            }


                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                        } else {
                            alert.setTitle("Tax Detail");
                            alert.setMessage("Please wait until your bluebook get verified!");
                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                        }
                        alert.show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Bluebook not Added!Nothing to show.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
        btnLend.setOnClickListener(v -> {
            String mobileNo = getIntent().getStringExtra(mobileNoParameter);
            String fullName13 = getIntent().getStringExtra(fullNameParameter);
            rootNode = FirebaseDatabase.getInstance("https://digitalvehicle-66b90-default-rtdb.firebaseio.com/");
            DatabaseReference reference = rootNode.getReference("bluebooks");
            Query checkUser = reference.orderByChild("lendStatus").equalTo("Lended");
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        Intent intent = new Intent(getApplicationContext(), LendActivity.class);
                        intent.putExtra(RequestBluebookActivity.mobileNoParameter, mobileNo);
                        intent.putExtra(RequestBluebookActivity.fullNameParameter, fullName13);
                        startActivity(intent);
                        finish();
                    } else {
                        String lendStatus = snapshot.child(mobileNo).child("lendStatus").getValue(String.class);
                        String lendTo = snapshot.child(mobileNo).child("lendTo").getValue(String.class);
                        String lendToMobileNo = snapshot.child(mobileNo).child("lendToMobileNo").getValue(String.class);
                        String lendToLicenseNo = snapshot.child(mobileNo).child("lendToLicenseNumber").getValue(String.class);
//                        String fullName12 = getIntent().getStringExtra(fullNameParameter);
//                        Intent i = new Intent(getApplicationContext(), messageActivity.class);
//                        i.putExtra(RequestBluebookActivity.mobileNoParameter, mobileNo);
//                        i.putExtra(RequestBluebookActivity.fullNameParameter, fullName12);
//                        startActivity(i);
//                        finish();
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainPageActivity.this);
                        alert.setTitle("Lend Detail");
                        alert.setMessage("Lend Status : " + lendStatus + "\nLend To : " + lendTo + "\nLend To Mobile Number: " + lendToMobileNo + "\nLend To License Number: " + lendToLicenseNo);
                        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String userEnteredName = " ";
                                final String userEnteredMobileNo = " ";
                                String lendStatus = "Not Lended";
                                final String userEnteredLicenseNo = " ";
                                rootNode = FirebaseDatabase.getInstance("https://digitalvehicle-66b90-default-rtdb.firebaseio.com");
                                DatabaseReference reference = rootNode.getReference("bluebooks");
                                Query checkUser = reference.orderByChild("mobileNumber").equalTo(mobileNo);
                                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            reference.child(mobileNo).child("lendTo").setValue(userEnteredName);
                                            reference.child(mobileNo).child("lendStatus").setValue(lendStatus);
                                            reference.child(mobileNo).child("lendToMobileNo").setValue(userEnteredMobileNo);
                                            reference.child(mobileNo).child("lendToLicenseNumber").setValue(userEnteredLicenseNo);
                                            Toast.makeText(getApplicationContext(), "Your lend detail is Deleted.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Sorry! First Add Bluebook.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                dialog.dismiss();
                            }
                        });
                        alert.show();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
        btnRequestBluebook.setOnClickListener(v -> {
            String mobileNo = getIntent().getStringExtra(mobileNoParameter);
            String fullName13 = getIntent().getStringExtra(fullNameParameter);
            rootNode = FirebaseDatabase.getInstance("https://digitalvehicle-66b90-default-rtdb.firebaseio.com/");
            DatabaseReference reference = rootNode.getReference("bluebooks");
            Query checkUser = reference.orderByChild("mobileNumber").equalTo(mobileNo);
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        Intent intent = new Intent(getApplicationContext(), RequestBluebookActivity.class);
                        intent.putExtra(RequestBluebookActivity.mobileNoParameter, mobileNo);
                        intent.putExtra(RequestBluebookActivity.fullNameParameter, fullName13);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainPageActivity.this, "You have already requested your vehicle documents.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
        btnSupport.setOnClickListener(v -> {
            isTheft();
        });

        imageViewLicense.setOnClickListener(v -> {
            String mobileNumber = getIntent().getStringExtra(mobileNoParameter);
            //Toast.makeText(getApplicationContext(), mobileNumber, Toast.LENGTH_SHORT).show();
            rootNode = FirebaseDatabase.getInstance("https://digitalvehicle-66b90-default-rtdb.firebaseio.com/");
            DatabaseReference reference = rootNode.getReference("Licenses");
            Query checkUser = reference.orderByChild("mobileNo").equalTo(mobileNumber);
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String fullNameFromDB = snapshot.child(mobileNumber).child("name").getValue(String.class);
                        String addressFromDB = snapshot.child(mobileNumber).child("address").getValue(String.class);
                        String mobileNoFromDB = snapshot.child(mobileNumber).child("mobileNo").getValue(String.class);
                        String licenseNoFromDB = snapshot.child(mobileNumber).child("licenseNo").getValue(String.class);
                        String bloodGroupFromDB = snapshot.child(mobileNumber).child("bloodGroup").getValue(String.class);
                        String dobFromDB = snapshot.child(mobileNumber).child("dob").getValue(String.class);
                        String doeFromDB = snapshot.child(mobileNumber).child("doe").getValue(String.class);
                        String doiFromDB = snapshot.child(mobileNumber).child("doi").getValue(String.class);
                        String fatherNameFromDB = snapshot.child(mobileNumber).child("fatherName").getValue(String.class);
                        String licenseTypeFromDB = snapshot.child(mobileNumber).child("licenseType").getValue(String.class);
                        String citizenshipNoFromDB = snapshot.child(mobileNumber).child("citizenshipNo").getValue(String.class);
                        String imageFromDB = snapshot.child(mobileNumber).child("ProfileImage").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), LicensePageActivity.class);

                        intent.putExtra(LicensePageActivity.mobileNoParameter, mobileNoFromDB);
                        intent.putExtra(LicensePageActivity.fullNameParameter, fullNameFromDB);
                        intent.putExtra(LicensePageActivity.addressParameter, addressFromDB);
                        intent.putExtra(LicensePageActivity.licenseNoParameter, licenseNoFromDB);
                        intent.putExtra(LicensePageActivity.bloodGroupParameter, bloodGroupFromDB);
                        intent.putExtra(LicensePageActivity.dobParameter, dobFromDB);
                        intent.putExtra(LicensePageActivity.doiParameter, doiFromDB);
                        intent.putExtra(LicensePageActivity.doeParameter, doeFromDB);
                        intent.putExtra(LicensePageActivity.fatherNameParameter, fatherNameFromDB);
                        intent.putExtra(LicensePageActivity.licenseTypeParameter, licenseTypeFromDB);
                        intent.putExtra(LicensePageActivity.citizenshipNoParameter, citizenshipNoFromDB);
                        intent.putExtra(LicensePageActivity.imagesParameter, imageFromDB);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "No such User", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "No file found", Toast.LENGTH_SHORT).show();
                }
            });
        });
        imageViewBluebook.setOnClickListener(v -> {
            String mobileNo = getIntent().getStringExtra(mobileNoParameter);
            String fullName1 = getIntent().getStringExtra(fullNameParameter);
            rootNode = FirebaseDatabase.getInstance("https://digitalvehicle-66b90-default-rtdb.firebaseio.com");
            DatabaseReference reference = rootNode.getReference("bluebooks");
            Query checkUser = reference.orderByChild("mobileNumber").equalTo(mobileNo);
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String requestStatusFromDB = snapshot.child(mobileNo).child("requestStatus").getValue(String.class);
                        if (requestStatusFromDB.equals("Pending")) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(MainPageActivity.this);
                            alert.setTitle("Bluebook Detail");
                            alert.setMessage("Your Bluebook Request is pending. Please wait until we finish checking your details.");
                            alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alert.show();
                        } else {
                            String vehicleTypeFromDB = snapshot.child(mobileNo).child("vehicleType").getValue(String.class);
                            String vehicleNoFromDB = snapshot.child(mobileNo).child("vehicleNo").getValue(String.class);
                            String mobileNoFromDB = snapshot.child(mobileNo).child("mobileNumber").getValue(String.class);
                            String ownerImageFromDB = snapshot.child(mobileNo).child("imageURL").getValue(String.class);
                            String vehicleImageFromDB = snapshot.child(mobileNo).child("imageURL1").getValue(String.class);
                            String fullName12 = getIntent().getStringExtra(fullNameParameter);
                            Intent intent = new Intent(getApplicationContext(), BluebookPageActivity.class);

                            intent.putExtra(BluebookPageActivity.mobileNoParameter, mobileNoFromDB);
                            intent.putExtra(BluebookPageActivity.fullNameParameter, fullName12);
                            intent.putExtra(BluebookPageActivity.vehicleNoParameter, vehicleNoFromDB);
                            intent.putExtra(BluebookPageActivity.vehicleTypeParameter, vehicleTypeFromDB);
                            intent.putExtra(BluebookPageActivity.ownerImageParameter, ownerImageFromDB);
                            intent.putExtra(BluebookPageActivity.vehicleImageParameter, vehicleImageFromDB);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Bluebook not Added!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }

    private void isTheft() {
        String mobileNo = getIntent().getStringExtra(mobileNoParameter);
        String theftStatus = "Reported Theft/Lost";
        rootNode = FirebaseDatabase.getInstance("https://digitalvehicle-66b90-default-rtdb.firebaseio.com");
        reference = rootNode.getReference("bluebooks");
        Query checkUser = reference.orderByChild("mobileNumber").equalTo(mobileNo);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String result = snapshot.child(mobileNo).child("theftStatus").getValue(String.class);
                    if (result.equals(theftStatus)) {
                        Toast.makeText(MainPageActivity.this, "You have already reported your bike is theft.", Toast.LENGTH_SHORT).show();
                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainPageActivity.this);
                        alert.setTitle("Digital Vehicle - Theft");
                        alert.setMessage("Are you sure you want to report your vehicle theft??");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                reference.child(mobileNo).child("theftStatus").setValue(theftStatus);
                                Toast.makeText(getApplicationContext(), "Reported Theft/Lost", Toast.LENGTH_SHORT).show();
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
                } else {
                    Toast.makeText(getApplicationContext(), "Sorry! First add bluebook to report it lost..", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainPageActivity.this);
        alert.setTitle("Logout");
        alert.setMessage("Are you sure you want to logout?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent a = new Intent(getApplicationContext(), MenuActivity.class);
                a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
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
