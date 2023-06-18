package com.realteenpatti.sanify.ui.auth.signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.realteenpatti.sanify.databinding.ActivitySignUpBinding;
import com.realteenpatti.sanify.MainActivity;
import com.realteenpatti.sanify.ui.auth.otp.OTPVerifyActivity;
import com.realteenpatti.sanify.ui.bottomsheet.BottomSheetPP;
import com.realteenpatti.sanify.ui.bottomsheet.BottomSheetTC;
import com.realteenpatti.sanify.ui.auth.login.LogInActivity;
import com.realteenpatti.sanify.ui.dialogbox.LoadingScreen;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    private ActivitySignUpBinding binding;
    private SignUpViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        setContentView(binding.getRoot());

        binding.termsAndConditionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetTC bottomSheetTC = new BottomSheetTC();
                bottomSheetTC.show(getSupportFragmentManager(), "TAG");
            }
        });

        binding.privacyAndPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetPP bottomSheetPP = new BottomSheetPP();
                bottomSheetPP.show(getSupportFragmentManager(), "TAG");
            }
        });

        binding.loginRedirectionBtn.setOnClickListener(new View.OnClickListener() {
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

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userPhoneNumber = Objects.requireNonNull(binding.signUpPhoneNumberEditText.getText()).toString().trim();
                String userPassword = Objects.requireNonNull(binding.signUpPasswordEditText.getText()).toString().trim();
                if (TextUtils.isEmpty(binding.signUpPhoneNumberEditText.getText())) {
                    Toast.makeText(SignUpActivity.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(binding.signUpNameEditText.getText())) {
                    Toast.makeText(SignUpActivity.this, "Enter your Name", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(binding.signUpPasswordEditText.getText())) {
                    Toast.makeText(SignUpActivity.this, "Enter your Password", Toast.LENGTH_SHORT).show();
                }
                if (binding.signUpPhoneNumberEditText.getText().toString().trim().isEmpty() || binding.signUpNameEditText.getText().toString().isEmpty() || binding.signUpPasswordEditText.getText().toString().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Enter your details first", Toast.LENGTH_SHORT).show();
                }

                if (userPhoneNumber.length() == 10) {
                    createNewUser();
                } else {
                    Toast.makeText(SignUpActivity.this, "Enter valid phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void createNewUser() {
        LoadingScreen.Companion.showLoadingDialog(this);
        //handel new user
        PhoneAuthProvider.getInstance().verifyPhoneNumber(binding.signUpCountryCodePicker.getSelectedCountryCodeWithPlus() + binding.signUpPhoneNumberEditText.getText().toString(),
                60L,
                TimeUnit.SECONDS,
                SignUpActivity.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        /*viewModel.signUp(binding.signUpNameEditText.getText().toString().trim(),
                                binding.signUpPasswordEditText.getText().toString().trim(),
                                binding.signUpPhoneNumberEditText.getText().toString().trim(),
                                binding.signUpCountryCodePicker.getSelectedCountryCodeWithPlus().trim());*/
                        LoadingScreen.Companion.hideLoadingDialog();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        LoadingScreen.Companion.hideLoadingDialog();
                        Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                        LoadingScreen.Companion.hideLoadingDialog();

                        Intent intent = new Intent(getApplicationContext(), OTPVerifyActivity.class);
                        intent.putExtra("phoneNumber", binding.signUpPhoneNumberEditText.getText().toString().trim());
                        intent.putExtra("verificationId", verificationId);
                        intent.putExtra("countryCode", binding.signUpCountryCodePicker.getSelectedCountryCodeWithPlus());
                        intent.putExtra("password", binding.signUpPasswordEditText.getText().toString().trim());
                        intent.putExtra("name", binding.signUpNameEditText.getText().toString().trim());

                        startActivity(intent);
                    }
                });
    }
}
