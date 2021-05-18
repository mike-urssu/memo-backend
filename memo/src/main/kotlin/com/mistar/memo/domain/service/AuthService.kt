package com.mistar.memo.domain.service

import com.mistar.memo.core.security.JwtTokenProvider
import com.mistar.memo.core.utils.Salt
import com.mistar.memo.domain.exception.InvalidPasswordException
import com.mistar.memo.domain.exception.UserAlreadyExistsException
import com.mistar.memo.domain.exception.UserNotFoundException
import com.mistar.memo.domain.model.dto.UserSignInDto
import com.mistar.memo.domain.model.dto.UserSignupDto
import com.mistar.memo.domain.model.entity.User
import com.mistar.memo.domain.model.repository.UserRxRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuthService(
    private val userRxRepository: UserRxRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {
    fun signup(userSignupDto: UserSignupDto): Mono<Unit> {
        return userRxRepository.existsByUsername(userSignupDto.username)
            .flatMap {
                if (it) Mono.error(UserAlreadyExistsException())
                else Mono.just(
                    User(
                        username = userSignupDto.username,
                        password = Salt.encryptPassword(userSignupDto.password)
                    )
                )
            }.flatMap(userRxRepository::save)
    }

    fun signIn(userSignInDto: UserSignInDto): Mono<String> {
        return userRxRepository.findByUsername(userSignInDto.username)
            .flatMap {
                if (it.isEmpty) Mono.error(UserNotFoundException())
                else Mono.just(it.get())
            }.map {
                if (!Salt.matchPassword(userSignInDto.password, it.password))
                    throw InvalidPasswordException()
                jwtTokenProvider.generateAccessToken(it.id!!, it.getUserRoles())
            }
    }
}