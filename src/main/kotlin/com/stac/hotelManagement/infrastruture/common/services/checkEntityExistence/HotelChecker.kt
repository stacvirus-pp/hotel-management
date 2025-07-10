package com.stac.hotelManagement.infrastruture.common.services.checkEntityExistence

import com.stac.hotelManagement.domain.hotel.infrastructure.HotelRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class HotelChecker(
  private val hotelRepository: HotelRepository
): EntityExistenceChecker {
  override fun exists(entityId: UUID): Mono<Boolean> {
    return hotelRepository.findById(entityId).hasElement()
  }
}