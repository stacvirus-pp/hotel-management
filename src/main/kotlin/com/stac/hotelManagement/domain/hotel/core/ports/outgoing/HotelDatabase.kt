package com.stac.hotelManagement.domain.hotel.core.ports.outgoing

import com.stac.hotelManagement.domain.hotel.core.model.Hotel
import com.stac.hotelManagement.domain.hotel.core.model.HotelDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

interface HotelDatabase {
  fun save(hotel: Hotel): Mono<Hotel>
  fun findById(id: UUID): Mono<Hotel>
  fun findAll(): Flux<Hotel>
  fun getHotelById(id: UUID): Mono<Hotel>
}