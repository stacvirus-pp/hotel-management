package com.stac.hotelManagement.domain.amenity.application

import com.stac.hotelManagement.util.FakerUtils.fakeCreateAmenityCommand
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
class AmenityCommandControllerTest {

  @Autowired
  private lateinit var client: WebTestClient

  @Test
  fun`create new amenity`(){
    val command = fakeCreateAmenityCommand()

    client.post()
      .uri("/api/v1/hotels/amenities/new")
      .bodyValue(command)
      .exchange()
      .expectStatus().isCreated
      .expectBody()
      .jsonPath("$.name").isEqualTo(command.name)
  }

  @Test
  fun`get all amenities`(){
    client.get()
      .uri("/api/v1/hotels/amenities")
      .exchange()
      .expectStatus().isOk
      .expectBody()
      .jsonPath("$").isArray
  }

  @Test
  fun`get amenities should yield not found error response`() {
    val id = "f748e142-6e77-467b-9904-a2bd9067b953"
    client.get()
      .uri("/api/v1/hotels/amenities/$id")
      .exchange()
      .expectStatus().isNotFound
      .expectBody()
      .jsonPath("$.message").isEqualTo("Amenity not found with id: $id.")
  }
}
