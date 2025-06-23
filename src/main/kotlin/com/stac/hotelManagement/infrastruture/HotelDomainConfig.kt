package com.stac.hotelManagement.infrastruture

import com.stac.hotelManagement.domain.hotel.core.ports.HotelFacade
import com.stac.hotelManagement.domain.hotel.core.ports.outgoing.HotelDatabase
import com.stac.hotelManagement.domain.hotel.infrastructure.HotelDatabaseAdapter
import com.stac.hotelManagement.domain.hotel.infrastructure.HotelRepository
import org.springframework.context.annotation.Bean

class HotelDomainConfig {

  @Bean
  fun hotelDatabase(
    hotelRepository: HotelRepository
  ): HotelDatabase {
    return HotelDatabaseAdapter(hotelRepository)
  }

  @Bean
  fun addHotel(hotelDatabase: HotelDatabase) = HotelFacade(hotelDatabase)
}