package com.stac.hotelManagement.domain.amenity.core.ports.outgoing

import com.stac.hotelManagement.domain.amenity.core.model.Amenity
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

interface AmenityDatabase {
  fun save(amenity: Amenity): Mono<Amenity>
  fun findAll(): Flux<Amenity>
  fun getAmenityById(id: UUID): Mono<Amenity>
}
