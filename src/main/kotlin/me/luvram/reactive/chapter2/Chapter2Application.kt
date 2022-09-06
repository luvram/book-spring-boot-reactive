package me.luvram.reactive.chapter2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoRepositories
class Chapter2Application {
//	companion object {
//		private val mongo = MongoDBContainer(DockerImageName.parse("mongo:4.0.10"))
//		init {
//			mongo.withExposedPorts(27017)
//			mongo.start()
//		}
//	}
}

fun main(args: Array<String>) {
	runApplication<Chapter2Application>(*args)
}
