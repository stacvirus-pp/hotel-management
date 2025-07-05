package com.stac.hotelManagement.domain.amenity.core.model

import com.stac.hotelManagement.infrastruture.util.Utils
import java.time.OffsetDateTime

data class CreateAmenityCommand(
  val name: String,
  val description: String,
  val icon: String
) {
  fun toAmenity(): Amenity {
    return Amenity(
      name = name,
      description = description,
      icon = icon,
      createdAt = Utils.dateToString(OffsetDateTime.now()),
      updatedAt = Utils.dateToString(OffsetDateTime.now())
    )
  }
}