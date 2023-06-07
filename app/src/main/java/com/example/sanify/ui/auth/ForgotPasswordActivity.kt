package com.example.sanify.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sanify.databinding.ActivityForgotPasswordBinding
import com.example.sanify.ui.auth.otp.OTPVerifyActivity
import com.example.sanify.ui.dialogbox.LoadingScreen.Companion.hideLoadingDialog
import com.example.sanify.ui.dialogbox.LoadingScreen.Companion.showLoadingDialog
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.continueBtn.setOnClickListener {
            val pass = binding.passWordEditText.text.toString().trim()
            val confirmPass = binding.confirmPassWordEditText.text.toString().trim()
            val phoneNumber = binding.forgotPasswordPhoneNumberEditText.text.toString().trim()
            val countryCode = binding.forgotPasswordCountryCodePicker.selectedCountryCodeWithPlus.toString().trim()

            if (phoneNumber.length != 10){
                Toast.makeText(this, "Please enter correct phone number", Toast.LENGTH_SHORT).show()
            }else if (pass != confirmPass){
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()
            }else{
                changePass(countryCode, phoneNumber, pass)
            }
        }
    }


    fun changePass(countryCode : String, phoneNumber : String, password : String) {
        showLoadingDialog(this)
        //handel new user
        PhoneAuthProvider.getInstance()
            .verifyPhoneNumber(countryCode + phoneNumber
                .toString(),
                60L,
                TimeUnit.SECONDS,
                this@ForgotPasswordActivity,
                object : OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                        /*viewModel.signUp(binding.signUpNameEditText.getText().toString().trim(),
                                binding.signUpPasswordEditText.getText().toString().trim(),
                                binding.signUpPhoneNumberEditText.getText().toString().trim(),
                                binding.signUpCountryCodePicker.getSelectedCountryCodeWithPlus().trim());*/
                        hideLoadingDialog()
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        hideLoadingDialog()
                        Toast.makeText(this@ForgotPasswordActivity, e.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        forceResendingToken: ForceResendingToken
                    ) {
                        hideLoadingDialog()
                        val intent = Intent(applicationContext, OTPVerifyActivity::class.java)
                        intent.putExtra(
                            "phoneNumber",
                            phoneNumber
                        )
                        intent.putExtra("verificationId", verificationId)
                        intent.putExtra(
                            "countryCode",
                            countryCode
                        )
                        intent.putExtra(
                            "password",
                            password
                        )
                        startActivity(intent)
                    }
                })
    }
}