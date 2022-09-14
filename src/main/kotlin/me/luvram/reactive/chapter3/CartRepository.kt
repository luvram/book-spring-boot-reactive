package me.luvram.reactive.chapter3

import me.luvram.reactive.chapter3.Cart
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface CartRepository: ReactiveCrudRepository<Cart, String> {
}
