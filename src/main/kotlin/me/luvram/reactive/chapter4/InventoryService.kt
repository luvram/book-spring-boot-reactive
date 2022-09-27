package me.luvram.reactive.chapter4

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class InventoryService(
    private val itemRepository: ItemRepository,
    private val cartRepository: CartRepository
) {
    fun getCart(cartId: String): Mono<Cart> {
        return cartRepository.findById(cartId)
    }

    fun getInventory(): Flux<Item> {
        return itemRepository.findAll()
    }

    fun deleteItem(id: String): Mono<Void> {
        return itemRepository.deleteById(id)
    }


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
                        cartItem.increase()
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

    fun addToCartBlocking(cartId: String, id: String): Mono<Cart> {
        val myCart = cartRepository.findById(cartId)
            .defaultIfEmpty(Cart(cartId))
            .block()


        return myCart?.cartItems?.stream()
            ?.filter { cartItem ->
                cartItem.item.id.equals(id)
            }?.findAny()
            ?.map { cartItem ->
                cartItem.increase()
                Mono.just(myCart)
            }
            ?.orElseGet {
                itemRepository.findById(id)
                    .map { item -> CartItem(item) }
                    .map { cartItem ->
                        myCart.cartItems.add(cartItem)
                        myCart
                    }
            }

            ?.flatMap { cart -> cartRepository.save(cart) } ?: Mono.empty()
    }


    fun removeFromCart(cartId: String, id: String): Mono<Cart> {
        return cartRepository.findById(cartId)
            .defaultIfEmpty(Cart(cartId))
            .flatMap { cart ->
                cart.cartItems.filter { cartItem -> cartItem.item.id == id }
                    .map { cartItem ->
                        cartItem.decrease()
                        cartItem
                    }
                Mono.just(cart)
            }.map { cart ->
                val items = cart.cartItems.filter { cartItem -> cartItem.quantity > 0 }
                Cart(cartId, items.toMutableList())
            }
            .flatMap {
                cartRepository.save(it)
            }
    }


}
