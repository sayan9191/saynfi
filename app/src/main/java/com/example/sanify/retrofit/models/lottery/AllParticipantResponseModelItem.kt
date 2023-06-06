package com.example.sanify.retrofit.models.lottery

data class AllParticipantResponseModelItem(
    val is_winner: Boolean,
    val lottery_token: Int,
    val user: User
)