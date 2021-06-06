package com.mistar.memo.application.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.mistar.memo.domain.model.dto.UserSignOutDto

class UserSignOutRequest(
    @JsonProperty("refreshToken")
    private val refreshToken: String
) {
    fun toUserSignOutDto() = UserSignOutDto(refreshToken)
}