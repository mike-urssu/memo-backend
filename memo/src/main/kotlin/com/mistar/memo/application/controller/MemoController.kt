package com.mistar.memo.application.controller

import com.mistar.memo.application.request.MemoPatchRequest
import com.mistar.memo.application.request.MemoPostRequest
import com.mistar.memo.application.response.MemoResponse
import com.mistar.memo.core.utils.ControllerUtils
import com.mistar.memo.domain.model.dto.MemoDto
import com.mistar.memo.domain.service.MemoService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class MemoController(
    private val memoService: MemoService
) {
    @ApiOperation("자신의 메모 작성하기")
    @ApiResponses(
        ApiResponse(code = 201, message = "메모 작성 성공"),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 401, message = "인증 안됨"),
        ApiResponse(code = 403, message = "권한 없음")
    )
    @PostMapping("/v2/memo")
    @ResponseStatus(HttpStatus.CREATED)
    fun createMemo(
        @RequestBody memoPostRequest: MemoPostRequest
    ): Flux<Unit> {
        val userId = ControllerUtils.getUserIdFromAuthentication()
        val memoPostDto = memoPostRequest.toMemoPostDto()
        return memoService.createMemo(userId, memoPostDto)
    }

    @ApiOperation("자신의 모든 메모 조회하기")
    @ApiResponses(
        ApiResponse(code = 200, message = "모든 메모 조회 성공", response = MemoResponse::class),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 401, message = "인증 안됨"),
        ApiResponse(code = 403, message = "권한 없음")
    )
    @GetMapping("/v2/memos/{page}")
    @ResponseStatus(HttpStatus.OK)
    fun getMemos(
        @PathVariable page: Int
    ): Mono<MemoResponse> {
        val userId = ControllerUtils.getUserIdFromAuthentication()
        return memoService.getMemos(userId, page)
            .map {
                MemoDto(it)
            }.collectList()
            .map {
                MemoResponse(it)
            }
    }

    @ApiOperation("특정 id의 메모 불러오기")
    @ApiResponses(
        ApiResponse(code = 200, message = "해당 id의 메모 조회 성공", response = MemoResponse::class),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 401, message = "인증 안됨"),
        ApiResponse(code = 403, message = "권한 없음"),
        ApiResponse(code = 404, message = "해당 id의 메모 없음")
    )
    @GetMapping("/{memoId}")
    @ResponseStatus(HttpStatus.OK)
    fun selectMemosById(
        @PathVariable memoId: Int,
    ): MemoResponse {
        val userId = ControllerUtils.getUserIdFromAuthentication()
        val memos = memoService.selectMemosById(userId, memoId)
            .map {
                MemoDto(it)
            }
        return MemoResponse(memos)
    }

    @ApiOperation("태그가 동일한 메모 불러오기")
    @ApiResponses(
        ApiResponse(code = 200, message = "동일 태그에 대한 메모 조회 성공", response = MemoResponse::class),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 401, message = "인증 안됨"),
        ApiResponse(code = 403, message = "권한 없음")
    )
    @GetMapping("/list/{page}/tags/{tag}")
    @ResponseStatus(HttpStatus.OK)
    fun selectMemosByTag(
        @PathVariable tag: String,
        @PathVariable page: Int
    ): MemoResponse {
        val userId = ControllerUtils.getUserIdFromAuthentication()
        val memos = memoService.selectMemosByTag(userId, tag, page)
            .map {
                MemoDto(it)
            }
        return MemoResponse(memos)
    }

    @ApiOperation("메모 수정하기")
    @ApiResponses(
        ApiResponse(code = 200, message = "메모 수정 성공"),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 401, message = "인증 안됨"),
        ApiResponse(code = 403, message = "권한 없음"),
        ApiResponse(code = 404, message = "해당 id의 메모 없음")
    )
    @PutMapping("/patch/{memoId}")
    @ResponseStatus(HttpStatus.OK)
    fun patchMemo(
        @PathVariable memoId: Int,
        @RequestBody memoPatchRequest: MemoPatchRequest
    ) {
        val userId = ControllerUtils.getUserIdFromAuthentication()
        val memoPatchDto = memoPatchRequest.toMemoPatchDto()
        return memoService.patchMemo(userId, memoId, memoPatchDto)
    }

    @ApiOperation("메모 삭제하기")
    @ApiResponses(
        ApiResponse(code = 204, message = "메모 삭제 성공"),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 401, message = "인증 안됨"),
        ApiResponse(code = 403, message = "권한 없음"),
        ApiResponse(code = 404, message = "해당 id의 메모 없음")
    )
    @DeleteMapping("/delete/{memoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMemo(
        @PathVariable memoId: Int
    ) {
        val userId = ControllerUtils.getUserIdFromAuthentication()
        return memoService.deleteMemo(userId, memoId)
    }
}