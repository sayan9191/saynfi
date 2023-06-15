package com.realteenpatti.sanify.ui.transactionhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.realteenpatti.sanify.repo.AllTransactionHistoryRepositry
import com.realteenpatti.sanify.retrofit.models.transaction.AllTransactionsResponseModel

class TransactionHistoryViewModel : ViewModel() {
    private val repo = AllTransactionHistoryRepositry()

    val isLoading: LiveData<Boolean> = repo.isLoading
    val errorMessage: LiveData<String> = repo.errorMessage
    val allTransactionHistory: LiveData<AllTransactionsResponseModel> = repo.allTransactionList

    fun getAllTransactionHistory(pageNo :Int, query:String){
        repo.getAllTransaction(pageNo, query)
    }
}