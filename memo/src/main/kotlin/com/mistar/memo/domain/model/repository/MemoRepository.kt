package com.mistar.memo.domain.model.repository

import com.mistar.memo.domain.model.entity.Memo
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface MemoRepository : JpaRepository<Memo, Int> {
    fun findAllByIsDeletedIsFalseAndIsPublicIsTrue(): List<Memo>

    fun findAllByIsDeletedIsFalseAndIsPublicIsTrue(page: Pageable): List<Memo>

    fun findByIdAndIsDeletedIsFalseAndIsPublicIsTrue(memoId: Int): Optional<Memo>

    fun findAllByDeletedAtBeforeAndIsDeletedIsTrue(now: LocalDateTime): List<Memo>
}