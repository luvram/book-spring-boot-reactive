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
            .defaultIfEmpty(Cart(cartId))
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
