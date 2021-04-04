package com.mistar.memo.domain.service

import com.mistar.memo.domain.model.dto.MemoPostDto
import com.mistar.memo.domain.model.entity.Memo
import com.mistar.memo.domain.model.entity.Tag
import com.mistar.memo.domain.model.repository.MemoRepository
import com.mistar.memo.domain.model.repository.TagRepository
import org.springframework.stereotype.Service

@Service
class MemoService(
    private val memoRepository: MemoRepository,
    private val tagRepository: TagRepository
) {
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
}