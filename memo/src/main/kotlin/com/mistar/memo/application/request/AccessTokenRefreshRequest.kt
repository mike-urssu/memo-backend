package com.mistar.memo.application.request

import com.mistar.memo.domain.model.dto.AccessTokenRefreshDto

data class AccessTokenRefreshRequest(
    val refreshToken: String
) {
    fun toAccessTokenRefreshDto() = AccessTokenRefreshDto(refreshToken)
}