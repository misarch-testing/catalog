package org.misarch.catalog.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import java.util.UUID

@GraphQLDescription("A value for a category characteristic.")
abstract class CategoryCharacteristicValue(
    private val characteristicId: UUID
)