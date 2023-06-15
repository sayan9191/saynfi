package com.realteenpatti.sanify.retrofit.models.lottery

data class BuyLotteryRequestModel(
    val amount: Int,
    val timeZoneOffsetFromUtc : Long?
)