package org.misarch.catalog.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import java.util.*

@GraphQLDescription("A numerical characteristic of a category.")
class NumericalCategoryCharacteristic(
    id: UUID,
    name: String,
    description: String,
    categoryId: UUID,
    @GraphQLDescription("The unit of the characteristic.")
    val unit: String,
) : CategoryCharacteristic(id, name, description, categoryId)