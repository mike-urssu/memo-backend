package com.mistar.memo.domain.model.entity

import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "memos")
@DynamicUpdate
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

    @Column
    var isDeleted: Boolean = false,

    @Column
    var deletedAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "memoId")
    var tags: MutableSet<Tag>
)