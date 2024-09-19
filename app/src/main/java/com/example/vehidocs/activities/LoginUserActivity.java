package com.example.vehidocs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vehidocs.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginUserActivity extends AppCompatActivity {

    private static final String TAG = "LoginUserActivity";
    //Variables
    TextInputLayout mobileNo;
    DatabaseReference reference;
    FirebaseDatabase rootNode;
    Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_user);

        Button back_button = findViewById(R.id.back_Btn);
        Login = findViewById(R.id.loginNext_btn);
        back_button.setOnClickListener(view -> {
            Intent intent = new Intent(LoginUserActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        });

        //Hooks
        mobileNo = findViewById(R.id.mobileNumber);
    }

    private Boolean validateMobileNo() {
        String mobileNumber = mobileNo.getEditText().getText().toString();

        Log.d(TAG, "Validating mobile number: " + mobileNumber);

        if (mobileNumber.isEmpty()) {
            mobileNo.setError("Please enter your mobile number");
            return false;
        } else if (mobileNumber.length() != 10) {
            mobileNo.setError("Please enter valid mobile Number.");
            return false;
        } else {
            mobileNo.setError(null);
            mobileNo.setErrorEnabled(false);
            return true;
        }
    }

    private void disableLoginButton() {
        Login.setEnabled(false);
        Login.setAlpha(0.5f);
    }

    private void enableLoginButton() {
        Login.setEnabled(true);
        Login.setAlpha(1.0f);
    }

    public void NextPage(View view) {
        disableLoginButton();
        //reference.setValue("Hello");
        //Get all the values
//                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        if (!validateMobileNo()) {
            enableLoginButton();
            return;
        } else {
            isUser();
        }
//        isUser();
    }

    private void isUser() {
        final String userEnteredMobileNo = mobileNo.getEditText().getText().toString();
        Log.d(TAG, "Checking user with mobile number: " + userEnteredMobileNo);
        //Database Reference
        rootNode = FirebaseDatabase.getInstance("https://digitalvehicle-66b90-default-rtdb.firebaseio.com");
        DatabaseReference reference = rootNode.getReference("Users");
        //Query
        Query checkUser = reference.orderByChild("mobileNo").equalTo(userEnteredMobileNo);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    mobileNo.setError(null);
                    mobileNo.setErrorEnabled(false);

                    String fullNameFromDB = snapshot.child(userEnteredMobileNo).child("fullName").getValue(String.class);
                    String emailFromDB = snapshot.child(userEnteredMobileNo).child("email").getValue(String.class);
                    String mobileNoFromDB = snapshot.child(userEnteredMobileNo).child("mobileNo").getValue(String.class);
                    String licenseFromDB = snapshot.child(userEnteredMobileNo).child("license").getValue(String.class);

                    Intent intent = new Intent(getApplicationContext(), LogInOTPConfirmation.class);

                    intent.putExtra("fullName", fullNameFromDB);
                    intent.putExtra("email", emailFromDB);
                    intent.putExtra("mobileNo", mobileNoFromDB);
                    intent.putExtra("license", licenseFromDB);
                    enableLoginButton();

                    startActivity(intent);

                } else {
                    mobileNo.setError("No such user exist");
                    mobileNo.requestFocus();
                    enableLoginButton();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "No file found", Toast.LENGTH_SHORT).show();
                enableLoginButton();
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