package com.mistar.memo.domain.model.repository

import com.mistar.memo.domain.model.entity.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : JpaRepository<Tag, Int> {
    fun findByContentContaining(content: String): List<Tag>

    fun existsByMemoIdAndContent(memoId: Int, content: String): Boolean

    fun deleteByMemoId(memoId: Int)

    @Query(
        """
            select t from Tag t
            group by t.content
            order by t.content desc
        """
    )
    fun findTagsOrderByContentDesc(page: Pageable): List<Tag>
}