package com.example.springbootkeycloak

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringbootKeycloakApplication

fun main(args: Array<String>) {
	runApplication<SpringbootKeycloakApplication>(*args)
}
