package com.stac.hotelManagement.domain.hotel.core.ports

import com.stac.hotelManagement.domain.hotel.core.ports.outgoing.HotelDatabase
import com.stac.hotelManagement.infrastruture.common.models.enums.EntityType
import com.stac.hotelManagement.infrastruture.common.services.checkEntityExistence.AmenityChecker
import com.stac.hotelManagement.infrastruture.common.services.checkEntityExistence.EntityExistenceCheckerFactory
import com.stac.hotelManagement.util.FakerUtils.fakeAddHotelCommand
import com.stac.hotelManagement.util.FakerUtils.fakeAmenity
import com.stac.hotelManagement.util.FakerUtils.fakeHotel
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.UUID

class HotelFacadeTest {

  private val database = mockk<HotelDatabase>()
  private val existenceCheckerFactory = mockk<EntityExistenceCheckerFactory>()
  private val facade = HotelFacade(database, existenceCheckerFactory)

  private val amenityChecker = mockk<AmenityChecker>()

  @Test
  fun `should create a new hotel`() {
    val command = fakeAddHotelCommand()
    val hotel = command.toHotel()
    val savedHotel = hotel.copy(id = hotel.id ?: UUID.randomUUID())
    val expectedDto = savedHotel.toDto()

    every { database.save(any()) } returns Mono.just(savedHotel)

    StepVerifier.create(facade.handle(command))
      .expectNextMatches { it.name == expectedDto.name }
      .verifyComplete()
  }

  @Test
  fun `should retrieve all hotels`() {
    val hotels = listOf(fakeHotel(), fakeHotel())
    val hotelDtos = hotels.map { it.toDto() }

    every { database.findAll() } returns Flux.fromIterable(hotels)

    StepVerifier.create(facade.getHotels())
      .expectNextSequence(hotelDtos)
      .verifyComplete()
  }

  @Test
  fun`add amenity completes successfully`(){
    val amenityId = UUID.randomUUID()
    val hotelId = UUID.randomUUID()
    val findHotel = fakeHotel().copy(id = hotelId, amenities = mutableListOf())
    val addedAmenity = fakeAmenity().copy(id = amenityId)
    val updatedHotel = findHotel.addAmenity(addedAmenity.id!!)

    every { existenceCheckerFactory.checker(EntityType.AMENITY.name) } returns amenityChecker
    every { amenityChecker.exists(amenityId) } returns Mono.just(true)
    every { database.findById(hotelId) } returns Mono.just(findHotel)
    every { database.save(any()) } returns Mono.just(updatedHotel)

    StepVerifier.create(facade.addAmenity(hotelId, amenityId))
      .expectNextMatches { it.amenities?.size == 1 }
      .verifyComplete()
  }

  @Test
  fun`add amenity completes with error if amenity doesn't exists`(){
    val amenityId = UUID.randomUUID()
    val hotelId = UUID.randomUUID()

    every { existenceCheckerFactory.checker(EntityType.AMENITY.name) } returns amenityChecker
    every { amenityChecker.exists(amenityId) } returns Mono.just(false)

    StepVerifier.create(facade.addAmenity(hotelId, amenityId))
      .expectErrorMatches { it is ResponseStatusException && it.statusCode == HttpStatus.NOT_FOUND }
      .verify()
  }
}
