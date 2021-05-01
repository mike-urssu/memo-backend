package com.mistar.memo.application.request

import com.mistar.memo.core.common.SHA256
import com.mistar.memo.domain.model.dto.UserSignInDto

class UserSignInRequest(
    private val username: String,
    private val password: String
) {
    fun toUserSignInDto() = UserSignInDto(username, SHA256.encryptPassword(password))
}