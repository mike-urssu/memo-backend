package com.mistar.memo.domain.service

import com.mistar.memo.core.common.Salt
import com.mistar.memo.domain.exception.UserAlreadyExistsException
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
}