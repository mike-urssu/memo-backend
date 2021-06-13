package com.mistar.memo.application.request

import com.mistar.memo.core.utils.SHA256
import com.mistar.memo.domain.model.dto.UserSignInDto

data class UserSignInRequest(
    val username: String,
    val password: String
) {
    fun toUserSignInDto() = UserSignInDto(username, SHA256.encryptPassword(password))
}