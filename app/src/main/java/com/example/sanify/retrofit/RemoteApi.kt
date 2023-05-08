package com.example.sanify.retrofit

import com.example.sanify.retrofit.models.Items
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header


interface RemoteApi {

    @GET("items")
    fun getItems(@Header("Authorization") token : String) : Call<Items>
}