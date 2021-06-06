package com.mistar.memo.domain.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("black")
data class BlackList(
    @Id
    val userId: Int,

    val refreshToken: String
)