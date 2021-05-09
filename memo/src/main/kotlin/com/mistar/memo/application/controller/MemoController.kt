package com.mistar.memo.application.controller

import com.mistar.memo.application.request.MemoPatchRequest
import com.mistar.memo.application.request.MemoPostRequest
import com.mistar.memo.application.response.MemoResponse
import com.mistar.memo.domain.service.MemoService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/memos")
class MemoController(
    private val memoService: MemoService
) {
    private val logger: Logger = LoggerFactory.getLogger(MemoController::class.java)

    @ApiOperation("메모 작성하기")
    @ApiResponses(
        ApiResponse(code = 201, message = "메모 작성 성공"),
        ApiResponse(code = 400, message = "잘못된 요청")
    )
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    fun createMemo(
        @RequestBody memoPostRequest: MemoPostRequest
    ) {
        logger.info("/v1/memos/post")

        val memoPostDto = memoPostRequest.toMemoPostDto()
        return memoService.createMemo(memoPostDto)
    }

    @ApiOperation("모든 메모 불러오기")
    @ApiResponses(
        ApiResponse(code = 200, message = "모든 메모 조회 성공", response = MemoResponse::class),
        ApiResponse(code = 400, message = "잘못된 요청")
    )
    @GetMapping("/list/{page}")
    @ResponseStatus(HttpStatus.OK)
    fun selectAllMemos(
        @PathVariable page: Int
    ): MemoResponse {
        logger.info("/v1/memos/list/$page")

        val memos = memoService.selectAllMemos(page)
        return MemoResponse(memos)
    }

    @ApiOperation("특정 id의 메모 불러오기")
    @ApiResponses(
        ApiResponse(code = 200, message = "해당 id의 메모 조회 성공", response = MemoResponse::class),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 404, message = "해당 id의 메모 없음")
    )
    @GetMapping("/{memoId}")
    @ResponseStatus(HttpStatus.OK)
    fun selectMemosById(
        @PathVariable memoId: Int,
    ): MemoResponse {
        logger.info("/v1/memos/$memoId")

        val memos = memoService.selectMemosById(memoId)
        return MemoResponse(memos)
    }

    @ApiOperation("태그가 동일한 메모 불러오기")
    @ApiResponses(
        ApiResponse(code = 200, message = "동일 태그에 대한 메모 조회 성공", response = MemoResponse::class),
        ApiResponse(code = 400, message = "잘못된 요청")
    )
    @GetMapping("/list/{page}/tags/{tag}")
    @ResponseStatus(HttpStatus.OK)
    fun selectMemosByTag(
        @PathVariable tag: String,
        @PathVariable page: Int
    ): MemoResponse {
        logger.info("/v1/memos/list/$page/tag/$tag")

        val memos = memoService.selectMemosByTag(tag, page)
        return MemoResponse(memos)
    }

    @ApiOperation("메모 수정하기")
    @ApiResponses(
        ApiResponse(code = 200, message = "메모 수정 성공"),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 404, message = "해당 id의 메모 없음")
    )
    @PutMapping("/patch/{memoId}")
    @ResponseStatus(HttpStatus.OK)
    fun patchMemo(
        @PathVariable memoId: Int,
        @RequestBody memoPatchRequest: MemoPatchRequest
    ) {
        logger.info("/v1/memos/patch/$memoId")

        val memoPatchDto = memoPatchRequest.toMemoPatchDto()
        return memoService.patchMemo(memoId, memoPatchDto)
    }

    @ApiOperation("메모 삭제하기")
    @ApiResponses(
        ApiResponse(code = 204, message = "메모 삭제 성공"),
        ApiResponse(code = 400, message = "잘못된 요청"),
        ApiResponse(code = 404, message = "해당 id의 메모 없음")
    )
    @DeleteMapping("/delete/{memoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMemo(
        @PathVariable memoId: Int
    ) {
        logger.info("/v1/memos/delete/$memoId")

        return memoService.deleteMemo(memoId)
    }
}