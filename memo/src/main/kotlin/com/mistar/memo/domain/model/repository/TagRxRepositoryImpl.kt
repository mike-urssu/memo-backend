package com.mistar.memo.domain.model.repository

import com.mistar.memo.domain.model.entity.Tag
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Repository
class TagRxRepositoryImpl(
    private val tagRepository: TagRepository
) : TagRxRepository {
    override fun save(tag: Tag): Mono<Unit> {
        return Mono.fromCallable { tagRepository.save(tag) }
            .subscribeOn(Schedulers.boundedElastic())
    }
}