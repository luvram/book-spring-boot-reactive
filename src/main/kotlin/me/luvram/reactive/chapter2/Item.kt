package me.luvram.reactive.chapter2

import org.springframework.data.annotation.Id

class Item(
    @Id
    var id: String? = null,
    val name: String,
    val price: Double
) {
}
