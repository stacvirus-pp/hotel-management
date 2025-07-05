package com.stac.hotelManagement.domain.amenity.core.ports

import com.stac.hotelManagement.domain.amenity.core.ports.outgoing.AmenityDatabase
import com.stac.hotelManagement.util.FakerUtils.fakeAmenity
import com.stac.hotelManagement.util.FakerUtils.fakeCreateAmenityCommand
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.UUID

class AmenityFacadeTest {

  private val amenityDatabase = mockk<AmenityDatabase>()
  private val facade = AmenityFacade(amenityDatabase)

  @Test
  fun`should create a new amenity`(){
    val command = fakeCreateAmenityCommand()
    val amenity = command.toAmenity()
    val savedAmenity = amenity.copy(id = amenity.id ?: UUID.randomUUID())
    val expectedDto = savedAmenity.toDto()

    every { amenityDatabase.save(any()) } returns Mono.just(savedAmenity)

    StepVerifier.create(facade.create(command))
      .expectNextMatches { it.name == expectedDto.name }
      .verifyComplete()
  }

  @Test
  fun`should retrieve all amenities`(){
    val amenities = listOf(
      fakeAmenity(),
      fakeAmenity()
    )
    val amenityDtos = amenities.map { it.toDto() }

    every { amenityDatabase.findAll() } returns Flux.fromIterable(amenities)

    StepVerifier.create(facade.getAmenities())
      .expectNextSequence(amenityDtos)
      .verifyComplete()
  }
}
