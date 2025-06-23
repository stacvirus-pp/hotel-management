package com.stac.hotelManagement.domain.hotel.core.ports.outgoing

import com.stac.hotelManagement.domain.hotel.core.model.Hotel
import reactor.core.publisher.Mono

fun interface HotelDatabase {
  fun save(hotel: Hotel): Mono<Hotel>
}