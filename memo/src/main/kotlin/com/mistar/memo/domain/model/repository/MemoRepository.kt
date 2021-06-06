package com.mistar.memo.domain.model.repository

import com.mistar.memo.domain.model.entity.memo.Memo
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface MemoRepository : JpaRepository<Memo, Int> {
    fun findAllByUserIdAndIsDeletedAndIsPublic(userId: Int, isDeleted: Boolean, isPublic: Boolean): List<Memo>

    fun findAllByUserIdAndIsDeletedAndIsPublic(page: Pageable, userId: Int, isDeleted: Boolean, isPublic: Boolean): List<Memo>

    fun findByIdAndIsDeletedAndIsPublic(memoId: Int, isDeleted: Boolean, isPublic: Boolean): Optional<Memo>

    fun findAllByDeletedAtBeforeAndIsDeleted(now: LocalDateTime, isDeleted: Boolean): List<Memo>

    fun existsByUserIdAndId(userId: Int, memoId: Int): Boolean

    fun findByIdAndIsDeleted(memoId: Int, isDeleted: Boolean): Optional<Memo>

    fun findAllByIsDeleted(isDeleted: Boolean): List<Memo>

    fun findAllByIsDeleted(page: Pageable, isDeleted: Boolean): List<Memo>
}