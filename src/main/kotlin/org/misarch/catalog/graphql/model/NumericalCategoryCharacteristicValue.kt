package org.misarch.catalog.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import java.util.*

@GraphQLDescription("A numerical value of a NumericalCategoryCharacteristic.")
class NumericalCategoryCharacteristicValue(
    characteristicId: UUID,
    @GraphQLDescription("The value of the NumericalCategoryCharacteristic.")
    val value: Double,
) : CategoryCharacteristicValue(characteristicId)