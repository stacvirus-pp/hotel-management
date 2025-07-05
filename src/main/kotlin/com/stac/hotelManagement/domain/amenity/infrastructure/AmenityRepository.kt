package com.stac.hotelManagement.domain.amenity.infrastructure

import com.stac.hotelManagement.domain.amenity.core.model.Amenity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import java.util.UUID

interface AmenityRepository: ReactiveCrudRepository<Amenity, UUID>