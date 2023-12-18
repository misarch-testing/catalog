package org.misarch.catalog.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.scalars.ID
import java.util.UUID

@GraphQLDescription("A characteristic of a category.")
abstract class CategoryCharacteristic(
    id: UUID,
    @GraphQLDescription("The name of the characteristic.")
    val name: String,
    @GraphQLDescription("The description of the characteristic.")
    val description: String
) : Node(id) {
}