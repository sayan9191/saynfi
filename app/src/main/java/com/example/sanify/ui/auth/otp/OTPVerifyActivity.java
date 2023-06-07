package com.example.sanify.ui.auth.otp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sanify.MainActivity;
import com.example.sanify.R;
import com.example.sanify.ui.auth.login.LogInActivity;
import com.example.sanify.ui.dialogbox.LoadingScreen;
import com.example.sanify.utils.StorageUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OTPVerifyActivity extends AppCompatActivity {

    EditText input1, input2, input3, input4, input5, input6;
    TextView mobile, verifyBtn, resendOtpBtn;
    ProgressBar progressBar1;

    String name, verificationId;

    VerifyOTPViewModel viewModel;
    StorageUtil localStorage = StorageUtil.Companion.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverify);

        viewModel = new ViewModelProvider(this).get(VerifyOTPViewModel.class);
        // Set the shared pref editable
        localStorage.setSharedPref(getSharedPreferences("sharedPref", Context.MODE_PRIVATE));


        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        input3 = findViewById(R.id.input3);
        input4 = findViewById(R.id.input4);
        input5 = findViewById(R.id.input5);
        input6 = findViewById(R.id.input6);

        progressBar1 = findViewById(R.id.progressbar1);
        verifyBtn = findViewById(R.id.verifyBtn);
        resendOtpBtn = findViewById(R.id.textSendOtp);

        mobile = findViewById(R.id.textMobile);


        String phone = getIntent().getStringExtra("phoneNumber");
        String countryCode = getIntent().getStringExtra("countryCode");
        String password = getIntent().getStringExtra("password");
        verificationId = getIntent().getStringExtra("verificationId");
        try{
            name = getIntent().getStringExtra("name");
        } catch (Exception e){
            e.getStackTrace();
        }


        mobile.setText(String.format(countryCode + "-" + phone));
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input1.getText().toString().trim().isEmpty()
                        || input2.getText().toString().trim().isEmpty()
                        || input3.getText().toString().trim().isEmpty()
                        || input4.getText().toString().trim().isEmpty()
                        || input5.getText().toString().trim().isEmpty()
                        || input6.getText().toString().trim().isEmpty()
                ){
                    Toast.makeText(OTPVerifyActivity.this, "Please enter valid code",Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = input1.getText().toString() + input2.getText().toString()
                        + input3.getText().toString() + input4.getText().toString() + input5.getText().toString()
                        + input6.getText().toString();


                if (name!= null){
                    progressBar1.setVisibility(View.VISIBLE);
                    verifyBtn.setVisibility(view.INVISIBLE);

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId,code);

                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar1.setVisibility(view.GONE);
                                    verifyBtn.setVisibility(view.VISIBLE);
                                    if (task.isSuccessful()){

                                        if (name != null){
                                            viewModel.signUp(name, password, phone, countryCode);
                                        }else{
                                            viewModel.changePassword(password, countryCode+phone);
                                        }
                                    }
                                    else{
                                        Toast.makeText(OTPVerifyActivity.this,"Invalid OTP" + task.getException(),Toast.LENGTH_SHORT).show();
                                        Log.d("Invalid----", task.getException().toString());
                                    }
                                }
                            });
                }
            }
        });

        //resend oto button(it is not working)
        resendOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(countryCode + phone,
                        60L,
                        TimeUnit.SECONDS,
                        OTPVerifyActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(OTPVerifyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                verificationId = newVerificationId;
                                Toast.makeText(OTPVerifyActivity.this,"OTP sent",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        viewModel.getLoginToken().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String loginToken) {
                if (!loginToken.isEmpty()){
                    localStorage.setToken(loginToken);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!Objects.equals(s, "")){
                    Toast.makeText(OTPVerifyActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading){
                    LoadingScreen.Companion.showLoadingDialog(OTPVerifyActivity.this);
                }else{
                    try {
                        LoadingScreen.Companion.hideLoadingDialog();
                    } catch (Exception e){
                        e.getStackTrace();
                    }
                }
            }
        });

        setupOTPInputs();
    }






    private void setupOTPInputs(){
        input1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    input2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    input3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    input4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    input5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        input5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    input6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}