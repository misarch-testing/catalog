package org.misarch.catalog.graphql.input

import com.expediagroup.graphql.generator.annotations.GraphQLDescription

@GraphQLDescription("Input for the createCategory mutation")
class CreateCategoryInput(
    @GraphQLDescription("The name of the category")
    val name: String,
    @GraphQLDescription("The description of the category")
    val description: String,
    @GraphQLDescription("The categorical characteristics of the category")
    val categoricalCharacteristics: List<CategoricalCategoryCharacteristicInput>,
    @GraphQLDescription("The numerical characteristics of the category")
    val numericalCharacteristics: List<NumericalCategoryCharacteristicInput>,
)