package com.stac.hotelManagement.domain.hotel.core.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("hotel")
data class Hotel(
  @Id
  val id: UUID? = null,

  val name: String,
  val location: List<Double>,
  val description: String,
  val amenities: List<String>,
  val images: List<String>,
)
