package com.realteenpatti.sanify.retrofit.models.auth

data class CreateUserResponseModel(
    val created_at: String,
    val id: Int,
    val name: String,
    val phone_no: String
)