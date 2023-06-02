package com.example.sanify.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sanify.retrofit.RemoteApi
import com.example.sanify.retrofit.models.CommonErrorModel
import com.example.sanify.retrofit.models.coin.CoinBalanceResponseModel
import com.example.sanify.utils.NetworkUtils
import com.example.sanify.utils.StorageUtil
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoinRepository private constructor(){

    companion object{
        private var instance : CoinRepository? = null

        fun getInstance() : CoinRepository {
            return if (instance != null)
                instance as CoinRepository
            else{
                instance = CoinRepository()
                instance as CoinRepository
            }
        }
    }

    private var localStorage = StorageUtil.getInstance()

    private val api: RemoteApi = NetworkUtils.getRetrofitInstance().create(RemoteApi::class.java)

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    val errorMessage: MutableLiveData<String> = MutableLiveData()


    val currentCoinBalance: MutableLiveData<Int> = MutableLiveData()


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