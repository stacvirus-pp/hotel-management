package com.stac.hotelManagement.domain.amenity.application

import com.stac.hotelManagement.domain.amenity.core.model.AmenityDto
import com.stac.hotelManagement.domain.amenity.core.model.CreateAmenityCommand
import com.stac.hotelManagement.domain.amenity.core.ports.incoming.ManageAmenity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/hotels/amenities")
class AmenityCommandController(
  private val manageAmenity: ManageAmenity
) {

  @PostMapping("/new")
  @ResponseStatus(HttpStatus.CREATED)
  fun createAmenity(@RequestBody createAmenityCommand: CreateAmenityCommand): Mono<AmenityDto>{
    return manageAmenity.create(createAmenityCommand)
  }

  @GetMapping("")
  fun getAmenities(): Flux<AmenityDto>{
    return manageAmenity.getAmenities()
  }
}