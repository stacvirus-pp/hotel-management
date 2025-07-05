package com.stac.hotelManagement.domain.amenity.core.model

import com.stac.hotelManagement.infrastruture.util.Utils
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("amenity")
data class Amenity(
  @Id
  val id: UUID? = null,

  val name: String,
  val description: String,
  val icon: String,
  val createdAt: String,
  val updatedAt: String
) {
  fun toDto(): AmenityDto{
    return AmenityDto(
      id = id,
      name = name,
      description = description,
      icon = icon,
      createdAt = Utils.stringToDate(createdAt),
      updatedAt = Utils.stringToDate(updatedAt)
    )
  }
}