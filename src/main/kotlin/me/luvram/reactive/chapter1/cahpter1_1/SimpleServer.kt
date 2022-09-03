package me.luvram.reactive.chapter1.cahpter1_1

import reactor.core.publisher.Flux


class SimpleServer(
    val kitchen: KitchenService
) {
    fun doingMyJob(): Flux<Dish> {
        return kitchen.getDishes()
            .map { dish -> Dish.deliver(dish) }
    }
}
