package com.mistar.memo.domain.model.entity.user

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("black")
data class Black(
    @Id
    val refreshToken: String
)