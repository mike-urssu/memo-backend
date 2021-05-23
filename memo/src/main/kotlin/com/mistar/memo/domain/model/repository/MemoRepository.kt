package com.mistar.memo.domain.model.repository

import com.mistar.memo.domain.model.entity.Memo
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface MemoRepository : JpaRepository<Memo, Int> {
    fun findAllByUserIdAndIsDeletedIsFalseAndIsPublicIsTrue(userId: Int): List<Memo>

    fun findAllByUserIdAndIsDeletedIsFalseAndIsPublicIsTrue(page: Pageable, userId: Int): List<Memo>

    fun findByIdAndIsDeletedIsFalseAndIsPublicIsTrue(memoId: Int): Optional<Memo>

    fun findAllByUserIdAndIsDeletedAndIsPublic(
        page: Pageable,
        userId: Int,
        isDeleted: Boolean,
        isPublic: Boolean
    ): List<Memo>

    fun findAllByDeletedAtBeforeAndIsDeletedIsTrue(now: LocalDateTime): List<Memo>

    fun existsByUserIdAndId(userId: Int, memoId: Int): Boolean

    fun findByIdAndIsDeletedIsFalse(memoId: Int): Optional<Memo>

    fun findAllByIsDeletedIsFalse(): List<Memo>

    fun findAllByIsDeletedIsFalse(page: Pageable): List<Memo>
}