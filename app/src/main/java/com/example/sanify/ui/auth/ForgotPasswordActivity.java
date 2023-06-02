package com.example.sanify.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sanify.R;
import com.example.sanify.ui.auth.login.LogInActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    com.google.android.material.textfield.TextInputEditText emailId, password1, password2;
    Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailId = findViewById(R.id.emailIdEditTxt);
        password1 = findViewById(R.id.confirmPassWordEditText);
        password2 = findViewById(R.id.passWordEditText);
        continueBtn = findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}