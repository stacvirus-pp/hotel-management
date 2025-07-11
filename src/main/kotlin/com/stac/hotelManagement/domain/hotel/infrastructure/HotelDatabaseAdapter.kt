package com.stac.hotelManagement.domain.hotel.infrastructure

import com.stac.hotelManagement.domain.hotel.core.model.Hotel
import com.stac.hotelManagement.domain.hotel.core.ports.outgoing.HotelDatabase
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

class HotelDatabaseAdapter(
  private val hotelRepository: HotelRepository
): HotelDatabase {
  override fun save(hotel: Hotel): Mono<Hotel> {
    return hotelRepository.save(hotel)
  }

  override fun findById(id: UUID): Mono<Hotel> {
    return hotelRepository.findById(id)
  }

  override fun findAll(): Flux<Hotel> {
    return hotelRepository.findAll()
  }

  override fun getHotelById(id: UUID): Mono<Hotel> {
    return hotelRepository.findById(id)
  }
}