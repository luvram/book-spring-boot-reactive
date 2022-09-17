package me.luvram.reactive.chapter4

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ItemUnitTest {
    @Test
    fun `itemBasicsShouldWork`() {
        val sampleItem = Item("item1", "TV tray", "Alf TV Tray", 19.99)
        sampleItem.id shouldBe "item1"
        sampleItem.name shouldBe "TV tray"
        sampleItem.description shouldBe "Alf TV Tray"
        sampleItem.price shouldBe 19.99
    }
}
