package com.stac.hotelManagement.domain.amenity.core.model

import com.stac.hotelManagement.infrastruture.util.Utils
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime
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

  fun updateName(newName: String): Amenity {
    return this.copy(name = newName, updatedAt = Utils.dateToString(OffsetDateTime.now()))
  }

  fun updateDescription(description: String): Amenity {
    return this.copy(description = description, updatedAt = Utils.dateToString(OffsetDateTime.now()))
  }

  fun updateIcon(newIcon: String): Amenity {
    return this.copy(icon = newIcon, updatedAt = Utils.dateToString(OffsetDateTime.now()))
  }
}