package com.example.sanify.retrofit

import com.example.sanify.retrofit.models.coin.CoinBalanceResponseModel
import com.example.sanify.retrofit.models.login.LoginResponse
import com.example.sanify.retrofit.models.transaction.TransactionRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface RemoteApi {

    @POST("login")
    @FormUrlEncoded
    fun login(@Field("username") username : String, @Field("password") password: String) : Call<LoginResponse>

    @GET("coin/")
    fun getCoinBalance(@Header("Authorization") token : String) : Call<CoinBalanceResponseModel>

    @POST("transaction/")
    fun transaction(@Header("Authorization") token: String, @Body body: TransactionRequestModel) : Call<String>
}