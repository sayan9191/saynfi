package com.example.sanify.retrofit.models.lottery

data class BuyLotteryRequestModel(
    val amount: Int,
    val timeZoneOffsetFromUtc : Long?
)