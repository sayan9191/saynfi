package com.realteenpatti.sanify.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.realteenpatti.sanify.retrofit.RemoteApi
import com.realteenpatti.sanify.retrofit.models.CommonErrorModel
import com.realteenpatti.sanify.utils.NetworkUtils
import com.realteenpatti.sanify.utils.StorageUtil
import com.google.gson.Gson
import com.realteenpatti.sanify.retrofit.models.lottery.AllWinnerResponseModel
import com.realteenpatti.sanify.retrofit.models.lottery.CountDownTimeResponseModel
import com.realteenpatti.sanify.retrofit.models.lottery.PrizePoolResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllWinnerRepository {
    private var localStorage = StorageUtil.getInstance()

    private val api: RemoteApi = NetworkUtils.getRetrofitInstance().create(RemoteApi::class.java)

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    val errorMessage: MutableLiveData<String> = MutableLiveData()

    val allWinnerList: MutableLiveData<AllWinnerResponseModel> = MutableLiveData()

    val lotterySlotDetails: MutableLiveData<CountDownTimeResponseModel> = MutableLiveData()

    val allPrizePoolList: MutableLiveData<PrizePoolResponseModel> = MutableLiveData()



    fun getAllWinner() {
        isLoading.postValue(true)
        api.getAllWinner()
            .enqueue(object : Callback<AllWinnerResponseModel> {
                override fun onResponse(
                    call: Call<AllWinnerResponseModel>,
                    response: Response<AllWinnerResponseModel>
                ) {
                    if (response.isSuccessful) {
                        isLoading.postValue(false)
                        errorMessage.postValue("")
                        Log.d("_________________", response.body().toString())
                        response.body().let {
                            allWinnerList.postValue(it)
                            Log.d("_________________", it.toString())

                        }
                        Log.d("_________________", response.body().toString())
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

                override fun onFailure(call: Call<AllWinnerResponseModel>, t: Throwable) {
                    Log.d("Request Failed. Error: ", t.message.toString())
                    isLoading.postValue(false)
                    errorMessage.postValue("Something went wrong")
                }

            })
    }


    fun getSlotDetails() {
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
                        lotterySlotDetails.postValue(it)
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

    fun getPrizePool() {
        isLoading.postValue(true)
        api.getPrizePool()
            .enqueue(object : Callback<PrizePoolResponseModel> {
                override fun onResponse(
                    call: Call<PrizePoolResponseModel>,
                    response: Response<PrizePoolResponseModel>
                ) {
                    if (response.isSuccessful) {
                        isLoading.postValue(false)
                        errorMessage.postValue("")
                        response.body()?.let {
                            allPrizePoolList.postValue(it)
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

                override fun onFailure(call: Call<PrizePoolResponseModel>, t: Throwable) {
                    Log.d("Request Failed. Error: ", t.message.toString())
                    isLoading.postValue(false)
                    errorMessage.postValue("Something went wrong")
                }

            })
    }

}