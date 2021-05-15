package com.mistar.memo.application.request

import com.mistar.memo.domain.model.dto.MemoPatchDto
import com.mistar.memo.domain.model.entity.Tag

class MemoPatchRequest(
    private val title: String?,
    private val content: String?,
    private val isPublic: Boolean?,
    private val tags: List<String>?
) {
    fun toMemoPatchDto(): MemoPatchDto {
        val tags = LinkedHashSet<Tag>()
        if (this.tags.isNullOrEmpty())
            return MemoPatchDto(title, content, isPublic, tags)
        for (tag in this.tags)
            tags.add(Tag(content = tag))
        return MemoPatchDto(title, content, isPublic, tags)
    }
}