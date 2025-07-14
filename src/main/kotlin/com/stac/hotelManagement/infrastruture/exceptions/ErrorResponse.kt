package com.stac.hotelManagement.infrastruture.exceptions

import java.time.OffsetDateTime

data class ErrorResponse(
  val message: String,
  val status: Int,
  val timestamp: OffsetDateTime
)