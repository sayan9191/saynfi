package com.example.sanify.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sanify.retrofit.RemoteApi
import com.example.sanify.retrofit.models.CommonErrorModel
import com.example.sanify.retrofit.models.login.LoginResponse
import com.example.sanify.utils.NetworkUtils
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Repository {
    private val api: RemoteApi = NetworkUtils.getRetrofitInstance().create(RemoteApi::class.java)

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    val errorMessage: MutableLiveData<String> = MutableLiveData()

    val loginToken: MutableLiveData<String> = MutableLiveData("")


    fun login(username: String, password: String) {
        isLoading.postValue(true)

        api.login(username, password).enqueue(object : Callback<LoginResponse> {

            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {

                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        isLoading.postValue(false)
                        errorMessage.postValue("")
                        loginToken.postValue(loginResponse.access_token)
                        Log.d("Login: ", "success")
                    }
                } else {
                    isLoading.postValue(false)
                    response.errorBody()?.let { errorBody ->
                        errorBody.string().let {
                            val errorResponse: CommonErrorModel =
                                Gson().fromJson(it, CommonErrorModel::class.java)
                            errorMessage.postValue(errorResponse.detail)
                            Log.d("Login error: ", it)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("Request Failed. Error: ", t.message.toString())
                isLoading.postValue(false)
                errorMessage.postValue("Something went wrong")
            }

        })
    }

}