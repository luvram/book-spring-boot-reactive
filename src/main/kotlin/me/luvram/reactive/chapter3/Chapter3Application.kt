package me.luvram.reactive.chapter3

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.thymeleaf.TemplateEngine
import reactor.blockhound.BlockHound

@SpringBootApplication
@EnableReactiveMongoRepositories
class Chapter3Application {
}

fun main(args: Array<String>) {
    BlockHound.builder()
        .allowBlockingCallsInside(TemplateEngine::class.java.canonicalName, "process")
        .install()

    runApplication<Chapter3Application>(*args)
}
