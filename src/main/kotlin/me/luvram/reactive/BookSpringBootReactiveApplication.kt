package me.luvram.reactive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BookSpringBootReactiveApplication

fun main(args: Array<String>) {
	runApplication<BookSpringBootReactiveApplication>(*args)
}
