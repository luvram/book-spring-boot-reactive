package me.luvram.reactive.chapter3

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CartService(
    private val itemRepository: ItemRepository,
    private val cartRepository: CartRepository
) {
    fun addToCart(cartId: String, id: String): Mono<Cart> {
        return cartRepository.findById(cartId)
            .log("foundCart")
            .defaultIfEmpty(Cart(cartId))
            .log("emptyCart")
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
                            .log("fetchItem")
                            .map { item -> CartItem(item) }
                            .log("cartItem")
                            .map { cartItem ->
                                cart.cartItems.add(cartItem)
                                cart
                            }
                    }
            }
            .log("cartWithAnotherItem")
            .flatMap { cart -> cartRepository.save(cart) }
            .log("savedCart")
    }

    fun deleteFromCart(cartId: String, id: String): Mono<Cart> {
        return cartRepository.findById(cartId)
            .flatMap { cart ->
                cart.cartItems.removeIf { cartItem -> cartItem.item.id == id }
                Mono.just(cart)
            }.flatMap {
                cartRepository.save(it)
            }
    }


}
