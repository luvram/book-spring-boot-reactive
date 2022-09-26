package me.luvram.reactive.chapter4

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.ExpectSpec
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.Duration

class BlockHoundUnitTest: ExpectSpec(
    {
        expect("blocking") {

            Mono.delay(Duration.ofSeconds(1))
                .flatMap {
                    try {
                        Thread.sleep(10)
                        Mono.just(true)
                    } catch (e: InterruptedException) {
                        Mono.error<InterruptedException>(e)
                    }
                }
                .`as` { StepVerifier.create(it) }
                .verifyComplete()
        }
    }
)
