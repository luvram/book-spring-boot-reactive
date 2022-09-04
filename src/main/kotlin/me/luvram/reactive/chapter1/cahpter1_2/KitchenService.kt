package me.luvram.reactive.chapter1.cahpter1_2

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.Duration
import java.util.Random

@Service
class KitchenService {
    private val menu: List<Dish> = listOf(
        Dish("Sesame chicken"),
        Dish("Lo mein noodles, plain"),
        Dish("Sweet & sour beef")
    )
    private val picker = Random()

    fun getDishes(): Flux<Dish> {
        return Flux.generate<Dish?> {
            it.next(randomDish())
        }.delayElements(Duration.ofMillis(250))
    }

    private fun randomDish(): Dish {
        return menu[picker.nextInt(menu.size)]
    }
}
