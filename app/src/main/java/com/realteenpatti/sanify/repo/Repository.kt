package com.realteenpatti.sanify.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.realteenpatti.sanify.retrofit.RemoteApi
import com.realteenpatti.sanify.retrofit.models.CommonErrorModel
import com.realteenpatti.sanify.retrofit.models.auth.CreateUserRequestModel
import com.realteenpatti.sanify.retrofit.models.auth.CreateUserResponseModel
import com.realteenpatti.sanify.retrofit.models.changepassword.ChangePasswordRequestModel
import com.realteenpatti.sanify.retrofit.models.login.LoginResponse
import com.realteenpatti.sanify.utils.NetworkUtils
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

    fun createUser(name : String, password : String, phoneNumber : String, countryCode : String, referral : String){
        isLoading.postValue(true)
        val body = CreateUserRequestModel(name, password, countryCode + phoneNumber, referral)
        api.createNewUser(body).enqueue(object : Callback<CreateUserResponseModel>{
            override fun onResponse(
                call: Call<CreateUserResponseModel>,
                response: Response<CreateUserResponseModel>
            ) {
                if (response.isSuccessful) {

                    val createUserResponse = response.body()
                    if (createUserResponse != null) {
                        isLoading.postValue(false)
                        errorMessage.postValue("")
                        login(phoneNumber, password)
                        Log.d("Sign Up: ", "success")
                    }
                } else {
                    isLoading.postValue(false)
                    response.errorBody()?.let { errorBody ->
                        errorBody.string().let {
                            val errorResponse: CommonErrorModel =
                                Gson().fromJson(it, CommonErrorModel::class.java)
                            errorMessage.postValue(errorResponse.detail)
                            Log.d("Sign Up error: ", it)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<CreateUserResponseModel>, t: Throwable) {
                Log.d("Request Failed. Error: ", t.message.toString())
                isLoading.postValue(false)
                errorMessage.postValue("Something went wrong")
            }

        })
    }

    fun changePassword(newPass : String, phoneNumber : String) {
        isLoading.postValue(true)
        val body = ChangePasswordRequestModel(newPass, phoneNumber)
        api.changePassword(body).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {

                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        isLoading.postValue(false)
                        errorMessage.postValue("")
                        loginToken.postValue(loginResponse.access_token)
                        Log.d("ChangePassword: ", "success")
                    }
                } else {
                    isLoading.postValue(false)
                    response.errorBody()?.let { errorBody ->
                        errorBody.string().let {
                            val errorResponse: CommonErrorModel =
                                Gson().fromJson(it, CommonErrorModel::class.java)
                            errorMessage.postValue(errorResponse.detail)
                            Log.d("ChangePassword error: ", it)
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