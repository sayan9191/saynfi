package com.example.sanify.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sanify.retrofit.RemoteApi
import com.example.sanify.retrofit.models.Items
import com.example.sanify.retrofit.models.LoginResponseModel
import com.example.sanify.utils.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    private val api : RemoteApi = NetworkUtils.getRetrofitInstance().create(RemoteApi::class.java)


    val allItems : MutableLiveData<String> = MutableLiveData()

    /*fun getAllItems(){
        api.login("sa", "").enqueue(object : Callback<LoginResponseModel>{
            override fun onResponse(
                call: Call<LoginResponseModel>,
                response: Response<LoginResponseModel>
            ) {
                if (response.isSuccessful){
                    if (response.code() == 200){
                        val loginData : LoginResponseModel? = response.body()
                        if (loginData!= null){
                            "Bearer ${loginData.access_token}"
                        }
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        api.getItems("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjo5LCJleHAiOjE2ODM0ODEwODd9.L3xvsuZiBwSs3cJu9gXXgfnvmte2odheT6Ulk0r2xL8").enqueue(object: Callback<Items>{
            override fun onResponse(call: Call<Items>, response: Response<Items>) {
                if (response.isSuccessful){
                    Log.d("network is working", response.body().toString())
                    allItems.postValue(response.body().toString())
                }
            }

            override fun onFailure(call: Call<Items>, t: Throwable) {
                Log.d("network is not working", t.toString())
            }

        })
    }*/
}