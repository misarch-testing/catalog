package org.misarch.catalog.persistance.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table

class CategoryCharacteristicEntity(
    val discriminator: Discriminator,
    val name: String,
    val description: String,
    val unit: String?,
    @Id
    val id: UUID?
) {

    enum class Discriminator{
        CATEGORICAL,
        NUMERICAL
    }

}