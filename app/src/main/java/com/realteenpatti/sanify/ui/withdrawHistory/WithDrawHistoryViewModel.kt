package com.realteenpatti.sanify.ui.withdrawHistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.realteenpatti.sanify.repo.AllWithDrawHistoryRepository
import com.realteenpatti.sanify.retrofit.models.transaction.AllWithDrawResponseModel

class WithDrawHistoryViewModel: ViewModel() {
    private val repo = AllWithDrawHistoryRepository()

    val isLoading: LiveData<Boolean> = repo.isLoading
    val errorMessage: LiveData<String> = repo.errorMessage
    val allWithDrawHistory: LiveData<AllWithDrawResponseModel> = repo.allWithDrawList

    fun getAllWithDrawHistory(pageNo :Int, query:String){
        repo.getAllWithDraw(pageNo, query)
    }
}