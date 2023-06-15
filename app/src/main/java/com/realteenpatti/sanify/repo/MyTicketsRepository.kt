package com.realteenpatti.sanify.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.realteenpatti.sanify.retrofit.RemoteApi
import com.realteenpatti.sanify.retrofit.models.CommonErrorModel
import com.realteenpatti.sanify.retrofit.models.lottery.MyTicketResponseModel
import com.realteenpatti.sanify.utils.NetworkUtils
import com.realteenpatti.sanify.utils.StorageUtil
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyTicketsRepository {
    private var localStorage = StorageUtil.getInstance()

    private val api: RemoteApi = NetworkUtils.getRetrofitInstance().create(RemoteApi::class.java)

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    val errorMessage: MutableLiveData<String> = MutableLiveData()

    val allMyTicketList: MutableLiveData<MyTicketResponseModel> = MutableLiveData()

    fun getMyAllTicket(query: String) {
        isLoading.postValue(true)
        api.getMyTickets("Bearer " + localStorage.token, query)
            .enqueue(object : Callback<MyTicketResponseModel> {
                override fun onResponse(
                    call: Call<MyTicketResponseModel>,
                    response: Response<MyTicketResponseModel>
                ) {
                    if (response.isSuccessful) {
                        isLoading.postValue(false)
                        errorMessage.postValue("")
                        response.body()?.let {
                            allMyTicketList.postValue(it)
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

                override fun onFailure(call: Call<MyTicketResponseModel>, t: Throwable) {
                    Log.d("Request Failed. Error: ", t.message.toString())
                    isLoading.postValue(false)
                    errorMessage.postValue("Something went wrong")
                }

            })
    }

}