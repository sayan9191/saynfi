package com.example.sanify.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sanify.MainActivity;
import com.example.sanify.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    com.google.android.material.textfield.TextInputEditText emailId, password;
    TextView logInBtn, signInBtn, forgotPasswordBtn;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        emailId = findViewById(R.id.emailIdEditTxt);
        password = findViewById(R.id.passWordEditText);
        logInBtn = findViewById(R.id.logInBtn);
        signInBtn = findViewById(R.id.signUpBtn);
        forgotPasswordBtn = findViewById(R.id.forgotPasswordBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        logInBtn.setOnClickListener(view -> {
            String loginEmail = emailId.getText().toString().trim();
            String loginPassword = password.getText().toString().trim();
            if (TextUtils.isEmpty(emailId.getText().toString().trim())) {
                Toast.makeText(LogInActivity.this, "Enter your Email ID", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(password.getText().toString().trim())) {
                Toast.makeText(LogInActivity.this, "Enter your password", Toast.LENGTH_SHORT).show();
            }
            if (emailId.getText().toString().trim().isEmpty() || password.getText().toString().isEmpty()) {
                Toast.makeText(LogInActivity.this, "Enter your details", Toast.LENGTH_SHORT).show();
                return;
            } else {
                firebaseAuth.signInWithEmailAndPassword(loginEmail,loginPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LogInActivity.this, "Welcome User", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Toast.makeText(LogInActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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