package com.realteenpatti.sanify.ui.addmoney

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.realteenpatti.sanify.repo.PaymentRepository

class AddMoneyViewModel: ViewModel() {
    private val repo = PaymentRepository()

    val isLoading : LiveData<Boolean> = repo.isLoading
    val errorMessage : LiveData<String> = repo.errorMessage
    val uploadStatus :LiveData<Boolean> = repo.uploadStatus

    fun transaction(imageUri: Uri, transactionId: String, transactionMedium: String, amount: Int){
        repo.uploadImage(imageUri, transactionId, transactionMedium, amount)
    }

}