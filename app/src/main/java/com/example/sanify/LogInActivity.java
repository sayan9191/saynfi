package com.example.sanify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class LogInActivity extends AppCompatActivity {

    EditText phoneNumber;
    TextView logInBtn, signInBtn;
    TextView googleSignInBtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        phoneNumber = findViewById(R.id.phoneEdTxt);
        logInBtn = findViewById(R.id.logInBtn);
        signInBtn = findViewById(R.id.btnSignIN);
        googleSignInBtn = findViewById(R.id.btnSignInGoogle);
        progressBar = findViewById(R.id.progressbar);



        //google button


        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (phoneNumber.getText().toString().trim().isEmpty()) {
                    Toast.makeText(LogInActivity.this, "Enter your details", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Intent intent = new Intent(LogInActivity.this,OTPVerifyActivity.class);
                    intent.putExtra("phoneNumber", phoneNumber.getText().toString());
                    startActivity(intent);
                }
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
    }
}