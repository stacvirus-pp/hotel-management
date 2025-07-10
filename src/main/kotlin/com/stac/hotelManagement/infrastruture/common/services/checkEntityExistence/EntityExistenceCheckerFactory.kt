package com.stac.hotelManagement.infrastruture.common.services.checkEntityExistence

import com.stac.hotelManagement.infrastruture.common.models.enums.EntityType
import org.springframework.stereotype.Component

@Component
class EntityExistenceCheckerFactory(
  private val hotelChecker: HotelChecker,
  private val amenityChecker: AmenityChecker
) {

  fun checker(entityType: String): EntityExistenceChecker{
    return when(entityType.uppercase()){
      EntityType.HOTEL.name -> hotelChecker
      EntityType.AMENITY.name -> amenityChecker
      else -> throw IllegalArgumentException("Unknown entity type, impossible to check existence.")
    }
  }
}
