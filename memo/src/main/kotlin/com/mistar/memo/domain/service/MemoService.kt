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
        val memo = memoRepository.findById(memoId).orElseThrow {
            MemoNotFoundException()
        }
        return listOf(memo)
    }

    fun selectMemosByTag(tag: String, page: Int): List<Memo> {
        if (page < 1)
            throw InvalidPageException()

        val memoIds = getMemoIds(tag)
        return when {
            memoIds.size / 10 < page - 1 -> {
                throw PageOutOfBoundsException()
            }
            memoIds.size / 10 == page - 1 -> {
                memoRepository.findAllById(memoIds)
                    .subList((page - 1) * defaultPageSize, (page - 1) * defaultPageSize + (memoIds.size % 10))
            }
            else -> {
                memoRepository.findAllById(memoIds)
                    .subList((page - 1) * defaultPageSize, page * defaultPageSize)
            }
        }
    }

    private fun getMemoIds(content: String): LinkedHashSet<Int> {
        val memoIds = linkedSetOf<Int>()
        val tags = tagRepository.findByContentContaining(content)
        for (tag in tags)
            memoIds.add(tag.memoId!!)
        return memoIds
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

        deleteTags(memo, memoPatchDto)
        saveTags(memo, memoPatchDto)
    }

    private fun deleteTags(memo: Memo, memoPatchDto: MemoPatchDto) {
        val contents = arrayListOf<String>()
        val tagsToDelete = arrayListOf<Tag>()

        for (tag in memoPatchDto.tags)
            contents.add(tag.content)

        for (tag in memo.tags)
            if (!contents.contains(tag.content))
                tagsToDelete.add(tag)

        for (tag in tagsToDelete) {
            tagRepository.delete(tag)
            memo.tags.remove(tag)
        }
    }

    private fun saveTags(memo: Memo, memoPatchDto: MemoPatchDto) {
        for (tag in memoPatchDto.tags) {
            if (!tagRepository.existsByMemoIdAndContent(memo.id!!, tag.content)) {
                logger.info(tag.content)
                memo.tags.add(tag)
                tag.memoId = memo.id
                tagRepository.save(tag)
            }
        }
    }

    fun deleteMemo(memoId: Int) {
        val memo = memoRepository.findById(memoId).orElseThrow {
            MemoNotFoundException()
        }

        memo.isDeleted = true
        memoRepository.save(memo)
    }
}