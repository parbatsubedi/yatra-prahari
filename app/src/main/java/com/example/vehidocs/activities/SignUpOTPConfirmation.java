package com.example.vehidocs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.vehidocs.R;
import com.example.vehidocs.features.models.UserHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class SignUpOTPConfirmation extends AppCompatActivity {

    Button verify_btn;
    EditText mobileNumberEnteredByTheUser;
    ProgressBar progressBar;
    String verificationCodeBySystem;
    PinView codeEnteredByUser;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.ForceResendingToken resendingToken;
    private String mobileNo;

    public static final String fullNameParameter = "fullName";
    public static final String mobileNoParameter = "mobileNo";
    public static final String emailParameter = "email";
    public static final String licenseParameter = "license";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_otpconfirmation);

        // Back Button
        Button back_button = findViewById(R.id.back_Btn);
        back_button.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpOTPConfirmation.this, RequestActivity.class);
            startActivity(intent);
            finish();
        });

        verify_btn = findViewById(R.id.verify_btn);
        codeEnteredByUser = findViewById(R.id.verification_code_entered_by_user);
        disableLoginButton();

        firebaseAuth = FirebaseAuth.getInstance();
        mobileNo = getIntent().getStringExtra("mobileNo");
        sendVerificationCodeToUser(mobileNo);

        codeEnteredByUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 6) {
                    enableLoginButton();
                } else {
                    disableLoginButton();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        verify_btn.setOnClickListener(v -> {
            String enteredCode = codeEnteredByUser.getText().toString();
            if (enteredCode.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter a value", Toast.LENGTH_SHORT).show();
            } else if (enteredCode.length() == 6) {
                verifyCode(enteredCode);
            } else {
                Toast.makeText(getApplicationContext(), "Invalid OTP pattern", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendVerificationCodeToUser(String mobileNo) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber("+977" + mobileNo)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(verificationId, token);
            verificationCodeBySystem = verificationId;
            resendingToken = token;
            Log.d("OTP", "Code sent: " + verificationId);
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            String code = credential.getSmsCode();
            if (code != null) {
                codeEnteredByUser.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(SignUpOTPConfirmation.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("OTP", "Verification failed: " + e.getMessage());
        }
    };

    private void disableLoginButton() {
        verify_btn.setEnabled(false);
        verify_btn.setAlpha(0.5f);
    }

    private void enableLoginButton() {
        verify_btn.setEnabled(true);
        verify_btn.setAlpha(1.0f);
    }

    private void verifyCode(String verificationCodeByUser) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, verificationCodeByUser);
        signInTheUserByCredentials(credential);
    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            rootNode = FirebaseDatabase.getInstance("https://digitalvehicle-66b90-default-rtdb.firebaseio.com");
                            reference = rootNode.getReference("Users");
                            String fullName = getIntent().getStringExtra(fullNameParameter);
                            String email = getIntent().getStringExtra(emailParameter);
                            String mobileNo = getIntent().getStringExtra(mobileNoParameter);
                            String license = getIntent().getStringExtra(licenseParameter);

                            UserHelperClass user = new UserHelperClass(fullName, email, mobileNo, license);
                            reference.child(mobileNo).setValue(user);

                            Log.d("Phone Auth", "successful");
                            Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
                            intent.putExtra(MainPageActivity.mobileNoParameter, mobileNo);
                            intent.putExtra(MainPageActivity.fullNameParameter, fullName);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(SignUpOTPConfirmation.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUpOTPConfirmation.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back Pressed", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), RequestActivity.class);
        startActivity(intent);
    }
}
