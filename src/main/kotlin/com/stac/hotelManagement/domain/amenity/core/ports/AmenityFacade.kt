package com.stac.hotelManagement.domain.amenity.core.ports

import com.stac.hotelManagement.domain.amenity.core.model.AmenityDto
import com.stac.hotelManagement.domain.amenity.core.model.CreateAmenityCommand
import com.stac.hotelManagement.domain.amenity.core.ports.incoming.ManageAmenity
import com.stac.hotelManagement.domain.amenity.core.ports.outgoing.AmenityDatabase
import com.stac.hotelManagement.infrastruture.exceptions.EntityNotFoundException
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

class AmenityFacade(
  private val database: AmenityDatabase
): ManageAmenity {
  private val log = LoggerFactory.getLogger(this::class.java)

  override fun create(createAmenityCommand: CreateAmenityCommand): Mono<AmenityDto> {
    log.info("creating new amenity using facade class: {}", createAmenityCommand)
    val amenity = createAmenityCommand.toAmenity()
    return database.save(amenity)
      .map { it.toDto() }
      .doOnSuccess { log.info("insertion successful, new amenity created: {}", it)}
      .doOnError { err -> log.error("inserting new hotel amenity: {}", err.message, err) }
  }

  override fun getAmenities(): Flux<AmenityDto> {
    return database.findAll()
      .map { it.toDto() }
      .doOnComplete { log.info("All amenities successfully retrieved") }
      .doOnError { err -> log.error("find all amenities failed: {}", err.message, err) }
  }

  override fun getAmenityById(id: UUID): Mono<AmenityDto> {
    return database.getAmenityById(id)
      .map { it.toDto() }
      .switchIfEmpty(
        Mono.error(
          EntityNotFoundException("Amenity not found with id: $id.")
        )
      )
      .doOnSuccess { log.info("Get Amenity by id {} successful {}", id, it)}
      .doOnError { err -> log.error("Get Amenity by id failed: {}", err.message, err) }
  }
}