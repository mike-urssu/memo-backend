package com.mistar.memo.core.security

data class AccessToken(
    val token: String,
    val expireIn: Long
)