package com.stac.hotelManagement.domain.hotel.application

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
      .uri("/api/v1/hotels/1/add-amenity/2")
      .exchange()
      .expectStatus().isOk
  }
}
