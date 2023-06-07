package com.example.sanify.retrofit

import com.example.sanify.retrofit.models.auth.CreateUserRequestModel
import com.example.sanify.retrofit.models.auth.CreateUserResponseModel
import com.example.sanify.retrofit.models.changepassword.ChangePasswordRequestModel
import com.example.sanify.retrofit.models.coin.CoinBalanceResponseModel
import com.example.sanify.retrofit.models.coin.UpdateCoinRequestModel
import com.example.sanify.retrofit.models.coin.UpdateCoinResponseModel
import com.example.sanify.retrofit.models.login.LoginResponse
import com.example.sanify.retrofit.models.lottery.*
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

    @POST("coin/")
    fun updateCoin(@Header("Authorization") token: String, @Body body: UpdateCoinRequestModel) : Call<UpdateCoinResponseModel>

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
    fun withdraw(
        @Header("Authorization") token: String,
        @Body body: WithDrawRequestModel
    ): Call<TransactionResponseModel>

    @GET("withdraw/my_requests")
    fun getAllWithDraw(
        @Header("Authorization") token: String,
        @Query("pageNo") pageNo: Int,
        @Query("search_withdraw_request_id") search_withdraw_request_id: String
    ): Call<AllWithDrawResponseModel>


    /**
     * Create User
      */
    @POST("users/")
    fun createNewUser(@Body body : CreateUserRequestModel) : Call<CreateUserResponseModel>

    /**
     * Change Password
     */
    @POST("change_password")
    fun changePassword(@Body body : ChangePasswordRequestModel) : Call<LoginResponse>


    @GET("lottery/participants")
    fun getAllParticipants(
        @Header("Authorization") token: String,
        @Query("pageNo") pageNo: Int, @Query("search") search: String
    ): Call<AllParticipantResponseModel>

    @GET("lottery/get_lottery_prizepool")
    fun getPrizePool(): Call<PrizePoolResponseModel>

    @GET("lottery/get_all_my_entries")
    fun getMyTickets(
        @Header("Authorization") token: String,
        @Query("search") search: String
    ): Call<MyTicketResponseModel>

    @GET("lottery/get_time_left_in_millis")
    fun getTimeLeft(@Header("Authorization") token: String): Call<CountDownTimeResponseModel>

    @GET("lottery/get_all_winners")
    fun getAllWinner(): Call<AllWinnerResponseModel>

    @POST("lottery/buy")
    fun getLotteryBuy(
        @Header("Authorization") token: String
    ):Call<LotteryBuyResponseModel>


}