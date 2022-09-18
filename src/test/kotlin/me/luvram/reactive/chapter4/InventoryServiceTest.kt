package me.luvram.reactive.chapter4

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(SpringExtension::class)
class InventoryServiceTest : BehaviorSpec(
    {
        val cartId = "My Cart"
        val itemRepository: ItemRepository = mockk()
        val cartRepository: CartRepository = mockk()
        val inventoryService: InventoryService = InventoryService(itemRepository, cartRepository)
        beforeTest {
            clearAllMocks()
            val sampleItem = Item("item1", "TV tray", "Alf TV Tray", 19.99)
            val sampleCartItem = CartItem(sampleItem)
            val sampleCart = Cart(cartId, mutableListOf(sampleCartItem))

            every { cartRepository.findById(any<String>()) } returns Mono.empty()
            every { itemRepository.findById(any<String>()) } returns Mono.just(sampleItem)
            every { cartRepository.save(any()) } returns Mono.just(sampleCart)
        }

        given("cart 1") {
            and("item1") {
                `when`("added to cart") {
                    then("one item") {
                        inventoryService.addToCart(cartId, "item1")
                            .`as` { cart -> StepVerifier.create(cart) }
                            .expectNextMatches { cart ->
                                cart.cartItems.forEach { cartItem -> cartItem.quantity shouldBe 1 }
                                cart.cartItems.forEach { cartItem ->
                                    cartItem.item shouldBe Item(
                                        "item1",
                                        "TV tray",
                                        "Alf TV Tray",
                                        19.99
                                    )
                                }
                                true
                            }
                            .verifyComplete()
                    }
                }
            }
        }
    }
)
