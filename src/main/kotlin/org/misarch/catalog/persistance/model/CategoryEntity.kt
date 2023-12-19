package org.misarch.catalog.persistance.model

import org.misarch.catalog.graphql.model.Category
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table
class CategoryEntity(
    val name: String,
    val description: String,
    @Id
    val id: UUID?
) {

    companion object {
        val ENTITY = QCategoryEntity.categoryEntity!!
    }

    fun toDTO(): Category {
        return Category(
            id = id!!,
            name = name,
            description = description
        )
    }

}