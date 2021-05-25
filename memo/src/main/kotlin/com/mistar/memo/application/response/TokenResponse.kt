package com.mistar.memo.application.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)