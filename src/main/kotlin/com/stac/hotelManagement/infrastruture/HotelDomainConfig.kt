package com.stac.hotelManagement.infrastruture

import com.stac.hotelManagement.domain.hotel.core.ports.HotelFacade
import com.stac.hotelManagement.domain.hotel.core.ports.outgoing.HotelDatabase
import com.stac.hotelManagement.domain.hotel.infrastructure.client.MinioClient
import com.stac.hotelManagement.domain.hotel.infrastructure.database.HotelDatabaseAdapter
import com.stac.hotelManagement.domain.hotel.infrastructure.database.HotelRepository
import com.stac.hotelManagement.infrastruture.common.services.checkEntityExistence.EntityExistenceCheckerFactory
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
    existenceCheckerFactory: EntityExistenceCheckerFactory,
    minioClientImpl: MinioClient
  ) = HotelFacade(hotelDatabase, existenceCheckerFactory, minioClientImpl)
}
