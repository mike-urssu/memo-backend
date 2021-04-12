package com.mistar.memo.domain.model.repository

import com.mistar.memo.domain.model.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : JpaRepository<Tag, Int> {
    @Query(
        """
            select t from Tag t
            inner join Memo m on m.id = t.memoId
            where m.isDeleted = false and m.isPublic = true
        """
    )
    fun findByContentContaining(content: String): List<Tag>

    fun existsByMemoIdAndContent(memoId: Int, content: String): Boolean

    fun deleteByMemoId(memoId: Int)
}