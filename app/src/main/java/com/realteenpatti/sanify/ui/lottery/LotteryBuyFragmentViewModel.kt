package com.realteenpatti.sanify.ui.lottery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.realteenpatti.sanify.repo.CountDownRepository
import com.realteenpatti.sanify.retrofit.models.lottery.CountDownTimeResponseModel

class LotteryBuyFragmentViewModel : ViewModel() {
    private val repo = CountDownRepository()
    val isLoading: LiveData<Boolean> = repo.isLoading
    val errorMessage: LiveData<String> = repo.errorMessage
    val countDownTime: LiveData<CountDownTimeResponseModel> = repo.countDownTimerList
    val coinBalance: LiveData<Int> = repo.currentCoinBalance
    val isBuyLotterySuccess : LiveData<Boolean> = repo.isBuySuccess


    fun getCountDownDetails() {
        repo.getCountDownTime()
    }

    fun buyLottery() {
        repo.buyLottery()
    }

    fun getCurrentCoinBalance() {
        repo.getCoinBalance()
    }
}