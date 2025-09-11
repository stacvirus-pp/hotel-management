package com.stac.hotelManagement.domain.hotel.infrastructure.client

import com.fasterxml.jackson.databind.JsonNode
import com.stac.hotelManagement.domain.hotel.core.ports.outgoing.UploadFileClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.MediaType
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration

@Component
class MinioClient (
  @Value("\${api.minio.scheme}") private val scheme: String,
  @Value("\${api.minio.host}") private val host: String,
  @Value("\${api.minio.path}") private val path: String,
  @Value("\${api.minio.base}") private val base: String,
  @Value("\${api.minio.port}") private val port: Int,
  private val webClient: WebClient
): UploadFileClient {
  private val log = LoggerFactory.getLogger(this::class.java)
  companion object {
    private const val FORM_FIELD_FILES = "files"
    private const val MAX_FILE_SIZE_BYTES = 10 * 1024 * 1024L // 10MB
  }

  override fun uploadFiles(files: List<FilePart>): Mono<List<String>> {
    if (files.isEmpty()) {
      log.warn("⚠ No files provided for upload")
      return Mono.error(IllegalArgumentException("Files list cannot be empty"))
    }

    log.info(":rocket: Starting upload of ${files.size} files")

    return Flux.fromIterable(files)
      .flatMap { filePart ->
        validateFilePart(filePart)
          .flatMap { contentType ->
            Mono.just(filePart)
              .doOnNext { part ->
                log.debug(":file_folder: Prepared file ${part.filename()}")
              }
              .doOnError { err ->
                log.error("❌ Failed to process file ${filePart.filename()}: {}", err.message, err)
              }
          }
          .retryWhen(Retry.backoff(2, Duration.ofSeconds(1)))
          .timeout(Duration.ofSeconds(10))
      }
      .collectList()
      .flatMap { validFiles ->
        val builder = MultipartBodyBuilder().apply {
          validFiles.forEach { filePart ->
            asyncPart(FORM_FIELD_FILES, filePart.content(), DataBuffer::class.java)
              .filename(filePart.filename())
              .header("Content-Type", filePart.headers().contentType?.toString() ?: "application/octet-stream")
          }
        }
        log.debug("Sending multipart request to MinIO with {} files", validFiles.size)
        webClient.post()
          .uri { builder ->
            builder
              .scheme(scheme)
              .host(host)
              .port(port)
              .path("$base/$path")
              .build()
          }
          .contentType(MediaType.MULTIPART_FORM_DATA)
          .body(BodyInserters.fromMultipartData(builder.build()))
          .retrieve()
          .onStatus({ it.isError }, handleErrorResponse("Minio client request failed"))
          .bodyToMono<List<String>>()
          .timeout(Duration.ofSeconds(30))
          .doOnSuccess { urls ->
            log.info(":rocket: Successfully uploaded ${validFiles.size} files: {}", urls)
          }
          .doOnError { e ->
            log.error("❌ Upload failed: {}", e.message, e)
          }
      }
  }

  private fun handleErrorResponse(reason: String): (ClientResponse) -> Mono<Throwable> {
    return { response ->
      response.bodyToMono(JsonNode::class.java)
        .doOnNext { body ->
          log.error("$reason: ${response.statusCode()} - ${body.toPrettyString()}")
        }
        .flatMap { body ->
          val message = when {
            body.has("message") -> body["message"]
            else -> "Unknown error"
          }
          Mono.error(ResponseStatusException(response.statusCode(),"$reason: $message"))
        }
    }
  }

  private fun validateFilePart(filePart: FilePart): Mono<String> {
    return Mono.just(filePart)
      .map { part ->
        val contentType = part.headers().contentType?.toString()
          ?: throw IllegalArgumentException("Content type is required for ${part.filename()}")
        if (!contentType.startsWith("image/")) {
          throw IllegalArgumentException("File ${part.filename()} must be an image")
        }
        if (part.headers().contentLength > MAX_FILE_SIZE_BYTES) {
          throw IllegalArgumentException(
            "File ${part.filename()} exceeds ${MAX_FILE_SIZE_BYTES / 1024 / 1024}MB"
          )
        }
        contentType
      }
  }
}
