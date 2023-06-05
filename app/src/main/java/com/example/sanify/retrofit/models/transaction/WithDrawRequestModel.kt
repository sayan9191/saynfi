package com.example.sanify.retrofit.models.transaction

data class WithDrawRequestModel(
    val amount: Int,
    val phone_no: String,
    val transaction_medium: String
)