package me.luvram.reactive.chapter4

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ItemRepository: ReactiveCrudRepository<Item, String> {
}
