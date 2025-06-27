package com.stac.hotelManagement

import com.stac.hotelManagement.infrastruture.HotelDomainConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(HotelDomainConfig::class)
//@Import(value = [HotelDomainConfig::class, AnotherConfig::class])
class HotelManagementApplication

fun main(args: Array<String>) {
	runApplication<HotelManagementApplication>(*args)
}
