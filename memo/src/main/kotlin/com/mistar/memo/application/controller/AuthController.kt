package com.mistar.memo.application.controller

import com.mistar.memo.application.request.UserSignInRequest
import com.mistar.memo.application.request.UserSignupRequest
import com.mistar.memo.core.security.AccessToken
import com.mistar.memo.domain.service.AuthService
import io.swagger.annotations.ApiOperation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val authService: AuthService
) {
    private val logger: Logger = LoggerFactory.getLogger(AuthController::class.java)

    @ApiOperation("회원가입하기")
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signup(
        @RequestBody userSignupRequest: UserSignupRequest
    ) {
        logger.info("/v1/auth/signup")

        return authService.signup(userSignupRequest.toUserSignupDto())
    }

    @ApiOperation("로그인하기")
    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    fun signIn(
        @RequestBody userSignInRequest: UserSignInRequest
    ): AccessToken {
        logger.info("/v1/auth/signin")

        return authService.signIn(userSignInRequest.toUserSignInDto())
    }
}