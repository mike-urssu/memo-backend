package com.mistar.memo.domain.model.entity.memo

import javax.persistence.*

@Entity
@Table(name = "tags")
data class Tag(
    @field:Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    var memoId: Int? = null,

    @Column(nullable = false, length = 50)
    val content: String
)