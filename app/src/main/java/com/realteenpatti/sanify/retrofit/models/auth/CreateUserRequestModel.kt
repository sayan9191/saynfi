package com.realteenpatti.sanify.retrofit.models.auth

data class CreateUserRequestModel(
    val name: String,
    val password: String,
    val phone_no: String
)