package com.mistar.memo.core.common

import org.mindrot.jbcrypt.BCrypt

object Salt {
    fun encryptPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun matchPassword(password: String, encryptedPassword: String): Boolean {
        return BCrypt.checkpw(password, encryptedPassword)
    }
}