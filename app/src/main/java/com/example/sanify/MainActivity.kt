package com.example.sanify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

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