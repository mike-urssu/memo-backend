package com.mistar.memo.core.security

import com.mistar.memo.domain.model.enums.UserRole
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors

@Component
class JwtTokenProvider(
    @Value("\${memo.jwt.secret}") private val jwtSecret: String,
    @Value("\${memo.jwt.expirationInMs}") private val jwtExpirationInMs: Int = 0
) {
    private val secretKey = Keys.hmacShaKeyFor(jwtSecret.toByteArray())

    fun generateAccessToken(userId: Int, roles: List<UserRole>): AccessToken {
        val expireIn = Date(Date().time + jwtExpirationInMs)
        return AccessToken(
            Jwts.builder()
                .claim("id", userId)
                .claim("roles", roles)
                .setIssuedAt(Date())
                .setExpiration(expireIn)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact(),
            expireIn.time
        )
    }

//    fun validateAndGetClaimsFromToken(authentication: Authentication): Claims {
//        return getClaimsFromToken(authentication.credentials.toString())
//    }
//
//    fun getUserIdFromToken(token: String): Int {
//        return getClaimsFromToken(token)["id"].toString().toInt()
//    }

    fun getClaimsFromToken(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .body
    }

//    fun getAuthorization(claims: Claims): Authentication {
//        return UsernamePasswordAuthenticationToken(claims["id"], "")
//    }
//
//    private fun getAuthorities(claims: Claims): Collection<GrantedAuthority> {
//        return claims.get("roles", List::class.java).stream()
//            .map { SimpleGrantedAuthority(it as String) }
//            .collect(Collectors.toList())
//    }
}