package com.example.springbootkeycloak.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class TestController {
    @GetMapping("/anonymous")
    fun anonymous() = ResponseEntity.ok("Hello Anonymous")

    @GetMapping("/admin")
    fun getAdmin(principal: Principal): ResponseEntity<String> {
        val token = principal as JwtAuthenticationToken
        val userName = token.tokenAttributes["name"] as String?
        val userEmail = token.tokenAttributes["email"] as String?
        return ResponseEntity.ok(
            "Hello Admin \nUser Name : $userName\nUser Email : $userEmail"
        )
    }

    @GetMapping("/user")
    fun getUser(principal: Principal): ResponseEntity<String> {
        val token = principal as JwtAuthenticationToken
        val userName = token.tokenAttributes["name"] as String?
        val userEmail = token.tokenAttributes["email"] as String?
        return ResponseEntity.ok(
            "Hello User \nUser Name : $userName\nUser Email : $userEmail"
        )
    }
}