package com.mistar.memo.domain.model.repository

import com.mistar.memo.domain.model.entity.Memo
import reactor.core.publisher.Mono

interface MemoRxRepository {
    fun save(memo: Memo): Mono<Memo>
}