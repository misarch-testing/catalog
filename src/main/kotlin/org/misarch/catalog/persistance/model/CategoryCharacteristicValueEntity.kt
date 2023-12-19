package org.misarch.catalog.persistance.model

import org.misarch.catalog.graphql.model.CategoricalCategoryCharacteristicValue
import org.misarch.catalog.graphql.model.CategoryCharacteristicValue
import org.misarch.catalog.graphql.model.NumericalCategoryCharacteristicValue
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

/**
 * Entity for category characteristic values
 * Two types of characteristic values are supported:
 * Categorical characteristic values have [stringValue] set and [doubleValue] null
 * Numerical characteristic values have [doubleValue] set and [stringValue] null
 *
 * @property discriminator discriminator to distinguish between categorical and numerical characteristics values
 * @property stringValue string value of the characteristic value, null for numerical characteristic values
 * @property doubleValue double value of the characteristic value, null for categorical characteristic values
 * @property categoryCharacteristicId id of the referenced characteristic
 * @property productVariantVersionId id of the referenced product variant version owning the value
 * @property id unique identifier of the characteristic value
 */
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

    /**
     * Discriminator to distinguish between categorical and numerical characteristics values
     */
    enum class Discriminator {
        /**
         * Categorical characteristic value, [stringValue] is not null
         */
        CATEGORICAL,

        /**
         * Numerical characteristic value, [doubleValue] is not null
         */
        NUMERICAL
    }

    companion object {
        /**
         * Querydsl entity
         */
        val ENTITY = QCategoryCharacteristicValueEntity.categoryCharacteristicValueEntity!!
    }

    /**
     * Convert this entity to a GraphQL DTO
     *
     * @return GraphQL DTO
     */
    fun toDTO(): CategoryCharacteristicValue {
        return when (discriminator) {
            Discriminator.CATEGORICAL -> CategoricalCategoryCharacteristicValue(categoryCharacteristicId, stringValue!!)
            Discriminator.NUMERICAL -> NumericalCategoryCharacteristicValue(categoryCharacteristicId, doubleValue!!)
        }
    }

}