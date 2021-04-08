package com.mistar.memo.application.controller

import com.mistar.memo.application.request.MemoPatchRequest
import com.mistar.memo.application.request.MemoPostRequest
import com.mistar.memo.application.response.MemoResponse
import com.mistar.memo.domain.service.MemoService
import io.swagger.annotations.ApiOperation
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
    fun selectAllMemos(
        @PathVariable page: Int
    ): MemoResponse {
        logger.info("/v1/memos/list/$page")

        val memos = memoService.selectAllMemos(page)
        return MemoResponse(memos)
    }

    @ApiOperation("특정 id의 메모 불러오기")
    @GetMapping("/{memoId}")
    fun selectMemosById(
        @PathVariable memoId: Int,
    ): MemoResponse {
        logger.info("/v1/memos/$memoId")

        val memos = memoService.selectMemosById(memoId)
        return MemoResponse(memos)
    }

    @ApiOperation("태그가 동일한 메모 불러오기")
    @GetMapping("/list/tag/{tag}/{page}")
    fun selectMemosByTag(
        @PathVariable tag: String,
        @PathVariable page: Int
    ): MemoResponse {
        logger.info("/v1/memos/list/tag/$tag/$page")

        val memos = memoService.selectMemosByTag(tag, page)
        return MemoResponse(memos)
    }

    @ApiOperation("메모 수정하기")
    @PatchMapping("/patch/{memoId}")
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
    fun deleteMemo(
        @PathVariable memoId: Int
    ) {
        logger.info("/v1/memos/delete/$memoId")

        return memoService.deleteMemo(memoId)
    }
}