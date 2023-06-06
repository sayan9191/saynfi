package com.example.sanify.ui.lottery.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sanify.repo.AllWinnerRepository
import com.example.sanify.repo.PrizePoolRepository
import com.example.sanify.retrofit.models.lottery.AllWinnerResponseModel
import com.example.sanify.retrofit.models.lottery.PrizePoolResponseModel

class ResultViewModel : ViewModel() {
    private val repo = AllWinnerRepository()

    val isLoading: LiveData<Boolean> = repo.isLoading
    val errorMessage: LiveData<String> = repo.errorMessage
    val allWinnerList: LiveData<AllWinnerResponseModel> = repo.allWinnerList

    fun getAllWinnerDetails() {
        repo.getAllWinner()
    }
}