package com.realteenpatti.sanify.retrofit.models.lottery

data class AllWinnerResponseModelItem(
    val lottery_token_no: Int,
    val position: Int,
    val user: User
)