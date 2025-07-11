package com.stac.hotelManagement.domain.hotel.core.model

import com.stac.hotelManagement.infrastruture.util.Utils
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime
import java.util.*

@Table("hotel")
data class Hotel(
  @Id
  val id: UUID? = null,

  val name: String,
  val location: List<Double>,
  val description: String,
  val amenities: MutableList<UUID>,
  val images: List<String>,
  val createdAt: String,
  val updatedAt: String
){
  fun toDto(): HotelDto{
    return HotelDto(
      id = id,
      name = name,
      location = location,
      description = description,
      amenities = amenities,
      images = images,
      createdAt = Utils.stringToDate(createdAt),
      updatedAt = Utils.stringToDate(updatedAt)
    )
  }

  fun addAmenity(amenity: UUID): Hotel{
    val updatedAmenities = (this.amenities + amenity)
      .distinct()
      .toMutableList()
    return this.copy(
      amenities = updatedAmenities,
      updatedAt = Utils.dateToString(OffsetDateTime.now())
    )
  }

  fun updateName(newName: String): Hotel {
    return this.copy(name = newName, updatedAt = Utils.dateToString(OffsetDateTime.now()))
  }

  fun updateDescription(description: String): Hotel {
    return this.copy(description = description, updatedAt = Utils.dateToString(OffsetDateTime.now()))
  }

  fun updateImages(newImages: List<String>): Hotel {
    val combined = (this.images + newImages).takeLast(6)
    return this.copy(images = combined, updatedAt = Utils.dateToString(OffsetDateTime.now()))
  }
}
