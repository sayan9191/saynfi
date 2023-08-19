package com.realteenpatti.sanify.retrofit.models.jhandimunda

data class JMWinnerResponseModel(
    val bid_on_winning_card: Int,
    val is_user_winner: Boolean,
    val total_bid_money: Int,
    val win_money: Int,
    val winnig_card_id: Int
)