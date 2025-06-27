package com.stac.hotelManagement.domain.hotel.core.model

import org.springframework.data.annotation.Id

data class Hotel(
  @Id
  val id: Long? = null,

  val name: String,
  val location: List<Double>,
  val description: String,
  val amenities: List<String>,
  val images: List<String>,
)
