package me.luvram.reactive.chapter4

class CartItem(
    val item: Item,
    quantity: Int = 1
) {
    var quantity: Int = quantity
        private set

    fun increase() {
        quantity++
    }

    fun decrease() {
        if (quantity == 0) {
            return
        }
        quantity--
    }
}
