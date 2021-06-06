package com.mistar.memo.domain.model.repository

import com.mistar.memo.domain.model.entity.user.Black
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BlackListRepository : CrudRepository<Black, String>