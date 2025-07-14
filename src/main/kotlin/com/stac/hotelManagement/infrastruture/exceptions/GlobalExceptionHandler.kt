package com.stac.hotelManagement.infrastruture.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono
import java.time.OffsetDateTime

@RestControllerAdvice
class GlobalExceptionHandler {

  @ExceptionHandler(EntityNotFoundException::class)
  fun handleEntityNotFound(ex: EntityNotFoundException): Mono<ResponseEntity<ErrorResponse>> {
    val error = ErrorResponse(
      message = ex.message ?: "Entity Not Found",
      status = HttpStatus.NOT_FOUND.value(),
      timestamp = OffsetDateTime.now()
    )
    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error))
  }
}