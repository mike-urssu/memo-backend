package com.mistar.memo.domain.model.entity.memo

import com.mistar.memo.domain.model.entity.user.User
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "memos")
@DynamicUpdate
data class Memo(
    @field:Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(length = 100)
    var title: String? = null,

    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    var isPublic: Boolean = true,

    var isDeleted: Boolean = false,

    var deletedAt: LocalDateTime? = null,

    @OneToMany(mappedBy = "memoId", fetch = FetchType.EAGER)
    var tags: MutableSet<Tag>,

    @ManyToOne
    var user: User
)