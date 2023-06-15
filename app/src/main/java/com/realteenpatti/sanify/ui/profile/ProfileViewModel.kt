package com.realteenpatti.sanify.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.realteenpatti.sanify.repo.CoinRepository
import com.realteenpatti.sanify.retrofit.models.UserInfoResponseModel

class ProfileViewModel : ViewModel() {
    private val coinRepo = CoinRepository()

    val isLoading : LiveData<Boolean> = coinRepo.isLoading
    val errorMessage : LiveData<String> = coinRepo.errorMessage
    val userInfo : LiveData<UserInfoResponseModel> = coinRepo.userInfo



    fun getCurrentUserInfo(){
        coinRepo.getUserInfo()
    }
}