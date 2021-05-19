package com.mistar.memo.domain.model.repository

import com.mistar.memo.domain.model.entity.Tag
import reactor.core.publisher.Mono

interface TagRxRepository {
    fun save(tag: Tag): Mono<Unit>
}