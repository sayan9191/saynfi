package com.realteenpatti.sanify.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.adminpannel.Retrofit.model.spinner.AllSpinnerCoinResponseModel
import com.realteenpatti.sanify.retrofit.RemoteApi
import com.realteenpatti.sanify.retrofit.models.CommonErrorModel
import com.realteenpatti.sanify.retrofit.models.UserInfoResponseModel
import com.realteenpatti.sanify.retrofit.models.coin.CoinBalanceResponseModel
import com.realteenpatti.sanify.retrofit.models.coin.UpdateCoinRequestModel
import com.realteenpatti.sanify.retrofit.models.coin.UpdateCoinResponseModel
import com.realteenpatti.sanify.utils.NetworkUtils
import com.realteenpatti.sanify.utils.StorageUtil
import com.google.gson.Gson
import com.realteenpatti.sanify.retrofit.models.spinner.DashBoardNoticeGetResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CoinRepository {

    private var localStorage = StorageUtil.getInstance()

    private val api: RemoteApi = NetworkUtils.getRetrofitInstance().create(RemoteApi::class.java)

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    val errorMessage: MutableLiveData<String> = MutableLiveData()

    val currentCoinBalance: MutableLiveData<Int> = MutableLiveData()

    val isCoinAdded : MutableLiveData<Boolean> = MutableLiveData()

    val isCoinDeducted : MutableLiveData<Boolean> = MutableLiveData()

    val userInfo : MutableLiveData<UserInfoResponseModel> = MutableLiveData()

    val allSpinnerCoins: MutableLiveData<AllSpinnerCoinResponseModel> = MutableLiveData()

    val noticeDashBoardMessage: MutableLiveData<DashBoardNoticeGetResponseModel> = MutableLiveData()



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

    fun getUserInfo(){
        isLoading.postValue(true)
        api.getUserInfo("Bearer " + localStorage.token).enqueue(object : Callback<UserInfoResponseModel>{
            override fun onResponse(
                call: Call<UserInfoResponseModel>,
                response: Response<UserInfoResponseModel>
            ) {
                if (response.isSuccessful) {
                    isLoading.postValue(false)
                    errorMessage.postValue("")
                    response.body()?.let{
                        userInfo.postValue(it)
                    }
                } else {
                    isLoading.postValue(false)
                    isCoinDeducted.postValue(false)
                    try {
                        response.errorBody()?.let { errorBody ->
                            errorBody.string().let {
                                Log.e("Error: ", it)
                                val errorResponse: CommonErrorModel =
                                    Gson().fromJson(it, CommonErrorModel::class.java)
                                errorMessage.postValue(errorResponse.detail)
                            }
                        }
                    } catch (e: Exception){
                        throw e
                    }

                }
            }

            override fun onFailure(call: Call<UserInfoResponseModel>, t: Throwable) {
                Log.d("Request Failed. Error: ", t.message.toString())
                isLoading.postValue(false)
                isCoinDeducted.postValue(false)
                errorMessage.postValue("Something went wrong")
            }

        }
        )
    }

    fun getAllCoin() {
        isLoading.postValue(true)
        api.getAllCoinInfo()
            .enqueue(object : Callback<AllSpinnerCoinResponseModel> {
                override fun onResponse(
                    call: Call<AllSpinnerCoinResponseModel>,
                    response: Response<AllSpinnerCoinResponseModel>
                ) {
                    if (response.isSuccessful) {
                        isLoading.postValue(false)
                        errorMessage.postValue("")
                        response.body()?.let {
                            allSpinnerCoins.postValue(it)
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

                override fun onFailure(call: Call<AllSpinnerCoinResponseModel>, t: Throwable) {
                    Log.d("Request Failed. Error: ", t.message.toString())
                    isLoading.postValue(false)
                    errorMessage.postValue("Something went wrong")
                }

            })
    }

    fun getNoticeDashBoardMessage() {
        isLoading.postValue(true)
        api.getDashBoardNotice()
            .enqueue(object : Callback<DashBoardNoticeGetResponseModel> {
                override fun onResponse(
                    call: Call<DashBoardNoticeGetResponseModel>,
                    response: Response<DashBoardNoticeGetResponseModel>
                ) {
                    if (response.isSuccessful) {
                        isLoading.postValue(false)
                        errorMessage.postValue("")
                        response.body()?.let {
                            noticeDashBoardMessage.postValue(it)
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

                override fun onFailure(call: Call<DashBoardNoticeGetResponseModel>, t: Throwable) {
                    Log.d("Request Failed. Error: ", t.message.toString())
                    isLoading.postValue(false)
                    errorMessage.postValue("Something went wrong")
                }

            })
    }

}