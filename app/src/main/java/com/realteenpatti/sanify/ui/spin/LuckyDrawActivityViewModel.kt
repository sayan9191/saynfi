package com.realteenpatti.sanify.ui.spin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.realteenpatti.sanify.repo.CoinRepository

class LuckyDrawActivityViewModel : ViewModel() {
    private val coinRepo = CoinRepository()

    val isLoading : LiveData<Boolean> = coinRepo.isLoading
    val errorMessage : LiveData<String> = coinRepo.errorMessage

    val coinBalance : LiveData<Int> = coinRepo.currentCoinBalance
    val isCoinDeducted : LiveData<Boolean> = coinRepo.isCoinDeducted
    val isCoinAdded : LiveData<Boolean> = coinRepo.isCoinAdded

    fun getCoinBalance(){
        coinRepo.getCoinBalance()
    }

    fun addCoin(amount : Int) {
        coinRepo.addCoin(amount)
    }

    fun deductCoin(amount: Int){
        coinRepo.deductCoin(amount)
    }

    fun getCoins() {
        coinRepo.getAllCoin()
    }

}