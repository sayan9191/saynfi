package com.realteenpatti.sanify.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.realteenpatti.sanify.retrofit.RemoteApi
import com.realteenpatti.sanify.retrofit.models.CommonErrorModel
import com.realteenpatti.sanify.retrofit.models.coin.CoinBalanceResponseModel
import com.realteenpatti.sanify.retrofit.models.lottery.BuyLotteryRequestModel
import com.realteenpatti.sanify.retrofit.models.lottery.BuyLotteryResponseModel
import com.realteenpatti.sanify.retrofit.models.lottery.CountDownTimeResponseModel
import com.realteenpatti.sanify.utils.NetworkUtils
import com.realteenpatti.sanify.utils.StorageUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CountDownRepository {
    private var localStorage = StorageUtil.getInstance()
    private val api: RemoteApi = NetworkUtils.getRetrofitInstance().create(RemoteApi::class.java)
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val countDownTimerList: MutableLiveData<CountDownTimeResponseModel> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val currentCoinBalance: MutableLiveData<Int> = MutableLiveData()
//    val lotteryBuy: MutableLiveData<LotteryBuyResponseModel> = MutableLiveData()
    val isBuySuccess : MutableLiveData<Boolean> = MutableLiveData()

    fun getCountDownTime() {
        isLoading.postValue(true)
        api.getTimeLeft("Bearer " + localStorage.token).enqueue(object : Callback<CountDownTimeResponseModel> {
            override fun onResponse(
                call: Call<CountDownTimeResponseModel>,
                response: Response<CountDownTimeResponseModel>
            ) {
                if (response.isSuccessful) {
                    isLoading.postValue(false)
                    errorMessage.postValue("")
                    response.body()?.let {
                        countDownTimerList.postValue(it)
                    }
                } else {
                    isLoading.postValue(false)
                    response.errorBody()?.let { errorBody ->
                        errorBody.string().let {
                            Log.e("Error: ", it)
                            val errorResponse: CommonErrorModel =
                                Gson().fromJson(it, CommonErrorModel::class.java)
                            errorMessage.postValue(errorResponse.detail)

                            Log.e("Error: ", errorResponse.detail)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<CountDownTimeResponseModel>, t: Throwable) {
                Log.d("Request Failed. Error: ", t.message.toString())
                isLoading.postValue(false)
                errorMessage.postValue("Something went wrong")
            }

        })
    }


    fun getCoinBalance() {
        isLoading.postValue(true)
        api.getCoinBalance("Bearer " + localStorage.token)
            .enqueue(object : Callback<CoinBalanceResponseModel> {
                override fun onResponse(
                    call: Call<CoinBalanceResponseModel>,
                    response: Response<CoinBalanceResponseModel>
                ) {
                    if (response.isSuccessful) {
                        val coinBalanceData = response.body()
                        if (coinBalanceData != null) {
                            currentCoinBalance.postValue(coinBalanceData.num_of_coins)
                            isLoading.postValue(false)
                        }
                    } else {
                        isLoading.postValue(false)
                        response.errorBody()?.let { errorBody ->
                            errorBody.string().let {
                                Log.e("Error: ", it)
                            val errorResponse: CommonErrorModel =
                                Gson().fromJson(it, CommonErrorModel::class.java)
                            errorMessage.postValue(errorResponse.detail)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<CoinBalanceResponseModel>, t: Throwable) {
                Log.d("Request Failed. Error: ", t.message.toString())
                isLoading.postValue(false)
                errorMessage.postValue("Something went wrong")
            }

        })
    }

    fun buyLottery() {
        isBuySuccess.postValue(false)
        isLoading.postValue(true)

        val tz = TimeZone.getDefault()
        val now = Date()
        val offsetFromUtc = tz.getOffset(now.time).toLong()

        val body = BuyLotteryRequestModel(amount = 20, offsetFromUtc)
        api.buyLottery("Bearer " + localStorage.token, body).enqueue(object : Callback<BuyLotteryResponseModel>{
            override fun onResponse(
                call: Call<BuyLotteryResponseModel>,
                response: Response<BuyLotteryResponseModel>
            ) {
                if (response.isSuccessful) {
                    val coinBalanceData = response.body()
                    if (coinBalanceData != null) {
                        isLoading.postValue(false)
                        isBuySuccess.postValue(true)
                    }
                } else {
                    isLoading.postValue(false)
                    response.errorBody()?.let { errorBody ->
                        errorBody.string().let {
                            Log.e("Error: ", it)
                            val errorResponse: CommonErrorModel =
                                Gson().fromJson(it, CommonErrorModel::class.java)
                            errorMessage.postValue(errorResponse.detail)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<BuyLotteryResponseModel>, t: Throwable) {
                Log.d("Request Failed. Error: ", t.message.toString())
                isLoading.postValue(false)
                errorMessage.postValue("Something went wrong")
            }
        }
        )
    }

}