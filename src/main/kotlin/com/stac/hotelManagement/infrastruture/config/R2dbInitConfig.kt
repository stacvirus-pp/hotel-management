package com.stac.hotelManagement.infrastruture.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

@Configuration
class R2dbcInitConfig {

  @Bean
  fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
    val initializer = ConnectionFactoryInitializer()
    initializer.setConnectionFactory(connectionFactory)

    val popular = ResourceDatabasePopulator(
      ClassPathResource("hotel.sql"),
      ClassPathResource("amenity.sql")
    )
    initializer.setDatabasePopulator(popular)
    return initializer
  }
}
