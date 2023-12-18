package org.misarch.catalog.persistance.model

import com.expediagroup.graphql.generator.scalars.ID
import org.misarch.catalog.graphql.model.Product
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table
class ProductEntity(
    var internalName: String,
    var isPubliclyVisible: Boolean,
    var defaultVariantId: UUID?,
    @Id
    val id: UUID?
) {

    companion object {
        val ENTITY = QProductEntity.productEntity
    }

    fun toDTO(): Product {
        return Product(
            id = id!!,
            internalName = internalName,
            isPubliclyVisible = isPubliclyVisible,
            defaultVariantId = defaultVariantId!!
        )
    }

}