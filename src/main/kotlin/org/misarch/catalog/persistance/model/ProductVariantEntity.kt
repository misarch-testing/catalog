package org.misarch.catalog.persistance.model

import org.misarch.catalog.graphql.model.ProductVariant
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table
class ProductVariantEntity(
    var isPubliclyVisible: Boolean,
    val productId: UUID,
    var currentVersion: UUID?,
    @Id
    val id: UUID?
) {

    companion object {
        val ENTITY = QProductVariantEntity.productVariantEntity!!
    }

    fun toDTO(): ProductVariant {
        return ProductVariant(
            id = id!!,
            isPubliclyVisible = isPubliclyVisible,
            productId = productId,
            currentVersion = currentVersion!!
        )
    }

}