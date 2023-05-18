package com.example.sanify.retrofit

import com.example.sanify.retrofit.models.Items
import com.example.sanify.retrofit.models.LoginResponseModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface RemoteApi {

    @FormUrlEncoded
    @POST
    fun login(@Field("username") username :String, @Field("password") password : String) : Call<LoginResponseModel>
}