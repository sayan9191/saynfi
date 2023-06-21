package com.realteenpatti.sanify.retrofit.models.refer

data class ReferResponseModelItem(
    val User: User,
    val amount: Int,
    val created_at: String,
    val is_success: Boolean,
    val reffered_user_id: Int
)