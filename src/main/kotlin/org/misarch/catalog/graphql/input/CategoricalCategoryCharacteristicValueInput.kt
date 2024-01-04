package org.misarch.catalog.graphql.input

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.scalars.ID

@GraphQLDescription("Input for creating a CategoricalCategoryCharacteristicValue.")
class CategoricalCategoryCharacteristicValueInput(
    @GraphQLDescription("The value of the CategoricalCategoryCharacteristicValue.")
    val value: String,
    @GraphQLDescription("The id of the CategoryCharacteristic.")
    val characteristicId: ID
)