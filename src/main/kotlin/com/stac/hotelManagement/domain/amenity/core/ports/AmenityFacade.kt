package com.stac.hotelManagement.domain.amenity.core.ports

import com.stac.hotelManagement.domain.amenity.core.model.AmenityDto
import com.stac.hotelManagement.domain.amenity.core.model.CreateAmenityCommand
import com.stac.hotelManagement.domain.amenity.core.model.UpdateAmenityCommand
import com.stac.hotelManagement.domain.amenity.core.ports.incoming.ManageAmenity
import com.stac.hotelManagement.domain.amenity.core.ports.outgoing.AmenityDatabase
import com.stac.hotelManagement.domain.hotel.core.ports.outgoing.UploadFileClient
import com.stac.hotelManagement.infrastruture.exceptions.EntityNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.codec.multipart.FilePart
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

class AmenityFacade(
  private val database: AmenityDatabase,
  private val uploadFileClient: UploadFileClient
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

  override fun updateAmenityById(updateAmenityCommand: UpdateAmenityCommand, id: UUID): Mono<AmenityDto> {
    return database.findById(id)
      .switchIfEmpty(Mono.error(
        EntityNotFoundException("Amenity not found with id: $id.")
      ))
      .flatMap { amenity ->
        var updatedAmenity = amenity
        if (updateAmenityCommand.name != "UNCHANGED") {
          updatedAmenity = updatedAmenity.updateName(updateAmenityCommand.name)
        }

        if (updateAmenityCommand.description != "UNCHANGED") {
          updatedAmenity = updatedAmenity.updateDescription(updateAmenityCommand.description)
        }

        if (updateAmenityCommand.icon != "UNCHANGED") {
          updatedAmenity = updatedAmenity.updateIcon(updateAmenityCommand.icon)
        }

        database.save(updatedAmenity).map { it.toDto() }
      }
      .doOnSuccess { log.info("Update amenity by id {} successful", id)}
      .doOnError { err -> log.error("Update amenity by id failed: {}", err.message, err) }
  }

  override fun addImage(file: FilePart, id: UUID): Mono<AmenityDto> {
    return uploadFileClient.uploadFile(file)
      .flatMap { imageLink ->
        updateAmenityById(UpdateAmenityCommand(icon = imageLink), id)
      }
      .doOnSuccess { log.info("Add icon to amenity id {} successful {}", id, it)}
      .doOnError { err -> log.error("Add icon to amenity failed: {}", err.message, err) }
  }
}