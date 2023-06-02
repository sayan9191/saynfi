package com.example.sanify.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sanify.EmailValidator;
import com.example.sanify.MainActivity;
import com.example.sanify.R;
import com.example.sanify.ui.auth.bottomsheet.BottomSheetPP;
import com.example.sanify.ui.auth.bottomsheet.BottomSheetTC;
import com.example.sanify.ui.auth.login.LogInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    com.google.android.material.textfield.TextInputEditText emailId, name, password;
    TextView termsAndConditionBtn, privacyAndPolicyBtn, signINBtn, continueBtn;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_sign_up);
        termsAndConditionBtn = findViewById(R.id.termsAndConditionBtn);
        privacyAndPolicyBtn = findViewById(R.id.privacyAndPolicy);
        emailId = findViewById(R.id.emailIdEditTxt);
        name = findViewById(R.id.nameIdEditTxt);
        password = findViewById(R.id.passWordEditText);
        continueBtn = findViewById(R.id.signUpBtn);
        signINBtn = findViewById(R.id.signInBtn);


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
        //old user check
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = Objects.requireNonNull(emailId.getText()).toString().trim();
                String userPassword = Objects.requireNonNull(password.getText()).toString().trim();
                if (TextUtils.isEmpty(emailId.getText())) {
                    Toast.makeText(SignUpActivity.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(name.getText())) {
                    Toast.makeText(SignUpActivity.this, "Enter your Name", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password.getText())) {
                    Toast.makeText(SignUpActivity.this, "Enter your Password", Toast.LENGTH_SHORT).show();
                }
                if (emailId.getText().toString().trim().isEmpty() || name.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Enter your details first", Toast.LENGTH_SHORT).show();
                }
                EmailValidator emailValidator = new EmailValidator();
                if (emailValidator.validate(userEmail)) {
                    //handel new user
                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(SignUpActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(SignUpActivity.this, "Enter Valid email Id", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
