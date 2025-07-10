package com.stac.hotelManagement.domain.hotel.core.ports

import com.stac.hotelManagement.domain.hotel.core.model.AddHotelCommand
import com.stac.hotelManagement.domain.hotel.core.model.HotelDto
import com.stac.hotelManagement.domain.hotel.core.ports.incoming.ManageHotel
import com.stac.hotelManagement.domain.hotel.core.ports.outgoing.HotelDatabase
import com.stac.hotelManagement.infrastruture.common.models.enums.EntityType
import com.stac.hotelManagement.infrastruture.common.services.checkEntityExistence.EntityExistenceCheckerFactory
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

class HotelFacade(
  private val database: HotelDatabase,
  private val existenceCheckerFactory: EntityExistenceCheckerFactory
): ManageHotel {

  private val log = LoggerFactory.getLogger(this::class.java)

  override fun handle(addHotelCommand: AddHotelCommand): Mono<HotelDto> {
    log.info("inserting new hotel using facade class: {}", addHotelCommand)
    val hotel = addHotelCommand.toHotel()
    return database.save(hotel)
      .map { it.toDto() }
      .doOnSuccess { log.info("insertion successful, new hotel created: {}", it)}
      .doOnError { err -> log.error("inserting new hotel failed: {}", err.message, err) }
  }

  override fun getHotels(): Flux<HotelDto> {
    return database.findAll()
      .map { it.toDto() }
      .doOnComplete { log.info("All hotels successfully retrieved") }
      .doOnError { err -> log.error("Find all hotels failed: {}", err.message, err) }
  }

  override fun addAmenity(hotelId: UUID, amenityId: UUID): Mono<HotelDto> {
    val checker = existenceCheckerFactory.checker(EntityType.AMENITY.name)
    return checker.exists(amenityId)
      .flatMap { exists ->
        if (!exists) {
          return@flatMap Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND, "Amenity was not found."))
        }
        database.findById(hotelId)
          .flatMap { hotel ->
            database.save(hotel.addAmenity(amenityId))
          }
          .map { it.toDto() }
      }
  }
}
