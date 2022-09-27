package me.luvram.reactive.chapter4

import io.kotest.core.spec.style.ExpectSpec
import io.kotest.matchers.string.shouldContain
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import me.luvram.reactive.chapter2.CartItem
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.Duration

@ExtendWith(SpringExtension::class)
class BlockHoundIntegrationTest: ExpectSpec(
    {
        lateinit var inventoryService: InventoryService
        lateinit var itemRepository: ItemRepository
        lateinit var cartRepository: CartRepository
        beforeTest {
            itemRepository = mockk()
            cartRepository = mockk()

            val sampleItem = Item("item1", "TV tray", "Alf TV Tray", 19.99)
            val sampleCartItem = CartItem(sampleItem)
            val sampleCart = Cart("My Cart", mutableListOf(sampleCartItem))

            every { cartRepository.findById(any<String>()) } returns Mono.empty<Cart?>().hide()
            every { itemRepository.findById(any<String>()) } returns Mono.just(sampleItem)
            every { cartRepository.save(any()) } returns Mono.just(sampleCart)

            inventoryService = InventoryService(itemRepository, cartRepository)
        }

        expect("blockhound should trap blocking call") {
            Mono.delay(Duration.ofSeconds(1))
                .flatMap { inventoryService.addToCartBlocking("My Cart", "item1") }
                .`as` { StepVerifier.create(it) }
                .verifyErrorSatisfies { throwable ->

                    throwable.message shouldContain "block()/blockFirst()/blockLast() are blocking"
                }
        }





    }
)
