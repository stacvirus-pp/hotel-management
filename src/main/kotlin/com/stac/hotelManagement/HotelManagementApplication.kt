package com.stac.hotelManagement

import com.stac.hotelManagement.infrastruture.AmenityDomainConfig
import com.stac.hotelManagement.infrastruture.HotelDomainConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(value = [
	HotelDomainConfig::class,
	AmenityDomainConfig::class
])
class HotelManagementApplication

fun main(args: Array<String>) {
	runApplication<HotelManagementApplication>(*args)
}
