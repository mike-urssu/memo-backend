package com.mistar.memo.application.controller

import com.mistar.memo.application.request.MemoPatchRequest
import com.mistar.memo.application.request.MemoPostRequest
import com.mistar.memo.application.response.MemoResponse
import com.mistar.memo.core.security.JwtTokenProvider
import com.mistar.memo.domain.service.MemoService
import io.swagger.annotations.ApiOperation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/v1/memos")
class MemoController(
    private val memoService: MemoService
) {
    private val logger: Logger = LoggerFactory.getLogger(MemoController::class.java)

    @ApiOperation("메모 생성하기")
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
    @PatchMapping("/patch/{memoId}")
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
    @DeleteMapping("/delete/{memoId}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteMemo(
        @PathVariable memoId: Int
    ) {
        logger.info("/v1/memos/delete/$memoId")

        return memoService.deleteMemo(memoId)
    }
}