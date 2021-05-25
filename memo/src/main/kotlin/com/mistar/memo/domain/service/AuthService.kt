package com.mistar.memo.domain.service

import com.mistar.memo.core.utils.Salt
import com.mistar.memo.domain.exception.InvalidPasswordException
import com.mistar.memo.domain.exception.UserAlreadyExistsException
import com.mistar.memo.domain.exception.UserNotFoundException
import com.mistar.memo.domain.model.dto.UserSignInDto
import com.mistar.memo.domain.model.dto.UserSignupDto
import com.mistar.memo.domain.model.entity.User
import com.mistar.memo.domain.model.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository
) {
    fun signup(userSignupDto: UserSignupDto) {
        if (userRepository.existsByUsername(userSignupDto.username))
            throw UserAlreadyExistsException()

        val user = User(
            username = userSignupDto.username,
            password = Salt.encryptPassword(userSignupDto.password)
        )
        userRepository.save(user)
    }

    fun signIn(userSignInDto: UserSignInDto): User {
        val user = userRepository.findByUsername(userSignInDto.username).orElseThrow { UserNotFoundException() }
        if (!Salt.matchPassword(userSignInDto.password, user.password))
            throw InvalidPasswordException()
        return user
    }
}