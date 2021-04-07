package com.mistar.memo.domain.service

import com.mistar.memo.application.controller.MemoController
import com.mistar.memo.domain.exception.InvalidPageException
import com.mistar.memo.domain.exception.MemoNotFoundException
import com.mistar.memo.domain.exception.PageOutOfBoundsException
import com.mistar.memo.domain.model.common.Page
import com.mistar.memo.domain.model.dto.MemoPatchDto
import com.mistar.memo.domain.model.dto.MemoPostDto
import com.mistar.memo.domain.model.entity.Memo
import com.mistar.memo.domain.model.entity.Tag
import com.mistar.memo.domain.model.repository.MemoRepository
import com.mistar.memo.domain.model.repository.TagRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemoService(
    private val memoRepository: MemoRepository,
    private val tagRepository: TagRepository
) {
    private val logger: Logger = LoggerFactory.getLogger(MemoController::class.java)
    private val defaultPageSize = 10

    fun createMemo(memoPostDto: MemoPostDto) {
        val memo = memoRepository.save(
            Memo(
                title = memoPostDto.title,
                content = memoPostDto.content,
                isPublic = memoPostDto.isPublic,
                tags = memoPostDto.tags
            )
        )
        createTags(memo.id!!, memoPostDto.tags)
    }

    private fun createTags(memoId: Int, tags: Set<Tag>) {
        for (tag in tags) {
            tag.memoId = memoId
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

    fun selectMemosByTag(tag: String, page: Int): List<Memo> {
        if (page < 1)
            throw InvalidPageException()
        val requestedPage = Page(page, defaultPageSize)

        val tags = tagRepository.findByContentContaining(tag)
        val memoIds = LinkedHashSet<Int>()
        for (tag in tags)
            memoIds.add(tag.memoId!!)
        val memos = memoRepository.findAllById(memoIds)
        val memoCnt = memos.size
        if (memoCnt < (page - 1) * 10)
            throw PageOutOfBoundsException()
        for (memo in memos) {
            logger.info("memoId: ${memo.id}")
        }
        return memos
    }

    @Transactional
    fun patchMemo(memoId: Int, memoPatchDto: MemoPatchDto) {
        val memo = memoRepository.findById(memoId).orElseThrow { MemoNotFoundException() }
        if (memoPatchDto.title != null)
            memo.title = memoPatchDto.title
        if (memoPatchDto.content != null)
            memo.content = memoPatchDto.content
        if (memoPatchDto.isPublic != null)
            memo.isPublic = memoPatchDto.isPublic

        if (memoPatchDto.tags.isNotEmpty()) {
            tagRepository.deleteByMemoId(memoId)
            memo.tags.clear()
            memo.tags = memoPatchDto.tags
            for (tag in memoPatchDto.tags)
                tagRepository.save(Tag(memoId = memoId, content = tag.content))
        }
    }

    fun deleteMemo(memoId: Int) {
        val memo = memoRepository.findById(memoId).orElseThrow { MemoNotFoundException() }
        memo.isDeleted = true
        memoRepository.save(memo)
    }
}