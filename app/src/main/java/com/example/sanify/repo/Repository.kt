package com.example.sanify.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sanify.retrofit.RemoteApi
import com.example.sanify.retrofit.models.Items
import com.example.sanify.retrofit.models.LoginResponseModel
import com.example.sanify.retrofit.models.login.LoginRequest
import com.example.sanify.retrofit.models.login.LoginResponse
import com.example.sanify.utils.NetworkUtils
import com.example.sanify.utils.StorageUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Repository {

    private val storage = StorageUtil()
    private val api : RemoteApi = NetworkUtils.getRetrofitInstance().create(RemoteApi::class.java)

    val isLoading : MutableLiveData<Boolean> = MutableLiveData(false)

    val errorMessage : MutableLiveData<String> = MutableLiveData()

    val isLoginSuccess : MutableLiveData<Boolean> = MutableLiveData(false)


    fun login(username : String, password : String){
        isLoading.postValue(true)

        val loginRequest = LoginRequest(username, password);

        api.login(loginRequest).enqueue(object : Callback<Any> {

            override fun onResponse(
                call: Call<Any>,
                response: Response<Any>
            ) {
                if (response.isSuccessful){
                    if (response.code() == 200){
                        val loginResponse = response.body();
                        if (loginResponse != null){
                            storage.token = (loginResponse as LoginResponse).access_token
                            isLoading.postValue(false)
                            errorMessage.postValue("")
                            isLoginSuccess.postValue(true)
                            Log.d("________________", response.toString())
                        }
                    }else{
                        Log.d("Error", response.message())
                        errorMessage.postValue("Something went wrong")
                        Log.d("________________", response.toString())

                    }
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("Error", t.message.toString())
                isLoading.postValue(false)
                errorMessage.postValue("Something went wrong")
            }

        })
    }

}