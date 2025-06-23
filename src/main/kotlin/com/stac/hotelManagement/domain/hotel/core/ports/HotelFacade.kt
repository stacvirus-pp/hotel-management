package com.stac.hotelManagement.domain.hotel.core.ports

import com.stac.hotelManagement.domain.hotel.core.model.AddHotelCommand
import com.stac.hotelManagement.domain.hotel.core.model.Hotel
import com.stac.hotelManagement.domain.hotel.core.ports.incoming.AddHotel
import com.stac.hotelManagement.domain.hotel.core.ports.outgoing.HotelDatabase
import reactor.core.publisher.Mono

class HotelFacade(
  private val database: HotelDatabase
): AddHotel {

  override fun handle(addHotelCommand: AddHotelCommand): Mono<Hotel> {
    val hotel = addHotelCommand.toHotel()
    return database.save(hotel)
  }
}