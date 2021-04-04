package com.mistar.memo.model.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "memos")
data class Memo(
    @field:Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column
    var title: String? = null,

    @Column(columnDefinition = "TEXT")
    var content: String,

    @Column
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column
    var isPublic: Boolean = true,

    @OneToMany(mappedBy = "memoId")
    var tags: List<Tag>
)