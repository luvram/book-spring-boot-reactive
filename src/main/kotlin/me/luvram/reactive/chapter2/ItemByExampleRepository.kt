package me.luvram.reactive.chapter2

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor

interface ItemByExampleRepository: ReactiveQueryByExampleExecutor<Item> {
}
