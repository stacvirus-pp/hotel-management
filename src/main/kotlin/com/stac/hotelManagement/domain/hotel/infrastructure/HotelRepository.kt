package com.stac.hotelManagement.domain.hotel.infrastructure

import com.stac.hotelManagement.domain.hotel.core.model.Hotel
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import java.util.UUID

interface HotelRepository: ReactiveCrudRepository<Hotel, UUID>