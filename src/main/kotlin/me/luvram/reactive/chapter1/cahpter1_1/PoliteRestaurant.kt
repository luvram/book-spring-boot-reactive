package me.luvram.reactive.chapter1.cahpter1_1

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun main() {
    val server = PoliteServer(KitchenService())
    val log: Logger = LoggerFactory.getLogger("main")

    server.doingMyJob()
        .subscribe(
            {
                log.info("Consuming $it")
            },
            {
                log.error(it.message)
            }
        )
}
