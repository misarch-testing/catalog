package org.misarch.catalog.graphql.input

import com.expediagroup.graphql.generator.annotations.GraphQLDescription

@GraphQLDescription("Input to create a CategoricalCategoryCharacteristic for a Category")
open class CategoricalCategoryCharacteristicInput(
    @GraphQLDescription("The name of the CategoricalCategoryCharacteristic")
    val name: String,
    @GraphQLDescription("The description of the CategoricalCategoryCharacteristic")
    val description: String
)