package com.example.sanify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LuckyDrawActivity extends AppCompatActivity {

    //LuckyDraw values
    final int[] sectors = {15, 1, 0, 5, 10, 20, 2, 3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_draw);
    }
}