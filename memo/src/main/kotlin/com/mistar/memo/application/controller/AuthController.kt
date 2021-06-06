package com.mistar.memo.application.controller

import com.mistar.memo.application.request.AccessTokenRefreshRequest
import com.mistar.memo.application.request.UserSignInRequest
import com.mistar.memo.application.request.UserSignOutRequest
import com.mistar.memo.application.request.UserSignupRequest
import com.mistar.memo.application.response.RefreshedTokenResponse
import com.mistar.memo.application.response.TokenResponse
import com.mistar.memo.core.utils.ControllerUtils
import com.mistar.memo.domain.service.AuthService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val authService: AuthService
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
    ) = authService.signup(userSignupRequest.toUserSignupDto())

    @ApiOperation("로그인하기")
    @ApiResponses(
        ApiResponse(code = 200, message = "로그인 성공", response = TokenResponse::class),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 404, message = "회원 정보 없음"),
        ApiResponse(code = 409, message = "비밀번호 오류")
    )
    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    fun signIn(
        @RequestBody userSignInRequest: UserSignInRequest
    ) = authService.signIn(userSignInRequest.toUserSignInDto())

    @ApiOperation("로그아웃하기")
    @ApiResponses(
        ApiResponse(code = 200, message = "로그아웃 성공"),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 404, message = "회원 정보 없음"),
        ApiResponse(code = 409, message = "토큰 불일치")
    )
    @DeleteMapping("/signout")
    @ResponseStatus(HttpStatus.OK)
    fun signOut(
        @RequestBody userSignOutRequest: UserSignOutRequest
    ) {
        val userId = ControllerUtils.getUserIdFromAuthentication()
        authService.signOut(userId, userSignOutRequest.toUserSignOutDto())
    }

    @ApiOperation("토큰 재발급하기")
    @ApiResponses(
        ApiResponse(code = 200, message = "재발급 성공", response = RefreshedTokenResponse::class),
        ApiResponse(code = 400, message = "잘못된 요청")
    )
    @PostMapping("/token/refresh")
    @ResponseStatus(HttpStatus.OK)
    fun reissueAccessToken(
        @RequestBody accessTokenRefreshRequest: AccessTokenRefreshRequest
    ): RefreshedTokenResponse {
        val userId = ControllerUtils.getUserIdFromAuthentication()
        val accessToken = authService.reissueAccessToken(userId, accessTokenRefreshRequest.toAccessTokenRefreshDto())
        return RefreshedTokenResponse(accessToken)
    }
}