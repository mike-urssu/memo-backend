package com.mistar.memo.domain.service

import com.mistar.memo.core.security.JwtTokenProvider
import com.mistar.memo.core.utils.Salt
import com.mistar.memo.domain.exception.InvalidPasswordException
import com.mistar.memo.domain.exception.UserAlreadyExistsException
import com.mistar.memo.domain.exception.UserNotFoundException
import com.mistar.memo.domain.model.dto.UserSignInDto
import com.mistar.memo.domain.model.dto.UserSignupDto
import com.mistar.memo.domain.model.entity.User
import com.mistar.memo.domain.model.repository.UserRepository
import com.mistar.memo.domain.model.repository.UserRxRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuthService(
    private val userRepository: UserRepository,
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

    fun signIn(userSignInDto: UserSignInDto): String {
        val user = userRepository.findByUsername(userSignInDto.username).orElseThrow { UserNotFoundException() }
        if (!Salt.matchPassword(userSignInDto.password, user.password))
            throw InvalidPasswordException()
        return jwtTokenProvider.generateAccessToken(user.id!!, user.getUserRoles())
    }
}