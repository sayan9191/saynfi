package com.example.sanify

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bluehomestudio.luckywheel.WheelItem
import com.example.sanify.databinding.ActivityMainBinding
import com.example.sanify.ui.lottery.LotteryActivity
import com.example.sanify.ui.spin.LuckyDrawActivity

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.lotteryOption.setOnClickListener {
            val intent = Intent(this, LotteryActivity::class.java)
            startActivity(intent)
        }

        binding.spinOption.setOnClickListener {
            val intent = Intent(this, LuckyDrawActivity::class.java)
            startActivity(intent)
        }

    }
}