package com.mistar.memo.domain.model.entity

import org.springframework.data.annotation.AccessType
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("refreshToken")
data class RefreshToken(
    @Id // Not javax.persistence.Id
    @AccessType(AccessType.Type.PROPERTY)
    val userId: Int,

    var refreshToken: String
)