package com.example.sanify.retrofit.models.transaction

data class AllWithDrawResponseModelItem(
    val amount: Int,
    val created_at: String,
    val id: Int,
    val is_rejected_by_admin: Boolean,
    val is_verified: Boolean,
    val phone_no: String,
    val transaction_medium: String
)