package com.stac.hotelManagement.domain.hotel.core.ports

import com.stac.hotelManagement.domain.hotel.core.model.AddHotelCommand
import com.stac.hotelManagement.domain.hotel.core.model.HotelDto
import com.stac.hotelManagement.domain.hotel.core.ports.incoming.ManageHotel
import com.stac.hotelManagement.domain.hotel.core.ports.outgoing.HotelDatabase
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class HotelFacade(
  private val database: HotelDatabase
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
}