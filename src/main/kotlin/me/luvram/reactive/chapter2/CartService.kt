package me.luvram.reactive.chapter2

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CartService(
    private val itemRepository: ItemRepository,
    private val cartRepository: CartRepository
) {
    fun addToCart(cartId: String, id: String): Mono<Cart> {
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
    }
}
