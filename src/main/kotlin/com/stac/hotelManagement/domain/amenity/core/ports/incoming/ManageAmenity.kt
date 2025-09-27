package com.stac.hotelManagement.domain.amenity.core.ports.incoming

import com.stac.hotelManagement.domain.amenity.core.model.AmenityDto
import com.stac.hotelManagement.domain.amenity.core.model.CreateAmenityCommand
import com.stac.hotelManagement.domain.amenity.core.model.UpdateAmenityCommand
import org.springframework.http.codec.multipart.FilePart
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

interface ManageAmenity {
  fun create(createAmenityCommand: CreateAmenityCommand): Mono<AmenityDto>
  fun getAmenities(): Flux<AmenityDto>
  fun getAmenityById(id: UUID): Mono<AmenityDto>
  fun updateAmenityById(updateAmenityCommand: UpdateAmenityCommand, id: UUID): Mono<AmenityDto>
  fun addImage(file: FilePart, id: UUID): Mono<AmenityDto>
}