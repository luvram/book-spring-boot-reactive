package me.luvram.reactive.chapter2

import org.springframework.data.annotation.Id
import org.springframework.data.geo.Point
import java.time.LocalDateTime
import java.util.Date

class Item(
    @Id
    var id: String? = null,
    val name: String,
    val description: String,
    val price: Double,
    val distributorRegion: String,
    val releaseDate: LocalDateTime,
    val availableUnits: Int,
    val location: Point,
    val active: Boolean
) {
}
