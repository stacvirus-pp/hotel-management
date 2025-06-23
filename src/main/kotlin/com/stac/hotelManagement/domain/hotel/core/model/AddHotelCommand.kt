package com.stac.hotelManagement.domain.hotel.core.model

data class AddHotelCommand(
  val hotelName: String,
  val hotelDescription: String,
  val hotelLocation: Pair<Double, Double>,
  val amenities: List<String>,
  val images: List<String>,
){
  fun toHotel(): Hotel {
    return Hotel(
      name = hotelName,
      description = hotelDescription,
      location = hotelLocation,
      amenities = amenities,
      images = images
    )
  }
}