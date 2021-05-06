package com.mistar.memo.domain.model.repository

import com.mistar.memo.domain.model.entity.User
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun existsByUsername(username: String): Boolean

    fun findByUsername(username: String): Optional<User>

    fun findAllByIsDeletedIsFalse(page: Pageable): List<User>

    fun countByIsDeletedIsFalse(): Long
}