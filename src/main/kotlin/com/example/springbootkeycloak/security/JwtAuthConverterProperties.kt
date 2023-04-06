package com.example.springbootkeycloak.security

import lombok.Data
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@ConfigurationProperties(prefix = "jwt.auth.converter")
@Component
data class JwtAuthConverterProperties (
    var resourceId: String = "web-app-1",
    var principalAttribute: String = "preferred_username"
)

/*
@Service
class JwtAuthConverterProperties {
    @Value("\${jwt.auth.converter.resource-id}")
    var resourceId = ""
    @Value("\${jwt.auth.converter.principal-attribute}")
    var principalAttribute = ""
}*/
