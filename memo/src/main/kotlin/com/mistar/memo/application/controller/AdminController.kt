package com.mistar.memo.application.controller

import com.mistar.memo.domain.model.dto.UserInfoDto
import com.mistar.memo.domain.service.AdminService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/admin")
class AdminController(
    private val adminService: AdminService
) {
    @ApiOperation("사용자 목록 가져오기")
    @ApiResponses(
        ApiResponse(code = 200, message = "사용자 목록 조회 성공"),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 401, message = "인증 안됨"),
        ApiResponse(code = 403, message = "권한 없음")
    )
    @GetMapping("/users/list/{page}")
    @ResponseStatus(HttpStatus.OK)
    fun getUserList(
        @PathVariable page: Int
    ): List<UserInfoDto> {
        return adminService.getUserList(page)
    }

    @ApiOperation("회원탈퇴")
    @ApiResponses(
        ApiResponse(code = 204, message = "사용자 회원탈퇴 성공"),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 401, message = "인증 안됨"),
        ApiResponse(code = 403, message = "권한 없음"),
        ApiResponse(code = 404, message = "해당 id의 사용자 없음")
    )
    @DeleteMapping("/users/delete/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(
        @PathVariable userId: Int
    ) {
        return adminService.deleteUser(userId)
    }

    @ApiOperation("관리자 권한 부여")
    @ApiResponses(
        ApiResponse(code = 204, message = "관리자 권한 부여 성공"),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 401, message = "인증 안됨"),
        ApiResponse(code = 403, message = "권한 없음"),
        ApiResponse(code = 404, message = "해당 id의 사용자 없음")
    )
    @PutMapping("/users/grant/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun grantRole(
        @PathVariable userId: Int
    ) {
        return adminService.grantRole(userId)
    }
}