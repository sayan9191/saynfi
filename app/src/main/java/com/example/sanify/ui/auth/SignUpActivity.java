package com.example.sanify.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sanify.R;
import com.example.sanify.ui.auth.bottomsheet.BottomSheetPP;
import com.example.sanify.ui.auth.bottomsheet.BottomSheetTC;

public class SignUpActivity extends AppCompatActivity {

    com.google.android.material.textfield.TextInputEditText phoneNo, name, password;
    TextView termsAndConditionBtn, privacyAndPolicyBtn, signINBtn, continueBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        termsAndConditionBtn = findViewById(R.id.termsAndConditionBtn);
        privacyAndPolicyBtn = findViewById(R.id.privacyAndPolicy);
        phoneNo = findViewById(R.id.emailIdEditTxt);
        name = findViewById(R.id.nameIdEditTxt);
        password = findViewById(R.id.passWordEditText);
        continueBtn = findViewById(R.id.signUpBtn);
        signINBtn= findViewById(R.id.signInBtn);


        String phoneNumber = phoneNo.getText().toString();
        termsAndConditionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetTC bottomSheetTC = new BottomSheetTC();
                bottomSheetTC.show(getSupportFragmentManager(), "TAG");
            }
        });

        privacyAndPolicyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetPP bottomSheetPP = new BottomSheetPP();
                bottomSheetPP.show(getSupportFragmentManager(), "TAG");
            }
        });

        signINBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneNo.getText().toString().trim().isEmpty() || name.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Enter your details first", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent = new Intent(SignUpActivity.this, OTPVerifyActivity.class);
                    intent.putExtra("phoneNumber", phoneNo.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}
