package com.mistar.memo.domain.model.entity.user

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("refreshToken")
data class RefreshToken(
    @Id // Not javax.persistence.Id
    val userId: Int,

    var refreshToken: String
)