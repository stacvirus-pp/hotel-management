package com.stac.hotelManagement.domain.hotel.core.ports.incoming

import com.stac.hotelManagement.domain.hotel.core.model.AddHotelCommand
import com.stac.hotelManagement.domain.hotel.core.model.Hotel
import reactor.core.publisher.Mono

fun interface AddHotel {
  fun handle(addHotelCommand: AddHotelCommand): Mono<Hotel>
}