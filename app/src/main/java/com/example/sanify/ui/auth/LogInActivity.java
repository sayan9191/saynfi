package com.example.sanify.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sanify.LoginViewModel;
import com.example.sanify.MainActivity;
import com.example.sanify.R;
import com.example.sanify.ui.DashBoardFragment;
import com.example.sanify.utils.NetworkUtils;
import com.example.sanify.utils.StorageUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LogInActivity extends AppCompatActivity {

    com.google.android.material.textfield.TextInputEditText emailId, password;
    TextView logInBtn, signUpBtn, forgotPasswordBtn;
    FirebaseAuth firebaseAuth;
    private LoginViewModel viewModel;
    StorageUtil localStorage = new StorageUtil();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        setContentView(R.layout.activity_log_in);
        emailId = findViewById(R.id.emailIdEditTxt);
        password = findViewById(R.id.passWordEditText);
        logInBtn = findViewById(R.id.logInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        forgotPasswordBtn = findViewById(R.id.forgotPasswordBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        if (localStorage.getUserStatus().equals("logged_in")){
            Intent intent = new Intent(LogInActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
            finish();
        }

        logInBtn.setOnClickListener(view -> {
            String loginEmail = emailId.getText().toString().trim();
            String loginPassword = password.getText().toString().trim();
            if (TextUtils.isEmpty(loginEmail)) {
                Toast.makeText(LogInActivity.this, "Enter your Email ID", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(loginPassword)) {
                Toast.makeText(LogInActivity.this, "Enter your password", Toast.LENGTH_SHORT).show();
            }
            if (loginEmail.isEmpty() || loginPassword.isEmpty()) {
                Toast.makeText(LogInActivity.this, "Enter your details", Toast.LENGTH_SHORT).show();
                return;
            } else {

                if (NetworkUtils.Companion.isOnline(this)){
                    if (loginEmail.equals("Ja.adam192@gmail.com") && loginPassword.equals("ABC@aws12345")){
                        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                        localStorage.saveTokenLocally("logged_in");
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LogInActivity.this, "Username or password not correct", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LogInActivity.this, "Please connect to the internet", Toast.LENGTH_SHORT).show();
                }


//                viewModel.login(loginEmail, loginPassword);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
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


        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!Objects.equals(s, "")){
                    Toast.makeText(LogInActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.isLogin().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLogin) {
                if (isLogin){
                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}