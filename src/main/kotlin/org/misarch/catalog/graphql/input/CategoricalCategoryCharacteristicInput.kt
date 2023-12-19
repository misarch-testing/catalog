package org.misarch.catalog.graphql.input

import com.expediagroup.graphql.generator.annotations.GraphQLDescription

@GraphQLDescription("Input to create a categorical characteristic for a category")
open class CategoricalCategoryCharacteristicInput(
    @GraphQLDescription("The name of the characteristic")
    val name: String,
    @GraphQLDescription("The description of the characteristic")
    val description: String
)