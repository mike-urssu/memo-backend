package com.mistar.memo.domain.service

import com.mistar.memo.application.response.TokenResponse
import com.mistar.memo.core.security.JwtTokenProvider
import com.mistar.memo.core.utils.Salt
import com.mistar.memo.domain.exception.*
import com.mistar.memo.domain.model.dto.UserSignInDto
import com.mistar.memo.domain.model.dto.UserSignOutDto
import com.mistar.memo.domain.model.dto.UserSignupDto
import com.mistar.memo.domain.model.entity.BlackList
import com.mistar.memo.domain.model.entity.RefreshToken
import com.mistar.memo.domain.model.entity.User
import com.mistar.memo.domain.model.repository.BlackListRepository
import com.mistar.memo.domain.model.repository.RefreshTokenRepository
import com.mistar.memo.domain.model.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val blackListRepository: BlackListRepository
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun signup(userSignupDto: UserSignupDto) {
        if (userRepository.existsByUsername(userSignupDto.username))
            throw UserAlreadyExistsException()

        val user = User(
            username = userSignupDto.username,
            password = Salt.encryptPassword(userSignupDto.password)
        )
        userRepository.save(user)
    }

    fun signIn(userSignInDto: UserSignInDto): TokenResponse {
        val user = userRepository.findByUsername(userSignInDto.username).orElseThrow { UserNotFoundException() }
        if (!Salt.matchPassword(userSignInDto.password, user.password))
            throw InvalidPasswordException()
        return issueTokens(user)
    }

    private fun issueTokens(user: User): TokenResponse {
        val issuedAccessToken = jwtTokenProvider.generateAccessToken(user.id!!, user.getUserRoles())
        val issuedRefreshToken = jwtTokenProvider.generateRefreshToken(user.id, user.getUserRoles())
        val refreshToken = RefreshToken(user.id, issuedRefreshToken)
        refreshTokenRepository.save(refreshToken)   // HGETALL refreshToken:1 or HGET refreshToken:1 refreshToken
        return TokenResponse(issuedAccessToken, issuedRefreshToken)
    }

    @Transactional
    fun signOut(userId: Int, userSignOutDto: UserSignOutDto) {
        val refreshToken = refreshTokenRepository.findById(userId).orElseThrow { UserNotSignedInException() }
        if (refreshToken.refreshToken != userSignOutDto.refreshToken)
            throw RefreshTokenNotMatchedException()
        refreshTokenRepository.delete(refreshToken)
        blackListRepository.save(BlackList(userId, userSignOutDto.refreshToken))
    }
}