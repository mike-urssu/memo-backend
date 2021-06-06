package com.mistar.memo.application.response

import com.mistar.memo.domain.model.entity.memo.Memo

data class MemoResponse(
    val memos: List<Memo>
)