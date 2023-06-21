package com.realteenpatti.sanify.retrofit.models.horse

data class GetSlotDetailsResponseModel(
    val is_horse_bidding_slot_open: Boolean,
    val remaining_time_in_millis: Int
)