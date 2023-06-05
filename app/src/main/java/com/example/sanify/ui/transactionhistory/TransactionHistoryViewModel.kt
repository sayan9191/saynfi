package com.example.sanify.ui.transactionhistory

import android.app.DownloadManager.Query
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sanify.repo.AllTransactionHistoryRepositry
import com.example.sanify.retrofit.models.transaction.AllTransactionsResponseModel

class TransactionHistoryViewModel : ViewModel() {
    private val repo = AllTransactionHistoryRepositry()

    val isLoading: LiveData<Boolean> = repo.isLoading
    val errorMessage: LiveData<String> = repo.errorMessage
    val allTransactionHistory: LiveData<AllTransactionsResponseModel> = repo.allTransactionList

    fun getAllTransactionHistory(pageNo :Int, query:String){
        repo.getAllTransaction(pageNo, query)
    }
}