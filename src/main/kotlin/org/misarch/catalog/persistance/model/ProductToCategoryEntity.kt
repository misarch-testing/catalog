package org.misarch.catalog.persistance.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table
class ProductToCategoryEntity(
    val productId: UUID,
    val categoryId: UUID,
    @Id
    val id: UUID?
) {

    companion object {
        val ENTITY = QProductToCategoryEntity.productToCategoryEntity!!
    }

}