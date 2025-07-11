package com.stac.hotelManagement.domain.hotel.core.ports.incoming

import com.stac.hotelManagement.domain.hotel.core.model.AddHotelCommand
import com.stac.hotelManagement.domain.hotel.core.model.HotelDto
import com.stac.hotelManagement.domain.hotel.core.model.UpdateHotelCommand
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

interface ManageHotel {
  fun handle(addHotelCommand: AddHotelCommand): Mono<HotelDto>
  fun getHotels(): Flux<HotelDto>
  fun addAmenity(hotelId: UUID, amenityId: UUID): Mono<HotelDto>
  fun updateHotel(updateHotelCommand: UpdateHotelCommand, hotelId: UUID): Mono<HotelDto>
  fun getHotelById(id: UUID): Mono<HotelDto>
}