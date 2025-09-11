package com.stac.hotelManagement.infrastruture

import com.stac.hotelManagement.domain.hotel.core.ports.HotelFacade
import com.stac.hotelManagement.domain.hotel.core.ports.outgoing.EntityChecker
import com.stac.hotelManagement.domain.hotel.core.ports.outgoing.HotelDatabase
import com.stac.hotelManagement.domain.hotel.infrastructure.client.MinioClient
import com.stac.hotelManagement.domain.hotel.infrastructure.database.HotelDatabaseAdapter
import com.stac.hotelManagement.domain.hotel.infrastructure.database.HotelRepository
import org.springframework.context.annotation.Bean

class HotelDomainConfig {

  @Bean
  fun hotelDatabase(
    hotelRepository: HotelRepository
  ): HotelDatabase {
    return HotelDatabaseAdapter(hotelRepository)
  }

  @Bean
  fun addHotel(
    hotelDatabase: HotelDatabase,
    entityChecker: EntityChecker,
    minioClientImpl: MinioClient
  ) = HotelFacade(hotelDatabase, entityChecker, minioClientImpl)
}
