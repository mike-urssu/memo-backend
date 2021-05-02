package com.mistar.memo.core.utils

import org.springframework.security.core.context.SecurityContextHolder

object ControllerUtils {
    fun getUserIdFromAuthentication(): Int = SecurityContextHolder.getContext().authentication.name.toInt()
}