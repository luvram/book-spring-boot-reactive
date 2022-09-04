package me.luvram.reactive.chapter1.cahpter1_2

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux


@RestController
class ServerController(
    private val kitchen: KitchenService
) {
    @GetMapping("/server", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun serveDishes(): Flux<Dish> {
        return kitchen.getDishes()
    }

    @GetMapping("/served-dishes", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun deliverDishes(): Flux<Dish> {
        return kitchen.getDishes()
            .map { Dish.deliver(it) }
    }

}
