package com.mistar.memo.domain.model.dto

import com.mistar.memo.domain.model.entity.user.User

class UserInfoDto(user: User) {
    val userId = user.id!!
    val username = user.username
    val roles = user.getUserRoles()
    val createdAt = user.createdAt
}