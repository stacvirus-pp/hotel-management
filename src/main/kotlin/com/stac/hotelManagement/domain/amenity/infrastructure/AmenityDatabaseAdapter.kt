package com.stac.hotelManagement.domain.amenity.infrastructure

import com.stac.hotelManagement.domain.amenity.core.model.Amenity
import com.stac.hotelManagement.domain.amenity.core.ports.outgoing.AmenityDatabase
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class AmenityDatabaseAdapter(
  private val amenityRepository: AmenityRepository
): AmenityDatabase {
  override fun save(amenity: Amenity): Mono<Amenity> {
    return amenityRepository.save(amenity)
  }

  override fun findAll(): Flux<Amenity> {
    return amenityRepository.findAll()
  }
}