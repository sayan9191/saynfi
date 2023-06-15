package com.realteenpatti.sanify.retrofit.models.transaction

data class TransactionRequestModel(
    val amount: Int,
    val isAdded: Boolean,
    val screenshot_url: String,
    val transaction_medium: String,
    val transction_id: String
)