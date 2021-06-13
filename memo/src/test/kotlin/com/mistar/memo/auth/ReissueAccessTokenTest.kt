package com.mistar.memo.auth

import com.mistar.memo.application.request.AccessTokenRefreshRequest
import com.mistar.memo.common.BaseControllerTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post

class ReissueAccessTokenTest : BaseControllerTest() {
    @Test
    @DisplayName("토큰 재발급 테스트")
    fun reissueAccessTokenTest() {
        val accessTokenRefreshRequest = AccessTokenRefreshRequest(refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MjAsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2MjM1NjU0MzMsImV4cCI6MTYyNDE3MDIzM30.YG1HOovYrDkyUHXVWEQW7cQZTGR5h5Gp2bhZE_4W2Vg")
        mockMvc.post("/v1/auth/token/refresh") {
            header("Authorization", "Bearer $accessToken")
            content = objectMapper.writeValueAsString(accessTokenRefreshRequest)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }
    }
}