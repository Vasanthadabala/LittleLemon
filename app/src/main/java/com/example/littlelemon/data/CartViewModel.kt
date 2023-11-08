package com.example.littlelemon.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {
    private val cartItems = mutableStateListOf<CartItem>()

    fun getCartItems(): List<CartItem> {
        return cartItems
    }

    fun addItemToCart(item: CartItem) {
        val existingItem = cartItems.find { it.id == item.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(item)
        }
    }
}