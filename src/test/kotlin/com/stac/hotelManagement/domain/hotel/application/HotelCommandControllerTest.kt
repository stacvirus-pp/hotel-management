package com.stac.hotelManagement.domain.hotel.application

import com.stac.hotelManagement.domain.hotel.core.model.UpdateHotelCommand
import com.stac.hotelManagement.util.FakerUtils.fakeAddHotelCommand
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.UUID

@SpringBootTest
@AutoConfigureWebTestClient
class HotelCommandControllerTest {

  @Autowired
  private lateinit var client: WebTestClient

  @Test
  fun`create hotel successfully`(){
    val command = fakeAddHotelCommand()

    client.post()
      .uri("/api/v1/hotels")
      .bodyValue(command)
      .exchange()
      .expectStatus().isCreated
  }

  @Test
  fun`get all hotels`(){
    client.get()
      .uri("/api/v1/hotels")
      .exchange()
      .expectStatus().isOk
      .expectBody()
      .jsonPath("$").isArray
  }

  @Test
  fun`add an amenity to a hotel should respond not found error`(){
    val amenityId = "f748e142-6e77-467b-9904-a2bd9067b953"
    val hotelId = UUID.randomUUID().toString()
    client.put()
      .uri("/api/v1/hotels/$hotelId/add-amenity/$amenityId")
      .exchange()
      .expectStatus().isNotFound
      .expectBody()
      .jsonPath("$.message").isEqualTo("Amenity was not found using id: $amenityId.")
  }

  @Test
  fun`get hotel by id should return not found response`(){
    val id = "f748e142-6e77-467b-9904-a2bd9067b953"
    client.get()
      .uri("/api/v1/hotels/$id")
      .exchange()
      .expectStatus().isNotFound
      .expectBody()
      .jsonPath("$.message").isEqualTo("Hotel not found with id: $id.")
  }

  @Test
  fun`update hotel successfully`(){
    val command = UpdateHotelCommand(name = "test hotel")

    client.put()
      .uri("/api/v1/hotels/f748e142-6e77-467b-9904-a2bd9067b953/update")
      .bodyValue(command)
      .exchange()
      .expectStatus().isOk
  }

  @Test
  fun`delete hotel successfully`(){
    val id = "f748e142-6e77-467b-9904-a2bd9067b953"

    client.delete()
      .uri("/api/v1/hotels/$id/delete")
      .exchange()
      .expectStatus().isNoContent
  }
}
