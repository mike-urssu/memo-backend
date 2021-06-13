package com.mistar.memo.auth

import com.mistar.memo.application.request.UserSignInRequest
import com.mistar.memo.common.BaseControllerTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post

class SignInTest : BaseControllerTest() {
    @Test
    @DisplayName("로그인 테스트")
    fun signInTest() {
        val userSignInRequest = UserSignInRequest(username = "testId", password = "password")
        mockMvc.post("/v1/auth/signin") {
            header("Authorization", "Bearer $accessToken")
            content = objectMapper.writeValueAsString(userSignInRequest)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }
    }
}