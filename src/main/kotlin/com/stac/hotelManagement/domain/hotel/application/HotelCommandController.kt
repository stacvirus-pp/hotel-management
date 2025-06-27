package com.stac.hotelManagement.domain.hotel.application

import com.stac.hotelManagement.domain.hotel.core.model.AddHotelCommand
import com.stac.hotelManagement.domain.hotel.core.model.Hotel
import com.stac.hotelManagement.domain.hotel.core.ports.incoming.ManageHotel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/hotels")
class HotelCommandController(
  private val manageHotel: ManageHotel
) {

  @PostMapping("")
  fun createHotel (@RequestBody addHotelCommand: AddHotelCommand): Mono<Hotel> {
    return manageHotel.handle(addHotelCommand)
  }

  @GetMapping
  fun getHotels(): Flux<Hotel> {
    return manageHotel.getHotels()
  }
}