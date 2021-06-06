package com.mistar.memo.core.security

import com.mistar.memo.domain.model.enums.UserRole
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.DecodingException
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
    @Value("\${app.jwt.secret}") private val jwtSecret: String,
    @Value("\${app.jwt.accessTokenValidMS}") private val accessTokenValidMilliSecond: Long = 0,
    @Value("\${app.jwt.refreshTokenValidMS}") private val refreshTokenValidMilliSecond: Long = 0
) {
    private val secretKey = Keys.hmacShaKeyFor(jwtSecret.toByteArray())

    fun generateAccessToken(userId: Int, roles: List<UserRole>): String {
        return generateToken(userId, roles, accessTokenValidMilliSecond)
    }

    fun generateRefreshToken(userId: Int, roles: List<UserRole>): String {
        return generateToken(userId, roles, refreshTokenValidMilliSecond)
    }

    fun generateToken(userId: Int, roles: List<UserRole>, tokenValidMilSecond: Long): String {
        return Jwts.builder()
            .claim("id", userId)
            .claim("roles", roles)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + tokenValidMilSecond))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun resolveToken(request: HttpServletRequest): Claims? {
        val header = request.getHeader("Authorization")
        val token = when {
            header == null -> return null
            header.contains("Bearer") -> header.replace("Bearer ", "")
            else -> throw DecodingException("")
        }
        return getClaimsFromToken(token)
    }

    fun getClaimsFromToken(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .body
    }

    fun getAuthentication(claims: Claims): Authentication {
        return UsernamePasswordAuthenticationToken(claims["id"], "", getAuthorities(claims))
    }

    private fun getAuthorities(claims: Claims): Collection<GrantedAuthority> {
        return claims.get("roles", List::class.java).stream()
            .map { SimpleGrantedAuthority(it as String) }
            .collect(Collectors.toList())
    }
}