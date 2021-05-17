package com.mistar.memo.application.controller

import com.mistar.memo.domain.model.dto.UserInfoDto
import com.mistar.memo.domain.model.entity.Memo
import com.mistar.memo.domain.model.entity.Tag
import com.mistar.memo.domain.service.AdminService
import com.mistar.memo.domain.service.MemoService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v2/admin")
class AdminController(
    private val adminService: AdminService,
    private val memoService: MemoService
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
        ApiResponse(code = 200, message = "관리자 권한 부여 성공"),
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

    @ApiOperation("특정 사용자의 메모 삭제")
    @ApiResponses(
        ApiResponse(code = 204, message = "메모 삭제 성공"),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 401, message = "인증 안됨"),
        ApiResponse(code = 403, message = "권한 없음"),
        ApiResponse(code = 404, message = "해당 id의 메모 없음")
    )
    @DeleteMapping("/memos/{memoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMemo(
        @PathVariable memoId: Int
    ) {
        return adminService.deleteMemo(memoId)
    }

    @ApiOperation("특정 사용자의 모든 메모 조회")
    @ApiResponses(
        ApiResponse(code = 200, message = "메모 조회 성공"),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 401, message = "인증 안됨"),
        ApiResponse(code = 403, message = "권한 없음"),
        ApiResponse(code = 404, message = "해당 id의 사용자 없음")
    )
    @GetMapping("/users/{userId}/memos/{page}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun getAllMemosByUserId(
        @PathVariable userId: Int,
        @PathVariable page: Int
    ): List<Memo> {
        return memoService.selectAllMemos(userId, page)
    }

    @ApiOperation("특정 사용자의 태그가 동일한 메모 조회")
    @ApiResponses(
        ApiResponse(code = 200, message = "메모 조회 성공"),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 401, message = "인증 안됨"),
        ApiResponse(code = 403, message = "권한 없음"),
        ApiResponse(code = 404, message = "해당 id의 사용자 없음")
    )
    @GetMapping("/users/{userId}/page/{page}/tag/{tag}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun getAllMemosByUserId(
        @PathVariable userId: Int,
        @PathVariable page: Int,
        @PathVariable tag: String
    ): List<Memo> {
        return memoService.selectMemosByTag(userId, tag, page)
    }

    @ApiOperation("모든 메모 조회")
    @ApiResponses(
        ApiResponse(code = 200, message = "메모 조회 성공"),
        ApiResponse(code = 400, message = "잘못된 페이지 요청"),
        ApiResponse(code = 401, message = "인증 안됨"),
        ApiResponse(code = 403, message = "권한 없음")
    )
    @GetMapping("/memos/{page}")
    @ResponseStatus(HttpStatus.OK)
    fun getAllMemos(
        @PathVariable page: Int
    ): List<Memo> {
        return memoService.selectAllMemos(page)
    }

    @ApiOperation("태그가 동일한 모든 메모 조회")
    @ApiResponses(
        ApiResponse(code = 200, message = "메모 조회 성공"),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 401, message = "인증 안됨"),
        ApiResponse(code = 403, message = "권한 없음")
    )
    @GetMapping("/memos/{page}/tag/{tag}")
    @ResponseStatus(HttpStatus.OK)
    fun getAllMemosByTag(
        @PathVariable page: Int,
        @PathVariable tag: String
    ): List<Memo> {
        return memoService.selectMemosByTag(tag, page)
    }

    @ApiOperation("사용된 태그를 내림차순으로 n개 조회")
    @ApiResponses(
        ApiResponse(code = 200, message = "태그 조회 성공"),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 401, message = "인증 안됨"),
        ApiResponse(code = 403, message = "권한 없음")
    )
    @GetMapping("/tags/{count}")
    @ResponseStatus(HttpStatus.OK)
    fun getTagsDescending(
        @PathVariable count: Int
    ): List<Tag> {
        return adminService.getTagsDescending(count)
    }
}