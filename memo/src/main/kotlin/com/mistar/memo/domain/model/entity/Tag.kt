package com.mistar.memo.domain.model.entity

import javax.persistence.*

@Entity
@Table(name = "tags")
data class Tag(
    @field:Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column
    var memoId: Int?,

    @Column(length = 50)
    val content: String
)