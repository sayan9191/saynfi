package com.realteenpatti.sanify.retrofit.models.horse

data class HorseWinnerResponseModel(
    val bid_on_winning_horse: Int,
    val is_user_winner: Boolean,
    val total_bid_money: Int,
    val win_money: Int,
    val winnig_horse_id: Int
)