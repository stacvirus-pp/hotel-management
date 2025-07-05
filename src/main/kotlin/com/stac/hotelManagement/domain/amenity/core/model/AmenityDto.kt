package com.stac.hotelManagement.domain.amenity.core.model

import java.time.OffsetDateTime
import java.util.UUID

data class AmenityDto(
  val id: UUID? = null,

  val name: String,
  val description: String,
  val icon: String,
  val createdAt: OffsetDateTime,
  val updatedAt: OffsetDateTime
)