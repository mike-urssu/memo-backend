package com.mistar.memo.domain.model.repository

import com.mistar.memo.domain.model.entity.Memo
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface MemoRxRepository {
    fun save(memo: Memo): Mono<Memo>

    fun findAllByUserIdAndIsDeletedAndIsPublic(
        page: Pageable,
        userId: Int,
        isDeleted: Boolean,
        isPublic: Boolean
    ): Flux<Memo>

    fun findByUserIdAndIdAndIsDeleted(
        userId: Int,
        memoId: Int,
        isDeleted: Boolean
    ): Mono<Optional<Memo>>
}