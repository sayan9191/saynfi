package com.realteenpatti.sanify.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.realteenpatti.sanify.retrofit.RemoteApi
import com.realteenpatti.sanify.retrofit.models.CommonErrorModel
import com.realteenpatti.sanify.retrofit.models.lottery.AllParticipantResponseModel
import com.realteenpatti.sanify.utils.NetworkUtils
import com.realteenpatti.sanify.utils.StorageUtil
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllParticipantRepository {
    private var localStorage = StorageUtil.getInstance()

    private val api: RemoteApi = NetworkUtils.getRetrofitInstance().create(RemoteApi::class.java)

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    val errorMessage: MutableLiveData<String> = MutableLiveData()

    val allParticipantList: MutableLiveData<AllParticipantResponseModel> = MutableLiveData()

    fun getAllParticipant(pageNo: Int, query: String) {
        isLoading.postValue(true)
        api.getAllParticipants("Bearer " + localStorage.token, pageNo, query)
            .enqueue(object : Callback<AllParticipantResponseModel> {
                override fun onResponse(
                    call: Call<AllParticipantResponseModel>,
                    response: Response<AllParticipantResponseModel>
                ) {
                    if (response.isSuccessful) {
                        isLoading.postValue(false)
                        errorMessage.postValue("")
                        response.body()?.let {
                            allParticipantList.postValue(it)
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

                override fun onFailure(call: Call<AllParticipantResponseModel>, t: Throwable) {
                    Log.d("Request Failed. Error: ", t.message.toString())
                    isLoading.postValue(false)
                    errorMessage.postValue("Something went wrong")
                }

            })
    }
}