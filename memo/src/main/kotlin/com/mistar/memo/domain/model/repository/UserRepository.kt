package com.mistar.memo.domain.model.repository

import com.mistar.memo.domain.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {

}