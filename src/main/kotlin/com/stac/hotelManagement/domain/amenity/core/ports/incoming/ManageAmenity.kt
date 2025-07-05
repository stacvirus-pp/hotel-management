package com.stac.hotelManagement.domain.amenity.core.ports.incoming

import com.stac.hotelManagement.domain.amenity.core.model.AmenityDto
import com.stac.hotelManagement.domain.amenity.core.model.CreateAmenityCommand
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ManageAmenity {
  fun create(createAmenityCommand: CreateAmenityCommand): Mono<AmenityDto>
  fun getAmenities(): Flux<AmenityDto>
}