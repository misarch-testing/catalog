package org.misarch.catalog.graphql.input

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.scalars.ID

@GraphQLDescription("Input for the createCategoricalCategoryCharacteristic mutation")
class CreateCategoricalCategoryCharacteristicInput(
    name: String,
    description: String,
    @GraphQLDescription("The category that the characteristic belongs to")
    val categoryId: ID
) : CategoricalCategoryCharacteristicInput(name, description)