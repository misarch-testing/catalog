package org.misarch.catalog.graphql.input

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.scalars.ID

@GraphQLDescription("Input for creating a NumericalCategoryCharacteristicValue.")
class NumericalCategoryCharacteristicValueInput(
    @GraphQLDescription("The value of the NumericalCategoryCharacteristicValue.")
    val value: Double,
    @GraphQLDescription("The id of the CategoryCharacteristic.")
    val characteristicId: ID
)