package com.mistar.memo.domain.model.entity

import com.mistar.memo.domain.model.entity.flag.Flag
import com.mistar.memo.domain.model.entity.flag.FlagDefinition

data class Role(override val value: Int) : Flag<Role.Flag>() {

    enum class Flag(override val value: Int) : FlagDefinition {
        DELETE(0),
        USER(1),
        ADMIN(8)
    }
}