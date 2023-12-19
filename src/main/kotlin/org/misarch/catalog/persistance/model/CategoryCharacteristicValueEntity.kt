package org.misarch.catalog.persistance.model

import org.misarch.catalog.graphql.model.CategoricalCategoryCharacteristicValue
import org.misarch.catalog.graphql.model.CategoryCharacteristicValue
import org.misarch.catalog.graphql.model.NumericalCategoryCharacteristicValue
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table
class CategoryCharacteristicValueEntity(
    val discriminator: Discriminator,
    val stringValue: String?,
    val doubleValue: Double?,
    val categoryCharacteristicId: UUID,
    val productVariantVersionId: UUID,
    @Id
    val id: UUID?
) {

    enum class Discriminator {
        CATEGORICAL,
        NUMERICAL
    }

    companion object {
        val ENTITY = QCategoryCharacteristicValueEntity.categoryCharacteristicValueEntity!!
    }

    fun toDTO(): CategoryCharacteristicValue {
        return when (discriminator) {
            Discriminator.CATEGORICAL -> CategoricalCategoryCharacteristicValue(categoryCharacteristicId, stringValue!!)
            Discriminator.NUMERICAL -> NumericalCategoryCharacteristicValue(categoryCharacteristicId, doubleValue!!)
        }
    }

}