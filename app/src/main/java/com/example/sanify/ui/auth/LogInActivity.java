package com.example.sanify.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sanify.MainActivity;
import com.example.sanify.R;

public class LogInActivity extends AppCompatActivity {

    com.google.android.material.textfield.TextInputEditText emailId, password;
    TextView logInBtn, signInBtn, forgotPasswordBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        emailId = findViewById(R.id.emailIdEditTxt);
        logInBtn = findViewById(R.id.logInBtn);
        signInBtn = findViewById(R.id.signUpBtn);
        forgotPasswordBtn = findViewById(R.id.forgotPasswordBtn);

        logInBtn.setOnClickListener(view -> {

            if (emailId.getText().toString().trim().isEmpty()) {
                Toast.makeText(LogInActivity.this, "Enter your details", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                intent.putExtra("phoneNumber", emailId.getText().toString());
                startActivity(intent);
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}