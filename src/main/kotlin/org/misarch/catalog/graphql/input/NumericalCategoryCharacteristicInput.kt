package org.misarch.catalog.graphql.input

import com.expediagroup.graphql.generator.annotations.GraphQLDescription

@GraphQLDescription("Input to create a numerical characteristic for a category")
open class NumericalCategoryCharacteristicInput(
    @GraphQLDescription("The name of the characteristic")
    val name: String,
    @GraphQLDescription("The description of the characteristic")
    val description: String,
    @GraphQLDescription("The unit of the characteristic")
    val unit: String,
)