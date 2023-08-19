package com.realteenpatti.sanify.ui.jhandimunda

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.realteenpatti.sanify.repo.JhandiMundaRepository
import com.realteenpatti.sanify.retrofit.models.jhandimunda.GetJMSlotDetailsResponseModel
import com.realteenpatti.sanify.retrofit.models.jhandimunda.JMMyBidResponseModel
import com.realteenpatti.sanify.retrofit.models.jhandimunda.JMWinnerResponseModel

class JhandiMundaViewModel : ViewModel() {
    private val repo = JhandiMundaRepository()

    val isLoading: LiveData<Boolean> = repo.isLoading
    val slotDetails: LiveData<GetJMSlotDetailsResponseModel> = repo.slotDetails
    val winnerDetail: LiveData<JMWinnerResponseModel> = repo.winnerDetail
    val isBidSuccess: LiveData<Boolean> = repo.isBidSuccess
    val errorMessage: LiveData<String> = repo.errorMessage
    val currentCoinBalance: LiveData<Int> = repo.currentCoinBalance
    val myBidDetails: LiveData<JMMyBidResponseModel> = repo.myBidDetails

    fun getCoinBalance(){
        repo.getCoinBalance()
    }

    fun getSlotDetailsInfo() {
        repo.getJMSSlotDetails()
    }

    fun getWinnerDetails() {
        repo.jmWinner()
    }

    fun jmBid(amount: Int, horseNum: Int) {
        repo.jmBid(amount, horseNum)
    }

    fun getMyJmBids(){
        repo.getMyDetails()
    }
}