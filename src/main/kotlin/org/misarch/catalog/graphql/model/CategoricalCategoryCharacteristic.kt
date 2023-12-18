package org.misarch.catalog.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.scalars.ID
import java.util.UUID

@GraphQLDescription("A categorical characteristic of a category.")
class CategoricalCategoryCharacteristic(
    id: UUID,
    name: String,
    description: String,
) : CategoryCharacteristic(id, name, description) {

}