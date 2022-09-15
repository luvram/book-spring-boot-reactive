package me.luvram.reactive.chapter3.stacktrace

import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.Random

fun main() {
    val source = if(Random().nextBoolean()) {
        Flux.range(1, 10).elementAt(5)
    } else {
        Flux.just(1, 2, 3, 4).elementAt(5)
    }

    source
        .subscribeOn(Schedulers.parallel())
        .block()
}
