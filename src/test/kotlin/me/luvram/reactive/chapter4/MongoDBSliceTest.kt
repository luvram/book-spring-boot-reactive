package me.luvram.reactive.chapter4

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.test.StepVerifier

@DataMongoTest
class MongoDBSliceTest(
    val repository: ItemRepository
): BehaviorSpec(
    {
        given("item") {
            val sampleItem = Item(name = "name", description = "description", price = 1.99)
            `when`("save") {
                then("saved") {
                    repository.save(sampleItem)
                        .`as` { StepVerifier.create(it) }
                        .expectNextMatches { item ->
                            item.id shouldNotBe null
                            item.name shouldBe "name"
                            item.description shouldBe "description"
                            item.price shouldBe 1.99
                            true
                        }
                        .verifyComplete()
                }
            }
        }
    }
)
