package com.mistar.memo.application.controller

import com.mistar.memo.domain.model.dto.UserInfoDto
import com.mistar.memo.domain.service.AdminService
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/admin")
class AdminController(
    private val adminService: AdminService
) {
    private final val logger = LoggerFactory.getLogger(AdminController::class.java)

    @ApiOperation("사용자 목록 가져오기")
    @GetMapping("/users/list/{page}")
    @ResponseStatus(HttpStatus.OK)
    fun getUserList(
        @PathVariable page: Int
    ): List<UserInfoDto> {
        logger.info("/v1/admin/users/list/$page")

        return adminService.getUserList(page)
    }

    @ApiOperation("회원탈퇴")
    @DeleteMapping("/users/delete/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteUser(
        @PathVariable userId: Int
    ) {
        logger.info("/v1/admin/users/delete/$userId")

        return adminService.deleteUser(userId)
    }
}