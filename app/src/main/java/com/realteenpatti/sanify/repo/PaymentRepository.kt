package com.realteenpatti.sanify.repo

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.gson.Gson
import com.realteenpatti.sanify.retrofit.RemoteApi
import com.realteenpatti.sanify.retrofit.models.CommonErrorModel
import com.realteenpatti.sanify.retrofit.models.addmoney.PaymentGetResponseModel
import com.realteenpatti.sanify.retrofit.models.transaction.TransactionRequestModel
import com.realteenpatti.sanify.retrofit.models.transaction.TransactionResponseModel
import com.realteenpatti.sanify.utils.ImageResizer
import com.realteenpatti.sanify.utils.NetworkUtils
import com.realteenpatti.sanify.utils.StorageUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*


class PaymentRepository {
    private var localStorage = StorageUtil.getInstance()

    private val api: RemoteApi = NetworkUtils.getRetrofitInstance().create(RemoteApi::class.java)

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    val errorMessage: MutableLiveData<String> = MutableLiveData()

    val uploadStatus: MutableLiveData<Boolean> = MutableLiveData(false)

    val getPaymentInfo: MutableLiveData<PaymentGetResponseModel> = MutableLiveData()



    fun uploadImage(imageBitmap: Bitmap, transactionId: String, transactionMedium: String, amount: Int) {
        isLoading.postValue(true)

        val simpleDateFormat = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA)
        val now = Date()
        val fileName = simpleDateFormat.format(now)
        val storageRef = FirebaseStorage.getInstance("gs://real-teenpatti-79bfb.appspot.com").getReference("transaction_images/$fileName")
        storageRef.putBytes(bitmapToByteArray(ImageResizer.reduceBitmapSize(imageBitmap, 650000)))
            .addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot?> {
                storageRef.downloadUrl.addOnSuccessListener {
                    if (it != null){
                        transact(it.toString(), transactionId, transactionMedium, amount)
                    }
                }
            }).addOnFailureListener(OnFailureListener {
                uploadStatus.postValue(false)
                errorMessage.postValue("Something went wrong")
                isLoading.postValue(false)
            })
    }


    private fun transact(
        screenshotUrl: String,
        transactionId: String,
        transactionMedium: String,
        amount: Int
    ) {
        val body =
            TransactionRequestModel(amount, true, screenshotUrl, transactionMedium, transactionId)

        api.transaction("Bearer " + localStorage.token, body).enqueue(object : Callback<TransactionResponseModel> {
            override fun onResponse(call: Call<TransactionResponseModel>, response: Response<TransactionResponseModel>) {

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

    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
//            val newBitmap = ImageResizer.generateThumb(bitmap, imageSize)
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)

        return stream.toByteArray()
    }

    fun getPayment() {
        isLoading.postValue(true)
        api.getAllTransactions()
            .enqueue(object : Callback<PaymentGetResponseModel> {
                override fun onResponse(
                    call: Call<PaymentGetResponseModel>,
                    response: Response<PaymentGetResponseModel>
                ) {
                    if (response.isSuccessful) {
                        isLoading.postValue(false)
                        errorMessage.postValue("")
                        response.body()?.let {
                            getPaymentInfo.postValue(it)
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

                override fun onFailure(call: Call<PaymentGetResponseModel>, t: Throwable) {
                    Log.d("Request Failed. Error: ", t.message.toString())
                    isLoading.postValue(false)
                    errorMessage.postValue("Something went wrong")
                }

            })
    }

}