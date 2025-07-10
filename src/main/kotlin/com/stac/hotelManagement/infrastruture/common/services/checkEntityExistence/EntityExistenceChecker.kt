package com.stac.hotelManagement.infrastruture.common.services.checkEntityExistence

import reactor.core.publisher.Mono
import java.util.UUID

fun interface EntityExistenceChecker {
  fun exists(entityId: UUID): Mono<Boolean>
}