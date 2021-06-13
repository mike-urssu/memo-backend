package com.mistar.memo.application.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.mistar.memo.domain.model.dto.UserSignOutDto

data class UserSignOutRequest(
    @JsonProperty("refreshToken")
    val refreshToken: String
) {
    fun toUserSignOutDto() = UserSignOutDto(refreshToken)
}