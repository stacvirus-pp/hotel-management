package com.stac.hotelManagement.domain.hotel.core.ports.outgoing

import com.stac.hotelManagement.infrastruture.common.services.checkEntityExistence.EntityExistenceChecker

interface EntityChecker {
  fun checker(entityType: String): EntityExistenceChecker
}