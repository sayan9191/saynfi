package com.example.sanify.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sanify.retrofit.RemoteApi
import com.example.sanify.retrofit.models.CommonErrorModel
import com.example.sanify.retrofit.models.coin.CoinBalanceResponseModel
import com.example.sanify.retrofit.models.coin.UpdateCoinRequestModel
import com.example.sanify.retrofit.models.coin.UpdateCoinResponseModel
import com.example.sanify.utils.NetworkUtils
import com.example.sanify.utils.StorageUtil
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CoinRepository {

    /*companion object{
        private var instance : CoinRepository? = null

        fun getInstance() : CoinRepository {
            return if (instance != null)
                instance as CoinRepository
            else{
                instance = CoinRepository()
                instance as CoinRepository
            }
        }
    }*/

    private var localStorage = StorageUtil.getInstance()

    private val api: RemoteApi = NetworkUtils.getRetrofitInstance().create(RemoteApi::class.java)

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    val errorMessage: MutableLiveData<String> = MutableLiveData()

    val currentCoinBalance: MutableLiveData<Int> = MutableLiveData()

    val isCoinAdded : MutableLiveData<Boolean> = MutableLiveData()

    val isCoinDeducted : MutableLiveData<Boolean> = MutableLiveData()


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

    fun addCoin(amount : Int){
        isLoading.postValue(true)

        val body = UpdateCoinRequestModel(amount, "add")
        api.updateCoin("Bearer " + localStorage.token, body).enqueue(object : Callback<UpdateCoinResponseModel>{
            override fun onResponse(
                call: Call<UpdateCoinResponseModel>,
                response: Response<UpdateCoinResponseModel>
            ) {
                if (response.isSuccessful) {
                    isLoading.postValue(false)
                    errorMessage.postValue("")
                    isCoinAdded.postValue(true)
                    response.body()?.let{
                        currentCoinBalance.postValue(it.num_of_coins)
                    }
                } else {
                    isLoading.postValue(false)
                    isCoinAdded.postValue(false)
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

            override fun onFailure(call: Call<UpdateCoinResponseModel>, t: Throwable) {
                Log.d("Request Failed. Error: ", t.message.toString())
                isLoading.postValue(false)
                isCoinAdded.postValue(false)
                errorMessage.postValue("Something went wrong")
            }

        })
    }


    fun deductCoin(amount : Int){
        isLoading.postValue(true)

        val body = UpdateCoinRequestModel(amount, "remove")
        api.updateCoin("Bearer " + localStorage.token, body).enqueue(object : Callback<UpdateCoinResponseModel>{
            override fun onResponse(
                call: Call<UpdateCoinResponseModel>,
                response: Response<UpdateCoinResponseModel>
            ) {
                if (response.isSuccessful) {
                    isLoading.postValue(false)
                    isCoinDeducted.postValue(true)
                    errorMessage.postValue("")
                    response.body()?.let{
                        currentCoinBalance.postValue(it.num_of_coins)
                    }
                } else {
                    isLoading.postValue(false)
                    isCoinDeducted.postValue(false)
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

            override fun onFailure(call: Call<UpdateCoinResponseModel>, t: Throwable) {
                Log.d("Request Failed. Error: ", t.message.toString())
                isLoading.postValue(false)
                isCoinDeducted.postValue(false)
                errorMessage.postValue("Something went wrong")
            }

        })
    }

}