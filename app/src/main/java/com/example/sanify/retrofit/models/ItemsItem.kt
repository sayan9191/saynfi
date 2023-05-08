package com.example.sanify.retrofit.models

data class ItemsItem(
    val created_at: String,
    val id: Int,
    val inventory: Int,
    val is_sale: Boolean,
    val name: String,
    val owner: Owner,
    val owner_id: Int,
    val price: Int
)