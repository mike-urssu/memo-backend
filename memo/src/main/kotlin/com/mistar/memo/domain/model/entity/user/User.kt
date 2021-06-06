package com.mistar.memo.domain.model.entity.user

import com.mistar.memo.domain.model.entity.user.flag.and
import com.mistar.memo.domain.model.enums.UserRole
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @field:Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false)
    val username: String,

    @Column(nullable = false, length = 60)
    val password: String,

    @Column(nullable = false)
    var roleFlag: Int = 1,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    var isDeleted: Boolean = false,

    var deletedAt: LocalDateTime? = null,
) {
    fun getUserRoles(): List<UserRole> {
        val role = mutableListOf<UserRole>()
        if ((roleFlag.and(Role.Flag.USER.value)) == Role.Flag.USER.value)
            role.add(UserRole.ROLE_USER)
        if ((roleFlag == Role((Role.Flag.ADMIN and Role.Flag.USER).value).value))
            role.add(UserRole.ROLE_ADMIN)
        return role
    }
}