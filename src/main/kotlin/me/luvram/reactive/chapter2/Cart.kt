package me.luvram.reactive.chapter2

import org.springframework.data.annotation.Id

class Cart(
    @Id
    val id: String,
    val cartItems: MutableList<CartItem> = mutableListOf()
)
