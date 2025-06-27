package com.stac.hotelManagement.domain.hotel.core.ports

import com.stac.hotelManagement.domain.hotel.core.model.AddHotelCommand
import com.stac.hotelManagement.domain.hotel.core.model.Hotel
import com.stac.hotelManagement.domain.hotel.core.ports.incoming.ManageHotel
import com.stac.hotelManagement.domain.hotel.core.ports.outgoing.HotelDatabase
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class HotelFacade(
  private val database: HotelDatabase
): ManageHotel {

  override fun handle(addHotelCommand: AddHotelCommand): Mono<Hotel> {
    val hotel = addHotelCommand.toHotel()
    return database.save(hotel)
  }

  override fun getHotels(): Flux<Hotel> {
    return database.findAll()
  }
}