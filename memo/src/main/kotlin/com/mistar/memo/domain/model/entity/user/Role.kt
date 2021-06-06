package com.mistar.memo.domain.model.entity.user

import com.mistar.memo.domain.model.entity.user.flag.Flag
import com.mistar.memo.domain.model.entity.user.flag.FlagDefinition

data class Role(override val value: Int) : Flag<Role.Flag>() {

    enum class Flag(override val value: Int) : FlagDefinition {
        DELETE(0),
        USER(1),
        ADMIN(8)
    }
}