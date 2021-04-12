package com.mistar.memo.domain.model.repository

import com.mistar.memo.domain.model.entity.Memo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface MemoRepository : JpaRepository<Memo, Int> {
    fun findAllByDeletedAtBeforeAndIsDeletedIsTrue(now: LocalDateTime): List<Memo>
}