package com.realteenpatti.sanify.retrofit.models.lottery

data class MyTicketResponseModelItem(
    val is_winner: Boolean,
    val lottery_token: Int,
    val user: UserMyTicket
)