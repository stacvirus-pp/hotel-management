package com.stac.hotelManagement.domain.hotel.core.model

import com.stac.hotelManagement.infrastruture.util.Utils
import java.time.OffsetDateTime
import java.util.UUID

data class AddHotelCommand(
  val name: String,
  val description: String,
  val location: List<Double>,
  val amenities: List<UUID> = emptyList(),
  val images: List<String>,
){
  fun toHotel(): Hotel {
    return Hotel(
      name = name,
      description = description,
      location = location,
      amenities = amenities.toMutableList(),
      images = images,
      createdAt = Utils.dateToString(OffsetDateTime.now()),
      updatedAt = Utils.dateToString(OffsetDateTime.now())
    )
  }
}