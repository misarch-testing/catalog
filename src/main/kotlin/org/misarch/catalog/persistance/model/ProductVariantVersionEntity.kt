package org.misarch.catalog.persistance.model

import com.expediagroup.graphql.generator.scalars.ID
import org.misarch.catalog.graphql.model.ProductVariantVersion
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime
import java.util.UUID

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
        val ENTITY = QProductVariantVersionEntity.productVariantVersionEntity
    }

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