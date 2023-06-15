package com.realteenpatti.sanify.retrofit.models.transaction

data class AllTransactionsResponseModelItem(
    val amount: Int,
    val created_at: String,
    val id: Int,
    val isAdded: Boolean,
    val is_rejected_by_admin: Boolean,
    val is_verified: Boolean,
    val screenshot_url: String,
    val transaction_medium: String,
    val transction_id: String
)