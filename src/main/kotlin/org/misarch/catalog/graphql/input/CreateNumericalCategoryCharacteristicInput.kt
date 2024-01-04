package org.misarch.catalog.graphql.input

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.scalars.ID

@GraphQLDescription("Input for the createNumericalCategoryCharacteristic mutation")
class CreateNumericalCategoryCharacteristicInput(
    name: String,
    description: String,
    unit: String,
    @GraphQLDescription("The Category that the NumericalCategoryCharacteristic belongs to")
    val categoryId: ID
) : NumericalCategoryCharacteristicInput(name, description, unit)