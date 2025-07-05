package com.stac.hotelManagement.util

import com.stac.hotelManagement.domain.amenity.core.model.Amenity
import com.stac.hotelManagement.domain.amenity.core.model.CreateAmenityCommand
import com.stac.hotelManagement.domain.hotel.core.model.AddHotelCommand
import com.stac.hotelManagement.domain.hotel.core.model.Hotel
import com.stac.hotelManagement.domain.hotel.core.model.HotelDto
import io.github.serpro69.kfaker.Faker
import java.time.OffsetDateTime
import java.util.UUID

object FakerUtils {

  private val faker = Faker()

  fun fakeAddHotelCommand(): AddHotelCommand {
    return AddHotelCommand(
      name = faker.company.name(),
      location = listOf(
        faker.random.nextDouble(),
        faker.random.nextDouble()
      ),
      description = faker.lorem.words(),
      amenities = mutableListOf(UUID.randomUUID(), UUID.randomUUID()),
      images = List(faker.random.nextInt(1, 3)) { "https://picsum.photos/seed/${UUID.randomUUID()}/640/480" }
    )
  }

  fun fakeHotelDto(): HotelDto {
    return HotelDto(
      id = UUID.randomUUID(),
      name = faker.company.name(),
      location = listOf(
        faker.random.nextDouble(),
        faker.random.nextDouble()
      ),
      description = faker.lorem.words(),
      amenities = mutableListOf(UUID.randomUUID(), UUID.randomUUID()),
      images = List(faker.random.nextInt(1, 3)) { "https://picsum.photos/seed/${UUID.randomUUID()}/640/480" },
      createdAt = OffsetDateTime.now(),
      updatedAt = OffsetDateTime.now()
    )
  }

  fun fakeHotel(): Hotel{
    return Hotel(
      id = UUID.randomUUID(),
      name = faker.company.name(),
      location = listOf(
        faker.random.nextDouble(),
        faker.random.nextDouble()
      ),
      description = faker.lorem.words(),
      amenities = mutableListOf(UUID.randomUUID(), UUID.randomUUID()),
      images = List(faker.random.nextInt(1, 3)) { "https://picsum.photos/seed/${UUID.randomUUID()}/640/480" },
      createdAt = OffsetDateTime.now().toString(),
      updatedAt = OffsetDateTime.now().toString()
    )
  }

  fun fakeCreateAmenityCommand(): CreateAmenityCommand {
    return CreateAmenityCommand(
      name = faker.company.name(),
      description = faker.lorem.words(),
      icon = "https://icon.photos/amenity/${UUID.randomUUID()}/640"
    )
  }

  fun fakeAmenity(): Amenity{
    return Amenity(
      id = UUID.randomUUID(),
      name = faker.company.name(),
      description = faker.lorem.words(),
      icon = "https://icon.photos/amenity/${UUID.randomUUID()}/640",
      createdAt = OffsetDateTime.now().toString(),
      updatedAt = OffsetDateTime.now().toString()
    )
  }
}