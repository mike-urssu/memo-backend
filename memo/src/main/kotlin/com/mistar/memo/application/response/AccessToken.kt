package com.mistar.memo.application.response

data class AccessToken(
    val token: String,
    val expireIn: Long
)