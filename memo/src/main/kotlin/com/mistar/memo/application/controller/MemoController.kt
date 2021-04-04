package com.mistar.memo.application.controller

import com.mistar.memo.application.request.MemoPostRequest
import com.mistar.memo.domain.service.MemoService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}