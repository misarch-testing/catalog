package org.misarch.catalog.persistence.model

import org.misarch.catalog.graphql.model.ProductVariant
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

/**
 * Entity for product variants
 *
 * @property isPubliclyVisible whether the product variant is publicly visible
 * @property productId id of the referenced product
 * @property currentVersion id of the current version of the product variant
 * @property id unique identifier of the product variant
 */
@Table
class ProductVariantEntity(
    var isPubliclyVisible: Boolean,
    val productId: UUID,
    var currentVersion: UUID?,
    @Id
    val id: UUID?
) {

    companion object {
        /**
         * Querydsl entity
         */
        val ENTITY = QProductVariantEntity.productVariantEntity!!
    }

    /**
     * Convert this entity to a GraphQL DTO
     *
     * @return GraphQL DTO
     */
    fun toDTO(): ProductVariant {
        return ProductVariant(
            id = id!!,
            isPubliclyVisible = isPubliclyVisible,
            productId = productId,
            currentVersion = currentVersion!!
        )
    }

}