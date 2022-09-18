package me.luvram.reactive.chapter4

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.reactive.result.view.Rendering
import reactor.core.publisher.Mono

@Controller
class HomeController(
    private val inventoryService: InventoryService
) {
    companion object {
        private const val CART_ID = "My Cart"
        val log = LoggerFactory.getLogger(HomeController::class.java)
    }
    @GetMapping
    fun home(): Mono<Rendering> {
        val view = Rendering.view("home")
            .modelAttribute("items", inventoryService.getInventory()
                .doOnNext { item -> log.info(item.toString()) }
            )
            .modelAttribute("cart", inventoryService.getCart(CART_ID).defaultIfEmpty(Cart("My Cart")))
            .build()
        return Mono.just(view)
    }

    @PostMapping("/add/{id}")
    fun addToCart(@PathVariable id: String): Mono<String> {
        return inventoryService.addToCart(CART_ID, id)
            .thenReturn("redirect:/")
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable id: String): Mono<String> {
        return inventoryService.deleteItem(id)
            .thenReturn("redirect:/")
    }

    @DeleteMapping("/remove/{id}")
    fun remove(@PathVariable id: String): Mono<String> {
        return inventoryService.removeFromCart(CART_ID, id)
            .thenReturn("redirect:/")
    }
}
