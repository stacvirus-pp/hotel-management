package com.stac.hotelManagement.infrastruture

import com.stac.hotelManagement.domain.amenity.core.ports.AmenityFacade
import com.stac.hotelManagement.domain.amenity.core.ports.outgoing.AmenityDatabase
import com.stac.hotelManagement.domain.amenity.infrastructure.AmenityDatabaseAdapter
import com.stac.hotelManagement.domain.amenity.infrastructure.AmenityRepository
import com.stac.hotelManagement.domain.hotel.infrastructure.client.MinioClient
import org.springframework.context.annotation.Bean

class AmenityDomainConfig {

  @Bean
  fun amenityDatabase(
    amenityRepository: AmenityRepository
  ): AmenityDatabase{
    return AmenityDatabaseAdapter(amenityRepository)
  }

  @Bean
  fun createAmenity(
    amenityDatabase: AmenityDatabase,
    minioClientImpl: MinioClient
  ) = AmenityFacade(amenityDatabase, minioClientImpl)
}