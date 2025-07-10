package com.stac.hotelManagement.infrastruture.common.services.checkEntityExistence

import com.stac.hotelManagement.domain.amenity.infrastructure.AmenityRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class AmenityChecker(
  private val amenityRepository: AmenityRepository
): EntityExistenceChecker {
  override fun exists(entityId: UUID): Mono<Boolean> {
    return amenityRepository.findById(entityId).hasElement()
  }
}