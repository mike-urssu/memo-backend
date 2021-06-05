package com.mistar.memo.domain.model.repository

import com.mistar.memo.domain.model.entity.RefreshToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository : CrudRepository<RefreshToken, Int>