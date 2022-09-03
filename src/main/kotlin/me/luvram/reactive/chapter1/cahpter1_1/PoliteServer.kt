package me.luvram.reactive.chapter1.cahpter1_1

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

class PoliteServer(val kitchen: KitchenService) {
    companion object {
        val log: Logger = LoggerFactory.getLogger("PoliteServer")
    }

    fun doingMyJob(): Flux<Dish> {
        return kitchen.getDishes()
            .doOnNext { dish -> log.info("Thank you for $dish!") }
            .doOnError { error -> log.error("So sorry about ${error.message}") }
            .doOnComplete { log.info("Thanks for all your hard work!") }
            .map { dish -> Dish.deliver(dish) }
    }
}
