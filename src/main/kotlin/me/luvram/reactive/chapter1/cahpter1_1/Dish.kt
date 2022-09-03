package me.luvram.reactive.chapter1.cahpter1_1

data class Dish(
    val description: String,
    val delivered: Boolean = false
) {
    companion object {
        fun deliver(dish: Dish): Dish {
            val deliveredDish = Dish(dish.description, true)
            return deliveredDish
        }
    }
}
