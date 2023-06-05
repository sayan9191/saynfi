package com.example.sanify.ui.withdraw

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sanify.repo.WithDrawRepository

class WithDrawViewModel : ViewModel() {
    private val repo = WithDrawRepository()
    val isLoading: LiveData<Boolean> = repo.isLoading
    val errorMessage: LiveData<String> = repo.errorMessage
    val uploadStatus: LiveData<Boolean> = repo.uploadStatus

    fun withDraw(phoneNo: String, transactionMedium: String, amount: Int){
        repo.withDraw(phoneNo, transactionMedium, amount)
    }

}