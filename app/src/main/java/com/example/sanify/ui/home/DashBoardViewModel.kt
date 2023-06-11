package com.example.sanify.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sanify.repo.CoinRepository
import com.example.sanify.retrofit.models.UserInfoResponseModel

class DashBoardViewModel : ViewModel() {
    private val coinRepo = CoinRepository()

    val isLoading : LiveData<Boolean> = coinRepo.isLoading
    val errorMessage : LiveData<String> = coinRepo.errorMessage

    val coinBalance : LiveData<Int> = coinRepo.currentCoinBalance

    val userInfo : LiveData<UserInfoResponseModel> = coinRepo.userInfo

    fun getCoinBalance(){
        coinRepo.getCoinBalance()
    }

    fun getCurrentUserInfo(){
        coinRepo.getUserInfo()
    }
}