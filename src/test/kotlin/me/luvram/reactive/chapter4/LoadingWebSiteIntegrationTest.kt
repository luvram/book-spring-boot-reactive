package me.luvram.reactive.chapter4

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.string.shouldContain
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
class LoadingWebSiteIntegrationTest @Autowired constructor(
    val client: WebTestClient
): BehaviorSpec( {
    given("webclient") {
        `when`("request home") {
            then("must include action") {
                client.get().uri("/").exchange()
                    .expectStatus().isOk
                    .expectHeader().contentType(                                      MediaType.TEXT_HTML)
                    .expectBody(String::class.java)
                    .consumeWith {exchangeResult ->
                        exchangeResult.responseBody shouldContain " action=\"/add"
                    }
            }
        }
    }
})
