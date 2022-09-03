package me.luvram.reactive.chapter1.cahpter1_1

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

class KitchenService {
    fun getDishes(): Flux<Dish> {
          return Flux.just(
              Dish("Sesame chicken"),
              Dish("Lo mein noodles, plain"),
              Dish("Sweet & sour beef")
          )
    }
}
