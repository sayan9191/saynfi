package com.example.sanify.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sanify.retrofit.RemoteApi
import com.example.sanify.retrofit.models.CommonErrorModel
import com.example.sanify.retrofit.models.coin.CoinBalanceResponseModel
import com.example.sanify.retrofit.models.lottery.CountDownTimeResponseModel
import com.example.sanify.utils.NetworkUtils
import com.example.sanify.utils.StorageUtil
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountDownRepository {
    private var localStorage = StorageUtil.getInstance()
    private val api: RemoteApi = NetworkUtils.getRetrofitInstance().create(RemoteApi::class.java)
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val countDownTimerList: MutableLiveData<CountDownTimeResponseModel> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val currentCoinBalance: MutableLiveData<Int> = MutableLiveData()

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


    fun getCoinBalance(){
        isLoading.postValue(true)
        api.getCoinBalance("Bearer " + localStorage.token).enqueue(object : Callback<CoinBalanceResponseModel>{
            override fun onResponse(
                call: Call<CoinBalanceResponseModel>,
                response: Response<CoinBalanceResponseModel>
            ) {
                if (response.isSuccessful){
                    val coinBalanceData = response.body()
                    if (coinBalanceData != null){
                        currentCoinBalance.postValue(coinBalanceData.num_of_coins)
                        isLoading.postValue(false)
                    }
                }else{
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

}