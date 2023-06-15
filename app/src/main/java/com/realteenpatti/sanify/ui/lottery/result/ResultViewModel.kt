package com.realteenpatti.sanify.ui.lottery.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.realteenpatti.sanify.repo.AllWinnerRepository
import com.realteenpatti.sanify.retrofit.models.lottery.AllWinnerResponseModel

class ResultViewModel : ViewModel() {
    private val repo = AllWinnerRepository()

    val isLoading: LiveData<Boolean> = repo.isLoading
    val errorMessage: LiveData<String> = repo.errorMessage
    val allWinnerList: LiveData<AllWinnerResponseModel> = repo.allWinnerList

    fun getAllWinnerDetails() {
        repo.getAllWinner()
    }
}