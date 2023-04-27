package com.example.sanify.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanify.ui.auth.bottomsheet.BottomSheetPP;
import com.example.sanify.ui.auth.bottomsheet.BottomSheetTC;
import com.example.sanify.R;

public class SignUpActivity extends AppCompatActivity {

    EditText phoneNo, name, emailId;
    TextView termsAndConditionBtn, privacyAndPolicyBtn, signINBtn, continueBtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        termsAndConditionBtn = findViewById(R.id.termsAndConditionBtn);
        privacyAndPolicyBtn = findViewById(R.id.privacyAndPolicy);
        phoneNo = findViewById(R.id.newPhoneEditTxt);
        name = findViewById(R.id.fullNameEditTxt);
        emailId = findViewById(R.id.emailIdEditTxt);
        signINBtn = findViewById(R.id.btnSignIN);
        continueBtn = findViewById(R.id.logInBtn);
        progressBar = findViewById(R.id.progressbar);

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
                if (phoneNo.getText().toString().trim().isEmpty() || name.getText().toString().isEmpty() || emailId.getText().toString().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Enter your details first", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Intent intent = new Intent(SignUpActivity.this, OTPVerifyActivity.class);
                    intent.putExtra("phoneNumber",phoneNo.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}
