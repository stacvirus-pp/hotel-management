package com.stac.hotelManagement.domain.hotel.application

import com.stac.hotelManagement.domain.hotel.core.model.AddHotelCommand
import com.stac.hotelManagement.domain.hotel.core.model.Hotel
import com.stac.hotelManagement.domain.hotel.core.ports.incoming.AddHotel
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/hotels")
class HotelCommandController(
  private val addHotel: AddHotel
) {

  @PostMapping("")
  fun createHotel (@RequestBody addHotelCommand: AddHotelCommand): Mono<Hotel> {
    return addHotel.handle(addHotelCommand)
  }
}