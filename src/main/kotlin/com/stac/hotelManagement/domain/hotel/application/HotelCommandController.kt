package com.stac.hotelManagement.domain.hotel.application

import com.stac.hotelManagement.domain.hotel.core.model.AddHotelCommand
import com.stac.hotelManagement.domain.hotel.core.model.HotelDto
import com.stac.hotelManagement.domain.hotel.core.ports.incoming.ManageHotel
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@RestController
@RequestMapping("/api/v1/hotels")
class HotelCommandController(
  private val manageHotel: ManageHotel
) {

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  fun createHotel (@RequestBody addHotelCommand: AddHotelCommand): Mono<HotelDto> {
    return manageHotel.handle(addHotelCommand)
  }

  @GetMapping
  fun getHotels(): Flux<HotelDto> {
    return manageHotel.getHotels()
  }

  @PutMapping("{hotelId}/add-amenity/{amenityId}")
  fun addAmenity(@PathVariable hotelId: UUID, @PathVariable amenityId: UUID): Mono<HotelDto>{
    return manageHotel.addAmenity(hotelId, amenityId)
  }
}
