package com.stac.hotelManagement.infrastruture.exceptions

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono
import java.time.OffsetDateTime

@RestControllerAdvice
class GlobalExceptionHandler {
  private val log = LoggerFactory.getLogger(this::class.java)

  @ExceptionHandler(IllegalArgumentException::class)
  fun handleIllegalArgumentException(ex: IllegalArgumentException): Mono<ResponseEntity<ErrorResponse>> {
    log.error("Invalid argument: {}", ex.message, ex)
    val error = ErrorResponse(
      message = ex.message ?: "Invalid request parameters",
      status = HttpStatus.BAD_REQUEST.value(),
      timestamp = OffsetDateTime.now()
    )
    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error))
  }

  @ExceptionHandler(RuntimeException::class)
  fun handleRuntimeException(ex: RuntimeException): Mono<ResponseEntity<ErrorResponse>> {
    log.error("Unexpected error: {}", ex.message, ex)
    val error = ErrorResponse(
      message = ex.message ?: "An unexpected error occurred",
      status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
      timestamp = OffsetDateTime.now()
    )
    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error))
  }

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