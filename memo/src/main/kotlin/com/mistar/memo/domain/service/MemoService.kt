package com.mistar.memo.domain.service

import com.mistar.memo.application.controller.MemoController
import com.mistar.memo.domain.exception.InvalidPageException
import com.mistar.memo.domain.exception.MemoNotFoundException
import com.mistar.memo.domain.exception.PageOutOfBoundsException
import com.mistar.memo.domain.model.common.Page
import com.mistar.memo.domain.model.dto.MemoPostDto
import com.mistar.memo.domain.model.entity.Memo
import com.mistar.memo.domain.model.entity.Tag
import com.mistar.memo.domain.model.repository.MemoRepository
import com.mistar.memo.domain.model.repository.TagRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MemoService(
    private val memoRepository: MemoRepository,
    private val tagRepository: TagRepository
) {
    private val logger: Logger = LoggerFactory.getLogger(MemoController::class.java)
    private val defaultPageSize = 10

    fun createMemoAndTags(memoPostDto: MemoPostDto) {
        val memo = memoRepository.save(
            Memo(
                title = memoPostDto.title,
                content = memoPostDto.content,
                isPublic = memoPostDto.isPublic,
                tags = memoPostDto.tags
            )
        )
        createTags(memo, memoPostDto.tags)
    }

    private fun createTags(memo: Memo, tags: List<Tag>) {
        for (tag in tags) {
            tag.memoId = memo.id
            tagRepository.save(tag)
        }
    }

    fun selectAllMemos(page: Int): List<Memo> {
        if (page < 1)
            throw InvalidPageException()
        val memoCnt = memoRepository.findAll().size
        if (memoCnt < (page - 1) * 10)
            throw PageOutOfBoundsException()

        val requestedPage = Page(page - 1, defaultPageSize)
        return memoRepository.findAll(requestedPage).toList()
    }

    fun selectMemosById(memoId: Int): List<Memo> {
        val memo = memoRepository.findById(memoId).orElseThrow { MemoNotFoundException() }
        return listOf(memo)
    }
}