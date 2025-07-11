package com.stac.hotelManagement.domain.hotel.core.model

data class UpdateHotelCommand(
  val name: String = "UNCHANGED",
  val description: String = "UNCHANGED",
  val images: List<String> = listOf("UNCHANGED")
)