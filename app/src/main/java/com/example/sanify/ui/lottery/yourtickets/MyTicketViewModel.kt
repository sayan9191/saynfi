package com.example.sanify.ui.lottery.yourtickets

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sanify.repo.MyTicketsRepository
import com.example.sanify.retrofit.models.lottery.MyTicketResponseModel

class MyTicketViewModel : ViewModel() {
    private val repo = MyTicketsRepository()

    val isLoading: LiveData<Boolean> = repo.isLoading
    val errorMessage: LiveData<String> = repo.errorMessage
    val myTicketList: LiveData<MyTicketResponseModel> = repo.allMyTicketList

    fun getAllMyTicket(search: String) {
        repo.getMyAllTicket(search)
    }
}