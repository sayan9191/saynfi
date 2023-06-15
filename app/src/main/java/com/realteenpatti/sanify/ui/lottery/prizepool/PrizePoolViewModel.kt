package com.realteenpatti.sanify.ui.lottery.prizepool

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.realteenpatti.sanify.repo.PrizePoolRepository
import com.realteenpatti.sanify.retrofit.models.lottery.PrizePoolResponseModel

class PrizePoolViewModel : ViewModel() {
    private val repo = PrizePoolRepository()

    val isLoading: LiveData<Boolean> = repo.isLoading
    val errorMessage: LiveData<String> = repo.errorMessage
    val allPrizePool: LiveData<PrizePoolResponseModel> = repo.allPrizePoolList

    fun getAllPrizePoolDetails() {
        repo.getPrizePool()
    }
}