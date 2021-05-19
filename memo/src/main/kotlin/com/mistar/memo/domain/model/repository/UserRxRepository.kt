package com.mistar.memo.domain.model.repository

import com.mistar.memo.domain.model.entity.User
import reactor.core.publisher.Mono
import java.util.*

interface UserRxRepository {
    fun existsByUsername(username: String): Mono<Boolean>

    fun save(user: User): Mono<Unit>

    fun findById(userId: Int): Mono<Optional<User>>
}