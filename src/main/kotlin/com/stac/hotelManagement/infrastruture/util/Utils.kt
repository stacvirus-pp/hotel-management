package com.stac.hotelManagement.infrastruture.util

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class Utils {
  companion object {

    fun dateToString(date: OffsetDateTime): String {
      val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
      return date.format(formatter)
    }

    fun stringToDate(string: String?): OffsetDateTime {
      val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
      return string?.let {
        OffsetDateTime.parse(it, formatter)
      } ?: throw IllegalArgumentException("Unable to parse date")
    }
  }
}
