package com.example.sanify.ui.addmoney

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sanify.repo.PaymentRepository
import com.example.sanify.repo.Repository

class AddMoneyViewModel: ViewModel() {
    private val repo = PaymentRepository()

    val isLoading : LiveData<Boolean> = repo.isLoading
    val errorMessage : LiveData<String> = repo.errorMessage
    val uploadStatus :LiveData<Boolean> = repo.uploadStatus

    fun transaction(imageUri: Uri, transactionId: String, transactionMedium: String, amount: Int){
        repo.uploadImage(imageUri, transactionId, transactionMedium, amount)
    }

}