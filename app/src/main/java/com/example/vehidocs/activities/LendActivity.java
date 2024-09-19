package com.example.vehidocs.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vehidocs.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LendActivity extends AppCompatActivity {
    public static final String mobileNoParameter = "mobileNo";
    public static final String fullNameParameter = "fullName";
    TextInputEditText lendTo, mobileNumber, licenseNo, text;
    Button lendButton;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend);
        String mobileNo = getIntent().getStringExtra(mobileNoParameter);

        lendTo = findViewById(R.id.lendTo);
        mobileNumber = findViewById(R.id.mobileNumber);
        licenseNo = findViewById(R.id.licenseNo);
        lendButton = findViewById(R.id.lendButton);

        lendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateName() | !validateMobileNo() | !validateLicenseNo()) {
                    return;
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(LendActivity.this);
                    alert.setTitle("Digital Vehicle - Lend");
                    alert.setMessage("Are you sure you want to lend your vehicle??");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            isLend();
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
        });
    }

    private boolean validateName() {
        String lendToName = lendTo.getText().toString();

        if (lendToName.isEmpty()) {
            lendTo.setError("Please enter your fullname");
            return false;
        } else {
            lendTo.setError(null);
            return true;
        }
    }

    private Boolean validateMobileNo() {
        String mobileNo = mobileNumber.getText().toString();

        if (mobileNo.isEmpty()) {
            mobileNumber.setError("Please enter your mobile number");
            return false;
        } else {
            mobileNumber.setError(null);
            return true;
        }
    }

    private Boolean validateLicenseNo() {
        String license = licenseNo.getText().toString();
        if (license.isEmpty()) {
            licenseNo.setError("Please enter your license number");
            return false;
        } else {
            licenseNo.setError(null);
            return true;
        }
    }

    private void isLend() {
        String mobileNo = getIntent().getStringExtra(mobileNoParameter);
        String fullName = getIntent().getStringExtra(fullNameParameter);
        final String userEnteredName = lendTo.getText().toString();
        final String userEnteredMobileNo = mobileNumber.getText().toString();
        String lendStatus = "Lended";
        final String userEnteredLicenseNo = licenseNo.getText().toString();
        rootNode = FirebaseDatabase.getInstance("https://digitalvehicle-66b90-default-rtdb.firebaseio.com");
        reference = rootNode.getReference("bluebooks");
        Query checkUser = reference.orderByChild("mobileNumber").equalTo(mobileNo);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    reference.child(mobileNo).child("lendTo").setValue(userEnteredName);
                    reference.child(mobileNo).child("lendStatus").setValue(lendStatus);
                    reference.child(mobileNo).child("lendToMobileNo").setValue(userEnteredMobileNo);
                    reference.child(mobileNo).child("lendToLicenseNumber").setValue(userEnteredLicenseNo);
                    Toast.makeText(LendActivity.this, "Your lend request is success.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), MainPageActivity.class);
                    i.putExtra(MainPageActivity.mobileNoParameter, mobileNo);
                    i.putExtra(MainPageActivity.fullNameParameter, fullName);
                    startActivity(i);
                } else {
                    Toast.makeText(LendActivity.this, "Sorry! First Add Bluebook to lend!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back Pressed", Toast.LENGTH_SHORT).show();
        Intent i = getIntent();
        String fullName = i.getStringExtra(fullNameParameter);
        String mobileNumber = i.getStringExtra(mobileNoParameter);
        AlertDialog.Builder alert = new AlertDialog.Builder(LendActivity.this);
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