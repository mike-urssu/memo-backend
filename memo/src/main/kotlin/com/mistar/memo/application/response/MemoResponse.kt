package com.mistar.memo.application.response

import com.mistar.memo.domain.model.dto.MemoDto

data class MemoResponse(
    val memos: MemoDto
)