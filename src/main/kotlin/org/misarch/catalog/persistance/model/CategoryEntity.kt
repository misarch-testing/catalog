package org.misarch.catalog.persistance.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table
class CategoryEntity(
    val name: String,
    val description: String,
    @Id
    val id: UUID?
)