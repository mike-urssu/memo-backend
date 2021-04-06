package com.mistar.memo.application.controller

import com.mistar.memo.application.request.MemoPostRequest
import com.mistar.memo.application.response.MemoResponse
import com.mistar.memo.domain.service.MemoService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/memos")
class MemoController(
    private val memoService: MemoService
) {
    private val logger: Logger = LoggerFactory.getLogger(MemoController::class.java)

    @PostMapping("/post")
    fun createMemo(@RequestBody memoPostRequest: MemoPostRequest) {
        logger.info("/v1/memos/post")

        val memoPostDto = memoPostRequest.toMemoPostDto()
        return memoService.createMemoAndTags(memoPostDto)
    }

    @GetMapping("/list/{page}")
    fun selectAllMemos(
        @PathVariable page: Int
    ): MemoResponse {
        logger.info("/v1/memos/list/$page")

        val memos = memoService.selectAllMemos(page)
        return MemoResponse(memos)
    }

    @GetMapping("/{memoId}")
    fun selectMemosById(
        @PathVariable memoId: Int,
    ): MemoResponse {
        logger.info("/v1/memos/$memoId")

        val memos = memoService.selectMemosById(memoId)
        return MemoResponse(memos)
    }

    @GetMapping("/list/tag/{tag}/{page}")
    fun selectMemosByTag(
        @PathVariable tag: String,
        @PathVariable page: Int
    ): MemoResponse {
        logger.info("/v1/memos/list/tag/$tag/$page")

        val memos = memoService.selectMemosByTag(tag, page)
        return MemoResponse(memos)
    }

    @DeleteMapping("/delete/{memoId}")
    fun deleteMemo(
        @PathVariable memoId: Int
    ) {
        logger.info("/v1/memos/delete/$memoId")

        return memoService.deleteMemo(memoId)
    }
}