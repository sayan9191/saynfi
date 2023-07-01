package com.realteenpatti.sanify.ui.bottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.realteenpatti.sanify.repo.CountDownRepository
import com.realteenpatti.sanify.retrofit.models.lottery.CountDownTimeResponseModel

class BottomSheetLotteryNoViewModel : ViewModel() {
    private val repo = CountDownRepository()
    val isLoading: LiveData<Boolean> = repo.isLoading
    val errorMessage: LiveData<String> = repo.errorMessage
    val isBuyLotterySuccess : LiveData<Boolean> = repo.isBuySuccess

    fun buyLottery(amount : Int) {
        repo.buyLottery(amount)
    }
}