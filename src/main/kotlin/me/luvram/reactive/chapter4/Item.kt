package me.luvram.reactive.chapter4

import org.springframework.data.annotation.Id
import org.springframework.data.geo.Point
import java.time.LocalDateTime
import java.util.Date

data class Item(
    @Id
    var id: String? = null,
    val name: String,
    val description: String,
    val price: Double,
) {
}
