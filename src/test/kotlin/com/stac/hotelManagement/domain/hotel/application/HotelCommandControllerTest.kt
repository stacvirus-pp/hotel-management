package com.stac.hotelManagement.domain.hotel.application

import com.stac.hotelManagement.domain.hotel.core.model.UpdateHotelCommand
import com.stac.hotelManagement.util.FakerUtils.fakeAddHotelCommand
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

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
  fun`add an amenity to a hotel`(){
    client.put()
      .uri("/api/v1/hotels/f748e142/add-amenity/f748e142")
      .exchange()
      .expectStatus().isOk
  }

  @Test
  fun`get hotel by id`(){
    client.get()
      .uri("/api/v1/hotels/f748e142-6e77-467b-9904-a2bd9067b953")
      .exchange()
      .expectStatus().isOk
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
}
