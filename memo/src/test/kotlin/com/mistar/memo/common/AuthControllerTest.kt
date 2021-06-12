package com.mistar.memo.common

import com.mistar.memo.application.request.UserSignupRequest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post

class AuthControllerTest : BaseControllerTest() {
    @Test
    @DisplayName("회원가입 테스트")
    fun signupTest() {
        val userSignupRequest = UserSignupRequest(username = "testId", password = "password")
        mockMvc.post("/v1/auth/signup") {
            header("Authorization", "Bearer $accessToken")
            content = objectMapper.writeValueAsString(userSignupRequest)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
        }
    }
}