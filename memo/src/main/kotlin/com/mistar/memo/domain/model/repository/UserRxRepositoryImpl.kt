package com.mistar.memo.domain.model.repository

import com.mistar.memo.domain.model.entity.User
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.*

@Repository
class UserRxRepositoryImpl(
    private val userRepository: UserRepository
) : UserRxRepository {
    override fun existsByUsername(username: String): Mono<Boolean> {
        return Mono.fromCallable { userRepository.existsByUsername(username) }
            .subscribeOn(Schedulers.boundedElastic())
    }

    override fun save(user: User): Mono<Unit> {
        return Mono.fromCallable { userRepository.save(user) }
            .subscribeOn(Schedulers.boundedElastic())
    }

    override fun findById(userId: Int): Mono<Optional<User>> {
        return Mono.fromCallable { userRepository.findById(userId) }
    }
}