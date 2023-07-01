package com.realteenpatti.sanify.ui.addmoney

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.realteenpatti.sanify.repo.PaymentRepository
import com.realteenpatti.sanify.retrofit.models.addmoney.PaymentGetResponseModel

class AddMoneyViewModel: ViewModel() {
    private val repo = PaymentRepository()

    val isLoading : LiveData<Boolean> = repo.isLoading
    val errorMessage : LiveData<String> = repo.errorMessage
    val uploadStatus :LiveData<Boolean> = repo.uploadStatus
    val allTransactionMediums : LiveData<PaymentGetResponseModel> = repo.allTransactionMediums

    fun transaction(imageBitmap: Bitmap, transactionId: String, transactionMedium: String, amount: Int){
        repo.uploadImage(imageBitmap, transactionId, transactionMedium, amount)
    }

    fun getTransactionMedium(){
        repo.getTransactionMediums()
    }
}