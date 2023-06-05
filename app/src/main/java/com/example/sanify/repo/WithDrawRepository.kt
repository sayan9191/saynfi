package com.example.sanify.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sanify.retrofit.RemoteApi
import com.example.sanify.retrofit.models.CommonErrorModel
import com.example.sanify.retrofit.models.transaction.TransactionResponseModel
import com.example.sanify.retrofit.models.transaction.WithDrawRequestModel
import com.example.sanify.utils.NetworkUtils
import com.example.sanify.utils.StorageUtil
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WithDrawRepository {
    private var localStorage = StorageUtil.getInstance()

    private val api: RemoteApi = NetworkUtils.getRetrofitInstance().create(RemoteApi::class.java)

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val uploadStatus: MutableLiveData<Boolean> = MutableLiveData(false)


    fun withDraw(phoneNo: String, transactionMedium: String, amount: Int) {
        val body = WithDrawRequestModel(amount, phoneNo, transactionMedium)
        isLoading.postValue(true)
        api.withdraw("Bearer " + localStorage.token, body).enqueue(object :
            Callback<TransactionResponseModel> {
            override fun onResponse(
                call: Call<TransactionResponseModel>,
                response: Response<TransactionResponseModel>
            ) {
                if (response.isSuccessful) {
                    errorMessage.postValue("")
                    isLoading.postValue(false)
                    uploadStatus.postValue(true)
                } else {
                    uploadStatus.postValue(false)
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

            override fun onFailure(call: Call<TransactionResponseModel>, t: Throwable) {
                isLoading.postValue(false)
                uploadStatus.postValue(false)
                errorMessage.postValue("Something went wrong")
                t.stackTrace
            }
        })
    }

}