package com.example.sanify

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.sanify.ui.lottery.LotteryBuyActivity
import com.example.sanify.ui.spin.LuckyDrawActivity

class MainActivity : AppCompatActivity() {

    lateinit var binding: com.example.sanify.databinding.ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = com.example.sanify.databinding.ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
//        val navController = navHostFragment.navController


//        binding.profileImage.setOnClickListener {
//            val intent = Intent(this, Profile_Activity::class.java)
//            startActivity(intent)
//        }
    }
}