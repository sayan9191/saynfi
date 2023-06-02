package com.example.sanify.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sanify.repo.CoinRepository

class DashBoardViewModel : ViewModel() {
    private val coinRepo = CoinRepository.getInstance()

    val isLoading : LiveData<Boolean> = coinRepo.isLoading
    val errorMessage : LiveData<String> = coinRepo.errorMessage

    val coinBalance : LiveData<Int> = coinRepo.currentCoinBalance

    fun getCoinBalance(){
        coinRepo.getCoinBalance()
    }
}