package com.stac.hotelManagement.domain.hotel.infrastructure

import com.stac.hotelManagement.domain.hotel.core.model.Hotel
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface HotelRepository: R2dbcRepository<Hotel, Long> {
}