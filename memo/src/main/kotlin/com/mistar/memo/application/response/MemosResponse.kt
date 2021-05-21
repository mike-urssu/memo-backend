package com.mistar.memo.application.response

import com.mistar.memo.domain.model.dto.MemoDto

data class MemosResponse(
    val memos: List<MemoDto>
)