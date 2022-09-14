package me.luvram.reactive.chapter3

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoRepositories
class Chapter3Application {
}

fun main(args: Array<String>) {
    runApplication<Chapter3Application>(*args)
}
