package me.luvram.reactive.chapter3

import java.util.Random
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.stream.Collectors
import java.util.stream.IntStream



fun main() {
    val executor = Executors.newSingleThreadExecutor()

    val source = if (Random().nextBoolean()) {
        IntStream.range(1, 11).boxed()
            .collect(Collectors.toList())
    } else {
        listOf(1, 2, 3, 4)
    }

    try {
        executor.submit (Callable{
            source[5]
        }).get()
    } catch (e: ExecutionException) {
        e.printStackTrace()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    } finally {
        executor.shutdown()
    }


}
