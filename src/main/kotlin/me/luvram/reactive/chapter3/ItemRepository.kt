package me.luvram.reactive.chapter3

import me.luvram.reactive.chapter3.Item
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface ItemRepository: ReactiveCrudRepository<Item, String> {
}
