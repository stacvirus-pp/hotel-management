package com.stac.hotelManagement.domain.hotel.infrastructure

import com.stac.hotelManagement.domain.hotel.core.model.Hotel
import com.stac.hotelManagement.domain.hotel.core.ports.outgoing.HotelDatabase
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class HotelDatabaseAdapter(
  private val hotelRepository: HotelRepository
): HotelDatabase {
  override fun save(hotel: Hotel): Mono<Hotel> {
    return hotelRepository.save(hotel)
  }

  override fun findAll(): Flux<Hotel> {
    return hotelRepository.findAll()
  }
}