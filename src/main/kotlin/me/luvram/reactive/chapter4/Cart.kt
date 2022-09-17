package me.luvram.reactive.chapter4

import org.springframework.data.annotation.Id

class Cart(
    @Id
    val id: String,
    val cartItems: MutableList<CartItem> = mutableListOf()
)
