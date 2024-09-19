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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RequestActivity extends AppCompatActivity {
    TextInputLayout regName, regEmail, regMobileNumber, regLicense;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        //Back button
        Button back_button = findViewById(R.id.back_Btn);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequestActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Hooks
        regName = findViewById(R.id.fullName);
        regEmail = findViewById(R.id.email);
        regMobileNumber = findViewById(R.id.mobileNumber);
        regLicense = findViewById(R.id.license);

    }

    private Boolean validateName() {
        String fullName = regName.getEditText().getText().toString();

        if (fullName.isEmpty()) {
            regName.setError("Please enter your fullname");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String email = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.isEmpty()) {
            regEmail.setError("Please enter a your email");
            return false;
        } else if (!email.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateMobileNo() {
        String mobileNumber = regMobileNumber.getEditText().getText().toString();

        if (mobileNumber.isEmpty()) {
            regMobileNumber.setError("Please enter your mobile number");
            return false;
        }else if (mobileNumber.length() != 10) {
            regMobileNumber.setError("Please enter valid mobile Number.");
            return false;
        }
        else {
            regMobileNumber.setError(null);
            regMobileNumber.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateLicenseNo() {
        String license = regLicense.getEditText().getText().toString();
        if (license.isEmpty()) {
            regLicense.setError("Please enter your license number");
            return false;
        } else {
            regLicense.setError(null);
            regLicense.setErrorEnabled(false);
            return true;
        }
    }

    public void registerUser(View view) {
        if (!validateName() | !validateEmail() | !validateMobileNo() | !validateLicenseNo()) {
            return;
        } else {
            isUser();
        }
    }

    private void isUser() {
        final String userEnteredFullName = regName.getEditText().getText().toString();
        final String userEnteredMobileNo = regMobileNumber.getEditText().getText().toString();
        final String userEnteredEmail = regEmail.getEditText().getText().toString();
        final String userEnteredLicenseNo = regLicense.getEditText().getText().toString();
        //Database Reference
        rootNode = FirebaseDatabase.getInstance("https://digitalvehicle-66b90-default-rtdb.firebaseio.com");
        DatabaseReference ref = rootNode.getReference("Licenses");
        Query chkLicense = ref.orderByChild("mobileNo").equalTo(userEnteredMobileNo);
        chkLicense.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    String nameFromDB = snapshot.child(userEnteredMobileNo).child("name").getValue(String.class);
                    String licenseNoFromDB = snapshot.child(userEnteredMobileNo).child("licenseNo").getValue(String.class);
                    String emailFromDB = snapshot.child(userEnteredMobileNo).child("email").getValue(String.class);

                    if (userEnteredFullName.equals(nameFromDB) && userEnteredEmail.equals(emailFromDB) && userEnteredLicenseNo.equals(licenseNoFromDB)) {
                        DatabaseReference reference = rootNode.getReference("Users");
                        //Query
                        Query checkUser = reference.orderByChild("mobileNo").equalTo(userEnteredMobileNo);
                        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (!snapshot.exists()) {
                                    regMobileNumber.setError(null);
                                    regMobileNumber.setErrorEnabled(false);

                                    String fullName = regName.getEditText().getText().toString();
                                    String email = regEmail.getEditText().getText().toString();
                                    String mobileNo = regMobileNumber.getEditText().getText().toString();
                                    String license = regLicense.getEditText().getText().toString();

                                    Intent intent = new Intent(getApplicationContext(), SignUpOTPConfirmation.class);
                                    intent.putExtra(SignUpOTPConfirmation.mobileNoParameter, mobileNo);
                                    intent.putExtra(SignUpOTPConfirmation.fullNameParameter, fullName);
                                    intent.putExtra(SignUpOTPConfirmation.emailParameter, email);
                                    intent.putExtra(SignUpOTPConfirmation.licenseParameter, license);

                                    startActivity(intent);

                                } else {
                                    Toast.makeText(getApplicationContext(), "User Already Exist", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getApplicationContext(), "No file found", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        // Toast.makeText(getApplicationContext(), "No License Found with this details", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alert = new AlertDialog.Builder(RequestActivity.this);
                        alert.setTitle("Digital Vehicle");
                        alert.setMessage("No License found with given details! Please fill up original details!");
                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(getApplicationContext(), RequestActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        alert.show();
                    }

                } else {
                    // Toast.makeText(getApplicationContext(), "No License Found with this details", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alert = new AlertDialog.Builder(RequestActivity.this);
                    alert.setTitle("Digital Vehicle");
                    alert.setMessage("No License found with given details! Please fill up original details!");
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(), RequestActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    alert.show();
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
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }
}

