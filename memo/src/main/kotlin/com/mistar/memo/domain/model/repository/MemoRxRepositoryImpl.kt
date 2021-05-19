package com.mistar.memo.domain.model.repository

import com.mistar.memo.domain.model.entity.Memo
import org.springframework.stereotype.Repository
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
}