package com.example.sanify.retrofit

import com.example.sanify.retrofit.models.coin.CoinBalanceResponseModel
import com.example.sanify.retrofit.models.login.LoginResponse
import com.example.sanify.retrofit.models.transaction.*
import retrofit2.Call
import retrofit2.http.*


interface RemoteApi {

    @POST("login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("coin/")
    fun getCoinBalance(@Header("Authorization") token: String): Call<CoinBalanceResponseModel>

    @POST("transaction/")
    fun transaction(
        @Header("Authorization") token: String,
        @Body body: TransactionRequestModel
    ): Call<TransactionResponseModel>

    @GET("transaction/my_transactions")
    fun getAllTransaction(
        @Header("Authorization") token: String,
        @Query("pageNo") pageNo: Int,
        @Query("search_transaction_id") search_transaction_id: String
    ): Call<AllTransactionsResponseModel>

    @POST("withdraw/")
    fun withdraw(@Header("Authorization") token : String,
    @Body body: WithDrawRequestModel): Call<TransactionResponseModel>

    @GET("withdraw/my_requests")
    fun getAllWithDraw(
        @Header("Authorization") token: String,
        @Query("pageNo") pageNo: Int,
        @Query("search_withdraw_request_id") search_withdraw_request_id: String
    ): Call<AllWithDrawResponseModel>
}