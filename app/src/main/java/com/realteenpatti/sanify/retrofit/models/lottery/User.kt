package com.realteenpatti.sanify.retrofit.models.lottery

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    val created_at: String,
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    val phone_no: String
)