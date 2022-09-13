package me.luvram.reactive.chapter2

import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface ItemRepository: ReactiveCrudRepository<Item, String> {
    fun findByNameContaining(partialName: String): Flux<Item>
    @Query("{ 'name': ?0, 'age':  ?1}")
    fun findItemsForCustomerMonthlyReport(name: String, age: Int): Flux<Item>

//    @Query(sort = "{'age': -1}")
//    fun findSortedStuffForWeeklyReport(): Flux<Item>
}
