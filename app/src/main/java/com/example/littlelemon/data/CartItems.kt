package com.example.littlelemon.data

data class CartItem(
    val id: Int,  // Unique identifier of the menu item
    val name: String,
    val price: Double,
    var quantity: Int
)