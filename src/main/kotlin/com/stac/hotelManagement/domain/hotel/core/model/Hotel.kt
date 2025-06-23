package com.stac.hotelManagement.domain.hotel.core.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "hotel")
data class Hotel(
  @Id
  val id: Long? = null,

  val name: String,
  val location: Pair<Double, Double>,
  val description: String,
  val amenities: List<String>,
  val images: List<String>,
)
