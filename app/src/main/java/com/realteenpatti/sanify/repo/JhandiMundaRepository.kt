package com.realteenpatti.sanify.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.realteenpatti.sanify.retrofit.RemoteApi
import com.realteenpatti.sanify.retrofit.models.CommonErrorModel
import com.realteenpatti.sanify.retrofit.models.coin.CoinBalanceResponseModel
import com.realteenpatti.sanify.retrofit.models.jhandimunda.GetJMSlotDetailsResponseModel
import com.realteenpatti.sanify.retrofit.models.jhandimunda.JMBidRequestModel
import com.realteenpatti.sanify.retrofit.models.jhandimunda.JMMyBidResponseModel
import com.realteenpatti.sanify.retrofit.models.jhandimunda.JMWinnerResponseModel
import com.realteenpatti.sanify.utils.NetworkUtils
import com.realteenpatti.sanify.utils.StorageUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JhandiMundaRepository {
    private var localStorage = StorageUtil.getInstance()

    private val api: RemoteApi = NetworkUtils.getRetrofitInstance().create(RemoteApi::class.java)
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val currentCoinBalance: MutableLiveData<Int> = MutableLiveData()
    val slotDetails: MutableLiveData<GetJMSlotDetailsResponseModel> = MutableLiveData()
    val isBidSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    val winnerDetail: MutableLiveData<JMWinnerResponseModel> = MutableLiveData()
    val myBidDetails: MutableLiveData<JMMyBidResponseModel> = MutableLiveData()


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


    fun getJMSSlotDetails() {
        isLoading.postValue(true)

        api.getJmSlotDetails().enqueue(object : Callback<GetJMSlotDetailsResponseModel> {
            override fun onResponse(
                call: Call<GetJMSlotDetailsResponseModel>,
                response: Response<GetJMSlotDetailsResponseModel>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        slotDetails.postValue(it)
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

            override fun onFailure(call: Call<GetJMSlotDetailsResponseModel>, t: Throwable) {
                Log.d("Request Failed. Error: ", t.message.toString())
                isLoading.postValue(false)
                errorMessage.postValue("Something went wrong")
            }
        })
    }

    fun jmBid(amount: Int, cardNumber: Int) {
        isLoading.postValue(true)
        isBidSuccess.postValue(false)
        val body = JMBidRequestModel(amount, cardNumber);
        api.jmBid("Bearer " + localStorage.token, body)
            .enqueue(object : Callback<CommonErrorModel> {
                override fun onResponse(
                    call: Call<CommonErrorModel>,
                    response: Response<CommonErrorModel>
                ) {
                    if (response.isSuccessful) {
                        isLoading.postValue(false)
                        errorMessage.postValue("")
                        response.body()?.let {
                            isBidSuccess.postValue(true)
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

                override fun onFailure(call: Call<CommonErrorModel>, t: Throwable) {
                    Log.d("Request Failed. Error: ", t.message.toString())
                    isLoading.postValue(false)
                    errorMessage.postValue("Something went wrong")
                }
            })
    }

    fun jmWinner() {
        isLoading.postValue(true)
        api.getJmResultDetails("Bearer " + localStorage.token)
            .enqueue(object : Callback<JMWinnerResponseModel> {
                override fun onResponse(
                    call: Call<JMWinnerResponseModel>,
                    response: Response<JMWinnerResponseModel>
                ) {
                    if (response.isSuccessful) {
                        isLoading.postValue(false)
                        errorMessage.postValue("")
                        response.body()?.let {
                            winnerDetail.postValue(it)
                            Log.d("JM--------winner------: ", it.win_money.toString());
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

                override fun onFailure(call: Call<JMWinnerResponseModel>, t: Throwable) {
                    Log.d("Request Failed. Error: ", t.message.toString())
                    isLoading.postValue(false)
                    errorMessage.postValue("Something went wrong")
                }
            })
    }

    fun getMyDetails() {
        isLoading.postValue(true)
        api.getMyJMBidDetails("Bearer " + localStorage.token)
            .enqueue(object : Callback<JMMyBidResponseModel> {
                override fun onResponse(
                    call: Call<JMMyBidResponseModel>,
                    response: Response<JMMyBidResponseModel>
                ) {
                    if (response.isSuccessful) {
                        isLoading.postValue(false)
                        errorMessage.postValue("")
                        response.body()?.let {
                            myBidDetails.postValue(it)
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

                override fun onFailure(call: Call<JMMyBidResponseModel>, t: Throwable) {
                    Log.d("Request Failed. Error: ", t.message.toString())
                    isLoading.postValue(false)
                    errorMessage.postValue("Something went wrong")
                }
            })
    }
}