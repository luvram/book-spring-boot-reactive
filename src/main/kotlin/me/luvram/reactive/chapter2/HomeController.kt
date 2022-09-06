package me.luvram.reactive.chapter2

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.reactive.result.view.Rendering
import reactor.core.publisher.Mono

@Controller
class HomeController(
    private val itemRepository: ItemRepository,
    private val cartRepository: CartRepository
) {
    @GetMapping
    fun home(): Mono<Rendering> {
        val view = Rendering.view("chapter2-home.html")
            .modelAttribute("items", itemRepository.findAll())
            .modelAttribute("cart", cartRepository.findById("My Cart").defaultIfEmpty(Cart("My Cart")))
            .build()
        return Mono.just(view)
    }

    @PostMapping("/add/{id}")
    fun addToCart(@PathVariable id: String): Mono<String> {
        return cartRepository.findById("My Cart")
            .defaultIfEmpty(Cart("My Cart"))
            .flatMap { cart ->
                cart.cartItems.stream()
                    .filter { cartItem ->
                        cartItem.item.id.equals(id)
                    }.findAny()
                    .map { cartItem ->
                        cartItem.quantity++
                        Mono.just(cart)
                    }
                    .orElseGet {
                        itemRepository.findById(id)
                            .map { item -> CartItem(item) }
                            .map { cartItem ->
                                cart.cartItems.add(cartItem)
                                cart
                            }
                    }
            }
            .flatMap { cart -> cartRepository.save(cart) }
            .thenReturn("redirect:/")

    }

}
