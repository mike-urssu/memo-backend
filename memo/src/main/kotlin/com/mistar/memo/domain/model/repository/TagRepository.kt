package com.mistar.memo.domain.model.repository

import com.mistar.memo.domain.model.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : JpaRepository<Tag, Int> {
}