package com.example.springbootkeycloak

import com.example.springbootkeycloak.security.JwtAuthConverter
import com.example.springbootkeycloak.security.JwtAuthConverterProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringbootKeycloakApplication

fun main(args: Array<String>) {
	runApplication<SpringbootKeycloakApplication>(*args)
}
