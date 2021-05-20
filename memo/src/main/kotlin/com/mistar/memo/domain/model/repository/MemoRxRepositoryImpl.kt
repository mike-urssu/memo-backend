package com.mistar.memo.domain.model.repository

import com.mistar.memo.domain.model.entity.Memo
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Repository
class MemoRxRepositoryImpl(
    private val memoRepository: MemoRepository
) : MemoRxRepository {
    override fun save(memo: Memo): Mono<Memo> {
        return Mono.fromCallable { memoRepository.save(memo) }
            .subscribeOn(Schedulers.boundedElastic())
    }

    override fun findAllByUserIdAndIsDeletedAndIsPublic(
        page: Pageable,
        userId: Int,
        isDeleted: Boolean,
        isPublic: Boolean
    ): Flux<Memo> {
        return Mono.fromCallable {
            memoRepository.findAllByUserIdAndIsDeletedAndIsPublic(
                page,
                userId,
                isDeleted,
                isPublic
            )
        }
            .subscribeOn(Schedulers.boundedElastic())
            .flatMapMany { Flux.fromIterable(it) }
    }
}