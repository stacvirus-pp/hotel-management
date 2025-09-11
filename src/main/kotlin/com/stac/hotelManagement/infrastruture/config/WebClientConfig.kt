package com.stac.hotelManagement.infrastruture.config

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {
  private val log = LoggerFactory.getLogger(this::class.java)

  @Bean
  fun webClient(builder: WebClient.Builder): WebClient {
    return builder
      .filter { request, next ->
        next.exchange(request)
          .doOnError {
            log.error("WebClient error during request to {}", request.url())
          }
          .contextCapture()
      }
      .build()
  }
}