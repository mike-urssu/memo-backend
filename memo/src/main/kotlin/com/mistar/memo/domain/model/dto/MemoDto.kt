package com.mistar.memo.domain.model.dto

import com.mistar.memo.domain.model.entity.Memo
import java.time.LocalDateTime

class MemoDto(memo: Memo) {
    val id: Int = memo.id!!
    val title: String? = memo.title
    val content: String = memo.content
    val createdAt: LocalDateTime = memo.createdAt
    val tags: List<String> = memo.getTags()
}