package com.mistar.memo.application.controller

import com.mistar.memo.domain.model.dto.UserInfoDto
import com.mistar.memo.domain.service.AdminService
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/admin")
class AdminController(
    private val adminService: AdminService
) {
    @ApiOperation("사용자 목록 가져오기")
    @GetMapping("/users/list/{page}")
    @ResponseStatus(HttpStatus.OK)
    fun getUserList(
        @PathVariable page: Int
    ): List<UserInfoDto> {
        return adminService.getUserList(page)
    }
}