package com.realteenpatti.sanify.retrofit.models.lottery

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AllWinnerResponseModelItem(
    val lottery_token_no: Int,
    val position: Int,
    val user: WinnerUser,
)

data class WinnerUser(
    val id : Int,
    val name : String,
    val phone_no : String,
    val created_at : String
)