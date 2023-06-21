package com.realteenpatti.sanify.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.realteenpatti.sanify.retrofit.RemoteApi
import com.realteenpatti.sanify.retrofit.models.CommonErrorModel
import com.realteenpatti.sanify.retrofit.models.horse.GetSlotDetailsResponseModel
import com.realteenpatti.sanify.retrofit.models.horse.HorseBidRequestModel
import com.realteenpatti.sanify.retrofit.models.horse.HorseWinnerResponseModel
import com.realteenpatti.sanify.retrofit.models.lottery.CountDownTimeResponseModel
import com.realteenpatti.sanify.utils.NetworkUtils
import com.realteenpatti.sanify.utils.StorageUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HorseRaceRepository {
    private var localStorage = StorageUtil.getInstance()

    private val api: RemoteApi = NetworkUtils.getRetrofitInstance().create(RemoteApi::class.java)

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    val errorMessage: MutableLiveData<String> = MutableLiveData()

    val slotDetails: MutableLiveData<GetSlotDetailsResponseModel> = MutableLiveData()
    val winnerDetail: MutableLiveData<HorseWinnerResponseModel> = MutableLiveData()
    val isBidSuccess: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getSlotDetails() {
        isLoading.postValue(true)

        api.getSlotDetails().enqueue(object : Callback<GetSlotDetailsResponseModel> {
            override fun onResponse(
                call: Call<GetSlotDetailsResponseModel>,
                response: Response<GetSlotDetailsResponseModel>
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

            override fun onFailure(call: Call<GetSlotDetailsResponseModel>, t: Throwable) {
                Log.d("Request Failed. Error: ", t.message.toString())
                isLoading.postValue(false)
                errorMessage.postValue("Something went wrong")
            }
        })
    }

   fun horseWinner(){
       isLoading.postValue(true)
       api.getResultDetails("Bearer " + localStorage.token).enqueue(object : Callback<HorseWinnerResponseModel> {
           override fun onResponse(
               call: Call<HorseWinnerResponseModel>,
               response: Response<HorseWinnerResponseModel>
           ) {
               if (response.isSuccessful) {
                   isLoading.postValue(false)
                   errorMessage.postValue("")
                   response.body()?.let {
                       winnerDetail.postValue(it)
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

           override fun onFailure(call: Call<HorseWinnerResponseModel>, t: Throwable) {
               Log.d("Request Failed. Error: ", t.message.toString())
               isLoading.postValue(false)
               errorMessage.postValue("Something went wrong")
           }

       })
   }

    fun horseBid(amount : Int, horseNum : Int){
        isLoading.postValue(true)
        isBidSuccess.postValue(false)
        val body = HorseBidRequestModel(amount, horseNum);
        api.horseBid("Bearer " + localStorage.token, body).enqueue(object : Callback<CommonErrorModel> {
            override fun onResponse(
                call: Call<CommonErrorModel>,
                response: Response<CommonErrorModel>
            ) {
                if (response.isSuccessful){
                    isLoading.postValue(false)
                    errorMessage.postValue("")
                    response.body()?.let {
                        isBidSuccess.postValue(true)
                    }
                }else{
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
}