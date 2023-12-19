package org.misarch.catalog.persistance.model

import org.misarch.catalog.graphql.model.ProductVariantVersion
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime
import java.util.*

/**
 * Entity for product variant versions
 *
 * @property name name of the product variant version
 * @property description description of the product variant version
 * @property version version of the product variant version
 * @property retailPrice retail price of the product variant version
 * @property createdAt creation timestamp of the product variant version
 * @property canBeReturnedForDays number of days the product variant version can be returned, null if it cannot be returned
 * @property productVariantId id of the referenced product variant
 * @property id unique identifier of the product variant version
 */
@Table
class ProductVariantVersionEntity(
    val name: String,
    val description: String,
    val version: Int,
    val retailPrice: Int,
    val createdAt: OffsetDateTime,
    val canBeReturnedForDays: Double?,
    val productVariantId: UUID,
    @Id
    val id: UUID?
) {

    companion object {
        /**
         * Querydsl entity
         */
        val ENTITY = QProductVariantVersionEntity.productVariantVersionEntity!!
    }

    /**
     * Convert this entity to a GraphQL DTO
     *
     * @return GraphQL DTO
     */
    fun toDTO(): ProductVariantVersion {
        return ProductVariantVersion(
            id = id!!,
            name = name,
            description = description,
            version = version,
            retailPrice = retailPrice,
            createdAt = createdAt,
            canBeReturnedForDays = canBeReturnedForDays,
            productVariantId = productVariantId
        )
    }

}