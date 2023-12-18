package org.misarch.catalog.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.scalars.ID
import java.util.UUID

class Category(
    id: UUID,
    @GraphQLDescription("The name of the category.")
    val name: String,
    @GraphQLDescription("The description of the category.")
    val description: String,
) : Node(id) {
}