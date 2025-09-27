package com.stac.hotelManagement.domain.amenity.core.model

class UpdateAmenityCommand(
  val name: String = "UNCHANGED",
  val description: String = "UNCHANGED",
  val icon: String = "UNCHANGED"
)