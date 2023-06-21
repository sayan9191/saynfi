package com.realteenpatti.sanify.ui.horserace

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.realteenpatti.sanify.repo.HorseRaceRepository
import com.realteenpatti.sanify.retrofit.models.horse.GetSlotDetailsResponseModel
import com.realteenpatti.sanify.retrofit.models.horse.HorseWinnerResponseModel

class HorseRaceViewModel : ViewModel() {
    private val repo = HorseRaceRepository()

    val isLoading: LiveData<Boolean> = repo.isLoading
    val slotDetails: LiveData<GetSlotDetailsResponseModel> = repo.slotDetails
    val winnerDetail: LiveData<HorseWinnerResponseModel> = repo.winnerDetail
    val isBidSuccess: LiveData<Boolean> = repo.isBidSuccess
    val errorMessage: LiveData<String> = repo.errorMessage

    fun getSlotDetails() {
        repo.getSlotDetails()
    }

    fun getWinnerDetails() {
        repo.horseWinner()
    }

    fun horseBid(amount: Int, horseNum: Int) {
        repo.horseBid(amount, horseNum)
    }
}