package me.luvram.reactive.chapter4

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.string.shouldContain
import io.mockk.every
import io.mockk.mockk
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(HomeController::class)
class HomeControllerSliceTest @Autowired constructor(
    val client: WebTestClient,
    val inventoryService: InventoryService
): BehaviorSpec(
    {

//        val inventoryService = mockk<InventoryService>()
        given("two items") {
//            val inventoryService = getMock(service)
            every { inventoryService.getInventory() } returns Flux.just(
                Item("id1", "name1", "desc1", 1.99),
                Item("id2", "name2", "desc2", 9.99)
            )
            every { inventoryService.getCart(any()) } returns Mono.just(Cart("My Cart"))

            `when`("request api") {
                then("should be ok") {
                    client.get().uri("/").exchange()
                        .expectStatus().isOk
                        .expectBody(String::class.java)
                        .consumeWith { exchangeResult ->
                            exchangeResult.responseBody shouldContain " action=\"/add/id1\""
                            exchangeResult.responseBody shouldContain " action=\"/add/id2\""

                        }
                }
            }
        }


    }
)
