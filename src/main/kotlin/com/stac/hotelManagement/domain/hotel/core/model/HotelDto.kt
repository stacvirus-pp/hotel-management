package com.stac.hotelManagement.domain.hotel.core.model

import java.time.OffsetDateTime
import java.util.UUID

data class HotelDto(
  val id: UUID? = null,

  val name: String,
  val location: List<Double>,
  val description: String,
  val amenities: MutableList<UUID>? = null,
  val images: List<String>,
  val createdAt: OffsetDateTime,
  val updatedAt: OffsetDateTime
)