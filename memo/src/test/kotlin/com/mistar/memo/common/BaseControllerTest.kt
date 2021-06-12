package com.mistar.memo.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.mistar.memo.core.security.JwtTokenProvider
import com.mistar.memo.domain.model.enums.UserRole
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Rollback
@Transactional
class BaseControllerTest {
    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @Autowired
    protected lateinit var jwtTokenProvider: JwtTokenProvider

    val userId = "99999"
    val adminId = "99998"
    val invalidUserId = "0"

    lateinit var accessToken: String
    lateinit var adminAccessToken: String
    lateinit var invalidAccessToken: String

    @BeforeEach
    fun setUp() {
        accessToken = jwtTokenProvider.generateAccessToken(userId.toInt(), listOf(UserRole.ROLE_USER))
        adminAccessToken = jwtTokenProvider.generateAccessToken(adminId.toInt(), listOf(UserRole.ROLE_USER, UserRole.ROLE_ADMIN))
        invalidAccessToken = jwtTokenProvider.generateAccessToken(invalidUserId.toInt(), listOf(UserRole.ROLE_USER))
    }
}