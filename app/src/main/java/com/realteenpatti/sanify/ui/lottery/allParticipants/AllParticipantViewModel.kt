package com.realteenpatti.sanify.ui.lottery.allParticipants

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.realteenpatti.sanify.repo.AllParticipantRepository
import com.realteenpatti.sanify.retrofit.models.lottery.AllParticipantResponseModel

class AllParticipantViewModel : ViewModel() {
    private val repo = AllParticipantRepository()

    val isLoading: LiveData<Boolean> = repo.isLoading
    val errorMessage: LiveData<String> = repo.errorMessage
    val allParticipant: LiveData<AllParticipantResponseModel> = repo.allParticipantList

    fun getAllParticipant(pageNo : Int, search : String){
        repo.getAllParticipant(pageNo, search)
    }


}