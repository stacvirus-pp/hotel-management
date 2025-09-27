package com.stac.hotelManagement.domain.hotel.core.ports.outgoing

import org.springframework.http.codec.multipart.FilePart
import reactor.core.publisher.Mono

interface UploadFileClient {
  fun uploadFiles(files: List<FilePart>): Mono<List<String>>
  fun uploadFile(file: FilePart): Mono<String>
}