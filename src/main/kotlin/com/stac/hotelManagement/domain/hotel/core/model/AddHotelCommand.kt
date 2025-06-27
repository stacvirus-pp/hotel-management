package com.stac.hotelManagement.domain.hotel.core.model

data class AddHotelCommand(
  val name: String,
  val description: String,
  val location: List<Double>,
  val amenities: List<String>,
  val images: List<String>,
){
  fun toHotel(): Hotel {
    return Hotel(
      name = name,
      description = description,
      location = location,
      amenities = amenities,
      images = images
    )
  }
}