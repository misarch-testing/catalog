package org.misarch.catalog.persistance.model

import org.misarch.catalog.graphql.model.CategoricalCategoryCharacteristic
import org.misarch.catalog.graphql.model.CategoryCharacteristic
import org.misarch.catalog.graphql.model.NumericalCategoryCharacteristic
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table

class CategoryCharacteristicEntity(
    val discriminator: Discriminator,
    val name: String,
    val description: String,
    val unit: String?,
    val categoryId: UUID,
    @Id
    val id: UUID?
) {

    enum class Discriminator {
        CATEGORICAL,
        NUMERICAL
    }

    companion object {
        val ENTITY = QCategoryCharacteristicEntity.categoryCharacteristicEntity!!
    }

    fun toDTO(): CategoryCharacteristic {
        return when (discriminator) {
            Discriminator.CATEGORICAL -> CategoricalCategoryCharacteristic(
                id = id!!,
                name = name,
                description = description
            )

            Discriminator.NUMERICAL -> NumericalCategoryCharacteristic(
                id = id!!,
                name = name,
                description = description,
                unit = unit!!
            )
        }
    }

}