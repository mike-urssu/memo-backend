package com.mistar.memo.model.entity

import javax.persistence.*

@Entity
@Table(name = "tags")
data class Tag(
    @field:Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(length = 50)
    val content: String,

    @Column
    val memoId: Int
)