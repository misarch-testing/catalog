package org.misarch.catalog.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.scalars.ID
import java.util.UUID

@GraphQLDescription("A numerical characteristic of a category.")
class NumericalCategoryCharacteristic(
    id: UUID,
    name: String,
    description: String,
    @GraphQLDescription("The unit of the characteristic.")
    val unit: String,
) : CategoryCharacteristic(id, name, description) {
}