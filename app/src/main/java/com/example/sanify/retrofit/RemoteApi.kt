package com.example.sanify.retrofit

import com.example.sanify.retrofit.models.Items
import com.example.sanify.retrofit.models.LoginResponseModel
import com.example.sanify.retrofit.models.login.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface RemoteApi {

    @POST("login/")
    fun login(@Body credentials: LoginRequest) : Call<Any>
}