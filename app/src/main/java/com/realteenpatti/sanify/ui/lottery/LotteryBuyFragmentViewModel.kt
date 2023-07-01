package com.realteenpatti.sanify.ui.lottery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.realteenpatti.sanify.repo.CountDownRepository
import com.realteenpatti.sanify.retrofit.models.lottery.CountDownTimeResponseModel
import com.realteenpatti.sanify.retrofit.models.lottery.LotteryNoticeGetResponseModel

class LotteryBuyFragmentViewModel : ViewModel() {
    private val repo = CountDownRepository()
    val isLoading: LiveData<Boolean> = repo.isLoading
    val errorMessage: LiveData<String> = repo.errorMessage
    val countDownTime: LiveData<CountDownTimeResponseModel> = repo.countDownTimerList
    val coinBalance: LiveData<Int> = repo.currentCoinBalance
    val isBuyLotterySuccess: LiveData<Boolean> = repo.isBuySuccess
    val noticeBoardMessage: MutableLiveData<LotteryNoticeGetResponseModel> = repo.noticeBoardMessage

    fun getLotteryNoticeMessage() {
        repo.getNoticeMessage()
    }

    fun getCountDownDetails() {
        repo.getCountDownTime()
    }

    fun buyLottery(amount: Int) {
        repo.buyLottery(amount)
    }

    fun getCurrentCoinBalance() {
        repo.getCoinBalance()
    }
}