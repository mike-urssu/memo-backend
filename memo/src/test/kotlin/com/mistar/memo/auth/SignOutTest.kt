package com.mistar.memo.auth

import com.mistar.memo.application.request.UserSignOutRequest
import com.mistar.memo.common.BaseControllerTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.delete

class SignOutTest : BaseControllerTest() {
    @Test
    @DisplayName("로그아웃 테스트")
    fun signOutTest() {
        val userSignOutRequest = UserSignOutRequest(refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MjAsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2MjM1NjU0MzMsImV4cCI6MTYyNDE3MDIzM30.YG1HOovYrDkyUHXVWEQW7cQZTGR5h5Gp2bhZE_4W2Vg")
        mockMvc.delete("/v1/auth/signout") {
            header("Authorization", "Bearer $accessToken")
            content = objectMapper.writeValueAsString(userSignOutRequest)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }
    }
}