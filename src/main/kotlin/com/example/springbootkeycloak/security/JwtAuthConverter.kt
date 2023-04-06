package com.example.springbootkeycloak.security

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimNames
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.stereotype.Component
import java.util.stream.Collectors
import java.util.stream.Stream

// Source: https://medium.com/geekculture/using-keycloak-with-spring-boot-3-0-376fa9f60e0b
@Component
class JwtAuthConverter : Converter<Jwt, AbstractAuthenticationToken> {
    private val logger = LoggerFactory.getLogger(JwtAuthConverter::class.java)
    private val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()

    @Autowired
    var properties = JwtAuthConverterProperties()

    override fun convert(source: Jwt): AbstractAuthenticationToken? {
        val authorities: Collection<GrantedAuthority> = Stream.concat(
            jwtGrantedAuthoritiesConverter.convert(source)!!.stream(),
            extractResourceRoles(source).stream()
        ).collect(Collectors.toSet())

        logger.info("The authorities are: ${getPrincipalClaimName(source)}")
        logger.info("The resourceID is: ${properties.resourceId}")
        logger.info("The principal attribute is: ${properties.principalAttribute}")

        return JwtAuthenticationToken(source, authorities, getPrincipalClaimName(source))
    }

    private fun extractResourceRoles(jwt: Jwt): Collection<GrantedAuthority> {
        val resourceAccess: Map<String, Any>? = jwt.getClaim("resource_access")
        val resource: Map<*, *>? = resourceAccess?.get(properties.resourceId) as Map<*, *>?
        val resourceRoles: Collection<*>? = resource?.get("roles") as Collection<*>?

        return if (resourceAccess == null || resource == null || resourceRoles == null) setOf()
        else resourceRoles.stream()
            .map { role -> SimpleGrantedAuthority("ROLE_$role") }
            .collect(Collectors.toSet())
    }

    private fun getPrincipalClaimName(jwt: Jwt): String {
        logger.info("The jwt is $jwt")
        var claimName = JwtClaimNames.SUB
        if (properties.principalAttribute != null) {
            claimName = properties.principalAttribute as String
            logger.info("The claim name is $claimName")
        }
        return jwt.getClaim(claimName)
    }
}
