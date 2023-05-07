package com.example.sanify.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtils {
    companion object{
        //getting retrofit instance
        fun getRetrofitInstance(): Retrofit {
            val client = Retrofit.Builder()
            client.baseUrl("http://10.0.2.2:8000/")
            client.addConverterFactory(GsonConverterFactory.create())
            return client.build()
        }



    }
}