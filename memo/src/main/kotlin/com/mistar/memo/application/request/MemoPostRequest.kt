package com.mistar.memo.application.request

import com.mistar.memo.domain.model.dto.MemoPostDto
import com.mistar.memo.domain.model.entity.Tag

class MemoPostRequest(
    private val title: String?,

    private val content: String,

    private val isPublic: Boolean = true,

    private val tags: List<String>
) {
    fun toMemoPostDto(): MemoPostDto {
        val tags = LinkedHashSet<Tag>()
        for (tag in this.tags)
            tags.add(Tag(memoId = null, content = tag))
        return MemoPostDto(title, content, isPublic, tags)
    }
}