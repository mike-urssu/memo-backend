package com.mistar.memo.domain.service

import com.mistar.memo.domain.model.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository
) {

}