package com.mistar.memo.domain.model.dto

import com.mistar.memo.domain.model.entity.Memo

data class MemoDto(
    val memo: Memo
) {
    val id = memo.id!!
    val title = memo.title
    val content = memo.content
    val createdAt = memo.createdAt
}