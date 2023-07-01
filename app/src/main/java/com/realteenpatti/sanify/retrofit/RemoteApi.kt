package com.realteenpatti.sanify.retrofit

import com.example.adminpannel.Retrofit.model.spinner.AllSpinnerCoinResponseModel
import com.realteenpatti.sanify.retrofit.models.CommonErrorModel
import com.realteenpatti.sanify.retrofit.models.UserInfoResponseModel
import com.realteenpatti.sanify.retrofit.models.addmoney.PaymentGetResponseModel
import com.realteenpatti.sanify.retrofit.models.auth.CreateUserRequestModel
import com.realteenpatti.sanify.retrofit.models.auth.CreateUserResponseModel
import com.realteenpatti.sanify.retrofit.models.changepassword.ChangePasswordRequestModel
import com.realteenpatti.sanify.retrofit.models.coin.CoinBalanceResponseModel
import com.realteenpatti.sanify.retrofit.models.coin.UpdateCoinRequestModel
import com.realteenpatti.sanify.retrofit.models.coin.UpdateCoinResponseModel
import com.realteenpatti.sanify.retrofit.models.horse.GetSlotDetailsResponseModel
import com.realteenpatti.sanify.retrofit.models.horse.HorseBidRequestModel
import com.realteenpatti.sanify.retrofit.models.horse.HorseMyBidResponseModel
import com.realteenpatti.sanify.retrofit.models.horse.HorseWinnerResponseModel
import com.realteenpatti.sanify.retrofit.models.login.LoginResponse
import com.realteenpatti.sanify.retrofit.models.lottery.*
import com.realteenpatti.sanify.retrofit.models.refer.ReferResponseModel

import com.realteenpatti.sanify.retrofit.models.transaction.*
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
        @Header("Authorization") token: String, @Query("page_no") pageNo: Int,
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
    fun buyLottery(
        @Header("Authorization") token: String, @Body body: BuyLotteryRequestModel
    ): Call<BuyLotteryResponseModel>


    @GET("users/")
    fun getUserInfo(@Header("Authorization") token: String): Call<UserInfoResponseModel>

    @GET("horse/get_slot_details")
    fun getSlotDetails(): Call<GetSlotDetailsResponseModel>

    @POST("horse/bid")
    fun horseBid(
        @Header("Authorization") token: String, @Body body: HorseBidRequestModel
    ): Call<CommonErrorModel>

    @GET("horse/get_result_details")
    fun getResultDetails(@Header("Authorization") token: String): Call<HorseWinnerResponseModel>

    @GET("horse/get_my_bids")
    fun getMyBidDetails(@Header("Authorization") token: String): Call<HorseMyBidResponseModel>

    @GET("reffer/get_my_refferals")
    fun getMyRefer(@Header("Authorization") token: String): Call<ReferResponseModel>

    @GET("lucky_draw/get_all_coin_values")
    fun getAllCoinInfo(): Call<AllSpinnerCoinResponseModel>

    @GET("transaction/get_all_transaction_mediums")
    fun getAllTransactions(): Call<PaymentGetResponseModel>


}