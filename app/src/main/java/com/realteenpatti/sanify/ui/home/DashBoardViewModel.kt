package com.realteenpatti.sanify.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.realteenpatti.sanify.repo.CoinRepository
import com.realteenpatti.sanify.retrofit.models.UserInfoResponseModel
import com.realteenpatti.sanify.retrofit.models.spinner.DashBoardNoticeGetResponseModel

class DashBoardViewModel : ViewModel() {
    private val coinRepo = CoinRepository()

    val isLoading : LiveData<Boolean> = coinRepo.isLoading
    val errorMessage : LiveData<String> = coinRepo.errorMessage

    val coinBalance : LiveData<Int> = coinRepo.currentCoinBalance

    val userInfo : LiveData<UserInfoResponseModel> = coinRepo.userInfo

    val noticeDashBoardMessage: MutableLiveData<DashBoardNoticeGetResponseModel> = coinRepo.noticeDashBoardMessage

    fun getCoinBalance(){
        coinRepo.getCoinBalance()
    }

    fun getCurrentUserInfo(){
        try{
            coinRepo.getUserInfo()
        } catch (e: Exception){
            throw e
        }

    }

    fun getDashBoardMessage(){
        coinRepo.getNoticeDashBoardMessage()
    }
}