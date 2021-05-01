package com.mistar.memo.domain.model.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @field:Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @Column(nullable = false)
    val username: String,

    @Column(nullable = false, length = 256)
    val password: String,

    var role: Int = 1,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val isDeleted: Boolean = false,

    val deletedAt: LocalDateTime? = null,

    @OneToMany
    val memos: MutableSet<Memo>
)