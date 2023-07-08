package com.realteenpatti.sanify.ui.lottery.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.realteenpatti.sanify.repo.AllWinnerRepository
import com.realteenpatti.sanify.retrofit.models.lottery.AllWinnerResponseModel
import com.realteenpatti.sanify.retrofit.models.lottery.CountDownTimeResponseModel
import com.realteenpatti.sanify.retrofit.models.lottery.PrizePoolResponseModel

class ResultViewModel : ViewModel() {
    private val repo = AllWinnerRepository()

    val isLoading: LiveData<Boolean> = repo.isLoading
    val errorMessage: LiveData<String> = repo.errorMessage
    val allWinnerList: LiveData<AllWinnerResponseModel> = repo.allWinnerList
    val slotDetails: LiveData<CountDownTimeResponseModel> = repo.lotterySlotDetails
    val allPrizePool: LiveData<PrizePoolResponseModel> = repo.allPrizePoolList

    fun getAllWinnerDetails() {
        repo.getAllWinner()
    }

    fun getLotterySlotDetails() {
        repo.getSlotDetails()
    }

    fun getAllPrizePoolDetails() {
        repo.getPrizePool()
    }
}