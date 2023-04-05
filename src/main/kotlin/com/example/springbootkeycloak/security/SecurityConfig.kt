package com.example.springbootkeycloak.security

import com.example.springbootkeycloak.security.JwtAuthConverter
import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
class SecurityConfig {
    private final val ADMIN = "admin"
    private final val USER = "user"
    private val jwtAuthConverter = JwtAuthConverter()

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests()
            .requestMatchers(HttpMethod.GET, "/anonymous").permitAll()
            .requestMatchers(HttpMethod.GET, "/admin").hasRole(ADMIN)
            .requestMatchers(HttpMethod.GET, "/user").hasAnyRole(ADMIN, USER)
            .anyRequest().authenticated()
        http.oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(jwtAuthConverter)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        return http.build()
    }
}