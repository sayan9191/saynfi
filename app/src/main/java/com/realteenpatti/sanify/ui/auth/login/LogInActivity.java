package com.realteenpatti.sanify.ui.auth.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.realteenpatti.sanify.MainActivity;
import com.realteenpatti.sanify.databinding.ActivityLogInBinding;
import com.realteenpatti.sanify.ui.auth.ForgotPasswordActivity;
import com.realteenpatti.sanify.ui.auth.signup.SignUpActivity;
import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen;
import com.realteenpatti.sanify.utils.NetworkUtils;
import com.realteenpatti.sanify.utils.StorageUtil;

import java.util.Objects;

public class LogInActivity extends AppCompatActivity {


    private LoginViewModel viewModel;
    StorageUtil localStorage = StorageUtil.Companion.getInstance();

    ActivityLogInBinding binding;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        setContentView(binding.getRoot());

        FirebaseApp.initializeApp(/*context=*/ this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                PlayIntegrityAppCheckProviderFactory.getInstance());

        // Set the shared pref editable
        localStorage.setSharedPref(getSharedPreferences("sharedPref", Context.MODE_PRIVATE));


        binding.logInBtn.setOnClickListener(view -> {
            String loginPhone = Objects.requireNonNull(binding.emailIdEditTxt.getText()).toString().trim();
            String loginPassword = Objects.requireNonNull(binding.passWordEditText.getText()).toString().trim();
            if (TextUtils.isEmpty(loginPhone)) {
                Toast.makeText(LogInActivity.this, "Enter your Email ID", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(loginPassword)) {
                Toast.makeText(LogInActivity.this, "Enter your password", Toast.LENGTH_SHORT).show();
            }
            if (loginPhone.isEmpty() || loginPassword.isEmpty()) {
                Toast.makeText(LogInActivity.this, "Enter your details", Toast.LENGTH_SHORT).show();
            } else {

                if (NetworkUtils.Companion.isOnline(this)){
                    viewModel.login(loginPhone, loginPassword);
                }else{
                    Toast.makeText(LogInActivity.this, "Please connect to the internet", Toast.LENGTH_SHORT).show();

                }

            }
        });

        String user_status = localStorage.getToken();

        // If user already logged in
        if (user_status != null && !user_status.isEmpty()){
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        binding.signUpRedirectionBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
                return true;
            }
        });

        binding.forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });


        viewModel.getLoginToken().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String loginToken) {
                if (!loginToken.isEmpty()){
                    localStorage.setToken(loginToken);
                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
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

        viewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading){
                    LoadingScreen.Companion.showLoadingDialog(LogInActivity.this);
                }else{
                    try {
                        LoadingScreen.Companion.hideLoadingDialog();
                    } catch (Exception e){
                        e.getStackTrace();
                    }
                }
            }
        });

    }
}