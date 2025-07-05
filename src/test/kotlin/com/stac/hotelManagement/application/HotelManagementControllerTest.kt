package com.stac.hotelManagement.application

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest(controllers = [HotelManagementController::class])
@ContextConfiguration(classes = [HotelManagementController::class])
class HotelManagementControllerTest {

  @Autowired
  private lateinit var client: WebTestClient

  @Test
  fun `Should get hotel managements api response message`() {
    client.get()
      .uri("/")
      .exchange()
      .expectStatus().isOk
      .expectBody(String::class.java)
      .isEqualTo("Hotel management REST API...")
  }
}
