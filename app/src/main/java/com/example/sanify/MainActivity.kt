package com.example.sanify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sanify.utils.StorageUtil

class MainActivity : AppCompatActivity() {

    lateinit var binding: com.example.sanify.databinding.ActivityMainBinding
    val localStorage = StorageUtil()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = com.example.sanify.databinding.ActivityMainBinding.inflate(layoutInflater)
        localStorage.sharedPref = getSharedPreferences("sharedPref", MODE_PRIVATE)
        setContentView(binding.root)

        if (localStorage.coins == -1){
            localStorage.coins = 1000
        }

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
//        val navController = navHostFragment.navController


//        binding.profileImage.setOnClickListener {
//            val intent = Intent(this, Profile_Activity::class.java)
//            startActivity(intent)
//        }
    }
}