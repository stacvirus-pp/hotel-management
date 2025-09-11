package com.stac.hotelManagement.domain.hotel.application

import com.stac.hotelManagement.domain.hotel.core.model.AddHotelCommand
import com.stac.hotelManagement.domain.hotel.core.model.HotelDto
import com.stac.hotelManagement.domain.hotel.core.model.UpdateHotelCommand
import com.stac.hotelManagement.domain.hotel.core.ports.incoming.ManageHotel
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/api/v1/hotels")
class HotelCommandController(
  private val manageHotel: ManageHotel
) {
  private val log = LoggerFactory.getLogger(this::class.java)

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun createHotel (@RequestBody addHotelCommand: AddHotelCommand): Mono<HotelDto> {
    return manageHotel.addHotel(addHotelCommand)
  }

  @GetMapping
  fun getHotels(): Flux<HotelDto> {
    return manageHotel.getHotels()
  }

  @GetMapping("/{hotelId}")
  fun getHotelById(@PathVariable hotelId: UUID): Mono<HotelDto> {
    return manageHotel.getHotelById(hotelId)
  }

  @PutMapping("{hotelId}/add-amenity/{amenityId}")
  fun addAmenity(@PathVariable hotelId: UUID, @PathVariable amenityId: UUID): Mono<HotelDto> {
    return manageHotel.addAmenity(hotelId, amenityId)
  }

  @PutMapping("{hotelId}/update")
  fun updateHotel(@RequestBody updateHotelCommand: UpdateHotelCommand, @PathVariable hotelId: UUID): Mono<HotelDto> {
    return manageHotel.updateHotel(updateHotelCommand, hotelId)
  }

  @PostMapping("{hotelId}/add-images", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
  @ResponseStatus(HttpStatus.OK)
  fun updateHotel(
    @RequestPart("files") parts: Flux<FilePart>,
    @PathVariable hotelId: UUID
  ): Mono<HotelDto> {
//    val files = parts["files"]?.map { it as FilePart } ?: emptyList()
//    log.info("Received files: {} for hotelId: {}", files.map { it.filename() }, hotelId)
    return parts
      .filter { part -> part.headers().contentType != null }
      .collectList()
      .flatMap { files ->
        manageHotel.addImages(files, hotelId)
      }
  }

  @DeleteMapping("/{hotelId}/delete")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun deleteHotelById(@PathVariable hotelId: UUID): Mono<Unit> {
    return manageHotel.deleteHotelById(hotelId)
  }
}
