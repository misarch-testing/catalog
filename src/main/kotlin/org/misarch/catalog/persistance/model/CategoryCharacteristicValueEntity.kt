package org.misarch.catalog.persistance.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

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

}