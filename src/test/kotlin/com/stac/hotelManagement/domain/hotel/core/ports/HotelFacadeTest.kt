package com.stac.hotelManagement.domain.hotel.core.ports

import com.stac.hotelManagement.domain.hotel.core.ports.outgoing.HotelDatabase
import com.stac.hotelManagement.util.FakerUtils.fakeAddHotelCommand
import com.stac.hotelManagement.util.FakerUtils.fakeHotel
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.UUID

class HotelFacadeTest {

  private val database = mockk<HotelDatabase>()
  private val facade = HotelFacade(database)

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
}