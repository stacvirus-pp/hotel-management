package com.stac.hotelManagement.domain.hotel.core.ports.outgoing

import com.stac.hotelManagement.domain.hotel.core.model.Hotel
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface HotelDatabase {
  fun save(hotel: Hotel): Mono<Hotel>
  fun findAll(): Flux<Hotel>
}