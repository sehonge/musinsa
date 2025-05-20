package kr.musinsa.domain.common

import jakarta.persistence.*
import kr.musinsa.domain.common.util.TimeZone.KST
import java.time.ZonedDateTime

@MappedSuperclass
class BaseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "id",
        updatable = false,
        nullable = false,
    )
    val id: Long? = null,

    @Column(
        name = "created_at",
        updatable = false,
        nullable = false,
    )
    val createdAt: ZonedDateTime = ZonedDateTime.now(KST),

    @Column(
        name = "updated_at",
        updatable = true,
        nullable = false,
    )
    var updatedAt: ZonedDateTime = ZonedDateTime.now(KST),
)
