package com.mistar.memo.application.request

import com.mistar.memo.core.utils.SHA256
import com.mistar.memo.domain.model.dto.UserSignupDto

class UserSignupRequest(
    val username: String,
    val password: String
) {
    fun toUserSignupDto() = UserSignupDto(username, SHA256.encryptPassword(password))
}