package com.stac.hotelManagement.application

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class HotelManagementController {
  @GetMapping("")
  fun getAppRoot(): String {
    return "Hotel management REST API..."
  }
}