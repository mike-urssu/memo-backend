package com.mistar.memo.application.controller

import com.mistar.memo.application.request.UserSignInRequest
import com.mistar.memo.application.request.UserSignupRequest
import com.mistar.memo.application.response.TokenResponse
import com.mistar.memo.core.security.JwtTokenProvider
import com.mistar.memo.domain.service.AuthService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val authService: AuthService,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @ApiOperation("회원가입하기")
    @ApiResponses(
        ApiResponse(code = 201, message = "회원가입 성공"),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 409, message = "중복 아이디")
    )
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signup(
        @RequestBody userSignupRequest: UserSignupRequest
    ) {
        return authService.signup(userSignupRequest.toUserSignupDto())
    }

    @ApiOperation("로그인하기")
    @ApiResponses(
        ApiResponse(code = 200, message = "로그인 성공"),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 404, message = "유저 정보 없음"),
        ApiResponse(code = 409, message = "비밀번호 오류")
    )
    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    fun signIn(
        @RequestBody userSignInRequest: UserSignInRequest
    ): TokenResponse {
        val user = authService.signIn(userSignInRequest.toUserSignInDto())
        val accessToken = jwtTokenProvider.generateAccessToken(user.id!!, user.getUserRoles())
        val refreshToken = jwtTokenProvider.generateRefreshToken(user.id, user.getUserRoles())
        return TokenResponse(accessToken, refreshToken)
    }
}