package com.example.sanify.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.sanify.MainActivity;
import com.example.sanify.R;

public class OTPVerifyActivity extends AppCompatActivity {

    EditText input1, input2, input3, input4, input5, input6;
    TextView mobile, verifyBtn, resendOtpBtn;
    ProgressBar progressBar1;
    String VerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverify);

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
        mobile.setText(String.format("+91-%s", getIntent().getStringExtra("phoneNumber")));
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OTPVerifyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}